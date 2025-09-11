package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpWorkCenterConfigMapper;
import com.jxqvue.erp.domain.ErpWorkCenterConfig;
import com.jxqvue.erp.service.IErpWorkCenterConfigService;

/**
 * 工作中心配置Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpWorkCenterConfigServiceImpl implements IErpWorkCenterConfigService 
{
    @Autowired
    private ErpWorkCenterConfigMapper erpWorkCenterConfigMapper;

    /**
     * 查询工作中心配置
     * 
     * @param id 工作中心配置主键
     * @return 工作中心配置
     */
    @Override
    public ErpWorkCenterConfig selectErpWorkCenterConfigById(Long id)
    {
        return erpWorkCenterConfigMapper.selectErpWorkCenterConfigById(id);
    }

    /**
     * 查询工作中心配置列表
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 工作中心配置
     */
    @Override
    public List<ErpWorkCenterConfig> selectErpWorkCenterConfigList(ErpWorkCenterConfig erpWorkCenterConfig)
    {
        return erpWorkCenterConfigMapper.selectErpWorkCenterConfigList(erpWorkCenterConfig);
    }

    /**
     * 根据工作中心ID查询配置列表
     * 
     * @param workCenterId 工作中心ID
     * @return 工作中心配置集合
     */
    @Override
    public List<ErpWorkCenterConfig> selectErpWorkCenterConfigByWorkCenterId(Long workCenterId)
    {
        return erpWorkCenterConfigMapper.selectErpWorkCenterConfigByWorkCenterId(workCenterId);
    }

    /**
     * 新增工作中心配置
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 结果
     */
    @Override
    public int insertErpWorkCenterConfig(ErpWorkCenterConfig erpWorkCenterConfig)
    {
        erpWorkCenterConfig.setCreateTime(DateUtils.getNowDate());
        return erpWorkCenterConfigMapper.insertErpWorkCenterConfig(erpWorkCenterConfig);
    }

    /**
     * 修改工作中心配置
     * 
     * @param erpWorkCenterConfig 工作中心配置
     * @return 结果
     */
    @Override
    public int updateErpWorkCenterConfig(ErpWorkCenterConfig erpWorkCenterConfig)
    {
        erpWorkCenterConfig.setUpdateTime(DateUtils.getNowDate());
        return erpWorkCenterConfigMapper.updateErpWorkCenterConfig(erpWorkCenterConfig);
    }

    /**
     * 批量删除工作中心配置
     * 
     * @param ids 需要删除的工作中心配置主键
     * @return 结果
     */
    @Override
    public int deleteErpWorkCenterConfigByIds(Long[] ids)
    {
        return erpWorkCenterConfigMapper.deleteErpWorkCenterConfigByIds(ids);
    }

    /**
     * 删除工作中心配置信息
     * 
     * @param id 工作中心配置主键
     * @return 结果
     */
    @Override
    public int deleteErpWorkCenterConfigById(Long id)
    {
        return erpWorkCenterConfigMapper.deleteErpWorkCenterConfigById(id);
    }

    /**
     * 根据工作中心ID删除配置
     * 
     * @param workCenterId 工作中心ID
     * @return 结果
     */
    @Override
    public int deleteErpWorkCenterConfigByWorkCenterId(Long workCenterId)
    {
        return erpWorkCenterConfigMapper.deleteErpWorkCenterConfigByWorkCenterId(workCenterId);
    }

    /**
     * 查询工作中心的首选配置
     * 
     * @param workCenterId 工作中心ID
     * @return 工作中心配置
     */
    @Override
    public ErpWorkCenterConfig selectPrimaryConfigByWorkCenterId(Long workCenterId)
    {
        return erpWorkCenterConfigMapper.selectPrimaryConfigByWorkCenterId(workCenterId);
    }
}
