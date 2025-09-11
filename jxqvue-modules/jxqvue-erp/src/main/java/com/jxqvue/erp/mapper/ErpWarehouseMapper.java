package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpWarehouse;

/**
 * 仓库Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
public interface ErpWarehouseMapper 
{
    /**
     * 查询仓库
     * 
     * @param id 仓库主键
     * @return 仓库
     */
    public ErpWarehouse selectErpWarehouseById(Long id);

    /**
     * 查询仓库列表
     * 
     * @param erpWarehouse 仓库
     * @return 仓库集合
     */
    public List<ErpWarehouse> selectErpWarehouseList(ErpWarehouse erpWarehouse);

    /**
     * 新增仓库
     * 
     * @param erpWarehouse 仓库
     * @return 结果
     */
    public int insertErpWarehouse(ErpWarehouse erpWarehouse);

    /**
     * 修改仓库
     * 
     * @param erpWarehouse 仓库
     * @return 结果
     */
    public int updateErpWarehouse(ErpWarehouse erpWarehouse);

    /**
     * 删除仓库
     * 
     * @param id 仓库主键
     * @return 结果
     */
    public int deleteErpWarehouseById(Long id);

    /**
     * 批量删除仓库
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpWarehouseByIds(Long[] ids);

    /**
     * 获取可用的仓库列表（已启用）
     * 
     * @return 仓库集合
     */
    public List<ErpWarehouse> selectAvailableWarehouses();
}
