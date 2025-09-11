package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventory;

/**
 * 库存主表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpInventoryService 
{
    /**
     * 查询库存主表
     * 
     * @param id 库存主表主键
     * @return 库存主表
     */
    public ErpInventory selectErpInventoryById(Long id);

    /**
     * 查询库存主表列表
     * 
     * @param erpInventory 库存主表
     * @return 库存主表集合
     */
    public List<ErpInventory> selectErpInventoryList(ErpInventory erpInventory);

    /**
     * 新增库存主表
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    public int insertErpInventory(ErpInventory erpInventory);

    /**
     * 修改库存主表
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    public int updateErpInventory(ErpInventory erpInventory);

    /**
     * 批量删除库存主表
     * 
     * @param ids 需要删除的库存主表主键集合
     * @return 结果
     */
    public int deleteErpInventoryByIds(Long[] ids);

    /**
     * 删除库存主表信息
     * 
     * @param id 库存主表主键
     * @return 结果
     */
    public int deleteErpInventoryById(Long id);

    /**
     * 根据物料ID和仓库ID查询库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 库存主表
     */
    public ErpInventory selectErpInventoryByMaterialAndWarehouse(Long materialId, Long warehouseId);

    /**
     * 更新库存数量
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    public int updateErpInventoryQuantity(ErpInventory erpInventory);

    /**
     * 查询库存预警列表
     * 
     * @param erpInventory 库存主表
     * @return 库存主表集合
     */
    public List<ErpInventory> selectErpInventoryAlertList(ErpInventory erpInventory);

    /**
     * 增加库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 增加数量
     * @param unitCost 单位成本
     * @return 结果
     */
    public int increaseInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity, java.math.BigDecimal unitCost);

    /**
     * 减少库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 减少数量
     * @return 结果
     */
    public int decreaseInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity);

    /**
     * 分配库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 分配数量
     * @return 结果
     */
    public int allocateInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity);

    /**
     * 释放库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 释放数量
     * @return 结果
     */
    public int releaseInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity);

    /**
     * 冻结库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 冻结数量
     * @return 结果
     */
    public int freezeInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity);

    /**
     * 解冻库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 解冻数量
     * @return 结果
     */
    public int unfreezeInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity);
}
