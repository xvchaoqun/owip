
20190217

更新 common-utils
*sample_cadreParty ->  sample_cadreParty_ow

ALTER TABLE `cadre`
	CHANGE COLUMN `type_id` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '行政级别，关联元数据' AFTER `user_id`,
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性，关联元数据' AFTER `admin_level`,
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '中层干部类别，1 处级 2 科级' AFTER `unit_id`,
	ADD COLUMN `state` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' AFTER `type`;

update cadre set type=1 where status in(1,3);

ALTER TABLE `sc_passport_hand`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性，关联元数据' AFTER `post`,
	CHANGE COLUMN `type_id` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '行政级别，关联元数据' AFTER `post_type`;

ALTER TABLE `cadre_ad_log`
	CHANGE COLUMN `type_id` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '行政级别，关联元数据' AFTER `cadre_id`,
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性，关联元数据' AFTER `admin_level`;

ALTER TABLE `cadre_post`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性，关联元数据' AFTER `post`,
	CHANGE COLUMN `admin_level_id` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '行政级别，关联元数据' AFTER `post_type`,
	CHANGE COLUMN `double_unit_ids` `double_unit_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '双肩挑单位' AFTER `is_double`;

ALTER TABLE `sc_committee_vote`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，关联元数据' AFTER `post`,
	CHANGE COLUMN `admin_level_id` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别，关联元数据' AFTER `post_type`;

ALTER TABLE `dispatch_cadre`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，关联元数据' AFTER `post`,
	CHANGE COLUMN `admin_level_id` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别，关联元数据' AFTER `post_type`;

ALTER TABLE `cadre_admin_level`
	CHANGE COLUMN `admin_level_id` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别' AFTER `cadre_id`;

ALTER TABLE `cet_unit_train`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性' AFTER `title`;

ALTER TABLE `dispatch_work_file_auth`
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，每个文件按照职务属性设置权限。' AFTER `work_file_id`;


ALTER TABLE `cet_project_obj`
	CHANGE COLUMN `type_id` `admin_level` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `title`,
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `admin_level`;

更新
sc_committee_vote_view

dispatch_cadre_view

unit_post_count_view

unit_view

cadre_view
crs_candidate_view
cadre_inspect_view
cadre_reserve_view

unit_post_view

cet_project_obj_view


*行政级别 增加 正科级、副科级


20190215
更新 西交大、南航


20190214
更新 sc_record_view

drop view cis_inspector_view;

*民主推荐 -> mt_sctype_dr


ALTER TABLE `unit_post`
	ADD UNIQUE INDEX `code` (`code`);

20190209

ALTER TABLE `sc_committee_topic`
	ADD COLUMN `unit_ids` VARCHAR(300) NULL COMMENT '涉及单位，逗号分隔' AFTER `name`;

更新 sc_committee_topic_view

ALTER TABLE `sc_motion`
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NOT NULL COMMENT '拟调整岗位' AFTER `num`,
	CHANGE COLUMN `sc_type` `sc_type` INT(10) UNSIGNED NOT NULL COMMENT '干部选任方式，关联元数据' AFTER `unit_post_id`,
	DROP COLUMN `unit_id`,
	DROP COLUMN `type`,
	DROP COLUMN `post_count`;

drop table sc_motion_post;

ALTER TABLE `sc_motion`
	CHANGE COLUMN `num` `seq` VARCHAR(50) NOT NULL COMMENT '动议编号' AFTER `hold_date`,
	DROP INDEX `year_num`,
	ADD UNIQUE INDEX `seq` (`seq`);

ALTER TABLE `sc_motion`
	ADD COLUMN `topics` VARCHAR(300) NULL DEFAULT NULL COMMENT '关联议题，干部小组会议题或党委常委会议题' AFTER `way_other`;

+ sc_motion_view

ALTER TABLE `cet_train_course`
	ADD COLUMN `sign_token` VARCHAR(32) NULL DEFAULT NULL COMMENT '刷卡签到页面token' AFTER `address`,
	ADD COLUMN `sign_token_expire` DATETIME NULL DEFAULT NULL COMMENT 'token有效截止时间' AFTER `sign_token`;

ALTER TABLE `cet_train_course`
	CHANGE COLUMN `sign_token_expire` `sign_token_expire` BIGINT NULL DEFAULT NULL COMMENT 'token有效截止时间' AFTER `sign_token`;


更新 common-utils


drop view cet_project_obj_cadre_view;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `cadre_id` INT UNSIGNED NULL DEFAULT NULL AFTER `remark`,
	ADD COLUMN `title` VARCHAR(200) NULL DEFAULT NULL AFTER `cadre_id`,
	ADD COLUMN `type_id` INT UNSIGNED NULL DEFAULT NULL AFTER `title`,
	ADD COLUMN `post_id` INT UNSIGNED NULL DEFAULT NULL AFTER `type_id`,
	ADD COLUMN `is_ow` TINYINT(1) UNSIGNED NULL DEFAULT NULL AFTER `post_id`,
	ADD COLUMN `ow_grow_time` DATE NULL DEFAULT NULL AFTER `is_ow`,
	ADD COLUMN `dp_grow_time` DATE NULL DEFAULT NULL AFTER `ow_grow_time`,
	ADD COLUMN `dp_type_id` INT UNSIGNED NULL DEFAULT NULL AFTER `dp_grow_time`,
	ADD COLUMN `pro_post` VARCHAR(200) NULL DEFAULT NULL AFTER `dp_type_id`,
	ADD COLUMN `lp_work_time` DATE NULL DEFAULT NULL AFTER `pro_post`,
	ADD COLUMN `mobile` VARCHAR(20) NULL DEFAULT NULL AFTER `lp_work_time`,
	ADD COLUMN `email` VARCHAR(200) NULL DEFAULT NULL AFTER `mobile`,
	ADD COLUMN `cadre_status` TINYINT(3) UNSIGNED NULL DEFAULT NULL AFTER `email`,
	ADD COLUMN `cadre_sort_order` INT UNSIGNED NULL DEFAULT NULL AFTER `cadre_status`;

更新 cet_project_obj_view


drop view cet_trainee_course_cadre_view;

更新 cet_trainee_course_view

ALTER TABLE `ow_branch_member`
  ADD COLUMN `assign_date` DATE NULL DEFAULT NULL COMMENT '任职时间，具体到月' AFTER `type_id`,
  ADD COLUMN `office_phone` VARCHAR(50) NULL DEFAULT NULL COMMENT '办公电话' AFTER `assign_date`,
  ADD COLUMN `mobile` VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号' AFTER `office_phone`;

+ ow_branch_member_view

更新 ow_branch_view
ow_branch_member_group_view
ow_party_member_group_view

ow_member_apply_view


ALTER TABLE `sc_motion`
  CHANGE COLUMN `way` `way` TINYINT(3) UNSIGNED NOT NULL COMMENT '动议形式，党委干部工作小组会、 党委常委会、 其他' AFTER `sc_type`;

更新 sc_motion_view


20190122

删除 cet_trainee_cadre_view

ALTER TABLE `cet_annual_obj`
	ADD UNIQUE INDEX `annual_id_user_id` (`annual_id`, `user_id`);

ALTER TABLE `pmd_order`
	CHANGE COLUMN `sign` `sign` VARCHAR(300) NULL DEFAULT NULL COMMENT '签名' AFTER `params`;

ALTER TABLE `pmd_member_pay`
	CHANGE COLUMN `order_no` `order_no` VARCHAR(30) NULL DEFAULT NULL COMMENT '缴费订单号，批量缴费时允许重复' AFTER `member_id`;


更新 common-utils
+ bnu.newpay.jar

ALTER TABLE `ext_retire_salary`
	CHANGE COLUMN `ltxf` `base` DECIMAL(10,2) NULL DEFAULT NULL COMMENT '党费计算基数' AFTER `rq`;


20190109
ALTER TABLE `cet_annual_obj`
	ADD COLUMN `need_update_require` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '同步信息时检测行政级别是否变更，如果变更，则必须修改年度学习任务后进行信息同步' AFTER `admin_level`,
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NOT NULL COMMENT '排序，同步干部库中的顺序' AFTER `is_quit`;

ALTER TABLE `dispatch_unit`
	DROP COLUMN `is_unit`;

更新 common-tools

20190108

更新  common-utils


20190107
update cet_annual_obj set finish_period=0;
ALTER TABLE `cet_annual_obj`
	CHANGE COLUMN `finish_period` `finish_period` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已完成学时数' AFTER `period`;

20190102
ALTER TABLE `sc_subsidy`
	CHANGE COLUMN `hr_type` `hr_type` INT(10) UNSIGNED NULL COMMENT '发人事处文号，关联年度类型' AFTER `year`,
	CHANGE COLUMN `fe_type` `fe_type` INT(10) UNSIGNED NULL COMMENT '发财经处文号，关联年度类型' AFTER `hr_type`,
	CHANGE COLUMN `hr_num` `hr_num` INT(10) UNSIGNED NULL COMMENT '人事处编号' AFTER `fe_type`,
	CHANGE COLUMN `fe_num` `fe_num` INT(10) UNSIGNED NULL COMMENT '财经处编号' AFTER `hr_num`;

ALTER TABLE `cet_plan_course_obj`
	CHANGE COLUMN `is_finished` `is_finished` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否结业，针对上级网上专题班，是否完成，针对自主学习' AFTER `num`;

更新 cadre_view
crs_candidate_view
cadre_inspect_view
cadre_reserve_view


20190102
ALTER TABLE `cet_project_obj`
	ADD COLUMN `finish_period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '已完成学时数，每天统计一次当年的记录、可手动刷新' AFTER `should_finish_period`;

更新 cet_project_obj_view   cet_project_obj_cadre_view

ALTER TABLE `cet_upper_train`
	ADD COLUMN `year` INT(10) UNSIGNED NOT NULL COMMENT '年度' AFTER `id`;

ALTER TABLE `cet_project`
	ADD COLUMN `is_valid` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否计入年度学习任务' AFTER `name`;

更新 cet_project_view

update cet_project set is_valid=1;

update cet_upper_train set year=2018;

更新common-utils


20181226
ALTER TABLE `sys_user_reg`
	COMMENT='校外账号注册';
RENAME TABLE `sys_user_reg` TO `ow_member_reg`;

sysUserReg:* -> memberReg:*
20181226

创建新表 pmd_order  pmd_notify

把 pmd_order_campuscard 数据导入 pmd_order
INSERT INTO `pmd_order` (`sn`, `member_id`, `is_batch`, `pay_month`, `payer`, `payername`, `amt`, `sign`, `user_id`, `is_success`, `is_closed`, `create_time`, `ip`)
select `sn`, `member_id`, `is_batch`, `pay_month`, `payer`, `payername`, `amt`, `sign`, `user_id`, `is_success`, `is_closed`, `create_time`, `ip` from pmd_order_campuscard;

更新 pmd_pay_view


20181224
role_admin1 -> role_super


20181221

ALTER TABLE `cadre_eva`
	ADD COLUMN `title` VARCHAR(100) NULL COMMENT '时任职务' AFTER `year`;

更新common-utils

20181213
-- 修改 use_passport 默认值为 -1
select  use_passport, count(*) from abroad_passport_draw group by use_passport;

select d.id, d.return_date, d.passport_id, d.type, d.use_passport, p.type as p_type, p.cancel_confirm from abroad_passport_draw d
left join abroad_passport p on p.id=d.passport_id
where d.is_deleted=0 and d.draw_status=1 and (d.use_passport!=2 or d.use_passport is null) and d.return_date <= now()
  and (p.type=1 or (p.type=2 and p.cancel_confirm=0));
-- 验证
select d.id, d.return_date, d.passport_id, d.type, d.use_passport, p.type as p_type, p.cancel_confirm from abroad_passport_draw d
left join abroad_passport p on p.id=d.passport_id
where d.is_deleted=0 and d.draw_status=1 and d.use_passport!=2 and d.return_date <= now()
  and (p.type=1 or (p.type=2 and p.cancel_confirm=0));

update abroad_passport_draw set use_passport=111 where use_passport is null;

ALTER TABLE `abroad_passport_draw`
	CHANGE COLUMN `use_passport` `use_passport` TINYINT(3) NOT NULL DEFAULT '-1' COMMENT '归还证件处理类别， 因私出国、因公赴台长期（1：持证件出国（境） 0：未持证件出国（境） 2：拒不交回证件） 处理其他事务（1：违规使用证件出国（境）0：没有使用证件出国（境） 2：拒不交回证件）' AFTER `attachment_filename`;

update 	abroad_passport_draw set use_passport=-1 where use_passport =111;


20181212
ALTER TABLE `unit_team`
	CHANGE COLUMN `year` `year` INT(10) UNSIGNED NOT NULL COMMENT '届数' AFTER `unit_id`,
	DROP INDEX `year`,
	ADD UNIQUE INDEX `unit_id_year` (`unit_id`, `year`);




20181208
ALTER TABLE `cet_upper_train`
	COMMENT='上级调训或二级单位组织培训',
	ADD COLUMN `upper_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '组织单位类型，1 上级调训  2 二级单位组织培训' AFTER `id`;

ALTER TABLE `cet_upper_train_admin`
	COMMENT='单位管理员，上级调训或二级单位组织培训',
	ADD COLUMN `upper_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '组织单位类型，1 上级调训  2 二级单位组织培训' AFTER `id`;

update cet_upper_train set upper_type=1;

update cet_upper_train_admin set upper_type=1;

ALTER TABLE `sys_resource`
	CHANGE COLUMN `permission` `permission` VARCHAR(200) NULL DEFAULT NULL COMMENT '权限字符串' AFTER `is_leaf`;

20181207
ALTER TABLE `unit_team`
	CHANGE COLUMN `seq` `year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '届数' AFTER `unit_id`,
	ADD COLUMN `is_present` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否现任班子' AFTER `year`,
	ADD UNIQUE INDEX `year` (`year`);

修改 common-utils.jar


20181207
ALTER TABLE `pmd_order_campuscard`
	CHANGE COLUMN `payername` `payername` VARCHAR(50) NULL DEFAULT NULL COMMENT '缴费人姓名' AFTER `payertype`;

更新 unit_post_count_view， unit_post_view

-- 20181203新建南航

20181201

更换 ehcache 2.10.6（需要删除所有缓存）

ALTER TABLE `unit_admin_group`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NOT NULL COMMENT '所属单位' AFTER `id`,
	ADD COLUMN `seq` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '届数' AFTER `unit_id`,
	CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL COMMENT '班子名称，默认单位名称，可修改' AFTER `seq`,
	CHANGE COLUMN `tran_time` `expect_depose_date` DATE NULL DEFAULT NULL COMMENT '应换届时间' AFTER `name`,
	CHANGE COLUMN `dispatch_unit_id` `appoint_dispatch_unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '任职文件，关联单位发文' AFTER `expect_depose_date`,
	CHANGE COLUMN `appoint_time` `appoint_date` DATE NOT NULL COMMENT '任职时间，默认任职文件的任免日期，可修改' AFTER `appoint_dispatch_unit_id`,
	ADD COLUMN `depose_dispatch_unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '免职文件，关联单位发文' AFTER `appoint_date`,
	CHANGE COLUMN `actual_tran_time` `depose_date` DATE NULL DEFAULT NULL COMMENT '免职时间，即实际换届时间，默认免职文件的任免日期，可修改' AFTER `depose_dispatch_unit_id`,
	ADD COLUMN `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `depose_date`,
	DROP COLUMN `fid`,
	DROP COLUMN `is_present`,
	DROP PRIMARY KEY,
	DROP INDEX `FK_base_unit_admin_group_base_unit_admin_group`,
	DROP INDEX `FK_base_unit_admin_group_base_unit`,
	ADD PRIMARY KEY (`id`),
	DROP FOREIGN KEY `FK_base_unit_admin_group_base_unit_admin_group`,
	DROP FOREIGN KEY `FK_base_unit_admin_group_base_unit`;

DROP TABLE `unit_admin`;

RENAME TABLE `unit_admin_group` TO `unit_team`;


ALTER TABLE `unit_team`
	CHANGE COLUMN `appoint_dispatch_unit_id` `appoint_dispatch_cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '任职文件，关联任免信息，院长任命' AFTER `expect_depose_date`,
	CHANGE COLUMN `depose_dispatch_unit_id` `depose_dispatch_cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '免职文件，关联单位发文' AFTER `appoint_date`;


修改权限：
delete from sys_resource where permission = 'unitAdmin:*';
update sys_resource set permission='unitTeam:*', name='单位班子管理', is_leaf=1 where permission = 'unitAdminGroup:*';


更新 common-utils


ALTER TABLE `base_meta_class`
	ADD COLUMN `extra_options` VARCHAR(200) NULL DEFAULT NULL COMMENT '附加属性选项，如果存在此值，则附加属性是一个下拉选；格式： jg|机关,xy|学院' AFTER `extra_attr`;

更新 dispatch_cadre_view


给 职务属性 mc_post 添加附加属性 所属班子成员（dw|党委班子成员,xz|行政班子成员）

ALTER TABLE `sys_config`
	ADD COLUMN `term_start_date` DATE NULL DEFAULT NULL COMMENT '本学期起始时间' AFTER `school_email`,
	ADD COLUMN `term_end_date` DATE NULL DEFAULT NULL COMMENT '本学期结束时间' AFTER `term_start_date`;


20181129
更新 dispatch_cadre_view
     dispatch_unit_view

select * from unit where file_path is not null;

update unit set file_path = null;

ALTER TABLE `unit`
	CHANGE COLUMN `file_path` `dispatch_unit_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '成立文件，关联内设机构调整文件' AFTER `type_id`,
	CHANGE COLUMN `work_time` `work_time` DATETIME NULL DEFAULT NULL COMMENT '成立时间，默认同成立文件的发文日期，可修改' AFTER `dispatch_unit_id`,
	CHANGE COLUMN `url` `url` VARCHAR(200) NULL DEFAULT NULL COMMENT '单位网址' AFTER `work_time`;

更新 unit_view

更新 common-utils

20181128
ALTER TABLE `dispatch`
	ADD COLUMN `category` VARCHAR(10) NOT NULL COMMENT '文件属性，1 干部任免文件 2 内设机构调整文件 3 组织机构调整文件，可同时是两种' AFTER `code`;

update dispatch set category=1;

更新 dispatch_view

ALTER TABLE `dispatch`
	CHANGE COLUMN `work_time` `work_time` DATE NULL COMMENT '任免日期' AFTER `pub_time`;

-- 更新前检查一下 dispatch_unit是否已有数据
select count(*) from dispatch_unit;

ALTER TABLE `dispatch_unit`
	ADD COLUMN `is_unit` TINYINT(1) UNSIGNED NOT NULL COMMENT '机构类型， 1 内设机构 0 组织机构 ，与发文类型保持同步' AFTER `dispatch_id`,
	CHANGE COLUMN `type_id` `type` INT(10) UNSIGNED NOT NULL COMMENT '调整方式，关联元数据' AFTER `is_unit`,
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '新成立机构名称，关联单位或党委' AFTER `type`,
	ADD COLUMN `old_unit_id` INT(10) UNSIGNED NULL COMMENT '撤销机构名称，关联单位或党委' AFTER `unit_id`,
	DROP COLUMN `year`,
	DROP COLUMN `sort_order`,
	DROP INDEX `FK_dispatch_unit_base_unit`,
	DROP INDEX `FK_dispatch_unit_base_meta_type`,
	DROP FOREIGN KEY `FK_dispatch_unit_base_unit`,
	DROP FOREIGN KEY `FK_dispatch_unit_base_meta_type`;


sys_resource: 单位发文管理 改为 机构调整信息，放在组织部发文二级菜单中

mc_dispatch_unit -> mc_dispatch_unit_type 和对应的值

20181123
更新 common-utils

+ sc_border_view

更换 itext-2.1.7.jar

20181121
+ sc_matter_view

ALTER TABLE `sc_matter_check`
	ADD COLUMN `files` VARCHAR(500) NULL DEFAULT NULL COMMENT '核查文件，中组部，多个文件' AFTER `check_file_name`;

更新 sc_matter_check_view

ALTER TABLE `sc_matter_check_item`
	ADD COLUMN `check_reason` VARCHAR(300) NULL DEFAULT NULL COMMENT '认定依据' AFTER `confirm_date`;

更新 sc_matter_check_item_view


ALTER TABLE `cadre_company`
	CHANGE COLUMN `type` `type` INT UNSIGNED NULL DEFAULT NULL COMMENT '兼职类型，关联元数据' AFTER `cadre_id`,
	CHANGE COLUMN `post` `post` VARCHAR(100) NULL DEFAULT NULL COMMENT '兼任职务' AFTER `unit`,
	CHANGE COLUMN `start_time` `start_time` DATE NULL DEFAULT NULL COMMENT '兼职起始时间' AFTER `post`,
	ADD COLUMN `is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否结束' AFTER `start_time`,
	ADD COLUMN `finish_time` DATE NULL DEFAULT NULL COMMENT '兼职结束时间' AFTER `is_finished`,
	CHANGE COLUMN `report_unit` `approval_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '审批单位' AFTER `finish_time`,
	ADD COLUMN `approval_date` DATE NULL DEFAULT NULL COMMENT '批复日期' AFTER `approval_unit`,
	CHANGE COLUMN `paper` `approval_file` VARCHAR(200) NULL DEFAULT NULL COMMENT '批复文件' AFTER `approval_date`,
	CHANGE COLUMN `paper_filename` `approval_filename` VARCHAR(200) NULL DEFAULT NULL COMMENT '批复文件名称' AFTER `approval_file`,
	CHANGE COLUMN `has_pay` `has_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否取酬' AFTER `approval_filename`,
	ADD COLUMN `has_hand` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '所取酬劳是否全额上交学校' AFTER `has_pay`;

兼职类型（1企业兼职 2社团兼职 3其他） 改为从元数据获取
（根据实际ID修改）
update cadre_company set type=497 where type=1;
update cadre_company set type=494 where type=2;
update cadre_company set type=499 where type=3;

+ cadre_company_view

+ cadreCompany:finish  X 没有加 ???

20181118
-- 兼职排序有误
update cadre_post cp,
 (select cadre_id from  cadre_post where is_main_post=0  group by cadre_id, sort_order having count(*)>1) t
 set cp.sort_order = cp.id where cp.cadre_id = t.cadre_id and cp.is_main_post=0;



20181116
ALTER TABLE `unit_post`
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '岗位级别，关联元数据，对应干部的行政级别' AFTER `is_principal_post`;

ALTER TABLE `unit_post`
	ADD COLUMN `open_date` DATE NULL DEFAULT NULL COMMENT '空缺起始时间' AFTER `abolish_date`;

更新 unit_post_view
+ unit_post_count_view

更新 unit_view

删除 表 cpc_allocation

删除缓存 unitPosts

修改 资源 干部配备一览表（/unitPostAllocation	unitPost:allocation）

更新common-utils.jar


20181115

ALTER TABLE `dispatch_cadre`
	COMMENT='干部任免信息';

ALTER TABLE `cadre_post`
	ADD UNIQUE INDEX `unit_post_id` (`unit_post_id`);

更新 unit_post_view



20181115

ALTER TABLE `dispatch_cadre`
	ADD COLUMN `unit_post_id` INT UNSIGNED NULL COMMENT '关联干部岗位' AFTER `type`;

	更新 dispatch_cadre_view

	ALTER TABLE `sc_committee_vote`
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联干部岗位' AFTER `type`;


	+ sc_committee_topic_cadre
	(ALTER TABLE `sc_committee_topic_cadre` ADD UNIQUE INDEX `topic_id_cadre_id` (`topic_id`, `cadre_id`);)

	insert into sc_committee_topic_cadre(topic_id, cadre_id, original_post,original_post_time)
	select topic_id, cadre_id, original_post,original_post_time from sc_committee_vote;


  ALTER TABLE `sc_committee_vote`
	DROP COLUMN `original_post`,
	DROP COLUMN `original_post_time`;

	更新 sc_committee_vote_view


ALTER TABLE `cadre_post`
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联干部岗位' AFTER `cadre_id`;


更新 unit_post_view

干部类型
mc_dispatch_cadre_type -> mt_dispatch_cadre_dw, mt_dispatch_cadre_xz

职务类别
mc_post_class  -> mt_post_dw, mt_post_xz

ALTER TABLE `sc_ad_archive`
	ADD COLUMN `adform_save_time` DATETIME NULL DEFAULT NULL COMMENT '任免表归档时间' AFTER `adform`,
	ADD COLUMN `cis_save_time` DATETIME NULL COMMENT '干部考察报告归档时间' AFTER `cis`;

	更新 sc_ad_archive_view

20181114
更新 sc_dispatch_view

20181107
ALTER TABLE `pcs_committee_member`
	ADD COLUMN `quit_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '离任文件' AFTER `quit_date`;

	更新 pcs_committee_member_view

	更新 common-utils

ALTER TABLE `sys_role`
	ADD COLUMN `user_count` INT UNSIGNED NULL COMMENT '权限拥有人数，当前拥有该角色的正常账号数，系统每小时自动统计' AFTER `m_resource_ids`;


ALTER TABLE `sc_committee_vote`
	CHANGE COLUMN `original_post` `original_post` VARCHAR(200) NULL DEFAULT NULL COMMENT '原任职务，任命才有' AFTER `type`,
	CHANGE COLUMN `post` `post` VARCHAR(200) NOT NULL COMMENT '职务' AFTER `procedure_id`;

20181105
ALTER TABLE `pcs_candidate_chosen`
	CHANGE COLUMN `config_id` `config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会' AFTER `id`;

+ pcs_committee_member
创建视图        pcs_committee_member_view

insert into pcs_committee_member(type,user_id) select 0, user_id  from cadre where is_committee_member=1 order by sort_order asc;

update pcs_committee_member set sort_order = id;

ALTER TABLE `cadre`
	DROP COLUMN `is_committee_member`;

更新 cadre_view  cadre_inspect_view    cadre_reserve_view  crs_candidate_view

ALTER TABLE `sc_committee`
	ADD COLUMN `committee_member_count` INT(10) UNSIGNED NOT NULL COMMENT '常委总数' AFTER `topic_num`;

ALTER TABLE `sc_committee`
	ADD COLUMN `ppt_file` VARCHAR(100) NULL DEFAULT NULL COMMENT '上会PPT' AFTER `log_file`;

update  sc_committee set committee_member_count= (select count(*) from pcs_committee_member where type=0 and is_quit=0);

ALTER TABLE `sc_committee_vote`
	CHANGE COLUMN `aggree_count` `agree_count` INT(10) UNSIGNED NOT NULL COMMENT '表决同意票数' AFTER `unit_id`,
	ADD COLUMN `abstain_count` INT(10) UNSIGNED NOT NULL COMMENT '表决弃权票数' AFTER `agree_count`,
	ADD COLUMN `disagree_count` INT(10) UNSIGNED NOT NULL COMMENT '表决反对票数' AFTER `abstain_count`;

更新 sc_committee_view  sc_committee_topic_view  sc_committee_vote_view   sc_committee_other_vote_view

sc_committee_member_view

ALTER TABLE `sc_committee_topic`
	ADD COLUMN `memo` LONGTEXT NULL COMMENT '议题讨论备忘' AFTER `content`;

ALTER TABLE `sc_committee_topic`
	ADD COLUMN `seq` INT(10) UNSIGNED NOT NULL COMMENT '议题序号' AFTER `committee_id`;

更新 sc_committee_topic_view


20181102
ALTER TABLE `dispatch`
	ADD UNIQUE INDEX `sc_dispatch_id` (`sc_dispatch_id`);

更新 sc_dispatch_view

ALTER TABLE `dispatch_cadre`
	DROP FOREIGN KEY `FK_dispatch_cadre_dispatch`;
ALTER TABLE `dispatch_cadre`
	ADD CONSTRAINT `FK_dispatch_cadre_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `dispatch` (`id`) ON DELETE CASCADE;

更新common-utils.jar

ALTER TABLE `cis_inspect_obj`
	CHANGE COLUMN `post` `post` VARCHAR(200) NULL DEFAULT NULL COMMENT '考察对象时任职务，默认是所在单位及职务' AFTER `talk_user_count`;

更新 cis_inspect_obj_view

ALTER TABLE `cis_obj_inspector`
	DROP FOREIGN KEY `FK_cis_obj_inspector_cis_inspect_obj`;
ALTER TABLE `cis_obj_inspector`
	ADD CONSTRAINT `FK_cis_obj_inspector_cis_inspect_obj` FOREIGN KEY (`obj_id`) REFERENCES `cis_inspect_obj` (`id`) ON DELETE CASCADE;
ALTER TABLE `cis_obj_unit`
	DROP FOREIGN KEY `FK_cis_obj_unit_cis_inspect_obj`;
ALTER TABLE `cis_obj_unit`
	ADD CONSTRAINT `FK_cis_obj_unit_cis_inspect_obj` FOREIGN KEY (`obj_id`) REFERENCES `cis_inspect_obj` (`id`) ON DELETE CASCADE;


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

