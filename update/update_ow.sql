
-- 2021-04-15 llb
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (4010, 0, '常用工作表格下载', '', 'menu', '', NULL, 105, '0/1/105/', 0, 'commonSheet:list', NULL, NULL, NULL, 1, 29750);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (4009, 0, '党员（含预备党员）', '', 'url', '', '/commonSheet/csMember', 4010, '0/1/105/4010/', 1, 'csMember:*', NULL, NULL, NULL, 1, 1000);



-- 2021.4.13 ly
INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `is_deleted`)
    VALUES (2606, '出党类别', '党建综合管理', '党员出党', 'mc_member_quit_type', '是否需要审批', '', '', 2621, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '自动退党', 'mt_quit_self', 1, NULL, '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '开除党籍', 'mt_quit_dismiss', 1, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '党员去世', 'mt_quit_withgod', 0, NULL, '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '不予承认党员身份', 'mt_quit_deny', 1, NULL, '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '学生毕业', 'mt_quit_graduate', 1, NULL, '', 5, 1);
ALTER TABLE `ow_member_quit`
	CHANGE COLUMN `type` `type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '类别，元数据出党类型' AFTER `branch_name`;
UPDATE ow_member_quit SET TYPE=(SELECT id FROM base_meta_type WHERE CODE='mt_quit_graduate') WHERE TYPE=5;
UPDATE ow_member_quit SET TYPE=(SELECT id FROM base_meta_type WHERE CODE='mt_quit_deny') WHERE TYPE=4;
UPDATE ow_member_quit SET TYPE=(SELECT id FROM base_meta_type WHERE CODE='mt_quit_withgod') WHERE TYPE=3;
UPDATE ow_member_quit SET TYPE=(SELECT id FROM base_meta_type WHERE CODE='mt_quit_dismiss') WHERE TYPE=2;
UPDATE ow_member_quit SET TYPE=(SELECT id FROM base_meta_type WHERE CODE='mt_quit_self') WHERE TYPE=1;


-- 2021.4.12 LY
ALTER TABLE `ow_member_transfer`
	CHANGE COLUMN `from_phone` `from_phone` VARCHAR(20) NULL COMMENT '转出单位联系电话' COLLATE 'utf8_general_ci' AFTER `to_branch_id`,
	CHANGE COLUMN `pay_time` `pay_time` DATE NULL COMMENT '党费缴纳至年月' AFTER `from_fax`,
	CHANGE COLUMN `valid_days` `valid_days` INT(10) UNSIGNED NULL COMMENT '介绍信有效期天数' AFTER `pay_time`,
	CHANGE COLUMN `from_handle_time` `from_handle_time` DATE NULL COMMENT '转出办理时间' AFTER `valid_days`,
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL COMMENT '状态，-2本人撤回 -1返回修改 0申请 1转出分党委审批 2转入分党委审批' AFTER `from_handle_time`;

-- 2021.4.10 ly
-- 更新分党委、党支部的简称
UPDATE ow_party p,sys_config s SET p.short_name=SUBSTRING_INDEX(p.name,s.school_name,-1) WHERE (p.short_name IS NULL OR p.short_name='') AND s.id=4;
UPDATE ow_branch p,sys_config s SET p.short_name=SUBSTRING_INDEX(p.name,s.school_name,-1) WHERE (p.short_name IS NULL OR p.short_name='') AND s.id=4;
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
    VALUES ('ow_show_full_name', '党组织显示全称', 'true', 3, 99, '页面显示的党组织和党组织的select标签中，是：分党委和党支部显示全称；否：分党委和党支部显示全称；');

-- 2021.4.9 ly
ALTER TABLE `ow_member_history`
	CHANGE COLUMN `phone` `mobile` VARCHAR(100) NULL DEFAULT NULL COMMENT '手机' COLLATE 'utf8_general_ci' AFTER `pro_post`;

-- 2021.3.27 ly
DROP TABLE IF EXISTS `ow_member_history`;
CREATE TABLE IF NOT EXISTS `ow_member_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `code` varchar(20) NOT NULL COMMENT '学工号',
  `realname` varchar(20) NOT NULL COMMENT '姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `lable` varchar(255) DEFAULT NULL COMMENT '标签 元数据可多选 记录转移至历史库的原因',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生 4博士研究生 5离退休教职工',
  `gender` tinyint(1) unsigned DEFAULT NULL COMMENT '性别 1男 2女',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `birth` date DEFAULT NULL COMMENT '出生时间',
  `party_name` varchar(200) DEFAULT NULL COMMENT '二级党组织名称',
  `branch_name` varchar(200) DEFAULT NULL COMMENT '党支部名称',
  `political_status` tinyint(1) NOT NULL COMMENT '党籍状态 1 预备党员、2 正式党员',
  `transfer_time` date DEFAULT NULL COMMENT '组织关系转入时间',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `sponsor` varchar(20) DEFAULT NULL COMMENT '入党介绍人',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `pro_post` varchar(50) DEFAULT NULL COMMENT '专业技术职务',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `detail_reason` varchar(200) DEFAULT NULL COMMENT '转至历史党员库详细原因',
  `out_reason` varchar(200) DEFAULT NULL COMMENT '移除原因',
  `add_user_id` int(10) unsigned NOT NULL COMMENT '添加人',
  `add_date` date NOT NULL COMMENT '添加时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0正常 1已移除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='历史党员库';
INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `is_deleted`) VALUES (2605, '标签', '历史党员库', '', 'mc_mh_lable', '', '', '', 2619, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '党员出国境', 'mt_udgxuo', NULL, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '失联党员', 'mt_n8hlza', NULL, NULL, '', 1, 1);

-- 2021.3.5 ly
-- 更新ow_member_apply_view

-- 2021.1.22 ly
ALTER TABLE `ow_apply_approval_log`
	DROP INDEX `FK_ow_apply_approval_log_sys_user`,
	DROP INDEX `FK_ow_apply_approval_log_sys_user_2`,
	DROP FOREIGN KEY `FK_ow_apply_approval_log_sys_user`;
ALTER TABLE `ow_branch_member`
	DROP FOREIGN KEY `FK_ow_branch_member_sys_user`;
ALTER TABLE `ow_branch_member`
	ADD CONSTRAINT `FK_ow_branch_member_sys_user` FOREIGN KEY (`user_id`) REFERENCES `db_owip_dlut`.`sys_user` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE;
-- 更新ow_member_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2572, 0, '历史党员管理', '', 'url', 'fa fa-star-o', '/member/memberHistory', 105, '0/1/105/', 0, 'memberHistory:list', 2, NULL, NULL, 1, 7537);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2633, 0, '删除', '', 'function', '', NULL, 2572, '0/1/105/2572/', 1, 'memberHistory:', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2573, 0, '编辑', '', 'function', '', NULL, 2572, '0/1/105/2572/', 1, 'memberHistory:edit', 2, NULL, NULL, 1, NULL);

DROP TABLE IF EXISTS `ow_member_history`;
CREATE TABLE IF NOT EXISTS `ow_member_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '学工号',
  `realname` varchar(20) NOT NULL COMMENT '姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `type` tinyint(1) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `gender` tinyint(1) unsigned DEFAULT NULL COMMENT '性别 1男 2女',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `birth` date DEFAULT NULL COMMENT '出生时间',
  `party_name` varchar(200) DEFAULT NULL COMMENT '二级党组织名称',
  `branch_name` varchar(200) DEFAULT NULL COMMENT '党支部名称',
  `political_status` tinyint(1) NOT NULL COMMENT '党籍状态 1 预备党员、2 正式党员',
  `transfer_time` date DEFAULT NULL COMMENT '组织关系转入时间',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `sponsor` varchar(20) DEFAULT NULL COMMENT '入党介绍人',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `pro_post` varchar(50) DEFAULT NULL COMMENT '专业技术职务',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `remark1` varchar(255) DEFAULT NULL COMMENT '备注1',
  `remark2` varchar(255) DEFAULT NULL COMMENT '备注2',
  `remark3` varchar(255) DEFAULT NULL COMMENT '备注3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='历史党员库';

-- 2021.1.15 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2567, 0, '基层党组织信息统计', '', 'url', '', '/party/partyInfoStatistics', 260, '0/1/260/', 1, 'stat:infoStatistics', NULL, NULL, NULL, 1, 75);
ALTER TABLE `ow_party`
	ADD COLUMN `fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上级党组织' AFTER `id`;
-- 更新view_ly.sql中的ow_party_static_view、更新ow_party_view

-- 2021.1.8 ly
ALTER TABLE `ow_party`
	ADD COLUMN `direct_type` INT(10) UNSIGNED NULL COMMENT '直属党支部所属支部类型 关联元数据' AFTER `unit_type_id`;
-- 更新ow_party_view、pcs_party_view

-- 2021.1.5 ly
ALTER TABLE `ow_member_out`
	ADD COLUMN `year` INT(10) UNSIGNED NULL COMMENT '年份 用于生成介绍信编号' AFTER `branch_id`,
	ADD COLUMN `sn` INT(10) UNSIGNED NULL COMMENT '编号 用于生成介绍信编号 依次递增' AFTER `year`,
	ADD UNIQUE INDEX `year_number` (`year`, `sn`);
-- 更新ow_member_out_view

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

