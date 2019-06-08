

20190608
更新哈工大、南航

20190608
更新 common-utils

ALTER TABLE `crs_post`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '所属单位，从“正在运转单位”库中选择' AFTER `admin_level`;

ALTER TABLE `sys_config`
	DROP COLUMN `site_home`;
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES (29, 'host', '域名', 'localhost:8080', 1, 29, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES (30, 'siteHome', '系统地址', 'http://localhost:8080', 1, 30, '末尾不带反斜杠');


20190606
创建哈工大

20190605
更新南航

20190605
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1049, 1, '党员基本信息', '', 'menu', 'fa fa-info-circle', NULL, 692, '0/692/', 0, 'm:memberInfo', NULL, NULL, NULL, 1, 1860);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1051, 1, '基本信息', '', 'url', '', '/m/member?cls=2', 1049, '0/692/1049/', 1, 'm:member:info', NULL, NULL, NULL, 1, 1000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1052, 1, '组织关系转出', '', 'url', '', '/m/memberOut', 1049, '0/692/1049/', 1, 'm:memberOut', NULL, NULL, NULL, 1, 900);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1053, 1, '校内组织关系转接', '', 'url', '', '/m/memberTransfer', 1049, '0/692/1049/', 1, 'm:memberTransfer', NULL, NULL, NULL, 1, 600);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1054, 1, '党员出国（境）申请组织关系暂留', '', 'url', '', '/m/memberStay?type=1', 1049, '0/692/1049/', 1, 'm:memberStay1', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1055, 1, '非出国（境）申请组织关系暂留', '', 'url', '', '/m/memberStay', 1049, '0/692/1049/', 1, 'm:memberStay2', NULL, NULL, NULL, 1, 400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1056, 1, '党员流出', '', 'url', '', '/m/memberOutflow', 1049, '0/692/1049/', 1, 'm:memberOutflow', NULL, NULL, NULL, 1, 300);

ALTER TABLE `ow_apply_approval_log`
	CHANGE COLUMN `remark` `remark` TEXT NULL DEFAULT NULL COMMENT '说明' AFTER `status`;

20190604
ALTER TABLE `ow_apply_sn_range`
	CHANGE COLUMN `use_count` `use_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已使用数量，不含已作废' AFTER `len`,
	ADD COLUMN `abolish_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已作废数量' AFTER `use_count`;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `party_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '所属党委，以下是班子成员信息' AFTER `cadre_sort_order`,
	ADD COLUMN `branch_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '所属支部' AFTER `party_name`,
	ADD COLUMN `party_type_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '分党委职务' AFTER `branch_name`,
	ADD COLUMN `post_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '分党委分工' AFTER `party_type_ids`,
	ADD COLUMN `branch_type_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '支部委员类别' AFTER `post_id`,
	ADD COLUMN `organizer_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '组织员类型，以下组织员信息' AFTER `branch_type_id`,
	ADD COLUMN `organizer_units` VARCHAR(200) NULL DEFAULT NULL COMMENT '组织员联系单位' AFTER `organizer_type`,
	ADD COLUMN `organizer_party_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '组织员联系党组织' AFTER `organizer_units`,
	ADD COLUMN `assign_date` DATE NULL DEFAULT NULL COMMENT '任职时间' AFTER `organizer_party_id`,
	ADD COLUMN `active_time` DATE NULL DEFAULT NULL COMMENT '成为入党积极分子时间' AFTER `assign_date`;

ALTER TABLE `cet_project_obj`
	CHANGE COLUMN `party_name` `party_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '所属党委，以下是班子成员信息' AFTER `cadre_sort_order`,
	CHANGE COLUMN `branch_name` `branch_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '所属支部' AFTER `party_id`;

-- 删除 cet_project_obj_view 及对应的类
drop view cet_project_obj_view;

UPDATE cet_trainee_type SET `name`='优秀年轻干部' WHERE  `id`=2;


UPDATE `sys_resource` SET `sort_order`='1000' WHERE  `id`=285;
UPDATE `sys_resource` SET `sort_order`='900' WHERE  `id`=287;
UPDATE `sys_resource` SET `sort_order`='800' WHERE  `id`=288;
UPDATE `sys_resource` SET `sort_order`='700' WHERE  `id`=295;
UPDATE `sys_resource` SET `sort_order`='600' WHERE  `id`=441;
UPDATE `sys_resource` SET `sort_order`='500' WHERE  `id`=286;

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1分党委审批 2组织部审批 10 归档（当第二次转出时，将之前已完成转出的记录归档）' AFTER `has_receipt`;

更新 ow_member_view

20190602
更新南航
20190602
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1048, 1, '权限开通申请', '', 'url', 'fa fa-star-o', '/m/apply', 692, '0/692/', 1, 'm:apply:*', NULL, NULL, NULL, 1, 800);

20190528
更新南航

20190528

ALTER TABLE `sys_role`
	CHANGE COLUMN `role` `code` VARCHAR(100) NOT NULL COMMENT '角色代码' AFTER `id`,
	CHANGE COLUMN `description` `name` VARCHAR(100) NULL DEFAULT NULL COMMENT '角色名称' AFTER `code`;

update sys_role set name='协同办公用户', remark='任务对象或任务指定负责人' where code='role_oa_user';

INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_oa_admin', '协同办公管理员', '108,1042,1045,560,561,563', '-1', NULL, 0, 0, 57, '任务发布人');

delete from sys_resource where permission like 'oaTaskType%';

ALTER TABLE `oa_task`
	ADD COLUMN `user_ids` VARCHAR(200) NULL COMMENT '共享任务人，创建人可以共享任务给相关人员，并赋予相应的管理员角色' AFTER `user_id`;

DROP VIEW IF EXISTS `oa_task_view`;
CREATE ALGORITHM = UNDEFINED VIEW `oa_task_view`
AS select ot.*, count(distinct otf.id) as file_count,
-- 任务对象数量
count(distinct otu.id) as user_count,
-- 已完成数
count(distinct otu2.id) as finish_count from oa_task ot
left join oa_task_file otf on otf.task_id=ot.id
left join oa_task_user otu on otu.task_id = ot.id and otu.is_delete=0
left join oa_task_user otu2 on otu2.task_id = ot.id and otu2.is_delete=0 and otu2.status=1 group by ot.id;

CREATE TABLE `oa_task_admin` (
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
	`types` TEXT NULL COMMENT '工作类型',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '添加时间',
	PRIMARY KEY (`user_id`)
)
COMMENT='协同办公任务管理员'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;

-- 删除 OaTaskMsgService.java

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1046, 0, '管理员列表', '', 'url', '', '/oa/oaTaskAdmin', 560, '0/1/560/', 1, 'oaTaskAdmin:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1047, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_oa_task_type', 560, '0/1/560/', 1, 'mc_oa_task_type:*', NULL, NULL, NULL, 1, NULL);
update sys_resource set sort_order=100 where id=561;
update sys_resource set sort_order=90 where id=562;

-- 删除所有角色的 协同办公的权限
-- 为admin新增协同办公 管理员列表、参数设置权限

DROP VIEW IF EXISTS `oa_task_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `oa_task_user_view` AS
select otu.*, uv.code, uv.realname, ot.name as task_name, ot.content as task_content,
ot.user_id as task_user_id, ot.user_ids as task_user_ids,
ot.deadline as task_deadline, ot.contact as task_contact,
ot.is_delete as task_is_delete, ot.is_publish as task_is_publish, ot.status as task_status,
ot.pub_date as task_pub_date, ot.type as task_type,
ouv.code as assign_code, ouv.realname as assign_realname,
ruv.code as report_code, ruv.realname as report_realname from oa_task_user otu
left join oa_task ot on otu.task_id = ot.id
left join sys_user_view uv on otu.user_id = uv.id
left join sys_user_view ouv on otu.assign_user_id = ouv.id
left join sys_user_view ruv on otu.report_user_id = ruv.id;

ALTER TABLE `oa_task_admin`
	ADD COLUMN `show_all` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否可以查看系统所有的任务' AFTER `types`;

20190526
更新南航

20190526
-- stat:* -> stat:ow
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (312, 0, '党建信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:ow', 4, NULL, NULL, 1, NULL);

-- suspend:page  待办事项页
-- suspend:ow  党建待办事项
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1042, 0, '待办事项', '', 'function', '', NULL, 108, '0/1/108/', 0, 'suspend:page', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1043, 0, '党建待办事项', '', 'function', '', NULL, 1042, '0/1/108/1042/', 1, 'suspend:ow', NULL, NULL, NULL, 1, NULL);
-- 系统信息统计
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1044, 0, '系统信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:sys', NULL, NULL, NULL, 1, NULL);
-- 协同待办
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1045, 0, '协同办公待办事项', '', 'function', '', NULL, 1042, '0/1/108/1042/', 1, 'suspend:oa', NULL, NULL, NULL, 1, NULL);

-- 删除jdbc.properties
-- 删除 StatMemberController
-- 删除 MemberStaticController

20190525
-- 增加 mc_abroad_reason
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (87, NULL, '因私出国（境）事由', '因私出国境审批', '因私出国境审批', 'mc_abroad_reason', '', '', '', 87, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (88, NULL, '因公出国事由', '因私出国境', '证件使用', 'mc_abroad_public_reason', '', '', '', 88, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (87, '旅游', 'mt_zahzju', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (87, '探亲', 'mt_ofm0cg', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (87, '访友', 'mt_anwn2w', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (87, '继承', 'mt_y2upyt', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (87, '接受和处理财产', 'mt_uuunrn', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (88, '学术会议', 'mt_frwifs', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (88, '考察访问', 'mt_ijtc9b', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (88, '合作研究', 'mt_t1jusy', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (88, '进修', 'mt_zim4js', NULL, '', '', 4, 1);

-- sysLogin:switchParty
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1040, 0, '允许切换党组织管理员', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'sysLogin:switchParty', NULL, NULL, NULL, 1, NULL);


20190518
新增 ow_organizer_group
 ow_organizer
ow_organizer_group_unit, ow_organizer_group_user

更新 common-utils

更新 ow_member_view
pcs_candidate_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1035, 0, '组织员信息管理', '', 'menu', '', NULL, 105, '0/1/105/', 0, 'organizer:menu', NULL, NULL, NULL, 1, 29500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1036, 0, '校级组织员信息', '', 'url', '', '/organizer?type=1', 1035, '0/1/105/1035/', 1, 'organizer:list1', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1037, 0, '院系级组织员信息', '', 'url', '', '/organizer?type=2', 1035, '0/1/105/1035/', 1, 'organizer:list2', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1038, 0, '组织员分组管理', '', 'function', '', NULL, 1035, '0/1/105/1035/', 1, 'organizerGroup:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1039, 0, '组织员管理', '', 'function', '', NULL, 1035, '0/1/105/1035/', 1, 'organizer:*', NULL, NULL, NULL, 1, NULL);

20190516
更新南航


20190516

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`,
                            `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                             VALUES (1034, 0, '查看用户档案页', '', 'function', '', NULL, 853, '0/1/21/853/', 1, 'sysUser:view', NULL, NULL, NULL, 1, NULL);

DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `native_place` `native_place` VARCHAR(100) NULL DEFAULT NULL COMMENT '籍贯' AFTER `nation`,
	CHANGE COLUMN `homeplace` `homeplace` VARCHAR(100) NULL DEFAULT NULL COMMENT '出生地' AFTER `native_place`,
	CHANGE COLUMN `household` `household` VARCHAR(100) NULL DEFAULT NULL COMMENT '户籍地' AFTER `homeplace`;


更新 cadreStateName


20190516

ALTER TABLE `sys_user_info`
	ADD COLUMN `file_number` VARCHAR(50) NULL COMMENT '档案编号' AFTER `not_send_msg`;

ALTER TABLE `sys_user_info`
	ADD COLUMN `mailing_address` VARCHAR(50) NULL DEFAULT NULL COMMENT '通讯地址' AFTER `email`;


20190516

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (1032, 0, '系统属性管理', '', 'function', '', NULL, 847, '0/1/21/847/', 1, 'sysProperty:*', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1033, 0, '批量导入头像', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'avatar:import', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `sys_config`
	DROP COLUMN `xss_ignore_uri`,
	DROP COLUMN `upload_max_size`,
	DROP COLUMN `short_msg_url`,
	DROP COLUMN `school_email`,
	DROP COLUMN `use_cadre_post`,
	DROP COLUMN `has_party_module`,
	DROP COLUMN `cadre_template_fs_note`;

INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (19, 'useCadrePost', '启用干部的岗位过程信息', 'false', 3, 19, '取值为true时，可在[数据同步]模块中点击按钮进行批量同步');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (20, 'hasPartyModule', '存在党建模块', 'true', 3, 20, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (21, 'superUsers', '超管', 'zzbgz', 1, 21, '拥有隐藏权限的账号，多个以,隔开');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (22, 'hideHelp', '隐藏帮助文档', 'false', 3, 22, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (23, 'cadreViewDef', '干部档案默认页', 'cadre_base', 1, 23, '在有权限看到任免审批表(cadreAdform_page)的情况下，干部档案页默认进入的页面');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (24, 'usernameRegex', '系统用户名正则表达式', '^[a-zA-z][a-zA-Z0-9_\\.]{3,20}$', 1, 24, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (25, 'passwdRegex', '系统密码正则表达式', '^[a-zA-Z0-9_]{6,16}$', 1, 25, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (26, 'mobileRegex', '手机号码正则表达式', '^1[3|4|5|6|7|8|9]\\d{9}$', 1, 26, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (27, 'passwdMsg', '密码不合法提示', '密码由6-16位的字母、下划线和数字组成', 1, 27, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (28, 'usernameMsg', '用户名不合法提示', '用户名由3-20位的字母、下划线和数字组成，且不能以数字或下划线开头', 1, 28, '');


删除spring.properties以下属性
sys.help.hide
sys.auth.super
username.regex
passwd.regex
mobile.regex
sys.settings.cadreView.default


20190514
更新南航，北化工，北邮

20190514


删除文件：
 MemberStudentMixin MemberTeacherMixin
 memberStudent*/memberTeacher*

drop view ow_member_student;
drop view ow_member_teacher;

update sys_resource SET name = '查看党员基本信息', permission = 'member:base' where id=220;
delete from sys_resource where permission='memberStudent:list';


DROP VIEW IF EXISTS `ow_member_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ow_member_view` AS
select
m.*, u.code, ui.realname, ui.gender, ui.nation, ui.native_place,
ui.birth, ui.idcard, ui.mobile, ui.email, ui.unit, p.unit_id,
mo.status as out_status, mo.handle_time as out_handle_time,

t.education,t.degree,t.degree_time,t.major,t.school,t.school_type, t.degree_school,
t.authorized_type, t.staff_type, t.staff_status, t.on_job, t.main_post_level,
t.post_class, t.post, t.post_level, t.pro_post, t.pro_post_level, t.manage_level, t.office_level,
t.title_level,t.marital_status,t.address,
t.arrive_time, t.work_time, t.from_type, t.talent_type, t.talent_title,
t.is_retire, t.is_honor_retire, t.retire_time,

s.delay_year,s.period,s.actual_graduate_time,
s.expect_graduate_time,s.actual_enrol_time,s.sync_source ,s.type as student_type,s.is_full_time,
s.enrol_year,s.grade,s.edu_type,s.edu_way,s.edu_level,s.edu_category,s.xj_status

from ow_member m
left join sys_user_info ui on ui.user_id=m.user_id
left join sys_user u on u.id=m.user_id
left join ow_party p on p.id = m.party_id
left join ow_member_out mo on mo.user_id = m.user_id
left join sys_teacher_info t on t.user_id = m.user_id
left join sys_student_info s on s.user_id = m.user_id;

DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_view where type=1 and status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id;

-- ----------------------------
--  View definition for `ow_branch_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_branch_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_branch_view` AS
select b.*, p.sort_order as party_sort_order, mtmp.num as member_count, mtmp.positive_count, mtmp.s_num as student_member_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, gtmp.num as group_count, gtmp2.num as present_group_count
from ow_branch b
left join ow_party p on b.party_id=p.id
left join (select  sum(if(political_status=2, 1, 0)) as positive_count, sum(if(type=2, 1, 0)) as s_num, count(*) as num,  branch_id from ow_member where  status=1 group by branch_id) mtmp on mtmp.branch_id=b.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, branch_id from ow_member_view where type=1 and status=1 group by branch_id) mtmp2 on mtmp2.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 group by branch_id) gtmp on gtmp.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 and is_present=1 group by branch_id) gtmp2 on gtmp2.branch_id=b.id;

ALTER TABLE `pmd_config_reset`
	ADD COLUMN `party_id` INT UNSIGNED NULL COMMENT '涉及分党委' AFTER `reset`,
	ADD COLUMN `branch_id` INT UNSIGNED NULL COMMENT '涉及党支部' AFTER `party_id`,
	ADD COLUMN `limited_user_id` INT UNSIGNED NULL COMMENT '涉及缴费党员' AFTER `branch_id`;

DROP VIEW IF EXISTS `ow_party_static_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_party_static_view` AS
select p.id, p.name,
s.bks, s.ss, s.bs, (s.bks+s.ss+s.bs) as student, s.positive_bks, s.positive_ss, s.positive_bs, (s.positive_bks + s.positive_ss + s.positive_bs) as positive_student,
t.teacher,t.teacher_retire, (t.teacher+t.teacher_retire) as teacher_total, t.positive_teacher, t.positive_teacher_retire, (t.positive_teacher + t.positive_teacher_retire)as positive_teacher_total,
b.bks_branch, b.ss_branch, b.bs_branch, b.sb_branch, b.bsb_branch,
(b.bks_branch + b.ss_branch + b.bs_branch + b.sb_branch + b.bsb_branch) as student_branch_total, b.teacher_branch, b.retire_branch, (b.teacher_branch + b.retire_branch) as teacher_branch_total,
a.teacher_apply_count, a.student_apply_count
from ow_party p left join
(
select party_id,
sum(if(edu_level is null, 1, 0)) as bks,
sum(if(edu_level='硕士', 1, 0)) as ss,
sum(if(edu_level='博士', 1, 0)) as bs,
sum(if(edu_level is null and political_status=2, 1, 0)) as positive_bks,
sum(if(edu_level='硕士' and political_status=2, 1, 0)) as positive_ss,
sum(if(edu_level='博士' and political_status=2, 1, 0)) as positive_bs
from ow_member_view where type=2 and status=1 group by party_id
) s on s.party_id = p.id
left join
(
select party_id,
sum(if(is_retire, 0, 1)) teacher,
sum(if(is_retire, 1, 0)) teacher_retire,
sum(if(!is_retire and political_status=2, 1, 0)) positive_teacher,
sum(if(is_retire and political_status=2, 1, 0)) positive_teacher_retire
from ow_member_view where type=1 and status=1 group by party_id
) t on t.party_id = p.id
left join
(select b.party_id,
sum(if(locate('本科生',bmt.name), 1, 0)) as bks_branch,
sum(if(locate('硕士',bmt.name), 1, 0)) as ss_branch,
sum(if(locate('博士',bmt.name), 1, 0)) as bs_branch,
sum(if(POSITION('硕博' in bmt.name)=1, 1, 0)) as sb_branch,
sum(if(locate('本硕博',bmt.name), 1, 0)) as bsb_branch,
sum(if(locate('在职',bmt.name), 1, 0)) as teacher_branch,
sum(if(locate('离退休',bmt.name), 1, 0)) as retire_branch
from ow_branch b, base_meta_type bmt where b.is_deleted=0 and b.type_id=bmt.id group by b.party_id
)b on b.party_id = p.id
left join
(select p.id as party_id, sum(if(type=1, 1, 0)) as teacher_apply_count, sum(if(type=2, 1, 0)) as student_apply_count from ow_member_apply oma
left join ow_party p on oma.party_id=p.id
left join ow_branch b on oma.branch_id=b.id
where p.is_deleted=0 and (b.is_deleted=0 or b.id is null) group by p.id
)a on a.party_id = p.id
where p.is_deleted=0 order by p.sort_order desc;

DROP VIEW IF EXISTS `pcs_branch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_branch_view` AS
select  ob.party_id, ob.id as branch_id, ob.name, ob.member_count, ob.positive_count,  ob.student_member_count, ob.teacher_member_count, ob.retire_member_count from ow_branch_view ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null
union all
select  op.id as party_id, null as branch_id, op.name, op.member_count, op.positive_count, op.student_member_count, op.teacher_member_count, op.retire_member_count from ow_party_view op, base_meta_type bmt
where op.is_deleted=0 and op.class_id=bmt.id and bmt.code='mt_direct_branch' order by member_count desc;

DROP VIEW IF EXISTS `pcs_pr_candidate_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_pr_candidate_view` AS
SELECT pc.*,
uv.code, uv.realname, if(c.status=6, c.sort_order, -1)  as leader_sort_order, if(isnull(c.id), if(isnull(om.user_id), 3 , 2), 1) as user_type, c.edu_id as edu_id, c.post,
  om.grow_time,  om.work_time, om.pro_post, om.education, om.is_retire, om.edu_level,
ppr.party_id, ppr.config_id, ppr.stage, op.sort_order as party_sort_order, u.name as unit_name
from pcs_pr_candidate pc
left join sys_user_view uv on uv.id=pc.user_id
left join cadre_view c on c.user_id = pc.user_id and c.status in(1, 6)
left join ow_member_view om on om.user_id = pc.user_id
, pcs_pr_recommend ppr
left join ow_party op on op.id = ppr.party_id
left join unit u on op.unit_id=u.id
where pc.recommend_id=ppr.id;

DROP VIEW IF EXISTS `pcs_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_party_view` AS select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(ob.id) as num, ob.party_id from ow_branch ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null group by ob.party_id) btmp on btmp.party_id=p.id
left join (select sum(if(om.type=2, 1, 0)) as s_num, sum(if(om.political_status=2, 1, 0)) as positive_count, count(om.user_id) as num,  om.party_id from ow_member om
left join ow_branch ob on ob.id = om.branch_id
left join pcs_exclude_branch peb on peb.party_id=om.party_id and peb.branch_id=om.branch_id
where ob.is_deleted=0 and om.status=1 and peb.id is null group by party_id
union all
select sum(if(om.type=2, 1, 0)) as s_num, sum(if(om.political_status=2, 1, 0)) as positive_count, count(om.user_id) as num,  om.party_id from ow_member om
left join (select op.* from ow_party op, base_meta_type bmt  where op.class_id=bmt.id and bmt.code='mt_direct_branch') as op on op.id = om.party_id
where op.is_deleted=0 and om.status=1 group by party_id
) mtmp on mtmp.party_id=p.id
left join (select sum(if(om.is_retire=0, 1, 0)) as t_num, sum(if(om.is_retire=1, 1, 0)) as t2_num,
count(om.user_id) as num, om.party_id from ow_member_view om
left join pcs_exclude_branch peb on peb.party_id=om.party_id and peb.branch_id=om.branch_id
where type=1 and status=1 and peb.id is null group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id ;





20190512
更新北邮



20190511
更新南航

/jsp/ -> /page/
20190511

-- 修改
update sys_resource set permission='memberTeacher:list' where permission='memberTeacher:*';
update sys_resource set permission='memberStudent:list' where permission='memberStudent:*';
update sys_resource set permission='memberReg:list' where permission='memberReg:*';

-- 新增
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1026, 0, '党员人事基础信息修改', '', 'function', '', NULL, 107, '0/1/105/107/', 1,
                                    'memberBaseInfo:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1027, 0, '已分配志愿书编码列表', '', 'function', '',
                                    NULL, 211, '0/1/105/211/', 1, 'applySnRange:list', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1028, 0, '审批', '', 'function', '', NULL, 290, '0/1/105/290/', 1, 'memberReg:check', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (1029, 0, '修改注册信息', '', 'function', '', NULL, 290, '0/1/105/290/', 1, 'memberReg:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
 VALUES (1030, 0, '批量生成账号', '', 'function', '', NULL, 290, '0/1/105/290/', 1, 'memberReg:import', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (1031, 0, '修改密码', '', 'function', '', NULL, 290, '0/1/105/290/', 1, 'memberReg:changepw', NULL, NULL, NULL, 1, NULL);

-- 调整分党委、党建管理员权限

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('nativePlaceHelpBlock', '籍贯格式说明', '格式：“河北保定”或“北京海淀”', 1, 16, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES (17, 'memberRegCodePrefix', '注册账号工号前缀', 'zg', 1, 17, '针对本人注册的账号');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES (18, 'memberRegPrefix', '注册账号前缀', 'dy', 1, 18, '针对后台添加或导入的账号');


ALTER TABLE `ow_apply_sn`
	ADD COLUMN `assign_time` DATETIME NULL DEFAULT NULL COMMENT '分配时间' AFTER `user_id`;

ALTER TABLE `ow_apply_sn`
	ADD COLUMN `draw_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '从党委领取时的操作人，保留字段' AFTER `assign_time`,
	ADD COLUMN `draw_time` DATETIME NULL DEFAULT NULL COMMENT '从党委领取的时间，保留字段' AFTER `draw_user_id`;

ALTER TABLE `ow_apply_sn`
	ADD COLUMN `party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '使用人所联系分党委' AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '使用人所联系支部' AFTER `party_id`;

update ow_apply_sn oas, ow_member_apply oma set oas.party_id=oma.party_id,
                                                oas.branch_id=oma.branch_id where oas.user_id=oma.user_id;

ALTER TABLE `ow_member_reg`
	ADD COLUMN `import_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '批量生成账号操作人' AFTER `ip`,
	ADD COLUMN `import_seq` INT UNSIGNED NULL DEFAULT NULL COMMENT '第几次批量生成' AFTER `import_user_id`;


update base_meta_class set bool_attr='布尔属性'  where id in
(select class_id from base_meta_type where length(bool_attr)>0) and length(bool_attr)=0;

update base_meta_class set extra_attr='附加属性' where id in
(select class_id from base_meta_type where length(extra_attr)>0) and length(extra_attr)=0;

更新 common-utils

20190509
ALTER TABLE `oa_task`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '工作类型' AFTER `user_id`;

更新 oa_task_view   oa_task_user_view

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (86, NULL, '工作类型', '协同办公', '任务', 'mc_oa_task_type', '', '操作权限', '', 86, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (86, '干部工作', 'mt_uyp3zi', NULL, 'oaTaskType:cadre', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (86, '党建工作', 'mt_la2kqv', NULL, 'oaTaskType:party', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (86, '培训工作', 'mt_rmsbam', NULL, 'oaTaskType:train', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (86, '统战工作', 'mt_nulpk8', NULL, 'oaTaskType:united', '', 4, 1);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1022, 0, '干部工作权限', '', 'function', '', NULL, 561, '0/1/560/561/', 1, 'oaTaskType:cadre', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1023, 0, '党建工作权限', '', 'function', '', NULL, 561, '0/1/560/561/', 1, 'oaTaskType:party', NULL, NULL, NULL, 1, 480);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1024, 0, '培训工作权限', '', 'function', '', NULL, 561, '0/1/560/561/', 1, 'oaTaskType:train', NULL, NULL, NULL, 1, 470);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1025, 0, '统战工作权限', '', 'function', '', NULL, 561, '0/1/560/561/', 1, 'oaTaskType:united', NULL, NULL, NULL, 1, 460);


-- 1 干部工作 2 党建工作 3 培训工作
update oa_task set type=530 where type=1;
update oa_task set type=531 where type=2;
update oa_task set type=532 where type=3;

20190509
更新南航

INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (12, 'loginCss', '登录页样式', '.top .separator{border-left: 1px solid red;}  .top .txt{color:red}', 1, 12, '');
INSERT INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (13, 'cadreStateName', '干部类别', '人员类别[m]', 1, 13, '');

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1021, 0, '添加', '', 'function', '', NULL, 106, '0/1/260/106/', 1, 'party:add', NULL, NULL, NULL, 1, NULL);

UPDATE `sys_resource` SET `name`='修改' WHERE  `id`=183;

党建管理员、分党委管理员修改权限


ALTER TABLE `cadre`
	CHANGE COLUMN `state` `state` INT(10) UNSIGNED NULL AFTER `type`;

更新 cadre_view
crs_candidate_view
cadre_inspect_view
cadre_reserve_view

更新 cadre_company_view

-- 新增 mc_cadre_state
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (85, NULL, '人员类别【M】', '干部', '干部信息', 'mc_cadre_state', '', '', '', 85, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (85, '是', 'mt_dscnst', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (85, '否', 'mt_mpbzis', NULL, '', '', 2, 1);

update cadre set state = 528 where state = 1;

update cadre set state = 529 where state = 0;


-- 修改 单位类型 附加属性元数据
UPDATE `base_meta_class` SET extra_attr='所属大类', `extra_options`='jg|机关及直属单位|机关职能部处、直属单位、教辅单位、机关党总支、经营性单位,xy|学部、院、系所,fs|附属单位' WHERE  `id`=8;

+ mt_admin_level_main_kj
mt_admin_level_vice_kj

更新 unit_view


update cadre set type=10 where type is null;
ALTER TABLE `cadre`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '干部类型，1 处级干部 2 科级干部 10 其他' AFTER `unit_id`;

update base_meta_class set extra_attr='所属大类', extra_options='jg|机关及直属单位|机关职能部处、直属单位、教辅单位、机关党总支、经营性单位,xy|学部、院、系所,fs|附属单位' where id=8;

REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (13, 'cadreStateName', '干部类别', '人员类别[M]', 1, 13, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (14, 'adFormType', '干部任免审批表类型', '2', 2, 14, '1：北京  2：工信部');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (15, 'hasKjCadre', '是否管理科级干部', 'true', 3, 15, '');


20190507
更新南航


20190507

更新 ext_cadre_view

ALTER TABLE `ow_apply_sn`
	ADD COLUMN `is_abolished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否作废，用于换领志愿书，必须已使用的情况下换领' AFTER `is_used`;

delete from ow_apply_sn where range_id not in(select id from ow_apply_sn_range);
ALTER TABLE `ow_apply_sn`
	ADD CONSTRAINT `FK_ow_apply_sn_ow_apply_sn_range` FOREIGN KEY (`range_id`) REFERENCES `ow_apply_sn_range` (`id`) ON DELETE CASCADE;


20190427
更新南航

20190426
更新南航

20190426

ALTER TABLE `ow_org_admin`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类别，1 分党委管理员  2 支部管理员' AFTER `branch_id`;
update ow_org_admin set type = 1 where party_id is not null;
update ow_org_admin set type = 2 where branch_id is not null;

ALTER TABLE `ow_org_admin`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1 分党委管理员  2 支部管理员' AFTER `branch_id`;

DROP VIEW IF EXISTS `ow_org_admin_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ow_org_admin_view` AS
select oa.*, p.sort_order as party_sort_order, b.party_id as branch_party_id,
bp.sort_order as branch_party_sort_order, b.sort_order as branch_sort_order from ow_org_admin oa
left join ow_party p on p.id=oa.party_id
left join ow_branch b on b.id=oa.branch_id
left join ow_party bp on bp.id=b.party_id;

20190425
ALTER TABLE `oa_task_user`
	ADD COLUMN `mobile` VARCHAR(11) NULL COMMENT '手机号码' AFTER `user_id`,
	ADD COLUMN `title` VARCHAR(200) NULL COMMENT '手机号码' AFTER `mobile`;

update oa_task_user otu, cadre_view cv set otu.mobile=cv.mobile, otu.title=cv.title where otu.user_id=cv.user_id;

ALTER TABLE `oa_task_user`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `check_remark`;

DROP VIEW IF EXISTS `oa_task_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `oa_task_user_view` AS
select otu.*, uv.code, uv.realname, ot.name as task_name, ot.content as task_content,
ot.deadline as task_deadline, ot.contact as task_contact,
ot.is_delete as task_is_delete, ot.is_publish as task_is_publish, ot.status as task_status,
ot.pub_date as task_pub_date, ot.type as task_type,
ouv.code as assign_code, ouv.realname as assign_realname,
ruv.code as report_code, ruv.realname as report_realname from oa_task_user otu
left join oa_task ot on otu.task_id = ot.id
left join sys_user_view uv on otu.user_id = uv.id
left join sys_user_view ouv on otu.assign_user_id = ouv.id
left join sys_user_view ruv on otu.report_user_id = ruv.id;

+ 协同办公任务对象录入样表.xlsx

ALTER TABLE `cadre_family`
	CHANGE COLUMN `sort_order` `sort_order` INT(10) UNSIGNED NULL COMMENT '排序，每个干部的排序' AFTER `unit`;

20190424
更新南航

20190424

CREATE TABLE `ow_apply_sn_range` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '所属年度',
	`prefix` VARCHAR(20) NULL DEFAULT NULL COMMENT '编码前缀',
	`start_sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '起始编码',
	`end_sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '结束编码',
	`len` INT(10) UNSIGNED NOT NULL COMMENT '编码长度，除前缀外的编码位数',
	`use_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已使用数量',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='入党志愿书编码段，连续编码段，同一年度不允许存在交集；每年可能有多个；'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

CREATE TABLE `ow_apply_sn` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年份',
	`sn` BIGINT(20) UNSIGNED NOT NULL COMMENT '编码',
	`display_sn` VARCHAR(30) NOT NULL COMMENT '显示编码，编码前缀+编码（不足编码长度前面补0）',
	`range_id` INT(10) UNSIGNED NOT NULL COMMENT '所属号段',
	`is_used` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否已使用',
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '使用人',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `year_sn` (`year`, `sn`),
	UNIQUE INDEX `sn_range_id` (`sn`, `range_id`)
)
COMMENT='入党志愿书编码，编码段添加时同步新增，编码段修改或删除时，如果存在已使用的号码，则不允许删除；组织部审批通过则标记为已使用，如果打回或移除操作，则标记为未使用。'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=1005
;

ALTER TABLE `ow_member_apply`
	CHANGE COLUMN `apply_sn` `apply_sn_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `draw_status`;

ALTER TABLE `ow_member_apply`
	ADD CONSTRAINT `FK_ow_member_apply_ow_apply_sn` FOREIGN KEY (`apply_sn_id`) REFERENCES `ow_apply_sn` (`id`);

ALTER TABLE `ow_member_apply`
	CHANGE COLUMN `apply_sn_id` `apply_sn_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联志愿书编码' AFTER `draw_status`,
	ADD COLUMN `apply_sn` VARCHAR(30) NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `apply_sn_id`;

-- 更新 ow_member_apply_view
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select ma.*, m.status as _status, if((m.status is null or m.status=1), 0, 1) as member_status
     , p.sort_order as party_sort_order, b.sort_order as branch_sort_order
from  ow_member_apply ma
        left join ow_branch b on ma.branch_id=b.id
        left join ow_party p on b.party_id=p.id
        left join ow_member m  on ma.user_id = m.user_id;

update abroad_passport set code = null where code='';

ALTER TABLE `abroad_passport`
	ADD UNIQUE INDEX `code` (`code`);

+ ct_oa_info_user

20190421

ALTER TABLE `sc_committee_vote`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性' AFTER `post`,
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别' AFTER `post_type`,
	CHANGE COLUMN `agree_count` `agree_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决同意票数' AFTER `unit_id`,
	CHANGE COLUMN `abstain_count` `abstain_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决弃权票数' AFTER `agree_count`,
	CHANGE COLUMN `disagree_count` `disagree_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '表决反对票数' AFTER `abstain_count`;

更换光连接池（必须保证telnet 127.0.0.1 3306 通; 全部jar更换）

20190418

更新南航

20190418

ALTER TABLE `cadre_edu`
	CHANGE COLUMN `school_type` `school_type` TINYINT(3) UNSIGNED NULL COMMENT '学校类型， 1本校 2境内 3境外' AFTER `major`;

ALTER TABLE `ow_member_apply`
	ADD COLUMN `apply_sn` BIGINT UNSIGNED NULL DEFAULT NULL COMMENT '志愿书编码' AFTER `draw_status`;

ALTER TABLE `ow_party`
	ADD COLUMN `mailbox` VARCHAR(50) NULL DEFAULT NULL COMMENT '信箱' AFTER `email`;
-- 更新 ow_party_view
DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_teacher where status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id;

CREATE TABLE `ow_party_public` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 发展党员公示文件 2 党员转正公示文件',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党委',
	`pub_date` DATE NOT NULL COMMENT '公示日期，强制当天',
	`party_name` VARCHAR(100) NOT NULL COMMENT '党委名称',
	`email` VARCHAR(50) NOT NULL COMMENT '邮箱',
	`phone` VARCHAR(50) NOT NULL COMMENT '联系电话',
	`mailbox` VARCHAR(50) NOT NULL COMMENT '信箱',
	`num` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '公示人数',
	`pub_users` TEXT NULL COMMENT '公示对象，账号ID，逗号隔开',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '创建人',
	`is_publish` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否发布',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '创建人IP',
	PRIMARY KEY (`id`)
)
COMMENT='党员公示文件，包含发展党员公示文件、党员转正公示文件，每个党委每天每类最多只能有一个公示'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14
;
CREATE TABLE `ow_party_public_user` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`public_id` INT(10) UNSIGNED NOT NULL COMMENT '所属公示',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '公示对象',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党委',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属支部',
	`party_name` VARCHAR(100) NOT NULL COMMENT '党委名称',
	`branch_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '支部名称',
	PRIMARY KEY (`id`),
	INDEX `FK_ow_public_user_ow_public` (`public_id`),
	CONSTRAINT `FK_ow_public_user_ow_public` FOREIGN KEY (`public_id`) REFERENCES `ow_party_public` (`id`) ON DELETE CASCADE
)
COMMENT='党员公示，包含党员发展公示、党员转正公示'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=789
;

ALTER TABLE `ow_member_apply`
	ADD COLUMN `active_train_start_time` DATE NULL DEFAULT NULL COMMENT '参加培训起始时间，成为入党积极分子之后参加的培训' AFTER `active_time`,
	ADD COLUMN `active_train_end_time` DATE NULL DEFAULT NULL COMMENT '参加培训结束时间' AFTER `active_train_start_time`,
	ADD COLUMN `active_grade` VARCHAR(20) NULL DEFAULT NULL COMMENT '积极分子结业考试成绩' AFTER `active_train_end_time`,
	CHANGE COLUMN `train_time` `candidate_train_start_time` DATE NULL DEFAULT NULL COMMENT '参加培训起始时间，成为发展对象之后参加的培训' AFTER `candidate_time`,
	ADD COLUMN `candidate_train_end_time` DATE NULL DEFAULT NULL COMMENT '参加培训结束时间' AFTER `candidate_train_start_time`,
	ADD COLUMN `candidate_grade` VARCHAR(20) NULL DEFAULT NULL COMMENT '发展对象结业考试成绩' AFTER `candidate_train_end_time`,
	ADD COLUMN `grow_public_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '发展公示' AFTER `apply_sn`,
	ADD COLUMN `positive_public_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '转正公示' AFTER `grow_status`;

-- 更新 ow_member_apply_view
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select ma.*, m.status as _status, if((m.status is null or m.status=1), 0, 1) as member_status
     , p.sort_order as party_sort_order, b.sort_order as branch_sort_order
from  ow_member_apply ma
        left join ow_branch b on ma.branch_id=b.id
        left join ow_party p on b.party_id=p.id
        left join ow_member m  on ma.user_id = m.user_id;

-- 更新sys_property表的内容
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (1, 'partyName', '二级党委名称', '分党委', 1, 1, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (2, 'zzb_email', '组织部邮箱', 'zzb@xxx.com', 1, 2, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (3, 'zzb_mailbox', '组织部信箱', '11xx25号', 1, 3, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (4, 'zzb_phone', '组织部电话', '84xx2749', 1, 4, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (5, 'memberOut_toTitle_remark', '转入单位抬头备注', '注：如果类别是京外，则抬头必须是区县级以上组织部门', 1, 5, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (6, 'memberApply_needActiveTrain', '积极分子培训', 'false', 3, 6, '是否需要提交培训时间、成绩');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (7, 'memberApply_needCandidateTrain', '发展党员培训', 'false', 3, 7, '培训时间、成绩');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (8, 'member_needGrowTime', '入党、转正时间是否必填', 'false', 3, 8, '');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (9, 'cadreFamily_noNeedBirth', '家庭成员出生日期', 'false', 3, 9, '家庭成员出生日期是否选填');
REPLACE INTO `sys_property` (`id`, `code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES (10, 'cadrePostPro_noNeed', '干部专业技术职务', 'false', 3, 10, '干部专业技术职务、等级和分级时间是否选填');



更新common-utils

删除 avatar.default



20190413
ALTER TABLE `base_short_msg`
	ADD COLUMN `relate_sn` VARCHAR(50) NULL DEFAULT NULL COMMENT '发送编号，每次定向发送时生成的UUID' AFTER `relate_id`;

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `send_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送次数' AFTER `sort_order`,
	ADD COLUMN `send_user_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送人次' AFTER `send_count`;

update base_short_msg_tpl tpl, (select relate_id, count(*) num from base_short_msg where relate_type=2 group by relate_id)
  msg set send_count=msg.num, send_user_count=msg.num where msg.relate_id=tpl.id;

ALTER TABLE `base_short_msg_tpl`
	CHANGE COLUMN `send_count` `send_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送次数' AFTER `sort_order`,
	CHANGE COLUMN `send_user_count` `send_user_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送人次' AFTER `send_count`;




20190411
更新北邮----
更新北化工
