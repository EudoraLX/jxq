package com.jxqvue.message.mapper;

import java.util.List;
import java.util.Map;
import com.jxqvue.message.domain.Message;
import com.jxqvue.message.domain.MessageStatistics;

/**
 * 消息Mapper接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface MessageMapper 
{
    /**
     * 查询消息
     * 
     * @param messageId 消息主键
     * @return 消息
     */
    public Message selectMessageByMessageId(Long messageId);

    /**
     * 查询消息列表
     * 
     * @param message 消息
     * @return 消息集合
     */
    public List<Message> selectMessageList(Message message);

    /**
     * 新增消息
     * 
     * @param message 消息
     * @return 结果
     */
    public int insertMessage(Message message);

    /**
     * 修改消息
     * 
     * @param message 消息
     * @return 结果
     */
    public int updateMessage(Message message);

    /**
     * 删除消息
     * 
     * @param messageId 消息主键
     * @return 结果
     */
    public int deleteMessageByMessageId(Long messageId);

    /**
     * 批量删除消息
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageByMessageIds(Long[] messageIds);

    /**
     * 查询用户未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    public int selectUnreadCount(Long userId);

    /**
     * 查询用户消息列表
     * 
     * @param userId 用户ID
     * @return 消息集合
     */
    public List<Message> selectUserMessageListByUserId(Long userId);

    /**
     * 查询用户消息详情
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 消息详情
     */
    public Message selectUserMessageDetailByUserIdAndMessageId(Long userId, Long messageId);

    /**
     * 统计总消息数
     * 
     * @return 总消息数
     */
    public Long countTotalMessages();

    /**
     * 统计今日消息数
     * 
     * @return 今日消息数
     */
    public Long countTodayMessages();

    /**
     * 统计本周消息数
     * 
     * @return 本周消息数
     */
    public Long countWeekMessages();

    /**
     * 查询消息统计列表
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
     * 查询我的消息列表
     * 
     * @param userId 用户ID
     * @param message 消息
     * @return 消息集合
     */
    public List<Message> selectMyMessageListByUserId(Long userId, Message message);
}
