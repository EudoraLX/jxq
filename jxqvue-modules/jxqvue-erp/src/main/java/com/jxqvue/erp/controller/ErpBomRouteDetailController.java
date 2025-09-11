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
import com.jxqvue.erp.domain.ErpBomRouteDetail;
import com.jxqvue.erp.service.IErpBomRouteDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * BOM工艺路线明细表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@RestController
@RequestMapping("/erp/bomRouteDetail")
public class ErpBomRouteDetailController extends BaseController
{
    @Autowired
    private IErpBomRouteDetailService erpBomRouteDetailService;

    /**
     * 查询BOM工艺路线明细表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpBomRouteDetail erpBomRouteDetail)
    {
        startPage();
        List<ErpBomRouteDetail> list = erpBomRouteDetailService.selectErpBomRouteDetailList(erpBomRouteDetail);
        return getDataTable(list);
    }

    /**
     * 导出BOM工艺路线明细表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:export')")
    @Log(title = "BOM工艺路线明细表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpBomRouteDetail erpBomRouteDetail)
    {
        List<ErpBomRouteDetail> list = erpBomRouteDetailService.selectErpBomRouteDetailList(erpBomRouteDetail);
        ExcelUtil<ErpBomRouteDetail> util = new ExcelUtil<ErpBomRouteDetail>(ErpBomRouteDetail.class);
        util.exportExcel(response, list, "BOM工艺路线明细表数据");
    }

    /**
     * 获取BOM工艺路线明细表详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpBomRouteDetailService.selectErpBomRouteDetailById(id));
    }

    /**
     * 新增BOM工艺路线明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:add')")
    @Log(title = "BOM工艺路线明细表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpBomRouteDetail erpBomRouteDetail)
    {
        return toAjax(erpBomRouteDetailService.insertErpBomRouteDetail(erpBomRouteDetail));
    }

    /**
     * 修改BOM工艺路线明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:edit')")
    @Log(title = "BOM工艺路线明细表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpBomRouteDetail erpBomRouteDetail)
    {
        return toAjax(erpBomRouteDetailService.updateErpBomRouteDetail(erpBomRouteDetail));
    }

    /**
     * 删除BOM工艺路线明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomRouteDetail:remove')")
    @Log(title = "BOM工艺路线明细表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpBomRouteDetailService.deleteErpBomRouteDetailByIds(ids));
    }
}
