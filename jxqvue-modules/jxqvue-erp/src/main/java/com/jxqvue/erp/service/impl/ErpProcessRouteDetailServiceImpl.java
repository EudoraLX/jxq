package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProcessRouteDetailMapper;
import com.jxqvue.erp.domain.ErpProcessRouteDetail;
import com.jxqvue.erp.service.IErpProcessRouteDetailService;

/**
 * 工艺路线明细Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class ErpProcessRouteDetailServiceImpl implements IErpProcessRouteDetailService 
{
    @Autowired
    private ErpProcessRouteDetailMapper erpProcessRouteDetailMapper;

    /**
     * 查询工艺路线明细
     * 
     * @param id 工艺路线明细主键
     * @return 工艺路线明细
     */
    @Override
    public ErpProcessRouteDetail selectErpProcessRouteDetailById(Long id)
    {
        return erpProcessRouteDetailMapper.selectErpProcessRouteDetailById(id);
    }

    /**
     * 查询工艺路线明细列表
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 工艺路线明细
     */
    @Override
    public List<ErpProcessRouteDetail> selectErpProcessRouteDetailList(ErpProcessRouteDetail erpProcessRouteDetail)
    {
        return erpProcessRouteDetailMapper.selectErpProcessRouteDetailList(erpProcessRouteDetail);
    }

    /**
     * 根据工艺路线ID查询明细列表
     * 
     * @param processRouteId 工艺路线ID
     * @return 工艺路线明细集合
     */
    @Override
    public List<ErpProcessRouteDetail> selectErpProcessRouteDetailByRouteId(Long processRouteId)
    {
        return erpProcessRouteDetailMapper.selectErpProcessRouteDetailByRouteId(processRouteId);
    }

    /**
     * 新增工艺路线明细
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 结果
     */
    @Override
    public int insertErpProcessRouteDetail(ErpProcessRouteDetail erpProcessRouteDetail)
    {
        erpProcessRouteDetail.setCreateTime(DateUtils.getNowDate());
        return erpProcessRouteDetailMapper.insertErpProcessRouteDetail(erpProcessRouteDetail);
    }

    /**
     * 修改工艺路线明细
     * 
     * @param erpProcessRouteDetail 工艺路线明细
     * @return 结果
     */
    @Override
    public int updateErpProcessRouteDetail(ErpProcessRouteDetail erpProcessRouteDetail)
    {
        erpProcessRouteDetail.setUpdateTime(DateUtils.getNowDate());
        return erpProcessRouteDetailMapper.updateErpProcessRouteDetail(erpProcessRouteDetail);
    }

    /**
     * 批量删除工艺路线明细
     * 
     * @param ids 需要删除的工艺路线明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessRouteDetailByIds(Long[] ids)
    {
        return erpProcessRouteDetailMapper.deleteErpProcessRouteDetailByIds(ids);
    }

    /**
     * 删除工艺路线明细信息
     * 
     * @param id 工艺路线明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessRouteDetailById(Long id)
    {
        return erpProcessRouteDetailMapper.deleteErpProcessRouteDetailById(id);
    }

    /**
     * 根据工艺路线ID删除明细
     * 
     * @param processRouteId 工艺路线ID
     * @return 结果
     */
    @Override
    public int deleteErpProcessRouteDetailByRouteId(Long processRouteId)
    {
        return erpProcessRouteDetailMapper.deleteErpProcessRouteDetailByRouteId(processRouteId);
    }
}
