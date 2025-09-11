package com.jxqvue.erp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 工艺路线明细对象 erp_process_route_detail
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ErpProcessRouteDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long id;

    /** 工艺路线ID */
    @Excel(name = "工艺路线ID")
    private Long processRouteId;

    /** 工序 */
    @Excel(name = "工序")
    private Long processId;

    /** 工序编码 */
    @Excel(name = "工序编码")
    private String processCode;

    /** 工序名称 */
    @Excel(name = "工序名称")
    private String processName;

    /** 工序顺序 */
    @Excel(name = "工序顺序")
    private Long operationSequence;



    /** 人工工时(小时) */
    @Excel(name = "人工工时(小时)")
    private BigDecimal laborHours;

    /** 机器工时(小时) */
    @Excel(name = "机器工时(小时)")
    private BigDecimal machineHours;

    /** 是否关键工序（0否 1是） */
    @Excel(name = "是否关键工序", readConverterExp = "0=否,1=是")
    private String isCritical;

    /** 是否可选工序（0否 1是） */
    @Excel(name = "是否可选工序", readConverterExp = "0=否,1=是")
    private String isOptional;

    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcessRouteId(Long processRouteId) 
    {
        this.processRouteId = processRouteId;
    }

    public Long getProcessRouteId() 
    {
        return processRouteId;
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
    public void setProcessName(String processName) 
    {
        this.processName = processName;
    }

    public String getProcessName() 
    {
        return processName;
    }
    public void setOperationSequence(Long operationSequence) 
    {
        this.operationSequence = operationSequence;
    }

    public Long getOperationSequence() 
    {
        return operationSequence;
    }
    public void setLaborHours(BigDecimal laborHours) 
    {
        this.laborHours = laborHours;
    }

    public BigDecimal getLaborHours() 
    {
        return laborHours;
    }
    public void setMachineHours(BigDecimal machineHours) 
    {
        this.machineHours = machineHours;
    }

    public BigDecimal getMachineHours() 
    {
        return machineHours;
    }
    public void setIsCritical(String isCritical) 
    {
        this.isCritical = isCritical;
    }

    public String getIsCritical() 
    {
        return isCritical;
    }
    public void setIsOptional(String isOptional) 
    {
        this.isOptional = isOptional;
    }

    public String getIsOptional() 
    {
        return isOptional;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("processRouteId", getProcessRouteId())
            .append("processId", getProcessId())
            .append("processCode", getProcessCode())
            .append("processName", getProcessName())
            .append("operationSequence", getOperationSequence())
            .append("laborHours", getLaborHours())
            .append("machineHours", getMachineHours())
            .append("isCritical", getIsCritical())
            .append("isOptional", getIsOptional())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}