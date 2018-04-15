

2018-4-15
更新 cet_trainee_view
cet_trainee_cadre_view

2018-4-13
ALTER TABLE `cet_plan_course`
	ADD CONSTRAINT `FK_cet_plan_course_cet_course` FOREIGN KEY (`course_id`) REFERENCES `cet_course` (`id`);

	ALTER TABLE `cet_plan_course_obj_result`
	ADD CONSTRAINT `FK_cet_plan_course_obj_result_cet_course_item` FOREIGN KEY (`course_item_id`) REFERENCES `cet_course_item` (`id`) ON DELETE RESTRICT;


ALTER TABLE `cet_train_course`
	ADD CONSTRAINT `FK_cet_train_course_cet_course` FOREIGN KEY (`course_id`) REFERENCES `cet_course` (`id`);

	ALTER TABLE `cet_train_course_file`
	ADD CONSTRAINT `FK_cet_train_course_file_cet_train_course` FOREIGN KEY (`train_course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_course_file`
	CHANGE COLUMN `file_name` `file_name` VARCHAR(200) NOT NULL COMMENT '学习材料名称' AFTER `course_id`,
	ADD COLUMN `has_paper` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '发放纸质学习材料' AFTER `file_path`;

ALTER TABLE `cet_plan_course`
	ADD COLUMN `need_note` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否要求上传学习心得，针对自主学习' AFTER `end_time`;

	ALTER TABLE `cet_plan_course_obj`
	ADD COLUMN `note` VARCHAR(200) NULL COMMENT '学习心得，针对自主学习' AFTER `obj_id`,
	CHANGE COLUMN `is_finished` `is_finished` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否结业，针对上级网上专题班，是否完成，针对自主学习' AFTER `num`;

2018-4-13
新建
party_school
cet_unit
cet_party
cet_party_school

cet_unit_view
cet_party_view
cet_party_school_view

cet_discuss
cet_discuss_group
cet_discuss_group_obj


2018-4-13

ALTER TABLE `sys_config`
	ADD COLUMN `apple_icon` VARCHAR(200) NULL DEFAULT NULL COMMENT 'iphone桌面图标, ICO格式，64*64' AFTER `login_top_bg_color`,
	ADD COLUMN `screen_icon` VARCHAR(200) NULL DEFAULT NULL COMMENT 'iphone桌面图标，PNG格式，234*234' AFTER `apple_icon`;

ALTER TABLE `sys_config`
	ADD COLUMN `mobile_title` VARCHAR(20) NULL DEFAULT NULL COMMENT '移动端平台title' AFTER `mobile_plantform_name`;


2018-4-10

新建 cet_plan_course
cet_plan_course_obj
cet_plan_course_obj_result

ALTER TABLE `cet_course_item`
	COMMENT='课程包含的专题班，针对上级网上专题班';

ALTER TABLE `cet_trainee_course`
	CHANGE COLUMN `choose_user_id` `choose_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '选课操作人， 如果是管理员选的， 那么显示管理员姓名； 如果是本人选的， 那么显示“本人”字样。' AFTER `choose_time`;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `word_write` VARCHAR(200) NULL COMMENT '心得体会，WORD版' AFTER `is_quit`,
	ADD COLUMN `pdf_write` VARCHAR(200) NULL COMMENT '心得体会，PDF版' AFTER `word_write`;

	更新
	cet_project_obj_view
	cet_project_obj_cadre_view


2018-4-10

更新 cet_train_course_stat_view

新建 cet_expert_view

更新 cet_train_inspector_view


ALTER TABLE `cet_project`
	ADD COLUMN `status` TINYINT NOT NULL DEFAULT '0' COMMENT '状态，0 未启动、 1 正在进行、 2 已结束' AFTER `remark`;

ALTER TABLE `cet_project`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '状态，0 未启动、 1 正在进行、 2 已结束' AFTER `remark`,
	ADD COLUMN `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发布状态，0 未发布 1 已发布  2 取消发布' AFTER `status`;

更新 cet_project_view


ALTER TABLE `cet_course`
	CHANGE COLUMN `is_online` `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '类型，0 线下课程 1 线上课程 2 自主学习 3 实践教学 4 网上专题培训班' AFTER `year`,
	CHANGE COLUMN `name` `name` VARCHAR(200) NOT NULL COMMENT '课程名称，或自主学习名称、实践教学名称、 网上专题培训班名称' AFTER `found_date`,
	ADD COLUMN `address` VARCHAR(200) NULL COMMENT '实践教学地点，或上级单位名称（网上专题）' AFTER `name`,
	CHANGE COLUMN `has_summary` `has_summary` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否提交了课程要点，针对线下和线上课程' AFTER `address`,
	CHANGE COLUMN `summary` `summary` TEXT NULL COMMENT '课程要点，针对线下和线上课程' AFTER `has_summary`,
	CHANGE COLUMN `expert_id` `expert_id` INT(10) UNSIGNED NULL COMMENT '主讲人，针对线下和线上课程' AFTER `summary`,
	CHANGE COLUMN `teach_method` `teach_method` INT(10) UNSIGNED NULL COMMENT '授课方式，关联元数据，针对线下和线上课程' AFTER `expert_id`,
	CHANGE COLUMN `period` `period` DECIMAL(10,1) NOT NULL COMMENT '学时，上级网上专题班时设置为0' AFTER `teach_method`,
	CHANGE COLUMN `duration` `duration` DECIMAL(10,1) NULL DEFAULT NULL COMMENT '时长，针对线上课程' AFTER `period`,
	CHANGE COLUMN `course_type_id` `course_type_id` INT(10) UNSIGNED NULL COMMENT '专题分类，针对线下和线上课程' AFTER `duration`,
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，每种类型内部的排序' AFTER `course_type_id`;

ALTER TABLE `cet_course`
	DROP INDEX `year_is_online_num`,
	ADD UNIQUE INDEX `year_type_num` (`year`, `type`, `num`);

	ALTER TABLE `cet_train_course`
	COMMENT='培训班课程',
	CHANGE COLUMN `course_id` `course_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属课程，允许为空（不从课程中心关联，直接创建课程）' AFTER `train_id`,
	CHANGE COLUMN `name` `name` VARCHAR(50) NULL DEFAULT NULL COMMENT '名称，直接创建的课程名称（包括专题班测评名称）' AFTER `course_id`,
	CHANGE COLUMN `teacher` `teacher` VARCHAR(50) NULL DEFAULT NULL COMMENT '教师名称，直接创建课程时设置的教师名称' AFTER `name`;

新建 cet_course_file
cet_course_item

cet_train_course_file



2018-4-8
更新 cet_train_view

ALTER TABLE `cet_trainee_course`
	ADD COLUMN `choose_user_id` INT NULL DEFAULT NULL COMMENT '选课操作人， 如果是管理员选的， 那么显示管理员姓名； 如果是本人选的， 那么显示“本人”字样。' AFTER `choose_time`;

更新 cet_trainee_course_view
cet_trainee_course_cadre_view

cet_train_inspector_view

cet_train_course_stat_view


2018-4-6
ALTER TABLE `cet_special`
	COMMENT='培训计划，包含专题培训和年度培训',
	ADD COLUMN `type` TINYINT UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 年度培训' AFTER `id`;

RENAME TABLE `cet_special` TO `cet_project`;

RENAME TABLE `cet_special_plan` TO `cet_project_plan`;

ALTER TABLE `cet_project_plan`
	DROP FOREIGN KEY `FK_cet_special_plan_cet_special`;

	ALTER TABLE `cet_project_plan`
	DROP INDEX `FK_cet_special_plan_cet_special`;

	ALTER TABLE `cet_project_plan`
	CHANGE COLUMN `special_id` `project_id` INT(10) UNSIGNED NOT NULL COMMENT '所属培训计划' AFTER `id`;

	ALTER TABLE `cet_project_plan`
	ADD CONSTRAINT `FK_cet_project_plan_cet_project` FOREIGN KEY (`project_id`) REFERENCES `cet_project` (`id`) ON DELETE CASCADE;

delete from cet_train_trainee_type;

ALTER TABLE `cet_train_trainee_type`
	COMMENT='培训计划包含的参训人员类型';
RENAME TABLE `cet_train_trainee_type` TO `cet_project_trainee_type`;

ALTER TABLE `cet_project_trainee_type`
	DROP INDEX `FK_cet_train_trainee_type_cet_train`,
	DROP FOREIGN KEY `FK_cet_train_trainee_type_cet_train`;

	ALTER TABLE `cet_project_trainee_type`
	CHANGE COLUMN `train_id` `project_id` INT(10) UNSIGNED NOT NULL COMMENT '培训计划' AFTER `id`,
	ADD CONSTRAINT `FK_cet_project_trainee_type_cet_project` FOREIGN KEY (`project_id`) REFERENCES `cet_project` (`id`) ON DELETE CASCADE;

drop view cet_special_view;
drop view cet_special_obj_cadre_view;
drop table cet_special_obj;



ALTER TABLE `cet_train`
	DROP COLUMN `year`,
	DROP COLUMN `num`,
	DROP INDEX `year_type_num`;

	ALTER TABLE `cet_train`
	ADD COLUMN `project_id` INT(10) UNSIGNED NULL COMMENT '所属培训计划，针对校内培训' AFTER `id`,
	DROP COLUMN `template_id`,
	ADD CONSTRAINT `FK_cet_train_cet_project` FOREIGN KEY (`project_id`) REFERENCES `cet_project` (`id`) ON DELETE CASCADE;



创建 cet_project_obj
cet_project_obj_cadre_view

在网页上删除所有的可选课人员
-- delete from cet_trainee;

ALTER TABLE `cet_trainee`
	COMMENT='已选课人员';

ALTER TABLE `cet_trainee`
	CHANGE COLUMN `user_id` `obj_id` INT(10) UNSIGNED NOT NULL COMMENT '培训对象' AFTER `train_id`,
	DROP COLUMN `trainee_type_id`,
	DROP INDEX `train_id_user_id`,
	ADD UNIQUE INDEX `train_id_obj_id` (`train_id`, `obj_id`),
	ADD CONSTRAINT `FK_cet_trainee_cet_project_obj` FOREIGN KEY (`obj_id`) REFERENCES `cet_project_obj` (`id`) ON DELETE CASCADE;

	更新 cet_trainee_cadre_view



更新 cet_project_view

ALTER TABLE `cet_train`
	CHANGE COLUMN `project_id` `plan_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属培训方案，针对校内培训' AFTER `id`,
	DROP INDEX `FK_cet_train_cet_project`,
	DROP FOREIGN KEY `FK_cet_train_cet_project`,
	ADD CONSTRAINT `FK_cet_train_cet_project_plan` FOREIGN KEY (`plan_id`) REFERENCES `cet_project_plan` (`id`) ON DELETE CASCADE;

	更新 cet_train_view

ALTER TABLE `cet_trainee_course`
	ADD COLUMN `can_quit` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否允许退课，由管理员设置的必选课程，不允许退课' AFTER `train_course_id`;

	更新 cet_trainee_course_view


更新 cet_trainee_cadre_view

     cet_trainee_course_view


新建 cet_trainee_course_cadre_view
cet_project_obj_view

更新
cet_project_obj_cadre_view

2018-4-2

ALTER TABLE `sys_log`
	CHANGE COLUMN `id` `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID' FIRST;

ALTER TABLE `sys_resource`
	DROP FOREIGN KEY `FK_sys_resource_sys_resource`;

	ALTER TABLE `sys_resource`
	CHANGE COLUMN `id` `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID' FIRST,
	CHANGE COLUMN `parent_id` `parent_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '上级资源' AFTER `url`;

	ALTER TABLE `sys_resource`
	ADD CONSTRAINT `FK_sys_resource_sys_resource` FOREIGN KEY (`parent_id`) REFERENCES `sys_resource` (`id`) ON DELETE CASCADE;

ALTER TABLE `sys_resource`
	CHANGE COLUMN `parent_id` `parent_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上级资源，null时是顶级节点' AFTER `url`;

ALTER TABLE `sys_role`
	CHANGE COLUMN `id` `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID' FIRST;

ALTER TABLE `sys_resource`
	ADD COLUMN `is_mobile` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否移动端' AFTER `id`;

	ALTER TABLE `sys_role`
	CHANGE COLUMN `resource_ids` `resource_ids` TEXT NULL COMMENT '拥有的资源，网页端' AFTER `description`,
	ADD COLUMN `m_resource_ids` TEXT NULL COMMENT '拥有的资源，移动端' AFTER `resource_ids`;

ALTER TABLE `sys_resource`
	DROP INDEX `url`;

update sys_resource set url = null where url='';

select url, count(*) from sys_resource group by url having(count(*))>1;

UPDATE sys_resource SET `url`=NULL WHERE  `id`=453;

ALTER TABLE `sys_resource`
	ADD UNIQUE INDEX `url` (`url`);

UPDATE `db_owip`.`sys_resource` SET `permission`=NULL WHERE  `id`=1;

2018-3-30
ALTER TABLE `crs_applicant`
	ADD CONSTRAINT `FK_crs_applicant_crs_post` FOREIGN KEY (`post_id`) REFERENCES `crs_post` (`id`) ON DELETE CASCADE;

	ALTER TABLE `crs_applicant_check`
	ADD CONSTRAINT `FK_crs_applicant_check_crs_applicant` FOREIGN KEY (`applicant_id`) REFERENCES `crs_applicant` (`id`) ON DELETE CASCADE;

ALTER TABLE `crs_candidate`
	ADD CONSTRAINT `FK_crs_candidate_crs_post` FOREIGN KEY (`post_id`) REFERENCES `crs_post` (`id`) ON DELETE CASCADE;

	ALTER TABLE `crs_post_expert`
	ADD CONSTRAINT `FK_crs_post_expert_crs_post` FOREIGN KEY (`post_id`) REFERENCES `crs_post` (`id`) ON DELETE CASCADE;

	ALTER TABLE `crs_post_file`
	ADD CONSTRAINT `FK_crs_post_file_crs_post` FOREIGN KEY (`post_id`) REFERENCES `crs_post` (`id`) ON DELETE CASCADE;

	ALTER TABLE `crs_require_rule`
	ADD CONSTRAINT `FK_crs_require_rule_crs_post_require` FOREIGN KEY (`post_require_id`) REFERENCES `crs_post_require` (`id`);

	ALTER TABLE `crs_rule_item`
	ADD CONSTRAINT `FK_crs_rule_item_crs_require_rule` FOREIGN KEY (`require_rule_id`) REFERENCES `crs_require_rule` (`id`);

	ALTER TABLE `crs_short_msg`
	ADD CONSTRAINT `FK_crs_short_msg_crs_post` FOREIGN KEY (`post_id`) REFERENCES `crs_post` (`id`) ON DELETE CASCADE;


2018-3-29
更新 sc_letter_reply_view
sc_letter_reply_item_view



============更新北化工=========
2018-3-21
ALTER TABLE `cet_trainee`
	ADD COLUMN `is_quit` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否退出培训班' AFTER `trainee_type_id`;

	更新 cet_trainee_cadre_view


2018-3-20
ALTER TABLE `cet_train`
	ADD COLUMN `eva_is_closed` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否关闭评课' AFTER `pub_status`,
	ADD COLUMN `eva_close_time` DATETIME NULL DEFAULT NULL COMMENT '评课关闭时间' AFTER `eva_is_closed`;

	ALTER TABLE `cet_train`
	ADD COLUMN `eva_note` TEXT NULL COMMENT '评课说明' AFTER `pub_status`;

ALTER TABLE `cet_train`
	ADD COLUMN `eva_anonymous` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT ' 是否匿名测评，如果是实名测评，则导入学员手机号等信息关联测评账号。' AFTER `pub_status`,
	CHANGE COLUMN `eva_is_closed` `eva_closed` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否关闭评课' AFTER `eva_note`;

	ALTER TABLE `cet_train`
	ADD COLUMN `is_on_campus` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT ' 是否校内培训，校外培训以上3个字段为空' AFTER `num`;

ALTER TABLE `cet_train`
	CHANGE COLUMN `year` `year` INT(10) UNSIGNED NULL COMMENT '年度' AFTER `id`,
	CHANGE COLUMN `type` `type` INT(10) UNSIGNED NULL COMMENT '培训班类型，关联元数据' AFTER `year`,
	CHANGE COLUMN `num` `num` INT(10) UNSIGNED NULL COMMENT '编号' AFTER `type`;

ALTER TABLE `cet_train`
	CHANGE COLUMN `is_finished` `is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0 未结课 1 已结课，校外培训无此字段' AFTER `is_deleted`;


ALTER TABLE `cet_train_course`
	CHANGE COLUMN `course_id` `course_id` INT(10) UNSIGNED NULL COMMENT '课程，针对校内培训' AFTER `train_id`,
	ADD COLUMN `name` VARCHAR(50) NULL COMMENT '名称，针对校外培训、整个培训班测评' AFTER `course_id`,
	ADD COLUMN `teacher` VARCHAR(50) NULL DEFAULT NULL COMMENT '教师名称，针对校外培训' AFTER `name`,
	ADD COLUMN `is_global` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否针对专题班测评，如果是，则评课针对整个培训班次，而不是某个课程；此时没有教师名称。' AFTER `end_time`,
	ADD COLUMN `eva_table_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '评估表' AFTER `is_global`;


ALTER TABLE `cet_train`
	CHANGE COLUMN `name` `name` VARCHAR(50) NOT NULL COMMENT '培训班名称' AFTER `is_on_campus`,
	CHANGE COLUMN `template_id` `template_id` INT(10) UNSIGNED NULL COMMENT '参训人类型信息模板，针对校内培训' AFTER `summary`,
	CHANGE COLUMN `enroll_status` `enroll_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '选课状态，0 根据选课时间而定 1 强制开启、2 强制关闭、3 暂停' AFTER `end_time`,
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发布状态，0 未发布 1 已发布  2 取消发布' AFTER `enroll_status`;

ALTER TABLE `cet_train`
	ADD COLUMN `eva_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '参评人数量' AFTER `pub_status`;


更新cet_train_view

insert into cet_train(id, is_on_campus, name, has_summary, summary, start_date, end_date, eva_count, eva_anonymous, eva_note, eva_closed, eva_close_time, remark, is_deleted, create_time)
select id, 0 as is_on_campus, name, (length(summary)>0) as has_summary, summary, start_date, end_date, total_count as eva_count,
is_anonymous as eva_anonymous , note as eva_note, is_closed as eva_closed, close_time as eva_close_time, remark, !status as is_deleted, create_time
from train;

insert into cet_train_course(id, train_id, name, teacher, start_time, end_time, is_global, eva_table_id, sort_order)
select id, train_id, name, teacher, start_time, end_time, is_global, eva_table_id, sort_order from train_course where status=1;

RENAME TABLE `train_eva_table` TO `cet_train_eva_table`;
RENAME TABLE `train_eva_norm` TO `cet_train_eva_norm`;
RENAME TABLE `train_eva_rank` TO `cet_train_eva_rank`;
RENAME TABLE `train_eva_result` TO `cet_train_eva_result`;
RENAME TABLE `train_inspector` TO `cet_train_inspector`;
RENAME TABLE `train_inspector_course` TO `cet_train_inspector_course`;

ALTER TABLE `cet_train_eva_result`
	ADD CONSTRAINT `FK_train_eva_result_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE,
	ADD CONSTRAINT `FK_train_eva_result_train_course` FOREIGN KEY (`course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_train_inspector`
	ADD CONSTRAINT `FK_train_inspector_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_train_inspector_course`
	ADD CONSTRAINT `FK_train_inspector_course_train_course` FOREIGN KEY (`course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;

ALTER TABLE `cet_train_inspector_course`
	CHANGE COLUMN `course_id` `train_course_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训课程' AFTER `inspector_id`;


ALTER TABLE `cet_train_inspector`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，（0可用、1作废）' AFTER `create_time`,
	DROP COLUMN `finish_course_num`;

ALTER TABLE `cet_train_eva_result`
	CHANGE COLUMN `course_id` `train_course_id` INT(10) UNSIGNED NOT NULL COMMENT '培训课程' AFTER `train_id`;

更新 cet_train_course_view

2018-3-18
ALTER TABLE `cet_trainee`
	DROP COLUMN `course_count`;

	更新cet_trainee_cadre_view

ALTER TABLE `cet_trainee_course`
	CHANGE COLUMN `is_finished` `is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否实际完成，签到即完成' AFTER `train_course_id`,
	ADD COLUMN `sign_time` DATETIME NULL COMMENT '签到时间' AFTER `is_finished`,
	ADD COLUMN `sign_type` TINYINT(3) UNSIGNED NULL COMMENT '签到类型， 1 手动签到 2 批量导入 3 刷卡签到' AFTER `sign_time`;

更新 cet_trainee_course_view

2018-3-16
ALTER TABLE `cet_train`
	CHANGE COLUMN `subject` `summary` TEXT NULL DEFAULT NULL COMMENT '内容简介' AFTER `name`;
ALTER TABLE `cet_train`
	ADD COLUMN `has_summary` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否提交了内容简介' AFTER `name`;
更新cet_train_view

ALTER TABLE `cet_trainee`
	ADD CONSTRAINT `FK_cet_trainee_cet_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE;
ALTER TABLE `cet_trainee_course`
	ADD CONSTRAINT `FK_cet_trainee_course_cet_trainee` FOREIGN KEY (`trainee_id`) REFERENCES `cet_trainee` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_train_course`
	ADD CONSTRAINT `FK_cet_train_course_cet_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_train_trainee_type`
	ADD CONSTRAINT `FK_cet_train_trainee_type_cet_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE;


ALTER TABLE `cet_trainee_course`
	ADD CONSTRAINT `FK_cet_trainee_course_cet_train_course` FOREIGN KEY (`train_course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cet_train_course`
	DROP COLUMN `status`;

ALTER TABLE `cet_train_course`
	DROP COLUMN `trainee_count`;


2018-3-14
ALTER TABLE `cet_train`
	ADD COLUMN `is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0 未结课 1 已结课' AFTER `is_deleted`;

更新cet_train_view

ALTER TABLE `cet_train_course`
	ADD UNIQUE INDEX `train_id_course_id` (`train_id`, `course_id`);

update cet_train_course set sort_order = id;


2018-3-14
ALTER TABLE `cet_trainee`
	ADD COLUMN `trainee_type_id` INT(10) UNSIGNED NOT NULL COMMENT '参训人员类型' AFTER `user_id`;

ALTER TABLE `cet_trainee`
	CHANGE COLUMN `course_count` `course_count` INT(10) UNSIGNED NULL COMMENT '已选课程数量' AFTER `trainee_type_id`;


update cet_trainee  set course_count=null;

update sys_role  set sort_order=id;

update sys_log set type_id=80 where type_id=400;

update sys_log set type_id=70 where type_id=399;

update sys_log set type_id=20 where type_id=51;

update sys_log set type_id=50 where type_id=28;

update sys_log set type_id=10 where type_id=27;

update sys_log set type_id=10 where type_id=26;

update sys_log set type_id=30 where type_id=143;

update sys_log set type_id=60 where type_id=144;

update sys_log set type_id=40 where type_id=145;

update sys_log set type_id=40 where type_id=146;

update sys_log set type_id=90 where type_id=401;


2018-3-13
ALTER TABLE `cet_course`
	ADD COLUMN `year` INT(10) UNSIGNED NOT NULL COMMENT '年份，设立时间的年份' AFTER `id`,
	ADD COLUMN `num` INT(10) UNSIGNED NOT NULL COMMENT '编号' AFTER `is_online`,
	ADD UNIQUE INDEX `year_is_online_num` (`year`, `is_online`, `num`);

	ALTER TABLE `cet_course`
	ADD COLUMN `has_summary` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否提交了课程要点' AFTER `name`,
	ADD COLUMN `summary` TEXT NULL COMMENT '课程要点' AFTER `has_summary`,
	ADD COLUMN `teach_method` INT(10) UNSIGNED NOT NULL COMMENT '授课方式，关联元数据' AFTER `expert_id`;

ALTER TABLE `cet_trainee_type`
	ADD COLUMN `code` VARCHAR(20) NOT NULL COMMENT '类型代码' AFTER `name`;


2018-3-12

ALTER TABLE `cet_course`
	CHANGE COLUMN `duration` `duration` DECIMAL(10,1) NULL COMMENT '时长' AFTER `period`;

ALTER TABLE `cet_column_course`
	ADD CONSTRAINT `FK_cet_column_course_cet_course` FOREIGN KEY (`course_id`) REFERENCES `cet_course` (`id`) ON DELETE CASCADE;

ALTER TABLE `cet_column`
	DROP COLUMN `child_num`,
	DROP COLUMN `course_num`;

ALTER TABLE `cet_column`
	ADD CONSTRAINT `FK_cet_column_cet_column` FOREIGN KEY (`fid`) REFERENCES `cet_column` (`id`) ON DELETE CASCADE;


-- 元数据升序
set @maxSortOrder = (select max(sort_order) from base_meta_type);
update base_meta_type set sort_order = (@maxSortOrder - sort_order)+1;


ALTER TABLE `cet_train`
	ADD COLUMN `type` INT(10) UNSIGNED NOT NULL COMMENT '培训班类型，关联元数据' AFTER `year`;

ALTER TABLE `cet_train`
	ADD UNIQUE INDEX `year_type_num` (`year`, `type`, `num`);


2018-3-9
ALTER TABLE `cet_course`
	COMMENT='课程中心',
	ADD COLUMN `is_online` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否线上课程' AFTER `id`,
	ADD COLUMN `duration` DECIMAL(10,1) NOT NULL COMMENT '时长' AFTER `period`;
ALTER TABLE `cet_course`
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，线下或线上内的排序' AFTER `course_type_id`;


2018-3-6
ALTER TABLE `pmd_member`
	ADD COLUMN `is_online_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费方式，1 线上缴费 0 现金缴费' AFTER `has_pay`,
	CHANGE COLUMN `is_self_pay` `is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '线上缴费途径， 1 线上缴费、0 代缴党费' AFTER `is_online_pay`;

ALTER TABLE `pmd_member_pay`
	ADD COLUMN `is_online_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费方式，1 线上缴费 0 现金缴费' AFTER `has_pay`,
	CHANGE COLUMN `is_self_pay` `is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '线上缴费途径， 1 线上缴费、0 代缴党费' AFTER `is_online_pay`;

ALTER TABLE `pmd_config_member`
	ADD COLUMN `is_online_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费方式，1 线上缴费 0 现金缴费 ， 缴费额度没设置前，不允许设置为现金缴费' AFTER `config_member_type_id`;

更新 pmd_member_pay_view

2018-2-16
ALTER TABLE `dispatch`
	ADD COLUMN `sc_dispatch_id` INT(10) UNSIGNED NULL COMMENT '文件签发稿' AFTER `id`;

2018-2-4
ALTER TABLE `cadre`
	ADD COLUMN `is_committee_member` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否常委' AFTER `unit_id`;


	更新 cadre_view

2018-1-25
更新 sc_matter_check_view

ALTER TABLE `sc_matter_check`
	ADD UNIQUE INDEX `year_num` (`year`, `num`);

insert into pmd_order_campuscard(sn, member_id, payer, payername, amt, user_id, is_success, is_closed)
select pmp.order_no as sn, pmp.member_id, u.code as payer, u.realname as payername,pm.due_pay as amt,
pmp.order_user_id as user_id, pmp.has_pay as is_success, 0 as is_closed from pmd_member_pay pmp
left join pmd_member pm on pmp.member_id=pm.id
left join sys_user_view u on pmp.order_user_id=u.id
 where pmp.order_no is not null and pmp.order_no not in(select sn from pmd_order_campuscard);

2018-1-19
ALTER TABLE `ow_member_out`
	DROP INDEX `user_id`;


2018-1-18
ALTER TABLE `pmd_order_campuscard`
	ADD COLUMN `is_closed` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否关闭了订单' AFTER `is_success`;


2018-1-15
ALTER TABLE `ow_member_apply`
	ADD COLUMN `is_remove` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否移除，（针对未发展的申请，可以移除）' AFTER `positive_status`;

	更新 ow_member_apply_view

2018-1-13
RENAME TABLE `pmd_notify_campuscard_log` TO `pmd_notify_campuscard`;
RENAME TABLE `pmd_notify_wszf_log` TO `pmd_notify_wszf`;

ALTER TABLE `pmd_member_pay`
	CHANGE COLUMN `order_no` `order_no` VARCHAR(20) NULL DEFAULT NULL COMMENT '缴费订单号' AFTER `member_id`,
	CHANGE COLUMN `order_user_id` `order_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '生成订单号时的账号' AFTER `order_no`;



2018-1-11
修改 ct_pmd_notify -> ct_pmd_notify_member


2018-1-10
ALTER TABLE `ext_jzg_salary`
	CHANGE COLUMN `rq` `rq` VARCHAR(20) NULL DEFAULT NULL COMMENT '日期， 格式yyyyMM' AFTER `zgh`,
	ADD UNIQUE INDEX `zgh_rq` (`zgh`, `rq`);

ALTER TABLE `ext_retire_salary`
	CHANGE COLUMN `rq` `rq` VARCHAR(10) NULL DEFAULT NULL COMMENT '日期， 格式yyyyMM' AFTER `zgh`,
	ADD UNIQUE INDEX `zgh_rq` (`zgh`, `rq`);


2018-1-8
ALTER TABLE `pmd_config_member`
	CHANGE COLUMN `has_set_salary` `has_reset` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否进行了党费重新计算，只针对支部确定的情况，组织部管理员进行党费重新计算时重置为0' AFTER `has_salary`,
	DROP COLUMN `is_self_set_salary`;

ALTER TABLE `pmd_config_member`
	CHANGE COLUMN `gwgz` `gwgz` DECIMAL(10,2) UNSIGNED NULL DEFAULT NULL COMMENT '岗位工资，（校聘工资）' AFTER `retire_salary`;

ALTER TABLE `pmd_config_member`
	ADD COLUMN `has_set_salary` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否提交了工资明细，（针对在职、校聘教职工)' AFTER `has_reset`;



2017-12-15
ALTER TABLE `pmd_config_member`
	ADD COLUMN `is_self_set_salary` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否本人设置或修改过工资，支部管理员可代替设置工资，规则：如果本人设置或修改过了，则支部管理员不允许设置或修改' AFTER `has_set_salary`;

ALTER TABLE `pmd_member_pay`
	ADD COLUMN `order_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '生成订单号时的账号，校园卡要求不允许更换人支付' AFTER `order_no`;

	更新 pmd_member_pay_view

update pmd_config_member set is_self_set_salary=has_set_salary;

update pmd_member_pay pmp, pmd_member pm set pmp.order_user_id=pm.user_id where pmp.has_pay=1 and pmp.member_id=pm.id;

update pmd_member_pay set order_user_id = charge_user_id where charge_user_id is not null;

ALTER TABLE `pmd_party`
	CHANGE COLUMN `branch_count` `branch_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党支部数，党委报送后计算' AFTER `has_report`;

2017-12-14
ALTER TABLE `pmd_member`
	CHANGE COLUMN `is_online_pay` `is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '缴费方式， 1 线上缴费、0 代缴党费' AFTER `has_pay`,
	CHANGE COLUMN `charge_user_id` `charge_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '收款人，代缴党费时的操作人' AFTER `pay_time`;

ALTER TABLE `pmd_member_pay`
	CHANGE COLUMN `is_online_pay` `is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '缴费方式， 1 线上缴费、0 代缴党费' AFTER `has_pay`,
	CHANGE COLUMN `charge_user_id` `charge_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '收款人，代缴党费时的操作人' AFTER `pay_time`;

	更新 pmd_member_pay_view

2017-12-10
ALTER TABLE `pmd_norm`
	CHANGE COLUMN `set_type` `set_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '额度设定类型，（1-3属于减免标准 4-6属于缴纳标准） 1 统一标准  2 基层党委设定 3 免交  4 公式 5 固定额度 6 支部确定 ' AFTER `name`,
	ADD COLUMN `formula_type` TINYINT(3) UNSIGNED NULL COMMENT '公式类别， 1 在职在编教职工 2 校聘教职工 3 离退休教职工' AFTER `set_type`;

	ALTER TABLE `pmd_norm_value`
	COMMENT='统一标准或固定额度';

	ALTER TABLE `pmd_norm`
	COMMENT='收费标准，包括党费计算方法和党费减免标准两大类',
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型， 1 党费计算方法 2 党费减免标准' AFTER `id`,
	CHANGE COLUMN `set_type` `set_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '额度设定类型，（1-3属于减免标准 4-6属于党费计算方法） 1 统一标准  2 基层党委设定 3 免交  4 公式 5 固定额度 6 支部确定 ' AFTER `name`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `norm_id` `norm_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '标准ID， 通过选择标准设定额度时使用; 如果标准的类型是"支部确定"，则此时需要直接编辑额度' AFTER `salary`,
	CHANGE COLUMN `norm_value_id` `norm_value_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '标准对应的固定额度ID，标准的类型为”统一标准或固定额度“时使用' AFTER `norm_id`,
	DROP COLUMN `norm_type`,
	DROP COLUMN `norm_name`,
	DROP COLUMN `norm_due_pay`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '党员类别，1 在职教职工 2 离退休教职工 3 学生 4 附属学校' AFTER `branch_id`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '党员类别，1 在职教职工 2 离退休教职工 3 学生 4 附属学校， 系统自动设定' AFTER `branch_id`,
	ADD COLUMN `config_member_type_id` INT UNSIGNED NULL COMMENT '党员分类别，系统自动设定或支部确定，在没有确定之前，不允许设定额度、减免及缴费操作' AFTER `type`,
	ADD COLUMN `config_member_type_name` TINYINT(3) UNSIGNED NULL COMMENT '党员分类别名称' AFTER `config_member_type_id`,
	ADD COLUMN `config_member_type_norm_id` INT UNSIGNED NULL COMMENT '党员分类别对应的党费计算方法' AFTER `config_member_type_name`,
	ADD COLUMN `config_member_type_norm_name` INT UNSIGNED NULL COMMENT '党员分类别对应的党费计算方法名称' AFTER `config_member_type_norm_id`,
	ADD COLUMN `config_member_due_pay` DECIMAL(10,2) UNSIGNED NULL COMMENT '缴费额度，同步表config_member的due_pay，只有缴费额度确定之后，才允许进行减免、缴费操作' AFTER `config_member_type_norm_name`,
	ADD COLUMN `due_pay_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '党费缴纳标准类别，1 公式  2 固定额度 3 支部确定，在确定党员分类别时进行判断' AFTER `config_member_due_pay`,
	CHANGE COLUMN `norm_id` `norm_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '减免标准ID， 通过选择减免标准设定额度时使用' AFTER `salary`,
	CHANGE COLUMN `norm_value_id` `norm_value_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '减免标准对应的固定额度ID，标准的类型为”统一标准“时使用' AFTER `norm_id`,
	CHANGE COLUMN `norm_display_name` `due_pay_reason` VARCHAR(50) NULL DEFAULT NULL COMMENT '党费缴纳标准，来源：党员分类别或支部选择的减免标准的名称，展示给管理员' AFTER `norm_value_id`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `config_member_type_name` `config_member_type_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '党员分类别名称' AFTER `config_member_type_id`,
	CHANGE COLUMN `config_member_type_norm_name` `config_member_type_norm_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '党员分类别对应的党费计算方法名称' AFTER `config_member_type_norm_id`,
	CHANGE COLUMN `due_pay_type` `due_pay_type` TINYINT(3) UNSIGNED NULL COMMENT '党费缴纳标准类别，1 公式  2 固定额度 3 支部确定，在确定党员分类别时进行判断' AFTER `config_member_due_pay`;

ALTER TABLE `pmd_norm`
	ADD UNIQUE INDEX `formula_type` (`formula_type`);

ALTER TABLE `pmd_member`
	CHANGE COLUMN `has_salary` `has_salary` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否带薪就读，支部确定学生的分类别时赋值' AFTER `due_pay_type`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `due_pay_type` `due_pay_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '党费缴纳标准类别，1 公式  2 额度已存在 3 支部确定，在确定党员分类别时进行判断' AFTER `config_member_due_pay`;


	ALTER TABLE `pmd_member`
	ADD COLUMN `need_set_salary` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否需要本人提交工资明细，如果本人已提交了明细，则不需要' AFTER `config_member_due_pay`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `need_set_salary` `need_set_salary` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否需要本人提交工资明细，如果是A1、A2类别的党员还没提交工资明细则需要，否则不需要' AFTER `config_member_due_pay`,
	DROP COLUMN `due_pay_type`;

ALTER TABLE `pmd_member`
	CHANGE COLUMN `config_member_due_pay` `config_member_due_pay` DECIMAL(10,2) UNSIGNED NULL DEFAULT NULL COMMENT '已设置的缴费额度，同步表config_member的due_pay' AFTER `config_member_type_norm_name`;

ALTER TABLE `pmd_notify_log`
	COMMENT='校园统一支付平台支付通知';
RENAME TABLE `pmd_notify_log` TO `pmd_notify_wszf_log`;


2017-11-21
ALTER TABLE `pcs_config`
	ADD COLUMN `committee_can_select` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '两委选举计票时，是否可以选择部分预备人选，否则同步全部预备人选' AFTER `jw_back_vote`;


2017-11-20
ALTER TABLE `pcs_config`
	ADD COLUMN `committee_join_count` INT(10) UNSIGNED NULL COMMENT '两委选举实到会的代表人数' AFTER `proposal_support_count`,
	ADD COLUMN `dw_send_vote` INT(10) UNSIGNED NULL COMMENT '发出党委委员选票数，两委选举' AFTER `committee_join_count`,
	ADD COLUMN `jw_send_vote` INT(10) UNSIGNED NULL COMMENT '发出纪委委员选票数，两委选举' AFTER `dw_send_vote`,
	ADD COLUMN `dw_back_vote` INT(10) UNSIGNED NULL COMMENT '收回党委委员选票数，两委选举' AFTER `jw_send_vote`,
	ADD COLUMN `jw_back_vote` INT(10) UNSIGNED NULL COMMENT '收回党委委员选票数，两委选举' AFTER `dw_back_vote`;


2017-11-18
ALTER TABLE `pmd_member`
	ADD COLUMN `pro_post_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '专技岗位等级' AFTER `main_post_level`,
	ADD COLUMN `manage_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '管理岗位等级' AFTER `pro_post_level`,
	ADD COLUMN `office_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '工勤岗位等级' AFTER `manage_level`;



2017-11-17
update sys_resource set url='/oa/oaTask' where url='/oaTask';

增加 mt_log_pmd

ALTER TABLE `pcs_admin`
	COMMENT='党代会分党委管理员，系统提供同步书记、副书记为管理员的功能';

组织委员： mt_branch_commissary
mt_party_member_type_zzwy


ALTER TABLE `ext_jzg`
	ADD COLUMN `gwlx` VARCHAR(100) NULL DEFAULT NULL COMMENT '岗位类型' AFTER `lxrq`;



2017-11-06
ALTER TABLE `cadre_leader_unit`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `type_id`;

update  cadre_leader_unit set sort_order=id;

2017-11-04
增加mt_log_oa

增加角色 role_oa_user

INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (560, '协同办公管理', '', 'menu', 'fa fa-life-bouy', '', 1, '0/1/', 0, 'oa:menu', NULL, NULL, 1, 3000);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (561, '任务列表', '', 'url', '', '/oaTask', 560, '0/1/560/', 0, 'oaTask:*', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (562, '我的任务列表', '', 'url', '', '/user/oa/oaTask', 560, '0/1/560/', 1, 'userOaTask:*', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (563, '任务对象管理', '', 'function', '', '', 561, '0/1/560/561/', 1, 'oaTaskUser:*', NULL, NULL, 1, NULL);


2017-10-31
更新 ow_party_static_view

增加 mt_log_pcs

2017-10-29
dispatchWorkFile:list > dispatchWorkFile:cadreList

INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (550, '党建工作文件', '', 'menu', '', '', 61, '0/1/61/', 0, 'dispatchWorkFile:owList', NULL, NULL, 1, 200);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (552, '工作文件列表', '', 'function', '', '', 61, '0/1/61/', 1, 'dispatchWorkFile:list', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (553, '专题教育活动', '', 'url', '', '/dispatchWorkFile?type=11', 550, '0/1/61/550/', 1, 'dispatchWorkFile:list:11', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (554, '参数设置', '', 'menu', '', '', 61, '0/1/61/', 0, 'dispatchWorkFile:workType', NULL, NULL, 1, 100);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (555, '干部专项工作', '', 'url', '', '/dispatchWorkFile_workType?type=1', 554, '0/1/61/554/', 1, 'dispatchWorkFile:workType:cadre', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (556, '党建专项工作', '', 'url', '', '/dispatchWorkFile_workType?type=2', 554, '0/1/61/554/', 1, 'dispatchWorkFile:workType:ow', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (557, '基层党组织建设', '', 'url', '', '/dispatchWorkFile?type=12', 550, '0/1/61/550/', 1, 'dispatchWorkFile:list:12', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (558, '党员队伍建设', '', 'url', '', '/dispatchWorkFile?type=13', 550, '0/1/61/550/', 1, 'dispatchWorkFile:list:13', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (559, '党内民主建设', '', 'url', '', '/dispatchWorkFile?type=14', 550, '0/1/61/550/', 1, 'dispatchWorkFile:list:14', NULL, NULL, 1, NULL);

2017-10-26
ALTER TABLE `crs_post`
	DROP COLUMN `stat_first_user_id`,
	DROP COLUMN `stat_second_user_id`;

	创建表 crs_candidate

更新 cadre_view

	创建 crs_candidate_view

ALTER TABLE `crs_applicant`
	CHANGE COLUMN `ppt_name` `ppt_name` VARCHAR(200) NULL DEFAULT NULL COMMENT 'PPT文件名' AFTER `report`;

创建crs_applicant_stat_view


2017-10-24
ALTER TABLE `crs_post`
	ADD COLUMN `stat_first_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '排名第一的应聘人' AFTER `stat_file_name`,
	ADD COLUMN `stat_second_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '排名第二的应聘人' AFTER `stat_first_user_id`;


2017-10-18
新增角色 cadreRecruit 应聘干部（普通教师）


ALTER TABLE `crs_applicant`
	ADD COLUMN `ppt` VARCHAR(100) NULL COMMENT '应聘PPT' AFTER `report`;
ALTER TABLE `crs_applicant`
	ADD COLUMN `ppt_name` VARCHAR(100) NULL COMMENT 'PPT文件名' AFTER `report`;

	更新 crs_applicant_view

	ALTER TABLE `crs_post`
	ADD COLUMN `report_deadline` DATETIME NULL DEFAULT NULL COMMENT '报名材料修改截止时间' AFTER `meeting_address`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_notice` TEXT NULL DEFAULT NULL COMMENT '招聘会公告' AFTER `meeting_address`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_summary` TEXT NULL COMMENT '会议备忘' AFTER `status`;

ALTER TABLE `crs_post`
	DROP COLUMN `stat_expert_count`;

专家组管理 -> 专家库管理

(已处理)ALTER TABLE `cadre_paper`
	CHANGE COLUMN `name` `name` VARCHAR(500) NULL DEFAULT NULL COMMENT '论文题目' AFTER `pub_time`,
	CHANGE COLUMN `press` `press` VARCHAR(200) NULL DEFAULT NULL COMMENT '期刊名称' AFTER `name`;

(已处理)更新 pcs_party_view


2017-10-12
ALTER TABLE `crs_applicant`
	ADD COLUMN `report` TEXT NULL COMMENT '工作设想和预期目标' AFTER `post_id`;

更新 crs_applicant_view

ALTER TABLE `crs_applicant`
	COMMENT='报名人员，唯一 (user_id， post_id, status=0或1） ',
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态， 0 暂存 1 已提交 2 已删除' AFTER `recommend_second_count`;

2017-10-11
#专技岗位等级
delete from base_meta_type where class_id=41 and sort_order is not null;
update  base_meta_type set sort_order=ABS(cast(id as signed)-294)  where class_id=41;

#专业技术职务
update  base_meta_type set sort_order=ABS(cast(id as signed)-281)  where class_id=40;



2017-10-09
ALTER TABLE `crs_post`
	CHANGE COLUMN `is_publish` `pub_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '发布状态，0 未发布 1 已发布  2 取消发布' AFTER `create_time`;

update crs_applicant set require_check_status=0 where require_check_status is null;


给干部、后备干部、考察对象添加权限 cadreAdminLevel:edit

ALTER TABLE `ow_member_in`
	CHANGE COLUMN `from_phone` `from_phone` VARCHAR(50) NOT NULL COMMENT '转出单位联系电话' AFTER `from_address`,
	CHANGE COLUMN `from_fax` `from_fax` VARCHAR(50) NOT NULL COMMENT '转出单位传真' AFTER `from_phone`;

2017-10-07
ALTER TABLE `sys_config`
	ADD COLUMN `school_short_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校简称' AFTER `school_name`,
	ADD COLUMN `school_login_url` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校门户地址' AFTER `school_short_name`;
ALTER TABLE `sys_config`
	CHANGE COLUMN `login_bg` `login_bg` VARCHAR(200) NULL DEFAULT NULL COMMENT '登录页背景，分辨率1920*890，JPG' AFTER `logo_white`,
	ADD COLUMN `login_top` VARCHAR(200) NULL DEFAULT NULL COMMENT '登录页顶部图片，分辨率1102*109，JPG' AFTER `login_bg`,
	ADD COLUMN `login_top_bg_color` VARCHAR(10) NULL DEFAULT NULL COMMENT '登录页顶部背景颜色' AFTER `login_top`;
ALTER TABLE `sys_config`
	ADD COLUMN `school_email` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校邮箱' AFTER `school_login_url`;

/*update sys_user_info set health=382 where health like '%健康%' or health like '%优%'  or health like '%好%';
update sys_user_info set health=383 where health like '%良%';
update sys_user_info set health=384 where health like '%一般%';
update sys_user_info set health=385 where health like '%较差%';
update sys_user_info set health=null where health='';*/

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `health` `health` INT UNSIGNED NULL DEFAULT NULL COMMENT '健康状况' AFTER `specialty`;

更新 sys_user_view

增加 mc_health
（
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `sort_order`, `available`) VALUES (58, NULL, '健康状况', '干部', '基本信息', 'mc_health', '', '', 58, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (58, '健康', 'mt_9nqaui', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (58, '良好', 'mt_3bqauv', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (58, '一般', 'mt_y5dqel', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (58, '较差', 'mt_dn2b9y', NULL, '', '', 1, 1);
）

“健康状况”根据本人的具体情况选择“健康”、“良好”、“一般”、“较差”。

创建表 cadre_info_check
增加权限 cadreInfo:check   开放给 干部、后备干部、考察对象、	干部工作管理员（目录1）、系统管理员
（
INSERT INTO `sys_resource` (`name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES ('无此类情况', '', 'function', '', '', 90, '0/1/88/90/', 1, 'cadreInfo:check', NULL, NULL, 1, 999901);
）

ALTER TABLE `cadre_paper`
	ADD COLUMN `name` VARCHAR(100) NULL DEFAULT NULL COMMENT '论文题目' AFTER `pub_time`,
	ADD COLUMN `press` VARCHAR(100) NULL DEFAULT NULL COMMENT '期刊名称' AFTER `name`;


2017-9-20( 北化工未更新)
ALTER TABLE `pcs_config`
	ADD COLUMN `proposal_submit_time` DATETIME NULL COMMENT '提交提案时间，精确到分钟' AFTER `remark`,
	ADD COLUMN `proposal_support_time` DATETIME NULL COMMENT '征集附议人时间，精确到分钟' AFTER `proposal_submit_time`,
	ADD COLUMN `proposal_support_count` INT(10) UNSIGNED NULL COMMENT '附议人数，立案标准（学校规定，附议人达到5个予以立案）' AFTER `proposal_support_time`;

ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `is_proposal` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否进入提案党代表名单，（从三上已选择的名单中选取）' AFTER `is_chosen`;

ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `is_proposal` `is_proposal` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否进入提案阶段，（从三上已选择的名单中选取）' AFTER `is_chosen`,
	ADD COLUMN `proposal_sort_order` INT UNSIGNED NULL DEFAULT NULL COMMENT '进入提案阶段的党代表顺序，初始化为三上的顺序，升序排列' AFTER `is_proposal`;

更新 pcs_pr_candidate_view

2017-9-14
ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `meeting_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '大会类型，1 全体党员大会 2 党员代表大会' AFTER `vote_member_count`;

update pcs_pr_recommend set meeting_type=1 where stage=3;

2017-9-12
删除 pcs_branch_view2
更新 ow_branch_view  （未更新mybaits）

更新 pcs_branch_view

2017-9-12
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `realname_sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '按笔画排序， 在recommend_id下排序，针对三下三上' AFTER `sort_order`;

	更新pcs_pr_candidate_view

2017-9-7
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `is_chosen` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否选择，针对三下三上' AFTER `vote3`;

更新pcs_pr_candidate_view

2017-9-6
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `vote3` INT(10) UNSIGNED NULL COMMENT '票数，针对三下三上' AFTER `vote`;

更新pcs_pr_candidate_view

2017-9-4
delete from pcs_exclude_branch;
insert into pcs_exclude_branch(party_id, branch_id, create_time)
select party_id, id as branch_id, now() as create_time from ow_branch where is_deleted=0 and name like '%留%';

2017-9-4
ALTER TABLE `pcs_pr_recommend`
	CHANGE COLUMN `expect_member_count` `expect_member_count` INT(10) UNSIGNED NOT NULL COMMENT '应参会党员数，三下三上不填' AFTER `party_id`,
	CHANGE COLUMN `actual_member_count` `actual_member_count` INT(10) UNSIGNED NOT NULL COMMENT '实参会党员数，三下三上不填' AFTER `expect_member_count`,
	CHANGE COLUMN `expect_positive_member_count` `expect_positive_member_count` INT(10) UNSIGNED NOT NULL COMMENT '应参会正式党员数' AFTER `actual_member_count`,
	ADD COLUMN `meeting_time` DATE NULL COMMENT '党员大会选举时间，针对三下三上' AFTER `actual_positive_member_count`,
	ADD COLUMN `meeting_address` VARCHAR(200) NULL COMMENT '党员大会选举地点，针对三下三上' AFTER `meeting_time`;

ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `vote_member_count` INT(10) UNSIGNED NULL COMMENT '具有选举权的正式党员数，针对三下三上' AFTER `actual_positive_member_count`;

	ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `mobile` VARCHAR(20) NULL COMMENT '手机号，针对三下三上' AFTER `nation`,
	ADD COLUMN `email` VARCHAR(100) NULL COMMENT '邮箱，针对三下三上' AFTER `mobile`;

ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `report_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '“选举结果报告单”扫描件，针对三下三上' AFTER `meeting_address`;

更新 pcs_pr_candidate_view



2017-9-1
ALTER TABLE `pcs_admin`
	DROP COLUMN `config_id`;



2017-9-1
ALTER TABLE `pcs_admin_report`
	ADD UNIQUE INDEX `party_id_config_id_stage` (`party_id`, `config_id`, `stage`);

ALTER TABLE `pcs_recommend`
	DROP INDEX `party_id_branch_id_config_id_is_finished`;

	ALTER TABLE `pcs_recommend`
	ADD UNIQUE INDEX `party_id_branch_id_config_id_stage` (`party_id`, `branch_id`, `config_id`, `stage`);


2017-8-29
ALTER TABLE `pcs_recommend`
	DROP INDEX `party_id_branch_id_config_id`;

	ALTER TABLE `pcs_recommend`
	ADD UNIQUE INDEX `party_id_branch_id_config_id_is_finished` (`party_id`, `branch_id`, `config_id`, `is_finished`);

ow_party_view

ALTER TABLE `pcs_recommend`
	CHANGE COLUMN `stage` `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '阶段，1 一下一上 2 二下二上 3 三下三上' AFTER `is_finished`;

pcs_candidate_view

ALTER TABLE `pcs_admin`
	ADD COLUMN `remark` VARCHAR(255) NULL COMMENT '备注' AFTER `config_id`;

2017-8-29
ALTER TABLE `sys_login_log`
	ADD COLUMN `country` VARCHAR(200) NULL DEFAULT NULL COMMENT '国家' AFTER `login_ip`,
	ADD COLUMN `area` VARCHAR(200) NULL DEFAULT NULL COMMENT '地区' AFTER `country`,
	ADD COLUMN `last_country` VARCHAR(200) NULL DEFAULT NULL COMMENT '上次国家' AFTER `last_login_ip`,
	ADD COLUMN `last_area` VARCHAR(200) NULL DEFAULT NULL COMMENT '上次地区' AFTER `last_country`;

更新party_view

2017-8-24
ALTER TABLE `crs_applicant`
	ADD COLUMN `is_quit` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否退出' AFTER `enroll_time`;

	ALTER TABLE `crs_applicant`
	ADD COLUMN `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态， 1 正常 0 已删除' AFTER `recommend_second_count`;

update crs_applicant set status=1;

ALTER TABLE `crs_applicant`
	COMMENT='报名人员，唯一 (user_id， post_id, status=1） ';

更新crs_applicant_view

2017-8-18
ALTER TABLE `crs_post`
	ADD COLUMN `job` VARCHAR(200) NULL COMMENT '分管工作' AFTER `name`,
	ADD COLUMN `num` INT(10) UNSIGNED NULL COMMENT '招聘人数' AFTER `unit_id`;

ALTER TABLE `crs_post`
	ADD COLUMN `quit_deadline` DATETIME NULL DEFAULT NULL COMMENT '退出报名的最后期限' AFTER `meeting_address`;

ALTER TABLE `base_short_msg`
	ADD COLUMN `content_tpl_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联模板' AFTER `receiver_id`;
ALTER TABLE `base_short_msg`
	CHANGE COLUMN `content_tpl_id` `relate_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联模板' AFTER `receiver_id`,
	ADD COLUMN `relate_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '关联类别，1 模板 2 定向短信' AFTER `relate_id`;

update base_short_msg bsm, base_content_tpl bct set bsm.relate_id = bct.id, relate_type=1 where bsm.`type`=bct.name;

update base_short_msg bsm, base_short_msg_tpl bsmt set bsm.relate_id = bsmt.id, relate_type=2 where bsm.`type`=bsmt.name and bsm.relate_id is null;

ALTER TABLE `crs_post_expert`
	CHANGE COLUMN `remark` `remark` VARCHAR(50) NULL COMMENT '备注' AFTER `sort_order`;

ALTER TABLE `crs_post`
	ADD COLUMN `stat_expert_count` INT UNSIGNED NULL COMMENT '招聘会专家组人数，专家组推荐意见汇总' AFTER `status`,
	ADD COLUMN `stat_give_count` INT UNSIGNED NULL COMMENT '发出推荐票数量' AFTER `stat_expert_count`,
	ADD COLUMN `stat_back_count` INT UNSIGNED NULL COMMENT '收回数量' AFTER `stat_give_count`,
	ADD COLUMN `stat_file` VARCHAR(200) NULL COMMENT '表扫描件' AFTER `stat_back_count`,
	ADD COLUMN `stat_date` DATE NULL COMMENT '记录日期' AFTER `stat_file`;

	ALTER TABLE `crs_applicant`
	ADD COLUMN `recommend_first_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '推荐排第一票数' AFTER `special_remark`,
	ADD COLUMN `recommend_second_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '推荐排第二票数' AFTER `recommend_first_count`;

	ALTER TABLE `crs_post`
	ADD COLUMN `stat_file_name` VARCHAR(100) NULL DEFAULT NULL AFTER `stat_file`;



2017-8-4
update sys_resource set permission = replace(permission, 'recruit', 'crs') where permission like 'recruit%';
update sys_resource set url = replace(url, '/recruit', '/crs') where url like '/recruit%';

（以下已删除替换，不需更新）
RENAME TABLE `recruit_post` TO `crs_post`;
RENAME TABLE `recruit_template` TO `crs_template`;


ALTER TABLE `crs_post`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '招聘类型' AFTER `year`,
	ADD COLUMN `seq` INT(10) UNSIGNED NOT NULL COMMENT '编号，招聘类型+序号，eg：竞争上岗[2017]1号、公开招聘[2017]100号' AFTER `type`;
ALTER TABLE `crs_post`
	CHANGE COLUMN `sign_status` `sign_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '报名状态，1未启动报名、2正在报名、3报名结束' AFTER `qualification`;

ALTER TABLE `crs_post`
	ADD COLUMN `notice` VARCHAR(255) NULL COMMENT '招聘公告，pdf文件' AFTER `unit_id`,
	ADD COLUMN `qualification_group_id` INT UNSIGNED NULL COMMENT '任职资格组合' AFTER `qualification`;
ALTER TABLE `crs_post`
	CHANGE COLUMN `qualification_group_id` `post_require_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '岗位要求' AFTER `qualification`;

ALTER TABLE `crs_post`
	ADD COLUMN `start_time` DATETIME NULL DEFAULT NULL COMMENT '报名开启时间' AFTER `post_require_id`,
	ADD COLUMN `end_time` DATETIME NULL DEFAULT NULL COMMENT '报名关闭时间' AFTER `start_time`,
	CHANGE COLUMN `sign_status` `enroll_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名' AFTER `end_time`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_time` DATETIME NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	ADD COLUMN `meeting_address` VARCHAR(100) NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	DROP COLUMN `meeting_status`;

ALTER TABLE `crs_post`
	DROP COLUMN `meeting_time`,
	DROP COLUMN `meeting_address`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_time` DATETIME NOT NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	ADD COLUMN `meeting_address` VARCHAR(100) NOT NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	ADD COLUMN `meeting_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '招聘会是否完成' AFTER `meeting_address`;


ALTER TABLE `crs_post`
	CHANGE COLUMN `enroll_status` `enroll_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名' AFTER `end_time`,
	CHANGE COLUMN `meeting_time` `meeting_time` DATETIME NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	CHANGE COLUMN `meeting_address` `meeting_address` VARCHAR(100) NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	CHANGE COLUMN `meeting_status` `meeting_status` TINYINT(1) UNSIGNED NULL COMMENT '招聘会是否完成' AFTER `meeting_address`,
	CHANGE COLUMN `committee_status` `committee_status` TINYINT(1) UNSIGNED NULL COMMENT '常委会情况，未上会、已上会' AFTER `meeting_status`;


2017-7-24
ALTER TABLE `abroad_taiwan_record`
	CHANGE COLUMN `start_date` `start_date` DATE NOT NULL COMMENT '离境时间' AFTER `record_date`,
	CHANGE COLUMN `end_date` `end_date` DATE NOT NULL COMMENT '回国时间' AFTER `start_date`;


2017-7-24
ALTER TABLE `abroad_passport`
	ADD COLUMN `taiwan_record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '如果是因公赴台备案' AFTER `apply_id`,
	ADD CONSTRAINT `FK_abroad_passport_abroad_taiwan_record` FOREIGN KEY (`taiwan_record_id`) REFERENCES `abroad_taiwan_record` (`id`) ON DELETE RESTRICT;

ALTER TABLE `abroad_passport_draw`
	CHANGE COLUMN `use_passport` `use_passport` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '归还证件处理类别， 因私出国、因公赴台长期（1：持证件出国（境） 0：未持证件出国（境） 2：拒不交回证件） 处理其他事务（1：违规使用证件出国（境）0：没有使用证件出国（境） 2：拒不交回证件）' AFTER `attachment_filename`;

ALTER TABLE `abroad_passport`
	ADD COLUMN `pic` VARCHAR(255) NULL DEFAULT NULL COMMENT '上传证件首页' AFTER `safe_box_id`;



2017-7-11
ALTER TABLE `sys_feedback`
	ADD COLUMN `fid` INT(10) UNSIGNED NULL COMMENT '回复主题' AFTER `id`,
	ADD COLUMN `reply_count` INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '回复数量，只有fid=null时有值' AFTER `ip`;


bug:
干部->离任干部，因私中已分配申请人身份的干部，没有删除；
解决：读取审批人时，过滤干部身份（SystemConstants.ABROAD_APPLICAT_CADRE_STATUS_SET）



select aat.*, a.aac_num, b.aao_num from abroad_applicat_type aat left join (
select count(aac.cadre_id) aac_num, aac.type_id from abroad_applicat_cadre aac, cadre c where c.`status` in(1,4,6) and aac.cadre_id=c.id group by aac.type_id
)a on a.type_id = aat.id
left join(
select count(aao.id) aao_num, aao.applicat_type_id from abroad_approval_order aao group by aao.applicat_type_id
)b on b.applicat_type_id = aat.id


2017-7-5
ALTER TABLE `sys_resource`
	ADD COLUMN `is_leaf` TINYINT(1) UNSIGNED NULL COMMENT '是否叶子节点' AFTER `parent_ids`;

	select group_concat(r.id) from sys_resource r where (select count(id) from sys_resource where parent_id=r.id) =0;

	update sys_resource set is_leaf=1 where id  in(108,210,312,416,22,31,41,256,301,302,313,404,414,421,257,261,112,113,114,116,117,118,233,83,84,157,158,159,149,150,151,152,153,154,155,145,146,147,160,320,66,245,124,125,126,127,318,333,334,335,336,337,338,374,375,395,396,133,134,135,140,141,142,143,231,232,234,235,236,237,238,239,240,241,242,280,304,305,306,307,308,309,310,311,316,317,319,357,393,94,95,96,97,98,99,100,101,102,103,104,246,247,249,250,251,252,253,254,290,291,296,440,183,184,185,220,222,299,300,376,186,187,188,189,190,191,192,193,194,195,392,196,197,198,199,204,205,206,208,209,213,214,244,285,286,287,288,295,441,292,293,283,267,268,278,272,273,274,275,276,281,303,271,321,264,266,270,279,75,294,297,415,315,446,329,355,358,359,360,370,371,324,325,326,327,444,330,331,332,445,346,348,342,343,344,345,349,350,351,352,356,354,362,363,365,366,367,368,369,373,382,383,391,387,388,389,390,378,399,400,407,408,409,412,413,418,419,420,424,425,426,429,430,431,432,433,434,435,436,437,438,443,447,448,449,450,451);

2017-06-28
ALTER TABLE `ow_member_out`
	ADD UNIQUE INDEX `user_id` (`user_id`);


2017-06-16
insert into cadre_party(user_id, type, class_id, grow_time, post, remark)
select user_id, 1, dp_type_id, dp_add_time, dp_post, dp_remark from cadre where is_dp = 1;

ALTER TABLE `cadre`
	DROP COLUMN `dp_type_id`,
	DROP COLUMN `dp_add_time`,
	DROP COLUMN `dp_post`,
	DROP COLUMN `dp_remark`,
	DROP COLUMN `is_dp`,
	DROP FOREIGN KEY `FK_cadre_base_meta_type`;

ALTER TABLE `cadre_ad_log`
	DROP COLUMN `dp_type_id`,
	DROP COLUMN `dp_add_time`,
	DROP COLUMN `dp_post`,
	DROP COLUMN `dp_remark`,
	DROP COLUMN `is_dp`;


2017-06-14
ALTER TABLE `abroad_apply_self`
	ADD COLUMN `approval_remark` VARCHAR(200) NULL COMMENT '审批意见，记录最新审批意见' AFTER `status`;


2017-06-13
ALTER TABLE `cis_inspect_obj`
	ADD COLUMN `log_file` VARCHAR(255) NULL COMMENT '考察原始记录' AFTER `summary`;



2017-06-10
update ow_member_stay set out_address=REPLACE(REPLACE(out_address, CHAR(10), ' '), CHAR(13), ' ');

2017-06-05
ALTER TABLE `ow_graduate_abroad`
	ADD COLUMN `code` VARCHAR(10) NULL COMMENT '编号，自动生成，今年就是从20170001开始，明年从20180001开始' AFTER `id`,
	ADD COLUMN `letter` VARCHAR(255) NULL DEFAULT NULL COMMENT '接收函/邀请函，图片格式' AFTER `email2`,
	ADD COLUMN `return_date` DATE NULL DEFAULT NULL COMMENT '预计回国时间（年/月）' AFTER `end_time`;


DROP TABLE `ow_apply_log`;

update ow_graduate_abroad set code = concat(left(create_time,4) , lpad(id, 4, '0'));

ALTER TABLE `ow_graduate_abroad`
	CHANGE COLUMN `code` `code` VARCHAR(10) NOT NULL COMMENT '编号，自动生成，今年就是从20170001开始，明年从20180001开始' AFTER `id`;

ALTER TABLE `ow_graduate_abroad`
	ADD UNIQUE INDEX `code` (`code`);


ALTER TABLE `ow_graduate_abroad`
	ADD COLUMN `print_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '打印次数' AFTER `create_time`,
	ADD COLUMN `last_print_time` DATETIME NULL DEFAULT NULL COMMENT '最后一次打印时间' AFTER `print_count`,
	ADD COLUMN `last_print_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '最后一次打印人' AFTER `last_print_time`;


DROP VIEW `ow_member_stay_view`;
DROP TABLE `ow_member_stay`;
DROP TABLE `ow_graduate_abroad_view`;

ALTER TABLE `ow_graduate_abroad`
	COMMENT='毕业生党员保留组织关系申请，分为出国（境）、境内两种',
	CHANGE COLUMN `type` `abroad_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '留学方式,公派/自费' AFTER `return_date`,
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类别， 1 出国  2 国内' AFTER `pay_time`;
RENAME TABLE `ow_graduate_abroad` TO `ow_member_stay`;




select * from sys_resource where url like '%memberStay%';

select * from sys_resource where url like '%graduateAbroad%';

select * from sys_role where resource_ids like '%296%';


delete from sys_resource where id in(255, 289);

update sys_resource set url='/memberStay?type=1' , permission='memberStay:*' where id=296;

update sys_resource set url='/user/memberStay?type=1' , permission='userMember:stay' where id=295;

清除缓存

update ow_member_stay set type =1;
ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别， 1 出国  2 国内' AFTER `pay_time`;

	ALTER TABLE `ow_member_stay`
	ADD UNIQUE INDEX `user_id` (`user_id`);


ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `abroad_reason` `stay_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '出国原因' AFTER `user_type`,
	CHANGE COLUMN `return_date` `over_date` DATE NULL DEFAULT NULL COMMENT '（出国）预计回国时间（年/月）， （国内）预计转出时间' AFTER `end_time`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `stay_reason` `stay_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '出国原因，（国内）暂留原因' AFTER `user_type`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `letter` `letter` VARCHAR(255) NULL DEFAULT NULL COMMENT '接收函/邀请函，（国内）户档暂留证明， 图片格式' AFTER `email2`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `abroad_type` `abroad_type` TINYINT(3) UNSIGNED NULL COMMENT '留学方式,公派/自费' AFTER `over_date`;

		CREATE ALGORITHM = UNDEFINED VIEW `ow_member_stay_view` AS SELECT wms.*,  om.`status` as member_status
from ow_member_stay wms, ow_member om
where wms.user_id=om.user_id  ;


ALTER TABLE `dispatch_work_file`
	CHANGE COLUMN `file_path` `pdf_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT 'pdf文件，如果"保密级别"选择了"秘密、机密、绝密"的其中之一，那么文件上传功能不 可以用。' AFTER `file_name`,
	ADD COLUMN `word_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT 'word文件' AFTER `pdf_file_path`;