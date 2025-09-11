package com.jxqvue.message.service;

import java.util.List;
import com.jxqvue.message.domain.Message;
import com.jxqvue.message.domain.MessageRequest;

/**
 * 消息Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IMessageService 
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
     * 批量删除消息
     * 
     * @param messageIds 需要删除的消息主键
     * @return 结果
     */
    public int deleteMessageByMessageIds(Long[] messageIds);

    /**
     * 删除消息信息
     * 
     * @param messageId 消息主键
     * @return 结果
     */
    public int deleteMessageByMessageId(Long messageId);

    /**
     * 查询用户未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    public int selectUnreadCount(Long userId);

    /**
     * 标记消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int markAsRead(Long messageId);

    /**
     * 批量标记消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    public int markAsReadBatch(Long[] messageIds);

    /**
     * 发送消息
     * 
     * @param message 消息
     * @return 结果
     */
    public int sendMessage(Message message);

    /**
     * 批量发送消息
     * 
     * @param message 消息模板
     * @param receiverIds 接收者ID列表
     * @return 结果
     */
    public int sendMessageBatch(Message message, List<Long> receiverIds);

    /**
     * 推送消息
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int pushMessage(Long messageId);

    /**
     * 获取消息详情（包含接收者）
     * 
     * @param messageId 消息ID
     * @return 消息详情
     */
    public MessageRequest getMessageDetailWithReceivers(Long messageId);

    /**
     * 查询用户消息列表
     * 
     * @param userId 用户ID
     * @return 消息集合
     */
    public List<Message> selectUserMessageList(Long userId);

    /**
     * 查询用户消息详情
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 消息详情
     */
    public Message selectUserMessageDetail(Long userId, Long messageId);

    /**
     * 新增消息（支持多接收者）
     * 
     * @param messageRequest 消息请求
     * @return 结果
     */
    public int insertMessageWithReceivers(MessageRequest messageRequest);

    /**
     * 修改消息（支持多接收者）
     * 
     * @param messageRequest 消息请求
     * @return 结果
     */
    public int updateMessageWithReceivers(MessageRequest messageRequest);

    // ==================== 我的消息相关方法 ====================

    /**
     * 查询我的消息列表
     * 
     * @param message 消息
     * @return 消息集合
     */
    public List<Message> selectMyMessageList(Message message);

    /**
     * 根据消息ID查询我的消息详情
     * 
     * @param messageId 消息ID
     * @return 消息详情
     */
    public Message selectMyMessageByMessageId(Long messageId);

    /**
     * 标记我的消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int markMyMessageAsRead(Long messageId);

    /**
     * 批量标记我的消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    public int markMyMessageAsReadBatch(Long[] messageIds);

    /**
     * 标记我的消息为未读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int markMyMessageAsUnread(Long messageId);

    /**
     * 删除我的消息
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int removeMyMessage(Long messageId);

    /**
     * 批量删除我的消息
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    public int removeMyMessageBatch(Long[] messageIds);

    /**
     * 获取我的消息统计
     * 
     * @return 统计结果
     */
    public java.util.Map<String, Object> getMyMessageStatistics();
}
