package com.jxqvue.erp.service.impl;

import java.math.BigDecimal;
import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProcessHandoverMapper;
import com.jxqvue.erp.domain.ErpProcessHandover;
import com.jxqvue.erp.service.IErpProcessHandoverService;
import com.jxqvue.erp.service.IErpNumberRuleService;
import com.jxqvue.erp.utils.NumberGeneratorUtil;

/**
 * 工序交接单Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpProcessHandoverServiceImpl implements IErpProcessHandoverService 
{
    @Autowired
    private ErpProcessHandoverMapper erpProcessHandoverMapper;
    
    @Autowired
    private NumberGeneratorUtil numberGeneratorUtil;
    
    @Autowired
    private IErpNumberRuleService erpNumberRuleService;

    /**
     * 查询工序交接单
     * 
     * @param id 工序交接单主键
     * @return 工序交接单
     */
    @Override
    public ErpProcessHandover selectErpProcessHandoverById(Long id)
    {
        return erpProcessHandoverMapper.selectErpProcessHandoverById(id);
    }

    /**
     * 查询工序交接单列表
     * 
     * @param erpProcessHandover 工序交接单
     * @return 工序交接单
     */
    @Override
    public List<ErpProcessHandover> selectErpProcessHandoverList(ErpProcessHandover erpProcessHandover)
    {
        return erpProcessHandoverMapper.selectErpProcessHandoverList(erpProcessHandover);
    }

    /**
     * 新增工序交接单
     * 
     * @param erpProcessHandover 工序交接单
     * @return 结果
     */
    @Override
    public int insertErpProcessHandover(ErpProcessHandover erpProcessHandover)
    {
        erpProcessHandover.setCreateTime(DateUtils.getNowDate());
        // 自动生成交接单编号
        if (erpProcessHandover.getHandoverCode() == null || erpProcessHandover.getHandoverCode().isEmpty()) {
            String handoverCode = numberGeneratorUtil.generateNumber("HANDOVER");
            erpProcessHandover.setHandoverCode(handoverCode);
        }
        // 根据编号规则设置审批状态
        setApprovalStatusByNumberRule(erpProcessHandover);
        // 计算交接数量
        calculateHandoverQuantity(erpProcessHandover);
        return erpProcessHandoverMapper.insertErpProcessHandover(erpProcessHandover);
    }

    /**
     * 修改工序交接单
     * 
     * @param erpProcessHandover 工序交接单
     * @return 结果
     */
    @Override
    public int updateErpProcessHandover(ErpProcessHandover erpProcessHandover)
    {
        erpProcessHandover.setUpdateTime(DateUtils.getNowDate());
        // 计算交接数量
        calculateHandoverQuantity(erpProcessHandover);
        return erpProcessHandoverMapper.updateErpProcessHandover(erpProcessHandover);
    }

    /**
     * 批量删除工序交接单
     * 
     * @param ids 需要删除的工序交接单主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessHandoverByIds(Long[] ids)
    {
        return erpProcessHandoverMapper.deleteErpProcessHandoverByIds(ids);
    }

    /**
     * 删除工序交接单信息
     * 
     * @param id 工序交接单主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessHandoverById(Long id)
    {
        return erpProcessHandoverMapper.deleteErpProcessHandoverById(id);
    }

    /**
     * 计算交接数量
     * 
     * @param erpProcessHandover 工序交接单
     */
    private void calculateHandoverQuantity(ErpProcessHandover erpProcessHandover) {
        BigDecimal qualifiedQuantity = erpProcessHandover.getQualifiedQuantity() != null ? erpProcessHandover.getQualifiedQuantity() : BigDecimal.ZERO;
        BigDecimal defectiveQuantity = erpProcessHandover.getDefectiveQuantity() != null ? erpProcessHandover.getDefectiveQuantity() : BigDecimal.ZERO;
        
        BigDecimal handoverQuantity = qualifiedQuantity.add(defectiveQuantity);
        erpProcessHandover.setHandoverQuantity(handoverQuantity);
    }

    /**
     * 根据编号规则设置审批状态
     * 
     * @param erpProcessHandover 工序交接单
     */
    private void setApprovalStatusByNumberRule(ErpProcessHandover erpProcessHandover) {
        try {
            // 查询HANDOVER编号规则的need_approval字段
            String needApproval = erpNumberRuleService.getNeedApprovalByBusinessType("HANDOVER");
            
            if ("0".equals(needApproval)) {
                // 如果need_approval为"0"，审批状态设置为"2"（已完成）
                erpProcessHandover.setApprovalStatus("2");
            } else {
                // 否则审批状态设置为"0"（草稿）
                erpProcessHandover.setApprovalStatus("0");
            }
        } catch (Exception e) {
            // 如果查询失败，默认设置为"0"（草稿）
            erpProcessHandover.setApprovalStatus("0");
        }
    }
}
