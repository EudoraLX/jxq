package com.jxqvue.aichat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.aichat.mapper.ChatSessionMapper;
import com.jxqvue.aichat.mapper.ChatMessageMapper;
import com.jxqvue.aichat.domain.ChatSession;
import com.jxqvue.aichat.domain.ChatMessage;
import com.jxqvue.aichat.domain.ChatRequest;
import com.jxqvue.aichat.domain.ChatResponse;
import com.jxqvue.aichat.service.IAiChatService;
import com.jxqvue.aichat.utils.OpenRouterClient;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.SecurityUtils;

/**
 * AI聊天服务业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class AiChatServiceImpl implements IAiChatService 
{
    @Autowired
    private ChatSessionMapper chatSessionMapper;

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Autowired
    private OpenRouterClient openRouterClient;

    /**
     * 查询AI聊天会话
     * 
     * @param sessionId AI聊天会话主键
     * @return AI聊天会话
     */
    @Override
    public ChatSession selectChatSessionBySessionId(Long sessionId)
    {
        return chatSessionMapper.selectChatSessionBySessionId(sessionId);
    }

    /**
     * 查询AI聊天会话列表
     * 
     * @param chatSession AI聊天会话
     * @return AI聊天会话
     */
    @Override
    public List<ChatSession> selectChatSessionList(ChatSession chatSession)
    {
        return chatSessionMapper.selectChatSessionList(chatSession);
    }

    /**
     * 新增AI聊天会话
     * 
     * @param chatSession AI聊天会话
     * @return 结果
     */
    @Override
    public int insertChatSession(ChatSession chatSession)
    {
        chatSession.setCreateTime(DateUtils.getNowDate());
        return chatSessionMapper.insertChatSession(chatSession);
    }

    /**
     * 修改AI聊天会话
     * 
     * @param chatSession AI聊天会话
     * @return 结果
     */
    @Override
    public int updateChatSession(ChatSession chatSession)
    {
        chatSession.setUpdateTime(DateUtils.getNowDate());
        return chatSessionMapper.updateChatSession(chatSession);
    }

    /**
     * 批量删除AI聊天会话
     * 
     * @param sessionIds 需要删除的AI聊天会话主键
     * @return 结果
     */
    @Override
    public int deleteChatSessionBySessionIds(Long[] sessionIds)
    {
        return chatSessionMapper.deleteChatSessionBySessionIds(sessionIds);
    }

    /**
     * 删除AI聊天会话信息
     * 
     * @param sessionId AI聊天会话主键
     * @return 结果
     */
    @Override
    public int deleteChatSessionBySessionId(Long sessionId)
    {
        return chatSessionMapper.deleteChatSessionBySessionId(sessionId);
    }

    /**
     * 查询AI聊天消息
     * 
     * @param messageId AI聊天消息主键
     * @return AI聊天消息
     */
    @Override
    public ChatMessage selectChatMessageByMessageId(Long messageId)
    {
        return chatMessageMapper.selectChatMessageByMessageId(messageId);
    }

    /**
     * 查询AI聊天消息列表
     * 
     * @param chatMessage AI聊天消息
     * @return AI聊天消息
     */
    @Override
    public List<ChatMessage> selectChatMessageList(ChatMessage chatMessage)
    {
        return chatMessageMapper.selectChatMessageList(chatMessage);
    }

    /**
     * 新增AI聊天消息
     * 
     * @param chatMessage AI聊天消息
     * @return 结果
     */
    @Override
    public int insertChatMessage(ChatMessage chatMessage)
    {
        chatMessage.setCreateTime(DateUtils.getNowDate());
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    /**
     * 修改AI聊天消息
     * 
     * @param chatMessage AI聊天消息
     * @return 结果
     */
    @Override
    public int updateChatMessage(ChatMessage chatMessage)
    {
        chatMessage.setUpdateTime(DateUtils.getNowDate());
        return chatMessageMapper.updateChatMessage(chatMessage);
    }

    /**
     * 批量删除AI聊天消息
     * 
     * @param messageIds 需要删除的AI聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByMessageIds(Long[] messageIds)
    {
        return chatMessageMapper.deleteChatMessageByMessageIds(messageIds);
    }

    /**
     * 删除AI聊天消息信息
     * 
     * @param messageId AI聊天消息主键
     * @return 结果
     */
    @Override
    public int deleteChatMessageByMessageId(Long messageId)
    {
        return chatMessageMapper.deleteChatMessageByMessageId(messageId);
    }

    /**
     * 发送聊天消息
     * 
     * @param chatRequest 聊天请求
     * @return 聊天响应
     */
    @Override
    public ChatResponse sendMessage(ChatRequest chatRequest)
    {
        ChatResponse response = new ChatResponse();
        
        try {
            Long sessionId = chatRequest.getSessionId();
            Long userId = SecurityUtils.getUserId();
            
            // 如果创建新会话或会话不存在，创建新会话
            if (chatRequest.getCreateNewSession() || sessionId == null) {
                sessionId = createNewSession(userId, "AI客服对话");
                response.setIsNewSession(true);
            }
            
            // 测试API连接
            if (!openRouterClient.testConnection()) {
                response.setSuccess(false);
                response.setErrorMessage("AI服务连接失败，请检查网络连接和API配置");
                return response;
            }
            
            // 保存用户消息
            ChatMessage userMessage = new ChatMessage();
            userMessage.setSessionId(sessionId);
            userMessage.setRole("user");
            userMessage.setContent(chatRequest.getMessage());
            userMessage.setMessageType(chatRequest.getMessageType() != null ? chatRequest.getMessageType() : "text");
            userMessage.setStatus("0");
            userMessage.setIsRead("1");
            userMessage.setCreateBy(SecurityUtils.getUsername());
            insertChatMessage(userMessage);
            
            // 获取历史消息（最近10条）
            List<ChatMessage> historyMessages = getLatestMessages(sessionId, 10);
            
            // 调用OpenRouter API
            String aiResponse = openRouterClient.sendChatRequest(historyMessages);
            
            // 保存AI回复
            ChatMessage aiMessage = new ChatMessage();
            aiMessage.setSessionId(sessionId);
            aiMessage.setRole("assistant");
            aiMessage.setContent(aiResponse);
            aiMessage.setMessageType("text");
            aiMessage.setStatus("0");
            aiMessage.setIsRead("0");
            aiMessage.setCreateBy("system");
            Long messageId = (long) insertChatMessage(aiMessage);
            
            // 更新会话最后消息时间和消息数量
            chatSessionMapper.updateLastMessageTime(sessionId);
            chatSessionMapper.updateMessageCount(sessionId);
            
            response.setSessionId(sessionId);
            response.setMessage(aiResponse);
            response.setMessageId(messageId);
            response.setSuccess(true);
            
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMessage("发送消息失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 获取用户会话列表
     * 
     * @param userId 用户ID
     * @return 会话列表
     */
    @Override
    public List<ChatSession> getUserSessions(Long userId)
    {
        return chatSessionMapper.selectChatSessionListByUserId(userId);
    }

    /**
     * 获取会话消息列表
     * 
     * @param sessionId 会话ID
     * @return 消息列表
     */
    @Override
    public List<ChatMessage> getSessionMessages(Long sessionId)
    {
        return chatMessageMapper.selectChatMessageListBySessionId(sessionId);
    }

    /**
     * 获取会话最新消息
     * 
     * @param sessionId 会话ID
     * @param limit 限制数量
     * @return 消息列表
     */
    @Override
    public List<ChatMessage> getLatestMessages(Long sessionId, Integer limit)
    {
        return chatMessageMapper.selectLatestChatMessages(sessionId, limit);
    }

    /**
     * 标记消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int markMessageAsRead(Long messageId)
    {
        return chatMessageMapper.markAsRead(messageId);
    }

    /**
     * 批量标记消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    @Override
    public int markMessagesAsRead(Long[] messageIds)
    {
        return chatMessageMapper.markAsReadBatch(messageIds);
    }

    /**
     * 获取会话未读消息数量
     * 
     * @param sessionId 会话ID
     * @return 未读消息数量
     */
    @Override
    public int getUnreadCount(Long sessionId)
    {
        return chatMessageMapper.selectUnreadCountBySessionId(sessionId);
    }

    /**
     * 创建新会话
     * 
     * @param userId 用户ID
     * @param sessionName 会话名称
     * @return 会话ID
     */
    @Override
    public Long createNewSession(Long userId, String sessionName)
    {
        ChatSession session = new ChatSession();
        session.setUserId(userId);
        session.setSessionName(sessionName);
        session.setLastMessageTime(DateUtils.getNowDate());
        session.setMessageCount(0);
        session.setStatus("0");
        session.setCreateBy(SecurityUtils.getUsername());
        
        insertChatSession(session);
        return session.getSessionId();
    }
}
