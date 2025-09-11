package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpBomDetail;

/**
 * BOM明细表Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-27
 */
public interface ErpBomDetailMapper 
{
    /**
     * 查询BOM明细表
     * 
     * @param id BOM明细表主键
     * @return BOM明细表
     */
    public ErpBomDetail selectErpBomDetailById(Long id);

    /**
     * 查询BOM明细表列表
     * 
     * @param erpBomDetail BOM明细表
     * @return BOM明细表集合
     */
    public List<ErpBomDetail> selectErpBomDetailList(ErpBomDetail erpBomDetail);

    /**
     * 新增BOM明细表
     * 
     * @param erpBomDetail BOM明细表
     * @return 结果
     */
    public int insertErpBomDetail(ErpBomDetail erpBomDetail);

    /**
     * 修改BOM明细表
     * 
     * @param erpBomDetail BOM明细表
     * @return 结果
     */
    public int updateErpBomDetail(ErpBomDetail erpBomDetail);

    /**
     * 删除BOM明细表
     * 
     * @param id BOM明细表主键
     * @return 结果
     */
    public int deleteErpBomDetailById(Long id);

    /**
     * 批量删除BOM明细表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpBomDetailByIds(Long[] ids);
}
