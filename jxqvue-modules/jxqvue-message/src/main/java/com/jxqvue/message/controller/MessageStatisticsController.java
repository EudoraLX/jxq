package com.jxqvue.message.controller;

import java.util.List;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.annotation.Log;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.message.domain.MessageStatistics;
import com.jxqvue.message.service.IMessageStatisticsService;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 消息统计Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/message/statistics")
public class MessageStatisticsController extends BaseController
{
    @Autowired
    private IMessageStatisticsService messageStatisticsService;

    /**
     * 获取消息统计概览
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:view')")
    @GetMapping
    public AjaxResult getMessageStatistics()
    {
        Map<String, Object> statistics = messageStatisticsService.getMessageStatistics();
        return success(statistics);
    }

    /**
     * 查询消息统计列表
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(MessageStatistics messageStatistics)
    {
        startPage();
        List<MessageStatistics> list = messageStatisticsService.selectMessageStatisticsList(messageStatistics);
        return getDataTable(list);
    }

    /**
     * 获取消息类型统计
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:view')")
    @GetMapping("/type")
    public AjaxResult getMessageTypeStatistics()
    {
        List<Map<String, Object>> statistics = messageStatisticsService.getMessageTypeStatistics();
        return success(statistics);
    }

    /**
     * 获取消息级别统计
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:view')")
    @GetMapping("/level")
    public AjaxResult getMessageLevelStatistics()
    {
        List<Map<String, Object>> statistics = messageStatisticsService.getMessageLevelStatistics();
        return success(statistics);
    }

    /**
     * 获取消息趋势统计
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:view')")
    @GetMapping("/trend")
    public AjaxResult getMessageTrendStatistics()
    {
        List<Map<String, Object>> statistics = messageStatisticsService.getMessageTrendStatistics();
        return success(statistics);
    }

    /**
     * 获取用户消息统计
     */
    @PreAuthorize("@ss.hasPermi('message:statistics:view')")
    @GetMapping("/user/{userId}")
    public AjaxResult getUserMessageStatistics(@PathVariable("userId") Long userId)
    {
        Map<String, Object> statistics = messageStatisticsService.getUserMessageStatistics(userId);
        return success(statistics);
    }
}