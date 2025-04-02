-- 创建数据库（支持中文存储）
CREATE DATABASE IF NOT EXISTS `crm_achievement`
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE `crm_achievement`;
-- 创建部门表
DROP TABLE IF EXISTS `crm_dept`;
CREATE TABLE `crm_dept` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT 'ID',
`pid` VARCHAR(36) DEFAULT NULL COMMENT '上级部门',
`sub_count` INT(5) DEFAULT 0 COMMENT '子部门数目',
`name` VARCHAR(255) NOT NULL COMMENT '部门名称',
`dept_sort` INT(5) DEFAULT 999 COMMENT '排序',
`enabled` TINYINT NOT NULL COMMENT '部门状态',
`created_by` VARCHAR(36) DEFAULT NULL COMMENT '创建者ID',
`updated_by` VARCHAR(36) DEFAULT NULL COMMENT '更新者ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
FOREIGN KEY (`pid`) REFERENCES `crm_dept`(`id`),
KEY `idx_pid` (`pid`),
KEY `idx_enabled` (`enabled`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='部门';

USE `crm_achievement`;
-- 创建用户表（包含必要字段约束）
DROP TABLE IF EXISTS `crm_user`;
CREATE TABLE IF NOT EXISTS `crm_user` (
`id` VARCHAR(36)  DEFAULT (UUID()) PRIMARY KEY COMMENT '用户ID',
`name` VARCHAR(50) NOT NULL COMMENT '中文姓名',
`password` VARCHAR(255) NOT NULL COMMENT '密码',
`dept_id` VARCHAR(36) DEFAULT NULL COMMENT '部门ID',
`status` TINYINT NOT NULL COMMENT '用户状态',
FOREIGN KEY (`dept_id`) REFERENCES `crm_dept`(`id`) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户表';

USE `crm_achievement`;
-- 创建客户表
DROP TABLE IF EXISTS `crm_customer`;
CREATE TABLE IF NOT EXISTS `crm_customer` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '客户ID',
`name` VARCHAR(50) NOT NULL COMMENT '中文姓名',
`city` VARCHAR(20) NOT NULL COMMENT '所在城市',
`phone` VARCHAR(15) NOT NULL COMMENT '联系电话',
`status` TINYINT NOT NULL COMMENT '状态',
`created_by` VARCHAR(36) DEFAULT NULL COMMENT '创建人ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_by` VARCHAR(36) DEFAULT NULL COMMENT '更新人ID',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
FOREIGN KEY (`created_by`) REFERENCES `crm_user`(`id`) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='客户表';


USE `crm_achievement`;
-- 角色表
DROP TABLE IF EXISTS `crm_role`;
CREATE TABLE `crm_role` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '角色ID',
`name` VARCHAR(100) NOT NULL COMMENT '角色名称',
`level` INT DEFAULT NULL COMMENT '角色级别',
`logical_key` VARCHAR(50) NOT NULL COMMENT '逻辑标识-用于代码',
`description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
`created_by` VARCHAR(36) DEFAULT NULL COMMENT '创建者ID',
`updated_by` VARCHAR(36) DEFAULT NULL COMMENT '更新者ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
UNIQUE KEY `uniq_name` (`name`),
KEY `idx_level` (`level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='角色表';

USE `crm_achievement`;
-- 权限表
DROP TABLE IF EXISTS `crm_permission`;
CREATE TABLE `crm_permission` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '权限ID',
`perm_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '权限标识（如"crm:customer:view"）',
`perm_name` VARCHAR(50) NOT NULL COMMENT '权限名称（如"查看客户"）',
`description` VARCHAR(255) COMMENT '权限描述'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='权限表';

USE `crm_achievement`;
-- 菜单表
DROP TABLE IF EXISTS `crm_menu`;
CREATE TABLE `crm_menu` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '菜单ID',
`menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称（前端展示:如"客户管理"）',
`parent_id` VARCHAR(36) DEFAULT '00000000-0000-0000-0000-000000000000' COMMENT '父菜单ID',
`sub_count` INT(5) COMMENT '子菜单数目',
`menu_icon` VARCHAR(50) COMMENT '菜单图标（如"user"）',
`menu_sort` INT(5) COMMENT '菜单排序值，用于控制显示顺序',
`component_name` VARCHAR(100) COMMENT '组件名称（供前端框架注册）',
`component_path` VARCHAR(100) COMMENT '前端组件路径（如 views/customer/index.vue）',
`url_path` VARCHAR(255) COMMENT '前端路由路径（如"/customer"）',
`menu_type` TINYINT COMMENT '菜单类型（如：按钮）',
`perm_code` VARCHAR(50) COMMENT '关联的权限标识',
`i_frame` TINYINT DEFAULT 0 COMMENT '是否为外链菜单（0否，1是）',
`cache` TINYINT DEFAULT 0 COMMENT '是否缓存页面（0否，1是）',
`hidden` TINYINT DEFAULT 0 COMMENT '是否隐藏菜单（0否，1是）',
`created_by` VARCHAR(36) COMMENT '创建者ID',
`updated_by` VARCHAR(36) COMMENT '更新者ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
FOREIGN KEY (`perm_code`) REFERENCES `crm_permission` (`perm_code`) ON UPDATE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='菜单表';
-- 创建菜单表之后执行,插入根菜单（id 设为特定值，如 '00000000-0000-0000-0000-000000000000'）
INSERT INTO `crm_menu` (`id`, `menu_name`)
VALUES ('00000000-0000-0000-0000-000000000000', '根菜单');

USE `crm_achievement`;
-- 字典表
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE `dict_type` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT 'ID',
`name` VARCHAR(255) NOT NULL COMMENT '字典类型名称（如"用户状态"）',
`type_code` VARCHAR(50) NOT NULL COMMENT '字典类型唯一标识（如 user_status）',      `description` VARCHAR(255) COMMENT '字典类型描述',
`created_by` VARCHAR(36) NOT NULL COMMENT '创建人ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_by` VARCHAR(36) NOT NULL COMMENT '更新人ID',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
UNIQUE KEY `uk_type_code` (`type_code`),
FOREIGN KEY (`created_by`) REFERENCES `crm_user`(`id`) ON UPDATE CASCADE,
FOREIGN KEY (`updated_by`) REFERENCES `crm_user`(`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='数据字典类型表';


USE `crm_achievement`;
-- 字典细节表
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT 'ID',
`dict_id` VARCHAR(36) NOT NULL COMMENT '字典ID（外键）',
`value` TINYINT UNSIGNED NOT NULL COMMENT '字典数值（如用户状态 0、1）',
`label` VARCHAR(50) NOT NULL COMMENT '字典标签（前端显示,如“禁用”）',
`sort` INT DEFAULT 0 COMMENT '排序号',
`created_by` VARCHAR(36) NOT NULL COMMENT '创建人ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_by` VARCHAR(36) NOT NULL COMMENT '更新人ID',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
FOREIGN KEY (`dict_id`) REFERENCES `dict_type`(`id`),
FOREIGN KEY (`created_by`) REFERENCES `crm_user`(`id`) ON UPDATE CASCADE,
FOREIGN KEY (`updated_by`) REFERENCES `crm_user`(`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='数据字典明细表';


USE `crm_achievement`;
-- 角色-权限关联表
DROP TABLE IF EXISTS `crm_role_permission`;
CREATE TABLE `crm_role_permission` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '关联ID',
`role_id` VARCHAR(36) NOT NULL COMMENT '角色ID',
`permission_perm_code` VARCHAR(50) NOT NULL COMMENT '权限标识',
`permission_id` VARCHAR(36) COMMENT '权限ID',
`created_by` VARCHAR(36) COMMENT '创建人ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_by` VARCHAR(36) COMMENT '更新人ID',
`updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    -- 外键指向角色表和权限表
 FOREIGN KEY (`role_id`) REFERENCES `crm_role`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (`permission_perm_code`) REFERENCES `crm_permission`(`perm_code`) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (`permission_id`) REFERENCES `crm_permission`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    -- 唯一约束避免重复分配
 UNIQUE KEY `uniq_role_permission` (`role_id`, `permission_perm_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='角色-权限关联表';


USE `crm_achievement`;
-- 用户-角色关联表
DROP TABLE IF EXISTS `crm_user_role`;
CREATE TABLE `crm_user_role` (
`id` VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY COMMENT '关联ID',
`user_id` VARCHAR(36) NOT NULL COMMENT '用户ID',
`role_id` VARCHAR(36) NOT NULL COMMENT '角色ID',
`assigned_by` VARCHAR(36) COMMENT '分配人ID',
`created_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '分配时间',
    -- 外键指向用户表和角色表
 FOREIGN KEY (`user_id`) REFERENCES `crm_user`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
 FOREIGN KEY (`role_id`) REFERENCES `crm_role`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    -- 唯一约束避免重复分配
 UNIQUE KEY `uniq_user_role` (`user_id`, `role_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=COMPACT COMMENT='用户-角色关联表';
