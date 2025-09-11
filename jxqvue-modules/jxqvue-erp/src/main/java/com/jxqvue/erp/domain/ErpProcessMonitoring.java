package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工艺参数监控对象 erp_process_monitoring
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpProcessMonitoring extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Monitoring ID */
    private Long id;

    /** 生产订单 */
    @Excel(name = "生产订单ID")
    private Long productionOrderId;

    /** 生产订单编码 */
    @Excel(name = "生产订单编码")
    private String productionOrderCode;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 工艺 */
    @Excel(name = "工艺ID")
    private Long processId;

    /** 工艺编码 */
    @Excel(name = "工艺编码")
    private String processCode;

    /** 工艺名称 */
    @Excel(name = "工艺名称")
    private String processName;

    /** 工作中心 */
    @Excel(name = "工作中心ID")
    private Long workCenterId;

    /** 工作中心编码 */
    @Excel(name = "工作中心编码")
    private String workCenterCode;

    /** 工作中心名称 */
    @Excel(name = "工作中心名称")
    private String workCenterName;

    /** 设备 */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String equipmentCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 模具 */
    @Excel(name = "模具ID")
    private Long moldId;

    /** 模具编码 */
    @Excel(name = "模具编码")
    private String moldCode;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 监控时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "监控时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date monitoringTime;

    /** 监控人 */
    @Excel(name = "监控人ID")
    private Long inspectorId;

    /** 监控人姓名 */
    @Excel(name = "监控人姓名")
    private String inspectorName;

    /** 监控类型 */
    @Excel(name = "监控参数")
    private Long parameterId;

    /** 参数编码 */
    @Excel(name = "参数编码")
    private String parameterCode;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String parameterName;

    /** 参数单位 */
    @Excel(name = "参数单位")
    private String parameterUnit;

    /** 标准值 */
    @Excel(name = "标准值")
    private BigDecimal standardValue;

    /** 监控值 */
    @Excel(name = "监控值")
    private BigDecimal actualValue;

    /** 允许最小值 */
    @Excel(name = "允许最小值")
    private BigDecimal minValue;

    /** 允许最大值 */
    @Excel(name = "允许最大值")
    private BigDecimal maxValue;

    /** 实际偏差 */
    @Excel(name = "实际偏差")
    private BigDecimal deviation;

    /** 偏差率 */
    @Excel(name = "偏差率")
    private BigDecimal deviationRate;

    /** 是否正常 */
    @Excel(name = "是否正常", readConverterExp = "0=异常,1=正常")
    private String isNormal;

    /** 报警等级 */
    @Excel(name = "报警等级", readConverterExp = "0=无报警,1=轻微报警,2=严重报警,3=紧急报警")
    private String alarmLevel;

    /** 报警信息 */
    @Excel(name = "报警信息")
    private String alarmMessage;

    /** 控制方法 */
    @Excel(name = "控制方法")
    private String controlMethod;

    /** 应对行动 */
    @Excel(name = "应对行动")
    private String adjustmentAction;

    /** 下次监控 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "下次监控", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date nextMonitoringTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setProductionOrderId(Long productionOrderId)
    {
        this.productionOrderId = productionOrderId;
    }

    public Long getProductionOrderId() 
    {
        return productionOrderId;
    }

    public void setProductionOrderCode(String productionOrderCode)
    {
        this.productionOrderCode = productionOrderCode;
    }

    public String getProductionOrderCode() 
    {
        return productionOrderCode;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
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

    public void setMonitoringTime(Date monitoringTime)
    {
        this.monitoringTime = monitoringTime;
    }

    public Date getMonitoringTime() 
    {
        return monitoringTime;
    }

    public void setInspectorId(Long inspectorId)
    {
        this.inspectorId = inspectorId;
    }

    public Long getInspectorId() 
    {
        return inspectorId;
    }

    public void setInspectorName(String inspectorName)
    {
        this.inspectorName = inspectorName;
    }

    public String getInspectorName() 
    {
        return inspectorName;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public void setParameterCode(String parameterCode)
    {
        this.parameterCode = parameterCode;
    }

    public String getParameterCode() 
    {
        return parameterCode;
    }

    public void setParameterName(String parameterName)
    {
        this.parameterName = parameterName;
    }

    public String getParameterName() 
    {
        return parameterName;
    }

    public void setParameterUnit(String parameterUnit)
    {
        this.parameterUnit = parameterUnit;
    }

    public String getParameterUnit() 
    {
        return parameterUnit;
    }

    public void setStandardValue(BigDecimal standardValue)
    {
        this.standardValue = standardValue;
    }

    public BigDecimal getStandardValue() 
    {
        return standardValue;
    }

    public void setActualValue(BigDecimal actualValue)
    {
        this.actualValue = actualValue;
    }

    public BigDecimal getActualValue() 
    {
        return actualValue;
    }

    public void setMinValue(BigDecimal minValue)
    {
        this.minValue = minValue;
    }

    public BigDecimal getMinValue() 
    {
        return minValue;
    }

    public void setMaxValue(BigDecimal maxValue)
    {
        this.maxValue = maxValue;
    }

    public BigDecimal getMaxValue() 
    {
        return maxValue;
    }

    public void setDeviation(BigDecimal deviation)
    {
        this.deviation = deviation;
    }

    public BigDecimal getDeviation() 
    {
        return deviation;
    }

    public void setDeviationRate(BigDecimal deviationRate)
    {
        this.deviationRate = deviationRate;
    }

    public BigDecimal getDeviationRate() 
    {
        return deviationRate;
    }

    public void setIsNormal(String isNormal)
    {
        this.isNormal = isNormal;
    }

    public String getIsNormal() 
    {
        return isNormal;
    }

    public void setAlarmLevel(String alarmLevel)
    {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmLevel() 
    {
        return alarmLevel;
    }

    public void setAlarmMessage(String alarmMessage)
    {
        this.alarmMessage = alarmMessage;
    }

    public String getAlarmMessage() 
    {
        return alarmMessage;
    }

    public void setControlMethod(String controlMethod)
    {
        this.controlMethod = controlMethod;
    }

    public String getControlMethod() 
    {
        return controlMethod;
    }

    public void setAdjustmentAction(String adjustmentAction)
    {
        this.adjustmentAction = adjustmentAction;
    }

    public String getAdjustmentAction() 
    {
        return adjustmentAction;
    }

    public void setNextMonitoringTime(Date nextMonitoringTime)
    {
        this.nextMonitoringTime = nextMonitoringTime;
    }

    public Date getNextMonitoringTime() 
    {
        return nextMonitoringTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("productionOrderId", getProductionOrderId())
            .append("productionOrderCode", getProductionOrderCode())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("workCenterId", getWorkCenterId())
            .append("workCenterCode", getWorkCenterCode())
            .append("workCenterName", getWorkCenterName())
            .append("equipmentId", getEquipmentId())
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("moldId", getMoldId())
            .append("moldCode", getMoldCode())
            .append("moldName", getMoldName())
            .append("monitoringTime", getMonitoringTime())
            .append("inspectorId", getInspectorId())
            .append("inspectorName", getInspectorName())
            .append("parameterId", getParameterId())
            .append("parameterCode", getParameterCode())
            .append("parameterName", getParameterName())
            .append("parameterUnit", getParameterUnit())
            .append("standardValue", getStandardValue())
            .append("actualValue", getActualValue())
            .append("minValue", getMinValue())
            .append("maxValue", getMaxValue())
            .append("deviation", getDeviation())
            .append("deviationRate", getDeviationRate())
            .append("isNormal", getIsNormal())
            .append("alarmLevel", getAlarmLevel())
            .append("alarmMessage", getAlarmMessage())
            .append("controlMethod", getControlMethod())
            .append("adjustmentAction", getAdjustmentAction())
            .append("nextMonitoringTime", getNextMonitoringTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
