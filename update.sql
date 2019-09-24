
20190924
更新 北邮

20190924

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (200, 0, '党小组', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroup:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (201, 0, '党小组成员', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroupMember:*', NULL, NULL, NULL, 1, NULL);

CREATE TABLE IF NOT EXISTS `ow_branch_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '小组名称',
  `branch_id` int(10) NOT NULL COMMENT '支部ID',
  `user_id` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `count_member` int(10) unsigned DEFAULT NULL COMMENT '党小组总人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='党小组';

CREATE TABLE IF NOT EXISTS `ow_branch_group_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '党小组ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '成员',
  `is_leader` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是党小组组长',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='党小组成员';

DROP VIEW IF EXISTS `ow_branch_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_branch_view` AS
select b.*, p.sort_order as party_sort_order, mtmp.num as member_count, mtmp.positive_count, mtmp.s_num as student_member_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, gtmp.num as group_count,
gtmp2.id as present_group_id, gtmp2.appoint_time, gtmp2.tran_time, gtmp2.actual_tran_time,bgmp.num as bg_count
from ow_branch b
left join ow_party p on b.party_id=p.id
left join (select  sum(if(political_status=2, 1, 0)) as positive_count, sum(if(type=2, 1, 0)) as s_num, count(*) as num,  branch_id from ow_member where  status=1 group by branch_id) mtmp on mtmp.branch_id=b.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, branch_id from ow_member_view where type=1 and status=1 group by branch_id) mtmp2 on mtmp2.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 group by branch_id) gtmp on gtmp.branch_id=b.id
LEFT JOIN ow_branch_member_group gtmp2 on gtmp2.is_deleted=0 and gtmp2.is_present=1 AND gtmp2.branch_id=b.id
left join (select count(*) as num,branch_id from ow_branch_group group by branch_id) bgmp on bgmp.branch_id = b.id ;

20190920

UPDATE `base_meta_class` SET `second_level`='二级党委培训', `bool_attr`='是否网络培训' WHERE   CODE='mc_cet_upper_train_type2';

REPLACE INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`)
VALUES (81, NULL, '培训班类型', '培训综合管理', '二级党委培训', 'mc_cet_upper_train_type2', '是否网络培训', '', '', 81, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`)
 VALUES (81, '网络培训', 'mt_cz5ve9', 1, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`)
VALUES (81, '专题讲座', 'mt_itthf8', 0, '', '', 3, 1);

delete from base_meta_class where code='mc_cet_upper_train_organizer2';

UPDATE sys_resource SET NAME='二级党委培训信息', sort_order=265 WHERE permission='cet:menu:unit';

UPDATE sys_resource SET sort_order=268 WHERE permission='cet:menu:oncampus';

DELETE FROM base_meta_class WHERE CODE = 'mc_cet_upper_train_special';
DELETE FROM base_meta_class WHERE CODE = 'mc_cet_upper_train_special2';
UPDATE sys_resource SET permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer,mc_cet_upper_train_type:*' WHERE permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer,mc_cet_upper_train_type,mc_cet_upper_train_special:*';
UPDATE sys_resource SET permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer2,mc_cet_upper_train_type2:*' WHERE permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer2,mc_cet_upper_train_type2,mc_cet_upper_train_special2:*';

ALTER TABLE `cet_unit_project`
	DROP COLUMN `special_type`;

ALTER TABLE `cet_upper_train`
	DROP COLUMN `special_type`;

ALTER TABLE `cet_unit_project`
	ADD COLUMN `report_name` VARCHAR(200) NULL COMMENT '报告名称，专题讲座时填写' AFTER `project_type`,
	ADD COLUMN `reporter` VARCHAR(50) NULL COMMENT '主讲人，专题讲座时填写' AFTER `report_name`;

ALTER TABLE `cet_unit_train`
	ADD COLUMN `trainee_type_id` INT(10) UNSIGNED NULL COMMENT '培训对象类型' AFTER `user_id`;

ALTER TABLE `cet_unit_train`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性' AFTER `title`;

ALTER TABLE `cet_unit_project`
	ADD COLUMN `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '审批状态，0 待报送 1 已报送 2 打回 3 审批通过' AFTER `add_time`,
	DROP COLUMN `status`;

ALTER TABLE `cet_unit_train`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性' AFTER `title`;

ALTER TABLE `cet_unit_train`
	COMMENT='二级党委培训参训人信息';
ALTER TABLE `cet_unit_project`
	COMMENT='二级党委培训';

ALTER TABLE `cet_unit_project`
	ADD COLUMN `party_id` INT(10) UNSIGNED NULL COMMENT '培训班主办方，从分党委中选择' AFTER `year`;

UPDATE cet_unit_project cup, ow_party p SET cup.party_id=p.id WHERE cup.unit_id=p.unit_id;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `candidate_time` DATE NULL DEFAULT NULL COMMENT '成为发展对象时间' AFTER `active_time`;
UPDATE cet_unit_train SET trainee_type_id=NULL;
update cet_unit_train set trainee_type_id=1;
-- 更新导入样表

20190917
更新 北邮、北航、南航、哈工大

20190916

ALTER TABLE `cadre_work`
	CHANGE COLUMN `work_type` `work_types` VARCHAR(200) NULL DEFAULT NULL COMMENT '院系/机关工作经历，关联元数据，可多选' AFTER `unit_ids`,
	DROP INDEX `FK_base_cadre_work_base_meta_type_2`,
	DROP FOREIGN KEY `FK_base_cadre_work_base_meta_type_2`;

ALTER TABLE `cadre_edu`
	ADD COLUMN `subject` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学科门类' AFTER `dep`,
	ADD COLUMN `first_subject` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '一级学科' AFTER `subject`;

ALTER TABLE `cadre`
	ADD COLUMN `label` VARCHAR(255) NULL DEFAULT NULL COMMENT '标签，关联元数据，多选' AFTER `dispatch_cadre_id`;

ALTER TABLE `cadre`
	ADD COLUMN `profile` TEXT NULL DEFAULT NULL COMMENT '相关资料，可上传多个文件' AFTER `label`;

ALTER TABLE `ow_branch_member`
	ADD COLUMN `is_double_leader` TINYINT(1) UNSIGNED NULL DEFAULT 0 COMMENT '是否双带头人' AFTER `type_id`;

ALTER TABLE `sys_teacher_info`
	ADD COLUMN `is_high_level_talent` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否高层次人才' AFTER `is_temp`;

ALTER TABLE `ow_member`
	ADD COLUMN `label` VARCHAR(255) NULL DEFAULT NULL COMMENT '标签，关联元数据，多选' AFTER `other_reward`,
	ADD COLUMN `profile` TEXT NULL COMMENT '相关资料，可上传多个文件' AFTER `label`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
 VALUES ('cadreEdu_needSubject', '学习经历包含学科类别', 'false', 3, 43, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cadreEdu_needCertificate', '是否需要上传学历学位证书', 'false', 3, 44, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cadreReward_needProof', '是否需要上传获奖证书', 'false', 3, 45, '');

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (92, NULL, '干部标签', '领导干部', '基本信息', 'mc_cadre_label', '', '', '', 2600, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (92, '沉稳', 'mt_944wlw', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (92, '善言', 'mt_fx0nzd', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (92, '勤奋', 'mt_t2dwdo', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (92, '忠诚', 'mt_50xhuz', NULL, '', '', 4, 1);


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`,
                            `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`,
                            `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (73, 0, '层级分类管理', '', 'url', '', '/layerType', 67, '0/1/67/', 1, 'layerType:*', NULL, NULL, NULL, 1, 450);

ALTER TABLE `ow_branch`
	CHANGE COLUMN `found_time` `found_time` DATE NULL COMMENT '成立时间' AFTER `email`;

update sys_resource set NAME='消息日志' WHERE permission='shortMsg:*';

-- 更新cadre_view等
-- 更新 ow_member_view
-- 更新 ow_branch_member_view
-- 更新 ow_party_view
-- 更新 ow_branch_view

-- 删除geronomo.mail/activation/stax.jar
-- 新增javax.mail-1.6.2.jar activation-1.1.jar

-- 刷新 location.js
-- 给支部增加 校内转接、组织关系转出、转入的权限
-- 层级分类管理的权限
-- 更新导入样表

20190910
北航、哈工大

20190909
北邮

20190908
北化工

20190908
-- 更新导入样表

20190907
-- 更新  jx.utils

20190906
北邮  南航

20190904
南航
20190904
ALTER TABLE `sc_subsidy_cadre`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '所在单位' AFTER `cadre_id`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.corpID', '微信企业号ID', 'xxx', 1, 40, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.corpSecret', '微信企业号secret', 'xxxx', 1, 41, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.agentId', '微信应用ID', '100', 2, 42, '');


UPDATE sys_resource SET NAME='消息模板' WHERE permission='contentTpl:*';
UPDATE sys_resource SET NAME='定向消息' WHERE permission='shortMsgTpl:*';

ALTER TABLE `base_content_tpl`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `role_id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `code`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL COMMENT '图片，微信使用' AFTER `wx_url`,
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `update_time`;

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `role_id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `type`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL DEFAULT NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL DEFAULT NULL COMMENT '图片，微信使用' AFTER `wx_url`;

ALTER TABLE `base_short_msg`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `type`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL DEFAULT NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL DEFAULT NULL COMMENT '图片，微信使用' AFTER `wx_url`,
	CHANGE COLUMN `type` `type_str` VARCHAR(50) NULL DEFAULT NULL COMMENT '类别' AFTER `relate_type`;

UPDATE base_short_msg SET TYPE=1;
UPDATE base_short_msg_tpl SET TYPE=1;

-- 删除 sys.SendMsgResult
-- 更新  jx.utils
-- 更新 twelvemonkeys等jar包，删除原来的版本
-- common-io-1.3.2.jar  -> common-io-2.6.jar
-- 增加 wx.send

20190902
南航

delete from sys_resource where permission='cadre:view';

ALTER TABLE `base_short_msg`
	ADD COLUMN `repeat_times` TEXT NULL DEFAULT NULL COMMENT '重复发送时间（成功发送的时间）' AFTER `create_time`;

UPDATE base_meta_class SET CODE='mc_dr_type' WHERE CODE='mc_dr_recommend_type';

20190901
南航

ALTER TABLE `dr_offline`
	ADD COLUMN `supervice_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '监督人员，针对会议推荐' AFTER `chief_member_id`;
DROP VIEW IF EXISTS `dr_offline_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_offline_view` AS
select do.*, sr.seq as sr_seq, sm.hold_date, sm.sc_type, sm.unit_post_id, up.name as post_name,
up.job, up.admin_level, up.post_type, up.unit_id,
u.type_id as unit_type from dr_offline do
left join sc_record sr on sr.id=do.record_id
left join sc_motion sm on sm.id= sr.motion_id
left join unit_post up on up.id = sm.unit_post_id
left join unit u on u.id = up.unit_id ;

