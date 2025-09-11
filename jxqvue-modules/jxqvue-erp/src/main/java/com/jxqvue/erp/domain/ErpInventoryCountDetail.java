package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 盘点单明细表对象 erp_inventory_count_detail
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventoryCountDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 盘点单ID */
    @Excel(name = "盘点单ID")
    private Long countId;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String countNo;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编码 */
    @Excel(name = "物料编码")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialModel;


    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 单位编码 */
    @Excel(name = "单位编码")
    private String unitCode;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 货架ID */
    @Excel(name = "货架ID")
    private Long shelfId;

    /** 货架编码 */
    @Excel(name = "货架编码")
    private String shelfCode;

    /** 货架名称 */
    @Excel(name = "货架名称")
    private String shelfName;

    /** 货位ID */
    @Excel(name = "货位ID")
    private Long locationId;

    /** 货位编码 */
    @Excel(name = "货位编码")
    private String locationCode;

    /** 货位名称 */
    @Excel(name = "货位名称")
    private String locationName;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 批号 */
    @Excel(name = "批号")
    private String lotNo;

    /** 序列号 */
    @Excel(name = "序列号")
    private String serialNo;

    /** 账面数量 */
    @Excel(name = "账面数量")
    private BigDecimal bookQuantity;

    /** 盘点数量 */
    @Excel(name = "盘点数量")
    private BigDecimal countQuantity;

    /** 差异数量 */
    @Excel(name = "差异数量")
    private BigDecimal differenceQuantity;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 差异金额 */
    @Excel(name = "差异金额")
    private BigDecimal differenceAmount;

    /** 差异原因 */
    @Excel(name = "差异原因")
    private String differenceReason;

    /** 调整数量 */
    @Excel(name = "调整数量")
    private BigDecimal adjustQuantity;

    /** 调整金额 */
    @Excel(name = "调整金额")
    private BigDecimal adjustAmount;

    /** 调整原因 */
    @Excel(name = "调整原因")
    private String adjustReason;

    /** 状态（0待盘点 1已盘点 2已调整） */
    @Excel(name = "状态", readConverterExp = "0=待盘点,1=已盘点,2=已调整")
    private String status;

    /** 盘点人ID */
    @Excel(name = "盘点人ID")
    private Long counterId;

    /** 盘点人姓名 */
    @Excel(name = "盘点人姓名")
    private String counterName;

    /** 盘点时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "盘点时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date countTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCountId(Long countId) 
    {
        this.countId = countId;
    }

    public Long getCountId() 
    {
        return countId;
    }

    public void setCountNo(String countNo) 
    {
        this.countNo = countNo;
    }

    public String getCountNo() 
    {
        return countNo;
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

    public void setMaterialSpec(String materialSpec) 
    {
        this.materialSpec = materialSpec;
    }

    public String getMaterialSpec() 
    {
        return materialSpec;
    }

    public void setMaterialModel(String materialModel) 
    {
        this.materialModel = materialModel;
    }

    public String getMaterialModel() 
    {
        return materialModel;
    }


    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setUnitCode(String unitCode) 
    {
        this.unitCode = unitCode;
    }

    public String getUnitCode() 
    {
        return unitCode;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    public void setShelfId(Long shelfId) 
    {
        this.shelfId = shelfId;
    }

    public Long getShelfId() 
    {
        return shelfId;
    }

    public void setShelfCode(String shelfCode) 
    {
        this.shelfCode = shelfCode;
    }

    public String getShelfCode() 
    {
        return shelfCode;
    }

    public void setShelfName(String shelfName) 
    {
        this.shelfName = shelfName;
    }

    public String getShelfName() 
    {
        return shelfName;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public void setLocationCode(String locationCode) 
    {
        this.locationCode = locationCode;
    }

    public String getLocationCode() 
    {
        return locationCode;
    }

    public void setLocationName(String locationName) 
    {
        this.locationName = locationName;
    }

    public String getLocationName() 
    {
        return locationName;
    }

    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }

    public void setLotNo(String lotNo) 
    {
        this.lotNo = lotNo;
    }

    public String getLotNo() 
    {
        return lotNo;
    }

    public void setSerialNo(String serialNo) 
    {
        this.serialNo = serialNo;
    }

    public String getSerialNo() 
    {
        return serialNo;
    }

    public void setBookQuantity(BigDecimal bookQuantity) 
    {
        this.bookQuantity = bookQuantity;
    }

    public BigDecimal getBookQuantity() 
    {
        return bookQuantity;
    }

    public void setCountQuantity(BigDecimal countQuantity) 
    {
        this.countQuantity = countQuantity;
    }

    public BigDecimal getCountQuantity() 
    {
        return countQuantity;
    }

    public void setDifferenceQuantity(BigDecimal differenceQuantity) 
    {
        this.differenceQuantity = differenceQuantity;
    }

    public BigDecimal getDifferenceQuantity() 
    {
        return differenceQuantity;
    }

    public void setUnitCost(BigDecimal unitCost) 
    {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() 
    {
        return unitCost;
    }

    public void setDifferenceAmount(BigDecimal differenceAmount) 
    {
        this.differenceAmount = differenceAmount;
    }

    public BigDecimal getDifferenceAmount() 
    {
        return differenceAmount;
    }

    public void setDifferenceReason(String differenceReason) 
    {
        this.differenceReason = differenceReason;
    }

    public String getDifferenceReason() 
    {
        return differenceReason;
    }

    public void setAdjustQuantity(BigDecimal adjustQuantity) 
    {
        this.adjustQuantity = adjustQuantity;
    }

    public BigDecimal getAdjustQuantity() 
    {
        return adjustQuantity;
    }

    public void setAdjustAmount(BigDecimal adjustAmount) 
    {
        this.adjustAmount = adjustAmount;
    }

    public BigDecimal getAdjustAmount() 
    {
        return adjustAmount;
    }

    public void setAdjustReason(String adjustReason) 
    {
        this.adjustReason = adjustReason;
    }

    public String getAdjustReason() 
    {
        return adjustReason;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setCounterId(Long counterId) 
    {
        this.counterId = counterId;
    }

    public Long getCounterId() 
    {
        return counterId;
    }

    public void setCounterName(String counterName) 
    {
        this.counterName = counterName;
    }

    public String getCounterName() 
    {
        return counterName;
    }

    public void setCountTime(Date countTime) 
    {
        this.countTime = countTime;
    }

    public Date getCountTime() 
    {
        return countTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("countId", getCountId())
            .append("countNo", getCountNo())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialModel", getMaterialModel())
            .append("unitId", getUnitId())
            .append("unitCode", getUnitCode())
            .append("unitName", getUnitName())
            .append("shelfId", getShelfId())
            .append("shelfCode", getShelfCode())
            .append("shelfName", getShelfName())
            .append("locationId", getLocationId())
            .append("locationCode", getLocationCode())
            .append("locationName", getLocationName())
            .append("batchNo", getBatchNo())
            .append("lotNo", getLotNo())
            .append("serialNo", getSerialNo())
            .append("bookQuantity", getBookQuantity())
            .append("countQuantity", getCountQuantity())
            .append("differenceQuantity", getDifferenceQuantity())
            .append("unitCost", getUnitCost())
            .append("differenceAmount", getDifferenceAmount())
            .append("differenceReason", getDifferenceReason())
            .append("adjustQuantity", getAdjustQuantity())
            .append("adjustAmount", getAdjustAmount())
            .append("adjustReason", getAdjustReason())
            .append("status", getStatus())
            .append("counterId", getCounterId())
            .append("counterName", getCounterName())
            .append("countTime", getCountTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
