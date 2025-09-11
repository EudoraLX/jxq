package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpProductionOrderMapper;
import com.jxqvue.erp.domain.ErpProductionOrder;
import com.jxqvue.erp.domain.ErpProductionOrderDetail;
import com.jxqvue.erp.domain.ErpProductionOrderMaterials;
import com.jxqvue.erp.service.IErpProductionOrderService;
import com.jxqvue.erp.utils.NumberGeneratorUtil;
import com.jxqvue.message.service.UnifiedMessageService;

/**
 * 生产订单Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@Service
public class ErpProductionOrderServiceImpl implements IErpProductionOrderService 
{
    @Autowired
    private ErpProductionOrderMapper erpProductionOrderMapper;
    
    @Autowired
    private NumberGeneratorUtil numberGeneratorUtil;
    
    @Autowired
    private UnifiedMessageService unifiedMessageService;

    /**
     * 查询生产订单
     * 
     * @param id 生产订单主键
     * @return 生产订单
     */
    @Override
    public ErpProductionOrder selectErpProductionOrderById(Long id)
    {
        return erpProductionOrderMapper.selectErpProductionOrderById(id);
    }

    /**
     * 查询生产订单列表
     * 
     * @param erpProductionOrder 生产订单
     * @return 生产订单
     */
    @Override
    public List<ErpProductionOrder> selectErpProductionOrderList(ErpProductionOrder erpProductionOrder)
    {
        return erpProductionOrderMapper.selectErpProductionOrderList(erpProductionOrder);
    }

    /**
     * 新增生产订单
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertErpProductionOrder(ErpProductionOrder erpProductionOrder)
    {
        // 自动生成订单编码（仅新增时）
        if (StringUtils.isEmpty(erpProductionOrder.getOrderCode())) {
            String generatedOrderCode = numberGeneratorUtil.generateNumber("PRODUCTION_ORDER");
            erpProductionOrder.setOrderCode(generatedOrderCode);
        }
        
        erpProductionOrder.setCreateTime(DateUtils.getNowDate());
        int rows = erpProductionOrderMapper.insertErpProductionOrder(erpProductionOrder);
        insertErpProductionOrderDetail(erpProductionOrder);
        insertErpProductionOrderMaterials(erpProductionOrder);
        return rows;
    }

    /**
     * 修改生产订单
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateErpProductionOrder(ErpProductionOrder erpProductionOrder)
    {
        erpProductionOrder.setUpdateTime(DateUtils.getNowDate());
        erpProductionOrderMapper.deleteErpProductionOrderDetailByOrderId(erpProductionOrder.getId());
        insertErpProductionOrderDetail(erpProductionOrder);
        erpProductionOrderMapper.deleteErpProductionOrderMaterialsByOrderId(erpProductionOrder.getId());
        insertErpProductionOrderMaterials(erpProductionOrder);
        return erpProductionOrderMapper.updateErpProductionOrder(erpProductionOrder);
    }

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的生产订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpProductionOrderByIds(Long[] ids)
    {
        erpProductionOrderMapper.deleteErpProductionOrderDetailByOrderIds(ids);
        erpProductionOrderMapper.deleteErpProductionOrderMaterialsByOrderIds(ids);
        return erpProductionOrderMapper.deleteErpProductionOrderByIds(ids);
    }

    /**
     * 删除生产订单信息
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpProductionOrderById(Long id)
    {
        erpProductionOrderMapper.deleteErpProductionOrderDetailByOrderId(id);
        erpProductionOrderMapper.deleteErpProductionOrderMaterialsByOrderId(id);
        return erpProductionOrderMapper.deleteErpProductionOrderById(id);
    }

    /**
     * 新增生产订单明细信息
     * 
     * @param erpProductionOrder 生产订单对象
     */
    public void insertErpProductionOrderDetail(ErpProductionOrder erpProductionOrder)
    {
        List<ErpProductionOrderDetail> erpProductionOrderDetailList = erpProductionOrder.getErpProductionOrderDetailList();
        Long id = erpProductionOrder.getId();
        if (StringUtils.isNotNull(erpProductionOrderDetailList))
        {
            List<ErpProductionOrderDetail> list = new ArrayList<ErpProductionOrderDetail>();
            for (ErpProductionOrderDetail erpProductionOrderDetail : erpProductionOrderDetailList)
            {
                erpProductionOrderDetail.setOrderId(id);
                list.add(erpProductionOrderDetail);
            }
            if (list.size() > 0)
            {
                erpProductionOrderMapper.batchErpProductionOrderDetail(list);
            }
        }
    }

    /**
     * 新增生产订单物料信息
     * 
     * @param erpProductionOrder 生产订单对象
     */
    public void insertErpProductionOrderMaterials(ErpProductionOrder erpProductionOrder)
    {
        List<ErpProductionOrderMaterials> erpProductionOrderMaterialsList = erpProductionOrder.getErpProductionOrderMaterialsList();
        Long id = erpProductionOrder.getId();
        if (StringUtils.isNotNull(erpProductionOrderMaterialsList))
        {
            List<ErpProductionOrderMaterials> list = new ArrayList<ErpProductionOrderMaterials>();
            for (ErpProductionOrderMaterials erpProductionOrderMaterials : erpProductionOrderMaterialsList)
            {
                erpProductionOrderMaterials.setOrderId(id);
                list.add(erpProductionOrderMaterials);
            }
            if (list.size() > 0)
            {
                erpProductionOrderMapper.batchErpProductionOrderMaterials(list);
            }
        }
    }

    /**
     * 更新生产订单状态
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpProductionOrderStatus(ErpProductionOrder erpProductionOrder)
    {
        erpProductionOrder.setUpdateTime(DateUtils.getNowDate());
        int result = erpProductionOrderMapper.updateErpProductionOrderStatus(erpProductionOrder);
        
        // 如果状态更新为"下达"（状态值为"2"），发送消息通知
        if (result > 0 && "2".equals(erpProductionOrder.getStatus())) {
            try {
                // 获取完整的生产订单信息
                ErpProductionOrder fullOrder = erpProductionOrderMapper.selectErpProductionOrderById(erpProductionOrder.getId());
                if (fullOrder != null) {
                    // 准备订单数据
                    Map<String, Object> orderData = new HashMap<>();
                    orderData.put("orderCode", fullOrder.getOrderCode());
                    orderData.put("orderName", fullOrder.getOrderName());
                    orderData.put("productName", fullOrder.getProductName());
                    orderData.put("plannedQuantity", fullOrder.getPlannedQuantity());
                    orderData.put("unitName", fullOrder.getUnitName());
                    orderData.put("plannedStartDate", fullOrder.getPlannedStartDate());
                    orderData.put("plannedEndDate", fullOrder.getPlannedEndDate());
                    orderData.put("priority", fullOrder.getPriority());
                    
                    // 获取相关用户ID列表（这里需要根据实际业务逻辑实现）
                    List<Long> relatedUserIds = getRelatedUserIds(fullOrder);
                    if (!relatedUserIds.isEmpty()) {
                        // 发送生产订单下达通知
                        unifiedMessageService.sendProductionOrderReleaseNotification(orderData, relatedUserIds);
                    }
                }
            } catch (Exception e) {
                // 消息发送失败不影响主流程，只记录日志
                System.err.println("发送生产订单下达通知失败: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 查询生产订单对应的工序列表
     * 
     * @param orderId 生产订单ID
     * @return 工序列表
     */
    @Override
    public List<Object> selectOrderProcesses(Long orderId)
    {
        return erpProductionOrderMapper.selectOrderProcesses(orderId);
    }

    /**
     * 根据订单编码查询生产订单
     * 
     * @param orderCode 订单编码
     * @return 生产订单
     */
    @Override
    public ErpProductionOrder selectErpProductionOrderByCode(String orderCode)
    {
        return erpProductionOrderMapper.selectErpProductionOrderByCode(orderCode);
    }

    /**
     * 根据生产订单获取相关用户ID列表
     * 这里需要根据实际业务逻辑来实现
     * 例如：根据工作中心、部门、角色等获取相关用户
     * 
     * @param productionOrder 生产订单
     * @return 用户ID列表
     */
    private List<Long> getRelatedUserIds(ErpProductionOrder productionOrder) {
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
