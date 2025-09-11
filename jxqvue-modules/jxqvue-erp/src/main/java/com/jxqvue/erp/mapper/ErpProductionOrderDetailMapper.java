package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionOrderDetail;

/**
 * 生产订单明细Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface ErpProductionOrderDetailMapper 
{
    /**
     * 查询生产订单明细
     * 
     * @param id 生产订单明细主键
     * @return 生产订单明细
     */
    public ErpProductionOrderDetail selectErpProductionOrderDetailById(Long id);

    /**
     * 查询生产订单明细列表
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 生产订单明细集合
     */
    public List<ErpProductionOrderDetail> selectErpProductionOrderDetailList(ErpProductionOrderDetail erpProductionOrderDetail);

    /**
     * 根据订单ID查询生产订单明细列表
     * 
     * @param orderId 订单ID
     * @return 生产订单明细集合
     */
    public List<ErpProductionOrderDetail> selectErpProductionOrderDetailByOrderId(Long orderId);

    /**
     * 新增生产订单明细
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 结果
     */
    public int insertErpProductionOrderDetail(ErpProductionOrderDetail erpProductionOrderDetail);

    /**
     * 修改生产订单明细
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 结果
     */
    public int updateErpProductionOrderDetail(ErpProductionOrderDetail erpProductionOrderDetail);

    /**
     * 删除生产订单明细
     * 
     * @param id 生产订单明细主键
     * @return 结果
     */
    public int deleteErpProductionOrderDetailById(Long id);

    /**
     * 批量删除生产订单明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderDetailByIds(Long[] ids);

    /**
     * 根据订单ID删除生产订单明细
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteErpProductionOrderDetailByOrderId(Long orderId);

    /**
     * 根据订单ID批量删除生产订单明细
     * 
     * @param orderIds 订单ID数组
     * @return 结果
     */
    public int deleteErpProductionOrderDetailByOrderIds(Long[] orderIds);

    /**
     * 批量新增生产订单明细
     * 
     * @param erpProductionOrderDetailList 生产订单明细列表
     * @return 结果
     */
    public int batchErpProductionOrderDetail(List<ErpProductionOrderDetail> erpProductionOrderDetailList);
}
