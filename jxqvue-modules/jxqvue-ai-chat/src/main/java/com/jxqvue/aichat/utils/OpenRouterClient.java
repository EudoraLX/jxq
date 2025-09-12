package com.jxqvue.aichat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jxqvue.aichat.config.OpenRouterConfig;
import com.jxqvue.aichat.domain.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenRouter API客户端
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Component
public class OpenRouterClient
{
    private static final Logger log = LoggerFactory.getLogger(OpenRouterClient.class);

    @Autowired
    private OpenRouterConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    private WebClient webClient;

    /**
     * 初始化WebClient
     */
    private void initWebClient() {
        if (webClient == null) {
            webClient = WebClient.builder()
                    .baseUrl(config.getBaseUrl())
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + config.getApiKey())
                    .build();
        }
    }

    /**
     * 发送聊天请求到OpenRouter
     * 
     * @param messages 消息列表
     * @return AI回复
     */
    public String sendChatRequest(List<ChatMessage> messages) {
        try {
            initWebClient();
            
            System.out.println("=== OpenRouter API 开始处理 ===");
            System.out.println("模型: " + config.getModel());
            System.out.println("最大令牌数: " + config.getMaxTokens());
            System.out.println("温度: " + config.getTemperature());
            
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", config.getModel());
            requestBody.put("max_tokens", config.getMaxTokens());
            requestBody.put("temperature", config.getTemperature());
            
            // 转换消息格式
            List<Map<String, String>> openRouterMessages = new ArrayList<>();
            for (int i = 0; i < messages.size(); i++) {
                ChatMessage message = messages.get(i);
                Map<String, String> msg = new HashMap<>();
                msg.put("role", message.getRole());
                msg.put("content", message.getContent());
                openRouterMessages.add(msg);
                System.out.println("消息" + i + " [" + message.getRole() + "]: " + message.getContent());
            }
            requestBody.put("messages", openRouterMessages);

            String requestJson = objectMapper.writeValueAsString(requestBody);
            System.out.println("发送到OpenRouter的完整请求: " + requestJson);
            log.info("发送OpenRouter请求: {}", requestJson);

            // 发送请求
            Mono<Map> response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofMillis(config.getTimeout()));

            Map<String, Object> result = response.block();
            System.out.println("OpenRouter原始响应: " + result);
            
            if (result != null && result.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");
                System.out.println("选择数量: " + choices.size());
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    System.out.println("第一个选择: " + choice);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    System.out.println("消息内容: " + message);
                    String content = (String) message.get("content");
                    
                    System.out.println("提取的AI回复内容: " + content);
                    System.out.println("AI回复内容长度: " + (content != null ? content.length() : "null"));
                    
                    if (content != null && !content.trim().isEmpty()) {
                        log.info("OpenRouter响应成功: {}", content);
                        return content;
                    } else {
                        System.out.println("AI回复内容为空，检查错误信息...");
                        if (choice.containsKey("finish_reason")) {
                            String finishReason = (String) choice.get("finish_reason");
                            System.out.println("完成原因: " + finishReason);
                        }
                        if (result.containsKey("error")) {
                            Map<String, Object> error = (Map<String, Object>) result.get("error");
                            System.out.println("API错误: " + error);
                        }
                        return "抱歉，AI服务返回了空内容，请稍后重试。";
                    }
                }
            }
            
            System.out.println("OpenRouter响应格式错误，完整响应: " + result);
            log.error("OpenRouter响应格式错误: {}", result);
            return "抱歉，AI服务暂时不可用，请稍后重试。";
            
        } catch (Exception e) {
            log.error("调用OpenRouter API失败: {}", e.getMessage(), e);
            
            // 根据不同的异常类型返回不同的错误信息
            if (e.getMessage().contains("timeout")) {
                return "AI服务响应超时，请稍后重试。";
            } else if (e.getMessage().contains("Connection refused")) {
                return "无法连接到AI服务，请检查网络连接。";
            } else if (e.getMessage().contains("401") || e.getMessage().contains("Unauthorized")) {
                return "AI服务认证失败，请检查API密钥配置。";
            } else if (e.getMessage().contains("429") || e.getMessage().contains("rate limit")) {
                return "AI服务请求过于频繁，请稍后再试。";
            } else {
                return "AI服务暂时不可用，请稍后重试。错误详情：" + e.getMessage();
            }
        }
    }

    /**
     * 测试API连接
     * 
     * @return 是否连接成功
     */
    public boolean testConnection() {
        try {
            List<ChatMessage> testMessages = new ArrayList<>();
            ChatMessage testMessage = new ChatMessage();
            testMessage.setRole("user");
            testMessage.setContent("你好");
            testMessages.add(testMessage);
            
            String response = sendChatRequest(testMessages);
            return response != null && !response.contains("抱歉");
        } catch (Exception e) {
            log.error("测试OpenRouter连接失败", e);
            return false;
        }
    }
}
