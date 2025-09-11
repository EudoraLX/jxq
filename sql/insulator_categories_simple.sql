-- 注塑类绝缘子行业物料分类数据 - 简化版
-- 创建时间：2025-01-XX
-- 说明：基于注塑类绝缘子行业特点的物料分类体系

-- 清空现有数据（可选，谨慎使用）
-- DELETE FROM erp_material_category WHERE category_code LIKE 'INS%';

-- 一级分类：主要材料类别
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS001', '塑料原料', NULL, 1, 1, '1', '注塑绝缘子主要塑料原料', 'admin', NOW()),
('INS002', '添加剂', NULL, 1, 2, '1', '塑料改性添加剂', 'admin', NOW()),
('INS003', '填料', NULL, 1, 3, '1', '增强材料填料', 'admin', NOW()),
('INS004', '助剂', NULL, 1, 4, '1', '加工助剂和功能助剂', 'admin', NOW()),
('INS005', '包装材料', NULL, 1, 5, '1', '产品包装相关材料', 'admin', NOW()),
('INS006', '辅助材料', NULL, 1, 6, '1', '生产辅助材料', 'admin', NOW());

-- 二级分类：塑料原料细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS001001', '聚氯乙烯(PVC)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 1, '1', '硬质PVC，用于绝缘子外壳', 'admin', NOW()),
('INS001002', '聚丙烯(PP)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 2, '1', '改性PP，用于绝缘子主体', 'admin', NOW()),
('INS001003', '聚乙烯(PE)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 3, '1', '高密度PE，用于绝缘子', 'admin', NOW()),
('INS001004', '聚碳酸酯(PC)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 4, '1', '工程塑料，用于高要求绝缘子', 'admin', NOW()),
('INS001005', '聚酰胺(PA)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 5, '1', '尼龙材料，用于特殊绝缘子', 'admin', NOW()),
('INS001006', '聚酯(PET/PBT)', (SELECT id FROM erp_material_category WHERE category_code = 'INS001'), 2, 6, '1', '聚酯材料，用于绝缘子', 'admin', NOW());

-- 二级分类：添加剂细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS002001', '增塑剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 1, '1', '改善塑料加工性能', 'admin', NOW()),
('INS002002', '稳定剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 2, '1', '热稳定剂、光稳定剂', 'admin', NOW()),
('INS002003', '抗氧剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 3, '1', '防止塑料氧化降解', 'admin', NOW()),
('INS002004', '阻燃剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 4, '1', '提高塑料阻燃性能', 'admin', NOW()),
('INS002005', '抗静电剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 5, '1', '消除塑料静电', 'admin', NOW()),
('INS002006', '着色剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002'), 2, 6, '1', '塑料着色用颜料和染料', 'admin', NOW());

-- 二级分类：填料细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS003001', '玻璃纤维', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 1, '1', '增强材料，提高强度', 'admin', NOW()),
('INS003002', '碳酸钙', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 2, '1', '填充材料，降低成本', 'admin', NOW()),
('INS003003', '滑石粉', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 3, '1', '填充材料，改善表面', 'admin', NOW()),
('INS003004', '云母粉', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 4, '1', '绝缘填料，提高绝缘性', 'admin', NOW()),
('INS003005', '硅灰石', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 5, '1', '增强填料，提高刚性', 'admin', NOW()),
('INS003006', '高岭土', (SELECT id FROM erp_material_category WHERE category_code = 'INS003'), 2, 6, '1', '填充材料，改善性能', 'admin', NOW());

-- 二级分类：助剂细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS004001', '润滑剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 1, '1', '改善塑料加工流动性', 'admin', NOW()),
('INS004002', '脱模剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 2, '1', '注塑脱模用助剂', 'admin', NOW()),
('INS004003', '发泡剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 3, '1', '制造发泡塑料用', 'admin', NOW()),
('INS004004', '交联剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 4, '1', '塑料交联改性用', 'admin', NOW()),
('INS004005', '偶联剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 5, '1', '改善填料与基体结合', 'admin', NOW()),
('INS004006', '成核剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS004'), 2, 6, '1', '控制塑料结晶', 'admin', NOW());

-- 二级分类：包装材料细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS005001', '包装袋', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 1, '1', '塑料原料包装袋', 'admin', NOW()),
('INS005002', '包装箱', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 2, '1', '产品包装箱', 'admin', NOW()),
('INS005003', '标签', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 3, '1', '产品标识标签', 'admin', NOW()),
('INS005004', '胶带', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 4, '1', '包装封口胶带', 'admin', NOW()),
('INS005005', '缓冲材料', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 5, '1', '产品运输缓冲', 'admin', NOW()),
('INS005006', '收缩膜', (SELECT id FROM erp_material_category WHERE category_code = 'INS005'), 2, 6, '1', '产品包装收缩膜', 'admin', NOW());

-- 二级分类：辅助材料细分
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS006001', '清洁剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 1, '1', '设备清洁用', 'admin', NOW()),
('INS006002', '润滑油', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 2, '1', '设备润滑用', 'admin', NOW()),
('INS006003', '工具', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 3, '1', '生产工具', 'admin', NOW()),
('INS006004', '检测用品', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 4, '1', '质量检测用品', 'admin', NOW()),
('INS006005', '安全用品', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 5, '1', '劳动保护用品', 'admin', NOW()),
('INS006006', '办公用品', (SELECT id FROM erp_material_category WHERE category_code = 'INS006'), 2, 6, '1', '办公相关用品', 'admin', NOW());

-- 三级分类：PVC材料品种（示例）
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS001001001', '硬质PVC SG-5', (SELECT id FROM erp_material_category WHERE category_code = 'INS001001'), 3, 1, '1', '通用级硬质PVC，K值65-68', 'admin', NOW()),
('INS001001002', '硬质PVC SG-7', (SELECT id FROM erp_material_category WHERE category_code = 'INS001001'), 3, 2, '1', '高聚合度PVC，K值68-71', 'admin', NOW()),
('INS001001003', '改性PVC', (SELECT id FROM erp_material_category WHERE category_code = 'INS001001'), 3, 3, '1', '抗冲击改性PVC', 'admin', NOW()),
('INS001001004', '阻燃PVC', (SELECT id FROM erp_material_category WHERE category_code = 'INS001001'), 3, 4, '1', '阻燃级PVC材料', 'admin', NOW());

-- 三级分类：PP材料品种（示例）
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS001002001', '均聚PP', (SELECT id FROM erp_material_category WHERE category_code = 'INS001002'), 3, 1, '1', '均聚聚丙烯', 'admin', NOW()),
('INS001002002', '共聚PP', (SELECT id FROM erp_material_category WHERE category_code = 'INS001002'), 3, 2, '1', '共聚聚丙烯', 'admin', NOW()),
('INS001002003', '改性PP', (SELECT id FROM erp_material_category WHERE category_code = 'INS001002'), 3, 3, '1', '玻璃纤维增强PP', 'admin', NOW()),
('INS001002004', '阻燃PP', (SELECT id FROM erp_material_category WHERE category_code = 'INS001002'), 3, 4, '1', '阻燃级PP材料', 'admin', NOW());

-- 三级分类：玻璃纤维填料（示例）
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS003001001', '短切玻璃纤维', (SELECT id FROM erp_material_category WHERE category_code = 'INS003001'), 3, 1, '1', '3-6mm短切玻璃纤维', 'admin', NOW()),
('INS003001002', '长玻璃纤维', (SELECT id FROM erp_material_category WHERE category_code = 'INS003001'), 3, 2, '1', '6-25mm长玻璃纤维', 'admin', NOW()),
('INS003001003', '玻璃纤维粉', (SELECT id FROM erp_material_category WHERE category_code = 'INS003001'), 3, 3, '1', '微米级玻璃纤维粉', 'admin', NOW());

-- 三级分类：增塑剂（示例）
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS002001001', '邻苯二甲酸二辛酯(DOP)', (SELECT id FROM erp_material_category WHERE category_code = 'INS002001'), 3, 1, '1', '通用增塑剂', 'admin', NOW()),
('INS002001002', '邻苯二甲酸二丁酯(DBP)', (SELECT id FROM erp_material_category WHERE category_code = 'INS002001'), 3, 2, '1', '快速增塑剂', 'admin', NOW()),
('INS002001003', '环氧大豆油', (SELECT id FROM erp_material_category WHERE category_code = 'INS002001'), 3, 3, '1', '环保增塑剂', 'admin', NOW());

-- 三级分类：阻燃剂（示例）
INSERT INTO erp_material_category (category_code, category_name, parent_id, level, sort_order, is_active, remark, create_by, create_time) VALUES
('INS002004001', '溴系阻燃剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002004'), 3, 1, '1', '十溴二苯醚等', 'admin', NOW()),
('INS002004002', '磷系阻燃剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002004'), 3, 2, '1', '磷酸酯类阻燃剂', 'admin', NOW()),
('INS002004003', '无机阻燃剂', (SELECT id FROM erp_material_category WHERE category_code = 'INS002004'), 3, 3, '1', '氢氧化铝、氢氧化镁', 'admin', NOW());

-- 插入完成提示
SELECT '注塑类绝缘子行业物料分类数据插入完成！' AS message;
SELECT COUNT(*) AS total_categories FROM erp_material_category WHERE category_code LIKE 'INS%';
