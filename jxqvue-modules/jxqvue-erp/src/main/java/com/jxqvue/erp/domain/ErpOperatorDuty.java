package com.jxqvue.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 在岗离岗记录对象 erp_operator_duty
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public class ErpOperatorDuty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long id;

    /** 工序ID */
    @Excel(name = "工序ID")
    private Long processId;

    /** 工序编号 */
    @Excel(name = "工序编号")
    private String processCode;

    /** 操作员 */
    @Excel(name = "操作员")
    private Long operatorId;

    /** 记录类型 */
    @Excel(name = "记录类型", readConverterExp = "1=在岗,2=离岗")
    private String dutyType;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    // 关联字段（用于显示）
    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 操作员姓名 */
    @Excel(name = "操作员姓名")
    private String operatorName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessId(Long processId) 
    {
        this.processId = processId;
    }

    public Long getProcessId() 
    {
        return processId;
    }
    public void setProcessCode(String processCode) 
    {
        this.processCode = processCode;
    }

    public String getProcessCode() 
    {
        return processCode;
    }
    public void setOperatorId(Long operatorId) 
    {
        this.operatorId = operatorId;
    }

    public Long getOperatorId() 
    {
        return operatorId;
    }
    public void setDutyType(String dutyType) 
    {
        this.dutyType = dutyType;
    }

    public String getDutyType() 
    {
        return dutyType;
    }
    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("operatorId", getOperatorId())
            .append("dutyType", getDutyType())
            .append("registerTime", getRegisterTime())
            .append("processName", getProcessName())
            .append("operatorName", getOperatorName())
            .toString();
    }
}
