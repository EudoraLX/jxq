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
import com.jxqvue.erp.domain.ErpBom;
import com.jxqvue.erp.service.IErpBomService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * BOM管理Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@RestController
@RequestMapping("/erp/bom")
public class ErpBomController extends BaseController
{
    @Autowired
    private IErpBomService erpBomService;

    /**
     * 查询BOM管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpBom erpBom)
    {
        startPage();
        List<ErpBom> list = erpBomService.selectErpBomList(erpBom);
        return getDataTable(list);
    }

    /**
     * 导出BOM管理列表
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:export')")
    @Log(title = "BOM管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpBom erpBom)
    {
        List<ErpBom> list = erpBomService.selectErpBomList(erpBom);
        ExcelUtil<ErpBom> util = new ExcelUtil<ErpBom>(ErpBom.class);
        util.exportExcel(response, list, "BOM管理数据");
    }

    /**
     * 获取BOM管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpBomService.selectErpBomById(id));
    }

    /**
     * 新增BOM管理
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:add')")
    @Log(title = "BOM管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpBom erpBom)
    {
        return toAjax(erpBomService.insertErpBom(erpBom));
    }

    /**
     * 修改BOM管理
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:edit')")
    @Log(title = "BOM管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpBom erpBom)
    {
        return toAjax(erpBomService.updateErpBom(erpBom));
    }

    /**
     * 删除BOM管理
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:remove')")
    @Log(title = "BOM管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpBomService.deleteErpBomByIds(ids));
    }

    /**
     * 检查BOM冲突
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:query')")
    @GetMapping("/checkConflict")
    public AjaxResult checkConflict(Long productId, String effectiveDate)
    {
        try {
            // 使用SimpleDateFormat解析ISO格式的日期字符串 "yyyy-MM-dd"
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(effectiveDate);
            List<ErpBom> conflictBoms = erpBomService.checkBomConflict(productId, date, null);
            return success(conflictBoms);
        } catch (Exception e) {
            return error("检查冲突失败：" + e.getMessage());
        }
    }

    /**
     * 审批BOM
     */
    @PreAuthorize("@ss.hasPermi('erp:bom:approve')")
    @Log(title = "BOM审批", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody java.util.Map<String, Object> params)
    {
        Long bomId = Long.valueOf(params.get("bomId").toString());
        Boolean forceApprove = Boolean.valueOf(params.getOrDefault("forceApprove", false).toString());
        
        return erpBomService.approveBom(bomId, forceApprove);
    }
}
