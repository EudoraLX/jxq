package com.jxqvue.erp.mapper;

import java.util.List;
import com.jxqvue.erp.domain.ErpProcessPara;

/**
 * 工艺参数明细Mapper接口
 * 
 * @author jxqvue
 * @date 2024-12-19
 */
public interface ErpProcessParaMapper 
{
    /**
     * 查询工艺参数明细
     * 
     * @param id 工艺参数明细主键
     * @return 工艺参数明细
     */
    public ErpProcessPara selectErpProcessParaById(Long id);

    /**
     * 查询工艺参数明细列表
     * 
     * @param erpProcessPara 工艺参数明细
     * @return 工艺参数明细集合
     */
    public List<ErpProcessPara> selectErpProcessParaList(ErpProcessPara erpProcessPara);

    /**
     * 根据工序ID查询工艺参数明细列表
     * 
     * @param processId 工序ID
     * @return 工艺参数明细集合
     */
    public List<ErpProcessPara> selectErpProcessParaListByProcessId(Long processId);

    /**
     * 新增工艺参数明细
     * 
     * @param erpProcessPara 工艺参数明细
     * @return 结果
     */
    public int insertErpProcessPara(ErpProcessPara erpProcessPara);

    /**
     * 修改工艺参数明细
     * 
     * @param erpProcessPara 工艺参数明细
     * @return 结果
     */
    public int updateErpProcessPara(ErpProcessPara erpProcessPara);

    /**
     * 删除工艺参数明细
     * 
     * @param id 工艺参数明细主键
     * @return 结果
     */
    public int deleteErpProcessParaById(Long id);

    /**
     * 批量删除工艺参数明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteErpProcessParaByIds(Long[] ids);

    /**
     * 根据工序ID删除工艺参数明细
     * 
     * @param processId 工序ID
     * @return 结果
     */
    public int deleteErpProcessParaByProcessId(Long processId);
}

