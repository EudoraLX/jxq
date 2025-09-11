package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 模具对象 erp_mold
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpMold extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 模具编码 */
    @Excel(name = "模具编码")
    private String moldCode;

    /** 模具名称 */
    @Excel(name = "模具名称")
    private String moldName;

    /** 模具类型 */
    @Excel(name = "模具类型")
    private String moldType;

    /** 模具分类 */
    @Excel(name = "模具分类")
    private String moldCategory;

    /** 产品物料ID */
    private Long productMaterialId;

    /** 存储位置ID */
    private Long storageLocationId;

    /** 模具重量(kg) */
    @Excel(name = "模具重量(kg)")
    private BigDecimal moldWeight;

    /** 模具尺寸 */
    @Excel(name = "模具尺寸")
    private String moldSize;

    /** 型腔数量 */
    @Excel(name = "型腔数量")
    private Long cavityCount;

    private BigDecimal moldDownTime;
    private BigDecimal moldUpTime;

    /** 最大注塑重量(g) */
    private BigDecimal maxShotWeight;

    /** 最小注塑重量(g) */
    private BigDecimal minShotWeight;

    /** 设计寿命(次) */
    private Long designLife;

    /** 当前使用次数 */
    private Long currentUsageCount;

    /** 维护周期(次) */
    private Long maintenanceCycle;

    /** 上次维护日期 */
    private Date lastMaintenanceDate;

    /** 下次维护日期 */
    private Date nextMaintenanceDate;

    /** 模具状态 */
    @Excel(name = "模具状态")
    private String moldStatus;

    /** 供应商 */
    private Long supplierId;

    /** 采购日期 */
    private Date purchaseDate;

    /** 采购价格 */
    private BigDecimal purchasePrice;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String isActive;

    /** 删除标志(0:正常 1:删除) */
    private String delFlag;

    /** 模具明细信息 */
    private List<ErpMoldDetail> erpMoldDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setMoldType(String moldType) 
    {
        this.moldType = moldType;
    }

    public String getMoldType() 
    {
        return moldType;
    }

    public void setMoldCategory(String moldCategory) 
    {
        this.moldCategory = moldCategory;
    }

    public String getMoldCategory() 
    {
        return moldCategory;
    }

    public void setProductMaterialId(Long productMaterialId) 
    {
        this.productMaterialId = productMaterialId;
    }

    public Long getProductMaterialId() 
    {
        return productMaterialId;
    }

    public void setStorageLocationId(Long storageLocationId) 
    {
        this.storageLocationId = storageLocationId;
    }

    public Long getStorageLocationId() 
    {
        return storageLocationId;
    }

    public void setMoldWeight(BigDecimal moldWeight) 
    {
        this.moldWeight = moldWeight;
    }

    public BigDecimal getMoldWeight() 
    {
        return moldWeight;
    }

    public void setMoldSize(String moldSize) 
    {
        this.moldSize = moldSize;
    }

    public String getMoldSize() 
    {
        return moldSize;
    }

    public void setCavityCount(Long cavityCount) 
    {
        this.cavityCount = cavityCount;
    }

    public Long getCavityCount() 
    {
        return cavityCount;
    }

    public void setMaxShotWeight(BigDecimal maxShotWeight) 
    {
        this.maxShotWeight = maxShotWeight;
    }

    public BigDecimal getMaxShotWeight() 
    {
        return maxShotWeight;
    }

    public void setMinShotWeight(BigDecimal minShotWeight) 
    {
        this.minShotWeight = minShotWeight;
    }

    public BigDecimal getMinShotWeight() 
    {
        return minShotWeight;
    }

    public void setDesignLife(Long designLife) 
    {
        this.designLife = designLife;
    }

    public Long getDesignLife() 
    {
        return designLife;
    }

    public void setCurrentUsageCount(Long currentUsageCount) 
    {
        this.currentUsageCount = currentUsageCount;
    }

    public Long getCurrentUsageCount() 
    {
        return currentUsageCount;
    }

    public void setMaintenanceCycle(Long maintenanceCycle) 
    {
        this.maintenanceCycle = maintenanceCycle;
    }

    public Long getMaintenanceCycle() 
    {
        return maintenanceCycle;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) 
    {
        this.lastMaintenanceDate = lastMaintenanceDate;
    }

    public Date getLastMaintenanceDate() 
    {
        return lastMaintenanceDate;
    }

    public void setNextMaintenanceDate(Date nextMaintenanceDate) 
    {
        this.nextMaintenanceDate = nextMaintenanceDate;
    }

    public Date getNextMaintenanceDate() 
    {
        return nextMaintenanceDate;
    }

    public void setMoldStatus(String moldStatus) 
    {
        this.moldStatus = moldStatus;
    }

    public String getMoldStatus() 
    {
        return moldStatus;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) 
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() 
    {
        return purchasePrice;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public List<ErpMoldDetail> getErpMoldDetailList()
    {
        return erpMoldDetailList;
    }

    public void setErpMoldDetailList(List<ErpMoldDetail> erpMoldDetailList)
    {
        this.erpMoldDetailList = erpMoldDetailList;
    }

    public BigDecimal getMoldDownTime() {
        return moldDownTime;
    }

    public void setMoldDownTime(BigDecimal moldDownTime) {
        this.moldDownTime = moldDownTime;
    }

    public BigDecimal getMoldUpTime() {
        return moldUpTime;
    }

    public void setMoldUpTime(BigDecimal moldUpTime) {
        this.moldUpTime = moldUpTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("moldCode", getMoldCode())
            .append("moldName", getMoldName())
            .append("moldType", getMoldType())
            .append("moldCategory", getMoldCategory())
            .append("productMaterialId", getProductMaterialId())
            .append("storageLocationId", getStorageLocationId())
            .append("moldWeight", getMoldWeight())
            .append("moldSize", getMoldSize())
            .append("cavityCount", getCavityCount())
            .append("maxShotWeight", getMaxShotWeight())
            .append("minShotWeight", getMinShotWeight())
            .append("designLife", getDesignLife())
            .append("currentUsageCount", getCurrentUsageCount())
            .append("maintenanceCycle", getMaintenanceCycle())
            .append("lastMaintenanceDate", getLastMaintenanceDate())
            .append("nextMaintenanceDate", getNextMaintenanceDate())
            .append("moldStatus", getMoldStatus())
            .append("supplierId", getSupplierId())
            .append("purchaseDate", getPurchaseDate())
            .append("purchasePrice", getPurchasePrice())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("erpMoldDetailList", getErpMoldDetailList())
                .append("moldDownTime", getMoldDownTime())
                .append("moldUpTime", getMoldUpTime())
            .toString();
    }
}
