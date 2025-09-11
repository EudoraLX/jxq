package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpNumberRule;

/**
 * 编号规则Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public interface ErpNumberRuleMapper 
{
    /**
     * 查询编号规则
     * 
     * @param ruleId 编号规则主键
     * @return 编号规则
     */
    public ErpNumberRule selectErpNumberRuleByRuleId(Long ruleId);

    /**
     * 查询编号规则列表
     * 
     * @param erpNumberRule 编号规则
     * @return 编号规则集合
     */
    public List<ErpNumberRule> selectErpNumberRuleList(ErpNumberRule erpNumberRule);

    /**
     * 新增编号规则
     * 
     * @param erpNumberRule 编号规则
     * @return 结果
     */
    public int insertErpNumberRule(ErpNumberRule erpNumberRule);

    /**
     * 修改编号规则
     * 
     * @param erpNumberRule 编号规则
     * @return 结果
     */
    public int updateErpNumberRule(ErpNumberRule erpNumberRule);

    /**
     * 删除编号规则
     * 
     * @param ruleId 编号规则主键
     * @return 结果
     */
    public int deleteErpNumberRuleByRuleId(Long ruleId);

    /**
     * 批量删除编号规则
     * 
     * @param ruleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpNumberRuleByRuleIds(Long[] ruleIds);

    /**
     * 根据业务类型生成编号
     *
     * @param businessType 业务类型
     * @param businessDate 业务日期
     * @return 生成的编号
     */
    public String generateNumberByBusinessType(String businessType, String businessDate);

    /**
     * 更新编号规则的当前序号
     *
     * @param businessType 业务类型
     * @return 结果
     */
    public int updateCurrentSequence(String businessType);

    /**
     * 根据业务类型获取need_approval字段值
     *
     * @param businessType 业务类型
     * @return need_approval字段值
     */
    public String getNeedApprovalByBusinessType(String businessType);
}
