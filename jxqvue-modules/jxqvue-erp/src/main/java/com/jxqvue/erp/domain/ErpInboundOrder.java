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
 * 入库单表对象 erp_inbound_order
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInboundOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单ID */
    private Long id;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String inboundNo;

    /** 入库单名称 */
    @Excel(name = "入库单名称")
    private String inboundName;

    /** 入库类型（01采购入库 02生产入库 03调拨入库 04退货入库 05其他入库） */
    @Excel(name = "入库类型", readConverterExp = "01=采购入库,02=生产入库,03=调拨入库,04=退货入库,05=其他入库")
    private String inboundType;

    /** 目标仓库ID */
    @Excel(name = "目标仓库ID")
    private Long warehouseId;

    /** 目标仓库编码 */
    @Excel(name = "目标仓库编码")
    private String warehouseCode;

    /** 目标仓库名称 */
    @Excel(name = "目标仓库名称")
    private String warehouseName;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 供应商编码 */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 关联单据类型 */
    @Excel(name = "关联单据类型")
    private String referenceType;

    /** 关联单据ID */
    @Excel(name = "关联单据ID")
    private Long referenceId;

    /** 关联单据号 */
    @Excel(name = "关联单据号")
    private String referenceNo;

    /** 计划入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedDate;

    /** 实际入库日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际入库日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualDate;

    /** 总数量 */
    @Excel(name = "总数量")
    private BigDecimal totalQuantity;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    /** 状态（0=草稿,1=待审核,2=已审核,-1=驳回） */
    @Excel(name = "审核状态", readConverterExp = "0=草稿,1=待审核,2=已审核,-1=驳回")
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

    /** 接收人ID */
    @Excel(name = "接收人ID")
    private Long receiverId;

    /** 接收人姓名 */
    @Excel(name = "接收人姓名")
    private String receiverName;

    /** 接收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "接收时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date receiveTime;

    /** 入库单明细信息 */
    private List<ErpInboundOrderDetail> inboundOrderDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setInboundNo(String inboundNo) 
    {
        this.inboundNo = inboundNo;
    }

    public String getInboundNo() 
    {
        return inboundNo;
    }

    public void setInboundName(String inboundName) 
    {
        this.inboundName = inboundName;
    }

    public String getInboundName() 
    {
        return inboundName;
    }

    public void setInboundType(String inboundType) 
    {
        this.inboundType = inboundType;
    }

    public String getInboundType() 
    {
        return inboundType;
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

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setSupplierCode(String supplierCode) 
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }

    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
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

    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }

    public void setReceiverName(String receiverName) 
    {
        this.receiverName = receiverName;
    }

    public String getReceiverName() 
    {
        return receiverName;
    }

    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }

    public List<ErpInboundOrderDetail> getInboundOrderDetailList()
    {
        return inboundOrderDetailList;
    }

    public void setInboundOrderDetailList(List<ErpInboundOrderDetail> inboundOrderDetailList)
    {
        this.inboundOrderDetailList = inboundOrderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inboundNo", getInboundNo())
            .append("inboundName", getInboundName())
            .append("inboundType", getInboundType())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("supplierId", getSupplierId())
            .append("supplierCode", getSupplierCode())
            .append("supplierName", getSupplierName())
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
            .append("receiverId", getReceiverId())
            .append("receiverName", getReceiverName())
            .append("receiveTime", getReceiveTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
