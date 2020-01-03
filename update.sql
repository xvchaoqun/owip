
2020.1.3
北邮

2020.1.1
北邮

2020.1.1
-- 更新 cadre_view等
-- ow_branch_member_view ow_party_member_view

20191230
北邮

20191228

-- INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2712, 0, '党支部考核', '', 'url', '', '/member/memberReport', 260, '0/1/260/', 0, 'owReport:menu', NULL, NULL, NULL, 1, 80);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2717, 0, '党支部书记考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:base', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2718, 0, '党支部考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:base', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `ow_party_report`
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属党支部' AFTER `party_name`,
	ADD COLUMN `branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属党支部名称' AFTER `branch_id`;
ALTER TABLE `ow_member_report`
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属党支部' AFTER `party_name`,
	ADD COLUMN `branch_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '所属党支部名称' AFTER `branch_id`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2526, 0, '介绍信自助打印', '', 'function', '', NULL, 252, '0/1/105/252/', 1, 'memberOutSelfPrint:edit', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `ow_member_out`
	ADD COLUMN `is_self_print` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否允许自助打印' AFTER `status`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `is_self_print_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '自助打印次数' AFTER `is_self_print`;

DROP VIEW IF EXISTS `ow_member_out_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_out_view` AS
select mo.*, m.type as member_type, t.is_retire
from ow_member_out mo, ow_member m
left join sys_teacher_info t on t.user_id = m.user_id where mo.user_id=m.user_id;


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2524, 0, '基本情况登记表', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpInfoForm:list', NULL, NULL, NULL, 1, 90);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2525, 0, '基本情况登记表下载', '', 'function', '', NULL, 2524, '0/1/2574/2524/', 1, 'dpInfoForm:download', NULL, NULL, NULL, 1, NULL);

-- 更新utils.jar

20191224
北邮

20191218
北邮

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `sort_order`, `available`) VALUES (134, NULL, '民族', '1', '1', 'mc_nation', '', '', 58, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '汉族', 'mt_b5rgkm', NULL, NULL, NULL, 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '蒙古族', 'mt_kky2pg', NULL, NULL, NULL, 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '回族', 'mt_bfmvqi', NULL, NULL, NULL, 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '藏族', 'mt_wzihhg', NULL, NULL, NULL, 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '维吾尔族', 'mt_njpnv7', NULL, NULL, NULL, 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '苗族', 'mt_oqenrt', NULL, NULL, NULL, 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '彝族', 'mt_jbeocm', NULL, NULL, NULL, 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '壮族', 'mt_flabtf', NULL, NULL, NULL, 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '布依族', 'mt_igbv9i', NULL, NULL, NULL, 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '朝鲜族', 'mt_6z9mjc', NULL, NULL, NULL, 10, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '满族', 'mt_ctx8cu', NULL, NULL, NULL, 11, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '侗族', 'mt_eiajhb', NULL, NULL, NULL, 12, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '瑶族', 'mt_ayqz6j', NULL, NULL, NULL, 13, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '白族', 'mt_xeqrcy', NULL, NULL, NULL, 14, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '土家族', 'mt_1w1crh', NULL, NULL, NULL, 15, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '哈尼族', 'mt_hgnzeh', NULL, NULL, NULL, 16, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '哈萨克族', 'mt_rkcy8p', NULL, NULL, NULL, 17, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '傣族', 'mt_jd7ci5', NULL, NULL, NULL, 18, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '黎族', 'mt_8zwnfi', NULL, NULL, NULL, 19, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '傈僳族', 'mt_t9zrz0', NULL, NULL, NULL, 20, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '佤族', 'mt_znpagg', NULL, NULL, NULL, 21, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '畲族', 'mt_tmpm8n', NULL, NULL, NULL, 22, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '高山族', 'mt_x4xhkg', NULL, NULL, NULL, 23, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '拉祜族', 'mt_d8vbhh', NULL, NULL, NULL, 24, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '水族', 'mt_ynil0x', NULL, NULL, NULL, 25, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '东乡族', 'mt_jqodio', NULL, NULL, NULL, 26, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '纳西族', 'mt_1jnpli', NULL, NULL, NULL, 27, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '景颇族', 'mt_sh6ses', NULL, NULL, NULL, 28, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '柯尔克孜族', 'mt_9wykpr', NULL, NULL, NULL, 29, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '土族', 'mt_ia1f7z', NULL, NULL, NULL, 30, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '达斡尔族', 'mt_ixa0mb', NULL, NULL, NULL, 31, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '仫佬族', 'mt_gagbua', NULL, NULL, NULL, 32, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '羌族', 'mt_fvu1pi', NULL, NULL, NULL, 33, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '布朗族', 'mt_xfexhz', NULL, NULL, NULL, 34, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '撒拉族', 'mt_kusqao', NULL, NULL, NULL, 35, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '毛难族', 'mt_9xaiam', NULL, NULL, NULL, 36, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '仡佬族', 'mt_g5smwe', NULL, NULL, NULL, 37, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '锡伯族', 'mt_fn8baa', NULL, NULL, NULL, 38, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '阿昌族', 'mt_iow8kc', NULL, NULL, NULL, 39, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '普米族', 'mt_kivlzh', NULL, NULL, NULL, 40, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '塔吉克族', 'mt_0kv9ll', NULL, NULL, NULL, 41, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '怒族', 'mt_eekt6q', NULL, NULL, NULL, 42, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '乌孜别克族', 'mt_sbppkw', NULL, NULL, NULL, 43, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '俄罗斯族', 'mt_aagqzn', NULL, NULL, NULL, 44, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '鄂温克族', 'mt_rxzjxl', NULL, NULL, NULL, 45, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '崩龙族', 'mt_igbwag', NULL, NULL, NULL, 46, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '保安族', 'mt_1c9aoy', NULL, NULL, NULL, 47, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '裕固族', 'mt_xbwocx', NULL, NULL, NULL, 48, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '京族', 'mt_vrsslm', NULL, NULL, NULL, 49, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '塔塔尔族', 'mt_xotm2h', NULL, NULL, NULL, 50, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '独龙族', 'mt_okq0cq', NULL, NULL, NULL, 51, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '鄂伦春族', 'mt_m2qnjb', NULL, NULL, NULL, 52, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '赫哲族', 'mt_60iuud', NULL, NULL, NULL, 53, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '门巴族', 'mt_p3vnvw', NULL, NULL, NULL, 54, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '珞巴族', 'mt_smr6je', NULL, NULL, NULL, 55, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '基诺族', 'mt_4znpcl', NULL, NULL, NULL, 56, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (134, '其他', 'mt_inhxzk', NULL, NULL, NULL, 57, 1);


ALTER TABLE `sys_user_info`
	CHANGE COLUMN `gender` `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1男 2女' AFTER `avatar_upload_time`;

update sys_user_info set gender=null where gender not in(1, 2);

UPDATE `sys_scheduler_job` SET `clazz`='job.member.UpdateIntegrityJob' WHERE  `clazz`='job.member.updateIntegrityJob';
UPDATE `sys_scheduler_job` SET `clazz`='job.party.UpdateIntegrityJob' WHERE  `clazz`='job.party.updateIntegrityJob';
UPDATE `sys_scheduler_job` SET `clazz`='job.branch.UpdateIntegrityJob' WHERE  `clazz`='job.branch.updateIntegrityJob';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3034, 0, '信息完整性汇总', '', 'url', '', '/stat_integrity?cls=1', 260, '0/1/260/', 1, 'partyIntegrity:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3035, 0, '信息完整性汇总', '', 'url', '', '/stat_integrity?cls=2', 260, '0/1/260/', 1, 'branchIntegrity:*', NULL, NULL, NULL, 1, NULL);


DROP VIEW IF EXISTS `ow_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_view` AS
select
m.*, u.source as user_source, u.code, ui.realname, ui.gender, ui.nation, ui.native_place,
ui.birth, ui.idcard, ui.mobile, ui.email, ui.unit, p.unit_id,
p.sort_order as party_sort_order, b.sort_order as branch_sort_order,
mo.status as out_status, mo.handle_time as out_handle_time,

t.education,t.degree,t.degree_time,t.major,t.school,t.school_type, t.degree_school,
t.authorized_type, t.staff_type, t.staff_status, t.on_job, t.main_post_level,
t.post_class, t.post, t.post_level, t.pro_post, t.pro_post_level, t.manage_level, t.office_level,
t.title_level,t.marital_status,t.address,
t.arrive_time, t.work_time, t.from_type, t.talent_type, t.talent_title,
t.is_retire, t.is_honor_retire, t.retire_time, t.is_high_level_talent,

s.delay_year,s.period,s.actual_graduate_time,
s.expect_graduate_time,s.actual_enrol_time,s.sync_source ,s.type as student_type,s.is_full_time,
s.enrol_year,s.grade,s.edu_type,s.edu_way,s.edu_level,s.edu_category,s.xj_status

from ow_member m
left join sys_user_info ui on ui.user_id=m.user_id
left join sys_user u on u.id=m.user_id
left join ow_party p on p.id = m.party_id
left join ow_branch b on b.id = m.branch_id
left join ow_member_out mo on mo.status!=10 and mo.user_id = m.user_id
left join sys_teacher_info t on t.user_id = m.user_id
left join sys_student_info s on s.user_id = m.user_id;

20191212
南航

20191212
-- 提交

20191212
北邮

ALTER TABLE `ow_member`
	CHANGE COLUMN `integrity` `integrity` DECIMAL(10,2) UNSIGNED NULL COMMENT '信息完整度' AFTER `profile`;

ALTER TABLE `ow_party`
	CHANGE COLUMN `integrity` `integrity` DECIMAL(10,2) UNSIGNED NULL COMMENT '信息完整度' AFTER `is_deleted`;

ALTER TABLE `ow_branch`
	CHANGE COLUMN `integrity` `integrity` DECIMAL(10,2) UNSIGNED NULL COMMENT '信息完整度' AFTER `is_deleted`;

-- 转出bug
-- SELECT USER_id FROM ow_member_out WHERE STATUS=2  AND user_id IN (SELECT user_id FROM ow_member WHERE STATUS=1);
UPDATE ow_member om, ow_member_out omo SET om.`status`=4 WHERE omo.user_id=om.user_id AND omo.`status`=2 AND om.`status`=1;

ALTER TABLE `leader_unit`
	CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NOT NULL COMMENT '校级领导，可以从干部库提取，也可以从校领导和党委常委中选择' AFTER `id`;

20191204
北邮

-- 更新 jx.utils.jar
-- jodconverter 两个jar包

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (1150, 0, '党员年度考核信息管理', '', 'function',
                                                                                    '', NULL, 260, '0/1/260/', 1, 'partyEva:*', NULL, NULL, NULL, 1, NULL);


ALTER TABLE `ow_party` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;
ALTER TABLE `ow_member` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `profile`;
ALTER TABLE `ow_branch` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('owCheckIntegrity', '党建是否验证信息完整度', 'false', 3, 49, '');

DELETE FROM `base_meta_type` WHERE  `id`=602;

ALTER TABLE `cet_upper_train`
	ADD COLUMN `upper_train_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训对象类型' AFTER `user_id`;

	ALTER TABLE `cet_trainee_course`
	ADD COLUMN `sign_out_time` DATETIME NULL DEFAULT NULL COMMENT '签退时间' AFTER `sign_time`;

DROP VIEW IF EXISTS `cet_trainee_course_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_trainee_course_view` AS
select cteec.id as id,cteec.trainee_id as trainee_id,cteec.train_course_id as train_course_id,
cteec.can_quit as can_quit,cteec.is_finished as is_finished,cteec.sign_time as sign_time,cteec.sign_out_time as sign_out_time,
cteec.sign_type as sign_type,cteec.remark as remark,cteec.choose_time as choose_time,cteec.choose_user_id as choose_user_id,
cteec.ip as ip,ctee.train_id as train_id,cpo.project_id as project_id,cpo.trainee_type_id as trainee_type_id,
cpo.user_id as user_id,ctc.course_id as course_id,cc.period as period,cp.year as year,uv.code as choose_user_code,uv.realname as choose_user_name
from cet_trainee_course cteec
left join cet_trainee ctee on cteec.trainee_id=ctee.id
left join cet_project_obj cpo on cpo.id=ctee.obj_id
left join cet_train_course ctc on ctc.id=cteec.train_course_id
left join cet_train ct on ct.id=ctee.train_id
left join cet_course cc on cc.id=ctc.course_id
left join cet_project cp on cp.id=cpo.project_id
left join sys_user_view uv on uv.id=cteec.choose_user_id order by cpo.id;


DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count,
pmgtmp2.id as present_group_id, pmgtmp2.appoint_time, pmgtmp2.tran_time, pmgtmp2.actual_tran_time from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_view where type=1 and status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_deleted=0 group by party_id) pmgtmp on pmgtmp.party_id=p.id
LEFT JOIN ow_party_member_group pmgtmp2 ON pmgtmp2.is_present=1 AND pmgtmp2.is_deleted=0 AND pmgtmp2.party_id=p.id;

DROP VIEW IF EXISTS `ow_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_view` AS
select
m.*, u.source as user_source, u.code, ui.realname, ui.gender, ui.nation, ui.native_place,
ui.birth, ui.idcard, ui.mobile, ui.email, ui.unit, p.unit_id,
mo.status as out_status, mo.handle_time as out_handle_time,

t.education,t.degree,t.degree_time,t.major,t.school,t.school_type, t.degree_school,
t.authorized_type, t.staff_type, t.staff_status, t.on_job, t.main_post_level,
t.post_class, t.post, t.post_level, t.pro_post, t.pro_post_level, t.manage_level, t.office_level,
t.title_level,t.marital_status,t.address,
t.arrive_time, t.work_time, t.from_type, t.talent_type, t.talent_title,
t.is_retire, t.is_honor_retire, t.retire_time, t.is_high_level_talent,

s.delay_year,s.period,s.actual_graduate_time,
s.expect_graduate_time,s.actual_enrol_time,s.sync_source ,s.type as student_type,s.is_full_time,
s.enrol_year,s.grade,s.edu_type,s.edu_way,s.edu_level,s.edu_category,s.xj_status

from ow_member m
left join sys_user_info ui on ui.user_id=m.user_id
left join sys_user u on u.id=m.user_id
left join ow_party p on p.id = m.party_id
left join ow_member_out mo on mo.status!=10 and mo.user_id = m.user_id
left join sys_teacher_info t on t.user_id = m.user_id
left join sys_student_info s on s.user_id = m.user_id;


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


20191204
北邮，南航

更新 jx.utils.jar

删除 CadreUnderEdu 相关类
删除 cadreTutor 相关类

20191201
北邮，南航

-- CetAnnualRequire 相关类都删除

UPDATE sys_resource SET permission='memberApply:admin' WHERE permission='memberApply:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
                            VALUES (912, 0, '批量导入发展党员', '', 'function', '', NULL, 211, '0/1/105/211/', 1, 'memberApply:import', NULL, NULL, NULL, 1, NULL);
-- 重新分配党员发展管理模块的权限（党建、分党委、支部）
-- 删除 MemberApplyLaoutController

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (913, 0, '批量导入党员信息', '', 'function', '', NULL, 107, '0/1/105/107/', 1,
                                    'member:import', NULL, NULL, NULL, 1, NULL);
-- 重新分配党员信息管理模块的导入权限（党建、分党委、支部）

20191130

-- 删除 RetireApplyController

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2520, 0, '党员信息采集表权限', '', 'function', '', NULL, 181, '0/1/260/181/', 1, 'memberInfoForm:*', NULL, NULL, NULL, 1, NULL);

-- 添加定时任务 桑文帅
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES ('更新协同任务统计缓存', '', 'job.oa.SyncOaTaskUser', '0 0/2 * * * ?', 1, 1, 29, '2019-11-29 11:03:31');

ALTER TABLE `cet_annual_obj`
	CHANGE COLUMN `period` `period_offline` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '年度学习任务（线下）' AFTER `lp_work_time`,
	ADD COLUMN `period_online` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '年度学习任务（网络）' AFTER `period_offline`,
	CHANGE COLUMN `finish_period` `finish_period_offline` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT '0.0' COMMENT '已完成学时数（线下）' AFTER `period_online`,
	ADD COLUMN `finish_period_online` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT '0.0' COMMENT '已完成学时数（网络）' AFTER `finish_period_offline`,
	CHANGE COLUMN `max_unit_period` `max_unit_period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '二级党委培训学时上限' AFTER `max_daily_period`,
	CHANGE COLUMN `unit_period` `unit_period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '二级党委培训完成学时' AFTER `daily_period`,
	DROP COLUMN `max_party_period`,
	DROP COLUMN `party_period`;

ALTER TABLE `cet_annual_obj`
	DROP COLUMN `max_special_period`,
	DROP COLUMN `max_daily_period`,
	DROP COLUMN `max_unit_period`,
	DROP COLUMN `max_upper_period`;

DROP TABLE `cet_annual_require`;


-- 20191128 jx update

20191122

-- 删除ExportController

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('memberOutNeedOwCheck', '组织关系转出需要组织部审批', 'false', 3, 48, '');
-- 北邮组织关系转出不需要组织部审批
-- update ow_member_out set status=2 where status=1;

UPDATE sys_resource SET permission='memberOut:list' WHERE permission='memberOut:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (910, 0, '编辑组织关系转出', '', 'function', '', NULL, 252, '0/1/105/252/', 1, 'memberOut:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (911, 0, '撤销组织关系转出', '', 'function', '', NULL, 252, '0/1/105/252/', 1, 'memberOut:abolish', NULL, NULL, NULL, 1, NULL);
-- 重新分配转出的权限给 组织部管理员及分党委管理员

ALTER TABLE `sys_user_info`
	ADD COLUMN `post` VARCHAR(200) NULL DEFAULT NULL COMMENT '行政职务，针对非干部' AFTER `mobile`;
DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;

-- 移除的入党申请，应该允许再次提交入党申请或转入申请
-- SELECT user_id FROM ow_enter_apply WHERE TYPE=1 AND STATUS=0 AND user_id IN(SELECT user_id FROM ow_member_apply WHERE is_remove=1 AND stage>=0);
update ow_enter_apply SET STATUS=2 WHERE TYPE=1 AND STATUS=0 AND user_id IN(SELECT user_id FROM ow_member_apply WHERE is_remove=1 AND stage>=0);

201901120
北邮

UPDATE `sys_resource` SET `name`='禁用/解禁账号/赋权', `permission`='sysUser:auth' WHERE  `permission`='sysUser:del';

201901113
北邮

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1100, 0, '各类工作总结', '', 'url', '', '/dispatchWorkFile?type=15', 550, '0/1/61/550/', 1, 'dispatchWorkFile:list:15', NULL, NULL, NULL, 1, NULL);

-- 党建文件：各类工作总结

CREATE TABLE IF NOT EXISTS `sys_msg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '接收人',
  `send_user_id` int(10) unsigned NOT NULL COMMENT '发送人',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '通知发送时间',
  `ip` varchar(50) NOT NULL COMMENT 'ip',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态 1.未读 2.已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='系统提醒';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3031, 0, '系统消息', '', 'url', 'fa fa-info-circle', '/sysMsg?type=2', 1, '0/1/', 1, 'sysMsg:list', NULL, NULL, NULL, 1, 800);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3032, 0, '系统消息管理', '', 'url', '', '/sysMsg?type=1', 21, '0/1/21/', 1, 'sysMsg:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3033, 0, '分党委信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:party', NULL, NULL, NULL, 1, NULL);

201901102
北邮

-- 删除 controller.global.ExportController

REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('birthToDay', '干部出生日期精确到日', 'true', 3, 46, '');
REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('postTimeToDay', '任职(职级)日期精确到日', 'true', 3, 47, '');

-- update base_meta_type SET NAME='xxxx' WHERE NAME = '北京师范大学';

201901023
北邮

/*ALTER TABLE `ow_party_reward`
	ADD COLUMN `reward_level` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '奖励级别' AFTER `reward_time`;

DROP VIEW IF EXISTS `ow_party_reward_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_reward_view` AS
SELECT opr.*,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order,ob.party_id as branch_party_id,om.party_id as user_party_id,om.branch_id as user_branch_id
from ow_party_reward opr
left join ow_party op ON opr.party_id=op.id
left join ow_branch ob ON opr.branch_id=ob.id
left join ow_member om on opr.user_id=om.user_id;*/

CREATE TABLE `ow_party_eva` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '干部',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年份',
	`type` INT(10) UNSIGNED NOT NULL COMMENT '考核情况，关联元数据',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='年度考核记录，支部书记'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=49
;

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (93, NULL, '党内考核结果', '基础党组织信息', '班子', 'mc_party_eva', '', '', '', 2603, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (94, NULL, '党内奖励级别', '基层党组织', '党员', 'mc_party_reward_level', '', '', '', 2604, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (93, '优秀', 'mt_r8ymkc', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (93, '合格', 'mt_ybehle', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (93, '不合格', 'mt_wwqemu', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (94, '国家级', 'mc_party_reward_gjj', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (94, '省部级', 'mc_party_reward_sbj', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (94, '地厅级', 'mc_party_reward_dtj', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (94, '校级奖励', 'mc_party_reward_xj', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (94, '民间奖励', 'mc_party_reward_mj', NULL, '', '', 5, 1);


201901024
南航/ 北航

ALTER TABLE `oa_task_user_file`
	CHANGE COLUMN `file_name` `file_name` VARCHAR(50) NULL AFTER `user_id`,
	CHANGE COLUMN `file_path` `file_path` VARCHAR(100) NULL AFTER `file_name`;


201901023
北邮

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`,
                             `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                             `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                              VALUES (192, 0, '查看分党委、支部委员档案', '', 'function', '', NULL, 181, '0/1/260/181/', 1, 'partyMember:archive', 2, NULL, NULL, 1, NULL);

ALTER TABLE `pmd_branch`
	DROP FOREIGN KEY `FK_pmd_branch_pmd_month`;
ALTER TABLE `pmd_branch`
	ADD CONSTRAINT `FK_pmd_branch_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

	ALTER TABLE `pmd_member_pay`
	DROP FOREIGN KEY `FK_pmd_member_real_pay_pmd_month`;
ALTER TABLE `pmd_member_pay`
	ADD CONSTRAINT `FK_pmd_member_real_pay_pmd_month` FOREIGN KEY (`pay_month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

	ALTER TABLE `pmd_party`
	DROP FOREIGN KEY `FK_pmd_party_pmd_month`;
ALTER TABLE `pmd_party`
	ADD CONSTRAINT `FK_pmd_party_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

	ALTER TABLE `pmd_pay_party`
	DROP FOREIGN KEY `FK_pmd_pay_party_pmd_month`;
ALTER TABLE `pmd_pay_party`
	ADD CONSTRAINT `FK_pmd_pay_party_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

ALTER TABLE `pmd_member`
	DROP FOREIGN KEY `pmd_member_ibfk_1`;
ALTER TABLE `pmd_member`
	ADD CONSTRAINT `pmd_member_ibfk_1` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

ALTER TABLE `pmd_pay_branch`
	DROP FOREIGN KEY `pmd_pay_branch_ibfk_1`;
ALTER TABLE `pmd_pay_branch`
	ADD CONSTRAINT `pmd_pay_branch_ibfk_1` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`) ON DELETE CASCADE;

-- 更新 ow_party_static_view


ALTER TABLE `cet_annual_obj`
	ADD CONSTRAINT `FK_cet_annual_obj_cet_annual` FOREIGN KEY (`annual_id`) REFERENCES `cet_annual` (`id`) ON DELETE CASCADE;

ALTER TABLE `cet_annual_require`
	ADD CONSTRAINT `FK_cet_annual_require_cet_annual` FOREIGN KEY (`annual_id`) REFERENCES `cet_annual` (`id`) ON DELETE CASCADE;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES
                            (202, 0, '分党委、支部委员信息采集表', '', 'function', '', NULL, 181, '0/1/260/181/', 1, 'partyMemberInfoForm:*', NULL, NULL, NULL, 1, NULL);

-- 执行 /test/party_cadre.jsp

201901015
南航

201901011
南航
ALTER TABLE `cr_applicant`
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，弃用' AFTER `second_check_remark`,
	ADD COLUMN `submit_time` DATETIME NULL DEFAULT NULL COMMENT '提交时间' AFTER `has_submit`,
	ADD COLUMN `has_report` TINYINT(1) UNSIGNED NULL DEFAULT 0 COMMENT '纸质表是否已交' AFTER `submit_time`;

201901011
哈工大/北航/南航

201901010
南航

-- 校外挂职： 所任职务 -> 所任单位及职务， 需手动合并一下数据库字段（除南航）
-- SELECT unit ,post, CONCAT(unit, post) FROM crp_record WHERE TYPE=1;
-- UPDATE crp_record SET post=CONCAT(unit, post) WHERE TYPE=1;

ALTER TABLE `cr_meeting`
	CHANGE COLUMN `apply_count` `require_num` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '招聘会人数要求' AFTER `post_ids`,
	ADD COLUMN `apply_num` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '应聘人数，汇总岗位对应的应聘人数' AFTER `require_num`;

ALTER TABLE `cr_post`
	ADD COLUMN `require_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '岗位要求' AFTER `info_id`;

ALTER TABLE `cr_info`
	DROP COLUMN `require_id`;

-- 删除 干部竞争上岗-统计分析的 角色

-- 更新 cadre_view等

201901005
南航

ALTER TABLE `ow_party_member`
	ADD COLUMN `dismiss_date` DATE NULL DEFAULT NULL COMMENT '离任时间' AFTER `assign_date`,
	CHANGE COLUMN `is_admin` `is_admin` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否管理员' AFTER `mobile`,
	ADD COLUMN `is_history` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否离任' AFTER `is_admin`;

ALTER TABLE `ow_branch_member`
	ADD COLUMN `dismiss_date` DATE NULL DEFAULT NULL COMMENT '离任时间' AFTER `assign_date`,
	CHANGE COLUMN `is_admin` `is_admin` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否管理员' AFTER `mobile`,
	ADD COLUMN `is_history` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否离任' AFTER `is_admin`;

DROP VIEW IF EXISTS `ow_party_member_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_member_view` AS
select opm.*,
`ui`.`msg_title` AS `msg_title`
	,`ui`.`email` AS `email`
	,`ui`.`realname` AS `realname`
	,`ui`.`gender` AS `gender`
	,`ui`.`nation` AS `nation`
	,`ui`.`native_place` AS `native_place`
	,`ui`.`idcard` AS `idcard`
	,`ui`.`birth` AS `birth`
	,`om`.`party_id` AS `party_id`
	,`om`.`branch_id` AS `branch_id`
	,`om`.`status` AS `member_status`
	, opmg.party_id as group_party_id, opmg.is_present, opmg.is_deleted
	, op.unit_id
	, op.sort_order as party_sort_order
	,`t`.`post_class` AS `post_class`
	,`t`.`sub_post_class` AS `sub_post_class`
	,`t`.`main_post_level` AS `main_post_level`
	,`t`.`pro_post_time` AS `pro_post_time`
	,`t`.`pro_post_level` AS `pro_post_level`
	,`t`.`pro_post_level_time` AS `pro_post_level_time`
	,`t`.`pro_post` AS `pro_post`
	,`t`.`manage_level` AS `manage_level`
	,`t`.`manage_level_time` AS `manage_level_time`
	,`t`.`arrive_time` AS `arrive_time`
	, ow.id as ow_id
	-- 判断是否是中共党员
	, if(!isnull(ow.id) or om.status=1 or om.status=4, 1, 0) as is_ow
	-- 优先以党员库中的入党时间为准
	, if(om.status=1 or om.status=4, om.grow_time, ow.grow_time) as ow_grow_time
	, ow.remark as ow_remark
	, dp.grow_time as dp_grow_time
  , dp.class_id as dp_type_id
	 from  ow_party_member opm  join ow_party_member_group opmg on opm.group_id=opmg.id
 left join sys_user_info ui on opm.user_id=ui.user_id
 left join ow_member om on opm.user_id=om.user_id
 left join ow_party op on opmg.party_id=op.id
 left join sys_teacher_info t on opm.user_id=t.user_id
left join cadre_party dp on dp.user_id= opm.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= opm.user_id and ow.type = 2;

-- ----------------------------
--  Records
-- ----------------------------

DROP VIEW IF EXISTS `ow_branch_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_branch_member_view` AS select obm.*,
`ui`.`msg_title` AS `msg_title`
,`ui`.`email` AS `email`
,`ui`.`realname` AS `realname`
,`ui`.`gender` AS `gender`
,`ui`.`nation` AS `nation`
,`ui`.`native_place` AS `native_place`
,`ui`.`idcard` AS `idcard`
,`ui`.`birth` AS `birth`
,`om`.`party_id` AS `party_id`
,`om`.`branch_id` AS `branch_id`
,`om`.`status` AS `member_status`
, obmg.branch_id as group_branch_id, obmg.is_present,obmg.is_deleted
, op.id as group_party_id
, op.unit_id
, op.sort_order as party_sort_order
, ob.sort_order as branch_sort_order
,`t`.`post_class` AS `post_class`
,`t`.`sub_post_class` AS `sub_post_class`
,`t`.`main_post_level` AS `main_post_level`
,`t`.`pro_post_time` AS `pro_post_time`
,`t`.`pro_post_level` AS `pro_post_level`
,`t`.`pro_post_level_time` AS `pro_post_level_time`
,`t`.`pro_post` AS `pro_post`
,`t`.`manage_level` AS `manage_level`
,`t`.`manage_level_time` AS `manage_level_time`
,`t`.`arrive_time` AS `arrive_time`
, ow.id as ow_id
-- 判断是否是中共党员
, if(!isnull(ow.id) or om.status=1 or om.status=4, 1, 0) as is_ow
-- 优先以党员库中的入党时间为准
, if(om.status=1 or om.status=4, om.grow_time, ow.grow_time) as ow_grow_time
, ow.remark as ow_remark
, dp.grow_time as dp_grow_time
, dp.class_id as dp_type_id
from  ow_branch_member obm  join ow_branch_member_group obmg on obm.group_id=obmg.id
left join sys_user_info ui on obm.user_id=ui.user_id
left join ow_member om on obm.user_id=om.user_id
left join ow_branch ob on obmg.branch_id=ob.id
left join ow_party op on ob.party_id=op.id
left join sys_teacher_info t on obm.user_id=t.user_id
left join cadre_party dp on dp.user_id= obm.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= obm.user_id and ow.type = 2;


ALTER TABLE `ow_party`
	ADD COLUMN `is_pycj` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否培育创建单位' AFTER `create_time`,
	ADD COLUMN `pycj_date` DATE NULL DEFAULT NULL COMMENT '评选培育创建单位时间' AFTER `is_pycj`,
	ADD COLUMN `is_bg` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否标杆院系' AFTER `pycj_date`,
	ADD COLUMN `bg_date` DATE NULL DEFAULT NULL COMMENT '评选标杆院系时间' AFTER `is_bg`;

ALTER TABLE `ow_branch`
	ALTER `type_id` DROP DEFAULT;
ALTER TABLE `ow_branch`
	CHANGE COLUMN `type_id` `types` VARCHAR(300) NULL COMMENT '支部类型，关联元数据，多选' AFTER `party_id`,
	DROP INDEX `FK_ow_branch_base_meta_type`,
	DROP FOREIGN KEY `FK_ow_branch_base_meta_type`;

DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count,
pmgtmp2.id as present_group_id, pmgtmp2.appoint_time, pmgtmp2.tran_time, pmgtmp2.actual_tran_time from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_view where type=1 and status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_deleted=0 group by party_id) pmgtmp on pmgtmp.party_id=p.id
LEFT JOIN ow_party_member_group pmgtmp2 ON pmgtmp2.is_present=1 AND pmgtmp2.is_deleted=0 AND pmgtmp2.party_id=p.id;

-- ----------------------------
--  View definition for `ow_branch_view`
-- ----------------------------
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


DROP VIEW IF EXISTS `ow_party_member_group_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_member_group_view` AS
select opmg.*, op.sort_order as party_sort_order, count(opm.id) as member_count from ow_party_member_group opmg
left join ow_party_member opm on opm.is_history=0 and opm.group_id=opmg.id
left join  ow_party op on opmg.party_id=op.id group by opmg.id;
DROP VIEW IF EXISTS `ow_branch_member_group_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_branch_member_group_view` AS
SELECT bmg.`*`, b.party_id, p.sort_order as party_sort_order, b.sort_order as branch_sort_order, count(obm.id) as member_count
from ow_branch_member_group bmg
left join ow_branch_member obm on obm.is_history=0 and obm.group_id=bmg.id
left join ow_branch b on bmg.branch_id=b.id
left join ow_party p on b.party_id=p.id group by bmg.id;


DROP TABLE crs_applicant_adjust;
DROP VIEW crs_applicant_adjust_view;
-- 删除以上对应的代码

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1080, 0, '干部竞争上岗2', '', 'menu', '', NULL, 339, '0/1/339/', 0, 'cr:admin', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1081, 0, '资格审核模板', '', 'url', '', '/crRequire', 1080, '0/1/339/1080/', 1, 'crRequire:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1082, 0, '招聘管理', '', 'url', '', '/crInfo', 1080, '0/1/339/1080/', 0, 'crInfo:*', NULL, NULL, NULL, 1, 1000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1083, 0, '岗位管理', '', 'function', '', NULL, 1082, '0/1/339/1080/1082/', 1, 'crPost:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1084, 0, '应聘人管理', '', 'function', '', NULL, 1082, '0/1/339/1080/1082/', 1, 'crApplicant:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1085, 0, '招聘会管理', '', 'function', '', NULL, 1082, '0/1/339/1080/1082/', 1, 'crMeeting:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1086, 0, '干部竞争上岗2', '', 'menu', 'fa fa-crosshairs', NULL, 1, '0/1/', 0, 'cr:menu', NULL, NULL, NULL, 1, 6300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1087, 0, '招聘信息', '', 'url', '', '/user/crInfo', 1086, '0/1/1086/', 1, 'userCrInfo:*', NULL, NULL, NULL, 1, NULL);


20190930
ALTER TABLE `cet_unit_project`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL
	  COMMENT '审批状态，0 待报送 1 已报送 2 审批通过 3 审批未通过（打回） 4 已删除' AFTER `add_time`;
UPDATE cet_unit_project SET STATUS=2;
DELETE FROM sys_resource WHERE permission='cetUnitProject:list2';
DELETE FROM sys_resource WHERE permission='cetUnitTrain:*';
DELETE FROM sys_resource WHERE permission='cetUnitProject:*';
DELETE FROM sys_resource WHERE permission='cetUpperTrain:unit';
DELETE FROM sys_resource WHERE permission='cetUpperTrain:listUnit';
update sys_resource SET permission='cetUnitProject:list' WHERE permission='cetUnitProject:menu';
update sys_resource SET NAME='二级党委培训信息' WHERE permission='userCetUnitTrain:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (881, 0, '二级党委培训编辑', '', 'function', '', NULL, 652, '0/1/384/652/', 1,
                                    'cetUnitProject:edit', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `cet_unit_project`
	DROP COLUMN `add_type`;

ALTER TABLE `cet_unit_train`
	DROP COLUMN `add_type`;

ALTER TABLE `cet_unit_train`
	DROP COLUMN `status`,
	DROP COLUMN `back_reason`;

ALTER TABLE `cet_unit_project`
	CHANGE COLUMN `total_count` `total_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '参训人数' AFTER `period`;

UPDATE cet_unit_project p SET total_count=(SELECT COUNT(*) FROM cet_unit_train WHERE project_id=p.id);

ALTER TABLE `cet_unit_project`
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `add_time`,
	ADD COLUMN `back_reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '不通过原因' AFTER `status`;
update sys_resource SET NAME='培训班类型', permission='mc_cet_upper_train_type2:*'
, url='/metaClass_type_list?cls=mc_cet_upper_train_type2'
WHERE permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer2,mc_cet_upper_train_type2:*';

INSERT INTO `sys_scheduler_job` (`id`, `name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`,
                                 `sort_order`, `create_time`) VALUES (27, '【干部培训】归档培训学时', '', 'job.cet.CetArchive', '0 0 4 * * ?', 0, 1, 28, '2019-09-30 09:52:49');


-- 给分党委分配二级党委培训权限

20190928
更新 南航

20190925
更新 北邮

ALTER TABLE `cadre_edu`
	ADD COLUMN `degree_type` TINYINT(3) UNSIGNED NULL COMMENT '学位类型，1 学士 2 硕士 3 博士' AFTER `has_degree`;

UPDATE cadre_edu SET degree_type=1 WHERE degree LIKE '%学士%' AND has_degree=1;
UPDATE cadre_edu SET degree_type=2 WHERE degree LIKE '%硕士%' AND has_degree=1;
UPDATE cadre_edu SET degree_type=3 WHERE degree LIKE '%博士%' AND has_degree=1;

-- 更新 cadre_view等
ALTER TABLE `cadre_admin_level`
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL COMMENT '备注' AFTER `end_dispatch_cadre_id`;

-- 更新导入样表

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

