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
import com.jxqvue.erp.domain.ErpOperatorDuty;
import com.jxqvue.erp.service.IErpOperatorDutyService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 在岗离岗记录Controller
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/erp/operatorDuty")
public class ErpOperatorDutyController extends BaseController
{
    @Autowired
    private IErpOperatorDutyService erpOperatorDutyService;

    /**
     * 查询在岗离岗记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpOperatorDuty erpOperatorDuty)
    {
        startPage();
        List<ErpOperatorDuty> list = erpOperatorDutyService.selectErpOperatorDutyList(erpOperatorDuty);
        return getDataTable(list);
    }

    /**
     * 导出在岗离岗记录列表
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:export')")
    @Log(title = "在岗离岗记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpOperatorDuty erpOperatorDuty)
    {
        List<ErpOperatorDuty> list = erpOperatorDutyService.selectErpOperatorDutyList(erpOperatorDuty);
        ExcelUtil<ErpOperatorDuty> util = new ExcelUtil<ErpOperatorDuty>(ErpOperatorDuty.class);
        util.exportExcel(response, list, "在岗离岗记录数据");
    }

    /**
     * 获取在岗离岗记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpOperatorDutyService.selectErpOperatorDutyById(id));
    }

    /**
     * 新增在岗离岗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:add')")
    @Log(title = "在岗离岗记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpOperatorDuty erpOperatorDuty)
    {
        return toAjax(erpOperatorDutyService.insertErpOperatorDuty(erpOperatorDuty));
    }

    /**
     * 修改在岗离岗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:edit')")
    @Log(title = "在岗离岗记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpOperatorDuty erpOperatorDuty)
    {
        return toAjax(erpOperatorDutyService.updateErpOperatorDuty(erpOperatorDuty));
    }

    /**
     * 删除在岗离岗记录
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:remove')")
    @Log(title = "在岗离岗记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpOperatorDutyService.deleteErpOperatorDutyByIds(ids));
    }

    /**
     * 获取当前用户最新上岗状态
     */
    @GetMapping("/currentUserStatus")
    public AjaxResult getCurrentUserStatus()
    {
        Long userId = getUserId();
        ErpOperatorDuty duty = erpOperatorDutyService.selectLatestOperatorDutyByOperatorId(userId);
        return success(duty);
    }

    /**
     * 根据工序编号查询工序信息
     */
    @GetMapping("/getProcessByCode/{processCode}")
    public AjaxResult getProcessByCode(@PathVariable("processCode") String processCode)
    {
        return success(erpOperatorDutyService.getProcessByCode(processCode));
    }

    /**
     * 切换上岗状态
     */
    @PreAuthorize("@ss.hasPermi('erp:operatorDuty:add')")
    @PostMapping("/toggleDutyStatus")
    public AjaxResult toggleDutyStatus(@RequestBody ErpOperatorDuty erpOperatorDuty)
    {
        return toAjax(erpOperatorDutyService.toggleDutyStatus(erpOperatorDuty, getUserId()));
    }
}
