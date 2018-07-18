

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

