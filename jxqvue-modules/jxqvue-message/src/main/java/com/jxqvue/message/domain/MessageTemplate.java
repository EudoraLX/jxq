package com.jxqvue.message.domain;

import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

/**
 * 消息模板对象 message_template
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class MessageTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 模板ID */
    private Long templateId;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 模板编码 */
    @Excel(name = "模板编码")
    private String templateCode;

    /** 模板类型 */
    @Excel(name = "模板类型")
    private String templateType;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long categoryId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 标题模板 */
    @Excel(name = "标题模板")
    private String titleTemplate;

    /** 内容模板 */
    @Excel(name = "内容模板")
    private String contentTemplate;

    /** 消息类型 */
    @Excel(name = "消息类型", readConverterExp = "1=系统消息,2=业务消息,3=通知消息")
    private String messageType;

    /** 消息级别 */
    @Excel(name = "消息级别", readConverterExp = "1=普通,2=重要,3=紧急")
    private String messageLevel;

    /** 变量定义 */
    private String variables;

    /** 状态 */
    @Excel(name = "状态", readConverterExp = "0=启用,1=禁用")
    private String status;

    /** 变量列表 */
    private List<MessageTemplateVariable> variableList;

    /** 变量值映射 */
    private Map<String, Object> variableValues;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageLevel() {
        return messageLevel;
    }

    public void setMessageLevel(String messageLevel) {
        this.messageLevel = messageLevel;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MessageTemplateVariable> getVariableList() {
        return variableList;
    }

    public void setVariableList(List<MessageTemplateVariable> variableList) {
        this.variableList = variableList;
    }

    public Map<String, Object> getVariableValues() {
        return variableValues;
    }

    public void setVariableValues(Map<String, Object> variableValues) {
        this.variableValues = variableValues;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("templateCode", getTemplateCode())
            .append("templateType", getTemplateType())
            .append("categoryId", getCategoryId())
            .append("titleTemplate", getTitleTemplate())
            .append("contentTemplate", getContentTemplate())
            .append("messageType", getMessageType())
            .append("messageLevel", getMessageLevel())
            .append("variables", getVariables())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
