package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpEquipmentMapper;
import com.jxqvue.erp.domain.ErpEquipment;
import com.jxqvue.erp.service.IErpEquipmentService;

/**
 * 设备Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpEquipmentServiceImpl implements IErpEquipmentService 
{
    @Autowired
    private ErpEquipmentMapper erpEquipmentMapper;

    /**
     * 查询设备
     * 
     * @param id 设备主键
     * @return 设备
     */
    @Override
    public ErpEquipment selectErpEquipmentById(Long id)
    {
        return erpEquipmentMapper.selectErpEquipmentById(id);
    }

    /**
     * 查询设备列表
     * 
     * @param erpEquipment 设备
     * @return 设备
     */
    @Override
    public List<ErpEquipment> selectErpEquipmentList(ErpEquipment erpEquipment)
    {
        return erpEquipmentMapper.selectErpEquipmentList(erpEquipment);
    }

    /**
     * 新增设备
     * 
     * @param erpEquipment 设备
     * @return 结果
     */
    @Override
    public int insertErpEquipment(ErpEquipment erpEquipment)
    {
        erpEquipment.setCreateTime(DateUtils.getNowDate());
        return erpEquipmentMapper.insertErpEquipment(erpEquipment);
    }

    /**
     * 修改设备
     * 
     * @param erpEquipment 设备
     * @return 结果
     */
    @Override
    public int updateErpEquipment(ErpEquipment erpEquipment)
    {
        erpEquipment.setUpdateTime(DateUtils.getNowDate());
        return erpEquipmentMapper.updateErpEquipment(erpEquipment);
    }

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteErpEquipmentByIds(Long[] ids)
    {
        return erpEquipmentMapper.deleteErpEquipmentByIds(ids);
    }

    /**
     * 删除设备信息
     * 
     * @param id 设备主键
     * @return 结果
     */
    @Override
    public int deleteErpEquipmentById(Long id)
    {
        return erpEquipmentMapper.deleteErpEquipmentById(id);
    }
}
