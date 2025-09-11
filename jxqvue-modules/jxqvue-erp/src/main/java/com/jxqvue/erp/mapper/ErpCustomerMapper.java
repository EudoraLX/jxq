package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpCustomer;

/**
 * 客户Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface ErpCustomerMapper 
{
    /**
     * 查询客户
     * 
     * @param id 客户主键
     * @return 客户
     */
    public ErpCustomer selectErpCustomerById(Long id);

    /**
     * 查询客户列表
     * 
     * @param erpCustomer 客户
     * @return 客户集合
     */
    public List<ErpCustomer> selectErpCustomerList(ErpCustomer erpCustomer);

    /**
     * 新增客户
     * 
     * @param erpCustomer 客户
     * @return 结果
     */
    public int insertErpCustomer(ErpCustomer erpCustomer);

    /**
     * 修改客户
     * 
     * @param erpCustomer 客户
     * @return 结果
     */
    public int updateErpCustomer(ErpCustomer erpCustomer);

    /**
     * 删除客户
     * 
     * @param id 客户主键
     * @return 结果
     */
    public int deleteErpCustomerById(Long id);

    /**
     * 批量删除客户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpCustomerByIds(Long[] ids);
}
