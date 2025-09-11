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
import com.jxqvue.erp.domain.ErpWorkCenter;
import com.jxqvue.erp.service.IErpWorkCenterService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 工作中心Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/workCenter")
public class ErpWorkCenterController extends BaseController
{
    @Autowired
    private IErpWorkCenterService erpWorkCenterService;

    /**
     * 查询工作中心列表
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpWorkCenter erpWorkCenter)
    {
        startPage();
        List<ErpWorkCenter> list = erpWorkCenterService.selectErpWorkCenterList(erpWorkCenter);
        return getDataTable(list);
    }

    /**
     * 导出工作中心列表
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:export')")
    @Log(title = "工作中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpWorkCenter erpWorkCenter)
    {
        List<ErpWorkCenter> list = erpWorkCenterService.selectErpWorkCenterList(erpWorkCenter);
        ExcelUtil<ErpWorkCenter> util = new ExcelUtil<ErpWorkCenter>(ErpWorkCenter.class);
        util.exportExcel(response, list, "工作中心数据");
    }

    /**
     * 获取工作中心详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpWorkCenterService.selectErpWorkCenterById(id));
    }

    /**
     * 新增工作中心
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:add')")
    @Log(title = "工作中心", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpWorkCenter erpWorkCenter)
    {
        return toAjax(erpWorkCenterService.insertErpWorkCenter(erpWorkCenter));
    }

    /**
     * 修改工作中心
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:edit')")
    @Log(title = "工作中心", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpWorkCenter erpWorkCenter)
    {
        return toAjax(erpWorkCenterService.updateErpWorkCenter(erpWorkCenter));
    }

    /**
     * 删除工作中心
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:remove')")
    @Log(title = "工作中心", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpWorkCenterService.deleteErpWorkCenterByIds(ids));
    }

    /**
     * 获取工作中心关联的设备和模具
     */
    @PreAuthorize("@ss.hasPermi('erp:workCenter:query')")
    @GetMapping("/{id}/equipmentAndMold")
    public AjaxResult getEquipmentAndMold(@PathVariable("id") Long id)
    {
        return success(erpWorkCenterService.getWorkCenterEquipmentAndMold(id));
    }
}
