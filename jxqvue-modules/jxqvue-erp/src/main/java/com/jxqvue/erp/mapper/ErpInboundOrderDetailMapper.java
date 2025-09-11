package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInboundOrderDetail;

/**
 * 入库单明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInboundOrderDetailMapper 
{
    /**
     * 查询入库单明细表
     * 
     * @param id 入库单明细表主键
     * @return 入库单明细表
     */
    public ErpInboundOrderDetail selectErpInboundOrderDetailById(Long id);

    /**
     * 查询入库单明细表列表
     * 
     * @param erpInboundOrderDetail 入库单明细表
     * @return 入库单明细表集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailList(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 新增入库单明细表
     * 
     * @param erpInboundOrderDetail 入库单明细表
     * @return 结果
     */
    public int insertErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 修改入库单明细表
     * 
     * @param erpInboundOrderDetail 入库单明细表
     * @return 结果
     */
    public int updateErpInboundOrderDetail(ErpInboundOrderDetail erpInboundOrderDetail);

    /**
     * 删除入库单明细表
     * 
     * @param id 入库单明细表主键
     * @return 结果
     */
    public int deleteErpInboundOrderDetailById(Long id);

    /**
     * 批量删除入库单明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInboundOrderDetailByIds(Long[] ids);

    /**
     * 根据入库单ID查询明细列表
     * 
     * @param inboundId 入库单ID
     * @return 入库单明细表集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByInboundId(Long inboundId);

    /**
     * 根据入库单号查询明细列表
     * 
     * @param inboundNo 入库单号
     * @return 入库单明细表集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByInboundNo(String inboundNo);

    /**
     * 根据物料ID查询入库单明细列表
     * 
     * @param materialId 物料ID
     * @return 入库单明细表集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByMaterialId(Long materialId);

    /**
     * 根据批次号查询入库单明细列表
     * 
     * @param batchNo 批次号
     * @return 入库单明细表集合
     */
    public List<ErpInboundOrderDetail> selectErpInboundOrderDetailByBatchNo(String batchNo);

    /**
     * 根据序列号查询入库单明细
     * 
     * @param serialNo 序列号
     * @return 入库单明细表
     */
    public ErpInboundOrderDetail selectErpInboundOrderDetailBySerialNo(String serialNo);

    /**
     * 批量新增入库单明细
     * 
     * @param erpInboundOrderDetailList 入库单明细列表
     * @return 结果
     */
    public int batchInsertErpInboundOrderDetail(List<ErpInboundOrderDetail> erpInboundOrderDetailList);

    /**
     * 根据入库单ID删除明细
     * 
     * @param inboundId 入库单ID
     * @return 结果
     */
    public int deleteErpInboundOrderDetailByInboundId(Long inboundId);

    /**
     * 更新入库单明细实际数量
     * 
     * @param erpInboundOrderDetail 入库单明细表
     * @return 结果
     */
    public int updateErpInboundOrderDetailActualQuantity(ErpInboundOrderDetail erpInboundOrderDetail);
}
