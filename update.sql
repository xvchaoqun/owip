201904010
更新南航，西交大

201904010
更新 京内京外介绍信 元数据

20190407
更新南航，西交大

20190406

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `specialty` `specialty` VARCHAR(200) NULL DEFAULT NULL COMMENT '熟悉专业有何特长' AFTER `household`;

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `phone`;

ALTER TABLE `ow_member_out_modify`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `phone`;

-- 更新 ow_member_out_view
DROP VIEW IF EXISTS `ow_member_out_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_out_view` AS
select mo.*, m.type as member_type, t.is_retire
from ow_member_out mo, ow_member m
left join sys_teacher_info t on t.user_id = m.user_id where mo.user_id=m.user_id;

ALTER TABLE `ow_member_in`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `political_status`;

ALTER TABLE `ow_member_in_modify`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `political_status`;

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (84, NULL, '组织关系转入、转出类别', '党建综合管理', '组织关系转入、转出', 'mc_member_in_out_type', '是否套打', '', '', 84, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (526, 84, '京内', 'mt_kfgny5', 0, '', '', 1, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (527, 84, '京外', 'mt_hjbdmh', 1, '', '', 2, 1);

-- 京内 1 -> 526
-- 京外 2 -> 527
update ow_member_out set type=526 where type=1;
update ow_member_out set type=527 where type=2;
update ow_member_out_modify set type=526 where type=1;
update ow_member_out_modify set type=527 where type=2;

update ow_member_in set type=526 where type=1;
update ow_member_in set type=527 where type=2;
update ow_member_in_modify set type=526 where type=1;
update ow_member_in_modify set type=527 where type=2;

ALTER TABLE `sys_feedback`
	ADD COLUMN `title` VARCHAR(200) NULL COMMENT '标题' AFTER `user_id`;

ALTER TABLE `sys_feedback`
	ADD COLUMN `pics` TEXT NULL COMMENT '截图，多张以逗号分割' AFTER `content`;

ALTER TABLE `ow_branch_member`
	DROP INDEX `group_id_user_id`,
	DROP FOREIGN KEY `FK_ow_branch_member_ow_branch_member_group`;

ALTER TABLE `ow_party_member`
	DROP INDEX `group_id_user_id`,
	DROP FOREIGN KEY `FK_ow_party_member_ow_party_member_group`;

ALTER TABLE `sys_feedback`
	ADD CONSTRAINT `FK_sys_feedback_sys_feedback` FOREIGN KEY (`fid`) REFERENCES `sys_feedback` (`id`) ON DELETE CASCADE;

ALTER TABLE `sys_feedback`
	ADD COLUMN `self_can_edit` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '本人是否可修改，别人未回复前的记录，本人均可修改和删除' AFTER `reply_count`;

update sys_feedback set self_can_edit = 1 where fid is null;
update sys_feedback set title = content;

给分党委管理员添加 分党委班子管理的权限
更新元数据，刷新metadata.js

20190328
更新南航，北邮


20190328
更新 unit_post_view


20190326

更新 common-utils

ALTER TABLE `unit_post`
	ADD COLUMN `leader_type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否班子负责人，0 否 1 党委班子负责人 2 行政班子负责人' AFTER `is_principal_post`;

	更新 unit_post_view

ALTER TABLE `cadre_post`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性，关联元数据' AFTER `post`,
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '行政级别，关联元数据' AFTER `post_type`,
	CHANGE COLUMN `double_unit_ids` `double_unit_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '双肩挑单位' AFTER `is_double`;

更新 cadre_view（添加是否班子负责人）


update sys_resource set parent_id=1, parent_ids='0/1/', name='待调整班子干部', sort_order=6250, menu_css='fa fa-history'  where  id=867;

update sys_resource set name='行政班子届满列表', url='/unitTeam?list=2', permission='unitTeam:list2', sort_order=90  where id=868;


20190322

北师大、 北邮


20190321
更新南航、北邮

20190321

update sys_resource set permission='sysUser:list' where permission='sysUser:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1012, 0, '账号管理权限', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'sysUser:*', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `cadre_family`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NOT NULL COMMENT '排序，每个干部的排序' AFTER `unit`;

update cadre_family set sort_order =id;

更新 common-utils



20190319
更新南航

20190319

-- + sys_property表
CREATE TABLE `sys_property` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`code` VARCHAR(50) NOT NULL COMMENT '代码',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '名称',
	`content` VARCHAR(200) NOT NULL COMMENT '内容',
	`type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类型，备用字段，1 字符串 2 整数 3 布尔值 4 时间 5 图片',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '说明',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `code` (`code`)
)
COMMENT='系统属性'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;


ALTER TABLE `ow_member`
	ADD COLUMN `sponsor` VARCHAR(50) NULL DEFAULT NULL COMMENT '入党介绍人' AFTER `candidate_time`,
	ADD COLUMN `grow_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '入党时所在党支部' AFTER `grow_time`,
	ADD COLUMN `positive_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '转正时所在党支部' AFTER `positive_time`;

更新 ow_member_student， ow_member_teacher

*** 更新 党员导入表（校内、校外）


ALTER TABLE `dispatch`
	CHANGE COLUMN `code` `code` VARCHAR(10) NOT NULL COMMENT '发文号，比如师党干[2015]年01号， 录入01' AFTER `dispatch_type_id`;

更新  dispatch_view  dispatch_cadre_view  dispatch_unit_view

ALTER TABLE `sc_dispatch`
	CHANGE COLUMN `code` `code` VARCHAR(10) NULL DEFAULT NULL COMMENT '发文号，手动填写' AFTER `dispatch_type_id`;

更新 sc_dispatch_view


-- + cadre:updateWithoutRequired
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1011, 0, '更新不考虑必填项', '用于干部信息录入角色', 'function', '', NULL, 90, '0/1/88/90/', 1, 'cadre:updateWithoutRequired', NULL, NULL, NULL, 1, NULL);


20190311

更新 common-utils


ALTER TABLE `cis_evaluate`
	CHANGE COLUMN `file_path` `pdf_file_path` VARCHAR(200) NULL COMMENT '材料内容， PDF版' AFTER `type`,
	ADD COLUMN `word_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '材料内容，WORD版' AFTER `pdf_file_path`;

ALTER TABLE `cis_evaluate`
	CHANGE COLUMN `file_name` `file_name` VARCHAR(100) NULL AFTER `word_file_path`;

20190310
北邮


20190310

删除OneSendResult.java
bnu.sso
bnu.newpay -> api.bnu.jar

20190309

更新 common-utils

20190308
南航


20190306

ALTER TABLE `crp_record`
	ADD COLUMN `unit_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '挂职单位，针对校内和外单位到本校挂职' AFTER `project`,
	CHANGE COLUMN `unit` `unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '挂职单位，针对校外挂职' AFTER `unit_id`;



20190306
南航

20190306

ALTER TABLE `ow_member`
	ADD COLUMN `add_type` INT UNSIGNED NULL COMMENT '增加类型' AFTER `source`;

ALTER TABLE `ow_member_modify`
	ADD COLUMN `add_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '增加类型' AFTER `source`;


INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (83, NULL, '增加类型', '党建', '党员信息', 'mc_member_add_type', '', '', '', 83, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '原有', 'mt_member_add_type_old', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '新发展', 'mt_member_add_type_new', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '停止党籍人员恢复党籍', 'mt_anjl6t', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '错误开除党籍人员恢复党籍', 'mt_skyiyx', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '其他恢复党籍', 'mt_tijaau', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '其他原因添加', 'mt_n1tdpz', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '恢复党籍', 'mt_member_add_type_return', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (83, '转入', 'mt_member_add_type_tran', NULL, '', '', 4, 1);

update sys_resource set permission = 'unitTeam:list1' where permission = 'unitTeam:list';
update sys_resource set name='查看干部任免事项', permission = 'unitCadreTransferGroup:list' where permission = 'unitCadreTransferGroup:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1010, 0, '查看班子', '', 'function', '', NULL, 85, '0/1/85/', 1, 'unitTeam:list', NULL, NULL, NULL, 1, NULL);

REPLACE INTO `sys_role` (`id`, `role`, `description`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES (61, 'role_cadre_view3', '查看干部、单位、岗位、组织机构、兼职信息', '85,245,84,87,1003,1010,260,106,181,189,182,203,196,836,838,423,372,373,900,89,140,88,90,306,307,309,310,311,316,317,319,852,231,232,234,235,236,237,238,239,240,241,242,374,318,395,453,1005,1006,862,863,864,1004,312', '-1', 0, 0, 0, 44, '查看干部、单位、岗位、组织机构、兼职信息');


更新 ow_member_student、ow_member_teacher

20190302
更新南航

20190302

更新
ow_branch_member_view
ow_party_member_view

ALTER TABLE `sys_resource`
	CHECKSUM=1,
	AUTO_INCREMENT=1000;


+ branchMemberGroup:realDel
+ partyMemberGroup:realDel


update sys_resource set permission='unit:list' where permission='unit:*';
update sys_resource set permission='cmMember:list1' where permission='cmMember:*';
update sys_resource set permission='cmMember:list' where permission='cmMember:list3';

update sys_resource set name='撤销' where permission='partyMemberGroup:del';
update sys_resource set name='撤销' where permission='branchMemberGroup:del';
update sys_resource set name='历史单位管理' where permission='unit:history';

+ unit:*

+ cmMember:* 两委委员管理

+ party:viewAll 党建全部查看权限

+ cadre:archive 查询干部电子档案
+ cadre:onlyView 仅允许查看干部信息

20190301
更新南航


20190226
更新南航

20190226
ALTER TABLE `cet_expert`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '专家类型，1 校内专家 2 校外专家' AFTER `id`,
	ADD COLUMN `code` INT(10) UNSIGNED NULL COMMENT '编码，用于校外专家' AFTER `type`,
	ADD COLUMN `user_id` INT(10) UNSIGNED NULL COMMENT '关联账号，用于校内专家' AFTER `code`;

更新 cet_expert_view

update cet_expert set type=1;

更新common-utils

20190225
更新 南航


20190224
UPDATE `sys_resource` SET `name`='账号信息', `url`='/profile' WHERE  `id`=207;

更新common-utils

20190222

删除 Xls****类
移动IPartyMapper, ISysMapper

ALTER TABLE `ext_yjs`
	ADD COLUMN `hkszd` VARCHAR(120) NULL DEFAULT NULL COMMENT '户口所在地' AFTER `qzny`,
	ADD COLUMN `syszd` VARCHAR(120) NULL DEFAULT NULL COMMENT '生源所在地' AFTER `hkszd`;


ALTER TABLE `sys_resource`
	ADD COLUMN `role_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT
	'权限拥有的角色数量，当前拥有该权限的正常角色数量，系统每小时自动统计' AFTER `permission`;

update sys_resource set permission='sysResource:*' where permission='sys:resource';

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `remark`;

ALTER TABLE `sys_attach_file`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `type`;

update sys_attach_file set sort_order= id;

update base_short_msg_tpl set sort_order= id;

ALTER TABLE `sys_scheduler_job`
	ADD COLUMN `need_log` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否记录执行日志' AFTER `is_started`;

CREATE TABLE `sys_scheduler_log` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`job_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联任务',
	`job_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '任务名称',
	`job_group` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在组',
	`is_manual_trigger` TINYINT(1) NULL DEFAULT '0' COMMENT '是否手动触发',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态, 1 jobToBeExecuted 2 jobExecutionVetoed 3 jobWasExecuted',
	`trigger_time` DATETIME NULL DEFAULT NULL COMMENT '发生时间',
	PRIMARY KEY (`id`)
)
COMMENT='系统定时任务'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=59
;


更新 common-utils

