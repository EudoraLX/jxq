-- 消息管理相关数据库表结构
-- 创建时间：2024-01-01
-- 说明：消息管理模块相关表结构

SET foreign_key_checks = 0;

-- 删除已存在的表
DROP TABLE IF EXISTS `message_receiver`;
DROP TABLE IF EXISTS `message`;
DROP TABLE IF EXISTS `message_template_variable`;
DROP TABLE IF EXISTS `message_template`;
DROP TABLE IF EXISTS `message_template_category`;
DROP TABLE IF EXISTS `message_statistics`;

-- 消息表
CREATE TABLE `message` (
  `message_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `title` varchar(200) NOT NULL COMMENT '消息标题',
  `content` text NOT NULL COMMENT '消息内容',
  `message_type` char(1) NOT NULL DEFAULT '1' COMMENT '消息类型（1：系统消息 2：业务消息 3：通知消息）',
  `message_level` char(1) NOT NULL DEFAULT '1' COMMENT '消息级别（1：普通 2：重要 3：紧急）',
  `sender_id` bigint(20) DEFAULT NULL COMMENT '发送者ID',
  `sender_name` varchar(50) DEFAULT NULL COMMENT '发送者名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '消息状态（0：未读 1：已读 2：已删除）',
  `business_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务ID',
  `push_status` char(1) NOT NULL DEFAULT '0' COMMENT '推送状态（0：未推送 1：已推送）',
  `push_time` datetime DEFAULT NULL COMMENT '推送时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`message_id`),
  KEY `idx_message_type` (`message_type`),
  KEY `idx_message_level` (`message_level`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_status` (`status`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息表';

-- 消息接收者表
CREATE TABLE `message_receiver` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `message_id` bigint(20) NOT NULL COMMENT '消息ID',
  `receiver_id` bigint(20) NOT NULL COMMENT '接收者ID',
  `receiver_name` varchar(50) DEFAULT NULL COMMENT '接收者名称',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '消息状态（0：未读 1：已读 2：已删除）',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`),
  KEY `idx_message_id` (`message_id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_status` (`status`),
  KEY `idx_read_time` (`read_time`),
  CONSTRAINT `fk_message_receiver_message` FOREIGN KEY (`message_id`) REFERENCES `message` (`message_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息接收者表';

-- 消息模板分类表
CREATE TABLE `message_template_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `category_code` varchar(50) NOT NULL COMMENT '分类编码',
  `parent_id` bigint(20) DEFAULT '0' COMMENT '父分类ID',
  `sort_order` int(11) DEFAULT '0' COMMENT '排序',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0：启用 1：禁用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uk_category_code` (`category_code`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息模板分类表';

-- 消息模板表
CREATE TABLE `message_template` (
  `template_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `template_name` varchar(100) NOT NULL COMMENT '模板名称',
  `template_code` varchar(50) NOT NULL COMMENT '模板编码',
  `template_type` varchar(50) DEFAULT NULL COMMENT '模板类型',
  `category_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `category_name` varchar(100) DEFAULT NULL COMMENT '分类名称',
  `title_template` text NOT NULL COMMENT '标题模板',
  `content_template` text NOT NULL COMMENT '内容模板',
  `message_type` char(1) NOT NULL DEFAULT '1' COMMENT '消息类型（1：系统消息 2：业务消息 3：通知消息）',
  `message_level` char(1) NOT NULL DEFAULT '1' COMMENT '消息级别（1：普通 2：重要 3：紧急）',
  `variables` text DEFAULT NULL COMMENT '变量定义',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0：启用 1：禁用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`template_id`),
  UNIQUE KEY `uk_template_code` (`template_code`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_message_type` (`message_type`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_message_template_category` FOREIGN KEY (`category_id`) REFERENCES `message_template_category` (`category_id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息模板表';

-- 消息模板变量表
CREATE TABLE `message_template_variable` (
  `variable_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '变量ID',
  `template_id` bigint(20) NOT NULL COMMENT '模板ID',
  `variable_name` varchar(100) NOT NULL COMMENT '变量名称',
  `variable_code` varchar(50) NOT NULL COMMENT '变量编码',
  `variable_type` varchar(20) NOT NULL COMMENT '变量类型',
  `variable_desc` varchar(200) DEFAULT NULL COMMENT '变量描述',
  `required` char(1) NOT NULL DEFAULT '0' COMMENT '是否必填（0：否 1：是）',
  `default_value` varchar(200) DEFAULT NULL COMMENT '默认值',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`variable_id`),
  KEY `idx_template_id` (`template_id`),
  KEY `idx_variable_code` (`variable_code`),
  CONSTRAINT `fk_message_template_variable_template` FOREIGN KEY (`template_id`) REFERENCES `message_template` (`template_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息模板变量表';

-- 消息统计表
CREATE TABLE `message_statistics` (
  `statistics_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '统计ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `total_count` int(11) NOT NULL DEFAULT '0' COMMENT '总消息数',
  `unread_count` int(11) NOT NULL DEFAULT '0' COMMENT '未读消息数',
  `read_count` int(11) NOT NULL DEFAULT '0' COMMENT '已读消息数',
  `system_count` int(11) NOT NULL DEFAULT '0' COMMENT '系统消息数',
  `business_count` int(11) NOT NULL DEFAULT '0' COMMENT '业务消息数',
  `notice_count` int(11) NOT NULL DEFAULT '0' COMMENT '通知消息数',
  `normal_count` int(11) NOT NULL DEFAULT '0' COMMENT '普通消息数',
  `important_count` int(11) NOT NULL DEFAULT '0' COMMENT '重要消息数',
  `urgent_count` int(11) NOT NULL DEFAULT '0' COMMENT '紧急消息数',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0' NOT NULL COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`statistics_id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_total_count` (`total_count`),
  KEY `idx_unread_count` (`unread_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息统计表';

SET foreign_key_checks = 1;

-- 插入初始数据
-- 插入消息模板分类
INSERT INTO `message_template_category` (`category_id`, `category_name`, `category_code`, `parent_id`, `sort_order`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '系统通知', 'SYSTEM_NOTICE', 0, 1, '0', 'admin', NOW(), '系统相关通知消息'),
(2, '业务提醒', 'BUSINESS_REMINDER', 0, 2, '0', 'admin', NOW(), '业务相关提醒消息'),
(3, '审批通知', 'APPROVAL_NOTICE', 0, 3, '0', 'admin', NOW(), '审批流程相关通知');

-- 插入消息模板
INSERT INTO `message_template` (`template_id`, `template_name`, `template_code`, `template_type`, `category_id`, `category_name`, `title_template`, `content_template`, `message_type`, `message_level`, `status`, `create_by`, `create_time`, `remark`) VALUES
(1, '系统维护通知', 'SYSTEM_MAINTENANCE', 'SYSTEM', 1, '系统通知', '系统维护通知', '系统将于{maintenance_time}进行维护，预计维护时间{estimated_duration}，请提前做好相关准备。', '1', '2', '0', 'admin', NOW(), '系统维护通知模板'),
(2, '订单状态变更', 'ORDER_STATUS_CHANGE', 'BUSINESS', 2, '业务提醒', '订单状态变更通知', '您的订单{order_no}状态已变更为{order_status}，请及时查看。', '2', '1', '0', 'admin', NOW(), '订单状态变更通知模板'),
(3, '审批流程通知', 'APPROVAL_PROCESS', 'APPROVAL', 3, '审批通知', '审批流程通知', '您有新的审批任务：{approval_title}，请及时处理。', '3', '2', '0', 'admin', NOW(), '审批流程通知模板');

-- 插入消息模板变量
INSERT INTO `message_template_variable` (`variable_id`, `template_id`, `variable_name`, `variable_code`, `variable_type`, `variable_desc`, `required`, `create_by`, `create_time`) VALUES
(1, 1, '维护时间', 'maintenance_time', 'datetime', '系统维护的具体时间', '1', 'admin', NOW()),
(2, 1, '预计维护时长', 'estimated_duration', 'string', '预计维护的时长', '1', 'admin', NOW()),
(3, 2, '订单号', 'order_no', 'string', '订单编号', '1', 'admin', NOW()),
(4, 2, '订单状态', 'order_status', 'string', '订单当前状态', '1', 'admin', NOW()),
(5, 3, '审批标题', 'approval_title', 'string', '审批任务的标题', '1', 'admin', NOW());
