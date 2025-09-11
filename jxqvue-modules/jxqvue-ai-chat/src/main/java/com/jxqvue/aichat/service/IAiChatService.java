package com.jxqvue.aichat.service;

import java.util.List;
import com.jxqvue.aichat.domain.ChatSession;
import com.jxqvue.aichat.domain.ChatMessage;
import com.jxqvue.aichat.domain.ChatRequest;
import com.jxqvue.aichat.domain.ChatResponse;

/**
 * AI聊天服务接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IAiChatService 
{
    /**
     * 查询AI聊天会话
     * 
     * @param sessionId AI聊天会话主键
     * @return AI聊天会话
     */
    public ChatSession selectChatSessionBySessionId(Long sessionId);

    /**
     * 查询AI聊天会话列表
     * 
     * @param chatSession AI聊天会话
     * @return AI聊天会话集合
     */
    public List<ChatSession> selectChatSessionList(ChatSession chatSession);

    /**
     * 新增AI聊天会话
     * 
     * @param chatSession AI聊天会话
     * @return 结果
     */
    public int insertChatSession(ChatSession chatSession);

    /**
     * 修改AI聊天会话
     * 
     * @param chatSession AI聊天会话
     * @return 结果
     */
    public int updateChatSession(ChatSession chatSession);

    /**
     * 批量删除AI聊天会话
     * 
     * @param sessionIds 需要删除的AI聊天会话主键集合
     * @return 结果
     */
    public int deleteChatSessionBySessionIds(Long[] sessionIds);

    /**
     * 删除AI聊天会话信息
     * 
     * @param sessionId AI聊天会话主键
     * @return 结果
     */
    public int deleteChatSessionBySessionId(Long sessionId);

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
     * 批量删除AI聊天消息
     * 
     * @param messageIds 需要删除的AI聊天消息主键集合
     * @return 结果
     */
    public int deleteChatMessageByMessageIds(Long[] messageIds);

    /**
     * 删除AI聊天消息信息
     * 
     * @param messageId AI聊天消息主键
     * @return 结果
     */
    public int deleteChatMessageByMessageId(Long messageId);

    /**
     * 发送聊天消息
     * 
     * @param chatRequest 聊天请求
     * @return 聊天响应
     */
    public ChatResponse sendMessage(ChatRequest chatRequest);

    /**
     * 获取用户会话列表
     * 
     * @param userId 用户ID
     * @return 会话列表
     */
    public List<ChatSession> getUserSessions(Long userId);

    /**
     * 获取会话消息列表
     * 
     * @param sessionId 会话ID
     * @return 消息列表
     */
    public List<ChatMessage> getSessionMessages(Long sessionId);

    /**
     * 获取会话最新消息
     * 
     * @param sessionId 会话ID
     * @param limit 限制数量
     * @return 消息列表
     */
    public List<ChatMessage> getLatestMessages(Long sessionId, Integer limit);

    /**
     * 标记消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    public int markMessageAsRead(Long messageId);

    /**
     * 批量标记消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    public int markMessagesAsRead(Long[] messageIds);

    /**
     * 获取会话未读消息数量
     * 
     * @param sessionId 会话ID
     * @return 未读消息数量
     */
    public int getUnreadCount(Long sessionId);

    /**
     * 创建新会话
     * 
     * @param userId 用户ID
     * @param sessionName 会话名称
     * @return 会话ID
     */
    public Long createNewSession(Long userId, String sessionName);
}
