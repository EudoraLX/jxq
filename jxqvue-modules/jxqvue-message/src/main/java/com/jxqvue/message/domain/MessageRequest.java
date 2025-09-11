package com.jxqvue.message.domain;

import com.jxqvue.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 消息请求对象 message_request
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class MessageRequest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 消息ID */
    private Long messageId;

    /** 消息标题 */
    private String title;

    /** 消息内容 */
    private String content;

    /** 消息类型（1：系统消息 2：业务消息 3：通知消息） */
    private String messageType;

    /** 消息级别（1：普通 2：重要 3：紧急） */
    private String messageLevel;

    /** 发送者ID */
    private Long senderId;

    /** 发送者名称 */
    private String senderName;

    /** 消息状态（0：未读 1：已读 2：已删除） */
    private String readStatus;

    /** 业务类型 */
    private String businessType;

    /** 业务ID */
    private Object businessId;

    /** 推送状态（0：未推送 1：已推送） */
    private String pushStatus;

    /** 推送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pushTime;

    /** 接收者列表 */
    private List<MessageReceiver> receivers;

    public void setMessageId(Long messageId) 
    {
        this.messageId = messageId;
    }

    public Long getMessageId() 
    {
        return messageId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setMessageLevel(String messageLevel) 
    {
        this.messageLevel = messageLevel;
    }

    public String getMessageLevel() 
    {
        return messageLevel;
    }
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setSenderName(String senderName) 
    {
        this.senderName = senderName;
    }

    public String getSenderName() 
    {
        return senderName;
    }
    public void setReadStatus(String readStatus) 
    {
        this.readStatus = readStatus;
    }

    public String getReadStatus() 
    {
        return readStatus;
    }
    public void setBusinessType(String businessType) 
    {
        this.businessType = businessType;
    }

    public String getBusinessType() 
    {
        return businessType;
    }
    public void setBusinessId(Object businessId) 
    {
        this.businessId = businessId;
    }

    public Object getBusinessId() 
    {
        return businessId;
    }
    public void setPushStatus(String pushStatus) 
    {
        this.pushStatus = pushStatus;
    }

    public String getPushStatus() 
    {
        return pushStatus;
    }
    public void setPushTime(Date pushTime) 
    {
        this.pushTime = pushTime;
    }

    public Date getPushTime() 
    {
        return pushTime;
    }
    public void setReceivers(List<MessageReceiver> receivers) 
    {
        this.receivers = receivers;
    }

    public List<MessageReceiver> getReceivers() 
    {
        return receivers;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("messageId", getMessageId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("messageType", getMessageType())
            .append("messageLevel", getMessageLevel())
            .append("senderId", getSenderId())
            .append("senderName", getSenderName())
            .append("readStatus", getReadStatus())
            .append("businessType", getBusinessType())
            .append("businessId", getBusinessId())
            .append("pushStatus", getPushStatus())
            .append("pushTime", getPushTime())
            .append("receivers", getReceivers())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
