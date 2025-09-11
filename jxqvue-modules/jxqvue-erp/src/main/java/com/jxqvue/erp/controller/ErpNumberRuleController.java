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
import com.jxqvue.common.core.page.TableDataInfo;
import com.jxqvue.common.enums.BusinessType;
import com.jxqvue.erp.domain.ErpNumberRule;
import com.jxqvue.erp.service.IErpNumberRuleService;
import com.jxqvue.common.utils.poi.ExcelUtil;

/**
 * 编号规则Controller
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@RestController
@RequestMapping("/erp/numberRule")
public class ErpNumberRuleController extends BaseController
{
    @Autowired
    private IErpNumberRuleService erpNumberRuleService;

    /**
     * 查询编号规则列表
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpNumberRule erpNumberRule)
    {
        startPage();
        List<ErpNumberRule> list = erpNumberRuleService.selectErpNumberRuleList(erpNumberRule);
        return getDataTable(list);
    }

    /**
     * 导出编号规则列表
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:export')")
    @Log(title = "编号规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpNumberRule erpNumberRule)
    {
        List<ErpNumberRule> list = erpNumberRuleService.selectErpNumberRuleList(erpNumberRule);
        ExcelUtil<ErpNumberRule> util = new ExcelUtil<ErpNumberRule>(ErpNumberRule.class);
        util.exportExcel(response, list, "编号规则数据");
    }

    /**
     * 获取编号规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId)
    {
        return success(erpNumberRuleService.selectErpNumberRuleByRuleId(ruleId));
    }

    /**
     * 新增编号规则
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:add')")
    @Log(title = "编号规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpNumberRule erpNumberRule)
    {
        return toAjax(erpNumberRuleService.insertErpNumberRule(erpNumberRule));
    }

    /**
     * 修改编号规则
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:edit')")
    @Log(title = "编号规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpNumberRule erpNumberRule)
    {
        return toAjax(erpNumberRuleService.updateErpNumberRule(erpNumberRule));
    }

    /**
     * 删除编号规则
     */
    @PreAuthorize("@ss.hasPermi('erp:numberRule:remove')")
    @Log(title = "编号规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds)
    {
        return toAjax(erpNumberRuleService.deleteErpNumberRuleByRuleIds(ruleIds));
    }
}
