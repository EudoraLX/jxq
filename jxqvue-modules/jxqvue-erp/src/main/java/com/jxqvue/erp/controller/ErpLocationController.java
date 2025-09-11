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
import com.jxqvue.erp.domain.ErpLocation;
import com.jxqvue.erp.service.IErpLocationService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 货位Controller
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@RestController
@RequestMapping("/erp/location")
public class ErpLocationController extends BaseController
{
    @Autowired
    private IErpLocationService erpLocationService;

    /**
     * 查询货位列表
     */
    @PreAuthorize("@ss.hasPermi('erp:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpLocation erpLocation)
    {
        startPage();
        List<ErpLocation> list = erpLocationService.selectErpLocationList(erpLocation);
        return getDataTable(list);
    }

    /**
     * 导出货位列表
     */
    @PreAuthorize("@ss.hasPermi('erp:location:export')")
    @Log(title = "货位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpLocation erpLocation)
    {
        List<ErpLocation> list = erpLocationService.selectErpLocationList(erpLocation);
        ExcelUtil<ErpLocation> util = new ExcelUtil<ErpLocation>(ErpLocation.class);
        util.exportExcel(response, list, "货位数据");
    }

    /**
     * 获取货位详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:location:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpLocationService.selectErpLocationById(id));
    }

    /**
     * 新增货位
     */
    @PreAuthorize("@ss.hasPermi('erp:location:add')")
    @Log(title = "货位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpLocation erpLocation)
    {
        return toAjax(erpLocationService.insertErpLocation(erpLocation));
    }

    /**
     * 修改货位
     */
    @PreAuthorize("@ss.hasPermi('erp:location:edit')")
    @Log(title = "货位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpLocation erpLocation)
    {
        return toAjax(erpLocationService.updateErpLocation(erpLocation));
    }

    /**
     * 删除货位
     */
    @PreAuthorize("@ss.hasPermi('erp:location:remove')")
    @Log(title = "货位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpLocationService.deleteErpLocationByIds(ids));
    }
    
    /**
     * 根据货架ID查询货位列表
     */
    @GetMapping("/shelf/{shelfId}")
    public AjaxResult getLocationsByShelfId(@PathVariable("shelfId") Long shelfId)
    {
        List<ErpLocation> list = erpLocationService.selectErpLocationListByShelfId(shelfId);
        return success(list);
    }
    
    /**
     * 根据仓库ID查询货位列表
     */
    @GetMapping("/warehouse/{warehouseId}")
    public AjaxResult getLocationsByWarehouseId(@PathVariable("warehouseId") Long warehouseId)
    {
        List<ErpLocation> list = erpLocationService.selectErpLocationListByWarehouseId(warehouseId);
        return success(list);
    }
    
    /**
     * 获取可用的货位列表（已启用且空闲）
     */
    @GetMapping("/available")
    public AjaxResult getAvailableLocations()
    {
        List<ErpLocation> list = erpLocationService.getAvailableLocations();
        return success(list);
    }
    
    /**
     * 获取空闲货位列表
     */
    @GetMapping("/free")
    public AjaxResult getFreeLocations()
    {
        List<ErpLocation> list = erpLocationService.getFreeLocations();
        return success(list);
    }
}
