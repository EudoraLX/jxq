package com.jxqvue.message.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jxqvue.common.annotation.Log;
import com.jxqvue.common.core.controller.BaseController;
import com.jxqvue.common.core.domain.AjaxResult;
import com.jxqvue.common.enums.BusinessType;
import com.jxqvue.message.domain.MessageTemplate;
import com.jxqvue.message.service.IMessageTemplateService;
import com.jxqvue.common.utils.poi.ExcelUtil;
import com.jxqvue.common.core.page.TableDataInfo;

/**
 * 消息模板Controller
 * 
 * @author jxqvue
 * @date 2024-01-01
 */
@RestController
@RequestMapping("/message/template")
public class MessageTemplateController extends BaseController
{
    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 查询消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(MessageTemplate messageTemplate)
    {
        startPage();
        List<MessageTemplate> list = messageTemplateService.selectMessageTemplateList(messageTemplate);
        return getDataTable(list);
    }

    /**
     * 导出消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:export')")
    @Log(title = "消息模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MessageTemplate messageTemplate)
    {
        List<MessageTemplate> list = messageTemplateService.selectMessageTemplateList(messageTemplate);
        ExcelUtil<MessageTemplate> util = new ExcelUtil<MessageTemplate>(MessageTemplate.class);
        util.exportExcel(response, list, "消息模板数据");
    }

    /**
     * 获取消息模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(messageTemplateService.selectMessageTemplateByTemplateId(templateId));
    }

    /**
     * 根据模板编码获取模板信息
     */
    @GetMapping(value = "/code/{templateCode}")
    public AjaxResult getInfoByCode(@PathVariable("templateCode") String templateCode)
    {
        return success(messageTemplateService.selectMessageTemplateByTemplateCode(templateCode));
    }

    /**
     * 新增消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:add')")
    @Log(title = "消息模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageTemplate messageTemplate)
    {
        return toAjax(messageTemplateService.insertMessageTemplate(messageTemplate));
    }

    /**
     * 修改消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:edit')")
    @Log(title = "消息模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageTemplate messageTemplate)
    {
        return toAjax(messageTemplateService.updateMessageTemplate(messageTemplate));
    }

    /**
     * 删除消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:remove')")
    @Log(title = "消息模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(messageTemplateService.deleteMessageTemplateByTemplateIds(templateIds));
    }
}
