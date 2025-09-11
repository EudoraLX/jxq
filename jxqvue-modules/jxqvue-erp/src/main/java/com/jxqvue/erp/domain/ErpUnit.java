package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 计量单位对象 erp_unit
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpUnit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 单位编码 */
    @Excel(name = "单位编码")
    private String unitCode;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位符号 */
    @Excel(name = "单位符号")
    private String unitSymbol;

    /** 是否基本单位(0:否 1:是) */
    @Excel(name = "是否基本单位(0:否 1:是)")
    private String isBaseUnit;

    /** 换算比例 */
    @Excel(name = "换算比例")
    private BigDecimal conversionRate;

    /** 基本单位ID */
    @Excel(name = "基本单位ID")
    private Long baseUnitId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;



    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setUnitSymbol(String unitSymbol) 
    {
        this.unitSymbol = unitSymbol;
    }

    public String getUnitSymbol() 
    {
        return unitSymbol;
    }

    public void setIsBaseUnit(String isBaseUnit) 
    {
        this.isBaseUnit = isBaseUnit;
    }

    public String getIsBaseUnit() 
    {
        return isBaseUnit;
    }

    public void setConversionRate(BigDecimal conversionRate) 
    {
        this.conversionRate = conversionRate;
    }

    public BigDecimal getConversionRate() 
    {
        return conversionRate;
    }

    public void setBaseUnitId(Long baseUnitId) 
    {
        this.baseUnitId = baseUnitId;
    }

    public Long getBaseUnitId() 
    {
        return baseUnitId;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitCode", getUnitCode())
            .append("unitName", getUnitName())
            .append("unitSymbol", getUnitSymbol())
            .append("isBaseUnit", getIsBaseUnit())
            .append("conversionRate", getConversionRate())
            .append("baseUnitId", getBaseUnitId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
