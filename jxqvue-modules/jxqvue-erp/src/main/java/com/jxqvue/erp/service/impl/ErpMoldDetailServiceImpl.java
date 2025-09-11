package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.erp.mapper.ErpMoldDetailMapper;
import com.jxqvue.erp.domain.ErpMoldDetail;
import com.jxqvue.erp.service.IErpMoldDetailService;

/**
 * 模具明细Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-09-01
 */
@Service
public class ErpMoldDetailServiceImpl implements IErpMoldDetailService 
{
    @Autowired
    private ErpMoldDetailMapper erpMoldDetailMapper;

    /**
     * 查询模具明细
     * 
     * @param id 模具明细主键
     * @return 模具明细
     */
    @Override
    public ErpMoldDetail selectErpMoldDetailById(Long id)
    {
        return erpMoldDetailMapper.selectErpMoldDetailById(id);
    }

    /**
     * 查询模具明细列表
     * 
     * @param erpMoldDetail 模具明细
     * @return 模具明细
     */
    @Override
    public List<ErpMoldDetail> selectErpMoldDetailList(ErpMoldDetail erpMoldDetail)
    {
        return erpMoldDetailMapper.selectErpMoldDetailList(erpMoldDetail);
    }

    /**
     * 新增模具明细
     * 
     * @param erpMoldDetail 模具明细
     * @return 结果
     */
    @Override
    public int insertErpMoldDetail(ErpMoldDetail erpMoldDetail)
    {
        erpMoldDetail.setCreateTime(DateUtils.getNowDate());
        return erpMoldDetailMapper.insertErpMoldDetail(erpMoldDetail);
    }

    /**
     * 修改模具明细
     * 
     * @param erpMoldDetail 模具明细
     * @return 结果
     */
    @Override
    public int updateErpMoldDetail(ErpMoldDetail erpMoldDetail)
    {
        erpMoldDetail.setUpdateTime(DateUtils.getNowDate());
        return erpMoldDetailMapper.updateErpMoldDetail(erpMoldDetail);
    }

    /**
     * 批量删除模具明细
     * 
     * @param ids 需要删除的模具明细主键
     * @return 结果
     */
    @Override
    public int deleteErpMoldDetailByIds(Long[] ids)
    {
        return erpMoldDetailMapper.deleteErpMoldDetailByIds(ids);
    }

    /**
     * 删除模具明细信息
     * 
     * @param id 模具明细主键
     * @return 结果
     */
    @Override
    public int deleteErpMoldDetailById(Long id)
    {
        return erpMoldDetailMapper.deleteErpMoldDetailById(id);
    }
}
