-- 注塑类绝缘子行业物料测试数据
-- 创建时间: 2025-01-31
-- 数据库: jxqvue

-- ========================================
-- 1. 物料分类数据 (erp_material_category)
-- ========================================

-- 一级分类：原材料
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(1, 'RM', '原材料', 0, '0', 1, 1, '0', '1', '注塑绝缘子生产用原材料', 'admin', NOW(), '', NULL, '0');

-- 二级分类：塑料原料
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(2, 'RM-PL', '塑料原料', 1, '0,1', 2, 1, '0', '1', '注塑用塑料原料', 'admin', NOW(), '', NULL, '0'),
(3, 'RM-AD', '添加剂', 1, '0,1', 2, 2, '0', '1', '塑料改性用添加剂', 'admin', NOW(), '', NULL, '0'),
(4, 'RM-COL', '着色剂', 1, '0,1', 2, 3, '0', '1', '塑料着色用颜料', 'admin', NOW(), '', NULL, '0');

-- 一级分类：半成品
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(5, 'WIP', '半成品', 0, '0', 1, 2, '0', '1', '注塑绝缘子生产过程中的半成品', 'admin', NOW(), '', NULL, '0');

-- 二级分类：注塑件
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(6, 'WIP-MOLD', '注塑件', 5, '0,5', 2, 1, '0', '1', '注塑成型的绝缘子主体', 'admin', NOW(), '', NULL, '0'),
(7, 'WIP-ASSY', '组装件', 5, '0,5', 2, 2, '0', '1', '部分组装的绝缘子', 'admin', NOW(), '', NULL, '0');

-- 一级分类：成品
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(8, 'FG', '成品', 0, '0', 1, 3, '0', '1', '注塑绝缘子成品', 'admin', NOW(), '', NULL, '0');

-- 二级分类：绝缘子成品
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(9, 'FG-INS', '绝缘子', 8, '0,8', 2, 1, '0', '1', '各类绝缘子成品', 'admin', NOW(), '', NULL, '0'),
(10, 'FG-ACC', '配件', 8, '0,8', 2, 2, '0', '1', '绝缘子配套配件', 'admin', NOW(), '', NULL, '0');

-- 一级分类：包装材料
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(11, 'PKG', '包装材料', 0, '0', 1, 4, '0', '1', '产品包装用材料', 'admin', NOW(), '', NULL, '0');

-- 二级分类：包装材料
INSERT INTO erp_material_category (id, category_code, category_name, parent_id, ancestors, level, sort_order, status, is_active, remark, create_by, create_time, update_by, update_time, del_flag) VALUES
(12, 'PKG-BOX', '包装箱', 11, '0,11', 2, 1, '0', '1', '产品包装箱', 'admin', NOW(), '', NULL, '0'),
(13, 'PKG-FILL', '填充物', 11, '0,11', 2, 2, '0', '1', '包装填充材料', 'admin', NOW(), '', NULL, '0');

-- ========================================
-- 2. 物料主数据 (erp_material_master)
-- ========================================

-- 塑料原料类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(1, 'RM-PL-001', '聚碳酸酯(PC)', '透明，密度1.2g/cm³，熔融指数10g/10min', 'PC-10', 2, 1, 25.50, 1000.00, 5000.00, 1500.00, 7, 365, '1', '0', '0', '1', '1', '高透明度PC，用于透明绝缘子', 'admin', NOW(), '', NULL),
(2, 'RM-PL-002', '聚酰胺66(PA66)', '白色，密度1.14g/cm³，熔点260℃', 'PA66-30', 2, 1, 22.80, 800.00, 4000.00, 1200.00, 7, 365, '1', '0', '0', '1', '1', '高强度PA66，用于结构件', 'admin', NOW(), '', NULL),
(3, 'RM-PL-003', '聚苯醚(PPO)', '米黄色，密度1.06g/cm³，热变形温度130℃', 'PPO-20', 2, 1, 35.20, 500.00, 2500.00, 800.00, 10, 365, '1', '0', '0', '1', '1', '耐高温PPO，用于高温环境', 'admin', NOW(), '', NULL),
(4, 'RM-PL-004', '聚氯乙烯(PVC)', '灰色，密度1.4g/cm³，硬度85A', 'PVC-85', 2, 1, 18.50, 1200.00, 6000.00, 1800.00, 5, 365, '1', '0', '0', '1', '1', '通用PVC，用于普通绝缘子', 'admin', NOW(), '', NULL);

-- 添加剂类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(5, 'RM-AD-001', '抗氧剂1010', '白色粉末，纯度≥98%，熔点110-125℃', 'AO-1010', 3, 1, 45.00, 50.00, 300.00, 100.00, 15, 730, '1', '0', '0', '1', '1', '塑料抗氧剂，延长使用寿命', 'admin', NOW(), '', NULL),
(6, 'RM-AD-002', '光稳定剂770', '淡黄色粉末，纯度≥99%，熔点48-52℃', 'LS-770', 3, 1, 68.50, 30.00, 200.00, 80.00, 15, 730, '1', '0', '0', '1', '1', '紫外线吸收剂，防老化', 'admin', NOW(), '', NULL),
(7, 'RM-AD-003', '阻燃剂TBBPA', '白色粉末，纯度≥98%，溴含量≥58%', 'FR-TBBPA', 3, 1, 52.30, 100.00, 500.00, 150.00, 12, 730, '1', '0', '0', '1', '1', '溴系阻燃剂，提高阻燃性', 'admin', NOW(), '', NULL);

-- 着色剂类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(8, 'RM-COL-001', '钛白粉R-902', '白色粉末，TiO2含量≥93%，粒径0.2μm', 'TiO2-R902', 4, 1, 18.80, 200.00, 1000.00, 300.00, 10, 1095, '1', '0', '0', '1', '1', '白色颜料，提高遮盖力', 'admin', NOW(), '', NULL),
(9, 'RM-COL-002', '炭黑N330', '黑色粉末，粒径30nm，比表面积80m²/g', 'CB-N330', 4, 1, 12.50, 150.00, 800.00, 250.00, 10, 1095, '1', '0', '0', '1', '1', '黑色颜料，导电性能', 'admin', NOW(), '', NULL),
(10, 'RM-COL-003', '酞菁蓝B', '蓝色粉末，纯度≥95%，色力≥100%', 'PB-15:3', 4, 1, 85.00, 20.00, 150.00, 50.00, 15, 1095, '1', '0', '0', '1', '1', '蓝色颜料，特殊颜色需求', 'admin', NOW(), '', NULL);

-- 注塑件类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(11, 'WIP-MOLD-001', '10kV针式绝缘子主体', 'PC材质，高度150mm，爬电距离200mm', 'PS-10-150', 6, 2, 0.00, 100.00, 500.00, 150.00, 0, 0, '0', '1', '0', '1', '1', '10kV针式绝缘子注塑件', 'admin', NOW(), '', NULL),
(12, 'WIP-MOLD-002', '35kV悬式绝缘子主体', 'PA66材质，高度200mm，爬电距离300mm', 'SUS-35-200', 6, 2, 0.00, 80.00, 400.00, 120.00, 0, 0, '0', '1', '0', '1', '1', '35kV悬式绝缘子注塑件', 'admin', NOW(), '', NULL),
(13, 'WIP-MOLD-003', '110kV支柱绝缘子主体', 'PPO材质，高度300mm，爬电距离450mm', 'PST-110-300', 6, 2, 0.00, 50.00, 250.00, 80.00, 0, 0, '0', '1', '0', '1', '1', '110kV支柱绝缘子注塑件', 'admin', NOW(), '', NULL);

-- 组装件类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(14, 'WIP-ASSY-001', '10kV针式绝缘子组装件', '含金属附件，待测试', 'PS-10-150-ASSY', 7, 2, 0.00, 80.00, 400.00, 120.00, 0, 0, '0', '1', '0', '1', '1', '10kV针式绝缘子半成品', 'admin', NOW(), '', NULL),
(15, 'WIP-ASSY-002', '35kV悬式绝缘子组装件', '含金属附件，待测试', 'SUS-35-200-ASSY', 7, 2, 0.00, 60.00, 300.00, 100.00, 0, 0, '0', '1', '0', '1', '1', '35kV悬式绝缘子半成品', 'admin', NOW(), '', NULL);

-- 绝缘子成品类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(16, 'FG-INS-001', '10kV针式绝缘子', 'PC材质，高度150mm，爬电距离200mm，耐压42kV', 'PS-10-150-FG', 9, 2, 85.00, 50.00, 300.00, 100.00, 0, 0, '0', '0', '1', '1', '1', '10kV针式绝缘子成品', 'admin', NOW(), '', NULL),
(17, 'FG-INS-002', '35kV悬式绝缘子', 'PA66材质，高度200mm，爬电距离300mm，耐压140kV', 'SUS-35-200-FG', 9, 2, 156.00, 30.00, 200.00, 80.00, 0, 0, '0', '0', '1', '1', '1', '35kV悬式绝缘子成品', 'admin', NOW(), '', NULL),
(18, 'FG-INS-003', '110kV支柱绝缘子', 'PPO材质，高度300mm，爬电距离450mm，耐压440kV', 'PST-110-300-FG', 9, 2, 320.00, 20.00, 150.00, 60.00, 0, 0, '0', '0', '1', '1', '1', '110kV支柱绝缘子成品', 'admin', NOW(), '', NULL),
(19, 'FG-INS-004', '220kV支柱绝缘子', 'PPO材质，高度450mm，爬电距离650mm，耐压880kV', 'PST-220-450-FG', 9, 2, 580.00, 15.00, 100.00, 40.00, 0, 0, '0', '0', '1', '1', '1', '220kV支柱绝缘子成品', 'admin', NOW(), '', NULL);

-- 配件类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(20, 'FG-ACC-001', '金属附件-针式', '镀锌钢制，M12螺纹，长度50mm', 'ACC-PS-M12-50', 10, 3, 12.50, 100.00, 500.00, 150.00, 0, 0, '1', '0', '1', '1', '1', '针式绝缘子金属附件', 'admin', NOW(), '', NULL),
(21, 'FG-ACC-002', '金属附件-悬式', '镀锌钢制，M16螺纹，长度80mm', 'ACC-SUS-M16-80', 10, 3, 18.80, 80.00, 400.00, 120.00, 0, 0, '1', '0', '1', '1', '1', '悬式绝缘子金属附件', 'admin', NOW(), '', NULL),
(22, 'FG-ACC-003', '密封圈', '丁腈橡胶，内径25mm，外径35mm', 'ACC-SEAL-25-35', 10, 3, 2.50, 500.00, 2000.00, 600.00, 0, 0, '1', '0', '1', '1', '1', '绝缘子密封用橡胶圈', 'admin', NOW(), '', NULL);

-- 包装材料类物料
INSERT INTO erp_material_master (id, material_code, material_name, material_spec, material_model, category_id, unit_id, unit_price, min_stock, max_stock, safety_stock, lead_time, shelf_life, is_purchased, is_manufactured, is_sales, is_inventory, is_active, remark, create_by, create_time, update_by, update_time) VALUES
(23, 'PKG-BOX-001', '绝缘子包装箱', '瓦楞纸箱，尺寸400×300×200mm，承重50kg', 'BOX-400-300-200', 12, 4, 8.50, 200.00, 1000.00, 300.00, 0, 0, '1', '0', '0', '1', '1', '绝缘子产品包装箱', 'admin', NOW(), '', NULL),
(24, 'PKG-BOX-002', '大件包装箱', '瓦楞纸箱，尺寸600×400×300mm，承重100kg', 'BOX-600-400-300', 12, 4, 12.80, 100.00, 500.00, 150.00, 0, 0, '1', '0', '0', '1', '1', '大型绝缘子包装箱', 'admin', NOW(), '', NULL),
(25, 'PKG-FILL-001', '泡沫填充物', 'EPS泡沫，密度15kg/m³，尺寸100×100×50mm', 'FILL-EPS-100-100-50', 13, 4, 1.20, 1000.00, 5000.00, 1500.00, 0, 0, '1', '0', '0', '1', '1', '包装防震填充材料', 'admin', NOW(), '', NULL),
(26, 'PKG-FILL-002', '气泡膜', 'PE材质，宽度500mm，厚度0.3mm，卷长100m', 'FILL-BUBBLE-500-0.3', 13, 4, 15.50, 50.00, 300.00, 100.00, 0, 0, '1', '0', '0', '1', '1', '包装防震气泡膜', 'admin', NOW(), '', NULL);

-- ========================================
-- 3. 单位ID说明
-- ========================================
-- 单位ID说明：
-- 1: kg (千克) - 用于原材料
-- 2: 个 (件) - 用于半成品和成品
-- 3: 套 - 用于配件
-- 4: 个 - 用于包装材料
