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
import com.jxqvue.erp.domain.ErpProcess;
import com.jxqvue.erp.service.IErpProcessService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 工序Controller
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@RestController
@RequestMapping("/erp/process")
public class ErpProcessController extends BaseController
{
    @Autowired
    private IErpProcessService erpProcessService;

    /**
     * 查询工序列表
     */
    @PreAuthorize("@ss.hasPermi('erp:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProcess erpProcess)
    {
        startPage();
        List<ErpProcess> list = erpProcessService.selectErpProcessList(erpProcess);
        return getDataTable(list);
    }

    /**
     * 导出工序列表
     */
    @PreAuthorize("@ss.hasPermi('erp:process:export')")
    @Log(title = "工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProcess erpProcess)
    {
        List<ErpProcess> list = erpProcessService.selectErpProcessList(erpProcess);
        ExcelUtil<ErpProcess> util = new ExcelUtil<ErpProcess>(ErpProcess.class);
        util.exportExcel(response, list, "工序数据");
    }

    /**
     * 获取工序详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:process:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProcessService.selectErpProcessById(id));
    }

    /**
     * 新增工序
     */
    @PreAuthorize("@ss.hasPermi('erp:process:add')")
    @Log(title = "工序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProcess erpProcess)
    {
        return toAjax(erpProcessService.insertErpProcess(erpProcess));
    }

    /**
     * 修改工序
     */
    @PreAuthorize("@ss.hasPermi('erp:process:edit')")
    @Log(title = "工序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProcess erpProcess)
    {
        return toAjax(erpProcessService.updateErpProcess(erpProcess));
    }

    /**
     * 删除工序
     */
    @PreAuthorize("@ss.hasPermi('erp:process:remove')")
    @Log(title = "工序", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProcessService.deleteErpProcessByIds(ids));
    }
}
