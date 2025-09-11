package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionOrder;

/**
 * 生产订单Service接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface IErpProductionOrderService 
{
    /**
     * 查询生产订单
     * 
     * @param id 生产订单主键
     * @return 生产订单
     */
    public ErpProductionOrder selectErpProductionOrderById(Long id);

    /**
     * 查询生产订单列表
     * 
     * @param erpProductionOrder 生产订单
     * @return 生产订单集合
     */
    public List<ErpProductionOrder> selectErpProductionOrderList(ErpProductionOrder erpProductionOrder);

    /**
     * 新增生产订单
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    public int insertErpProductionOrder(ErpProductionOrder erpProductionOrder);

    /**
     * 修改生产订单
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    public int updateErpProductionOrder(ErpProductionOrder erpProductionOrder);

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的生产订单主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderByIds(Long[] ids);

    /**
     * 删除生产订单信息
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    public int deleteErpProductionOrderById(Long id);

    /**
     * 更新生产订单状态
     * 
     * @param erpProductionOrder 生产订单
     * @return 结果
     */
    public int updateErpProductionOrderStatus(ErpProductionOrder erpProductionOrder);

    /**
     * 查询生产订单对应的工序列表
     * 
     * @param orderId 生产订单ID
     * @return 工序列表
     */
    public List<Object> selectOrderProcesses(Long orderId);

    /**
     * 根据订单编码查询生产订单
     * 
     * @param orderCode 订单编码
     * @return 生产订单
     */
    public ErpProductionOrder selectErpProductionOrderByCode(String orderCode);
}
