package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionOperationRecordMapper;
import com.jxqvue.erp.domain.ErpProductionOperationRecord;
import com.jxqvue.erp.service.IErpProductionOperationRecordService;

/**
 * 生产操作记录Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class ErpProductionOperationRecordServiceImpl implements IErpProductionOperationRecordService 
{
    @Autowired
    private ErpProductionOperationRecordMapper erpProductionOperationRecordMapper;

    /**
     * 查询生产操作记录
     * 
     * @param id 生产操作记录主键
     * @return 生产操作记录
     */
    @Override
    public ErpProductionOperationRecord selectErpProductionOperationRecordById(Long id)
    {
        return erpProductionOperationRecordMapper.selectErpProductionOperationRecordById(id);
    }

    /**
     * 查询生产操作记录列表
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 生产操作记录
     */
    @Override
    public List<ErpProductionOperationRecord> selectErpProductionOperationRecordList(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        return erpProductionOperationRecordMapper.selectErpProductionOperationRecordList(erpProductionOperationRecord);
    }

    /**
     * 新增生产操作记录
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 结果
     */
    @Override
    public int insertErpProductionOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        erpProductionOperationRecord.setCreateTime(DateUtils.getNowDate());
        return erpProductionOperationRecordMapper.insertErpProductionOperationRecord(erpProductionOperationRecord);
    }

    /**
     * 修改生产操作记录
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 结果
     */
    @Override
    public int updateErpProductionOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        erpProductionOperationRecord.setUpdateTime(DateUtils.getNowDate());
        return erpProductionOperationRecordMapper.updateErpProductionOperationRecord(erpProductionOperationRecord);
    }

    /**
     * 批量删除生产操作记录
     * 
     * @param ids 需要删除的生产操作记录主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOperationRecordByIds(Long[] ids)
    {
        return erpProductionOperationRecordMapper.deleteErpProductionOperationRecordByIds(ids);
    }

    /**
     * 删除生产操作记录信息
     * 
     * @param id 生产操作记录主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOperationRecordById(Long id)
    {
        return erpProductionOperationRecordMapper.deleteErpProductionOperationRecordById(id);
    }

    /**
     * 根据操作员ID获取最新的工序ID
     * 
     * @param operatorId 操作员ID
     * @return 工序ID
     */
    @Override
    public Long getLatestProcessIdByOperatorId(Long operatorId)
    {
        return erpProductionOperationRecordMapper.getLatestProcessIdByOperatorId(operatorId);
    }

    /**
     * 根据生产订单ID和工序ID获取工作中心、设备、模具信息
     * 
     * @param productionOrderId 生产订单ID
     * @param processId 工序ID
     * @return 生产操作记录
     */
    @Override
    public ErpProductionOperationRecord getWorkCenterInfoByOrderAndProcess(Long productionOrderId, Long processId)
    {
        return erpProductionOperationRecordMapper.getWorkCenterInfoByOrderAndProcess(productionOrderId, processId);
    }

    /**
     * 根据生产订单ID获取工艺路线明细信息
     * 
     * @param productionOrderId 生产订单ID
     * @return 工艺路线明细列表
     */
    @Override
    public List<ErpProductionOperationRecord> getProcessRouteDetailsByOrderId(Long productionOrderId)
    {
        return erpProductionOperationRecordMapper.getProcessRouteDetailsByOrderId(productionOrderId);
    }

    /**
     * 获取最新操作记录
     * 
     * @param erpProductionOperationRecord 查询条件
     * @return 最新操作记录
     */
    @Override
    public ErpProductionOperationRecord getLatestOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord)
    {
        return erpProductionOperationRecordMapper.getLatestOperationRecord(erpProductionOperationRecord);
    }

}
