package com.jxqvue.aichat.domain;

/**
 * AI聊天响应对象
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public class ChatResponse
{
    /** 会话ID */
    private Long sessionId;

    /** AI回复消息 */
    private String message;

    /** 消息ID */
    private Long messageId;

    /** 是否成功 */
    private Boolean success;

    /** 错误信息 */
    private String errorMessage;

    /** 是否为新会话 */
    private Boolean isNewSession;

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

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Boolean getIsNewSession() {
        return isNewSession;
    }

    public void setIsNewSession(Boolean isNewSession) {
        this.isNewSession = isNewSession;
    }
}
