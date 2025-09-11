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
import com.jxqvue.erp.domain.ErpInboundOrderDetail;
import com.jxqvue.erp.service.IErpInboundOrderDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 入库单明细Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/inventory/inboundOrderDetail")
public class ErpInboundOrderDetailController extends BaseController
{
    @Autowired
    private IErpInboundOrderDetailService erpInboundOrderDetailService;

    /**
     * 查询入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInboundOrderDetail erpInboundOrderDetail)
    {
        startPage();
        List<ErpInboundOrderDetail> list = erpInboundOrderDetailService.selectErpInboundOrderDetailList(erpInboundOrderDetail);
        return getDataTable(list);
    }

    /**
     * 导出入库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:export')")
    @Log(title = "入库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInboundOrderDetail erpInboundOrderDetail)
    {
        List<ErpInboundOrderDetail> list = erpInboundOrderDetailService.selectErpInboundOrderDetailList(erpInboundOrderDetail);
        ExcelUtil<ErpInboundOrderDetail> util = new ExcelUtil<ErpInboundOrderDetail>(ErpInboundOrderDetail.class);
        util.exportExcel(response, list, "入库单明细数据");
    }

    /**
     * 获取入库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInboundOrderDetailService.selectErpInboundOrderDetailById(id));
    }

    /**
     * 新增入库单明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:add')")
    @Log(title = "入库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInboundOrderDetail erpInboundOrderDetail)
    {
        return toAjax(erpInboundOrderDetailService.insertErpInboundOrderDetail(erpInboundOrderDetail));
    }

    /**
     * 修改入库单明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:edit')")
    @Log(title = "入库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInboundOrderDetail erpInboundOrderDetail)
    {
        return toAjax(erpInboundOrderDetailService.updateErpInboundOrderDetail(erpInboundOrderDetail));
    }

    /**
     * 删除入库单明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:remove')")
    @Log(title = "入库单明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInboundOrderDetailService.deleteErpInboundOrderDetailByIds(ids));
    }

    /**
     * 根据入库单ID查询明细列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:list')")
    @GetMapping("/inbound/{inboundId}")
    public TableDataInfo getDetailsByInboundId(@PathVariable("inboundId") Long inboundId)
    {
        startPage();
        List<ErpInboundOrderDetail> list = erpInboundOrderDetailService.selectErpInboundOrderDetailByInboundId(inboundId);
        return getDataTable(list);
    }

    /**
     * 批量新增入库单明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:add')")
    @Log(title = "入库单明细", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<ErpInboundOrderDetail> erpInboundOrderDetails)
    {
        return toAjax(erpInboundOrderDetailService.insertErpInboundOrderDetails(erpInboundOrderDetails));
    }

    /**
     * 批量更新入库单明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:inbound:edit')")
    @Log(title = "入库单明细", businessType = BusinessType.UPDATE)
    @PutMapping("/batch")
    public AjaxResult updateBatch(@RequestBody List<ErpInboundOrderDetail> erpInboundOrderDetails)
    {
        return toAjax(erpInboundOrderDetailService.updateErpInboundOrderDetails(erpInboundOrderDetails));
    }
}
