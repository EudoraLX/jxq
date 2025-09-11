package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import com.jxqvue.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpInboundOrderMapper;
import com.jxqvue.erp.mapper.ErpInboundOrderDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryTransactionMapper;
import com.jxqvue.erp.domain.ErpInboundOrder;
import com.jxqvue.erp.domain.ErpInboundOrderDetail;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.domain.ErpInventoryTransaction;
import com.jxqvue.erp.service.IErpInboundOrderService;

/**
 * 入库单表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpInboundOrderServiceImpl implements IErpInboundOrderService 
{
    @Autowired
    private ErpInboundOrderMapper erpInboundOrderMapper;

    @Autowired
    private ErpInboundOrderDetailMapper erpInboundOrderDetailMapper;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    @Autowired
    private ErpInventoryTransactionMapper erpInventoryTransactionMapper;

    /**
     * 查询入库单表
     * 
     * @param id 入库单表主键
     * @return 入库单表
     */
    @Override
    public ErpInboundOrder selectErpInboundOrderById(Long id)
    {
        ErpInboundOrder erpInboundOrder = erpInboundOrderMapper.selectErpInboundOrderById(id);
        if (erpInboundOrder != null) {
            // 查询明细信息
            ErpInboundOrderDetail detail = new ErpInboundOrderDetail();
            detail.setInboundId(id);
            List<ErpInboundOrderDetail> detailList = erpInboundOrderDetailMapper.selectErpInboundOrderDetailList(detail);
            erpInboundOrder.setInboundOrderDetailList(detailList);
        }
        return erpInboundOrder;
    }

    /**
     * 查询入库单表列表
     * 
     * @param erpInboundOrder 入库单表
     * @return 入库单表
     */
    @Override
    public List<ErpInboundOrder> selectErpInboundOrderList(ErpInboundOrder erpInboundOrder)
    {
        return erpInboundOrderMapper.selectErpInboundOrderList(erpInboundOrder);
    }

    /**
     * 新增入库单表
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpInboundOrder(ErpInboundOrder erpInboundOrder)
    {
        erpInboundOrder.setCreateTime(DateUtils.getNowDate());
        // 生成入库单号
        if (StringUtils.isEmpty(erpInboundOrder.getInboundNo())) {
            erpInboundOrder.setInboundNo(generateInboundNo());
        }
        int result = erpInboundOrderMapper.insertErpInboundOrder(erpInboundOrder);
        
        // 插入明细信息
        if (erpInboundOrder.getInboundOrderDetailList() != null && !erpInboundOrder.getInboundOrderDetailList().isEmpty()) {
            for (ErpInboundOrderDetail detail : erpInboundOrder.getInboundOrderDetailList()) {
                detail.setInboundId(erpInboundOrder.getId());
                detail.setInboundNo(erpInboundOrder.getInboundNo());
                erpInboundOrderDetailMapper.insertErpInboundOrderDetail(detail);
            }
        }
        
        return result;
    }

    /**
     * 修改入库单表
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpInboundOrder(ErpInboundOrder erpInboundOrder)
    {
        erpInboundOrder.setUpdateTime(DateUtils.getNowDate());
        
        // 更新明细信息
        if (erpInboundOrder.getInboundOrderDetailList() != null) {
            // 删除原有明细
            erpInboundOrderDetailMapper.deleteErpInboundOrderDetailByInboundId(erpInboundOrder.getId());
            // 插入新明细
            for (ErpInboundOrderDetail detail : erpInboundOrder.getInboundOrderDetailList()) {
                detail.setInboundId(erpInboundOrder.getId());
                detail.setInboundNo(erpInboundOrder.getInboundNo());
                erpInboundOrderDetailMapper.insertErpInboundOrderDetail(detail);
            }
        }
        
        return erpInboundOrderMapper.updateErpInboundOrder(erpInboundOrder);
    }

    /**
     * 批量删除入库单表
     * 
     * @param ids 需要删除的入库单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInboundOrderByIds(Long[] ids)
    {
        for (Long id : ids) {
            // 删除明细信息
            erpInboundOrderDetailMapper.deleteErpInboundOrderDetailByInboundId(id);
        }
        return erpInboundOrderMapper.deleteErpInboundOrderByIds(ids);
    }

    /**
     * 删除入库单表信息
     * 
     * @param id 入库单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInboundOrderById(Long id)
    {
        // 删除明细信息
        erpInboundOrderDetailMapper.deleteErpInboundOrderDetailByInboundId(id);
        return erpInboundOrderMapper.deleteErpInboundOrderById(id);
    }

    /**
     * 根据入库单号查询入库单
     * 
     * @param inboundNo 入库单号
     * @return 入库单表
     */
    @Override
    public ErpInboundOrder selectErpInboundOrderByInboundNo(String inboundNo)
    {
        return erpInboundOrderMapper.selectErpInboundOrderByInboundNo(inboundNo);
    }

    /**
     * 审核入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int approveErpInboundOrder(ErpInboundOrder erpInboundOrder)
    {
        erpInboundOrder.setApprovalStatus("2"); // 已审核
        erpInboundOrder.setApproveTime(DateUtils.getNowDate());
        return erpInboundOrderMapper.updateErpInboundOrder(erpInboundOrder);
    }

    /**
     * 执行入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int executeErpInboundOrder(ErpInboundOrder erpInboundOrder)
    {
        // 更新入库单状态
        erpInboundOrder.setApprovalStatus("3"); // 已入库
        erpInboundOrder.setActualDate(DateUtils.getNowDate());
        erpInboundOrder.setReceiveTime(DateUtils.getNowDate());
        int result = erpInboundOrderMapper.updateErpInboundOrder(erpInboundOrder);
        
        // 查询明细信息
        ErpInboundOrderDetail detail = new ErpInboundOrderDetail();
        detail.setInboundId(erpInboundOrder.getId());
        List<ErpInboundOrderDetail> detailList = erpInboundOrderDetailMapper.selectErpInboundOrderDetailList(detail);
        
        // 更新库存
        for (ErpInboundOrderDetail orderDetail : detailList) {
            // 更新库存主表
            ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(
                orderDetail.getMaterialId(), erpInboundOrder.getWarehouseId());
            
            if (inventory == null) {
                // 创建新的库存记录
                inventory = new ErpInventory();
                inventory.setMaterialId(orderDetail.getMaterialId());
                inventory.setMaterialCode(orderDetail.getMaterialCode());
                inventory.setMaterialName(orderDetail.getMaterialName());
                inventory.setWarehouseId(erpInboundOrder.getWarehouseId());
                inventory.setWarehouseCode(erpInboundOrder.getWarehouseCode());
                inventory.setWarehouseName(erpInboundOrder.getWarehouseName());
                inventory.setTotalQuantity(orderDetail.getActualQuantity());
                inventory.setAvailableQuantity(orderDetail.getActualQuantity());
                inventory.setAllocatedQuantity(java.math.BigDecimal.ZERO);
                inventory.setFrozenQuantity(java.math.BigDecimal.ZERO);
                inventory.setUnitCost(orderDetail.getUnitPrice());
                inventory.setTotalCost(orderDetail.getTotalAmount());
                inventory.setLastInDate(DateUtils.getNowDate());
                erpInventoryMapper.insertErpInventory(inventory);
            } else {
                // 更新现有库存
                java.math.BigDecimal newTotalQuantity = inventory.getTotalQuantity().add(orderDetail.getActualQuantity());
                java.math.BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(orderDetail.getActualQuantity());
                java.math.BigDecimal newTotalCost = inventory.getTotalCost().add(orderDetail.getTotalAmount());
                java.math.BigDecimal newUnitCost = newTotalCost.divide(newTotalQuantity, 4, java.math.BigDecimal.ROUND_HALF_UP);
                
                inventory.setTotalQuantity(newTotalQuantity);
                inventory.setAvailableQuantity(newAvailableQuantity);
                inventory.setUnitCost(newUnitCost);
                inventory.setTotalCost(newTotalCost);
                inventory.setLastInDate(DateUtils.getNowDate());
                erpInventoryMapper.updateErpInventory(inventory);
            }
            
            // 创建库存明细记录
            ErpInventoryDetail inventoryDetail = new ErpInventoryDetail();
            inventoryDetail.setInventoryId(inventory.getId());
            inventoryDetail.setMaterialId(orderDetail.getMaterialId());
            inventoryDetail.setMaterialCode(orderDetail.getMaterialCode());
            inventoryDetail.setMaterialName(orderDetail.getMaterialName());
            inventoryDetail.setMaterialSpec(orderDetail.getMaterialSpec());
            inventoryDetail.setMaterialModel(orderDetail.getMaterialModel());
            inventoryDetail.setUnitId(orderDetail.getUnitId());
            inventoryDetail.setUnitCode(orderDetail.getUnitCode());
            inventoryDetail.setUnitName(orderDetail.getUnitName());
            inventoryDetail.setWarehouseId(erpInboundOrder.getWarehouseId());
            inventoryDetail.setWarehouseCode(erpInboundOrder.getWarehouseCode());
            inventoryDetail.setWarehouseName(erpInboundOrder.getWarehouseName());
            inventoryDetail.setShelfId(orderDetail.getShelfId());
            inventoryDetail.setShelfCode(orderDetail.getShelfCode());
            inventoryDetail.setShelfName(orderDetail.getShelfName());
            inventoryDetail.setLocationId(orderDetail.getLocationId());
            inventoryDetail.setLocationCode(orderDetail.getLocationCode());
            inventoryDetail.setLocationName(orderDetail.getLocationName());
            inventoryDetail.setBatchNo(orderDetail.getBatchNo());
            inventoryDetail.setLotNo(orderDetail.getLotNo());
            inventoryDetail.setSerialNo(orderDetail.getSerialNo());
            inventoryDetail.setQuantity(orderDetail.getActualQuantity());
            inventoryDetail.setAvailableQuantity(orderDetail.getActualQuantity());
            inventoryDetail.setAllocatedQuantity(java.math.BigDecimal.ZERO);
            inventoryDetail.setFrozenQuantity(java.math.BigDecimal.ZERO);
            inventoryDetail.setUnitCost(orderDetail.getUnitPrice());
            inventoryDetail.setTotalCost(orderDetail.getTotalAmount());
            inventoryDetail.setProductionDate(orderDetail.getProductionDate());
            inventoryDetail.setExpiryDate(orderDetail.getExpiryDate());
            inventoryDetail.setQualityStatus(orderDetail.getQualityStatus());
            inventoryDetail.setQualityLevel(orderDetail.getQualityLevel());
            erpInventoryDetailMapper.insertErpInventoryDetail(inventoryDetail);
            
            // 创建库存事务记录
            ErpInventoryTransaction transaction = new ErpInventoryTransaction();
            transaction.setTransactionNo(IdUtils.fastSimpleUUID());
            transaction.setTransactionType("01"); // 入库
            transaction.setTransactionSubType(erpInboundOrder.getInboundType());
            transaction.setMaterialId(orderDetail.getMaterialId());
            transaction.setMaterialCode(orderDetail.getMaterialCode());
            transaction.setMaterialName(orderDetail.getMaterialName());
            transaction.setWarehouseId(erpInboundOrder.getWarehouseId());
            transaction.setWarehouseCode(erpInboundOrder.getWarehouseCode());
            transaction.setWarehouseName(erpInboundOrder.getWarehouseName());
            transaction.setShelfId(orderDetail.getShelfId());
            transaction.setShelfCode(orderDetail.getShelfCode());
            transaction.setShelfName(orderDetail.getShelfName());
            transaction.setLocationId(orderDetail.getLocationId());
            transaction.setLocationCode(orderDetail.getLocationCode());
            transaction.setLocationName(orderDetail.getLocationName());
            transaction.setBatchNo(orderDetail.getBatchNo());
            transaction.setLotNo(orderDetail.getLotNo());
            transaction.setSerialNo(orderDetail.getSerialNo());
            transaction.setQuantity(orderDetail.getActualQuantity());
            transaction.setUnitCost(orderDetail.getUnitPrice());
            transaction.setTotalCost(orderDetail.getTotalAmount());
            transaction.setBeforeQuantity(inventory.getTotalQuantity().subtract(orderDetail.getActualQuantity()));
            transaction.setAfterQuantity(inventory.getTotalQuantity());
            transaction.setReferenceType("INBOUND");
            transaction.setReferenceId(erpInboundOrder.getId());
            transaction.setReferenceNo(erpInboundOrder.getInboundNo());
            transaction.setOperatorId(erpInboundOrder.getReceiverId());
            transaction.setOperatorName(erpInboundOrder.getReceiverName());
            transaction.setTransactionDate(DateUtils.getNowDate());
            erpInventoryTransactionMapper.insertErpInventoryTransaction(transaction);
        }
        
        return result;
    }

    /**
     * 取消入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelErpInboundOrder(ErpInboundOrder erpInboundOrder)
    {
        erpInboundOrder.setApprovalStatus("4"); // 已取消
        return erpInboundOrderMapper.updateErpInboundOrder(erpInboundOrder);
    }

    /**
     * 生成入库单号
     * 
     * @return 入库单号
     */
    @Override
    public String generateInboundNo()
    {
        String dateStr = DateUtils.dateTimeNow("yyyyMMdd");
        String randomStr = String.valueOf((int)(Math.random() * 9000) + 1000);
        return "IN" + dateStr + randomStr;
    }

    /**
     * 查询入库单统计信息
     * 
     * @param erpInboundOrder 入库单表
     * @return 统计结果
     */
    @Override
    public List<ErpInboundOrder> selectErpInboundOrderStatistics(ErpInboundOrder erpInboundOrder)
    {
        return erpInboundOrderMapper.selectErpInboundOrderStatistics(erpInboundOrder);
    }
}
