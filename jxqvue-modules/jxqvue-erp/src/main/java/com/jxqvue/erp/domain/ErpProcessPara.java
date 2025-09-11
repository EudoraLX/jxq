package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工艺参数明细对象 erp_process_para
 *
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProcessPara extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long processId;

    /**
     * 参数类型
     */
    @Excel(name = "参数类型")
    private String parameterType;

    /**
     * 参数名称
     */
    @Excel(name = "参数名称")
    private String parameterName;

    /**
     * 参数编码
     */
    @Excel(name = "参数编码")
    private String parameterCode;

    /**
     * 参数单位
     */
    @Excel(name = "参数单位")
    private String parameterUnit;

    /**
     * 标准值
     */
    @Excel(name = "标准值")
    private BigDecimal standardValue;

    /**
     * 最小值
     */
    @Excel(name = "最小值")
    private BigDecimal minValue;

    /**
     * 最大值
     */
    @Excel(name = "最大值")
    private BigDecimal maxValue;

    /**
     * 公差范围
     */
    @Excel(name = "公差范围")
    private BigDecimal tolerance;

    /**
     * 控制方法
     */
    @Excel(name = "控制方法")
    private String controlMethod;

    /**
     * 监控点
     */
    @Excel(name = "监控点")
    private String monitoringPoint;

    /**
     * 报警上限
     */
    @Excel(name = "报警上限")
    private BigDecimal alarmUpper;

    /**
     * 报警下限
     */
    @Excel(name = "报警下限")
    private BigDecimal alarmLower;

    /**
     * 是否关键参数
     */
    @Excel(name = "是否关键参数", readConverterExp = "0=否,1=是")
    private String isCritical;

    /**
     * 是否监控
     */
    @Excel(name = "是否监控", readConverterExp = "0=否,1=是")
    private String isMonitored;

    /**
     * 排序顺序
     */
    @Excel(name = "排序顺序")
    private Integer sortOrder;

    /**
     * 监控间隔(分)
     */
    @Excel(name = "监控间隔(分)")
    private Integer monitoringInterval;

    /**
     * 删除标志
     */
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterCode(String parameterCode) {
        this.parameterCode = parameterCode;
    }

    public String getParameterCode() {
        return parameterCode;
    }

    public void setParameterUnit(String parameterUnit) {
        this.parameterUnit = parameterUnit;
    }

    public String getParameterUnit() {
        return parameterUnit;
    }

    public void setStandardValue(BigDecimal standardValue) {
        this.standardValue = standardValue;
    }

    public BigDecimal getStandardValue() {
        return standardValue;
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getMinValue() {
        return minValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMaxValue() {
        return maxValue;
    }

    public void setTolerance(BigDecimal tolerance) {
        this.tolerance = tolerance;
    }

    public BigDecimal getTolerance() {
        return tolerance;
    }

    public void setControlMethod(String controlMethod) {
        this.controlMethod = controlMethod;
    }

    public String getControlMethod() {
        return controlMethod;
    }

    public void setMonitoringPoint(String monitoringPoint) {
        this.monitoringPoint = monitoringPoint;
    }

    public String getMonitoringPoint() {
        return monitoringPoint;
    }

    public void setAlarmUpper(BigDecimal alarmUpper) {
        this.alarmUpper = alarmUpper;
    }

    public BigDecimal getAlarmUpper() {
        return alarmUpper;
    }

    public void setAlarmLower(BigDecimal alarmLower) {
        this.alarmLower = alarmLower;
    }

    public BigDecimal getAlarmLower() {
        return alarmLower;
    }

    public void setIsCritical(String isCritical) {
        this.isCritical = isCritical;
    }

    public String getIsCritical() {
        return isCritical;
    }

    public void setIsMonitored(String isMonitored) {
        this.isMonitored = isMonitored;
    }

    public String getIsMonitored() {
        return isMonitored;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setMonitoringInterval(Integer monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }

    public Integer getMonitoringInterval() {
        return monitoringInterval;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("processId", getProcessId())
                .append("parameterType", getParameterType())
                .append("parameterName", getParameterName())
                .append("parameterCode", getParameterCode())
                .append("parameterUnit", getParameterUnit())
                .append("standardValue", getStandardValue())
                .append("minValue", getMinValue())
                .append("maxValue", getMaxValue())
                .append("tolerance", getTolerance())
                .append("controlMethod", getControlMethod())
                .append("monitoringPoint", getMonitoringPoint())
                .append("alarmUpper", getAlarmUpper())
                .append("alarmLower", getAlarmLower())
                .append("isCritical", getIsCritical())
                .append("isMonitored", getIsMonitored())
                .append("sortOrder", getSortOrder())
                .append("monitoringInterval", getMonitoringInterval())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}

