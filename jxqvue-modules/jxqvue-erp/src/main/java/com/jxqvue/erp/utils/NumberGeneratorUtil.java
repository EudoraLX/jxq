package com.jxqvue.erp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jxqvue.erp.service.IErpNumberRuleService;

/**
 * 编号生成工具类
 * 支持根据业务类型动态生成编号，基于数据库中的编号规则配置
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Component
public class NumberGeneratorUtil {
    
    @Autowired
    private IErpNumberRuleService erpNumberRuleService;
    
    /**
     * 根据业务类型生成编号
     * 
     * @param businessType 业务类型（如：PRODUCTION_ORDER, MATERIAL, BOM, ROUTE等）
     * @return 生成的编号
     */
    public String generateNumber(String businessType) {
        return generateNumber(businessType, null);
    }
    
    /**
     * 根据业务类型和业务日期生成编号
     * 
     * @param businessType 业务类型（如：PRODUCTION_ORDER, MATERIAL, BOM, ROUTE等）
     * @param businessDate 业务日期（可选，用于特定日期格式的编号）
     * @return 生成的编号
     */
    public String generateNumber(String businessType, String businessDate) {
        try {
            // 根据业务类型动态生成编号
            String generatedNumber = erpNumberRuleService.generateNumberByBusinessType(businessType, businessDate);
            if (generatedNumber != null && !generatedNumber.isEmpty()) {
                // 更新编号规则的当前序号
                erpNumberRuleService.updateCurrentSequence(businessType);
                return generatedNumber;
            }
        } catch (Exception e) {
            // 如果编号生成失败，使用默认格式
            return generateDefaultNumber(businessType);
        }
        return generateDefaultNumber(businessType);
    }
    
    /**
     * 生成生产订单编号（向后兼容的方法）
     * 
     * @return 生成的订单编号
     * @deprecated 建议使用 generateNumber("PRODUCTION_ORDER") 替代
     */
    @Deprecated
    public String generateProductionOrderNumber() {
        return generateNumber("PRODUCTION_ORDER");
    }
    
    /**
     * 生成物料编号
     * 
     * @return 生成的物料编号
     */
    public String generateMaterialNumber() {
        return generateNumber("MATERIAL");
    }
    
    /**
     * 生成BOM编号
     * 
     * @return 生成的BOM编号
     */
    public String generateBomNumber() {
        return generateNumber("BOM");
    }
    
    /**
     * 生成工艺路线编号
     * 
     * @return 生成的工艺路线编号
     */
    public String generateRouteNumber() {
        return generateNumber("ROUTE");
    }
    
    /**
     * 生成工序编号
     * 
     * @return 生成的工序编号
     */
    public String generateProcessNumber() {
        return generateNumber("PROCESS");
    }
    
    /**
     * 生成工作中心编号
     * 
     * @return 生成的工作中心编号
     */
    public String generateWorkCenterNumber() {
        return generateNumber("WORK_CENTER");
    }
    
    /**
     * 生成设备编号
     * 
     * @return 生成的设备编号
     */
    public String generateEquipmentNumber() {
        return generateNumber("EQUIPMENT");
    }
    
    /**
     * 生成模具编号
     * 
     * @return 生成的模具编号
     */
    public String generateMoldNumber() {
        return generateNumber("MOLD");
    }
    
    /**
     * 生成注塑工艺编号
     * 
     * @return 生成的注塑工艺编号
     */
    public String generateInjectionProcessNumber() {
        return generateNumber("INJECTION_PROCESS");
    }
    
    /**
     * 生成生产线编号
     * 
     * @return 生成的生产线编号
     */
    public String generateProductionLineNumber() {
        return generateNumber("PRODUCTION_LINE");
    }
    
    /**
     * 生成排产计划编号
     * 
     * @return 生成的排产计划编号
     */
    public String generateScheduleNumber() {
        return generateNumber("SCHEDULE");
    }
    
    /**
     * 生成默认编号（当编号规则不可用时）
     * 
     * @param businessType 业务类型
     * @return 默认编号
     */
    private String generateDefaultNumber(String businessType) {
        long timestamp = System.currentTimeMillis();
        String prefix = getDefaultPrefix(businessType);
        return prefix + timestamp;
    }
    
    /**
     * 根据业务类型获取默认前缀
     * 
     * @param businessType 业务类型
     * @return 默认前缀
     */
    private String getDefaultPrefix(String businessType) {
        if (businessType == null) {
            return "NO";
        }
        
        switch (businessType.toUpperCase()) {
            case "PRODUCTION_ORDER":
                return "PO";
            case "MATERIAL":
                return "M";
            case "BOM":
                return "BOM";
            case "ROUTE":
                return "R";
            case "PROCESS":
                return "P";
            case "WORK_CENTER":
                return "WC";
            case "EQUIPMENT":
                return "EQ";
            case "MOLD":
                return "MD";
            case "INJECTION_PROCESS":
                return "IP";
            case "PRODUCTION_LINE":
                return "PL";
            case "SCHEDULE":
                return "SCH";
            case "PRODUCT_REPORT":
                return "PR";
            case "HANDOVER":
                return "HO";
            default:
                return businessType.substring(0, Math.min(2, businessType.length())).toUpperCase();
        }
    }
}
