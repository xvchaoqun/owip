
2019-08-05
新增角色 桑文帅
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_allPs_Admin', '全部二级党校管理员', '728,729,1065,1066,1067,1068,1069,1071,1072,1073,1076,1074,1077,1078,1079,1080,1075,1081,1082,1083,1070', '-1', NULL, 0, 0, 58, '');
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_ps_admin', '二级党校管理员', '1068,1071,1072,1073,1074,1075,1070', '-1', NULL, 0, 1, 59, '');

2019-08-05
新增系统资源 桑文帅
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '添加、修改二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '批量转移二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '删除二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '二级党校基本信息', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:view', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '二级党校调序', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '年度工作任务', '', 'url', '', 'ps/psTask', 728, '0/1/728/', 1, 'psTask:*', NULL, NULL, NULL, 1, 4310);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '二级党校建设单位', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psParty:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '增加、修改建设单位', '', 'function', '', NULL, 1071, '0/1/728/729/1071/', 1, 'psParty:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '删除建设单位', '', 'function', '', NULL, 1071, '0/1/728/729/1071/', 1, 'psParty:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '二级党校组织架构', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psMember:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '二级党校系统管理员', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psAdmin:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '撤销建设单位', '', 'function', '', NULL, 1071, '0/1/728/729/1071/', 1, 'psParty:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '添加、修改组织架构', '', 'function', '', NULL, 1074, '0/1/728/729/1074/', 1, 'psMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '删除组织架构', '', 'function', '', NULL, 1074, '0/1/728/729/1074/', 1, 'psMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '撤销组织架构', '', 'function', '', NULL, 1074, '0/1/728/729/1074/', 1, 'psMember:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '组织架构调序', '', 'function', '', NULL, 1074, '0/1/728/729/1074/', 1, 'psMember:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '添加、修改系统管理员', '', 'function', '', NULL, 1075, '0/1/728/729/1075/', 1, 'psAdmin:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '删除系统管理员', '', 'function', '', NULL, 1075, '0/1/728/729/1075/', 1, 'psAdmin:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '撤销系统管理员', '', 'function', '', NULL, 1075, '0/1/728/729/1075/', 1, 'psAdmin:history', NULL, NULL, NULL, 1, NULL);

2019-07-30
新增发布时间字段 桑文帅
ALTER TABLE `ps_task` ADD COLUMN `release_date` DATETIME NULL COMMENT '发布时间' AFTER `files`;

2019-07-25
新增排序字段 桑文帅
ALTER TABLE `ps_admin` ADD COLUMN `sort_order` INT(11) NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

2019-07-22
新增排序字段 桑文帅
ALTER TABLE `ps_member` ADD COLUMN `sort_order` INT NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

2019-07-20
修改表字段属性 桑文帅
ALTER TABLE `ps_member` ALTER `type` DROP DEFAULT;
ALTER TABLE `ps_member` CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '党校职务' AFTER `seq`;
