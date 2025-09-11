package com.jxqvue.aichat.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.jxqvue.aichat.domain.ChatMessage;

/**
 * AI聊天消息Mapper接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface ChatMessageMapper 
{
    /**
     * 查询AI聊天消息
     * 
     * @param messageId AI聊天消息主键
     * @return AI聊天消息
     */
    public ChatMessage selectChatMessageByMessageId(Long messageId);

    /**
     * 查询AI聊天消息列表
     * 
     * @param chatMessage AI聊天消息
     * @return AI聊天消息集合
     */
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage);

    /**
     * 新增AI聊天消息
     * 
     * @param chatMessage AI聊天消息
     * @return 结果
     */
    public int insertChatMessage(ChatMessage chatMessage);

    /**
     * 修改AI聊天消息
     * 
     * @param chatMessage AI聊天消息
     * @return 结果
     */
    public int updateChatMessage(ChatMessage chatMessage);

    /**
     * 删除AI聊天消息
     * 
     * @param messageId AI聊天消息主键
     * @return 结果
     */
    public int deleteChatMessageByMessageId(Long messageId);

    /**
     * 批量删除AI聊天消息
     * 
     * @param messageIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChatMessageByMessageIds(Long[] messageIds);

    /**
     * 查询会话的消息列表
     * 
     * @param sessionId 会话ID
     * @return 消息列表
     */
    public List<ChatMessage> selectChatMessageListBySessionId(Long sessionId);

    /**
     * 查询会话的最新消息
     * 
     * @param sessionId 会话ID
     * @param limit 限制数量
     * @return 消息列表
     */
    public List<ChatMessage> selectLatestChatMessages(@Param("sessionId") Long sessionId, @Param("limit") Integer limit);

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
     * 查询会话的未读消息数量
     * 
     * @param sessionId 会话ID
     * @return 未读消息数量
     */
    public int selectUnreadCountBySessionId(Long sessionId);
}
