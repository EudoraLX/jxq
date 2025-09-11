package com.jxqvue.erp.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 编号规则对象 erp_number_rule
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public class ErpNumberRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规则ID */
    private Long ruleId;

    /** 规则编码 */
    @Excel(name = "规则编码")
    private String ruleCode;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 业务类型 */
    @Excel(name = "业务类型", readConverterExp = "erp_business_type")
    private String businessType;

    /** 前缀 */
    @Excel(name = "前缀")
    private String prefix;

    /** 日期格式 */
    @Excel(name = "日期格式")
    private String dateFormat;

    /** 序列号长度 */
    @Excel(name = "序列号长度")
    private Integer sequenceLength;

    /** 当前序列号 */
    @Excel(name = "当前序列号")
    private Long currentSequence;

    /** 重置类型 */
    @Excel(name = "重置类型", readConverterExp = "YEAR=按年重置,MONTH=按月重置,DAY=按日重置,NEVER=永不重置")
    private String resetType;

    /** 最后重置日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后重置日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastResetDate;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String needApproval;

    public void setRuleId(Long ruleId) 
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId() 
    {
        return ruleId;
    }
    public void setRuleCode(String ruleCode) 
    {
        this.ruleCode = ruleCode;
    }

    public String getRuleCode() 
    {
        return ruleCode;
    }
    public void setRuleName(String ruleName) 
    {
        this.ruleName = ruleName;
    }

    public String getRuleName() 
    {
        return ruleName;
    }
    public void setBusinessType(String businessType) 
    {
        this.businessType = businessType;
    }

    public String getBusinessType() 
    {
        return businessType;
    }
    public void setPrefix(String prefix) 
    {
        this.prefix = prefix;
    }

    public String getPrefix() 
    {
        return prefix;
    }
    public void setDateFormat(String dateFormat) 
    {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() 
    {
        return dateFormat;
    }
    public void setSequenceLength(Integer sequenceLength) 
    {
        this.sequenceLength = sequenceLength;
    }

    public Integer getSequenceLength() 
    {
        return sequenceLength;
    }
    public void setCurrentSequence(Long currentSequence) 
    {
        this.currentSequence = currentSequence;
    }

    public Long getCurrentSequence() 
    {
        return currentSequence;
    }
    public void setResetType(String resetType) 
    {
        this.resetType = resetType;
    }

    public String getResetType() 
    {
        return resetType;
    }
    public void setLastResetDate(Date lastResetDate) 
    {
        this.lastResetDate = lastResetDate;
    }

    public Date getLastResetDate() 
    {
        return lastResetDate;
    }
    
    public void setNeedApproval(String needApproval) 
    {
        this.needApproval = needApproval;
    }

    public String getNeedApproval() 
    {
        return needApproval;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleId", getRuleId())
            .append("ruleCode", getRuleCode())
            .append("ruleName", getRuleName())
            .append("businessType", getBusinessType())
            .append("prefix", getPrefix())
            .append("dateFormat", getDateFormat())
            .append("sequenceLength", getSequenceLength())
            .append("currentSequence", getCurrentSequence())
            .append("resetType", getResetType())
            .append("lastResetDate", getLastResetDate())
            .append("needApproval", getNeedApproval())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
