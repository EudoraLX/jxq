package com.jxqvue.message.service.impl;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.message.mapper.MessageMapper;
import com.jxqvue.message.mapper.MessageReceiverMapper;
import com.jxqvue.message.domain.Message;
import com.jxqvue.message.domain.MessageRequest;
import com.jxqvue.message.domain.MessageReceiver;
import com.jxqvue.message.service.IMessageService;
import com.jxqvue.message.service.MessagePushService;
import com.jxqvue.common.utils.SecurityUtils;

/**
 * 消息Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class MessageServiceImpl implements IMessageService 
{
    @Autowired
    private MessageMapper messageMapper;
    
    @Autowired
    private MessageReceiverMapper messageReceiverMapper;
    
    @Autowired
    private MessagePushService messagePushService;

    /**
     * 查询消息
     * 
     * @param messageId 消息主键
     * @return 消息
     */
    @Override
    public Message selectMessageByMessageId(Long messageId)
    {
        return messageMapper.selectMessageByMessageId(messageId);
    }

    /**
     * 查询消息列表
     * 
     * @param message 消息
     * @return 消息
     */
    @Override
    public List<Message> selectMessageList(Message message)
    {
        return messageMapper.selectMessageList(message);
    }

    /**
     * 新增消息
     * 
     * @param message 消息
     * @return 结果
     */
    @Override
    public int insertMessage(Message message)
    {
        message.setCreateTime(new Date());
        return messageMapper.insertMessage(message);
    }

    /**
     * 修改消息
     * 
     * @param message 消息
     * @return 结果
     */
    @Override
    public int updateMessage(Message message)
    {
        message.setUpdateTime(new Date());
        return messageMapper.updateMessage(message);
    }

    /**
     * 批量删除消息
     * 
     * @param messageIds 需要删除的消息主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageIds(Long[] messageIds)
    {
        return messageMapper.deleteMessageByMessageIds(messageIds);
    }

    /**
     * 删除消息信息
     * 
     * @param messageId 消息主键
     * @return 结果
     */
    @Override
    public int deleteMessageByMessageId(Long messageId)
    {
        return messageMapper.deleteMessageByMessageId(messageId);
    }

    /**
     * 查询用户未读消息数量
     * 
     * @param userId 用户ID
     * @return 未读消息数量
     */
    @Override
    public int selectUnreadCount(Long userId)
    {
        return messageMapper.selectUnreadCount(userId);
    }

    /**
     * 标记消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int markAsRead(Long messageId)
    {
        // 获取当前用户ID
        Long userId = SecurityUtils.getUserId();
        
        // 查找当前用户的消息接收记录
        MessageReceiver queryReceiver = new MessageReceiver();
        queryReceiver.setMessageId(messageId);
        queryReceiver.setReceiverId(userId);
        List<MessageReceiver> receivers = messageReceiverMapper.selectMessageReceiverList(queryReceiver);
        
        if (!receivers.isEmpty()) {
            MessageReceiver receiver = receivers.get(0);
            receiver.setReadStatus("1"); // 已读
            receiver.setReadTime(new Date());
            int result = messageReceiverMapper.updateMessageReceiver(receiver);
            
            // 标记已读后更新未读消息数量
            if (result > 0) {
                try {
                    int unreadCount = selectUnreadCount(userId);
                    messagePushService.pushUnreadCountUpdate(userId, unreadCount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            return result;
        }
        
        return 0;
    }

    /**
     * 批量标记消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    @Override
    public int markAsReadBatch(Long[] messageIds)
    {
        // 获取当前用户ID
        Long userId = SecurityUtils.getUserId();
        
        // 验证用户是否有权限标记这些消息为已读
        for (Long messageId : messageIds) {
            MessageReceiver queryReceiver = new MessageReceiver();
            queryReceiver.setMessageId(messageId);
            queryReceiver.setReceiverId(userId);
            List<MessageReceiver> receivers = messageReceiverMapper.selectMessageReceiverList(queryReceiver);
            
            if (receivers.isEmpty()) {
                throw new RuntimeException("消息ID " + messageId + " 不属于当前用户或不存在");
            }
        }
        
        // 批量更新当前用户的消息接收记录
        int result = 0;
        for (Long messageId : messageIds) {
            MessageReceiver queryReceiver = new MessageReceiver();
            queryReceiver.setMessageId(messageId);
            queryReceiver.setReceiverId(userId);
            List<MessageReceiver> receivers = messageReceiverMapper.selectMessageReceiverList(queryReceiver);
            
            if (!receivers.isEmpty()) {
                MessageReceiver receiver = receivers.get(0);
                receiver.setReadStatus("1"); // 已读
                receiver.setReadTime(new Date());
                result += messageReceiverMapper.updateMessageReceiver(receiver);
            }
        }
        
        // 更新未读消息数量
        if (result > 0) {
            try {
                int unreadCount = selectUnreadCount(userId);
                messagePushService.pushUnreadCountUpdate(userId, unreadCount);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 发送消息
     * 
     * @param message 消息
     * @return 结果
     */
    @Override
    public int sendMessage(Message message)
    {
        // 检查消息ID是否存在
        if (message.getMessageId() == null) {
            throw new RuntimeException("消息ID不能为空");
        }
        
        // 获取消息的接收者列表，确保接收者记录存在
        List<MessageReceiver> receivers = messageReceiverMapper.selectMessageReceiverByMessageId(message.getMessageId());
        if (receivers == null || receivers.isEmpty()) {
            throw new RuntimeException("消息没有接收者，无法发送");
        }
        
        // 更新消息状态为已发布
        message.setReadStatus("1"); // 已发布
        message.setPushStatus("0"); // 未推送
        
        int result = updateMessage(message);
        
        // 发送成功后进行实时推送
        if (result > 0) {
            try {
                for (MessageReceiver receiver : receivers) {
                    messagePushService.pushMessageToUser(receiver.getReceiverId(), message);
                    
                    // 更新未读消息数量
                    int unreadCount = selectUnreadCount(receiver.getReceiverId());
                    messagePushService.pushUnreadCountUpdate(receiver.getReceiverId(), unreadCount);
                }
            } catch (Exception e) {
                // 推送失败不影响消息发送
                e.printStackTrace();
            }
        }
        
        return result;
    }

    /**
     * 批量发送消息
     * 
     * @param message 消息模板
     * @param receiverIds 接收者ID列表
     * @return 结果
     */
    @Override
    public int sendMessageBatch(Message message, List<Long> receiverIds)
    {
        // 创建消息请求对象
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setTitle(message.getTitle());
        messageRequest.setContent(message.getContent());
        messageRequest.setMessageType(message.getMessageType());
        messageRequest.setMessageLevel(message.getMessageLevel());
        messageRequest.setSenderId(message.getSenderId());
        messageRequest.setSenderName(message.getSenderName());
        messageRequest.setBusinessType(message.getBusinessType());
        messageRequest.setBusinessId(message.getBusinessId());
        messageRequest.setReadStatus("0"); // 未读
        messageRequest.setPushStatus("0"); // 未推送
        
        // 创建接收者列表
        List<MessageReceiver> receivers = new java.util.ArrayList<>();
        for (Long receiverId : receiverIds) {
            MessageReceiver receiver = new MessageReceiver();
            receiver.setReceiverId(receiverId);
            receivers.add(receiver);
        }
        messageRequest.setReceivers(receivers);
        
        return insertMessageWithReceivers(messageRequest);
    }

    /**
     * 推送消息
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int pushMessage(Long messageId)
    {
        Message message = new Message();
        message.setMessageId(messageId);
        message.setPushStatus("1"); // 已推送
        message.setPushTime(new Date());
        return updateMessage(message);
    }

    @Override
    public MessageRequest getMessageDetailWithReceivers(Long messageId)
    {
        // 获取消息基本信息
        Message message = selectMessageByMessageId(messageId);
        if (message == null) {
            return null;
        }
        
        // 获取接收者列表
        List<MessageReceiver> receivers = messageReceiverMapper.selectMessageReceiverByMessageId(messageId);
        
        // 构建MessageRequest对象
        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageId(message.getMessageId());
        messageRequest.setTitle(message.getTitle());
        messageRequest.setContent(message.getContent());
        messageRequest.setMessageType(message.getMessageType());
        messageRequest.setMessageLevel(message.getMessageLevel());
        messageRequest.setSenderId(message.getSenderId());
        messageRequest.setSenderName(message.getSenderName());
        messageRequest.setBusinessType(message.getBusinessType());
        messageRequest.setBusinessId(message.getBusinessId());
        messageRequest.setReadStatus(message.getReadStatus());
        messageRequest.setPushStatus(message.getPushStatus());
        messageRequest.setRemark(message.getRemark());
        messageRequest.setReceivers(receivers);
        
        return messageRequest;
    }

    /**
     * 查询用户消息列表
     * 
     * @param userId 用户ID
     * @return 消息集合
     */
    @Override
    public List<Message> selectUserMessageList(Long userId)
    {
        // 通过消息接收者关联表查询用户的消息
        return messageMapper.selectUserMessageListByUserId(userId);
    }

    /**
     * 查询用户消息详情
     * 
     * @param userId 用户ID
     * @param messageId 消息ID
     * @return 消息详情
     */
    @Override
    public Message selectUserMessageDetail(Long userId, Long messageId)
    {
        // 通过消息接收者关联表查询用户的消息详情
        return messageMapper.selectUserMessageDetailByUserIdAndMessageId(userId, messageId);
    }

    /**
     * 新增消息（支持多接收者）
     * 
     * @param messageRequest 消息请求
     * @return 结果
     */
    @Override
    public int insertMessageWithReceivers(MessageRequest messageRequest)
    {
        // 设置默认值
        if (messageRequest.getReadStatus() == null) {
            messageRequest.setReadStatus("0"); // 未读
        }
        if (messageRequest.getPushStatus() == null) {
            messageRequest.setPushStatus("0"); // 未推送
        }
        
        // 插入消息
        Message message = new Message();
        message.setTitle(messageRequest.getTitle());
        message.setContent(messageRequest.getContent());
        message.setMessageType(messageRequest.getMessageType());
        message.setMessageLevel(messageRequest.getMessageLevel());
        message.setSenderId(messageRequest.getSenderId());
        message.setSenderName(messageRequest.getSenderName());
        message.setReadStatus(messageRequest.getReadStatus());
        message.setBusinessType(messageRequest.getBusinessType());
        if (messageRequest.getBusinessId() != null) {
            message.setBusinessId(Long.valueOf(messageRequest.getBusinessId().toString()));
        }
        message.setPushStatus(messageRequest.getPushStatus());
        message.setPushTime(messageRequest.getPushTime());
        message.setCreateBy(messageRequest.getCreateBy());
        message.setCreateTime(new Date());
        message.setRemark(messageRequest.getRemark());
        
        int result = messageMapper.insertMessage(message);
        
        // 设置返回的消息ID
        messageRequest.setMessageId(message.getMessageId());
        
        if (result > 0 && messageRequest.getMessageId() != null) {
            // 插入接收者关联记录
            for (MessageReceiver receiver : messageRequest.getReceivers()) {
                receiver.setMessageId(messageRequest.getMessageId());
                receiver.setReadStatus("0"); // 未读
                receiver.setCreateTime(new Date());
                receiver.setCreateBy(SecurityUtils.getUsername());
                // 如果没有设置接收者名称，使用默认值
                if (receiver.getReceiverName() == null || receiver.getReceiverName().isEmpty()) {
                    receiver.setReceiverName("用户" + receiver.getReceiverId());
                }
                messageReceiverMapper.insertMessageReceiver(receiver);
            }
        }
        
        return result;
    }

    /**
     * 修改消息（支持多接收者）
     * 
     * @param messageRequest 消息请求
     * @return 结果
     */
    @Override
    public int updateMessageWithReceivers(MessageRequest messageRequest)
    {
        // 更新消息
        Message message = new Message();
        message.setMessageId(messageRequest.getMessageId());
        message.setTitle(messageRequest.getTitle());
        message.setContent(messageRequest.getContent());
        message.setMessageType(messageRequest.getMessageType());
        message.setMessageLevel(messageRequest.getMessageLevel());
        message.setSenderId(messageRequest.getSenderId());
        message.setSenderName(messageRequest.getSenderName());
        message.setReadStatus(messageRequest.getReadStatus());
        message.setBusinessType(messageRequest.getBusinessType());
        if (messageRequest.getBusinessId() != null) {
            message.setBusinessId(Long.valueOf(messageRequest.getBusinessId().toString()));
        }
        message.setPushStatus(messageRequest.getPushStatus());
        message.setPushTime(messageRequest.getPushTime());
        message.setUpdateBy(messageRequest.getUpdateBy());
        message.setUpdateTime(new Date());
        message.setRemark(messageRequest.getRemark());
        
        int result = messageMapper.updateMessage(message);
        
        if (result > 0 && messageRequest.getMessageId() != null) {
            // 删除原有的接收者关联记录
            messageReceiverMapper.deleteMessageReceiverByMessageId(messageRequest.getMessageId());
            
            // 插入新的接收者关联记录
            for (MessageReceiver receiver : messageRequest.getReceivers()) {
                receiver.setMessageId(messageRequest.getMessageId());
                receiver.setReadStatus("0"); // 未读
                receiver.setCreateTime(new Date());
                receiver.setCreateBy(SecurityUtils.getUsername());
                // 如果没有设置接收者名称，使用默认值
                if (receiver.getReceiverName() == null || receiver.getReceiverName().isEmpty()) {
                    receiver.setReceiverName("用户" + receiver.getReceiverId());
                }
                messageReceiverMapper.insertMessageReceiver(receiver);
            }
        }
        
        return result;
    }

    // ==================== 我的消息相关方法实现 ====================

    /**
     * 查询我的消息列表
     * 
     * @param message 消息
     * @return 消息集合
     */
    @Override
    public List<Message> selectMyMessageList(Message message)
    {
        // 获取当前用户ID
        Long currentUserId = SecurityUtils.getUserId();
        return messageMapper.selectMyMessageListByUserId(currentUserId, message);
    }

    /**
     * 根据消息ID查询我的消息详情
     * 
     * @param messageId 消息ID
     * @return 消息详情
     */
    @Override
    public Message selectMyMessageByMessageId(Long messageId)
    {
        Long currentUserId = SecurityUtils.getUserId();
        return messageMapper.selectUserMessageDetailByUserIdAndMessageId(currentUserId, messageId);
    }

    /**
     * 标记我的消息为已读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int markMyMessageAsRead(Long messageId)
    {
        Long currentUserId = SecurityUtils.getUserId();
        return messageReceiverMapper.markAsReadByUserIdAndMessageId(currentUserId, messageId);
    }

    /**
     * 批量标记我的消息为已读
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    @Override
    public int markMyMessageAsReadBatch(Long[] messageIds)
    {
        Long currentUserId = SecurityUtils.getUserId();
        int result = 0;
        for (Long messageId : messageIds) {
            result += messageReceiverMapper.markAsReadByUserIdAndMessageId(currentUserId, messageId);
        }
        return result;
    }

    /**
     * 标记我的消息为未读
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int markMyMessageAsUnread(Long messageId)
    {
        Long currentUserId = SecurityUtils.getUserId();
        return messageReceiverMapper.markAsUnreadByUserIdAndMessageId(currentUserId, messageId);
    }

    /**
     * 删除我的消息
     * 
     * @param messageId 消息ID
     * @return 结果
     */
    @Override
    public int removeMyMessage(Long messageId)
    {
        Long currentUserId = SecurityUtils.getUserId();
        return messageReceiverMapper.deleteByUserIdAndMessageId(currentUserId, messageId);
    }

    /**
     * 批量删除我的消息
     * 
     * @param messageIds 消息ID数组
     * @return 结果
     */
    @Override
    public int removeMyMessageBatch(Long[] messageIds)
    {
        Long currentUserId = SecurityUtils.getUserId();
        int result = 0;
        for (Long messageId : messageIds) {
            result += messageReceiverMapper.deleteByUserIdAndMessageId(currentUserId, messageId);
        }
        return result;
    }

    /**
     * 获取我的消息统计
     * 
     * @return 统计结果
     */
    @Override
    public Map<String, Object> getMyMessageStatistics()
    {
        Long currentUserId = SecurityUtils.getUserId();
        Map<String, Object> result = new HashMap<>();
        
        // 我的总消息数
        Long totalMessages = messageReceiverMapper.countUserMessages(currentUserId);
        result.put("totalMessages", totalMessages);
        
        // 我的未读消息数
        Long unreadMessages = messageReceiverMapper.countUserUnreadMessages(currentUserId);
        result.put("unreadMessages", unreadMessages);
        
        // 我的已读消息数
        Long readMessages = messageReceiverMapper.countUserReadMessages(currentUserId);
        result.put("readMessages", readMessages);
        
        return result;
    }
}
