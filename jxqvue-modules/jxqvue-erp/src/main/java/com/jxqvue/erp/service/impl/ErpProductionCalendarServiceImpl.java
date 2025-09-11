package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpProductionCalendarMapper;
import com.jxqvue.erp.domain.ErpProductionCalendar;
import com.jxqvue.erp.domain.ErpProductionCalendarDetail;
import com.jxqvue.erp.service.IErpProductionCalendarService;

/**
 * 生产日历Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@Service
public class ErpProductionCalendarServiceImpl implements IErpProductionCalendarService 
{
    @Autowired
    private ErpProductionCalendarMapper erpProductionCalendarMapper;

    /**
     * 查询生产日历
     * 
     * @param id 生产日历主键
     * @return 生产日历
     */
    @Override
    public ErpProductionCalendar selectErpProductionCalendarById(Long id)
    {
        return erpProductionCalendarMapper.selectErpProductionCalendarById(id);
    }

    /**
     * 查询生产日历列表
     * 
     * @param erpProductionCalendar 生产日历
     * @return 生产日历
     */
    @Override
    public List<ErpProductionCalendar> selectErpProductionCalendarList(ErpProductionCalendar erpProductionCalendar)
    {
        return erpProductionCalendarMapper.selectErpProductionCalendarList(erpProductionCalendar);
    }

    /**
     * 根据生产线ID查询生产日历
     * 
     * @param productionLineId 生产线ID
     * @return 生产日历集合
     */
    @Override
    public List<ErpProductionCalendar> selectErpProductionCalendarByLineId(Long productionLineId)
    {
        return erpProductionCalendarMapper.selectErpProductionCalendarByLineId(productionLineId);
    }

    /**
     * 根据日期范围查询生产日历
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 生产日历集合
     */
    @Override
    public List<ErpProductionCalendar> selectErpProductionCalendarByDateRange(String startDate, String endDate)
    {
        return erpProductionCalendarMapper.selectErpProductionCalendarByDateRange(startDate, endDate);
    }

    /**
     * 新增生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    @Transactional
    @Override
    public int insertErpProductionCalendar(ErpProductionCalendar erpProductionCalendar)
    {
        erpProductionCalendar.setCreateTime(DateUtils.getNowDate());
        erpProductionCalendar.setCreateBy(SecurityUtils.getUsername());
        erpProductionCalendar.setDelFlag("0");
        
        int rows = erpProductionCalendarMapper.insertErpProductionCalendar(erpProductionCalendar);
        insertErpProductionCalendarDetail(erpProductionCalendar);
        return rows;
    }

    /**
     * 修改生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    @Transactional
    @Override
    public int updateErpProductionCalendar(ErpProductionCalendar erpProductionCalendar)
    {
        erpProductionCalendar.setUpdateTime(DateUtils.getNowDate());
        erpProductionCalendar.setUpdateBy(SecurityUtils.getUsername());
        
        erpProductionCalendarMapper.deleteErpProductionCalendarDetailByCalendarId(erpProductionCalendar.getId());
        insertErpProductionCalendarDetail(erpProductionCalendar);
        return erpProductionCalendarMapper.updateErpProductionCalendar(erpProductionCalendar);
    }

    /**
     * 批量删除生产日历
     * 
     * @param ids 需要删除的生产日历主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpProductionCalendarByIds(Long[] ids)
    {
        erpProductionCalendarMapper.deleteErpProductionCalendarDetailByCalendarIds(ids);
        return erpProductionCalendarMapper.deleteErpProductionCalendarByIds(ids);
    }

    /**
     * 删除生产日历信息
     * 
     * @param id 生产日历主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpProductionCalendarById(Long id)
    {
        erpProductionCalendarMapper.deleteErpProductionCalendarDetailByCalendarId(id);
        return erpProductionCalendarMapper.deleteErpProductionCalendarById(id);
    }

    /**
     * 生成生产日历
     * 
     * @param erpProductionCalendar 生产日历
     * @return 结果
     */
    @Transactional
    @Override
    public int generateProductionCalendar(ErpProductionCalendar erpProductionCalendar)
    {
        // 设置基本信息
        erpProductionCalendar.setCreateTime(DateUtils.getNowDate());
        erpProductionCalendar.setCreateBy(SecurityUtils.getUsername());
        erpProductionCalendar.setDelFlag("0");
        
        // 插入主表
        int rows = erpProductionCalendarMapper.insertErpProductionCalendar(erpProductionCalendar);
        
        // 生成日历明细
        List<ErpProductionCalendarDetail> detailList = generateCalendarDetails(erpProductionCalendar);
        if (detailList.size() > 0) {
            erpProductionCalendarMapper.batchErpProductionCalendarDetail(detailList);
            
            // 计算总工作日数和工时
            int totalWorkDays = (int) detailList.stream().filter(detail -> "1".equals(detail.getIsWorkDay())).count();
            double totalWorkHours = detailList.stream()
                .filter(detail -> "1".equals(detail.getIsWorkDay()))
                .mapToDouble(detail -> detail.getWorkHours() != null ? detail.getWorkHours() : 0)
                .sum();
            
            // 更新主表统计信息
            erpProductionCalendar.setTotalWorkDays(totalWorkDays);
            erpProductionCalendar.setTotalWorkHours(totalWorkHours);
            erpProductionCalendarMapper.updateErpProductionCalendar(erpProductionCalendar);
        }
        
        return rows;
    }

    /**
     * 复制生产日历
     * 
     * @param sourceCalendarId 源日历ID
     * @param targetCalendar 目标日历
     * @return 结果
     */
    @Transactional
    @Override
    public int copyProductionCalendar(Long sourceCalendarId, ErpProductionCalendar targetCalendar)
    {
        // 获取源日历信息
        ErpProductionCalendar sourceCalendar = erpProductionCalendarMapper.selectErpProductionCalendarById(sourceCalendarId);
        if (sourceCalendar == null) {
            return 0;
        }
        
        // 复制基本信息
        targetCalendar.setCalendarType(sourceCalendar.getCalendarType());
        targetCalendar.setProductionLineId(sourceCalendar.getProductionLineId());
        targetCalendar.setStatus("1"); // 草稿状态
        targetCalendar.setIsActive("1"); // 启用状态
        targetCalendar.setCreateTime(DateUtils.getNowDate());
        targetCalendar.setCreateBy(SecurityUtils.getUsername());
        targetCalendar.setDelFlag("0");
        
        // 插入目标日历
        int rows = erpProductionCalendarMapper.insertErpProductionCalendar(targetCalendar);
        
        // 复制日历明细
        List<ErpProductionCalendarDetail> sourceDetails = sourceCalendar.getErpProductionCalendarDetailList();
        if (sourceDetails != null && sourceDetails.size() > 0) {
            List<ErpProductionCalendarDetail> targetDetails = new ArrayList<>();
            for (ErpProductionCalendarDetail sourceDetail : sourceDetails) {
                ErpProductionCalendarDetail targetDetail = new ErpProductionCalendarDetail();
                targetDetail.setCalendarId(targetCalendar.getId());
                targetDetail.setWorkDate(sourceDetail.getWorkDate());
                targetDetail.setDayOfWeek(sourceDetail.getDayOfWeek());
                targetDetail.setShiftType(sourceDetail.getShiftType());
                targetDetail.setShiftStartTime(sourceDetail.getShiftStartTime());
                targetDetail.setShiftEndTime(sourceDetail.getShiftEndTime());
                targetDetail.setWorkHours(sourceDetail.getWorkHours());
                targetDetail.setBreakHours(sourceDetail.getBreakHours());
                targetDetail.setWorkEfficiency(sourceDetail.getWorkEfficiency());
                targetDetail.setIsWorkDay(sourceDetail.getIsWorkDay());
                targetDetail.setTemperatureControl(sourceDetail.getTemperatureControl());
                targetDetail.setPressureControl(sourceDetail.getPressureControl());
                targetDetail.setMoldMaintenance(sourceDetail.getMoldMaintenance());
                targetDetail.setEquipmentMaintenance(sourceDetail.getEquipmentMaintenance());
                targetDetail.setRemark(sourceDetail.getRemark());
                targetDetail.setCreateBy(SecurityUtils.getUsername());
                targetDetail.setCreateTime(DateUtils.getNowDate());
                targetDetail.setDelFlag("0");
                targetDetails.add(targetDetail);
            }
            erpProductionCalendarMapper.batchErpProductionCalendarDetail(targetDetails);
            
            // 复制统计信息
            targetCalendar.setTotalWorkDays(sourceCalendar.getTotalWorkDays());
            targetCalendar.setTotalWorkHours(sourceCalendar.getTotalWorkHours());
            erpProductionCalendarMapper.updateErpProductionCalendar(targetCalendar);
        }
        
        return rows;
    }

    /**
     * 新增生产日历明细信息
     * 
     * @param erpProductionCalendar 生产日历对象
     */
    public void insertErpProductionCalendarDetail(ErpProductionCalendar erpProductionCalendar)
    {
        List<ErpProductionCalendarDetail> erpProductionCalendarDetailList = erpProductionCalendar.getErpProductionCalendarDetailList();
        Long id = erpProductionCalendar.getId();
        if (erpProductionCalendarDetailList != null && erpProductionCalendarDetailList.size() > 0)
        {
            List<ErpProductionCalendarDetail> list = new ArrayList<ErpProductionCalendarDetail>();
            for (ErpProductionCalendarDetail erpProductionCalendarDetail : erpProductionCalendarDetailList)
            {
                erpProductionCalendarDetail.setCalendarId(id);
                erpProductionCalendarDetail.setCreateBy(SecurityUtils.getUsername());
                erpProductionCalendarDetail.setCreateTime(DateUtils.getNowDate());
                erpProductionCalendarDetail.setDelFlag("0");
                list.add(erpProductionCalendarDetail);
            }
            if (list.size() > 0)
            {
                erpProductionCalendarMapper.batchErpProductionCalendarDetail(list);
            }
        }
    }

    /**
     * 生成日历明细
     * 
     * @param erpProductionCalendar 生产日历
     * @return 日历明细列表
     */
    private List<ErpProductionCalendarDetail> generateCalendarDetails(ErpProductionCalendar erpProductionCalendar)
    {
        List<ErpProductionCalendarDetail> detailList = new ArrayList<>();
        
        Date startDate = erpProductionCalendar.getEffectiveStartDate();
        Date endDate = erpProductionCalendar.getEffectiveEndDate();
        
        if (startDate == null || endDate == null) {
            return detailList;
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        
        while (!calendar.getTime().after(endDate)) {
            Date currentDate = calendar.getTime();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            
            // 判断是否为工作日（周一到周五）
            boolean isWorkDay = dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY;
            
            // 生成班次明细（默认早班和中班）
            if (isWorkDay) {
                // 早班
                ErpProductionCalendarDetail morningShift = new ErpProductionCalendarDetail();
                morningShift.setCalendarId(erpProductionCalendar.getId());
                morningShift.setWorkDate(currentDate);
                morningShift.setDayOfWeek(dayOfWeek);
                morningShift.setShiftType("1"); // 早班
                morningShift.setWorkHours(8.0);
                morningShift.setBreakHours(1.0);
                morningShift.setWorkEfficiency(100.0);
                morningShift.setIsWorkDay("1");
                morningShift.setTemperatureControl("0");
                morningShift.setPressureControl("0");
                morningShift.setMoldMaintenance("0");
                morningShift.setEquipmentMaintenance("0");
                morningShift.setCreateBy(SecurityUtils.getUsername());
                morningShift.setCreateTime(DateUtils.getNowDate());
                morningShift.setDelFlag("0");
                detailList.add(morningShift);
                
                // 中班
                ErpProductionCalendarDetail afternoonShift = new ErpProductionCalendarDetail();
                afternoonShift.setCalendarId(erpProductionCalendar.getId());
                afternoonShift.setWorkDate(currentDate);
                afternoonShift.setDayOfWeek(dayOfWeek);
                afternoonShift.setShiftType("2"); // 中班
                afternoonShift.setWorkHours(8.0);
                afternoonShift.setBreakHours(1.0);
                afternoonShift.setWorkEfficiency(100.0);
                afternoonShift.setIsWorkDay("1");
                afternoonShift.setTemperatureControl("0");
                afternoonShift.setPressureControl("0");
                afternoonShift.setMoldMaintenance("0");
                afternoonShift.setEquipmentMaintenance("0");
                afternoonShift.setCreateBy(SecurityUtils.getUsername());
                afternoonShift.setCreateTime(DateUtils.getNowDate());
                afternoonShift.setDelFlag("0");
                detailList.add(afternoonShift);
            } else {
                // 周末班次（全天班）
                ErpProductionCalendarDetail weekendShift = new ErpProductionCalendarDetail();
                weekendShift.setCalendarId(erpProductionCalendar.getId());
                weekendShift.setWorkDate(currentDate);
                weekendShift.setDayOfWeek(dayOfWeek);
                weekendShift.setShiftType("4"); // 全天班
                weekendShift.setWorkHours(8.0);
                weekendShift.setBreakHours(1.0);
                weekendShift.setWorkEfficiency(100.0);
                weekendShift.setIsWorkDay("1");
                weekendShift.setTemperatureControl("0");
                weekendShift.setPressureControl("0");
                weekendShift.setMoldMaintenance("0");
                weekendShift.setEquipmentMaintenance("0");
                weekendShift.setCreateBy(SecurityUtils.getUsername());
                weekendShift.setCreateTime(DateUtils.getNowDate());
                weekendShift.setDelFlag("0");
                detailList.add(weekendShift);
            }
            
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        return detailList;
    }
}