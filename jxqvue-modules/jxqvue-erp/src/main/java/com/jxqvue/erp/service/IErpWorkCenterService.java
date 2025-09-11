package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpWorkCenter;

/**
 * 工作中心Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpWorkCenterService 
{
    /**
     * 查询工作中心
     * 
     * @param id 工作中心主键
     * @return 工作中心
     */
    public ErpWorkCenter selectErpWorkCenterById(Long id);

    /**
     * 查询工作中心列表
     * 
     * @param erpWorkCenter 工作中心
     * @return 工作中心集合
     */
    public List<ErpWorkCenter> selectErpWorkCenterList(ErpWorkCenter erpWorkCenter);

    /**
     * 新增工作中心
     * 
     * @param erpWorkCenter 工作中心
     * @return 结果
     */
    public int insertErpWorkCenter(ErpWorkCenter erpWorkCenter);

    /**
     * 修改工作中心
     * 
     * @param erpWorkCenter 工作中心
     * @return 结果
     */
    public int updateErpWorkCenter(ErpWorkCenter erpWorkCenter);

    /**
     * 批量删除工作中心
     * 
     * @param ids 需要删除的工作中心主键集合
     * @return 结果
     */
    public int deleteErpWorkCenterByIds(Long[] ids);

    /**
     * 删除工作中心信息
     * 
     * @param id 工作中心主键
     * @return 结果
     */
    public int deleteErpWorkCenterById(Long id);

    /**
     * 获取工作中心关联的设备和模具
     * 
     * @param id 工作中心主键
     * @return 设备和模具信息
     */
    public java.util.Map<String, Object> getWorkCenterEquipmentAndMold(Long id);
}
