package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionCalendarDetail;

/**
 * 生产日历明细Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IErpProductionCalendarDetailService 
{
    /**
     * 查询生产日历明细
     * 
     * @param detailId 生产日历明细主键
     * @return 生产日历明细
     */
    public ErpProductionCalendarDetail selectErpProductionCalendarDetailByDetailId(Long detailId);

    /**
     * 查询生产日历明细列表
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 生产日历明细集合
     */
    public List<ErpProductionCalendarDetail> selectErpProductionCalendarDetailList(ErpProductionCalendarDetail erpProductionCalendarDetail);

    /**
     * 新增生产日历明细
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 结果
     */
    public int insertErpProductionCalendarDetail(ErpProductionCalendarDetail erpProductionCalendarDetail);

    /**
     * 修改生产日历明细
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 结果
     */
    public int updateErpProductionCalendarDetail(ErpProductionCalendarDetail erpProductionCalendarDetail);

    /**
     * 批量删除生产日历明细
     * 
     * @param detailIds 需要删除的生产日历明细主键集合
     * @return 结果
     */
    public int deleteErpProductionCalendarDetailByDetailIds(Long[] detailIds);

    /**
     * 删除生产日历明细信息
     * 
     * @param detailId 生产日历明细主键
     * @return 结果
     */
    public int deleteErpProductionCalendarDetailByDetailId(Long detailId);
}
