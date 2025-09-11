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
import com.jxqvue.erp.domain.ErpEquipment;
import com.jxqvue.erp.service.IErpEquipmentService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 设备Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/equipment")
public class ErpEquipmentController extends BaseController
{
    @Autowired
    private IErpEquipmentService erpEquipmentService;

    /**
     * 查询设备列表
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpEquipment erpEquipment)
    {
        startPage();
        List<ErpEquipment> list = erpEquipmentService.selectErpEquipmentList(erpEquipment);
        return getDataTable(list);
    }

    /**
     * 导出设备列表
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:export')")
    @Log(title = "设备", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpEquipment erpEquipment)
    {
        List<ErpEquipment> list = erpEquipmentService.selectErpEquipmentList(erpEquipment);
        ExcelUtil<ErpEquipment> util = new ExcelUtil<ErpEquipment>(ErpEquipment.class);
        util.exportExcel(response, list, "设备数据");
    }

    /**
     * 获取设备详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpEquipmentService.selectErpEquipmentById(id));
    }

    /**
     * 新增设备
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:add')")
    @Log(title = "设备", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpEquipment erpEquipment)
    {
        return toAjax(erpEquipmentService.insertErpEquipment(erpEquipment));
    }

    /**
     * 修改设备
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:edit')")
    @Log(title = "设备", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpEquipment erpEquipment)
    {
        return toAjax(erpEquipmentService.updateErpEquipment(erpEquipment));
    }

    /**
     * 删除设备
     */
    @PreAuthorize("@ss.hasPermi('erp:equipment:remove')")
    @Log(title = "设备", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpEquipmentService.deleteErpEquipmentByIds(ids));
    }
}
