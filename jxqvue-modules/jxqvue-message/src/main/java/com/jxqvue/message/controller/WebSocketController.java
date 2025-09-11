package com.jxqvue.message.controller;

import com.jxqvue.message.service.impl.MessagePushServiceImpl;
import com.jxqvue.common.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket消息推送控制器
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Component
public class WebSocketController implements WebSocketHandler
{
    private static final Logger log = LoggerFactory.getLogger(WebSocketController.class);
    
    @Autowired
    private MessagePushServiceImpl messagePushService;
    
    // 存储用户WebSocket连接
    private static final Map<Long, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 从session中获取用户ID（需要在连接时传递）
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.put(userId, session);
            messagePushService.addUserSession(userId, session);
            log.info("用户 {} WebSocket连接已建立", userId);
        } else {
            log.warn("WebSocket连接建立失败：无法获取用户ID");
            session.close();
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 处理客户端发送的消息
        if (message instanceof TextMessage) {
            String payload = ((TextMessage) message).getPayload();
            log.info("收到WebSocket消息: {}", payload);
            
            // 可以在这里处理客户端发送的特定指令
            // 比如心跳检测、特定操作等
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误: {}", exception.getMessage());
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.remove(userId);
            messagePushService.removeUserSession(userId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        Long userId = getUserIdFromSession(session);
        if (userId != null) {
            userSessions.remove(userId);
            messagePushService.removeUserSession(userId);
            log.info("用户 {} WebSocket连接已关闭", userId);
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 从WebSocket会话中获取用户ID
     * 
     * @param session WebSocket会话
     * @return 用户ID
     */
    private Long getUserIdFromSession(WebSocketSession session) {
        try {
            // 从查询参数中获取用户ID
            String userIdStr = session.getUri().getQuery();
            if (userIdStr != null && userIdStr.contains("userId=")) {
                String userId = userIdStr.substring(userIdStr.indexOf("userId=") + 7);
                if (userId.contains("&")) {
                    userId = userId.substring(0, userId.indexOf("&"));
                }
                return Long.valueOf(userId);
            }
            
            // 从请求头中获取用户ID
            Map<String, Object> attributes = session.getAttributes();
            if (attributes.containsKey("userId")) {
                return (Long) attributes.get("userId");
            }
            
            // 从当前登录用户获取
            try {
                return SecurityUtils.getUserId();
            } catch (Exception e) {
                log.warn("无法获取当前登录用户ID: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.error("获取用户ID失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 获取所有在线用户数量
     * 
     * @return 在线用户数量
     */
    public int getOnlineUserCount() {
        return userSessions.size();
    }

    /**
     * 检查用户是否在线
     * 
     * @param userId 用户ID
     * @return 是否在线
     */
    public boolean isUserOnline(Long userId) {
        WebSocketSession session = userSessions.get(userId);
        return session != null && session.isOpen();
    }
}