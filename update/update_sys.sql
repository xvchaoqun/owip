

-- 2021.2.5 ly
ALTER TABLE `sys_teacher_info`
	ADD COLUMN `is_full_time_teacher` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否专任教师' AFTER `post`;

-- 2021.01.28 hwd
ALTER TABLE `base_api_key`
	CHANGE COLUMN `remark` `remark` VARCHAR(50) NOT NULL COMMENT '备注' AFTER `api_key`;

INSERT INTO `base_api_key` (`name`, `api_key`, `remark`) VALUES ('cadreApi', '5e257013876cc0e69a53bae6037d1491', '');

-- 2021.1.27 hwd
CREATE TABLE `base_api_key` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`name` VARCHAR(50) NULL DEFAULT '0' COMMENT 'app名称',
	`api_key` VARCHAR(50) NULL DEFAULT '0' COMMENT '对应键值',
	`remark` VARCHAR(50) NOT NULL DEFAULT '0' COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='API接口管理'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=13
;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (4001, 0, '接口管理', '', 'url', '', '/apiKey', 67, '0/1/67/', 1, 'apiKey:*', NULL, NULL, NULL, 1, NULL);


-- 2021.1.22
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2634, 0, '党建信息统计', '', 'menu', '组织部，分党委', NULL, 105, '0/1/105/', 0, 'statSummary:menu', NULL, NULL, NULL, 1, 29700);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2566, 0, '二级党委数据统计', '组织部,分党委', 'url', '', '/stat/partySum?cls=1', 2634, '0/1/105/2634/', 1, 'stat:partySum', 2, NULL, NULL, 1, 60);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2565, 0, '组织机构年统数据', '组织部', 'url', '', '/stat/owSum', 2634, '0/1/105/2634/', 1, 'stat:owSum', 1, NULL, NULL, 1, 70);

-- 2021.1.20 ly
ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生' AFTER `code`;

-- 2021.1.14 ly
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('use_inside_pgb', '启用内设党总支', 'false', 3, 97, '在二级党委和党支部之间加一层内设党总支');

-- 2021.1.7 sxx
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '年终考核测评', '', 'function', '', NULL, 90, '0/1/88/90/', 1, 'cadreEvaResult:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '设置考察对象有效期', '', 'function', '', NULL, 341, '0/1/339/341/', 1, 'cadreInspect:validTime', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '任务报送', '', 'function', '', NULL, 562, '0/1/560/562/', 1, 'userOaTask:report', NULL, NULL, NULL, 1, NULL);

-- 2021.1.6 ly
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('use_code_as_identify', '用学工号作为编号', 'true', 3, 96, '转出中的编号生成方式：1学工号作为编号；0通过年份和一个四位数字生成编号');

-- 2020.12.28 ly
UPDATE `sys_resource` SET `remark`='组织部，分党委' WHERE  `id`=2553;
UPDATE `sys_resource` SET `remark`='组织部，分党委' WHERE  `id`=3033;

-- 2020.12.26 sxx
INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (0, '干部信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:cadre', NULL, NULL, NULL, 1, NULL);

-- 2020.12.22 ly
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('ignore_plan_and_draw', '党员发展流程节点控制', 'false', 3, 83, '打开党员发展流程中的“列入发展计划”和“领取志愿书”');

-- 2020.12.17 ly
ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生 4博士研究生' AFTER `code`;
-- 更新ow_member_view


-- 2020.11.25 ly
UPDATE `sys_resource` SET `name`='系统提醒', `url`='/sys/sysMsg?cls=2' WHERE  `id`=3031;
UPDATE `sys_resource` SET `name`='系统提醒', `url`='/sys/sysMsg?cls=1' WHERE  `id`=3032;
ALTER TABLE `sys_msg`
	ADD COLUMN `confirm_time` DATETIME NULL DEFAULT NULL COMMENT '通知确认时间' AFTER `create_time`;
ALTER TABLE `sys_msg`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态 1.未确认 2.已确认' AFTER `ip`;
ALTER TABLE `sys_msg`
	ALTER `create_time` DROP DEFAULT;
ALTER TABLE `sys_msg`
	CHANGE COLUMN `create_time` `send_time` DATETIME NOT NULL COMMENT '通知发送时间' AFTER `content`;


2020.11.16

UPDATE `sys_resource` SET `name`='党校培训信息' WHERE  `permission`='userCetProject:*';
UPDATE `sys_resource` SET `url`='/user/cet/cetProject?type=1&isPartyProject=1' WHERE  `permission`='userCetProject:list3';
UPDATE `sys_resource` SET `url`='/user/cet/cetProject?type=2&isPartyProject=1' WHERE  `permission`='userCetProject:list4';

2020.11.12
ALTER TABLE `sys_role`
	ADD COLUMN `resource_ids_minus` TEXT NULL COMMENT '角色减资源，网页端' AFTER `m_resource_ids`,
	ADD COLUMN `m_resource_ids_minus` TEXT NULL COMMENT '角色减资源，移动端' AFTER `resource_ids_minus`;

update sys_role set resource_ids_minus=resource_ids, m_resource_ids_minus=m_resource_ids where type=2;
update sys_role set resource_ids='-1',m_resource_ids='-1' where type=2;

ALTER TABLE `sys_role`
	DROP COLUMN `type`;

INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('cadre_leave', '离任干部', '-1', '-1', '-1', '-1', NULL, 0, 0, 68, '');

--执行 /test/sys_role.jsp

2020.09.04
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('show_msg_btns', '系统短信通知按钮', 'true', 3, 74, '是否显示短信通知按钮');

2020.08.19
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2791, 0, '参与党代会的党组织', '', 'url', '', '/pcs/pcsPartyList', 469, '0/1/469/', 1, 'pcsPartyList:*', NULL, NULL, NULL, 1, 1450);

2020.08.19
UPDATE `db_owip`.`sys_property` SET `name`='干部配备一览表显示',`content`='1',`type`='2',`remark`='1 原版   2 显示空岗及保留待遇  3 不占职数（无行政级别）' WHERE  `code`='upa_displayPosts';

2020.08.12
-- 添加源数据
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (3002, NULL, '交流轮岗类型', '干部选拔任用', '交流轮岗', 'mc_sc_shift', '', '', '', 2615, 1);

INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (811, 3002, '机关部处之间轮岗', 'mt_cudnjh', NULL, NULL, '', 1, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (812, 3002, '学院之间轮岗', 'mt_qkhpqb', NULL, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (813, 3002, '机关部处与学院之间轮岗', 'mt_ut5hjb', NULL, NULL, '', 3, 1);

-- 更新系统资源
UPDATE `db_owip`.`sys_resource` SET `type`='menu', `url`=NULL WHERE  `id`=893;

-- 添加系统资源
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3042, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_sc_shift', 893, '0/1/339/893/', 1, 'mc_sc_shift:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3043, 0, '交流轮岗', '', 'url', '', '/sc/scShift', 893, '0/1/339/893/', 1, 'scShiftPost:*', NULL, NULL, NULL, 1, NULL);


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