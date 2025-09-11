package com.jxqvue.erp.service.impl;

import java.util.List;
import com.jxqvue.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jxqvue.erp.mapper.ErpProcessMapper;
import com.jxqvue.erp.mapper.ErpProcessParaMapper;
import com.jxqvue.erp.domain.ErpProcess;
import com.jxqvue.erp.domain.ErpProcessPara;
import com.jxqvue.erp.service.IErpProcessService;

/**
 * 工序Service业务层处理
 * 
 * @author Gavin Zhang
 * @date 2025-01-31
 */
@Service
public class ErpProcessServiceImpl implements IErpProcessService 
{
    @Autowired
    private ErpProcessMapper erpProcessMapper;
    
    @Autowired
    private ErpProcessParaMapper erpProcessParaMapper;

    /**
     * 查询工序
     * 
     * @param id 工序主键
     * @return 工序
     */
    @Override
    public ErpProcess selectErpProcessById(Long id)
    {
        ErpProcess erpProcess = erpProcessMapper.selectErpProcessById(id);
        if (erpProcess != null) {
            // 查询工艺参数明细
            ErpProcessPara paraQuery = new ErpProcessPara();
            paraQuery.setProcessId(id);
            List<ErpProcessPara> paraList = erpProcessParaMapper.selectErpProcessParaList(paraQuery);
            erpProcess.setErpProcessParaList(paraList);
        }
        return erpProcess;
    }

    /**
     * 查询工序列表
     * 
     * @param erpProcess 工序
     * @return 工序
     */
    @Override
    public List<ErpProcess> selectErpProcessList(ErpProcess erpProcess)
    {
        return erpProcessMapper.selectErpProcessList(erpProcess);
    }

    /**
     * 新增工序
     * 
     * @param erpProcess 工序
     * @return 结果
     */
    @Override
    @Transactional
    public int insertErpProcess(ErpProcess erpProcess)
    {
        erpProcess.setCreateTime(DateUtils.getNowDate());
        int result = erpProcessMapper.insertErpProcess(erpProcess);
        
        // 插入工艺参数明细
        if (result > 0 && erpProcess.getErpProcessParaList() != null && !erpProcess.getErpProcessParaList().isEmpty()) {
            insertErpProcessPara(erpProcess);
        }
        
        return result;
    }

    /**
     * 修改工序
     * 
     * @param erpProcess 工序
     * @return 结果
     */
    @Override
    @Transactional
    public int updateErpProcess(ErpProcess erpProcess)
    {
        erpProcess.setUpdateTime(DateUtils.getNowDate());
        int result = erpProcessMapper.updateErpProcess(erpProcess);
        
        // 更新工艺参数明细
        if (result > 0) {
            // 先删除原有的工艺参数明细
            erpProcessParaMapper.deleteErpProcessParaByProcessId(erpProcess.getId());
            // 再插入新的工艺参数明细
            if (erpProcess.getErpProcessParaList() != null && !erpProcess.getErpProcessParaList().isEmpty()) {
                insertErpProcessPara(erpProcess);
            }
        }
        
        return result;
    }

    /**
     * 批量删除工序
     * 
     * @param ids 需要删除的工序主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessByIds(Long[] ids)
    {
        return erpProcessMapper.deleteErpProcessByIds(ids);
    }

    /**
     * 删除工序信息
     * 
     * @param id 工序主键
     * @return 结果
     */
    @Override
    public int deleteErpProcessById(Long id)
    {
        return erpProcessMapper.deleteErpProcessById(id);
    }
    
    /**
     * 插入工艺参数明细
     * 
     * @param erpProcess 工序对象
     */
    private void insertErpProcessPara(ErpProcess erpProcess)
    {
        List<ErpProcessPara> erpProcessParaList = erpProcess.getErpProcessParaList();
        for (ErpProcessPara erpProcessPara : erpProcessParaList)
        {
            erpProcessPara.setProcessId(erpProcess.getId());
            erpProcessPara.setCreateTime(DateUtils.getNowDate());
            erpProcessParaMapper.insertErpProcessPara(erpProcessPara);
        }
    }
}
