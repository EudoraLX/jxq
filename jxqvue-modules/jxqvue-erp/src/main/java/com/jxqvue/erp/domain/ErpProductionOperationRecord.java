package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产操作记录对象 erp_production_operation_record
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ErpProductionOperationRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 操作记录ID */
    private Long id;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String operationType;

    /** 生产订单ID */
    @Excel(name = "生产订单ID")
    private Long productionOrderId;

    /** 生产订单编码 */
    @Excel(name = "生产订单编码")
    private String orderCode;

    /** 产品ID */
    @Excel(name = "产品ID")
    private Long productId;

    private String productName;

    private String productCode;

    private Long unitId;
    private String unitName;

    /** 工艺路线ID */
    private Long processRouteId;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工作中心ID */
    @Excel(name = "工作中心ID")
    private Long workCenterId;

    /** 工作中心编码 */
    @Excel(name = "工作中心编码")
    private String workCenterCode;

    /** 工作中心名称 */
    @Excel(name = "工作中心名称")
    private String workCenterName;

    /** 设备ID */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String equipmentCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 模具ID */
    @Excel(name = "模具ID")
    private Long moldId;

    /** 模具编码 */
    @Excel(name = "模具编码")
    private String moldCode;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 操作员ID */
    @Excel(name = "操作员ID")
    private Long operatorId;

    /** 操作员姓名 */
    @Excel(name = "操作员姓名")
    private String operatorName;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operationTime;

    /** 实际数量 */
    @Excel(name = "实际数量")
    private BigDecimal quantity;



    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setProductionOrderId(Long productionOrderId)
    {
        this.productionOrderId = productionOrderId;
    }

    public Long getProductionOrderId() 
    {
        return productionOrderId;
    }
    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }
    public void setProcessRouteId(Long processRouteId) 
    {
        this.processRouteId = processRouteId;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getProcessRouteId()
    {
        return processRouteId;
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
    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }
    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }
    public void setOperationTime(Date operationTime) 
    {
        this.operationTime = operationTime;
    }

    public Date getOperationTime() 
    {
        return operationTime;
    }
    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("operationType", getOperationType())
            .append("productionOrderId", getProductionOrderId())
            .append("orderCode", getOrderCode())
                .append("productId", getProductId())
                .append("productCode", getProductCode())
                .append("productName", getProductName())
                .append("unitId", getUnitId())
                .append("unitName", getUnitName())
            .append("processRouteId", getProcessRouteId())
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
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("operationTime", getOperationTime())
            .append("quantity", getQuantity())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
