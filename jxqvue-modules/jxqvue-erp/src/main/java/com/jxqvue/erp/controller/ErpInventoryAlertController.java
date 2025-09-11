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
import com.jxqvue.erp.domain.ErpInventoryAlert;
import com.jxqvue.erp.service.IErpInventoryAlertService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 库存预警表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/alert")
public class ErpInventoryAlertController extends BaseController
{
    @Autowired
    private IErpInventoryAlertService erpInventoryAlertService;

    /**
     * 查询库存预警表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInventoryAlert erpInventoryAlert)
    {
        startPage();
        List<ErpInventoryAlert> list = erpInventoryAlertService.selectErpInventoryAlertList(erpInventoryAlert);
        return getDataTable(list);
    }

    /**
     * 导出库存预警表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:export')")
    @Log(title = "库存预警表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInventoryAlert erpInventoryAlert)
    {
        List<ErpInventoryAlert> list = erpInventoryAlertService.selectErpInventoryAlertList(erpInventoryAlert);
        ExcelUtil<ErpInventoryAlert> util = new ExcelUtil<ErpInventoryAlert>(ErpInventoryAlert.class);
        util.exportExcel(response, list, "库存预警表数据");
    }

    /**
     * 获取库存预警表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInventoryAlertService.selectErpInventoryAlertById(id));
    }

    /**
     * 新增库存预警表
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:add')")
    @Log(title = "库存预警表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventoryAlert erpInventoryAlert)
    {
        return toAjax(erpInventoryAlertService.insertErpInventoryAlert(erpInventoryAlert));
    }

    /**
     * 修改库存预警表
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:edit')")
    @Log(title = "库存预警表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventoryAlert erpInventoryAlert)
    {
        return toAjax(erpInventoryAlertService.updateErpInventoryAlert(erpInventoryAlert));
    }

    /**
     * 删除库存预警表
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:remove')")
    @Log(title = "库存预警表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInventoryAlertService.deleteErpInventoryAlertByIds(ids));
    }

    /**
     * 处理库存预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:handle')")
    @Log(title = "库存预警表", businessType = BusinessType.UPDATE)
    @PostMapping("/handle")
    public AjaxResult handle(@RequestBody ErpInventoryAlert erpInventoryAlert)
    {
        return toAjax(erpInventoryAlertService.handleErpInventoryAlert(erpInventoryAlert));
    }

    /**
     * 批量处理库存预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:handle')")
    @Log(title = "库存预警表", businessType = BusinessType.UPDATE)
    @PostMapping("/handleBatch")
    public AjaxResult handleBatch(@RequestBody Long[] ids)
    {
        return toAjax(erpInventoryAlertService.handleErpInventoryAlertByIds(ids));
    }

    /**
     * 查询库存预警表统计信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:list')")
    @GetMapping("/statistics")
    public AjaxResult statistics(ErpInventoryAlert erpInventoryAlert)
    {
        List<ErpInventoryAlert> list = erpInventoryAlertService.selectErpInventoryAlertStatistics(erpInventoryAlert);
        return success(list);
    }

    /**
     * 生成库存预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:add')")
    @Log(title = "库存预警表", businessType = BusinessType.INSERT)
    @PostMapping("/generate")
    public AjaxResult generate()
    {
        try {
            int result = erpInventoryAlertService.generateInventoryAlerts();
            return success("生成成功，共生成 " + result + " 条预警信息");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查询未处理的预警数量
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:list')")
    @GetMapping("/unhandledCount")
    public AjaxResult unhandledCount()
    {
        int count = erpInventoryAlertService.selectUnhandledAlertCount();
        return success(count);
    }

    /**
     * 查询高优先级预警数量
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:list')")
    @GetMapping("/highPriorityCount")
    public AjaxResult highPriorityCount()
    {
        int count = erpInventoryAlertService.selectHighPriorityAlertCount();
        return success(count);
    }

    /**
     * 检查库存不足预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:query')")
    @GetMapping("/checkLowStock")
    public AjaxResult checkLowStock(Long materialId, Long warehouseId)
    {
        boolean alert = erpInventoryAlertService.checkLowStockAlert(materialId, warehouseId);
        return success(alert);
    }

    /**
     * 检查库存过多预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:query')")
    @GetMapping("/checkHighStock")
    public AjaxResult checkHighStock(Long materialId, Long warehouseId)
    {
        boolean alert = erpInventoryAlertService.checkHighStockAlert(materialId, warehouseId);
        return success(alert);
    }

    /**
     * 检查即将过期预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:query')")
    @GetMapping("/checkExpiry")
    public AjaxResult checkExpiry(Long materialId, Long warehouseId, int days)
    {
        boolean alert = erpInventoryAlertService.checkExpiryAlert(materialId, warehouseId, days);
        return success(alert);
    }

    /**
     * 检查已过期预警
     */
    @PreAuthorize("@ss.hasPermi('inventory:alert:query')")
    @GetMapping("/checkExpired")
    public AjaxResult checkExpired(Long materialId, Long warehouseId)
    {
        boolean alert = erpInventoryAlertService.checkExpiredAlert(materialId, warehouseId);
        return success(alert);
    }
}
