package com.jxqvue.erp.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import com.jxqvue.common.utils.DateUtils;
import com.jxqvue.common.utils.StringUtils;
import com.jxqvue.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpBomMapper;
import com.jxqvue.erp.domain.ErpBom;
import com.jxqvue.erp.domain.ErpBomDetail;
import com.jxqvue.erp.domain.ErpBomRouteDetail;
import com.jxqvue.erp.service.IErpBomService;
import com.jxqvue.erp.utils.NumberGeneratorUtil;

/**
 * BOM主表Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
@Service
public class ErpBomServiceImpl implements IErpBomService 
{
    @Autowired
    private ErpBomMapper erpBomMapper;
    
    @Autowired
    private NumberGeneratorUtil numberGeneratorUtil;

    /**
     * 查询BOM主表
     * 
     * @param id BOM主表主键
     * @return BOM主表
     */
    @Override
    public ErpBom selectErpBomById(Long id)
    {
        return erpBomMapper.selectErpBomById(id);
    }

    /**
     * 查询BOM主表列表
     * 
     * @param erpBom BOM主表
     * @return BOM主表
     */
    @Override
    public List<ErpBom> selectErpBomList(ErpBom erpBom)
    {
        return erpBomMapper.selectErpBomList(erpBom);
    }

    /**
     * 新增BOM主表
     * 
     * @param erpBom BOM主表
     * @return 结果
     */
    @Transactional
    @Override
    public int insertErpBom(ErpBom erpBom)
    {
        // 自动生成BOM编号
        if (StringUtils.isEmpty(erpBom.getBomCode())) {
            String bomCode = numberGeneratorUtil.generateNumber("BOM");
            erpBom.setBomCode(bomCode);
        }
        
        erpBom.setCreateTime(DateUtils.getNowDate());
        int rows = erpBomMapper.insertErpBom(erpBom);
        insertErpBomDetail(erpBom);
        insertErpBomRouteDetail(erpBom);
        return rows;
    }

    /**
     * 修改BOM主表
     * 
     * @param erpBom BOM主表
     * @return 结果
     */
    @Transactional
    @Override
    public int updateErpBom(ErpBom erpBom)
    {
        erpBom.setUpdateTime(DateUtils.getNowDate());
        erpBomMapper.deleteErpBomDetailByBomId(erpBom.getId());
        insertErpBomDetail(erpBom);
        erpBomMapper.deleteErpBomRouteDetailByBomId(erpBom.getId());
        insertErpBomRouteDetail(erpBom);
        return erpBomMapper.updateErpBom(erpBom);
    }

    /**
     * 批量删除BOM主表
     * 
     * @param ids 需要删除的BOM主表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpBomByIds(Long[] ids)
    {
        erpBomMapper.deleteErpBomDetailByBomIds(ids);
        erpBomMapper.deleteErpBomRouteDetailByBomIds(ids);
        return erpBomMapper.deleteErpBomByIds(ids);
    }

    /**
     * 删除BOM主表信息
     * 
     * @param id BOM主表主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteErpBomById(Long id)
    {
        erpBomMapper.deleteErpBomDetailByBomId(id);
        erpBomMapper.deleteErpBomRouteDetailByBomId(id);
        return erpBomMapper.deleteErpBomById(id);
    }

    /**
     * 新增BOM明细信息
     * 
     * @param erpBom BOM主表对象
     */
    public void insertErpBomDetail(ErpBom erpBom)
    {
        List<ErpBomDetail> erpBomDetailList = erpBom.getErpBomDetailList();
        Long bomId = erpBom.getId();
        if (StringUtils.isNotNull(erpBomDetailList))
        {
            List<ErpBomDetail> list = new ArrayList<ErpBomDetail>();
            for (ErpBomDetail erpBomDetail : erpBomDetailList)
            {
                erpBomDetail.setBomId(bomId);
                list.add(erpBomDetail);
            }
            if (list.size() > 0)
            {
                erpBomMapper.batchErpBomDetail(list);
            }
        }
    }

    /**
     * 新增BOM工艺路线明细信息
     * 
     * @param erpBom BOM主表对象
     */
    public void insertErpBomRouteDetail(ErpBom erpBom)
    {
        List<ErpBomRouteDetail> erpBomRouteDetailList = erpBom.getErpBomRouteDetailList();
        Long bomId = erpBom.getId();
        if (StringUtils.isNotNull(erpBomRouteDetailList))
        {
            List<ErpBomRouteDetail> list = new ArrayList<ErpBomRouteDetail>();
            for (ErpBomRouteDetail erpBomRouteDetail : erpBomRouteDetailList)
            {
                erpBomRouteDetail.setBomId(bomId);
                list.add(erpBomRouteDetail);
            }
            if (list.size() > 0)
            {
                erpBomMapper.batchErpBomRouteDetail(list);
            }
        }
    }

    /**
     * 检查BOM冲突
     * 
     * @param productId 产品ID
     * @param effectiveDate 生效日期
     * @param excludeId 排除的BOM ID
     * @return 冲突的BOM列表
     */
    @Override
    public List<ErpBom> checkBomConflict(Long productId, Date effectiveDate, Long excludeId)
    {
        return erpBomMapper.checkBomConflict(productId, effectiveDate, excludeId);
    }

    /**
     * 审批BOM
     * 
     * @param bomId BOM ID
     * @param forceApprove 是否强制审批
     * @return 结果
     */
    @Transactional
    @Override
    public AjaxResult approveBom(Long bomId, Boolean forceApprove)
    {
        // 获取BOM信息
        ErpBom bom = erpBomMapper.selectErpBomById(bomId);
        if (bom == null) {
            return AjaxResult.error("BOM不存在");
        }

        // 检查是否已经审批
        if ("2".equals(bom.getApprovalStatus())) {
            return AjaxResult.error("BOM已经审批通过");
        }

        // 检查冲突（如果不强制审批）
        if (!forceApprove) {
            List<ErpBom> conflicts = erpBomMapper.checkBomConflict(bom.getProductId(), bom.getEffectiveDate(), bomId);
            if (!conflicts.isEmpty()) {
                return AjaxResult.error("存在冲突的BOM，请先处理冲突或选择强制审批");
            }
        } else {
            // 强制审批时，更新冲突BOM的失效日期
            List<ErpBom> conflicts = erpBomMapper.checkBomConflict(bom.getProductId(), bom.getEffectiveDate(), bomId);
            for (ErpBom conflictBom : conflicts) {
                erpBomMapper.updateBomExpiryDate(conflictBom.getId(), bom.getEffectiveDate());
            }
        }

        // 审批BOM
        int result = erpBomMapper.approveBom(bomId);
        if (result > 0) {
            return AjaxResult.success("审批成功");
        } else {
            return AjaxResult.error("审批失败");
        }
    }
}
