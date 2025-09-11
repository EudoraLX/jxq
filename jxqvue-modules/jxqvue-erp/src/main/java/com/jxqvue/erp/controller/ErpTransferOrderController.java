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
import com.jxqvue.erp.domain.ErpTransferOrder;
import com.jxqvue.erp.service.IErpTransferOrderService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 调拨单表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/transfer")
public class ErpTransferOrderController extends BaseController
{
    @Autowired
    private IErpTransferOrderService erpTransferOrderService;

    /**
     * 查询调拨单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpTransferOrder erpTransferOrder)
    {
        startPage();
        List<ErpTransferOrder> list = erpTransferOrderService.selectErpTransferOrderList(erpTransferOrder);
        return getDataTable(list);
    }

    /**
     * 导出调拨单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:export')")
    @Log(title = "调拨单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpTransferOrder erpTransferOrder)
    {
        List<ErpTransferOrder> list = erpTransferOrderService.selectErpTransferOrderList(erpTransferOrder);
        ExcelUtil<ErpTransferOrder> util = new ExcelUtil<ErpTransferOrder>(ErpTransferOrder.class);
        util.exportExcel(response, list, "调拨单表数据");
    }

    /**
     * 获取调拨单表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpTransferOrderService.selectErpTransferOrderById(id));
    }

    /**
     * 新增调拨单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:add')")
    @Log(title = "调拨单表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpTransferOrder erpTransferOrder)
    {
        return toAjax(erpTransferOrderService.insertErpTransferOrder(erpTransferOrder));
    }

    /**
     * 修改调拨单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:edit')")
    @Log(title = "调拨单表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpTransferOrder erpTransferOrder)
    {
        return toAjax(erpTransferOrderService.updateErpTransferOrder(erpTransferOrder));
    }

    /**
     * 删除调拨单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:remove')")
    @Log(title = "调拨单表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpTransferOrderService.deleteErpTransferOrderByIds(ids));
    }

    /**
     * 根据调拨单号查询调拨单
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:query')")
    @GetMapping("/getByTransferNo")
    public AjaxResult getByTransferNo(String transferNo)
    {
        ErpTransferOrder transferOrder = erpTransferOrderService.selectErpTransferOrderByTransferNo(transferNo);
        return success(transferOrder);
    }

    /**
     * 审核调拨单
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:approve')")
    @Log(title = "调拨单表", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody ErpTransferOrder erpTransferOrder)
    {
        return toAjax(erpTransferOrderService.approveErpTransferOrder(erpTransferOrder));
    }

    /**
     * 执行调拨单
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:execute')")
    @Log(title = "调拨单表", businessType = BusinessType.UPDATE)
    @PostMapping("/execute")
    public AjaxResult execute(@RequestBody ErpTransferOrder erpTransferOrder)
    {
        try {
            int result = erpTransferOrderService.executeErpTransferOrder(erpTransferOrder);
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 取消调拨单
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:edit')")
    @Log(title = "调拨单表", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody ErpTransferOrder erpTransferOrder)
    {
        return toAjax(erpTransferOrderService.cancelErpTransferOrder(erpTransferOrder));
    }

    /**
     * 生成调拨单号
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:add')")
    @GetMapping("/generateTransferNo")
    public AjaxResult generateTransferNo()
    {
        String transferNo = erpTransferOrderService.generateTransferNo();
        return success("生成成功：" + transferNo);
    }

    /**
     * 查询调拨单统计信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:list')")
    @GetMapping("/statistics")
    public AjaxResult statistics(ErpTransferOrder erpTransferOrder)
    {
        List<ErpTransferOrder> list = erpTransferOrderService.selectErpTransferOrderStatistics(erpTransferOrder);
        return success(list);
    }

    /**
     * 检查源库存是否充足
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:query')")
    @GetMapping("/checkFromInventory")
    public AjaxResult checkFromInventory(Long materialId, Long fromWarehouseId, java.math.BigDecimal quantity)
    {
        boolean sufficient = erpTransferOrderService.checkFromInventorySufficient(materialId, fromWarehouseId, quantity);
        return success(sufficient);
    }

    /**
     * 检查目标仓库是否可用
     */
    @PreAuthorize("@ss.hasPermi('inventory:transfer:query')")
    @GetMapping("/checkToWarehouse")
    public AjaxResult checkToWarehouse(Long toWarehouseId)
    {
        boolean available = erpTransferOrderService.checkToWarehouseAvailable(toWarehouseId);
        return success(available);
    }
}
