
20181101

更新common-utils.jar

更新 sc_dispatch_view

dispatch_view


20181031
ALTER TABLE `cadre_leader_unit`
	ADD CONSTRAINT `FK_cadre_leader_unit_cadre_leader` FOREIGN KEY (`leader_id`) REFERENCES `cadre_leader` (`id`) ON DELETE CASCADE;

新增jar包 icepdf 12个

新增移动端干部文件管理

	更新 sc_committee_vote_view

ALTER TABLE `sc_dispatch`
	ADD COLUMN `title` VARCHAR(200) NULL DEFAULT NULL COMMENT '标题' AFTER `code`;

ALTER TABLE `sc_dispatch`
	ADD COLUMN `file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '文件签发稿，pdf格式' AFTER `pub_time`,
	CHANGE COLUMN `file_path` `word_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '文件签发稿，word格式' AFTER `file_path`,
	CHANGE COLUMN `sign_file_path` `sign_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '正式签发单，pdf格式' AFTER `word_file_path`;

ALTER TABLE `sc_dispatch_committee`
	ADD CONSTRAINT `FK_sc_dispatch_committee_sc_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `sc_dispatch` (`id`) ON DELETE CASCADE;

	ALTER TABLE `sc_dispatch_user`
	ADD CONSTRAINT `FK_sc_dispatch_user_sc_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `sc_dispatch` (`id`) ON DELETE CASCADE;

更新 sc_dispatch_view


删除 bc*jdk14*.jar



	-- 更新西交大
20181024
ALTER TABLE `sys_teacher_info`
	CHANGE COLUMN `school` `school` VARCHAR(200) NULL DEFAULT NULL COMMENT '学历毕业学校' AFTER `major`;

更新 sc_group_member_view

ALTER TABLE `sc_group_topic`
	CHANGE COLUMN `file_path` `file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '讨论议题附件' AFTER `id`;

ALTER TABLE `sc_group_topic`
	CHANGE COLUMN `file_path` `file_path` TEXT NULL DEFAULT NULL COMMENT '讨论议题附件' AFTER `id`;

更新 sc_group_topic_view

ALTER TABLE `cadre_reserve`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `cadre_id`;

更新 cadre_reserve_view

update cadre_reserve set type=454 where type=2;

update cadre_reserve set type=455 where type=3;

update cadre_reserve set type=456 where type=4;

update cadre_reserve set type=457 where type=5;


ALTER TABLE `cadre_reserve_origin`
	CHANGE COLUMN `reserve_type` `reserve_type` INT(10) UNSIGNED NOT NULL COMMENT '后备干部类别' AFTER `user_id`;



20181021

pom.xml:
	升级 quartz 2.3.0
(删除 c3p0-0.9.1.1.jar， quartzxxxx-2.2.1.jar)


update sys_resource set permission='sysRole:*' where permission='sys:role';

sysLogin:switch  切换账号登录

sysOnlineLog:kickout  踢用户

profile:updateAvatar 更新头像

menu:preview  菜单预览


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (853, 0, '系统功能点', '', 'menu', '', NULL, 21, '0/1/21/', 0, 'sys:function', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (854, 0, '切换账号登录', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'sysLogin:switch', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (855, 0, '踢用户', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'sysOnlineLog:kickout', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (856, 0, '更新头像', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'profile:updateAvatar', NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (857, 0, '菜单预览', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'menu:preview', NULL, NULL, 1, NULL);


20181012
更新 mysql-connector-java 5.1.10

20181011
更新 druid 1.1.11


20181008

ALTER TABLE `cadre_post`
	CHANGE COLUMN `double_unit_id` `double_unit_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '双肩挑单位，只用于主职' AFTER `is_double`,
	DROP FOREIGN KEY `FK_base_cadre_main_work_base_unit_2`;

更新 cadre_view

更新 cadre_inspect_view
cadre_reserve_view
crs_candidate_view


-- 更新北化工

20180929
update sys_log set type_id=501 where content like '提交评课%';
update sys_log set type_id=500 where type_id=190;

20180921
alter table ow_branch change phone phone varchar(50) NULL DEFAULT NULL COMMENT '联系电话';

增加 mc_cadre_eva， 年度考核情况
新增表 cadre_eva

20180915
ALTER TABLE `sys_user_info`
	ADD COLUMN `msg_mobile` VARCHAR(100) NULL DEFAULT NULL COMMENT '代收短信的手机号，如果存在代收手机号，则发短信至代收手机号' AFTER `mobile`,
	ADD COLUMN `not_send_msg` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否禁止给手机发送短信，如果禁止，那么代收手机号同样不收短信' AFTER `msg_mobile`;

更新 sys_user_view

更新common-utils.jar

-- 更新西交大
20180906
更新common-utils.jar


20180906
ALTER TABLE `cadre_work`
	CHANGE COLUMN `unit_id` `unit_ids` VARCHAR(100) NULL DEFAULT NULL COMMENT '所属内设机构，包含历史单位' AFTER `detail`;


20180830
update  pmd_member pm, pmd_config_member pcm set pm.has_salary=pcm.has_salary where pm.user_id=pcm.user_id and pm.type=3 and pm.has_salary is null;


20180828
ALTER TABLE `sc_subsidy_cadre`
	CHANGE COLUMN `post` `post` VARCHAR(100) NULL COMMENT '现任职务，离任显示“原职务”' AFTER `unit_id`,
	ADD COLUMN `title` VARCHAR(200) NULL COMMENT '所在单位及职务，离任显示“离任后所在单位及职务”' AFTER `post`;

更新 sc_subsidy_cadre_view

update sc_subsidy_cadre ssc, cadre c set ssc.title=c.title where ssc.cadre_id=c.id;

20180826
ALTER TABLE `cet_upper_train_admin`
	CHANGE COLUMN `leader_id` `leader_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属校领导' AFTER `unit_id`;

ALTER TABLE `cet_upper_train`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `is_valid`;

ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '审批状态，0 待审批 1 审批通过 2 审批不通过，（仅针对本人填写，组织部派出由组织部管理员审批，其他部门派出由部门管理员审批）' AFTER `remark`;

20180822
更新common-utils.jar

20180730
新增unit_view

20180729
新增 base_annual_type

新增 sc_subsidy等表
新增 sc_subsidy_cadre_view  sc_subsidy_dispatch_view

新增 sc_motion等表

增加 mc_sc_motion_type， mc_sc_motion_sctype
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `sort_order`, `available`) VALUES (69, NULL, '动议事项', '干部选拔', '干部任用纪实', 'mc_sc_motion_type', '', '', 69, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `sort_order`, `available`) VALUES (70, NULL, '干部选任方式', '干部选拔', '干部任用纪实', 'mc_sc_motion_sctype', '', '', 70, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (69, '学院行政班子换届', 'mt_ngpqab', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (69, '基层党组织换届', 'mt_j4mpl4', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (69, '补充干部', 'mt_sqmsio', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '民主推荐', 'mt_jczbfe', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '竞争上岗', 'mt_eupqjf', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '公开招聘', 'mt_7ov3ym', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '基层党组织换届', 'mt_eonilk', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '行政班子换届', 'mt_akqjki', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, '交流轮岗', 'mt_2zqqri', NULL, '', '', 6, 1);


-- 更新北化工、西交大

20180719
ALTER TABLE `abroad_approver_black_list`
	DROP INDEX `cadre_id_approver_type_id`,
	DROP INDEX `FK_abroad_approver_black_list_abroad_approver_type`,
	DROP FOREIGN KEY `FK_abroad_approver_black_list_abroad_approver_type`,
	DROP FOREIGN KEY `FK_abroad_approver_black_list_base_cadre`;

	ALTER TABLE `abroad_approver_black_list`
	ADD COLUMN `unit_id` INT(10) UNSIGNED NOT NULL COMMENT '干部所属单位，包含兼审单位' AFTER `cadre_id`;

	ALTER TABLE `abroad_approver_black_list`
	ADD UNIQUE INDEX `cadre_id_unit_id_approver_type_id` (`cadre_id`, `unit_id`, `approver_type_id`);

	update abroad_approver_black_list b, cadre c set b.unit_id=c.unit_id where b.cadre_id=c.id;


 -- 更新西交大、北化工
20180718
ALTER TABLE `sys_config`
	ADD COLUMN `city` VARCHAR(50) NULL DEFAULT NULL COMMENT '所在城市' AFTER `login_timeout`;

update sys_config set city='北京市';


 -- 更新北化工
20180718
ALTER TABLE `cadre_leader_unit`
	DROP FOREIGN KEY `FK_base_leader_unit_base_leader`,
	DROP FOREIGN KEY `FK_base_leader_unit_base_meta_type`,
	DROP FOREIGN KEY `FK_base_leader_unit_base_unit`;

	ALTER TABLE `cadre_leader_unit`
	DROP INDEX `leader_id_unit_id_type_id`,
	DROP INDEX `FK_base_leader_unit_base_unit`,
	DROP INDEX `FK_base_leader_unit_base_meta_type`;

ALTER TABLE `cadre_leader_unit`
	ADD UNIQUE INDEX `leader_id_unit_id` (`leader_id`, `unit_id`);

新建 cadre_leader_unit_view

更新 metadata.js

20180718
ALTER TABLE `abroad_approver_type`
	ADD COLUMN `auth` VARCHAR(50) NULL COMMENT '审批范围，针对分管校领导，逗号分隔' AFTER `type`;

更新 common-utils

增加 ct_applyself_approval_other

20180714
ALTER TABLE `unit`
	ADD COLUMN `file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '成立文件，pdf文档' AFTER `work_time`,
	CHANGE COLUMN `url` `url` VARCHAR(200) NULL DEFAULT NULL COMMENT '单位网址，弃用' AFTER `file_path`;

新建 unit_post,  unit_post_view

20180710
提任-> mt_dispatch_cadre_way_promote


20180710
ALTER TABLE `cet_train_course`
	CHANGE COLUMN `name` `name` VARCHAR(200) NULL DEFAULT NULL COMMENT '名称，直接创建的课程名称（包括专题班测评名称）' AFTER `course_id`;


20180705
ALTER TABLE `cet_train`
	CHANGE COLUMN `name` `name` VARCHAR(200) NOT NULL COMMENT '培训班名称' AFTER `is_on_campus`;

20180703

ALTER TABLE `pmd_pay_branch`
	DROP PRIMARY KEY;

	ALTER TABLE `pmd_pay_branch`
	ADD COLUMN `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID' FIRST,
	ADD PRIMARY KEY (`id`);

ALTER TABLE `pmd_pay_branch`
	ADD UNIQUE INDEX `branch_id_party_id` (`branch_id`, `party_id`);

更新 pmd_pay_branch_view

20180630
ALTER TABLE `cadre_family`
	CHANGE COLUMN `unit` `unit` VARCHAR(200) NULL DEFAULT NULL COMMENT '工作单位及职务' AFTER `political_status`;


20180628
ALTER TABLE `crs_applicant`
	ADD COLUMN `quit_pdf` VARCHAR(100) NULL DEFAULT NULL COMMENT '退出申请' AFTER `is_quit`;
ALTER TABLE `crs_applicant`
	CHANGE COLUMN `quit_pdf` `quit_proof` VARCHAR(100) NULL DEFAULT NULL COMMENT '退出申请，图片或pdf' AFTER `is_quit`;
更新 crs_applicant_view


20180628
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `sort_order`)
VALUES (NULL, 'hf_cadre_family_note', NULL, NULL, NULL, '家庭成员信息填写说明（特殊）', '家庭成员信息填写说明（特殊）', NULL, '', 48);


== 更新 西交大、北化工
20180626
ALTER TABLE `ext_yjs`
	DROP COLUMN `id`,
	DROP INDEX `xh`;
	ALTER TABLE `ext_yjs`
	ADD PRIMARY KEY (`xh`);

	ALTER TABLE `ext_jzg`
	DROP COLUMN `id`,
	DROP INDEX `zgh`;

	ALTER TABLE `ext_jzg`
	ADD PRIMARY KEY (`zgh`);

	ALTER TABLE `ext_bks`
	DROP COLUMN `id`,
	DROP INDEX `xh`;
	ALTER TABLE `ext_bks`
	CHANGE COLUMN `xh` `xh` VARCHAR(100) NOT NULL COMMENT '学号' FIRST,
	ADD PRIMARY KEY (`xh`);

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_apply_count` INT UNSIGNED NULL COMMENT '招聘会人数要求' AFTER `enroll_status`;


20180625
hf_cadre_famliy -> hf_cadre_family

20180625
ALTER TABLE `cadre_family`
	ADD COLUMN `with_god` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否去世' AFTER `birthday`;

20180625
ALTER TABLE `unit`
	CHANGE COLUMN `work_time` `work_time` DATETIME NULL COMMENT '成立时间' AFTER `type_id`;

-- 修改unit 为正序排序
select max(sort_order) from unit into @max;
update unit set sort_order = @max - sort_order + 1;

更新 utils

20180622
ALTER TABLE `sys_config`
	ADD COLUMN `has_party_module` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否存在党建模块' AFTER `use_cadre_post`;

