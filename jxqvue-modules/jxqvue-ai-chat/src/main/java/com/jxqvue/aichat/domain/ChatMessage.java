package com.jxqvue.aichat.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AI聊天消息对象 ai_chat_message
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ChatMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 会话ID */
    @Excel(name = "会话ID")
    private Long sessionId;

    /** 角色（user用户 assistant助手 system系统） */
    @Excel(name = "角色", readConverterExp = "user=用户,assistant=助手,system=系统")
    private String role;

    /** 消息内容 */
    @Excel(name = "消息内容")
    private String content;

    /** 消息状态（0正常 1删除） */
    @Excel(name = "消息状态", readConverterExp = "0=正常,1=删除")
    private String status;

    /** 消息类型（text文本 image图片 file文件） */
    @Excel(name = "消息类型", readConverterExp = "text=文本,image=图片,file=文件")
    private String messageType;

    /** 是否已读（0未读 1已读） */
    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private String isRead;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setSessionId(Long sessionId) 
    {
        this.sessionId = sessionId;
    }

    public Long getSessionId() 
    {
        return sessionId;
    }
    public void setRole(String role) 
    {
        this.role = role;
    }

    public String getRole() 
    {
        return role;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setIsRead(String isRead) 
    {
        this.isRead = isRead;
    }

    public String getIsRead() 
    {
        return isRead;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("sessionId", getSessionId())
            .append("role", getRole())
            .append("content", getContent())
            .append("status", getStatus())
            .append("messageType", getMessageType())
            .append("isRead", getIsRead())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
