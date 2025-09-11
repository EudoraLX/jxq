package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryCount;

/**
 * 盘点单表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpInventoryCountService 
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
     * 批量删除盘点单表
     * 
     * @param ids 需要删除的盘点单表主键集合
     * @return 结果
     */
    public int deleteErpInventoryCountByIds(Long[] ids);

    /**
     * 删除盘点单表信息
     * 
     * @param id 盘点单表主键
     * @return 结果
     */
    public int deleteErpInventoryCountById(Long id);

    /**
     * 根据盘点单号查询盘点单
     * 
     * @param countNo 盘点单号
     * @return 盘点单表
     */
    public ErpInventoryCount selectErpInventoryCountByCountNo(String countNo);

    /**
     * 审核盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int approveErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 执行盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int executeErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 调整盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int adjustErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 取消盘点单
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int cancelErpInventoryCount(ErpInventoryCount erpInventoryCount);

    /**
     * 生成盘点单号
     * 
     * @return 盘点单号
     */
    public String generateCountNo();

    /**
     * 查询盘点单统计信息
     * 
     * @param erpInventoryCount 盘点单表
     * @return 统计结果
     */
    public List<ErpInventoryCount> selectErpInventoryCountStatistics(ErpInventoryCount erpInventoryCount);

    /**
     * 生成盘点明细
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int generateCountDetails(ErpInventoryCount erpInventoryCount);

    /**
     * 计算盘点差异
     * 
     * @param erpInventoryCount 盘点单表
     * @return 结果
     */
    public int calculateCountDifferences(ErpInventoryCount erpInventoryCount);

    /**
     * 查询盘点差异列表
     * 
     * @param erpInventoryCount 盘点单表
     * @return 差异列表
     */
    public List<ErpInventoryCount> selectCountDifferences(ErpInventoryCount erpInventoryCount);
}
