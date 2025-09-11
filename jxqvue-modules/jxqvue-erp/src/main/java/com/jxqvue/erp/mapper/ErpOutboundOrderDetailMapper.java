package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpOutboundOrderDetail;

/**
 * 出库单明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpOutboundOrderDetailMapper 
{
    /**
     * 查询出库单明细表
     * 
     * @param id 出库单明细表主键
     * @return 出库单明细表
     */
    public ErpOutboundOrderDetail selectErpOutboundOrderDetailById(Long id);

    /**
     * 查询出库单明细表列表
     * 
     * @param erpOutboundOrderDetail 出库单明细表
     * @return 出库单明细表集合
     */
    public List<ErpOutboundOrderDetail> selectErpOutboundOrderDetailList(ErpOutboundOrderDetail erpOutboundOrderDetail);

    /**
     * 新增出库单明细表
     * 
     * @param erpOutboundOrderDetail 出库单明细表
     * @return 结果
     */
    public int insertErpOutboundOrderDetail(ErpOutboundOrderDetail erpOutboundOrderDetail);

    /**
     * 修改出库单明细表
     * 
     * @param erpOutboundOrderDetail 出库单明细表
     * @return 结果
     */
    public int updateErpOutboundOrderDetail(ErpOutboundOrderDetail erpOutboundOrderDetail);

    /**
     * 删除出库单明细表
     * 
     * @param id 出库单明细表主键
     * @return 结果
     */
    public int deleteErpOutboundOrderDetailById(Long id);

    /**
     * 批量删除出库单明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpOutboundOrderDetailByIds(Long[] ids);

    /**
     * 根据出库单ID查询明细列表
     * 
     * @param outboundId 出库单ID
     * @return 出库单明细表集合
     */
    public List<ErpOutboundOrderDetail> selectErpOutboundOrderDetailByOutboundId(Long outboundId);

    /**
     * 根据出库单号查询明细列表
     * 
     * @param outboundNo 出库单号
     * @return 出库单明细表集合
     */
    public List<ErpOutboundOrderDetail> selectErpOutboundOrderDetailByOutboundNo(String outboundNo);

    /**
     * 根据物料ID查询出库单明细列表
     * 
     * @param materialId 物料ID
     * @return 出库单明细表集合
     */
    public List<ErpOutboundOrderDetail> selectErpOutboundOrderDetailByMaterialId(Long materialId);

    /**
     * 根据批次号查询出库单明细列表
     * 
     * @param batchNo 批次号
     * @return 出库单明细表集合
     */
    public List<ErpOutboundOrderDetail> selectErpOutboundOrderDetailByBatchNo(String batchNo);

    /**
     * 根据序列号查询出库单明细
     * 
     * @param serialNo 序列号
     * @return 出库单明细表
     */
    public ErpOutboundOrderDetail selectErpOutboundOrderDetailBySerialNo(String serialNo);

    /**
     * 批量新增出库单明细
     * 
     * @param erpOutboundOrderDetailList 出库单明细列表
     * @return 结果
     */
    public int batchInsertErpOutboundOrderDetail(List<ErpOutboundOrderDetail> erpOutboundOrderDetailList);

    /**
     * 根据出库单ID删除明细
     * 
     * @param outboundId 出库单ID
     * @return 结果
     */
    public int deleteErpOutboundOrderDetailByOutboundId(Long outboundId);

    /**
     * 更新出库单明细实际数量
     * 
     * @param erpOutboundOrderDetail 出库单明细表
     * @return 结果
     */
    public int updateErpOutboundOrderDetailActualQuantity(ErpOutboundOrderDetail erpOutboundOrderDetail);
}
