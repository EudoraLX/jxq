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
import com.jxqvue.erp.domain.ErpProductionCalendar;
import com.jxqvue.erp.service.IErpProductionCalendarService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 生产日历Controller
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@RestController
@RequestMapping("/erp/productionCalendar")
public class ErpProductionCalendarController extends BaseController
{
    @Autowired
    private IErpProductionCalendarService erpProductionCalendarService;

    /**
     * 查询生产日历列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionCalendar erpProductionCalendar)
    {
        startPage();
        List<ErpProductionCalendar> list = erpProductionCalendarService.selectErpProductionCalendarList(erpProductionCalendar);
        return getDataTable(list);
    }

    /**
     * 导出生产日历列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:export')")
    @Log(title = "生产日历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionCalendar erpProductionCalendar)
    {
        List<ErpProductionCalendar> list = erpProductionCalendarService.selectErpProductionCalendarList(erpProductionCalendar);
        ExcelUtil<ErpProductionCalendar> util = new ExcelUtil<ErpProductionCalendar>(ErpProductionCalendar.class);
        util.exportExcel(response, list, "生产日历数据");
    }

    /**
     * 获取生产日历详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProductionCalendarService.selectErpProductionCalendarById(id));
    }

    /**
     * 新增生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:add')")
    @Log(title = "生产日历", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionCalendar erpProductionCalendar)
    {
        return toAjax(erpProductionCalendarService.insertErpProductionCalendar(erpProductionCalendar));
    }

    /**
     * 修改生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:edit')")
    @Log(title = "生产日历", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionCalendar erpProductionCalendar)
    {
        return toAjax(erpProductionCalendarService.updateErpProductionCalendar(erpProductionCalendar));
    }

    /**
     * 删除生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:remove')")
    @Log(title = "生产日历", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProductionCalendarService.deleteErpProductionCalendarByIds(ids));
    }

    /**
     * 根据生产线ID查询生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:query')")
    @GetMapping("/line/{productionLineId}")
    public AjaxResult getByProductionLineId(@PathVariable("productionLineId") Long productionLineId)
    {
        return success(erpProductionCalendarService.selectErpProductionCalendarByLineId(productionLineId));
    }

    /**
     * 根据日期范围查询生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:query')")
    @GetMapping("/range")
    public AjaxResult getByDateRange(String startDate, String endDate)
    {
        List<ErpProductionCalendar> list = erpProductionCalendarService.selectErpProductionCalendarByDateRange(startDate, endDate);
        return success(list);
    }

    /**
     * 生成生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:add')")
    @Log(title = "生成生产日历", businessType = BusinessType.INSERT)
    @PostMapping("/generate")
    public AjaxResult generate(@RequestBody ErpProductionCalendar erpProductionCalendar)
    {
        return toAjax(erpProductionCalendarService.generateProductionCalendar(erpProductionCalendar));
    }

    /**
     * 复制生产日历
     */
    @PreAuthorize("@ss.hasPermi('erp:productionCalendar:add')")
    @Log(title = "复制生产日历", businessType = BusinessType.INSERT)
    @PostMapping("/copy/{sourceCalendarId}")
    public AjaxResult copy(@PathVariable("sourceCalendarId") Long sourceCalendarId, @RequestBody ErpProductionCalendar targetCalendar)
    {
        return toAjax(erpProductionCalendarService.copyProductionCalendar(sourceCalendarId, targetCalendar));
    }
}