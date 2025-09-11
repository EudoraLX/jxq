-- AI聊天模块数据库表结构
-- 创建时间: 2024-01-01
-- 说明: 全局智能客服模块相关表

-- ----------------------------
-- 1. AI聊天会话表
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_session`;
CREATE TABLE `ai_chat_session` (
  `session_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '会话ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `session_name` varchar(100) DEFAULT NULL COMMENT '会话名称',
  `last_message_time` datetime DEFAULT NULL COMMENT '最后消息时间',
  `message_count` int(11) DEFAULT '0' COMMENT '消息数量',
  `status` char(1) DEFAULT '0' COMMENT '会话状态（0正常 1删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`session_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_last_message_time` (`last_message_time`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天会话表';

-- ----------------------------
-- 2. AI聊天消息表
-- ----------------------------
DROP TABLE IF EXISTS `ai_chat_message`;
CREATE TABLE `ai_chat_message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `session_id` bigint(20) NOT NULL COMMENT '会话ID',
  `role` varchar(20) NOT NULL COMMENT '角色（user用户 assistant助手 system系统）',
  `content` text NOT NULL COMMENT '消息内容',
  `status` char(1) DEFAULT '0' COMMENT '消息状态（0正常 1删除）',
  `message_type` varchar(20) DEFAULT 'text' COMMENT '消息类型（text文本 image图片 file文件）',
  `is_read` char(1) DEFAULT '0' COMMENT '是否已读（0未读 1已读）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`message_id`),
  KEY `idx_session_id` (`session_id`),
  KEY `idx_role` (`role`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`),
  KEY `idx_is_read` (`is_read`),
  CONSTRAINT `fk_message_session` FOREIGN KEY (`session_id`) REFERENCES `ai_chat_session` (`session_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI聊天消息表';

-- ----------------------------
-- 3. 插入菜单数据
-- ----------------------------
-- 主菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('AI智能客服', 0, 6, 'aichat', NULL, '', 1, 0, 'M', '0', '0', '', 'message', 'admin', NOW(), '', NULL, 'AI智能客服管理目录');

-- 获取刚插入的主菜单ID
SET @parent_menu_id = LAST_INSERT_ID();

-- 会话管理菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('会话管理', @parent_menu_id, 1, 'session', 'aichat/session/index', '', 1, 0, 'C', '0', '0', 'aichat:session:list', 'list', 'admin', NOW(), '', NULL, 'AI聊天会话管理菜单');

-- 消息管理菜单
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('消息管理', @parent_menu_id, 2, 'message', 'aichat/message/index', '', 1, 0, 'C', '0', '0', 'aichat:message:list', 'message', 'admin', NOW(), '', NULL, 'AI聊天消息管理菜单');

-- 获取会话管理菜单ID
SET @session_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '会话管理' AND parent_id = @parent_menu_id);

-- 会话管理按钮权限
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('会话查询', @session_menu_id, 1, '', '', '', 1, 0, 'F', '0', '0', 'aichat:session:query', '#', 'admin', NOW(), '', NULL, ''),
('会话新增', @session_menu_id, 2, '', '', '', 1, 0, 'F', '0', '0', 'aichat:session:add', '#', 'admin', NOW(), '', NULL, ''),
('会话修改', @session_menu_id, 3, '', '', '', 1, 0, 'F', '0', '0', 'aichat:session:edit', '#', 'admin', NOW(), '', NULL, ''),
('会话删除', @session_menu_id, 4, '', '', '', 1, 0, 'F', '0', '0', 'aichat:session:remove', '#', 'admin', NOW(), '', NULL, ''),
('会话导出', @session_menu_id, 5, '', '', '', 1, 0, 'F', '0', '0', 'aichat:session:export', '#', 'admin', NOW(), '', NULL, '');

-- 获取消息管理菜单ID
SET @message_menu_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '消息管理' AND parent_id = @parent_menu_id);

-- 消息管理按钮权限
INSERT INTO `sys_menu` (`menu_name`, `parent_id`, `order_num`, `path`, `component`, `query`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES
('消息查询', @message_menu_id, 1, '', '', '', 1, 0, 'F', '0', '0', 'aichat:message:query', '#', 'admin', NOW(), '', NULL, ''),
('消息新增', @message_menu_id, 2, '', '', '', 1, 0, 'F', '0', '0', 'aichat:message:add', '#', 'admin', NOW(), '', NULL, ''),
('消息修改', @message_menu_id, 3, '', '', '', 1, 0, 'F', '0', '0', 'aichat:message:edit', '#', 'admin', NOW(), '', NULL, ''),
('消息删除', @message_menu_id, 4, '', '', '', 1, 0, 'F', '0', '0', 'aichat:message:remove', '#', 'admin', NOW(), '', NULL, '');

-- ----------------------------
-- 4. 插入字典数据
-- ----------------------------
-- 会话状态字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
('会话状态', 'ai_chat_session_status', '0', 'admin', NOW(), 'AI聊天会话状态列表');

INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '正常', '0', 'ai_chat_session_status', '', 'success', 'Y', '0', 'admin', NOW(), '会话正常状态'),
(2, '删除', '1', 'ai_chat_session_status', '', 'danger', 'N', '0', 'admin', NOW(), '会话删除状态');

-- 消息角色字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
('消息角色', 'ai_chat_message_role', '0', 'admin', NOW(), 'AI聊天消息角色列表');

INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '用户', 'user', 'ai_chat_message_role', '', 'primary', 'Y', '0', 'admin', NOW(), '用户发送的消息'),
(2, '助手', 'assistant', 'ai_chat_message_role', '', 'success', 'N', '0', 'admin', NOW(), 'AI助手回复的消息'),
(3, '系统', 'system', 'ai_chat_message_role', '', 'info', 'N', '0', 'admin', NOW(), '系统消息');

-- 消息类型字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
('消息类型', 'ai_chat_message_type', '0', 'admin', NOW(), 'AI聊天消息类型列表');

INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '文本', 'text', 'ai_chat_message_type', '', 'primary', 'Y', '0', 'admin', NOW(), '文本消息'),
(2, '图片', 'image', 'ai_chat_message_type', '', 'success', 'N', '0', 'admin', NOW(), '图片消息'),
(3, '文件', 'file', 'ai_chat_message_type', '', 'info', 'N', '0', 'admin', NOW(), '文件消息');

-- 消息状态字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
('消息状态', 'ai_chat_message_status', '0', 'admin', NOW(), 'AI聊天消息状态列表');

INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '正常', '0', 'ai_chat_message_status', '', 'success', 'Y', '0', 'admin', NOW(), '消息正常状态'),
(2, '删除', '1', 'ai_chat_message_status', '', 'danger', 'N', '0', 'admin', NOW(), '消息删除状态');

-- 已读状态字典
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `remark`) VALUES
('已读状态', 'ai_chat_message_read', '0', 'admin', NOW(), 'AI聊天消息已读状态列表');

INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '未读', '0', 'ai_chat_message_read', '', 'warning', 'Y', '0', 'admin', NOW(), '消息未读状态'),
(2, '已读', '1', 'ai_chat_message_read', '', 'success', 'N', '0', 'admin', NOW(), '消息已读状态');
