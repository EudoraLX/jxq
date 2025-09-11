package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpMaterialCategoryMapper;
import com.jxqvue.erp.domain.ErpMaterialCategory;
import com.jxqvue.erp.service.IErpMaterialCategoryService;

/**
 * 物料分类Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpMaterialCategoryServiceImpl implements IErpMaterialCategoryService 
{
    @Autowired
    private ErpMaterialCategoryMapper erpMaterialCategoryMapper;

    /**
     * 查询物料分类
     * 
     * @param id 物料分类主键
     * @return 物料分类
     */
    @Override
    public ErpMaterialCategory selectErpMaterialCategoryById(Long id)
    {
        return erpMaterialCategoryMapper.selectErpMaterialCategoryById(id);
    }

    /**
     * 查询物料分类列表
     * 
     * @param erpMaterialCategory 物料分类
     * @return 物料分类
     */
    @Override
    public List<ErpMaterialCategory> selectErpMaterialCategoryList(ErpMaterialCategory erpMaterialCategory)
    {
        return erpMaterialCategoryMapper.selectErpMaterialCategoryList(erpMaterialCategory);
    }

    /**
     * 新增物料分类
     * 
     * @param erpMaterialCategory 物料分类
     * @return 结果
     */
    @Override
    public int insertErpMaterialCategory(ErpMaterialCategory erpMaterialCategory)
    {
        erpMaterialCategory.setCreateTime(DateUtils.getNowDate());
        return erpMaterialCategoryMapper.insertErpMaterialCategory(erpMaterialCategory);
    }

    /**
     * 修改物料分类
     * 
     * @param erpMaterialCategory 物料分类
     * @return 结果
     */
    @Override
    public int updateErpMaterialCategory(ErpMaterialCategory erpMaterialCategory)
    {
        erpMaterialCategory.setUpdateTime(DateUtils.getNowDate());
        return erpMaterialCategoryMapper.updateErpMaterialCategory(erpMaterialCategory);
    }

    /**
     * 批量删除物料分类
     * 
     * @param ids 需要删除的物料分类主键
     * @return 结果
     */
    @Override
    public int deleteErpMaterialCategoryByIds(Long[] ids)
    {
        return erpMaterialCategoryMapper.deleteErpMaterialCategoryByIds(ids);
    }

    /**
     * 删除物料分类信息
     * 
     * @param id 物料分类主键
     * @return 结果
     */
    @Override
    public int deleteErpMaterialCategoryById(Long id)
    {
        return erpMaterialCategoryMapper.deleteErpMaterialCategoryById(id);
    }
}
