package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpInboundOrder;

/**
 * 入库单表Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpInboundOrderService 
{
    /**
     * 查询入库单表
     * 
     * @param id 入库单表主键
     * @return 入库单表
     */
    public ErpInboundOrder selectErpInboundOrderById(Long id);

    /**
     * 查询入库单表列表
     * 
     * @param erpInboundOrder 入库单表
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderList(ErpInboundOrder erpInboundOrder);

    /**
     * 新增入库单表
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int insertErpInboundOrder(ErpInboundOrder erpInboundOrder);

    /**
     * 修改入库单表
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int updateErpInboundOrder(ErpInboundOrder erpInboundOrder);

    /**
     * 批量删除入库单表
     * 
     * @param ids 需要删除的入库单表主键集合
     * @return 结果
     */
    public int deleteErpInboundOrderByIds(Long[] ids);

    /**
     * 删除入库单表信息
     * 
     * @param id 入库单表主键
     * @return 结果
     */
    public int deleteErpInboundOrderById(Long id);

    /**
     * 根据入库单号查询入库单
     * 
     * @param inboundNo 入库单号
     * @return 入库单表
     */
    public ErpInboundOrder selectErpInboundOrderByInboundNo(String inboundNo);

    /**
     * 审核入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int approveErpInboundOrder(ErpInboundOrder erpInboundOrder);

    /**
     * 执行入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int executeErpInboundOrder(ErpInboundOrder erpInboundOrder);

    /**
     * 取消入库单
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int cancelErpInboundOrder(ErpInboundOrder erpInboundOrder);

    /**
     * 生成入库单号
     * 
     * @return 入库单号
     */
    public String generateInboundNo();

    /**
     * 查询入库单统计信息
     * 
     * @param erpInboundOrder 入库单表
     * @return 统计结果
     */
    public List<ErpInboundOrder> selectErpInboundOrderStatistics(ErpInboundOrder erpInboundOrder);
}
