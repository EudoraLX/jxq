package com.jxqvue.erp.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProcessMonitoringMapper;
import com.jxqvue.erp.domain.ErpProcessMonitoring;
import com.jxqvue.erp.service.IErpProcessMonitoringService;

/**
 * 工艺参数监控Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpProcessMonitoringServiceImpl implements IErpProcessMonitoringService 
{
    @Autowired
    private ErpProcessMonitoringMapper erpProcessMonitoringMapper;

    /**
     * 查询工艺参数监控
     * 
     * @param id 工艺参数监控主键
     * @return 工艺参数监控
     */
    @Override
    public ErpProcessMonitoring selectErpProcessMonitoringById(Long id)
    {
        return erpProcessMonitoringMapper.selectErpProcessMonitoringById(id);
    }

    /**
     * 查询工艺参数监控列表
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 工艺参数监控
     */
    @Override
    public List<ErpProcessMonitoring> selectErpProcessMonitoringList(ErpProcessMonitoring erpProcessMonitoring)
    {
        return erpProcessMonitoringMapper.selectErpProcessMonitoringList(erpProcessMonitoring);
    }

    /**
     * 新增工艺参数监控
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 结果
     */
    @Override
    public int insertErpProcessMonitoring(ErpProcessMonitoring erpProcessMonitoring)
    {
        // 设置默认值
        if (erpProcessMonitoring.getMonitoringTime() == null) {
            erpProcessMonitoring.setMonitoringTime(new Date());
        }
        if (erpProcessMonitoring.getInspectorId() == null) {
            erpProcessMonitoring.setInspectorId(SecurityUtils.getUserId());
        }
        
        // 计算偏差和偏差率
        erpProcessMonitoring = calculateDeviation(erpProcessMonitoring);
        
        erpProcessMonitoring.setCreateTime(DateUtils.getNowDate());
        return erpProcessMonitoringMapper.insertErpProcessMonitoring(erpProcessMonitoring);
    }

    /**
     * 修改工艺参数监控
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 结果
     */
    @Override
    public int updateErpProcessMonitoring(ErpProcessMonitoring erpProcessMonitoring)
    {
        // 计算偏差和偏差率
        erpProcessMonitoring = calculateDeviation(erpProcessMonitoring);
        
        erpProcessMonitoring.setUpdateTime(DateUtils.getNowDate());
        return erpProcessMonitoringMapper.updateErpProcessMonitoring(erpProcessMonitoring);
    }

    /**
     * 批量删除工艺参数监控
     * 
     * @param ids 需要删除的工艺参数监控主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessMonitoringByIds(Long[] ids)
    {
        return erpProcessMonitoringMapper.deleteErpProcessMonitoringByIds(ids);
    }

    /**
     * 删除工艺参数监控信息
     * 
     * @param id 工艺参数监控主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessMonitoringById(Long id)
    {
        return erpProcessMonitoringMapper.deleteErpProcessMonitoringById(id);
    }

    /**
     * 根据工艺ID获取工艺参数列表
     * 
     * @param processId 工艺ID
     * @return 工艺参数列表
     */
    @Override
    public List<ErpProcessMonitoring> getProcessParametersByProcessId(Long processId)
    {
        return erpProcessMonitoringMapper.getProcessParametersByProcessId(processId);
    }

    /**
     * 根据生产订单ID和工艺ID获取工作中心信息
     * 
     * @param productionOrderId 生产订单ID
     * @param processId 工艺ID
     * @return 工作中心信息
     */
    @Override
    public ErpProcessMonitoring getWorkCenterInfoByOrderAndProcess(Long productionOrderId, Long processId)
    {
        return erpProcessMonitoringMapper.getWorkCenterInfoByOrderAndProcess(productionOrderId, processId);
    }

    /**
     * 计算偏差和偏差率
     * 
     * @param erpProcessMonitoring 工艺参数监控
     * @return 计算结果
     */
    @Override
    public ErpProcessMonitoring calculateDeviation(ErpProcessMonitoring erpProcessMonitoring)
    {
        if (erpProcessMonitoring.getActualValue() != null && erpProcessMonitoring.getStandardValue() != null) {
            // 计算偏差
            BigDecimal deviation = erpProcessMonitoring.getStandardValue().subtract(erpProcessMonitoring.getActualValue());
            erpProcessMonitoring.setDeviation(deviation);
            
            // 计算偏差率
            if (erpProcessMonitoring.getStandardValue().compareTo(BigDecimal.ZERO) != 0) {
                BigDecimal deviationRate = deviation.divide(erpProcessMonitoring.getStandardValue(), 4, RoundingMode.HALF_UP);
                erpProcessMonitoring.setDeviationRate(deviationRate);
            }
            
            // 判断是否正常
            boolean isNormal = true;
            if (erpProcessMonitoring.getMinValue() != null && erpProcessMonitoring.getMaxValue() != null) {
                isNormal = erpProcessMonitoring.getActualValue().compareTo(erpProcessMonitoring.getMinValue()) >= 0 
                        && erpProcessMonitoring.getActualValue().compareTo(erpProcessMonitoring.getMaxValue()) <= 0;
            }
            erpProcessMonitoring.setIsNormal(isNormal ? "1" : "0");
            
            // 如果异常，设置报警等级和报警信息
            if (!isNormal) {
                BigDecimal deviationRate = erpProcessMonitoring.getDeviationRate();
                if (deviationRate != null) {
                    BigDecimal absDeviationRate = deviationRate.abs();
                    BigDecimal tenPercent = new BigDecimal("0.1");
                    
                    if (absDeviationRate.compareTo(tenPercent) <= 0) {
                        erpProcessMonitoring.setAlarmLevel("1");
                    } else {
                        erpProcessMonitoring.setAlarmLevel("2");
                    }
                    
                    // 生成报警信息
                    String alarmMessage = String.format("%s - %s 于 %s 测试值为 %s，与标准值 %s 偏差达 %s。请注意！",
                            erpProcessMonitoring.getParameterCode(),
                            erpProcessMonitoring.getParameterName(),
                            DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", erpProcessMonitoring.getMonitoringTime()),
                            erpProcessMonitoring.getActualValue(),
                            erpProcessMonitoring.getStandardValue(),
                            absDeviationRate.multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP) + "%");
                    erpProcessMonitoring.setAlarmMessage(alarmMessage);
                }
            } else {
                erpProcessMonitoring.setAlarmLevel("0");
                erpProcessMonitoring.setAlarmMessage(null);
            }
        }
        
        return erpProcessMonitoring;
    }

    /**
     * 根据生产订单ID获取工艺路线明细信息
     * 
     * @param productionOrderId 生产订单ID
     * @return 工艺路线明细列表
     */
    @Override
    public List<ErpProcessMonitoring> getProcessRouteDetailsByOrderId(Long productionOrderId)
    {
        return erpProcessMonitoringMapper.getProcessRouteDetailsByOrderId(productionOrderId);
    }
}
