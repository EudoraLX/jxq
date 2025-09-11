package com.jxqvue.message.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.message.mapper.MessageMapper;
import com.jxqvue.message.mapper.MessageReceiverMapper;
import com.jxqvue.message.domain.MessageStatistics;
import com.jxqvue.message.service.IMessageStatisticsService;

/**
 * 消息统计Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class MessageStatisticsServiceImpl implements IMessageStatisticsService 
{
    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
    private MessageReceiverMapper messageReceiverMapper;

    /**
     * 获取消息统计概览
     * 
     * @return 统计概览数据
     */
    @Override
    public Map<String, Object> getMessageStatistics()
    {
        Map<String, Object> result = new HashMap<>();
        
        // 总消息数
        Long totalMessages = messageMapper.countTotalMessages();
        result.put("totalMessages", totalMessages);
        
        // 未读消息数
        Long unreadMessages = messageReceiverMapper.countUnreadMessages();
        result.put("unreadMessages", unreadMessages);
        
        // 今日消息数
        Long todayMessages = messageMapper.countTodayMessages();
        result.put("todayMessages", todayMessages);
        
        // 本周消息数
        Long weekMessages = messageMapper.countWeekMessages();
        result.put("weekMessages", weekMessages);
        
        return result;
    }

    /**
     * 获取消息统计列表
     * 
     * @param messageStatistics 消息统计
     * @return 消息统计集合
     */
    @Override
    public List<MessageStatistics> selectMessageStatisticsList(MessageStatistics messageStatistics)
    {
        return messageMapper.selectMessageStatisticsList(messageStatistics);
    }

    /**
     * 获取消息类型统计
     * 
     * @return 消息类型统计
     */
    @Override
    public List<Map<String, Object>> getMessageTypeStatistics()
    {
        return messageMapper.getMessageTypeStatistics();
    }

    /**
     * 获取消息级别统计
     * 
     * @return 消息级别统计
     */
    @Override
    public List<Map<String, Object>> getMessageLevelStatistics()
    {
        return messageMapper.getMessageLevelStatistics();
    }

    /**
     * 获取消息趋势统计
     * 
     * @return 消息趋势统计
     */
    @Override
    public List<Map<String, Object>> getMessageTrendStatistics()
    {
        return messageMapper.getMessageTrendStatistics();
    }

    /**
     * 获取用户消息统计
     * 
     * @param userId 用户ID
     * @return 用户消息统计
     */
    @Override
    public Map<String, Object> getUserMessageStatistics(Long userId)
    {
        Map<String, Object> result = new HashMap<>();
        
        // 用户总消息数
        Long userTotalMessages = messageReceiverMapper.countUserMessages(userId);
        result.put("totalMessages", userTotalMessages);
        
        // 用户未读消息数
        Long userUnreadMessages = messageReceiverMapper.countUserUnreadMessages(userId);
        result.put("unreadMessages", userUnreadMessages);
        
        // 用户已读消息数
        Long userReadMessages = messageReceiverMapper.countUserReadMessages(userId);
        result.put("readMessages", userReadMessages);
        
        return result;
    }
}
