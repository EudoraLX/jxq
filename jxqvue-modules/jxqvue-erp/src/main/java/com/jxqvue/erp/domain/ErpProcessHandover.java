package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工序交接单对象 erp_process_handover
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpProcessHandover extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 交接单ID */
    private Long id;

    /** 交接单编码 */
    @Excel(name = "交接单编码")
    private String handoverCode;

    /** 交接单名称 */
    @Excel(name = "交接单名称")
    private String handoverName;

    /** 生产订单 */
    @Excel(name = "生产订单")
    private Long productionOrderId;

    /** 源工序 */
    @Excel(name = "源工序")
    private Long fromProcessId;

    /** 目标工序 */
    @Excel(name = "目标工序")
    private Long toProcessId;

    /** 交接日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交接日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handoverDate;

    /** 交接时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "交接时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handoverTime;

    /** 产品 */
    @Excel(name = "产品")
    private Long productId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 交接数量 */
    @Excel(name = "交接数量")
    private BigDecimal handoverQuantity;

    /** 合格数量 */
    @Excel(name = "合格数量")
    private BigDecimal qualifiedQuantity;

    /** 不良数量 */
    @Excel(name = "不良数量")
    private BigDecimal defectiveQuantity;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 批号 */
    @Excel(name = "批号")
    private String lotNo;

    /** 交接人 */
    @Excel(name = "交接人")
    private Long fromOperatorId;

    /** 接收人 */
    @Excel(name = "接收人")
    private Long toOperatorId;

    /** 交接类型（1正常交接 2返工交接 3补料交接） */
    @Excel(name = "交接类型", readConverterExp = "1=正常交接,2=返工交接,3=补料交接")
    private String handoverType;

    /** 质量状态（1合格 2待检 3不合格） */
    @Excel(name = "质量状态", readConverterExp = "1=合格,2=待检,3=不合格")
    private String qualityStatus;

    /** 审批状态 */
    @Excel(name = "审批状态", readConverterExp = "0=草稿,1=待审批,2=已完成")
    private String approvalStatus;

    // 关联字段（用于显示）
    /** 生产订单编码 */
    @Excel(name = "生产订单编码")
    private String orderCode;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String productCode;

    /** 产品名称 */
    @Excel(name = "产品名称")
    private String productName;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 源工序编码 */
    @Excel(name = "源工序编码")
    private String fromProcessCode;

    /** 源工序名称 */
    @Excel(name = "源工序名称")
    private String fromProcessName;

    /** 目标工序编码 */
    @Excel(name = "目标工序编码")
    private String toProcessCode;

    /** 目标工序名称 */
    @Excel(name = "目标工序名称")
    private String toProcessName;

    /** 交接人姓名 */
    @Excel(name = "交接人姓名")
    private String fromOperatorName;

    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    private String toOperatorName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHandoverCode(String handoverCode) 
    {
        this.handoverCode = handoverCode;
    }

    public String getHandoverCode() 
    {
        return handoverCode;
    }
    public void setHandoverName(String handoverName) 
    {
        this.handoverName = handoverName;
    }

    public String getHandoverName() 
    {
        return handoverName;
    }
    public void setProductionOrderId(Long productionOrderId) 
    {
        this.productionOrderId = productionOrderId;
    }

    public Long getProductionOrderId() 
    {
        return productionOrderId;
    }
    public void setFromProcessId(Long fromProcessId) 
    {
        this.fromProcessId = fromProcessId;
    }

    public Long getFromProcessId() 
    {
        return fromProcessId;
    }
    public void setToProcessId(Long toProcessId) 
    {
        this.toProcessId = toProcessId;
    }

    public Long getToProcessId() 
    {
        return toProcessId;
    }
    public void setHandoverDate(Date handoverDate) 
    {
        this.handoverDate = handoverDate;
    }

    public Date getHandoverDate() 
    {
        return handoverDate;
    }
    public void setHandoverTime(Date handoverTime) 
    {
        this.handoverTime = handoverTime;
    }

    public Date getHandoverTime() 
    {
        return handoverTime;
    }
    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setHandoverQuantity(BigDecimal handoverQuantity) 
    {
        this.handoverQuantity = handoverQuantity;
    }

    public BigDecimal getHandoverQuantity() 
    {
        return handoverQuantity;
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
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setLotNo(String lotNo) 
    {
        this.lotNo = lotNo;
    }

    public String getLotNo() 
    {
        return lotNo;
    }
    public void setFromOperatorId(Long fromOperatorId) 
    {
        this.fromOperatorId = fromOperatorId;
    }

    public Long getFromOperatorId() 
    {
        return fromOperatorId;
    }
    public void setToOperatorId(Long toOperatorId) 
    {
        this.toOperatorId = toOperatorId;
    }

    public Long getToOperatorId() 
    {
        return toOperatorId;
    }
    public void setHandoverType(String handoverType) 
    {
        this.handoverType = handoverType;
    }

    public String getHandoverType() 
    {
        return handoverType;
    }
    public void setQualityStatus(String qualityStatus) 
    {
        this.qualityStatus = qualityStatus;
    }

    public String getQualityStatus() 
    {
        return qualityStatus;
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

    public String getUnitName() 
    {
        return unitName;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getFromProcessCode() 
    {
        return fromProcessCode;
    }

    public void setFromProcessCode(String fromProcessCode) 
    {
        this.fromProcessCode = fromProcessCode;
    }

    public String getFromProcessName() 
    {
        return fromProcessName;
    }

    public void setFromProcessName(String fromProcessName) 
    {
        this.fromProcessName = fromProcessName;
    }

    public String getToProcessCode() 
    {
        return toProcessCode;
    }

    public void setToProcessCode(String toProcessCode) 
    {
        this.toProcessCode = toProcessCode;
    }

    public String getToProcessName() 
    {
        return toProcessName;
    }

    public void setToProcessName(String toProcessName) 
    {
        this.toProcessName = toProcessName;
    }

    public String getFromOperatorName() 
    {
        return fromOperatorName;
    }

    public void setFromOperatorName(String fromOperatorName) 
    {
        this.fromOperatorName = fromOperatorName;
    }

    public String getToOperatorName() 
    {
        return toOperatorName;
    }

    public void setToOperatorName(String toOperatorName) 
    {
        this.toOperatorName = toOperatorName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("handoverCode", getHandoverCode())
            .append("handoverName", getHandoverName())
            .append("productionOrderId", getProductionOrderId())
            .append("fromProcessId", getFromProcessId())
            .append("toProcessId", getToProcessId())
            .append("handoverDate", getHandoverDate())
            .append("handoverTime", getHandoverTime())
            .append("productId", getProductId())
            .append("unitId", getUnitId())
            .append("handoverQuantity", getHandoverQuantity())
            .append("qualifiedQuantity", getQualifiedQuantity())
            .append("defectiveQuantity", getDefectiveQuantity())
            .append("batchNo", getBatchNo())
            .append("lotNo", getLotNo())
            .append("fromOperatorId", getFromOperatorId())
            .append("toOperatorId", getToOperatorId())
            .append("handoverType", getHandoverType())
            .append("qualityStatus", getQualityStatus())
            .append("approvalStatus", getApprovalStatus())
            .append("remark", getRemark())
            .append("orderCode", getOrderCode())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("unitName", getUnitName())
            .append("fromProcessCode", getFromProcessCode())
            .append("fromProcessName", getFromProcessName())
            .append("toProcessCode", getToProcessCode())
            .append("toProcessName", getToProcessName())
            .append("fromOperatorName", getFromOperatorName())
            .append("toOperatorName", getToOperatorName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
