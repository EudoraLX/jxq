package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionOperationRecord;

/**
 * 生产操作记录Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IErpProductionOperationRecordService 
{
    /**
     * 查询生产操作记录
     * 
     * @param id 生产操作记录主键
     * @return 生产操作记录
     */
    public ErpProductionOperationRecord selectErpProductionOperationRecordById(Long id);

    /**
     * 查询生产操作记录列表
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 生产操作记录集合
     */
    public List<ErpProductionOperationRecord> selectErpProductionOperationRecordList(ErpProductionOperationRecord erpProductionOperationRecord);

    /**
     * 新增生产操作记录
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 结果
     */
    public int insertErpProductionOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord);

    /**
     * 修改生产操作记录
     * 
     * @param erpProductionOperationRecord 生产操作记录
     * @return 结果
     */
    public int updateErpProductionOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord);

    /**
     * 批量删除生产操作记录
     * 
     * @param ids 需要删除的生产操作记录主键集合
     * @return 结果
     */
    public int deleteErpProductionOperationRecordByIds(Long[] ids);

    /**
     * 删除生产操作记录信息
     * 
     * @param id 生产操作记录主键
     * @return 结果
     */
    public int deleteErpProductionOperationRecordById(Long id);

    /**
     * 根据操作员ID获取最新的工序ID
     * 
     * @param operatorId 操作员ID
     * @return 工序ID
     */
    public Long getLatestProcessIdByOperatorId(Long operatorId);

    /**
     * 根据生产订单ID和工序ID获取工作中心、设备、模具信息
     * 
     * @param productionOrderId 生产订单ID
     * @param processId 工序ID
     * @return 生产操作记录
     */
    public ErpProductionOperationRecord getWorkCenterInfoByOrderAndProcess(Long productionOrderId, Long processId);

    /**
     * 根据生产订单ID获取工艺路线明细信息
     * 
     * @param productionOrderId 生产订单ID
     * @return 工艺路线明细列表
     */
    public List<ErpProductionOperationRecord> getProcessRouteDetailsByOrderId(Long productionOrderId);

    /**
     * 获取最新操作记录
     * 
     * @param erpProductionOperationRecord 查询条件
     * @return 最新操作记录
     */
    public ErpProductionOperationRecord getLatestOperationRecord(ErpProductionOperationRecord erpProductionOperationRecord);
}
