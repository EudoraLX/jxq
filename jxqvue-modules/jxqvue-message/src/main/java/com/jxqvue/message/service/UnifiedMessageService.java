package com.jxqvue.message.service;

import com.jxqvue.message.domain.MessageRequest;
import com.jxqvue.message.domain.MessageTemplate;
import com.jxqvue.message.service.IMessageService;
import com.jxqvue.message.service.IMessageTemplateService;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 统一消息发送服务
 * 为所有模块提供统一的消息发送接口
 * 
 * @author jxqvue
 * @date 2025-01-27
 */
@Service
public class UnifiedMessageService {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 根据模板编码发送消息
     * 
     * @param templateCode 模板编码
     * @param variables 模板变量
     * @param receiverUserIds 接收用户ID列表
     * @return 发送结果
     */
    @Transactional
    public boolean sendMessageByTemplate(String templateCode, Map<String, Object> variables, List<Long> receiverUserIds) {
        if (StringUtils.isEmpty(templateCode) || receiverUserIds == null || receiverUserIds.isEmpty()) {
            return false;
        }

        try {
            // 获取消息模板
            MessageTemplate template = messageTemplateService.selectMessageTemplateByTemplateCode(templateCode);
            if (template == null) {
                System.err.println("消息模板不存在: " + templateCode);
                return false;
            }

            // 检查模板状态
            if (!"0".equals(template.getStatus())) {
                System.err.println("消息模板已禁用: " + templateCode);
                return false;
            }

            // 替换模板变量
            String title = replaceTemplateVariables(template.getTitleTemplate(), variables);
            String content = replaceTemplateVariables(template.getContentTemplate(), variables);

            // 创建消息请求
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setMessageType(template.getMessageType());
            messageRequest.setMessageLevel(template.getMessageLevel());
            messageRequest.setTitle(title);
            messageRequest.setContent(content);
            messageRequest.setCreateBy("system");
            messageRequest.setRemark("系统自动发送");

            // 创建接收者列表
            List<com.jxqvue.message.domain.MessageReceiver> receivers = new ArrayList<>();
            for (Long userId : receiverUserIds) {
                com.jxqvue.message.domain.MessageReceiver receiver = new com.jxqvue.message.domain.MessageReceiver();
                receiver.setReceiverId(userId);
                receiver.setReceiverName("用户" + userId); // 这里应该查询用户名称
                receiver.setReadStatus("0"); // 未读
                receiver.setCreateBy("system");
                receiver.setCreateTime(DateUtils.getNowDate());
                receivers.add(receiver);
            }
            messageRequest.setReceivers(receivers);

            // 发送消息
            int result = messageService.insertMessageWithReceivers(messageRequest);
            return result > 0;

        } catch (Exception e) {
            System.err.println("发送消息失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 发送生产订单下达通知
     * 
     * @param orderData 订单数据
     * @param receiverUserIds 接收用户ID列表
     * @return 发送结果
     */
    public boolean sendProductionOrderReleaseNotification(Map<String, Object> orderData, List<Long> receiverUserIds) {
        // 准备模板变量
        Map<String, Object> variables = prepareProductionOrderVariables(orderData);
        
        // 调用通用发送方法
        return sendMessageByTemplate("PRODUCTION_ORDER_RELEASE", variables, receiverUserIds);
    }

    /**
     * 发送BOM审批通知
     * 
     * @param bomData BOM数据
     * @param receiverUserIds 接收用户ID列表
     * @return 发送结果
     */
    public boolean sendBomApprovalNotification(Map<String, Object> bomData, List<Long> receiverUserIds) {
        // 准备模板变量
        Map<String, Object> variables = prepareBomVariables(bomData);
        
        // 调用通用发送方法
        return sendMessageByTemplate("BOM_APPROVAL_NOTIFICATION", variables, receiverUserIds);
    }

    /**
     * 发送库存预警通知
     * 
     * @param inventoryData 库存数据
     * @param receiverUserIds 接收用户ID列表
     * @return 发送结果
     */
    public boolean sendInventoryAlertNotification(Map<String, Object> inventoryData, List<Long> receiverUserIds) {
        // 准备模板变量
        Map<String, Object> variables = prepareInventoryVariables(inventoryData);
        
        // 调用通用发送方法
        return sendMessageByTemplate("INVENTORY_ALERT_NOTIFICATION", variables, receiverUserIds);
    }

    /**
     * 发送自定义消息
     * 
     * @param title 消息标题
     * @param content 消息内容
     * @param messageType 消息类型
     * @param messageLevel 消息级别
     * @param receiverUserIds 接收用户ID列表
     * @return 发送结果
     */
    public boolean sendCustomMessage(String title, String content, String messageType, String messageLevel, List<Long> receiverUserIds) {
        if (StringUtils.isEmpty(title) || StringUtils.isEmpty(content) || receiverUserIds == null || receiverUserIds.isEmpty()) {
            return false;
        }

        try {
            // 创建消息请求
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setMessageType(StringUtils.isNotEmpty(messageType) ? messageType : "1");
            messageRequest.setMessageLevel(StringUtils.isNotEmpty(messageLevel) ? messageLevel : "1");
            messageRequest.setTitle(title);
            messageRequest.setContent(content);
            messageRequest.setCreateBy("system");
            messageRequest.setRemark("自定义消息");

            // 创建接收者列表
            List<com.jxqvue.message.domain.MessageReceiver> receivers = new ArrayList<>();
            for (Long userId : receiverUserIds) {
                com.jxqvue.message.domain.MessageReceiver receiver = new com.jxqvue.message.domain.MessageReceiver();
                receiver.setReceiverId(userId);
                receiver.setReceiverName("用户" + userId);
                receiver.setReadStatus("0");
                receiver.setCreateBy("system");
                receiver.setCreateTime(DateUtils.getNowDate());
                receivers.add(receiver);
            }
            messageRequest.setReceivers(receivers);

            // 发送消息
            int result = messageService.insertMessageWithReceivers(messageRequest);
            return result > 0;

        } catch (Exception e) {
            System.err.println("发送自定义消息失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 替换模板变量
     * 
     * @param template 模板字符串
     * @param variables 变量映射
     * @return 替换后的字符串
     */
    private String replaceTemplateVariables(String template, Map<String, Object> variables) {
        if (StringUtils.isEmpty(template) || variables == null) {
            return template;
        }

        String result = template;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue() != null ? entry.getValue().toString() : "";
            result = result.replace(placeholder, value);
        }

        return result;
    }

    /**
     * 准备生产订单模板变量
     * 
     * @param orderData 订单数据
     * @return 变量映射
     */
    private Map<String, Object> prepareProductionOrderVariables(Map<String, Object> orderData) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("orderCode", StringUtils.isNotEmpty((String)orderData.get("orderCode")) ? (String)orderData.get("orderCode") : "");
        variables.put("orderName", StringUtils.isNotEmpty((String)orderData.get("orderName")) ? (String)orderData.get("orderName") : "");
        variables.put("productName", StringUtils.isNotEmpty((String)orderData.get("productName")) ? (String)orderData.get("productName") : "");
        variables.put("plannedQuantity", orderData.get("plannedQuantity") != null ? orderData.get("plannedQuantity").toString() : "0");
        variables.put("unitName", StringUtils.isNotEmpty((String)orderData.get("unitName")) ? (String)orderData.get("unitName") : "");
        
        // 格式化日期
        if (orderData.get("plannedStartDate") != null) {
            variables.put("plannedStartDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", (java.util.Date)orderData.get("plannedStartDate")));
        } else {
            variables.put("plannedStartDate", "");
        }
        
        if (orderData.get("plannedEndDate") != null) {
            variables.put("plannedEndDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", (java.util.Date)orderData.get("plannedEndDate")));
        } else {
            variables.put("plannedEndDate", "");
        }
        
        // 优先级处理
        String priorityText = "普通";
        if (orderData.get("priority") != null) {
            Integer priority = (Integer)orderData.get("priority");
            switch (priority) {
                case 1: priorityText = "低"; break;
                case 2: priorityText = "普通"; break;
                case 3: priorityText = "高"; break;
                case 4: priorityText = "紧急"; break;
                default: priorityText = "普通"; break;
            }
        }
        variables.put("priority", priorityText);
        
        return variables;
    }

    /**
     * 准备BOM模板变量
     * 
     * @param bomData BOM数据
     * @return 变量映射
     */
    private Map<String, Object> prepareBomVariables(Map<String, Object> bomData) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("bomCode", StringUtils.isNotEmpty((String)bomData.get("bomCode")) ? (String)bomData.get("bomCode") : "");
        variables.put("bomName", StringUtils.isNotEmpty((String)bomData.get("bomName")) ? (String)bomData.get("bomName") : "");
        variables.put("productName", StringUtils.isNotEmpty((String)bomData.get("productName")) ? (String)bomData.get("productName") : "");
        variables.put("version", StringUtils.isNotEmpty((String)bomData.get("version")) ? (String)bomData.get("version") : "");
        variables.put("approvalStatus", StringUtils.isNotEmpty((String)bomData.get("approvalStatus")) ? (String)bomData.get("approvalStatus") : "");
        
        return variables;
    }

    /**
     * 准备库存模板变量
     * 
     * @param inventoryData 库存数据
     * @return 变量映射
     */
    private Map<String, Object> prepareInventoryVariables(Map<String, Object> inventoryData) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("materialCode", StringUtils.isNotEmpty((String)inventoryData.get("materialCode")) ? (String)inventoryData.get("materialCode") : "");
        variables.put("materialName", StringUtils.isNotEmpty((String)inventoryData.get("materialName")) ? (String)inventoryData.get("materialName") : "");
        variables.put("currentQuantity", inventoryData.get("currentQuantity") != null ? inventoryData.get("currentQuantity").toString() : "0");
        variables.put("minQuantity", inventoryData.get("minQuantity") != null ? inventoryData.get("minQuantity").toString() : "0");
        variables.put("alertType", StringUtils.isNotEmpty((String)inventoryData.get("alertType")) ? (String)inventoryData.get("alertType") : "");
        
        return variables;
    }
}
