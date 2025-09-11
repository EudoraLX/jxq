-- 为代码生成器表添加关联表相关字段
-- 执行时间：2024年

-- 1. 为gen_table表添加is_related字段
ALTER TABLE gen_table ADD COLUMN is_related char(1) DEFAULT '0' COMMENT '是否为关联主表（1是 0否）';

-- 2. 为gen_table_column表添加related_table字段
ALTER TABLE gen_table_column ADD COLUMN related_table varchar(200) DEFAULT NULL COMMENT '关联表名称';

-- 3. 为gen_table_column表添加related_fields字段
ALTER TABLE gen_table_column ADD COLUMN related_fields varchar(500) DEFAULT NULL COMMENT '关联字段，以逗号分隔';

-- 4. 添加索引以提高查询性能
CREATE INDEX idx_gen_table_is_related ON gen_table(is_related);
CREATE INDEX idx_gen_table_column_related_table ON gen_table_column(related_table);
