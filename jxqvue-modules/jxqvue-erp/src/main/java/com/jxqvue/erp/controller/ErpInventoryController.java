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
import com.jxqvue.erp.domain.ErpInventory;
import com.jxqvue.erp.service.IErpInventoryService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 库存主表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/inventory")
public class ErpInventoryController extends BaseController
{
    @Autowired
    private IErpInventoryService erpInventoryService;

    /**
     * 查询库存主表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInventory erpInventory)
    {
        startPage();
        List<ErpInventory> list = erpInventoryService.selectErpInventoryList(erpInventory);
        return getDataTable(list);
    }

    /**
     * 导出库存主表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:export')")
    @Log(title = "库存主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInventory erpInventory)
    {
        List<ErpInventory> list = erpInventoryService.selectErpInventoryList(erpInventory);
        ExcelUtil<ErpInventory> util = new ExcelUtil<ErpInventory>(ErpInventory.class);
        util.exportExcel(response, list, "库存主表数据");
    }

    /**
     * 获取库存主表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInventoryService.selectErpInventoryById(id));
    }

    /**
     * 新增库存主表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:add')")
    @Log(title = "库存主表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventory erpInventory)
    {
        return toAjax(erpInventoryService.insertErpInventory(erpInventory));
    }

    /**
     * 修改库存主表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventory erpInventory)
    {
        return toAjax(erpInventoryService.updateErpInventory(erpInventory));
    }

    /**
     * 删除库存主表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:remove')")
    @Log(title = "库存主表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInventoryService.deleteErpInventoryByIds(ids));
    }

    /**
     * 根据物料ID和仓库ID查询库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:query')")
    @GetMapping("/getByMaterialAndWarehouse")
    public AjaxResult getByMaterialAndWarehouse(Long materialId, Long warehouseId)
    {
        ErpInventory inventory = erpInventoryService.selectErpInventoryByMaterialAndWarehouse(materialId, warehouseId);
        return success(inventory);
    }

    /**
     * 查询库存预警列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/alertList")
    public TableDataInfo alertList(ErpInventory erpInventory)
    {
        startPage();
        List<ErpInventory> list = erpInventoryService.selectErpInventoryAlertList(erpInventory);
        return getDataTable(list);
    }

    /**
     * 增加库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/increase")
    public AjaxResult increaseInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.increaseInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity(), 
                erpInventory.getUnitCost()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 减少库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/decrease")
    public AjaxResult decreaseInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.decreaseInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 分配库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/allocate")
    public AjaxResult allocateInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.allocateInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 释放库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/release")
    public AjaxResult releaseInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.releaseInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 冻结库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/freeze")
    public AjaxResult freezeInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.freezeInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 解冻库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存主表", businessType = BusinessType.UPDATE)
    @PostMapping("/unfreeze")
    public AjaxResult unfreezeInventory(@RequestBody ErpInventory erpInventory)
    {
        try {
            int result = erpInventoryService.unfreezeInventory(
                erpInventory.getMaterialId(), 
                erpInventory.getWarehouseId(), 
                erpInventory.getTotalQuantity()
            );
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }
}
