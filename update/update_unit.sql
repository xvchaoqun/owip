-- 2021 02.25 sxx
ALTER TABLE `unit_post`
	ADD COLUMN `label` VARCHAR(255) NULL DEFAULT NULL COMMENT '标签，关联元数据，多选' AFTER `is_cpc`;
-- 更新 unit_post_view

-- 2020 10.10 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2552, 0, '支部委员会届满列表', '', 'url', '', 'unitTeam?list=3', 867, '0/1/867/', 1, 'unitTeam:list3', NULL, NULL, NULL, 1, 95);
