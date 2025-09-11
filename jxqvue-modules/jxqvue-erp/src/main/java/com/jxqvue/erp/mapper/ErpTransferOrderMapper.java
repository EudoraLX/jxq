package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpTransferOrder;

/**
 * 调拨单表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpTransferOrderMapper 
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
     * 删除调拨单表
     * 
     * @param id 调拨单表主键
     * @return 结果
     */
    public int deleteErpTransferOrderById(Long id);

    /**
     * 批量删除调拨单表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpTransferOrderByIds(Long[] ids);

    /**
     * 根据调拨单号查询调拨单
     * 
     * @param transferNo 调拨单号
     * @return 调拨单表
     */
    public ErpTransferOrder selectErpTransferOrderByTransferNo(String transferNo);

    /**
     * 根据调拨类型查询调拨单列表
     * 
     * @param transferType 调拨类型
     * @return 调拨单表集合
     */
    public List<ErpTransferOrder> selectErpTransferOrderByType(String transferType);

    /**
     * 根据源仓库ID查询调拨单列表
     * 
     * @param fromWarehouseId 源仓库ID
     * @return 调拨单表集合
     */
    public List<ErpTransferOrder> selectErpTransferOrderByFromWarehouseId(Long fromWarehouseId);

    /**
     * 根据目标仓库ID查询调拨单列表
     * 
     * @param toWarehouseId 目标仓库ID
     * @return 调拨单表集合
     */
    public List<ErpTransferOrder> selectErpTransferOrderByToWarehouseId(Long toWarehouseId);

    /**
     * 根据状态查询调拨单列表
     * 
     * @param status 状态
     * @return 调拨单表集合
     */
    public List<ErpTransferOrder> selectErpTransferOrderByStatus(String status);

    /**
     * 更新调拨单状态
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    public int updateErpTransferOrderStatus(ErpTransferOrder erpTransferOrder);

    /**
     * 查询调拨单统计信息
     * 
     * @param erpTransferOrder 调拨单表
     * @return 统计结果
     */
    public List<ErpTransferOrder> selectErpTransferOrderStatistics(ErpTransferOrder erpTransferOrder);
}
