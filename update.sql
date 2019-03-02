
20190302

更新
ow_branch_member_view
ow_party_member_view

ALTER TABLE `sys_resource`
	CHECKSUM=1,
	AUTO_INCREMENT=1000;

partyMemberGroup:del -> 撤销

branchMemberGroup:del ——》 撤销

+ branchMemberGroup:realDel
+ partyMemberGroup:realDel

unit:* -> unit:list

+ unit:*

cmMember:* -> cmMember:list1
+ cmMember:* 两委委员管理


unit:history->历史单位管理

+ party:viewAll 党建全部查看权限

cmMember:list3 -> cmMember:list

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

