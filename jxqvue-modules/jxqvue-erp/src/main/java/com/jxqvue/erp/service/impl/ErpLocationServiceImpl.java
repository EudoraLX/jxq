package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpLocationMapper;
import com.jxqvue.erp.domain.ErpLocation;
import com.jxqvue.erp.service.IErpLocationService;

/**
 * 货位Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Service
public class ErpLocationServiceImpl implements IErpLocationService 
{
    @Autowired
    private ErpLocationMapper erpLocationMapper;

    /**
     * 查询货位
     * 
     * @param id 货位主键
     * @return 货位
     */
    @Override
    public ErpLocation selectErpLocationById(Long id)
    {
        return erpLocationMapper.selectErpLocationById(id);
    }

    /**
     * 查询货位列表
     * 
     * @param erpLocation 货位
     * @return 货位
     */
    @Override
    public List<ErpLocation> selectErpLocationList(ErpLocation erpLocation)
    {
        return erpLocationMapper.selectErpLocationList(erpLocation);
    }

    /**
     * 新增货位
     * 
     * @param erpLocation 货位
     * @return 结果
     */
    @Override
    public int insertErpLocation(ErpLocation erpLocation)
    {
        erpLocation.setCreateTime(DateUtils.getNowDate());
        return erpLocationMapper.insertErpLocation(erpLocation);
    }

    /**
     * 修改货位
     * 
     * @param erpLocation 货位
     * @return 结果
     */
    @Override
    public int updateErpLocation(ErpLocation erpLocation)
    {
        erpLocation.setUpdateTime(DateUtils.getNowDate());
        return erpLocationMapper.updateErpLocation(erpLocation);
    }

    /**
     * 批量删除货位
     * 
     * @param ids 需要删除的货位主键
     * @return 结果
     */
    @Override
    public int deleteErpLocationByIds(Long[] ids)
    {
        return erpLocationMapper.deleteErpLocationByIds(ids);
    }

    /**
     * 删除货位信息
     * 
     * @param id 货位主键
     * @return 结果
     */
    @Override
    public int deleteErpLocationById(Long id)
    {
        return erpLocationMapper.deleteErpLocationById(id);
    }

    /**
     * 根据货架ID查询货位列表
     * 
     * @param shelfId 货架ID
     * @return 货位集合
     */
    @Override
    public List<ErpLocation> selectErpLocationListByShelfId(Long shelfId)
    {
        return erpLocationMapper.selectErpLocationListByShelfId(shelfId);
    }

    /**
     * 根据仓库ID查询货位列表
     * 
     * @param warehouseId 仓库ID
     * @return 货位集合
     */
    @Override
    public List<ErpLocation> selectErpLocationListByWarehouseId(Long warehouseId)
    {
        return erpLocationMapper.selectErpLocationListByWarehouseId(warehouseId);
    }

    /**
     * 获取可用的货位列表（已启用且空闲）
     * 
     * @return 货位集合
     */
    @Override
    public List<ErpLocation> getAvailableLocations()
    {
        return erpLocationMapper.selectAvailableLocations();
    }

    /**
     * 获取空闲货位列表
     * 
     * @return 货位集合
     */
    @Override
    public List<ErpLocation> getFreeLocations()
    {
        return erpLocationMapper.selectFreeLocations();
    }
}
