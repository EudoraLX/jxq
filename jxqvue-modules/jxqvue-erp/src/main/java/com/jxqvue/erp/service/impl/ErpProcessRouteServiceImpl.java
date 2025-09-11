package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpProcessRouteMapper;
import com.jxqvue.erp.domain.ErpProcessRoute;
import com.jxqvue.erp.domain.ErpProcessRouteDetail;
import com.jxqvue.erp.service.IErpProcessRouteService;

/**
 * 工艺路线主表Service业务层处理
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
@Service
public class ErpProcessRouteServiceImpl implements IErpProcessRouteService 
{
    @Autowired
    private ErpProcessRouteMapper erpProcessRouteMapper;

    /**
     * 查询工艺路线主表
     * 
     * @param id 工艺路线主表主键
     * @return 工艺路线主表
     */
    @Override
    public ErpProcessRoute selectErpProcessRouteById(Long id)
    {
        return erpProcessRouteMapper.selectErpProcessRouteById(id);
    }

    /**
     * 查询工艺路线主表列表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 工艺路线主表
     */
    @Override
    public List<ErpProcessRoute> selectErpProcessRouteList(ErpProcessRoute erpProcessRoute)
    {
        return erpProcessRouteMapper.selectErpProcessRouteList(erpProcessRoute);
    }

    /**
     * 新增工艺路线主表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpProcessRoute(ErpProcessRoute erpProcessRoute)
    {
        erpProcessRoute.setCreateTime(DateUtils.getNowDate());
        
        // 插入主表
        int result = erpProcessRouteMapper.insertErpProcessRoute(erpProcessRoute);
        
        // 处理明细表
        if (erpProcessRoute.getErpProcessRouteDetailList() != null && !erpProcessRoute.getErpProcessRouteDetailList().isEmpty()) {
            List<ErpProcessRouteDetail> detailList = erpProcessRoute.getErpProcessRouteDetailList();
            for (ErpProcessRouteDetail detail : detailList) {
                detail.setProcessRouteId(erpProcessRoute.getId());
                detail.setCreateTime(DateUtils.getNowDate());
                detail.setUpdateTime(DateUtils.getNowDate());
                detail.setDelFlag("0");
            }
            erpProcessRouteMapper.batchErpProcessRouteDetail(detailList);
        }
        
        return result;
    }

    /**
     * 修改工艺路线主表
     * 
     * @param erpProcessRoute 工艺路线主表
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpProcessRoute(ErpProcessRoute erpProcessRoute)
    {
        erpProcessRoute.setUpdateTime(DateUtils.getNowDate());
        
        // 更新主表
        int result = erpProcessRouteMapper.updateErpProcessRoute(erpProcessRoute);
        
        // 处理明细表
        if (erpProcessRoute.getErpProcessRouteDetailList() != null && !erpProcessRoute.getErpProcessRouteDetailList().isEmpty()) {
            // 先删除原有明细
            erpProcessRouteMapper.deleteErpProcessRouteDetailByProcessRouteId(erpProcessRoute.getId());
            
            // 重新插入明细
            List<ErpProcessRouteDetail> detailList = erpProcessRoute.getErpProcessRouteDetailList();
            for (ErpProcessRouteDetail detail : detailList) {
                detail.setProcessRouteId(erpProcessRoute.getId());
                detail.setCreateTime(DateUtils.getNowDate());
                detail.setUpdateTime(DateUtils.getNowDate());
                detail.setDelFlag("0");
            }
            erpProcessRouteMapper.batchErpProcessRouteDetail(detailList);
        }
        
        return result;
    }

    /**
     * 批量删除工艺路线主表
     * 
     * @param ids 需要删除的工艺路线主表主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessRouteByIds(Long[] ids)
    {
        erpProcessRouteMapper.deleteErpProcessRouteDetailByProcessRouteIds(ids);
        return erpProcessRouteMapper.deleteErpProcessRouteByIds(ids);
    }

    /**
     * 删除工艺路线主表信息
     * 
     * @param id 工艺路线主表主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessRouteById(Long id)
    {
        erpProcessRouteMapper.deleteErpProcessRouteDetailByProcessRouteId(id);
        return erpProcessRouteMapper.deleteErpProcessRouteById(id);
    }
}
