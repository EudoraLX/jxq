-- 消息管理菜单权限配置
-- 创建时间：2024-01-01
-- 说明：消息管理模块相关菜单权限配置

-- 消息管理主菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息管理', 0, 5, 'message', null, 1, 0, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '消息管理目录');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 消息列表菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息列表', @parentId, '1', 'message', 'message/message/index', 1, 0, 'C', '0', '0', 'message:message:list', 'message', 'admin', sysdate(), '', null, '消息列表菜单');

-- 按钮父菜单ID
SELECT @messageId := LAST_INSERT_ID();

-- 消息列表按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息查询', @messageId, '1',  '#', '', 1, 0, 'F', '0', '0', 'message:message:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息新增', @messageId, '2',  '#', '', 1, 0, 'F', '0', '0', 'message:message:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息修改', @messageId, '3',  '#', '', 1, 0, 'F', '0', '0', 'message:message:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息删除', @messageId, '4',  '#', '', 1, 0, 'F', '0', '0', 'message:message:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息导出', @messageId, '5',  '#', '', 1, 0, 'F', '0', '0', 'message:message:export',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息发送', @messageId, '6',  '#', '', 1, 0, 'F', '0', '0', 'message:message:send',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息推送', @messageId, '7',  '#', '', 1, 0, 'F', '0', '0', 'message:message:push',         '#', 'admin', sysdate(), '', null, '');

-- 消息模板菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息模板', @parentId, '2', 'template', 'message/template/index', 1, 0, 'C', '0', '0', 'message:template:list', 'template', 'admin', sysdate(), '', null, '消息模板菜单');

-- 按钮父菜单ID
SELECT @templateId := LAST_INSERT_ID();

-- 消息模板按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模板查询', @templateId, '1',  '#', '', 1, 0, 'F', '0', '0', 'message:template:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模板新增', @templateId, '2',  '#', '', 1, 0, 'F', '0', '0', 'message:template:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模板修改', @templateId, '3',  '#', '', 1, 0, 'F', '0', '0', 'message:template:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模板删除', @templateId, '4',  '#', '', 1, 0, 'F', '0', '0', 'message:template:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('模板导出', @templateId, '5',  '#', '', 1, 0, 'F', '0', '0', 'message:template:export',       '#', 'admin', sysdate(), '', null, '');

-- 消息统计菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('消息统计', @parentId, '3', 'statistics', 'message/statistics/index', 1, 0, 'C', '0', '0', 'message:statistics:list', 'chart', 'admin', sysdate(), '', null, '消息统计菜单');

-- 按钮父菜单ID
SELECT @statisticsId := LAST_INSERT_ID();

-- 消息统计按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统计查询', @statisticsId, '1',  '#', '', 1, 0, 'F', '0', '0', 'message:statistics:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('统计导出', @statisticsId, '2',  '#', '', 1, 0, 'F', '0', '0', 'message:statistics:export',       '#', 'admin', sysdate(), '', null, '');