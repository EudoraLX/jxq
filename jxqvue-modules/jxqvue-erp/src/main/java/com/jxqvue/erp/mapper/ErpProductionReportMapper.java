package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionReport;

/**
 * 生产报工单Mapper接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface ErpProductionReportMapper 
{
    /**
     * 查询生产报工单
     * 
     * @param id 生产报工单主键
     * @return 生产报工单
     */
    public ErpProductionReport selectErpProductionReportById(Long id);

    /**
     * 查询生产报工单列表
     * 
     * @param erpProductionReport 生产报工单
     * @return 生产报工单集合
     */
    public List<ErpProductionReport> selectErpProductionReportList(ErpProductionReport erpProductionReport);

    /**
     * 新增生产报工单
     * 
     * @param erpProductionReport 生产报工单
     * @return 结果
     */
    public int insertErpProductionReport(ErpProductionReport erpProductionReport);

    /**
     * 修改生产报工单
     * 
     * @param erpProductionReport 生产报工单
     * @return 结果
     */
    public int updateErpProductionReport(ErpProductionReport erpProductionReport);

    /**
     * 删除生产报工单
     * 
     * @param id 生产报工单主键
     * @return 结果
     */
    public int deleteErpProductionReportById(Long id);

    /**
     * 批量删除生产报工单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionReportByIds(Long[] ids);
}
