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
 * 工艺路线主表对象 erp_process_route
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpProcessRoute extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工艺路线ID */
    private Long id;

    /** 工艺路线编码 */
    @Excel(name = "工艺路线编码")
    private String processRouteCode;

    /** 工艺路线名称 */
    @Excel(name = "工艺路线名称")
    private String processRouteName;



    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 状态（字典类型：approval_status） */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=待审批,2=已审批,3=已拒绝,4=已作废")
    private String status;

    /** 默认版本（字典类型：business_yes_no） */
    @Excel(name = "默认版本", readConverterExp = "0=否,1=是")
    private String isDefault;

    /** 总周期时间(分钟) */
    @Excel(name = "总周期时间(分钟)")
    private BigDecimal totalCycleTime;

    /** 总准备时间(分钟) */
    @Excel(name = "总准备时间(分钟)")
    private BigDecimal totalSetupTime;

    /** 总加工时间(分钟) */
    @Excel(name = "总加工时间(分钟)")
    private BigDecimal totalProcessingTime;

    /** 总移动时间(分钟) */
    @Excel(name = "总移动时间(分钟)")
    private BigDecimal totalMoveTime;

    /** 总等待时间(分钟) */
    @Excel(name = "总等待时间(分钟)")
    private BigDecimal totalQueueTime;

    /** 是否启用（字典类型：business_active） */
    @Excel(name = "是否启用", readConverterExp = "0=停用,1=启用")
    private String isActive;

    /** 删除标志（0代表存在，1代表删除） */
    private String delFlag;

    /** 工艺路线明细信息 */
    private List<ErpProcessRouteDetail> erpProcessRouteDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessRouteCode(String processRouteCode) 
    {
        this.processRouteCode = processRouteCode;
    }

    public String getProcessRouteCode() 
    {
        return processRouteCode;
    }
    public void setProcessRouteName(String processRouteName) 
    {
        this.processRouteName = processRouteName;
    }

    public String getProcessRouteName() 
    {
        return processRouteName;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setEffectiveDate(Date effectiveDate) 
    {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() 
    {
        return effectiveDate;
    }
    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setIsDefault(String isDefault) 
    {
        this.isDefault = isDefault;
    }

    public String getIsDefault() 
    {
        return isDefault;
    }
    public void setTotalCycleTime(BigDecimal totalCycleTime) 
    {
        this.totalCycleTime = totalCycleTime;
    }

    public BigDecimal getTotalCycleTime() 
    {
        return totalCycleTime;
    }
    public void setTotalSetupTime(BigDecimal totalSetupTime) 
    {
        this.totalSetupTime = totalSetupTime;
    }

    public BigDecimal getTotalSetupTime() 
    {
        return totalSetupTime;
    }
    public void setTotalProcessingTime(BigDecimal totalProcessingTime) 
    {
        this.totalProcessingTime = totalProcessingTime;
    }

    public BigDecimal getTotalProcessingTime() 
    {
        return totalProcessingTime;
    }
    public void setTotalMoveTime(BigDecimal totalMoveTime) 
    {
        this.totalMoveTime = totalMoveTime;
    }

    public BigDecimal getTotalMoveTime() 
    {
        return totalMoveTime;
    }
    public void setTotalQueueTime(BigDecimal totalQueueTime) 
    {
        this.totalQueueTime = totalQueueTime;
    }

    public BigDecimal getTotalQueueTime() 
    {
        return totalQueueTime;
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

    public List<ErpProcessRouteDetail> getErpProcessRouteDetailList()
    {
        return erpProcessRouteDetailList;
    }

    public void setErpProcessRouteDetailList(List<ErpProcessRouteDetail> erpProcessRouteDetailList)
    {
        this.erpProcessRouteDetailList = erpProcessRouteDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processRouteCode", getProcessRouteCode())
            .append("processRouteName", getProcessRouteName())

            .append("version", getVersion())
            .append("effectiveDate", getEffectiveDate())
            .append("expiryDate", getExpiryDate())
            .append("status", getStatus())
            .append("isDefault", getIsDefault())
            .append("totalCycleTime", getTotalCycleTime())
            .append("totalSetupTime", getTotalSetupTime())
            .append("totalProcessingTime", getTotalProcessingTime())
            .append("totalMoveTime", getTotalMoveTime())
            .append("totalQueueTime", getTotalQueueTime())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("erpProcessRouteDetailList", getErpProcessRouteDetailList())
            .toString();
    }
}
