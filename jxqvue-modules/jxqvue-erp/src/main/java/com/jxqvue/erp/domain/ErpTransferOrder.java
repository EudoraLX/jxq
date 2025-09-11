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
 * 调拨单表对象 erp_transfer_order
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpTransferOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨单ID */
    private Long id;

    /** 调拨单号 */
    @Excel(name = "调拨单号")
    private String transferNo;

    /** 调拨单名称 */
    @Excel(name = "调拨单名称")
    private String transferName;

    /** 调拨类型（01仓库调拨 02货位调拨） */
    @Excel(name = "调拨类型", readConverterExp = "01=仓库调拨,02=货位调拨")
    private String transferType;

    /** 源仓库ID */
    @Excel(name = "源仓库ID")
    private Long fromWarehouseId;

    /** 源仓库编码 */
    @Excel(name = "源仓库编码")
    private String fromWarehouseCode;

    /** 源仓库名称 */
    @Excel(name = "源仓库名称")
    private String fromWarehouseName;

    /** 源货架ID */
    @Excel(name = "源货架ID")
    private Long fromShelfId;

    /** 源货架编码 */
    @Excel(name = "源货架编码")
    private String fromShelfCode;

    /** 源货架名称 */
    @Excel(name = "源货架名称")
    private String fromShelfName;

    /** 源货位ID */
    @Excel(name = "源货位ID")
    private Long fromLocationId;

    /** 源货位编码 */
    @Excel(name = "源货位编码")
    private String fromLocationCode;

    /** 源货位名称 */
    @Excel(name = "源货位名称")
    private String fromLocationName;

    /** 目标仓库ID */
    @Excel(name = "目标仓库ID")
    private Long toWarehouseId;

    /** 目标仓库编码 */
    @Excel(name = "目标仓库编码")
    private String toWarehouseCode;

    /** 目标仓库名称 */
    @Excel(name = "目标仓库名称")
    private String toWarehouseName;

    /** 目标货架ID */
    @Excel(name = "目标货架ID")
    private Long toShelfId;

    /** 目标货架编码 */
    @Excel(name = "目标货架编码")
    private String toShelfCode;

    /** 目标货架名称 */
    @Excel(name = "目标货架名称")
    private String toShelfName;

    /** 目标货位ID */
    @Excel(name = "目标货位ID")
    private Long toLocationId;

    /** 目标货位编码 */
    @Excel(name = "目标货位编码")
    private String toLocationCode;

    /** 目标货位名称 */
    @Excel(name = "目标货位名称")
    private String toLocationName;

    /** 计划调拨日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划调拨日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedDate;

    /** 实际调拨日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际调拨日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualDate;

    /** 总数量 */
    @Excel(name = "总数量")
    private BigDecimal totalQuantity;

    /** 状态（0草稿 1待审核 2已审核 3已调拨 4已取消） */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=待审核,2=已审核,3=已调拨,4=已取消")
    private String status;

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

    /** 操作人ID */
    @Excel(name = "操作人ID")
    private Long operatorId;

    /** 操作人姓名 */
    @Excel(name = "操作人姓名")
    private String operatorName;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date operateTime;

    /** 调拨单明细信息 */
    private List<ErpTransferOrderDetail> transferOrderDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setTransferNo(String transferNo) 
    {
        this.transferNo = transferNo;
    }

    public String getTransferNo() 
    {
        return transferNo;
    }

    public void setTransferName(String transferName) 
    {
        this.transferName = transferName;
    }

    public String getTransferName() 
    {
        return transferName;
    }

    public void setTransferType(String transferType) 
    {
        this.transferType = transferType;
    }

    public String getTransferType() 
    {
        return transferType;
    }

    public void setFromWarehouseId(Long fromWarehouseId) 
    {
        this.fromWarehouseId = fromWarehouseId;
    }

    public Long getFromWarehouseId() 
    {
        return fromWarehouseId;
    }

    public void setFromWarehouseCode(String fromWarehouseCode) 
    {
        this.fromWarehouseCode = fromWarehouseCode;
    }

    public String getFromWarehouseCode() 
    {
        return fromWarehouseCode;
    }

    public void setFromWarehouseName(String fromWarehouseName) 
    {
        this.fromWarehouseName = fromWarehouseName;
    }

    public String getFromWarehouseName() 
    {
        return fromWarehouseName;
    }

    public void setFromShelfId(Long fromShelfId) 
    {
        this.fromShelfId = fromShelfId;
    }

    public Long getFromShelfId() 
    {
        return fromShelfId;
    }

    public void setFromShelfCode(String fromShelfCode) 
    {
        this.fromShelfCode = fromShelfCode;
    }

    public String getFromShelfCode() 
    {
        return fromShelfCode;
    }

    public void setFromShelfName(String fromShelfName) 
    {
        this.fromShelfName = fromShelfName;
    }

    public String getFromShelfName() 
    {
        return fromShelfName;
    }

    public void setFromLocationId(Long fromLocationId) 
    {
        this.fromLocationId = fromLocationId;
    }

    public Long getFromLocationId() 
    {
        return fromLocationId;
    }

    public void setFromLocationCode(String fromLocationCode) 
    {
        this.fromLocationCode = fromLocationCode;
    }

    public String getFromLocationCode() 
    {
        return fromLocationCode;
    }

    public void setFromLocationName(String fromLocationName) 
    {
        this.fromLocationName = fromLocationName;
    }

    public String getFromLocationName() 
    {
        return fromLocationName;
    }

    public void setToWarehouseId(Long toWarehouseId) 
    {
        this.toWarehouseId = toWarehouseId;
    }

    public Long getToWarehouseId() 
    {
        return toWarehouseId;
    }

    public void setToWarehouseCode(String toWarehouseCode) 
    {
        this.toWarehouseCode = toWarehouseCode;
    }

    public String getToWarehouseCode() 
    {
        return toWarehouseCode;
    }

    public void setToWarehouseName(String toWarehouseName) 
    {
        this.toWarehouseName = toWarehouseName;
    }

    public String getToWarehouseName() 
    {
        return toWarehouseName;
    }

    public void setToShelfId(Long toShelfId) 
    {
        this.toShelfId = toShelfId;
    }

    public Long getToShelfId() 
    {
        return toShelfId;
    }

    public void setToShelfCode(String toShelfCode) 
    {
        this.toShelfCode = toShelfCode;
    }

    public String getToShelfCode() 
    {
        return toShelfCode;
    }

    public void setToShelfName(String toShelfName) 
    {
        this.toShelfName = toShelfName;
    }

    public String getToShelfName() 
    {
        return toShelfName;
    }

    public void setToLocationId(Long toLocationId) 
    {
        this.toLocationId = toLocationId;
    }

    public Long getToLocationId() 
    {
        return toLocationId;
    }

    public void setToLocationCode(String toLocationCode) 
    {
        this.toLocationCode = toLocationCode;
    }

    public String getToLocationCode() 
    {
        return toLocationCode;
    }

    public void setToLocationName(String toLocationName) 
    {
        this.toLocationName = toLocationName;
    }

    public String getToLocationName() 
    {
        return toLocationName;
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

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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

    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }

    public void setOperatorName(String operatorName) 
    {
        this.operatorName = operatorName;
    }

    public String getOperatorName() 
    {
        return operatorName;
    }

    public void setOperateTime(Date operateTime) 
    {
        this.operateTime = operateTime;
    }

    public Date getOperateTime() 
    {
        return operateTime;
    }

    public List<ErpTransferOrderDetail> getTransferOrderDetailList()
    {
        return transferOrderDetailList;
    }

    public void setTransferOrderDetailList(List<ErpTransferOrderDetail> transferOrderDetailList)
    {
        this.transferOrderDetailList = transferOrderDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("transferNo", getTransferNo())
            .append("transferName", getTransferName())
            .append("transferType", getTransferType())
            .append("fromWarehouseId", getFromWarehouseId())
            .append("fromWarehouseCode", getFromWarehouseCode())
            .append("fromWarehouseName", getFromWarehouseName())
            .append("fromShelfId", getFromShelfId())
            .append("fromShelfCode", getFromShelfCode())
            .append("fromShelfName", getFromShelfName())
            .append("fromLocationId", getFromLocationId())
            .append("fromLocationCode", getFromLocationCode())
            .append("fromLocationName", getFromLocationName())
            .append("toWarehouseId", getToWarehouseId())
            .append("toWarehouseCode", getToWarehouseCode())
            .append("toWarehouseName", getToWarehouseName())
            .append("toShelfId", getToShelfId())
            .append("toShelfCode", getToShelfCode())
            .append("toShelfName", getToShelfName())
            .append("toLocationId", getToLocationId())
            .append("toLocationCode", getToLocationCode())
            .append("toLocationName", getToLocationName())
            .append("plannedDate", getPlannedDate())
            .append("actualDate", getActualDate())
            .append("totalQuantity", getTotalQuantity())
            .append("status", getStatus())
            .append("approverId", getApproverId())
            .append("approverName", getApproverName())
            .append("approveTime", getApproveTime())
            .append("operatorId", getOperatorId())
            .append("operatorName", getOperatorName())
            .append("operateTime", getOperateTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
