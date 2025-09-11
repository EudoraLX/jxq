package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpMold;

/**
 * 模具Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpMoldService 
{
    /**
     * 查询模具
     * 
     * @param id 模具主键
     * @return 模具
     */
    public ErpMold selectErpMoldById(Long id);

    /**
     * 查询模具列表
     * 
     * @param erpMold 模具
     * @return 模具集合
     */
    public List<ErpMold> selectErpMoldList(ErpMold erpMold);

    /**
     * 新增模具
     * 
     * @param erpMold 模具
     * @return 结果
     */
    public int insertErpMold(ErpMold erpMold);

    /**
     * 修改模具
     * 
     * @param erpMold 模具
     * @return 结果
     */
    public int updateErpMold(ErpMold erpMold);

    /**
     * 批量删除模具
     * 
     * @param ids 需要删除的模具主键集合
     * @return 结果
     */
    public int deleteErpMoldByIds(Long[] ids);

    /**
     * 删除模具信息
     * 
     * @param id 模具主键
     * @return 结果
     */
    public int deleteErpMoldById(Long id);
}
