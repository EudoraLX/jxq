package com.jxqvue.erp.service.impl;

import java.util.List;
import java.math.BigDecimal;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.service.IErpInventoryService;

/**
 * 库存主表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpInventoryServiceImpl implements IErpInventoryService 
{
    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    /**
     * 查询库存主表
     * 
     * @param id 库存主表主键
     * @return 库存主表
     */
    @Override
    public ErpInventory selectErpInventoryById(Long id)
    {
        return erpInventoryMapper.selectErpInventoryById(id);
    }

    /**
     * 查询库存主表列表
     * 
     * @param erpInventory 库存主表
     * @return 库存主表
     */
    @Override
    public List<ErpInventory> selectErpInventoryList(ErpInventory erpInventory)
    {
        return erpInventoryMapper.selectErpInventoryList(erpInventory);
    }

    /**
     * 新增库存主表
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    @Override
    public int insertErpInventory(ErpInventory erpInventory)
    {
        erpInventory.setCreateTime(DateUtils.getNowDate());
        return erpInventoryMapper.insertErpInventory(erpInventory);
    }

    /**
     * 修改库存主表
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    @Override
    public int updateErpInventory(ErpInventory erpInventory)
    {
        erpInventory.setUpdateTime(DateUtils.getNowDate());
        return erpInventoryMapper.updateErpInventory(erpInventory);
    }

    /**
     * 批量删除库存主表
     * 
     * @param ids 需要删除的库存主表主键
     * @return 结果
     */
    @Override
    public int deleteErpInventoryByIds(Long[] ids)
    {
        return erpInventoryMapper.deleteErpInventoryByIds(ids);
    }

    /**
     * 删除库存主表信息
     * 
     * @param id 库存主表主键
     * @return 结果
     */
    @Override
    public int deleteErpInventoryById(Long id)
    {
        return erpInventoryMapper.deleteErpInventoryById(id);
    }

    /**
     * 根据物料ID和仓库ID查询库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 库存主表
     */
    @Override
    public ErpInventory selectErpInventoryByMaterialAndWarehouse(Long materialId, Long warehouseId)
    {
        return erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
    }

    /**
     * 更新库存数量
     * 
     * @param erpInventory 库存主表
     * @return 结果
     */
    @Override
    public int updateErpInventoryQuantity(ErpInventory erpInventory)
    {
        return erpInventoryMapper.updateErpInventoryQuantity(erpInventory);
    }

    /**
     * 查询库存预警列表
     * 
     * @param erpInventory 库存主表
     * @return 库存主表集合
     */
    @Override
    public List<ErpInventory> selectErpInventoryAlertList(ErpInventory erpInventory)
    {
        return erpInventoryMapper.selectErpInventoryAlertList(erpInventory);
    }

    /**
     * 增加库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 增加数量
     * @param unitCost 单位成本
     * @return 结果
     */
    @Override
    @Transactional
    public int increaseInventory(Long materialId, Long warehouseId, BigDecimal quantity, BigDecimal unitCost)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            // 如果库存不存在，创建新的库存记录
            inventory = new ErpInventory();
            inventory.setMaterialId(materialId);
            inventory.setWarehouseId(warehouseId);
            inventory.setTotalQuantity(quantity);
            inventory.setAvailableQuantity(quantity);
            inventory.setAllocatedQuantity(BigDecimal.ZERO);
            inventory.setFrozenQuantity(BigDecimal.ZERO);
            inventory.setUnitCost(unitCost);
            inventory.setTotalCost(quantity.multiply(unitCost));
            inventory.setLastInDate(DateUtils.getNowDate());
            return insertErpInventory(inventory);
        } else {
            // 更新现有库存
            BigDecimal newTotalQuantity = inventory.getTotalQuantity().add(quantity);
            BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(quantity);
            BigDecimal newTotalCost = inventory.getTotalCost().add(quantity.multiply(unitCost));
            BigDecimal newUnitCost = newTotalCost.divide(newTotalQuantity, 4, BigDecimal.ROUND_HALF_UP);
            
            inventory.setTotalQuantity(newTotalQuantity);
            inventory.setAvailableQuantity(newAvailableQuantity);
            inventory.setUnitCost(newUnitCost);
            inventory.setTotalCost(newTotalCost);
            inventory.setLastInDate(DateUtils.getNowDate());
            return updateErpInventory(inventory);
        }
    }

    /**
     * 减少库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 减少数量
     * @return 结果
     */
    @Override
    @Transactional
    public int decreaseInventory(Long materialId, Long warehouseId, BigDecimal quantity)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            throw new RuntimeException("库存不足，无法减少库存");
        }
        
        if (inventory.getAvailableQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("可用库存不足，无法减少库存");
        }
        
        BigDecimal newTotalQuantity = inventory.getTotalQuantity().subtract(quantity);
        BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(quantity);
        BigDecimal newTotalCost = inventory.getTotalCost().subtract(quantity.multiply(inventory.getUnitCost()));
        
        inventory.setTotalQuantity(newTotalQuantity);
        inventory.setAvailableQuantity(newAvailableQuantity);
        inventory.setTotalCost(newTotalCost);
        inventory.setLastOutDate(DateUtils.getNowDate());
        
        return updateErpInventory(inventory);
    }

    /**
     * 分配库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 分配数量
     * @return 结果
     */
    @Override
    @Transactional
    public int allocateInventory(Long materialId, Long warehouseId, BigDecimal quantity)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            throw new RuntimeException("库存不存在，无法分配库存");
        }
        
        if (inventory.getAvailableQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("可用库存不足，无法分配库存");
        }
        
        BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(quantity);
        BigDecimal newAllocatedQuantity = inventory.getAllocatedQuantity().add(quantity);
        
        inventory.setAvailableQuantity(newAvailableQuantity);
        inventory.setAllocatedQuantity(newAllocatedQuantity);
        
        return updateErpInventory(inventory);
    }

    /**
     * 释放库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 释放数量
     * @return 结果
     */
    @Override
    @Transactional
    public int releaseInventory(Long materialId, Long warehouseId, BigDecimal quantity)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            throw new RuntimeException("库存不存在，无法释放库存");
        }
        
        if (inventory.getAllocatedQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("已分配库存不足，无法释放库存");
        }
        
        BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(quantity);
        BigDecimal newAllocatedQuantity = inventory.getAllocatedQuantity().subtract(quantity);
        
        inventory.setAvailableQuantity(newAvailableQuantity);
        inventory.setAllocatedQuantity(newAllocatedQuantity);
        
        return updateErpInventory(inventory);
    }

    /**
     * 冻结库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 冻结数量
     * @return 结果
     */
    @Override
    @Transactional
    public int freezeInventory(Long materialId, Long warehouseId, BigDecimal quantity)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            throw new RuntimeException("库存不存在，无法冻结库存");
        }
        
        if (inventory.getAvailableQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("可用库存不足，无法冻结库存");
        }
        
        BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(quantity);
        BigDecimal newFrozenQuantity = inventory.getFrozenQuantity().add(quantity);
        
        inventory.setAvailableQuantity(newAvailableQuantity);
        inventory.setFrozenQuantity(newFrozenQuantity);
        
        return updateErpInventory(inventory);
    }

    /**
     * 解冻库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 解冻数量
     * @return 结果
     */
    @Override
    @Transactional
    public int unfreezeInventory(Long materialId, Long warehouseId, BigDecimal quantity)
    {
        ErpInventory inventory = selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            throw new RuntimeException("库存不存在，无法解冻库存");
        }
        
        if (inventory.getFrozenQuantity().compareTo(quantity) < 0) {
            throw new RuntimeException("冻结库存不足，无法解冻库存");
        }
        
        BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(quantity);
        BigDecimal newFrozenQuantity = inventory.getFrozenQuantity().subtract(quantity);
        
        inventory.setAvailableQuantity(newAvailableQuantity);
        inventory.setFrozenQuantity(newFrozenQuantity);
        
        return updateErpInventory(inventory);
    }
}
