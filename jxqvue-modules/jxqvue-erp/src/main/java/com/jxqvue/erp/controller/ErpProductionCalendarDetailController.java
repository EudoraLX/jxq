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
import com.jxqvue.erp.domain.ErpProductionCalendarDetail;
import com.jxqvue.erp.service.IErpProductionCalendarDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 生产日历明细Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/erp/productioncalendardetail")
public class ErpProductionCalendarDetailController extends BaseController
{
    @Autowired
    private IErpProductionCalendarDetailService erpProductionCalendarDetailService;

    /**
     * 查询生产日历明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        startPage();
        List<ErpProductionCalendarDetail> list = erpProductionCalendarDetailService.selectErpProductionCalendarDetailList(erpProductionCalendarDetail);
        return getDataTable(list);
    }

    /**
     * 导出生产日历明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:export')")
    @Log(title = "生产日历明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        List<ErpProductionCalendarDetail> list = erpProductionCalendarDetailService.selectErpProductionCalendarDetailList(erpProductionCalendarDetail);
        ExcelUtil<ErpProductionCalendarDetail> util = new ExcelUtil<ErpProductionCalendarDetail>(ErpProductionCalendarDetail.class);
        util.exportExcel(response, list, "生产日历明细数据");
    }

    /**
     * 获取生产日历明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(erpProductionCalendarDetailService.selectErpProductionCalendarDetailByDetailId(detailId));
    }

    /**
     * 新增生产日历明细
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:add')")
    @Log(title = "生产日历明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        return toAjax(erpProductionCalendarDetailService.insertErpProductionCalendarDetail(erpProductionCalendarDetail));
    }

    /**
     * 修改生产日历明细
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:edit')")
    @Log(title = "生产日历明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        return toAjax(erpProductionCalendarDetailService.updateErpProductionCalendarDetail(erpProductionCalendarDetail));
    }

    /**
     * 删除生产日历明细
     */
    @PreAuthorize("@ss.hasPermi('erp:productioncalendardetail:remove')")
    @Log(title = "生产日历明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(erpProductionCalendarDetailService.deleteErpProductionCalendarDetailByDetailIds(detailIds));
    }
}
