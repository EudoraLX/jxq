package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInventoryDetail;

/**
 * 库存明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInventoryDetailMapper 
{
    /**
     * 查询库存明细表
     * 
     * @param id 库存明细表主键
     * @return 库存明细表
     */
    public ErpInventoryDetail selectErpInventoryDetailById(Long id);

    /**
     * 查询库存明细表列表
     * 
     * @param erpInventoryDetail 库存明细表
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailList(ErpInventoryDetail erpInventoryDetail);

    /**
     * 新增库存明细表
     * 
     * @param erpInventoryDetail 库存明细表
     * @return 结果
     */
    public int insertErpInventoryDetail(ErpInventoryDetail erpInventoryDetail);

    /**
     * 修改库存明细表
     * 
     * @param erpInventoryDetail 库存明细表
     * @return 结果
     */
    public int updateErpInventoryDetail(ErpInventoryDetail erpInventoryDetail);

    /**
     * 删除库存明细表
     * 
     * @param id 库存明细表主键
     * @return 结果
     */
    public int deleteErpInventoryDetailById(Long id);

    /**
     * 批量删除库存明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInventoryDetailByIds(Long[] ids);

    /**
     * 根据库存主表ID查询明细列表
     * 
     * @param inventoryId 库存主表ID
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailByInventoryId(Long inventoryId);

    /**
     * 根据物料ID和货位ID查询库存明细
     * 
     * @param materialId 物料ID
     * @param locationId 货位ID
     * @return 库存明细表
     */
    public ErpInventoryDetail selectErpInventoryDetailByMaterialAndLocation(Long materialId, Long locationId);

    /**
     * 根据批次号查询库存明细
     * 
     * @param batchNo 批次号
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailByBatchNo(String batchNo);

    /**
     * 根据序列号查询库存明细
     * 
     * @param serialNo 序列号
     * @return 库存明细表
     */
    public ErpInventoryDetail selectErpInventoryDetailBySerialNo(String serialNo);

    /**
     * 更新库存明细数量
     * 
     * @param erpInventoryDetail 库存明细表
     * @return 结果
     */
    public int updateErpInventoryDetailQuantity(ErpInventoryDetail erpInventoryDetail);

    /**
     * 查询即将过期的库存明细
     * 
     * @param days 天数
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailExpiringSoon(int days);

    /**
     * 根据物料ID和仓库ID查询库存明细列表
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailByMaterialAndWarehouse(Long materialId, Long warehouseId);

    /**
     * 根据物料ID、仓库ID和货位ID查询库存明细
     * 
     * @param materialId 物料ID
     * @param warehouseId 仓库ID
     * @param locationId 货位ID
     * @return 库存明细表集合
     */
    public List<ErpInventoryDetail> selectErpInventoryDetailByMaterialAndWarehouseAndLocation(Long materialId, Long warehouseId, Long locationId);
}
