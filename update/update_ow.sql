


-- 2020.11.13 ly
ALTER TABLE `ow_member`
	ADD COLUMN `remark1` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注1' AFTER `sort_order`,
	ADD COLUMN `remark2` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注2' AFTER `remark1`,
	ADD COLUMN `remark3` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注3' AFTER `remark2`;
ALTER TABLE `ow_member_apply`
	ADD COLUMN `draw_acceptor` VARCHAR(30) NULL DEFAULT NULL COMMENT '入党志愿书接收人' AFTER `apply_sn`,
	ADD COLUMN `join_apply_time` DATE NULL DEFAULT NULL COMMENT '入党申请时间' AFTER `apply_time`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `accept_receipt_time` DATE NULL DEFAULT NULL COMMENT '回执接收时间' AFTER `has_receipt`;
-- 更新ow_member_view、ow_member_apply_view、ow_member_out_view


-- 2020.09.25 sxx

--  执行 /test/branch_member.jsp

-- 2020.09.24 sxx
ALTER TABLE `ow_branch_member`
	DROP FOREIGN KEY `FK_ow_branch_member_base_meta_type`;
ALTER TABLE `ow_branch_member`
	CHANGE COLUMN `type_id` `types` VARCHAR(50) NOT NULL COMMENT '职务，关联元数据（书记、副书记、各类委员）,以逗号分隔' AFTER `user_id`;
--更新ow_branch_member_view,ext_branch_view,ext_branch_view2

-- 2020.07.17 ly
ALTER TABLE `ow_branch_member_group`
	CHANGE COLUMN `appoint_time` `appoint_time` DATE NULL COMMENT '任命时间，本届班子任命时间' AFTER `actual_tran_time`;
ALTER TABLE `ow_party_member_group`
	CHANGE COLUMN `appoint_time` `appoint_time` DATE NULL COMMENT '任命时间，本届班子任命时间' AFTER `actual_tran_time`;
UPDATE ow_party SET found_time=NULL WHERE id IN (120,121,122);
UPDATE ow_branch SET found_time=NULL WHERE party_id IN (120,121,122);
UPDATE ow_party_member_group SET appoint_time=NULL WHERE party_id IN (120,121,122);
UPDATE ow_branch_member_group obmg,ow_branch ob SET obmg.appoint_time=NULL WHERE ob.party_id IN (120,121,122) AND ob.id=obmg.branch_id;


-- 2020.07.17 ly
ALTER TABLE `ow_party`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '关联单位' AFTER `url`,
	CHANGE COLUMN `unit_type_id` `unit_type_id` INT(10) UNSIGNED NOT NULL COMMENT '关联单位属性，关联元数据，企业，事业单位' AFTER `type_id`,
	CHANGE COLUMN `is_separate` `is_separate` TINYINT(1) UNSIGNED NOT NULL COMMENT '所在单位是否独立法人' AFTER `is_enterprise_nationalized`;
-- 更新`ow_party_view`

-- 2020.7.13 ly
ALTER TABLE `ow_party`
	CHANGE COLUMN `phone` `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '联系电话' AFTER `is_separate`;
-- 更新`ow_party_view`


-- 2020.7.3 ly
ALTER TABLE `ow_member_certify`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `certify_date`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `party_id`,
	ADD COLUMN `status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1分党委审批 2党支部审批 ' AFTER `branch_id`,
	ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`,
	ADD COLUMN `reason` VARCHAR(100) NULL DEFAULT NULL COMMENT '返回修改原因' AFTER `is_back`,
	ADD COLUMN `apply_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间' AFTER `reason`;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2541, 0, '临时组织关系介绍信', '', 'url', '', '/member/memberCertify?cls=0', 258, '0/1/258/', 1, 'userMemberCertify:*', NULL, NULL, NULL, 1, 400);


-- 2020.6.29 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2540, 0, '临时组织关系介绍信', '', 'url', '', '/member/memberCertify', 105, '0/1/105/', 1, 'memberCertify:*', NULL, NULL, NULL, 1, 15000);

2020-04-30
ALTER TABLE `ow_organizer`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型， 1 校级组织员 2 院系级组织员   3支部组织员' AFTER `year`;

2020-04-27
ALTER TABLE `ow_organizer`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '联系党委' AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '联系党支部' AFTER `party_id`;
ALTER TABLE `ow_organizer`
	ADD COLUMN `phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '联系方式' AFTER `units`;

2019-12-27
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2717, 0, '党支部书记考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:base', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2718, 0, '党支部考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:base', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `ow_party_report`
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属党支部' AFTER `party_name`,
	ADD COLUMN `branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属党支部名称' AFTER `branch_id`;
ALTER TABLE `ow_member_report`
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属党支部' AFTER `party_name`,
	ADD COLUMN `branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属党支部名称' AFTER `branch_id`;

-- 20191227 ly 需要更新ow_member_out_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2526, 0, '介绍信自助打印', '', 'function', '', NULL, 252, '0/1/105/252/', 1, 'memberOutSelfPrint:edit', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `ow_member_out`
	ADD COLUMN `is_self_print` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否允许自助打印' AFTER `status`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `is_self_print_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '自助打印次数' AFTER `is_self_print`;

2019-12-23
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2712, 0, '党支部考核', '', 'url', '', '/member/memberReport', 260, '0/1/260/', 0, 'owReport:menu', NULL, NULL, NULL, 1, 80);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2713, 0, '党组织书记考核:查看', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2714, 0, '党组织书记考核:编辑', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2715, 0, '党支部考核:查看', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2716, 0, '党支部考核:编辑', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:edit', NULL, NULL, NULL, 1, NULL);

CREATE TABLE `ow_party_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度',
	`party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属分党委',
	`party_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属分党委名称',
	`report_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '工作总结word',
	`eva_result` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '考核结果',
	`eva_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '考核结果文件pdf',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态  1未报送  2 已报送',
	PRIMARY KEY (`id`)
)
COMMENT='党支部考核'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=6
;
CREATE TABLE `ow_member_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
	`party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属分党委',
	`party_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属分党委名称',
	`report_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '述职报告word',
	`eva_result` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '考核结果',
	`eva_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '考核结果文件pdf',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态  1未报送  2 已报送',
	PRIMARY KEY (`id`)
)
COMMENT='党组织书记考核'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=15
;

2019-12-03

-- 更新视图 ow_party_view、ow_member_view;

-- 添加字段 桑文帅
ALTER TABLE `ow_party` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;
ALTER TABLE `ow_member` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `profile`;
ALTER TABLE `ow_branch` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;

2019-09-24

-- 添加资源 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (200, 0, '党小组', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroup:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (201, 0, '党小组成员', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroupMember:*', NULL, NULL, NULL, 1, NULL);

-- 建表语句 桑文帅
CREATE TABLE IF NOT EXISTS `ow_branch_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '小组名称',
  `branch_id` int(10) NOT NULL COMMENT '支部ID',
  `user_id` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `count_member` int(10) unsigned DEFAULT NULL COMMENT '党小组总人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='党小组';

CREATE TABLE IF NOT EXISTS `ow_branch_group_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '党小组ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '成员',
  `is_leader` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是党小组组长',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='党小组成员';

