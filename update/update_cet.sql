

-- 2021.4.9 ly
ALTER TABLE `cet_project_file`
	ADD COLUMN `website` VARCHAR(200) NULL DEFAULT NULL COMMENT '培训材料网址' AFTER `file_path`;

-- 2021.1.7 sxx
ALTER TABLE `cet_train_obj`
    ADD CONSTRAINT `FK_cet_train_obj_cet_train_course` FOREIGN KEY (`train_course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;

-- 2020.9.3 ly 删除西工大培训中的重复数据
DELETE FROM cet_upper_train WHERE id IN (SELECT tmp.id FROM (SELECT MIN(c.id) AS id,c.user_id,c.train_name, COUNT(*) AS count FROM cet_upper_train c
WHERE TYPE!=10
GROUP BY c.user_id,c.start_date,c.end_date,c.train_name
HAVING COUNT>1)tmp)

-- 2020.7.23 ly
DROP TABLE IF EXISTS `cet_project_file`;
CREATE TABLE IF NOT EXISTS `cet_project_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_id` int(10) unsigned NOT NULL COMMENT '所属培训班id',
  `file_name` varchar(200) NOT NULL COMMENT '培训课件名称',
  `file_path` varchar(200) DEFAULT NULL COMMENT '课件， pdf或word',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序，每个培训班内部的排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='培训课件';

-- 2020.6.29 ly
ALTER TABLE `cet_project_type`
	COMMENT='培训类别，针对专题班',
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 年度培训' AFTER `name`,
	ADD COLUMN `code` VARCHAR(50) NULL DEFAULT NULL COMMENT '代码' AFTER `type`;
UPDATE `sys_resource` SET `name`='培训类别（党校培训）' WHERE  `id`=658;

-- 2020.6.24 ly
ALTER TABLE `cet_upper_train`
	ALTER `train_type` DROP DEFAULT;
ALTER TABLE `cet_unit_project`
	ADD COLUMN `special_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '培训类别 1专题培训 2日常培训' AFTER `project_type`;
DROP VIEW `cet_party_view`;
ALTER TABLE `cet_party`
  ADD COLUMN `admin_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '管理员数量' AFTER `name`;

-- 20200619 ly
ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `train_type` `train_type` INT(10) UNSIGNED NULL COMMENT '培训班类型' AFTER `other_organizer`;



-- 20200617 ly
ALTER TABLE `cet_project_obj`
	DROP FOREIGN KEY `FK_cet_project_obj_cet_trainee_type`;
ALTER TABLE `cet_project_obj`
	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选了其他参训人员类型时，需要填写' AFTER `trainee_type_id`,
  ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`;

--  20200615 ly
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2601, NULL, '参训人员身份', '培训综合管理', '上级调训管理', 'mc_cet_identity', '', '', '', 2614, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2601, '双肩挑', 'mt_mg36bf', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2601, '支部书记', 'mt_mlzslp', NULL, '', '', 2, 1);
ALTER TABLE `cet_upper_train`
	DROP COLUMN `is_double`,
	DROP COLUMN `is_branch_secretary`;
ALTER TABLE `cet_upper_train`
	ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`,
	ADD COLUMN `score` VARCHAR(100) NULL DEFAULT NULL COMMENT '培训成绩' AFTER `pdf_note`,
	CHANGE COLUMN `post_id` `post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '时任职务属性' AFTER `title`;
ALTER TABLE `cet_unit_train`
	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选其他参训人员类型时，需要填写' AFTER `trainee_type_id`;
	ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`;
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '时任职务属性' AFTER `title`;
ALTER TABLE `cet_upper_train`
	ADD COLUMN `special_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '其他培训类别 1党校专题培训 2党校日常培训' AFTER `type`;



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