package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpBomRouteDetail;

/**
 * BOM工艺路线明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public interface ErpBomRouteDetailMapper 
{
    /**
     * 查询BOM工艺路线明细表
     * 
     * @param id BOM工艺路线明细表主键
     * @return BOM工艺路线明细表
     */
    public ErpBomRouteDetail selectErpBomRouteDetailById(Long id);

    /**
     * 查询BOM工艺路线明细表列表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return BOM工艺路线明细表集合
     */
    public List<ErpBomRouteDetail> selectErpBomRouteDetailList(ErpBomRouteDetail erpBomRouteDetail);

    /**
     * 新增BOM工艺路线明细表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return 结果
     */
    public int insertErpBomRouteDetail(ErpBomRouteDetail erpBomRouteDetail);

    /**
     * 修改BOM工艺路线明细表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return 结果
     */
    public int updateErpBomRouteDetail(ErpBomRouteDetail erpBomRouteDetail);

    /**
     * 删除BOM工艺路线明细表
     * 
     * @param id BOM工艺路线明细表主键
     * @return 结果
     */
    public int deleteErpBomRouteDetailById(Long id);

    /**
     * 批量删除BOM工艺路线明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpBomRouteDetailByIds(Long[] ids);
}
