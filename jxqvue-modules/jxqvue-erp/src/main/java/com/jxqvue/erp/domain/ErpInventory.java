package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 库存主表对象 erp_inventory
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 库存ID */
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

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 物料型号 */
    @Excel(name = "物料型号")
    private String materialModel;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 分类编码 */
    @Excel(name = "分类编码")
    private String categoryCode;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 基本单位 */
    @Excel(name = "基本单位")
    private String unit;

    /** 总库存数量 */
    @Excel(name = "总库存数量")
    private BigDecimal totalQuantity;

    /** 可用库存数量 */
    @Excel(name = "可用库存数量")
    private BigDecimal availableQuantity;

    /** 已分配数量 */
    @Excel(name = "已分配数量")
    private BigDecimal allocatedQuantity;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private BigDecimal frozenQuantity;

    /** 在途数量 */
    @Excel(name = "在途数量")
    private BigDecimal onOrderQuantity;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 总成本 */
    @Excel(name = "总成本")
    private BigDecimal totalCost;

    /** 最后入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastInDate;

    /** 最后出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastOutDate;

    /** 最后盘点日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastCountDate;

    /** 最小库存 */
    @Excel(name = "最小库存")
    private BigDecimal minStock;

    /** 最大库存 */
    @Excel(name = "最大库存")
    private BigDecimal maxStock;

    /** 安全库存 */
    @Excel(name = "安全库存")
    private BigDecimal safetyStock;

    /** 保质期(天) */
    @Excel(name = "保质期(天)")
    private Long shelfLife;

    /** 到期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

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

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
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

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) 
    {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalQuantity() 
    {
        return totalQuantity;
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

    public void setOnOrderQuantity(BigDecimal onOrderQuantity) 
    {
        this.onOrderQuantity = onOrderQuantity;
    }

    public BigDecimal getOnOrderQuantity() 
    {
        return onOrderQuantity;
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

    public void setLastInDate(Date lastInDate) 
    {
        this.lastInDate = lastInDate;
    }

    public Date getLastInDate() 
    {
        return lastInDate;
    }

    public void setLastOutDate(Date lastOutDate) 
    {
        this.lastOutDate = lastOutDate;
    }

    public Date getLastOutDate() 
    {
        return lastOutDate;
    }

    public void setLastCountDate(Date lastCountDate) 
    {
        this.lastCountDate = lastCountDate;
    }

    public Date getLastCountDate() 
    {
        return lastCountDate;
    }

    public void setMinStock(BigDecimal minStock) 
    {
        this.minStock = minStock;
    }

    public BigDecimal getMinStock() 
    {
        return minStock;
    }

    public void setMaxStock(BigDecimal maxStock) 
    {
        this.maxStock = maxStock;
    }

    public BigDecimal getMaxStock() 
    {
        return maxStock;
    }

    public void setSafetyStock(BigDecimal safetyStock) 
    {
        this.safetyStock = safetyStock;
    }

    public BigDecimal getSafetyStock() 
    {
        return safetyStock;
    }

    public void setShelfLife(Long shelfLife) 
    {
        this.shelfLife = shelfLife;
    }

    public Long getShelfLife() 
    {
        return shelfLife;
    }

    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
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
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialModel", getMaterialModel())
            .append("categoryId", getCategoryId())
            .append("categoryCode", getCategoryCode())
            .append("categoryName", getCategoryName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("unit", getUnit())
            .append("totalQuantity", getTotalQuantity())
            .append("availableQuantity", getAvailableQuantity())
            .append("allocatedQuantity", getAllocatedQuantity())
            .append("frozenQuantity", getFrozenQuantity())
            .append("onOrderQuantity", getOnOrderQuantity())
            .append("unitCost", getUnitCost())
            .append("totalCost", getTotalCost())
            .append("lastInDate", getLastInDate())
            .append("lastOutDate", getLastOutDate())
            .append("lastCountDate", getLastCountDate())
            .append("minStock", getMinStock())
            .append("maxStock", getMaxStock())
            .append("safetyStock", getSafetyStock())
            .append("shelfLife", getShelfLife())
            .append("expiryDate", getExpiryDate())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
