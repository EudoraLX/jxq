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
import com.jxqvue.erp.domain.ErpProductionOperationRecord;
import com.jxqvue.erp.service.IErpProductionOperationRecordService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;
import com.jxqvue.common.utils.SecurityUtils;

/**
 * 生产操作记录Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/erp/productionOperationRecord")
public class ErpProductionOperationRecordController extends BaseController
{
    @Autowired
    private IErpProductionOperationRecordService erpProductionOperationRecordService;

    /**
     * 查询生产操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        startPage();
        List<ErpProductionOperationRecord> list = erpProductionOperationRecordService.selectErpProductionOperationRecordList(erpProductionOperationRecord);
        return getDataTable(list);
    }

    /**
     * 导出生产操作记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:export')")
    @Log(title = "生产操作记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionOperationRecord erpProductionOperationRecord)
    {
        List<ErpProductionOperationRecord> list = erpProductionOperationRecordService.selectErpProductionOperationRecordList(erpProductionOperationRecord);
        ExcelUtil<ErpProductionOperationRecord> util = new ExcelUtil<ErpProductionOperationRecord>(ErpProductionOperationRecord.class);
        util.exportExcel(response, list, "生产操作记录数据");
    }

    /**
     * 获取生产操作记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProductionOperationRecordService.selectErpProductionOperationRecordById(id));
    }

    /**
     * 新增生产操作记录
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:add')")
    @Log(title = "生产操作记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionOperationRecord erpProductionOperationRecord)
    {
        // 设置操作员为当前用户
        erpProductionOperationRecord.setOperatorId(SecurityUtils.getUserId());
        return toAjax(erpProductionOperationRecordService.insertErpProductionOperationRecord(erpProductionOperationRecord));
    }

    /**
     * 修改生产操作记录
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:edit')")
    @Log(title = "生产操作记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionOperationRecord erpProductionOperationRecord)
    {
        return toAjax(erpProductionOperationRecordService.updateErpProductionOperationRecord(erpProductionOperationRecord));
    }

    /**
     * 删除生产操作记录
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:remove')")
    @Log(title = "生产操作记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProductionOperationRecordService.deleteErpProductionOperationRecordByIds(ids));
    }

    /**
     * 根据操作员ID获取最新的工序ID
     */
    @GetMapping("/getLatestProcessId/{operatorId}")
    public AjaxResult getLatestProcessId(@PathVariable("operatorId") Long operatorId)
    {
        Long processId = erpProductionOperationRecordService.getLatestProcessIdByOperatorId(operatorId);
        return success(processId);
    }

    /**
     * 根据生产订单ID和工序ID获取工作中心、设备、模具信息
     */
    @GetMapping("/getWorkCenterInfo/{productionOrderId}/{processId}")
    public AjaxResult getWorkCenterInfo(@PathVariable("productionOrderId") Long productionOrderId, 
                                       @PathVariable("processId") Long processId)
    {
        ErpProductionOperationRecord info = erpProductionOperationRecordService.getWorkCenterInfoByOrderAndProcess(productionOrderId, processId);
        return success(info);
    }

    /**
     * 根据生产订单ID获取工艺路线明细信息
     */
    @GetMapping("/getProcessRouteDetails/{productionOrderId}")
    public AjaxResult getProcessRouteDetails(@PathVariable("productionOrderId") Long productionOrderId)
    {
        List<ErpProductionOperationRecord> list = erpProductionOperationRecordService.getProcessRouteDetailsByOrderId(productionOrderId);
        return success(list);
    }

    /**
     * 获取最新操作记录
     */
    @PreAuthorize("@ss.hasPermi('erp:productionOperationRecord:query')")
    @GetMapping("/latest")
    public AjaxResult getLatestOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        ErpProductionOperationRecord record = erpProductionOperationRecordService.getLatestOperationRecord(erpProductionOperationRecord);
        return success(record);
    }
}
