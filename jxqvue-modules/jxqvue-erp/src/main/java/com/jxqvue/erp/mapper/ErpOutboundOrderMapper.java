package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpOutboundOrder;

/**
 * 出库单表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpOutboundOrderMapper 
{
    /**
     * 查询出库单表
     * 
     * @param id 出库单表主键
     * @return 出库单表
     */
    public ErpOutboundOrder selectErpOutboundOrderById(Long id);

    /**
     * 查询出库单表列表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderList(ErpOutboundOrder erpOutboundOrder);

    /**
     * 新增出库单表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int insertErpOutboundOrder(ErpOutboundOrder erpOutboundOrder);

    /**
     * 修改出库单表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int updateErpOutboundOrder(ErpOutboundOrder erpOutboundOrder);

    /**
     * 删除出库单表
     * 
     * @param id 出库单表主键
     * @return 结果
     */
    public int deleteErpOutboundOrderById(Long id);

    /**
     * 批量删除出库单表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpOutboundOrderByIds(Long[] ids);

    /**
     * 根据出库单号查询出库单
     * 
     * @param outboundNo 出库单号
     * @return 出库单表
     */
    public ErpOutboundOrder selectErpOutboundOrderByOutboundNo(String outboundNo);

    /**
     * 根据出库类型查询出库单列表
     * 
     * @param outboundType 出库类型
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderByType(String outboundType);

    /**
     * 根据仓库ID查询出库单列表
     * 
     * @param warehouseId 仓库ID
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderByWarehouseId(Long warehouseId);

    /**
     * 根据客户ID查询出库单列表
     * 
     * @param customerId 客户ID
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderByCustomerId(Long customerId);

    /**
     * 根据关联单据查询出库单列表
     * 
     * @param referenceType 关联单据类型
     * @param referenceId 关联单据ID
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderByReference(String referenceType, Long referenceId);

    /**
     * 根据状态查询出库单列表
     * 
     * @param status 状态
     * @return 出库单表集合
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderByStatus(String status);

    /**
     * 更新出库单状态
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int updateErpOutboundOrderStatus(ErpOutboundOrder erpOutboundOrder);

    /**
     * 查询出库单统计信息
     * 
     * @param erpOutboundOrder 出库单表
     * @return 统计结果
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderStatistics(ErpOutboundOrder erpOutboundOrder);
}
