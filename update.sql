
2018-6-1
ALTER TABLE `pmd_config_reset`
	ADD COLUMN `reset` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '是否重置支部自行设定的额度' AFTER `user_id`;



-- 更新北化工
2018-5-29
ALTER TABLE `cet_project_obj`
	CHANGE COLUMN `word_write` `word_write` VARCHAR(200) NULL DEFAULT NULL COMMENT '心得体会，WORD版，（此字段作废）' AFTER `is_graduate`,
	CHANGE COLUMN `pdf_write` `write_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '心得体会，pdf或word版' AFTER `word_write`;

更新cet_project_obj_view
cet_project_obj_cadre_view


-- 更新jixiantech

2018-5-28
ALTER TABLE `crs_post`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '岗位状态，1正在招聘、2完成招聘、3已删除、4 已作废' AFTER `pub_status`;

2018-5-28
update cet_plan_course_obj set is_finished=0 where is_finished is null;

ALTER TABLE `cet_plan_course_obj`
	CHANGE COLUMN `is_finished` `is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否结业，针对上级网上专题班，是否完成，针对自主学习' AFTER `num`;

2018-5-27
ALTER TABLE `crp_record`
	CHANGE COLUMN `temp_post` `temp_post` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他挂职类型，手动录入挂职类型' AFTER `temp_post_type`,
	CHANGE COLUMN `title` `unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '挂职单位' AFTER `project`,
	ADD COLUMN `post` VARCHAR(100) NULL DEFAULT NULL COMMENT '所任职务' AFTER `unit`;


2018-5-24
修改民族党派元数据

研究生课程班 mt_edu_sstd3 -> mt_edu_yjskcb
update base_meta_type set code='mt_edu_yjskcb' where code='mt_edu_sstd3';
全日制教育 mt_fullltime -> mt_fulltime
update base_meta_type set code='mt_fulltime' where code='mt_fullltime';
更新了common-utils

ALTER TABLE `cadre_reward`
	ADD COLUMN `is_independent` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否独立获奖' AFTER `remark`;

update cadre_reward set rank=null where rank=0;

ALTER TABLE `cadre_reward`
	ADD COLUMN `reward_level` INT(10) UNSIGNED NULL COMMENT '奖励级别' AFTER `cadre_id`;

增加 mc_reward_level

2018-5-23

ALTER TABLE `crs_applicant`
	ADD COLUMN `career` TEXT NULL COMMENT '管理工作经历' AFTER `post_id`;

更新 crs_applicant_view

ALTER TABLE `crs_short_msg`
	CHANGE COLUMN `tpl_key` `tpl_key` VARCHAR(50) NULL DEFAULT NULL COMMENT '短信模板key，可能是自定义的key（以crs_开头）' AFTER `user_id`,
	CHANGE COLUMN `content_tpl_id` `content_tpl_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '短信模板ID，自定义key时为空' AFTER `tpl_key`;

ALTER TABLE `cet_short_msg`
	CHANGE COLUMN `tpl_key` `tpl_key` VARCHAR(50) NULL DEFAULT NULL COMMENT '短信模板key，可能是自定义的key（以crs_开头）' AFTER `mobile`,
	CHANGE COLUMN `content_tpl_id` `content_tpl_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '短信模板ID，自定义key时为空' AFTER `tpl_key`;

ALTER TABLE `cet_short_msg`
	ADD COLUMN `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `success`;

ALTER TABLE `cet_short_msg`
	CHANGE COLUMN `record_id` `record_id` VARCHAR(50) NULL DEFAULT NULL COMMENT '关联记录，有可能是多条关联（比如：补选课通知）' AFTER `id`;

ALTER TABLE `cet_short_msg`
	ADD INDEX `record_id` (`record_id`),
	ADD INDEX `tpl_key` (`tpl_key`);


2018-5-21
ALTER TABLE `pmd_order_campuscard`
	CHANGE COLUMN `member_id` `member_id` INT(10) UNSIGNED NULL COMMENT '党员缴费记录' AFTER `sn`,
	ADD COLUMN `is_batch` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否批量缴费，批量缴费时，member_id为空' AFTER `member_id`;

ALTER TABLE `pmd_order_campuscard`
	ADD INDEX `user_id` (`user_id`),
	ADD INDEX `is_batch` (`is_batch`);

删除 pmd_order_campuscard_view

ALTER TABLE `pmd_order_campuscard`
	ADD COLUMN `pay_month` VARCHAR(10) NULL COMMENT '缴费月份，用于批量缴费生成订单号' AFTER `is_batch`,
	ADD INDEX `pay_month` (`pay_month`);

update pmd_order_campuscard set pay_month = left(sn, 6);

ALTER TABLE `pmd_order_campuscard`
	CHANGE COLUMN `pay_month` `pay_month` VARCHAR(10) NOT NULL COMMENT '缴费月份，用于批量缴费生成订单号' AFTER `is_batch`;

ALTER TABLE `pmd_member_pay`
	CHANGE COLUMN `order_no` `order_no` VARCHAR(20) NULL DEFAULT NULL COMMENT '缴费订单号，批量缴费时允许重复' AFTER `member_id`,
	DROP INDEX `order_no`;


update base_meta_type set code='mt_edu_jxxx' where code='mt_edu_jxxi';


2018-5-18
先备份 cadre_work

ALTER TABLE `cadre_work`
	CHANGE COLUMN `unit` `detail` VARCHAR(100) NULL DEFAULT NULL COMMENT '工作单位及担任职务（或专技职务）' AFTER `end_time`;

update cadre_work set post='' where post = '无' or post='（无职务）' or post='(无职务)';
update cadre_work set detail = concat(detail, post);
ALTER TABLE `cadre_work` DROP COLUMN `post`;

ALTER TABLE `cadre_work`
	DROP COLUMN `type_id`,
	DROP FOREIGN KEY `FK_base_cadre_work_base_meta_type`;

2018-5-17
ALTER TABLE `cet_train_course`
	ADD COLUMN `apply_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0'
	COMMENT '选课/退课状态，0：由培训班的选课时间决定  1： 已关闭选课  2： 已关闭退课  3： 已关闭选课和退课' AFTER `apply_limit`;

更新 cet_train_course_view

更新 cet_train_view

ALTER TABLE `cadre_edu`
	ADD COLUMN `note` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他说明' AFTER `remark`;

2018-5-16
ALTER TABLE `cet_short_msg`
	ADD COLUMN `mobile` VARCHAR(20) NULL DEFAULT NULL COMMENT '接收手机号' AFTER `user_id`;

2018-5-15
更新 cet_train_view
2018-5-13
ALTER TABLE `cet_course`
	CHANGE COLUMN `url` `url` VARCHAR(500) NULL DEFAULT NULL COMMENT '视频播放地址，针对线上课程' AFTER `teach_method`;

2018-5-9
update  crs_post_expert set sort_order=id;

2018-5-9
ALTER TABLE `cet_project`
	ADD COLUMN `open_time` DATETIME NULL DEFAULT NULL COMMENT '开班时间' AFTER `end_date`,
	ADD COLUMN `open_address` VARCHAR(200) NULL DEFAULT NULL COMMENT '开班地点' AFTER `open_time`;

	ALTER TABLE `cet_train`
	DROP COLUMN `open_time`,
	DROP COLUMN `open_address`;

		更新 cet_project_view
			更新 cet_train_view

			ALTER TABLE `cet_short_msg`
	CHANGE COLUMN `train_id` `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联记录' AFTER `id`;


	ALTER TABLE `crs_short_msg`
	CHANGE COLUMN `msg` `msg` TEXT NULL DEFAULT NULL COMMENT '短信内容' AFTER `content_tpl_id`;

ALTER TABLE `cet_short_msg`
	CHANGE COLUMN `msg` `msg` TEXT NULL DEFAULT NULL COMMENT '短信内容' AFTER `content_tpl_id`;
2018-5-8
ALTER TABLE `cet_discuss_group`
	CHANGE COLUMN `hold_user_id` `hold_user_ids` VARCHAR(100) NULL COMMENT '召集人' AFTER `name`,
	ADD COLUMN `link_user_ids` VARCHAR(100) NULL COMMENT '联络员' AFTER `hold_user_ids`;


ALTER TABLE `cet_train`
	ADD COLUMN `open_time` DATETIME NULL DEFAULT NULL COMMENT '开班时间' AFTER `end_date`,
	ADD COLUMN `open_address` VARCHAR(200) NULL COMMENT '开班地点' AFTER `open_time`;

	更新 cet_train_view

2018-5-8
ALTER TABLE `cet_course`
	CHANGE COLUMN `teach_method` `teach_method` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '授课方式，关联元数据，针对线下课程' AFTER `expert_id`,
	ADD COLUMN `url` VARCHAR(200) NULL DEFAULT NULL COMMENT '视频播放地址，针对线上课程' AFTER `teach_method`;



2018-5-6
更新 cet_train_view


ALTER TABLE `cet_course`
	DROP COLUMN `course_type_id`,
	DROP INDEX `FK_cet_course_cet_course_type`,
	DROP FOREIGN KEY `FK_cet_course_cet_course_type`;

	删除 cet_course_type_view

	ALTER TABLE `cet_course_type`
	COMMENT='专题分类，针对专题班';
RENAME TABLE `cet_course_type` TO `cet_project_type`;

ALTER TABLE `cet_project`
	ADD COLUMN `project_type_id` INT UNSIGNED NULL COMMENT '专题分类' AFTER `name`;

	更新 cet_project_view

ALTER TABLE `cet_course_file`
	ADD COLUMN `paper_note` VARCHAR(200) NULL DEFAULT NULL COMMENT '纸质学习材料说明' AFTER `has_paper`;

	ALTER TABLE `cet_train_course`
	ADD COLUMN `apply_limit` INT(10) UNSIGNED NULL COMMENT '选课人数上限' AFTER `teacher`;

	更新 cet_train_course_view

	ALTER TABLE `cet_project_obj`
	ADD COLUMN `is_graduate` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否结业' AFTER `is_quit`;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `should_finish_period` DECIMAL(10,1) UNSIGNED NULL COMMENT '应完成学时' AFTER `is_quit`;


	更新 cet_project_obj_view, cet_project_obj_cadre_view


2018-5-3

ALTER TABLE `crs_post`
	ADD COLUMN `ppt_upload_closed` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '上传通道是否关闭' AFTER `ppt_deadline`;


2018-4-28

1、删除“信息修改申请”下的所有function
2、导入 746~779 的sys_resource
3、将“信息修改申请”移动到中层干部信息-分类统计下面
4、重新分配处级干部和干部管理员的信息审核的权限


alter table  cet_course_type add column `is_deleted` tinyint(1)
 unsigned NOT NULL DEFAULT '0' COMMENT '状态，0 正常 1 已删除' after remark;

更新 cet_course_type_view


============更新北化工========= 包括crs
2018-4-28
ALTER TABLE `sys_config`
	ADD COLUMN `favicon` VARCHAR(200) NULL DEFAULT NULL COMMENT 'favicon.ico，48*48' AFTER `mobile_title`,
	ADD COLUMN `cadre_template_fs_note` VARCHAR(100) NULL DEFAULT NULL COMMENT '附属单位说明，用于中层干部统计表格' AFTER `display_login_msg`;



2018-4-27
更改学历 mt_edu_bk  mt_edu_zk
update base_meta_type set code='mt_edu_jxxi' where code='mt_jxxi';
update base_meta_type set code='mt_edu_sstd' where code='mt_sstd';
update base_meta_type set code='mt_edu_sstd3' where code='mt_sstd3';

-- 去掉 <w:cantSplit/> 防止word文档显示不全而且不进行不分页

ALTER TABLE `crs_post`
	ADD COLUMN `ppt_deadline` DATETIME NULL DEFAULT NULL COMMENT '上传应聘PPT截止时间' AFTER `quit_deadline`;

update sys_html_fragment set sort_order=id;

2018-4-27
ALTER TABLE `crs_applicant`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL
	COMMENT '排序，（审核通过或破格）后的排序，审核通过或破格时，对其赋值' AFTER `recommend_second_count`;

更新 crs_applicant_view

2018-4-26
ALTER TABLE `sys_user_info`
	CHANGE COLUMN `avatar` `avatar` VARCHAR(200) NULL DEFAULT NULL COMMENT '照片' AFTER `birth`,
	ADD COLUMN `avatar_upload_time` DATETIME NULL DEFAULT NULL COMMENT '照片上传时间' AFTER `avatar`;

更新 sys_user_view


============更新北化工=========
2018-4-25
update modify_table_apply set table_name='cadre_post_admin' where module=15;
update modify_table_apply set table_name='cadre_post_work' where module=16;
update modify_table_apply set table_name='cadre_family' where module=17;
update modify_table_apply set table_name='cadre_family_abroad' where module=18;


update cadre_edu set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_edu' and status !=0
);

update cadre_work set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_work' and status !=0
);

update cadre_book set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_book' and status !=0
);

update cadre_parttime set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_parttime' and status !=0
);

update cadre_research set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_research' and status !=0
);

update cadre_train set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_train' and status !=0
);


update cadre_paper set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_paper' and status !=0
);


update cadre_reward set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_reward' and status !=0
);


update cadre_course set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_course' and status !=0
);


update cadre_company set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_company' and status !=0
);

update cadre_post_pro set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_pro' and status !=0
);


update cadre_post_admin set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_admin' and status !=0
);


update cadre_post_work set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_work' and status !=0
);

update cadre_family set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_family' and status !=0
);

update cadre_family_abroad set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_family_abroad' and status !=0
);


更新utils.jar



2018-4-25

RENAME TABLE `cadre_famliy` TO `cadre_family`;

RENAME TABLE `cadre_famliy_abroad` TO `cadre_family_abroad`;

ALTER TABLE `cadre_family_abroad`
	CHANGE COLUMN `famliy_id` `family_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所选家庭成员' AFTER `cadre_id`;

UPDATE `sys_resource` SET `permission`='cadreFamily:*' WHERE  `id`=239;
UPDATE `sys_resource` SET `permission`='cadreFamilyAbroad:*' WHERE  `id`=242;
UPDATE `sys_resource` SET `permission`='cadre:exportFamily' WHERE  `id`=454;

ALTER TABLE `cadre_family`
	DROP FOREIGN KEY `FK_base_cadre_famliy_base_cadre`;
ALTER TABLE `cadre_family`
	ADD CONSTRAINT `FK_base_cadre_family_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cadre_family_abroad`
	DROP FOREIGN KEY `FK_base_cadre_famliy_abroad_base_cadre`,
	DROP FOREIGN KEY `FK_base_cadre_famliy_abroad_base_cadre_famliy`;
ALTER TABLE `cadre_family_abroad`
	ADD CONSTRAINT `FK_base_cadre_family_abroad_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE,
	ADD CONSTRAINT `FK_base_cadre_family_abroad_base_cadre_family` FOREIGN KEY (`family_id`) REFERENCES `cadre_family` (`id`);
ALTER TABLE `cadre_family`
	DROP INDEX `FK_base_cadre_famliy_base_cadre`,
	ADD INDEX `FK_base_cadre_family_base_cadre` (`cadre_id`);

	ALTER TABLE `cadre_family_abroad`
	DROP INDEX `FK_base_cadre_famliy_abroad_base_cadre`,
	ADD INDEX `FK_base_cadre_family_abroad_base_cadre` (`cadre_id`),
	DROP INDEX `FK_base_cadre_famliy_abroad_base_cadre_famliy`,
	ADD INDEX `FK_base_cadre_family_abroad_base_cadre_family` (`family_id`);

ALTER TABLE `cadre_info_check`
	CHANGE COLUMN `famliy_abroad` `family_abroad` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '家庭成员移居国（境）外的情况' AFTER `reward`;


