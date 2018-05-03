
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


