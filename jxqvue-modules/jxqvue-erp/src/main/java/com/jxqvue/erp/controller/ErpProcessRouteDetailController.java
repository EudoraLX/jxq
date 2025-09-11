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
import com.jxqvue.erp.domain.ErpProcessRouteDetail;
import com.jxqvue.erp.service.IErpProcessRouteDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 工艺路线明细Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/erp/processRouteDetail")
public class ErpProcessRouteDetailController extends BaseController
{
    @Autowired
    private IErpProcessRouteDetailService erpProcessRouteDetailService;

    /**
     * 查询工艺路线明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProcessRouteDetail erpProcessRouteDetail)
    {
        startPage();
        List<ErpProcessRouteDetail> list = erpProcessRouteDetailService.selectErpProcessRouteDetailList(erpProcessRouteDetail);
        return getDataTable(list);
    }

    /**
     * 根据工艺路线ID查询明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:list')")
    @GetMapping("/listByRouteId/{processRouteId}")
    public TableDataInfo listByRouteId(@PathVariable("processRouteId") Long processRouteId)
    {
        List<ErpProcessRouteDetail> list = erpProcessRouteDetailService.selectErpProcessRouteDetailByRouteId(processRouteId);
        return getDataTable(list);
    }



    /**
     * 导出工艺路线明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:export')")
    @Log(title = "工艺路线明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProcessRouteDetail erpProcessRouteDetail)
    {
        List<ErpProcessRouteDetail> list = erpProcessRouteDetailService.selectErpProcessRouteDetailList(erpProcessRouteDetail);
        ExcelUtil<ErpProcessRouteDetail> util = new ExcelUtil<ErpProcessRouteDetail>(ErpProcessRouteDetail.class);
        util.exportExcel(response, list, "工艺路线明细数据");
    }

    /**
     * 获取工艺路线明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProcessRouteDetailService.selectErpProcessRouteDetailById(id));
    }

    /**
     * 新增工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:add')")
    @Log(title = "工艺路线明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProcessRouteDetail erpProcessRouteDetail)
    {
        return toAjax(erpProcessRouteDetailService.insertErpProcessRouteDetail(erpProcessRouteDetail));
    }

    /**
     * 修改工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:edit')")
    @Log(title = "工艺路线明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProcessRouteDetail erpProcessRouteDetail)
    {
        return toAjax(erpProcessRouteDetailService.updateErpProcessRouteDetail(erpProcessRouteDetail));
    }

    /**
     * 删除工艺路线明细
     */
    @PreAuthorize("@ss.hasPermi('erp:processRouteDetail:remove')")
    @Log(title = "工艺路线明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProcessRouteDetailService.deleteErpProcessRouteDetailByIds(ids));
    }
}
