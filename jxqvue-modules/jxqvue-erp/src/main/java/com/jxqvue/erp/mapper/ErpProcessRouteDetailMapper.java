package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessRouteDetail;

/**
 * 工艺路线明细Mapper接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface ErpProcessRouteDetailMapper 
{
    /**
     * 查询工艺路线明细
     * 
     * @param id 工艺路线明细主键
     * @return 工艺路线明细
     */
    public ErpProcessRouteDetail selectErpProcessRouteDetailById(Long id);

    /**
     * 查询工艺路线明细列表
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 工艺路线明细集合
     */
    public List<ErpProcessRouteDetail> selectErpProcessRouteDetailList(ErpProcessRouteDetail erpProcessRouteDetail);

    /**
     * 根据工艺路线ID查询明细列表
     * 
     * @param processRouteId 工艺路线ID
     * @return 工艺路线明细集合
     */
    public List<ErpProcessRouteDetail> selectErpProcessRouteDetailByRouteId(Long processRouteId);

    /**
     * 新增工艺路线明细
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 结果
     */
    public int insertErpProcessRouteDetail(ErpProcessRouteDetail erpProcessRouteDetail);

    /**
     * 修改工艺路线明细
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 结果
     */
    public int updateErpProcessRouteDetail(ErpProcessRouteDetail erpProcessRouteDetail);

    /**
     * 删除工艺路线明细
     * 
     * @param id 工艺路线明细主键
     * @return 结果
     */
    public int deleteErpProcessRouteDetailById(Long id);

    /**
     * 批量删除工艺路线明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessRouteDetailByIds(Long[] ids);

    /**
     * 根据工艺路线ID删除明细
     * 
     * @param processRouteId 工艺路线ID
     * @return 结果
     */
    public int deleteErpProcessRouteDetailByRouteId(Long processRouteId);
}
