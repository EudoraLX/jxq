package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionCalendar;
import com.jxqvue.erp.domain.ErpProductionCalendarDetail;

/**
 * 生产日历Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface ErpProductionCalendarMapper 
{
    /**
     * 查询生产日历
     * 
     * @param id 生产日历主键
     * @return 生产日历
     */
    public ErpProductionCalendar selectErpProductionCalendarById(Long id);

    /**
     * 查询生产日历列表
     * 
     * @param erpProductionCalendar 生产日历
     * @return 生产日历集合
     */
    public List<ErpProductionCalendar> selectErpProductionCalendarList(ErpProductionCalendar erpProductionCalendar);

    /**
     * 根据生产线ID查询生产日历
     * 
     * @param productionLineId 生产线ID
     * @return 生产日历集合
     */
    public List<ErpProductionCalendar> selectErpProductionCalendarByLineId(Long productionLineId);

    /**
     * 根据日期范围查询生产日历
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 生产日历集合
     */
    public List<ErpProductionCalendar> selectErpProductionCalendarByDateRange(String startDate, String endDate);

    /**
     * 新增生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    public int insertErpProductionCalendar(ErpProductionCalendar erpProductionCalendar);

    /**
     * 修改生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    public int updateErpProductionCalendar(ErpProductionCalendar erpProductionCalendar);

    /**
     * 删除生产日历
     * 
     * @param id 生产日历主键
     * @return 结果
     */
    public int deleteErpProductionCalendarById(Long id);

    /**
     * 批量删除生产日历
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionCalendarByIds(Long[] ids);

    /**
     * 批量删除生产日历明细
     * 
     * @param calendarIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionCalendarDetailByCalendarIds(Long[] calendarIds);

    /**
     * 批量新增生产日历明细
     * 
     * @param erpProductionCalendarDetailList 生产日历明细列表
     * @return 结果
     */
    public int batchErpProductionCalendarDetail(List<ErpProductionCalendarDetail> erpProductionCalendarDetailList);

    /**
     * 通过生产日历主键删除生产日历明细信息
     * 
     * @param calendarId 生产日历ID
     * @return 结果
     */
    public int deleteErpProductionCalendarDetailByCalendarId(Long calendarId);
}