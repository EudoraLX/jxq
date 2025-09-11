package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 生产报工单对象 erp_production_report
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpProductionReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报工单ID */
    private Long id;

    /** 报工单编码 */
    @Excel(name = "报工单编码")
    private String reportCode;

    /** 生产订单 */
    @Excel(name = "生产订单")
    private Long productionOrderId;

    /** 产品 */
    @Excel(name = "产品")
    private Long productId;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 操作员 */
    @Excel(name = "操作员")
    private Long operatorId;

    /** 报工日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "报工日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reportDate;

    /** 报工数量 */
    @Excel(name = "报工数量")
    private BigDecimal actualQuantity;

    /** 合格数量 */
    @Excel(name = "合格数量")
    private BigDecimal qualifiedQuantity;

    /** 不良数量 */
    @Excel(name = "不良数量")
    private BigDecimal defectiveQuantity;

    /** 报废数量 */
    @Excel(name = "报废数量")
    private BigDecimal scrapQuantity;

    /** 返工数量 */
    @Excel(name = "返工数量")
    private BigDecimal reworkQuantity;

    /** 实际工时（小时） */
    @Excel(name = "实际工时（小时）")
    private BigDecimal actualHours;

    /** 合格率 */
    @Excel(name = "合格率")
    private BigDecimal qualityRate;

    /** 审批状态 */
    @Excel(name = "审批状态", readConverterExp = "0=待审批,1=已审批,2=已拒绝")
    private String approvalStatus;

    // 关联字段（用于显示）
    /** 生产订单编码 */
    @Excel(name = "生产订单编码")
    private String orderCode;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String materialCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String materialName;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 操作员姓名 */
    @Excel(name = "操作员姓名")
    private String operatorName;

    /** 产品编码（用于前端显示） */
    private String productCode;

    /** 产品名称（用于前端显示） */
    private String productName;

    /** 单位ID（用于前端显示） */
    private Long unitId;

    /** 单位编码（用于前端显示） */
    private String unitCode;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReportCode(String reportCode) 
    {
        this.reportCode = reportCode;
    }

    public String getReportCode() 
    {
        return reportCode;
    }
    public void setProductionOrderId(Long productionOrderId) 
    {
        this.productionOrderId = productionOrderId;
    }

    public Long getProductionOrderId() 
    {
        return productionOrderId;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }
    public void setReportDate(Date reportDate) 
    {
        this.reportDate = reportDate;
    }

    public Date getReportDate() 
    {
        return reportDate;
    }
    public void setActualQuantity(BigDecimal actualQuantity) 
    {
        this.actualQuantity = actualQuantity;
    }

    public BigDecimal getActualQuantity() 
    {
        return actualQuantity;
    }
    public void setQualifiedQuantity(BigDecimal qualifiedQuantity) 
    {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public BigDecimal getQualifiedQuantity() 
    {
        return qualifiedQuantity;
    }
    public void setDefectiveQuantity(BigDecimal defectiveQuantity) 
    {
        this.defectiveQuantity = defectiveQuantity;
    }

    public BigDecimal getDefectiveQuantity() 
    {
        return defectiveQuantity;
    }
    public void setScrapQuantity(BigDecimal scrapQuantity) 
    {
        this.scrapQuantity = scrapQuantity;
    }

    public BigDecimal getScrapQuantity() 
    {
        return scrapQuantity;
    }
    public void setReworkQuantity(BigDecimal reworkQuantity) 
    {
        this.reworkQuantity = reworkQuantity;
    }

    public BigDecimal getReworkQuantity() 
    {
        return reworkQuantity;
    }
    public void setActualHours(BigDecimal actualHours) 
    {
        this.actualHours = actualHours;
    }

    public BigDecimal getActualHours() 
    {
        return actualHours;
    }
    public void setQualityRate(BigDecimal qualityRate) 
    {
        this.qualityRate = qualityRate;
    }

    public BigDecimal getQualityRate() 
    {
        return qualityRate;
    }
    public void setApprovalStatus(String approvalStatus) 
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() 
    {
        return approvalStatus;
    }

    public String getOrderCode() 
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode) 
    {
        this.orderCode = orderCode;
    }

    public String getMaterialCode() 
    {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) 
    {
        this.materialCode = materialCode;
    }

    public String getMaterialName() 
    {
        return materialName;
    }

    public void setMaterialName(String materialName) 
    {
        this.materialName = materialName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getProcessCode() 
    {
        return processCode;
    }

    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessName() 
    {
        return processName;
    }

    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getProductCode() 
    {
        return productCode;
    }

    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public Long getUnitId() 
    {
        return unitId;
    }

    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public String getUnitCode() 
    {
        return unitCode;
    }

    public void setUnitCode(String unitCode) 
    {
        this.unitCode = unitCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reportCode", getReportCode())
            .append("productionOrderId", getProductionOrderId())
            .append("productId", getProductId())
            .append("processId", getProcessId())
            .append("operatorId", getOperatorId())
            .append("reportDate", getReportDate())
            .append("actualQuantity", getActualQuantity())
            .append("qualifiedQuantity", getQualifiedQuantity())
            .append("defectiveQuantity", getDefectiveQuantity())
            .append("scrapQuantity", getScrapQuantity())
            .append("reworkQuantity", getReworkQuantity())
            .append("actualHours", getActualHours())
            .append("qualityRate", getQualityRate())
            .append("approvalStatus", getApprovalStatus())
            .append("remark", getRemark())
            .append("orderCode", getOrderCode())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("unitId", getUnitId())
            .append("unitCode", getUnitCode())
            .append("unitName", getUnitName())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("operatorName", getOperatorName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
