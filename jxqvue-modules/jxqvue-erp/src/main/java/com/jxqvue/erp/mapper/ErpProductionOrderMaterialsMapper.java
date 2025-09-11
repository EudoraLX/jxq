package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProductionOrderMaterials;

/**
 * 生产订单物料Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface ErpProductionOrderMaterialsMapper 
{
    /**
     * 查询生产订单物料
     * 
     * @param id 生产订单物料主键
     * @return 生产订单物料
     */
    public ErpProductionOrderMaterials selectErpProductionOrderMaterialsById(Long id);

    /**
     * 查询生产订单物料列表
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 生产订单物料集合
     */
    public List<ErpProductionOrderMaterials> selectErpProductionOrderMaterialsList(ErpProductionOrderMaterials erpProductionOrderMaterials);

    /**
     * 根据订单ID查询生产订单物料列表
     * 
     * @param orderId 订单ID
     * @return 生产订单物料集合
     */
    public List<ErpProductionOrderMaterials> selectErpProductionOrderMaterialsByOrderId(Long orderId);

    /**
     * 新增生产订单物料
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 结果
     */
    public int insertErpProductionOrderMaterials(ErpProductionOrderMaterials erpProductionOrderMaterials);

    /**
     * 修改生产订单物料
     * 
     * @param erpProductionOrderMaterials 生产订单物料
     * @return 结果
     */
    public int updateErpProductionOrderMaterials(ErpProductionOrderMaterials erpProductionOrderMaterials);

    /**
     * 删除生产订单物料
     * 
     * @param id 生产订单物料主键
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsById(Long id);

    /**
     * 批量删除生产订单物料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsByIds(Long[] ids);

    /**
     * 根据订单ID删除生产订单物料
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsByOrderId(Long orderId);

    /**
     * 根据订单ID批量删除生产订单物料
     * 
     * @param orderIds 订单ID数组
     * @return 结果
     */
    public int deleteErpProductionOrderMaterialsByOrderIds(Long[] orderIds);

    /**
     * 批量新增生产订单物料
     * 
     * @param erpProductionOrderMaterialsList 生产订单物料列表
     * @return 结果
     */
    public int batchErpProductionOrderMaterials(List<ErpProductionOrderMaterials> erpProductionOrderMaterialsList);
}
