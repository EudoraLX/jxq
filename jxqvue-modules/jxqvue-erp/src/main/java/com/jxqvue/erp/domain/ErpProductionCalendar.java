package com.jxqvue.erp.domain;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产日历对象 erp_production_calendar
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProductionCalendar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 日历编码 */
    @Excel(name = "日历编码")
    private String calendarCode;

    /** 日历名称 */
    @Excel(name = "日历名称")
    private String calendarName;

    /** 日历类型(1:年度 2:季度 3:月度 4:周度 5:日度) */
    @Excel(name = "日历类型", readConverterExp = "1=年度,2=季度,3=月度,4=周度,5=日度")
    private String calendarType;

    /** 生产线ID */
    @Excel(name = "生产线ID")
    private Long productionLineId;

    /** 生产线名称 */
    private String productionLineName;

    /** 年份 */
    @Excel(name = "年份")
    private Integer year;

    /** 月份 */
    @Excel(name = "月份")
    private Integer month;

    /** 周次 */
    @Excel(name = "周次")
    private Integer week;

    /** 生效开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveStartDate;

    /** 生效结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveEndDate;

    /** 总工作日数 */
    @Excel(name = "总工作日数")
    private Integer totalWorkDays;

    /** 总工作小时数 */
    @Excel(name = "总工作小时数")
    private Double totalWorkHours;

    /** 状态(1:草稿 2:生效 3:过期) */
    @Excel(name = "状态", readConverterExp = "1=草稿,2=生效,3=过期")
    private String status;

    /** 是否启用(0:禁用 1:启用) */
    @Excel(name = "是否启用", readConverterExp = "0=禁用,1=启用")
    private String isActive;

    /** 删除标志(0:正常 1:删除) */
    private String delFlag;

    /** 生产日历明细信息 */
    private List<ErpProductionCalendarDetail> erpProductionCalendarDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCalendarCode(String calendarCode) 
    {
        this.calendarCode = calendarCode;
    }

    public String getCalendarCode() 
    {
        return calendarCode;
    }

    public void setCalendarName(String calendarName) 
    {
        this.calendarName = calendarName;
    }

    public String getCalendarName() 
    {
        return calendarName;
    }

    public void setCalendarType(String calendarType) 
    {
        this.calendarType = calendarType;
    }

    public String getCalendarType() 
    {
        return calendarType;
    }

    public void setProductionLineId(Long productionLineId) 
    {
        this.productionLineId = productionLineId;
    }

    public Long getProductionLineId() 
    {
        return productionLineId;
    }

    public void setProductionLineName(String productionLineName) 
    {
        this.productionLineName = productionLineName;
    }

    public String getProductionLineName() 
    {
        return productionLineName;
    }

    public void setYear(Integer year) 
    {
        this.year = year;
    }

    public Integer getYear() 
    {
        return year;
    }

    public void setMonth(Integer month) 
    {
        this.month = month;
    }

    public Integer getMonth() 
    {
        return month;
    }

    public void setWeek(Integer week) 
    {
        this.week = week;
    }

    public Integer getWeek() 
    {
        return week;
    }

    public void setEffectiveStartDate(Date effectiveStartDate) 
    {
        this.effectiveStartDate = effectiveStartDate;
    }

    public Date getEffectiveStartDate() 
    {
        return effectiveStartDate;
    }

    public void setEffectiveEndDate(Date effectiveEndDate) 
    {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Date getEffectiveEndDate() 
    {
        return effectiveEndDate;
    }

    public void setTotalWorkDays(Integer totalWorkDays) 
    {
        this.totalWorkDays = totalWorkDays;
    }

    public Integer getTotalWorkDays() 
    {
        return totalWorkDays;
    }

    public void setTotalWorkHours(Double totalWorkHours) 
    {
        this.totalWorkHours = totalWorkHours;
    }

    public Double getTotalWorkHours() 
    {
        return totalWorkHours;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public List<ErpProductionCalendarDetail> getErpProductionCalendarDetailList()
    {
        return erpProductionCalendarDetailList;
    }

    public void setErpProductionCalendarDetailList(List<ErpProductionCalendarDetail> erpProductionCalendarDetailList)
    {
        this.erpProductionCalendarDetailList = erpProductionCalendarDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("calendarCode", getCalendarCode())
            .append("calendarName", getCalendarName())
            .append("calendarType", getCalendarType())
            .append("productionLineId", getProductionLineId())
            .append("productionLineName", getProductionLineName())
            .append("year", getYear())
            .append("month", getMonth())
            .append("week", getWeek())
            .append("effectiveStartDate", getEffectiveStartDate())
            .append("effectiveEndDate", getEffectiveEndDate())
            .append("totalWorkDays", getTotalWorkDays())
            .append("totalWorkHours", getTotalWorkHours())
            .append("status", getStatus())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("erpProductionCalendarDetailList", getErpProductionCalendarDetailList())
            .toString();
    }
}