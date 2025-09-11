package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpInboundOrderDetailMapper;
import com.jxqvue.erp.domain.ErpInboundOrderDetail;
import com.jxqvue.erp.service.IErpInboundOrderDetailService;

/**
 * 入库单明细Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class ErpInboundOrderDetailServiceImpl implements IErpInboundOrderDetailService 
{
    @Autowired
    private ErpInboundOrderDetailMapper erpInboundOrderDetailMapper;

    /**
     * 查询入库单明细
     * 
     * @param id 入库单明细主键
     * @return 入库单明细
     */
    @Override
    public ErpInboundOrderDetail selectErpInboundOrderDetailById(Long id)
    {
        return erpInboundOrderDetailMapper.selectErpInboundOrderDetailById(id);
    }

    /**
     * 查询入库单明细列表
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 入库单明细
     */
    @Override
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailList(ErpInboundOrderDetail erpInboundOrderDetail)
    {
        return erpInboundOrderDetailMapper.selectErpInboundOrderDetailList(erpInboundOrderDetail);
    }

    /**
     * 新增入库单明细
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 结果
     */
    @Override
    public int insertErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail)
    {
        erpInboundOrderDetail.setCreateTime(DateUtils.getNowDate());
        return erpInboundOrderDetailMapper.insertErpInboundOrderDetail(erpInboundOrderDetail);
    }

    /**
     * 修改入库单明细
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 结果
     */
    @Override
    public int updateErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail)
    {
        erpInboundOrderDetail.setUpdateTime(DateUtils.getNowDate());
        return erpInboundOrderDetailMapper.updateErpInboundOrderDetail(erpInboundOrderDetail);
    }

    /**
     * 批量删除入库单明细
     * 
     * @param ids 需要删除的入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteErpInboundOrderDetailByIds(Long[] ids)
    {
        return erpInboundOrderDetailMapper.deleteErpInboundOrderDetailByIds(ids);
    }

    /**
     * 删除入库单明细信息
     * 
     * @param id 入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteErpInboundOrderDetailById(Long id)
    {
        return erpInboundOrderDetailMapper.deleteErpInboundOrderDetailById(id);
    }

    /**
     * 根据入库单ID查询明细列表
     * 
     * @param inboundId 入库单ID
     * @return 入库单明细集合
     */
    @Override
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByInboundId(Long inboundId)
    {
        ErpInboundOrderDetail query = new ErpInboundOrderDetail();
        query.setInboundId(inboundId);
        return erpInboundOrderDetailMapper.selectErpInboundOrderDetailList(query);
    }

    /**
     * 批量新增入库单明细
     * 
     * @param erpInboundOrderDetails 入库单明细列表
     * @return 结果
     */
    @Override
    public int insertErpInboundOrderDetails(List<ErpInboundOrderDetail> erpInboundOrderDetails)
    {
        int result = 0;
        for (ErpInboundOrderDetail detail : erpInboundOrderDetails) {
            detail.setCreateTime(DateUtils.getNowDate());
            result += erpInboundOrderDetailMapper.insertErpInboundOrderDetail(detail);
        }
        return result;
    }

    /**
     * 批量更新入库单明细
     * 
     * @param erpInboundOrderDetails 入库单明细列表
     * @return 结果
     */
    @Override
    public int updateErpInboundOrderDetails(List<ErpInboundOrderDetail> erpInboundOrderDetails)
    {
        int result = 0;
        for (ErpInboundOrderDetail detail : erpInboundOrderDetails) {
            detail.setUpdateTime(DateUtils.getNowDate());
            result += erpInboundOrderDetailMapper.updateErpInboundOrderDetail(detail);
        }
        return result;
    }

    /**
     * 根据入库单ID删除明细
     * 
     * @param inboundId 入库单ID
     * @return 结果
     */
    @Override
    public int deleteErpInboundOrderDetailByInboundId(Long inboundId)
    {
        return erpInboundOrderDetailMapper.deleteErpInboundOrderDetailByInboundId(inboundId);
    }
}
