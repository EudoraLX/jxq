package com.jxqvue.message.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jxqvue.message.mapper.MessageTemplateMapper;
import com.jxqvue.message.domain.MessageTemplate;
import com.jxqvue.message.service.IMessageTemplateService;

/**
 * 消息模板Service业务层处理
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@Service
public class MessageTemplateServiceImpl implements IMessageTemplateService 
{
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 查询消息模板
     * 
     * @param templateId 消息模板主键
     * @return 消息模板
     */
    @Override
    public MessageTemplate selectMessageTemplateByTemplateId(Long templateId)
    {
        return messageTemplateMapper.selectMessageTemplateByTemplateId(templateId);
    }

    /**
     * 查询消息模板列表
     * 
     * @param messageTemplate 消息模板
     * @return 消息模板
     */
    @Override
    public List<MessageTemplate> selectMessageTemplateList(MessageTemplate messageTemplate)
    {
        return messageTemplateMapper.selectMessageTemplateList(messageTemplate);
    }

    /**
     * 根据模板编码查询模板
     * 
     * @param templateCode 模板编码
     * @return 消息模板
     */
    @Override
    public MessageTemplate selectMessageTemplateByTemplateCode(String templateCode)
    {
        return messageTemplateMapper.selectMessageTemplateByTemplateCode(templateCode);
    }

    /**
     * 新增消息模板
     * 
     * @param messageTemplate 消息模板
     * @return 结果
     */
    @Override
    public int insertMessageTemplate(MessageTemplate messageTemplate)
    {
        messageTemplate.setCreateTime(new Date());
        return messageTemplateMapper.insertMessageTemplate(messageTemplate);
    }

    /**
     * 修改消息模板
     * 
     * @param messageTemplate 消息模板
     * @return 结果
     */
    @Override
    public int updateMessageTemplate(MessageTemplate messageTemplate)
    {
        messageTemplate.setUpdateTime(new Date());
        return messageTemplateMapper.updateMessageTemplate(messageTemplate);
    }

    /**
     * 批量删除消息模板
     * 
     * @param templateIds 需要删除的消息模板主键
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByTemplateIds(Long[] templateIds)
    {
        return messageTemplateMapper.deleteMessageTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除消息模板信息
     * 
     * @param templateId 消息模板主键
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByTemplateId(Long templateId)
    {
        return messageTemplateMapper.deleteMessageTemplateByTemplateId(templateId);
    }
}
