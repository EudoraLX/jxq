package com.jxqvue.erp.service;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcess;

/**
 * 工序Service接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface IErpProcessService 
{
    /**
     * 查询工序
     * 
     * @param id 工序主键
     * @return 工序
     */
    public ErpProcess selectErpProcessById(Long id);

    /**
     * 查询工序列表
     * 
     * @param erpProcess 工序
     * @return 工序集合
     */
    public List<ErpProcess> selectErpProcessList(ErpProcess erpProcess);

    /**
     * 新增工序
     * 
     * @param erpProcess 工序
     * @return 结果
     */
    public int insertErpProcess(ErpProcess erpProcess);

    /**
     * 修改工序
     * 
     * @param erpProcess 工序
     * @return 结果
     */
    public int updateErpProcess(ErpProcess erpProcess);

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的工序主键集合
     * @return 结果
     */
    public int deleteErpProcessByIds(Long[] ids);

    /**
     * 删除工序信息
     * 
     * @param id 工序主键
     * @return 结果
     */
    public int deleteErpProcessById(Long id);
}
