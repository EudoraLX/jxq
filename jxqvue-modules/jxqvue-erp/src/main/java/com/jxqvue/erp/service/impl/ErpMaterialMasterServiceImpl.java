package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpMaterialMasterMapper;
import com.jxqvue.erp.domain.ErpMaterialMaster;
import com.jxqvue.erp.service.IErpMaterialMasterService;

/**
 * 物料主数据Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpMaterialMasterServiceImpl implements IErpMaterialMasterService 
{
    @Autowired
    private ErpMaterialMasterMapper erpMaterialMasterMapper;

    /**
     * 查询物料主数据
     * 
     * @param id 物料主数据主键
     * @return 物料主数据
     */
    @Override
    public ErpMaterialMaster selectErpMaterialMasterById(Long id)
    {
        return erpMaterialMasterMapper.selectErpMaterialMasterById(id);
    }

    /**
     * 查询物料主数据列表
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 物料主数据
     */
    @Override
    public List<ErpMaterialMaster> selectErpMaterialMasterList(ErpMaterialMaster erpMaterialMaster)
    {
        return erpMaterialMasterMapper.selectErpMaterialMasterList(erpMaterialMaster);
    }

    /**
     * 新增物料主数据
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 结果
     */
    @Override
    public int insertErpMaterialMaster(ErpMaterialMaster erpMaterialMaster)
    {
        erpMaterialMaster.setCreateTime(DateUtils.getNowDate());
        return erpMaterialMasterMapper.insertErpMaterialMaster(erpMaterialMaster);
    }

    /**
     * 修改物料主数据
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 结果
     */
    @Override
    public int updateErpMaterialMaster(ErpMaterialMaster erpMaterialMaster)
    {
        erpMaterialMaster.setUpdateTime(DateUtils.getNowDate());
        return erpMaterialMasterMapper.updateErpMaterialMaster(erpMaterialMaster);
    }

    /**
     * 批量删除物料主数据
     * 
     * @param ids 需要删除的物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteErpMaterialMasterByIds(Long[] ids)
    {
        return erpMaterialMasterMapper.deleteErpMaterialMasterByIds(ids);
    }

    /**
     * 删除物料主数据信息
     * 
     * @param id 物料主数据主键
     * @return 结果
     */
    @Override
    public int deleteErpMaterialMasterById(Long id)
    {
        return erpMaterialMasterMapper.deleteErpMaterialMasterById(id);
    }
}
