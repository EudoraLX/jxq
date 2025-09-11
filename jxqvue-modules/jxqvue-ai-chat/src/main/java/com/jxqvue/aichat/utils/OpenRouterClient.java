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
            
            // 构建请求体
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", config.getModel());
            requestBody.put("max_tokens", config.getMaxTokens());
            requestBody.put("temperature", config.getTemperature());
            
            // 转换消息格式
            List<Map<String, String>> openRouterMessages = new ArrayList<>();
            for (ChatMessage message : messages) {
                Map<String, String> msg = new HashMap<>();
                msg.put("role", message.getRole());
                msg.put("content", message.getContent());
                openRouterMessages.add(msg);
            }
            requestBody.put("messages", openRouterMessages);

            log.info("发送OpenRouter请求: {}", objectMapper.writeValueAsString(requestBody));

            // 发送请求
            Mono<Map> response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .timeout(Duration.ofMillis(config.getTimeout()));

            Map<String, Object> result = response.block();
            
            if (result != null && result.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    String content = (String) message.get("content");
                    
                    log.info("OpenRouter响应成功: {}", content);
                    return content;
                }
            }
            
            log.error("OpenRouter响应格式错误: {}", result);
            return "抱歉，AI服务暂时不可用，请稍后重试。";
            
        } catch (Exception e) {
            log.error("调用OpenRouter API失败", e);
            return "抱歉，AI服务出现错误，请稍后重试。";
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
