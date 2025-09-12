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
import com.jxqvue.aichat.service.LocalAIDatabaseAnalyzer;
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

    @Autowired
    private LocalAIDatabaseAnalyzer localAIDatabaseAnalyzer;

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
            String userMessage = chatRequest.getMessage();
            
            System.out.println("=== AI聊天服务开始处理 ===");
            System.out.println("用户ID: " + userId);
            System.out.println("会话ID: " + sessionId);
            System.out.println("用户消息: " + userMessage);
            System.out.println("是否创建新会话: " + chatRequest.getCreateNewSession());
            
            // 如果创建新会话或会话不存在，创建新会话
            if (chatRequest.getCreateNewSession() || sessionId == null) {
                sessionId = createNewSession(userId, "AI客服对话");
                response.setIsNewSession(true);
                System.out.println("创建新会话，会话ID: " + sessionId);
            }
            
            // 检测是否是数据库查询问题，优先使用本地AI分析
            String aiResponse = null;
            if (localAIDatabaseAnalyzer.isDatabaseQuery(userMessage)) {
                try {
                    System.out.println("检测到数据库查询问题，使用本地AI分析");
                    aiResponse = localAIDatabaseAnalyzer.analyzeWithLocalAI(userMessage);
                    if (aiResponse != null && !aiResponse.trim().isEmpty()) {
                        System.out.println("本地AI数据库分析成功");
                    }
                } catch (Exception e) {
                    System.out.println("本地AI数据库分析失败: " + e.getMessage());
                }
            }
            
            // 保存用户消息
            ChatMessage userMsg = new ChatMessage();
            userMsg.setSessionId(sessionId);
            userMsg.setRole("user");
            userMsg.setContent(userMessage);
            userMsg.setMessageType(chatRequest.getMessageType() != null ? chatRequest.getMessageType() : "text");
            userMsg.setStatus("0");
            userMsg.setIsRead("1");
            userMsg.setCreateBy(SecurityUtils.getUsername());
            insertChatMessage(userMsg);
            System.out.println("用户消息已保存到数据库");
            
            // 如果本地AI分析失败，使用原有的OpenRouter API
            if (aiResponse == null || aiResponse.trim().isEmpty()) {
                // 测试API连接
                if (!openRouterClient.testConnection()) {
                    response.setSuccess(false);
                    response.setErrorMessage("AI服务连接失败，请检查网络连接和API配置");
                    return response;
                }
                
                // 获取历史消息（最近10条）
                List<ChatMessage> historyMessages = getLatestMessages(sessionId, 10);
                System.out.println("获取历史消息数量: " + historyMessages.size());
                
                // 反转消息顺序，确保按时间顺序发送给AI（从旧到新）
                List<ChatMessage> orderedMessages = new ArrayList<>();
                for (int i = historyMessages.size() - 1; i >= 0; i--) {
                    orderedMessages.add(historyMessages.get(i));
                }
                
                System.out.println("按时间顺序排列的消息:");
                for (int i = 0; i < orderedMessages.size(); i++) {
                    ChatMessage msg = orderedMessages.get(i);
                    System.out.println("消息" + i + ": " + msg.getRole() + " - " + msg.getContent());
                }
                
                // 调用OpenRouter API
                System.out.println("开始调用OpenRouter API...");
                aiResponse = openRouterClient.sendChatRequest(orderedMessages);
                System.out.println("OpenRouter API返回: " + aiResponse);
                
                // 检查AI回复是否为空
                if (aiResponse == null || aiResponse.trim().isEmpty()) {
                    System.out.println("AI回复为空，使用模拟回复");
                    aiResponse = generateMockResponse(userMessage);
                }
            }
            
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
            System.out.println("AI回复已保存到数据库，消息ID: " + messageId);
            
            // 更新会话最后消息时间和消息数量
            chatSessionMapper.updateLastMessageTime(sessionId);
            chatSessionMapper.updateMessageCount(sessionId);
            
            response.setSessionId(sessionId);
            response.setMessage(aiResponse);
            response.setMessageId(messageId);
            response.setSuccess(true);
            
            System.out.println("=== AI聊天服务处理完成 ===");
            System.out.println("最终返回消息: " + aiResponse);
            
        } catch (Exception e) {
            System.out.println("AI聊天服务异常: " + e.getMessage());
            e.printStackTrace();
            response.setSuccess(false);
            response.setErrorMessage("发送消息失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 生成模拟回复
     * 
     * @param userMessage 用户消息
     * @return 模拟回复
     */
    private String generateMockResponse(String userMessage) {
        String[] responses = {
            "您好！我是AI智能客服，很高兴为您服务。",
            "感谢您的咨询，我会尽力帮助您解决问题。",
            "这是一个很好的问题，让我为您详细解答。",
            "我理解您的需求，请稍等片刻，我来为您查找相关信息。",
            "根据您的问题，我建议您可以尝试以下解决方案。",
            "如果您还有其他问题，请随时告诉我。",
            "我会持续关注您的需求，为您提供更好的服务。"
        };
        
        // 根据用户消息内容选择更相关的回复
        if (userMessage.contains("ERP") || userMessage.contains("erp")) {
            return "关于ERP系统，我可以为您介绍生产订单管理、库存控制、销售管理等核心功能模块。您想了解哪个具体方面呢？";
        } else if (userMessage.contains("订单") || userMessage.contains("生产")) {
            return "生产订单管理是ERP系统的重要功能，包括订单创建、生产计划、进度跟踪等。您需要了解订单的哪个环节？";
        } else if (userMessage.contains("库存") || userMessage.contains("物料")) {
            return "库存管理模块可以帮助您实时掌握物料库存情况，包括入库、出库、盘点等功能。您想了解库存管理的哪个方面？";
        } else if (userMessage.contains("简述") || userMessage.contains("介绍")) {
            return "ERP（企业资源计划）系统是一个集成化的管理平台，涵盖生产、销售、采购、库存、财务等各个业务环节，帮助企业实现资源优化配置和业务流程标准化。";
        }
        
        // 随机选择一个通用回复
        int index = (int) (Math.random() * responses.length);
        return responses[index];
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
