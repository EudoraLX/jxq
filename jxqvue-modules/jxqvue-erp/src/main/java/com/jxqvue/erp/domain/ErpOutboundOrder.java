package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 出库单表对象 erp_outbound_order
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpOutboundOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库单ID */
    private Long id;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String outboundNo;

    /** 出库单名称 */
    @Excel(name = "出库单名称")
    private String outboundName;

    /** 出库类型（01生产领料 02销售出库 03调拨出库 04其他出库） */
    @Excel(name = "出库类型", readConverterExp = "01=生产领料,02=销售出库,03=调拨出库,04=其他出库")
    private String outboundType;

    /** 源仓库ID */
    @Excel(name = "源仓库ID")
    private Long warehouseId;

    /** 源仓库编码 */
    @Excel(name = "源仓库编码")
    private String warehouseCode;

    /** 源仓库名称 */
    @Excel(name = "源仓库名称")
    private String warehouseName;

    /** 客户ID */
    @Excel(name = "客户ID")
    private Long customerId;

    /** 客户编码 */
    @Excel(name = "客户编码")
    private String customerCode;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** 关联单据类型 */
    @Excel(name = "关联单据类型")
    private String referenceType;

    /** 关联单据ID */
    @Excel(name = "关联单据ID")
    private Long referenceId;

    /** 关联单据号 */
    @Excel(name = "关联单据号")
    private String referenceNo;

    /** 计划出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedDate;

    /** 实际出库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际出库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualDate;

    /** 总数量 */
    @Excel(name = "总数量")
    private BigDecimal totalQuantity;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 状态（0=草稿,1=待审核,2=已审核,-1=驳回） */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=待审核,2=已审核,-1=驳回")
    private String approvalStatus;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long approverId;

    /** 审核人姓名 */
    @Excel(name = "审核人姓名")
    private String approverName;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;

    /** 拣货人ID */
    @Excel(name = "拣货人ID")
    private Long pickerId;

    /** 拣货人姓名 */
    @Excel(name = "拣货人姓名")
    private String pickerName;

    /** 拣货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "拣货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date pickTime;

    /** 出库单明细信息 */
    private List<ErpOutboundOrderDetail> outboundOrderDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setOutboundNo(String outboundNo) 
    {
        this.outboundNo = outboundNo;
    }

    public String getOutboundNo() 
    {
        return outboundNo;
    }

    public void setOutboundName(String outboundName) 
    {
        this.outboundName = outboundName;
    }

    public String getOutboundName() 
    {
        return outboundName;
    }

    public void setOutboundType(String outboundType) 
    {
        this.outboundType = outboundType;
    }

    public String getOutboundType() 
    {
        return outboundType;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setWarehouseCode(String warehouseCode) 
    {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() 
    {
        return warehouseCode;
    }

    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public void setCustomerCode(String customerCode) 
    {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() 
    {
        return customerCode;
    }

    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }

    public void setReferenceType(String referenceType) 
    {
        this.referenceType = referenceType;
    }

    public String getReferenceType() 
    {
        return referenceType;
    }

    public void setReferenceId(Long referenceId) 
    {
        this.referenceId = referenceId;
    }

    public Long getReferenceId() 
    {
        return referenceId;
    }

    public void setReferenceNo(String referenceNo) 
    {
        this.referenceNo = referenceNo;
    }

    public String getReferenceNo() 
    {
        return referenceNo;
    }

    public void setPlannedDate(Date plannedDate) 
    {
        this.plannedDate = plannedDate;
    }

    public Date getPlannedDate() 
    {
        return plannedDate;
    }

    public void setActualDate(Date actualDate) 
    {
        this.actualDate = actualDate;
    }

    public Date getActualDate() 
    {
        return actualDate;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) 
    {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalQuantity() 
    {
        return totalQuantity;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setApproverId(Long approverId)
    {
        this.approverId = approverId;
    }

    public Long getApproverId() 
    {
        return approverId;
    }

    public void setApproverName(String approverName) 
    {
        this.approverName = approverName;
    }

    public String getApproverName() 
    {
        return approverName;
    }

    public void setApproveTime(Date approveTime) 
    {
        this.approveTime = approveTime;
    }

    public Date getApproveTime() 
    {
        return approveTime;
    }

    public void setPickerId(Long pickerId) 
    {
        this.pickerId = pickerId;
    }

    public Long getPickerId() 
    {
        return pickerId;
    }

    public void setPickerName(String pickerName) 
    {
        this.pickerName = pickerName;
    }

    public String getPickerName() 
    {
        return pickerName;
    }

    public void setPickTime(Date pickTime) 
    {
        this.pickTime = pickTime;
    }

    public Date getPickTime() 
    {
        return pickTime;
    }

    public List<ErpOutboundOrderDetail> getOutboundOrderDetailList()
    {
        return outboundOrderDetailList;
    }

    public void setOutboundOrderDetailList(List<ErpOutboundOrderDetail> outboundOrderDetailList)
    {
        this.outboundOrderDetailList = outboundOrderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("outboundNo", getOutboundNo())
            .append("outboundName", getOutboundName())
            .append("outboundType", getOutboundType())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("customerId", getCustomerId())
            .append("customerCode", getCustomerCode())
            .append("customerName", getCustomerName())
            .append("referenceType", getReferenceType())
            .append("referenceId", getReferenceId())
            .append("referenceNo", getReferenceNo())
            .append("plannedDate", getPlannedDate())
            .append("actualDate", getActualDate())
            .append("totalQuantity", getTotalQuantity())
            .append("totalAmount", getTotalAmount())
            .append("approvalStatus", getApprovalStatus())
            .append("approverId", getApproverId())
            .append("approverName", getApproverName())
            .append("approveTime", getApproveTime())
            .append("pickerId", getPickerId())
            .append("pickerName", getPickerName())
            .append("pickTime", getPickTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
