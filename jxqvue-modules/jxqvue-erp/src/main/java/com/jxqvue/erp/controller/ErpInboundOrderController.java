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
import com.jxqvue.erp.domain.ErpInboundOrder;
import com.jxqvue.erp.service.IErpInboundOrderService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 入库单表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/inbound")
public class ErpInboundOrderController extends BaseController
{
    @Autowired
    private IErpInboundOrderService erpInboundOrderService;

    /**
     * 查询入库单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInboundOrder erpInboundOrder)
    {
        startPage();
        List<ErpInboundOrder> list = erpInboundOrderService.selectErpInboundOrderList(erpInboundOrder);
        return getDataTable(list);
    }

    /**
     * 导出入库单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:export')")
    @Log(title = "入库单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInboundOrder erpInboundOrder)
    {
        List<ErpInboundOrder> list = erpInboundOrderService.selectErpInboundOrderList(erpInboundOrder);
        ExcelUtil<ErpInboundOrder> util = new ExcelUtil<ErpInboundOrder>(ErpInboundOrder.class);
        util.exportExcel(response, list, "入库单表数据");
    }

    /**
     * 获取入库单表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInboundOrderService.selectErpInboundOrderById(id));
    }

    /**
     * 新增入库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:add')")
    @Log(title = "入库单表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInboundOrder erpInboundOrder)
    {
        return toAjax(erpInboundOrderService.insertErpInboundOrder(erpInboundOrder));
    }

    /**
     * 修改入库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:edit')")
    @Log(title = "入库单表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInboundOrder erpInboundOrder)
    {
        return toAjax(erpInboundOrderService.updateErpInboundOrder(erpInboundOrder));
    }

    /**
     * 删除入库单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:remove')")
    @Log(title = "入库单表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInboundOrderService.deleteErpInboundOrderByIds(ids));
    }

    /**
     * 根据入库单号查询入库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:query')")
    @GetMapping("/getByInboundNo")
    public AjaxResult getByInboundNo(String inboundNo)
    {
        ErpInboundOrder inboundOrder = erpInboundOrderService.selectErpInboundOrderByInboundNo(inboundNo);
        return success(inboundOrder);
    }

    /**
     * 审核入库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:approve')")
    @Log(title = "入库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody ErpInboundOrder erpInboundOrder)
    {
        return toAjax(erpInboundOrderService.approveErpInboundOrder(erpInboundOrder));
    }

    /**
     * 执行入库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:execute')")
    @Log(title = "入库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/execute")
    public AjaxResult execute(@RequestBody ErpInboundOrder erpInboundOrder)
    {
        try {
            int result = erpInboundOrderService.executeErpInboundOrder(erpInboundOrder);
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 取消入库单
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:edit')")
    @Log(title = "入库单表", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody ErpInboundOrder erpInboundOrder)
    {
        return toAjax(erpInboundOrderService.cancelErpInboundOrder(erpInboundOrder));
    }

    /**
     * 生成入库单号
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:add')")
    @GetMapping("/generateInboundNo")
    public AjaxResult generateInboundNo()
    {
        String inboundNo = erpInboundOrderService.generateInboundNo();
        return success("生成成功：" + inboundNo);
    }

    /**
     * 查询入库单统计信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:list')")
    @GetMapping("/statistics")
    public AjaxResult statistics(ErpInboundOrder erpInboundOrder)
    {
        List<ErpInboundOrder> list = erpInboundOrderService.selectErpInboundOrderStatistics(erpInboundOrder);
        return success(list);
    }
}
