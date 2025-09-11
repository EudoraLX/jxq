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
 * 盘点单表对象 erp_inventory_count
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpInventoryCount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点单ID */
    private Long id;

    /** 盘点单号 */
    @Excel(name = "盘点单号")
    private String countNo;

    /** 盘点单名称 */
    @Excel(name = "盘点单名称")
    private String countName;

    /** 盘点类型（01全盘 02抽盘 03循环盘点） */
    @Excel(name = "盘点类型", readConverterExp = "01=全盘,02=抽盘,03=循环盘点")
    private String countType;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 盘点日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "盘点日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date countDate;

    /** 盘点时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "盘点时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date countTime;

    /** 审批状态（0=草稿,1=待审核,2=已审核,-1=驳回） */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=待审核,2=已审核,-1=驳回")
    private String approvalStatus;

    /** 盘点项目总数 */
    @Excel(name = "盘点项目总数")
    private Long totalItems;

    /** 已盘点项目数 */
    @Excel(name = "已盘点项目数")
    private Long countedItems;

    /** 差异项目数 */
    @Excel(name = "差异项目数")
    private Long differenceItems;

    /** 总差异金额 */
    @Excel(name = "总差异金额")
    private BigDecimal totalDifferenceAmount;

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

    /** 调整人ID */
    @Excel(name = "调整人ID")
    private Long adjusterId;

    /** 调整人姓名 */
    @Excel(name = "调整人姓名")
    private String adjusterName;

    /** 调整时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "调整时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date adjustTime;

    /** 盘点单明细信息 */
    private List<ErpInventoryCountDetail> inventoryCountDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setCountNo(String countNo) 
    {
        this.countNo = countNo;
    }

    public String getCountNo() 
    {
        return countNo;
    }

    public void setCountName(String countName) 
    {
        this.countName = countName;
    }

    public String getCountName() 
    {
        return countName;
    }

    public void setCountType(String countType) 
    {
        this.countType = countType;
    }

    public String getCountType() 
    {
        return countType;
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

    public void setCountDate(Date countDate) 
    {
        this.countDate = countDate;
    }

    public Date getCountDate() 
    {
        return countDate;
    }

    public void setCountTime(Date countTime) 
    {
        this.countTime = countTime;
    }

    public Date getCountTime() 
    {
        return countTime;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setTotalItems(Long totalItems)
    {
        this.totalItems = totalItems;
    }

    public Long getTotalItems() 
    {
        return totalItems;
    }

    public void setCountedItems(Long countedItems) 
    {
        this.countedItems = countedItems;
    }

    public Long getCountedItems() 
    {
        return countedItems;
    }

    public void setDifferenceItems(Long differenceItems) 
    {
        this.differenceItems = differenceItems;
    }

    public Long getDifferenceItems() 
    {
        return differenceItems;
    }

    public void setTotalDifferenceAmount(BigDecimal totalDifferenceAmount) 
    {
        this.totalDifferenceAmount = totalDifferenceAmount;
    }

    public BigDecimal getTotalDifferenceAmount() 
    {
        return totalDifferenceAmount;
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

    public void setAdjusterId(Long adjusterId) 
    {
        this.adjusterId = adjusterId;
    }

    public Long getAdjusterId() 
    {
        return adjusterId;
    }

    public void setAdjusterName(String adjusterName) 
    {
        this.adjusterName = adjusterName;
    }

    public String getAdjusterName() 
    {
        return adjusterName;
    }

    public void setAdjustTime(Date adjustTime) 
    {
        this.adjustTime = adjustTime;
    }

    public Date getAdjustTime() 
    {
        return adjustTime;
    }

    public List<ErpInventoryCountDetail> getInventoryCountDetailList()
    {
        return inventoryCountDetailList;
    }

    public void setInventoryCountDetailList(List<ErpInventoryCountDetail> inventoryCountDetailList)
    {
        this.inventoryCountDetailList = inventoryCountDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("countNo", getCountNo())
            .append("countName", getCountName())
            .append("countType", getCountType())
            .append("warehouseId", getWarehouseId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("countDate", getCountDate())
            .append("countTime", getCountTime())
            .append("approvalStatus", getApprovalStatus())
            .append("totalItems", getTotalItems())
            .append("countedItems", getCountedItems())
            .append("differenceItems", getDifferenceItems())
            .append("totalDifferenceAmount", getTotalDifferenceAmount())
            .append("approverId", getApproverId())
            .append("approverName", getApproverName())
            .append("approveTime", getApproveTime())
            .append("adjusterId", getAdjusterId())
            .append("adjusterName", getAdjusterName())
            .append("adjustTime", getAdjustTime())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
