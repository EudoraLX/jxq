package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * BOM明细表对象 erp_bom_detail
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public class ErpBomDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** BOM ID */
    @Excel(name = "BOM ID")
    private Long bomId;

    /** 组件物料(关联erp_material_master表id字段) */
    @Excel(name = "组件物料")
    private Long materialId;

    /** 组件物料编码（用于显示） */
    private String materialCode;

    /** 组件物料名称（用于显示） */
    private String materialName;

    /** 组件物料规格（用于显示） */
    private String materialSpec;

    /** 组件单位(根据material_id的值自动更新) */
    @Excel(name = "组件单位")
    private Long materialUnitId;

    /** 组件单位名称（用于显示） */
    private String materialUnitName;

    /** 单件用量 */
    @Excel(name = "单件用量")
    private BigDecimal quantityPerUnit;

    /** 损耗率 */
    @Excel(name = "损耗率")
    private BigDecimal lossRate;

    /** 有效用量 */
    @Excel(name = "有效用量")
    private BigDecimal effectiveQuantity;

    /** 位置 */
    @Excel(name = "位置")
    private String position;

    /** 工序顺序 */
    @Excel(name = "工序顺序")
    private Integer operationSequence;

    /** 是否关键件（数据字典类型为business_yes_no，默认值为1） */
    @Excel(name = "是否关键件", readConverterExp = "0=否,1=是")
    private String isCritical;

    /** 是否可选件（数据字典类型为business_yes_no，默认值为1） */
    @Excel(name = "是否可选件", readConverterExp = "0=否,1=是")
    private String isOptional;

    /** 单位成本 */
    @Excel(name = "单位成本")
    private BigDecimal unitCost;

    /** 总成本 */
    @Excel(name = "总成本")
    private BigDecimal totalCost;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBomId(Long bomId) 
    {
        this.bomId = bomId;
    }

    public Long getBomId() 
    {
        return bomId;
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

    public void setMaterialUnitId(Long materialUnitId) 
    {
        this.materialUnitId = materialUnitId;
    }

    public Long getMaterialUnitId() 
    {
        return materialUnitId;
    }

    public void setMaterialUnitName(String materialUnitName) 
    {
        this.materialUnitName = materialUnitName;
    }

    public String getMaterialUnitName() 
    {
        return materialUnitName;
    }

    public void setQuantityPerUnit(BigDecimal quantityPerUnit) 
    {
        this.quantityPerUnit = quantityPerUnit;
    }

    public BigDecimal getQuantityPerUnit() 
    {
        return quantityPerUnit;
    }

    public void setLossRate(BigDecimal lossRate) 
    {
        this.lossRate = lossRate;
    }

    public BigDecimal getLossRate() 
    {
        return lossRate;
    }

    public void setEffectiveQuantity(BigDecimal effectiveQuantity) 
    {
        this.effectiveQuantity = effectiveQuantity;
    }

    public BigDecimal getEffectiveQuantity() 
    {
        return effectiveQuantity;
    }

    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }

    public void setOperationSequence(Integer operationSequence) 
    {
        this.operationSequence = operationSequence;
    }

    public Integer getOperationSequence() 
    {
        return operationSequence;
    }

    public void setIsCritical(String isCritical) 
    {
        this.isCritical = isCritical;
    }

    public String getIsCritical() 
    {
        return isCritical;
    }

    public void setIsOptional(String isOptional) 
    {
        this.isOptional = isOptional;
    }

    public String getIsOptional() 
    {
        return isOptional;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bomId", getBomId())
            .append("materialId", getMaterialId())
            .append("materialCode", getMaterialCode())
            .append("materialName", getMaterialName())
            .append("materialSpec", getMaterialSpec())
            .append("materialUnitId", getMaterialUnitId())
            .append("materialUnitName", getMaterialUnitName())
            .append("quantityPerUnit", getQuantityPerUnit())
            .append("lossRate", getLossRate())
            .append("effectiveQuantity", getEffectiveQuantity())
            .append("position", getPosition())
            .append("operationSequence", getOperationSequence())
            .append("isCritical", getIsCritical())
            .append("isOptional", getIsOptional())
            .append("unitCost", getUnitCost())
            .append("totalCost", getTotalCost())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
