
2019-12-18
-- 干部述职报告 苏小霞
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2708, 0, '述职报告', '', 'url', '', '/cadrePositionReport?admin=1', 88, '0/1/88/', 0, 'cadrePositionReport:adminMenu', NULL, NULL, NULL, 1, 1900);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2709, 0, '述职报告', '', 'url', '', '/cadrePositionReport?admin=0', 353, '0/1/353/', 1, 'cadrePositionReport:menu', NULL, NULL, NULL, 1, 450);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2710, 0, '述职报告:查看', '', 'function', '', NULL, 2708, '0/1/88/2708/', 1, 'cadrePositionReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2711, 0, '述职报告:编辑', '', 'function', '', NULL, 2708, '0/1/88/2708/', 1, 'cadrePositionReport:edit', NULL, NULL, NULL, 1, NULL);

CREATE TABLE `cadre_position_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '干部id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度',
	`create_date` DATE NULL DEFAULT NULL COMMENT '创建时间',
	`content` TEXT NULL COMMENT '述职报告内容',
	PRIMARY KEY (`id`)
)
COMMENT='干部述职报告'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=8
;