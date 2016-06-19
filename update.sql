
--2016-6-19
ALTER TABLE `abroad_passport_draw`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类型，1因私 2因公赴台 3其他事务 4 长期因公出国' AFTER `cadre_id`;


--2016-6-17
ALTER TABLE `sys_config`
	ADD COLUMN `apply_self_approval_note` LONGTEXT NULL COMMENT '因私出国审批说明' AFTER `apply_self_note`;
ALTER TABLE `sys_config`
	CHANGE COLUMN `id` `id` INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST;
--2016-6-15
ALTER TABLE `ow_member_out`
	ADD COLUMN `is_print` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打印' AFTER `apply_time`,
	ADD COLUMN `print_time` DATETIME NULL DEFAULT NULL COMMENT '打印时间' AFTER `is_print`,
	ADD COLUMN `print_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '打印人' AFTER `print_time`;

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `is_print` `print_count` INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '打印次数' AFTER `apply_time`,
	CHANGE COLUMN `print_time` `last_print_time` DATETIME NULL DEFAULT NULL COMMENT '最后一次打印时间' AFTER `print_count`,
	CHANGE COLUMN `print_user_id` `last_print_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '最后一次打印人' AFTER `last_print_time`;


--2016-6-14
CREATE ALGORITHM = UNDEFINED VIEW `base_cadre_view` AS select bc.*, bci.mobile, bci.office_phone, bci.home_phone, bci.email from base_cadre bc left join base_cadre_info bci on bci.cadre_id = bc.id ;

ALTER TABLE `abroad_apply_self`
	ADD COLUMN `is_agreed` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否最终同意申请，供查询使用' AFTER `flow_users`;
update abroad_apply_self aas, (select apply_id from abroad_approval_log where od_type=1 and status=1) aal set aas.is_agreed=1 where aal.apply_id=aas.id;

ALTER TABLE `ow_member_modify`
	ADD COLUMN `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
	ADD PRIMARY KEY (`id`);



--2016-6-13
ALTER TABLE `base_dispatch`
	ADD COLUMN `appoint_count` INT UNSIGNED NULL COMMENT '任命人数' AFTER `work_time`,
	ADD COLUMN `dismiss_count` INT UNSIGNED NULL COMMENT '免职人数' AFTER `appoint_count`;

ALTER TABLE `base_dispatch`
	CHANGE COLUMN `code` `code` INT(10) UNSIGNED NOT NULL COMMENT '发文号，自动生成，比如师党干[2015]年01号' AFTER `dispatch_type_id`,
	ADD COLUMN `real_appoint_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '录入任命人数，后台统计得到' AFTER `appoint_count`,
	ADD COLUMN `real_dismiss_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '录入免职人数，后台统计得到' AFTER `dismiss_count`;


ALTER TABLE `ow_member_out`
	ADD COLUMN `phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '联系电话' AFTER `branch_id`;

	ALTER TABLE `ow_member_out_modify`
	ADD COLUMN `phone` VARCHAR(100) NOT NULL COMMENT '联系电话' AFTER `apply_user_id`;



ALTER TABLE `sys_login_log`
	CHANGE COLUMN `agent` `agent` TEXT NULL DEFAULT NULL COMMENT '客户端类型' AFTER `last_login_ip`;





--2016-6-13
ALTER TABLE `base_dispatch_cadre`
	CHANGE COLUMN `way_id` `way_id` INT(10) UNSIGNED NULL COMMENT '任免方式，关联元数据（1 提任 2连任 3平级调动 4免职）' AFTER `cadre_type_id`,
	CHANGE COLUMN `procedure_id` `procedure_id` INT(10) UNSIGNED NULL COMMENT '任免程序，关联元数据（1 民主推荐 2公开招聘 3引进人才 4其他 5免职）' AFTER `way_id`;
ALTER TABLE `base_cadre`
	CHANGE COLUMN `type_id` `type_id` INT(10) UNSIGNED NULL COMMENT '行政级别，关联元数据' AFTER `user_id`,
	CHANGE COLUMN `post_id` `post_id` INT(10) UNSIGNED NULL COMMENT '职务属性，关联元数据' AFTER `type_id`;

--2016-6-12
ALTER TABLE `abroad_passport_apply`
	ADD COLUMN `handle_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '证件接收人' AFTER `handle_date`,
	ADD CONSTRAINT `FK_abroad_passport_apply_sys_user_2` FOREIGN KEY (`handle_user_id`) REFERENCES `sys_user` (`id`);


CREATE ALGORITHM = UNDEFINED VIEW `abroad_passport_apply_view` AS select apa.`*` , ap.id as passport_id, ap.code from abroad_passport_apply apa  left join abroad_passport ap on ap.apply_id=apa.id ;

--2016-6-12
ALTER TABLE `abroad_passport_draw`
	ADD COLUMN `use_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '领取证件用途，1 仅签证 2 已签证，本次出境 3 同时签证和出境' AFTER `remark`;
ALTER TABLE `abroad_passport_draw`
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '申请备注' AFTER `need_sign`,
	CHANGE COLUMN `approve_remark` `approve_remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '审批备注' AFTER `user_id`;


--2016-6-11
ALTER TABLE `sys_online_static`
	CHANGE COLUMN `count` `online_count` INT(10) UNSIGNED NULL DEFAULT '0' AFTER `id`;

--2016-6-10
ALTER TABLE `sys_login_log`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '登录类型：1 网站 2 CAS 3 手机' AFTER `agent`,
	ADD COLUMN `success` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否成功' AFTER `type`,
	ADD COLUMN `remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注' AFTER `success`;

	ALTER TABLE `sys_login_log`
	COMMENT='登录日志',
	CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NULL COMMENT '账号ID, 登录成功才有值' AFTER `id`,
	ADD COLUMN `username` VARCHAR(100) NULL COMMENT '账号' AFTER `user_id`,
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '登录类型：1 网站 2 网站下次自动登录 3CAS 4移动设备' AFTER `agent`;

	ALTER TABLE `sys_login_log`
	ADD INDEX `username` (`username`);

	删除mt_log_login

CREATE TABLE `sys_online_static` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`count` INT(10) UNSIGNED NULL DEFAULT '0',
	`bks` INT(10) UNSIGNED NULL DEFAULT '0',
	`yjs` INT(10) UNSIGNED NULL DEFAULT '0',
	`jzg` INT(10) UNSIGNED NULL DEFAULT '0',
	`create_time` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COMMENT='在线人数统计, 隔断时间统计一次'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7
;


--2016-6-10
ALTER TABLE `sys_role`
	ADD COLUMN `is_sys_hold` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否系统自动维护, 如果是，则不可以手动给某个账号指定该角色' AFTER `available`,
	ADD COLUMN `remark` VARCHAR(255) NULL COMMENT '说明' AFTER `is_sys_hold`;

ALTER TABLE `sys_role`
	ADD COLUMN `sort_order` INT UNSIGNED NULL DEFAULT '0' COMMENT '排序' AFTER `is_sys_hold`;
	ALTER TABLE `sys_role`
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NULL COMMENT '排序' AFTER `is_sys_hold`;

	ALTER TABLE `sys_role`
	CHANGE COLUMN `available` `available` TINYINT(3) NULL DEFAULT '0' COMMENT '状态，0禁用 1启用，当前未用' AFTER `resource_ids`;
	ALTER TABLE `sys_role`
	CHANGE COLUMN `role` `role` VARCHAR(100) NOT NULL COMMENT '角色代码' AFTER `id`,
	CHANGE COLUMN `description` `description` VARCHAR(100) NULL DEFAULT NULL COMMENT '角色名称' AFTER `role`;

--2016-6-9
ALTER TABLE `sys_log`
	CHANGE COLUMN `api` `api` VARCHAR(255) NULL DEFAULT NULL COMMENT 'api地址' AFTER `type_id`;

--2016-6-8
党总支 mt_party_general_branch

--2016-6-2
ALTER TABLE `ow_graduate_abroad`
	ADD COLUMN `to_branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '转移至支部' AFTER `branch_id`,
	ADD COLUMN `user_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '人员类别，关联元数据' AFTER `to_branch_id`,
	ADD COLUMN `abroad_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '出国原因' AFTER `user_type`,
	ADD CONSTRAINT `FK_ow_graduate_abroad_ow_branch_2` FOREIGN KEY (`to_branch_id`) REFERENCES `ow_branch` (`id`),
	ADD CONSTRAINT `FK_ow_graduate_abroad_base_meta_type` FOREIGN KEY (`user_type`) REFERENCES `base_meta_type` (`id`);

添加mc_abroad_user_type

--2016-5-31
ALTER TABLE `sys_login_log`
	CHANGE COLUMN `agent` `agent` VARCHAR(255) NULL DEFAULT NULL COMMENT '客户端类型' AFTER `last_login_ip`;

ALTER TABLE `sys_user_sync`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1人事库 2研究库 3本科生库 4教职工党员出国信息库' AFTER `id`;

ALTER TABLE `sys_login_log`
	CHANGE COLUMN `login_ip` `login_ip` VARCHAR(100) NULL DEFAULT NULL COMMENT '登陆IP' AFTER `login_time`,
	CHANGE COLUMN `last_login_ip` `last_login_ip` VARCHAR(100) NULL DEFAULT NULL COMMENT '上次登陆IP' AFTER `last_login_time`;

CREATE ALGORITHM = UNDEFINED VIEW `ow_member_abroad_view` AS select ea.`*`, m.user_id, u.realname, u.code, u.gender, m.party_id, m.branch_id from ext_abroad ea , sys_user u, ow_member m where ea.gzzh=u.code and u.id=m.user_id ;


--20160525
ALTER TABLE `ow_branch`
	CHANGE COLUMN `short_name` `short_name` VARCHAR(50) NULL COMMENT '简称' AFTER `name`;

update ow_branch b , ow_party p set b.code=concat(p.code, lpad(b.code, 3, '0')) where b.party_id=p.id

--20160522
ALTER TABLE `ow_member_out`
	ADD COLUMN `is_modify` TINYINT(1) UNSIGNED NULL COMMENT '是否修改，审批完成后是否修改过' AFTER `status`;

update ow_member_out set is_modify = 0 where is_modify is null;
ALTER TABLE `ow_member_out`
	CHANGE COLUMN `is_modify` `is_modify` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否修改，审批完成后是否修改过' AFTER `status`;
--20160520
ALTER TABLE `ow_apply_approval_log`
	ADD COLUMN  `user_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '操作人类别, 0本人 1党支部 2分党委 3组织部 4系统管理员' AFTER `user_id`,
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '操作结果， 0不通过 1通过 2打回 3直接通过' AFTER `user_type`;

ALTER TABLE `ow_apply_approval_log`
	CHANGE COLUMN `stage` `stage` VARCHAR(50) NULL DEFAULT NULL COMMENT '阶段备注，比如 初审、党支部审核、分党委审核、终审、本人打回等' AFTER `type`,
	CHANGE COLUMN `user_type` `user_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '操作人类别, 0本人 1党支部 2分党委 3组织部 4系统管理员' AFTER `user_id`,
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '操作结果， 0审核不通过 1审核通过 2打回 3直接通过' AFTER `user_type`,
	CHANGE COLUMN `create_time` `create_time` DATETIME NULL DEFAULT NULL COMMENT '操作时间' AFTER `remark`;

	CREATE ALGORITHM = UNDEFINED VIEW `ow_graduate_abroad_view` AS SELECT oga.*,  om.`status` as member_status from ow_graduate_abroad oga, ow_member om where oga.user_id=om.user_id  ;

--20160519

ALTER TABLE `ow_member_in`
	CHANGE COLUMN `apply_time` `apply_time` DATE NULL COMMENT '提交书面申请书时间' AFTER `handle_time`,
	CHANGE COLUMN `active_time` `active_time` DATE NULL COMMENT '确定为入党积极分子时间' AFTER `apply_time`,
	CHANGE COLUMN `candidate_time` `candidate_time` DATE NULL COMMENT '确定为发展对象时间' AFTER `active_time`,
	CHANGE COLUMN `grow_time` `grow_time` DATE NULL COMMENT '入党时间' AFTER `candidate_time`,
	CHANGE COLUMN `positive_time` `positive_time` DATE NULL COMMENT '转正时间' AFTER `grow_time`;

--20160518
ALTER TABLE `ow_member_outflow`
	ADD COLUMN `remark` VARCHAR(255) NOT NULL COMMENT '打回原因' AFTER `is_back`;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_outflow_view` AS SELECT omo.*, om.`status` as member_status from ow_member_outflow omo, ow_member om where omo.user_id=om.user_id  ;

--20160510
update ow_teacher set is_retire=0 where is_retire is null;
ALTER TABLE `ow_teacher`
	CHANGE COLUMN `is_retire` `is_retire` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否退休，参与分类，不可为空' AFTER `phone`;
	ALTER TABLE `ow_teacher`
	CHANGE COLUMN `update_time` `update_time` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `create_time`;

--20160510
CREATE ALGORITHM = UNDEFINED VIEW `ow_branch_member_group_view` AS SELECT bmg.`*`, b.party_id from ow_branch_member_group bmg, ow_branch b where bmg.branch_id=b.id ;


--20160428
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_outflow_view` AS SELECT omo.*,  om.`status` as member_status from ow_member_outflow omo, ow_member om where omo.user_id=om.user_id  ;

ALTER TABLE `ow_member_inflow`
	CHANGE COLUMN `outflow_unit` `out_unit` VARCHAR(200) NULL DEFAULT NULL COMMENT '转出单位' AFTER `is_back`,
	CHANGE COLUMN `outflow_location` `out_location` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '转出地' AFTER `out_unit`,
	CHANGE COLUMN `outflow_time` `out_time` DATE NULL DEFAULT NULL COMMENT '转出时间' AFTER `out_location`,
	CHANGE COLUMN `outflow_status` `out_status` TINYINT(4) NULL COMMENT '转出状态，-1不通过 0申请 1党支部审核 2分党委审核' AFTER `out_time`,
	ADD COLUMN `out_is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '转出是否打回，当前状态是否是打回的' AFTER `out_status`;

ALTER TABLE `ow_member_inflow`
	ADD UNIQUE INDEX `user_id` (`user_id`);

	ALTER TABLE `ow_member_inflow`
	ADD COLUMN `flow_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '流入原因' AFTER `flow_time`,
	CHANGE COLUMN `reason` `reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '打回原因' AFTER `is_back`;
ALTER TABLE `ow_member_inflow`
	ADD COLUMN `out_reason` VARCHAR(255) NULL COMMENT '转出打回原因' AFTER `out_is_back`;

--20160425
ALTER TABLE `ow_member_quit`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1支部审核 2分党委审批 3组织部审批' AFTER `quit_time`;

ALTER TABLE `ow_member_inflow`
	CHANGE COLUMN `inflow_status` `inflow_status` TINYINT(4) NOT NULL COMMENT '流入状态，-1不通过 0申请 1党支部审核通过 2分党委审核通过' AFTER `or_location`;

	ALTER TABLE `ow_member_outflow`
	CHANGE COLUMN `status` `status` TINYINT(4) NOT NULL COMMENT '流出状态，-1不通过 0申请 1党支部审核通过 2分党委审核通过' AFTER `or_status`;
	ALTER TABLE `ow_member_return`
	CHANGE COLUMN `status` `status` TINYINT(4) NOT NULL COMMENT '状态，-1不通过（管理员或本人撤销） 0申请 1支部审核 2分党委审核' AFTER `political_status`;

	ALTER TABLE `ow_member_quit` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

ALTER TABLE `ow_member_in` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

ALTER TABLE `ow_member_inflow` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `inflow_status`;

	ALTER TABLE `ow_member_out` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

	ALTER TABLE `ow_member_outflow` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

		ALTER TABLE `ow_member_return` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

		ALTER TABLE `ow_member_stay` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;

		ALTER TABLE `ow_member_transfer` ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`;
--20160421
ALTER TABLE `ow_member_return`
	ADD COLUMN `return_apply_time` DATE NULL DEFAULT NULL COMMENT '提交恢复组织生活申请时间' AFTER `apply_time`;

--20160420
--select count(distinct om.user_id) from ow_member om, ow_party op, ow_branch ob, base_meta_type bmt where bmt.code='mt_direct_branch' and op.class_id=bmt.id and ob.party_id=op.id and om.branch_id=ob.id;

update ow_member om, ow_party op, ow_branch ob, base_meta_type bmt set om.branch_id=null where bmt.code='mt_direct_branch' and op.class_id=bmt.id and ob.party_id=op.id and om.branch_id=ob.id;
update ow_member_stay oms, ow_party op, ow_branch ob, base_meta_type bmt set oms.branch_id=null where bmt.code='mt_direct_branch' and op.class_id=bmt.id and ob.party_id=op.id and oms.branch_id=ob.id;

delete ob.* from ow_party op, ow_branch ob, base_meta_type bmt where bmt.code='mt_direct_branch' and op.class_id=bmt.id and ob.party_id=op.id;


update ow_member set transfer_time=null where transfer_time='1900-01-01';

update ow_member set apply_time=null where apply_time='1900-01-01';

update ow_member set active_time=null where active_time='1900-01-01';

update ow_member set candidate_time=null where candidate_time='1900-01-01';

update ow_member set grow_time=null where grow_time='1900-01-01';

update ow_member set positive_time=null where positive_time='1900-01-01';


ALTER TABLE `ow_apply_approval_log`
	DROP FOREIGN KEY `FK_ow_apply_approval_log_sys_user_2`;

--20160416
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES ('基本信息', '', 'url', '', '/user/member', 258, '0/1/258/', 'userMember:base', 1, NULL);
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES ('党员流出', '', 'url', '', '/user/memberOutflow', 258, '0/1/258/', 'userMember:outflow', 1, NULL);
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES ('组织关系转出', '', 'url', '', '/user/memberOut', 258, '0/1/258/', 'userMember:out', 1, NULL);
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES ('校内组织关系转接', '', 'url', '', '/user/memberTransfer', 258, '0/1/258/', 'userMember:transfer', 1, NULL);
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES ('公派留学生申请组织关系暂留', '', 'url', '', '/user/memberStay', 258, '0/1/258/', 'userMember:stay', 1, NULL);

--20160413
ALTER TABLE `ow_apply_approval_log`
	ADD CONSTRAINT `FK_ow_apply_approval_log_sys_user_2` FOREIGN KEY (`apply_user_id`) REFERENCES `sys_user` (`id`);
ALTER TABLE `ow_member_apply`
	ADD COLUMN `reason` VARCHAR(255) NULL COMMENT '不通过原因' AFTER `stage`;

--20160411
ALTER TABLE `ow_member`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '1正常，2已退休 3已出党 4已转出 5暂时转出（外出挂职、休学等）' AFTER `type`;

	添加 日志类型 ： mt_log_user 用户操作
--20160410
ALTER TABLE `ow_apply_approval_log`
	ADD COLUMN `remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '说明' AFTER `status`;
-----------------

--20160328
ALTER TABLE `abroad_passport`
	ADD COLUMN `has_find` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否找回，丢失后找回' AFTER `lost_proof`,
	ADD COLUMN `find_time` DATETIME NULL COMMENT '找回时间' AFTER `has_find`;

--20160327 22
ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_student` AS SELECT `m`.`create_time` AS `create_time`,`m`.`apply_time` AS `apply_time`,`m`.`source` AS `source`,`m`.`positive_time` AS `positive_time`,`m`.`active_time` AS `active_time`,`m`.`political_status` AS `political_status`,`m`.`transfer_time` AS `transfer_time`,`m`.`user_id` AS `user_id`,`m`.`branch_id` AS `branch_id`,`m`.`candidate_time` AS `candidate_time`,`m`.`party_id` AS `party_id`,`m`.`grow_time` AS `grow_time`,`m`.`status` AS `status`,`m`.`party_post` AS `party_post`,`m`.`party_reward` AS `party_reward`,`m`.`other_reward` AS `other_reward`,`s`.`delay_year` AS `delay_year`,`s`.`period` AS `period`,`s`.`code` AS `code`,`s`.`edu_category` AS `edu_category`,`s`.`gender` AS `gender`,`s`.`birth` AS `birth`,`s`.`nation` AS `nation`,`s`.`actual_graduate_time` AS `actual_graduate_time`,`s`.`expect_graduate_time` AS `expect_graduate_time`,`s`.`actual_enrol_time` AS `actual_enrol_time`,`s`.`sync_source` AS `sync_source`,`s`.`type` AS `type`,`s`.`is_full_time` AS `is_full_time`,`s`.`realname` AS `realname`,`s`.`enrol_year` AS `enrol_year`,`s`.`native_place` AS `native_place`,`s`.`edu_way` AS `edu_way`,`s`.`idcard` AS `idcard`,`s`.`edu_level` AS `edu_level`,`s`.`grade` AS `grade`,`s`.`edu_type` AS `edu_type`, s.xj_status as xj_status, p.unit_id as unit_id
FROM (`ow_member` `m`,`ow_party` p
JOIN `ow_student` `s`)
where (`m`.`user_id` = `s`.`user_id` and m.party_id=p.id)  ;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_teacher` AS SELECT `m`.`create_time` AS `create_time`,`m`.`apply_time` AS `apply_time`,`m`.`source` AS `source`,`m`.`positive_time` AS `positive_time`,`m`.`active_time` AS `active_time`,`m`.`political_status` AS `political_status`,`m`.`transfer_time` AS `transfer_time`,`m`.`user_id` AS `user_id`,`m`.`branch_id` AS `branch_id`,`m`.`candidate_time` AS `candidate_time`,`m`.`party_id` AS `party_id`,`m`.`grow_time` AS `grow_time`,`m`.`status` AS `status`,`m`.`party_post` AS `party_post`,`m`.`party_reward` AS `party_reward`,`m`.`other_reward` AS `other_reward`,`t`.`code` AS `code`,`t`.`education` AS `education`,`t`.`gender` AS `gender`,`t`.`nation` AS `nation`,`t`.`school_type` AS `school_type`,`t`.`title_level` AS `title_level`,`t`.`staff_status` AS `staff_status`,`t`.`retire_time` AS `retire_time`,`t`.`post_class` AS `post_class`,`t`.`pro_post` AS `pro_post`,`t`.`major` AS `major`,`t`.`post` AS `post`,`t`.`school` AS `school`,`t`.`is_retire` AS `is_retire`,`t`.`is_honor_retire` AS `is_honor_retire`,`t`.`post_type` AS `post_type`,`t`.`degree_time` AS `degree_time`,`t`.`manage_level` AS `manage_level`,`t`.`email` AS `email`,`t`.`post_level` AS `post_level`,`t`.`office_level` AS `office_level`,`t`.`talent_title` AS `talent_title`,`t`.`address` AS `address`,`t`.`degree` AS `degree`,`t`.`mobile` AS `mobile`,`t`.`birth` AS `birth`,`t`.`authorized_type` AS `authorized_type`,`t`.`realname` AS `realname`,`t`.`arrive_time` AS `arrive_time`,`t`.`native_place` AS `native_place`,`t`.`marital_status` AS `marital_status`,`t`.`staff_type` AS `staff_type`,`t`.`phone` AS `phone`,`t`.`idcard` AS `idcard`,`t`.`on_job` AS `on_job`,`t`.`pro_post_level` AS `pro_post_level`, p.unit_id as unit_id
FROM (`ow_member` `m`,`ow_party` p
JOIN `ow_teacher` `t`)
WHERE (`m`.`user_id` = `t`.`user_id` and m.party_id=p.id)  ;
--20160327
ALTER TABLE `ow_student`
	ADD COLUMN `xj_status` VARCHAR(200) NULL DEFAULT NULL COMMENT '学籍状态，本科生XJBD,研究生ZT' AFTER `actual_graduate_time`;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_student` AS SELECT `m`.`create_time` AS `create_time`,`m`.`apply_time` AS `apply_time`,`m`.`source` AS `source`,`m`.`positive_time` AS `positive_time`,`m`.`active_time` AS `active_time`,`m`.`political_status` AS `political_status`,`m`.`transfer_time` AS `transfer_time`,`m`.`user_id` AS `user_id`,`m`.`branch_id` AS `branch_id`,`m`.`candidate_time` AS `candidate_time`,`m`.`party_id` AS `party_id`,`m`.`grow_time` AS `grow_time`,`m`.`status` AS `status`,`m`.`party_post` AS `party_post`,`m`.`party_reward` AS `party_reward`,`m`.`other_reward` AS `other_reward`,`s`.`delay_year` AS `delay_year`,`s`.`period` AS `period`,`s`.`code` AS `code`,`s`.`edu_category` AS `edu_category`,`s`.`gender` AS `gender`,`s`.`birth` AS `birth`,`s`.`nation` AS `nation`,`s`.`actual_graduate_time` AS `actual_graduate_time`,`s`.`expect_graduate_time` AS `expect_graduate_time`,`s`.`actual_enrol_time` AS `actual_enrol_time`,`s`.`sync_source` AS `sync_source`,`s`.`type` AS `type`,`s`.`is_full_time` AS `is_full_time`,`s`.`realname` AS `realname`,`s`.`enrol_year` AS `enrol_year`,`s`.`native_place` AS `native_place`,`s`.`edu_way` AS `edu_way`,`s`.`idcard` AS `idcard`,`s`.`edu_level` AS `edu_level`,`s`.`grade` AS `grade`,`s`.`edu_type` AS `edu_type`, s.xj_status as xj_status
FROM (`ow_member` `m`
JOIN `ow_student` `s`)
WHERE (`m`.`user_id` = `s`.`user_id`)  ;

-- 2016-3-23
ALTER TABLE `abroad_passport_apply`
	ADD COLUMN `abolish` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否作废，【审批通过且没作废 或 没开始审批 ，则不可以申请同类型证件】' AFTER `status`;

--2016-3-21
update abroad_passport set is_lent=0;

ALTER TABLE `abroad_passport`
	CHANGE COLUMN `is_lent` `is_lent` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否借出，（干部领取）' AFTER `safe_box_id`;

--2016-3-20
ALTER TABLE `sys_resource`
	ADD COLUMN `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `name`;

	ALTER TABLE `abroad_passport`
	ADD COLUMN `lost_type` TINYINT(3) UNSIGNED  NOT NULL DEFAULT '0'  COMMENT '丢失类型，1集中证件库中丢失 2 后台添加' AFTER `cancel_time`;
update abroad_passport set lost_type=0;


资源：申办证件-》修改为因私出国境证件，更改路径
ALTER TABLE `abroad_passport`
	CHANGE COLUMN `lost_proof` `lost_proof` VARCHAR(200) NULL DEFAULT '' COMMENT '丢失证明，拍照上传' AFTER `lost_time`;
-- 2016-3-19
ALTER TABLE `abroad_passport`
	CHANGE COLUMN `cancel_type` `cancel_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '取消集中保管原因，1证件过期 2不再担任行政职务3证件作废' AFTER `type`;

	update abroad_passport set type=2 ,cancel_type=3, abolish=0 where abolish=1;
ALTER TABLE `abroad_passport` DROP COLUMN `abolish`;

ALTER TABLE `abroad_passport`
	ADD COLUMN `lost_time` DATETIME NULL DEFAULT NULL COMMENT '丢失日期' AFTER `cancel_time`;

--2016-3-13
ALTER TABLE `base_cadre_edu`
	ADD COLUMN `sort_order` INT UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

	update base_cadre_edu set sort_order=id;

update abroad_passport set cancel_confirm=0 where cancel_confirm is null;
ALTER TABLE `abroad_passport`
	CHANGE COLUMN `cancel_confirm` `cancel_confirm` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否已确认' AFTER `cancel_type`;

--2016.3.11
ALTER TABLE `abroad_apply_self`
	ADD COLUMN `node` INT NOT NULL COMMENT '下一个审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）' AFTER `status`,
	ADD COLUMN `flow` VARCHAR(50) NOT NULL COMMENT '已审批身份类型,（按顺序排序，逗号分隔）' AFTER `node`;

ALTER TABLE `abroad_apply_self`
	CHANGE COLUMN `node` `flow_node` INT(11) NOT NULL COMMENT '下一个审批身份类型,（-1：组织部初审，0：组织部终审，其他：其他身份审批）' AFTER `status`,
	CHANGE COLUMN `flow` `flow_nodes` VARCHAR(100) NULL COMMENT '已审批身份类型,（按顺序排序，逗号分隔）' AFTER `flow_node`,
	ADD COLUMN `flow_users` VARCHAR(100) NULL COMMENT '已审批的审批人USER_ID，（按顺序排序，逗号分隔）' AFTER `flow_nodes`;

ALTER TABLE `abroad_apply_self`
	ADD COLUMN `is_finish` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否完成终审' AFTER `status`;


CREATE TABLE `abroad_safe_box` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`code` VARCHAR(50) NOT NULL COMMENT '保险柜编号',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`)
)
COMMENT='保险柜'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=11
;


ALTER TABLE `abroad_passport`
	CHANGE COLUMN `safe_code` `safe_box_id` VARCHAR(50) NULL DEFAULT '' COMMENT '存放保险柜' AFTER `keep_date`;

insert into abroad_safe_box(code) select  distinct safe_box_id from abroad_passport where safe_box_id is not null and safe_box_id!='';
update abroad_passport ap , abroad_safe_box asb set ap.safe_box_id=asb.id where ap.safe_box_id=asb.code;

ALTER TABLE `abroad_passport`
	CHANGE COLUMN `safe_box_id` `safe_box_id` INT UNSIGNED NULL COMMENT '存放保险柜' AFTER `keep_date`,
	ADD CONSTRAINT `FK_abroad_passport_abroad_safe_box` FOREIGN KEY (`safe_box_id`) REFERENCES `abroad_safe_box` (`id`);

update abroad_safe_box set sort_order =id


ALTER TABLE `base_cadre`
	CHANGE COLUMN `title` `title` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位及职务' AFTER `unit_id`,
	ADD COLUMN `post` VARCHAR(50) NULL DEFAULT NULL COMMENT '职务' AFTER `title`;

update base_cadre set post=title;


--2016.3.10
ALTER TABLE `abroad_approval_log`
	CHANGE COLUMN `cadre_id` `user_id` INT(10) UNSIGNED NOT NULL COMMENT '审批人' AFTER `apply_id`,
	DROP FOREIGN KEY `FK_abroad_apply_approval_base_cadre`;
	ALTER TABLE `abroad_approval_log`
	DROP INDEX `FK_abroad_apply_approval_base_cadre`;

	ALTER TABLE `abroad_approval_log`
	ADD CONSTRAINT `FK_abroad_approval_log_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`);


--2016.3.10
-- 删除元数据 学校类型
ALTER TABLE `base_cadre_edu`
	ALTER `school_type` DROP DEFAULT;
ALTER TABLE `base_cadre_edu`
	CHANGE COLUMN `school_type` `school_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '学校类型， 1本校 2境内 3境外' AFTER `major`,
	ADD COLUMN `has_degree` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否获得学位' AFTER `learn_style`,
	DROP COLUMN `tutor_name`,
	DROP COLUMN `tutor_unit`,
	DROP INDEX `FK_base_cadre_edu_base_meta_type_2`,
	DROP FOREIGN KEY `FK_base_cadre_edu_base_meta_type_2`;

CREATE TABLE `base_cadre_tutor` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属干部',
	`name` VARCHAR(50) NOT NULL COMMENT '导师姓名',
	`title` VARCHAR(100) NOT NULL COMMENT '所在单位及职务（职称）',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型， 1硕士生导师 2博士生导师',
	PRIMARY KEY (`id`),
	INDEX `FK_base_cadre_tutor_base_cadre` (`cadre_id`),
	CONSTRAINT `FK_base_cadre_tutor_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
)
COMMENT='导师信息'
ENGINE=InnoDB
;




--2016.3.10 已更新
ALTER TABLE `abroad_apply_self`
	ADD COLUMN `status` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否提交，组织部初审可以打回' AFTER `ip`;

--2016.3.10
ALTER TABLE `ow_member`
	ADD CONSTRAINT `FK_ow_member_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_apply_log`
	ADD CONSTRAINT `FK_ow_apply_log_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
	ADD CONSTRAINT `FK_ow_apply_log_sys_user_2` FOREIGN KEY (`operator_id`) REFERENCES `sys_user` (`id`);

	ALTER TABLE `ow_branch`
	ADD CONSTRAINT `FK_ow_branch_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
	ADD CONSTRAINT `FK_ow_branch_base_meta_type_2` FOREIGN KEY (`unit_type_id`) REFERENCES `base_meta_type` (`id`);

	ALTER TABLE `ow_branch_member_group`
	ADD CONSTRAINT `FK_ow_branch_member_group_base_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`);

	ALTER TABLE `ow_member_abroad`
	ADD CONSTRAINT `FK_ow_member_abroad_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_abroad_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_in`
	ADD CONSTRAINT `FK_ow_member_in_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_in_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_inflow`
	ADD CONSTRAINT `FK_ow_member_inflow_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_inflow_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_out`
	ADD CONSTRAINT `FK_ow_member_out_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_out_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_outflow`
	ADD CONSTRAINT `FK_ow_member_outflow_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_outflow_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_quit`
	ADD CONSTRAINT `FK_ow_member_quit_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_quit_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_stay`
	ADD CONSTRAINT `FK_ow_member_stay_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_stay_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`);

	ALTER TABLE `ow_member_transfer`
	ADD CONSTRAINT `FK_ow_member_transfer_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_transfer_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
	ADD CONSTRAINT `FK_ow_member_transfer_ow_party_2` FOREIGN KEY (`to_party_id`) REFERENCES `ow_party` (`id`),
	ADD CONSTRAINT `FK_ow_member_transfer_ow_branch_2` FOREIGN KEY (`to_branch_id`) REFERENCES `ow_branch` (`id`);

--2016.2.28
ALTER TABLE `ow_branch`
	CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL COMMENT '名称，需用全称：院系+支部名称' AFTER `code`,
	CHANGE COLUMN `short_name` `short_name` VARCHAR(50) NOT NULL COMMENT '简称' AFTER `name`;

--2016.2.27
ALTER TABLE `base_dispatch`
	CHANGE COLUMN `code` `code` INT UNSIGNED NOT NULL COMMENT '发文号，自动生成，比如师党干[2015]年01号' AFTER `dispatch_type_id`;
ALTER TABLE `base_dispatch`
	DROP INDEX `code`,
	ADD UNIQUE INDEX `year_dispatch_type_id_code` (`year`, `dispatch_type_id`, `code`);
--2016.2.26
ALTER TABLE `base_cadre`
	ADD COLUMN `unit_id` INT(10) UNSIGNED NULL COMMENT '单位' AFTER `post_id`,
	CHANGE COLUMN `title` `title` VARCHAR(50) NULL DEFAULT NULL COMMENT '职务' AFTER `unit_id`,
	ADD CONSTRAINT `FK_base_cadre_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`);

-- 2016-2-24
ALTER TABLE `sys_user`
	ADD COLUMN `sign` VARCHAR(100) NULL DEFAULT NULL COMMENT '手写签名，图片上传' AFTER `idcard`;

	UPDATE `owip`.`ow_branch` SET `staff_type_id`=0 WHERE  `id`=0;
	ALTER TABLE `ow_branch`
	CHANGE COLUMN `staff_type_id` `is_staff` TINYINT(1) UNSIGNED NULL COMMENT '是否是教工党支部' AFTER `type_id`,
	ADD COLUMN `is_prefessional` TINYINT(1) UNSIGNED NULL COMMENT '是否是专业教师党支部，教工党支部时才选择' AFTER `is_staff`,
	ADD COLUMN `is_base_team` TINYINT(1) UNSIGNED NULL COMMENT '是否建立在团队，专业教师党支部时才选择' AFTER `is_prefessional`;

	--删除mc_branch_staff_type

--2016.01.25
ALTER TABLE `sys_user`
	ADD COLUMN `avatar` VARCHAR(200) NULL DEFAULT NULL COMMENT '系统头像' AFTER `realname`;
ALTER TABLE `base_location`
	ADD UNIQUE INDEX `code` (`code`);
--2016.01.19
ALTER TABLE `ow_member_out`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL AFTER `party_id`;

update ow_member_out o, ow_member m set o.party_id=m.party_id, o.branch_id=m.branch_id where o.user_id=m.user_id;

	ALTER TABLE `ow_member_quit`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL AFTER `party_id`;

update ow_member_quit o, ow_member m set o.party_id=m.party_id, o.branch_id=m.branch_id where o.user_id=m.user_id;

ALTER TABLE `ow_member_stay`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL AFTER `party_id`;

update ow_member_stay o, ow_member m set o.party_id=m.party_id, o.branch_id=m.branch_id where o.user_id=m.user_id;

	ALTER TABLE `ow_member_transfer`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL AFTER `party_id`;

update ow_member_transfer o, ow_member m set o.party_id=m.party_id, o.branch_id=m.branch_id where o.user_id=m.user_id;

ALTER TABLE `ow_member_abroad`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `party_id`;

update ow_member_abroad o, ow_member m set o.party_id=m.party_id, o.branch_id=m.branch_id where o.user_id=m.user_id;

--2016.01.10
ALTER TABLE `base_meta_class`
	ADD COLUMN `first_level` VARCHAR(100) NULL COMMENT '所属一级目录' AFTER `name`,
	ADD COLUMN `second_level` VARCHAR(100) NULL COMMENT '所属二级目录' AFTER `first_level`;

ALTER TABLE `base_dispatch_cadre`
	ADD COLUMN `cadre_type_id` INT UNSIGNED NOT NULL COMMENT '干部类型，关联元数据' AFTER `type`;

	ALTER TABLE `base_dispatch_cadre`
	DROP COLUMN `name`;

#####2016.1.4
ALTER TABLE `base_dispatch`
	DROP INDEX `FK_dispatch_base_meta_type`,
	DROP FOREIGN KEY `FK_dispatch_base_meta_type`;
ALTER TABLE `base_dispatch`
	ALTER `type_id` DROP DEFAULT;
ALTER TABLE `base_dispatch`
	CHANGE COLUMN `type_id` `dispatch_type_id` INT(10) UNSIGNED NOT NULL COMMENT '发文类型' AFTER `year`,
	ADD CONSTRAINT `FK_base_dispatch_base_dispatch_type` FOREIGN KEY (`dispatch_type_id`) REFERENCES `base_dispatch_type` (`id`);

ALTER TABLE `base_dispatch`
	CHANGE COLUMN `meeting_time` `meeting_time` DATE NULL COMMENT '党委常委会日期' AFTER `code`;

	ALTER TABLE `base_dispatch_cadre`
	DROP FOREIGN KEY `FK_dispatch_cadre_base_meta_type`;
	ALTER TABLE `base_dispatch_cadre`
	DROP INDEX `FK_dispatch_cadre_base_meta_type`;
	ALTER TABLE `base_dispatch_cadre`
	DROP COLUMN `type_id`;

#####12.11
ALTER TABLE `ow_member_abroad`
	DROP COLUMN `party_name`,
	DROP COLUMN `branch_name`,
	DROP COLUMN `grow_time`;

	ALTER TABLE `ow_member_in`
	DROP COLUMN `realname`,
	DROP COLUMN `gender`,
	DROP COLUMN `age`,
	DROP COLUMN `nation`,
	DROP COLUMN `idcard`,
	DROP COLUMN `to_unit`;

	ALTER TABLE `ow_member_out`
	DROP COLUMN `code`,
	DROP COLUMN `realname`,
	DROP COLUMN `gender`,
	DROP COLUMN `age`,
	DROP COLUMN `nation`,
	DROP COLUMN `political_status`,
	DROP COLUMN `idcard`;

	ALTER TABLE `ow_member_stay`
	DROP COLUMN `code`,
	DROP COLUMN `realname`,
	DROP COLUMN `gender`,
	DROP COLUMN `age`,
	DROP COLUMN `nation`,
	DROP COLUMN `idcard`,
	DROP COLUMN `political_status`,
	DROP COLUMN `grow_time`;

	ALTER TABLE `ow_member_transfer`
	DROP COLUMN `code`,
	DROP COLUMN `realname`,
	DROP COLUMN `type`,
	DROP COLUMN `gender`,
	DROP COLUMN `age`,
	DROP COLUMN `nation`,
	DROP COLUMN `political_status`,
	DROP COLUMN `idcard`,
	DROP COLUMN `to_unit`,
	DROP COLUMN `from_unit`,
	DROP COLUMN `from_party_id`,
	DROP COLUMN `from_branch_id`;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_student` AS SELECT
m.create_time,
m.apply_time,
m.source,
m.positive_time,
m.active_time,

m.political_status,
m.transfer_time,
m.user_id,
m.branch_id,
m.candidate_time,
m.party_id,
m.grow_time,
m.status,
m.party_post,
m.party_reward,
m.other_reward,
s.delay_year,
s.period,
s.code,
s.edu_category,
s.gender,
s.birth,
s.nation,
s.actual_graduate_time,
s.expect_graduate_time,
s.actual_enrol_time,
s.sync_source ,
s.type,
s.is_full_time,
s.realname,
s.enrol_year,
s.native_place,
s.edu_way,

s.idcard,
s.edu_level,
s.grade,
s.edu_type

from ow_member m, ow_student s  where m.user_id = s.user_id  ;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_teacher` AS SELECT
m.create_time,
m.apply_time,
m.source,

m.positive_time,
m.active_time,

m.political_status,
m.transfer_time,
m.user_id,
m.branch_id,
m.candidate_time,
m.party_id,
m.grow_time,
m.status,
m.party_post,
m.party_reward,
m.other_reward,
t.code,
t.education,
t.gender,
t.nation,
t.school_type,
t.title_level,
t.staff_status,
t.retire_time,
t.post_class,
t.pro_post,
t.major,
t.post,
t.school,
t.is_retire,
t.is_honor_retire,
t.post_type,
t.degree_time,
t.manage_level,
t.email,
t.post_level,
t.office_level,
t.talent_title,
t.address,
t.degree,
t.mobile,
t.birth,
t.authorized_type,
t.realname,
t.arrive_time,
t.native_place,
t.marital_status,
t.staff_type,
t.phone,
t.idcard,
t.on_job,
t.pro_post_level
from ow_member m, ow_teacher t where m.user_id=t.user_id  ;


###12.9
ALTER TABLE `ow_student`
	CHANGE COLUMN `grade` `grade` VARCHAR(10) NULL DEFAULT NULL COMMENT '年级' AFTER `period`;

##12.6
## 访客->群众
ALTER TABLE `ow_member_apply`
	ADD COLUMN `pass_time` DATE NULL COMMENT '通过时间' AFTER `stage`;

ALTER TABLE `ow_member_return`
	ADD COLUMN `apply_time` DATE NULL DEFAULT NULL COMMENT '提交书面申请书时间' AFTER `branch_id`;

ALTER TABLE `ow_member_return`
	CHANGE COLUMN `status` `status` TINYINT(4) NULL DEFAULT NULL COMMENT '状态，-1不通过（管理员或本人撤销） 0申请 1支部审核 2分党委审核' AFTER `positive_time`;

ALTER TABLE `ow_member_return`
	ADD COLUMN `political_status` TINYINT UNSIGNED NOT NULL COMMENT '状态，1预备 2正式' AFTER `positive_time`;

ALTER TABLE `ow_member_in`
	ADD COLUMN `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间' AFTER `reason`;

ALTER TABLE `ow_member_inflow`
	ADD COLUMN `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间' AFTER `outflow_status`;

	ALTER TABLE `ow_member_outflow`
	ADD COLUMN `status` TINYINT(4) NULL DEFAULT NULL COMMENT '流出状态，-1不通过 0申请 1党支部审核 2分党委审核' AFTER `or_status`,
	ADD COLUMN `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间' AFTER `status`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `apply_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间' AFTER `reason`;

	ALTER TABLE `ow_member_transfer`
	ADD COLUMN `apply_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间' AFTER `reason`;

	ALTER TABLE `ow_member_transfer`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL COMMENT '状态，-2本人撤回 -1返回修改 0申请 1转出分党委审批 2转入分党委审批' AFTER `from_handle_time`;

ALTER TABLE `ow_member_stay`
	ADD COLUMN `apply_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间' AFTER `reason`;
###12.4
ALTER TABLE `ow_member_inflow`
	ADD COLUMN `inflow_status` TINYINT NULL DEFAULT NULL COMMENT '流入状态，-1不通过 0申请 1党支部审核 2分党委审核' AFTER `or_location`,
	ADD COLUMN `outflow_status` TINYINT NULL DEFAULT NULL COMMENT '流出状态，-1不通过 0申请 1党支部审核 2分党委审核' AFTER `outflow_time`;

CREATE TABLE `ow_enter_apply` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类别，1申请入党 2 留学归国申请 3转入申请  4 流入申请',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '用户',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态，0申请 1本人撤销 2 管理员撤回 3通过；当前0的状态每个用户只允许一个，且是最新的一条',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '撤回原因',
	`back_time` DATETIME NULL DEFAULT NULL COMMENT '撤回时间',
	`pass_time` DATETIME NULL DEFAULT NULL COMMENT '通过时间',
	PRIMARY KEY (`id`),
	INDEX `FK_ow_enter_apply_sys_user` (`user_id`),
	CONSTRAINT `FK_ow_enter_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
)
COMMENT='权限开通申请'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;



#####2015.12.3
#更改表 base_location


##2015.11.30

ALTER TABLE `ow_member`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1教工 2学生' AFTER `political_status`;

#职业 mc_job
##
ALTER TABLE `ow_member`
	ADD COLUMN `party_post` VARCHAR(100) NULL COMMENT '党内职务' AFTER `update_time`,
	ADD COLUMN `party_reward` VARCHAR(255) NULL COMMENT '党内奖励' AFTER `party_post`,
	ADD COLUMN `other_reward` VARCHAR(255) NULL COMMENT '其他奖励' AFTER `party_reward`;


ALTER DEFINER=`root`@`localhost` VIEW `ow_member_student` AS SELECT
m.create_time,
m.apply_time,
m.source,
m.positive_time,
m.active_time,

m.political_status,
m.transfer_time,
m.user_id,
m.branch_id,
m.candidate_time,
m.party_id,
m.grow_time,
m.status,
m.party_post,
m.party_reward,
m.other_reward,
s.delay_year,
s.period,
s.code,
s.edu_category,
s.gender,
s.birth,
s.nation,
s.actual_graduate_time,
s.expect_graduate_time,
s.actual_enrol_time,
s.sync_source ,
s.type,
s.is_full_time,
s.realname,
s.enrol_year,
s.native_place,
s.edu_way,

s.idcard,
s.edu_level,
s.grade,
s.edu_type

from ow_student s , ow_member m where s.user_id = m.user_id  ;

ALTER ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_teacher` AS SELECT
m.create_time,
m.apply_time,
m.source,

m.positive_time,
m.active_time,

m.political_status,
m.transfer_time,
m.user_id,
m.branch_id,
m.candidate_time,
m.party_id,
m.grow_time,
m.status,
m.party_post,
m.party_reward,
m.other_reward,
t.code,
t.education,
t.gender,
t.nation,
t.school_type,
t.title_level,
t.staff_status,
t.retire_time,
t.post_class,
t.pro_post,
t.major,
t.post,
t.school,
t.is_retire,
t.is_honor_retire,
t.post_type,
t.degree_time,
t.manage_level,
t.email,
t.post_level,
t.office_level,
t.talent_title,
t.address,
t.degree,
t.mobile,
t.birth,
t.authorized_type,
t.realname,
t.arrive_time,
t.native_place,
t.marital_status,
t.staff_type,
t.phone,
t.idcard,
t.on_job,
t.pro_post_level
from ow_member m, ow_teacher t where t.user_id=m.user_id  ;


ALTER TABLE `ow_teacher`
	ADD CONSTRAINT `FK_ow_teacher_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`);

	ALTER TABLE `ow_student`
	ADD CONSTRAINT `FK_ow_student_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`);

CREATE TABLE `ow_retire_apply` (
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '退休人员',
	`party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '退休后所在分党委',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '退休后所在党支部',
	`status` TINYINT(3) UNSIGNED NOT NULL COMMENT '0 申请 1分党委审核',
	`apply_id` INT(10) UNSIGNED NOT NULL COMMENT '申请人',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '填报时间',
	`verify_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '审核人',
	`verify_time` DATETIME NULL DEFAULT NULL COMMENT '审核时间',
	PRIMARY KEY (`user_id`),
	INDEX `FK_ow_retire_apply_ow_party` (`party_id`),
	INDEX `FK_ow_retire_apply_ow_branch` (`branch_id`),
	INDEX `FK_ow_retire_apply_sys_user_2` (`apply_id`),
	INDEX `FK_ow_retire_apply_sys_user_3` (`verify_id`),
	CONSTRAINT `FK_ow_retire_apply_sys_user_3` FOREIGN KEY (`verify_id`) REFERENCES `sys_user` (`id`),
	CONSTRAINT `FK_ow_retire_apply_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
	CONSTRAINT `FK_ow_retire_apply_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	CONSTRAINT `FK_ow_retire_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
	CONSTRAINT `FK_ow_retire_apply_sys_user_2` FOREIGN KEY (`apply_id`) REFERENCES `sys_user` (`id`)
)
COMMENT='在职党员退休记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
CREATE TABLE `ow_member_quit` (
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '账号ID',
	`party_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '分党委名称',
	`branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '党支部名称',
	`type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类别，1自动退党 2开除党籍 3党员去世',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	`grow_time` DATE NULL COMMENT '入党时间' ,
	`quit_time` DATE NOT NULL COMMENT '出党时间',
	`status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0未审核 1已审核，暂时未用',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`user_id`),
	CONSTRAINT `FK_ow_member_quit_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
)
COMMENT='党员出党'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `ow_member_abroad` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员',
	 `party_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '分党委名称' ,
	 `branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '党支部名称' ,
	 `grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间' ,
	`abroad_time` DATE NULL DEFAULT NULL COMMENT '出国时间',
	`reason` VARCHAR(200)  NULL DEFAULT NULL COMMENT '出国缘由',
	`expect_return_time` DATE NULL DEFAULT NULL COMMENT '预计归国时间',
	`actual_return_time` DATE NULL DEFAULT NULL COMMENT '实际归国时间',
	PRIMARY KEY (`id`),
	INDEX `FK_ow_member_abroad_sys_user` (`user_id`),
	CONSTRAINT `FK_ow_member_abroad_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
)
COMMENT='党员出国境信息'
ENGINE=InnoDB
;

ALTER TABLE `sys_user`
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1男 2女 3未知' AFTER `realname`,
	ADD COLUMN `birth` DATE NULL DEFAULT NULL COMMENT '出生年月' AFTER `gender`;


CREATE TABLE `ow_member_return` (
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属党支部，直属党支部没有这一项',
	`active_time` DATE NULL DEFAULT NULL COMMENT '确定为入党积极分子时间',
	`candidate_time` DATE NULL DEFAULT NULL COMMENT '确定为发展对象时间',
	`grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间，由党支部填写、分党委审核，党总支、直属党支部需增加组织部审核',
	`positive_time` DATE NULL DEFAULT NULL COMMENT '转正时间',
	`status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态，0申请 1支部审核 2分党委审核',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`user_id`),
	INDEX `FK_ow_member_return_ow_party` (`party_id`),
	INDEX `FK_ow_member_return_ow_branch` (`branch_id`),
	CONSTRAINT `FK_ow_member_return_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
	CONSTRAINT `FK_ow_member_return_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
	CONSTRAINT `FK_ow_member_return_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`)
)
COMMENT='留学归国人员申请恢复组织生活'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;



#2015.1129
# 联系院系所 mt_leader_contact
# 分管部门 mt_leader_manager
# 单位管理->综合管理 3 /unit_layout unitLayout:*
ALTER TABLE `base_unit_admin`
	CHANGE COLUMN `is_admin` `is_admin` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否管理员，暂未用' AFTER `post_id`;

##2015.11.28
drop table base_unit_transfer_item;
ALTER TABLE `base_unit_transfer`
	ADD COLUMN `dispatchs` VARCHAR(200) NULL COMMENT '相关发文' AFTER `pub_time`;

drop table base_unit_cadre_transfer_item;
	ALTER TABLE `base_unit_cadre_transfer`
	ADD COLUMN `dispatchs` VARCHAR(200) NOT NULL COMMENT '相关发文，逗号分隔' AFTER `dismiss_time`;

CREATE TABLE `base_unit_admin_group` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上一届',
	`unit_id` INT(10) UNSIGNED NOT NULL COMMENT '所属单位',
	`name` VARCHAR(100) NOT NULL COMMENT '名称',
	`is_present` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否现任班子',
	`tran_time` DATE NULL DEFAULT NULL COMMENT '应换届时间',
	`actual_tran_time` DATE NULL DEFAULT NULL COMMENT '实际换届时间',
	`appoint_time` DATE NOT NULL COMMENT '任命时间，本届班子任命时间',
	`dispatch_unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发文，关联单位发文',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`),
	INDEX `FK_base_unit_admin_group_base_unit_admin_group` (`fid`),
	INDEX `FK_base_unit_admin_group_base_unit` (`unit_id`),
	CONSTRAINT `FK_base_unit_admin_group_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
	CONSTRAINT `FK_base_unit_admin_group_base_unit_admin_group` FOREIGN KEY (`fid`) REFERENCES `base_unit_admin_group` (`id`)
)
COMMENT='单位行政班子'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

CREATE TABLE `base_unit_admin` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_id` INT(10) UNSIGNED NOT NULL COMMENT '所属班子',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '关联干部',
	`post_id` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，关联元数据',
	`is_admin` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否管理员，暂未用',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`),
	INDEX `FK_base_unit_admin_base_unit_admin_group` (`group_id`),
	INDEX `FK_base_unit_admin_base_cadre` (`cadre_id`),
	INDEX `FK_base_unit_admin_base_meta_type` (`post_id`),
	CONSTRAINT `FK_base_unit_admin_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
	CONSTRAINT `FK_base_unit_admin_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
	CONSTRAINT `FK_base_unit_admin_base_unit_admin_group` FOREIGN KEY (`group_id`) REFERENCES `base_unit_admin_group` (`id`)
)
COMMENT='行政班子成员信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

