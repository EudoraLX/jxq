package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryDetail;

/**
 * 库存明细Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IErpInventoryDetailService 
{
    /**
     * 查询库存明细
     * 
     * @param id 库存明细主键
     * @return 库存明细
     */
    public ErpInventoryDetail selectErpInventoryDetailById(Long id);

    /**
     * 查询库存明细列表
     * 
     * @param erpInventoryDetail 库存明细
     * @return 库存明细集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailList(ErpInventoryDetail erpInventoryDetail);

    /**
     * 新增库存明细
     * 
     * @param erpInventoryDetail 库存明细
     * @return 结果
     */
    public int insertErpInventoryDetail(ErpInventoryDetail erpInventoryDetail);

    /**
     * 修改库存明细
     * 
     * @param erpInventoryDetail 库存明细
     * @return 结果
     */
    public int updateErpInventoryDetail(ErpInventoryDetail erpInventoryDetail);

    /**
     * 批量删除库存明细
     * 
     * @param ids 需要删除的库存明细主键集合
     * @return 结果
     */
    public int deleteErpInventoryDetailByIds(Long[] ids);

    /**
     * 删除库存明细信息
     * 
     * @param id 库存明细主键
     * @return 结果
     */
    public int deleteErpInventoryDetailById(Long id);

    /**
     * 查询指定位置的库存
     * 
     * @param warehouseId 仓库ID
     * @param shelfId 货架ID
     * @param locationId 货位ID
     * @return 库存明细集合
     */
    public List<ErpInventoryDetail> selectInventoryByLocation(Long warehouseId, Long shelfId, Long locationId);

    /**
     * 查询指定仓库的物料库存位置
     * 
     * @param warehouseId 仓库ID
     * @param materialId 物料ID
     * @return 库存明细集合
     */
    public List<ErpInventoryDetail> selectInventoryByWarehouse(Long warehouseId, Long materialId);

    /**
     * 查询即将过期的库存
     * 
     * @param days 天数
     * @return 库存明细集合
     */
    public List<ErpInventoryDetail> selectExpiringInventory(Integer days);

    /**
     * 查询批次库存位置
     * 
     * @param batchNo 批次号
     * @return 库存明细集合
     */
    public List<ErpInventoryDetail> selectInventoryByBatch(String batchNo);
}
