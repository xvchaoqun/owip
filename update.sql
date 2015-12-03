

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

