package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpBom;
import com.jxqvue.erp.domain.ErpBomDetail;
import com.jxqvue.erp.domain.ErpBomRouteDetail;

/**
 * BOM主表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public interface ErpBomMapper 
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
     * 删除BOM主表
     * 
     * @param id BOM主表主键
     * @return 结果
     */
    public int deleteErpBomById(Long id);

    /**
     * 批量删除BOM主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpBomByIds(Long[] ids);

    /**
     * 批量新增BOM明细
     * 
     * @param erpBomDetailList BOM明细列表
     * @return 结果
     */
    public int batchErpBomDetail(List<ErpBomDetail> erpBomDetailList);
    

    /**
     * 通过BOM主表主键删除BOM明细信息
     * 
     * @param bomId BOM主表ID
     * @return 结果
     */
    public int deleteErpBomDetailByBomId(Long bomId);
    
    /**
     * 通过BOM主表主键批量删除BOM明细信息
     * 
     * @param bomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpBomDetailByBomIds(Long[] bomIds);

    /**
     * 批量新增BOM工艺路线明细
     * 
     * @param erpBomRouteDetailList BOM工艺路线明细列表
     * @return 结果
     */
    public int batchErpBomRouteDetail(List<ErpBomRouteDetail> erpBomRouteDetailList);
    

    /**
     * 通过BOM主表主键删除BOM工艺路线明细信息
     * 
     * @param bomId BOM主表ID
     * @return 结果
     */
    public int deleteErpBomRouteDetailByBomId(Long bomId);
    
    /**
     * 通过BOM主表主键批量删除BOM工艺路线明细信息
     * 
     * @param bomIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpBomRouteDetailByBomIds(Long[] bomIds);

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
     * 更新BOM失效日期
     * 
     * @param bomId BOM ID
     * @param expiryDate 失效日期
     * @return 结果
     */
    public int updateBomExpiryDate(Long bomId, java.util.Date expiryDate);

    /**
     * 审批BOM
     * 
     * @param bomId BOM ID
     * @return 结果
     */
    public int approveBom(Long bomId);
}
