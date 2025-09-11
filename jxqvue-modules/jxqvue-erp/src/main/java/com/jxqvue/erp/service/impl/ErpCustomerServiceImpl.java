package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpCustomerMapper;
import com.jxqvue.erp.domain.ErpCustomer;
import com.jxqvue.erp.service.IErpCustomerService;

/**
 * 客户Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpCustomerServiceImpl implements IErpCustomerService 
{
    @Autowired
    private ErpCustomerMapper erpCustomerMapper;

    /**
     * 查询客户
     * 
     * @param id 客户主键
     * @return 客户
     */
    @Override
    public ErpCustomer selectErpCustomerById(Long id)
    {
        return erpCustomerMapper.selectErpCustomerById(id);
    }

    /**
     * 查询客户列表
     * 
     * @param erpCustomer 客户
     * @return 客户
     */
    @Override
    public List<ErpCustomer> selectErpCustomerList(ErpCustomer erpCustomer)
    {
        return erpCustomerMapper.selectErpCustomerList(erpCustomer);
    }

    /**
     * 新增客户
     * 
     * @param erpCustomer 客户
     * @return 结果
     */
    @Override
    public int insertErpCustomer(ErpCustomer erpCustomer)
    {
        erpCustomer.setCreateTime(DateUtils.getNowDate());
        return erpCustomerMapper.insertErpCustomer(erpCustomer);
    }

    /**
     * 修改客户
     * 
     * @param erpCustomer 客户
     * @return 结果
     */
    @Override
    public int updateErpCustomer(ErpCustomer erpCustomer)
    {
        erpCustomer.setUpdateTime(DateUtils.getNowDate());
        return erpCustomerMapper.updateErpCustomer(erpCustomer);
    }

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteErpCustomerByIds(Long[] ids)
    {
        return erpCustomerMapper.deleteErpCustomerByIds(ids);
    }

    /**
     * 删除客户信息
     * 
     * @param id 客户主键
     * @return 结果
     */
    @Override
    public int deleteErpCustomerById(Long id)
    {
        return erpCustomerMapper.deleteErpCustomerById(id);
    }
}
