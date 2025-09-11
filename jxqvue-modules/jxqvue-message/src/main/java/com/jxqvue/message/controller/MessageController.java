package com.jxqvue.message.controller;

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
import com.jxqvue.message.domain.Message;
import com.jxqvue.message.domain.MessageRequest;
import com.jxqvue.message.service.IMessageService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 消息Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/message/message")
public class MessageController extends BaseController
{
    @Autowired
    private IMessageService messageService;

    /**
     * 查询消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(Message message)
    {
        startPage();
        List<Message> list = messageService.selectMessageList(message);
        return getDataTable(list);
    }

    /**
     * 导出消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:message:export')")
    @Log(title = "消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Message message)
    {
        List<Message> list = messageService.selectMessageList(message);
        ExcelUtil<Message> util = new ExcelUtil<Message>(Message.class);
        util.exportExcel(response, list, "消息数据");
    }

    /**
     * 获取消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId)
    {
        return success(messageService.selectMessageByMessageId(messageId));
    }

    /**
     * 获取消息详细信息（包含接收者）
     */
    @PreAuthorize("@ss.hasPermi('message:message:query')")
    @GetMapping(value = "/detail/{messageId}")
    public AjaxResult getDetailWithReceivers(@PathVariable("messageId") Long messageId)
    {
        return success(messageService.getMessageDetailWithReceivers(messageId));
    }

    /**
     * 新增消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:add')")
    @Log(title = "消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageRequest messageRequest)
    {
        try {
            // 验证接收者列表
            if (messageRequest.getReceivers() == null || messageRequest.getReceivers().isEmpty()) {
                return error("请选择至少一个接收者");
            }
            
            // 验证业务ID
            if (messageRequest.getBusinessId() != null) {
                try {
                    Long businessId = Long.valueOf(messageRequest.getBusinessId().toString());
                    messageRequest.setBusinessId(businessId);
                } catch (NumberFormatException e) {
                    return error("业务ID必须是有效的数字");
                }
            }
            
            return toAjax(messageService.insertMessageWithReceivers(messageRequest));
        } catch (Exception e) {
            return error("新增消息失败：" + e.getMessage());
        }
    }

    /**
     * 修改消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:edit')")
    @Log(title = "消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageRequest messageRequest)
    {
        try {
            // 验证接收者列表
            if (messageRequest.getReceivers() == null || messageRequest.getReceivers().isEmpty()) {
                return error("请选择至少一个接收者");
            }
            
            // 验证业务ID
            if (messageRequest.getBusinessId() != null) {
                try {
                    Long businessId = Long.valueOf(messageRequest.getBusinessId().toString());
                    messageRequest.setBusinessId(businessId);
                } catch (NumberFormatException e) {
                    return error("业务ID必须是有效的数字");
                }
            }
            
            return toAjax(messageService.updateMessageWithReceivers(messageRequest));
        } catch (Exception e) {
            return error("修改消息失败：" + e.getMessage());
        }
    }

    /**
     * 删除消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:remove')")
    @Log(title = "消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds)
    {
        return toAjax(messageService.deleteMessageByMessageIds(messageIds));
    }

    /**
     * 查询用户未读消息数量
     */
    @GetMapping("/unread/count")
    public AjaxResult getUnreadCount()
    {
        Long userId = getUserId();
        int count = messageService.selectUnreadCount(userId);
        return success(count);
    }

    /**
     * 查询用户消息列表
     */
    @GetMapping("/user/list")
    public TableDataInfo getUserMessageList(Message message)
    {
        startPage();
        List<Message> list = messageService.selectUserMessageList(getUserId());
        return getDataTable(list);
    }

    /**
     * 查询用户消息详情（移动端专用，无需特殊权限）
     */
    @GetMapping("/user/detail/{messageId}")
    public AjaxResult getUserMessageDetail(@PathVariable("messageId") Long messageId)
    {
        Long userId = getUserId();
        Message message = messageService.selectUserMessageDetail(userId, messageId);
        if (message == null) {
            return error("消息不存在或无权限访问");
        }
        return success(message);
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/read/{messageId}")
    public AjaxResult markAsRead(@PathVariable Long messageId)
    {
        return toAjax(messageService.markAsRead(messageId));
    }

    /**
     * 批量标记消息为已读
     */
    @PutMapping("/read/batch")
    public AjaxResult markAsReadBatch(@RequestBody Long[] messageIds)
    {
        return toAjax(messageService.markAsReadBatch(messageIds));
    }

    /**
     * 发送消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:send')")
    @Log(title = "发送消息", businessType = BusinessType.UPDATE)
    @PostMapping("/send")
    public AjaxResult sendMessage(@RequestBody Message message)
    {
        return toAjax(messageService.sendMessage(message));
    }

    /**
     * 推送消息
     */
    @PreAuthorize("@ss.hasPermi('message:message:push')")
    @Log(title = "推送消息", businessType = BusinessType.UPDATE)
    @PutMapping("/push/{messageId}")
    public AjaxResult pushMessage(@PathVariable Long messageId)
    {
        return toAjax(messageService.pushMessage(messageId));
    }

    // ==================== 我的消息相关接口 ====================

    /**
     * 查询我的消息列表
     */
    @PreAuthorize("@ss.hasPermi('message:my:list')")
    @GetMapping("/my/list")
    public TableDataInfo getMyMessageList(Message message)
    {
        startPage();
        List<Message> list = messageService.selectMyMessageList(message);
        return getDataTable(list);
    }

    /**
     * 获取我的消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:my:query')")
    @GetMapping(value = "/my/{messageId}")
    public AjaxResult getMyMessageInfo(@PathVariable("messageId") Long messageId)
    {
        return success(messageService.selectMyMessageByMessageId(messageId));
    }

    /**
     * 标记我的消息为已读
     */
    @PreAuthorize("@ss.hasPermi('message:my:read')")
    @Log(title = "标记消息已读", businessType = BusinessType.UPDATE)
    @PutMapping("/my/read/{messageId}")
    public AjaxResult markMyMessageAsRead(@PathVariable Long messageId)
    {
        return toAjax(messageService.markMyMessageAsRead(messageId));
    }

    /**
     * 批量标记我的消息为已读
     */
    @PreAuthorize("@ss.hasPermi('message:my:read')")
    @Log(title = "批量标记消息已读", businessType = BusinessType.UPDATE)
    @PutMapping("/my/read/batch")
    public AjaxResult markMyMessageAsReadBatch(@RequestBody Long[] messageIds)
    {
        return toAjax(messageService.markMyMessageAsReadBatch(messageIds));
    }

    /**
     * 标记我的消息为未读
     */
    @PreAuthorize("@ss.hasPermi('message:my:read')")
    @Log(title = "标记消息未读", businessType = BusinessType.UPDATE)
    @PutMapping("/my/unread/{messageId}")
    public AjaxResult markMyMessageAsUnread(@PathVariable Long messageId)
    {
        return toAjax(messageService.markMyMessageAsUnread(messageId));
    }

    /**
     * 删除我的消息
     */
    @PreAuthorize("@ss.hasPermi('message:my:remove')")
    @Log(title = "删除我的消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/my/{messageId}")
    public AjaxResult removeMyMessage(@PathVariable Long messageId)
    {
        return toAjax(messageService.removeMyMessage(messageId));
    }

    /**
     * 批量删除我的消息
     */
    @PreAuthorize("@ss.hasPermi('message:my:remove')")
    @Log(title = "批量删除我的消息", businessType = BusinessType.DELETE)
    @DeleteMapping("/my/batch")
    public AjaxResult removeMyMessageBatch(@RequestBody Long[] messageIds)
    {
        return toAjax(messageService.removeMyMessageBatch(messageIds));
    }

    /**
     * 获取我的消息统计
     */
    @PreAuthorize("@ss.hasPermi('message:my:query')")
    @GetMapping("/my/statistics")
    public AjaxResult getMyMessageStatistics()
    {
        return success(messageService.getMyMessageStatistics());
    }

    // ==================== 移动端专用接口（无需权限） ====================

    /**
     * 移动端获取我的消息列表
     */
    @GetMapping("/mobile/my/list")
    public TableDataInfo getMobileMyMessageList(Message message)
    {
        startPage();
        List<Message> list = messageService.selectMyMessageList(message);
        return getDataTable(list);
    }

    /**
     * 移动端获取我的消息详细信息
     */
    @GetMapping(value = "/mobile/my/{messageId}")
    public AjaxResult getMobileMyMessageInfo(@PathVariable("messageId") Long messageId)
    {
        return success(messageService.selectMyMessageByMessageId(messageId));
    }

    /**
     * 移动端标记我的消息为已读
     */
    @Log(title = "移动端标记消息已读", businessType = BusinessType.UPDATE)
    @PutMapping("/mobile/my/read/{messageId}")
    public AjaxResult markMobileMyMessageAsRead(@PathVariable Long messageId)
    {
        return toAjax(messageService.markMyMessageAsRead(messageId));
    }

    /**
     * 移动端批量标记我的消息为已读
     */
    @Log(title = "移动端批量标记消息已读", businessType = BusinessType.UPDATE)
    @PutMapping("/mobile/my/read/batch")
    public AjaxResult markMobileMyMessageAsReadBatch(@RequestBody Long[] messageIds)
    {
        return toAjax(messageService.markMyMessageAsReadBatch(messageIds));
    }

    /**
     * 移动端获取我的消息统计
     */
    @GetMapping("/mobile/my/statistics")
    public AjaxResult getMobileMyMessageStatistics()
    {
        return success(messageService.getMyMessageStatistics());
    }
}
