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
import com.jxqvue.erp.domain.ErpCustomer;
import com.jxqvue.erp.service.IErpCustomerService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 客户Controller
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@RestController
@RequestMapping("/erp/customer")
public class ErpCustomerController extends BaseController
{
    @Autowired
    private IErpCustomerService erpCustomerService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(ErpCustomer erpCustomer)
    {
        startPage();
        List<ErpCustomer> list = erpCustomerService.selectErpCustomerList(erpCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:export')")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ErpCustomer erpCustomer)
    {
        List<ErpCustomer> list = erpCustomerService.selectErpCustomerList(erpCustomer);
        ExcelUtil<ErpCustomer> util = new ExcelUtil<ErpCustomer>(ErpCustomer.class);
        util.exportExcel(response, list, "客户数据");
    }

    /**
     * 获取客户详细信息
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(erpCustomerService.selectErpCustomerById(id));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:add')")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ErpCustomer erpCustomer)
    {
        return toAjax(erpCustomerService.insertErpCustomer(erpCustomer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:edit')")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ErpCustomer erpCustomer)
    {
        return toAjax(erpCustomerService.updateErpCustomer(erpCustomer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('erp:customer:remove')")
    @Log(title = "客户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(erpCustomerService.deleteErpCustomerByIds(ids));
    }
}
