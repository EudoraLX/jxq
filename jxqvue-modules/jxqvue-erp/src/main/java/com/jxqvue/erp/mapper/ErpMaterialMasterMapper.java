package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpMaterialMaster;

/**
 * 物料主数据Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
public interface ErpMaterialMasterMapper 
{
    /**
     * 查询物料主数据
     * 
     * @param id 物料主数据主键
     * @return 物料主数据
     */
    public ErpMaterialMaster selectErpMaterialMasterById(Long id);

    /**
     * 查询物料主数据列表
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 物料主数据集合
     */
    public List<ErpMaterialMaster> selectErpMaterialMasterList(ErpMaterialMaster erpMaterialMaster);

    /**
     * 新增物料主数据
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 结果
     */
    public int insertErpMaterialMaster(ErpMaterialMaster erpMaterialMaster);

    /**
     * 修改物料主数据
     * 
     * @param erpMaterialMaster 物料主数据
     * @return 结果
     */
    public int updateErpMaterialMaster(ErpMaterialMaster erpMaterialMaster);

    /**
     * 删除物料主数据
     * 
     * @param id 物料主数据主键
     * @return 结果
     */
    public int deleteErpMaterialMasterById(Long id);

    /**
     * 批量删除物料主数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpMaterialMasterByIds(Long[] ids);
}
