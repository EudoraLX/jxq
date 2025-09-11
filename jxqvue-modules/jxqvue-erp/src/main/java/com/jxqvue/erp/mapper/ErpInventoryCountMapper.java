package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryCount;

/**
 * 盘点单表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryCountMapper 
{
    /**
     * 查询盘点单表
     * 
     * @param id 盘点单表主键
     * @return 盘点单表
     */
    public ErpInventoryCount selectErpInventoryCountById(Long id);

    /**
     * 查询盘点单表列表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 盘点单表集合
     */
    public List<ErpInventoryCount> selectErpInventoryCountList(ErpInventoryCount erpInventoryCount);

    /**
     * 新增盘点单表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int insertErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 修改盘点单表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int updateErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 删除盘点单表
     * 
     * @param id 盘点单表主键
     * @return 结果
     */
    public int deleteErpInventoryCountById(Long id);

    /**
     * 批量删除盘点单表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryCountByIds(Long[] ids);

    /**
     * 根据盘点单号查询盘点单
     * 
     * @param countNo 盘点单号
     * @return 盘点单表
     */
    public ErpInventoryCount selectErpInventoryCountByCountNo(String countNo);

    /**
     * 根据盘点类型查询盘点单列表
     * 
     * @param countType 盘点类型
     * @return 盘点单表集合
     */
    public List<ErpInventoryCount> selectErpInventoryCountByType(String countType);

    /**
     * 根据仓库ID查询盘点单列表
     * 
     * @param warehouseId 仓库ID
     * @return 盘点单表集合
     */
    public List<ErpInventoryCount> selectErpInventoryCountByWarehouseId(Long warehouseId);

    /**
     * 根据状态查询盘点单列表
     * 
     * @param status 状态
     * @return 盘点单表集合
     */
    public List<ErpInventoryCount> selectErpInventoryCountByStatus(String status);

    /**
     * 更新盘点单状态
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int updateErpInventoryCountStatus(ErpInventoryCount erpInventoryCount);

    /**
     * 更新盘点单统计信息
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int updateErpInventoryCountStatistics(ErpInventoryCount erpInventoryCount);

    /**
     * 查询盘点单统计信息
     * 
     * @param erpInventoryCount 盘点单表
     * @return 统计结果
     */
    public List<ErpInventoryCount> selectErpInventoryCountStatistics(ErpInventoryCount erpInventoryCount);

    /**
     * 查询盘点差异列表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 差异列表
     */
    public List<ErpInventoryCount> selectCountDifferences(ErpInventoryCount erpInventoryCount);
}
