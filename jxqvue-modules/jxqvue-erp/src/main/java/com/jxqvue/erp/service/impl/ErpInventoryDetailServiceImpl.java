package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpInventoryDetailMapper;
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.service.IErpInventoryDetailService;

/**
 * 库存明细Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class ErpInventoryDetailServiceImpl implements IErpInventoryDetailService 
{
    @Autowired
    private ErpInventoryDetailMapper erpInventoryDetailMapper;

    /**
     * 查询库存明细
     * 
     * @param id 库存明细主键
     * @return 库存明细
     */
    @Override
    public ErpInventoryDetail selectErpInventoryDetailById(Long id)
    {
        return erpInventoryDetailMapper.selectErpInventoryDetailById(id);
    }

    /**
     * 查询库存明细列表
     * 
     * @param erpInventoryDetail 库存明细
     * @return 库存明细
     */
    @Override
    public List<ErpInventoryDetail> selectErpInventoryDetailList(ErpInventoryDetail erpInventoryDetail)
    {
        return erpInventoryDetailMapper.selectErpInventoryDetailList(erpInventoryDetail);
    }

    /**
     * 新增库存明细
     * 
     * @param erpInventoryDetail 库存明细
     * @return 结果
     */
    @Override
    public int insertErpInventoryDetail(ErpInventoryDetail erpInventoryDetail)
    {
        erpInventoryDetail.setCreateTime(DateUtils.getNowDate());
        return erpInventoryDetailMapper.insertErpInventoryDetail(erpInventoryDetail);
    }

    /**
     * 修改库存明细
     * 
     * @param erpInventoryDetail 库存明细
     * @return 结果
     */
    @Override
    public int updateErpInventoryDetail(ErpInventoryDetail erpInventoryDetail)
    {
        erpInventoryDetail.setUpdateTime(DateUtils.getNowDate());
        return erpInventoryDetailMapper.updateErpInventoryDetail(erpInventoryDetail);
    }

    /**
     * 批量删除库存明细
     * 
     * @param ids 需要删除的库存明细主键
     * @return 结果
     */
    @Override
    public int deleteErpInventoryDetailByIds(Long[] ids)
    {
        return erpInventoryDetailMapper.deleteErpInventoryDetailByIds(ids);
    }

    /**
     * 删除库存明细信息
     * 
     * @param id 库存明细主键
     * @return 结果
     */
    @Override
    public int deleteErpInventoryDetailById(Long id)
    {
        return erpInventoryDetailMapper.deleteErpInventoryDetailById(id);
    }

    /**
     * 查询指定位置的库存
     * 
     * @param warehouseId 仓库ID
     * @param shelfId 货架ID
     * @param locationId 货位ID
     * @return 库存明细集合
     */
    @Override
    public List<ErpInventoryDetail> selectInventoryByLocation(Long warehouseId, Long shelfId, Long locationId)
    {
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setWarehouseId(warehouseId);
        query.setShelfId(shelfId);
        query.setLocationId(locationId);
        return erpInventoryDetailMapper.selectErpInventoryDetailList(query);
    }

    /**
     * 查询指定仓库的物料库存位置
     * 
     * @param warehouseId 仓库ID
     * @param materialId 物料ID
     * @return 库存明细集合
     */
    @Override
    public List<ErpInventoryDetail> selectInventoryByWarehouse(Long warehouseId, Long materialId)
    {
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setWarehouseId(warehouseId);
        query.setMaterialId(materialId);
        return erpInventoryDetailMapper.selectErpInventoryDetailList(query);
    }

    /**
     * 查询即将过期的库存
     * 
     * @param days 天数
     * @return 库存明细集合
     */
    @Override
    public List<ErpInventoryDetail> selectExpiringInventory(Integer days)
    {
        return erpInventoryDetailMapper.selectErpInventoryDetailExpiringSoon(days);
    }

    /**
     * 查询批次库存位置
     * 
     * @param batchNo 批次号
     * @return 库存明细集合
     */
    @Override
    public List<ErpInventoryDetail> selectInventoryByBatch(String batchNo)
    {
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setBatchNo(batchNo);
        return erpInventoryDetailMapper.selectErpInventoryDetailList(query);
    }
}
