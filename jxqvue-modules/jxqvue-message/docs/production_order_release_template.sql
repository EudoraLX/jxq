-- 生产订单下达消息模板SQL脚本
-- 创建时间: 2025-01-27
-- 说明: 当生产订单状态变更为"下达"时，提醒对应的用户准备开始工作

-- 1. 创建消息模板分类（如果不存在）
INSERT INTO message_template_category (category_name, category_code, parent_id, sort_order, status, create_by, create_time, remark)
VALUES ('生产管理', 'PRODUCTION', 0, 1, '0', 'admin', NOW(), '生产管理相关消息模板分类')
ON DUPLICATE KEY UPDATE category_name = '生产管理';

-- 2. 创建生产订单下达消息模板
INSERT INTO message_template (
    template_name,
    template_code,
    template_type,
    category_id,
    category_name,
    title_template,
    content_template,
    message_type,
    message_level,
    variables,
    status,
    create_by,
    create_time,
    remark
) VALUES (
    '生产订单下达通知',
    'PRODUCTION_ORDER_RELEASE',
    'PRODUCTION_ORDER',
    (SELECT category_id FROM message_template_category WHERE category_code = 'PRODUCTION'),
    '生产管理',
    '生产订单下达通知 - {orderCode}',
    '您好！\n\n生产订单 {orderCode} 已下达，请准备开始工作。\n\n订单详情：\n- 订单名称：{orderName}\n- 产品名称：{productName}\n- 计划数量：{plannedQuantity} {unitName}\n- 计划开始时间：{plannedStartDate}\n- 计划完成时间：{plannedEndDate}\n- 优先级：{priority}\n\n请及时查看订单详情并开始生产准备工作。\n\n如有疑问，请联系相关负责人。\n\n系统自动发送',
    '2',  -- 业务消息
    '2',  -- 重要级别
    '{"orderCode":"订单编码","orderName":"订单名称","productName":"产品名称","plannedQuantity":"计划数量","unitName":"单位名称","plannedStartDate":"计划开始时间","plannedEndDate":"计划完成时间","priority":"优先级"}',
    '0',  -- 启用状态
    'admin',
    NOW(),
    '生产订单状态变更为"下达"时自动发送的通知消息模板'
);

-- 3. 创建消息模板变量记录
INSERT INTO message_template_variable (
    template_id,
    variable_name,
    variable_code,
    variable_type,
    is_required,
    default_value,
    description,
    sort_order,
    create_by,
    create_time
) VALUES 
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '订单编码',
    'orderCode',
    'STRING',
    1,
    '',
    '生产订单编码',
    1,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '订单名称',
    'orderName',
    'STRING',
    1,
    '',
    '生产订单名称',
    2,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '产品名称',
    'productName',
    'STRING',
    1,
    '',
    '产品物料名称',
    3,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '计划数量',
    'plannedQuantity',
    'NUMBER',
    1,
    '0',
    '计划生产数量',
    4,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '单位名称',
    'unitName',
    'STRING',
    1,
    '',
    '数量单位',
    5,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '计划开始时间',
    'plannedStartDate',
    'DATETIME',
    0,
    '',
    '计划开始生产时间',
    6,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '计划完成时间',
    'plannedEndDate',
    'DATETIME',
    0,
    '',
    '计划完成生产时间',
    7,
    'admin',
    NOW()
),
(
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '优先级',
    'priority',
    'STRING',
    0,
    '普通',
    '订单优先级',
    8,
    'admin',
    NOW()
);

-- 4. 创建消息发送规则（可选）
INSERT INTO message_rule (
    rule_name,
    rule_code,
    rule_type,
    trigger_condition,
    template_id,
    target_users,
    target_roles,
    target_departments,
    is_enabled,
    create_by,
    create_time,
    remark
) VALUES (
    '生产订单下达通知规则',
    'PRODUCTION_ORDER_RELEASE_RULE',
    'AUTO',
    'production_order_status=2',
    (SELECT template_id FROM message_template WHERE template_code = 'PRODUCTION_ORDER_RELEASE'),
    '',
    'production_manager,workshop_manager',
    '',
    '1',
    'admin',
    NOW(),
    '当生产订单状态变更为"下达"时自动发送通知'
);
