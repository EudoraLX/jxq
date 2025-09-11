-- 测试关联表功能
-- 执行时间：2024年

-- 1. 创建测试表：用户表
CREATE TABLE IF NOT EXISTS test_user (
  user_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  user_name varchar(50) NOT NULL COMMENT '用户名称',
  user_code varchar(50) NOT NULL COMMENT '用户编码',
  status char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (user_id)
) ENGINE=InnoDB COMMENT='测试用户表';

-- 2. 创建测试表：部门表
CREATE TABLE IF NOT EXISTS test_dept (
  dept_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  dept_name varchar(50) NOT NULL COMMENT '部门名称',
  dept_code varchar(50) NOT NULL COMMENT '部门编码',
  parent_id bigint(20) DEFAULT 0 COMMENT '父部门ID',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (dept_id)
) ENGINE=InnoDB COMMENT='测试部门表';

-- 3. 创建测试表：订单表（包含关联字段）
CREATE TABLE IF NOT EXISTS test_order (
  order_id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  order_no varchar(50) NOT NULL COMMENT '订单编号',
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  dept_id bigint(20) NOT NULL COMMENT '部门ID',
  order_amount decimal(10,2) NOT NULL COMMENT '订单金额',
  order_status char(1) DEFAULT '0' COMMENT '订单状态（0待支付 1已支付 2已取消）',
  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (order_id),
  KEY idx_user_id (user_id),
  KEY idx_dept_id (dept_id)
) ENGINE=InnoDB COMMENT='测试订单表';

-- 4. 插入测试数据
INSERT INTO test_user (user_name, user_code, status) VALUES 
('张三', 'USER001', '0'),
('李四', 'USER002', '0'),
('王五', 'USER003', '1');

INSERT INTO test_dept (dept_name, dept_code, parent_id) VALUES 
('技术部', 'DEPT001', 0),
('销售部', 'DEPT002', 0),
('人事部', 'DEPT003', 0);

INSERT INTO test_order (order_no, user_id, dept_id, order_amount, order_status) VALUES 
('ORD001', 1, 1, 100.00, '1'),
('ORD002', 2, 2, 200.00, '0'),
('ORD003', 3, 3, 300.00, '2');

-- 5. 测试查询关联数据
SELECT 
  o.order_id,
  o.order_no,
  u.user_name,
  d.dept_name,
  o.order_amount,
  o.order_status
FROM test_order o
LEFT JOIN test_user u ON o.user_id = u.user_id
LEFT JOIN test_dept d ON o.dept_id = d.dept_id;
