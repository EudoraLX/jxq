package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpShelfMapper;
import com.jxqvue.erp.domain.ErpShelf;
import com.jxqvue.erp.service.IErpShelfService;

/**
 * 货架Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-12-20
 */
@Service
public class ErpShelfServiceImpl implements IErpShelfService 
{
    @Autowired
    private ErpShelfMapper erpShelfMapper;

    /**
     * 查询货架
     * 
     * @param id 货架主键
     * @return 货架
     */
    @Override
    public ErpShelf selectErpShelfById(Long id)
    {
        return erpShelfMapper.selectErpShelfById(id);
    }

    /**
     * 查询货架列表
     * 
     * @param erpShelf 货架
     * @return 货架
     */
    @Override
    public List<ErpShelf> selectErpShelfList(ErpShelf erpShelf)
    {
        return erpShelfMapper.selectErpShelfList(erpShelf);
    }

    /**
     * 新增货架
     * 
     * @param erpShelf 货架
     * @return 结果
     */
    @Override
    public int insertErpShelf(ErpShelf erpShelf)
    {
        erpShelf.setCreateTime(DateUtils.getNowDate());
        return erpShelfMapper.insertErpShelf(erpShelf);
    }

    /**
     * 修改货架
     * 
     * @param erpShelf 货架
     * @return 结果
     */
    @Override
    public int updateErpShelf(ErpShelf erpShelf)
    {
        erpShelf.setUpdateTime(DateUtils.getNowDate());
        return erpShelfMapper.updateErpShelf(erpShelf);
    }

    /**
     * 批量删除货架
     * 
     * @param ids 需要删除的货架主键
     * @return 结果
     */
    @Override
    public int deleteErpShelfByIds(Long[] ids)
    {
        return erpShelfMapper.deleteErpShelfByIds(ids);
    }

    /**
     * 删除货架信息
     * 
     * @param id 货架主键
     * @return 结果
     */
    @Override
    public int deleteErpShelfById(Long id)
    {
        return erpShelfMapper.deleteErpShelfById(id);
    }

    /**
     * 根据仓库ID查询货架列表
     * 
     * @param warehouseId 仓库ID
     * @return 货架集合
     */
    @Override
    public List<ErpShelf> selectErpShelfListByWarehouseId(Long warehouseId)
    {
        return erpShelfMapper.selectErpShelfListByWarehouseId(warehouseId);
    }

    /**
     * 获取可用的货架列表（已启用）
     * 
     * @return 货架集合
     */
    @Override
    public List<ErpShelf> getAvailableShelves()
    {
        return erpShelfMapper.selectAvailableShelves();
    }
}
