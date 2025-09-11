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
import com.jxqvue.erp.domain.ErpMoldDetail;
import com.jxqvue.erp.service.IErpMoldDetailService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 模具明细Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/moldDetail")
public class ErpMoldDetailController extends BaseController
{
    @Autowired
    private IErpMoldDetailService erpMoldDetailService;

    /**
     * 查询模具明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpMoldDetail erpMoldDetail)
    {
        startPage();
        List<ErpMoldDetail> list = erpMoldDetailService.selectErpMoldDetailList(erpMoldDetail);
        return getDataTable(list);
    }

    /**
     * 导出模具明细列表
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:export')")
    @Log(title = "模具明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpMoldDetail erpMoldDetail)
    {
        List<ErpMoldDetail> list = erpMoldDetailService.selectErpMoldDetailList(erpMoldDetail);
        ExcelUtil<ErpMoldDetail> util = new ExcelUtil<ErpMoldDetail>(ErpMoldDetail.class);
        util.exportExcel(response, list, "模具明细数据");
    }

    /**
     * 获取模具明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpMoldDetailService.selectErpMoldDetailById(id));
    }

    /**
     * 新增模具明细
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:add')")
    @Log(title = "模具明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpMoldDetail erpMoldDetail)
    {
        return toAjax(erpMoldDetailService.insertErpMoldDetail(erpMoldDetail));
    }

    /**
     * 修改模具明细
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:edit')")
    @Log(title = "模具明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpMoldDetail erpMoldDetail)
    {
        return toAjax(erpMoldDetailService.updateErpMoldDetail(erpMoldDetail));
    }

    /**
     * 删除模具明细
     */
    @PreAuthorize("@ss.hasPermi('erp:moldDetail:remove')")
    @Log(title = "模具明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpMoldDetailService.deleteErpMoldDetailByIds(ids));
    }
}
