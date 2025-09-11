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
import com.jxqvue.erp.domain.ErpProcessRoute;
import com.jxqvue.erp.service.IErpProcessRouteService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 工艺路线主表Controller
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/erp/processRoute")
public class ErpProcessRouteController extends BaseController
{
    @Autowired
    private IErpProcessRouteService erpProcessRouteService;

    /**
     * 查询工艺路线主表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProcessRoute erpProcessRoute)
    {
        startPage();
        List<ErpProcessRoute> list = erpProcessRouteService.selectErpProcessRouteList(erpProcessRoute);
        return getDataTable(list);
    }

    /**
     * 导出工艺路线主表列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:export')")
    @Log(title = "工艺路线主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProcessRoute erpProcessRoute)
    {
        List<ErpProcessRoute> list = erpProcessRouteService.selectErpProcessRouteList(erpProcessRoute);
        ExcelUtil<ErpProcessRoute> util = new ExcelUtil<ErpProcessRoute>(ErpProcessRoute.class);
        util.exportExcel(response, list, "工艺路线主表数据");
    }

    /**
     * 获取工艺路线主表详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProcessRouteService.selectErpProcessRouteById(id));
    }

    /**
     * 新增工艺路线主表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:add')")
    @Log(title = "工艺路线主表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProcessRoute erpProcessRoute)
    {
        return toAjax(erpProcessRouteService.insertErpProcessRoute(erpProcessRoute));
    }

    /**
     * 修改工艺路线主表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:edit')")
    @Log(title = "工艺路线主表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProcessRoute erpProcessRoute)
    {
        return toAjax(erpProcessRouteService.updateErpProcessRoute(erpProcessRoute));
    }

    /**
     * 删除工艺路线主表
     */
    @PreAuthorize("@ss.hasPermi('erp:processRoute:remove')")
    @Log(title = "工艺路线主表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProcessRouteService.deleteErpProcessRouteByIds(ids));
    }
}
