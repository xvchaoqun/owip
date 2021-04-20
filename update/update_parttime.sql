
20210417 xcq
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5012, 0, '兼职申报申请', '', 'menu', 'fa fa-sign-out', NULL, 1, '0/1/', 0, 'parttime:user', NULL, NULL, NULL, 1, 5400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5013, 0, '兼职申报申请', '', 'url', '', '/user/parttime/parttimeApply', 5012, '0/1/5012/', 1, 'userParttimeApply:*', NULL, NULL, NULL, 1, 400);

20210416 xcq

CREATE TABLE `parttime_applicat_cadre` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type_id` INT(10) UNSIGNED NOT NULL COMMENT '申请人身份',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '申请人',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，暂无用',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_cadre_company_applicat_cadre_cadre_company_applicat_type` (`type_id`) USING BTREE,
	INDEX `FK_cadre_company_applicat_cadre_cadre` (`cadre_id`) USING BTREE
)
COMMENT='申请人分组'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=16
;
CREATE TABLE `parttime_applicat_type` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`name` VARCHAR(100) NOT NULL COMMENT '申请人身份' COLLATE 'utf8_general_ci',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '排序',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='申请人身份'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=5
;
CREATE TABLE `parttime_apply` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`cadre_id` INT(10) UNSIGNED NOT NULL DEFAULT '0',
	`type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '兼职单位类别 0. 社会团体 1.基金会 2.民办非企业单位 3.企业',
	`title` VARCHAR(100) NOT NULL COMMENT '兼职单位及职务' COLLATE 'utf8_general_ci',
	`is_first` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否首次 默认为首次，其他为连任',
	`background` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否有国境外背景',
	`has_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否取酬',
	`balance` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '取酬金额',
	`reason` VARCHAR(100) NULL DEFAULT NULL COMMENT '申请理由' COLLATE 'utf8_general_ci',
	`start_time` DATE NULL DEFAULT NULL COMMENT '兼职开始时间',
	`end_time` DATE NULL DEFAULT NULL COMMENT '兼职结束时间',
	`apply_time` DATE NULL DEFAULT NULL COMMENT '申请时间',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`approval_remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '审批意见，记录最新审批意见' COLLATE 'utf8_general_ci',
	`is_finish` TINYINT(1) NULL DEFAULT '0' COMMENT '是否完成终审',
	`flow_node` INT(10) NULL DEFAULT NULL COMMENT '下一个审批类型（0.其他 1.组织部终审）',
	`flow_nodes` VARCHAR(100) NULL DEFAULT NULL COMMENT '已审批身份类型（按顺序排序，逗号分隔）' COLLATE 'utf8_general_ci',
	`flow_users` VARCHAR(100) NULL DEFAULT NULL COMMENT '已审批的审批人userid（顺序排列，逗号分隔）' COLLATE 'utf8_general_ci',
	`status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否提交',
	`is_agreed` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否最终同意申请，供查询使用 0 不同意 1. 同意',
	`is_modify` TINYINT(1) NULL DEFAULT NULL COMMENT '后台是否修改',
	`is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否已删除',
	`remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	`ip` VARCHAR(100) NULL DEFAULT NULL COMMENT 'ip' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_cadre_company_apply_cadre` (`cadre_id`) USING BTREE
)
COMMENT='兼职申报申请'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=30
;
CREATE TABLE `parttime_apply_file` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`apply_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '申请记录id',
	`file_name` VARCHAR(200) NULL DEFAULT NULL COMMENT '文件名称' COLLATE 'utf8_general_ci',
	`file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '文件路径' COLLATE 'utf8_general_ci',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK_cadre_company_apply_file_cadre_company_apply` (`apply_id`) USING BTREE
)
COMMENT='兼职申报文件材料'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;
CREATE TABLE `parttime_apply_modify` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`modify_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '修改类型： 1. 首次提交申请 2. 修改',
	`apply_id` INT(10) UNSIGNED NOT NULL COMMENT '申请记录',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '干部',
	`apply_date` DATE NULL DEFAULT NULL COMMENT '申请日期',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '兼职单位类别 0. 社会团体 1.基金会 2.民办非企业单位 3.企业',
	`title` VARCHAR(100) NOT NULL COMMENT '兼职单位及职务' COLLATE 'utf8_general_ci',
	`is_first` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否首次 默认为首次，其他为连任',
	`background` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否有国境外背景',
	`has_pay` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否取酬',
	`balance` INT(10) UNSIGNED NOT NULL COMMENT '取酬金额',
	`reason` VARCHAR(100) NULL DEFAULT NULL COMMENT '申请理由' COLLATE 'utf8_general_ci',
	`start_time` DATE NOT NULL COMMENT '兼职开始时间',
	`end_time` DATE NOT NULL COMMENT '兼职结束时间',
	`modify_proof` VARCHAR(255) NULL DEFAULT NULL COMMENT '变更证明' COLLATE 'utf8_general_ci',
	`modify_proof_file_name` VARCHAR(255) NULL DEFAULT NULL COMMENT '变更证明文件' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '变更原因' COLLATE 'utf8_general_ci',
	`modify_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '变更人',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '变更时间',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '变更人ip' COLLATE 'utf8_general_ci',
	`stauts` VARCHAR(50) NULL DEFAULT NULL COMMENT '是否提交（从此往下的字段都是保留当时的值）' COLLATE 'utf8_general_ci',
	`is_finish` TINYINT(1) NULL DEFAULT '0' COMMENT '是否完成终审',
	`flow_node` TINYINT(1) NULL DEFAULT '0' COMMENT '下一个审批身份类型 0. 其他 1. 组织部终审',
	`flow_nodes` VARCHAR(100) NULL DEFAULT NULL COMMENT '已审批身份类型(按顺序排列，逗号分隔)' COLLATE 'utf8_general_ci',
	`flow_users` VARCHAR(100) NULL DEFAULT NULL COMMENT '已审批的审批人id(顺序排列，逗号分隔)' COLLATE 'utf8_general_ci',
	`is_agreed` TINYINT(1) NULL DEFAULT '0' COMMENT '是否最终同意申请，供查询使用',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='兼职申报变更记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=12
;
CREATE TABLE `parttime_approval_log` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`apply_id` INT(10) UNSIGNED NOT NULL COMMENT '申请记录',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '审批人',
	`type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '审批人类别,null代表管理员',
	`od_type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '管理员审批类型，0 初审 1终审',
	`status` TINYINT(1) UNSIGNED NOT NULL COMMENT '审批状态：0 未通过 1 通过',
	`suggestion` INT(11) NULL DEFAULT NULL COMMENT '意见',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	`create_time` DATETIME NOT NULL COMMENT '审批时间',
	`ip` VARCHAR(50) NOT NULL COMMENT 'ip' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `apply_id` (`apply_id`) USING BTREE,
	INDEX `user_id` (`user_id`) USING BTREE,
	INDEX `type_id` (`type_id`) USING BTREE
)
COMMENT='兼职申报申请记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=23
;
CREATE TABLE `parttime_approval_order` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`applicate_type_id` INT(10) UNSIGNED NOT NULL COMMENT '申请人身份',
	`approver_type_id` INT(10) UNSIGNED NOT NULL COMMENT '审批人分类',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，未用',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `applicate_type_id` (`applicate_type_id`) USING BTREE,
	INDEX `approver_type_id` (`approver_type_id`) USING BTREE
)
COMMENT='申请人身份关联的审批顺序，除了组织部终审'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=20
;
CREATE TABLE `parttime_approver` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '干部id',
	`type_id` INT(10) UNSIGNED NOT NULL COMMENT '类别： 1.院级党组织 2.外事部门（有国境外背景）3.分管/联系校领导（正职领导人员）',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `cadre_id` (`cadre_id`) USING BTREE,
	INDEX `type_id` (`type_id`) USING BTREE
)
COMMENT='审批人'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4974
;
CREATE TABLE `parttime_approver_black_list` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '干部id',
	`approver_type_id` INT(10) UNSIGNED NOT NULL COMMENT '审批人身份',
	`remark` VARCHAR(100) NULL DEFAULT NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	INDEX `FK__cadre_company_approver_type1` (`approver_type_id`) USING BTREE,
	INDEX `cadre_id` (`cadre_id`) USING BTREE
)
COMMENT='审批人黑名单，根据审批人身份设定黑名单'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=7587
;
CREATE TABLE `parttime_approver_type` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`name` VARCHAR(100) NOT NULL COMMENT '名称' COLLATE 'utf8_general_ci',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别： 1.院级党组织 2.外事部门（有国境外背景）3.分管/联系校领导（正职领导人员）',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='审批人分类'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=12
;

20210410 xcq

/*replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5007, 0, '兼职申报', '', 'menu', '', NULL, 862, '0/1/862/', 0, 'parttimeApply:menu', 4, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5011, 0, '兼职申报', '', 'url', '', '/parttime/parttimeApplyList', 5007, '0/1/862/5007/', 1, 'parttimeApply:approvalList', 4, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5010, 0, '兼职申报管理员', '', 'function', '', NULL, 5007, '0/1/862/5007/', 1, 'parttime:companyApply', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5009, 0, '审批权限管理', '', 'url', '', '/parttimeApproveAuth', 5007, '0/1/862/5007/', 1, 'parttimeApply:approve', 4, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5008, 0, '兼职申报（管理员）', '', 'url', '', '/parttime/parttimeApply_page', 5007, '0/1/862/5007/', 1, 'parttimeApply:*', 4, NULL, NULL, 1, NULL);
*/
INSERT INTO `base_content_tpl` ( `name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ( '[兼职申报]初审', NULL, 1, 'ct_parttime_apply_submit_info', NULL, NULL, NULL, NULL, '{0}：您好！{1}{2}老师已提交兼职申报申请，请登录 http://zzgz.**.edu.cn 进行初审。谢谢！', 1, 1, NULL, NULL, NULL, 50, 100719, '2018-06-07 11:57:08', '2019-06-08 16:49:23', 0, '兼职申报审批');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ( '[兼职申报]终审', NULL, 1, 'ct_parttime_apply_pass_info', NULL, NULL, NULL, NULL, '{0}：您好！{1}{2}老师兼职申报申请领导已完成审批，请登录 http://zzgz.**.edu.cn 进行终审。谢谢！', 1, 1, NULL, NULL, NULL, 51, 100719, '2018-06-07 11:57:41', '2019-06-08 16:49:18', 0, '兼职申报审批');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ( '兼职申报报送提醒', NULL, 1, 'part_branch_parttime_master', NULL, NULL, NULL, NULL, '{0}：您好！{1}{2}老师的兼职申报申请，请您直接点击 http://zzgz.**.edu.cn 在手机浏览器上登陆系统进行审批，账号为工作证号，密码为信息门户登陆密码。也可以在电脑的浏览器上输入上述网址进行审批。联系电话：**。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 69, 100719, '2020-12-10 17:42:57', '2021-04-03 17:45:14', 0, '');

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5007, 0, '干部兼职申报管理', '', 'menu', 'fa fa-random', NULL, 1, '0/1/', 0, 'parttimeApply:menu', 4, NULL, NULL, 1, 5390);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5008, 0, '兼职申报审批', '', 'url', '', '/parttime/parttimeApply_page', 5007, '0/1/5007/', 1, 'parttimeApply:*', 4, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5009, 0, '审批权限管理', '', 'url', '', '/parttimeApproveAuth', 5007, '0/1/5007/', 1, 'parttimeApply:approve', 4, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5010, 0, '兼职申报管理员', '', 'function', '', NULL, 5007, '0/1/5007/', 1, 'parttimeApply:admin', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (5011, 0, '兼职申报', '', 'url', '', '/parttime/parttimeApplyList', 5007, '0/1/5007/', 1, 'parttimeApply:approvalList', 4, NULL, NULL, 1, NULL);
