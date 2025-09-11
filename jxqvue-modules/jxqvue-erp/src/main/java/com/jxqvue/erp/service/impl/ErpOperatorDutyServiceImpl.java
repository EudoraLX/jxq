package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpOperatorDutyMapper;
import com.jxqvue.erp.domain.ErpOperatorDuty;
import com.jxqvue.erp.service.IErpOperatorDutyService;

/**
 * 在岗离岗记录Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpOperatorDutyServiceImpl implements IErpOperatorDutyService 
{
    @Autowired
    private ErpOperatorDutyMapper erpOperatorDutyMapper;

    /**
     * 查询在岗离岗记录
     * 
     * @param id 在岗离岗记录主键
     * @return 在岗离岗记录
     */
    @Override
    public ErpOperatorDuty selectErpOperatorDutyById(Long id)
    {
        return erpOperatorDutyMapper.selectErpOperatorDutyById(id);
    }

    /**
     * 查询在岗离岗记录列表
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 在岗离岗记录
     */
    @Override
    public List<ErpOperatorDuty> selectErpOperatorDutyList(ErpOperatorDuty erpOperatorDuty)
    {
        return erpOperatorDutyMapper.selectErpOperatorDutyList(erpOperatorDuty);
    }

    /**
     * 新增在岗离岗记录
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 结果
     */
    @Override
    public int insertErpOperatorDuty(ErpOperatorDuty erpOperatorDuty)
    {
        erpOperatorDuty.setCreateTime(DateUtils.getNowDate());
        // 如果记录时间为空，设置为当前时间
        if (erpOperatorDuty.getRegisterTime() == null) {
            erpOperatorDuty.setRegisterTime(DateUtils.getNowDate());
        }
        return erpOperatorDutyMapper.insertErpOperatorDuty(erpOperatorDuty);
    }

    /**
     * 修改在岗离岗记录
     * 
     * @param erpOperatorDuty 在岗离岗记录
     * @return 结果
     */
    @Override
    public int updateErpOperatorDuty(ErpOperatorDuty erpOperatorDuty)
    {
        erpOperatorDuty.setUpdateTime(DateUtils.getNowDate());
        return erpOperatorDutyMapper.updateErpOperatorDuty(erpOperatorDuty);
    }

    /**
     * 批量删除在岗离岗记录
     * 
     * @param ids 需要删除的在岗离岗记录主键
     * @return 结果
     */
    @Override
    public int deleteErpOperatorDutyByIds(Long[] ids)
    {
        return erpOperatorDutyMapper.deleteErpOperatorDutyByIds(ids);
    }

    /**
     * 删除在岗离岗记录信息
     * 
     * @param id 在岗离岗记录主键
     * @return 结果
     */
    @Override
    public int deleteErpOperatorDutyById(Long id)
    {
        return erpOperatorDutyMapper.deleteErpOperatorDutyById(id);
    }

    /**
     * 获取用户最新上岗状态
     * 
     * @param operatorId 操作员ID
     * @return 最新上岗状态记录
     */
    @Override
    public ErpOperatorDuty selectLatestOperatorDutyByOperatorId(Long operatorId)
    {
        return erpOperatorDutyMapper.selectLatestOperatorDutyByOperatorId(operatorId);
    }

    /**
     * 根据工序编号查询工序信息
     * 
     * @param processCode 工序编号
     * @return 工序信息
     */
    @Override
    public ErpOperatorDuty getProcessByCode(String processCode)
    {
        return erpOperatorDutyMapper.selectProcessByCode(processCode);
    }

    /**
     * 切换上岗状态
     * 
     * @param erpOperatorDuty 上岗记录信息
     * @param operatorId 操作员ID
     * @return 结果
     */
    @Override
    public int toggleDutyStatus(ErpOperatorDuty erpOperatorDuty, Long operatorId)
    {
        // 设置操作员ID
        erpOperatorDuty.setOperatorId(operatorId);
        // 设置记录时间为当前时间
        erpOperatorDuty.setRegisterTime(DateUtils.getNowDate());
        // 设置创建时间
        erpOperatorDuty.setCreateTime(DateUtils.getNowDate());
        
        return erpOperatorDutyMapper.insertErpOperatorDuty(erpOperatorDuty);
    }
}
