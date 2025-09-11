package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpNumberRuleMapper;
import com.jxqvue.erp.domain.ErpNumberRule;
import com.jxqvue.erp.service.IErpNumberRuleService;

/**
 * 编号规则Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Service
public class ErpNumberRuleServiceImpl implements IErpNumberRuleService 
{
    @Autowired
    private ErpNumberRuleMapper erpNumberRuleMapper;

    /**
     * 查询编号规则
     * 
     * @param ruleId 编号规则主键
     * @return 编号规则
     */
    @Override
    public ErpNumberRule selectErpNumberRuleByRuleId(Long ruleId)
    {
        return erpNumberRuleMapper.selectErpNumberRuleByRuleId(ruleId);
    }

    /**
     * 查询编号规则列表
     * 
     * @param erpNumberRule 编号规则
     * @return 编号规则
     */
    @Override
    public List<ErpNumberRule> selectErpNumberRuleList(ErpNumberRule erpNumberRule)
    {
        return erpNumberRuleMapper.selectErpNumberRuleList(erpNumberRule);
    }

    /**
     * 新增编号规则
     * 
     * @param erpNumberRule 编号规则
     * @return 结果
     */
    @Override
    public int insertErpNumberRule(ErpNumberRule erpNumberRule)
    {
        erpNumberRule.setCreateTime(DateUtils.getNowDate());
        return erpNumberRuleMapper.insertErpNumberRule(erpNumberRule);
    }

    /**
     * 修改编号规则
     * 
     * @param erpNumberRule 编号规则
     * @return 结果
     */
    @Override
    public int updateErpNumberRule(ErpNumberRule erpNumberRule)
    {
        erpNumberRule.setUpdateTime(DateUtils.getNowDate());
        return erpNumberRuleMapper.updateErpNumberRule(erpNumberRule);
    }

    /**
     * 批量删除编号规则
     * 
     * @param ruleIds 需要删除的编号规则主键
     * @return 结果
     */
    @Override
    public int deleteErpNumberRuleByRuleIds(Long[] ruleIds)
    {
        return erpNumberRuleMapper.deleteErpNumberRuleByRuleIds(ruleIds);
    }

    /**
     * 删除编号规则信息
     * 
     * @param ruleId 编号规则主键
     * @return 结果
     */
    @Override
    public int deleteErpNumberRuleByRuleId(Long ruleId)
    {
        return erpNumberRuleMapper.deleteErpNumberRuleByRuleId(ruleId);
    }

    /**
     * 根据业务类型生成编号
     *
     * @param businessType 业务类型
     * @param businessDate 业务日期
     * @return 生成的编号
     */
    @Override
    public String generateNumberByBusinessType(String businessType, String businessDate)
    {
        return erpNumberRuleMapper.generateNumberByBusinessType(businessType, businessDate);
    }

    /**
     * 更新编号规则的当前序号
     *
     * @param businessType 业务类型
     * @return 结果
     */
    @Override
    public int updateCurrentSequence(String businessType)
    {
        return erpNumberRuleMapper.updateCurrentSequence(businessType);
    }

    /**
     * 根据业务类型获取need_approval字段值
     *
     * @param businessType 业务类型
     * @return need_approval字段值
     */
    @Override
    public String getNeedApprovalByBusinessType(String businessType)
    {
        return erpNumberRuleMapper.getNeedApprovalByBusinessType(businessType);
    }

}
