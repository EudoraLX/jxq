package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 库存预警表对象 erp_inventory_alert
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventoryAlert extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 预警ID */
    private Long id;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 预警类型（01库存不足 02库存过多 03即将过期 04已过期） */
    @Excel(name = "预警类型", readConverterExp = "01=库存不足,02=库存过多,03=即将过期,04=已过期")
    private String alertType;

    /** 当前库存 */
    @Excel(name = "当前库存")
    private BigDecimal currentQuantity;

    /** 阈值数量 */
    @Excel(name = "阈值数量")
    private BigDecimal thresholdQuantity;

    /** 预警级别（1低 2中 3高） */
    @Excel(name = "预警级别", readConverterExp = "1=低,2=中,3=高")
    private String alertLevel;

    /** 预警信息 */
    @Excel(name = "预警信息")
    private String alertMessage;

    /** 是否已处理（0否 1是） */
    @Excel(name = "是否已处理", readConverterExp = "0=否,1=是")
    private String isHandled;

    /** 处理人ID */
    @Excel(name = "处理人ID")
    private Long handlerId;

    /** 处理人姓名 */
    @Excel(name = "处理人姓名")
    private String handlerName;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 处理结果 */
    @Excel(name = "处理结果")
    private String handleResult;

    /** 预警日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "预警日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date alertDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setMaterialId(Long materialId) 
    {
        this.materialId = materialId;
    }

    public Long getMaterialId() 
    {
        return materialId;
    }

    public void setMaterialCode(String materialCode) 
    {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() 
    {
        return materialCode;
    }

    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getMaterialName() 
    {
        return materialName;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setWarehouseCode(String warehouseCode) 
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() 
    {
        return warehouseCode;
    }

    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }

    public void setAlertType(String alertType) 
    {
        this.alertType = alertType;
    }

    public String getAlertType() 
    {
        return alertType;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) 
    {
        this.currentQuantity = currentQuantity;
    }

    public BigDecimal getCurrentQuantity() 
    {
        return currentQuantity;
    }

    public void setThresholdQuantity(BigDecimal thresholdQuantity) 
    {
        this.thresholdQuantity = thresholdQuantity;
    }

    public BigDecimal getThresholdQuantity() 
    {
        return thresholdQuantity;
    }

    public void setAlertLevel(String alertLevel) 
    {
        this.alertLevel = alertLevel;
    }

    public String getAlertLevel() 
    {
        return alertLevel;
    }

    public void setAlertMessage(String alertMessage) 
    {
        this.alertMessage = alertMessage;
    }

    public String getAlertMessage() 
    {
        return alertMessage;
    }

    public void setIsHandled(String isHandled) 
    {
        this.isHandled = isHandled;
    }

    public String getIsHandled() 
    {
        return isHandled;
    }

    public void setHandlerId(Long handlerId) 
    {
        this.handlerId = handlerId;
    }

    public Long getHandlerId() 
    {
        return handlerId;
    }

    public void setHandlerName(String handlerName) 
    {
        this.handlerName = handlerName;
    }

    public String getHandlerName() 
    {
        return handlerName;
    }

    public void setHandleTime(Date handleTime) 
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime() 
    {
        return handleTime;
    }

    public void setHandleResult(String handleResult) 
    {
        this.handleResult = handleResult;
    }

    public String getHandleResult() 
    {
        return handleResult;
    }

    public void setAlertDate(Date alertDate) 
    {
        this.alertDate = alertDate;
    }

    public Date getAlertDate() 
    {
        return alertDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("alertType", getAlertType())
            .append("currentQuantity", getCurrentQuantity())
            .append("thresholdQuantity", getThresholdQuantity())
            .append("alertLevel", getAlertLevel())
            .append("alertMessage", getAlertMessage())
            .append("isHandled", getIsHandled())
            .append("handlerId", getHandlerId())
            .append("handlerName", getHandlerName())
            .append("handleTime", getHandleTime())
            .append("handleResult", getHandleResult())
            .append("alertDate", getAlertDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
