package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpSupplierMapper;
import com.jxqvue.erp.domain.ErpSupplier;
import com.jxqvue.erp.service.IErpSupplierService;

/**
 * 供应商Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpSupplierServiceImpl implements IErpSupplierService 
{
    @Autowired
    private ErpSupplierMapper erpSupplierMapper;

    /**
     * 查询供应商
     * 
     * @param id 供应商主键
     * @return 供应商
     */
    @Override
    public ErpSupplier selectErpSupplierById(Long id)
    {
        return erpSupplierMapper.selectErpSupplierById(id);
    }

    /**
     * 查询供应商列表
     * 
     * @param erpSupplier 供应商
     * @return 供应商
     */
    @Override
    public List<ErpSupplier> selectErpSupplierList(ErpSupplier erpSupplier)
    {
        return erpSupplierMapper.selectErpSupplierList(erpSupplier);
    }

    /**
     * 新增供应商
     * 
     * @param erpSupplier 供应商
     * @return 结果
     */
    @Override
    public int insertErpSupplier(ErpSupplier erpSupplier)
    {
        erpSupplier.setCreateTime(DateUtils.getNowDate());
        return erpSupplierMapper.insertErpSupplier(erpSupplier);
    }

    /**
     * 修改供应商
     * 
     * @param erpSupplier 供应商
     * @return 结果
     */
    @Override
    public int updateErpSupplier(ErpSupplier erpSupplier)
    {
        erpSupplier.setUpdateTime(DateUtils.getNowDate());
        return erpSupplierMapper.updateErpSupplier(erpSupplier);
    }

    /**
     * 批量删除供应商
     * 
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteErpSupplierByIds(Long[] ids)
    {
        return erpSupplierMapper.deleteErpSupplierByIds(ids);
    }

    /**
     * 删除供应商信息
     * 
     * @param id 供应商主键
     * @return 结果
     */
    @Override
    public int deleteErpSupplierById(Long id)
    {
        return erpSupplierMapper.deleteErpSupplierById(id);
    }
}
