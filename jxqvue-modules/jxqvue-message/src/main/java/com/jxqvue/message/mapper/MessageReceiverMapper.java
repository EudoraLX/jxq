package com.jxqvue.message.mapper;

import java.util.List;
import com.jxqvue.message.domain.MessageReceiver;

/**
 * 消息接收者Mapper接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface MessageReceiverMapper 
{
    /**
     * 查询消息接收者
     * 
     * @param id 消息接收者主键
     * @return 消息接收者
     */
    public MessageReceiver selectMessageReceiverById(Long id);

    /**
     * 查询消息接收者列表
     * 
     * @param messageReceiver 消息接收者
     * @return 消息接收者集合
     */
    public List<MessageReceiver> selectMessageReceiverList(MessageReceiver messageReceiver);

    /**
     * 根据消息ID查询接收者列表
     * 
     * @param messageId 消息ID
     * @return 消息接收者集合
     */
    public List<MessageReceiver> selectMessageReceiverByMessageId(Long messageId);

    /**
     * 新增消息接收者
     * 
     * @param messageReceiver 消息接收者
     * @return 结果
     */
    public int insertMessageReceiver(MessageReceiver messageReceiver);

    /**
     * 修改消息接收者
     * 
     * @param messageReceiver 消息接收者
     * @return 结果
     */
    public int updateMessageReceiver(MessageReceiver messageReceiver);

    /**
     * 删除消息接收者
     * 
     * @param id 消息接收者主键
     * @return 结果
     */
    public int deleteMessageReceiverById(Long id);

    /**
     * 根据消息ID删除接收者
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int deleteMessageReceiverByMessageId(Long messageId);

    /**
     * 批量删除消息接收者
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageReceiverByIds(Long[] ids);

    /**
     * 统计未读消息数
     * 
     * @return 未读消息数
     */
    public Long countUnreadMessages();

    /**
     * 统计用户消息数
     * 
     * @param userId 用户ID
     * @return 用户消息数
     */
    public Long countUserMessages(Long userId);

    /**
     * 统计用户未读消息数
     * 
     * @param userId 用户ID
     * @return 用户未读消息数
     */
    public Long countUserUnreadMessages(Long userId);

    /**
     * 统计用户已读消息数
     * 
     * @param userId 用户ID
     * @return 用户已读消息数
     */
    public Long countUserReadMessages(Long userId);

    /**
     * 根据用户ID和消息ID标记为已读
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 结果
     */
    public int markAsReadByUserIdAndMessageId(Long userId, Long messageId);

    /**
     * 根据用户ID和消息ID标记为未读
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 结果
     */
    public int markAsUnreadByUserIdAndMessageId(Long userId, Long messageId);

    /**
     * 根据用户ID和消息ID删除
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 结果
     */
    public int deleteByUserIdAndMessageId(Long userId, Long messageId);
}
