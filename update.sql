

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

