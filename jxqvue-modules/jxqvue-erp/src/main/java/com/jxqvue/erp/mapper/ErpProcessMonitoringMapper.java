package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessMonitoring;

/**
 * 工艺参数监控Mapper接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface ErpProcessMonitoringMapper 
{
    /**
     * 查询工艺参数监控
     * 
     * @param id 工艺参数监控主键
     * @return 工艺参数监控
     */
    public ErpProcessMonitoring selectErpProcessMonitoringById(Long id);

    /**
     * 查询工艺参数监控列表
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 工艺参数监控集合
     */
    public List<ErpProcessMonitoring> selectErpProcessMonitoringList(ErpProcessMonitoring erpProcessMonitoring);

    /**
     * 新增工艺参数监控
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 结果
     */
    public int insertErpProcessMonitoring(ErpProcessMonitoring erpProcessMonitoring);

    /**
     * 修改工艺参数监控
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 结果
     */
    public int updateErpProcessMonitoring(ErpProcessMonitoring erpProcessMonitoring);

    /**
     * 删除工艺参数监控
     * 
     * @param id 工艺参数监控主键
     * @return 结果
     */
    public int deleteErpProcessMonitoringById(Long id);

    /**
     * 批量删除工艺参数监控
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessMonitoringByIds(Long[] ids);

    /**
     * 根据工艺ID获取工艺参数列表
     * 
     * @param processId 工艺ID
     * @return 工艺参数列表
     */
    public List<ErpProcessMonitoring> getProcessParametersByProcessId(Long processId);

    /**
     * 根据生产订单ID和工艺ID获取工作中心信息
     * 
     * @param productionOrderId 生产订单ID
     * @param processId 工艺ID
     * @return 工作中心信息
     */
    public ErpProcessMonitoring getWorkCenterInfoByOrderAndProcess(Long productionOrderId, Long processId);

    /**
     * 根据生产订单ID获取工艺路线明细信息
     * 
     * @param productionOrderId 生产订单ID
     * @return 工艺路线明细列表
     */
    public List<ErpProcessMonitoring> getProcessRouteDetailsByOrderId(Long productionOrderId);
}
