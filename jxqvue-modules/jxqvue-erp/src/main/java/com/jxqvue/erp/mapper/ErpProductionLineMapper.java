package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionLine;

/**
 * 生产线Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public interface ErpProductionLineMapper 
{
    /**
     * 查询生产线
     * 
     * @param id 生产线主键
     * @return 生产线
     */
    public ErpProductionLine selectErpProductionLineById(Long id);

    /**
     * 查询生产线列表
     * 
     * @param erpProductionLine 生产线
     * @return 生产线集合
     */
    public List<ErpProductionLine> selectErpProductionLineList(ErpProductionLine erpProductionLine);

    /**
     * 新增生产线
     * 
     * @param erpProductionLine 生产线
     * @return 结果
     */
    public int insertErpProductionLine(ErpProductionLine erpProductionLine);

    /**
     * 修改生产线
     * 
     * @param erpProductionLine 生产线
     * @return 结果
     */
    public int updateErpProductionLine(ErpProductionLine erpProductionLine);

    /**
     * 删除生产线
     * 
     * @param id 生产线主键
     * @return 结果
     */
    public int deleteErpProductionLineById(Long id);

    /**
     * 批量删除生产线
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionLineByIds(Long[] ids);

    /**
     * 获取可用的生产线列表（已启用且状态正常）
     * 
     * @return 生产线集合
     */
    public List<ErpProductionLine> selectAvailableProductionLines();
}
