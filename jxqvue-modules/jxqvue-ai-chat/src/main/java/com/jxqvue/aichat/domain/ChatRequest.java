package com.jxqvue.aichat.domain;

import java.util.List;

/**
 * AI聊天请求对象
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ChatRequest
{
    /** 会话ID */
    private Long sessionId;

    /** 用户消息 */
    private String message;

    /** 消息类型 */
    private String messageType;

    /** 历史消息列表 */
    private List<ChatMessage> historyMessages;

    /** 是否创建新会话 */
    private Boolean createNewSession;

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<ChatMessage> getHistoryMessages() {
        return historyMessages;
    }

    public void setHistoryMessages(List<ChatMessage> historyMessages) {
        this.historyMessages = historyMessages;
    }

    public Boolean getCreateNewSession() {
        return createNewSession;
    }

    public void setCreateNewSession(Boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
