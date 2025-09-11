package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.jxqvue.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.domain.ErpMoldDetail;
import com.jxqvue.erp.mapper.ErpMoldMapper;
import com.jxqvue.erp.domain.ErpMold;
import com.jxqvue.erp.service.IErpMoldService;

/**
 * 模具Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpMoldServiceImpl implements IErpMoldService 
{
    @Autowired
    private ErpMoldMapper erpMoldMapper;

    /**
     * 查询模具
     * 
     * @param id 模具主键
     * @return 模具
     */
    @Override
    public ErpMold selectErpMoldById(Long id)
    {
        return erpMoldMapper.selectErpMoldById(id);
    }

    /**
     * 查询模具列表
     * 
     * @param erpMold 模具
     * @return 模具
     */
    @Override
    public List<ErpMold> selectErpMoldList(ErpMold erpMold)
    {
        return erpMoldMapper.selectErpMoldList(erpMold);
    }

    /**
     * 新增模具
     * 
     * @param erpMold 模具
     * @return 结果
     */
    @Transactional
    @Override
    public int insertErpMold(ErpMold erpMold)
    {
        erpMold.setCreateTime(DateUtils.getNowDate());
        int rows = erpMoldMapper.insertErpMold(erpMold);
        insertErpMoldDetail(erpMold);
        return rows;
    }

    /**
     * 修改模具
     * 
     * @param erpMold 模具
     * @return 结果
     */
    @Transactional
    @Override
    public int updateErpMold(ErpMold erpMold)
    {
        erpMold.setUpdateTime(DateUtils.getNowDate());
        erpMoldMapper.deleteErpMoldDetailByMoldId(erpMold.getId());
        insertErpMoldDetail(erpMold);
        return erpMoldMapper.updateErpMold(erpMold);
    }

    /**
     * 批量删除模具
     * 
     * @param ids 需要删除的模具主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpMoldByIds(Long[] ids)
    {
        erpMoldMapper.deleteErpMoldDetailByMoldIds(ids);
        return erpMoldMapper.deleteErpMoldByIds(ids);
    }

    /**
     * 删除模具信息
     * 
     * @param id 模具主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpMoldById(Long id)
    {
        erpMoldMapper.deleteErpMoldDetailByMoldId(id);
        return erpMoldMapper.deleteErpMoldById(id);
    }

    /**
     * 新增模具明细信息
     * 
     * @param erpMold 模具对象
     */
    public void insertErpMoldDetail(ErpMold erpMold)
    {
        List<ErpMoldDetail> erpMoldDetailList = erpMold.getErpMoldDetailList();
        Long id = erpMold.getId();
        if (StringUtils.isNotNull(erpMoldDetailList))
        {
            List<ErpMoldDetail> list = new ArrayList<ErpMoldDetail>();
            for (ErpMoldDetail erpMoldDetail : erpMoldDetailList)
            {
                erpMoldDetail.setMoldId(id);
                list.add(erpMoldDetail);
            }
            if (list.size() > 0)
            {
                erpMoldMapper.batchErpMoldDetail(list);
            }
        }
    }
}
