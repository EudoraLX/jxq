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
import com.jxqvue.erp.domain.ErpShelf;
import com.jxqvue.erp.service.IErpShelfService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 货架Controller
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@RestController
@RequestMapping("/erp/shelf")
public class ErpShelfController extends BaseController
{
    @Autowired
    private IErpShelfService erpShelfService;

    /**
     * 查询货架列表
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpShelf erpShelf)
    {
        startPage();
        List<ErpShelf> list = erpShelfService.selectErpShelfList(erpShelf);
        return getDataTable(list);
    }

    /**
     * 导出货架列表
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:export')")
    @Log(title = "货架", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpShelf erpShelf)
    {
        List<ErpShelf> list = erpShelfService.selectErpShelfList(erpShelf);
        ExcelUtil<ErpShelf> util = new ExcelUtil<ErpShelf>(ErpShelf.class);
        util.exportExcel(response, list, "货架数据");
    }

    /**
     * 获取货架详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpShelfService.selectErpShelfById(id));
    }

    /**
     * 新增货架
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:add')")
    @Log(title = "货架", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpShelf erpShelf)
    {
        return toAjax(erpShelfService.insertErpShelf(erpShelf));
    }

    /**
     * 修改货架
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:edit')")
    @Log(title = "货架", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpShelf erpShelf)
    {
        return toAjax(erpShelfService.updateErpShelf(erpShelf));
    }

    /**
     * 删除货架
     */
    @PreAuthorize("@ss.hasPermi('erp:shelf:remove')")
    @Log(title = "货架", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpShelfService.deleteErpShelfByIds(ids));
    }
    
    /**
     * 根据仓库ID查询货架列表
     */
    @GetMapping("/warehouse/{warehouseId}")
    public AjaxResult getShelvesByWarehouseId(@PathVariable("warehouseId") Long warehouseId)
    {
        List<ErpShelf> list = erpShelfService.selectErpShelfListByWarehouseId(warehouseId);
        return success(list);
    }
    
    /**
     * 获取可用的货架列表（已启用）
     */
    @GetMapping("/available")
    public AjaxResult getAvailableShelves()
    {
        List<ErpShelf> list = erpShelfService.getAvailableShelves();
        return success(list);
    }
}
