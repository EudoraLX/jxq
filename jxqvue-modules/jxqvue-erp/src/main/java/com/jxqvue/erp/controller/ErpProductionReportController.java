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
import com.jxqvue.erp.domain.ErpProductionReport;
import com.jxqvue.erp.service.IErpProductionReportService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 生产报工单Controller
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/erp/productionReport")
public class ErpProductionReportController extends BaseController
{
    @Autowired
    private IErpProductionReportService erpProductionReportService;

    /**
     * 查询生产报工单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionReport erpProductionReport)
    {
        startPage();
        List<ErpProductionReport> list = erpProductionReportService.selectErpProductionReportList(erpProductionReport);
        return getDataTable(list);
    }

    /**
     * 导出生产报工单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:export')")
    @Log(title = "生产报工单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionReport erpProductionReport)
    {
        List<ErpProductionReport> list = erpProductionReportService.selectErpProductionReportList(erpProductionReport);
        ExcelUtil<ErpProductionReport> util = new ExcelUtil<ErpProductionReport>(ErpProductionReport.class);
        util.exportExcel(response, list, "生产报工单数据");
    }

    /**
     * 获取生产报工单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProductionReportService.selectErpProductionReportById(id));
    }

    /**
     * 新增生产报工单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:add')")
    @Log(title = "生产报工单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionReport erpProductionReport)
    {
        return toAjax(erpProductionReportService.insertErpProductionReport(erpProductionReport));
    }

    /**
     * 修改生产报工单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:edit')")
    @Log(title = "生产报工单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionReport erpProductionReport)
    {
        return toAjax(erpProductionReportService.updateErpProductionReport(erpProductionReport));
    }

    /**
     * 删除生产报工单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionReport:remove')")
    @Log(title = "生产报工单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProductionReportService.deleteErpProductionReportByIds(ids));
    }
}
