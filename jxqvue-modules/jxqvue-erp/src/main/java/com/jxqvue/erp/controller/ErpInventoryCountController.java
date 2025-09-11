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
import com.jxqvue.erp.domain.ErpInventoryCount;
import com.jxqvue.erp.service.IErpInventoryCountService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 盘点单表Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/inventory/count")
public class ErpInventoryCountController extends BaseController
{
    @Autowired
    private IErpInventoryCountService erpInventoryCountService;

    /**
     * 查询盘点单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpInventoryCount erpInventoryCount)
    {
        startPage();
        List<ErpInventoryCount> list = erpInventoryCountService.selectErpInventoryCountList(erpInventoryCount);
        return getDataTable(list);
    }

    /**
     * 导出盘点单表列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:export')")
    @Log(title = "盘点单表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpInventoryCount erpInventoryCount)
    {
        List<ErpInventoryCount> list = erpInventoryCountService.selectErpInventoryCountList(erpInventoryCount);
        ExcelUtil<ErpInventoryCount> util = new ExcelUtil<ErpInventoryCount>(ErpInventoryCount.class);
        util.exportExcel(response, list, "盘点单表数据");
    }

    /**
     * 获取盘点单表详细信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpInventoryCountService.selectErpInventoryCountById(id));
    }

    /**
     * 新增盘点单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:add')")
    @Log(title = "盘点单表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        return toAjax(erpInventoryCountService.insertErpInventoryCount(erpInventoryCount));
    }

    /**
     * 修改盘点单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:edit')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        return toAjax(erpInventoryCountService.updateErpInventoryCount(erpInventoryCount));
    }

    /**
     * 删除盘点单表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:remove')")
    @Log(title = "盘点单表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpInventoryCountService.deleteErpInventoryCountByIds(ids));
    }

    /**
     * 根据盘点单号查询盘点单
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:query')")
    @GetMapping("/getByCountNo")
    public AjaxResult getByCountNo(String countNo)
    {
        ErpInventoryCount inventoryCount = erpInventoryCountService.selectErpInventoryCountByCountNo(countNo);
        return success(inventoryCount);
    }

    /**
     * 审核盘点单
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:approve')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        return toAjax(erpInventoryCountService.approveErpInventoryCount(erpInventoryCount));
    }

    /**
     * 执行盘点单
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:execute')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/execute")
    public AjaxResult execute(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        try {
            int result = erpInventoryCountService.executeErpInventoryCount(erpInventoryCount);
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 调整盘点单
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:adjust')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/adjust")
    public AjaxResult adjust(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        try {
            int result = erpInventoryCountService.adjustErpInventoryCount(erpInventoryCount);
            return toAjax(result);
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 取消盘点单
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:edit')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        return toAjax(erpInventoryCountService.cancelErpInventoryCount(erpInventoryCount));
    }

    /**
     * 生成盘点单号
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:add')")
    @GetMapping("/generateCountNo")
    public AjaxResult generateCountNo()
    {
        String countNo = erpInventoryCountService.generateCountNo();
        return success("生成成功：" + countNo);
    }

    /**
     * 查询盘点单统计信息
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:list')")
    @GetMapping("/statistics")
    public AjaxResult statistics(ErpInventoryCount erpInventoryCount)
    {
        List<ErpInventoryCount> list = erpInventoryCountService.selectErpInventoryCountStatistics(erpInventoryCount);
        return success(list);
    }

    /**
     * 生成盘点明细
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:edit')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/generateDetails")
    public AjaxResult generateDetails(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        try {
            int result = erpInventoryCountService.generateCountDetails(erpInventoryCount);
            return success("生成成功，共生成 " + result + " 条盘点明细");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 计算盘点差异
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:edit')")
    @Log(title = "盘点单表", businessType = BusinessType.UPDATE)
    @PostMapping("/calculateDifferences")
    public AjaxResult calculateDifferences(@RequestBody ErpInventoryCount erpInventoryCount)
    {
        try {
            int result = erpInventoryCountService.calculateCountDifferences(erpInventoryCount);
            return success("计算完成，共发现 " + result + " 条差异");
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    /**
     * 查询盘点差异列表
     */
    @PreAuthorize("@ss.hasPermi('inventory:count:list')")
    @GetMapping("/differences")
    public AjaxResult differences(ErpInventoryCount erpInventoryCount)
    {
        List<ErpInventoryCount> list = erpInventoryCountService.selectCountDifferences(erpInventoryCount);
        return success(list);
    }
}
