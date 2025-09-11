package com.jxqvue.message.service;

import java.util.List;
import com.jxqvue.message.domain.MessageTemplate;

/**
 * 消息模板Service接口
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
public interface IMessageTemplateService 
{
    /**
     * 查询消息模板
     * 
     * @param templateId 消息模板主键
     * @return 消息模板
     */
    public MessageTemplate selectMessageTemplateByTemplateId(Long templateId);

    /**
     * 查询消息模板列表
     * 
     * @param messageTemplate 消息模板
     * @return 消息模板集合
     */
    public List<MessageTemplate> selectMessageTemplateList(MessageTemplate messageTemplate);

    /**
     * 根据模板编码查询模板
     * 
     * @param templateCode 模板编码
     * @return 消息模板
     */
    public MessageTemplate selectMessageTemplateByTemplateCode(String templateCode);

    /**
     * 新增消息模板
     * 
     * @param messageTemplate 消息模板
     * @return 结果
     */
    public int insertMessageTemplate(MessageTemplate messageTemplate);

    /**
     * 修改消息模板
     * 
     * @param messageTemplate 消息模板
     * @return 结果
     */
    public int updateMessageTemplate(MessageTemplate messageTemplate);

    /**
     * 批量删除消息模板
     * 
     * @param templateIds 需要删除的消息模板主键
     * @return 结果
     */
    public int deleteMessageTemplateByTemplateIds(Long[] templateIds);

    /**
     * 删除消息模板信息
     * 
     * @param templateId 消息模板主键
     * @return 结果
     */
    public int deleteMessageTemplateByTemplateId(Long templateId);
}
