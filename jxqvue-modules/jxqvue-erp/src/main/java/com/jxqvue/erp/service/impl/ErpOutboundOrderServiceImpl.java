package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import com.jxqvue.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpOutboundOrderMapper;
import com.jxqvue.erp.mapper.ErpOutboundOrderDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryTransactionMapper;
import com.jxqvue.erp.domain.ErpOutboundOrder;
import com.jxqvue.erp.domain.ErpOutboundOrderDetail;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.domain.ErpInventoryTransaction;
import com.jxqvue.erp.service.IErpOutboundOrderService;

/**
 * 出库单表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpOutboundOrderServiceImpl implements IErpOutboundOrderService 
{
    @Autowired
    private ErpOutboundOrderMapper erpOutboundOrderMapper;

    @Autowired
    private ErpOutboundOrderDetailMapper erpOutboundOrderDetailMapper;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    @Autowired
    private ErpInventoryTransactionMapper erpInventoryTransactionMapper;

    /**
     * 查询出库单表
     * 
     * @param id 出库单表主键
     * @return 出库单表
     */
    @Override
    public ErpOutboundOrder selectErpOutboundOrderById(Long id)
    {
        ErpOutboundOrder erpOutboundOrder = erpOutboundOrderMapper.selectErpOutboundOrderById(id);
        if (erpOutboundOrder != null) {
            // 查询明细信息
            ErpOutboundOrderDetail detail = new ErpOutboundOrderDetail();
            detail.setOutboundId(id);
            List<ErpOutboundOrderDetail> detailList = erpOutboundOrderDetailMapper.selectErpOutboundOrderDetailList(detail);
            erpOutboundOrder.setOutboundOrderDetailList(detailList);
        }
        return erpOutboundOrder;
    }

    /**
     * 查询出库单表列表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 出库单表
     */
    @Override
    public List<ErpOutboundOrder> selectErpOutboundOrderList(ErpOutboundOrder erpOutboundOrder)
    {
        return erpOutboundOrderMapper.selectErpOutboundOrderList(erpOutboundOrder);
    }

    /**
     * 新增出库单表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpOutboundOrder(ErpOutboundOrder erpOutboundOrder)
    {
        erpOutboundOrder.setCreateTime(DateUtils.getNowDate());
        // 生成出库单号
        if (StringUtils.isEmpty(erpOutboundOrder.getOutboundNo())) {
            erpOutboundOrder.setOutboundNo(generateOutboundNo());
        }
        int result = erpOutboundOrderMapper.insertErpOutboundOrder(erpOutboundOrder);
        
        // 插入明细信息
        if (erpOutboundOrder.getOutboundOrderDetailList() != null && !erpOutboundOrder.getOutboundOrderDetailList().isEmpty()) {
            for (ErpOutboundOrderDetail detail : erpOutboundOrder.getOutboundOrderDetailList()) {
                detail.setOutboundId(erpOutboundOrder.getId());
                detail.setOutboundNo(erpOutboundOrder.getOutboundNo());
                erpOutboundOrderDetailMapper.insertErpOutboundOrderDetail(detail);
            }
        }
        
        return result;
    }

    /**
     * 修改出库单表
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpOutboundOrder(ErpOutboundOrder erpOutboundOrder)
    {
        erpOutboundOrder.setUpdateTime(DateUtils.getNowDate());
        
        // 更新明细信息
        if (erpOutboundOrder.getOutboundOrderDetailList() != null) {
            // 删除原有明细
            erpOutboundOrderDetailMapper.deleteErpOutboundOrderDetailByOutboundId(erpOutboundOrder.getId());
            // 插入新明细
            for (ErpOutboundOrderDetail detail : erpOutboundOrder.getOutboundOrderDetailList()) {
                detail.setOutboundId(erpOutboundOrder.getId());
                detail.setOutboundNo(erpOutboundOrder.getOutboundNo());
                erpOutboundOrderDetailMapper.insertErpOutboundOrderDetail(detail);
            }
        }
        
        return erpOutboundOrderMapper.updateErpOutboundOrder(erpOutboundOrder);
    }

    /**
     * 批量删除出库单表
     * 
     * @param ids 需要删除的出库单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpOutboundOrderByIds(Long[] ids)
    {
        for (Long id : ids) {
            // 删除明细信息
            erpOutboundOrderDetailMapper.deleteErpOutboundOrderDetailByOutboundId(id);
        }
        return erpOutboundOrderMapper.deleteErpOutboundOrderByIds(ids);
    }

    /**
     * 删除出库单表信息
     * 
     * @param id 出库单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpOutboundOrderById(Long id)
    {
        // 删除明细信息
        erpOutboundOrderDetailMapper.deleteErpOutboundOrderDetailByOutboundId(id);
        return erpOutboundOrderMapper.deleteErpOutboundOrderById(id);
    }

    /**
     * 根据出库单号查询出库单
     * 
     * @param outboundNo 出库单号
     * @return 出库单表
     */
    @Override
    public ErpOutboundOrder selectErpOutboundOrderByOutboundNo(String outboundNo)
    {
        return erpOutboundOrderMapper.selectErpOutboundOrderByOutboundNo(outboundNo);
    }

    /**
     * 审核出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int approveErpOutboundOrder(ErpOutboundOrder erpOutboundOrder)
    {
        erpOutboundOrder.setApprovalStatus("2"); // 已审核
        erpOutboundOrder.setApproveTime(DateUtils.getNowDate());
        return erpOutboundOrderMapper.updateErpOutboundOrder(erpOutboundOrder);
    }

    /**
     * 执行出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int executeErpOutboundOrder(ErpOutboundOrder erpOutboundOrder)
    {
        // 更新出库单状态
        erpOutboundOrder.setApprovalStatus("3"); // 已出库
        erpOutboundOrder.setActualDate(DateUtils.getNowDate());
        erpOutboundOrder.setPickTime(DateUtils.getNowDate());
        int result = erpOutboundOrderMapper.updateErpOutboundOrder(erpOutboundOrder);
        
        // 查询明细信息
        ErpOutboundOrderDetail detail = new ErpOutboundOrderDetail();
        detail.setOutboundId(erpOutboundOrder.getId());
        List<ErpOutboundOrderDetail> detailList = erpOutboundOrderDetailMapper.selectErpOutboundOrderDetailList(detail);
        
        // 更新库存
        for (ErpOutboundOrderDetail orderDetail : detailList) {
            // 检查库存是否充足
            if (!checkInventorySufficient(orderDetail.getMaterialId(), erpOutboundOrder.getWarehouseId(), orderDetail.getActualQuantity())) {
                throw new RuntimeException("物料 " + orderDetail.getMaterialCode() + " 库存不足");
            }
            
            // 更新库存主表
            ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(
                orderDetail.getMaterialId(), erpOutboundOrder.getWarehouseId());
            
            if (inventory != null) {
                // 更新现有库存
                java.math.BigDecimal newTotalQuantity = inventory.getTotalQuantity().subtract(orderDetail.getActualQuantity());
                java.math.BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().subtract(orderDetail.getActualQuantity());
                java.math.BigDecimal newTotalCost = inventory.getTotalCost().subtract(orderDetail.getTotalAmount());
                
                inventory.setTotalQuantity(newTotalQuantity);
                inventory.setAvailableQuantity(newAvailableQuantity);
                inventory.setTotalCost(newTotalCost);
                inventory.setLastOutDate(DateUtils.getNowDate());
                erpInventoryMapper.updateErpInventory(inventory);
            }
            
            // 更新库存明细记录（按FIFO策略）
            List<ErpInventoryDetail> inventoryDetails = erpInventoryDetailMapper.selectErpInventoryDetailByMaterialAndWarehouse(
                orderDetail.getMaterialId(), erpOutboundOrder.getWarehouseId());
            
            java.math.BigDecimal remainingQuantity = orderDetail.getActualQuantity();
            for (ErpInventoryDetail inventoryDetail : inventoryDetails) {
                if (remainingQuantity.compareTo(java.math.BigDecimal.ZERO) <= 0) {
                    break;
                }
                
                java.math.BigDecimal availableQuantity = inventoryDetail.getAvailableQuantity();
                if (availableQuantity.compareTo(java.math.BigDecimal.ZERO) > 0) {
                    java.math.BigDecimal deductQuantity = remainingQuantity.min(availableQuantity);
                    
                    // 更新库存明细
                    inventoryDetail.setAvailableQuantity(availableQuantity.subtract(deductQuantity));
                    inventoryDetail.setTotalCost(inventoryDetail.getTotalCost().subtract(
                        deductQuantity.multiply(inventoryDetail.getUnitCost())));
                    erpInventoryDetailMapper.updateErpInventoryDetail(inventoryDetail);
                    
                    // 创建库存事务记录
                    ErpInventoryTransaction transaction = new ErpInventoryTransaction();
                    transaction.setTransactionNo(IdUtils.fastSimpleUUID());
                    transaction.setTransactionType("02"); // 出库
                    transaction.setTransactionSubType(erpOutboundOrder.getOutboundType());
                    transaction.setMaterialId(orderDetail.getMaterialId());
                    transaction.setMaterialCode(orderDetail.getMaterialCode());
                    transaction.setMaterialName(orderDetail.getMaterialName());
                    // 设置物料规格和型号（如果ErpInventoryTransaction有这些字段）
                    // transaction.setMaterialSpec(orderDetail.getMaterialSpec());
                    // transaction.setMaterialModel(orderDetail.getMaterialModel());
                    // transaction.setUnitId(orderDetail.getUnitId());
                    // transaction.setUnitCode(orderDetail.getUnitCode());
                    // transaction.setUnitName(orderDetail.getUnitName());
                    transaction.setWarehouseId(erpOutboundOrder.getWarehouseId());
                    transaction.setWarehouseCode(erpOutboundOrder.getWarehouseCode());
                    transaction.setWarehouseName(erpOutboundOrder.getWarehouseName());
                    transaction.setShelfId(inventoryDetail.getShelfId());
                    transaction.setShelfCode(inventoryDetail.getShelfCode());
                    transaction.setShelfName(inventoryDetail.getShelfName());
                    transaction.setLocationId(inventoryDetail.getLocationId());
                    transaction.setLocationCode(inventoryDetail.getLocationCode());
                    transaction.setLocationName(inventoryDetail.getLocationName());
                    transaction.setBatchNo(inventoryDetail.getBatchNo());
                    transaction.setLotNo(inventoryDetail.getLotNo());
                    transaction.setSerialNo(inventoryDetail.getSerialNo());
                    transaction.setQuantity(deductQuantity);
                    transaction.setUnitCost(inventoryDetail.getUnitCost());
                    transaction.setTotalCost(deductQuantity.multiply(inventoryDetail.getUnitCost()));
                    transaction.setBeforeQuantity(inventoryDetail.getAvailableQuantity().add(deductQuantity));
                    transaction.setAfterQuantity(inventoryDetail.getAvailableQuantity());
                    transaction.setReferenceType("OUTBOUND");
                    transaction.setReferenceId(erpOutboundOrder.getId());
                    transaction.setReferenceNo(erpOutboundOrder.getOutboundNo());
                    transaction.setOperatorId(erpOutboundOrder.getPickerId());
                    transaction.setOperatorName(erpOutboundOrder.getPickerName());
                    transaction.setTransactionDate(DateUtils.getNowDate());
                    erpInventoryTransactionMapper.insertErpInventoryTransaction(transaction);
                    
                    remainingQuantity = remainingQuantity.subtract(deductQuantity);
                }
            }
        }
        
        return result;
    }

    /**
     * 取消出库单
     * 
     * @param erpOutboundOrder 出库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelErpOutboundOrder(ErpOutboundOrder erpOutboundOrder)
    {
        erpOutboundOrder.setApprovalStatus("4"); // 已取消
        return erpOutboundOrderMapper.updateErpOutboundOrder(erpOutboundOrder);
    }

    /**
     * 生成出库单号
     * 
     * @return 出库单号
     */
    @Override
    public String generateOutboundNo()
    {
        String dateStr = DateUtils.dateTimeNow("yyyyMMdd");
        String randomStr = String.valueOf((int)(Math.random() * 9000) + 1000);
        return "OUT" + dateStr + randomStr;
    }

    /**
     * 查询出库单统计信息
     * 
     * @param erpOutboundOrder 出库单表
     * @return 统计结果
     */
    @Override
    public List<ErpOutboundOrder> selectErpOutboundOrderStatistics(ErpOutboundOrder erpOutboundOrder)
    {
        return erpOutboundOrderMapper.selectErpOutboundOrderStatistics(erpOutboundOrder);
    }

    /**
     * 检查库存是否充足
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 出库数量
     * @return 是否充足
     */
    @Override
    public boolean checkInventorySufficient(Long materialId, Long warehouseId, java.math.BigDecimal quantity)
    {
        ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            return false;
        }
        return inventory.getAvailableQuantity().compareTo(quantity) >= 0;
    }

    /**
     * 按FIFO策略分配库存
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param quantity 出库数量
     * @return 分配结果
     */
    @Override
    public List<ErpOutboundOrder> allocateInventoryByFIFO(Long materialId, Long warehouseId, java.math.BigDecimal quantity)
    {
        // 按生产日期升序排列，实现FIFO
        List<ErpInventoryDetail> inventoryDetails = erpInventoryDetailMapper.selectErpInventoryDetailByMaterialAndWarehouse(
            materialId, warehouseId);
        
        java.math.BigDecimal remainingQuantity = quantity;
        List<ErpOutboundOrder> allocationResult = new java.util.ArrayList<>();
        
        for (ErpInventoryDetail inventoryDetail : inventoryDetails) {
            if (remainingQuantity.compareTo(java.math.BigDecimal.ZERO) <= 0) {
                break;
            }
            
            java.math.BigDecimal availableQuantity = inventoryDetail.getAvailableQuantity();
            if (availableQuantity.compareTo(java.math.BigDecimal.ZERO) > 0) {
                java.math.BigDecimal allocateQuantity = remainingQuantity.min(availableQuantity);
                
                // 创建分配记录（ErpOutboundOrder不应该有这些字段，这里需要重新设计）
                // ErpOutboundOrder allocation = new ErpOutboundOrder();
                // allocation.setMaterialId(materialId);
                // allocation.setWarehouseId(warehouseId);
                // allocation.setShelfId(inventoryDetail.getShelfId());
                // allocation.setLocationId(inventoryDetail.getLocationId());
                // allocation.setBatchNo(inventoryDetail.getBatchNo());
                // allocation.setLotNo(inventoryDetail.getLotNo());
                // allocation.setSerialNo(inventoryDetail.getSerialNo());
                // allocation.setQuantity(allocateQuantity);
                // allocation.setUnitCost(inventoryDetail.getUnitCost());
                // allocation.setTotalCost(allocateQuantity.multiply(inventoryDetail.getUnitCost()));
                // allocation.setProductionDate(inventoryDetail.getProductionDate());
                // allocation.setExpiryDate(inventoryDetail.getExpiryDate());
                // allocation.setQualityStatus(inventoryDetail.getQualityStatus());
                // allocation.setQualityLevel(inventoryDetail.getQualityLevel());
                
                // 暂时返回空列表，需要重新设计分配逻辑
                // allocationResult.add(allocation);
                
                // 暂时跳过分配逻辑，需要重新设计
                // allocationResult.add(allocation);
                // remainingQuantity = remainingQuantity.subtract(allocateQuantity);
            }
        }
        
        return allocationResult;
    }
}
