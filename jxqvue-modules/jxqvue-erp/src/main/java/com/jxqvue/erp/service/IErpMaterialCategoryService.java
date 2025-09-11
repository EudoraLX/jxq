package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpMaterialCategory;

/**
 * 物料分类Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpMaterialCategoryService 
{
    /**
     * 查询物料分类
     * 
     * @param id 物料分类主键
     * @return 物料分类
     */
    public ErpMaterialCategory selectErpMaterialCategoryById(Long id);

    /**
     * 查询物料分类列表
     * 
     * @param erpMaterialCategory 物料分类
     * @return 物料分类集合
     */
    public List<ErpMaterialCategory> selectErpMaterialCategoryList(ErpMaterialCategory erpMaterialCategory);

    /**
     * 新增物料分类
     * 
     * @param erpMaterialCategory 物料分类
     * @return 结果
     */
    public int insertErpMaterialCategory(ErpMaterialCategory erpMaterialCategory);

    /**
     * 修改物料分类
     * 
     * @param erpMaterialCategory 物料分类
     * @return 结果
     */
    public int updateErpMaterialCategory(ErpMaterialCategory erpMaterialCategory);

    /**
     * 批量删除物料分类
     * 
     * @param ids 需要删除的物料分类主键集合
     * @return 结果
     */
    public int deleteErpMaterialCategoryByIds(Long[] ids);

    /**
     * 删除物料分类信息
     * 
     * @param id 物料分类主键
     * @return 结果
     */
    public int deleteErpMaterialCategoryById(Long id);
}
