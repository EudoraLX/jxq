package com.jxqvue.message.service;

import com.jxqvue.message.domain.Message;

/**
 * 消息推送服务接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface MessagePushService 
{
    /**
     * 推送消息给指定用户
     * 
     * @param userId 用户ID
     * @param message 消息
     */
    void pushMessageToUser(Long userId, Message message);

    /**
     * 推送未读消息数量更新
     * 
     * @param userId 用户ID
     * @param unreadCount 未读消息数量
     */
    void pushUnreadCountUpdate(Long userId, int unreadCount);

    /**
     * 推送系统通知
     * 
     * @param message 消息
     */
    void pushSystemNotification(Message message);
}
