package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import com.jxqvue.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpInventoryCountMapper;
import com.jxqvue.erp.mapper.ErpInventoryCountDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.mapper.ErpInventoryTransactionMapper;
import com.jxqvue.erp.domain.ErpInventoryCount;
import com.jxqvue.erp.domain.ErpInventoryCountDetail;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.domain.ErpInventoryTransaction;
import com.jxqvue.erp.service.IErpInventoryCountService;

/**
 * 盘点单表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpInventoryCountServiceImpl implements IErpInventoryCountService 
{
    @Autowired
    private ErpInventoryCountMapper erpInventoryCountMapper;

    @Autowired
    private ErpInventoryCountDetailMapper erpInventoryCountDetailMapper;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    @Autowired
    private ErpInventoryTransactionMapper erpInventoryTransactionMapper;

    /**
     * 查询盘点单表
     * 
     * @param id 盘点单表主键
     * @return 盘点单表
     */
    @Override
    public ErpInventoryCount selectErpInventoryCountById(Long id)
    {
        ErpInventoryCount erpInventoryCount = erpInventoryCountMapper.selectErpInventoryCountById(id);
        if (erpInventoryCount != null) {
            // 查询明细信息
            ErpInventoryCountDetail detail = new ErpInventoryCountDetail();
            detail.setCountId(id);
            List<ErpInventoryCountDetail> detailList = erpInventoryCountDetailMapper.selectErpInventoryCountDetailList(detail);
            erpInventoryCount.setInventoryCountDetailList(detailList);
        }
        return erpInventoryCount;
    }

    /**
     * 查询盘点单表列表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 盘点单表
     */
    @Override
    public List<ErpInventoryCount> selectErpInventoryCountList(ErpInventoryCount erpInventoryCount)
    {
        return erpInventoryCountMapper.selectErpInventoryCountList(erpInventoryCount);
    }

    /**
     * 新增盘点单表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        erpInventoryCount.setCreateTime(DateUtils.getNowDate());
        // 生成盘点单号
        if (StringUtils.isEmpty(erpInventoryCount.getCountNo())) {
            erpInventoryCount.setCountNo(generateCountNo());
        }
        int result = erpInventoryCountMapper.insertErpInventoryCount(erpInventoryCount);
        
        // 插入明细信息
        if (erpInventoryCount.getInventoryCountDetailList() != null && !erpInventoryCount.getInventoryCountDetailList().isEmpty()) {
            for (ErpInventoryCountDetail detail : erpInventoryCount.getInventoryCountDetailList()) {
                detail.setCountId(erpInventoryCount.getId());
                detail.setCountNo(erpInventoryCount.getCountNo());
                erpInventoryCountDetailMapper.insertErpInventoryCountDetail(detail);
            }
        }
        
        return result;
    }

    /**
     * 修改盘点单表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        erpInventoryCount.setUpdateTime(DateUtils.getNowDate());
        
        // 更新明细信息
        if (erpInventoryCount.getInventoryCountDetailList() != null) {
            // 删除原有明细
            erpInventoryCountDetailMapper.deleteErpInventoryCountDetailByCountId(erpInventoryCount.getId());
            // 插入新明细
            for (ErpInventoryCountDetail detail : erpInventoryCount.getInventoryCountDetailList()) {
                detail.setCountId(erpInventoryCount.getId());
                detail.setCountNo(erpInventoryCount.getCountNo());
                erpInventoryCountDetailMapper.insertErpInventoryCountDetail(detail);
            }
        }
        
        return erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
    }

    /**
     * 批量删除盘点单表
     * 
     * @param ids 需要删除的盘点单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInventoryCountByIds(Long[] ids)
    {
        for (Long id : ids) {
            // 删除明细信息
            erpInventoryCountDetailMapper.deleteErpInventoryCountDetailByCountId(id);
        }
        return erpInventoryCountMapper.deleteErpInventoryCountByIds(ids);
    }

    /**
     * 删除盘点单表信息
     * 
     * @param id 盘点单表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInventoryCountById(Long id)
    {
        // 删除明细信息
        erpInventoryCountDetailMapper.deleteErpInventoryCountDetailByCountId(id);
        return erpInventoryCountMapper.deleteErpInventoryCountById(id);
    }

    /**
     * 根据盘点单号查询盘点单
     * 
     * @param countNo 盘点单号
     * @return 盘点单表
     */
    @Override
    public ErpInventoryCount selectErpInventoryCountByCountNo(String countNo)
    {
        return erpInventoryCountMapper.selectErpInventoryCountByCountNo(countNo);
    }

    /**
     * 审核盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int approveErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        erpInventoryCount.setApprovalStatus("2"); // 已审核
        erpInventoryCount.setApproveTime(DateUtils.getNowDate());
        return erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
    }

    /**
     * 执行盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int executeErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        // 更新盘点单状态
        erpInventoryCount.setApprovalStatus("3"); // 已盘点
        erpInventoryCount.setCountTime(DateUtils.getNowDate());
        int result = erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
        
        // 计算盘点差异
        calculateCountDifferences(erpInventoryCount);
        
        return result;
    }

    /**
     * 调整盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int adjustErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        // 更新盘点单状态
        erpInventoryCount.setApprovalStatus("4"); // 已调整
        erpInventoryCount.setAdjustTime(DateUtils.getNowDate());
        int result = erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
        
        // 查询明细信息
        ErpInventoryCountDetail detail = new ErpInventoryCountDetail();
        detail.setCountId(erpInventoryCount.getId());
        List<ErpInventoryCountDetail> detailList = erpInventoryCountDetailMapper.selectErpInventoryCountDetailList(detail);
        
        // 调整库存
        for (ErpInventoryCountDetail countDetail : detailList) {
            if (countDetail.getDifferenceQuantity().compareTo(java.math.BigDecimal.ZERO) != 0) {
                // 更新库存主表
                ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(
                    countDetail.getMaterialId(), erpInventoryCount.getWarehouseId());
                
                if (inventory != null) {
                    java.math.BigDecimal newTotalQuantity = inventory.getTotalQuantity().add(countDetail.getDifferenceQuantity());
                    java.math.BigDecimal newAvailableQuantity = inventory.getAvailableQuantity().add(countDetail.getDifferenceQuantity());
                    java.math.BigDecimal newTotalCost = inventory.getTotalCost().add(countDetail.getDifferenceAmount());
                    
                    inventory.setTotalQuantity(newTotalQuantity);
                    inventory.setAvailableQuantity(newAvailableQuantity);
                    inventory.setTotalCost(newTotalCost);
                    inventory.setLastCountDate(DateUtils.getNowDate());
                    erpInventoryMapper.updateErpInventory(inventory);
                }
                
                // 更新或创建库存明细记录
                List<ErpInventoryDetail> inventoryDetailList = erpInventoryDetailMapper.selectErpInventoryDetailByMaterialAndWarehouseAndLocation(
                    countDetail.getMaterialId(), erpInventoryCount.getWarehouseId(), countDetail.getLocationId());
                ErpInventoryDetail inventoryDetail = inventoryDetailList.isEmpty() ? null : inventoryDetailList.get(0);
                
                if (inventoryDetail != null) {
                    // 更新现有库存明细
                    inventoryDetail.setQuantity(countDetail.getCountQuantity());
                    inventoryDetail.setAvailableQuantity(countDetail.getCountQuantity());
                    inventoryDetail.setTotalCost(countDetail.getCountQuantity().multiply(countDetail.getUnitCost()));
                    inventoryDetail.setUnitCost(countDetail.getUnitCost());
                    erpInventoryDetailMapper.updateErpInventoryDetail(inventoryDetail);
                } else {
                    // 创建新的库存明细记录
                    inventoryDetail = new ErpInventoryDetail();
                    inventoryDetail.setInventoryId(inventory.getId());
                    inventoryDetail.setMaterialId(countDetail.getMaterialId());
                    inventoryDetail.setMaterialCode(countDetail.getMaterialCode());
                    inventoryDetail.setMaterialName(countDetail.getMaterialName());
                    inventoryDetail.setMaterialSpec(countDetail.getMaterialSpec());
                    inventoryDetail.setMaterialModel(countDetail.getMaterialModel());
                    inventoryDetail.setUnitId(countDetail.getUnitId());
                    inventoryDetail.setUnitCode(countDetail.getUnitCode());
                    inventoryDetail.setUnitName(countDetail.getUnitName());
                    inventoryDetail.setWarehouseId(erpInventoryCount.getWarehouseId());
                    inventoryDetail.setWarehouseCode(erpInventoryCount.getWarehouseCode());
                    inventoryDetail.setWarehouseName(erpInventoryCount.getWarehouseName());
                    inventoryDetail.setShelfId(countDetail.getShelfId());
                    inventoryDetail.setShelfCode(countDetail.getShelfCode());
                    inventoryDetail.setShelfName(countDetail.getShelfName());
                    inventoryDetail.setLocationId(countDetail.getLocationId());
                    inventoryDetail.setLocationCode(countDetail.getLocationCode());
                    inventoryDetail.setLocationName(countDetail.getLocationName());
                    inventoryDetail.setBatchNo(countDetail.getBatchNo());
                    inventoryDetail.setLotNo(countDetail.getLotNo());
                    inventoryDetail.setSerialNo(countDetail.getSerialNo());
                    inventoryDetail.setQuantity(countDetail.getCountQuantity());
                    inventoryDetail.setAvailableQuantity(countDetail.getCountQuantity());
                    inventoryDetail.setAllocatedQuantity(java.math.BigDecimal.ZERO);
                    inventoryDetail.setFrozenQuantity(java.math.BigDecimal.ZERO);
                    inventoryDetail.setUnitCost(countDetail.getUnitCost());
                    inventoryDetail.setTotalCost(countDetail.getCountQuantity().multiply(countDetail.getUnitCost()));
                    // 设置生产日期和到期日期（如果ErpInventoryCountDetail有这些字段）
                    // inventoryDetail.setProductionDate(countDetail.getProductionDate());
                    // inventoryDetail.setExpiryDate(countDetail.getExpiryDate());
                    // inventoryDetail.setQualityStatus(countDetail.getQualityStatus());
                    // inventoryDetail.setQualityLevel(countDetail.getQualityLevel());
                    erpInventoryDetailMapper.insertErpInventoryDetail(inventoryDetail);
                }
                
                // 创建库存事务记录
                ErpInventoryTransaction transaction = new ErpInventoryTransaction();
                transaction.setTransactionNo(IdUtils.fastSimpleUUID());
                transaction.setTransactionType("04"); // 盘点
                transaction.setTransactionSubType(erpInventoryCount.getCountType());
                transaction.setMaterialId(countDetail.getMaterialId());
                transaction.setMaterialCode(countDetail.getMaterialCode());
                transaction.setMaterialName(countDetail.getMaterialName());
                // 设置物料规格和型号（如果ErpInventoryTransaction有这些字段）
                // transaction.setMaterialSpec(countDetail.getMaterialSpec());
                // transaction.setMaterialModel(countDetail.getMaterialModel());
                // transaction.setUnitId(countDetail.getUnitId());
                // transaction.setUnitCode(countDetail.getUnitCode());
                // transaction.setUnitName(countDetail.getUnitName());
                transaction.setWarehouseId(erpInventoryCount.getWarehouseId());
                transaction.setWarehouseCode(erpInventoryCount.getWarehouseCode());
                transaction.setWarehouseName(erpInventoryCount.getWarehouseName());
                transaction.setShelfId(countDetail.getShelfId());
                transaction.setShelfCode(countDetail.getShelfCode());
                transaction.setShelfName(countDetail.getShelfName());
                transaction.setLocationId(countDetail.getLocationId());
                transaction.setLocationCode(countDetail.getLocationCode());
                transaction.setLocationName(countDetail.getLocationName());
                transaction.setBatchNo(countDetail.getBatchNo());
                transaction.setLotNo(countDetail.getLotNo());
                transaction.setSerialNo(countDetail.getSerialNo());
                transaction.setQuantity(countDetail.getDifferenceQuantity());
                transaction.setUnitCost(countDetail.getUnitCost());
                transaction.setTotalCost(countDetail.getDifferenceAmount());
                transaction.setBeforeQuantity(countDetail.getBookQuantity());
                transaction.setAfterQuantity(countDetail.getCountQuantity());
                transaction.setReferenceType("COUNT");
                transaction.setReferenceId(erpInventoryCount.getId());
                transaction.setReferenceNo(erpInventoryCount.getCountNo());
                // 设置操作人信息（如果ErpInventoryCount有这些字段）
                // transaction.setOperatorId(erpInventoryCount.getCounterId());
                // transaction.setOperatorName(erpInventoryCount.getCounterName());
                transaction.setTransactionDate(DateUtils.getNowDate());
                erpInventoryTransactionMapper.insertErpInventoryTransaction(transaction);
            }
        }
        
        return result;
    }

    /**
     * 取消盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int cancelErpInventoryCount(ErpInventoryCount erpInventoryCount)
    {
        erpInventoryCount.setApprovalStatus("5"); // 已取消
        return erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
    }

    /**
     * 生成盘点单号
     * 
     * @return 盘点单号
     */
    @Override
    public String generateCountNo()
    {
        String dateStr = DateUtils.dateTimeNow("yyyyMMdd");
        String randomStr = String.valueOf((int)(Math.random() * 9000) + 1000);
        return "CNT" + dateStr + randomStr;
    }

    /**
     * 查询盘点单统计信息
     * 
     * @param erpInventoryCount 盘点单表
     * @return 统计结果
     */
    @Override
    public List<ErpInventoryCount> selectErpInventoryCountStatistics(ErpInventoryCount erpInventoryCount)
    {
        return erpInventoryCountMapper.selectErpInventoryCountStatistics(erpInventoryCount);
    }

    /**
     * 生成盘点明细
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int generateCountDetails(ErpInventoryCount erpInventoryCount)
    {
        // 查询仓库中的所有库存
        List<ErpInventory> inventoryList = erpInventoryMapper.selectErpInventoryList(
            new ErpInventory() {{
                setWarehouseId(erpInventoryCount.getWarehouseId());
            }}
        );
        
        int count = 0;
        for (ErpInventory inventory : inventoryList) {
            ErpInventoryCountDetail detail = new ErpInventoryCountDetail();
            detail.setCountId(erpInventoryCount.getId());
            detail.setCountNo(erpInventoryCount.getCountNo());
            detail.setMaterialId(inventory.getMaterialId());
            detail.setMaterialCode(inventory.getMaterialCode());
            detail.setMaterialName(inventory.getMaterialName());
            detail.setMaterialSpec(inventory.getMaterialSpec());
            detail.setMaterialModel(inventory.getMaterialModel());
            detail.setUnitName(inventory.getUnit());
            detail.setBookQuantity(inventory.getTotalQuantity());
            detail.setCountQuantity(java.math.BigDecimal.ZERO);
            detail.setDifferenceQuantity(java.math.BigDecimal.ZERO);
            detail.setUnitCost(inventory.getUnitCost());
            // 设置金额字段（如果ErpInventoryCountDetail有这些字段）
            // detail.setBookAmount(inventory.getTotalCost());
            // detail.setCountAmount(java.math.BigDecimal.ZERO);
            detail.setDifferenceAmount(java.math.BigDecimal.ZERO);
            detail.setStatus("0"); // 未盘点
            
            erpInventoryCountDetailMapper.insertErpInventoryCountDetail(detail);
            count++;
        }
        
        // 更新盘点单统计信息
        erpInventoryCount.setTotalItems((long) count);
        erpInventoryCount.setCountedItems(0L);
        erpInventoryCount.setDifferenceItems(0L);
        erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
        
        return count;
    }

    /**
     * 计算盘点差异
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    @Override
    @Transactional
    public int calculateCountDifferences(ErpInventoryCount erpInventoryCount)
    {
        // 查询明细信息
        ErpInventoryCountDetail detail = new ErpInventoryCountDetail();
        detail.setCountId(erpInventoryCount.getId());
        List<ErpInventoryCountDetail> detailList = erpInventoryCountDetailMapper.selectErpInventoryCountDetailList(detail);
        
        int differenceItems = 0;
        java.math.BigDecimal totalDifferenceAmount = java.math.BigDecimal.ZERO;
        
        for (ErpInventoryCountDetail countDetail : detailList) {
            // 计算数量差异
            java.math.BigDecimal differenceQuantity = countDetail.getCountQuantity().subtract(countDetail.getBookQuantity());
            countDetail.setDifferenceQuantity(differenceQuantity);
            
            // 计算金额差异（如果ErpInventoryCountDetail有这些字段）
            // java.math.BigDecimal differenceAmount = countDetail.getCountAmount().subtract(countDetail.getBookAmount());
            // countDetail.setDifferenceAmount(differenceAmount);
            java.math.BigDecimal differenceAmount = java.math.BigDecimal.ZERO;
            
            // 更新盘点状态
            if (differenceQuantity.compareTo(java.math.BigDecimal.ZERO) != 0) {
                countDetail.setStatus("2"); // 有差异
                differenceItems++;
            } else {
                countDetail.setStatus("1"); // 无差异
            }
            
            // 更新明细
            erpInventoryCountDetailMapper.updateErpInventoryCountDetail(countDetail);
            
            totalDifferenceAmount = totalDifferenceAmount.add(differenceAmount);
        }
        
        // 更新盘点单统计信息
        erpInventoryCount.setDifferenceItems((long) differenceItems);
        erpInventoryCount.setTotalDifferenceAmount(totalDifferenceAmount);
        erpInventoryCountMapper.updateErpInventoryCount(erpInventoryCount);
        
        return differenceItems;
    }

    /**
     * 查询盘点差异列表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 差异列表
     */
    @Override
    public List<ErpInventoryCount> selectCountDifferences(ErpInventoryCount erpInventoryCount)
    {
        return erpInventoryCountMapper.selectCountDifferences(erpInventoryCount);
    }
}
