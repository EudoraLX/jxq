package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 库存事务表对象 erp_inventory_transaction
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventoryTransaction extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 事务ID */
    private Long id;

    /** 事务单号 */
    @Excel(name = "事务单号")
    private String transactionNo;

    /** 事务类型（01入库 02出库 03调拨 04盘点 05调整） */
    @Excel(name = "事务类型", readConverterExp = "01=入库,02=出库,03=调拨,04=盘点,05=调整")
    private String transactionType;

    /** 事务子类型 */
    @Excel(name = "事务子类型")
    private String transactionSubType;

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

    /** 事务数量 */
    @Excel(name = "事务数量")
    private BigDecimal quantity;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 总成本 */
    @Excel(name = "总成本")
    private BigDecimal totalCost;

    /** 事务前数量 */
    @Excel(name = "事务前数量")
    private BigDecimal beforeQuantity;

    /** 事务后数量 */
    @Excel(name = "事务后数量")
    private BigDecimal afterQuantity;

    /** 关联单据类型 */
    @Excel(name = "关联单据类型")
    private String referenceType;

    /** 关联单据ID */
    @Excel(name = "关联单据ID")
    private Long referenceId;

    /** 关联单据号 */
    @Excel(name = "关联单据号")
    private String referenceNo;

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    /** 操作人姓名 */
    @Excel(name = "操作人姓名")
    private String operatorName;

    /** 事务日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "事务日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date transactionDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTransactionNo(String transactionNo) 
    {
        this.transactionNo = transactionNo;
    }

    public String getTransactionNo() 
    {
        return transactionNo;
    }

    public void setTransactionType(String transactionType) 
    {
        this.transactionType = transactionType;
    }

    public String getTransactionType() 
    {
        return transactionType;
    }

    public void setTransactionSubType(String transactionSubType) 
    {
        this.transactionSubType = transactionSubType;
    }

    public String getTransactionSubType() 
    {
        return transactionSubType;
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

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }

    public void setUnitCost(BigDecimal unitCost) 
    {
        this.unitCost = unitCost;
    }

    public BigDecimal getUnitCost() 
    {
        return unitCost;
    }

    public void setTotalCost(BigDecimal totalCost) 
    {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalCost() 
    {
        return totalCost;
    }

    public void setBeforeQuantity(BigDecimal beforeQuantity) 
    {
        this.beforeQuantity = beforeQuantity;
    }

    public BigDecimal getBeforeQuantity() 
    {
        return beforeQuantity;
    }

    public void setAfterQuantity(BigDecimal afterQuantity) 
    {
        this.afterQuantity = afterQuantity;
    }

    public BigDecimal getAfterQuantity() 
    {
        return afterQuantity;
    }

    public void setReferenceType(String referenceType) 
    {
        this.referenceType = referenceType;
    }

    public String getReferenceType() 
    {
        return referenceType;
    }

    public void setReferenceId(Long referenceId) 
    {
        this.referenceId = referenceId;
    }

    public Long getReferenceId() 
    {
        return referenceId;
    }

    public void setReferenceNo(String referenceNo) 
    {
        this.referenceNo = referenceNo;
    }

    public String getReferenceNo() 
    {
        return referenceNo;
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

    public void setTransactionDate(Date transactionDate) 
    {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDate() 
    {
        return transactionDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("transactionNo", getTransactionNo())
            .append("transactionType", getTransactionType())
            .append("transactionSubType", getTransactionSubType())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("shelfId", getShelfId())
            .append("shelfCode", getShelfCode())
            .append("shelfName", getShelfName())
            .append("locationId", getLocationId())
            .append("locationCode", getLocationCode())
            .append("locationName", getLocationName())
            .append("batchNo", getBatchNo())
            .append("lotNo", getLotNo())
            .append("serialNo", getSerialNo())
            .append("quantity", getQuantity())
            .append("unitCost", getUnitCost())
            .append("totalCost", getTotalCost())
            .append("beforeQuantity", getBeforeQuantity())
            .append("afterQuantity", getAfterQuantity())
            .append("referenceType", getReferenceType())
            .append("referenceId", getReferenceId())
            .append("referenceNo", getReferenceNo())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("transactionDate", getTransactionDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
