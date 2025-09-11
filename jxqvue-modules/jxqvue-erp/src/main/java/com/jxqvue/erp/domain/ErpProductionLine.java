package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产线对象 erp_production_line
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public class ErpProductionLine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 生产线编码 */
    @Excel(name = "生产线编码")
    private String productionLineCode;

    /** 生产线名称 */
    @Excel(name = "生产线名称")
    private String productionLineName;

    /** 生产线类型(1:注塑线 2:后处理线 3:检测线 4:包装线) */
    @Excel(name = "生产线类型", readConverterExp = "1=注塑线,2=后处理线,3=检测线,4=包装线")
    private String productionLineType;

    /** 所属部门ID */
    @Excel(name = "所属部门ID")
    private Long departmentId;

    /** 部门名称 */
    private String departmentName;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 长度 */
    @Excel(name = "长度")
    private BigDecimal length;

    /** 宽度 */
    @Excel(name = "宽度")
    private BigDecimal width;

    /** 高度 */
    @Excel(name = "高度")
    private BigDecimal height;

    /** 设计产能 */
    @Excel(name = "设计产能")
    private BigDecimal designCapacity;

    /** 实际产能 */
    @Excel(name = "实际产能")
    private BigDecimal actualCapacity;

    /** 产能单位 */
    @Excel(name = "产能单位")
    private String capacityUnit;

    /** 效率系数 */
    @Excel(name = "效率系数")
    private BigDecimal efficiencyRate;

    /** 节拍时间 */
    @Excel(name = "节拍时间")
    private BigDecimal taktTime;

    /** 周期时间 */
    @Excel(name = "周期时间")
    private BigDecimal cycleTime;

    /** 状态(0:停用 1:正常 2:维护) */
    @Excel(name = "状态", readConverterExp = "0=停用,1=正常,2=维护")
    private String status;

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

    public void setProductionLineCode(String productionLineCode) 
    {
        this.productionLineCode = productionLineCode;
    }

    public String getProductionLineCode() 
    {
        return productionLineCode;
    }

    public void setProductionLineName(String productionLineName) 
    {
        this.productionLineName = productionLineName;
    }

    public String getProductionLineName() 
    {
        return productionLineName;
    }

    public void setProductionLineType(String productionLineType) 
    {
        this.productionLineType = productionLineType;
    }

    public String getProductionLineType() 
    {
        return productionLineType;
    }

    public void setDepartmentId(Long departmentId) 
    {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() 
    {
        return departmentId;
    }

    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }

    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }

    public void setLength(BigDecimal length) 
    {
        this.length = length;
    }

    public BigDecimal getLength() 
    {
        return length;
    }

    public void setWidth(BigDecimal width) 
    {
        this.width = width;
    }

    public BigDecimal getWidth() 
    {
        return width;
    }

    public void setHeight(BigDecimal height) 
    {
        this.height = height;
    }

    public BigDecimal getHeight() 
    {
        return height;
    }

    public void setDesignCapacity(BigDecimal designCapacity) 
    {
        this.designCapacity = designCapacity;
    }

    public BigDecimal getDesignCapacity() 
    {
        return designCapacity;
    }

    public void setActualCapacity(BigDecimal actualCapacity) 
    {
        this.actualCapacity = actualCapacity;
    }

    public BigDecimal getActualCapacity() 
    {
        return actualCapacity;
    }

    public void setCapacityUnit(String capacityUnit) 
    {
        this.capacityUnit = capacityUnit;
    }

    public String getCapacityUnit() 
    {
        return capacityUnit;
    }

    public void setEfficiencyRate(BigDecimal efficiencyRate) 
    {
        this.efficiencyRate = efficiencyRate;
    }

    public BigDecimal getEfficiencyRate() 
    {
        return efficiencyRate;
    }

    public void setTaktTime(BigDecimal taktTime) 
    {
        this.taktTime = taktTime;
    }

    public BigDecimal getTaktTime() 
    {
        return taktTime;
    }

    public void setCycleTime(BigDecimal cycleTime) 
    {
        this.cycleTime = cycleTime;
    }

    public BigDecimal getCycleTime() 
    {
        return cycleTime;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("productionLineCode", getProductionLineCode())
            .append("productionLineName", getProductionLineName())
            .append("productionLineType", getProductionLineType())
            .append("departmentId", getDepartmentId())
            .append("departmentName", getDepartmentName())
            .append("location", getLocation())
            .append("length", getLength())
            .append("width", getWidth())
            .append("height", getHeight())
            .append("designCapacity", getDesignCapacity())
            .append("actualCapacity", getActualCapacity())
            .append("capacityUnit", getCapacityUnit())
            .append("efficiencyRate", getEfficiencyRate())
            .append("taktTime", getTaktTime())
            .append("cycleTime", getCycleTime())
            .append("status", getStatus())
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
