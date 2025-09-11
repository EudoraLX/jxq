package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpProductionOrderDetailMapper;
import com.jxqvue.erp.domain.ErpProductionOrderDetail;
import com.jxqvue.erp.service.IErpProductionOrderDetailService;

/**
 * 生产订单明细Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
@Service
public class ErpProductionOrderDetailServiceImpl implements IErpProductionOrderDetailService 
{
    @Autowired
    private ErpProductionOrderDetailMapper erpProductionOrderDetailMapper;

    /**
     * 查询生产订单明细
     * 
     * @param id 生产订单明细主键
     * @return 生产订单明细
     */
    @Override
    public ErpProductionOrderDetail selectErpProductionOrderDetailById(Long id)
    {
        return erpProductionOrderDetailMapper.selectErpProductionOrderDetailById(id);
    }

    /**
     * 查询生产订单明细列表
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 生产订单明细
     */
    @Override
    public List<ErpProductionOrderDetail> selectErpProductionOrderDetailList(ErpProductionOrderDetail erpProductionOrderDetail)
    {
        return erpProductionOrderDetailMapper.selectErpProductionOrderDetailList(erpProductionOrderDetail);
    }

    /**
     * 根据订单ID查询生产订单明细列表
     * 
     * @param orderId 订单ID
     * @return 生产订单明细集合
     */
    @Override
    public List<ErpProductionOrderDetail> selectErpProductionOrderDetailByOrderId(Long orderId)
    {
        return erpProductionOrderDetailMapper.selectErpProductionOrderDetailByOrderId(orderId);
    }

    /**
     * 新增生产订单明细
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 结果
     */
    @Override
    public int insertErpProductionOrderDetail(ErpProductionOrderDetail erpProductionOrderDetail)
    {
        erpProductionOrderDetail.setCreateTime(DateUtils.getNowDate());
        return erpProductionOrderDetailMapper.insertErpProductionOrderDetail(erpProductionOrderDetail);
    }

    /**
     * 修改生产订单明细
     * 
     * @param erpProductionOrderDetail 生产订单明细
     * @return 结果
     */
    @Override
    public int updateErpProductionOrderDetail(ErpProductionOrderDetail erpProductionOrderDetail)
    {
        erpProductionOrderDetail.setUpdateTime(DateUtils.getNowDate());
        return erpProductionOrderDetailMapper.updateErpProductionOrderDetail(erpProductionOrderDetail);
    }

    /**
     * 批量删除生产订单明细
     * 
     * @param ids 需要删除的生产订单明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderDetailByIds(Long[] ids)
    {
        return erpProductionOrderDetailMapper.deleteErpProductionOrderDetailByIds(ids);
    }

    /**
     * 删除生产订单明细信息
     * 
     * @param id 生产订单明细主键
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderDetailById(Long id)
    {
        return erpProductionOrderDetailMapper.deleteErpProductionOrderDetailById(id);
    }

    /**
     * 根据订单ID删除生产订单明细
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteErpProductionOrderDetailByOrderId(Long orderId)
    {
        return erpProductionOrderDetailMapper.deleteErpProductionOrderDetailByOrderId(orderId);
    }
}
