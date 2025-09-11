package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpLocation;

/**
 * 货位Service接口
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public interface IErpLocationService 
{
    /**
     * 查询货位
     * 
     * @param id 货位主键
     * @return 货位
     */
    public ErpLocation selectErpLocationById(Long id);

    /**
     * 查询货位列表
     * 
     * @param erpLocation 货位
     * @return 货位集合
     */
    public List<ErpLocation> selectErpLocationList(ErpLocation erpLocation);

    /**
     * 新增货位
     * 
     * @param erpLocation 货位
     * @return 结果
     */
    public int insertErpLocation(ErpLocation erpLocation);

    /**
     * 修改货位
     * 
     * @param erpLocation 货位
     * @return 结果
     */
    public int updateErpLocation(ErpLocation erpLocation);

    /**
     * 批量删除货位
     * 
     * @param ids 需要删除的货位主键集合
     * @return 结果
     */
    public int deleteErpLocationByIds(Long[] ids);

    /**
     * 删除货位信息
     * 
     * @param id 货位主键
     * @return 结果
     */
    public int deleteErpLocationById(Long id);

    /**
     * 根据货架ID查询货位列表
     * 
     * @param shelfId 货架ID
     * @return 货位集合
     */
    public List<ErpLocation> selectErpLocationListByShelfId(Long shelfId);

    /**
     * 根据仓库ID查询货位列表
     * 
     * @param warehouseId 仓库ID
     * @return 货位集合
     */
    public List<ErpLocation> selectErpLocationListByWarehouseId(Long warehouseId);

    /**
     * 获取可用的货位列表（已启用且空闲）
     * 
     * @return 货位集合
     */
    public List<ErpLocation> getAvailableLocations();

    /**
     * 获取空闲货位列表
     * 
     * @return 货位集合
     */
    public List<ErpLocation> getFreeLocations();
}
