package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpShelf;

/**
 * 货架Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public interface ErpShelfMapper 
{
    /**
     * 查询货架
     * 
     * @param id 货架主键
     * @return 货架
     */
    public ErpShelf selectErpShelfById(Long id);

    /**
     * 查询货架列表
     * 
     * @param erpShelf 货架
     * @return 货架集合
     */
    public List<ErpShelf> selectErpShelfList(ErpShelf erpShelf);

    /**
     * 新增货架
     * 
     * @param erpShelf 货架
     * @return 结果
     */
    public int insertErpShelf(ErpShelf erpShelf);

    /**
     * 修改货架
     * 
     * @param erpShelf 货架
     * @return 结果
     */
    public int updateErpShelf(ErpShelf erpShelf);

    /**
     * 删除货架
     * 
     * @param id 货架主键
     * @return 结果
     */
    public int deleteErpShelfById(Long id);

    /**
     * 批量删除货架
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpShelfByIds(Long[] ids);

    /**
     * 根据仓库ID查询货架列表
     * 
     * @param warehouseId 仓库ID
     * @return 货架集合
     */
    public List<ErpShelf> selectErpShelfListByWarehouseId(Long warehouseId);

    /**
     * 获取可用的货架列表（已启用）
     * 
     * @return 货架集合
     */
    public List<ErpShelf> selectAvailableShelves();
}
