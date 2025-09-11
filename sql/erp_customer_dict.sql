-- =============================================
-- 客户模块数据字典SQL脚本
-- 创建时间: 2024-12-20
-- 说明: 为客户管理模块创建必要的数据字典
-- =============================================

-- 1. 客户类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('客户类型', 'erp_customer_type', '0', 'admin', NOW(), '客户类型字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '生产企业', '1', 'erp_customer_type', '', 'primary', 'Y', '0', 'admin', NOW(), '生产制造型企业'),
(2, '贸易公司', '2', 'erp_customer_type', '', 'success', 'N', '0', 'admin', NOW(), '贸易流通企业'),
(3, '终端用户', '3', 'erp_customer_type', '', 'info', 'N', '0', 'admin', NOW(), '最终使用客户');

-- 2. 信用等级字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('信用等级', 'erp_credit_rating', '0', 'admin', NOW(), '客户信用等级字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '优秀', 'A', 'erp_credit_rating', '', 'success', 'Y', '0', 'admin', NOW(), '信用优秀'),
(2, '良好', 'B', 'erp_credit_rating', '', 'primary', 'N', '0', 'admin', NOW(), '信用良好'),
(3, '一般', 'C', 'erp_credit_rating', '', 'warning', 'N', '0', 'admin', NOW(), '信用一般'),
(4, '较差', 'D', 'erp_credit_rating', '', 'danger', 'N', '0', 'admin', NOW(), '信用较差'),
(5, '很差', 'E', 'erp_credit_rating', '', 'danger', 'N', '0', 'admin', NOW(), '信用很差');

-- 3. 客户状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('客户状态', 'erp_customer_status', '0', 'admin', NOW(), '客户状态字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '正常', '0', 'erp_customer_status', '', 'success', 'Y', '0', 'admin', NOW(), '正常状态'),
(2, '停用', '1', 'erp_customer_status', '', 'danger', 'N', '0', 'admin', NOW(), '停用状态');

-- 4. 客户启用状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('客户启用状态', 'erp_customer_active', '0', 'admin', NOW(), '客户启用状态字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '启用', '1', 'erp_customer_active', '', 'success', 'Y', '0', 'admin', NOW(), '启用状态'),
(2, '禁用', '0', 'erp_customer_active', '', 'danger', 'N', '0', 'admin', NOW(), '禁用状态');

-- 5. 行业分类字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('行业分类', 'erp_industry_type', '0', 'admin', NOW(), '客户所属行业分类字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '电力设备制造', 'POWER_EQUIPMENT', 'erp_industry_type', '', 'primary', 'N', '0', 'admin', NOW(), '电力设备制造行业'),
(2, '电子元器件', 'ELECTRONIC_COMPONENTS', 'erp_industry_type', '', 'success', 'N', '0', 'admin', NOW(), '电子元器件行业'),
(3, '汽车零部件', 'AUTO_PARTS', 'erp_industry_type', '', 'info', 'N', '0', 'admin', NOW(), '汽车零部件行业'),
(4, '家电制造', 'HOME_APPLIANCES', 'erp_industry_type', '', 'warning', 'N', '0', 'admin', NOW(), '家电制造行业'),
(5, '通信设备', 'COMMUNICATION', 'erp_industry_type', '', 'primary', 'N', '0', 'admin', NOW(), '通信设备行业'),
(6, '医疗器械', 'MEDICAL_DEVICES', 'erp_industry_type', '', 'success', 'N', '0', 'admin', NOW(), '医疗器械行业'),
(7, '航空航天', 'AEROSPACE', 'erp_industry_type', '', 'info', 'N', '0', 'admin', NOW(), '航空航天行业'),
(8, '新能源', 'NEW_ENERGY', 'erp_industry_type', '', 'warning', 'N', '0', 'admin', NOW(), '新能源行业'),
(9, '其他制造业', 'OTHER_MANUFACTURING', 'erp_industry_type', '', 'default', 'N', '0', 'admin', NOW(), '其他制造业'),
(10, '贸易流通', 'TRADE', 'erp_industry_type', '', 'info', 'N', '0', 'admin', NOW(), '贸易流通行业');

-- 6. 付款条件字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('付款条件', 'erp_payment_terms', '0', 'admin', NOW(), '客户付款条件字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '预付款', 'PREPAID', 'erp_payment_terms', '', 'success', 'N', '0', 'admin', NOW(), '预付款'),
(2, '货到付款', 'COD', 'erp_payment_terms', '', 'primary', 'N', '0', 'admin', NOW(), '货到付款'),
(3, '月结30天', 'NET30', 'erp_payment_terms', '', 'warning', 'Y', '0', 'admin', NOW(), '月结30天'),
(4, '月结60天', 'NET60', 'erp_payment_terms', '', 'warning', 'N', '0', 'admin', NOW(), '月结60天'),
(5, '月结90天', 'NET90', 'erp_payment_terms', '', 'danger', 'N', '0', 'admin', NOW(), '月结90天'),
(6, '其他', 'OTHER', 'erp_payment_terms', '', 'info', 'N', '0', 'admin', NOW(), '其他付款条件');

-- 7. 交货条件字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('交货条件', 'erp_delivery_terms', '0', 'admin', NOW(), '客户交货条件字典');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '工厂交货', 'EXW', 'erp_delivery_terms', '', 'primary', 'N', '0', 'admin', NOW(), '工厂交货'),
(2, '货交承运人', 'FCA', 'erp_delivery_terms', '', 'success', 'N', '0', 'admin', NOW(), '货交承运人'),
(3, '船边交货', 'FAS', 'erp_delivery_terms', '', 'info', 'N', '0', 'admin', NOW(), '船边交货'),
(4, '船上交货', 'FOB', 'erp_delivery_terms', '', 'warning', 'Y', '0', 'admin', NOW(), '船上交货'),
(5, '成本加运费', 'CFR', 'erp_delivery_terms', '', 'info', 'N', '0', 'admin', NOW(), '成本加运费'),
(6, '成本保险费加运费', 'CIF', 'erp_delivery_terms', '', 'success', 'N', '0', 'admin', NOW(), '成本保险费加运费'),
(7, '其他', 'OTHER', 'erp_delivery_terms', '', 'default', 'N', '0', 'admin', NOW(), '其他交货条件');

-- 8. 评级范围字典（用于质量、交付、服务、综合评级）
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('评级范围', 'erp_rating_scale', '0', 'admin', NOW(), '客户评级范围字典（0.00-5.00）');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '极差(0.00-1.00)', '0-1', 'erp_rating_scale', '', 'danger', 'N', '0', 'admin', NOW(), '极差评级'),
(2, '较差(1.00-2.00)', '1-2', 'erp_rating_scale', '', 'warning', 'N', '0', 'admin', NOW(), '较差评级'),
(3, '一般(2.00-3.00)', '2-3', 'erp_rating_scale', '', 'info', 'Y', '0', 'admin', NOW(), '一般评级'),
(4, '良好(3.00-4.00)', '3-4', 'erp_rating_scale', '', 'primary', 'N', '0', 'admin', NOW(), '良好评级'),
(5, '优秀(4.00-5.00)', '4-5', 'erp_rating_scale', '', 'success', 'N', '0', 'admin', NOW(), '优秀评级');

-- =============================================
-- 数据字典创建完成
-- 总计创建了8个字典类型，包含客户管理的核心业务数据
-- =============================================
