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
import com.jxqvue.erp.domain.ErpProcessHandover;
import com.jxqvue.erp.service.IErpProcessHandoverService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 工序交接单Controller
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/erp/processHandover")
public class ErpProcessHandoverController extends BaseController
{
    @Autowired
    private IErpProcessHandoverService erpProcessHandoverService;

    /**
     * 查询工序交接单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProcessHandover erpProcessHandover)
    {
        startPage();
        List<ErpProcessHandover> list = erpProcessHandoverService.selectErpProcessHandoverList(erpProcessHandover);
        return getDataTable(list);
    }

    /**
     * 导出工序交接单列表
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:export')")
    @Log(title = "工序交接单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProcessHandover erpProcessHandover)
    {
        List<ErpProcessHandover> list = erpProcessHandoverService.selectErpProcessHandoverList(erpProcessHandover);
        ExcelUtil<ErpProcessHandover> util = new ExcelUtil<ErpProcessHandover>(ErpProcessHandover.class);
        util.exportExcel(response, list, "工序交接单数据");
    }

    /**
     * 获取工序交接单详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProcessHandoverService.selectErpProcessHandoverById(id));
    }

    /**
     * 新增工序交接单
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:add')")
    @Log(title = "工序交接单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProcessHandover erpProcessHandover)
    {
        return toAjax(erpProcessHandoverService.insertErpProcessHandover(erpProcessHandover));
    }

    /**
     * 修改工序交接单
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:edit')")
    @Log(title = "工序交接单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProcessHandover erpProcessHandover)
    {
        return toAjax(erpProcessHandoverService.updateErpProcessHandover(erpProcessHandover));
    }

    /**
     * 删除工序交接单
     */
    @PreAuthorize("@ss.hasPermi('erp:processHandover:remove')")
    @Log(title = "工序交接单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProcessHandoverService.deleteErpProcessHandoverByIds(ids));
    }
}
