package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryCountDetail;

/**
 * 盘点单明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryCountDetailMapper 
{
    /**
     * 查询盘点单明细表
     * 
     * @param id 盘点单明细表主键
     * @return 盘点单明细表
     */
    public ErpInventoryCountDetail selectErpInventoryCountDetailById(Long id);

    /**
     * 查询盘点单明细表列表
     * 
     * @param erpInventoryCountDetail 盘点单明细表
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailList(ErpInventoryCountDetail erpInventoryCountDetail);

    /**
     * 新增盘点单明细表
     * 
     * @param erpInventoryCountDetail 盘点单明细表
     * @return 结果
     */
    public int insertErpInventoryCountDetail(ErpInventoryCountDetail erpInventoryCountDetail);

    /**
     * 修改盘点单明细表
     * 
     * @param erpInventoryCountDetail 盘点单明细表
     * @return 结果
     */
    public int updateErpInventoryCountDetail(ErpInventoryCountDetail erpInventoryCountDetail);

    /**
     * 删除盘点单明细表
     * 
     * @param id 盘点单明细表主键
     * @return 结果
     */
    public int deleteErpInventoryCountDetailById(Long id);

    /**
     * 批量删除盘点单明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryCountDetailByIds(Long[] ids);

    /**
     * 根据盘点单ID查询明细列表
     * 
     * @param countId 盘点单ID
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailByCountId(Long countId);

    /**
     * 根据盘点单号查询明细列表
     * 
     * @param countNo 盘点单号
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailByCountNo(String countNo);

    /**
     * 根据物料ID查询盘点单明细列表
     * 
     * @param materialId 物料ID
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailByMaterialId(Long materialId);

    /**
     * 根据货位ID查询盘点单明细列表
     * 
     * @param locationId 货位ID
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailByLocationId(Long locationId);

    /**
     * 根据批次号查询盘点单明细列表
     * 
     * @param batchNo 批次号
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailByBatchNo(String batchNo);

    /**
     * 根据序列号查询盘点单明细
     * 
     * @param serialNo 序列号
     * @return 盘点单明细表
     */
    public ErpInventoryCountDetail selectErpInventoryCountDetailBySerialNo(String serialNo);

    /**
     * 批量新增盘点单明细
     * 
     * @param erpInventoryCountDetailList 盘点单明细列表
     * @return 结果
     */
    public int batchInsertErpInventoryCountDetail(List<ErpInventoryCountDetail> erpInventoryCountDetailList);

    /**
     * 根据盘点单ID删除明细
     * 
     * @param countId 盘点单ID
     * @return 结果
     */
    public int deleteErpInventoryCountDetailByCountId(Long countId);

    /**
     * 更新盘点单明细盘点数量
     * 
     * @param erpInventoryCountDetail 盘点单明细表
     * @return 结果
     */
    public int updateErpInventoryCountDetailCountQuantity(ErpInventoryCountDetail erpInventoryCountDetail);

    /**
     * 更新盘点单明细调整信息
     * 
     * @param erpInventoryCountDetail 盘点单明细表
     * @return 结果
     */
    public int updateErpInventoryCountDetailAdjust(ErpInventoryCountDetail erpInventoryCountDetail);

    /**
     * 查询有差异的盘点单明细列表
     * 
     * @param countId 盘点单ID
     * @return 盘点单明细表集合
     */
    public List<ErpInventoryCountDetail> selectErpInventoryCountDetailWithDifference(Long countId);
}
