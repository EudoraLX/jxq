package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpWarehouseMapper;
import com.jxqvue.erp.domain.ErpWarehouse;
import com.jxqvue.erp.service.IErpWarehouseService;

/**
 * 仓库Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Service
public class ErpWarehouseServiceImpl implements IErpWarehouseService 
{
    @Autowired
    private ErpWarehouseMapper erpWarehouseMapper;

    /**
     * 查询仓库
     * 
     * @param id 仓库主键
     * @return 仓库
     */
    @Override
    public ErpWarehouse selectErpWarehouseById(Long id)
    {
        return erpWarehouseMapper.selectErpWarehouseById(id);
    }

    /**
     * 查询仓库列表
     * 
     * @param erpWarehouse 仓库
     * @return 仓库
     */
    @Override
    public List<ErpWarehouse> selectErpWarehouseList(ErpWarehouse erpWarehouse)
    {
        return erpWarehouseMapper.selectErpWarehouseList(erpWarehouse);
    }

    /**
     * 新增仓库
     * 
     * @param erpWarehouse 仓库
     * @return 结果
     */
    @Override
    public int insertErpWarehouse(ErpWarehouse erpWarehouse)
    {
        erpWarehouse.setCreateTime(DateUtils.getNowDate());
        return erpWarehouseMapper.insertErpWarehouse(erpWarehouse);
    }

    /**
     * 修改仓库
     * 
     * @param erpWarehouse 仓库
     * @return 结果
     */
    @Override
    public int updateErpWarehouse(ErpWarehouse erpWarehouse)
    {
        erpWarehouse.setUpdateTime(DateUtils.getNowDate());
        return erpWarehouseMapper.updateErpWarehouse(erpWarehouse);
    }

    /**
     * 批量删除仓库
     * 
     * @param ids 需要删除的仓库主键
     * @return 结果
     */
    @Override
    public int deleteErpWarehouseByIds(Long[] ids)
    {
        return erpWarehouseMapper.deleteErpWarehouseByIds(ids);
    }

    /**
     * 删除仓库信息
     * 
     * @param id 仓库主键
     * @return 结果
     */
    @Override
    public int deleteErpWarehouseById(Long id)
    {
        return erpWarehouseMapper.deleteErpWarehouseById(id);
    }

    /**
     * 获取可用的仓库列表（已启用）
     * 
     * @return 仓库集合
     */
    @Override
    public List<ErpWarehouse> getAvailableWarehouses()
    {
        return erpWarehouseMapper.selectAvailableWarehouses();
    }
}
