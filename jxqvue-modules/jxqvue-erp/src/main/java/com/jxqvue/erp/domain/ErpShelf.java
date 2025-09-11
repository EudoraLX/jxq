package com.jxqvue.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 货架对象 erp_shelf
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public class ErpShelf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 货架编码 */
    @Excel(name = "货架编码")
    private String shelfCode;

    /** 货架名称 */
    @Excel(name = "货架名称")
    private String shelfName;

    /** 所属仓库ID */
    @Excel(name = "所属仓库ID")
    private Long warehouseId;

    /** 货架类型(1:普通货架 2:重型货架 3:悬臂货架 4:阁楼货架 5:流利式货架) */
    @Excel(name = "货架类型", readConverterExp = "1=普通货架,2=重型货架,3=悬臂货架,4=阁楼货架,5=流利式货架")
    private String shelfType;

    /** 区域编码 */
    @Excel(name = "区域编码")
    private String areaCode;

    /** 排号 */
    @Excel(name = "排号")
    private Integer rowNum;

    /** 列号 */
    @Excel(name = "列号")
    private Integer columnNum;

    /** 货架高度(米) */
    @Excel(name = "货架高度(米)")
    private BigDecimal height;

    /** 货架宽度(米) */
    @Excel(name = "货架宽度(米)")
    private BigDecimal width;

    /** 货架深度(米) */
    @Excel(name = "货架深度(米)")
    private BigDecimal depth;

    /** 最大承重(kg) */
    @Excel(name = "最大承重(kg)")
    private BigDecimal maxWeight;

    /** 是否启用(0:禁用 1:启用) */
    @Excel(name = "是否启用", readConverterExp = "0=禁用,1=启用")
    private String isActive;

    /** 删除标志(0:正常 1:删除) */
    private String delFlag;

    /** 货位信息 */
    private List<ErpLocation> locationList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setShelfType(String shelfType) 
    {
        this.shelfType = shelfType;
    }

    public String getShelfType() 
    {
        return shelfType;
    }
    public void setAreaCode(String areaCode) 
    {
        this.areaCode = areaCode;
    }

    public String getAreaCode() 
    {
        return areaCode;
    }
    public void setRowNum(Integer rowNum) 
    {
        this.rowNum = rowNum;
    }

    public Integer getRowNum() 
    {
        return rowNum;
    }
    public void setColumnNum(Integer columnNum) 
    {
        this.columnNum = columnNum;
    }

    public Integer getColumnNum() 
    {
        return columnNum;
    }
    public void setHeight(BigDecimal height) 
    {
        this.height = height;
    }

    public BigDecimal getHeight() 
    {
        return height;
    }
    public void setWidth(BigDecimal width) 
    {
        this.width = width;
    }

    public BigDecimal getWidth() 
    {
        return width;
    }
    public void setDepth(BigDecimal depth) 
    {
        this.depth = depth;
    }

    public BigDecimal getDepth() 
    {
        return depth;
    }
    public void setMaxWeight(BigDecimal maxWeight) 
    {
        this.maxWeight = maxWeight;
    }

    public BigDecimal getMaxWeight() 
    {
        return maxWeight;
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

    public List<ErpLocation> getLocationList()
    {
        return locationList;
    }

    public void setLocationList(List<ErpLocation> locationList)
    {
        this.locationList = locationList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("shelfCode", getShelfCode())
            .append("shelfName", getShelfName())
            .append("warehouseId", getWarehouseId())
            .append("shelfType", getShelfType())
            .append("areaCode", getAreaCode())
            .append("rowNum", getRowNum())
            .append("columnNum", getColumnNum())
            .append("height", getHeight())
            .append("width", getWidth())
            .append("depth", getDepth())
            .append("maxWeight", getMaxWeight())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("locationList", getLocationList())
            .toString();
    }
}
