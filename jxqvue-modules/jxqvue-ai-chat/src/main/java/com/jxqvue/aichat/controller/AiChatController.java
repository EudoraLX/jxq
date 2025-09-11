package com.jxqvue.aichat.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.annotation.Log;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.common.enums.BusinessType;
import com.jxqvue.aichat.domain.ChatSession;
import com.jxqvue.aichat.domain.ChatMessage;
import com.jxqvue.aichat.domain.ChatRequest;
import com.jxqvue.aichat.domain.ChatResponse;
import com.jxqvue.aichat.service.IAiChatService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * AI聊天Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/aichat")
public class AiChatController extends BaseController
{
    @Autowired
    private IAiChatService aiChatService;

    /**
     * 查询AI聊天会话列表
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:list')")
    @GetMapping("/session/list")
    public TableDataInfo list(ChatSession chatSession)
    {
        startPage();
        List<ChatSession> list = aiChatService.selectChatSessionList(chatSession);
        return getDataTable(list);
    }

    /**
     * 导出AI聊天会话列表
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:export')")
    @Log(title = "AI聊天会话", businessType = BusinessType.EXPORT)
    @PostMapping("/session/export")
    public void export(HttpServletResponse response, ChatSession chatSession)
    {
        List<ChatSession> list = aiChatService.selectChatSessionList(chatSession);
        ExcelUtil<ChatSession> util = new ExcelUtil<ChatSession>(ChatSession.class);
        util.exportExcel(response, list, "AI聊天会话数据");
    }

    /**
     * 获取AI聊天会话详细信息
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:query')")
    @GetMapping(value = "/session/{sessionId}")
    public AjaxResult getSessionInfo(@PathVariable("sessionId") Long sessionId)
    {
        return success(aiChatService.selectChatSessionBySessionId(sessionId));
    }

    /**
     * 新增AI聊天会话
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:add')")
    @Log(title = "AI聊天会话", businessType = BusinessType.INSERT)
    @PostMapping("/session")
    public AjaxResult addSession(@RequestBody ChatSession chatSession)
    {
        return toAjax(aiChatService.insertChatSession(chatSession));
    }

    /**
     * 修改AI聊天会话
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:edit')")
    @Log(title = "AI聊天会话", businessType = BusinessType.UPDATE)
    @PutMapping("/session")
    public AjaxResult editSession(@RequestBody ChatSession chatSession)
    {
        return toAjax(aiChatService.updateChatSession(chatSession));
    }

    /**
     * 删除AI聊天会话
     */
    @PreAuthorize("@ss.hasPermi('aichat:session:remove')")
    @Log(title = "AI聊天会话", businessType = BusinessType.DELETE)
    @DeleteMapping("/session/{sessionIds}")
    public AjaxResult removeSession(@PathVariable Long[] sessionIds)
    {
        return toAjax(aiChatService.deleteChatSessionBySessionIds(sessionIds));
    }

    /**
     * 查询AI聊天消息列表
     */
    @PreAuthorize("@ss.hasPermi('aichat:message:list')")
    @GetMapping("/message/list")
    public TableDataInfo listMessages(ChatMessage chatMessage)
    {
        startPage();
        List<ChatMessage> list = aiChatService.selectChatMessageList(chatMessage);
        return getDataTable(list);
    }

    /**
     * 获取AI聊天消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('aichat:message:query')")
    @GetMapping(value = "/message/{messageId}")
    public AjaxResult getMessageInfo(@PathVariable("messageId") Long messageId)
    {
        return success(aiChatService.selectChatMessageByMessageId(messageId));
    }

    /**
     * 新增AI聊天消息
     */
    @PreAuthorize("@ss.hasPermi('aichat:message:add')")
    @Log(title = "AI聊天消息", businessType = BusinessType.INSERT)
    @PostMapping("/message")
    public AjaxResult addMessage(@RequestBody ChatMessage chatMessage)
    {
        return toAjax(aiChatService.insertChatMessage(chatMessage));
    }

    /**
     * 修改AI聊天消息
     */
    @PreAuthorize("@ss.hasPermi('aichat:message:edit')")
    @Log(title = "AI聊天消息", businessType = BusinessType.UPDATE)
    @PutMapping("/message")
    public AjaxResult editMessage(@RequestBody ChatMessage chatMessage)
    {
        return toAjax(aiChatService.updateChatMessage(chatMessage));
    }

    /**
     * 删除AI聊天消息
     */
    @PreAuthorize("@ss.hasPermi('aichat:message:remove')")
    @Log(title = "AI聊天消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/message/{messageIds}")
    public AjaxResult removeMessage(@PathVariable Long[] messageIds)
    {
        return toAjax(aiChatService.deleteChatMessageByMessageIds(messageIds));
    }

    // ==================== 聊天功能接口 ====================

    /**
     * 发送聊天消息
     */
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestBody ChatRequest chatRequest)
    {
        try {
            ChatResponse response = aiChatService.sendMessage(chatRequest);
            if (response.getSuccess()) {
                return success(response);
            } else {
                return error(response.getErrorMessage());
            }
        } catch (Exception e) {
            return error("发送消息失败：" + e.getMessage());
        }
    }

    /**
     * 获取用户会话列表
     */
    @GetMapping("/user/sessions")
    public AjaxResult getUserSessions()
    {
        Long userId = getUserId();
        List<ChatSession> sessions = aiChatService.getUserSessions(userId);
        return success(sessions);
    }

    /**
     * 获取会话消息列表
     */
    @GetMapping("/session/{sessionId}/messages")
    public AjaxResult getSessionMessages(@PathVariable Long sessionId)
    {
        List<ChatMessage> messages = aiChatService.getSessionMessages(sessionId);
        return success(messages);
    }

    /**
     * 创建新会话
     */
    @PostMapping("/session/new")
    public AjaxResult createNewSession(@RequestBody ChatSession chatSession)
    {
        Long userId = getUserId();
        String sessionName = chatSession.getSessionName() != null ? chatSession.getSessionName() : "AI客服对话";
        Long sessionId = aiChatService.createNewSession(userId, sessionName);
        return success(sessionId);
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/message/read/{messageId}")
    public AjaxResult markMessageAsRead(@PathVariable Long messageId)
    {
        return toAjax(aiChatService.markMessageAsRead(messageId));
    }

    /**
     * 批量标记消息为已读
     */
    @PutMapping("/message/read/batch")
    public AjaxResult markMessagesAsRead(@RequestBody Long[] messageIds)
    {
        return toAjax(aiChatService.markMessagesAsRead(messageIds));
    }

    /**
     * 获取会话未读消息数量
     */
    @GetMapping("/session/{sessionId}/unread")
    public AjaxResult getUnreadCount(@PathVariable Long sessionId)
    {
        int count = aiChatService.getUnreadCount(sessionId);
        return success(count);
    }

    /**
     * 获取用户所有未读消息数量
     */
    @GetMapping("/user/unread")
    public AjaxResult getUserUnreadCount()
    {
        Long userId = getUserId();
        List<ChatSession> sessions = aiChatService.getUserSessions(userId);
        int totalUnread = 0;
        for (ChatSession session : sessions) {
            totalUnread += aiChatService.getUnreadCount(session.getSessionId());
        }
        return success(totalUnread);
    }
}
