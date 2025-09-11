package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpInboundOrder;

/**
 * 入库单表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpInboundOrderMapper 
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
     * 删除入库单表
     * 
     * @param id 入库单表主键
     * @return 结果
     */
    public int deleteErpInboundOrderById(Long id);

    /**
     * 批量删除入库单表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpInboundOrderByIds(Long[] ids);

    /**
     * 根据入库单号查询入库单
     * 
     * @param inboundNo 入库单号
     * @return 入库单表
     */
    public ErpInboundOrder selectErpInboundOrderByInboundNo(String inboundNo);

    /**
     * 根据入库类型查询入库单列表
     * 
     * @param inboundType 入库类型
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderByType(String inboundType);

    /**
     * 根据仓库ID查询入库单列表
     * 
     * @param warehouseId 仓库ID
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderByWarehouseId(Long warehouseId);

    /**
     * 根据供应商ID查询入库单列表
     * 
     * @param supplierId 供应商ID
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderBySupplierId(Long supplierId);

    /**
     * 根据关联单据查询入库单列表
     * 
     * @param referenceType 关联单据类型
     * @param referenceId 关联单据ID
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderByReference(String referenceType, Long referenceId);

    /**
     * 根据状态查询入库单列表
     * 
     * @param status 状态
     * @return 入库单表集合
     */
    public List<ErpInboundOrder> selectErpInboundOrderByStatus(String status);

    /**
     * 更新入库单状态
     * 
     * @param erpInboundOrder 入库单表
     * @return 结果
     */
    public int updateErpInboundOrderStatus(ErpInboundOrder erpInboundOrder);

    /**
     * 查询入库单统计信息
     * 
     * @param erpInboundOrder 入库单表
     * @return 统计结果
     */
    public List<ErpInboundOrder> selectErpInboundOrderStatistics(ErpInboundOrder erpInboundOrder);
}
