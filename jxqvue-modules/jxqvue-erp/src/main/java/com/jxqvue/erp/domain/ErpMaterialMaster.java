package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 物料主数据对象 erp_material_master
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpMaterialMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物料ID */
    private Long id;

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

    /** 分类 */
    @Excel(name = "分类")
    private Long categoryId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 基本单位 */
    private String unitName;

    /** 标准单价 */
    private BigDecimal unitPrice;

    /** 最小库存 */
    private BigDecimal minStock;

    /** 最大库存 */
    private BigDecimal maxStock;

    /** 安全库存 */
    private BigDecimal safetyStock;

    /** 提前期(天) */
    private Long leadTime;

    /** 保质期(天) */
    private Long shelfLife;

    /** 是否采购件（0否 1是） */
    private String isPurchased;

    /** 是否制造件（0否 1是） */
    private String isManufactured;

    /** 是否销售件（0否 1是） */
    private String isSales;

    /** 是否库存件（0否 1是） */
    private String isInventory;

    /** 是否启用（0否 1是） */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String isActive;

    /** 工艺路线ID */
    private Long processRouteId;

    /** 工艺路线编码（用于显示） */
    private String processRouteCode;

    /** 工艺路线名称（用于显示） */
    private String processRouteName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
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

    public void setLeadTime(Long leadTime) 
    {
        this.leadTime = leadTime;
    }

    public Long getLeadTime() 
    {
        return leadTime;
    }

    public void setShelfLife(Long shelfLife) 
    {
        this.shelfLife = shelfLife;
    }

    public Long getShelfLife() 
    {
        return shelfLife;
    }

    public void setIsPurchased(String isPurchased) 
    {
        this.isPurchased = isPurchased;
    }

    public String getIsPurchased() 
    {
        return isPurchased;
    }

    public void setIsManufactured(String isManufactured) 
    {
        this.isManufactured = isManufactured;
    }

    public String getIsManufactured() 
    {
        return isManufactured;
    }

    public void setIsSales(String isSales) 
    {
        this.isSales = isSales;
    }

    public String getIsSales() 
    {
        return isSales;
    }

    public void setIsInventory(String isInventory) 
    {
        this.isInventory = isInventory;
    }

    public String getIsInventory() 
    {
        return isInventory;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    public void setProcessRouteId(Long processRouteId) 
    {
        this.processRouteId = processRouteId;
    }

    public Long getProcessRouteId() 
    {
        return processRouteId;
    }

    public void setProcessRouteCode(String processRouteCode) 
    {
        this.processRouteCode = processRouteCode;
    }

    public String getProcessRouteCode() 
    {
        return processRouteCode;
    }

    public void setProcessRouteName(String processRouteName) 
    {
        this.processRouteName = processRouteName;
    }

    public String getProcessRouteName() 
    {
        return processRouteName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialModel", getMaterialModel())
            .append("categoryId", getCategoryId())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("unitPrice", getUnitPrice())
            .append("minStock", getMinStock())
            .append("maxStock", getMaxStock())
            .append("safetyStock", getSafetyStock())
            .append("leadTime", getLeadTime())
            .append("shelfLife", getShelfLife())
            .append("isPurchased", getIsPurchased())
            .append("isManufactured", getIsManufactured())
            .append("isSales", getIsSales())
            .append("isInventory", getIsInventory())
            .append("isActive", getIsActive())
            .append("processRouteId", getProcessRouteId())
            .append("processRouteCode", getProcessRouteCode())
            .append("processRouteName", getProcessRouteName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
