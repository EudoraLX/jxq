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
            request.setModel("phi3:mini");
            request.setPrompt(prompt);
            request.setStream(false);
            
            String response = webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .exchange()
                .timeout(Duration.ofSeconds(30))
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
            request.setModel("phi3:mini");
            request.setPrompt(prompt);
            request.setStream(false);
            
            String response = webClient.post()
                .uri("/api/generate")
                .bodyValue(request)
                .exchange()
                .timeout(Duration.ofSeconds(30))
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
                .timeout(Duration.ofSeconds(30))
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
            "Generate SQL for: %s\n" +
            "Schema: %s\n" +
            "Return only SQL SELECT statement, no explanations.",
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
            "Question: %s\n" +
            "Data: %s\n" +
            "Analyze the data and provide insights in Chinese.",
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
