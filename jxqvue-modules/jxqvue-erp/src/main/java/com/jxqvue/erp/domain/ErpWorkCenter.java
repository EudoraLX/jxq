package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工作中心对象 erp_work_center
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpWorkCenter extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工作中心ID */
    private Long id;

    /** 中心编码 */
    @Excel(name = "中心编码")
    private String workCenterCode;

    /** 中心名称 */
    @Excel(name = "中心名称")
    private String workCenterName;

    /** 中心类型 */
    @Excel(name = "中心类型")
    private String workCenterType;

    /** 部门 */
    @Excel(name = "部门")
    private Long departmentId;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 产能类型 */
    @Excel(name = "产能类型")
    private String capacityType;

    /** 标准产能 */
    @Excel(name = "标准产能")
    private BigDecimal standardCapacity;

    /** 产能单位 */
    @Excel(name = "产能单位")
    private String capacityUnit;

    /** 效率系数 */
    @Excel(name = "效率系数")
    private BigDecimal efficiencyRate;

    /** 可用产能 */
    @Excel(name = "可用产能")
    private BigDecimal availableCapacity;

    /** 成本中心 */
    private String costCenter;

    /** 小时成本 */
    private BigDecimal costPerHour;

    /** 准备时间(分钟) */
    private BigDecimal setupTime;

    private BigDecimal processingTime;
    private BigDecimal moveTime;
    private BigDecimal queueTime;
    private BigDecimal cycleTime;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String isActive;

    /** 删除标志（0代表存在，1代表删除） */
    private String delFlag;

    /** 工作中心配置列表 */
    private List<ErpWorkCenterConfig> configList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setWorkCenterCode(String workCenterCode) 
    {
        this.workCenterCode = workCenterCode;
    }

    public String getWorkCenterCode() 
    {
        return workCenterCode;
    }

    public void setWorkCenterName(String workCenterName) 
    {
        this.workCenterName = workCenterName;
    }

    public String getWorkCenterName() 
    {
        return workCenterName;
    }

    public void setWorkCenterType(String workCenterType) 
    {
        this.workCenterType = workCenterType;
    }

    public String getWorkCenterType() 
    {
        return workCenterType;
    }

    public void setDepartmentId(Long departmentId) 
    {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() 
    {
        return departmentId;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setCapacityType(String capacityType) 
    {
        this.capacityType = capacityType;
    }

    public String getCapacityType() 
    {
        return capacityType;
    }

    public void setStandardCapacity(BigDecimal standardCapacity) 
    {
        this.standardCapacity = standardCapacity;
    }

    public BigDecimal getStandardCapacity() 
    {
        return standardCapacity;
    }

    public void setCapacityUnit(String capacityUnit) 
    {
        this.capacityUnit = capacityUnit;
    }

    public String getCapacityUnit() 
    {
        return capacityUnit;
    }

    public void setEfficiencyRate(BigDecimal efficiencyRate) 
    {
        this.efficiencyRate = efficiencyRate;
    }

    public BigDecimal getEfficiencyRate() 
    {
        return efficiencyRate;
    }

    public void setAvailableCapacity(BigDecimal availableCapacity) 
    {
        this.availableCapacity = availableCapacity;
    }

    public BigDecimal getAvailableCapacity() 
    {
        return availableCapacity;
    }

    public void setCostCenter(String costCenter) 
    {
        this.costCenter = costCenter;
    }

    public String getCostCenter() 
    {
        return costCenter;
    }

    public void setCostPerHour(BigDecimal costPerHour) 
    {
        this.costPerHour = costPerHour;
    }

    public BigDecimal getCostPerHour() 
    {
        return costPerHour;
    }

    public void setSetupTime(BigDecimal setupTime) 
    {
        this.setupTime = setupTime;
    }

    public BigDecimal getSetupTime() 
    {
        return setupTime;
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

    public BigDecimal getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(BigDecimal processingTime) {
        this.processingTime = processingTime;
    }

    public BigDecimal getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(BigDecimal moveTime) {
        this.moveTime = moveTime;
    }

    public BigDecimal getQueueTime() {
        return queueTime;
    }

    public void setQueueTime(BigDecimal queueTime) {
        this.queueTime = queueTime;
    }

    public BigDecimal getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(BigDecimal cycleTime) {
        this.cycleTime = cycleTime;
    }

    public List<ErpWorkCenterConfig> getConfigList()
    {
        return configList;
    }

    public void setConfigList(List<ErpWorkCenterConfig> configList) 
    {
        this.configList = configList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workCenterCode", getWorkCenterCode())
            .append("workCenterName", getWorkCenterName())
            .append("workCenterType", getWorkCenterType())
            .append("departmentId", getDepartmentId())
            .append("location", getLocation())
            .append("capacityType", getCapacityType())
            .append("standardCapacity", getStandardCapacity())
            .append("capacityUnit", getCapacityUnit())
            .append("efficiencyRate", getEfficiencyRate())
            .append("availableCapacity", getAvailableCapacity())
            .append("costCenter", getCostCenter())
            .append("costPerHour", getCostPerHour())
            .append("setupTime", getSetupTime())
                .append("processingTime", getProcessingTime())
                .append("moveTime", getMoveTime())
                .append("queueTime", getQueueTime())
                .append("cycleTime", getCycleTime())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("configList", getConfigList())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
