package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 出库单明细表对象 erp_outbound_order_detail
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpOutboundOrderDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 出库单ID */
    @Excel(name = "出库单ID")
    private Long outboundId;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String outboundNo;

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

    /** 计划数量 */
    @Excel(name = "计划数量")
    private BigDecimal plannedQuantity;

    /** 实际数量 */
    @Excel(name = "实际数量")
    private BigDecimal actualQuantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 批号 */
    @Excel(name = "批号")
    private String lotNo;

    /** 序列号 */
    @Excel(name = "序列号")
    private String serialNo;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOutboundId(Long outboundId) 
    {
        this.outboundId = outboundId;
    }

    public Long getOutboundId() 
    {
        return outboundId;
    }

    public void setOutboundNo(String outboundNo) 
    {
        this.outboundNo = outboundNo;
    }

    public String getOutboundNo() 
    {
        return outboundNo;
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

    public void setPlannedQuantity(BigDecimal plannedQuantity) 
    {
        this.plannedQuantity = plannedQuantity;
    }

    public BigDecimal getPlannedQuantity() 
    {
        return plannedQuantity;
    }

    public void setActualQuantity(BigDecimal actualQuantity) 
    {
        this.actualQuantity = actualQuantity;
    }

    public BigDecimal getActualQuantity() 
    {
        return actualQuantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("outboundId", getOutboundId())
            .append("outboundNo", getOutboundNo())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialModel", getMaterialModel())
            .append("unitId", getUnitId())
            .append("unitCode", getUnitCode())
            .append("unitName", getUnitName())
            .append("plannedQuantity", getPlannedQuantity())
            .append("actualQuantity", getActualQuantity())
            .append("unitPrice", getUnitPrice())
            .append("totalAmount", getTotalAmount())
            .append("batchNo", getBatchNo())
            .append("lotNo", getLotNo())
            .append("serialNo", getSerialNo())
            .append("shelfId", getShelfId())
            .append("shelfCode", getShelfCode())
            .append("shelfName", getShelfName())
            .append("locationId", getLocationId())
            .append("locationCode", getLocationCode())
            .append("locationName", getLocationName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
