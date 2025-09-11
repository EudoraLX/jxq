package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpUnitMapper;
import com.jxqvue.erp.domain.ErpUnit;
import com.jxqvue.erp.service.IErpUnitService;

/**
 * 计量单位Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpUnitServiceImpl implements IErpUnitService 
{
    @Autowired
    private ErpUnitMapper erpUnitMapper;

    /**
     * 查询计量单位
     * 
     * @param id 计量单位主键
     * @return 计量单位
     */
    @Override
    public ErpUnit selectErpUnitById(Long id)
    {
        return erpUnitMapper.selectErpUnitById(id);
    }

    /**
     * 查询计量单位列表
     * 
     * @param erpUnit 计量单位
     * @return 计量单位
     */
    @Override
    public List<ErpUnit> selectErpUnitList(ErpUnit erpUnit)
    {
        return erpUnitMapper.selectErpUnitList(erpUnit);
    }

    /**
     * 新增计量单位
     * 
     * @param erpUnit 计量单位
     * @return 结果
     */
    @Override
    public int insertErpUnit(ErpUnit erpUnit)
    {
        erpUnit.setCreateTime(DateUtils.getNowDate());
        return erpUnitMapper.insertErpUnit(erpUnit);
    }

    /**
     * 修改计量单位
     * 
     * @param erpUnit 计量单位
     * @return 结果
     */
    @Override
    public int updateErpUnit(ErpUnit erpUnit)
    {
        erpUnit.setUpdateTime(DateUtils.getNowDate());
        return erpUnitMapper.updateErpUnit(erpUnit);
    }

    /**
     * 批量删除计量单位
     * 
     * @param ids 需要删除的计量单位主键
     * @return 结果
     */
    @Override
    public int deleteErpUnitByIds(Long[] ids)
    {
        return erpUnitMapper.deleteErpUnitByIds(ids);
    }

    /**
     * 删除计量单位信息
     * 
     * @param id 计量单位主键
     * @return 结果
     */
    @Override
    public int deleteErpUnitById(Long id)
    {
        return erpUnitMapper.deleteErpUnitById(id);
    }
}
