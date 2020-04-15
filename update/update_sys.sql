2020-04-14
UPDATE `db_owip`.`base_meta_type` SET `name`='毛南族' WHERE  `name`='毛难族';
ALTER TABLE `unit` DROP INDEX `code`;
2020-02-24
-- 添加字段 桑文帅
ALTER TABLE `sys_html_fragment` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `remark`;
ALTER TABLE `base_content_tpl` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `update_time`;
ALTER TABLE `sys_scheduler_job` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `create_time`;
ALTER TABLE `sys_attach_file` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `create_time`;

2019-12-18
-- 插入资源数据 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3034, 0, '信息完整性汇总', '', 'url', '', '/stat_integrity?cls=1', 260, '0/1/260/', 1, 'partyIntegrity:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3035, 0, '信息完整性汇总', '', 'url', '', '/stat_integrity?cls=2', 260, '0/1/260/', 1, 'branchIntegrity:*', NULL, NULL, NULL, 1, NULL);

2019-12-18
-- 规范代码 桑文帅
UPDATE `sys_scheduler_job` SET `clazz`='job.member.UpdateIntegrityJob' WHERE  `clazz`='job.member.updateIntegrityJob';
UPDATE `sys_scheduler_job` SET `clazz`='job.party.UpdateIntegrityJob' WHERE  `clazz`='job.party.updateIntegrityJob';
UPDATE `sys_scheduler_job` SET `clazz`='job.branch.UpdateIntegrityJob' WHERE  `clazz`='job.branch.updateIntegrityJob';

2019-12-09
-- 添加定时任务 桑文帅
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES ('校验党员信息完整度', '', 'job.member.updateIntegrityJob', '0 0 3 * * ?', 1, 1, 30, '2019-12-03 09:26:36');
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES ('校验二级基层党组织完整度', '', 'job.party.updateIntegrityJob', '0 20 3 * * ?', 1, 1, 31, '2019-12-03 11:20:29');
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES ('校验党支部信息完整度', '', 'job.branch.updateIntegrityJob', '0 40 3 * * ?', 1, 1, 32, '2019-12-03 15:47:48');

2019-11-29
-- 建表语句 桑文帅

CREATE TABLE IF NOT EXISTS `sys_msg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '接收人',
  `send_user_id` int(10) unsigned NOT NULL COMMENT '发送人',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '通知发送时间',
  `ip` varchar(50) NOT NULL COMMENT 'ip',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态 1.未读 2.已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='系统提醒';

-- 添加定时任务 桑文帅
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES ('更新协同任务统计缓存', '', 'job.oa.SyncOaTaskUser', '0 0/2 * * * ?', 1, 1, 29, '2019-11-29 11:03:31');