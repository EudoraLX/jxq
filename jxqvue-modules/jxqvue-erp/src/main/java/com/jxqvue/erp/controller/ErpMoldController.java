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
import com.jxqvue.erp.domain.ErpMold;
import com.jxqvue.erp.service.IErpMoldService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 模具Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/mold")
public class ErpMoldController extends BaseController
{
    @Autowired
    private IErpMoldService erpMoldService;

    /**
     * 查询模具列表
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpMold erpMold)
    {
        startPage();
        List<ErpMold> list = erpMoldService.selectErpMoldList(erpMold);
        return getDataTable(list);
    }

    /**
     * 导出模具列表
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:export')")
    @Log(title = "模具", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpMold erpMold)
    {
        List<ErpMold> list = erpMoldService.selectErpMoldList(erpMold);
        ExcelUtil<ErpMold> util = new ExcelUtil<ErpMold>(ErpMold.class);
        util.exportExcel(response, list, "模具数据");
    }

    /**
     * 获取模具详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpMoldService.selectErpMoldById(id));
    }

    /**
     * 新增模具
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:add')")
    @Log(title = "模具", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpMold erpMold)
    {
        return toAjax(erpMoldService.insertErpMold(erpMold));
    }

    /**
     * 修改模具
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:edit')")
    @Log(title = "模具", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpMold erpMold)
    {
        return toAjax(erpMoldService.updateErpMold(erpMold));
    }

    /**
     * 删除模具
     */
    @PreAuthorize("@ss.hasPermi('erp:mold:remove')")
    @Log(title = "模具", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpMoldService.deleteErpMoldByIds(ids));
    }
}
