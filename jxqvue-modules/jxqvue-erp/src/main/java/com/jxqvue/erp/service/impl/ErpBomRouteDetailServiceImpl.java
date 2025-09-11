package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpBomRouteDetailMapper;
import com.jxqvue.erp.domain.ErpBomRouteDetail;
import com.jxqvue.erp.service.IErpBomRouteDetailService;

/**
 * BOM工艺路线明细表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@Service
public class ErpBomRouteDetailServiceImpl implements IErpBomRouteDetailService 
{
    @Autowired
    private ErpBomRouteDetailMapper erpBomRouteDetailMapper;

    /**
     * 查询BOM工艺路线明细表
     * 
     * @param id BOM工艺路线明细表主键
     * @return BOM工艺路线明细表
     */
    @Override
    public ErpBomRouteDetail selectErpBomRouteDetailById(Long id)
    {
        return erpBomRouteDetailMapper.selectErpBomRouteDetailById(id);
    }

    /**
     * 查询BOM工艺路线明细表列表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return BOM工艺路线明细表
     */
    @Override
    public List<ErpBomRouteDetail> selectErpBomRouteDetailList(ErpBomRouteDetail erpBomRouteDetail)
    {
        return erpBomRouteDetailMapper.selectErpBomRouteDetailList(erpBomRouteDetail);
    }

    /**
     * 新增BOM工艺路线明细表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return 结果
     */
    @Override
    public int insertErpBomRouteDetail(ErpBomRouteDetail erpBomRouteDetail)
    {
        erpBomRouteDetail.setCreateTime(DateUtils.getNowDate());
        return erpBomRouteDetailMapper.insertErpBomRouteDetail(erpBomRouteDetail);
    }

    /**
     * 修改BOM工艺路线明细表
     * 
     * @param erpBomRouteDetail BOM工艺路线明细表
     * @return 结果
     */
    @Override
    public int updateErpBomRouteDetail(ErpBomRouteDetail erpBomRouteDetail)
    {
        erpBomRouteDetail.setUpdateTime(DateUtils.getNowDate());
        return erpBomRouteDetailMapper.updateErpBomRouteDetail(erpBomRouteDetail);
    }

    /**
     * 批量删除BOM工艺路线明细表
     * 
     * @param ids 需要删除的BOM工艺路线明细表主键
     * @return 结果
     */
    @Override
    public int deleteErpBomRouteDetailByIds(Long[] ids)
    {
        return erpBomRouteDetailMapper.deleteErpBomRouteDetailByIds(ids);
    }

    /**
     * 删除BOM工艺路线明细表信息
     * 
     * @param id BOM工艺路线明细表主键
     * @return 结果
     */
    @Override
    public int deleteErpBomRouteDetailById(Long id)
    {
        return erpBomRouteDetailMapper.deleteErpBomRouteDetailById(id);
    }
}
