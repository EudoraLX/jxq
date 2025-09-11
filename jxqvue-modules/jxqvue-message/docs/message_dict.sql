-- 消息管理数据字典配置
-- 创建时间：2024-01-01
-- 说明：消息管理模块相关数据字典配置

-- 消息类型字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '消息类型', 'message_type', '0', 'admin', NOW(), '消息类型列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '系统消息', '1', 'message_type', '', 'primary', 'Y', '0', 'admin', NOW(), '系统相关消息'),
(NULL, 2, '业务消息', '2', 'message_type', '', 'success', 'N', '0', 'admin', NOW(), '业务相关消息'),
(NULL, 3, '通知消息', '3', 'message_type', '', 'info', 'N', '0', 'admin', NOW(), '通知相关消息');

-- 消息级别字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '消息级别', 'message_level', '0', 'admin', NOW(), '消息级别列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '普通', '1', 'message_level', '', 'info', 'Y', '0', 'admin', NOW(), '普通级别消息'),
(NULL, 2, '重要', '2', 'message_level', '', 'warning', 'N', '0', 'admin', NOW(), '重要级别消息'),
(NULL, 3, '紧急', '3', 'message_level', '', 'danger', 'N', '0', 'admin', NOW(), '紧急级别消息');

-- 消息状态字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '消息状态', 'message_status', '0', 'admin', NOW(), '消息状态列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '未读', '0', 'message_status', '', 'danger', 'Y', '0', 'admin', NOW(), '未读状态'),
(NULL, 2, '已读', '1', 'message_status', '', 'success', 'N', '0', 'admin', NOW(), '已读状态'),
(NULL, 3, '已删除', '2', 'message_status', '', 'info', 'N', '0', 'admin', NOW(), '已删除状态');

-- 推送状态字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '推送状态', 'push_status', '0', 'admin', NOW(), '推送状态列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '未推送', '0', 'push_status', '', 'info', 'Y', '0', 'admin', NOW(), '未推送状态'),
(NULL, 2, '已推送', '1', 'push_status', '', 'success', 'N', '0', 'admin', NOW(), '已推送状态');

-- 模板类型字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '模板类型', 'template_type', '0', 'admin', NOW(), '消息模板类型列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '系统模板', 'SYSTEM', 'template_type', '', 'primary', 'Y', '0', 'admin', NOW(), '系统相关模板'),
(NULL, 2, '业务模板', 'BUSINESS', 'template_type', '', 'success', 'N', '0', 'admin', NOW(), '业务相关模板'),
(NULL, 3, '审批模板', 'APPROVAL', 'template_type', '', 'warning', 'N', '0', 'admin', NOW(), '审批相关模板'),
(NULL, 4, '通知模板', 'NOTICE', 'template_type', '', 'info', 'N', '0', 'admin', NOW(), '通知相关模板');

-- 变量类型字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '变量类型', 'variable_type', '0', 'admin', NOW(), '模板变量类型列表');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '字符串', 'string', 'variable_type', '', 'info', 'Y', '0', 'admin', NOW(), '字符串类型变量'),
(NULL, 2, '数字', 'number', 'variable_type', '', 'primary', 'N', '0', 'admin', NOW(), '数字类型变量'),
(NULL, 3, '日期时间', 'datetime', 'variable_type', '', 'success', 'N', '0', 'admin', NOW(), '日期时间类型变量'),
(NULL, 4, '布尔值', 'boolean', 'variable_type', '', 'warning', 'N', '0', 'admin', NOW(), '布尔值类型变量');

-- 是否必填字典
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, '是否必填', 'yes_no', '0', 'admin', NOW(), '是否必填选项');

INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(NULL, 1, '是', '1', 'yes_no', '', 'danger', 'N', '0', 'admin', NOW(), '是'),
(NULL, 2, '否', '0', 'yes_no', '', 'success', 'Y', '0', 'admin', NOW(), '否');
