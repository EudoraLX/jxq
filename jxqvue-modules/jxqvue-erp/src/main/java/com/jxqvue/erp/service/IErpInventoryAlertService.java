package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryAlert;

/**
 * 库存预警表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpInventoryAlertService 
{
    /**
     * 查询库存预警表
     * 
     * @param id 库存预警表主键
     * @return 库存预警表
     */
    public ErpInventoryAlert selectErpInventoryAlertById(Long id);

    /**
     * 查询库存预警表列表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertList(ErpInventoryAlert erpInventoryAlert);

    /**
     * 新增库存预警表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    public int insertErpInventoryAlert(ErpInventoryAlert erpInventoryAlert);

    /**
     * 修改库存预警表
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    public int updateErpInventoryAlert(ErpInventoryAlert erpInventoryAlert);

    /**
     * 批量删除库存预警表
     * 
     * @param ids 需要删除的库存预警表主键集合
     * @return 结果
     */
    public int deleteErpInventoryAlertByIds(Long[] ids);

    /**
     * 删除库存预警表信息
     * 
     * @param id 库存预警表主键
     * @return 结果
     */
    public int deleteErpInventoryAlertById(Long id);

    /**
     * 处理库存预警
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    public int handleErpInventoryAlert(ErpInventoryAlert erpInventoryAlert);

    /**
     * 批量处理库存预警
     * 
     * @param ids 需要处理的库存预警表主键集合
     * @return 结果
     */
    public int handleErpInventoryAlertByIds(Long[] ids);

    /**
     * 查询库存预警表统计信息
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 统计结果
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertStatistics(ErpInventoryAlert erpInventoryAlert);

    /**
     * 生成库存预警
     * 
     * @return 结果
     */
    public int generateInventoryAlerts();

    /**
     * 检查库存不足预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    public boolean checkLowStockAlert(Long materialId, Long warehouseId);

    /**
     * 检查库存过多预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    public boolean checkHighStockAlert(Long materialId, Long warehouseId);

    /**
     * 检查即将过期预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param days 提前天数
     * @return 是否预警
     */
    public boolean checkExpiryAlert(Long materialId, Long warehouseId, int days);

    /**
     * 检查已过期预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 是否预警
     */
    public boolean checkExpiredAlert(Long materialId, Long warehouseId);

    /**
     * 查询未处理的预警数量
     * 
     * @return 未处理预警数量
     */
    public int selectUnhandledAlertCount();

    /**
     * 查询高优先级预警数量
     * 
     * @return 高优先级预警数量
     */
    public int selectHighPriorityAlertCount();
}
