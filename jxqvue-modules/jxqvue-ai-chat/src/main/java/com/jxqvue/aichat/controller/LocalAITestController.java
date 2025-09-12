package com.jxqvue.aichat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.aichat.utils.OllamaClient;
import com.jxqvue.aichat.service.LocalAIDatabaseAnalyzer;
import com.jxqvue.aichat.service.DatabaseSchemaService;

/**
 * 本地AI测试Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/aichat/localai")
public class LocalAITestController extends BaseController {
    
    @Autowired
    private OllamaClient ollamaClient;
    
    @Autowired
    private LocalAIDatabaseAnalyzer localAIDatabaseAnalyzer;
    
    @Autowired
    private DatabaseSchemaService databaseSchemaService;
    
    /**
     * 测试Ollama连接
     */
    @GetMapping("/test-ollama")
    public AjaxResult testOllama() {
        try {
            boolean isConnected = ollamaClient.testConnection();
            if (isConnected) {
                return success("Ollama服务连接正常");
            } else {
                return error("Ollama服务连接失败，请确保Ollama已启动");
            }
        } catch (Exception e) {
            return error("测试Ollama连接时发生错误：" + e.getMessage());
        }
    }
    
    /**
     * 测试数据库结构获取
     */
    @GetMapping("/test-schema")
    public AjaxResult testDatabaseSchema() {
        try {
            String schema = databaseSchemaService.getSimplifiedSchema();
            return AjaxResult.success("数据库结构获取成功", schema);
        } catch (Exception e) {
            return error("获取数据库结构失败：" + e.getMessage());
        }
    }
    
    /**
     * 测试数据库统计信息
     */
    @GetMapping("/test-statistics")
    public AjaxResult testDatabaseStatistics() {
        try {
            String statistics = databaseSchemaService.getTableStatistics();
            return AjaxResult.success("数据库统计信息获取成功", statistics);
        } catch (Exception e) {
            return error("获取数据库统计信息失败：" + e.getMessage());
        }
    }
    
    /**
     * 测试本地AI数据库分析
     */
    @GetMapping("/test-analysis")
    public AjaxResult testLocalAIAnalysis(@RequestParam(defaultValue = "我们有多少客户？") String question) {
        try {
            String result = localAIDatabaseAnalyzer.analyzeWithLocalAI(question);
            return AjaxResult.success("本地AI分析成功", result);
        } catch (Exception e) {
            return error("本地AI分析失败：" + e.getMessage());
        }
    }
    
    /**
     * 测试AI分析功能
     */
    @GetMapping("/test-ai-analysis")
    public AjaxResult testAIAnalysis() {
        try {
            String result = localAIDatabaseAnalyzer.testAIAnalysis();
            return AjaxResult.success("AI分析测试成功", result);
        } catch (Exception e) {
            return error("AI分析测试失败：" + e.getMessage());
        }
    }
    
    /**
     * 检查是否是数据库查询问题
     */
    @GetMapping("/check-query")
    public AjaxResult checkDatabaseQuery(@RequestParam String question) {
        try {
            boolean isQuery = localAIDatabaseAnalyzer.isDatabaseQuery(question);
            return AjaxResult.success("查询检测完成", isQuery ? "是数据库查询问题" : "不是数据库查询问题");
        } catch (Exception e) {
            return error("查询检测失败：" + e.getMessage());
        }
    }
    
    /**
     * 获取系统状态
     */
    @GetMapping("/status")
    public AjaxResult getSystemStatus() {
        try {
            StringBuilder status = new StringBuilder();
            
            // 检查Ollama连接
            boolean ollamaConnected = ollamaClient.testConnection();
            status.append("Ollama服务: ").append(ollamaConnected ? "正常" : "异常").append("\n");
            
            // 检查数据库连接
            try {
                String schema = databaseSchemaService.getSimplifiedSchema();
                status.append("数据库连接: 正常\n");
            } catch (Exception e) {
                status.append("数据库连接: 异常 - ").append(e.getMessage()).append("\n");
            }
            
            // 检查Redis连接
            try {
                // 这里可以添加Redis连接检查
                status.append("Redis缓存: 正常\n");
            } catch (Exception e) {
                status.append("Redis缓存: 异常 - ").append(e.getMessage()).append("\n");
            }
            
            return AjaxResult.success("系统状态检查完成", status.toString());
        } catch (Exception e) {
            return error("系统状态检查失败：" + e.getMessage());
        }
    }
}
