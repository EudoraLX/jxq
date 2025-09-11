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
import com.jxqvue.erp.domain.ErpProductionLine;
import com.jxqvue.erp.service.IErpProductionLineService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 生产线Controller
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@RestController
@RequestMapping("/erp/productionLine")
public class ErpProductionLineController extends BaseController
{
    @Autowired
    private IErpProductionLineService erpProductionLineService;

    /**
     * 查询生产线列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpProductionLine erpProductionLine)
    {
        startPage();
        List<ErpProductionLine> list = erpProductionLineService.selectErpProductionLineList(erpProductionLine);
        return getDataTable(list);
    }

    /**
     * 导出生产线列表
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:export')")
    @Log(title = "生产线", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpProductionLine erpProductionLine)
    {
        List<ErpProductionLine> list = erpProductionLineService.selectErpProductionLineList(erpProductionLine);
        ExcelUtil<ErpProductionLine> util = new ExcelUtil<ErpProductionLine>(ErpProductionLine.class);
        util.exportExcel(response, list, "生产线数据");
    }

    /**
     * 获取生产线详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpProductionLineService.selectErpProductionLineById(id));
    }

    /**
     * 新增生产线
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:add')")
    @Log(title = "生产线", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpProductionLine erpProductionLine)
    {
        return toAjax(erpProductionLineService.insertErpProductionLine(erpProductionLine));
    }

    /**
     * 修改生产线
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:edit')")
    @Log(title = "生产线", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpProductionLine erpProductionLine)
    {
        return toAjax(erpProductionLineService.updateErpProductionLine(erpProductionLine));
    }

    /**
     * 删除生产线
     */
    @PreAuthorize("@ss.hasPermi('erp:productionLine:remove')")
    @Log(title = "生产线", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpProductionLineService.deleteErpProductionLineByIds(ids));
    }
    
    /**
     * 获取可用的生产线列表（已启用且状态正常）
     */
    @GetMapping("/available")
    public AjaxResult getAvailableLines()
    {
        List<ErpProductionLine> list = erpProductionLineService.getAvailableProductionLines();
        return success(list);
    }
}
