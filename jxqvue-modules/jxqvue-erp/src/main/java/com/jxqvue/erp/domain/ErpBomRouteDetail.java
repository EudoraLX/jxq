package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * BOM工艺路线明细表对象 erp_bom_route_detail
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public class ErpBomRouteDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** BOMID */
    @Excel(name = "BOMID")
    private Long bomId;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 工序编码（用于显示） */
    private String processCode;

    /** 工序名称（用于显示） */
    private String processName;

    /** 工序顺序 */
    @Excel(name = "工序顺序")
    private Integer operationSequence;

    /** 工作中心 */
    @Excel(name = "工作中心")
    private Long workCenterId;

    /** 工作中心编码（用于显示） */
    private String workCenterCode;

    /** 工作中心名称（用于显示） */
    private String workCenterName;

    /** 设备 */
    @Excel(name = "设备")
    private Long equipmentId;

    /** 设备编码（用于显示） */
    private String equipmentCode;

    /** 设备名称（用于显示） */
    private String equipmentName;

    /** 模具 */
    @Excel(name = "模具")
    private Long moldId;

    /** 模具编码（用于显示） */
    private String moldCode;

    /** 模具名称（用于显示） */
    private String moldName;

    /** 准备时间(分钟) */
    @Excel(name = "准备时间(分钟)")
    private BigDecimal setupTime;

    /** 加工时间(分钟) */
    @Excel(name = "加工时间(分钟)")
    private BigDecimal processingTime;

    /** 移动时间(分钟) */
    @Excel(name = "移动时间(分钟)")
    private BigDecimal moveTime;

    /** 等待时间(分钟) */
    @Excel(name = "等待时间(分钟)")
    private BigDecimal queueTime;

    /** 周期时间(分钟) */
    @Excel(name = "周期时间(分钟)")
    private BigDecimal cycleTime;

    /** 人工工时(小时) */
    @Excel(name = "人工工时(小时)")
    private BigDecimal laborHours;

    /** 机器工时(小时) */
    @Excel(name = "机器工时(小时)")
    private BigDecimal machineHours;

    /** 是否关键工序（0否 1是） */
    @Excel(name = "是否关键工序", readConverterExp = "0=否,1=是")
    private String isCritical;

    /** 是否可选工序（0否 1是） */
    @Excel(name = "是否可选工序", readConverterExp = "0=否,1=是")
    private String isOptional;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBomId(Long bomId) 
    {
        this.bomId = bomId;
    }

    public Long getBomId() 
    {
        return bomId;
    }

    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }

    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }

    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }

    public void setOperationSequence(Integer operationSequence) 
    {
        this.operationSequence = operationSequence;
    }

    public Integer getOperationSequence() 
    {
        return operationSequence;
    }

    public void setWorkCenterId(Long workCenterId) 
    {
        this.workCenterId = workCenterId;
    }

    public Long getWorkCenterId() 
    {
        return workCenterId;
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

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }

    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setMoldId(Long moldId) 
    {
        this.moldId = moldId;
    }

    public Long getMoldId() 
    {
        return moldId;
    }

    public void setMoldCode(String moldCode) 
    {
        this.moldCode = moldCode;
    }

    public String getMoldCode() 
    {
        return moldCode;
    }

    public void setMoldName(String moldName) 
    {
        this.moldName = moldName;
    }

    public String getMoldName() 
    {
        return moldName;
    }

    public void setSetupTime(BigDecimal setupTime) 
    {
        this.setupTime = setupTime;
    }

    public BigDecimal getSetupTime() 
    {
        return setupTime;
    }

    public void setProcessingTime(BigDecimal processingTime) 
    {
        this.processingTime = processingTime;
    }

    public BigDecimal getProcessingTime() 
    {
        return processingTime;
    }

    public void setMoveTime(BigDecimal moveTime) 
    {
        this.moveTime = moveTime;
    }

    public BigDecimal getMoveTime() 
    {
        return moveTime;
    }

    public void setQueueTime(BigDecimal queueTime) 
    {
        this.queueTime = queueTime;
    }

    public BigDecimal getQueueTime() 
    {
        return queueTime;
    }

    public void setCycleTime(BigDecimal cycleTime) 
    {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getCycleTime() 
    {
        return cycleTime;
    }

    public void setLaborHours(BigDecimal laborHours) 
    {
        this.laborHours = laborHours;
    }

    public BigDecimal getLaborHours() 
    {
        return laborHours;
    }

    public void setMachineHours(BigDecimal machineHours) 
    {
        this.machineHours = machineHours;
    }

    public BigDecimal getMachineHours() 
    {
        return machineHours;
    }

    public void setIsCritical(String isCritical) 
    {
        this.isCritical = isCritical;
    }

    public String getIsCritical() 
    {
        return isCritical;
    }

    public void setIsOptional(String isOptional) 
    {
        this.isOptional = isOptional;
    }

    public String getIsOptional() 
    {
        return isOptional;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bomId", getBomId())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("operationSequence", getOperationSequence())
            .append("workCenterId", getWorkCenterId())
            .append("workCenterCode", getWorkCenterCode())
            .append("workCenterName", getWorkCenterName())
            .append("equipmentId", getEquipmentId())
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("moldId", getMoldId())
            .append("moldCode", getMoldCode())
            .append("moldName", getMoldName())
            .append("setupTime", getSetupTime())
            .append("processingTime", getProcessingTime())
            .append("moveTime", getMoveTime())
            .append("queueTime", getQueueTime())
            .append("cycleTime", getCycleTime())
            .append("laborHours", getLaborHours())
            .append("machineHours", getMachineHours())
            .append("isCritical", getIsCritical())
            .append("isOptional", getIsOptional())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
