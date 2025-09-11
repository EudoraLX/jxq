package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionOrderMaterialsMapper;
import com.jxqvue.erp.domain.ErpProductionOrderMaterials;
import com.jxqvue.erp.service.IErpProductionOrderMaterialsService;

/**
 * 生产订单物料Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@Service
public class ErpProductionOrderMaterialsServiceImpl implements IErpProductionOrderMaterialsService 
{
    @Autowired
    private ErpProductionOrderMaterialsMapper erpProductionOrderMaterialsMapper;

    /**
     * 查询生产订单物料
     * 
     * @param id 生产订单物料主键
     * @return 生产订单物料
     */
    @Override
    public ErpProductionOrderMaterials selectErpProductionOrderMaterialsById(Long id)
    {
        return erpProductionOrderMaterialsMapper.selectErpProductionOrderMaterialsById(id);
    }

    /**
     * 查询生产订单物料列表
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 生产订单物料
     */
    @Override
    public List<ErpProductionOrderMaterials> selectErpProductionOrderMaterialsList(ErpProductionOrderMaterials erpProductionOrderMaterials)
    {
        return erpProductionOrderMaterialsMapper.selectErpProductionOrderMaterialsList(erpProductionOrderMaterials);
    }

    /**
     * 根据订单ID查询生产订单物料列表
     * 
     * @param orderId 订单ID
     * @return 生产订单物料集合
     */
    @Override
    public List<ErpProductionOrderMaterials> selectErpProductionOrderMaterialsByOrderId(Long orderId)
    {
        return erpProductionOrderMaterialsMapper.selectErpProductionOrderMaterialsByOrderId(orderId);
    }

    /**
     * 新增生产订单物料
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 结果
     */
    @Override
    public int insertErpProductionOrderMaterials(ErpProductionOrderMaterials erpProductionOrderMaterials)
    {
        erpProductionOrderMaterials.setCreateTime(DateUtils.getNowDate());
        return erpProductionOrderMaterialsMapper.insertErpProductionOrderMaterials(erpProductionOrderMaterials);
    }

    /**
     * 修改生产订单物料
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 结果
     */
    @Override
    public int updateErpProductionOrderMaterials(ErpProductionOrderMaterials erpProductionOrderMaterials)
    {
        erpProductionOrderMaterials.setUpdateTime(DateUtils.getNowDate());
        return erpProductionOrderMaterialsMapper.updateErpProductionOrderMaterials(erpProductionOrderMaterials);
    }

    /**
     * 批量删除生产订单物料
     * 
     * @param ids 需要删除的生产订单物料主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderMaterialsByIds(Long[] ids)
    {
        return erpProductionOrderMaterialsMapper.deleteErpProductionOrderMaterialsByIds(ids);
    }

    /**
     * 删除生产订单物料信息
     * 
     * @param id 生产订单物料主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderMaterialsById(Long id)
    {
        return erpProductionOrderMaterialsMapper.deleteErpProductionOrderMaterialsById(id);
    }

    /**
     * 根据订单ID删除生产订单物料
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderMaterialsByOrderId(Long orderId)
    {
        return erpProductionOrderMaterialsMapper.deleteErpProductionOrderMaterialsByOrderId(orderId);
    }
}
