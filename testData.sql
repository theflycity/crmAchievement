--  步骤1：插入部门数据（手工指定ID以保证层次关系）
INSERT INTO `crm_dept` (`id`, `pid`, `name`, `enabled`) VALUES
('dept_root',   NULL, '集团公司', 1), -- 顶级部门
('dept_sales',  'dept_root', '销售部', 1),
('dept_sales_1',  'dept_sales', '销售一组', 1),
('dept_sales_2',  'dept_sales', '销售二组', 1),
('dept_tech',   'dept_root', '技术中心', 1);

--  步骤2：插入用户数据（手动绑定部门ID）
INSERT INTO `crm_user` (`id`, `name`, `password`, `dept_id`, `status`) VALUES
('user_admin',   'admin', '123', 'dept_root', 1),
('user_sales1',  '01',  '456', 'dept_sales_1', 1),
('user_sales2',  '02',  '456', 'dept_sales_1', 1),
('user_sales3',  '03',  '456', 'dept_sales_2', 1),
('user_tech1',   '99',  '789', 'dept_tech', 1);

--  步骤3：插入权限（确保唯一标识符准确）
INSERT INTO `crm_permission` (`id`,`perm_code`, `perm_name`, `description`) VALUES
 ('PC_VIEW','crm:customer:view',  '查看客户', '允许查看客户基本信息'),
 ('PC_EDIT','crm:customer:edit',  '编辑客户', '允许修改客户信息'),
 ('PC_ADD','crm:customer:add',  '增加客户', '允许增加客户信息'),
 ('PC_DELETE','crm:customer:delete',  '删除客户', '允许删除客户信息');

--  步骤4：插入菜单（分层次按顺序插入）
-- 顶级菜单
INSERT INTO `crm_menu` (`id`, `menu_name`, `parent_id`, `perm_code`) VALUES
('menu_system',  '系统管理', '00000000-0000-0000-0000-000000000000', NULL),
('menu_customer','客户管理', '00000000-0000-0000-0000-000000000000', 'crm:customer:view');

-- 二级菜单（系统管理子项）
INSERT INTO `crm_menu` (`id`, `menu_name`, `parent_id`) VALUES
('menu_user',    '用户管理', 'menu_system'),
('menu_role',    '角色管理', 'menu_system');

--  步骤5：插入角色数据（关联管理员用户）
INSERT INTO `crm_role` (`id`, `name`, `logical_key`, `level`, `created_by`, `updated_by`) VALUES
('role_admin',  '超级管理员', 'admin',      0, 'user_admin', 'user_admin'),
('role_sales_manager',  '销售组长', 'sales_manager',      1, 'user_admin', 'user_admin'),
('role_sales',  '销售',  'sales', 2, 'user_admin', 'user_admin');

--  步骤6：插入字典数据（完整工作流）
-- 字典类型
INSERT INTO `dict_type` (`id`, `name`, `type_code`, `created_by`, `updated_by`) VALUES
('dict_user_status', '用户状态', 'user_status', 'user_admin', 'user_admin');

-- 字典明细
INSERT INTO `dict_data` (`id`, `dict_id`, `value`, `label`, `created_by`, `updated_by`) VALUES
('data_user_enable', 'dict_user_status', 1, '启用', 'user_admin', 'user_admin'),
('data_user_disable','dict_user_status', 0, '停用', 'user_admin', 'user_admin');

--  步骤7：插入客户数据（绑定销售用户）
INSERT INTO `crm_customer` (`id`, `name`, `city`, `phone`, `status`, `created_by`,`updated_by`) VALUES
('cust_sh01', '上海AI实验室', '上海', '021-67890123', 1, 'user_sales1', 'user_sales1'),
('cust_sh02', '腾达集团', '上海', '021-33530123', 1, 'user_sales2', 'user_sales2'),
('cust_tj04', '神华集团', '天津', '022-00930123', 1, 'user_sales2', 'user_sales2'),
('cust_sh03', '天火工作室', '上海', '021-34290123', 1, 'user_sales3', 'user_sales3'),
('cust_bj01', '北京航天科技', '北京', '010-56781234', 1, 'user_sales3', 'user_sales3');

-- 角色-权限关联数据（假设：
-- role_admin 拥有所有权限
-- role_sales 拥有查看和编辑客户权限）
INSERT INTO `crm_role_permission` (`role_id`, `permission_perm_code`, `permission_id`, `created_by`, `updated_by`)
VALUES
('role_admin', 'crm:customer:view','PC_VIEW', 'user_admin', 'user_admin'),
('role_admin', 'crm:customer:edit', 'PC_EDIT','user_admin', 'user_admin'),
('role_admin', 'crm:customer:add','PC_ADD', 'user_admin', 'user_admin'),
('role_admin', 'crm:customer:delete','PC_DELETE', 'user_admin', 'user_admin'),
('role_sales_manager', 'crm:customer:view','PC_VIEW', 'user_admin', 'user_admin'),
('role_sales_manager', 'crm:customer:add','PC_ADD', 'user_admin', 'user_admin'),
('role_sales_manager', 'crm:customer:edit','PC_EDIT', 'user_admin', 'user_admin'),
('role_sales', 'crm:customer:view','PC_VIEW', 'user_admin', 'user_admin'),
('role_sales', 'crm:customer:add','PC_ADD', 'user_admin', 'user_admin');

-- 用户-角色关联数据
INSERT INTO `crm_user_role` (`user_id`, `role_id`, `assigned_by`)
VALUES
('user_admin', 'role_admin', 'user_admin'),            -- 管理员用户拥有管理员角色
('user_sales1', 'role_sales_manager', 'user_admin'),   -- 销售用户1被分配销售组长
('user_sales1', 'role_sales', 'user_admin'),           -- 销售用户1被分配销售角色
('user_sales2', 'role_sales', 'user_admin'),           -- 销售用户2被分配销售角色
('user_sales3', 'role_sales_manager', 'user_admin');   -- 销售用户3被分配销售角色


