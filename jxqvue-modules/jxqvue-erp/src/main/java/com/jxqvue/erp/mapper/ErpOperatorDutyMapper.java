package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpOperatorDuty;

/**
 * 在岗离岗记录Mapper接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface ErpOperatorDutyMapper 
{
    /**
     * 查询在岗离岗记录
     * 
     * @param id 在岗离岗记录主键
     * @return 在岗离岗记录
     */
    public ErpOperatorDuty selectErpOperatorDutyById(Long id);

    /**
     * 查询在岗离岗记录列表
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 在岗离岗记录集合
     */
    public List<ErpOperatorDuty> selectErpOperatorDutyList(ErpOperatorDuty erpOperatorDuty);

    /**
     * 新增在岗离岗记录
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 结果
     */
    public int insertErpOperatorDuty(ErpOperatorDuty erpOperatorDuty);

    /**
     * 修改在岗离岗记录
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 结果
     */
    public int updateErpOperatorDuty(ErpOperatorDuty erpOperatorDuty);

    /**
     * 删除在岗离岗记录
     * 
     * @param id 在岗离岗记录主键
     * @return 结果
     */
    public int deleteErpOperatorDutyById(Long id);

    /**
     * 批量删除在岗离岗记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpOperatorDutyByIds(Long[] ids);

    /**
     * 获取用户最新上岗状态
     * 
     * @param operatorId 操作员ID
     * @return 最新上岗状态记录
     */
    public ErpOperatorDuty selectLatestOperatorDutyByOperatorId(Long operatorId);

    /**
     * 根据工序编号查询工序信息
     * 
     * @param processCode 工序编号
     * @return 工序信息
     */
    public ErpOperatorDuty selectProcessByCode(String processCode);
}
