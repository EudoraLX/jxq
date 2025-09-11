package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产订单物料对象 erp_production_order_materials
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public class ErpProductionOrderMaterials extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 生产单编号 */
    @Excel(name = "生产单编号")
    private Long orderId;

    /** 物料ID */
    @Excel(name = "物料ID")
    private Long materialId;

    /** 物料编号 */
    @Excel(name = "物料编号")
    private String materialCode;

    /** 物料名称 */
    @Excel(name = "物料名称")
    private String materialName;

    /** 物料规格 */
    @Excel(name = "物料规格")
    private String materialSpec;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 发料数量 */
    @Excel(name = "发料数量")
    private BigDecimal issuedQty;

    /** 待发数量 */
    @Excel(name = "待发数量")
    private BigDecimal needQty;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
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

    public void setQuantity(BigDecimal quantity) 
    {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() 
    {
        return quantity;
    }
    public void setIssuedQty(BigDecimal issuedQty) 
    {
        this.issuedQty = issuedQty;
    }

    public BigDecimal getIssuedQty() 
    {
        return issuedQty;
    }
    public void setNeedQty(BigDecimal needQty) 
    {
        this.needQty = needQty;
    }

    public BigDecimal getNeedQty() 
    {
        return needQty;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderId", getOrderId())
            .append("materialId", getMaterialId())
            .append("quantity", getQuantity())
            .append("issuedQty", getIssuedQty())
            .append("needQty", getNeedQty())
            .append("unitId", getUnitId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
