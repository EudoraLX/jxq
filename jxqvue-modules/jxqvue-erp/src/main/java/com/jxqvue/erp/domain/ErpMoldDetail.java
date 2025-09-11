package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 模具明细对象 erp_mold_detail
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public class ErpMoldDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 模具ID */
    private Long moldId;

    /** 组件编码 */
    @Excel(name = "组件编码")
    private String componentCode;

    /** 组件名称 */
    @Excel(name = "组件名称")
    private String componentName;

    /** 组件类型 */
    @Excel(name = "组件类型")
    private String componentType;

    /** 材料 */
    private String material;

    /** 硬度 */
    private String hardness;

    /** 表面处理 */
    private String surfaceTreatment;

    /** 尺寸规格 */
    private String dimensions;

    /** 重量(kg) */
    private BigDecimal weight;

    /** 使用寿命(次) */
    private Long lifeCycle;

    /** 当前使用次数 */
    private Long currentUsageCount;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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
    public void setMoldId(Long moldId) 
    {
        this.moldId = moldId;
    }

    public Long getMoldId() 
    {
        return moldId;
    }
    public void setComponentCode(String componentCode) 
    {
        this.componentCode = componentCode;
    }

    public String getComponentCode() 
    {
        return componentCode;
    }
    public void setComponentName(String componentName) 
    {
        this.componentName = componentName;
    }

    public String getComponentName() 
    {
        return componentName;
    }
    public void setComponentType(String componentType) 
    {
        this.componentType = componentType;
    }

    public String getComponentType() 
    {
        return componentType;
    }
    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }
    public void setHardness(String hardness) 
    {
        this.hardness = hardness;
    }

    public String getHardness() 
    {
        return hardness;
    }
    public void setSurfaceTreatment(String surfaceTreatment) 
    {
        this.surfaceTreatment = surfaceTreatment;
    }

    public String getSurfaceTreatment() 
    {
        return surfaceTreatment;
    }
    public void setDimensions(String dimensions) 
    {
        this.dimensions = dimensions;
    }

    public String getDimensions() 
    {
        return dimensions;
    }
    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }
    public void setLifeCycle(Long lifeCycle) 
    {
        this.lifeCycle = lifeCycle;
    }

    public Long getLifeCycle() 
    {
        return lifeCycle;
    }
    public void setCurrentUsageCount(Long currentUsageCount) 
    {
        this.currentUsageCount = currentUsageCount;
    }

    public Long getCurrentUsageCount() 
    {
        return currentUsageCount;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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
            .append("moldId", getMoldId())
            .append("componentCode", getComponentCode())
            .append("componentName", getComponentName())
            .append("componentType", getComponentType())
            .append("material", getMaterial())
            .append("hardness", getHardness())
            .append("surfaceTreatment", getSurfaceTreatment())
            .append("dimensions", getDimensions())
            .append("weight", getWeight())
            .append("lifeCycle", getLifeCycle())
            .append("currentUsageCount", getCurrentUsageCount())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
