package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpMoldDetail;

/**
 * 模具明细Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface ErpMoldDetailMapper 
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
     * 删除模具明细
     * 
     * @param id 模具明细主键
     * @return 结果
     */
    public int deleteErpMoldDetailById(Long id);

    /**
     * 批量删除模具明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpMoldDetailByIds(Long[] ids);
}
