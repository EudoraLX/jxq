package com.jxqvue.aichat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.jxqvue.aichat.utils.OllamaClient;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 本地AI数据库分析器
 * 使用Ollama AI模型分析数据库查询
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class LocalAIDatabaseAnalyzer {
    
    @Autowired
    private OllamaClient ollamaClient;
    
    @Autowired
    private DatabaseSchemaService schemaService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    // SQL清理正则表达式
    private static final Pattern SQL_PATTERN = Pattern.compile("(?i)SELECT.*?;?", Pattern.DOTALL);
    private static final Pattern CODE_BLOCK_PATTERN = Pattern.compile("```(?:sql)?\\s*(.*?)\\s*```", Pattern.DOTALL);
    
    /**
     * 使用本地AI分析数据库查询
     * 
     * @param userQuestion 用户问题
     * @return AI分析结果
     */
    public String analyzeWithLocalAI(String userQuestion) {
        try {
            System.out.println("开始本地AI数据库分析，问题: " + userQuestion);
            
            // 1. 检查缓存
            String cacheKey = "ai_analysis_" + userQuestion.hashCode();
            String cachedResult = (String) redisTemplate.opsForValue().get(cacheKey);
            if (cachedResult != null) {
                System.out.println("使用缓存结果");
                return cachedResult;
            }
            
            // 2. 检查Ollama连接
            if (!ollamaClient.testConnection()) {
                return "Ollama服务未启动，请先启动Ollama服务。\n" +
                       "启动命令：ollama serve\n" +
                       "下载模型：ollama pull codellama:7b";
            }
            
            // 3. 获取数据库结构
            String schema = schemaService.getSimplifiedSchema();
            System.out.println("获取数据库结构成功");
            
            // 4. 使用AI生成SQL
            String sql = ollamaClient.generateSQL(userQuestion, schema);
            System.out.println("AI生成的SQL: " + sql);
            
            // 5. 清理和验证SQL
            sql = cleanAndValidateSQL(sql);
            System.out.println("清理后的SQL: " + sql);
            
            // 6. 执行查询
            List<Map<String, Object>> results = executeQuery(sql);
            System.out.println("查询结果数量: " + results.size());
            
            // 7. 使用AI分析结果
            String analysis = ollamaClient.analyzeResults(userQuestion, results);
            System.out.println("AI分析结果: " + analysis);
            
            // 8. 缓存结果（1小时）
            redisTemplate.opsForValue().set(cacheKey, analysis, Duration.ofHours(1));
            
            return analysis;
            
        } catch (Exception e) {
            // 静默处理AI超时，直接使用备用方案
            System.out.println("AI分析超时，使用备用方案: " + e.getMessage());
            
            // 尝试使用备用方案
            try {
                return fallbackAnalysis(userQuestion);
            } catch (Exception fallbackException) {
                return "抱歉，数据库查询失败: " + fallbackException.getMessage() + 
                       "\n\n您可以尝试更具体的问题，比如：\n" +
                       "- 我们有多少客户？\n" +
                       "- 库存不足的物料有哪些？\n" +
                       "- 生产订单完成情况如何？\n" +
                       "- 设备运行状态统计\n" +
                       "- 供应商评级情况";
            }
        }
    }
    
    /**
     * 备用分析方法（当AI失败时使用）
     */
    private String fallbackAnalysis(String userQuestion) {
        String question = userQuestion.toLowerCase();
        
        // 客户相关查询
        if (question.contains("客户") || question.contains("customer")) {
            String sql = "SELECT COUNT(*) as customer_count FROM erp_customer";
            List<Map<String, Object>> results = executeQuery(sql);
            if (!results.isEmpty()) {
                Object count = results.get(0).get("customer_count");
                return "根据数据库查询，我们总共有 " + count + " 个客户。";
            }
        }
        
        // 物料相关查询
        if (question.contains("物料") || question.contains("material")) {
            String sql = "SELECT COUNT(*) as material_count FROM erp_material_master";
            List<Map<String, Object>> results = executeQuery(sql);
            if (!results.isEmpty()) {
                Object count = results.get(0).get("material_count");
                return "根据数据库查询，我们总共有 " + count + " 种物料。";
            }
        }
        
        // 生产订单相关查询
        if (question.contains("生产订单") || question.contains("production")) {
            String sql = "SELECT COUNT(*) as order_count FROM erp_production_order";
            List<Map<String, Object>> results = executeQuery(sql);
            if (!results.isEmpty()) {
                Object count = results.get(0).get("order_count");
                return "根据数据库查询，我们总共有 " + count + " 个生产订单。";
            }
        }
        
        // 默认返回基本统计
        return "抱歉，我无法理解您的问题。请尝试更具体的问题，比如：\n" +
               "- 我们有多少客户？\n" +
               "- 库存不足的物料有哪些？\n" +
               "- 生产订单完成情况如何？\n" +
               "- 设备运行状态统计\n" +
               "- 供应商评级情况";
    }
    
    /**
     * 清理和验证SQL
     */
    private String cleanAndValidateSQL(String sql) {
        if (sql == null || sql.trim().isEmpty()) {
            throw new RuntimeException("AI生成的SQL为空");
        }
        
        // 清理SQL
        sql = sql.trim();
        
        // 检查是否是错误信息
        if (sql.contains("error") || sql.contains("Error") || sql.contains("ERROR")) {
            throw new RuntimeException("AI服务错误: " + sql);
        }
        
        // 移除代码块标记
        Matcher codeBlockMatcher = CODE_BLOCK_PATTERN.matcher(sql);
        if (codeBlockMatcher.find()) {
            sql = codeBlockMatcher.group(1).trim();
        }
        
        // 提取SELECT语句
        Matcher sqlMatcher = SQL_PATTERN.matcher(sql);
        if (sqlMatcher.find()) {
            sql = sqlMatcher.group(0).trim();
        }
        
        // 确保是SELECT语句
        if (!sql.toUpperCase().startsWith("SELECT")) {
            throw new RuntimeException("AI生成的不是SELECT语句: " + sql);
        }
        
        // 移除末尾的分号
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        
        // 添加LIMIT限制（如果查询没有LIMIT）
        if (!sql.toUpperCase().contains("LIMIT")) {
            sql += " LIMIT 100";
        }
        
        return sql;
    }
    
    /**
     * 执行查询
     */
    private List<Map<String, Object>> executeQuery(String sql) {
        try {
            System.out.println("执行SQL: " + sql);
            List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
            System.out.println("查询成功，返回 " + results.size() + " 条记录");
            return results;
        } catch (Exception e) {
            throw new RuntimeException("执行SQL查询失败: " + e.getMessage() + 
                                     "\nSQL: " + sql, e);
        }
    }
    
    /**
     * 检查是否是数据库查询问题
     */
    public boolean isDatabaseQuery(String question) {
        String[] dbKeywords = {
            "客户", "订单", "库存", "物料", "设备", "供应商", 
            "生产", "统计", "数量", "列表", "查询", "分析",
            "多少", "哪些", "如何", "情况", "状态", "数据",
            "记录", "总数", "完成", "进行中", "评级", "评分"
        };
        
        String lowerQuestion = question.toLowerCase();
        for (String keyword : dbKeywords) {
            if (lowerQuestion.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 获取数据库统计信息
     */
    public String getDatabaseStatistics() {
        try {
            return schemaService.getTableStatistics();
        } catch (Exception e) {
            return "获取数据库统计信息失败: " + e.getMessage();
        }
    }
    
    /**
     * 测试AI分析功能
     */
    public String testAIAnalysis() {
        try {
            String testQuestion = "我们有多少客户？";
            return analyzeWithLocalAI(testQuestion);
        } catch (Exception e) {
            return "AI分析测试失败: " + e.getMessage();
        }
    }
}
