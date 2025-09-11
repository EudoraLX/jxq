package com.jxqvue.erp.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.annotation.Log;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.common.enums.BusinessType;
import com.jxqvue.erp.domain.ErpMaterialCategory;
import com.jxqvue.erp.service.IErpMaterialCategoryService;
import com.jxqvue.common.utils.poi.ExcelUtil;

/**
 * 物料分类Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/materialCategory")
public class ErpMaterialCategoryController extends BaseController
{
    @Autowired
    private IErpMaterialCategoryService erpMaterialCategoryService;

    /**
     * 查询物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:list')")
    @GetMapping("/list")
    public AjaxResult list(ErpMaterialCategory erpMaterialCategory)
    {
        List<ErpMaterialCategory> list = erpMaterialCategoryService.selectErpMaterialCategoryList(erpMaterialCategory);
        return success(list);
    }

    /**
     * 导出物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:export')")
    @Log(title = "物料分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpMaterialCategory erpMaterialCategory)
    {
        List<ErpMaterialCategory> list = erpMaterialCategoryService.selectErpMaterialCategoryList(erpMaterialCategory);
        ExcelUtil<ErpMaterialCategory> util = new ExcelUtil<ErpMaterialCategory>(ErpMaterialCategory.class);
        util.exportExcel(response, list, "物料分类数据");
    }

    /**
     * 获取物料分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpMaterialCategoryService.selectErpMaterialCategoryById(id));
    }

    /**
     * 新增物料分类
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:add')")
    @Log(title = "物料分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpMaterialCategory erpMaterialCategory)
    {
        return toAjax(erpMaterialCategoryService.insertErpMaterialCategory(erpMaterialCategory));
    }

    /**
     * 修改物料分类
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:edit')")
    @Log(title = "物料分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpMaterialCategory erpMaterialCategory)
    {
        return toAjax(erpMaterialCategoryService.updateErpMaterialCategory(erpMaterialCategory));
    }

    /**
     * 删除物料分类
     */
    @PreAuthorize("@ss.hasPermi('erp:materialCategory:remove')")
    @Log(title = "物料分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpMaterialCategoryService.deleteErpMaterialCategoryByIds(ids));
    }

}
