package com.jxqvue.message.service;

import java.util.List;
import java.util.Map;
import com.jxqvue.message.domain.MessageStatistics;

/**
 * 消息统计Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IMessageStatisticsService 
{
    /**
     * 获取消息统计概览
     * 
     * @return 统计概览数据
     */
    public Map<String, Object> getMessageStatistics();

    /**
     * 获取消息统计列表
     * 
     * @param messageStatistics 消息统计
     * @return 消息统计集合
     */
    public List<MessageStatistics> selectMessageStatisticsList(MessageStatistics messageStatistics);

    /**
     * 获取消息类型统计
     * 
     * @return 消息类型统计
     */
    public List<Map<String, Object>> getMessageTypeStatistics();

    /**
     * 获取消息级别统计
     * 
     * @return 消息级别统计
     */
    public List<Map<String, Object>> getMessageLevelStatistics();

    /**
     * 获取消息趋势统计
     * 
     * @return 消息趋势统计
     */
    public List<Map<String, Object>> getMessageTrendStatistics();

    /**
     * 获取用户消息统计
     * 
     * @param userId 用户ID
     * @return 用户消息统计
     */
    public Map<String, Object> getUserMessageStatistics(Long userId);
}
