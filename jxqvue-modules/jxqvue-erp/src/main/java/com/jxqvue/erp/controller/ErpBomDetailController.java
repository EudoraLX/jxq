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
import com.jxqvue.erp.domain.ErpBomDetail;
import com.jxqvue.erp.service.IErpBomDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * BOM明细表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@RestController
@RequestMapping("/erp/bomDetail")
public class ErpBomDetailController extends BaseController
{
    @Autowired
    private IErpBomDetailService erpBomDetailService;

    /**
     * 查询BOM明细表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpBomDetail erpBomDetail)
    {
        startPage();
        List<ErpBomDetail> list = erpBomDetailService.selectErpBomDetailList(erpBomDetail);
        return getDataTable(list);
    }

    /**
     * 导出BOM明细表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:export')")
    @Log(title = "BOM明细表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpBomDetail erpBomDetail)
    {
        List<ErpBomDetail> list = erpBomDetailService.selectErpBomDetailList(erpBomDetail);
        ExcelUtil<ErpBomDetail> util = new ExcelUtil<ErpBomDetail>(ErpBomDetail.class);
        util.exportExcel(response, list, "BOM明细表数据");
    }

    /**
     * 获取BOM明细表详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpBomDetailService.selectErpBomDetailById(id));
    }

    /**
     * 新增BOM明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:add')")
    @Log(title = "BOM明细表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpBomDetail erpBomDetail)
    {
        return toAjax(erpBomDetailService.insertErpBomDetail(erpBomDetail));
    }

    /**
     * 修改BOM明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:edit')")
    @Log(title = "BOM明细表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpBomDetail erpBomDetail)
    {
        return toAjax(erpBomDetailService.updateErpBomDetail(erpBomDetail));
    }

    /**
     * 删除BOM明细表
     */
    @PreAuthorize("@ss.hasPermi('erp:bomDetail:remove')")
    @Log(title = "BOM明细表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpBomDetailService.deleteErpBomDetailByIds(ids));
    }
}
