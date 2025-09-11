package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessHandover;

/**
 * 工序交接单Service接口
 * 
 * @author jxqvue
 * @date 2025-01-31
 */
public interface IErpProcessHandoverService 
{
    /**
     * 查询工序交接单
     * 
     * @param id 工序交接单主键
     * @return 工序交接单
     */
    public ErpProcessHandover selectErpProcessHandoverById(Long id);

    /**
     * 查询工序交接单列表
     * 
     * @param erpProcessHandover 工序交接单
     * @return 工序交接单集合
     */
    public List<ErpProcessHandover> selectErpProcessHandoverList(ErpProcessHandover erpProcessHandover);

    /**
     * 新增工序交接单
     * 
     * @param erpProcessHandover 工序交接单
     * @return 结果
     */
    public int insertErpProcessHandover(ErpProcessHandover erpProcessHandover);

    /**
     * 修改工序交接单
     * 
     * @param erpProcessHandover 工序交接单
     * @return 结果
     */
    public int updateErpProcessHandover(ErpProcessHandover erpProcessHandover);

    /**
     * 批量删除工序交接单
     * 
     * @param ids 需要删除的工序交接单主键集合
     * @return 结果
     */
    public int deleteErpProcessHandoverByIds(Long[] ids);

    /**
     * 删除工序交接单信息
     * 
     * @param id 工序交接单主键
     * @return 结果
     */
    public int deleteErpProcessHandoverById(Long id);
}
