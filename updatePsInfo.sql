2019-08-06
-- 修改字段属性 桑文帅
ALTER TABLE `ps_party` ALTER `start_date` DROP DEFAULT;
ALTER TABLE `ps_party` CHANGE COLUMN `start_date` `start_date` DATE NULL COMMENT '开始时间' AFTER `is_finish`;

-- 新增排序字段 桑文帅
ALTER TABLE `ps_party` ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `start_date`;

2019-08-05
-- 新增元数据 桑文帅
INSERT INTO `base_meta_class` (`role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (NULL, '二级党校职务', '二级党校管理', '二级党校信息', 'mc_party_school_position', '', '', '', 90, 1);

-- 新增元数据属性 桑文帅
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (90, '校长', 'ps_principal', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (90, '副校长', 'ps_viceprincipal', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (90, '工作人员', 'ps_staff', NULL, '', '', 3, 1);

-- 新增角色 桑文帅
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_ps_admin', '二级党校管理员(组织部)', '728,729,2065,2066,2067,2068,2069,2071,2072,2073,2076,2074,2077,2078,2079,2080,2075,2081,2082,2083,2070', '-1', NULL, 0, 0, 58, '');
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_ps_party', '二级党校管理员', '1068,2071,2072,2073,2074,2075,2070', '-1', NULL, 0, 1, 59, '');

-- 新增系统资源 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2065, 0, '添加、修改二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2066, 0, '批量转移二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2067, 0, '删除二级党校', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2068, 0, '二级党校基本信息', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:view', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2069, 0, '二级党校调序', '', 'function', '', NULL, 729, '0/1/728/729/', 1, 'psInfo:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2070, 0, '年度工作任务', '', 'url', '', 'ps/psTask', 728, '0/1/728/', 1, 'psTask:*', NULL, NULL, NULL, 1, 4310);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2071, 0, '二级党校建设单位', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psParty:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2072, 0, '增加、修改建设单位', '', 'function', '', NULL, 2071, '0/1/728/729/2071/', 1, 'psParty:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2073, 0, '删除建设单位', '', 'function', '', NULL, 2071, '0/1/728/729/2071/', 1, 'psParty:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2074, 0, '二级党校组织架构', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psMember:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2075, 0, '二级党校系统管理员', '', 'function', '', NULL, 729, '0/1/728/729/', 0, 'psAdmin:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2076, 0, '撤销建设单位', '', 'function', '', NULL, 2071, '0/1/728/729/2071/', 1, 'psParty:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2077, 0, '添加、修改组织架构', '', 'function', '', NULL, 2074, '0/1/728/729/2074/', 1, 'psMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2078, 0, '删除组织架构', '', 'function', '', NULL, 2074, '0/1/728/729/2074/', 1, 'psMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2079, 0, '撤销组织架构', '', 'function', '', NULL, 2074, '0/1/728/729/2074/', 1, 'psMember:history', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2080, 0, '组织架构调序', '', 'function', '', NULL, 2074, '0/1/728/729/2074/', 1, 'psMember:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2081, 0, '添加、修改系统管理员', '', 'function', '', NULL, 2075, '0/1/728/729/2075/', 1, 'psAdmin:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2082, 0, '删除系统管理员', '', 'function', '', NULL, 2075, '0/1/728/729/2075/', 1, 'psAdmin:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2083, 0, '撤销系统管理员', '', 'function', '', NULL, 2075, '0/1/728/729/2075/', 1, 'psAdmin:history', NULL, NULL, NULL, 1, NULL);

update sys_resource SET sort_order=4320, permission='psInfo:list' WHERE permission = 'psInfo:*';

ALTER TABLE `ps_party`
	CHANGE COLUMN `end_date` `end_date` DATE NULL COMMENT '结束时间' AFTER `start_date`;

-- 新建ps_task_file表 桑文帅
CREATE TABLE IF NOT EXISTS `ps_task_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) NOT NULL DEFAULT '0',
  `file_name` varchar(50) NOT NULL DEFAULT '0',
  `file_path` varchar(200) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='二级党校年度工作任务附件';

2019-07-30
-- 新增发布时间字段 桑文帅
ALTER TABLE `ps_task` ADD COLUMN `release_date` DATETIME NULL COMMENT '发布时间' AFTER `files`;

2019-07-25
-- 新增排序字段 桑文帅
ALTER TABLE `ps_admin` ADD COLUMN `sort_order` INT(11) NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

2019-07-22
-- 新增排序字段 桑文帅
ALTER TABLE `ps_member` ADD COLUMN `sort_order` INT NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

2019-07-20
-- 修改表字段属性 桑文帅
ALTER TABLE `ps_member` ALTER `type` DROP DEFAULT;
ALTER TABLE `ps_member` CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '党校职务' AFTER `seq`;
