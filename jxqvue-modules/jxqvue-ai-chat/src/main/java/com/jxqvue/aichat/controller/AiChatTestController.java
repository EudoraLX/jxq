package com.jxqvue.aichat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.aichat.utils.OpenRouterClient;

/**
 * AI聊天测试Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/aichat/test")
public class AiChatTestController extends BaseController
{
    @Autowired
    private OpenRouterClient openRouterClient;

    /**
     * 测试AI服务连接
     */
    @GetMapping("/connection")
    public AjaxResult testConnection()
    {
        try {
            boolean isConnected = openRouterClient.testConnection();
            if (isConnected) {
                return success("AI服务连接正常");
            } else {
                return error("AI服务连接失败");
            }
        } catch (Exception e) {
            return error("测试连接时发生错误：" + e.getMessage());
        }
    }

    /**
     * 获取API配置信息
     */
    @GetMapping("/config")
    public AjaxResult getConfig()
    {
        try {
            // 这里可以返回一些配置信息（不包含敏感信息）
            return success("API配置正常");
        } catch (Exception e) {
            return error("获取配置信息失败：" + e.getMessage());
        }
    }
}
