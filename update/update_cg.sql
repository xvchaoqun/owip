
2019-09-10
-- 添加资源数据 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3001, 0, '委员会和领导小组', '', 'menu', 'fa fa-star', NULL, 1, '0/1/', 0, 'cg:menu', NULL, NULL, NULL, 1, 3500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3002, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_cg_type,mc_cg_staff', 3001, '0/1/3001/', 1, 'mc_cg_staff,mc_cg_type:*', NULL, NULL, NULL, 1, 3510);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3003, 0, '委员会和领导小组列表', '', 'url', '', '/cg/cgTeam', 3001, '0/1/3001/', 1, 'cgTeam:*', NULL, NULL, NULL, 1, 3590);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3004, 0, '办公室主任', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgLeader:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3005, 0, '委员会和小组成员', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgMember:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3006, 0, '工作职责', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgRule:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3007, 0, '挂靠单位', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgUnit:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3008, 0, '统计分析', '', 'url', '', '/cg/cgCount', 3001, '0/1/3001/', 1, 'cgCount:*', NULL, NULL, NULL, 1, 3550);
-- 添加角色
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_cg_admin', '委员会和领导小组管理员', '2084,2086,2091,2085,2087,2088,2089,2090', '-1', NULL, 0, 0, 60, '');

2019-09-10
-- 添加定时任务数据 桑文帅
INSERT INTO `sys_scheduler_job` (`id`, `name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES (26, '委员会和领导小组人员变动提醒', '委员会和领导小组关联岗位的现任干部变动提醒', 'job.cg.NeedAdjustMember', '0 0 0/2 * * ?', 0, 0, 27, '2019-09-06 15:48:19');

2019-09-06
-- 添加字段 桑文帅
ALTER TABLE `cg_member` ADD COLUMN `need_adjust` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否需要调整' AFTER `is_current`;

2019-09-02
-- 删除字段 桑文帅
ALTER TABLE `cg_member` DROP COLUMN `user_ids`;

2019-08-08
-- 修改字段属性 桑文帅
ALTER TABLE `cg_member` ALTER `user_id` DROP DEFAULT;
ALTER TABLE `cg_member` CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NULL COMMENT '现任干部' AFTER `unit_post_id`;