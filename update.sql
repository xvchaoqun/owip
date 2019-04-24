
+ ow_organizer_group
+ ow_organizer

更新 党员发展信息导入模板.xlsx

20190424
更新南航

20190424

CREATE TABLE `ow_apply_sn_range` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '所属年度',
	`prefix` VARCHAR(20) NULL DEFAULT NULL COMMENT '编码前缀',
	`start_sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '起始编码',
	`end_sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '结束编码',
	`len` INT(10) UNSIGNED NOT NULL COMMENT '编码长度，除前缀外的编码位数',
	`use_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已使用数量',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='入党志愿书编码段，连续编码段，同一年度不允许存在交集；每年可能有多个；'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

CREATE TABLE `ow_apply_sn` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年份',
	`sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '编码',
	`display_sn` VARCHAR(30) NOT NULL COMMENT '显示编码，编码前缀+编码（不足编码长度前面补0）',
	`range_id` INT(10) UNSIGNED NOT NULL COMMENT '所属号段',
	`is_used` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否已使用',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '使用人',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `year_sn` (`year`, `sn`),
	UNIQUE INDEX `sn_range_id` (`sn`, `range_id`)
)
COMMENT='入党志愿书编码，编码段添加时同步新增，编码段修改或删除时，如果存在已使用的号码，则不允许删除；组织部审批通过则标记为已使用，如果打回或移除操作，则标记为未使用。'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=1005
;

ALTER TABLE `ow_member_apply`
	CHANGE COLUMN `apply_sn` `apply_sn_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `draw_status`;

ALTER TABLE `ow_member_apply`
	ADD CONSTRAINT `FK_ow_member_apply_ow_apply_sn` FOREIGN KEY (`apply_sn_id`) REFERENCES `ow_apply_sn` (`id`);

ALTER TABLE `ow_member_apply`
	CHANGE COLUMN `apply_sn_id` `apply_sn_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联志愿书编码' AFTER `draw_status`,
	ADD COLUMN `apply_sn` VARCHAR(30) NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `apply_sn_id`;

-- 更新 ow_member_apply_view
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select ma.*, m.status as _status, if((m.status is null or m.status=1), 0, 1) as member_status
     , p.sort_order as party_sort_order, b.sort_order as branch_sort_order
from  ow_member_apply ma
        left join ow_branch b on ma.branch_id=b.id
        left join ow_party p on b.party_id=p.id
        left join ow_member m  on ma.user_id = m.user_id;

update abroad_passport set code = null where code='';

ALTER TABLE `abroad_passport`
	ADD UNIQUE INDEX `code` (`code`);

20190421

ALTER TABLE `sc_committee_vote`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性' AFTER `post`,
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别' AFTER `post_type`,
	CHANGE COLUMN `agree_count` `agree_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决同意票数' AFTER `unit_id`,
	CHANGE COLUMN `abstain_count` `abstain_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决弃权票数' AFTER `agree_count`,
	CHANGE COLUMN `disagree_count` `disagree_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决反对票数' AFTER `abstain_count`;

更换光连接池（必须保证telnet 127.0.0.1 3306 通; 全部jar更换）

20190418

更新南航

20190418

ALTER TABLE `cadre_edu`
	CHANGE COLUMN `school_type` `school_type` TINYINT(3) UNSIGNED NULL COMMENT '学校类型， 1本校 2境内 3境外' AFTER `major`;

ALTER TABLE `ow_member_apply`
	ADD COLUMN `apply_sn` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `draw_status`;

ALTER TABLE `ow_party`
	ADD COLUMN `mailbox` VARCHAR(50) NULL DEFAULT NULL COMMENT '信箱' AFTER `email`;
-- 更新 ow_party_view
DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_teacher where status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id;

CREATE TABLE `ow_party_public` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 发展党员公示文件 2 党员转正公示文件',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党委',
	`pub_date` DATE NOT NULL COMMENT '公示日期，强制当天',
	`party_name` VARCHAR(100) NOT NULL COMMENT '党委名称',
	`email` VARCHAR(50) NOT NULL COMMENT '邮箱',
	`phone` VARCHAR(50) NOT NULL COMMENT '联系电话',
	`mailbox` VARCHAR(50) NOT NULL COMMENT '信箱',
	`num` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '公示人数',
	`pub_users` TEXT NULL COMMENT '公示对象，账号ID，逗号隔开',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '创建人',
	`is_publish` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否发布',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人IP',
	PRIMARY KEY (`id`)
)
COMMENT='党员公示文件，包含发展党员公示文件、党员转正公示文件，每个党委每天每类最多只能有一个公示'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14
;
CREATE TABLE `ow_party_public_user` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`public_id` INT(10) UNSIGNED NOT NULL COMMENT '所属公示',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '公示对象',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党委',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属支部',
	`party_name` VARCHAR(100) NOT NULL COMMENT '党委名称',
	`branch_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '支部名称',
	PRIMARY KEY (`id`),
	INDEX `FK_ow_public_user_ow_public` (`public_id`),
	CONSTRAINT `FK_ow_public_user_ow_public` FOREIGN KEY (`public_id`) REFERENCES `ow_party_public` (`id`) ON DELETE CASCADE
)
COMMENT='党员公示，包含党员发展公示、党员转正公示'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=789
;

ALTER TABLE `ow_member_apply`
	ADD COLUMN `active_train_start_time` DATE NULL DEFAULT NULL COMMENT '参加培训起始时间，成为入党积极分子之后参加的培训' AFTER `active_time`,
	ADD COLUMN `active_train_end_time` DATE NULL DEFAULT NULL COMMENT '参加培训结束时间' AFTER `active_train_start_time`,
	ADD COLUMN `active_grade` VARCHAR(20) NULL DEFAULT NULL COMMENT '积极分子结业考试成绩' AFTER `active_train_end_time`,
	CHANGE COLUMN `train_time` `candidate_train_start_time` DATE NULL DEFAULT NULL COMMENT '参加培训起始时间，成为发展对象之后参加的培训' AFTER `candidate_time`,
	ADD COLUMN `candidate_train_end_time` DATE NULL DEFAULT NULL COMMENT '参加培训结束时间' AFTER `candidate_train_start_time`,
	ADD COLUMN `candidate_grade` VARCHAR(20) NULL DEFAULT NULL COMMENT '发展对象结业考试成绩' AFTER `candidate_train_end_time`,
	ADD COLUMN `grow_public_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '发展公示' AFTER `apply_sn`,
	ADD COLUMN `positive_public_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '转正公示' AFTER `grow_status`;

-- 更新 ow_member_apply_view
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select ma.*, m.status as _status, if((m.status is null or m.status=1), 0, 1) as member_status
     , p.sort_order as party_sort_order, b.sort_order as branch_sort_order
from  ow_member_apply ma
        left join ow_branch b on ma.branch_id=b.id
        left join ow_party p on b.party_id=p.id
        left join ow_member m  on ma.user_id = m.user_id;

-- 更新sys_property表的内容
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (1, 'partyName', '二级党委名称', '分党委', 1, 1, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (2, 'zzb_email', '组织部邮箱', 'zzb@xxx.com', 1, 2, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (3, 'zzb_mailbox', '组织部信箱', '11xx25号', 1, 3, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (4, 'zzb_phone', '组织部电话', '84xx2749', 1, 4, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (5, 'memberOut_toTitle_remark', '转入单位抬头备注', '注：如果类别是京外，则抬头必须是区县级以上组织部门', 1, 5, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (6, 'memberApply_needActiveTrain', '积极分子培训', 'false', 3, 6, '是否需要提交培训时间、成绩');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (7, 'memberApply_needCandidateTrain', '发展党员培训', 'false', 3, 7, '培训时间、成绩');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (8, 'member_needGrowTime', '入党、转正时间是否必填', 'false', 3, 8, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (9, 'cadreFamily_noNeedBirth', '家庭成员出生日期', 'false', 3, 9, '家庭成员出生日期是否选填');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (10, 'cadrePostPro_noNeed', '干部专业技术职务', 'false', 3, 10, '干部专业技术职务、等级和分级时间是否选填');



更新common-utils

删除 avatar.default



20190413
ALTER TABLE `base_short_msg`
	ADD COLUMN `relate_sn` VARCHAR(50) NULL DEFAULT NULL COMMENT '发送编号，每次定向发送时生成的UUID' AFTER `relate_id`;

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `send_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送次数' AFTER `sort_order`,
	ADD COLUMN `send_user_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送人次' AFTER `send_count`;

update base_short_msg_tpl tpl, (select relate_id, count(*) num from base_short_msg where relate_type=2 group by relate_id)
  msg set send_count=msg.num, send_user_count=msg.num where msg.relate_id=tpl.id;

ALTER TABLE `base_short_msg_tpl`
	CHANGE COLUMN `send_count` `send_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送次数' AFTER `sort_order`,
	CHANGE COLUMN `send_user_count` `send_user_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送人次' AFTER `send_count`;




20190411
更新北邮----
更新北化工
