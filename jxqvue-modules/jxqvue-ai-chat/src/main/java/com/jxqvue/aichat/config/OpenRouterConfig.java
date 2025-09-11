package com.jxqvue.aichat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OpenRouter配置类
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Component
@ConfigurationProperties(prefix = "openrouter")
public class OpenRouterConfig
{
    /** API密钥 */
    private String apiKey;

    /** API基础地址 */
    private String baseUrl = "https://openrouter.ai/api/v1";

    /** 模型名称 */
    private String model = "deepseek/deepseek-r1:free:online";

    /** 最大令牌数 */
    private Integer maxTokens = 1000;

    /** 温度参数 */
    private Double temperature = 0.7;

    /** 请求超时时间（毫秒） */
    private Integer timeout = 30000;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
