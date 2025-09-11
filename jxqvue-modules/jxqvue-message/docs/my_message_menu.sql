-- 我的消息菜单权限SQL脚本
-- 注意：请根据实际需要调整菜单ID和权限前缀

-- 1. 添加我的消息菜单
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('我的消息', 2240, 4, 'myMessage', 'message/myMessage/index', '', 1, 0, 'C', '0', '0', 'message:my:view', 'message', 'admin', sysdate(), '我的消息菜单');

-- 获取刚插入的菜单ID
SET @myMessageMenuId = LAST_INSERT_ID();

-- 2. 添加我的消息相关权限
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('我的消息查询', @myMessageMenuId, 1, '', '', '', 1, 0, 'F', '0', '0', 'message:my:query', '#', 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('我的消息列表', @myMessageMenuId, 2, '', '', '', 1, 0, 'F', '0', '0', 'message:my:list', '#', 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('我的消息标记已读', @myMessageMenuId, 3, '', '', '', 1, 0, 'F', '0', '0', 'message:my:read', '#', 'admin', sysdate(), '');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES ('我的消息删除', @myMessageMenuId, 4, '', '', '', 1, 0, 'F', '0', '0', 'message:my:remove', '#', 'admin', sysdate(), '');

-- 3. 为管理员角色分配权限
INSERT INTO sys_role_menu (role_id, menu_id) 
SELECT 1, menu_id FROM sys_menu WHERE perms IN (
    'message:my:view',
    'message:my:query', 
    'message:my:list',
    'message:my:read',
    'message:my:remove'
);
