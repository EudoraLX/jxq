package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import com.jxqvue.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpTransferOrderMapper;
import com.jxqvue.erp.mapper.ErpTransferOrderDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryTransactionMapper;
import com.jxqvue.erp.domain.ErpTransferOrder;
import com.jxqvue.erp.domain.ErpTransferOrderDetail;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.domain.ErpInventoryTransaction;
import com.jxqvue.erp.service.IErpTransferOrderService;

/**
 * 调拨单表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpTransferOrderServiceImpl implements IErpTransferOrderService 
{
    @Autowired
    private ErpTransferOrderMapper erpTransferOrderMapper;

    @Autowired
    private ErpTransferOrderDetailMapper erpTransferOrderDetailMapper;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    @Autowired
    private ErpInventoryTransactionMapper erpInventoryTransactionMapper;

    /**
     * 查询调拨单表
     * 
     * @param id 调拨单表主键
     * @return 调拨单表
     */
    @Override
    public ErpTransferOrder selectErpTransferOrderById(Long id)
    {
        ErpTransferOrder erpTransferOrder = erpTransferOrderMapper.selectErpTransferOrderById(id);
        if (erpTransferOrder != null) {
            // 查询明细信息
            ErpTransferOrderDetail detail = new ErpTransferOrderDetail();
            detail.setTransferId(id);
            List<ErpTransferOrderDetail> detailList = erpTransferOrderDetailMapper.selectErpTransferOrderDetailList(detail);
            erpTransferOrder.setTransferOrderDetailList(detailList);
        }
        return erpTransferOrder;
    }

    /**
     * 查询调拨单表列表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 调拨单表
     */
    @Override
    public List<ErpTransferOrder> selectErpTransferOrderList(ErpTransferOrder erpTransferOrder)
    {
        return erpTransferOrderMapper.selectErpTransferOrderList(erpTransferOrder);
    }

    /**
     * 新增调拨单表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpTransferOrder(ErpTransferOrder erpTransferOrder)
    {
        erpTransferOrder.setCreateTime(DateUtils.getNowDate());
        // 生成调拨单号
        if (StringUtils.isEmpty(erpTransferOrder.getTransferNo())) {
            erpTransferOrder.setTransferNo(generateTransferNo());
        }
        int result = erpTransferOrderMapper.insertErpTransferOrder(erpTransferOrder);
        
        // 插入明细信息
        if (erpTransferOrder.getTransferOrderDetailList() != null && !erpTransferOrder.getTransferOrderDetailList().isEmpty()) {
            for (ErpTransferOrderDetail detail : erpTransferOrder.getTransferOrderDetailList()) {
                detail.setTransferId(erpTransferOrder.getId());
                detail.setTransferNo(erpTransferOrder.getTransferNo());
                erpTransferOrderDetailMapper.insertErpTransferOrderDetail(detail);
            }
        }
        
        return result;
    }

    /**
     * 修改调拨单表
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpTransferOrder(ErpTransferOrder erpTransferOrder)
    {
        erpTransferOrder.setUpdateTime(DateUtils.getNowDate());
        
        // 更新明细信息
        if (erpTransferOrder.getTransferOrderDetailList() != null) {
            // 删除原有明细
            erpTransferOrderDetailMapper.deleteErpTransferOrderDetailByTransferId(erpTransferOrder.getId());
            // 插入新明细
            for (ErpTransferOrderDetail detail : erpTransferOrder.getTransferOrderDetailList()) {
                detail.setTransferId(erpTransferOrder.getId());
                detail.setTransferNo(erpTransferOrder.getTransferNo());
                erpTransferOrderDetailMapper.insertErpTransferOrderDetail(detail);
            }
        }
        
        return erpTransferOrderMapper.updateErpTransferOrder(erpTransferOrder);
    }

    /**
     * 批量删除调拨单表
     * 
     * @param ids 需要删除的调拨单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpTransferOrderByIds(Long[] ids)
    {
        for (Long id : ids) {
            // 删除明细信息
            erpTransferOrderDetailMapper.deleteErpTransferOrderDetailByTransferId(id);
        }
        return erpTransferOrderMapper.deleteErpTransferOrderByIds(ids);
    }

    /**
     * 删除调拨单表信息
     * 
     * @param id 调拨单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpTransferOrderById(Long id)
    {
        // 删除明细信息
        erpTransferOrderDetailMapper.deleteErpTransferOrderDetailByTransferId(id);
        return erpTransferOrderMapper.deleteErpTransferOrderById(id);
    }

    /**
     * 根据调拨单号查询调拨单
     * 
     * @param transferNo 调拨单号
     * @return 调拨单表
     */
    @Override
    public ErpTransferOrder selectErpTransferOrderByTransferNo(String transferNo)
    {
        return erpTransferOrderMapper.selectErpTransferOrderByTransferNo(transferNo);
    }

    /**
     * 审核调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    @Override
    @Transactional
    public int approveErpTransferOrder(ErpTransferOrder erpTransferOrder)
    {
        erpTransferOrder.setStatus("2"); // 已审核
        erpTransferOrder.setApproveTime(DateUtils.getNowDate());
        return erpTransferOrderMapper.updateErpTransferOrder(erpTransferOrder);
    }

    /**
     * 执行调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    @Override
    @Transactional
    public int executeErpTransferOrder(ErpTransferOrder erpTransferOrder)
    {
        // 更新调拨单状态
        erpTransferOrder.setStatus("3"); // 已调拨
        erpTransferOrder.setActualDate(DateUtils.getNowDate());
        int result = erpTransferOrderMapper.updateErpTransferOrder(erpTransferOrder);
        
        // 查询明细信息
        ErpTransferOrderDetail detail = new ErpTransferOrderDetail();
        detail.setTransferId(erpTransferOrder.getId());
        List<ErpTransferOrderDetail> detailList = erpTransferOrderDetailMapper.selectErpTransferOrderDetailList(detail);
        
        // 执行调拨
        for (ErpTransferOrderDetail orderDetail : detailList) {
            // 检查源库存是否充足
            if (!checkFromInventorySufficient(orderDetail.getMaterialId(), erpTransferOrder.getFromWarehouseId(), orderDetail.getActualQuantity())) {
                throw new RuntimeException("物料 " + orderDetail.getMaterialCode() + " 源库存不足");
            }
            
            // 减少源库存
            ErpInventory fromInventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(
                orderDetail.getMaterialId(), erpTransferOrder.getFromWarehouseId());
            
            if (fromInventory != null) {
                java.math.BigDecimal newTotalQuantity = fromInventory.getTotalQuantity().subtract(orderDetail.getActualQuantity());
                java.math.BigDecimal newAvailableQuantity = fromInventory.getAvailableQuantity().subtract(orderDetail.getActualQuantity());
                // 计算总成本（如果ErpTransferOrderDetail有totalAmount字段）
                // java.math.BigDecimal newTotalCost = fromInventory.getTotalCost().subtract(orderDetail.getTotalAmount());
                java.math.BigDecimal newTotalCost = fromInventory.getTotalCost();
                
                fromInventory.setTotalQuantity(newTotalQuantity);
                fromInventory.setAvailableQuantity(newAvailableQuantity);
                fromInventory.setTotalCost(newTotalCost);
                fromInventory.setLastOutDate(DateUtils.getNowDate());
                erpInventoryMapper.updateErpInventory(fromInventory);
            }
            
            // 增加目标库存
            ErpInventory toInventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(
                orderDetail.getMaterialId(), erpTransferOrder.getToWarehouseId());
            
            if (toInventory == null) {
                // 创建新的库存记录
                toInventory = new ErpInventory();
                toInventory.setMaterialId(orderDetail.getMaterialId());
                toInventory.setMaterialCode(orderDetail.getMaterialCode());
                toInventory.setMaterialName(orderDetail.getMaterialName());
                toInventory.setWarehouseId(erpTransferOrder.getToWarehouseId());
                toInventory.setWarehouseCode(erpTransferOrder.getToWarehouseCode());
                toInventory.setWarehouseName(erpTransferOrder.getToWarehouseName());
                toInventory.setTotalQuantity(orderDetail.getActualQuantity());
                toInventory.setAvailableQuantity(orderDetail.getActualQuantity());
                toInventory.setAllocatedQuantity(java.math.BigDecimal.ZERO);
                toInventory.setFrozenQuantity(java.math.BigDecimal.ZERO);
                // 设置单位成本和总成本（如果ErpTransferOrderDetail有这些字段）
                // toInventory.setUnitCost(orderDetail.getUnitPrice());
                // toInventory.setTotalCost(orderDetail.getTotalAmount());
                toInventory.setUnitCost(java.math.BigDecimal.ZERO);
                toInventory.setTotalCost(java.math.BigDecimal.ZERO);
                toInventory.setLastInDate(DateUtils.getNowDate());
                erpInventoryMapper.insertErpInventory(toInventory);
            } else {
                // 更新现有库存
                java.math.BigDecimal newTotalQuantity = toInventory.getTotalQuantity().add(orderDetail.getActualQuantity());
                java.math.BigDecimal newAvailableQuantity = toInventory.getAvailableQuantity().add(orderDetail.getActualQuantity());
                // 计算总成本（如果ErpTransferOrderDetail有totalAmount字段）
                // java.math.BigDecimal newTotalCost = toInventory.getTotalCost().add(orderDetail.getTotalAmount());
                java.math.BigDecimal newTotalCost = toInventory.getTotalCost();
                java.math.BigDecimal newUnitCost = newTotalCost.divide(newTotalQuantity, 4, java.math.BigDecimal.ROUND_HALF_UP);
                
                toInventory.setTotalQuantity(newTotalQuantity);
                toInventory.setAvailableQuantity(newAvailableQuantity);
                toInventory.setUnitCost(newUnitCost);
                toInventory.setTotalCost(newTotalCost);
                toInventory.setLastInDate(DateUtils.getNowDate());
                erpInventoryMapper.updateErpInventory(toInventory);
            }
            
            // 创建目标库存明细记录
            ErpInventoryDetail toInventoryDetail = new ErpInventoryDetail();
            toInventoryDetail.setInventoryId(toInventory.getId());
            toInventoryDetail.setMaterialId(orderDetail.getMaterialId());
            toInventoryDetail.setMaterialCode(orderDetail.getMaterialCode());
            toInventoryDetail.setMaterialName(orderDetail.getMaterialName());
            toInventoryDetail.setMaterialSpec(orderDetail.getMaterialSpec());
            toInventoryDetail.setMaterialModel(orderDetail.getMaterialModel());
            toInventoryDetail.setUnitId(orderDetail.getUnitId());
            toInventoryDetail.setUnitCode(orderDetail.getUnitCode());
            toInventoryDetail.setUnitName(orderDetail.getUnitName());
            toInventoryDetail.setWarehouseId(erpTransferOrder.getToWarehouseId());
            toInventoryDetail.setWarehouseCode(erpTransferOrder.getToWarehouseCode());
            toInventoryDetail.setWarehouseName(erpTransferOrder.getToWarehouseName());
            // 设置目标货架和货位（如果ErpTransferOrderDetail有这些字段）
            // toInventoryDetail.setShelfId(orderDetail.getToShelfId());
            // toInventoryDetail.setShelfCode(orderDetail.getToShelfCode());
            // toInventoryDetail.setShelfName(orderDetail.getToShelfName());
            // toInventoryDetail.setLocationId(orderDetail.getToLocationId());
            // toInventoryDetail.setLocationCode(orderDetail.getToLocationCode());
            // toInventoryDetail.setLocationName(orderDetail.getToLocationName());
            toInventoryDetail.setBatchNo(orderDetail.getBatchNo());
            toInventoryDetail.setLotNo(orderDetail.getLotNo());
            toInventoryDetail.setSerialNo(orderDetail.getSerialNo());
            toInventoryDetail.setQuantity(orderDetail.getActualQuantity());
            toInventoryDetail.setAvailableQuantity(orderDetail.getActualQuantity());
            toInventoryDetail.setAllocatedQuantity(java.math.BigDecimal.ZERO);
            toInventoryDetail.setFrozenQuantity(java.math.BigDecimal.ZERO);
            // 设置成本和日期字段（如果ErpTransferOrderDetail有这些字段）
            // toInventoryDetail.setUnitCost(orderDetail.getUnitPrice());
            // toInventoryDetail.setTotalCost(orderDetail.getTotalAmount());
            // toInventoryDetail.setProductionDate(orderDetail.getProductionDate());
            // toInventoryDetail.setExpiryDate(orderDetail.getExpiryDate());
            // toInventoryDetail.setQualityStatus(orderDetail.getQualityStatus());
            // toInventoryDetail.setQualityLevel(orderDetail.getQualityLevel());
            toInventoryDetail.setUnitCost(java.math.BigDecimal.ZERO);
            toInventoryDetail.setTotalCost(java.math.BigDecimal.ZERO);
            erpInventoryDetailMapper.insertErpInventoryDetail(toInventoryDetail);
            
            // 创建库存事务记录（出库）
            ErpInventoryTransaction outTransaction = new ErpInventoryTransaction();
            outTransaction.setTransactionNo(IdUtils.fastSimpleUUID());
            outTransaction.setTransactionType("03"); // 调拨
            outTransaction.setTransactionSubType(erpTransferOrder.getTransferType());
            outTransaction.setMaterialId(orderDetail.getMaterialId());
            outTransaction.setMaterialCode(orderDetail.getMaterialCode());
            outTransaction.setMaterialName(orderDetail.getMaterialName());
            // 设置物料规格和型号（如果ErpInventoryTransaction有这些字段）
            // outTransaction.setMaterialSpec(orderDetail.getMaterialSpec());
            // outTransaction.setMaterialModel(orderDetail.getMaterialModel());
            // outTransaction.setUnitId(orderDetail.getUnitId());
            // outTransaction.setUnitCode(orderDetail.getUnitCode());
            // outTransaction.setUnitName(orderDetail.getUnitName());
            outTransaction.setWarehouseId(erpTransferOrder.getFromWarehouseId());
            outTransaction.setWarehouseCode(erpTransferOrder.getFromWarehouseCode());
            outTransaction.setWarehouseName(erpTransferOrder.getFromWarehouseName());
            outTransaction.setQuantity(orderDetail.getActualQuantity().negate()); // 负数表示出库
            // 设置成本和金额（如果ErpTransferOrderDetail有这些字段）
            // outTransaction.setUnitCost(orderDetail.getUnitPrice());
            // outTransaction.setTotalCost(orderDetail.getTotalAmount().negate()); // 负数表示出库
            outTransaction.setUnitCost(java.math.BigDecimal.ZERO);
            outTransaction.setTotalCost(java.math.BigDecimal.ZERO);
            outTransaction.setBeforeQuantity(fromInventory.getTotalQuantity().add(orderDetail.getActualQuantity()));
            outTransaction.setAfterQuantity(fromInventory.getTotalQuantity());
            outTransaction.setReferenceType("TRANSFER");
            outTransaction.setReferenceId(erpTransferOrder.getId());
            outTransaction.setReferenceNo(erpTransferOrder.getTransferNo());
            outTransaction.setOperatorId(erpTransferOrder.getOperatorId());
            outTransaction.setOperatorName(erpTransferOrder.getOperatorName());
            outTransaction.setTransactionDate(DateUtils.getNowDate());
            erpInventoryTransactionMapper.insertErpInventoryTransaction(outTransaction);
            
            // 创建库存事务记录（入库）
            ErpInventoryTransaction inTransaction = new ErpInventoryTransaction();
            inTransaction.setTransactionNo(IdUtils.fastSimpleUUID());
            inTransaction.setTransactionType("03"); // 调拨
            inTransaction.setTransactionSubType(erpTransferOrder.getTransferType());
            inTransaction.setMaterialId(orderDetail.getMaterialId());
            inTransaction.setMaterialCode(orderDetail.getMaterialCode());
            inTransaction.setMaterialName(orderDetail.getMaterialName());
            // 设置物料规格和型号（如果ErpInventoryTransaction有这些字段）
            // inTransaction.setMaterialSpec(orderDetail.getMaterialSpec());
            // inTransaction.setMaterialModel(orderDetail.getMaterialModel());
            // inTransaction.setUnitId(orderDetail.getUnitId());
            // inTransaction.setUnitCode(orderDetail.getUnitCode());
            // inTransaction.setUnitName(orderDetail.getUnitName());
            inTransaction.setWarehouseId(erpTransferOrder.getToWarehouseId());
            inTransaction.setWarehouseCode(erpTransferOrder.getToWarehouseCode());
            inTransaction.setWarehouseName(erpTransferOrder.getToWarehouseName());
            inTransaction.setQuantity(orderDetail.getActualQuantity());
            // 设置成本和金额（如果ErpTransferOrderDetail有这些字段）
            // inTransaction.setUnitCost(orderDetail.getUnitPrice());
            // inTransaction.setTotalCost(orderDetail.getTotalAmount());
            inTransaction.setUnitCost(java.math.BigDecimal.ZERO);
            inTransaction.setTotalCost(java.math.BigDecimal.ZERO);
            inTransaction.setBeforeQuantity(toInventory.getTotalQuantity().subtract(orderDetail.getActualQuantity()));
            inTransaction.setAfterQuantity(toInventory.getTotalQuantity());
            inTransaction.setReferenceType("TRANSFER");
            inTransaction.setReferenceId(erpTransferOrder.getId());
            inTransaction.setReferenceNo(erpTransferOrder.getTransferNo());
            inTransaction.setOperatorId(erpTransferOrder.getOperatorId());
            inTransaction.setOperatorName(erpTransferOrder.getOperatorName());
            inTransaction.setTransactionDate(DateUtils.getNowDate());
            erpInventoryTransactionMapper.insertErpInventoryTransaction(inTransaction);
        }
        
        return result;
    }

    /**
     * 取消调拨单
     * 
     * @param erpTransferOrder 调拨单表
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelErpTransferOrder(ErpTransferOrder erpTransferOrder)
    {
        erpTransferOrder.setStatus("4"); // 已取消
        return erpTransferOrderMapper.updateErpTransferOrder(erpTransferOrder);
    }

    /**
     * 生成调拨单号
     * 
     * @return 调拨单号
     */
    @Override
    public String generateTransferNo()
    {
        String dateStr = DateUtils.dateTimeNow("yyyyMMdd");
        String randomStr = String.valueOf((int)(Math.random() * 9000) + 1000);
        return "TR" + dateStr + randomStr;
    }

    /**
     * 查询调拨单统计信息
     * 
     * @param erpTransferOrder 调拨单表
     * @return 统计结果
     */
    @Override
    public List<ErpTransferOrder> selectErpTransferOrderStatistics(ErpTransferOrder erpTransferOrder)
    {
        return erpTransferOrderMapper.selectErpTransferOrderStatistics(erpTransferOrder);
    }

    /**
     * 检查源库存是否充足
     * 
     * @param materialId 物料ID
     * @param fromWarehouseId 源仓库ID
     * @param quantity 调拨数量
     * @return 是否充足
     */
    @Override
    public boolean checkFromInventorySufficient(Long materialId, Long fromWarehouseId, java.math.BigDecimal quantity)
    {
        ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(materialId, fromWarehouseId);
        if (inventory == null) {
            return false;
        }
        return inventory.getAvailableQuantity().compareTo(quantity) >= 0;
    }

    /**
     * 检查目标仓库是否可用
     * 
     * @param toWarehouseId 目标仓库ID
     * @return 是否可用
     */
    @Override
    public boolean checkToWarehouseAvailable(Long toWarehouseId)
    {
        // 这里可以添加仓库状态检查逻辑
        // 例如检查仓库是否启用、是否维护中等
        return true;
    }
}
