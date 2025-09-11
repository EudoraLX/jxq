package com.jxqvue.message.domain;

import com.jxqvue.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 消息模板变量对象 message_template_variable
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class MessageTemplateVariable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 变量ID */
    private Long variableId;

    /** 模板ID */
    private Long templateId;

    /** 变量名称 */
    private String variableName;

    /** 变量编码 */
    private String variableCode;

    /** 变量类型 */
    private String variableType;

    /** 变量描述 */
    private String variableDesc;

    /** 是否必填 */
    private String required;

    /** 默认值 */
    private String defaultValue;

    public void setVariableId(Long variableId) 
    {
        this.variableId = variableId;
    }

    public Long getVariableId() 
    {
        return variableId;
    }

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }

    public void setVariableName(String variableName) 
    {
        this.variableName = variableName;
    }

    public String getVariableName() 
    {
        return variableName;
    }

    public void setVariableCode(String variableCode) 
    {
        this.variableCode = variableCode;
    }

    public String getVariableCode() 
    {
        return variableCode;
    }

    public void setVariableType(String variableType) 
    {
        this.variableType = variableType;
    }

    public String getVariableType() 
    {
        return variableType;
    }

    public void setVariableDesc(String variableDesc) 
    {
        this.variableDesc = variableDesc;
    }

    public String getVariableDesc() 
    {
        return variableDesc;
    }

    public void setRequired(String required) 
    {
        this.required = required;
    }

    public String getRequired() 
    {
        return required;
    }

    public void setDefaultValue(String defaultValue) 
    {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() 
    {
        return defaultValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("variableId", getVariableId())
            .append("templateId", getTemplateId())
            .append("variableName", getVariableName())
            .append("variableCode", getVariableCode())
            .append("variableType", getVariableType())
            .append("variableDesc", getVariableDesc())
            .append("required", getRequired())
            .append("defaultValue", getDefaultValue())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
