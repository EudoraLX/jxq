package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpBom;

/**
 * BOM主表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public interface IErpBomService 
{
    /**
     * 查询BOM主表
     * 
     * @param id BOM主表主键
     * @return BOM主表
     */
    public ErpBom selectErpBomById(Long id);

    /**
     * 查询BOM主表列表
     * 
     * @param erpBom BOM主表
     * @return BOM主表集合
     */
    public List<ErpBom> selectErpBomList(ErpBom erpBom);

    /**
     * 新增BOM主表
     * 
     * @param erpBom BOM主表
     * @return 结果
     */
    public int insertErpBom(ErpBom erpBom);

    /**
     * 修改BOM主表
     * 
     * @param erpBom BOM主表
     * @return 结果
     */
    public int updateErpBom(ErpBom erpBom);

    /**
     * 批量删除BOM主表
     * 
     * @param ids 需要删除的BOM主表主键集合
     * @return 结果
     */
    public int deleteErpBomByIds(Long[] ids);

    /**
     * 删除BOM主表信息
     * 
     * @param id BOM主表主键
     * @return 结果
     */
    public int deleteErpBomById(Long id);

    /**
     * 检查BOM冲突
     * 
     * @param productId 产品ID
     * @param effectiveDate 生效日期
     * @param excludeId 排除的BOM ID
     * @return 冲突的BOM列表
     */
    public List<ErpBom> checkBomConflict(Long productId, java.util.Date effectiveDate, Long excludeId);

    /**
     * 审批BOM
     * 
     * @param bomId BOM ID
     * @param forceApprove 是否强制审批
     * @return 结果
     */
    public com.jxqvue.common.core.domain.AjaxResult approveBom(Long bomId, Boolean forceApprove);
}
