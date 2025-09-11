package com.jxqvue.erp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import java.util.List;

/**
 * 仓库对象 erp_warehouse
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public class ErpWarehouse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 仓库编码 */
    @Excel(name = "仓库编码")
    private String warehouseCode;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String warehouseName;

    /** 仓库类型 */
    @Excel(name = "仓库类型", readConverterExp = "1=原材料仓,2=半成品仓,3=成品仓,4=包装物仓,5=辅料仓")
    private String warehouseType;

    /** 父级仓库ID */
    @Excel(name = "父级仓库ID")
    private Long parentId;

    /** 仓库管理员 */
    @Excel(name = "仓库管理员")
    private String manager;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 仓库地址 */
    @Excel(name = "仓库地址")
    private String address;

    /** 是否启用 */
    @Excel(name = "是否启用", readConverterExp = "0=禁用,1=启用")
    private String isActive;

    /** 删除标志（0代表存在 1代表删除） */
    private String delFlag;

    /** 货架信息 */
    private List<ErpShelf> shelfList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    public void setWarehouseType(String warehouseType) 
    {
        this.warehouseType = warehouseType;
    }

    public String getWarehouseType() 
    {
        return warehouseType;
    }

    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    public void setManager(String manager) 
    {
        this.manager = manager;
    }

    public String getManager() 
    {
        return manager;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
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

    public List<ErpShelf> getShelfList()
    {
        return shelfList;
    }

    public void setShelfList(List<ErpShelf> shelfList)
    {
        this.shelfList = shelfList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warehouseCode", getWarehouseCode())
            .append("warehouseName", getWarehouseName())
            .append("warehouseType", getWarehouseType())
            .append("parentId", getParentId())
            .append("manager", getManager())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("isActive", getIsActive())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .append("shelfList", getShelfList())
            .toString();
    }
}
