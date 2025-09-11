package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpOutboundOrder;

/**
 * 出库单表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpOutboundOrderService 
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
     * 批量删除出库单表
     * 
     * @param ids 需要删除的出库单表主键集合
     * @return 结果
     */
    public int deleteErpOutboundOrderByIds(Long[] ids);

    /**
     * 删除出库单表信息
     * 
     * @param id 出库单表主键
     * @return 结果
     */
    public int deleteErpOutboundOrderById(Long id);

    /**
     * 根据出库单号查询出库单
     * 
     * @param outboundNo 出库单号
     * @return 出库单表
     */
    public ErpOutboundOrder selectErpOutboundOrderByOutboundNo(String outboundNo);

    /**
     * 审核出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int approveErpOutboundOrder(ErpOutboundOrder erpOutboundOrder);

    /**
     * 执行出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int executeErpOutboundOrder(ErpOutboundOrder erpOutboundOrder);

    /**
     * 取消出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    public int cancelErpOutboundOrder(ErpOutboundOrder erpOutboundOrder);

    /**
     * 生成出库单号
     * 
     * @return 出库单号
     */
    public String generateOutboundNo();

    /**
     * 查询出库单统计信息
     * 
     * @param erpOutboundOrder 出库单表
     * @return 统计结果
     */
    public List<ErpOutboundOrder> selectErpOutboundOrderStatistics(ErpOutboundOrder erpOutboundOrder);

    /**
     * 检查库存是否充足
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 出库数量
     * @return 是否充足
     */
    public boolean checkInventorySufficient(Long materialId, Long warehouseId, java.math.BigDecimal quantity);

    /**
     * 按FIFO策略分配库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 出库数量
     * @return 分配结果
     */
    public List<ErpOutboundOrder> allocateInventoryByFIFO(Long materialId, Long warehouseId, java.math.BigDecimal quantity);
}
