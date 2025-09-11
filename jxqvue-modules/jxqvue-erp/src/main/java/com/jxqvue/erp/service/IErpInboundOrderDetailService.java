package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInboundOrderDetail;

/**
 * 入库单明细Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IErpInboundOrderDetailService 
{
    /**
     * 查询入库单明细
     * 
     * @param id 入库单明细主键
     * @return 入库单明细
     */
    public ErpInboundOrderDetail selectErpInboundOrderDetailById(Long id);

    /**
     * 查询入库单明细列表
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 入库单明细集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailList(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 新增入库单明细
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 结果
     */
    public int insertErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 修改入库单明细
     * 
     * @param erpInboundOrderDetail 入库单明细
     * @return 结果
     */
    public int updateErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 批量删除入库单明细
     * 
     * @param ids 需要删除的入库单明细主键集合
     * @return 结果
     */
    public int deleteErpInboundOrderDetailByIds(Long[] ids);

    /**
     * 删除入库单明细信息
     * 
     * @param id 入库单明细主键
     * @return 结果
     */
    public int deleteErpInboundOrderDetailById(Long id);

    /**
     * 根据入库单ID查询明细列表
     * 
     * @param inboundId 入库单ID
     * @return 入库单明细集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByInboundId(Long inboundId);

    /**
     * 批量新增入库单明细
     * 
     * @param erpInboundOrderDetails 入库单明细列表
     * @return 结果
     */
    public int insertErpInboundOrderDetails(List<ErpInboundOrderDetail> erpInboundOrderDetails);

    /**
     * 批量更新入库单明细
     * 
     * @param erpInboundOrderDetails 入库单明细列表
     * @return 结果
     */
    public int updateErpInboundOrderDetails(List<ErpInboundOrderDetail> erpInboundOrderDetails);

    /**
     * 根据入库单ID删除明细
     * 
     * @param inboundId 入库单ID
     * @return 结果
     */
    public int deleteErpInboundOrderDetailByInboundId(Long inboundId);
}
