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
import com.jxqvue.erp.domain.ErpInventoryDetail;
import com.jxqvue.erp.service.IErpInventoryDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 库存明细Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/inventory/inventoryDetail")
public class ErpInventoryDetailController extends BaseController
{
    @Autowired
    private IErpInventoryDetailService erpInventoryDetailService;

    /**
     * 查询库存明细列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInventoryDetail erpInventoryDetail)
    {
        startPage();
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectErpInventoryDetailList(erpInventoryDetail);
        return getDataTable(list);
    }

    /**
     * 导出库存明细列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:export')")
    @Log(title = "库存明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInventoryDetail erpInventoryDetail)
    {
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectErpInventoryDetailList(erpInventoryDetail);
        ExcelUtil<ErpInventoryDetail> util = new ExcelUtil<ErpInventoryDetail>(ErpInventoryDetail.class);
        util.exportExcel(response, list, "库存明细数据");
    }

    /**
     * 获取库存明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInventoryDetailService.selectErpInventoryDetailById(id));
    }

    /**
     * 新增库存明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:add')")
    @Log(title = "库存明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventoryDetail erpInventoryDetail)
    {
        return toAjax(erpInventoryDetailService.insertErpInventoryDetail(erpInventoryDetail));
    }

    /**
     * 修改库存明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:edit')")
    @Log(title = "库存明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventoryDetail erpInventoryDetail)
    {
        return toAjax(erpInventoryDetailService.updateErpInventoryDetail(erpInventoryDetail));
    }

    /**
     * 删除库存明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:remove')")
    @Log(title = "库存明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInventoryDetailService.deleteErpInventoryDetailByIds(ids));
    }

    /**
     * 查询指定位置的库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/location")
    public TableDataInfo getInventoryByLocation(Long warehouseId, Long shelfId, Long locationId)
    {
        startPage();
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setWarehouseId(warehouseId);
        query.setShelfId(shelfId);
        query.setLocationId(locationId);
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectErpInventoryDetailList(query);
        return getDataTable(list);
    }

    /**
     * 查询指定仓库的物料库存位置
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/warehouse")
    public TableDataInfo getInventoryByWarehouse(Long warehouseId, Long materialId)
    {
        startPage();
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setWarehouseId(warehouseId);
        query.setMaterialId(materialId);
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectErpInventoryDetailList(query);
        return getDataTable(list);
    }

    /**
     * 查询即将过期的库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/expiring")
    public TableDataInfo getExpiringInventory(Integer days)
    {
        startPage();
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectExpiringInventory(days);
        return getDataTable(list);
    }

    /**
     * 查询批次库存位置
     */
    @PreAuthorize("@ss.hasPermi('inventory:inventory:list')")
    @GetMapping("/batch")
    public TableDataInfo getInventoryByBatch(String batchNo)
    {
        startPage();
        ErpInventoryDetail query = new ErpInventoryDetail();
        query.setBatchNo(batchNo);
        List<ErpInventoryDetail> list = erpInventoryDetailService.selectErpInventoryDetailList(query);
        return getDataTable(list);
    }
}
