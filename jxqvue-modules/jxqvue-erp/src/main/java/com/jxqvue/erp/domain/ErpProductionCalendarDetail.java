package com.jxqvue.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产日历明细对象 erp_production_calendar_detail
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProductionCalendarDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 日历ID */
    @Excel(name = "日历ID")
    private Long calendarId;

    /** 工作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDate;

    /** 星期几(1-7) */
    @Excel(name = "星期几")
    private Integer dayOfWeek;

    /** 班次类型(1:早班 2:中班 3:晚班 4:全天) */
    @Excel(name = "班次类型", readConverterExp = "1=早班,2=中班,3=晚班,4=全天")
    private String shiftType;

    /** 班次开始时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "班次开始时间", width = 30, dateFormat = "HH:mm:ss")
    private Date shiftStartTime;

    /** 班次结束时间 */
    @JsonFormat(pattern = "HH:mm:ss")
    @Excel(name = "班次结束时间", width = 30, dateFormat = "HH:mm:ss")
    private Date shiftEndTime;

    /** 工作小时数 */
    @Excel(name = "工作小时数")
    private Double workHours;

    /** 休息小时数 */
    @Excel(name = "休息小时数")
    private Double breakHours;

    /** 工作效率(%) */
    @Excel(name = "工作效率")
    private Double workEfficiency;

    /** 是否工作日(0:否 1:是) */
    @Excel(name = "是否工作日", readConverterExp = "0=否,1=是")
    private String isWorkDay;

    /** 是否需要温控(0:否 1:是) */
    @Excel(name = "是否需要温控", readConverterExp = "0=否,1=是")
    private String temperatureControl;

    /** 是否需要压力控制(0:否 1:是) */
    @Excel(name = "是否需要压力控制", readConverterExp = "0=否,1=是")
    private String pressureControl;

    /** 是否需要模具维护(0:否 1:是) */
    @Excel(name = "是否需要模具维护", readConverterExp = "0=否,1=是")
    private String moldMaintenance;

    /** 是否需要设备维护(0:否 1:是) */
    @Excel(name = "是否需要设备维护", readConverterExp = "0=否,1=是")
    private String equipmentMaintenance;

    /** 删除标志(0:正常 1:删除) */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCalendarId(Long calendarId) 
    {
        this.calendarId = calendarId;
    }

    public Long getCalendarId() 
    {
        return calendarId;
    }

    public void setWorkDate(Date workDate) 
    {
        this.workDate = workDate;
    }

    public Date getWorkDate() 
    {
        return workDate;
    }

    public void setDayOfWeek(Integer dayOfWeek) 
    {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getDayOfWeek() 
    {
        return dayOfWeek;
    }

    public void setShiftType(String shiftType) 
    {
        this.shiftType = shiftType;
    }

    public String getShiftType() 
    {
        return shiftType;
    }

    public void setShiftStartTime(Date shiftStartTime) 
    {
        this.shiftStartTime = shiftStartTime;
    }

    public Date getShiftStartTime() 
    {
        return shiftStartTime;
    }

    public void setShiftEndTime(Date shiftEndTime) 
    {
        this.shiftEndTime = shiftEndTime;
    }

    public Date getShiftEndTime() 
    {
        return shiftEndTime;
    }

    public void setWorkHours(Double workHours) 
    {
        this.workHours = workHours;
    }

    public Double getWorkHours() 
    {
        return workHours;
    }

    public void setBreakHours(Double breakHours) 
    {
        this.breakHours = breakHours;
    }

    public Double getBreakHours() 
    {
        return breakHours;
    }

    public void setWorkEfficiency(Double workEfficiency) 
    {
        this.workEfficiency = workEfficiency;
    }

    public Double getWorkEfficiency() 
    {
        return workEfficiency;
    }

    public void setIsWorkDay(String isWorkDay) 
    {
        this.isWorkDay = isWorkDay;
    }

    public String getIsWorkDay() 
    {
        return isWorkDay;
    }

    public void setTemperatureControl(String temperatureControl) 
    {
        this.temperatureControl = temperatureControl;
    }

    public String getTemperatureControl() 
    {
        return temperatureControl;
    }

    public void setPressureControl(String pressureControl) 
    {
        this.pressureControl = pressureControl;
    }

    public String getPressureControl() 
    {
        return pressureControl;
    }

    public void setMoldMaintenance(String moldMaintenance) 
    {
        this.moldMaintenance = moldMaintenance;
    }

    public String getMoldMaintenance() 
    {
        return moldMaintenance;
    }

    public void setEquipmentMaintenance(String equipmentMaintenance) 
    {
        this.equipmentMaintenance = equipmentMaintenance;
    }

    public String getEquipmentMaintenance() 
    {
        return equipmentMaintenance;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("calendarId", getCalendarId())
            .append("workDate", getWorkDate())
            .append("dayOfWeek", getDayOfWeek())
            .append("shiftType", getShiftType())
            .append("shiftStartTime", getShiftStartTime())
            .append("shiftEndTime", getShiftEndTime())
            .append("workHours", getWorkHours())
            .append("breakHours", getBreakHours())
            .append("workEfficiency", getWorkEfficiency())
            .append("isWorkDay", getIsWorkDay())
            .append("temperatureControl", getTemperatureControl())
            .append("pressureControl", getPressureControl())
            .append("moldMaintenance", getMoldMaintenance())
            .append("equipmentMaintenance", getEquipmentMaintenance())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}