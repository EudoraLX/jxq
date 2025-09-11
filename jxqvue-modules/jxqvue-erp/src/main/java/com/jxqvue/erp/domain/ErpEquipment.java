package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 设备对象 erp_equipment
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpEquipment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 设备ID */
    private Long id;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String equipmentCode;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String equipmentModel;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String equipmentType;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 工作中心 */
    @Excel(name = "工作中心")
    private Long workCenterId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 工作中心名称 */
    @Excel(name = "工作中心名称")
    private String workCenterName;

    /** 购买日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 保修到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "保修到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warrantyExpiryDate;

    /** 折旧方法（1直线法 2加速折旧法） */
    @Excel(name = "折旧方法", readConverterExp = "1=直线法,2=加速折旧法")
    private String depreciationMethod;

    /** 使用年限 */
    @Excel(name = "使用年限")
    private Long usefulLife;

    /** 原值 */
    @Excel(name = "原值")
    private BigDecimal originalValue;

    /** 现值 */
    @Excel(name = "现值")
    private BigDecimal currentValue;

    /** 产能 */
    @Excel(name = "产能")
    private BigDecimal capacity;

    /** 产能单位 */
    @Excel(name = "产能单位")
    private String capacityUnit;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在，1代表删除） */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setEquipmentModel(String equipmentModel) 
    {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentModel() 
    {
        return equipmentModel;
    }

    public void setEquipmentType(String equipmentType) 
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType() 
    {
        return equipmentType;
    }

    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setWorkCenterId(Long workCenterId) 
    {
        this.workCenterId = workCenterId;
    }

    public Long getWorkCenterId() 
    {
        return workCenterId;
    }

    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }

    public void setWorkCenterName(String workCenterName) 
    {
        this.workCenterName = workCenterName;
    }

    public String getWorkCenterName() 
    {
        return workCenterName;
    }

    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }

    public void setWarrantyExpiryDate(Date warrantyExpiryDate) 
    {
        this.warrantyExpiryDate = warrantyExpiryDate;
    }

    public Date getWarrantyExpiryDate() 
    {
        return warrantyExpiryDate;
    }

    public void setDepreciationMethod(String depreciationMethod) 
    {
        this.depreciationMethod = depreciationMethod;
    }

    public String getDepreciationMethod() 
    {
        return depreciationMethod;
    }

    public void setUsefulLife(Long usefulLife) 
    {
        this.usefulLife = usefulLife;
    }

    public Long getUsefulLife() 
    {
        return usefulLife;
    }

    public void setOriginalValue(BigDecimal originalValue) 
    {
        this.originalValue = originalValue;
    }

    public BigDecimal getOriginalValue() 
    {
        return originalValue;
    }

    public void setCurrentValue(BigDecimal currentValue) 
    {
        this.currentValue = currentValue;
    }

    public BigDecimal getCurrentValue() 
    {
        return currentValue;
    }

    public void setCapacity(BigDecimal capacity) 
    {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity() 
    {
        return capacity;
    }

    public void setCapacityUnit(String capacityUnit) 
    {
        this.capacityUnit = capacityUnit;
    }

    public String getCapacityUnit() 
    {
        return capacityUnit;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("equipmentCode", getEquipmentCode())
            .append("equipmentName", getEquipmentName())
            .append("equipmentModel", getEquipmentModel())
            .append("equipmentType", getEquipmentType())
            .append("manufacturer", getManufacturer())
            .append("supplierId", getSupplierId())
            .append("workCenterId", getWorkCenterId())
            .append("supplierName", getSupplierName())
            .append("workCenterName", getWorkCenterName())
            .append("purchaseDate", getPurchaseDate())
            .append("warrantyExpiryDate", getWarrantyExpiryDate())
            .append("depreciationMethod", getDepreciationMethod())
            .append("usefulLife", getUsefulLife())
            .append("originalValue", getOriginalValue())
            .append("currentValue", getCurrentValue())
            .append("capacity", getCapacity())
            .append("capacityUnit", getCapacityUnit())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
