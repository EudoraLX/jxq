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
import com.jxqvue.erp.domain.ErpProductionOrder;
import com.jxqvue.erp.service.IErpProductionOrderService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 生产订单Controller
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@RestController
@RequestMapping("/erp/productionOrder")
public class ErpProductionOrderController extends BaseController
{
    @Autowired
    private IErpProductionOrderService erpProductionOrderService;

    /**
     * 查询生产订单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionOrder erpProductionOrder)
    {
        startPage();
        List<ErpProductionOrder> list = erpProductionOrderService.selectErpProductionOrderList(erpProductionOrder);
        return getDataTable(list);
    }

    /**
     * 导出生产订单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:export')")
    @Log(title = "生产订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionOrder erpProductionOrder)
    {
        List<ErpProductionOrder> list = erpProductionOrderService.selectErpProductionOrderList(erpProductionOrder);
        ExcelUtil<ErpProductionOrder> util = new ExcelUtil<ErpProductionOrder>(ErpProductionOrder.class);
        util.exportExcel(response, list, "生产订单数据");
    }

    /**
     * 获取生产订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProductionOrderService.selectErpProductionOrderById(id));
    }

    /**
     * 新增生产订单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:add')")
    @Log(title = "生产订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionOrder erpProductionOrder)
    {
        return toAjax(erpProductionOrderService.insertErpProductionOrder(erpProductionOrder));
    }

    /**
     * 修改生产订单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:edit')")
    @Log(title = "生产订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionOrder erpProductionOrder)
    {
        return toAjax(erpProductionOrderService.updateErpProductionOrder(erpProductionOrder));
    }

    /**
     * 删除生产订单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:remove')")
    @Log(title = "生产订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProductionOrderService.deleteErpProductionOrderByIds(ids));
    }

    /**
     * 更新生产订单状态
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:edit') or @ss.hasPermi('erp:productionOrder:approval')")
    @Log(title = "生产订单状态更新", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody ErpProductionOrder erpProductionOrder)
    {
        return toAjax(erpProductionOrderService.updateErpProductionOrderStatus(erpProductionOrder));
    }

    /**
     * 查询生产订单对应的工序列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:query')")
    @GetMapping("/{orderId}/processes")
    public AjaxResult getOrderProcesses(@PathVariable("orderId") Long orderId)
    {
        return success(erpProductionOrderService.selectOrderProcesses(orderId));
    }

    /**
     * 根据订单编码查询生产订单
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOrder:query')")
    @GetMapping("/code/{orderCode}")
    public AjaxResult getProductionOrderByCode(@PathVariable("orderCode") String orderCode)
    {
        ErpProductionOrder order = erpProductionOrderService.selectErpProductionOrderByCode(orderCode);
        if (order != null) {
            return success(order);
        } else {
            return error("未找到该生产订单");
        }
    }
}
