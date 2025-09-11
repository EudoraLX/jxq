package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpEquipment;

/**
 * 设备Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface ErpEquipmentMapper 
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
     * 删除设备
     * 
     * @param id 设备主键
     * @return 结果
     */
    public int deleteErpEquipmentById(Long id);

    /**
     * 批量删除设备
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpEquipmentByIds(Long[] ids);
}
