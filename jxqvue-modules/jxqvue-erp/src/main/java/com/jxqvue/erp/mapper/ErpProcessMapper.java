package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcess;
import com.jxqvue.erp.domain.ErpProcessPara;

/**
 * 工序Mapper接口
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
public interface ErpProcessMapper 
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
     * 删除工序
     * 
     * @param id 工序主键
     * @return 结果
     */
    public int deleteErpProcessById(Long id);

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessByIds(Long[] ids);

    /**
     * 查询工艺参数明细列表
     * 
     * @param processId 工序ID
     * @return 工艺参数明细集合
     */
    public List<ErpProcessPara> selectErpProcessParaList(Long processId);
}
