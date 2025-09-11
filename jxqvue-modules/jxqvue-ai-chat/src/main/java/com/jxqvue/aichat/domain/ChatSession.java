package com.jxqvue.aichat.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * AI聊天会话对象 ai_chat_session
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ChatSession extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会话ID */
    private Long sessionId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 会话名称 */
    @Excel(name = "会话名称")
    private String sessionName;

    /** 最后消息时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后消息时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastMessageTime;

    /** 消息数量 */
    @Excel(name = "消息数量")
    private Integer messageCount;

    /** 会话状态（0正常 1删除） */
    @Excel(name = "会话状态", readConverterExp = "0=正常,1=删除")
    private String status;

    public void setSessionId(Long sessionId) 
    {
        this.sessionId = sessionId;
    }

    public Long getSessionId() 
    {
        return sessionId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setSessionName(String sessionName) 
    {
        this.sessionName = sessionName;
    }

    public String getSessionName() 
    {
        return sessionName;
    }
    public void setLastMessageTime(Date lastMessageTime) 
    {
        this.lastMessageTime = lastMessageTime;
    }

    public Date getLastMessageTime() 
    {
        return lastMessageTime;
    }
    public void setMessageCount(Integer messageCount) 
    {
        this.messageCount = messageCount;
    }

    public Integer getMessageCount() 
    {
        return messageCount;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("sessionId", getSessionId())
            .append("userId", getUserId())
            .append("sessionName", getSessionName())
            .append("lastMessageTime", getLastMessageTime())
            .append("messageCount", getMessageCount())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
