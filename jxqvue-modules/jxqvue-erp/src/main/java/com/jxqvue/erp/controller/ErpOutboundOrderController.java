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
import com.jxqvue.erp.domain.ErpOutboundOrder;
import com.jxqvue.erp.service.IErpOutboundOrderService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 出库单表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/outbound")
public class ErpOutboundOrderController extends BaseController
{
    @Autowired
    private IErpOutboundOrderService erpOutboundOrderService;

    /**
     * 查询出库单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpOutboundOrder erpOutboundOrder)
    {
        startPage();
        List<ErpOutboundOrder> list = erpOutboundOrderService.selectErpOutboundOrderList(erpOutboundOrder);
        return getDataTable(list);
    }

    /**
     * 导出出库单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:export')")
    @Log(title = "出库单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpOutboundOrder erpOutboundOrder)
    {
        List<ErpOutboundOrder> list = erpOutboundOrderService.selectErpOutboundOrderList(erpOutboundOrder);
        ExcelUtil<ErpOutboundOrder> util = new ExcelUtil<ErpOutboundOrder>(ErpOutboundOrder.class);
        util.exportExcel(response, list, "出库单表数据");
    }

    /**
     * 获取出库单表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpOutboundOrderService.selectErpOutboundOrderById(id));
    }

    /**
     * 新增出库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:add')")
    @Log(title = "出库单表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpOutboundOrder erpOutboundOrder)
    {
        return toAjax(erpOutboundOrderService.insertErpOutboundOrder(erpOutboundOrder));
    }

    /**
     * 修改出库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:edit')")
    @Log(title = "出库单表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpOutboundOrder erpOutboundOrder)
    {
        return toAjax(erpOutboundOrderService.updateErpOutboundOrder(erpOutboundOrder));
    }

    /**
     * 删除出库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:remove')")
    @Log(title = "出库单表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpOutboundOrderService.deleteErpOutboundOrderByIds(ids));
    }

    /**
     * 根据出库单号查询出库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:query')")
    @GetMapping("/getByOutboundNo")
    public AjaxResult getByOutboundNo(String outboundNo)
    {
        ErpOutboundOrder outboundOrder = erpOutboundOrderService.selectErpOutboundOrderByOutboundNo(outboundNo);
        return success(outboundOrder);
    }

    /**
     * 审核出库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:approve')")
    @Log(title = "出库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody ErpOutboundOrder erpOutboundOrder)
    {
        return toAjax(erpOutboundOrderService.approveErpOutboundOrder(erpOutboundOrder));
    }

    /**
     * 执行出库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:execute')")
    @Log(title = "出库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/execute")
    public AjaxResult execute(@RequestBody ErpOutboundOrder erpOutboundOrder)
    {
        try {
            int result = erpOutboundOrderService.executeErpOutboundOrder(erpOutboundOrder);
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 取消出库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:edit')")
    @Log(title = "出库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody ErpOutboundOrder erpOutboundOrder)
    {
        return toAjax(erpOutboundOrderService.cancelErpOutboundOrder(erpOutboundOrder));
    }

    /**
     * 生成出库单号
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:add')")
    @GetMapping("/generateOutboundNo")
    public AjaxResult generateOutboundNo()
    {
        String outboundNo = erpOutboundOrderService.generateOutboundNo();
        return success("生成成功：" + outboundNo);
    }

    /**
     * 查询出库单统计信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:list')")
    @GetMapping("/statistics")
    public AjaxResult statistics(ErpOutboundOrder erpOutboundOrder)
    {
        List<ErpOutboundOrder> list = erpOutboundOrderService.selectErpOutboundOrderStatistics(erpOutboundOrder);
        return success(list);
    }

    /**
     * 检查库存是否充足
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:query')")
    @GetMapping("/checkInventory")
    public AjaxResult checkInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity)
    {
        boolean sufficient = erpOutboundOrderService.checkInventorySufficient(materialId, warehouseId, quantity);
        return success(sufficient);
    }

    /**
     * 按FIFO策略分配库存
     */
    @PreAuthorize("@ss.hasPermi('inventory:outbound:query')")
    @GetMapping("/allocateInventory")
    public AjaxResult allocateInventory(Long materialId, Long warehouseId, java.math.BigDecimal quantity)
    {
        List<ErpOutboundOrder> allocationResult = erpOutboundOrderService.allocateInventoryByFIFO(materialId, warehouseId, quantity);
        return success(allocationResult);
    }
}
