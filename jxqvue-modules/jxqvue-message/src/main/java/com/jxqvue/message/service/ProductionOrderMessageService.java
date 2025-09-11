package com.jxqvue.message.service;

// import com.jxqvue.erp.domain.ErpProductionOrder; // 注释掉，避免循环依赖
import com.jxqvue.message.domain.Message;
import com.jxqvue.message.domain.MessageReceiver;
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
 * 生产订单消息服务
 * 
 * @author jxqvue
 * @date 2025-01-27
 */
@Service
public class ProductionOrderMessageService {

    @Autowired
    private IMessageService messageService;

    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 发送生产订单下达通知
     * 
     * @param productionOrder 生产订单（使用Map传递数据避免循环依赖）
     * @param receiverUserIds 接收用户ID列表
     */
    @Transactional
    public void sendProductionOrderReleaseNotification(Map<String, Object> productionOrder, List<Long> receiverUserIds) {
        if (productionOrder == null || receiverUserIds == null || receiverUserIds.isEmpty()) {
            return;
        }

        try {
            // 获取消息模板
            MessageTemplate template = messageTemplateService.selectMessageTemplateByTemplateCode("PRODUCTION_ORDER_RELEASE");
            if (template == null) {
                System.err.println("生产订单下达消息模板不存在");
                return;
            }

            // 准备模板变量
            Map<String, Object> variables = prepareTemplateVariables(productionOrder);

            // 创建消息
            Message message = new Message();
            message.setMessageType("2"); // 业务消息
            message.setMessageLevel("2"); // 重要级别
            message.setTitle(replaceTemplateVariables(template.getTitleTemplate(), variables));
            message.setContent(replaceTemplateVariables(template.getContentTemplate(), variables));
            message.setCreateBy("system");
            message.setCreateTime(DateUtils.getNowDate());
            message.setRemark("生产订单下达自动通知");

            // 创建接收者列表
            List<MessageReceiver> receivers = new ArrayList<>();
            for (Long userId : receiverUserIds) {
                MessageReceiver receiver = new MessageReceiver();
                receiver.setReceiverId(userId);
                receiver.setReceiverName("用户" + userId); // 这里应该查询用户名称
                receiver.setReadStatus("0"); // 未读
                receiver.setCreateBy("system");
                receiver.setCreateTime(DateUtils.getNowDate());
                receivers.add(receiver);
            }

            // 发送消息（使用MessageRequest）
            com.jxqvue.message.domain.MessageRequest messageRequest = new com.jxqvue.message.domain.MessageRequest();
            messageRequest.setMessageType("2");
            messageRequest.setMessageLevel("2");
            messageRequest.setTitle(replaceTemplateVariables(template.getTitleTemplate(), variables));
            messageRequest.setContent(replaceTemplateVariables(template.getContentTemplate(), variables));
            messageRequest.setReceivers(receivers);
            messageRequest.setCreateBy("system");
            messageRequest.setRemark("生产订单下达自动通知");

            messageService.insertMessageWithReceivers(messageRequest);

        } catch (Exception e) {
            System.err.println("发送生产订单下达通知失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 准备模板变量
     * 
     * @param productionOrder 生产订单（使用Map传递数据避免循环依赖）
     * @return 变量映射
     */
    private Map<String, Object> prepareTemplateVariables(Map<String, Object> productionOrder) {
        Map<String, Object> variables = new HashMap<>();
        
        variables.put("orderCode", StringUtils.isNotEmpty((String)productionOrder.get("orderCode")) ? (String)productionOrder.get("orderCode") : "");
        variables.put("orderName", StringUtils.isNotEmpty((String)productionOrder.get("orderName")) ? (String)productionOrder.get("orderName") : "");
        variables.put("productName", StringUtils.isNotEmpty((String)productionOrder.get("productName")) ? (String)productionOrder.get("productName") : "");
        variables.put("plannedQuantity", productionOrder.get("plannedQuantity") != null ? productionOrder.get("plannedQuantity").toString() : "0");
        variables.put("unitName", StringUtils.isNotEmpty((String)productionOrder.get("unitName")) ? (String)productionOrder.get("unitName") : "");
        
        // 格式化日期
        if (productionOrder.get("plannedStartDate") != null) {
            variables.put("plannedStartDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", (java.util.Date)productionOrder.get("plannedStartDate")));
        } else {
            variables.put("plannedStartDate", "");
        }
        
        if (productionOrder.get("plannedEndDate") != null) {
            variables.put("plannedEndDate", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm", (java.util.Date)productionOrder.get("plannedEndDate")));
        } else {
            variables.put("plannedEndDate", "");
        }
        
        // 优先级处理
        String priorityText = "普通";
        if (productionOrder.get("priority") != null) {
            Integer priority = (Integer)productionOrder.get("priority");
            switch (priority) {
                case 1:
                    priorityText = "低";
                    break;
                case 2:
                    priorityText = "普通";
                    break;
                case 3:
                    priorityText = "高";
                    break;
                case 4:
                    priorityText = "紧急";
                    break;
                default:
                    priorityText = "普通";
                    break;
            }
        }
        variables.put("priority", priorityText);
        
        return variables;
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
     * 根据生产订单获取相关用户ID列表
     * 这里需要根据实际业务逻辑来实现
     * 例如：根据工作中心、部门、角色等获取相关用户
     * 
     * @param productionOrder 生产订单（使用Map传递数据避免循环依赖）
     * @return 用户ID列表
     */
    public List<Long> getRelatedUserIds(Map<String, Object> productionOrder) {
        List<Long> userIds = new ArrayList<>();
        
        // TODO: 根据实际业务逻辑获取相关用户
        // 例如：
        // 1. 根据工作中心获取相关用户
        // 2. 根据部门获取相关用户
        // 3. 根据角色获取相关用户
        // 4. 根据订单创建者获取相关用户
        
        // 临时实现：返回空列表，需要根据实际业务逻辑完善
        return userIds;
    }
}
