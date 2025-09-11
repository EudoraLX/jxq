package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 库存明细表对象 erp_inventory_detail
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventoryDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存明细ID */
    private Long id;

    /** 库存主表ID */
    @Excel(name = "库存主表ID")
    private Long inventoryId;

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

    /** 库存数量 */
    @Excel(name = "库存数量")
    private BigDecimal quantity;

    /** 可用数量 */
    @Excel(name = "可用数量")
    private BigDecimal availableQuantity;

    /** 已分配数量 */
    @Excel(name = "已分配数量")
    private BigDecimal allocatedQuantity;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private BigDecimal frozenQuantity;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 总成本 */
    @Excel(name = "总成本")
    private BigDecimal totalCost;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 质量状态（1合格 2待检 3不合格） */
    @Excel(name = "质量状态", readConverterExp = "1=合格,2=待检,3=不合格")
    private String qualityStatus;

    /** 质量等级（1优等 2一等 3合格 4不合格） */
    @Excel(name = "质量等级", readConverterExp = "1=优等,2=一等,3=合格,4=不合格")
    private String qualityLevel;

    /** 是否启用（0否 1是） */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String isActive;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setInventoryId(Long inventoryId) 
    {
        this.inventoryId = inventoryId;
    }

    public Long getInventoryId() 
    {
        return inventoryId;
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

    public void setAvailableQuantity(BigDecimal availableQuantity) 
    {
        this.availableQuantity = availableQuantity;
    }

    public BigDecimal getAvailableQuantity() 
    {
        return availableQuantity;
    }

    public void setAllocatedQuantity(BigDecimal allocatedQuantity) 
    {
        this.allocatedQuantity = allocatedQuantity;
    }

    public BigDecimal getAllocatedQuantity() 
    {
        return allocatedQuantity;
    }

    public void setFrozenQuantity(BigDecimal frozenQuantity) 
    {
        this.frozenQuantity = frozenQuantity;
    }

    public BigDecimal getFrozenQuantity() 
    {
        return frozenQuantity;
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

    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }

    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setSupplierCode(String supplierCode) 
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }

    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }

    public void setQualityStatus(String qualityStatus) 
    {
        this.qualityStatus = qualityStatus;
    }

    public String getQualityStatus() 
    {
        return qualityStatus;
    }

    public void setQualityLevel(String qualityLevel) 
    {
        this.qualityLevel = qualityLevel;
    }

    public String getQualityLevel() 
    {
        return qualityLevel;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inventoryId", getInventoryId())
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
            .append("availableQuantity", getAvailableQuantity())
            .append("allocatedQuantity", getAllocatedQuantity())
            .append("frozenQuantity", getFrozenQuantity())
            .append("unitCost", getUnitCost())
            .append("totalCost", getTotalCost())
            .append("productionDate", getProductionDate())
            .append("expiryDate", getExpiryDate())
            .append("supplierId", getSupplierId())
            .append("supplierCode", getSupplierCode())
            .append("supplierName", getSupplierName())
            .append("qualityStatus", getQualityStatus())
            .append("qualityLevel", getQualityLevel())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
