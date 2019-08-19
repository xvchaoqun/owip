

-- -----------------??
ALTER TABLE `sc_motion`
	CHANGE COLUMN `seq` `seq` VARCHAR(50) NOT NULL COMMENT '动议编号' AFTER `id`,
	CHANGE COLUMN `way` `way` TINYINT(3) UNSIGNED NOT NULL COMMENT '动议主体，党委干部工作小组会、 党委常委会、 其他' AFTER `year`,
	ADD COLUMN `committee_id` INT UNSIGNED NULL COMMENT '动议记录，党委常委会' AFTER `way`,
	ADD COLUMN `group_topic_id` INT UNSIGNED NULL COMMENT '动议记录，干部小组会议题，类型为干部选任动议' AFTER `committee_id`,
	CHANGE COLUMN `hold_date` `hold_date` DATE NOT NULL COMMENT '动议时间' AFTER `group_topic_id`,
	CHANGE COLUMN `unit_post_id` `unit_post_id` INT(10) UNSIGNED NOT NULL COMMENT '拟调整岗位' AFTER `hold_date`,
	CHANGE COLUMN `topics` `topics` VARCHAR(300) NULL DEFAULT NULL COMMENT '关联议题，干部小组会议题或党委常委会议题，弃用' AFTER `way_other`;

ALTER TABLE `sc_motion`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `topics`;

-- 更新 sc_motion_view?

ALTER TABLE `crs_post`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实，选任方式为竞争上岗的纪实' AFTER `seq`,
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `record_id`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的岗位' AFTER `record_user_id`,
	CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL COMMENT '招聘岗位，如果选择了岗位则同步岗位信息' AFTER `unit_post_id`;


20190819

ALTER TABLE `unit_function`
	DROP COLUMN `img_path`;

ALTER TABLE `modify_table_apply`
	ADD COLUMN `reason` VARCHAR(200) NULL COMMENT '修改原因' AFTER `type`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                        `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1090, 0, '参数设置', '', 'function', '', NULL, 626, '0/1/339/626/', 1, 'mc_sc_group_topic_type:*', NULL, NULL, NULL, 1, NULL);
ALTER TABLE `sc_group_topic`
	ADD COLUMN `type` INT UNSIGNED NULL COMMENT '议题类型' AFTER `name`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '拟调整的岗位，动议、确定考察对象、推荐拟任人选' AFTER `type`,
	ADD COLUMN `sc_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '选任方式，动议' AFTER `unit_post_id`,
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实，确定考察对象、推荐拟任人选' AFTER `sc_type`,
	ADD COLUMN `candidate_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '推荐拟任人选，从确定的考察对象中选一人' AFTER `record_id`,
	CHANGE COLUMN `content` `content` LONGTEXT NULL COMMENT '议题内容（其他）/工作方案（动议）' AFTER `candidate_user_id`;

ALTER TABLE `sc_group`
	ADD COLUMN `record_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `attend_users`;

-- 更新sc_group_topic_view
DROP VIEW IF EXISTS `sc_group_topic_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_group_topic_view` AS
select sgt.*, sg.year, sg.hold_date, sg.file_path as group_file_path, sg.log_file, sg.attend_users, sgtu.unit_ids
 from sc_group_topic sgt
left join sc_group sg on sgt.group_id = sg.id
left join (select group_concat(unit_id) as unit_ids, topic_id from sc_group_topic_unit group by topic_id) sgtu on sgtu.topic_id=sgt.id
where sg.is_deleted=0;


UPDATE base_meta_class SET CODE='mc_sc_type' WHERE  CODE='mc_sc_motion_sctype';

-- 重新发布jx.utils-1.1.jar