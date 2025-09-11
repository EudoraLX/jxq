package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryTransaction;

/**
 * 库存事务表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryTransactionMapper 
{
    /**
     * 查询库存事务表
     * 
     * @param id 库存事务表主键
     * @return 库存事务表
     */
    public ErpInventoryTransaction selectErpInventoryTransactionById(Long id);

    /**
     * 查询库存事务表列表
     * 
     * @param erpInventoryTransaction 库存事务表
     * @return 库存事务表集合
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionList(ErpInventoryTransaction erpInventoryTransaction);

    /**
     * 新增库存事务表
     * 
     * @param erpInventoryTransaction 库存事务表
     * @return 结果
     */
    public int insertErpInventoryTransaction(ErpInventoryTransaction erpInventoryTransaction);

    /**
     * 修改库存事务表
     * 
     * @param erpInventoryTransaction 库存事务表
     * @return 结果
     */
    public int updateErpInventoryTransaction(ErpInventoryTransaction erpInventoryTransaction);

    /**
     * 删除库存事务表
     * 
     * @param id 库存事务表主键
     * @return 结果
     */
    public int deleteErpInventoryTransactionById(Long id);

    /**
     * 批量删除库存事务表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryTransactionByIds(Long[] ids);

    /**
     * 根据事务单号查询库存事务
     * 
     * @param transactionNo 事务单号
     * @return 库存事务表
     */
    public ErpInventoryTransaction selectErpInventoryTransactionByTransactionNo(String transactionNo);

    /**
     * 根据物料ID查询库存事务列表
     * 
     * @param materialId 物料ID
     * @return 库存事务表集合
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionByMaterialId(Long materialId);

    /**
     * 根据仓库ID查询库存事务列表
     * 
     * @param warehouseId 仓库ID
     * @return 库存事务表集合
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionByWarehouseId(Long warehouseId);

    /**
     * 根据关联单据查询库存事务列表
     * 
     * @param referenceType 关联单据类型
     * @param referenceId 关联单据ID
     * @return 库存事务表集合
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionByReference(String referenceType, Long referenceId);

    /**
     * 根据事务类型查询库存事务列表
     * 
     * @param transactionType 事务类型
     * @return 库存事务表集合
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionByType(String transactionType);

    /**
     * 查询库存事务统计信息
     * 
     * @param erpInventoryTransaction 库存事务表
     * @return 统计结果
     */
    public List<ErpInventoryTransaction> selectErpInventoryTransactionStatistics(ErpInventoryTransaction erpInventoryTransaction);
}
