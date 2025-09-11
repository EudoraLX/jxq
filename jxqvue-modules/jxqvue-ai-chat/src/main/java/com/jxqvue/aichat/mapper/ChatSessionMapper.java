package com.jxqvue.aichat.mapper;

import java.util.List;
import com.jxqvue.aichat.domain.ChatSession;

/**
 * AI聊天会话Mapper接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface ChatSessionMapper 
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
     * 删除AI聊天会话
     * 
     * @param sessionId AI聊天会话主键
     * @return 结果
     */
    public int deleteChatSessionBySessionId(Long sessionId);

    /**
     * 批量删除AI聊天会话
     * 
     * @param sessionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteChatSessionBySessionIds(Long[] sessionIds);

    /**
     * 查询用户的会话列表
     * 
     * @param userId 用户ID
     * @return 会话列表
     */
    public List<ChatSession> selectChatSessionListByUserId(Long userId);

    /**
     * 更新会话最后消息时间
     * 
     * @param sessionId 会话ID
     * @return 结果
     */
    public int updateLastMessageTime(Long sessionId);

    /**
     * 更新会话消息数量
     * 
     * @param sessionId 会话ID
     * @return 结果
     */
    public int updateMessageCount(Long sessionId);
}
