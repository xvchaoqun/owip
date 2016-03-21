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

