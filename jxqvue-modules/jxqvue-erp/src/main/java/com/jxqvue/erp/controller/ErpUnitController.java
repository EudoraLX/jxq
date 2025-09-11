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
import com.jxqvue.erp.domain.ErpUnit;
import com.jxqvue.erp.service.IErpUnitService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 计量单位Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/unit")
public class ErpUnitController extends BaseController
{
    @Autowired
    private IErpUnitService erpUnitService;

    /**
     * 查询计量单位列表
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpUnit erpUnit)
    {
        startPage();
        List<ErpUnit> list = erpUnitService.selectErpUnitList(erpUnit);
        return getDataTable(list);
    }

    /**
     * 导出计量单位列表
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:export')")
    @Log(title = "计量单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpUnit erpUnit)
    {
        List<ErpUnit> list = erpUnitService.selectErpUnitList(erpUnit);
        ExcelUtil<ErpUnit> util = new ExcelUtil<ErpUnit>(ErpUnit.class);
        util.exportExcel(response, list, "计量单位数据");
    }

    /**
     * 获取计量单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpUnitService.selectErpUnitById(id));
    }

    /**
     * 新增计量单位
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:add')")
    @Log(title = "计量单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpUnit erpUnit)
    {
        return toAjax(erpUnitService.insertErpUnit(erpUnit));
    }

    /**
     * 修改计量单位
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:edit')")
    @Log(title = "计量单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpUnit erpUnit)
    {
        return toAjax(erpUnitService.updateErpUnit(erpUnit));
    }

    /**
     * 删除计量单位
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:remove')")
    @Log(title = "计量单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpUnitService.deleteErpUnitByIds(ids));
    }

    /**
     * 修改计量单位状态
     */
    @PreAuthorize("@ss.hasPermi('erp:unit:edit')")
    @Log(title = "计量单位", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody ErpUnit erpUnit)
    {
        return toAjax(erpUnitService.updateErpUnit(erpUnit));
    }

}
