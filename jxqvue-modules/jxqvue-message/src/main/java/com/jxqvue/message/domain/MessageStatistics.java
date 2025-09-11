package com.jxqvue.message.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jxqvue.common.annotation.Excel;
import com.jxqvue.common.core.domain.BaseEntity;

/**
 * 消息统计对象 message_statistics
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class MessageStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 统计ID */
    private Long id;

    /** 统计日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "统计日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date statisticsDate;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String messageType;

    /** 消息级别 */
    @Excel(name = "消息级别")
    private String messageLevel;

    /** 消息数量 */
    @Excel(name = "消息数量")
    private Long messageCount;

    /** 未读数量 */
    @Excel(name = "未读数量")
    private Long unreadCount;

    /** 已读数量 */
    @Excel(name = "已读数量")
    private Long readCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStatisticsDate(Date statisticsDate) 
    {
        this.statisticsDate = statisticsDate;
    }

    public Date getStatisticsDate() 
    {
        return statisticsDate;
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
    public void setMessageCount(Long messageCount) 
    {
        this.messageCount = messageCount;
    }

    public Long getMessageCount() 
    {
        return messageCount;
    }
    public void setUnreadCount(Long unreadCount) 
    {
        this.unreadCount = unreadCount;
    }

    public Long getUnreadCount() 
    {
        return unreadCount;
    }
    public void setReadCount(Long readCount) 
    {
        this.readCount = readCount;
    }

    public Long getReadCount() 
    {
        return readCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("statisticsDate", getStatisticsDate())
            .append("messageType", getMessageType())
            .append("messageLevel", getMessageLevel())
            .append("messageCount", getMessageCount())
            .append("unreadCount", getUnreadCount())
            .append("readCount", getReadCount())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}