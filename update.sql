
20190224
修改资源：
个人信息(profile:view) -> 账号信息 /profile

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

