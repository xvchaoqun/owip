2020.06.15
-- 添加系统参数 桑文帅
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (53, 'memberApply_needContinueDevelop', '申请继续培养', 'true', 3, 52, '申请继续培养');

2020.06.01
-- 添加权限资源 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3039, 1, '离任干部信息', '', 'url', 'fa fa-history', '/m/cadreHistory', 692, '0/692/', 1, 'm:cadreHistory:*', NULL, NULL, NULL, 1, 1849);

2020.05.19
-- 添加权限资源 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3038, 0, '干部信息表(简表)', '', 'function', '', NULL, 90, '0/1/88/90/', 1, 'cadreInfoFormSimple:*', NULL, NULL, NULL, 1, NULL);

2020-4-30
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2782, 0, '支部组织员信息', '', 'url', '', '/organizer?type=3', 1035, '0/1/105/1035/', 1, 'organizer:list3', NULL, NULL, NULL, 1, NULL);

2020-04-24
ALTER TABLE `sys_user_info`
	ADD COLUMN `res_ids_add` TEXT NULL DEFAULT NULL COMMENT '网页端加权限资源id' AFTER `sync`,
	ADD COLUMN `m_res_ids_add` TEXT NULL DEFAULT NULL COMMENT '手机端加权限资源id' AFTER `res_ids_add`,
	ADD COLUMN `res_ids_minus` TEXT NULL DEFAULT NULL COMMENT '网页端减权限资源id' AFTER `m_res_ids_add`,
	ADD COLUMN `m_res_ids_minus` TEXT NULL DEFAULT NULL COMMENT '手机端减权限资源id' AFTER `res_ids_minus`;

2020-04-23
-- 添加资源 桑文帅
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '聘任制干部信息', '', 'url', '', '/cadre?isEngage=1', 442, '0/1/88/442/', 1, 'statCadreEngage:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '保留待遇干部信息', '', 'url', '', '/cadre?isKeepSalary=1', 442, '0/1/88/442/', 1, 'statCadreKeepSalary:list', NULL, NULL, NULL, 1, NULL);

2020-04-21
-- 添加字段 桑文帅
ALTER TABLE `unit` ADD COLUMN `not_stat_post` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否不列入配备一览表' AFTER `status`;

2020-04-23
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (52, 'cadrePost_vacant', '干部配备一览表显示空岗情况', 'false', 3, 53, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (53, 'label_adminLevelNone', '无行政级别干部名称', '聘任制', 1, 54, '');

update cadre_admin_level set s_work_time=DATE_FORMAT(s_work_time,'%y-%m-01');
update cadre_admin_level set e_work_time=DATE_FORMAT(e_work_time,'%y-%m-01');
update cadre_post set np_work_time=DATE_FORMAT(np_work_time,'%y-%m-01');

ALTER TABLE `sys_role`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '类别，1加权限 2减权限' AFTER `name`;

2020-04-14
UPDATE `db_owip`.`base_meta_type` SET `code`='mt_nation_qz' WHERE  `name`='mt_irw3y7';

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