package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpTransferOrder;

/**
 * 调拨单表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpTransferOrderService 
{
    /**
     * 查询调拨单表
     * 
     * @param id 调拨单表主键
     * @return 调拨单表
     */
    public ErpTransferOrder selectErpTransferOrderById(Long id);

    /**
     * 查询调拨单表列表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 调拨单表集合
     */
    public List<ErpTransferOrder> selectErpTransferOrderList(ErpTransferOrder erpTransferOrder);

    /**
     * 新增调拨单表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int insertErpTransferOrder(ErpTransferOrder erpTransferOrder);

    /**
     * 修改调拨单表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int updateErpTransferOrder(ErpTransferOrder erpTransferOrder);

    /**
     * 批量删除调拨单表
     * 
     * @param ids 需要删除的调拨单表主键集合
     * @return 结果
     */
    public int deleteErpTransferOrderByIds(Long[] ids);

    /**
     * 删除调拨单表信息
     * 
     * @param id 调拨单表主键
     * @return 结果
     */
    public int deleteErpTransferOrderById(Long id);

    /**
     * 根据调拨单号查询调拨单
     * 
     * @param transferNo 调拨单号
     * @return 调拨单表
     */
    public ErpTransferOrder selectErpTransferOrderByTransferNo(String transferNo);

    /**
     * 审核调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int approveErpTransferOrder(ErpTransferOrder erpTransferOrder);

    /**
     * 执行调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int executeErpTransferOrder(ErpTransferOrder erpTransferOrder);

    /**
     * 取消调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int cancelErpTransferOrder(ErpTransferOrder erpTransferOrder);

    /**
     * 生成调拨单号
     * 
     * @return 调拨单号
     */
    public String generateTransferNo();

    /**
     * 查询调拨单统计信息
     * 
     * @param erpTransferOrder 调拨单表
     * @return 统计结果
     */
    public List<ErpTransferOrder> selectErpTransferOrderStatistics(ErpTransferOrder erpTransferOrder);

    /**
     * 检查源库存是否充足
     * 
     * @param materialId 物料ID
     * @param fromWarehouseId 源仓库ID
     * @param quantity 调拨数量
     * @return 是否充足
     */
    public boolean checkFromInventorySufficient(Long materialId, Long fromWarehouseId, java.math.BigDecimal quantity);

    /**
     * 检查目标仓库是否可用
     * 
     * @param toWarehouseId 目标仓库ID
     * @return 是否可用
     */
    public boolean checkToWarehouseAvailable(Long toWarehouseId);
}
