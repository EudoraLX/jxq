package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpBomDetailMapper;
import com.jxqvue.erp.domain.ErpBomDetail;
import com.jxqvue.erp.service.IErpBomDetailService;

/**
 * BOM明细表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@Service
public class ErpBomDetailServiceImpl implements IErpBomDetailService 
{
    @Autowired
    private ErpBomDetailMapper erpBomDetailMapper;

    /**
     * 查询BOM明细表
     * 
     * @param id BOM明细表主键
     * @return BOM明细表
     */
    @Override
    public ErpBomDetail selectErpBomDetailById(Long id)
    {
        return erpBomDetailMapper.selectErpBomDetailById(id);
    }

    /**
     * 查询BOM明细表列表
     * 
     * @param erpBomDetail BOM明细表
     * @return BOM明细表
     */
    @Override
    public List<ErpBomDetail> selectErpBomDetailList(ErpBomDetail erpBomDetail)
    {
        return erpBomDetailMapper.selectErpBomDetailList(erpBomDetail);
    }

    /**
     * 新增BOM明细表
     * 
     * @param erpBomDetail BOM明细表
     * @return 结果
     */
    @Override
    public int insertErpBomDetail(ErpBomDetail erpBomDetail)
    {
        erpBomDetail.setCreateTime(DateUtils.getNowDate());
        return erpBomDetailMapper.insertErpBomDetail(erpBomDetail);
    }

    /**
     * 修改BOM明细表
     * 
     * @param erpBomDetail BOM明细表
     * @return 结果
     */
    @Override
    public int updateErpBomDetail(ErpBomDetail erpBomDetail)
    {
        erpBomDetail.setUpdateTime(DateUtils.getNowDate());
        return erpBomDetailMapper.updateErpBomDetail(erpBomDetail);
    }

    /**
     * 批量删除BOM明细表
     * 
     * @param ids 需要删除的BOM明细表主键
     * @return 结果
     */
    @Override
    public int deleteErpBomDetailByIds(Long[] ids)
    {
        return erpBomDetailMapper.deleteErpBomDetailByIds(ids);
    }

    /**
     * 删除BOM明细表信息
     * 
     * @param id BOM明细表主键
     * @return 结果
     */
    @Override
    public int deleteErpBomDetailById(Long id)
    {
        return erpBomDetailMapper.deleteErpBomDetailById(id);
    }
}
