package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工作中心配置对象 erp_work_center_config
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpWorkCenterConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配置ID */
    private Long id;

    /** 中心编码 */
    @Excel(name = "中心编码")
    private Long workCenterId;

    /** 设备 */
    @Excel(name = "设备")
    private Long equipmentId;

    /** 模具 */
    @Excel(name = "模具")
    private Long moldId;

    /** 产能类型 */
    @Excel(name = "产能类型", readConverterExp = "1=按小时,2=按件数,3=按批次")
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

    /** 准备时间(分钟) */
    @Excel(name = "准备时间(分钟)")
    private BigDecimal setupTime;

    private BigDecimal processingTime;
    private BigDecimal moveTime;
    private BigDecimal queueTime;
    private BigDecimal cycleTime;

    /** 是否首选 */
    @Excel(name = "是否首选", readConverterExp = "0=否,1=是")
    private String isPrimary;

    // 关联字段（用于显示）
    /** 设备编码 */
    @Excel(name = "设备编码")
    private String equipmentCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 模具编码 */
    @Excel(name = "模具编码")
    private String moldCode;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWorkCenterId(Long workCenterId) 
    {
        this.workCenterId = workCenterId;
    }

    public Long getWorkCenterId() 
    {
        return workCenterId;
    }
    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }
    public void setMoldId(Long moldId) 
    {
        this.moldId = moldId;
    }

    public Long getMoldId() 
    {
        return moldId;
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
    public void setSetupTime(BigDecimal setupTime) 
    {
        this.setupTime = setupTime;
    }

    public BigDecimal getSetupTime() 
    {
        return setupTime;
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

    public void setIsPrimary(String isPrimary)
    {
        this.isPrimary = isPrimary;
    }

    public String getIsPrimary() 
    {
        return isPrimary;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getMoldCode() 
    {
        return moldCode;
    }

    public void setMoldCode(String moldCode) 
    {
        this.moldCode = moldCode;
    }

    public String getMoldName() 
    {
        return moldName;
    }

    public void setMoldName(String moldName) 
    {
        this.moldName = moldName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workCenterId", getWorkCenterId())
            .append("equipmentId", getEquipmentId())
            .append("moldId", getMoldId())
            .append("capacityType", getCapacityType())
            .append("standardCapacity", getStandardCapacity())
            .append("capacityUnit", getCapacityUnit())
            .append("efficiencyRate", getEfficiencyRate())
            .append("availableCapacity", getAvailableCapacity())
            .append("setupTime", getSetupTime())
                .append("processingTime", getProcessingTime())
                .append("moveTime", getMoveTime())
                .append("queueTime", getQueueTime())
                .append("cycleTime", getCycleTime())
            .append("isPrimary", getIsPrimary())
            .append("remark", getRemark())
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("moldCode", getMoldCode())
            .append("moldName", getMoldName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
