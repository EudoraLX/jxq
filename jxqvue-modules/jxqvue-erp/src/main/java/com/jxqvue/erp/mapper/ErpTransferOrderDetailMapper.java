package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpTransferOrderDetail;

/**
 * 调拨单明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpTransferOrderDetailMapper 
{
    /**
     * 查询调拨单明细表
     * 
     * @param id 调拨单明细表主键
     * @return 调拨单明细表
     */
    public ErpTransferOrderDetail selectErpTransferOrderDetailById(Long id);

    /**
     * 查询调拨单明细表列表
     * 
     * @param erpTransferOrderDetail 调拨单明细表
     * @return 调拨单明细表集合
     */
    public List<ErpTransferOrderDetail> selectErpTransferOrderDetailList(ErpTransferOrderDetail erpTransferOrderDetail);

    /**
     * 新增调拨单明细表
     * 
     * @param erpTransferOrderDetail 调拨单明细表
     * @return 结果
     */
    public int insertErpTransferOrderDetail(ErpTransferOrderDetail erpTransferOrderDetail);

    /**
     * 修改调拨单明细表
     * 
     * @param erpTransferOrderDetail 调拨单明细表
     * @return 结果
     */
    public int updateErpTransferOrderDetail(ErpTransferOrderDetail erpTransferOrderDetail);

    /**
     * 删除调拨单明细表
     * 
     * @param id 调拨单明细表主键
     * @return 结果
     */
    public int deleteErpTransferOrderDetailById(Long id);

    /**
     * 批量删除调拨单明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpTransferOrderDetailByIds(Long[] ids);

    /**
     * 根据调拨单ID查询明细列表
     * 
     * @param transferId 调拨单ID
     * @return 调拨单明细表集合
     */
    public List<ErpTransferOrderDetail> selectErpTransferOrderDetailByTransferId(Long transferId);

    /**
     * 根据调拨单号查询明细列表
     * 
     * @param transferNo 调拨单号
     * @return 调拨单明细表集合
     */
    public List<ErpTransferOrderDetail> selectErpTransferOrderDetailByTransferNo(String transferNo);

    /**
     * 根据物料ID查询调拨单明细列表
     * 
     * @param materialId 物料ID
     * @return 调拨单明细表集合
     */
    public List<ErpTransferOrderDetail> selectErpTransferOrderDetailByMaterialId(Long materialId);

    /**
     * 根据批次号查询调拨单明细列表
     * 
     * @param batchNo 批次号
     * @return 调拨单明细表集合
     */
    public List<ErpTransferOrderDetail> selectErpTransferOrderDetailByBatchNo(String batchNo);

    /**
     * 根据序列号查询调拨单明细
     * 
     * @param serialNo 序列号
     * @return 调拨单明细表
     */
    public ErpTransferOrderDetail selectErpTransferOrderDetailBySerialNo(String serialNo);

    /**
     * 批量新增调拨单明细
     * 
     * @param erpTransferOrderDetailList 调拨单明细列表
     * @return 结果
     */
    public int batchInsertErpTransferOrderDetail(List<ErpTransferOrderDetail> erpTransferOrderDetailList);

    /**
     * 根据调拨单ID删除明细
     * 
     * @param transferId 调拨单ID
     * @return 结果
     */
    public int deleteErpTransferOrderDetailByTransferId(Long transferId);

    /**
     * 更新调拨单明细实际数量
     * 
     * @param erpTransferOrderDetail 调拨单明细表
     * @return 结果
     */
    public int updateErpTransferOrderDetailActualQuantity(ErpTransferOrderDetail erpTransferOrderDetail);
}
