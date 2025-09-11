package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpWorkCenterMapper;
import com.jxqvue.erp.domain.ErpWorkCenter;
import com.jxqvue.erp.domain.ErpWorkCenterConfig;
import com.jxqvue.erp.service.IErpWorkCenterService;
import com.jxqvue.erp.service.IErpWorkCenterConfigService;

/**
 * 工作中心Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpWorkCenterServiceImpl implements IErpWorkCenterService 
{
    @Autowired
    private ErpWorkCenterMapper erpWorkCenterMapper;
    
    @Autowired
    private IErpWorkCenterConfigService erpWorkCenterConfigService;

    /**
     * 查询工作中心
     * 
     * @param id 工作中心主键
     * @return 工作中心
     */
    @Override
    public ErpWorkCenter selectErpWorkCenterById(Long id)
    {
        ErpWorkCenter workCenter = erpWorkCenterMapper.selectErpWorkCenterById(id);
        if (workCenter != null) {
            // 查询配置列表
            List<ErpWorkCenterConfig> configList = erpWorkCenterConfigService.selectErpWorkCenterConfigByWorkCenterId(id);
            workCenter.setConfigList(configList);
        }
        return workCenter;
    }

    /**
     * 查询工作中心列表
     * 
     * @param erpWorkCenter 工作中心
     * @return 工作中心
     */
    @Override
    public List<ErpWorkCenter> selectErpWorkCenterList(ErpWorkCenter erpWorkCenter)
    {
        return erpWorkCenterMapper.selectErpWorkCenterList(erpWorkCenter);
    }

    /**
     * 新增工作中心
     * 
     * @param erpWorkCenter 工作中心
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpWorkCenter(ErpWorkCenter erpWorkCenter)
    {
        erpWorkCenter.setCreateTime(DateUtils.getNowDate());
        int result = erpWorkCenterMapper.insertErpWorkCenter(erpWorkCenter);
        
        // 插入配置列表
        if (result > 0 && erpWorkCenter.getConfigList() != null && !erpWorkCenter.getConfigList().isEmpty()) {
            insertConfigList(erpWorkCenter);
        }
        
        return result;
    }

    /**
     * 修改工作中心
     * 
     * @param erpWorkCenter 工作中心
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpWorkCenter(ErpWorkCenter erpWorkCenter)
    {
        erpWorkCenter.setUpdateTime(DateUtils.getNowDate());
        int result = erpWorkCenterMapper.updateErpWorkCenter(erpWorkCenter);
        
        // 更新配置列表
        if (result > 0 && erpWorkCenter.getConfigList() != null) {
            // 先删除原有配置
            erpWorkCenterConfigService.deleteErpWorkCenterConfigByWorkCenterId(erpWorkCenter.getId());
            // 再插入新配置
            if (!erpWorkCenter.getConfigList().isEmpty()) {
                insertConfigList(erpWorkCenter);
            }
        }
        
        return result;
    }

    /**
     * 批量删除工作中心
     * 
     * @param ids 需要删除的工作中心主键
     * @return 结果
     */
    @Override
    public int deleteErpWorkCenterByIds(Long[] ids)
    {
        return erpWorkCenterMapper.deleteErpWorkCenterByIds(ids);
    }

    /**
     * 删除工作中心信息
     * 
     * @param id 工作中心主键
     * @return 结果
     */
    @Override
    public int deleteErpWorkCenterById(Long id)
    {
        return erpWorkCenterMapper.deleteErpWorkCenterById(id);
    }

    /**
     * 插入配置列表
     * 
     * @param erpWorkCenter 工作中心
     */
    private void insertConfigList(ErpWorkCenter erpWorkCenter) {
        for (ErpWorkCenterConfig config : erpWorkCenter.getConfigList()) {
            config.setWorkCenterId(erpWorkCenter.getId());
            erpWorkCenterConfigService.insertErpWorkCenterConfig(config);
        }
    }

    /**
     * 获取工作中心关联的设备和模具
     * 
     * @param id 工作中心主键
     * @return 设备和模具信息
     */
    @Override
    public java.util.Map<String, Object> getWorkCenterEquipmentAndMold(Long id) {
        return erpWorkCenterMapper.getWorkCenterEquipmentAndMold(id);
    }
}
