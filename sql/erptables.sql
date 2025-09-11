/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80403
 Source Host           : localhost:3306
 Source Schema         : jxqerp

 Target Server Type    : MySQL
 Target Server Version : 80403
 File Encoding         : 65001

 Date: 03/09/2025 10:42:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for erp_bom
-- ----------------------------
DROP TABLE IF EXISTS `erp_bom`;
CREATE TABLE `erp_bom`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
  `bom_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'BOM编码',
  `bom_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'BOM名称',
  `product_id` bigint NOT NULL COMMENT '产品物料ID',
  `unit_id` bigint NULL DEFAULT NULL COMMENT '单位',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本号',
  `process_route_id` bigint NULL DEFAULT NULL COMMENT '工艺路线',
  `effective_date` date NOT NULL COMMENT '生效日期',
  `expiry_date` date NULL DEFAULT NULL COMMENT '失效日期',
  `approval_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '审批状态',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否默认版本（0否 1是）',
  `total_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '总成本',
  `labor_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '人工成本',
  `material_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '材料成本',
  `overhead_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '制造费用',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_bom_code_version`(`bom_code` ASC, `version` ASC) USING BTREE,
  INDEX `idx_product_material_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_status`(`approval_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'BOM主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_bom_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_bom_detail`;
CREATE TABLE `erp_bom_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `bom_id` bigint NOT NULL COMMENT 'BOM ID',
  `material_id` bigint NOT NULL COMMENT '组件物料ID',
  `material_unit_id` bigint NOT NULL COMMENT '组件单位',
  `quantity_per_unit` decimal(18, 4) NOT NULL COMMENT '单件用量',
  `loss_rate` decimal(5, 4) NULL DEFAULT 0.0000 COMMENT '损耗率',
  `effective_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '有效用量',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '位置',
  `operation_sequence` int NULL DEFAULT 0 COMMENT '工序顺序',
  `is_critical` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否关键件（0否 1是）',
  `is_optional` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否可选件（0否 1是）',
  `unit_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '单位成本',
  `total_cost` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '总成本',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_bom_id`(`bom_id` ASC) USING BTREE,
  INDEX `idx_component_material_id`(`material_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'BOM明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_bom_route_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_bom_route_detail`;
CREATE TABLE `erp_bom_route_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `bom_id` bigint NOT NULL COMMENT 'BOMID',
  `process_id` bigint NOT NULL COMMENT '工序',
  `operation_sequence` int NOT NULL COMMENT '工序顺序',
  `work_center_id` bigint NOT NULL COMMENT '工作中心',
  `equipment_id` bigint NULL DEFAULT NULL COMMENT '设备',
  `mold_id` bigint NULL DEFAULT NULL COMMENT '模具',
  `setup_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '准备时间(分钟)',
  `processing_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '加工时间(分钟)',
  `move_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '移动时间(分钟)',
  `queue_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '等待时间(分钟)',
  `cycle_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '周期时间(分钟)',
  `labor_hours` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '人工工时(小时)',
  `machine_hours` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '机器工时(小时)',
  `is_critical` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否关键工序（0否 1是）',
  `is_optional` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否可选工序（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_process_route_id`(`bom_id` ASC) USING BTREE,
  INDEX `idx_operation_sequence`(`operation_sequence` ASC) USING BTREE,
  INDEX `idx_work_center_id`(`work_center_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工艺路线明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_capacity_requirement_plan
-- ----------------------------
DROP TABLE IF EXISTS `erp_capacity_requirement_plan`;
CREATE TABLE `erp_capacity_requirement_plan`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划编码',
  `plan_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划名称',
  `work_center_id` bigint NOT NULL COMMENT '工作中心ID',
  `work_center_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作中心编码',
  `work_center_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作中心名称',
  `period_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划周期类型（1日 2周 3月 4季度）',
  `plan_start_date` date NOT NULL COMMENT '计划开始日期',
  `plan_end_date` date NOT NULL COMMENT '计划结束日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '计划状态（0草稿 1已确认 2执行中 3已完成 4已取消）',
  `available_capacity` decimal(18, 4) NOT NULL COMMENT '可用产能',
  `required_capacity` decimal(18, 4) NOT NULL COMMENT '需求产能',
  `capacity_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '小时' COMMENT '产能单位',
  `capacity_utilization` decimal(5, 4) NULL DEFAULT 0.0000 COMMENT '产能利用率',
  `overload_hours` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '超负荷工时',
  `underload_hours` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '负荷不足工时',
  `efficiency_rate` decimal(5, 4) NULL DEFAULT 1.0000 COMMENT '效率系数',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_plan_code`(`plan_code` ASC) USING BTREE,
  INDEX `idx_work_center_id`(`work_center_id` ASC) USING BTREE,
  INDEX `idx_plan_start_date`(`plan_start_date` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '能力需求计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_customer
-- ----------------------------
DROP TABLE IF EXISTS `erp_customer`;
CREATE TABLE `erp_customer`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户编码',
  `customer_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '客户名称',
  `customer_short_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '客户简称',
  `customer_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '客户类型（1生产企业 2贸易公司 3终端用户）',
  `industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所属行业',
  `business_license` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '营业执照号',
  `tax_registration_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '税务登记号',
  `legal_representative` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '法定代表人',
  `registered_capital` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '注册资本',
  `establishment_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `business_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '经营范围',
  `contact_person` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '地址',
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `bank_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '银行账号',
  `account_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户名称',
  `credit_rating` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'C' COMMENT '信用等级（A优秀 B良好 C一般 D较差 E很差）',
  `credit_limit` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '信用额度',
  `payment_terms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '付款条件',
  `delivery_terms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交货条件',
  `quality_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '质量评级',
  `delivery_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '交付评级',
  `service_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '服务评级',
  `overall_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '综合评级',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_customer_code`(`customer_code` ASC) USING BTREE,
  INDEX `idx_customer_name`(`customer_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '客户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_equipment
-- ----------------------------
DROP TABLE IF EXISTS `erp_equipment`;
CREATE TABLE `erp_equipment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '设备ID',
  `equipment_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备编码',
  `equipment_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '设备名称',
  `equipment_model` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '设备型号',
  `equipment_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '设备类型',
  `manufacturer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '制造商',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商ID',
  `work_center_id` bigint NULL DEFAULT NULL COMMENT '工作中心ID',
  `purchase_date` date NULL DEFAULT NULL COMMENT '购买日期',
  `warranty_expiry_date` date NULL DEFAULT NULL COMMENT '保修到期日期',
  `depreciation_method` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '折旧方法（1直线法 2加速折旧法）',
  `useful_life` int NULL DEFAULT 0 COMMENT '使用年限',
  `original_value` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '原值',
  `current_value` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '现值',
  `capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '产能',
  `capacity_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'piece/hour' COMMENT '产能单位',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_equipment_code`(`equipment_code` ASC) USING BTREE,
  INDEX `idx_equipment_name`(`equipment_name` ASC) USING BTREE,
  INDEX `idx_work_center_id`(`work_center_id` ASC) USING BTREE,
  INDEX `idx_supplier_id`(`supplier_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '设备表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_location
-- ----------------------------
DROP TABLE IF EXISTS `erp_location`;
CREATE TABLE `erp_location`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `location_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货位编码',
  `location_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货位名称',
  `shelf_id` bigint NOT NULL COMMENT '所属货架ID',
  `warehouse_id` bigint NOT NULL COMMENT '所属仓库ID',
  `location_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '货位类型(1:普通货位 2:特殊货位 3:临时货位 4:待检货位 5:不良品货位)',
  `level_num` int NULL DEFAULT 1 COMMENT '层号',
  `position_num` int NULL DEFAULT 1 COMMENT '位号',
  `capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '货位容量',
  `used_capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '已用容量',
  `max_weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '最大承重(kg)',
  `current_weight` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '当前重量(kg)',
  `is_occupied` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否占用(0:空闲 1:占用 2:部分占用)',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用(0:禁用 1:启用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_location_code`(`location_code` ASC) USING BTREE,
  INDEX `idx_shelf`(`shelf_id` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_occupied`(`is_occupied` ASC) USING BTREE,
  CONSTRAINT `erp_location_ibfk_1` FOREIGN KEY (`shelf_id`) REFERENCES `erp_shelf` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `erp_location_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `erp_warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_master_production_schedule
-- ----------------------------
DROP TABLE IF EXISTS `erp_master_production_schedule`;
CREATE TABLE `erp_master_production_schedule`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划编码',
  `plan_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划名称',
  `product_material_id` bigint NOT NULL COMMENT '产品物料ID',
  `product_material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品物料编码',
  `product_material_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '产品物料名称',
  `period_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划周期类型（1日 2周 3月 4季度）',
  `plan_start_date` date NOT NULL COMMENT '计划开始日期',
  `plan_end_date` date NOT NULL COMMENT '计划结束日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '计划状态（0草稿 1已确认 2执行中 3已完成 4已取消）',
  `plan_total_quantity` decimal(18, 4) NOT NULL COMMENT '计划总数量',
  `produced_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '已生产数量',
  `remaining_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '剩余数量',
  `safety_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '安全库存',
  `max_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最大库存',
  `min_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最小批量',
  `max_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最大批量',
  `standard_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '标准批量',
  `lead_time` int NULL DEFAULT 0 COMMENT '提前期(天)',
  `priority` int NULL DEFAULT 0 COMMENT '优先级',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_plan_code`(`plan_code` ASC) USING BTREE,
  INDEX `idx_product_material_id`(`product_material_id` ASC) USING BTREE,
  INDEX `idx_plan_start_date`(`plan_start_date` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '主生产计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_material_category
-- ----------------------------
DROP TABLE IF EXISTS `erp_material_category`;
CREATE TABLE `erp_material_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类编码',
  `category_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父分类ID',
  `level` int NULL DEFAULT 0 COMMENT '显示顺序',
  `sort_order` int NULL DEFAULT 0 COMMENT '显示顺序',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_category_code`(`category_code` ASC) USING BTREE,
  INDEX `idx_parent_id`(`parent_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物料分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_material_master
-- ----------------------------
DROP TABLE IF EXISTS `erp_material_master`;
CREATE TABLE `erp_material_master`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '物料ID',
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物料编码',
  `material_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物料名称',
  `material_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物料规格',
  `material_model` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物料型号',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `unit_id` int NULL DEFAULT NULL COMMENT '单位',
  `unit_price` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '标准单价',
  `min_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最小库存',
  `max_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最大库存',
  `safety_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '安全库存',
  `lead_time` int NULL DEFAULT 0 COMMENT '提前期(天)',
  `shelf_life` int NULL DEFAULT 0 COMMENT '保质期(天)',
  `is_purchased` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否采购件（0否 1是）',
  `is_manufactured` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否制造件（0否 1是）',
  `is_sales` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否销售件（0否 1是）',
  `is_inventory` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '是否库存件（0否 1是）',
  `process_route_id` bigint NULL DEFAULT NULL COMMENT '工艺路线',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_material_code`(`material_code` ASC) USING BTREE,
  INDEX `idx_category_id`(`category_id` ASC) USING BTREE,
  INDEX `idx_material_name`(`material_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物料主数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_mold
-- ----------------------------
DROP TABLE IF EXISTS `erp_mold`;
CREATE TABLE `erp_mold`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mold_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模具编码',
  `mold_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模具名称',
  `mold_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '模具类型',
  `mold_category` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '模具分类',
  `product_material_id` bigint NOT NULL COMMENT '产品物料ID',
  `storage_location_id` bigint NULL DEFAULT NULL COMMENT '存储位置ID',
  `mold_weight` decimal(10, 4) NULL DEFAULT NULL COMMENT '模具重量(kg)',
  `mold_size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模具尺寸',
  `cavity_count` int NULL DEFAULT 1 COMMENT '型腔数量',
  `max_shot_weight` decimal(10, 4) NULL DEFAULT NULL COMMENT '最大注塑重量(g)',
  `min_shot_weight` decimal(10, 4) NULL DEFAULT NULL COMMENT '最小注塑重量(g)',
  `design_life` int NULL DEFAULT NULL COMMENT '设计寿命(次)',
  `current_usage_count` int NULL DEFAULT 0 COMMENT '当前使用次数',
  `maintenance_cycle` int NULL DEFAULT NULL COMMENT '维护周期(次)',
  `last_maintenance_date` date NULL DEFAULT NULL COMMENT '上次维护日期',
  `next_maintenance_date` date NULL DEFAULT NULL COMMENT '下次维护日期',
  `mold_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '模具状态',
  `supplier_id` bigint NULL DEFAULT NULL COMMENT '供应商',
  `purchase_date` date NULL DEFAULT NULL COMMENT '采购日期',
  `purchase_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '采购价格',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_mold_code`(`mold_code` ASC) USING BTREE,
  INDEX `idx_product_material`(`product_material_id` ASC) USING BTREE,
  INDEX `idx_mold_type`(`mold_type` ASC) USING BTREE,
  INDEX `idx_mold_category`(`mold_category` ASC) USING BTREE,
  INDEX `idx_storage_location`(`storage_location_id` ASC) USING BTREE,
  INDEX `idx_mold_status`(`mold_status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模具表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_mold_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_mold_detail`;
CREATE TABLE `erp_mold_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mold_id` bigint NOT NULL COMMENT '模具ID',
  `component_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件编码',
  `component_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组件名称',
  `component_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '组件类型',
  `material` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '材料',
  `hardness` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '硬度',
  `surface_treatment` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表面处理',
  `dimensions` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '尺寸规格',
  `weight` decimal(10, 4) NULL DEFAULT NULL COMMENT '重量(kg)',
  `life_cycle` int NULL DEFAULT NULL COMMENT '使用寿命(次)',
  `current_usage_count` int NULL DEFAULT 0 COMMENT '当前使用次数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_mold_id`(`mold_id` ASC) USING BTREE,
  INDEX `idx_component_type`(`component_type` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `erp_mold_detail_ibfk_1` FOREIGN KEY (`mold_id`) REFERENCES `erp_mold` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模具明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_mold_usage
-- ----------------------------
DROP TABLE IF EXISTS `erp_mold_usage`;
CREATE TABLE `erp_mold_usage`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `mold_id` bigint NOT NULL COMMENT '模具ID',
  `order_id` bigint NOT NULL COMMENT '生产订单ID',
  `operator_id` bigint NOT NULL COMMENT '操作人员ID',
  `start_time` datetime NOT NULL COMMENT '开始使用时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束使用时间',
  `usage_duration` decimal(8, 2) NULL DEFAULT NULL COMMENT '使用时长(小时)',
  `product_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '生产产品数量',
  `defect_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '缺陷产品数量',
  `mold_temperature` decimal(8, 2) NULL DEFAULT NULL COMMENT '模具温度(℃)',
  `mold_pressure` decimal(8, 2) NULL DEFAULT NULL COMMENT '模具压力(MPa)',
  `maintenance_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否需要维护(0:否 1:是)',
  `maintenance_note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '维护说明',
  `usage_status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '使用状态(1:使用中 2:已完成 3:异常)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_mold_id`(`mold_id` ASC) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_operator`(`operator_id` ASC) USING BTREE,
  INDEX `idx_start_time`(`start_time` ASC) USING BTREE,
  INDEX `idx_usage_status`(`usage_status` ASC) USING BTREE,
  CONSTRAINT `erp_mold_usage_ibfk_1` FOREIGN KEY (`mold_id`) REFERENCES `prod_mold` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模具使用记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_number_rule
-- ----------------------------
DROP TABLE IF EXISTS `erp_number_rule`;
CREATE TABLE `erp_number_rule`  (
  `rule_id` bigint NOT NULL AUTO_INCREMENT COMMENT '规则ID',
  `rule_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则编码',
  `rule_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '规则名称',
  `business_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务类型',
  `prefix` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前缀',
  `date_format` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日期格式',
  `sequence_length` int NULL DEFAULT 4 COMMENT '序列号长度',
  `current_sequence` bigint NULL DEFAULT 1 COMMENT '当前序列号',
  `reset_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'YEAR' COMMENT '重置类型(YEAR/MONTH/DAY/NEVER)',
  `last_reset_date` date NULL DEFAULT NULL COMMENT '最后重置日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`rule_id`) USING BTREE,
  UNIQUE INDEX `uk_rule_code`(`rule_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '编号规则表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_plan_material_requirement
-- ----------------------------
DROP TABLE IF EXISTS `erp_plan_material_requirement`;
CREATE TABLE `erp_plan_material_requirement`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '计划ID',
  `plan_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划编码',
  `plan_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划名称',
  `material_id` bigint NOT NULL COMMENT '物料ID',
  `material_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物料编码',
  `material_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '物料名称',
  `material_spec` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物料规格',
  `material_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '物料单位',
  `period_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '计划周期类型（1日 2周 3月 4季度）',
  `plan_start_date` date NOT NULL COMMENT '计划开始日期',
  `plan_end_date` date NOT NULL COMMENT '计划结束日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '计划状态（0草稿 1已确认 2执行中 3已完成 4已取消）',
  `gross_requirement` decimal(18, 4) NOT NULL COMMENT '毛需求数量',
  `on_hand_inventory` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '现有库存',
  `in_transit_inventory` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '在途库存',
  `planned_receipts` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '计划接收数量',
  `planned_orders` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '计划订单数量',
  `net_requirement` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '净需求数量',
  `suggested_order_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '建议订单数量',
  `suggested_order_date` date NULL DEFAULT NULL COMMENT '建议订单日期',
  `safety_stock` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '安全库存',
  `min_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最小批量',
  `max_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '最大批量',
  `standard_lot_size` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '标准批量',
  `lead_time` int NULL DEFAULT 0 COMMENT '提前期(天)',
  `priority` int NULL DEFAULT 0 COMMENT '优先级',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_plan_code`(`plan_code` ASC) USING BTREE,
  INDEX `idx_material_id`(`material_id` ASC) USING BTREE,
  INDEX `idx_plan_start_date`(`plan_start_date` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '物料需求计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_process
-- ----------------------------
DROP TABLE IF EXISTS `erp_process`;
CREATE TABLE `erp_process`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工序ID',
  `process_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工序编码',
  `process_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工序名称',
  `process_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '工序类型(1:加工 2:装配 3:检测 4:包装)',
  `standard_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '标准工时(小时)',
  `setup_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '准备时间(小时)',
  `teardown_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT '收尾时间(小时)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态(0:正常 1:停用)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_process_code`(`process_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工序表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_process_para
-- ----------------------------
DROP TABLE IF EXISTS `erp_process_para`;
CREATE TABLE `erp_process_para`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `process_id` bigint NOT NULL COMMENT '工艺参数ID',
  `parameter_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数类型(数据字典类型：parameter_type)',
  `parameter_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数名称',
  `parameter_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '参数编码',
  `parameter_unit` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数单位',
  `standard_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '标准值',
  `min_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '最小值',
  `max_value` decimal(10, 2) NULL DEFAULT NULL COMMENT '最大值',
  `tolerance` decimal(10, 2) NULL DEFAULT NULL COMMENT '公差范围',
  `control_method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控制方法',
  `monitoring_point` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '监控点',
  `alarm_upper` decimal(10, 2) NULL DEFAULT NULL COMMENT '报警上限',
  `alarm_lower` decimal(10, 2) NULL DEFAULT NULL COMMENT '报警下限',
  `is_critical` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否关键参数(数据字典类型：business_yes_no)',
  `is_monitored` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否监控(数据字典类型：business_yes_no)',
  `sort_order` int NULL DEFAULT 0 COMMENT '排序顺序',
  `monitoring_interval` int NULL DEFAULT NULL COMMENT '监控间隔(分)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_process_id`(`process_id` ASC) USING BTREE,
  INDEX `idx_parameter_type`(`parameter_type` ASC) USING BTREE,
  INDEX `idx_parameter_code`(`parameter_code` ASC) USING BTREE,
  INDEX `idx_is_critical`(`is_critical` ASC) USING BTREE,
  INDEX `idx_sort_order`(`sort_order` ASC) USING BTREE,
  CONSTRAINT `erp_process_para_ibfk_1` FOREIGN KEY (`process_id`) REFERENCES `erp_process` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工艺参数明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_process_route
-- ----------------------------
DROP TABLE IF EXISTS `erp_process_route`;
CREATE TABLE `erp_process_route`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工艺路线ID',
  `process_route_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工艺路线编码',
  `process_route_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工艺路线名称',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '版本号',
  `effective_date` date NOT NULL COMMENT '生效日期',
  `expiry_date` date NULL DEFAULT NULL COMMENT '失效日期',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '状态（0草稿 1已确认 2已发布 3已作废）',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否默认版本（0否 1是）',
  `total_cycle_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总周期时间(分钟)',
  `total_setup_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总准备时间(分钟)',
  `total_processing_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总加工时间(分钟)',
  `total_move_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总移动时间(分钟)',
  `total_queue_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '总等待时间(分钟)',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_process_route_code_version`(`process_route_code` ASC, `version` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工艺路线主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_process_route_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_process_route_detail`;
CREATE TABLE `erp_process_route_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `process_route_id` bigint NOT NULL COMMENT '工艺路线ID',
  `process_id` bigint NOT NULL COMMENT '工序',
  `operation_sequence` int NOT NULL COMMENT '工序顺序',
  `work_center_id` bigint NOT NULL COMMENT '工作中心ID',
  `equipment_id` bigint NULL DEFAULT NULL COMMENT '设备',
  `mold_id` bigint NULL DEFAULT NULL COMMENT '模具',
  `setup_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '准备时间(分钟)',
  `processing_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '加工时间(分钟)',
  `move_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '移动时间(分钟)',
  `queue_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '等待时间(分钟)',
  `cycle_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '周期时间(分钟)',
  `labor_hours` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '人工工时(小时)',
  `machine_hours` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '机器工时(小时)',
  `is_critical` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否关键工序（0否 1是）',
  `is_optional` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '是否可选工序（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_process_route_id`(`process_route_id` ASC) USING BTREE,
  INDEX `idx_operation_sequence`(`operation_sequence` ASC) USING BTREE,
  INDEX `idx_work_center_id`(`work_center_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工艺路线明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_calendar
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_calendar`;
CREATE TABLE `erp_production_calendar`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `calendar_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历编码',
  `calendar_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '3' COMMENT '日历类型(1:年度 2:季度 3:月度 4:周度 5:日度)',
  `production_line_id` bigint NOT NULL COMMENT '生产线ID',
  `year` int NOT NULL COMMENT '年份',
  `month` int NULL DEFAULT NULL COMMENT '月份',
  `week` int NULL DEFAULT NULL COMMENT '周次',
  `effective_start_date` date NOT NULL COMMENT '生效开始日期',
  `effective_end_date` date NOT NULL COMMENT '生效结束日期',
  `total_work_days` int NULL DEFAULT 0 COMMENT '总工作日数',
  `total_work_hours` decimal(6, 2) NULL DEFAULT 0.00 COMMENT '总工作小时数',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态(1:草稿 2:生效 3:过期)',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用(0:禁用 1:启用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_calendar_code`(`calendar_code` ASC) USING BTREE,
  INDEX `idx_production_line_id`(`production_line_id` ASC) USING BTREE,
  INDEX `idx_effective_date`(`effective_start_date` ASC, `effective_end_date` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_is_active`(`is_active` ASC) USING BTREE,
  CONSTRAINT `erp_production_calendar_ibfk_1` FOREIGN KEY (`production_line_id`) REFERENCES `erp_production_line` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3007 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生产日历主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_calendar_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_calendar_detail`;
CREATE TABLE `erp_production_calendar_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `calendar_id` bigint NOT NULL COMMENT '日历ID',
  `work_date` date NOT NULL COMMENT '工作日期',
  `day_of_week` int NOT NULL COMMENT '星期几(1-7)',
  `shift_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '班次类型(1:早班 2:中班 3:晚班 4:全天)',
  `shift_start_time` time NOT NULL COMMENT '班次开始时间',
  `shift_end_time` time NOT NULL COMMENT '班次结束时间',
  `work_hours` decimal(5, 2) NOT NULL COMMENT '工作小时数',
  `break_hours` decimal(5, 2) NULL DEFAULT 0.00 COMMENT '休息小时数',
  `work_efficiency` decimal(5, 2) NULL DEFAULT 100.00 COMMENT '工作效率(%)',
  `is_work_day` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否工作日(0:否 1:是)',
  `temperature_control` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否需要温控(0:否 1:是)',
  `pressure_control` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否需要压力控制(0:否 1:是)',
  `mold_maintenance` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否需要模具维护(0:否 1:是)',
  `equipment_maintenance` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否需要设备维护(0:否 1:是)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_calendar_id`(`calendar_id` ASC) USING BTREE,
  INDEX `idx_work_date`(`work_date` ASC) USING BTREE,
  INDEX `idx_shift_type`(`shift_type` ASC) USING BTREE,
  INDEX `idx_is_work_day`(`is_work_day` ASC) USING BTREE,
  CONSTRAINT `erp_production_calendar_detail_ibfk_1` FOREIGN KEY (`calendar_id`) REFERENCES `erp_production_calendar` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 4006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '生产日历明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_line
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_line`;
CREATE TABLE `erp_production_line`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '生产线ID',
  `production_line_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '生产线编码',
  `production_line_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '生产线名称',
  `production_line_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '生产线类型（1装配线 2加工线 3包装线 4混合线）',
  `department_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `department_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名称',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '位置',
  `length` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '生产线长度(米)',
  `width` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '生产线宽度(米)',
  `height` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '生产线高度(米)',
  `design_capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '设计产能',
  `actual_capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '实际产能',
  `capacity_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '件/小时' COMMENT '产能单位',
  `efficiency_rate` decimal(5, 4) NULL DEFAULT 1.0000 COMMENT '效率系数',
  `takt_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '节拍时间(秒)',
  `cycle_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '周期时间(分钟)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1维护中 2停用）',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_production_line_code`(`production_line_code` ASC) USING BTREE,
  INDEX `idx_production_line_name`(`production_line_name` ASC) USING BTREE,
  INDEX `idx_department_id`(`department_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '生产线主表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_order
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_order`;
CREATE TABLE `erp_production_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Order ID',
  `order_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Order Code',
  `order_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Order Name',
  `order_type` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Order Type',
  `product_id` bigint NOT NULL COMMENT 'Product Material ID',
  `bom_id` bigint NULL DEFAULT NULL COMMENT 'BOM ID',
  `process_route_id` bigint NULL DEFAULT NULL COMMENT 'Process Route ID',
  `unit_id` bigint NULL DEFAULT NULL COMMENT 'Unit ID',
  `planned_quantity` decimal(18, 4) NOT NULL COMMENT 'Planned Quantity',
  `completed_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT 'Completed Quantity',
  `scrapped_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT 'Scrapped Quantity',
  `remaining_quantity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT 'Remaining Quantity',
  `planned_start_date` datetime NULL DEFAULT NULL COMMENT 'Planned Start Date',
  `planned_end_date` datetime NULL DEFAULT NULL COMMENT 'Planned End Date',
  `actual_start_date` datetime NULL DEFAULT NULL COMMENT 'Actual Start Date',
  `actual_end_date` datetime NULL DEFAULT NULL COMMENT 'Actual End Date',
  `priority` int NULL DEFAULT 0 COMMENT 'Priority',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Status',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Create By',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Update By',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Delete Flag',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_order_code`(`order_code` ASC) USING BTREE,
  INDEX `idx_product_material_id`(`product_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  INDEX `idx_planned_start_date`(`planned_start_date` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Production Order Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_order_detail`;
CREATE TABLE `erp_production_order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Primary Key ID',
  `order_id` bigint NOT NULL COMMENT 'Production Order ID',
  `process_id` bigint NOT NULL COMMENT 'Process ID',
  `process_order` int NOT NULL COMMENT 'Process Order',
  `work_center_id` bigint NULL DEFAULT NULL COMMENT 'Work Center ID',
  `equipment_id` bigint NULL DEFAULT NULL COMMENT 'Equipment ID',
  `mold_id` bigint NULL DEFAULT NULL COMMENT 'Mold ID',
  `planned_duration` decimal(10, 2) NULL DEFAULT 0.00 COMMENT 'Planned Duration',
  `actual_duration` decimal(10, 2) NULL DEFAULT 0.00 COMMENT 'Actual Duration',
  `operator_id` bigint NULL DEFAULT NULL COMMENT 'Operator ID',
  `mold_setup_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT 'Mold Setup Time',
  `mold_removal_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT 'Mold Removal Time',
  `temperature_control_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT 'Temperature Control Time',
  `pressure_control_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT 'Pressure Control Time',
  `quality_check_time` decimal(8, 2) NULL DEFAULT 0.00 COMMENT 'Quality Check Time',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT 'Status',
  `start_time` datetime NULL DEFAULT NULL COMMENT 'Start Time',
  `end_time` datetime NULL DEFAULT NULL COMMENT 'End Time',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Create By',
  `create_time` datetime NULL DEFAULT NULL COMMENT 'Create Time',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Update By',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update Time',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'Delete Flag',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_id`(`order_id` ASC) USING BTREE,
  INDEX `idx_process_id`(`process_id` ASC) USING BTREE,
  INDEX `idx_work_center_id`(`work_center_id` ASC) USING BTREE,
  INDEX `idx_equipment`(`equipment_id` ASC) USING BTREE,
  INDEX `idx_mold`(`mold_id` ASC) USING BTREE,
  INDEX `idx_operator`(`operator_id` ASC) USING BTREE,
  INDEX `idx_status`(`status` ASC) USING BTREE,
  CONSTRAINT `erp_production_order_detail_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `erp_production_order` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单工艺路线' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_production_order_materials
-- ----------------------------
DROP TABLE IF EXISTS `erp_production_order_materials`;
CREATE TABLE `erp_production_order_materials`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Serial Number',
  `order_id` bigint NULL DEFAULT NULL COMMENT 'Production Order ID',
  `material_id` bigint NULL DEFAULT NULL COMMENT 'Material ID',
  `quantity` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Quantity',
  `issued_qty` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Issued Quantity',
  `need_qty` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Need Quantity',
  `unit_id` bigint NULL DEFAULT NULL COMMENT 'Unit ID',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Remark',
  `create_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Production Order Materials Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_shelf
-- ----------------------------
DROP TABLE IF EXISTS `erp_shelf`;
CREATE TABLE `erp_shelf`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `shelf_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货架编码',
  `shelf_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '货架名称',
  `warehouse_id` bigint NOT NULL COMMENT '所属仓库ID',
  `shelf_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '货架类型(1:普通货架 2:重型货架 3:悬臂货架 4:阁楼货架 5:流利式货架)',
  `area_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  `row_num` int NULL DEFAULT NULL COMMENT '排号',
  `column_num` int NULL DEFAULT NULL COMMENT '列号',
  `height` decimal(10, 2) NULL DEFAULT NULL COMMENT '货架高度(米)',
  `width` decimal(10, 2) NULL DEFAULT NULL COMMENT '货架宽度(米)',
  `depth` decimal(10, 2) NULL DEFAULT NULL COMMENT '货架深度(米)',
  `max_weight` decimal(10, 2) NULL DEFAULT NULL COMMENT '最大承重(kg)',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用(0:禁用 1:启用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_shelf_code`(`shelf_code` ASC) USING BTREE,
  INDEX `idx_warehouse`(`warehouse_id` ASC) USING BTREE,
  INDEX `idx_area`(`area_code` ASC) USING BTREE,
  CONSTRAINT `erp_shelf_ibfk_1` FOREIGN KEY (`warehouse_id`) REFERENCES `erp_warehouse` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '货架表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_supplier
-- ----------------------------
DROP TABLE IF EXISTS `erp_supplier`;
CREATE TABLE `erp_supplier`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '供应商编码',
  `supplier_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '供应商名称',
  `supplier_short_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '供应商简称',
  `supplier_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '供应商类型（1生产商 2代理商 3贸易商）',
  `business_license` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '营业执照号',
  `tax_registration_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '税务登记号',
  `legal_representative` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '法定代表人',
  `registered_capital` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '注册资本',
  `establishment_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `business_scope` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '经营范围',
  `contact_person` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '地址',
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮政编码',
  `bank_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '银行账号',
  `account_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '账户名称',
  `credit_rating` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'C' COMMENT '信用等级（A优秀 B良好 C一般 D较差 E很差）',
  `payment_terms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '付款条件',
  `delivery_terms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '交货条件',
  `quality_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '质量评级',
  `delivery_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '交付评级',
  `service_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '服务评级',
  `overall_rating` decimal(3, 2) NULL DEFAULT 0.00 COMMENT '综合评级',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_supplier_code`(`supplier_code` ASC) USING BTREE,
  INDEX `idx_supplier_name`(`supplier_name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '供应商表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_unit
-- ----------------------------
DROP TABLE IF EXISTS `erp_unit`;
CREATE TABLE `erp_unit`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `unit_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位编码',
  `unit_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '单位名称',
  `unit_symbol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单位符号',
  `is_base_unit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '是否基本单位(0:否 1:是)',
  `conversion_rate` decimal(18, 6) NULL DEFAULT 1.000000 COMMENT '换算比例',
  `base_unit_id` bigint NULL DEFAULT NULL COMMENT '基本单位ID',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态(0:正常 1:停用)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_unit_code`(`unit_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '计量单位表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `erp_warehouse`;
CREATE TABLE `erp_warehouse`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `warehouse_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '仓库名称',
  `warehouse_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '仓库类型(1:原材料仓 2:半成品仓 3:成品仓 4:包装物仓 5:辅料仓)',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父级仓库ID',
  `manager` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库管理员',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '仓库地址',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '是否启用(0:禁用 1:启用)',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '删除标志(0:正常 1:删除)',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_warehouse_code`(`warehouse_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '仓库表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_work_center
-- ----------------------------
DROP TABLE IF EXISTS `erp_work_center`;
CREATE TABLE `erp_work_center`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '工作中心ID',
  `work_center_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '中心编码',
  `work_center_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '中心名称',
  `work_center_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '中心类型',
  `department_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '位置',
  `capacity_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '产能类型',
  `standard_capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '标准产能',
  `capacity_unit` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '小时' COMMENT '产能单位',
  `efficiency_rate` decimal(5, 4) NULL DEFAULT 1.0000 COMMENT '效率系数',
  `available_capacity` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '可用产能',
  `cost_center` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成本中心',
  `cost_per_hour` decimal(18, 4) NULL DEFAULT 0.0000 COMMENT '小时成本',
  `setup_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '准备时间(分钟)',
  `teardown_time` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '清理时间(分钟)',
  `is_active` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '是否启用',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在，1代表删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_work_center_code`(`work_center_code` ASC) USING BTREE,
  INDEX `idx_work_center_name`(`work_center_name` ASC) USING BTREE,
  INDEX `idx_department_id`(`department_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工作中心表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
