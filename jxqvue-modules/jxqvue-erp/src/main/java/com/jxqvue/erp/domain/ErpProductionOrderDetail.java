package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产订单明细对象 erp_production_order_detail
 *
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProductionOrderDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 生产订单ID
     */
    @Excel(name = "生产订单ID")
    private Long orderId;

    /**
     * 工序ID
     */
    @Excel(name = "工序ID")
    private Long processId;

    /**
     * 工序名称
     */
    @Excel(name = "工序名称")
    private String processName;

    /**
     * 工序顺序
     */
    @Excel(name = "工序顺序")
    private Integer processOrder;

    /**
     * 工作中心ID
     */
    @Excel(name = "工作中心ID")
    private Long workCenterId;

    /**
     * 工作中心名称
     */
    @Excel(name = "工作中心名称")
    private String workCenterName;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipmentName;

    /**
     * 模具ID
     */
    @Excel(name = "模具ID")
    private Long moldId;

    /**
     * 模具名称
     */
    @Excel(name = "模具名称")
    private String moldName;

    /**
     * 计划工时(小时)
     */
    @Excel(name = "计划工时(小时)")
    private BigDecimal plannedDuration;

    /**
     * 实际工时(小时)
     */
    @Excel(name = "实际工时(小时)")
    private BigDecimal actualDuration;

    /**
     * 操作人员ID
     */
    @Excel(name = "操作人员ID")
    private Long operatorId;

    /**
     * 操作人员名称
     */
    @Excel(name = "操作人员名称")
    private String operatorName;

    /**
     * 模具安装时间(小时)
     */
    @Excel(name = "模具安装时间(小时)")
    private BigDecimal moldSetupTime;

    /**
     * 模具拆卸时间(小时)
     */
    @Excel(name = "模具拆卸时间(小时)")
    private BigDecimal moldRemovalTime;

    /**
     * 温控时间(小时)
     */
    @Excel(name = "温控时间(小时)")
    private BigDecimal temperatureControlTime;

    /**
     * 压力控制时间(小时)
     */
    @Excel(name = "压力控制时间(小时)")
    private BigDecimal pressureControlTime;

    /**
     * 质量检查时间(小时)
     */
    @Excel(name = "质量检查时间(小时)")
    private BigDecimal qualityCheckTime;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessOrder(Integer processOrder) {
        this.processOrder = processOrder;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public void setWorkCenterId(Long workCenterId) {
        this.workCenterId = workCenterId;
    }

    public Long getWorkCenterId() {
        return workCenterId;
    }

    public void setWorkCenterName(String workCenterName) {
        this.workCenterName = workCenterName;
    }

    public String getWorkCenterName() {
        return workCenterName;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setMoldId(Long moldId) {
        this.moldId = moldId;
    }

    public Long getMoldId() {
        return moldId;
    }

    public void setMoldName(String moldName) {
        this.moldName = moldName;
    }

    public String getMoldName() {
        return moldName;
    }

    public void setPlannedDuration(BigDecimal plannedDuration) {
        this.plannedDuration = plannedDuration;
    }

    public BigDecimal getPlannedDuration() {
        return plannedDuration;
    }

    public void setActualDuration(BigDecimal actualDuration) {
        this.actualDuration = actualDuration;
    }

    public BigDecimal getActualDuration() {
        return actualDuration;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setMoldSetupTime(BigDecimal moldSetupTime) {
        this.moldSetupTime = moldSetupTime;
    }

    public BigDecimal getMoldSetupTime() {
        return moldSetupTime;
    }

    public void setMoldRemovalTime(BigDecimal moldRemovalTime) {
        this.moldRemovalTime = moldRemovalTime;
    }

    public BigDecimal getMoldRemovalTime() {
        return moldRemovalTime;
    }

    public void setTemperatureControlTime(BigDecimal temperatureControlTime) {
        this.temperatureControlTime = temperatureControlTime;
    }

    public BigDecimal getTemperatureControlTime() {
        return temperatureControlTime;
    }

    public void setPressureControlTime(BigDecimal pressureControlTime) {
        this.pressureControlTime = pressureControlTime;
    }

    public BigDecimal getPressureControlTime() {
        return pressureControlTime;
    }

    public void setQualityCheckTime(BigDecimal qualityCheckTime) {
        this.qualityCheckTime = qualityCheckTime;
    }

    public BigDecimal getQualityCheckTime() {
        return qualityCheckTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("processId", getProcessId())
                .append("processOrder", getProcessOrder())
                .append("workCenterId", getWorkCenterId())
                .append("equipmentId", getEquipmentId())
                .append("moldId", getMoldId())
                .append("plannedDuration", getPlannedDuration())
                .append("actualDuration", getActualDuration())
                .append("operatorId", getOperatorId())
                .append("moldSetupTime", getMoldSetupTime())
                .append("moldRemovalTime", getMoldRemovalTime())
                .append("temperatureControlTime", getTemperatureControlTime())
                .append("pressureControlTime", getPressureControlTime())
                .append("qualityCheckTime", getQualityCheckTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
