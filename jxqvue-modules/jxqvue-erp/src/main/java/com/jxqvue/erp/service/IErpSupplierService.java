package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpSupplier;

/**
 * 供应商Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpSupplierService 
{
    /**
     * 查询供应商
     * 
     * @param id 供应商主键
     * @return 供应商
     */
    public ErpSupplier selectErpSupplierById(Long id);

    /**
     * 查询供应商列表
     * 
     * @param erpSupplier 供应商
     * @return 供应商集合
     */
    public List<ErpSupplier> selectErpSupplierList(ErpSupplier erpSupplier);

    /**
     * 新增供应商
     * 
     * @param erpSupplier 供应商
     * @return 结果
     */
    public int insertErpSupplier(ErpSupplier erpSupplier);

    /**
     * 修改供应商
     * 
     * @param erpSupplier 供应商
     * @return 结果
     */
    public int updateErpSupplier(ErpSupplier erpSupplier);

    /**
     * 批量删除供应商
     * 
     * @param ids 需要删除的供应商主键集合
     * @return 结果
     */
    public int deleteErpSupplierByIds(Long[] ids);

    /**
     * 删除供应商信息
     * 
     * @param id 供应商主键
     * @return 结果
     */
    public int deleteErpSupplierById(Long id);
}
