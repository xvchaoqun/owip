
-- 更新视图 qy_reward_record_view
-- 更新视图 qy_reward_obj_view
CREATE TABLE `qy_reward` (
	`id` INT(10) UNSIGNED NOT NULL COMMENT 'id',
	`name` VARCHAR(200) NULL DEFAULT NULL COMMENT '奖项名称',
	`type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '奖励对象 1 分党委 2 党支部  3 党员  4 党日活动 ',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='七一表彰_奖项'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


CREATE TABLE `qy_year` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度',
	`plan_pdf` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰方案pdf',
	`plan_pdf_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰方案pdf名称',
	`plan_word` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰方案word',
	`plan_word_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰方案word名称',
	`result_pdf` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰结果pdf',
	`result_pdf_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰结果pdf名称',
	`result_word` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰结果word',
	`result_word_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '表彰结果word名称',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='七一表彰_年度'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3
;


CREATE TABLE `qy_year_reward` (
	`id` INT(10) UNSIGNED NOT NULL COMMENT 'id',
	`year_id` INT(10) UNSIGNED NOT NULL COMMENT '年度',
	`reward_id` INT(10) UNSIGNED NOT NULL COMMENT '奖项id',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='七一表彰_每年度奖项'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `qy_reward_obj` (
	`id` INT(10) UNSIGNED NOT NULL COMMENT 'id',
	`year_reward_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度奖励id',
	`party_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '获表彰分党委名称',
	`party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '分党委id',
	`branch_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '获表彰党支部名称',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党支部id',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '获表彰党员id',
	`meeting_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '获表彰党日活动名称',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`)
)
COMMENT='七一表彰_奖励对象'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `qy_year_reward`
	ADD CONSTRAINT `FK_qy_year_reward_qy_reward` FOREIGN KEY (`reward_id`) REFERENCES `qy_reward` (`id`) ON DELETE CASCADE;

ALTER TABLE `qy_year_reward`
	ADD CONSTRAINT `FK_qy_year_reward_qy_year` FOREIGN KEY (`year_id`) REFERENCES `qy_year` (`id`) ON DELETE CASCADE;

ALTER TABLE `qy_reward_record`
	ADD CONSTRAINT `FK_qy_reward_record_qy_year_reward` FOREIGN KEY (`year_reward_id`) REFERENCES `qy_year_reward` (`id`) ON DELETE CASCADE;

ALTER TABLE `qy_reward_obj`
	ADD CONSTRAINT `FK_qy_reward_obj_qy_reward_record` FOREIGN KEY (`record_id`) REFERENCES `qy_reward_record` (`id`) ON DELETE CASCADE;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2700, 0, '七一表彰管理', '', 'menu', 'fa fa-files-o', NULL, 1, '0/1/', 0, 'qy:menu', NULL, NULL, NULL, 1, 4600);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2701, 0, '表彰院系级党委', '', 'url', '', '/qyRewardRecord?type=1', 2700, '0/1/2700/', 1, 'qyReward:menu1', NULL, NULL, NULL, 1, 400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2702, 0, '表彰党支部', '', 'url', '', '/qyRewardRecord?type=2', 2700, '0/1/2700/', 1, 'qyReward:menu2', NULL, NULL, NULL, 1, 300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2703, 0, '表彰党员', '', 'url', '', '/qyRewardRecord?type=3', 2700, '0/1/2700/', 1, 'qyReward:menu3', NULL, NULL, NULL, 1, 200);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2704, 0, '表彰党日活动', '', 'url', '', '/qyRewardRecord?type=4', 2700, '0/1/2700/', 1, 'qyReward:menu4', NULL, NULL, NULL, 1, 100);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2705, 0, '参数设置', '', 'url', '', '/qyReward', 2700, '0/1/2700/', 1, 'qyReward:menu', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2706, 0, '七一表彰:查看', '', 'function', '', NULL, 2700, '0/1/2700/', 1, 'qyReward:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2707, 0, '七一表彰:编辑', '', 'function', '', NULL, 2700, '0/1/2700/', 1, 'qyReward:edit', NULL, NULL, NULL, 1, NULL);
