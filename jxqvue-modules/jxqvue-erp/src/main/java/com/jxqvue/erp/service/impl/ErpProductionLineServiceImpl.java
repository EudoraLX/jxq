package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionLineMapper;
import com.jxqvue.erp.domain.ErpProductionLine;
import com.jxqvue.erp.service.IErpProductionLineService;

/**
 * 生产线Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Service
public class ErpProductionLineServiceImpl implements IErpProductionLineService 
{
    @Autowired
    private ErpProductionLineMapper erpProductionLineMapper;

    /**
     * 查询生产线
     * 
     * @param id 生产线主键
     * @return 生产线
     */
    @Override
    public ErpProductionLine selectErpProductionLineById(Long id)
    {
        return erpProductionLineMapper.selectErpProductionLineById(id);
    }

    /**
     * 查询生产线列表
     * 
     * @param erpProductionLine 生产线
     * @return 生产线
     */
    @Override
    public List<ErpProductionLine> selectErpProductionLineList(ErpProductionLine erpProductionLine)
    {
        return erpProductionLineMapper.selectErpProductionLineList(erpProductionLine);
    }

    /**
     * 新增生产线
     * 
     * @param erpProductionLine 生产线
     * @return 结果
     */
    @Override
    public int insertErpProductionLine(ErpProductionLine erpProductionLine)
    {
        erpProductionLine.setCreateTime(DateUtils.getNowDate());
        return erpProductionLineMapper.insertErpProductionLine(erpProductionLine);
    }

    /**
     * 修改生产线
     * 
     * @param erpProductionLine 生产线
     * @return 结果
     */
    @Override
    public int updateErpProductionLine(ErpProductionLine erpProductionLine)
    {
        erpProductionLine.setUpdateTime(DateUtils.getNowDate());
        return erpProductionLineMapper.updateErpProductionLine(erpProductionLine);
    }

    /**
     * 批量删除生产线
     * 
     * @param ids 需要删除的生产线主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionLineByIds(Long[] ids)
    {
        return erpProductionLineMapper.deleteErpProductionLineByIds(ids);
    }

    /**
     * 删除生产线信息
     * 
     * @param id 生产线主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionLineById(Long id)
    {
        return erpProductionLineMapper.deleteErpProductionLineById(id);
    }

    /**
     * 获取可用的生产线列表（已启用且状态正常）
     * 
     * @return 生产线集合
     */
    @Override
    public List<ErpProductionLine> getAvailableProductionLines()
    {
        return erpProductionLineMapper.selectAvailableProductionLines();
    }
}
