package com.jxqvue.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 货位对象 erp_location
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public class ErpLocation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 货位编码 */
    @Excel(name = "货位编码")
    private String locationCode;

    /** 货位名称 */
    @Excel(name = "货位名称")
    private String locationName;

    /** 所属货架ID */
    @Excel(name = "所属货架ID")
    private Long shelfId;

    /** 所属仓库ID */
    @Excel(name = "所属仓库ID")
    private Long warehouseId;

    /** 货位类型(1:普通货位 2:特殊货位 3:临时货位 4:待检货位 5:不良品货位) */
    @Excel(name = "货位类型", readConverterExp = "1=普通货位,2=特殊货位,3=临时货位,4=待检货位,5=不良品货位")
    private String locationType;

    /** 层号 */
    @Excel(name = "层号")
    private Integer levelNum;

    /** 位号 */
    @Excel(name = "位号")
    private Integer positionNum;

    /** 货位容量 */
    @Excel(name = "货位容量")
    private BigDecimal capacity;

    /** 已用容量 */
    @Excel(name = "已用容量")
    private BigDecimal usedCapacity;

    /** 最大承重(kg) */
    @Excel(name = "最大承重(kg)")
    private BigDecimal maxWeight;

    /** 当前重量(kg) */
    @Excel(name = "当前重量(kg)")
    private BigDecimal currentWeight;

    /** 是否占用(0:空闲 1:占用 2:部分占用) */
    @Excel(name = "是否占用", readConverterExp = "0=空闲,1=占用,2=部分占用")
    private String isOccupied;

    /** 是否启用(0:禁用 1:启用) */
    @Excel(name = "是否启用", readConverterExp = "0=禁用,1=启用")
    private String isActive;

    /** 删除标志(0:正常 1:删除) */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setShelfId(Long shelfId) 
    {
        this.shelfId = shelfId;
    }

    public Long getShelfId() 
    {
        return shelfId;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setLocationType(String locationType) 
    {
        this.locationType = locationType;
    }

    public String getLocationType() 
    {
        return locationType;
    }

    public void setLevelNum(Integer levelNum) 
    {
        this.levelNum = levelNum;
    }

    public Integer getLevelNum() 
    {
        return levelNum;
    }

    public void setPositionNum(Integer positionNum) 
    {
        this.positionNum = positionNum;
    }

    public Integer getPositionNum() 
    {
        return positionNum;
    }

    public void setCapacity(BigDecimal capacity) 
    {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity() 
    {
        return capacity;
    }

    public void setUsedCapacity(BigDecimal usedCapacity) 
    {
        this.usedCapacity = usedCapacity;
    }

    public BigDecimal getUsedCapacity() 
    {
        return usedCapacity;
    }

    public void setMaxWeight(BigDecimal maxWeight) 
    {
        this.maxWeight = maxWeight;
    }

    public BigDecimal getMaxWeight() 
    {
        return maxWeight;
    }

    public void setCurrentWeight(BigDecimal currentWeight) 
    {
        this.currentWeight = currentWeight;
    }

    public BigDecimal getCurrentWeight() 
    {
        return currentWeight;
    }

    public void setIsOccupied(String isOccupied) 
    {
        this.isOccupied = isOccupied;
    }

    public String getIsOccupied() 
    {
        return isOccupied;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("locationCode", getLocationCode())
            .append("locationName", getLocationName())
            .append("shelfId", getShelfId())
            .append("warehouseId", getWarehouseId())
            .append("locationType", getLocationType())
            .append("levelNum", getLevelNum())
            .append("positionNum", getPositionNum())
            .append("capacity", getCapacity())
            .append("usedCapacity", getUsedCapacity())
            .append("maxWeight", getMaxWeight())
            .append("currentWeight", getCurrentWeight())
            .append("isOccupied", getIsOccupied())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
