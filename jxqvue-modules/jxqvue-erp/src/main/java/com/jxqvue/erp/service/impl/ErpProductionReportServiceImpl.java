package com.jxqvue.erp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionReportMapper;
import com.jxqvue.erp.domain.ErpProductionReport;
import com.jxqvue.erp.service.IErpProductionReportService;
import com.jxqvue.erp.service.IErpNumberRuleService;
import com.jxqvue.erp.utils.NumberGeneratorUtil;

/**
 * 生产报工单Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpProductionReportServiceImpl implements IErpProductionReportService 
{
    @Autowired
    private ErpProductionReportMapper erpProductionReportMapper;
    
    @Autowired
    private NumberGeneratorUtil numberGeneratorUtil;
    
    @Autowired
    private IErpNumberRuleService erpNumberRuleService;

    /**
     * 查询生产报工单
     * 
     * @param id 生产报工单主键
     * @return 生产报工单
     */
    @Override
    public ErpProductionReport selectErpProductionReportById(Long id)
    {
        return erpProductionReportMapper.selectErpProductionReportById(id);
    }

    /**
     * 查询生产报工单列表
     * 
     * @param erpProductionReport 生产报工单
     * @return 生产报工单
     */
    @Override
    public List<ErpProductionReport> selectErpProductionReportList(ErpProductionReport erpProductionReport)
    {
        return erpProductionReportMapper.selectErpProductionReportList(erpProductionReport);
    }

    /**
     * 新增生产报工单
     * 
     * @param erpProductionReport 生产报工单
     * @return 结果
     */
    @Override
    public int insertErpProductionReport(ErpProductionReport erpProductionReport)
    {
        erpProductionReport.setCreateTime(DateUtils.getNowDate());
        // 自动生成报工单编号
        if (erpProductionReport.getReportCode() == null || erpProductionReport.getReportCode().isEmpty()) {
            String reportCode = numberGeneratorUtil.generateNumber("PRODUCT_REPORT");
            erpProductionReport.setReportCode(reportCode);
        }
        // 根据编号规则设置审批状态
        setApprovalStatusByNumberRule(erpProductionReport);
        // 计算报工数量
        calculateActualQuantity(erpProductionReport);
        // 计算合格率
        calculateQualityRate(erpProductionReport);
        return erpProductionReportMapper.insertErpProductionReport(erpProductionReport);
    }

    /**
     * 修改生产报工单
     * 
     * @param erpProductionReport 生产报工单
     * @return 结果
     */
    @Override
    public int updateErpProductionReport(ErpProductionReport erpProductionReport)
    {
        erpProductionReport.setUpdateTime(DateUtils.getNowDate());
        // 计算报工数量
        calculateActualQuantity(erpProductionReport);
        // 计算合格率
        calculateQualityRate(erpProductionReport);
        return erpProductionReportMapper.updateErpProductionReport(erpProductionReport);
    }

    /**
     * 批量删除生产报工单
     * 
     * @param ids 需要删除的生产报工单主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionReportByIds(Long[] ids)
    {
        return erpProductionReportMapper.deleteErpProductionReportByIds(ids);
    }

    /**
     * 删除生产报工单信息
     * 
     * @param id 生产报工单主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionReportById(Long id)
    {
        return erpProductionReportMapper.deleteErpProductionReportById(id);
    }

    /**
     * 计算报工数量
     * 公式：合格数量+不良数量+报废数量+返工数量
     */
    private void calculateActualQuantity(ErpProductionReport erpProductionReport) {
        BigDecimal qualifiedQuantity = erpProductionReport.getQualifiedQuantity() != null ? erpProductionReport.getQualifiedQuantity() : BigDecimal.ZERO;
        BigDecimal defectiveQuantity = erpProductionReport.getDefectiveQuantity() != null ? erpProductionReport.getDefectiveQuantity() : BigDecimal.ZERO;
        BigDecimal scrapQuantity = erpProductionReport.getScrapQuantity() != null ? erpProductionReport.getScrapQuantity() : BigDecimal.ZERO;
        BigDecimal reworkQuantity = erpProductionReport.getReworkQuantity() != null ? erpProductionReport.getReworkQuantity() : BigDecimal.ZERO;
        
        BigDecimal actualQuantity = qualifiedQuantity.add(defectiveQuantity).add(scrapQuantity).add(reworkQuantity);
        erpProductionReport.setActualQuantity(actualQuantity);
    }

    /**
     * 计算合格率
     * 公式：合格数量/报工数量
     */
    private void calculateQualityRate(ErpProductionReport erpProductionReport) {
        BigDecimal qualifiedQuantity = erpProductionReport.getQualifiedQuantity() != null ? erpProductionReport.getQualifiedQuantity() : BigDecimal.ZERO;
        BigDecimal actualQuantity = erpProductionReport.getActualQuantity();
        
        if (actualQuantity != null && actualQuantity.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal qualityRate = qualifiedQuantity.divide(actualQuantity, 4, java.math.RoundingMode.HALF_UP);
            erpProductionReport.setQualityRate(qualityRate);
        } else {
            erpProductionReport.setQualityRate(BigDecimal.ZERO);
        }
    }

    /**
     * 根据编号规则设置审批状态
     * 
     * @param erpProductionReport 生产报工单
     */
    private void setApprovalStatusByNumberRule(ErpProductionReport erpProductionReport) {
        try {
            // 查询PRODUCT_REPORT编号规则的need_approval字段
            String needApproval = erpNumberRuleService.getNeedApprovalByBusinessType("PRODUCT_REPORT");
            
            if ("0".equals(needApproval)) {
                // 如果need_approval为"0"，审批状态设置为"2"（已完成）
                erpProductionReport.setApprovalStatus("2");
            } else {
                // 否则审批状态设置为"0"（草稿）
                erpProductionReport.setApprovalStatus("0");
            }
        } catch (Exception e) {
            // 如果查询失败，默认设置为"0"（草稿）
            erpProductionReport.setApprovalStatus("0");
        }
    }
}
