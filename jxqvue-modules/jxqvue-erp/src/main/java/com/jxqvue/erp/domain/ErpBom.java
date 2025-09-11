package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * BOM主表对象 erp_bom
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public class ErpBom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** BOM ID */
    private Long id;

    /** BOM编码 */
    @Excel(name = "BOM编码")
    private String bomCode;

    /** BOM名称 */
    @Excel(name = "BOM名称")
    private String bomName;

    /** 产品编号(关联erp_material_master表id字段) */
    @Excel(name = "产品编号")
    private Long productId;

    /** 产品编码（用于显示） */
    private String productCode;

    /** 产品名称（用于显示） */
    private String productName;

    /** 产品规格（用于显示） */
    private String productSpec;

    /** 单位(只读，显示unit_name) */
    @Excel(name = "单位")
    private Long unitId;

    /** 单位名称（用于显示） */
    private String unitName;

    /** 版本号(只读字段，修改BOM表，保存时提示是否更新版本) */
    @Excel(name = "版本号")
    private String version;

    /** 工艺路线(关联erp_process_route表id字段) */
    @Excel(name = "工艺路线")
    private Long processRouteId;

    /** 工艺路线编码（用于显示） */
    private String processRouteCode;

    /** 工艺路线名称（用于显示） */
    private String processRouteName;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 失效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "失效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 审批状态（数据字典类型为approval_status） */
    @Excel(name = "审批状态", readConverterExp = "0=待审批,1=已审批,2=已拒绝")
    private String approvalStatus;

    /** 是否默认（数据字典类型为business_yes_no，默认值为1） */
    @Excel(name = "是否默认", readConverterExp = "0=否,1=是")
    private String isDefault;

    /** 总成本 */
    @Excel(name = "总成本")
    private BigDecimal totalCost;

    /** 人工成本 */
    @Excel(name = "人工成本")
    private BigDecimal laborCost;

    /** 材料成本 */
    @Excel(name = "材料成本")
    private BigDecimal materialCost;

    /** 制造费用 */
    @Excel(name = "制造费用")
    private BigDecimal overheadCost;

    /** 是否启用（数据字典类型为business_active，默认值为1） */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String isActive;

    /** BOM明细信息 */
    private List<ErpBomDetail> erpBomDetailList;

    /** BOM工艺路线明细信息 */
    private List<ErpBomRouteDetail> erpBomRouteDetailList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBomCode(String bomCode) 
    {
        this.bomCode = bomCode;
    }

    public String getBomCode() 
    {
        return bomCode;
    }

    public void setBomName(String bomName) 
    {
        this.bomName = bomName;
    }

    public String getBomName() 
    {
        return bomName;
    }

    public void setProductId(Long productId) 
    {
        this.productId = productId;
    }

    public Long getProductId() 
    {
        return productId;
    }

    public void setProductCode(String productCode) 
    {
        this.productCode = productCode;
    }

    public String getProductCode() 
    {
        return productCode;
    }

    public void setProductName(String productName) 
    {
        this.productName = productName;
    }

    public String getProductName() 
    {
        return productName;
    }

    public void setProductSpec(String productSpec) 
    {
        this.productSpec = productSpec;
    }

    public String getProductSpec() 
    {
        return productSpec;
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

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    public void setProcessRouteId(Long processRouteId) 
    {
        this.processRouteId = processRouteId;
    }

    public Long getProcessRouteId() 
    {
        return processRouteId;
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

    public void setApprovalStatus(String approvalStatus) 
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() 
    {
        return approvalStatus;
    }

    public void setIsDefault(String isDefault) 
    {
        this.isDefault = isDefault;
    }

    public String getIsDefault() 
    {
        return isDefault;
    }

    public void setTotalCost(BigDecimal totalCost) 
    {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalCost() 
    {
        return totalCost;
    }

    public void setLaborCost(BigDecimal laborCost) 
    {
        this.laborCost = laborCost;
    }

    public BigDecimal getLaborCost() 
    {
        return laborCost;
    }

    public void setMaterialCost(BigDecimal materialCost) 
    {
        this.materialCost = materialCost;
    }

    public BigDecimal getMaterialCost() 
    {
        return materialCost;
    }

    public void setOverheadCost(BigDecimal overheadCost) 
    {
        this.overheadCost = overheadCost;
    }

    public BigDecimal getOverheadCost() 
    {
        return overheadCost;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    public List<ErpBomDetail> getErpBomDetailList()
    {
        return erpBomDetailList;
    }

    public void setErpBomDetailList(List<ErpBomDetail> erpBomDetailList)
    {
        this.erpBomDetailList = erpBomDetailList;
    }

    public List<ErpBomRouteDetail> getErpBomRouteDetailList()
    {
        return erpBomRouteDetailList;
    }

    public void setErpBomRouteDetailList(List<ErpBomRouteDetail> erpBomRouteDetailList)
    {
        this.erpBomRouteDetailList = erpBomRouteDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bomCode", getBomCode())
            .append("bomName", getBomName())
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("productSpec", getProductSpec())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("version", getVersion())
            .append("processRouteId", getProcessRouteId())
            .append("processRouteCode", getProcessRouteCode())
            .append("processRouteName", getProcessRouteName())
            .append("effectiveDate", getEffectiveDate())
            .append("expiryDate", getExpiryDate())
            .append("approvalStatus", getApprovalStatus())
            .append("isDefault", getIsDefault())
            .append("totalCost", getTotalCost())
            .append("laborCost", getLaborCost())
            .append("materialCost", getMaterialCost())
            .append("overheadCost", getOverheadCost())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("erpBomDetailList", getErpBomDetailList())
            .append("erpBomRouteDetailList", getErpBomRouteDetailList())
            .toString();
    }
}
