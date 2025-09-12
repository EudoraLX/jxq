package com.jxqvue.aichat.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;

/**
 * Ollama客户端服务
 * 用于调用本地Ollama AI模型
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class OllamaClient {
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String ollamaUrl = "http://localhost:11434";
    
    public OllamaClient() {
        this.webClient = WebClient.builder()
            .baseUrl(ollamaUrl)
            .defaultHeader("Content-Type", "application/json")
            .build();
        this.objectMapper = new ObjectMapper();
    }
    
    /**
     * 调用Ollama生成SQL查询
     * 
     * @param userQuestion 用户问题
     * @param databaseSchema 数据库结构
     * @return AI生成的SQL查询
     */
    public String generateSQL(String userQuestion, String databaseSchema) {
        try {
            String prompt = buildSQLPrompt(userQuestion, databaseSchema);
            
            OllamaRequest request = new OllamaRequest();
            request.setModel("codellama:7b");
            request.setPrompt(prompt);
            request.setStream(false);
            
            String response = webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .exchange()
                .timeout(Duration.ofSeconds(10))
                .block()
                .bodyToMono(String.class)
                .block();
            
            return parseOllamaResponse(response);
            
        } catch (Exception e) {
            throw new RuntimeException("调用Ollama生成SQL失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 调用Ollama分析查询结果
     * 
     * @param question 原始问题
     * @param results 查询结果
     * @return AI分析结果
     */
    public String analyzeResults(String question, java.util.List<java.util.Map<String, Object>> results) {
        try {
            String prompt = buildAnalysisPrompt(question, results);
            
            OllamaRequest request = new OllamaRequest();
            request.setModel("codellama:7b");
            request.setPrompt(prompt);
            request.setStream(false);
            
            String response = webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .exchange()
                .timeout(Duration.ofSeconds(10))
                .block()
                .bodyToMono(String.class)
                .block();
            
            return parseOllamaResponse(response);
            
        } catch (Exception e) {
            throw new RuntimeException("Ollama分析结果失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * 测试Ollama连接
     * 
     * @return 是否连接成功
     */
    public boolean testConnection() {
        try {
            String response = webClient.get()
                .uri("/api/tags")
                .exchange()
                .timeout(Duration.ofSeconds(10))
                .block()
                .bodyToMono(String.class)
                .block();
            
            return response != null && !response.isEmpty();
        } catch (Exception e) {
            System.out.println("Ollama连接测试失败: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * 构建SQL生成提示词
     */
    private String buildSQLPrompt(String question, String schema) {
        return String.format(
            "你是一个SQL专家。根据用户问题和数据库结构，生成准确的SQL查询。\n" +
            "\n" +
            "用户问题：%s\n" +
            "\n" +
            "数据库结构：\n" +
            "%s\n" +
            "\n" +
            "请直接返回SQL查询语句，不要包含任何解释或其他内容。\n" +
            "只返回SELECT语句，确保语法正确。\n" +
            "如果问题涉及统计，使用COUNT、SUM等聚合函数。\n" +
            "如果问题涉及列表，使用LIMIT限制结果数量。",
            question, schema);
    }
    
    /**
     * 构建结果分析提示词
     */
    private String buildAnalysisPrompt(String question, java.util.List<java.util.Map<String, Object>> results) {
        StringBuilder dataStr = new StringBuilder();
        for (java.util.Map<String, Object> row : results) {
            dataStr.append(row.toString()).append("\n");
        }
        
        return String.format(
            "你是一个数据分析专家。请根据用户问题和查询结果，生成清晰的分析报告。\n" +
            "\n" +
            "用户问题：%s\n" +
            "\n" +
            "查询结果：\n" +
            "%s\n" +
            "\n" +
            "请用自然语言分析这些数据，提供有价值的洞察。\n" +
            "如果数据为空，请说明原因。\n" +
            "如果数据很多，请总结主要趋势。",
            question, dataStr.toString());
    }
    
    /**
     * 解析Ollama响应
     */
    private String parseOllamaResponse(String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            return jsonNode.get("response").asText();
        } catch (Exception e) {
            // 如果解析失败，直接返回原始响应
            return response;
        }
    }
    
    /**
     * Ollama请求模型
     */
    public static class OllamaRequest {
        private String model;
        private String prompt;
        private boolean stream;
        
        // Getters and Setters
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        
        public String getPrompt() { return prompt; }
        public void setPrompt(String prompt) { this.prompt = prompt; }
        
        public boolean isStream() { return stream; }
        public void setStream(boolean stream) { this.stream = stream; }
    }
}
