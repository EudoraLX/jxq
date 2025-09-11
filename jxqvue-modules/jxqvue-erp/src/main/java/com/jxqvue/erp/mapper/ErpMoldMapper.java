package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpMold;
import com.jxqvue.erp.domain.ErpMoldDetail;

/**
 * 模具Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface ErpMoldMapper 
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
     * 删除模具
     * 
     * @param id 模具主键
     * @return 结果
     */
    public int deleteErpMoldById(Long id);

    /**
     * 批量删除模具
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpMoldByIds(Long[] ids);

    /**
     * 批量删除模具明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpMoldDetailByMoldIds(Long[] ids);
    
    /**
     * 批量新增模具明细
     * 
     * @param erpMoldDetailList 模具明细列表
     * @return 结果
     */
    public int batchErpMoldDetail(List<ErpMoldDetail> erpMoldDetailList);
    

    /**
     * 通过模具主键删除模具明细信息
     * 
     * @param id 模具ID
     * @return 结果
     */
    public int deleteErpMoldDetailByMoldId(Long id);
}
