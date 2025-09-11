package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventory;

/**
 * 库存主表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryMapper 
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
     * 删除库存主表
     * 
     * @param id 库存主表主键
     * @return 结果
     */
    public int deleteErpInventoryById(Long id);

    /**
     * 批量删除库存主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryByIds(Long[] ids);

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
}
