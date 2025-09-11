package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionOrder;
import com.jxqvue.erp.domain.ErpProductionOrderDetail;
import com.jxqvue.erp.domain.ErpProductionOrderMaterials;

/**
 * 生产订单Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface ErpProductionOrderMapper 
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
     * 删除生产订单
     * 
     * @param id 生产订单主键
     * @return 结果
     */
    public int deleteErpProductionOrderById(Long id);

    /**
     * 批量删除生产订单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderByIds(Long[] ids);

    /**
     * 批量删除生产订单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderDetailByOrderIds(Long[] ids);
    
    /**
     * 批量新增生产订单明细
     * 
     * @param erpProductionOrderDetailList 生产订单明细列表
     * @return 结果
     */
    public int batchErpProductionOrderDetail(List<ErpProductionOrderDetail> erpProductionOrderDetailList);
    

    /**
     * 通过生产订单主键删除生产订单明细信息
     * 
     * @param id 生产订单ID
     * @return 结果
     */
    public int deleteErpProductionOrderDetailByOrderId(Long id);

    /**
     * 批量删除生产订单物料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsByOrderIds(Long[] ids);
    
    /**
     * 批量新增生产订单物料
     * 
     * @param erpProductionOrderMaterialsList 生产订单物料列表
     * @return 结果
     */
    public int batchErpProductionOrderMaterials(List<ErpProductionOrderMaterials> erpProductionOrderMaterialsList);
    

    /**
     * 通过生产订单主键删除生产订单物料信息
     * 
     * @param id 生产订单ID
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsByOrderId(Long id);

    /**
     * 根据订单ID查询生产订单明细列表
     * 
     * @param id 订单ID
     * @return 生产订单明细集合
     */
    public List<ErpProductionOrderDetail> selectErpProductionOrderDetailList(Long id);

    /**
     * 根据订单ID查询生产订单物料列表
     * 
     * @param id 订单ID
     * @return 生产订单物料集合
     */
    public List<ErpProductionOrderMaterials> selectErpProductionOrderMaterialsList(Long id);

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
