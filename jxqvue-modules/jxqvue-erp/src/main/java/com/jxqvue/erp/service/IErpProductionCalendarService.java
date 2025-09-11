package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionCalendar;

/**
 * 生产日历Service接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface IErpProductionCalendarService 
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
     * 批量删除生产日历
     * 
     * @param ids 需要删除的生产日历主键集合
     * @return 结果
     */
    public int deleteErpProductionCalendarByIds(Long[] ids);

    /**
     * 删除生产日历信息
     * 
     * @param id 生产日历主键
     * @return 结果
     */
    public int deleteErpProductionCalendarById(Long id);

    /**
     * 生成生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    public int generateProductionCalendar(ErpProductionCalendar erpProductionCalendar);

    /**
     * 复制生产日历
     * 
     * @param sourceCalendarId 源日历ID
     * @param targetCalendar 目标日历
     * @return 结果
     */
    public int copyProductionCalendar(Long sourceCalendarId, ErpProductionCalendar targetCalendar);
}