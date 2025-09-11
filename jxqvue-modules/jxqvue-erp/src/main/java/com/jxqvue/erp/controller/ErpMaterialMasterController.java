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
import com.jxqvue.erp.domain.ErpMaterialMaster;
import com.jxqvue.erp.service.IErpMaterialMasterService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 物料主数据Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/masterMaterial")
public class ErpMaterialMasterController extends BaseController
{
    @Autowired
    private IErpMaterialMasterService erpMaterialMasterService;

    /**
     * 查询物料主数据列表
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpMaterialMaster erpMaterialMaster)
    {
        startPage();
        List<ErpMaterialMaster> list = erpMaterialMasterService.selectErpMaterialMasterList(erpMaterialMaster);
        return getDataTable(list);
    }

    /**
     * 导出物料主数据列表
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:export')")
    @Log(title = "物料主数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpMaterialMaster erpMaterialMaster)
    {
        List<ErpMaterialMaster> list = erpMaterialMasterService.selectErpMaterialMasterList(erpMaterialMaster);
        ExcelUtil<ErpMaterialMaster> util = new ExcelUtil<ErpMaterialMaster>(ErpMaterialMaster.class);
        util.exportExcel(response, list, "物料主数据数据");
    }

    /**
     * 获取物料主数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpMaterialMasterService.selectErpMaterialMasterById(id));
    }

    /**
     * 新增物料主数据
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:add')")
    @Log(title = "物料主数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpMaterialMaster erpMaterialMaster)
    {
        return toAjax(erpMaterialMasterService.insertErpMaterialMaster(erpMaterialMaster));
    }

    /**
     * 修改物料主数据
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:edit')")
    @Log(title = "物料主数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpMaterialMaster erpMaterialMaster)
    {
        return toAjax(erpMaterialMasterService.updateErpMaterialMaster(erpMaterialMaster));
    }

    /**
     * 删除物料主数据
     */
    @PreAuthorize("@ss.hasPermi('erp:masterMaterial:remove')")
    @Log(title = "物料主数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpMaterialMasterService.deleteErpMaterialMasterByIds(ids));
    }

}
