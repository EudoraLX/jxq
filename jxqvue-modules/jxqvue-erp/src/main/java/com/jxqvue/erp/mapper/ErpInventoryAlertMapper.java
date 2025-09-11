package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryAlert;

/**
 * 库存预警表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryAlertMapper 
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
     * 删除库存预警表
     * 
     * @param id 库存预警表主键
     * @return 结果
     */
    public int deleteErpInventoryAlertById(Long id);

    /**
     * 批量删除库存预警表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryAlertByIds(Long[] ids);

    /**
     * 根据物料ID查询库存预警列表
     * 
     * @param materialId 物料ID
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertByMaterialId(Long materialId);

    /**
     * 根据仓库ID查询库存预警列表
     * 
     * @param warehouseId 仓库ID
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertByWarehouseId(Long warehouseId);

    /**
     * 根据预警类型查询库存预警列表
     * 
     * @param alertType 预警类型
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertByType(String alertType);

    /**
     * 根据预警级别查询库存预警列表
     * 
     * @param alertLevel 预警级别
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertByLevel(String alertLevel);

    /**
     * 根据处理状态查询库存预警列表
     * 
     * @param isHandled 是否已处理
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertByHandled(String isHandled);

    /**
     * 更新库存预警处理状态
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 结果
     */
    public int updateErpInventoryAlertHandled(ErpInventoryAlert erpInventoryAlert);

    /**
     * 查询未处理的库存预警列表
     * 
     * @return 库存预警表集合
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertUnhandled();

    /**
     * 查询库存预警统计信息
     * 
     * @param erpInventoryAlert 库存预警表
     * @return 统计结果
     */
    public List<ErpInventoryAlert> selectErpInventoryAlertStatistics(ErpInventoryAlert erpInventoryAlert);

    /**
     * 根据物料ID和仓库ID查询库存预警
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param alertType 预警类型
     * @return 库存预警表
     */
    public ErpInventoryAlert selectErpInventoryAlertByMaterialAndWarehouse(Long materialId, Long warehouseId, String alertType);

    /**
     * 批量处理库存预警
     * 
     * @param ids 需要处理的库存预警表主键
     * @return 结果
     */
    public int handleErpInventoryAlertByIds(Long[] ids);

    /**
     * 查询未处理预警数量
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
