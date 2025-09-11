package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpMoldDetail;

/**
 * 模具明细Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpMoldDetailService 
{
    /**
     * 查询模具明细
     * 
     * @param id 模具明细主键
     * @return 模具明细
     */
    public ErpMoldDetail selectErpMoldDetailById(Long id);

    /**
     * 查询模具明细列表
     * 
     * @param erpMoldDetail 模具明细
     * @return 模具明细集合
     */
    public List<ErpMoldDetail> selectErpMoldDetailList(ErpMoldDetail erpMoldDetail);

    /**
     * 新增模具明细
     * 
     * @param erpMoldDetail 模具明细
     * @return 结果
     */
    public int insertErpMoldDetail(ErpMoldDetail erpMoldDetail);

    /**
     * 修改模具明细
     * 
     * @param erpMoldDetail 模具明细
     * @return 结果
     */
    public int updateErpMoldDetail(ErpMoldDetail erpMoldDetail);

    /**
     * 批量删除模具明细
     * 
     * @param ids 需要删除的模具明细主键集合
     * @return 结果
     */
    public int deleteErpMoldDetailByIds(Long[] ids);

    /**
     * 删除模具明细信息
     * 
     * @param id 模具明细主键
     * @return 结果
     */
    public int deleteErpMoldDetailById(Long id);
}
