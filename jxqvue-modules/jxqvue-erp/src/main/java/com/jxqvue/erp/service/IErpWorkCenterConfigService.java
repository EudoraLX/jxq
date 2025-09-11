package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpWorkCenterConfig;

/**
 * 工作中心配置Service接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface IErpWorkCenterConfigService 
{
    /**
     * 查询工作中心配置
     * 
     * @param id 工作中心配置主键
     * @return 工作中心配置
     */
    public ErpWorkCenterConfig selectErpWorkCenterConfigById(Long id);

    /**
     * 查询工作中心配置列表
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 工作中心配置集合
     */
    public List<ErpWorkCenterConfig> selectErpWorkCenterConfigList(ErpWorkCenterConfig erpWorkCenterConfig);

    /**
     * 根据工作中心ID查询配置列表
     * 
     * @param workCenterId 工作中心ID
     * @return 工作中心配置集合
     */
    public List<ErpWorkCenterConfig> selectErpWorkCenterConfigByWorkCenterId(Long workCenterId);

    /**
     * 新增工作中心配置
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 结果
     */
    public int insertErpWorkCenterConfig(ErpWorkCenterConfig erpWorkCenterConfig);

    /**
     * 修改工作中心配置
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 结果
     */
    public int updateErpWorkCenterConfig(ErpWorkCenterConfig erpWorkCenterConfig);

    /**
     * 批量删除工作中心配置
     * 
     * @param ids 需要删除的工作中心配置主键集合
     * @return 结果
     */
    public int deleteErpWorkCenterConfigByIds(Long[] ids);

    /**
     * 删除工作中心配置信息
     * 
     * @param id 工作中心配置主键
     * @return 结果
     */
    public int deleteErpWorkCenterConfigById(Long id);

    /**
     * 根据工作中心ID删除配置
     * 
     * @param workCenterId 工作中心ID
     * @return 结果
     */
    public int deleteErpWorkCenterConfigByWorkCenterId(Long workCenterId);

    /**
     * 查询工作中心的首选配置
     * 
     * @param workCenterId 工作中心ID
     * @return 工作中心配置
     */
    public ErpWorkCenterConfig selectPrimaryConfigByWorkCenterId(Long workCenterId);
}
