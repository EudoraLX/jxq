package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpInventoryAlertMapper;
import com.jxqvue.erp.mapper.ErpInventoryMapper;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.domain.ErpInventoryAlert;
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.service.IErpInventoryAlertService;

/**
 * 库存预警表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpInventoryAlertServiceImpl implements IErpInventoryAlertService 
{
    @Autowired
    private ErpInventoryAlertMapper erpInventoryAlertMapper;

    @Autowired
    private ErpInventoryMapper erpInventoryMapper;

    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    /**
     * 查询库存预警表
     * 
     * @param id 库存预警表主键
     * @return 库存预警表
     */
    @Override
    public ErpInventoryAlert selectErpInventoryAlertById(Long id)
    {
        return erpInventoryAlertMapper.selectErpInventoryAlertById(id);
    }

    /**
     * 查询库存预警表列表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 库存预警表
     */
    @Override
    public List<ErpInventoryAlert> selectErpInventoryAlertList(ErpInventoryAlert erpInventoryAlert)
    {
        return erpInventoryAlertMapper.selectErpInventoryAlertList(erpInventoryAlert);
    }

    /**
     * 新增库存预警表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpInventoryAlert(ErpInventoryAlert erpInventoryAlert)
    {
        erpInventoryAlert.setCreateTime(DateUtils.getNowDate());
        return erpInventoryAlertMapper.insertErpInventoryAlert(erpInventoryAlert);
    }

    /**
     * 修改库存预警表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpInventoryAlert(ErpInventoryAlert erpInventoryAlert)
    {
        erpInventoryAlert.setUpdateTime(DateUtils.getNowDate());
        return erpInventoryAlertMapper.updateErpInventoryAlert(erpInventoryAlert);
    }

    /**
     * 批量删除库存预警表
     * 
     * @param ids 需要删除的库存预警表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInventoryAlertByIds(Long[] ids)
    {
        return erpInventoryAlertMapper.deleteErpInventoryAlertByIds(ids);
    }

    /**
     * 删除库存预警表信息
     * 
     * @param id 库存预警表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteErpInventoryAlertById(Long id)
    {
        return erpInventoryAlertMapper.deleteErpInventoryAlertById(id);
    }

    /**
     * 处理库存预警
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    @Override
    @Transactional
    public int handleErpInventoryAlert(ErpInventoryAlert erpInventoryAlert)
    {
        erpInventoryAlert.setIsHandled("1"); // 已处理
        erpInventoryAlert.setHandleTime(DateUtils.getNowDate());
        return erpInventoryAlertMapper.updateErpInventoryAlert(erpInventoryAlert);
    }

    /**
     * 批量处理库存预警
     * 
     * @param ids 需要处理的库存预警表主键
     * @return 结果
     */
    @Override
    @Transactional
    public int handleErpInventoryAlertByIds(Long[] ids)
    {
        return erpInventoryAlertMapper.handleErpInventoryAlertByIds(ids);
    }

    /**
     * 查询库存预警表统计信息
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 统计结果
     */
    @Override
    public List<ErpInventoryAlert> selectErpInventoryAlertStatistics(ErpInventoryAlert erpInventoryAlert)
    {
        return erpInventoryAlertMapper.selectErpInventoryAlertStatistics(erpInventoryAlert);
    }

    /**
     * 生成库存预警
     * 
     * @return 结果
     */
    @Override
    @Transactional
    public int generateInventoryAlerts()
    {
        int alertCount = 0;
        
        // 查询所有库存
        List<ErpInventory> inventoryList = erpInventoryMapper.selectErpInventoryList(new ErpInventory());
        
        for (ErpInventory inventory : inventoryList) {
            // 检查库存不足预警
            if (checkLowStockAlert(inventory.getMaterialId(), inventory.getWarehouseId())) {
                ErpInventoryAlert alert = new ErpInventoryAlert();
                alert.setMaterialId(inventory.getMaterialId());
                alert.setMaterialCode(inventory.getMaterialCode());
                alert.setMaterialName(inventory.getMaterialName());
                alert.setWarehouseId(inventory.getWarehouseId());
                alert.setWarehouseCode(inventory.getWarehouseCode());
                alert.setWarehouseName(inventory.getWarehouseName());
                alert.setAlertType("01"); // 库存不足
                alert.setCurrentQuantity(inventory.getAvailableQuantity());
                alert.setThresholdQuantity(inventory.getSafetyStock());
                alert.setAlertLevel("3"); // 高
                alert.setAlertMessage("物料 " + inventory.getMaterialCode() + " 库存不足，当前库存：" + inventory.getAvailableQuantity() + "，安全库存：" + inventory.getSafetyStock());
                alert.setIsHandled("0"); // 未处理
                insertErpInventoryAlert(alert);
                alertCount++;
            }
            
            // 检查库存过多预警
            if (checkHighStockAlert(inventory.getMaterialId(), inventory.getWarehouseId())) {
                ErpInventoryAlert alert = new ErpInventoryAlert();
                alert.setMaterialId(inventory.getMaterialId());
                alert.setMaterialCode(inventory.getMaterialCode());
                alert.setMaterialName(inventory.getMaterialName());
                alert.setWarehouseId(inventory.getWarehouseId());
                alert.setWarehouseCode(inventory.getWarehouseCode());
                alert.setWarehouseName(inventory.getWarehouseName());
                alert.setAlertType("02"); // 库存过多
                alert.setCurrentQuantity(inventory.getAvailableQuantity());
                alert.setThresholdQuantity(inventory.getMaxStock());
                alert.setAlertLevel("2"); // 中
                alert.setAlertMessage("物料 " + inventory.getMaterialCode() + " 库存过多，当前库存：" + inventory.getAvailableQuantity() + "，最大库存：" + inventory.getMaxStock());
                alert.setIsHandled("0"); // 未处理
                insertErpInventoryAlert(alert);
                alertCount++;
            }
            
            // 检查即将过期预警
            if (checkExpiryAlert(inventory.getMaterialId(), inventory.getWarehouseId(), 30)) {
                ErpInventoryAlert alert = new ErpInventoryAlert();
                alert.setMaterialId(inventory.getMaterialId());
                alert.setMaterialCode(inventory.getMaterialCode());
                alert.setMaterialName(inventory.getMaterialName());
                alert.setWarehouseId(inventory.getWarehouseId());
                alert.setWarehouseCode(inventory.getWarehouseCode());
                alert.setWarehouseName(inventory.getWarehouseName());
                alert.setAlertType("03"); // 即将过期
                alert.setCurrentQuantity(inventory.getAvailableQuantity());
                alert.setThresholdQuantity(java.math.BigDecimal.ZERO);
                alert.setAlertLevel("2"); // 中
                alert.setAlertMessage("物料 " + inventory.getMaterialCode() + " 即将在30天内过期");
                alert.setIsHandled("0"); // 未处理
                insertErpInventoryAlert(alert);
                alertCount++;
            }
            
            // 检查已过期预警
            if (checkExpiredAlert(inventory.getMaterialId(), inventory.getWarehouseId())) {
                ErpInventoryAlert alert = new ErpInventoryAlert();
                alert.setMaterialId(inventory.getMaterialId());
                alert.setMaterialCode(inventory.getMaterialCode());
                alert.setMaterialName(inventory.getMaterialName());
                alert.setWarehouseId(inventory.getWarehouseId());
                alert.setWarehouseCode(inventory.getWarehouseCode());
                alert.setWarehouseName(inventory.getWarehouseName());
                alert.setAlertType("04"); // 已过期
                alert.setCurrentQuantity(inventory.getAvailableQuantity());
                alert.setThresholdQuantity(java.math.BigDecimal.ZERO);
                alert.setAlertLevel("3"); // 高
                alert.setAlertMessage("物料 " + inventory.getMaterialCode() + " 已过期");
                alert.setIsHandled("0"); // 未处理
                insertErpInventoryAlert(alert);
                alertCount++;
            }
        }
        
        return alertCount;
    }

    /**
     * 检查库存不足预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    @Override
    public boolean checkLowStockAlert(Long materialId, Long warehouseId)
    {
        ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            return false;
        }
        
        // 检查是否已存在未处理的库存不足预警
        ErpInventoryAlert existingAlert = new ErpInventoryAlert();
        existingAlert.setMaterialId(materialId);
        existingAlert.setWarehouseId(warehouseId);
        existingAlert.setAlertType("01");
        existingAlert.setIsHandled("0");
        List<ErpInventoryAlert> existingAlerts = erpInventoryAlertMapper.selectErpInventoryAlertList(existingAlert);
        if (!existingAlerts.isEmpty()) {
            return false;
        }
        
        return inventory.getAvailableQuantity().compareTo(inventory.getSafetyStock()) < 0;
    }

    /**
     * 检查库存过多预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    @Override
    public boolean checkHighStockAlert(Long materialId, Long warehouseId)
    {
        ErpInventory inventory = erpInventoryMapper.selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        if (inventory == null) {
            return false;
        }
        
        // 检查是否已存在未处理的库存过多预警
        ErpInventoryAlert existingAlert = new ErpInventoryAlert();
        existingAlert.setMaterialId(materialId);
        existingAlert.setWarehouseId(warehouseId);
        existingAlert.setAlertType("02");
        existingAlert.setIsHandled("0");
        List<ErpInventoryAlert> existingAlerts = erpInventoryAlertMapper.selectErpInventoryAlertList(existingAlert);
        if (!existingAlerts.isEmpty()) {
            return false;
        }
        
        return inventory.getAvailableQuantity().compareTo(inventory.getMaxStock()) > 0;
    }

    /**
     * 检查即将过期预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param days 提前天数
     * @return 是否预警
     */
    @Override
    public boolean checkExpiryAlert(Long materialId, Long warehouseId, int days)
    {
        // 查询库存明细，检查到期日期
        List<ErpInventoryDetail> detailList = erpInventoryDetailMapper.selectErpInventoryDetailByMaterialAndWarehouse(materialId, warehouseId);
        
        if (detailList.isEmpty()) {
            return false;
        }
        
        // 检查是否已存在未处理的即将过期预警
        ErpInventoryAlert existingAlert = new ErpInventoryAlert();
        existingAlert.setMaterialId(materialId);
        existingAlert.setWarehouseId(warehouseId);
        existingAlert.setAlertType("03");
        existingAlert.setIsHandled("0");
        List<ErpInventoryAlert> existingAlerts = erpInventoryAlertMapper.selectErpInventoryAlertList(existingAlert);
        if (!existingAlerts.isEmpty()) {
            return false;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date thresholdDate = calendar.getTime();
        
        for (ErpInventoryDetail detail : detailList) {
            if (detail.getExpiryDate() != null && detail.getExpiryDate().before(thresholdDate)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * 检查已过期预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    @Override
    public boolean checkExpiredAlert(Long materialId, Long warehouseId)
    {
        // 查询库存明细，检查到期日期
        List<ErpInventoryDetail> detailList = erpInventoryDetailMapper.selectErpInventoryDetailByMaterialAndWarehouse(materialId, warehouseId);
        
        if (detailList.isEmpty()) {
            return false;
        }
        
        // 检查是否已存在未处理的已过期预警
        ErpInventoryAlert existingAlert = new ErpInventoryAlert();
        existingAlert.setMaterialId(materialId);
        existingAlert.setWarehouseId(warehouseId);
        existingAlert.setAlertType("04");
        existingAlert.setIsHandled("0");
        List<ErpInventoryAlert> existingAlerts = erpInventoryAlertMapper.selectErpInventoryAlertList(existingAlert);
        if (!existingAlerts.isEmpty()) {
            return false;
        }
        
        Date currentDate = new Date();
        
        for (ErpInventoryDetail detail : detailList) {
            if (detail.getExpiryDate() != null && detail.getExpiryDate().before(currentDate)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * 查询未处理的预警数量
     * 
     * @return 未处理预警数量
     */
    @Override
    public int selectUnhandledAlertCount()
    {
        return erpInventoryAlertMapper.selectUnhandledAlertCount();
    }

    /**
     * 查询高优先级预警数量
     * 
     * @return 高优先级预警数量
     */
    @Override
    public int selectHighPriorityAlertCount()
    {
        return erpInventoryAlertMapper.selectHighPriorityAlertCount();
    }
}
