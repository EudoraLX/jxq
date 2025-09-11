package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpEquipment;

/**
 * 设备Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpEquipmentService 
{
    /**
     * 查询设备
     * 
     * @param id 设备主键
     * @return 设备
     */
    public ErpEquipment selectErpEquipmentById(Long id);

    /**
     * 查询设备列表
     * 
     * @param erpEquipment 设备
     * @return 设备集合
     */
    public List<ErpEquipment> selectErpEquipmentList(ErpEquipment erpEquipment);

    /**
     * 新增设备
     * 
     * @param erpEquipment 设备
     * @return 结果
     */
    public int insertErpEquipment(ErpEquipment erpEquipment);

    /**
     * 修改设备
     * 
     * @param erpEquipment 设备
     * @return 结果
     */
    public int updateErpEquipment(ErpEquipment erpEquipment);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的设备主键集合
     * @return 结果
     */
    public int deleteErpEquipmentByIds(Long[] ids);

    /**
     * 删除设备信息
     * 
     * @param id 设备主键
     * @return 结果
     */
    public int deleteErpEquipmentById(Long id);
}
