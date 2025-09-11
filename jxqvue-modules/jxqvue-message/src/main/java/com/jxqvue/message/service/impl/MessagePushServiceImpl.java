package com.jxqvue.message.service.impl;

import com.jxqvue.message.domain.Message;
import com.jxqvue.message.service.MessagePushService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 消息推送服务实现类
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class MessagePushServiceImpl implements MessagePushService 
{
    private static final Logger log = LoggerFactory.getLogger(MessagePushServiceImpl.class);
    
    // 存储用户WebSocket连接
    private static final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    /**
     * 添加用户WebSocket连接
     * 
     * @param userId 用户ID
     * @param session WebSocket会话
     */
    public void addUserSession(Long userId, WebSocketSession session) {
        userSessions.put(userId, session);
        log.info("用户 {} WebSocket连接已建立", userId);
    }

    /**
     * 移除用户WebSocket连接
     * 
     * @param userId 用户ID
     */
    public void removeUserSession(Long userId) {
        userSessions.remove(userId);
        log.info("用户 {} WebSocket连接已断开", userId);
    }

    /**
     * 推送消息给指定用户
     * 
     * @param userId 用户ID
     * @param message 消息
     */
    @Override
    public void pushMessageToUser(Long userId, Message message) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String messageJson = buildMessageJson(message);
                session.sendMessage(new TextMessage(messageJson));
                log.info("消息推送成功，用户ID: {}, 消息ID: {}, 标题: {}", userId, message.getMessageId(), message.getTitle());
            } catch (IOException e) {
                log.error("消息推送失败，用户ID: {}, 消息ID: {}, 错误: {}", userId, message.getMessageId(), e.getMessage());
                // 推送失败时移除连接
                removeUserSession(userId);
            }
        } else {
            log.warn("用户 {} 的WebSocket连接不存在或已关闭", userId);
        }
    }

    /**
     * 推送未读消息数量更新
     * 
     * @param userId 用户ID
     * @param unreadCount 未读消息数量
     */
    @Override
    public void pushUnreadCountUpdate(Long userId, int unreadCount) {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                String countJson = buildUnreadCountJson(unreadCount);
                session.sendMessage(new TextMessage(countJson));
                log.info("未读消息数量推送成功，用户ID: {}, 数量: {}", userId, unreadCount);
            } catch (IOException e) {
                log.error("未读消息数量推送失败，用户ID: {}, 错误: {}", userId, e.getMessage());
                // 推送失败时移除连接
                removeUserSession(userId);
            }
        } else {
            log.warn("用户 {} 的WebSocket连接不存在或已关闭", userId);
        }
    }

    /**
     * 推送系统通知
     * 
     * @param message 消息
     */
    @Override
    public void pushSystemNotification(Message message) {
        // 推送给所有在线用户
        for (Map.Entry<Long, WebSocketSession> entry : userSessions.entrySet()) {
            Long userId = entry.getKey();
            WebSocketSession session = entry.getValue();
            
            if (session != null && session.isOpen()) {
                try {
                    String messageJson = buildMessageJson(message);
                    session.sendMessage(new TextMessage(messageJson));
                    log.info("系统通知推送成功，用户ID: {}, 消息ID: {}, 标题: {}", userId, message.getMessageId(), message.getTitle());
                } catch (IOException e) {
                    log.error("系统通知推送失败，用户ID: {}, 消息ID: {}, 错误: {}", userId, message.getMessageId(), e.getMessage());
                    // 推送失败时移除连接
                    removeUserSession(userId);
                }
            }
        }
    }

    /**
     * 构建消息JSON
     * 
     * @param message 消息
     * @return JSON字符串
     */
    private String buildMessageJson(Message message) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"type\":\"message\",");
        json.append("\"messageId\":").append(message.getMessageId()).append(",");
        json.append("\"title\":\"").append(escapeJson(message.getTitle())).append("\",");
        json.append("\"content\":\"").append(escapeJson(message.getContent())).append("\",");
        json.append("\"messageType\":\"").append(message.getMessageType()).append("\",");
        json.append("\"messageLevel\":\"").append(message.getMessageLevel()).append("\",");
        json.append("\"senderName\":\"").append(escapeJson(message.getSenderName())).append("\",");
        json.append("\"createTime\":\"").append(message.getCreateTime()).append("\"");
        json.append("}");
        return json.toString();
    }

    /**
     * 构建未读消息数量JSON
     * 
     * @param unreadCount 未读消息数量
     * @return JSON字符串
     */
    private String buildUnreadCountJson(int unreadCount) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"type\":\"unreadCount\",");
        json.append("\"count\":").append(unreadCount);
        json.append("}");
        return json.toString();
    }

    /**
     * 转义JSON字符串
     * 
     * @param str 原始字符串
     * @return 转义后的字符串
     */
    private String escapeJson(String str) {
        if (str == null) {
            return "";
        }
        return str.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }

}
