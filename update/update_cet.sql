
-- 20200612 ly 更新视图cet_party_view
ALTER TABLE `cet_party_admin`
	CHANGE COLUMN `party_id` `cet_party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委' AFTER `id`;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2538, 0, '二级党委培训管理员', '', 'url', '', '/cet/cetParty', 656, '0/1/384/656/', 0, 'cetParty:*', NULL, NULL, NULL, 1, 55);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2539, 0, '管理员', '', 'function', '', NULL, 3040, '0/1/384/656/3040/', 1, 'cetPartyAdmin:*', NULL, NULL, NULL, 1, NULL);

-- 20200611 ly
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2537, 0, '补录审核', '', 'function', '', NULL, 869, '0/1/384/869/', 1, 'cetUnitProject:check', NULL, NULL, NULL, 1, 100);

DELETE FROM sys_resource WHERE parent_id=724;
DELETE FROM sys_resource WHERE id=724;
DROP VIEW IF EXISTS `cet_party_view`;
ALTER TABLE `cet_party`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `sort_order`;

-- 20200609 ly
UPDATE `sys_resource` SET `url`='/cet/cetUnitTrain_info?cls=2' WHERE  `id`=2536;

-- 20200604 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2536, 0, '参训人员信息', '', 'url', '', '/cet/cetUnitTrain_info', 869, '0/1/384/869/', 1, 'cetUnitTrain:list', NULL, NULL, NULL, 1, 250);
ALTER TABLE `cet_unit_train`
	CHANGE COLUMN `status` `status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '补录状态 0 审批通过 1 待二级党委审批 2 待组织部审批 3暂存' AFTER `add_time`,
	ADD COLUMN `reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '未补录成功原因' AFTER `status`;


-- 20191204 ly 签退时间
-- cet_trainee_course_view有更新
ALTER TABLE `cet_trainee_course`
	ADD COLUMN `sign_out_time` DATETIME NULL DEFAULT NULL COMMENT '签退时间' AFTER `sign_time`;

-- 20191202 ly 培训对象类型

ALTER TABLE `cet_upper_train`
	ADD COLUMN `upper_train_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训对象类型' AFTER `user_id`;