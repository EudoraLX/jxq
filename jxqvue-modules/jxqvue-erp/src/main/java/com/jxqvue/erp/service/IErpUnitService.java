package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpUnit;

/**
 * 计量单位Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface IErpUnitService 
{
    /**
     * 查询计量单位
     * 
     * @param id 计量单位主键
     * @return 计量单位
     */
    public ErpUnit selectErpUnitById(Long id);

    /**
     * 查询计量单位列表
     * 
     * @param erpUnit 计量单位
     * @return 计量单位集合
     */
    public List<ErpUnit> selectErpUnitList(ErpUnit erpUnit);

    /**
     * 新增计量单位
     * 
     * @param erpUnit 计量单位
     * @return 结果
     */
    public int insertErpUnit(ErpUnit erpUnit);

    /**
     * 修改计量单位
     * 
     * @param erpUnit 计量单位
     * @return 结果
     */
    public int updateErpUnit(ErpUnit erpUnit);

    /**
     * 批量删除计量单位
     * 
     * @param ids 需要删除的计量单位主键集合
     * @return 结果
     */
    public int deleteErpUnitByIds(Long[] ids);

    /**
     * 删除计量单位信息
     * 
     * @param id 计量单位主键
     * @return 结果
     */
    public int deleteErpUnitById(Long id);
}
