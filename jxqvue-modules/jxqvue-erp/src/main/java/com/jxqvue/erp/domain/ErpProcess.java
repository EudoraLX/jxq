package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工序对象 erp_process
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public class ErpProcess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 工序ID */
    private Long id;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工序类型 */
    @Excel(name = "工序类型")
    private String processType;

    /** 标准工时(小时) */
    @Excel(name = "标准工时(小时)")
    private BigDecimal standardTime;

    /** 准备时间(小时) */
    @Excel(name = "准备时间(小时)")
    private BigDecimal setupTime;

    /** 收尾时间(小时) */
    @Excel(name = "收尾时间(小时)")
    private BigDecimal teardownTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 删除标志（0代表存在，1代表删除） */
    private String delFlag;

    /** 工艺参数明细信息 */
    private List<ErpProcessPara> erpProcessParaList;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setProcessType(String processType) 
    {
        this.processType = processType;
    }

    public String getProcessType() 
    {
        return processType;
    }
    public void setStandardTime(BigDecimal standardTime) 
    {
        this.standardTime = standardTime;
    }

    public BigDecimal getStandardTime() 
    {
        return standardTime;
    }
    public void setSetupTime(BigDecimal setupTime) 
    {
        this.setupTime = setupTime;
    }

    public BigDecimal getSetupTime() 
    {
        return setupTime;
    }
    public void setTeardownTime(BigDecimal teardownTime) 
    {
        this.teardownTime = teardownTime;
    }

    public BigDecimal getTeardownTime() 
    {
        return teardownTime;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public List<ErpProcessPara> getErpProcessParaList()
    {
        return erpProcessParaList;
    }

    public void setErpProcessParaList(List<ErpProcessPara> erpProcessParaList)
    {
        this.erpProcessParaList = erpProcessParaList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("processType", getProcessType())
            .append("standardTime", getStandardTime())
            .append("setupTime", getSetupTime())
            .append("teardownTime", getTeardownTime())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
