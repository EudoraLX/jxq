package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessRoute;

/**
 * 工艺路线主表Service接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface IErpProcessRouteService 
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
     * 批量删除工艺路线主表
     * 
     * @param ids 需要删除的工艺路线主表主键集合
     * @return 结果
     */
    public int deleteErpProcessRouteByIds(Long[] ids);

    /**
     * 删除工艺路线主表信息
     * 
     * @param id 工艺路线主表主键
     * @return 结果
     */
    public int deleteErpProcessRouteById(Long id);
}
