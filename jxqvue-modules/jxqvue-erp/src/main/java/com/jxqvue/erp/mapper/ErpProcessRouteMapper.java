package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessRoute;
import com.jxqvue.erp.domain.ErpProcessRouteDetail;

/**
 * 工艺路线主表Mapper接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface ErpProcessRouteMapper 
{
    /**
     * 查询工艺路线主表
     * 
     * @param id 工艺路线主表主键
     * @return 工艺路线主表
     */
    public ErpProcessRoute selectErpProcessRouteById(Long id);

    /**
     * 查询工艺路线主表列表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 工艺路线主表集合
     */
    public List<ErpProcessRoute> selectErpProcessRouteList(ErpProcessRoute erpProcessRoute);

    /**
     * 新增工艺路线主表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 结果
     */
    public int insertErpProcessRoute(ErpProcessRoute erpProcessRoute);

    /**
     * 修改工艺路线主表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 结果
     */
    public int updateErpProcessRoute(ErpProcessRoute erpProcessRoute);

    /**
     * 删除工艺路线主表
     * 
     * @param id 工艺路线主表主键
     * @return 结果
     */
    public int deleteErpProcessRouteById(Long id);

    /**
     * 批量删除工艺路线主表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessRouteByIds(Long[] ids);

    /**
     * 批量删除工艺路线明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessRouteDetailByProcessRouteIds(Long[] ids);
    
    /**
     * 批量新增工艺路线明细表
     * 
     * @param erpProcessRouteDetailList 工艺路线明细表列表
     * @return 结果
     */
    public int batchErpProcessRouteDetail(List<ErpProcessRouteDetail> erpProcessRouteDetailList);
    

    /**
     * 通过工艺路线主表主键删除工艺路线明细表信息
     * 
     * @param id 工艺路线主表ID
     * @return 结果
     */
    public int deleteErpProcessRouteDetailByProcessRouteId(Long id);
}
