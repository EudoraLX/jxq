package com.jxqvue.aichat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * 数据库结构获取服务
 * 用于获取数据库表结构和示例数据
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class DatabaseSchemaService {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    /**
     * 获取数据库结构信息
     * 
     * @return 数据库结构描述
     */
    public String getDatabaseSchema() {
        StringBuilder schema = new StringBuilder();
        
        // 主要业务表
        String[] tables = {
            "erp_customer", "erp_production_order", "erp_material_master",
            "erp_equipment", "erp_supplier", "erp_inventory", "erp_warehouse",
            "erp_work_center", "erp_bom", "erp_process_route"
        };
        
        schema.append("数据库结构信息：\n");
        schema.append("数据库名：jxqerp\n\n");
        
        for (String table : tables) {
            try {
                schema.append("表名: ").append(table).append("\n");
                schema.append("字段信息:\n");
                
                String sql = "DESCRIBE " + table;
                List<Map<String, Object>> columns = jdbcTemplate.queryForList(sql);
                
                for (Map<String, Object> column : columns) {
                    String field = (String) column.get("Field");
                    String type = (String) column.get("Type");
                    String comment = (String) column.get("Comment");
                    
                    schema.append("  - ").append(field)
                          .append(" (").append(type).append(")");
                    
                    if (comment != null && !comment.isEmpty()) {
                        schema.append(" - ").append(comment);
                    }
                    schema.append("\n");
                }
                schema.append("\n");
                
            } catch (Exception e) {
                System.out.println("获取表 " + table + " 结构失败: " + e.getMessage());
            }
        }
        
        return schema.toString();
    }
    
    /**
     * 获取表的数据示例
     * 
     * @param tableName 表名
     * @param limit 限制条数
     * @return 数据示例
     */
    public String getTableSample(String tableName, int limit) {
        try {
            String sql = "SELECT * FROM " + tableName + " LIMIT " + limit;
            List<Map<String, Object>> samples = jdbcTemplate.queryForList(sql);
            
            StringBuilder sample = new StringBuilder();
            sample.append("表 ").append(tableName).append(" 数据示例:\n");
            
            for (Map<String, Object> row : samples) {
                sample.append(row.toString()).append("\n");
            }
            
            return sample.toString();
            
        } catch (Exception e) {
            return "获取表 " + tableName + " 数据示例失败: " + e.getMessage();
        }
    }
    
    /**
     * 获取简化的数据库结构（用于AI提示）
     * 
     * @return 简化的数据库结构
     */
    public String getSimplifiedSchema() {
        StringBuilder schema = new StringBuilder();
        
        schema.append("数据库表结构：\n\n");
        
        // 客户表
        schema.append("erp_customer (客户表):\n");
        schema.append("  - id: 客户ID\n");
        schema.append("  - customer_code: 客户编码\n");
        schema.append("  - customer_name: 客户名称\n");
        schema.append("  - customer_type: 客户类型\n");
        schema.append("  - overall_rating: 综合评级\n");
        schema.append("  - is_active: 是否启用\n\n");
        
        // 生产订单表
        schema.append("erp_production_order (生产订单表):\n");
        schema.append("  - id: 订单ID\n");
        schema.append("  - order_code: 订单编码\n");
        schema.append("  - order_name: 订单名称\n");
        schema.append("  - product_id: 产品ID\n");
        schema.append("  - planned_quantity: 计划数量\n");
        schema.append("  - completed_quantity: 完成数量\n");
        schema.append("  - status: 订单状态\n");
        schema.append("  - planned_start_date: 计划开始日期\n");
        schema.append("  - planned_end_date: 计划结束日期\n\n");
        
        // 物料主数据表
        schema.append("erp_material_master (物料主数据表):\n");
        schema.append("  - id: 物料ID\n");
        schema.append("  - material_code: 物料编码\n");
        schema.append("  - material_name: 物料名称\n");
        schema.append("  - material_spec: 物料规格\n");
        schema.append("  - min_stock: 最小库存\n");
        schema.append("  - max_stock: 最大库存\n");
        schema.append("  - safety_stock: 安全库存\n");
        schema.append("  - is_active: 是否启用\n\n");
        
        // 设备表
        schema.append("erp_equipment (设备表):\n");
        schema.append("  - id: 设备ID\n");
        schema.append("  - equipment_code: 设备编码\n");
        schema.append("  - equipment_name: 设备名称\n");
        schema.append("  - equipment_model: 设备型号\n");
        schema.append("  - status: 设备状态\n");
        schema.append("  - capacity: 产能\n\n");
        
        // 供应商表
        schema.append("erp_supplier (供应商表):\n");
        schema.append("  - id: 供应商ID\n");
        schema.append("  - supplier_code: 供应商编码\n");
        schema.append("  - supplier_name: 供应商名称\n");
        schema.append("  - overall_rating: 综合评级\n");
        schema.append("  - is_active: 是否启用\n\n");
        
        return schema.toString();
    }
    
    /**
     * 获取表统计信息
     * 
     * @return 表统计信息
     */
    public String getTableStatistics() {
        StringBuilder stats = new StringBuilder();
        
        String[] tables = {
            "erp_customer", "erp_production_order", "erp_material_master",
            "erp_equipment", "erp_supplier"
        };
        
        stats.append("数据库统计信息：\n\n");
        
        for (String table : tables) {
            try {
                String sql = "SELECT COUNT(*) as count FROM " + table;
                List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
                int count = ((Number) result.get(0).get("count")).intValue();
                stats.append(table).append(": ").append(count).append(" 条记录\n");
            } catch (Exception e) {
                stats.append(table).append(": 查询失败\n");
            }
        }
        
        return stats.toString();
    }
}
