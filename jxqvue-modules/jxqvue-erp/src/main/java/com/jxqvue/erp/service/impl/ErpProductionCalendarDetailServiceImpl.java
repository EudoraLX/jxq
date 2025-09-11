package com.jxqvue.erp.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionCalendarDetailMapper;
import com.jxqvue.erp.domain.ErpProductionCalendarDetail;
import com.jxqvue.erp.service.IErpProductionCalendarDetailService;

/**
 * 生产日历明细Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class ErpProductionCalendarDetailServiceImpl implements IErpProductionCalendarDetailService 
{
    @Autowired
    private ErpProductionCalendarDetailMapper erpProductionCalendarDetailMapper;

    /**
     * 查询生产日历明细
     * 
     * @param detailId 生产日历明细主键
     * @return 生产日历明细
     */
    @Override
    public ErpProductionCalendarDetail selectErpProductionCalendarDetailByDetailId(Long detailId)
    {
        return erpProductionCalendarDetailMapper.selectErpProductionCalendarDetailByDetailId(detailId);
    }

    /**
     * 查询生产日历明细列表
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 生产日历明细
     */
    @Override
    public List<ErpProductionCalendarDetail> selectErpProductionCalendarDetailList(ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        return erpProductionCalendarDetailMapper.selectErpProductionCalendarDetailList(erpProductionCalendarDetail);
    }

    /**
     * 新增生产日历明细
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 结果
     */
    @Override
    public int insertErpProductionCalendarDetail(ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        return erpProductionCalendarDetailMapper.insertErpProductionCalendarDetail(erpProductionCalendarDetail);
    }

    /**
     * 修改生产日历明细
     * 
     * @param erpProductionCalendarDetail 生产日历明细
     * @return 结果
     */
    @Override
    public int updateErpProductionCalendarDetail(ErpProductionCalendarDetail erpProductionCalendarDetail)
    {
        return erpProductionCalendarDetailMapper.updateErpProductionCalendarDetail(erpProductionCalendarDetail);
    }

    /**
     * 批量删除生产日历明细
     * 
     * @param detailIds 需要删除的生产日历明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionCalendarDetailByDetailIds(Long[] detailIds)
    {
        return erpProductionCalendarDetailMapper.deleteErpProductionCalendarDetailByDetailIds(detailIds);
    }

    /**
     * 删除生产日历明细信息
     * 
     * @param detailId 生产日历明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionCalendarDetailByDetailId(Long detailId)
    {
        return erpProductionCalendarDetailMapper.deleteErpProductionCalendarDetailByDetailId(detailId);
    }
}
