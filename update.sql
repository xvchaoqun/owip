

20210403
-- 戏曲-

ALTER TABLE `pcs_pr_allocate`
	CHANGE COLUMN `candidate_count` `candidate_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '代表上限，投票阶段每个分党委的推荐代表上限' AFTER `party_id`;

ALTER TABLE `pcs_pr_allocate`
	ADD COLUMN `pr_count` TEXT NULL COMMENT '代表构成，json格式，{元数据类型:数量,...}' AFTER `candidate_count`;

-- !!!!删除前先更新!!!! pr_count json
-- 执行 /test/pcs.jsp
/*ALTER TABLE `pcs_pr_allocate`
	DROP COLUMN `pro_count`,
	DROP COLUMN `stu_count`,
	DROP COLUMN `retire_count`;*/

ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '代表类型' AFTER `branch_vote`;

INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`,
                               `extra_attr`, `extra_options`, `sort_order`, `is_deleted`)
                               VALUES (95, '党代会代表类型', '', '', 'mc_pcs_pr_type', '', '账号类型', '', 2620, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (95, '专业技术人员和干部代表', 'mt_ztjcet', NULL, '1', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (95, '学生代表', 'mt_llqjgb', NULL, '2,3,4', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (95, '离退休代表', 'mt_i2uqrl', NULL, '5', '', 5, 1);

-- 删除 PCS_PR_TYPE_
-- 删除 PcsPrAlocateService（拼写错误）


20210402
-- 戏曲

-- 更新 unit_post_view
-- 删除 role_unit_admin_dw，role_unit_admin_XZ -> role_unit_admin
-- 更新导入样表、utils

ALTER TABLE `unit_post`
	CHANGE COLUMN `post_class` `post_class` INT(10) UNSIGNED NOT NULL COMMENT '职务类别，关联元数据，弃用' AFTER `post_type`;
ALTER TABLE `unit_post`
	CHANGE COLUMN `post_class` `post_class` INT(10) UNSIGNED NULL COMMENT '职务类别，关联元数据，弃用' AFTER `post_type`;

update sys_role set code='role_unit_admin', name='班子负责人' where code='role_unit_admin_xz';
delete from sys_role where code='role_unit_admin_dw';

20210331
-- 哈工大、北师大、吉大

/*types=${USER_TYPE_JZG}->types=${USER_TYPE_JZG},${USER_TYPE_RETIRE}*/


20210331   17:00
-- 南航、戏曲-、哈工大、大工、珠海-

-- 更新utils

/*INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (1302, 0, '组织部/学工部审核权限', '', 'function', '', NULL, 2555, '0/1/2555/', 1, 'pm:viewAll', NULL, NULL, NULL, 1, NULL);*/

20210330
-- 珠海、南航

/*ALTER TABLE `pm3_meeting`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态， 0 暂存 1 待分党委审核 2 待组织部审核 3 组织部/学工部审核通过 4待学工部审核' AFTER `content`;
*/

20210329
-- 哈工大、大工

update sys_resource set url=replace(url, '?type=', '?cls=') where url like '%stat_cadre_category?type%';

INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`,
                        `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_unit_admin_xz', '行政班子负责人', '1191,3050,5003,5004,5005', '-1', '-1', '-1', NULL, 0, 1, 69, '');
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`,
                        `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_unit_admin_dw', '党委班子负责人', '1191,3050,5003,5004,5005', '-1', '-1', '-1', NULL, 0, 1, 70, '');

ALTER TABLE `ow_member_reg` DROP COLUMN `type`;

20210329
-- 南航、吉大

ALTER TABLE `pmd_fee`
	ADD COLUMN `user_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '缴费时的人员类型，同sys_user.type' AFTER `user_id`;

update pmd_fee f, sys_user u set f.user_type=u.type where f.user_id=u.id;


20210328
-- 南航、北师大

-- MEMBER_STATUS_TRANSFER ->  MEMBER_STATUS_OUT

ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1 在职教职工 2本科生 3硕士研究生 4博士研究生 5 离退休教职工' AFTER `code`;

-- 硕士研究生
update sys_user u, sys_student_info s set u.`type`=3 where u.id=s.user_id and s.student_level=3;

-- 博士研究生
update sys_user u, sys_student_info s set u.`type`=4 where u.id=s.user_id and s.student_level=4;

-- 离退休教职工
update sys_user u, sys_teacher_info t set u.`type`=5 where u.id=t.user_id and t.is_retire=1;

ALTER TABLE `sys_student_info` DROP COLUMN `student_level`;

ALTER TABLE `sys_teacher_info` DROP COLUMN `is_retire`;

ALTER TABLE `ow_member`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '1正常，2 已转移至历史党员库 3已减员  4已转出' AFTER `political_status`,
	DROP COLUMN `type`;

ALTER TABLE `ow_member_apply` DROP COLUMN `type`;

ALTER TABLE `ow_member_inflow`
	DROP COLUMN `type`;
ALTER TABLE `ow_member_modify`
	DROP COLUMN `type`;
ALTER TABLE `ow_member_outflow`
	DROP COLUMN `type`;

ALTER TABLE `ow_member_out`
	DROP COLUMN `member_type`;

ALTER TABLE `ow_member_out_modify`
	DROP COLUMN `member_type`;

ALTER TABLE `ow_member_reg`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '账号类别，同sys_user.type' AFTER `passwd`;

-- 更新 ow_member_view  ow_member_apply_view ow_member_outflow_view  ow_member_out_view
-- pmd_config_member_view  dp_member_view  ow_party_view  ow_branch_view  ext_member_view ow_party_static_view

-- 删除系统缓存目录

DROP TABLE IF EXISTS `ow_member_history`;
CREATE TABLE IF NOT EXISTS `ow_member_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户id',
  `code` varchar(20) NOT NULL COMMENT '学工号',
  `realname` varchar(20) NOT NULL COMMENT '姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `lable` varchar(255) DEFAULT NULL COMMENT '标签 元数据可多选 记录转移至历史库的原因',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生 4博士研究生 5离退休教职工',
  `gender` tinyint(1) unsigned DEFAULT NULL COMMENT '性别 1男 2女',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `birth` date DEFAULT NULL COMMENT '出生时间',
  `party_name` varchar(200) DEFAULT NULL COMMENT '二级党组织名称',
  `branch_name` varchar(200) DEFAULT NULL COMMENT '党支部名称',
  `political_status` tinyint(1) NOT NULL COMMENT '党籍状态 1 预备党员、2 正式党员',
  `transfer_time` date DEFAULT NULL COMMENT '组织关系转入时间',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `sponsor` varchar(20) DEFAULT NULL COMMENT '入党介绍人',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `pro_post` varchar(50) DEFAULT NULL COMMENT '专业技术职务',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `detail_reason` varchar(200) DEFAULT NULL COMMENT '转至历史党员库详细原因',
  `out_reason` varchar(200) DEFAULT NULL COMMENT '移除原因',
  `add_user_id` int(10) unsigned NOT NULL COMMENT '添加人',
  `add_date` date NOT NULL COMMENT '添加时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0正常 1已移除',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='历史党员库';
INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `is_deleted`) VALUES (2605, '标签', '历史党员库', '', 'mc_mh_lable', '', '', '', 2619, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '党员出国境', 'mt_udgxuo', NULL, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '失联党员', 'mt_n8hlza', NULL, NULL, '', 1, 1);

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`,
                                 `sort_order`, `create_time`, `is_deleted`) VALUES ('更新党委班子负责人、行政班子负责人角色', '更新党委班子负责人、行政班子负责人角色，每5分钟执行一次',
                                                                                    'job.unit.UpdateUnitAdmin', '0 0/5 * * * ?', 0, 1, 33, '2021-03-23 18:17:48', 0);
update sys_resource set url=replace(url, 'cesResults', 'cesResult')  where url like '/cesResults%';
update sys_resource set url=replace(url, 'cadreEva_page', 'cadreEva')  where url like '/cadreEva_page%';

-- UPDATE sys_resource SET permission = 'cesResult:list' WHERE id = 5003;
update sys_resource set name='年终考核结果管理', parent_id=5003 where permission='cesResult:*';
update sys_resource set is_leaf=0 where permission='cesResult:list';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (1191, 0, '查看本单位的结果', '', 'function', '', NULL, 5003, '0/1/5003/', 1, 'cesResult:unit', NULL, NULL, NULL, 1, NULL);
update sys_resource set sort_order=7204 where permission='cesResult:teamList';
UPDATE `sys_resource` SET `sort_order`='29900',`menu_css`='' WHERE  `id`=2572;

ALTER TABLE `cadre`
	ADD COLUMN `is_sync_post` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '是否同步第一主职职务名称，如果存在主职，则保持同步，不可单独修改' AFTER `is_outside`;
-- 更新cadre_view

ALTER TABLE `pmd_fee`
	CHANGE COLUMN `pay_month` `start_month` DATE NOT NULL COMMENT '缴费起始月份' AFTER `type`,
	ADD COLUMN `end_month` DATE NULL COMMENT '缴费结束月份' AFTER `start_month`;

20210326
-- 哈工大

ALTER TABLE `ow_party`
	ADD COLUMN `address` VARCHAR(100) NULL DEFAULT NULL COMMENT '联系地址' AFTER `phone`;
-- 更新 ow_party_view

ALTER TABLE `ow_member_in`
	ADD COLUMN `idcard` VARCHAR(20) NULL DEFAULT NULL COMMENT '身份证号码' COLLATE 'utf8_general_ci' AFTER `user_id`,
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1 男 2 女' AFTER `idcard`,
	ADD COLUMN `birth` DATE NULL DEFAULT NULL COMMENT '出生年月' AFTER `gender`,
	ADD COLUMN `nation` VARCHAR(100) NULL DEFAULT NULL COMMENT '民族' COLLATE 'utf8_general_ci' AFTER `birth`;

update ow_member_in i, sys_user_view u set i.birth=u.birth, i.nation=u.nation,
    i.idcard=u.idcard, i.gender=u.gender where i.user_id=u.id;

ALTER TABLE `ow_member_out_modify`
	ADD COLUMN `member_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '用户类别，1 学生 2 在职教职工 3 离退休' AFTER `apply_user_id`,
	ADD COLUMN `idcard` VARCHAR(20) NULL DEFAULT NULL COMMENT '身份证号码' COLLATE 'utf8_general_ci' AFTER `member_type`,
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1 男 2 女' AFTER `idcard`,
	ADD COLUMN `user_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '学工号' COLLATE 'utf8_general_ci' AFTER `gender`,
	ADD COLUMN `realname` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名' COLLATE 'utf8_general_ci' AFTER `user_code`,
	ADD COLUMN `age` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年龄' AFTER `realname`,
	ADD COLUMN `nation` VARCHAR(100) NULL DEFAULT NULL COMMENT '民族' COLLATE 'utf8_general_ci' AFTER `age`,
	ADD COLUMN `political_status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '党籍状态' AFTER `nation`;

ALTER TABLE `ow_member_in_modify`
	ADD COLUMN `idcard` VARCHAR(20) NULL DEFAULT NULL COMMENT '身份证号码' COLLATE 'utf8_general_ci' AFTER `apply_user_id`,
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1 男 2 女' AFTER `idcard`,
	ADD COLUMN `birth` DATE NULL DEFAULT NULL COMMENT '出生年月' AFTER `gender`,
	ADD COLUMN `nation` VARCHAR(100) NULL DEFAULT NULL COMMENT '民族' COLLATE 'utf8_general_ci' AFTER `birth`;

INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`,
                                 `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_member_transfer_info', NULL, NULL, NULL, '校内组织关系转接说明', '&#60;span style=&#34;font-size:18.6667px;font-weight:700;&#34;&#62;注：本校读取研究生或博士生或留校，&#60;/span&#62;&#60;span style=&#34;font-size:18.6667px;font-weight:700;color:#FF0000;&#34;&#62;需通过现有学工号提交完成“组织关系转出”审批，再用新分配学工号提交完成“组织关系转入”审批。&#60;/span&#62;', NULL, '', 0, 60);

-- 哈工大执行 /test/member_out.jsp

ALTER TABLE `cadre_research`
	ADD COLUMN `file_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件名' COLLATE 'utf8_general_ci' AFTER `research_type`,
	ADD COLUMN `file_path` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件路径' COLLATE 'utf8_general_ci' AFTER `file_name`;

ALTER TABLE `cadre_book`
	ADD COLUMN `file_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件名' COLLATE 'utf8_general_ci' AFTER `status`,
	ADD COLUMN `file_path` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件路径' COLLATE 'utf8_general_ci' AFTER `file_name`;

ALTER TABLE `cadre_parttime`
	ADD COLUMN `file_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件名' COLLATE 'utf8_general_ci' AFTER `status`,
	ADD COLUMN `file_path` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件路径' COLLATE 'utf8_general_ci' AFTER `file_name`;

ALTER TABLE `cadre_train`
	ADD COLUMN `file_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件名' COLLATE 'utf8_general_ci' AFTER `status`,
	ADD COLUMN `file_path` VARCHAR(100) NULL DEFAULT NULL COMMENT '文件路径' COLLATE 'utf8_general_ci' AFTER `file_name`;

-- 更新 utils  新增jar包 itext-asian  itextpdf

20210325
-- 哈工大

REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cas_type', '校园账号登录类别', '1', 2, 56, '0：使用系统密码登陆  1：必须统一身份认证登陆  2或3：可通过代理接口认证');

update ow_member_out o ,ow_member_view m set o.member_type =if(m.type=2, 1, if(m.type=1 and m.is_retire=0, 2, 3))
where o.user_id=m.user_id;



20210323
-- 北师大

20210321
-- 南航， 哈工大

ALTER TABLE `cadre_eva_result`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0. 个人年度测评结果 1. 单位年度测评结果' COLLATE 'utf8_general_ci' AFTER `id`,
	ADD COLUMN `unit_id` INT NULL COMMENT '单位id' COLLATE 'utf8_general_ci' AFTER `type`,
	ADD COLUMN `title` VARCHAR(100) NULL DEFAULT NULL COMMENT '时任单位及职务名称' COLLATE 'utf8_general_ci' AFTER `remark`;

ALTER TABLE `cadre_eva_result`
	CHANGE COLUMN `cadre_id`    `cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属干部' AFTER `unit_id`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (5003, 0, '考核结果管理', '', 'menu', 'fa fa-street-view', NULL, 1, '0/1/', 0, 'cesResult:menu', 3, NULL, NULL, 1, 7203);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (5006, 0, '干部年度考核记录', '', 'url', '', '/cadreEva_page?cls=1', 5003, '0/1/5003/', 1, 'cadreEva:list', 3, NULL, NULL, 1, 7202);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (5005, 0, '班子年终考核结果', '', 'url', '', '/cesResults?type=2', 5003, '0/1/5003/', 1, 'cesResult:teamList', 3, NULL, NULL, 1, 7202);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (5004, 0, '干部年终考核结果', '', 'url', '', '/cesResults?type=1', 5003, '0/1/5003/', 1, 'cesResult:cadreList', 3, NULL, NULL, 1, 7203);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (5007, 0, '兼职申报', '', 'url', '', '/cadre/partTimeDeclare', 353, '0/1/353/', 1, 'cadre:partTimeDeclare', 4, NULL, NULL, 1, NULL);

-- 更新 utils

update sys_resource set permission='cadreEvaResult:cadreList' where permission='cadreEvaResult:list';
update sys_resource set permission='cadreEvaResult:list' where permission='cadreEvaResult:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (1190, 0, '后台管理', '', 'function', '', NULL, 3050, '0/1/88/90/3050/', 1, 'cadreEvaResult:*', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `cadre_eva_result`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0 干部 1  班子' AFTER `id`,
	CHANGE COLUMN `unit_id` `unit_id` INT(11) NULL DEFAULT NULL COMMENT '所属单位' AFTER `type`,
	CHANGE COLUMN `title` `title` VARCHAR(100) NULL DEFAULT NULL COMMENT '时任职务' COLLATE 'utf8_general_ci' AFTER `cadre_id`;

RENAME TABLE `cadre_eva_result` TO `ces_result`;
-- 删除 CadreEvaResult

update sys_resource set permission=replace(permission, 'cadreEvaResult:', 'cesResult:') where permission like 'cadreEvaResult:%';

ALTER TABLE `ces_result`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '1 干部 2 班子' AFTER `id`;

ALTER TABLE `ces_result`
	CHANGE COLUMN `num` `num` INT UNSIGNED NULL DEFAULT NULL COMMENT '总数' AFTER `group_name`,
	CHANGE COLUMN `sort_order` `rank` INT UNSIGNED NULL DEFAULT NULL COMMENT '排名' AFTER `num`;

ALTER TABLE `ces_result`
	CHANGE COLUMN `group_name` `name` VARCHAR(100) NULL DEFAULT NULL COMMENT '测评类别' COLLATE 'utf8_general_ci' AFTER `year`;

-- 更新导入表
-- 删除 OrgAdminView

20210318
-- 大工

20210317
-- 哈工大 / 测试服务器

update ow_branch_member_group set actual_tran_time=null where is_deleted=0 and actual_tran_time is not null;
update ow_party_member_group set actual_tran_time=null where is_deleted=0 and actual_tran_time is not null;

20210317
-- 吉大，北师大

20210315
-- 哈工大

ALTER TABLE `ow_member_out`
	ADD COLUMN `age` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年龄' AFTER `sn`,
	ADD COLUMN `nation` VARCHAR(100) NULL DEFAULT NULL COMMENT '民族' AFTER `age`,
	ADD COLUMN `political_status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '党籍状态' AFTER `nation`;

ALTER TABLE `ow_member_out`
	ADD COLUMN `check_time` DATETIME NULL DEFAULT NULL COMMENT '审批完成时间' AFTER `apply_time`;

ALTER TABLE `ow_member_out`
	ADD COLUMN `member_type` TINYINT UNSIGNED NULL DEFAULT NULL COMMENT '用户类别，1 学生 2 在职教职工 3 离退休' AFTER `sn`;

/*update  ow_member_out mo, ow_member m
left join sys_teacher_info t on t.user_id = m.user_id
set mo.member_type=if(t.is_retire, 3, m.type), mo.check_time=mo.apply_time
where mo.user_id=m.user_id;*/

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `member_type` `member_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '用户类别，1 学生 2 在职教职工 3 离退休' AFTER `sn`,
	ADD COLUMN `idcard` VARCHAR(20) NULL DEFAULT NULL COMMENT '身份证号码' AFTER `member_type`,
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1 男 2 女' AFTER `idcard`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `user_code` VARCHAR(20) NULL DEFAULT NULL COMMENT '学工号' AFTER `gender`,
	ADD COLUMN `realname` VARCHAR(50) NULL DEFAULT NULL COMMENT '姓名' AFTER `user_code`;

update ow_member_out mo
left join ow_member m on mo.user_id=m.user_id
left join sys_user_view u on mo.user_id=u.id
set mo.user_code=u.code, mo.realname=u.realname, mo.gender=u.gender, mo.idcard=u.idcard, mo.nation=u.nation, mo.age=TIMESTAMPDIFF(YEAR, u.birth, mo.apply_time),
    mo.political_status=m.political_status;


20210315
-- 北师大
update sys_role set name=replace(name, '干部工作管理员', '干部管理员');
update sys_role set name=replace(name, '干部管理员（目录1）', '干部管理员');
update sys_role set name=replace(name, '干部管理员（目录2）', '干部管理员（部分权限）');
update sys_role set name=replace(name, '党建工作管理员', '党建管理员');
update sys_role set name=replace(name, '管理员(干部教育培训)', '培训管理员');
update sys_resource set name='干部管理员', remark='管理所有干部相关的模块' where permission='cadre:admin';
-- 删除 ROLE_CADREADMIN  及角色

update sys_role r, sys_resource re set r.resource_ids=concat(r.resource_ids, ',', re.id)
where r.code='cadreAdmin-menu1' and re.permission='cadre:admin';
delete from sys_role where code='cadreAdmin';

ALTER TABLE `base_short_msg_tpl`
	DROP COLUMN `role_id`;

INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`,
                                 `is_deleted`, `sort_order`) VALUES (NULL, 'hf_avatar_info', NULL, NULL, NULL, '上传照片说明', '&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62;“照片”栏中应插入近三年彩色免冠证件照片，纸质版应粘贴彩色冲洗照片。照片标准如下：&#60;/span&#62;&#60;br /&#62;\r\n&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62; （1）照片高宽比例为5：4，分辨率最低为600×480（高600像素，宽480像素），最高为1200×960（高1200像素，宽960像素）。&#60;/span&#62;&#60;br /&#62;\r\n&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62; （2）照片背景颜色统一为淡蓝色，色值为#3492C4(即R52，G146，B196），以肩膀为界，由上至下，设置为蓝白渐变。&#60;/span&#62;&#60;br /&#62;\r\n&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62; （3）照片清晰度不低于300DPI。&#60;/span&#62;&#60;br /&#62;\r\n&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62; （4）照片格式为JPG，大小200K以上，不超过2M&#60;/span&#62;&#60;br /&#62;\r\n&#60;p&#62;\r\n	&#60;span style=&#34;font-size:16px;font-family:SimHei;&#34;&#62; （5）照片头像高度约占照片整体高度的三分之二，宽度约占照片整体宽度的二分之一（见下图）。&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p style=&#34;text-align:center;&#34;&#62;\r\n	&#60;span style=&#34;font-size:16px;&#34;&#62;&#60;img src=&#34;/attached/image/20210313/20210313203923_768.png&#34; alt=&#34;&#34; width=&#34;200&#34; height=&#34;221&#34; title=&#34;&#34; align=&#34;&#34; /&#62;&#60;br /&#62;\r\n&#60;/span&#62; \r\n&#60;/p&#62;', NULL, '', 0, 59);
-- 上传样例照片

20210312
-- 哈工大，南航

 -- 已更新北航
 REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
 VALUES ('cas_type', '校园账号登录类别', '1', 2, 56, '0：使用系统密码登陆  1：必须统一身份认证登陆  2或3：可通过代理接口认证');
-- 已更新北航


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (120, 0, '个人信息', '', 'function', '', NULL, 108, '0/1/108/', 1, 'index:self', NULL, NULL, NULL, 1, NULL);
update  sys_role set resource_ids= concat(resource_ids, ',120')  where concat(',',resource_ids, ',') like '%,108,%';



20210312
-- 南航

20210311
-- 西工大
update sys_resource set parent_id=(select id from (select id from sys_resource where permission='sys:menu') tmp) where permission='apiKey:*';

ALTER TABLE `base_api_key`
	CHANGE COLUMN `request_uri` `request_uri` VARCHAR(200) NOT NULL COMMENT '请求地址，正则匹配' COLLATE 'utf8_general_ci' AFTER `secret`,
	ADD COLUMN `valid_ip` VARCHAR(200) NULL COMMENT '允许访问的IP地址，正则匹配' AFTER `request_uri`;


20210311
-- 南航

INSERT INTO `sys_resource` (id,`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (3050, 0, '年终考核测评', '', 'function', '', NULL, 90, '0/1/88/90/', 1, 'cadreEvaResult:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (id,`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (3051,0, '设置考察对象有效期', '', 'function', '', NULL, 341, '0/1/339/341/', 1, 'cadreInspect:validTime', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (id,`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (3052,0, '任务报送', '', 'function', '', NULL, 562, '0/1/560/562/', 1, 'userOaTask:report', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `sc_matter_item`
	ADD CONSTRAINT `FK_sc_matter_item_sc_matter` FOREIGN KEY (`matter_id`) REFERENCES `sc_matter` (`id`) ON DELETE CASCADE;

ALTER TABLE `sc_matter_access_item`
	ADD CONSTRAINT `FK_sc_matter_access_item_sc_matter_access` FOREIGN KEY (`access_id`) REFERENCES `sc_matter_access` (`id`) ON DELETE CASCADE,
	ADD CONSTRAINT `FK_sc_matter_access_item_sc_matter_item` FOREIGN KEY (`matter_item_id`) REFERENCES `sc_matter_item` (`id`) ON DELETE CASCADE;

ALTER TABLE `sc_matter_check_item`
	ADD CONSTRAINT `FK_sc_matter_check_item_sc_matter_check` FOREIGN KEY (`check_id`) REFERENCES `sc_matter_check` (`id`) ON DELETE CASCADE;

-- 更新 sc_matter_item_view   sc_matter_access_item_view    sc_matter_check_item_view
--  sc_letter_reply_view   sc_letter_reply_item_view

ALTER TABLE `sc_committee`
	CHANGE COLUMN `file_path` `file_path` VARCHAR(200) NULL COMMENT '文件，ppt' COLLATE 'utf8_general_ci' AFTER `id`;

delete from sc_border_item where border_id not in (select id from sc_border);
ALTER TABLE `sc_border_item`
	ADD CONSTRAINT `FK_sc_border_item_sc_border` FOREIGN KEY (`border_id`) REFERENCES `sc_border` (`id`) ON DELETE CASCADE;

20210309
-- 南航、大工

CREATE TABLE `pmd_fee` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` INT(10) UNSIGNED NOT NULL COMMENT '缴费类型，元数据，包含补缴党费、捐赠党费',
	`pay_month` DATE NOT NULL COMMENT '缴费月份，默认当月，可以修改',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委，本人添加时，只允许选择本支部，支部、分党委同理',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所在党支部',
	`amt` DECIMAL(10,2) UNSIGNED NULL DEFAULT NULL COMMENT '缴费金额',
	`reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '缴费原因',
	`is_online_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费方式，1 线上缴费 0 现金缴费',
	`is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '线上缴费途径， 1 线上缴费、0 代缴党费',
	`has_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0：未交费，1：已缴费',
	`pay_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '缴费人',
	`pay_time` DATETIME NULL DEFAULT NULL COMMENT '缴费时间',
	`order_no` VARCHAR(30) NULL DEFAULT NULL COMMENT '缴费订单号，批量缴费时允许重复',
	`order_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '生成订单号时的账号',
	`remark` TEXT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '审核状态，0 待审核 1 支部审核 2 分党委审核 3 组织部审核',
	PRIMARY KEY (`id`)
)
COMMENT='党员缴纳党费'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=10
;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2793, 0, '党费补缴列表', '', 'url', '', '/pmd/pmdFee', 564, '0/1/564/', 0, 'pmdFee:list', NULL, NULL, NULL, 1, 1500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2794, 0, '党费补缴编辑', '', 'function', '', NULL, 2793, '0/1/564/2793/', 1, 'pmdFee:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2795, 0, '党费补缴删除', '', 'function', '', NULL, 2793, '0/1/564/2793/', 1, 'pmdFee:del', NULL, NULL, NULL, 1, NULL);

INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`,
                               `extra_attr`, `extra_options`, `sort_order`, `is_deleted`)
                               VALUES (3101, '党费补缴类型', '党费收缴管理', '党费补缴列表', 'mc_pmd_fee_type', '', '', '', 2618, 0);

ALTER TABLE `pmd_order`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT 1 COMMENT '缴费类别，1 缴纳每月党费 2 补缴党费' AFTER `sn`,
	CHANGE COLUMN `member_id` `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '缴费记录，如果是每月党费，则是pmd_member_id，如果是补缴党费，则是pmd_fee_id' AFTER `type`,
	CHANGE COLUMN `is_batch` `is_batch` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否批量缴费，批量缴费时，record_id为空' AFTER `record_id`,
	DROP INDEX `member_id`,
	ADD INDEX `member_id` (`record_id`) USING BTREE;

ALTER TABLE `pmd_order`
	DROP INDEX `member_id`,
	ADD INDEX `record_id` (`record_id`) USING BTREE;

ALTER TABLE `pmd_order_item`
	CHANGE COLUMN `member_id` `record_id` INT(10) UNSIGNED NOT NULL COMMENT '缴费记录' AFTER `sn`,
	DROP INDEX `member_id`,
	ADD INDEX `record_id` (`record_id`) USING BTREE;

ALTER TABLE `pmd_fee`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '状态，0 正常 1 已删除' AFTER `remark`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (991, 0, '支付', '', 'function', '', NULL, 2793, '0/1/564/2793/', 1, 'pmdFee:pay', NULL, NULL, NULL, 1, NULL);

-- 更新 pmd_pay_view, pmd_pay_item_view

20210308
-- 哈工大


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (4006, 0, '中组部年度统计', '', 'menu', '', NULL, 105, '0/1/105/', 0, 'statCod:list', NULL, NULL, NULL, 1, 29600);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (4008, 0, '申请人-中组部', '', 'url', '', '/stat/statCodAppply', 4006, '0/1/105/4006/', 1, 'statCodAppply:list', NULL, NULL, NULL, 1, 1000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (4007, 0, '党员-中组部', '', 'url', '', '/stat/statCodMember', 4006, '0/1/105/4006/', 1, 'statCodMember:list', NULL, NULL, NULL, 1, 900);
UPDATE `sys_resource` SET `url`='/stat/statCod?cls=1' WHERE  `id`=4008;
UPDATE `sys_resource` SET `url`='/stat/statCod?cls=2' WHERE  `id`=4007;

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `email` `email` VARCHAR(500) NULL COMMENT '邮箱' COLLATE 'utf8_general_ci' AFTER `home_phone`;


-- 更新 pmd_config_member_view
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2796, 0, '党费核算管理', '组织部党费收缴管理员、分党委党费收缴管理员、支部党费收缴管理员', 'url', '', '/pmd/pmdConfigDuePay', 564, '0/1/564/', 1, 'pmdConfigDuePay:*', NULL, NULL, NULL, 1, 1300);

ALTER TABLE `pmd_month`
	ADD COLUMN `pay_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '缴费开关状态 0 未开启  1 已开启' AFTER `status`,
	ADD COLUMN `pay_info` VARCHAR(50) NULL DEFAULT NULL COMMENT '缴费未开启时的文字提示' AFTER `pay_status`;
ALTER TABLE `pmd_party`
	ADD COLUMN `pay_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费开关状态 0 未开启  1 已开启' AFTER `sort_order`,
	ADD COLUMN `pay_info` VARCHAR(50) NULL DEFAULT NULL COMMENT '缴费未开启时的文字提示' AFTER `pay_status`;
ALTER TABLE `pmd_month`
	CHANGE COLUMN `pay_info` `pay_tip` VARCHAR(50) NULL DEFAULT NULL COMMENT '缴费未开启时的文字提示' AFTER `pay_status`;
ALTER TABLE `pmd_party`
	CHANGE COLUMN `pay_info` `pay_tip` VARCHAR(50) NULL DEFAULT NULL COMMENT '缴费未开启时的文字提示' AFTER `pay_status`;
-- 更新 pmd_party_view


ALTER TABLE `cadre`
	ADD COLUMN `type` INT(10) UNSIGNED NULL COMMENT '类别，元数据，院系干部、机关干部、直属干部等' AFTER `user_id`;
INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`,
                               `extra_attr`, `extra_options`, `sort_order`, `is_deleted`)
                               VALUES (10, '干部类别', '', '', 'mc_cadre_type', '', '', '', 2618, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`,
                              `sort_order`, `available`) VALUES (10, '机关干部', 'mt_cadre_type_jg', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`,
                              `available`) VALUES (10, '院系干部', 'mt_cadre_type_yx', NULL, NULL, '', 2, 1);
update cadre set type =(select id from base_meta_type where code='mt_cadre_type_yx') where is_dep=1;
update cadre set type =(select id from base_meta_type where code='mt_cadre_type_jg') where is_dep=0;
ALTER TABLE `cadre` DROP COLUMN `is_dep`;

-- 更新 cadre_view
-- CADRE_TYPE_MAP -> CADRE_CATEGORY_MAP
-- CADRE_TYPE_CJ -> CADRE_CATEGORY_CJ
-- CADRE_TYPE_KJ -> CADRE_CATEGORY_KJ

-- 更新 ow_member_apply_view

20210304
-- 南航

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (24, 0, '更换学工号', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'sysUser:exchangeCode', NULL, NULL, NULL, 1, NULL);


20210301
-- 北师大

UPDATE `sys_resource` SET `name`='全校研究生党员信息统计',`url`='/statOwInfo?cls=1' WHERE  `id`=2635;

ALTER TABLE `pmd_config_reset`
	ADD COLUMN `user_type` TINYINT(3) NULL COMMENT '限定人员类型， 1 学生 2 教职工 3 离退休' AFTER `reset`;

20210228
-- 吉大  哈工大  西工大  北航

-- 已经更新了哈工大
ALTER TABLE `cadre_edu`
	ADD COLUMN `adform_display_as_fulltime` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '显示为主要经历，仅对在职教育有效' AFTER `adform_resume_exclude`;


ALTER TABLE `unit_post`
	ADD COLUMN `label` VARCHAR(255) NULL DEFAULT NULL COMMENT '标签，关联元数据，多选' AFTER `is_cpc`;
-- 更新 unit_post_view (需要在cadre_view后面更新）

ALTER TABLE `cadre_punish`
    ADD COLUMN	`proof` VARCHAR(255) NULL DEFAULT NULL COMMENT '处分文件'  AFTER `unit`,
    ADD COLUMN	`proof_filename` VARCHAR(255) NULL DEFAULT NULL COMMENT '处分文件名' AFTER `proof`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (4005, 0, '全校基层党组织基本情况', '', 'url', '', '/statOwInfo?cls=7', 2634, '0/1/105/2634/', 1, 'statOwInfo:cls7', NULL, NULL, NULL, 1, 50);



20210225
-- 吉大


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
    VALUES (5000, 0, '各二级党组织研究生队伍党员信息分析', '', 'url', '', '/statOwInfo?cls=2', 2634, '0/1/105/2634/', 1, 'statPartyYjsInfo:list', NULL, NULL, NULL, 1, 50);


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (2635, 0, '信息统计', '党建管理员 分党委管理员', 'url', '', '/statOwInfo', 2634, '0/1/105/2634/', 1, 'statOwInfo:list', NULL, NULL, NULL, 1, 50);

UPDATE `sys_resource` SET `url`='/statOwInfo?cls=1' WHERE  `id`=2635;


update sys_student_info set student_level=2;
update sys_student_info set student_level=3 where edu_level like '%硕士%';
update sys_student_info set student_level=4 where edu_level like '%博士%';

-- 重建 update/dp0219.sql 表  并 更新view_dp
replace INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2595, '其他统战人员类别', '统战信息管理', '统战人员信息', 'mc_dp_other_type', '', '', '', 2595);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '华侨、归侨及侨眷', 'mt_dp_other_type_1', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '欧美同学会会员', 'mt_dp_other_type_2', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '知联会员', 'mt_dp_other_type_3', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '宗教信仰', 'mt_dp_other_type_5', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '港澳台', 'mt_dp_other_type_4', NULL, '', '', 4, 1);
DELETE FROM `base_meta_type` WHERE `id`=673;

ALTER TABLE `sys_teacher_info`
	ADD COLUMN `is_full_time_teacher` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否专任教师' AFTER `post`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
    VALUES (5001, 0, '全校教工队伍党员信息分析', '', 'url', '', '/statOwInfo?cls=5', 2634, '0/1/105/2634/', 1, 'statOwJzgInfo:list', NULL, NULL, NULL, 1, 50);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
    VALUES (5002, 0, '各二级党组织专任教师队伍党员信息分析', '', 'url', '', '/statOwInfo?cls=6', 2634, '0/1/105/2634/', 1, 'statPartyJzgInfo:list', NULL, NULL, NULL, 1, 50);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (4004, 0, '全校党支部书记队伍整体情况分析', '', 'url', '', '/statOwInfo?cls=8', 2634, '0/1/105/2634/', 1, 'statOwInfo:cls8', NULL, NULL, NULL, 1, 50);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (4003, 0, '各二级党组织本科生队伍党员信息分析', '', 'url', '', '/statOwInfo?cls=4', 2634, '0/1/105/2634/', 1, 'statOwInfo:cls4', NULL, NULL, NULL, 1, 50);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (4002, 0, '全校本科生队伍党员信息分析', '', 'url', '', '/statOwInfo?cls=3', 2634, '0/1/105/2634/', 1, 'statOwInfo:cls3', NULL, NULL, NULL, 1, 50);


20210204
-- 西工大

ALTER TABLE `base_api_key`
	CHANGE COLUMN `name` `app` VARCHAR(50) NOT NULL COMMENT '应用名称' COLLATE 'utf8_general_ci' AFTER `id`,
	CHANGE COLUMN `api_key` `secret` VARCHAR(50) NOT NULL COMMENT '秘钥' COLLATE 'utf8_general_ci' AFTER `app`,
	ADD COLUMN `request_uri` VARCHAR(100) NOT NULL COMMENT '请求地址' AFTER `secret`,
	CHANGE COLUMN `remark` `remark` VARCHAR(50) NULL COMMENT '备注' COLLATE 'utf8_general_ci' AFTER `request_uri`;

update base_api_key set request_uri='/api/abroad/approve_count' where app='oa';
update base_api_key set request_uri='/api/member/status' where app='LXXT';
update base_api_key set request_uri='/api/member/print' where app='zcdy';

20210203
-- 北师大  南航

CREATE TABLE `base_api_key` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
	`name` VARCHAR(50) NULL DEFAULT '0' COMMENT 'app名称' COLLATE 'utf8_general_ci',
	`api_key` VARCHAR(50) NULL DEFAULT '0' COMMENT '对应键值' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(50) NOT NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='API接口管理'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=13;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (4001, 0, '接口管理', '', 'url', '', '/apiKey', 67, '0/1/67/', 1, 'apiKey:*', NULL, NULL, NULL, 1, NULL);

/*北师大更新
  INSERT INTO `base_api_key` (`name`, `api_key`, `remark`) VALUES ('LXXT', '7507a3c61bf38d9f06d00c3f2fa2de58', '查询组织关系转接状态');
    INSERT INTO `base_api_key` (`name`, `api_key`, `remark`) VALUES ('oa', 'b887e286bf5d82b7b9712ed03d3e6e0e', '查询因私审批数量');
    INSERT INTO `base_api_key` (`name`, `api_key`, `remark`) VALUES ('zcdy', '5931e054d3b59be97b3481f6e604afe6', '组织关系转出介绍信自助打印回调接口');
*/

20210203
-- 哈工大  吉大

ALTER TABLE `cadre_edu`
	ADD COLUMN `resume` VARCHAR(200) NULL COMMENT '简历上的表述，如果填写了，则完全按此进行表述' AFTER `note_brackets_exclude`,
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci' AFTER `adform_resume_exclude`;

20210202
-- 吉大

20210201
-- 哈工大

-- 元数据  同等学历  - > 同等学力 ，  yjs| -> ss| (并修改一下硕士研究生所属大类）
update base_meta_class set bool_attr='任免表学历学位栏隐藏该学历' where code='mc_edu';
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('defaultEvaResult', '任免表默认考核结果表述', '{0}年均为合格', 1, 98, '');
--

ALTER TABLE `cadre_edu`
	CHANGE COLUMN `note` `note` VARCHAR(100) NULL COMMENT '补充说明' COLLATE 'utf8_general_ci' AFTER `remark`,
	ADD COLUMN `note_brackets_exclude` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '补充说明不加括号' AFTER `note`,
	ADD COLUMN `adform_edu_exclude` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '不计入任免表学历学位栏' AFTER `note_brackets_exclude`,
	ADD COLUMN `adform_resume_exclude` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '不计入任免表简历栏' AFTER `adform_edu_exclude`;


20210128
-- 吉大

-- 删除 CetProjectDetailController

20210128
-- 哈工大

-- 为spring.properties 增加 logback.xxx属性

20210122
-- 大工

ALTER TABLE `ow_apply_approval_log`
	DROP INDEX `FK_ow_apply_approval_log_sys_user`,
	DROP INDEX `FK_ow_apply_approval_log_sys_user_2`,
	DROP FOREIGN KEY `FK_ow_apply_approval_log_sys_user`;

ALTER TABLE `sys_student_info`
    CHANGE COLUMN `enrol_year` `enrol_year` VARCHAR(50) NULL COMMENT '招生年度' COLLATE 'utf8_general_ci' AFTER `is_full_time`,
    CHANGE COLUMN `grade` `grade` VARCHAR(10) NULL COMMENT '当前所在年级' COLLATE 'utf8_general_ci' AFTER `period`,
	ADD COLUMN `student_level` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '学生分类 1教职工 2本科生 3硕士研究生 4博士研究生' AFTER `type`,
	ADD COLUMN `is_graduate` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否毕业' AFTER `grade`,
	ADD COLUMN `is_work` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否就业' AFTER `is_graduate`,
	ADD COLUMN `is_graduate_grade` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '当前所在年级是否是毕业年级' AFTER `is_work`;

ALTER TABLE `ow_branch_member`
	DROP FOREIGN KEY `FK_ow_branch_member_sys_user`;
ALTER TABLE `ow_branch_member`
	ADD CONSTRAINT `FK_ow_branch_member_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE;
-- 更新 ow_member_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2572, 0, '历史党员管理', '', 'url', 'fa fa-star-o', '/member/memberHistory', 105, '0/1/105/', 0, 'memberHistory:list', 2, NULL, NULL, 1, 7537);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2633, 0, '删除', '', 'function', '', NULL, 2572, '0/1/105/2572/', 1, 'memberHistory:', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2573, 0, '编辑', '', 'function', '', NULL, 2572, '0/1/105/2572/', 1, 'memberHistory:edit', 2, NULL, NULL, 1, NULL);

DROP TABLE IF EXISTS `ow_member_history`;
CREATE TABLE IF NOT EXISTS `ow_member_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL COMMENT '学工号',
  `realname` varchar(20) NOT NULL COMMENT '姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `type` tinyint(1) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `gender` tinyint(1) unsigned DEFAULT NULL COMMENT '性别 1男 2女',
  `nation` varchar(50) DEFAULT NULL COMMENT '民族',
  `native_place` varchar(100) DEFAULT NULL COMMENT '籍贯',
  `birth` date DEFAULT NULL COMMENT '出生时间',
  `party_name` varchar(200) DEFAULT NULL COMMENT '二级党组织名称',
  `branch_name` varchar(200) DEFAULT NULL COMMENT '党支部名称',
  `political_status` tinyint(1) NOT NULL COMMENT '党籍状态 1 预备党员、2 正式党员',
  `transfer_time` date DEFAULT NULL COMMENT '组织关系转入时间',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `sponsor` varchar(20) DEFAULT NULL COMMENT '入党介绍人',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `pro_post` varchar(50) DEFAULT NULL COMMENT '专业技术职务',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `remark1` varchar(255) DEFAULT NULL COMMENT '备注1',
  `remark2` varchar(255) DEFAULT NULL COMMENT '备注2',
  `remark3` varchar(255) DEFAULT NULL COMMENT '备注3',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='历史党员库';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2634, 0, '党建信息统计', '', 'menu', '组织部，分党委', NULL, 105, '0/1/105/', 0, 'statSummary:menu', NULL, NULL, NULL, 1, 29700);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2566, 0, '二级党委数据统计', '组织部,分党委', 'url', '', '/stat/partySum?cls=1', 2634, '0/1/105/2634/', 1, 'stat:partySum', 2, NULL, NULL, 1, 60);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2565, 0, '组织机构年统数据', '组织部', 'url', '', '/stat/owSum', 2634, '0/1/105/2634/', 1, 'stat:owSum', 1, NULL, NULL, 1, 70);

ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生' AFTER `code`;

20210118
-- 南航、西工大、北师大

20210115
-- 大工

ALTER TABLE `cadre_inspect`
	ADD COLUMN `valid_time` DATETIME NULL DEFAULT NULL COMMENT '有效期' AFTER `record_user_id`;
-- 更新 cadre_inspect_view

CREATE TABLE `cadre_eva_result` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '所属干部',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年份',
	`group_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '测评类别(分区名称)',
	`sort_order` INT(11) NULL DEFAULT NULL COMMENT '排名',
	`num` INT(11) NULL DEFAULT NULL COMMENT '总人数',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='干部年度测评结果'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2567, 0, '基层党组织信息统计', '', 'url', '', '/party/partyInfoStatistics', 260, '0/1/260/', 1, 'stat:infoStatistics', NULL, NULL, NULL, 1, 75);
ALTER TABLE `ow_party`
	ADD COLUMN `fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上级党组织' AFTER `id`;
-- 更新 ow_party_static_view、更新 ow_party_view

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('use_inside_pgb', '启用内设党总支', 'false', 3, 97, '在二级党委和党支部之间加一层内设党总支');


20210115
-- 更新utils

20210112
哈工大

20210108
大工

ALTER TABLE `sys_approval_log`
	CHANGE COLUMN `stage` `stage` TEXT NULL COMMENT '阶段备注，比如 初审、返回等' COLLATE 'utf8_general_ci' AFTER `type`;

ALTER TABLE `ow_member_out`
	ADD COLUMN `year` INT(10) UNSIGNED NULL COMMENT '年份 用于生成介绍信编号' AFTER `branch_id`,
	ADD COLUMN `sn` INT(10) UNSIGNED NULL COMMENT '编号 用于生成介绍信编号 依次递增' AFTER `year`,
	ADD UNIQUE INDEX `year_number` (`year`, `sn`);
-- 更新 ow_member_out_view

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('use_code_as_identify', '用学工号作为编号', 'true', 3, 96, '转出中的编号生成方式：1学工号作为编号；0通过年份和一个四位数字生成编号');

ALTER TABLE `ow_party`
	ADD COLUMN `branch_type` INT(10) UNSIGNED NULL COMMENT '直属党支部所属支部类型 关联元数据' AFTER `unit_type_id`;
-- 更新 ow_party_view

20210106
珠海校区

20210105

delete from cet_train_obj where train_course_id not in(select id from cet_train_course);
ALTER TABLE `cet_train_obj`
    ADD CONSTRAINT `FK_cet_train_obj_cet_train_course` FOREIGN KEY (`train_course_id`) REFERENCES `cet_train_course` (`id`) ON DELETE CASCADE;


20210101
哈工大


20201230
大工

ALTER TABLE `ow_branch`
	CHANGE COLUMN `short_name` `short_name` VARCHAR(100) NULL COMMENT '简称' COLLATE 'utf8_general_ci' AFTER `name`;
ALTER TABLE `ow_party`
	CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL COMMENT '名称' COLLATE 'utf8_general_ci' AFTER `code`,
	CHANGE COLUMN `short_name` `short_name` VARCHAR(100) NOT NULL COMMENT '简称' COLLATE 'utf8_general_ci' AFTER `name`;
update ow_party set short_name=name where short_name is null or short_name='';


update base_meta_type set name='党总支类别' where code='mc_party_class';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`,
                            `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`,
                            `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (2566, 0, '二级党委数据统计', '组织部,分党委', 'url', '', '/stat/partySum?cls=1', 260, '0/1/260/', 1, 'stat:partySum', NULL, NULL, NULL, 1, 60);


20201229
浙大

ALTER TABLE `sys_approval_log`
   CHANGE COLUMN `stage` `stage` VARCHAR(500) NULL DEFAULT NULL COMMENT '阶段备注，比如 初审、返回等' AFTER `type`;

20201228
南航

20201227

/*ALTER TABLE `sys_user`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别，1教职工 2本科生 3硕士研究生 4博士研究生' AFTER `code`;*/
-- 更新 ow_member_view


INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('ignore_plan_and_draw', '党员发展流程节点控制', 'false', 3, 83, '忽略党员发展流程中的“列入发展计划”和“领取志愿书”');

INSERT INTO `sys_resource` (id, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
                            VALUES (170, 0, '干部信息统计', '', 'function', '', NULL, 108,
                                    '0/1/108/', 1, 'stat:cadre', NULL, NULL, NULL, 1, NULL);


20201220
哈工大

-- 如果领取志愿书不需要组织部审核bug，更新语句
-- update ow_member_apply set grow_status=2 where stage=5 and grow_status is null;

ALTER TABLE `ow_member`
	ADD COLUMN `remark4` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注4' AFTER `remark3`,
	ADD COLUMN `remark5` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注5' AFTER `remark4`,
	ADD COLUMN `remark6` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注6' AFTER `remark5`;
-- 更新 ow_member_view


20201215

-- 更新utils

-- (以下已更新北化工）
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('wx.appSecret', '微信应用secret', '4rUjh4NgyYtH_vPJiWjMrLNq7QurHLiY9B9hI_az688', 1, 82, '');
UPDATE sys_property SET type=1 WHERE  code='wx.agentId';

20201213
哈工大

INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('组织生活报送提醒', NULL, 1, 'pm_3_notice_branch', NULL, NULL, NULL, NULL, '各位书记/党支部管理员：您好！您管理的党支部%s组织生活月报未报送，请尽快上传报送。[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 69, 100719, '2020-12-10 17:42:57', '2020-12-10 17:56:57', 0, '');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('组织生活报送提醒', NULL, 1, 'pm_3_notice_party', NULL, NULL, NULL, NULL, '各位书记/分党委管理员：您好！您管理的党支部%s组织生活月报未报送或者未审批的情况，请审批月报或者提醒党支部尽快上传月报。[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 68, 100719, '2020-12-10 15:41:35', '2020-12-10 17:57:08', 0, '');

-- 更新导入表

20201208

北航

20201207

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2563, 0, '党支部信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:branch', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2564, 0, '党支部党建待办事项', '', 'function', '', NULL, 1042, '0/1/108/1042/', 1, 'suspend:branch', NULL, NULL, NULL, 1, NULL);
UPDATE `sys_resource` SET `name`='系统提醒', `url`='/sys/sysMsg?cls=2' WHERE  `id`=3031;
UPDATE `sys_resource` SET `name`='系统提醒', `url`='/sys/sysMsg?cls=1' WHERE  `id`=3032;
ALTER TABLE `sys_msg`
	ADD COLUMN `confirm_time` DATETIME NULL DEFAULT NULL COMMENT '通知确认时间' AFTER `create_time`;
ALTER TABLE `sys_msg`
	CHANGE COLUMN `status` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态 1.未确认 2.已确认' AFTER `ip`;
ALTER TABLE `sys_msg`
	ALTER `create_time` DROP DEFAULT;
ALTER TABLE `sys_msg`
	CHANGE COLUMN `create_time` `send_time` DATETIME NOT NULL COMMENT '通知发送时间' AFTER `content`;


20201130

大工，西工大

-- 标准化职级，需修改 SyncService  ExtCommonService.formatProPostLevel(proPostLevel)
update sys_teacher_info set pro_post_level=null where pro_post_level not REGEXP '副高|正高|(中|初)级';
update sys_teacher_info set pro_post_level = '正高' where pro_post_level like '%正高%';
update sys_teacher_info set pro_post_level = '副高' where pro_post_level like '%副高%';
update sys_teacher_info set pro_post_level = '中级' where pro_post_level like '%中级%';
update sys_teacher_info set pro_post_level = '初级' where pro_post_level like '%初级%';

drop table ow_apply_open_time;
-- 删除ApplyOpenTime类

drop table ow_enter_apply;
-- 删除EnterApply类

20201126
南航

ALTER TABLE `oa_task_file`
	CHANGE COLUMN `file_name` `file_name` VARCHAR(100) NOT NULL COLLATE 'utf8_general_ci' AFTER `task_id`;

-- 更新utils

20201124
哈工大

20201123
西工大

-- 更新  cet_train_obj_view    cet_trainee_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
`is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (973, 0, '培训记录查询', '', 'url', '', '/cet/cetTraineeList', 869, '0/1/384/869/', 1, 'cetProject:traineeList', NULL, NULL, NULL, 1, 350);


20201123

北航


-- 更新 unit_post_view

20201115
大工

ALTER TABLE `ow_member`
	ADD COLUMN `remark1` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注1' AFTER `sort_order`,
	ADD COLUMN `remark2` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注2' AFTER `remark1`,
	ADD COLUMN `remark3` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注3' AFTER `remark2`;
ALTER TABLE `ow_member_apply`
	ADD COLUMN `draw_acceptor` VARCHAR(30) NULL DEFAULT NULL COMMENT '入党志愿书接收人' AFTER `apply_sn`,
	ADD COLUMN `join_apply_time` DATE NULL DEFAULT NULL COMMENT '入党申请时间' AFTER `apply_time`;
ALTER TABLE `ow_member_out`
	ADD COLUMN `accept_receipt_time` DATE NULL DEFAULT NULL COMMENT '回执接收时间' AFTER `has_receipt`;
-- 更新ow_member_view、ow_member_apply_view、ow_member_out_view


20201113

ALTER TABLE `sc_letter`
	DROP INDEX `year_type_num`;

20201112
西工大，南航
ALTER TABLE `cet_trainee_type`
	DROP INDEX `code`;

ALTER TABLE `cet_trainee_type`
	DROP COLUMN `template_id`;

ALTER TABLE `sys_role`
	ADD COLUMN `resource_ids_minus` TEXT NULL COMMENT '角色减资源，网页端' AFTER `m_resource_ids`,
	ADD COLUMN `m_resource_ids_minus` TEXT NULL COMMENT '角色减资源，移动端' AFTER `resource_ids_minus`;

update sys_role set resource_ids_minus=resource_ids, m_resource_ids_minus=m_resource_ids where type=2;
update sys_role set resource_ids='-1',m_resource_ids='-1' where type=2;

INSERT INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('cadre_leave', '离任干部', NULL, '-1', '-1', '-1', '-1', NULL, 0, 0, 68, '');

ALTER TABLE `sys_role`
	DROP COLUMN `type`;


-- 执行 /test/sys_role.jsp

20201110

西工大

20201106

南航

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (985, 0, '导出兼职确认表', '', 'function', '', NULL, 353, '0/1/353/', 1, 'export:cadreCompanyConfirm', NULL, NULL, NULL, 1, NULL);


-- 党内奖惩、任职、考核， 已更新北邮
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2500, 0, '党内奖惩信息', '', 'menu', '', NULL, 260, '0/1/260/', 0, 'RePu:function', 1, NULL, NULL, 1, 99);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2501, 0, '查看党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:list', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2502, 0, '编辑党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:edit', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2503, 0, '查看党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:list', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2504, 0, '编辑党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:edit', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2505, 0, '党内任职信息', '', 'url', '', '/party/partyPostList_page', 260, '0/1/260/', 0, 'partyPost:menu', 1, NULL, NULL, 1, 90);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2506, 0, '编辑党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:edit', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2507, 0, '查看党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:list', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2508, 0, '分党委奖惩', '', 'url', '', '/party/partyRePu_page?type=1', 2500, '0/1/260/2500/', 1, 'partyRePu:menu', 2, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2509, 0, '党支部奖惩', '', 'url', '', '/party/partyRePu_page?type=2', 2500, '0/1/260/2500/', 1, 'branchRePu:menu', 1, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2510, 0, '个人奖惩', '', 'url', '', '/party/partyRePu_page?type=3', 2500, '0/1/260/2500/', 1, 'memberRePu:menu', 2, NULL, NULL, 1, NULL);
replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2520, 0, '党员信息采集表权限', '', 'function', '', NULL, 181, '0/1/260/181/', 1, 'memberInfoForm:*', 3, NULL, NULL, 1, NULL);


drop table if EXISTS ow_party_eva;
CREATE TABLE `ow_party_eva` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '干部',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年份',
	`type` INT(10) UNSIGNED NOT NULL COMMENT '考核情况，关联元数据',
	`remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='年度考核记录，支部书记'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;

drop table if EXISTS ow_party_post;
CREATE TABLE `ow_party_post` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党员',
	`start_date` DATE NULL COMMENT '开始日期',
	`end_date` DATE NULL COMMENT '结束日期',
	`detail` VARCHAR(100) NULL COMMENT '工作单位及担任职务' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(300) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='党内任职经历'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=2
;

drop table if EXISTS ow_party_punish;
CREATE TABLE `ow_party_punish` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 分党委 2 党支部 3 个人',
	`party_id` INT(10) UNSIGNED NULL,
	`branch_id` INT(10) UNSIGNED NULL,
	`user_id` INT(10) UNSIGNED NULL,
	`punish_time` DATE NULL COMMENT '处分日期',
	`end_time` DATE NULL COMMENT '处分期限',
	`name` VARCHAR(200) NULL COMMENT '受何种处分' COLLATE 'utf8_general_ci',
	`unit` VARCHAR(300) NULL COMMENT '处分单位' COLLATE 'utf8_general_ci',
	`sort_order` INT(10) UNSIGNED NULL COMMENT '排序',
	`remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='党内惩罚信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;

drop table if EXISTS ow_party_report;
CREATE TABLE `ow_party_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`year` INT(10) UNSIGNED NULL COMMENT '年度',
	`party_id` INT(10) UNSIGNED NULL COMMENT '所属分党委',
	`party_name` VARCHAR(50) NULL COMMENT '所属分党委名称' COLLATE 'utf8_general_ci',
	`branch_id` INT(10) UNSIGNED NULL COMMENT '所属党支部',
	`branch_name` VARCHAR(50) NULL COMMENT '所属党支部名称' COLLATE 'utf8_general_ci',
	`report_file` VARCHAR(200) NULL COMMENT '工作总结word' COLLATE 'utf8_general_ci',
	`eva_result` TINYINT(3) UNSIGNED NULL COMMENT '考核结果',
	`eva_file` VARCHAR(200) NULL COMMENT '考核结果文件pdf' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	`status` TINYINT(3) UNSIGNED NULL COMMENT '状态  1未报送  2 已报送',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='党支部考核'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=168
;

drop table if EXISTS ow_party_reward;
CREATE TABLE `ow_party_reward` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 分党委 2 党支部 3 个人',
	`party_id` INT(10) UNSIGNED NULL,
	`branch_id` INT(10) UNSIGNED NULL,
	`user_id` INT(10) UNSIGNED NULL,
	`reward_time` DATE NULL COMMENT '获奖日期',
	`reward_level` INT(10) UNSIGNED NULL COMMENT '奖励级别',
	`reward_type` INT(10) UNSIGNED NULL COMMENT '获奖类型',
	`name` VARCHAR(200) NULL COMMENT '获得奖项' COLLATE 'utf8_general_ci',
	`unit` VARCHAR(300) NULL COMMENT '颁奖单位' COLLATE 'utf8_general_ci',
	`proof` VARCHAR(255) NULL COMMENT '获奖证书' COLLATE 'utf8_general_ci',
	`proof_filename` VARCHAR(255) NULL COMMENT '获奖证书文件名' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	`sort_order` INT(10) UNSIGNED NULL COMMENT '排序',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='党内奖励信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;


DROP VIEW IF EXISTS `ow_party_post_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_post_view` AS
SELECT opp.*,om.party_id,om.branch_id,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order
from ow_party_post opp
left join ow_member om ON opp.user_id=om.user_id
left join ow_party op ON op.id=om.party_id
left join ow_branch ob ON ob.id=om.branch_id;

DROP VIEW IF EXISTS `ow_party_punish_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_punish_view` AS
SELECT opp.*,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order,ob.party_id as branch_party_id,om.party_id as user_party_id,om.branch_id as user_branch_id
from ow_party_punish opp
left join ow_party op ON opp.party_id=op.id
left join ow_branch ob ON opp.branch_id=ob.id
left join ow_member om on opp.user_id=om.user_id;

DROP VIEW IF EXISTS `ow_party_reward_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_reward_view` AS
SELECT opr.*,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order,ob.party_id as branch_party_id,om.party_id as user_party_id,om.branch_id as user_branch_id
from ow_party_reward opr
left join ow_party op ON opr.party_id=op.id
left join ow_branch ob ON opr.branch_id=ob.id
left join ow_member om on opr.user_id=om.user_id;


INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2596,  '分党委获奖类型', '组织机构管理', '基层党组织', 'mt_party_reward', '', '', '', 2601);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2597, '党支部获奖类型', '组织机构管理', '党支部管理', 'mt_branch_reward', '', '', '', 2602);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '校级先进党组织', 'mt_qmcq98', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '省部级先进党组织', 'mt_uttewz', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '国家级先进党组织', 'mt_klwpii', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '校级先进党支部', 'mt_ie6tsu', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '省部级先进党支部', 'mt_idzxhq', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '国家级先进党支部', 'mt_cko3mg', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '校级红色“1+1”', 'mt_37xxui', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '省部级红色“1+1”', 'mt_krnrhg', NULL, '', '', 5, 1);

drop table if EXISTS ow_member_report;
CREATE TABLE `ow_member_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`year` INT(10) UNSIGNED NULL COMMENT '年度',
	`user_id` INT(10) UNSIGNED NULL COMMENT '用户id',
	`party_id` INT(10) UNSIGNED NULL COMMENT '所属分党委',
	`party_name` VARCHAR(50) NULL COMMENT '所属分党委名称' COLLATE 'utf8_general_ci',
	`branch_id` INT(10) UNSIGNED NULL COMMENT '所属党支部',
	`branch_name` VARCHAR(50) NULL COMMENT '所属党支部名称' COLLATE 'utf8_general_ci',
	`report_file` VARCHAR(200) NULL COMMENT '述职报告word' COLLATE 'utf8_general_ci',
	`eva_result` TINYINT(3) UNSIGNED NULL COMMENT '考核结果',
	`eva_file` VARCHAR(200) NULL COMMENT '考核结果文件pdf' COLLATE 'utf8_general_ci',
	`remark` VARCHAR(200) NULL COMMENT '备注' COLLATE 'utf8_general_ci',
	`status` TINYINT(3) UNSIGNED NULL COMMENT '状态  1未报送  2 已报送',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='党组织书记考核'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=370
;

replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2712, 0, '党支部考核', '', 'url', '', '/member/memberReport', 260, '0/1/260/', 0, 'owReport:menu', NULL, NULL, NULL, 1, 80);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2713, 0, '党组织书记考核:查看', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2714, 0, '党组织书记考核:编辑', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2715, 0, '党支部考核:查看', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2716, 0, '党支部考核:编辑', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:edit', NULL, NULL, NULL, 1, NULL);
-- 党内奖惩， 已更新北邮

20201104
大工

20201103

ALTER TABLE `ow_member_certify`
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL AFTER `certify_date`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL AFTER `party_id`,
	ADD COLUMN `status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1分党委审批 2党支部审批 ' AFTER `branch_id`,
	ADD COLUMN `is_back` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的' AFTER `status`,
	ADD COLUMN `reason` VARCHAR(100) NULL DEFAULT NULL COMMENT '返回修改原因' AFTER `is_back`,
	ADD COLUMN `apply_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间' AFTER `reason`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (2541, 0, '临时组织关系介绍信', '党员个人权限', 'url', '', '/member/memberCertify?cls=0', 258, '0/1/258/', 1, 'userMemberCertify:*', NULL, NULL, NULL, 1, 400);

-- 培训bug
-- update cet_train_course ctc, cet_train ct, cet_project_plan cpp set ctc.project_id=cpp.project_id where ctc.train_id=ct.id and ct.plan_id=cpp.id;

20201103
北航，南航

20201102
南航
-- 更新utils
20201102
北邮

-- 执行 /test/oa_task.jsp

20201029

南航，北航，西工大，北邮

-- 更新jx.ext

20201028
哈工大

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (990, 0, '同步头像接口', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'avatar:sync', 2, NULL, NULL, 1, NULL);
-- 更新 Utils

20201025
西工大，，北邮

-- 执行 /test/member_add_role.jsp
--     /test/member_del_role.jsp
-- select count(*) from sys_user u, ow_member m where m.user_id=u.id and m.`status`=1 and u.role_ids like '%,8,%';
-- select count(*) from sys_user u, ow_member m where m.user_id=u.id and m.`status`!=1 and u.role_ids like '%,8,%';
-- select count(*) from sys_user u, ow_member m where m.user_id=u.id and m.`status`=1 and u.role_ids not like '%,8,%';
-- select count(*) from sys_user u, ow_member m where m.user_id=u.id and m.`status`=1 and u.role_ids not like '%,8,%';
-- 更新 Utils

20201025
北航

ALTER TABLE `ow_member_apply`
	ADD COLUMN `contact_user_ids` VARCHAR(50) NULL COMMENT '培养联系人，如果为空则是校外，否则校内，逗号分割' AFTER `active_grade`,
	ADD COLUMN `contact_users` VARCHAR(50) NULL COMMENT '培养联系人姓名，逗号分割' AFTER `contact_user_ids`,
	ADD COLUMN `sponsor_user_ids` VARCHAR(50) NULL COMMENT '入党介绍人，如果为空则是校外，否则校内，逗号分割' AFTER `candidate_status`,
	ADD COLUMN `sponsor_users` VARCHAR(50) NULL COMMENT '入党介绍人姓名，逗号分割' AFTER `sponsor_user_ids`,
	ADD COLUMN `grow_contact_user_ids` VARCHAR(50) NULL COMMENT '培养联系人，预备党员阶段，如果为空则是校外，否则校内，逗号分割' AFTER `grow_status`,
	ADD COLUMN `grow_contact_users` VARCHAR(50) NULL COMMENT '培养联系人姓名，预备党员阶段，逗号分割' AFTER `grow_contact_user_ids`;
-- 更新 ow_member_apply_view

ALTER TABLE `cadre`
	ADD COLUMN `is_outside` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否校外任职' AFTER `title`;

-- 更新cadre_view

REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('contactUsers_count', '培养联系人数量', '0', 2, 79, '入党积极分子阶段');
REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('sponsorUsers_count', '入党介绍人数量', '0', 2, 80, '');
REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('growContactUsers_count', '培养联系人数量', '0', 2, 81, '预备党员阶段');


20201021
西工大

-- 更新 Utils
-- 加入 GraphicsMagick
-- gm.command=/opt/GraphicsMagick-1.3.35/bin
-- im4java.jar

ALTER TABLE `pmd_config_member_type`
	ADD UNIQUE INDEX `type_name` (`type`, `name`);


INSERT INTO `sys_role` (`id`, `code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`)
VALUES (75, 'dp_member', '民主党派成员', 1, '-1', '-1', NULL, 0, 0, 65, '');
UPDATE `sys_role` SET `name`='民主党派干部成员' WHERE  `id`=70;
UPDATE sys_user set role_ids=REPLACE(role_ids, ',69,',',75,') WHERE role_ids LIKE '%,69,%';


20201019

北邮

-- 党费收缴
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (2554, 0, '支部添加', '', 'function', '', NULL, 570, '0/1/564/570/', 1, 'pmdBranch:edit', NULL, NULL, NULL, 1, NULL);

20201019
吉大

ALTER TABLE `pcs_candidate`
	ADD INDEX `config_id` (`config_id`);


	ALTER TABLE `pcs_recommend`
	ADD COLUMN `has_report` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '分党委是否已报送' AFTER `is_finished`;

	update pcs_recommend pr, pcs_admin_report par set pr.has_report=1
	where par.config_id=pr.config_id and par.party_id=pr.party_id and par.stage=pr.stage;


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
`parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (1300, 0, '显示所有分党委和支部', '给组织部管理员', 'function', '', NULL, 570, '0/1/564/570/', 1, 'pmd:viewAll', NULL, NULL, NULL, 1, NULL);

update sys_resource set permission='pmdMonth:list' where permission='pmdOw:admin';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
`parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (1301, 0, '组织部管理员操作权限', '给组织部管理员', 'function', '', NULL, 570, '0/1/564/570/', 1, 'pmdOw:admin', NULL, NULL, NULL, 1, NULL);

update sys_resource set permission='pmdMember:helpSetSalary' where permission='userPmdMember:helpSetSalary';

-- 修改党费组织部管理员权限


20201015
哈工大

delete from base_meta_class where code='mc_branch_staff_type';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2553, 0, '分党委党建代办事项', '', 'function', '', NULL, 1042, '0/1/108/1042/', 1, 'suspend:party', NULL, NULL, NULL, 1, NULL);

20201014
西工大
-- 更新 utils

20201014

ALTER TABLE `ow_party_member_group`
	DROP FOREIGN KEY `FK_ow_party_member_group_ow_party_member_group`;
ALTER TABLE `ow_party_member_group`
	ADD CONSTRAINT `FK_ow_party_member_group_ow_party_member_group` FOREIGN KEY (`fid`) REFERENCES `ow_party_member_group` (`id`) ON DELETE SET NULL;


20201013

北航

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (2552, 0, '支部委员会届满列表', '', 'url', '', 'unitTeam?list=3', 867, '0/1/867/', 1, 'unitTeam:list3', NULL, NULL, NULL, 1, 95);

-- 去重
delete ppr.* from pcs_poll_result ppr,  (select max(id) maxid, inspector_id,user_id,type, count(*) num from pcs_poll_result group by inspector_id,user_id, type having count(*)>1)tmp
where ppr.inspector_id=tmp.inspector_id and ppr.user_id=tmp.user_id and ppr.type = tmp.type and ppr.id != tmp.maxid;
-- 加唯一索引
ALTER TABLE `pcs_poll_result`
	ADD UNIQUE INDEX `inspector_id_type_user_id` (`inspector_id`, `type`, `user_id`);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (76, 0, '隐藏科级干部', '', 'function', '', NULL, 88, '0/1/88/', 1, 'hide:kj', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (77, 0, '隐藏离任干部', '', 'function', '', NULL, 88, '0/1/88/', 1, 'hide:cadreLeave', NULL, NULL, NULL, 1, NULL);


20201010
南航

drop view IF EXISTS pcs_party_view;

-- 删除 service.pmd.PmdExtService

ALTER TABLE `pmd_config_member`
	ADD COLUMN `salary` TEXT NULL DEFAULT NULL COMMENT '工资项，JSON格式' AFTER `retire_salary`;

-- 执行 /test/pmd_update.jsp

ALTER TABLE `pmd_config_member`
	DROP COLUMN `gwgz`,
	DROP COLUMN `xjgz`,
	DROP COLUMN `gwjt`,
	DROP COLUMN `zwbt`,
	DROP COLUMN `zwbt1`,
	DROP COLUMN `shbt`,
	DROP COLUMN `sbf`,
	DROP COLUMN `xlf`,
	DROP COLUMN `gzcx`,
	DROP COLUMN `shiyebx`,
	DROP COLUMN `yanglaobx`,
	DROP COLUMN `yiliaobx`,
	DROP COLUMN `gsbx`,
	DROP COLUMN `shengyubx`,
	DROP COLUMN `qynj`,
	DROP COLUMN `zynj`,
	DROP COLUMN `gjj`;

-- 更新 utils

20201010
南航（停止同步更新）

update  sys_resource set name='批量生成、导出账号' where permission = 'memberReg:import';

ALTER TABLE `pmd_branch`
	CHANGE COLUMN `sort_order` `party_sort_order` INT(10) UNSIGNED NULL COMMENT '党委的顺序' AFTER `branch_name`,
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL COMMENT '支部的顺序' AFTER `party_sort_order`;
-- 更新 pmd_branch_view

20201008
西工大，北航，北化工（停止同步更新）

ALTER TABLE `sys_teacher_info`
	CHANGE COLUMN `pro_post_level` `pro_post_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '专业技术职务级别（职称级别）' AFTER `pro_post_time`,
	CHANGE COLUMN `post_level` `post_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '任职级别（职员级别）' AFTER `post`;

ALTER TABLE `ow_member`
	ADD COLUMN `sort_order` FLOAT UNSIGNED NULL DEFAULT NULL COMMENT '排序，用于下拉选' AFTER `integrity`;
-- 更新 ow_member_view

update  sys_resource set url='/m/cadreList?type=11' where permission='m:cadreList:leader';
update  sys_resource set url='/m/cadreList?type=2' where permission='m:cadreList';
update  sys_resource set url='/m/cadreList?type=1' where permission='m:cadreList:leader';


ALTER TABLE `abroad_approval_log`
	DROP FOREIGN KEY `FK_abroad_apply_approval_abroad_approver_type`;
ALTER TABLE `abroad_approval_order`
	DROP INDEX `FK_abroad_approval_order_abroad_approval_identity`,
	DROP INDEX `FK_abroad_approval_order_abroad_approver_type`,
	DROP FOREIGN KEY `FK_abroad_approval_order_abroad_approver_type`,
	DROP FOREIGN KEY `FK_abroad_approval_order_abroad_approval_identity`;
ALTER TABLE `abroad_approver`
	DROP INDEX `type_id`,
	DROP FOREIGN KEY `abroad_approver_ibfk_2`;
ALTER TABLE `abroad_applicat_cadre`
	DROP INDEX `FK_abroad_applicat_user_abroad_applicat_type`,
	DROP FOREIGN KEY `FK_abroad_applicat_user_abroad_applicat_type`;
	


20201003
大工（停止同步更新）

-- 更新utils、导入样表

REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('sync', '同步字段是否只同步一次', '0110111101101100', 1, 51, '0或空代表每次都同步覆盖，1代表仅同步一次，后面不覆盖，字段顺序：姓名、性别、出生年月、身份证号码、民族、籍贯、出生地、户籍地、职称、手机号、邮箱、办公电话、家庭电话、头像、专业技术职务级别、所在单位');


20201002
大工，北邮，戏曲，南航


20200929


update sys_resource set permission='pmdOw:admin' where permission='pmdOw:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (781, 0, '关闭订单', '', 'function', '', NULL, 570, '0/1/564/570/', 1, 'pmdOw:closeTrade', NULL, NULL, NULL, 1, NULL);


ALTER TABLE `pcs_admin`
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL COMMENT '所属党支部' AFTER `party_id`,
	ADD COLUMN `category` TINYINT(3) UNSIGNED NULL COMMENT '类别， 1 分党委管理员 2 党支部管理员' AFTER `branch_id`;

ALTER TABLE `pcs_admin`
	DROP INDEX `config_id_user_id`,
	DROP FOREIGN KEY `FK_pcs_admin_pcs_config`;

update pcs_admin set category=1 where category is null;

INSERT INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`,
                        `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_pcs_branch', '党代会-支部管理员', 1, '469,476,470,477,479,491,492', '-1', NULL, 0, 0, 64, '');


ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `education` `education` VARCHAR(50) NULL DEFAULT NULL COMMENT '学历' AFTER `pro_post`,
	ADD COLUMN `degree` VARCHAR(50) NULL DEFAULT NULL COMMENT '学位' AFTER `education`;

-- 更新 utils

20200927
西工大，北航

--  执行 /test/branch_member.jsp

20200924
西工大

update ow_party_member_group set is_deleted=1  where is_deleted=0 and is_present=0
and party_id in(select * from (select party_id from ow_party_member_group where is_deleted=0 group by party_id having count(*)>1)tmp);

update ow_branch_member_group set is_deleted=1  where is_deleted=0 and is_present=0
and branch_id in(select * from (select branch_id from ow_branch_member_group where is_deleted=0 group by branch_id having count(*)>1)tmp);

-- 校验数据
-- select party_id from ow_party_member_group where is_deleted=0 group by party_id having count(*)>1;
-- select branch_id from ow_branch_member_group where is_deleted=0 group by branch_id having count(*)>1;

ALTER TABLE `ow_party_member_group`
	CHANGE COLUMN `is_deleted` `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否撤销' AFTER `sort_order`,
	DROP COLUMN `is_present`;

ALTER TABLE `ow_branch_member_group`
	CHANGE COLUMN `is_deleted` `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否撤销' AFTER `sort_order`,
	DROP COLUMN `is_present`;

-- 执行 /test/party_admin.jsp

-- 更新导入样表

ALTER TABLE `ow_branch_member`
	CHANGE COLUMN `type_id` `types` VARCHAR(50) NOT NULL COMMENT '类别，关联元数据（书记、副书记、各类委员）' AFTER `user_id`,
	DROP INDEX `FK_ow_branch_member_base_meta_type`,
	DROP FOREIGN KEY `FK_ow_branch_member_base_meta_type`;

-- 更新 ow_party_member_group_view  ow_party_member_view
-- ow_branch_member_group_view   ow_branch_member_view
-- ow_party_view  ow_branch_view
-- ext_branch_view  ext_branch_view2


-- 删除 pcs.cm.PcsPartyController
-- 删除 service.base.OneSendService

20200921

ALTER TABLE `pcs_party`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `present_group_count`;
ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `edu_id` `edu_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学历，弃用' AFTER `user_type`,
	CHANGE COLUMN `work_time` `work_time` DATE NULL DEFAULT NULL COMMENT '参加工作时间，弃用' AFTER `grow_time`,
	CHANGE COLUMN `education` `education` VARCHAR(50) NULL DEFAULT NULL COMMENT '学历学位' AFTER `pro_post`;
update pcs_pr_candidate ppc, base_meta_type bmt set education=bmt.name where ppc.edu_id=bmt.id and ppc.education is null;

ALTER TABLE `pcs_candidate`
	CHANGE COLUMN `title` `title` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位及职务，如果为空则读取人事单位' AFTER `birth`,
	CHANGE COLUMN `ext_unit` `ext_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位，人事信息，弃用' AFTER `native_place`;

update pcs_candidate set title = ext_unit where title is null;


20200920
西工大

20200918
北邮

-- ln -s /opt/apache-tomcat-xxx/logs  /opt/logs

-- 检查历史数据：select * from cadre_edu where `is_high_edu` = 1 and is_second_degree=0 and status=0 group by cadre_id having count(*)>1;
update cadre_edu set is_high_edu=0 where id in(select id from (select id from cadre_edu where `is_high_edu` = 1 and is_second_degree=0 and status=0 group by cadre_id having count(*)>1)tmp);
/*
update pcs_recommend pr , pcs_branch pb
set pr.member_count=(if(pr.expect_member_count> pr.member_count, pr.expect_member_count, pr.member_count)), pr.positive_count=pb.positive_count where pr.branch_id=pb.branch_id;


update pcs_admin_report par,
(select config_id, stage, party_id, count(id) as branch_count, sum(member_count) as member_count, sum(positive_count) as positive_count from pcs_recommend group by config_id, stage,party_id) pr
set par.branch_count= pr.branch_count, par.member_count=pr.member_count, par.positive_count=pr.positive_count
where pr.config_id=par.config_id and pr.stage=par.stage and par.party_id=pr.party_id;


update pcs_pr_recommend par,
(select config_id, stage, party_id, count(id) as branch_count, sum(member_count) as member_count, sum(positive_count) as positive_count from pcs_recommend group by config_id, stage,party_id) pr
set par.branch_count= pr.branch_count, par.member_count=pr.member_count, par.positive_count=pr.positive_count
where pr.config_id=par.config_id and pr.stage=par.stage and par.party_id=pr.party_id;

*/

20200917
西工大

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_member_need_vote', '党代会党员是否需要投票', 'false', 3, 75, '');
ALTER TABLE `pcs_poll_report`
	ALTER `disagree_ballot` DROP DEFAULT,
	ALTER `abstain_ballot` DROP DEFAULT;
ALTER TABLE `pcs_poll_report`
	CHANGE COLUMN `disagree_ballot` `disagree_ballot` INT(10) UNSIGNED NULL COMMENT '不支持人数' AFTER `grow_ballot`,
	CHANGE COLUMN `abstain_ballot` `abstain_ballot` INT(10) UNSIGNED NULL COMMENT '弃权票' AFTER `disagree_ballot`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (2792, 0, '三会一课2:退回', '', 'function', '', NULL, 2783, '0/1/2783/', 1, 'pmMeeting2:back', NULL, NULL, NULL, 1, NULL);

20200916
西工大，北邮


insert INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('payTest', '支付测试', 'false', 3, 78, '党费收缴支付测试');
-- 更新 jx.ext.jar
-- 更新 utils


20200914
南航，戏曲

-- 更新 utils

20200912
北邮

-- 更新导入样表

ALTER TABLE `pcs_recommend`
	ADD COLUMN `member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员数量，二级党委报送时归档用' AFTER `stage`,
	ADD COLUMN `positive_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '正式党员数量' AFTER `member_count`,
	ADD COLUMN `student_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生党员数量' AFTER `positive_count`,
	ADD COLUMN `teacher_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '教师党员数量' AFTER `student_member_count`,
	ADD COLUMN `retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量' AFTER `teacher_member_count`;

ALTER TABLE `pcs_admin_report`
	COMMENT='两委委员分党委管理员上报',
	ADD COLUMN `branch_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部数量，分党委报送时归档' AFTER `create_time`,
	ADD COLUMN `member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员数量' AFTER `branch_count`,
	ADD COLUMN `positive_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '正式党员数量' AFTER `member_count`,
	ADD COLUMN `student_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生党员数量' AFTER `positive_count`,
	ADD COLUMN `teacher_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '教师党员数量' AFTER `student_member_count`,
	ADD COLUMN `retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量' AFTER `teacher_member_count`;

ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `branch_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部数量，分党委报送时归档' AFTER `report_time`,
	ADD COLUMN `member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员数量' AFTER `branch_count`,
	ADD COLUMN `positive_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '正式党员数量' AFTER `member_count`,
	ADD COLUMN `student_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生党员数量' AFTER `positive_count`,
	ADD COLUMN `teacher_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '教师党员数量' AFTER `student_member_count`,
	ADD COLUMN `retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量' AFTER `teacher_member_count`;

-- 更新 ow_member_view
-- 更新 utils

-- 西工大数据脱敏
REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('privateDataMask', '数据脱敏', 'true', 3, 75, '隐私数据是否脱敏处理');


INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('pcsPrWhiteList', '党代表白名单', ' ', '1', '77', '所有分党委均可选择的人的工号列表');

20200909
西工大， 戏曲

ALTER TABLE `cet_plan_course_obj`
	ADD COLUMN `period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '完成学时数，针对上级网上专题班, 自主学习' AFTER `is_finished`;

update cet_plan_course_obj o, (select plan_course_obj_id, sum(period) period from cet_plan_course_obj_result group by plan_course_obj_id) r
set o.period=r.period where o.id=r.plan_course_obj_id;

ALTER TABLE `pm_meeting2`
	CHANGE COLUMN `file_path` `file_path` TEXT NULL DEFAULT NULL COMMENT '附件地址' AFTER `file_name`;
ALTER TABLE `pm_meeting2`
	CHANGE COLUMN `file_name` `file_name` TEXT NULL DEFAULT NULL COMMENT '附件名称' AFTER `recorder`;




20200907
南航

20200906

-- 更新 utils
-- jx.bnu.jar -> jx.ext.jar

delete from pmd_member_pay where member_id not in (select id from pmd_member) and has_pay!=1;

20200904
南航

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('show_msg_btns', '系统短信通知按钮', 'true', 3, 74, '是否显示短信通知按钮');

-- 更新SyncService sysUserService.dbFindByUsernameOrCode

20200903
北邮，戏曲

-- 更新cadre_view

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`, `is_deleted`)
VALUES ('更新领导干部的培训信息', '更新培训模块的培训信息至领导干部的培训信息', 'job.cadre.UpdateCadreTrain', '0 0 1 * * ?', 0, 0, 32, '2020-09-01 16:13:32', 0);
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`, `is_deleted`)
VALUES ('更新离退休党员的人员状态', '离退休分党委和党支部中，把党员的人员状态为“在职”的更新为“退休”', 'job.member.UpdateRetireStatus', '0 0 1 * * ?', 0, 0, 31, '2020-08-31 16:36:23', 0);

-- 删除西工大培训中的重复数据
/*DELETE FROM cet_upper_train WHERE id IN (SELECT tmp.id FROM (SELECT MIN(c.id) AS id,c.user_id,c.train_name, COUNT(*) AS count FROM cet_upper_train c
WHERE TYPE!=10
GROUP BY c.user_id,c.start_date,c.end_date,c.train_name
HAVING COUNT>1)tmp);*/
-- 删除西工大培训中的重复数据
-- 更新导入样表

20200902
吉大

ALTER TABLE `pcs_admin`
	COMMENT='党代会分党委管理员补充信息',
	ADD COLUMN `config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会' AFTER `id`,
	ADD COLUMN `mobile` INT(10) UNSIGNED NOT NULL COMMENT '手机号码' AFTER `user_id`,
	DROP COLUMN `type`;

update pcs_admin set config_id = (select id from pcs_config where is_current=1);
ALTER TABLE `pcs_admin`
	ADD CONSTRAINT `FK_pcs_admin_pcs_config` FOREIGN KEY (`config_id`) REFERENCES `pcs_config` (`id`) ON DELETE CASCADE;
ALTER TABLE `pcs_admin`
	ADD COLUMN `unit` VARCHAR(200) NULL COMMENT '所在单位' AFTER `user_id`;
ALTER TABLE `pcs_admin`
	CHANGE COLUMN `mobile` `mobile` VARCHAR(50) NULL COMMENT '手机号码' AFTER `unit`;
update pcs_admin pa left join sys_user_view uv on pa.user_id=uv.user_id set pa.mobile=uv.mobile, pa.unit=uv.unit ;
-- 删除角色 role_pcs_party 相关权限给分党委管理员
ALTER TABLE `pcs_admin`
	DROP INDEX `user_id`;
ALTER TABLE `pcs_admin`
	ADD UNIQUE INDEX `config_id_user_id` (`config_id`, `user_id`);


20200902
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
VALUES (984, 0, '提取简介', '', 'function', '', NULL, 88, '0/1/88/', 1, 'cadre:brief', 5, NULL, NULL, 1, NULL);


20200901
北邮， 西工大

-- 更新 SyncService
-- record.setIsRetire(false);

20200831
哈工大

update ow_member_apply set apply_stage=0 where apply_stage is null;
ALTER TABLE `ow_member_apply`
	CHANGE COLUMN `apply_stage` `apply_stage` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '申请培养阶段，0申请 2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书' AFTER `remark`;

20200830
西工大

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('upload_file_whitelist', '系统允许上传的文件格式',
        'jpg,jpeg,tif,png,gif,pdf,doc,docx,ppt,pptx,xls,xlsx,mp3,mpg,mpeg,flv,zip,rar,7z,ico', 1, 73, '');

-- 更新utils

20200826
北邮、北航

/*ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `vote` `vote` INT(10) UNSIGNED NOT NULL COMMENT '推荐提名的党员数' AFTER `email`,
	ADD COLUMN `positive_vote` INT(10) UNSIGNED NOT NULL COMMENT '推荐提名的正式党员数' AFTER `vote`;


ALTER TABLE `pcs_candidate`
	CHANGE COLUMN `vote` `vote` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '推荐提名的党员数' AFTER `type`,
	ADD COLUMN `positive_vote` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '推荐提名的正式党员数' AFTER `vote`;
*/

INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_3_paper_notice', NULL, NULL, NULL, '党代会投票三下阶段纸质票说明', '&#60;h1&#62;\r\n	党代会投票三下阶段纸质票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;', NULL, '', 0, 58);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_2_paper_notice', NULL, NULL, NULL, '党代会投票二下阶段纸质票说明', '&#60;h1&#62;\r\n	党代会投票二下阶段纸质票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;', NULL, '', 0, 57);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_1_paper_notice', NULL, NULL, NULL, '党代会投票一下阶段纸质票说明', '&#60;h1&#62;\r\n	党代会投票一下阶段纸质票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62;\r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;', NULL, '', 0, 56);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_3_m_notice', NULL, NULL, NULL, '党代会投票三下阶段手机端投票说明', '&#60;h1&#62;\r\n	党代会投票三下阶段手机端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;', NULL, '', 0, 55);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_2_m_notice', NULL, NULL, NULL, '党代会投票二下阶段手机端投票说明', '&#60;h1&#62;\r\n	党代会投票二下阶段手机端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;', NULL, '', 0, 54);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_1_m_notice', NULL, NULL, NULL, '党代会投票一下阶段手机端投票说明', '&#60;h1&#62;\r\n	党代会投票一下阶段手机端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;', NULL, '', 0, 53);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_3_pc_notice', NULL, NULL, NULL, '党代会投票三下阶段PC端投票说明', '&#60;h1&#62;\r\n	党代会投票三下阶段PC端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;', NULL, '', 0, 52);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_2_pc_notice', NULL, NULL, NULL, '党代会投票二下阶段PC端投票说明', '&#60;h1&#62;\r\n	党代会投票二下阶段PC端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;br /&#62;\r\n&#60;/p&#62;', NULL, '', 0, 51);
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_poll_1_pc_notice', NULL, NULL, NULL, '党代会投票一下阶段PC端投票说明', '&#60;h1&#62;\r\n	党代会投票一下阶段PC端投票说明\r\n&#60;/h1&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;1、......&#60;/strong&#62; \r\n&#60;/p&#62;\r\n&#60;p&#62;\r\n	&#60;strong&#62;2、......&#60;/strong&#62; \r\n&#60;/p&#62;', NULL, '', 0, 50);

-- 更新录入样表

20200826

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('ad_show_onjob', '干部任免审批表显示在职', 'true', 3, 72, '干部任免审批表是否对在职学习显示“在职”两字');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('zzb_address', '组织部地址', 'xxx', 1, 72, '');

-- 更新师大组织部联系电话58808302、58805166

-- 更新utils

20200824
西工大、北航、南航

20200823
北邮

20200821

哈工大、北航

-- 给ext文件夹的jsp cm:sign(
-- 检查/page/caslogout.jsp

update cadre_post cp left join unit_post up on cp.unit_post_id=up.id set cp.is_cpc=up.is_cpc;

-- 更新系统属性 upa_displayPosts （bool -> int   0->1 1->2)
UPDATE `sys_property` SET `name`='干部配备一览表显示',`content`='1',`type`='2',`remark`='1 原版   2 显示空岗及保留待遇  3 不占职数（无行政级别）' WHERE  `code`='upa_displayPosts';


-- 更新 ow_member_view

/*ALTER TABLE `pcs_candidate`
	ADD COLUMN `code` VARCHAR(20) NULL COMMENT '学工号，老师为工作证号，学生为学号' AFTER `type`,
	ADD COLUMN `realname` VARCHAR(100) NULL DEFAULT NULL COMMENT '真实姓名' AFTER `code`,
	ADD COLUMN `birth` DATE NULL DEFAULT NULL COMMENT '出生年月' AFTER `realname`,
	ADD COLUMN `title` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位及职务' AFTER `birth`,
	ADD COLUMN `gender` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别，1男 2女' AFTER `title`,
	ADD COLUMN `nation` VARCHAR(50) NULL DEFAULT NULL COMMENT '民族' AFTER `gender`,
	ADD COLUMN `native_place` VARCHAR(100) NULL DEFAULT NULL COMMENT '籍贯' AFTER `nation`,
	ADD COLUMN `ext_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位，人事信息' AFTER `native_place`,
	ADD COLUMN `grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间' AFTER `ext_unit`,
	ADD COLUMN `work_time` DATE NULL DEFAULT NULL COMMENT '参加工作时间' AFTER `grow_time`,
	ADD COLUMN `pro_post` VARCHAR(50) NULL DEFAULT NULL COMMENT '专业技术职务' AFTER `work_time`,
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL COMMENT '分党委' AFTER `pro_post`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部' AFTER `party_id`,
	ADD COLUMN `config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会' AFTER `branch_id`,
	ADD COLUMN `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '阶段，1 一下一上 2 二下二上 3 三下三上' AFTER `config_id`;

ALTER TABLE `pcs_candidate`
	ADD COLUMN `vote` INT(10) UNSIGNED NULL COMMENT '票数' AFTER `type`;

ALTER TABLE `pcs_pr_candidate`
	COMMENT='党代表被推荐人',
	ADD COLUMN `code` VARCHAR(20) NULL COMMENT '学工号，老师为工作证号，学生为学号' AFTER `user_id`,
	ADD COLUMN `realname` VARCHAR(100) NULL DEFAULT NULL COMMENT '真实姓名' AFTER `code`,
	ADD COLUMN `leader_sort_order` INT NOT NULL DEFAULT -1 COMMENT '校领导排序' AFTER `realname`,
	ADD COLUMN `user_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '1 干部 2 党员 3 其他' AFTER `leader_sort_order`,
	ADD COLUMN `edu_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学历，关联元数据，干部' AFTER `user_type`,
	ADD COLUMN `post` VARCHAR(100) NULL DEFAULT NULL COMMENT '职务，干部' AFTER `edu_id`,
	ADD COLUMN `grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间' AFTER `post`,
	ADD COLUMN `work_time` DATE NULL DEFAULT NULL COMMENT '参加工作时间' AFTER `grow_time`,
	ADD COLUMN `pro_post` VARCHAR(50) NULL DEFAULT NULL COMMENT '专业技术职务' AFTER `work_time`,
	ADD COLUMN `education` VARCHAR(50) NULL DEFAULT NULL COMMENT '最高学历' AFTER `pro_post`,
	ADD COLUMN `is_retire` TINYINT(1) UNSIGNED NULL COMMENT '是否退休' AFTER `education`,
	ADD COLUMN `edu_level` VARCHAR(50) NULL DEFAULT NULL COMMENT '培养层次，学生' AFTER `is_retire`,
	ADD COLUMN `party_id` INT(10) UNSIGNED NOT NULL COMMENT '分党委' AFTER `edu_level`,
	ADD COLUMN `config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会' AFTER `party_id`,
	ADD COLUMN `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '阶段，1 一下一上 2 二下二上 3 三下三上' AFTER `config_id`,
	ADD COLUMN `party_sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '分党委排序' AFTER `stage`,
	ADD COLUMN `unit_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位，人事信息' AFTER `party_sort_order`,
	ADD COLUMN `branch_vote` INT UNSIGNED NULL DEFAULT NULL COMMENT '提名支部数量' AFTER `unit_name`;

update pcs_candidate c, pcs_candidate_view cv
set c.code = cv.code, c.realname =cv.realname , c.title=cv.title, c.ext_unit=cv.ext_unit, c.gender=cv.gender,
 c.nation=cv.nation, c.native_place=cv.native_place, c.birth=cv.birth, c.grow_time=cv.grow_time,
c.work_time=cv.work_time, c.pro_post=cv.pro_post,
c.party_id=cv.party_id, c.branch_id=cv.branch_id, c.config_id=cv.config_id, c.stage=cv.stage
where c.id=cv.id;

update pcs_pr_candidate c, pcs_pr_candidate_view cv
set c.code=cv.code,c.realname=cv.realname,c.leader_sort_order=cv.leader_sort_order,c.user_type=cv.user_type, c.edu_id=cv.edu_id,
c.post=cv.post, c.grow_time=cv.grow_time, c.work_time=cv.work_time,c.pro_post=cv.pro_post,
c.education=cv.education,c.is_retire=cv.is_retire,c.edu_level=cv.edu_level,
c.party_id=cv.party_id,c.config_id=cv.config_id,c.stage=cv.stage,c.party_sort_order=cv.party_sort_order,c.unit_name=cv.unit_name
where c.id=cv.id;

-- 删除 pcs_pr_candidate_view   pcs_candidate_view  删除对应的类
drop view pcs_pr_candidate_view;
drop view pcs_candidate_view;

CREATE TABLE `pcs_branch` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委ID',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部ID',
	`name` VARCHAR(100) NOT NULL COMMENT '名称',
	`member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员数量',
	`positive_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '正式党员数量',
	`student_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生党员数量',
	`teacher_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '教师党员数量',
	`retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `config_id_party_id_branch_id` (`config_id`, `party_id`, `branch_id`)
)
COMMENT='召开党代会的支部，包含直属党支部'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1024
;


insert into pcs_branch select null as id, 3 as config_id, v.* from pcs_branch_view v;

CREATE TABLE `pcs_party` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委ID',
	`name` VARCHAR(100) NOT NULL COMMENT '名称',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`branch_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部数量',
	`member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '党员数量',
	`positive_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '正式党员数量',
	`student_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '学生党员数量',
	`teacher_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '教师党员数量',
	`retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量',
	`group_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '班子数量',
	`present_group_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '现任班子数量',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `config_id_party_id` (`config_id`, `party_id`)
)
COMMENT='参与党代会的分党委 '
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=64
;


insert into pcs_party select null as id, 3 as config_id,
v.id as party_id, v.name, v.sort_order, v.branch_count, v.member_count,
v.positive_count, v.student_member_count, v.teacher_member_count,
v.retire_member_count, v.group_count, v.present_group_count from pcs_party_view v;

-- 删除 pcs_party_view   pcs_branch_view  删除对应的类
drop view pcs_party_view;
drop view pcs_branch_view;

delete from pcs_party where party_id not in (select party_id from pcs_pr_allocate);
delete from pcs_branch where branch_id not in (select branch_id from pcs_recommend where branch_id is not null);
insert into pcs_branch(config_id, party_id, branch_id, name) select distinct pr.config_id, pr.party_id, pr.branch_id, if(isnull(ob.name), op.name, ob.name) from pcs_recommend pr
left join ow_branch ob on ob.id=pr.branch_id
left join ow_party op on op.id=pr.party_id
where pr.branch_id is null or pr.branch_id not in (select branch_id from pcs_branch where branch_id is not null);

ALTER TABLE `pcs_branch`
	ADD COLUMN `sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序' AFTER `name`;
update pcs_branch pb, ow_branch ob set pb.sort_order=ob.sort_order where pb.branch_id=ob.id;
*/
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('党代会二级党委报送提醒', NULL, 1, 'ct_pcs_wy_msg', NULL, NULL, NULL, NULL, '{0}：您好！按照第十三次党员代表大会筹备工作领导小组的统一部署，两委委员候选人酝酿提名工作“{1}”阶段于9月6日结束，目前尚未收到贵单位的报送材料。为了保证学校的整体进程不受影响，请务必今天完成报送。联系电话：**。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 63, 100719, '2020-08-17 16:21:45', '2020-08-17 16:22:01', 0, '');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('党代会代表二级党委报送提醒', NULL, 1, 'ct_pcs_pr_msg', NULL, NULL, NULL, NULL, '{0}：您好！按照第十三次党员代表大会筹备工作领导小组的统一部署，代表选举“{1}”阶段于9月6日结束，目前尚未收到贵单位的报送材料。为了保证学校的整体进程不受影响，请务必今天完成报送。联系电话：**。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 64, 100719, '2020-08-17 16:22:44', '2020-08-17 16:22:43', 0, '');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('党代会下发二下名单通知', NULL, 1, 'ct_pcs_stage2_msg', NULL, NULL, NULL, NULL, '各位书记/分党委管理员：您好！学校党委根据各分党委、党总支、直属党支部报送的两委委员候选人初步人选推荐提名情况，经研究确定了“二下”名单。请按照学校工作部署及时开展“二下二上”阶段工作，时间是9月8日至11日。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 65, 100719, '2020-08-17 16:33:36', '2020-08-17 16:33:36', 0, '');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('党代会代表未审核通过通知', NULL, 1, 'ct_pcs_pr_unpass_msg', NULL, NULL, NULL, NULL, '各位书记/分党委管理员：您好！您单位报送的党代表候选人初步人选名单未通过审核，请尽快按照要求重新报送。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 66, 100719, '2020-08-17 16:35:20', '2020-08-17 16:35:20', 0, '');
INSERT INTO `base_content_tpl` (`name`, `role_id`, `type`, `code`, `wx_msg_type`, `wx_title`, `wx_url`, `wx_pic`, `content`, `content_type`, `engine`, `param_count`, `param_names`, `param_def_values`, `sort_order`, `user_id`, `create_time`, `update_time`, `is_deleted`, `remark`) VALUES ('党代会代表审批通过通知', NULL, 1, 'ct_pcs_pr_pass_msg', NULL, NULL, NULL, NULL, '各位书记/分党委管理员：您好！您单位报送的党员代表大会代表候选人初步人选名单已通过审核，请按照学校工作部署及时开展“二下二上”阶段工作，时间是9月8日至11日。谢谢！[系统短信，请勿回复]', 1, NULL, NULL, NULL, NULL, 67, 100719, '2020-08-17 16:36:22', '2020-08-17 16:36:22', 0, '');
INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `is_deleted`, `sort_order`) VALUES (NULL, 'hf_pcs_expect_count_info', NULL, NULL, NULL, '【党代会】应到会人数如何计算？', '&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;党员大会进行选举时，有选举权的到会人数超过应到会人数的&#60;span&#62;4/5&#60;/span&#62;，会议有效。为了保证选举工作能够顺利进行，党员因下列情况不能参加选举的，经报上级党组织同意，并经支部党员大会通过，可以不计算在应到会人数之内：&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;1&#60;/span&#62;）患有精神病或因其他疾病导致不能表达本人意志的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;2&#60;/span&#62;）出国半年以上的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;3&#60;/span&#62;）虽未受到留党察看以上党纪处分，但正在服刑的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;4&#60;/span&#62;）年老体弱卧床不起和长期生病、生活不能自理的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;5&#60;/span&#62;）工作调动，下派锻炼，外出学习或工作半年以上等，按规定应转走正式组织关系而没有转走的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;p class=&#34;MsoListParagraph&#34; align=&#34;left&#34; style=&#34;margin-left:16.8pt;text-indent:28.1pt;&#34;&#62;\r\n	&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;（&#60;span&#62;6&#60;/span&#62;）已经回原籍长期居住的离退休人员中的党员，因特殊情况，没有从原单位转出党员组织关系、确实不能参加选举的。&#60;/span&#62;&#60;/b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;&#60;/span&#62; \r\n&#60;/p&#62;\r\n&#60;b&#62;&#60;span style=&#34;font-size:14.0pt;font-family:宋体;&#34;&#62;凡上述情况之外的党员不能参加党员大会进行选举，仍应计算在应到会人数之列。&#60;/span&#62;&#60;/b&#62;&#60;br /&#62;', NULL, '', 0, 49);

update sys_resource set url=replace(url, '/pcs', '/pcs/pcs')  where url like '/pcs%' and url not like '/pcs/pcs%';

/*ALTER TABLE `pcs_branch`
	ADD COLUMN `is_direct_branch` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否直属党支部' AFTER `branch_id`;

ALTER TABLE `pcs_party`
	ADD COLUMN `stage` TINYINT(3) UNSIGNED NULL COMMENT '当前启动的阶段，用于控制支部投票时间' AFTER `name`;

ALTER TABLE `pcs_party`
	ADD COLUMN `is_direct_branch` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否直属党支部' AFTER `name`;

update pcs_branch set is_direct_branch = isnull(branch_id);
update pcs_party pp, pcs_branch pb set pp.is_direct_branch=pb.is_direct_branch where pp.party_id=pb.party_id;

ALTER TABLE `pcs_candidate`
	ADD COLUMN `cadre_sort_order` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '干部的排序' AFTER `title`,
	ADD COLUMN `cadre_status` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '干部类别' AFTER `cadre_sort_order`;

ALTER TABLE `pcs_exclude_branch`
	ADD COLUMN `config_id` INT(10) UNSIGNED NOT NULL COMMENT '所属党代会' AFTER `id`,
	CHANGE COLUMN `branch_id` `branch_id` INT(10) UNSIGNED NULL COMMENT '支部' AFTER `party_id`,
	DROP INDEX `party_id_branch_id`;

ALTER TABLE `pcs_exclude_branch`
	ADD UNIQUE INDEX `config_id_party_id_branch_id` (`config_id`, `party_id`, `branch_id`);

ALTER TABLE `pcs_party`
	CHANGE COLUMN `stage` `current_stage` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '当前启动的阶段，用于控制投票时间' AFTER `is_direct_branch`;

ALTER TABLE `pcs_branch`
	CHANGE COLUMN `retire_member_count` `retire_member_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '离退休党员数量' AFTER `teacher_member_count`,
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `retire_member_count`;

ALTER TABLE `pcs_pr_allocate`
	ADD COLUMN `candidate_count` INT(10) UNSIGNED NULL COMMENT '推荐代表数量，差额后的每个分党委的推荐代表数量' AFTER `party_id`;

*/
delete from sys_resource where permission='pcsExcludeBranch:*';

/*ALTER TABLE `pcs_branch`
	ADD COLUMN `party_name` VARCHAR(100) NOT NULL COMMENT '分党委名称' AFTER `is_direct_branch`;

*/
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
`parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (2791, 0, '基层党组织列表', '', 'url', '', '/pcs/pcsPartyList', 469, '0/1/469/', 1, 'pcsPartyList:*', NULL, NULL, NULL, 1, 1450);


INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('ad_show_degree', '干部任免审批表显示学历', 'true', 3, 71, '干部任免审批表是否显示获得的学历');


-- 新建表2020.8.21 ly pcs_poll 等

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2542, 0, '党代会投票', '', 'url', '', '/pcs/pcsPoll', 469, '0/1/469/', 0, 'pcsPoll:*', NULL, NULL, NULL, 1, 1450);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2543, 0, '党代会推荐人', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollCandidate:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2544, 0, '党代会投票人', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollInspector:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2545, 0, '党代会投票结果', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollResult:*', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2546, 0, '党代会报送结果', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollReport:*', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_site_name', '党代会投票用户端名称', '党代会投票系统', 1, 67, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_jw_num', '纪委委员推荐人数', '11', 2, 70, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_dw_num', '党委委员推荐人数', '30', 2, 69, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_site_bg', '党代会投票登录页面背景', '\\sysProperty\\20200814\\f31daf3b-b563-4055-b49b-708db925e6b0.png', 5, 68, '');
/*
ALTER TABLE `pcs_party`
	CHANGE COLUMN `present_group_count` `present_group_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '现任班子数量' AFTER `group_count`;
ALTER TABLE `pcs_branch`
	ADD COLUMN `party_sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '分党委排序' AFTER `name`;
*/
update sys_resource set permission='pcsPoll:list' where permission='pcsPoll:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (980, 0, '编辑投票', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPoll:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (981, 0, '作废投票', '给分党委', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPoll:abolish', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (982, 0, '开启投票', '分党委', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPoll:open', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (983, 0, '删除投票', '真删除', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPoll:del', NULL, NULL, NULL, 1, NULL);

update sys_role set code='role_pcs_party' where code='role_pcs_admin';

INSERT INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_pcs_admin', '党代会-组织部管理员', 1, '469,473,2791,2542,980,981,982,983,2543,2544,2545,2546,474,476,472,499,477,481,491,494,597,598,599,485,482,487,490,500,501,502,503', '-1', NULL, 0, 0, 62, '');


20200814

-- 更新 cet_expert_view

-- 更新北师大 & 北邮 &南航
-- select count(*) from ow_member where party_id is not null and status in(3,4);
-- select count(*) from ow_member where party_id is null;
update  ow_member om, ow_member_quit omq set om.party_id=omq.party_id , om.branch_id=omq.branch_id
where om.user_id=omq.user_id and omq.`status`=3 and om.party_id is null;
update  ow_member om, ow_member_out omo set om.party_id=omo.party_id , om.branch_id=omo.branch_id
where om.user_id=omo.user_id and omo.`status` in(10, 2) and om.party_id is null;
ALTER TABLE `ow_member`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委' AFTER `user_id`;
-- 更新北师大 & 北邮 &南航

20200813
哈工大

20200812

西工大

ALTER TABLE `cet_project`
	COMMENT='培训项目，包含专题培训和年度培训',
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 日常培训 3 二级党委专题培训 4 二级党委日常培训' AFTER `id`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (968, 0, '专题培训', '', 'url', '', '/cet/cetProject?type=3', 869, '0/1/384/869/', 1, 'cetProject:list3', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (969, 0, '日常培训', '', 'url', '', '/cet/cetProject?type=4', 869, '0/1/384/869/', 1, 'cetProject:list4', NULL, NULL, NULL, 1, 400);

ALTER TABLE `cet_project`
	CHANGE COLUMN `is_deleted` `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除，0 正常 1 已删除' AFTER `has_archive`,
	ADD COLUMN `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '审批状态，0 待报送 1 已报送 2 审批通过 3 审批未通过（退回）' AFTER `is_deleted`,
	ADD COLUMN `back_reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '退回原因' AFTER `status`;

update cet_project set status=2;

update sys_resource set name='培训班类型（党校、二级党委培训）' where permission='cetProjectType:list';

-- 删除相关类
DROP VIEW IF EXISTS `cet_train_view`;

update cet_train_course ctc
left join cet_course cc on ctc.course_id=cc.id set ctc.is_online=1 where cc.`type`=1;
update cet_train_course ctc
left join cet_course cc on ctc.course_id=cc.id set ctc.address=cc.address where cc.`type`=3 and ctc.address is null;

ALTER TABLE `cet_train_obj`
	CHANGE COLUMN `train_id` `train_id` INT(10) UNSIGNED NULL COMMENT '所属培训班，二级党委培训时为空' AFTER `id`,
	ADD CONSTRAINT `FK_cet_train_obj_cet_project_obj` FOREIGN KEY (`obj_id`) REFERENCES `cet_project_obj` (`id`) ON DELETE CASCADE;
ALTER TABLE `cet_train_obj`
	DROP COLUMN `train_id`;

-- 更新 cet_train_obj_view ， cet_trainee_view

ALTER TABLE `cet_train`
	DROP COLUMN `enroll_status`;
ALTER TABLE `cet_project`
	CHANGE COLUMN `quit_count` `quit_count` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '已退出参训人员数量' AFTER `obj_count`,
	ADD COLUMN `start_time` DATETIME NULL DEFAULT NULL COMMENT '选课开启时间，针对二级党委培训' AFTER `quit_count`,
	ADD COLUMN `end_time` DATETIME NULL DEFAULT NULL COMMENT '选课关闭时间' AFTER `start_time`;

update cet_project set period=null;
update cet_project cp , (select project_id,sum(period) period from cet_project_plan group by project_id) tmp
set cp.period=tmp.period where cp.id=tmp.project_id;

ALTER TABLE `cet_project`
	ADD COLUMN `cet_party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班主办方，从二级党委中选择，针对二级党委培训' AFTER `type`,
	ADD COLUMN `unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班主办方，从设置了单位管理员的单位中选择，针对二级党委培训' AFTER `cet_party_id`;


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`)
VALUES (970, 0, '二级党委培训信息', '', 'menu', '', NULL, 714, '0/1/714/', 1, 'userCetProject:party', NULL, NULL, NULL, 1, 560);

UPDATE `sys_resource` SET name='其他培训',  `parent_id`='970', `parent_ids`='0/1/970/' where permission='userCetUnitTrain:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                             VALUES (971, 0, '专题培训', '', 'url', '', '/user/cet/cetProject?type=3', 970, '0/1/714/970/', 1, 'userCetProject:list3', NULL, NULL, NULL, 1, 800);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (972, 0, '日常培训', '', 'url', '', '/user/cet/cetProject?type=4', 970, '0/1/714/970/', 1, 'userCetProject:list4', NULL, NULL, NULL, 1, 700);

-- 更新 role_teacher 培训权限

ALTER TABLE `cet_train`
	ADD COLUMN `course_num` INT UNSIGNED NULL DEFAULT 0 COMMENT '课程数量' AFTER `end_time`;

-- 更新utils

update sys_resource set name='过程培训管理' where permission='cetProject:*';

update sys_resource set permission='cetProject:list1' where permission='cetProject:list';

update sys_resource set permission='cetProject:list2' where permission='cetAnnual:list';

ALTER TABLE `cet_unit_project`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班主办方' AFTER `cet_party_id`;

ALTER TABLE `cet_record`
	CHANGE COLUMN `no` `cert_no` SMALLINT(5) UNSIGNED NULL DEFAULT NULL COMMENT '证书编号，结业情况下，生成证书编号。培训大类、身份类别、年份、培训子类相同时，从0001算起' AFTER `user_type`;

ALTER TABLE `cet_project`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 日常培训' AFTER `id`,
	ADD COLUMN `is_party_project` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否二级党委培训' AFTER `type`;

ALTER TABLE `cet_project_type`
	ADD COLUMN `is_party_project` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否二级党委培训' AFTER `type`;

delete from sys_resource where permission='cetProjectType:*';
update sys_resource set permission='cetProjectType:*' where permission='cetProjectType:list';

update sys_resource set url='/cet/cetProject?cls=1' where permission='cetProject:list1';
update sys_resource set url='/cet/cetProject?cls=2' where permission='cetProject:list2';
update sys_resource set url='/cet/cetProject?cls=3' where permission='cetProject:list3';
update sys_resource set url='/cet/cetProject?cls=4' where permission='cetProject:list4';


delete from cet_project_type  where type>2 or is_party_project=1;

insert into cet_project_type
select id+200 as id, name, type, 1 as is_party_project, code, sort_order, remark, is_deleted from cet_project_type where type=1;

insert into cet_project_type
select id+200 as id, name, type, 1 as is_party_project, code, sort_order, remark, is_deleted from cet_project_type where type=2;

update cet_unit_project set project_type_id = project_type_id+200;


-- 二级党委培训管理员添加权限 cetProject:* cetProjectObj:*  cetTrain:*  cetTrainObj:*  cetTrainCourse:* cetTrainee:*

ALTER TABLE `cet_train`
	DROP COLUMN `is_finished`;

delete from sys_scheduler_job where clazz='job.cet.CetAutoAdjust';

update base_meta_type set code='mt_branch_institution' where  class_id=25 and name='事业单位';

ALTER TABLE `ow_branch`
	CHANGE COLUMN `unit_type_id` `unit_type_id` INT(10) UNSIGNED NULL COMMENT '单位属性，关联元数据，企业、事业单位' AFTER `is_base_team`;

ALTER TABLE `cet_annual_obj`
	ADD COLUMN `trainee_type_id` INT(10) UNSIGNED NOT NULL COMMENT '培训对象类型，冗余字段' AFTER `year`;
update cet_annual_obj cao, cet_annual ca set cao.trainee_type_id=ca.trainee_type_id where ca.id=cao.annual_id;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('draw_od_check', '组织部审批领取志愿书', 'true', 3, 67, '领取志愿书是否需要组织部审批');
-- 领取志愿书不需要组织部审批，需更新历史数据（北邮）
-- update ow_member_apply set grow_status=2 where stage=5 and (draw_status is null or draw_status=1) and grow_status is null;

ALTER TABLE `base_meta_class`
	CHANGE COLUMN `available` `is_deleted` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否删除 ' AFTER `sort_order`,
	DROP COLUMN `role_id`;

update base_meta_class set is_deleted =ABS(is_deleted-1);
  -- 更新 base_meta_type_view

INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `is_deleted`) VALUES (2700, '发文属性', '元数据管理', '发文类型', 'mc_dispatch_attr', '', '', '', 2617, 0);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2700, '党务', 'mt_6o4qyy', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2700, '行政', 'mt_zkdnwg', NULL, '', '', 2, 1);

-- 查询一下实际情况 select distinct attr from dispatch_type;
update dispatch_type set attr = (select id from base_meta_type bmt where bmt.code = 'mt_6o4qyy')
where attr =  '党务' or attr =  '党委';
update dispatch_type set attr = (select id from base_meta_type bmt where bmt.code = 'mt_zkdnwg')
where attr =  '行政';

ALTER TABLE `dispatch_type`
	CHANGE COLUMN `attr` `attr` INT UNSIGNED NULL DEFAULT NULL COMMENT '发文属性' AFTER `name`;

ALTER TABLE `base_meta_class`
	CHANGE COLUMN `is_deleted` `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除 ' AFTER `sort_order`;

-- 添加源数据
INSERT INTO `base_meta_class` (`id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `is_deleted`) VALUES (3100, '交流轮岗类型', '干部选拔任用', '交流轮岗', 'mc_sc_shift', '', '', '', 2615, 0);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3100, '机关部处之间轮岗', 'mt_cudnjh', NULL, NULL, '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3100, '学院之间轮岗', 'mt_qkhpqb', NULL, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3100, '机关部处与学院之间轮岗', 'mt_ut5hjb', NULL, NULL, '', 3, 1);

-- 更新系统资源
UPDATE `sys_resource` SET `type`='menu', is_leaf=0, `url`=NULL WHERE  `id`=893;

-- 添加系统资源
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3042, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_sc_shift', 893, '0/1/339/893/', 1, 'mc_sc_shift:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3043, 0, '交流轮岗', '', 'url', '', '/sc/scShift', 893, '0/1/339/893/', 1, 'scShiftPost:*', NULL, NULL, NULL, 1, NULL);


20200730
吉大

ALTER TABLE `cet_train_course`
	DROP INDEX `train_id_course_id`,
	DROP INDEX `FK_cet_train_course_cet_course`,
	DROP FOREIGN KEY `FK_cet_train_course_cet_course`,
	DROP FOREIGN KEY `FK_cet_train_course_cet_train`;

ALTER TABLE `cet_train_course`
	ADD CONSTRAINT `FK_cet_train_course_cet_train` FOREIGN KEY (`train_id`) REFERENCES `cet_train` (`id`) ON DELETE CASCADE;

ALTER TABLE `cet_train_course`
	ADD COLUMN `project_id` INT(10) UNSIGNED NULL COMMENT '所属培训项目，针对过程培训' AFTER `id`,
	CHANGE COLUMN `train_id` `train_id` INT(10) UNSIGNED NULL COMMENT '培训班次，过程培训时允许为空' AFTER `project_id`,
	ADD COLUMN `selected_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '选课人数' AFTER `end_time`,
	ADD COLUMN `finish_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '签到人数' AFTER `selected_count`,
	ADD COLUMN `eva_finish_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '测评完成账号数量' AFTER `finish_count`,
	ADD CONSTRAINT `FK_cet_train_course_cet_project` FOREIGN KEY (`project_id`) REFERENCES `cet_project` (`id`) ON DELETE CASCADE;

-- 删除相关类 还有 BaseCetTrain   CetProjectDetailController  CetAutoAdjust
DROP VIEW IF EXISTS `cet_train_course_view`;


update cet_train_course ctc
left join
(select train_course_id, count(id) as eva_finish_count from cet_train_inspector_course group by train_course_id)tmp on tmp.train_course_id=ctc.id
set ctc.eva_finish_count=tmp.eva_finish_count;

update cet_train_course ctc
left join
(select train_course_id, count(id) as selected_count, sum(if(is_finished, 1,0)) as finish_count
from  cet_train_obj group by train_course_id)tmp on tmp.train_course_id=ctc.id
set ctc.selected_count=tmp.selected_count, ctc.finish_count = tmp.finish_count;

update cet_train_course ctc
left join
(select train_course_id, count(id) as eva_finish_count from cet_train_inspector_course group by train_course_id)tmp on tmp.train_course_id=ctc.id
set ctc.eva_finish_count=tmp.eva_finish_count;

-- 更新  cet_train_course_stat_view

ALTER TABLE `cet_project_plan`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '培训形式，1 线下培训  2 线上培训  3 上级网上专题班  4 分组研讨  5 实践教学  6 自主学习  8 撰写心得体会' AFTER `end_date`,
	CHANGE COLUMN `period` `period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '学时，只有type=8时需要手动设定' AFTER `summary`;

ALTER TABLE `cet_train_course`
	ADD COLUMN `period` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '学时' AFTER `course_id`;
ALTER TABLE `cet_plan_course`
	ADD COLUMN `period` DECIMAL(10,1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '学时' AFTER `course_id`;

ALTER TABLE `cet_train_course`
	ADD COLUMN `is_online` TINYINT(1) UNSIGNED NULL COMMENT '培训形式，0：线下 1：线上，针对过程培训的课程' AFTER `train_id`,
	CHANGE COLUMN `name` `name` VARCHAR(200) NULL DEFAULT NULL COMMENT '课程名称' AFTER `period`,
	CHANGE COLUMN `teacher` `teacher` VARCHAR(50) NULL DEFAULT NULL COMMENT '教师名称' AFTER `name`,
	ADD COLUMN `summary` TEXT NULL COMMENT '课程要点，针对线下和线上课程、二级党委培训' AFTER `teacher`;

update cet_train_course ctc, cet_course cc
left join cet_expert ce on ce.id=cc.expert_id set ctc.period=cc.period,
ctc.name=cc.name, ctc.summary=cc.summary, ctc.teacher=ce.realname where ctc.course_id=cc.id;

-- 更新 cet_train_obj_view

ALTER TABLE `cet_plan_course`
	COMMENT='培训方案包含的培训课程， 针对自主学习、上级网上专题',
	ADD COLUMN `name` VARCHAR(200) NOT NULL COMMENT '课程名称' AFTER `course_id`,
	ADD COLUMN `unit` VARCHAR(200) NULL COMMENT '上级单位名称，针对上级网上专题' AFTER `name`;

update cet_plan_course cpc, cet_course cc set cpc.period=cc.period,
                                              cpc.name=cc.name, cpc.unit=cc.address where cpc.course_id=cc.id;

ALTER TABLE `cet_course`
	CHANGE COLUMN `period` `period` DECIMAL(10,1) NOT NULL COMMENT '学时' AFTER `url`;

update cet_course cc
left join
(select cc.id, cc.type, cc.name, sum(cci.period) as period from cet_course cc left join cet_course_item cci on cci.course_id=cc.id where cc.`type` in(4) and cc.is_deleted=0  group by cc.id) tmp
on cc.id=tmp.id
set cc.period=tmp.period where tmp.period is not null;

update cet_plan_course cpc
left join cet_course cc on cpc.course_id=cc.id
set cpc.period=cc.period;

-- 更新utils

ALTER TABLE `sys_property`
	CHANGE COLUMN `content` `content` TEXT NOT NULL COMMENT '内容' AFTER `name`;

ALTER TABLE `sys_property`
	CHANGE COLUMN `remark` `remark` TEXT NULL DEFAULT NULL COMMENT '说明' AFTER `sort_order`;

2020.7.28
北化工

2020.7.27
西工大

2020.7.27

update sys_resource set name='管理所有任务权限', permission='oaTaskShowAll:*', type='function', parent_id=561,
                        parent_ids='0/1/560/561/', is_leaf=1 where permission='oaTaskAdmin:*';

update sys_resource set name='任务管理' where permission='oaTask:*';

ALTER TABLE `oa_task`
	ADD COLUMN `user_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务对象数量' AFTER `status`;
ALTER TABLE `oa_task`
	ADD COLUMN `file_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务附件数量' AFTER `status`,
	CHANGE COLUMN `user_count` `user_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '任务对象人数' AFTER `file_count`,
	ADD COLUMN `finish_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '任务对象完成人数' AFTER `user_count`;
ALTER TABLE `oa_task`
	ADD COLUMN `report_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '已报送人数' AFTER `user_count`;

update oa_task ot,
(select ot.id, count(distinct otf.id) as file_count,
-- 任务对象数量
count(distinct otu.id) as user_count,
-- 已报送数
count(distinct otu3.id) as report_count,
-- 已完成数
count(distinct otu2.id) as finish_count from oa_task ot
left join oa_task_file otf on otf.task_id=ot.id
left join oa_task_user otu on otu.task_id = ot.id and otu.is_delete=0
left join oa_task_user otu2 on otu2.task_id = ot.id and otu2.is_delete=0 and otu2.status=1
left join oa_task_user otu3 on otu3.task_id = ot.id and otu3.is_delete=0 and otu2.status in (0, 1)
group by ot.id) tmp
set ot.file_count=tmp.file_count, ot.user_count=tmp.user_count, ot.finish_count=tmp.finish_count where ot.id=tmp.id;

-- 删除相关类
drop view oa_task_view;
-- 删除 controller.oa.TaskUser， persistence.oa.common.IOaTaskUserMapper

update oa_task_admin ta, sys_user u set u.role_ids=concat(u.role_ids,
  (select id from sys_role where code='role_oa_admin'), ',') where ta.user_id=u.id;
-- select u.id, u.realname, u.code, u.role_ids, ta.show_all from oa_task_admin ta, sys_user_view u where ta.user_id=u.id;
-- 删除相关类
drop table oa_task_admin;

CREATE TABLE `tmp_sort_cadre` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`user_id` INT(10) UNSIGNED NULL DEFAULT NULL,
	`code` VARCHAR(50) NULL DEFAULT NULL,
	`realname` VARCHAR(50) NULL DEFAULT NULL,
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL,
	PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=512
;
CREATE TABLE `cet_project_file` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`project_id` INT(10) UNSIGNED NOT NULL COMMENT '所属培训班id',
	`file_name` VARCHAR(200) NOT NULL COMMENT '培训课件名称',
	`file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '课件， pdf或word',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序，每个培训班内部的排序',
	PRIMARY KEY (`id`)
)
COMMENT='培训课件'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=30
;


ALTER TABLE `cet_project`
	ADD COLUMN `file_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '课件数量' AFTER `quit_count`;

ALTER TABLE `cet_project`
	CHANGE COLUMN `obj_count` `obj_count` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '参训人员数量， 选择或导入参训人时更新' AFTER `other_trainee_type`,
	CHANGE COLUMN `quit_count` `quit_count` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '已退出参选人员数量' AFTER `obj_count`,
	CHANGE COLUMN `file_count` `file_count` INT(10) UNSIGNED NULL DEFAULT '0' COMMENT '课件数量' AFTER `quit_count`;

-- 更新Utils
-- 删除 xss.ignoreUrIs


2020.7.22
北化工

ALTER TABLE `crp_record`
	ADD COLUMN `is_add_form` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否存入干部简历' AFTER `type`;

UPDATE `crp_record` SET `is_add_form`=1;


ALTER TABLE `oa_task_user_file`
	CHANGE COLUMN `file_name` `file_name` VARCHAR(300) NULL DEFAULT NULL AFTER `user_id`;

update sys_resource set url='/m/cadreList?type=1', permission='m:cadreList' where permission='m:cadreHistory:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
 `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (967, 1, '校领导信息', '', 'url', 'fa fa-street-view', '/m/cadreList?type=2', 692, '0/692/', 1, 'm:cadreList:leader', 4, NULL, NULL, 1, 1851);

-- 更新utils

update  sys_property set code='dr_site_bg' where code='drLoginBg';
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('dr_site_name', '民主推荐用户端名称', '线上民主推荐系统', 1, 66, '');

-- 更新录入样表

UPDATE sys_resource set url='/metaClass_type_list?cls=mc_dr_type',permission='mc_dr_type:*' WHERE id=897;

delete from sys_resource where permission='drOnline:*';

-- update sys_resource set type='url', url='/dr/drOnline', is_leaf=1, permission='drOnline:*' where permission='drOnline:list';

REPLACE INTO `sys_resource` (id, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (895, 0, '线上民主推荐', '', 'url', '', '/dr/drOnline', 890, '0/1/339/890/', 1, 'drOnline:*', 1, NULL, NULL, 1, 700);

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2530, 0, '线上民主推荐', '', 'url', '', '/dr/drOnlineParam', 896, '0/1/339/890/896/', 0, 'drOnlineParam:menu', NULL, NULL, NULL, 1, 300);


REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2531, 0, '推荐人身份类型', '', 'function', '', '/dr/drOnlineInspectorType', 2530, '0/1/339/890/896/2530/', 1, 'drOnlineInspectorType:*', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2532, 0, '线上民主推荐情况模板', '', 'function', '', '/dr/drOnlineNotice', 2530, '0/1/339/890/896/2530/', 1, 'drOnlineNotice:*', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2528, 0, '推荐职务及资格条件', '', 'function', '', '/dr/drOnlinePost', 895, '0/1/339/890/895/', 1, 'drOnlinePost:*', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2529, 0, '推荐结果', '', 'function', '', '/dr/drOnlineResult', 895, '0/1/339/890/895/', 1, 'drOnlineResult:*', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2533, 0, '参评人导出记录', '', 'function', '', '/dr/drOnlineInspectorLog', 895, '0/1/339/890/895/', 1, 'drOnlineInspectorLog:*', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2534, 0, '参评人', '', 'function', '', '/dr/drOnlineInspector', 895, '0/1/339/890/895/', 1, 'drOnlineInspector:*', NULL, NULL, NULL, 1, NULL);

REPLACE INTO `base_meta_class` (id, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (82, NULL, '推荐类型', '干部选拔任用', '民主推荐', 'mc_dr_type', '', '', '', 82, 1);
REPLACE INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (82, '谈话推荐', 'mt_f639fe', NULL, NULL, '', 1, 1);
REPLACE INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (82, '会议推荐', 'mt_dr_type_meeting', NULL, NULL, '', 2, 1);
REPLACE INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (82, '二次会议推荐', 'mt_qnuxk8', NULL, NULL, '', 3, 1);



-- 更新 ow_party_member_group_view
-- 更新 ow_party_member_view


2020.7.17
西工大

ALTER TABLE `pm_meeting`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '会议类型 1 支部党员大会 2 支部委员会 3 党小组会  4 党课  5 主题党日活动  6 组织生活会  7民主生活会' AFTER `branch_id`,
	ADD COLUMN `month` INT UNSIGNED NULL DEFAULT NULL COMMENT '月份' AFTER `quarter`;

replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2772, 0, '三会一课管理', '', 'menu', 'fa fa-pencil-square-o', NULL, 1, '0/1/', 0, 'pm:menu', NULL, NULL, NULL, 1, 4500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2788, 0, '组织生活会', '', 'url', '', '/pmMeeting?type=6', 2772, '0/1/2772/', 1, 'pmMeeting:list:6', NULL, NULL, NULL, 1, 60);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2789, 0, '民主生活会', '', 'url', '', '/pmMeeting?type=7', 2772, '0/1/2772/', 1, 'pmMeeting:list:7', NULL, NULL, NULL, 1, 50);

UPDATE `sys_resource` SET `url`='/pmMeeting2Stat', `permission`='pmMeeting2Stat:list' WHERE  `url`='/pmMeetingStat';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2790, 0, '数据统计', '', 'url', '', '/pmMeetingStat', 2772, '0/1/2772/', 1, 'pmMeetingStat:list', NULL, NULL, NULL, 1, 40);

update pm_meeting set month=month(date);

-- 三会一课 更新 录入样表

-- 修改web.xml（casConfig.xml）
-- 修改tomcat 下配置 conf/context.xml： <Context> -> <Context xmlBlockExternal="false">

-- 移动 /WEB-INF/jsp/ext 至根目录

-- 删除spring-m.properties 在 spring.properties增加： m.page.pageSize=5

--
replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (966, 0, '签到人员管理', '', 'function', '', NULL, 652, '0/1/384/652/', 1, 'cetTrainObj:*', 1, NULL, NULL, 1, NULL);

ALTER TABLE `ow_party`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '关联单位' AFTER `url`,
	CHANGE COLUMN `unit_type_id` `unit_type_id` INT(10) UNSIGNED NOT NULL COMMENT '关联单位属性，关联元数据，企业，事业单位' AFTER `type_id`,
	CHANGE COLUMN `is_separate` `is_separate` TINYINT(1) UNSIGNED NOT NULL COMMENT '所在单位是否独立法人' AFTER `is_enterprise_nationalized`;

ALTER TABLE `ow_party`
	CHANGE COLUMN `phone` `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '联系电话' AFTER `is_separate`;
-- 更新`ow_party_view`

ALTER TABLE `ow_branch_member_group`
	CHANGE COLUMN `appoint_time` `appoint_time` DATE NULL COMMENT '任命时间，本届班子任命时间' AFTER `actual_tran_time`;
ALTER TABLE `ow_party_member_group`
	CHANGE COLUMN `appoint_time` `appoint_time` DATE NULL COMMENT '任命时间，本届班子任命时间' AFTER `actual_tran_time`;

2020.7.15
南航，北航

2020.7.10
西工大


ALTER TABLE `sys_user_info`
	ADD COLUMN `user_status` VARCHAR(100) NULL DEFAULT NULL COMMENT '人员状态，人事状态/在岗情况/学籍状态' AFTER `realname`;

DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;

-- 更新 SyncService 增加 ui.setUserStatus

-- INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('label_adform_reason', '任免审批表-任免理由', '工 作 需 要', 1, 65, '默认');


ALTER TABLE `ow_member_certify`
	CHANGE COLUMN `from_unit` `from_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '原单位' AFTER `political_status`,
	CHANGE COLUMN `to_title` `to_title` VARCHAR(100) NULL DEFAULT NULL COMMENT '介绍信抬头' AFTER `from_unit`,
	CHANGE COLUMN `to_unit` `to_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '拟去往的工作学习单位' AFTER `to_title`;

-- 更新utils

ALTER TABLE `cet_record`
	ADD COLUMN `cet_party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班主办方，二级党委培训时赋值' AFTER `source_type`;

ALTER TABLE `ow_member_in`
	CHANGE COLUMN `from_fax` `from_fax` VARCHAR(50) NULL COMMENT '转出单位传真' AFTER `from_phone`;

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `from_fax` `from_fax` VARCHAR(100) NULL COMMENT '转出单位传真' AFTER `from_phone`;

ALTER TABLE `ow_member_transfer`
	CHANGE COLUMN `from_fax` `from_fax` VARCHAR(20) NULL COMMENT '转出单位传真' AFTER `from_phone`;

CREATE TABLE `pm_meeting2` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '分党委id',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '支部id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年份',
	`quarter` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '季度',
	`month` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '月份',
	`date` DATETIME NULL DEFAULT NULL COMMENT '实际召开会议时间',
	`address` VARCHAR(100) NULL DEFAULT NULL COMMENT '地点',
	`type1` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '活动类型 1 支部党员大会 2 支部委员会 3 党小组会  4 党课  5主题党日活动',
	`type2` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '活动类型 5主题党日活动',
	`number1` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '次数（当前第几次会议）',
	`number2` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '次数（当前第几次会议）',
	`time1` VARCHAR(200) NULL DEFAULT NULL COMMENT '时长',
	`time2` VARCHAR(200) NULL DEFAULT NULL COMMENT '时长',
	`short_content` VARCHAR(200) NULL DEFAULT NULL COMMENT '主要内容',
	`content` TEXT NULL COMMENT '详细内容',
	`due_num` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '应到人数',
	`attend_num` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '实到人数',
	`absents` VARCHAR(200) NULL DEFAULT NULL COMMENT '缺席名单及原因，输入文本',
	`presenter` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '主持人',
	`recorder` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '记录人',
	`file_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '附件名称',
	`file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '附件地址',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '状态， 0 未审核 1 审核通过 2 审核未通过',
	`is_back` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否退回',
	`reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '退回原因',
	`is_delete` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否删除',
	PRIMARY KEY (`id`)
)
COMMENT='三会一课2(支部会议)'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=17
;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2783, 0, '三会一课管理', '', 'menu', 'fa fa-pencil-square-o', NULL, 1, '0/1/', 0, 'pmMeeting2:menu', NULL, NULL, NULL, 1, 4470);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2784, 0, '党支部活动记录', '', 'url', '', '/pmMeeting2', 2783, '0/1/2783/', 1, 'pmMeeting2:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2785, 0, '数据统计', '', 'url', '', '/pmMeetingStat', 2783, '0/1/2783/', 1, 'pmMeetingStat:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2786, 0, '三会一课2:更新', '', 'function', '', NULL, 2783, '0/1/2783/', 1, 'pmMeeting2:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2787, 0, '三会一课2:审批', '', 'function', '', NULL, 2783, '0/1/2783/', 1, 'pmMeeting2:approve', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `pm_meeting2`
	CHANGE COLUMN `reason` `reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '审批未通过原因' AFTER `status`,
	DROP COLUMN `is_back`;

2020.7.3

西工大

ALTER TABLE `cet_project`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 日常培训' AFTER `id`;
ALTER TABLE `cet_record`
	CHANGE COLUMN `user_type` `user_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '身份类别， 1 教职工  2学生' AFTER `project_type`;

update sys_resource set name='培训班类型（党校培训）' where permission='cetProjectType:list';

ALTER TABLE `cet_unit_train`
	ADD COLUMN `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `remark`;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `candidate_time`;

ALTER TABLE `cet_project`
	ADD COLUMN `has_archive` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否已归档，由触发器、定时器变更状态' AFTER `create_time`;

ALTER TABLE `cet_project`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0 正常 1 已删除' AFTER `has_archive`;


CREATE TABLE `ow_member_certify` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户，从党员库中选择',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年份',
	`sn` INT(10) UNSIGNED NOT NULL COMMENT '介绍信编号，年份+3位数字自动编码',
	`political_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
	`from_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '转出单位',
	`to_title` VARCHAR(100) NULL DEFAULT NULL COMMENT '转入单位抬头',
	`to_unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '转入单位',
	`certify_date` DATE NULL DEFAULT NULL COMMENT '介绍信日期',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`)
)
COMMENT='临时组织关系介绍信'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=2
;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
VALUES (2540, 0, '临时组织关系介绍信', '', 'url', '', '/member/memberCertify', 105, '0/1/105/', 1, 'memberCertify:*', NULL, NULL, NULL, 1, 15000);

ALTER TABLE `cet_project`
	ADD COLUMN `category` CHAR(200) NULL DEFAULT NULL COMMENT '专题分类，关联元数据，逗号分割' AFTER `project_type_id`;

update cet_project set category=project_type_id;
-- 新建元数据
INSERT INTO `base_meta_class` (id, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`,
`extra_options`, `sort_order`, `available`) VALUES (3002, NULL, '培训内容分类', '培训综合管理', '党校培训', 'mc_cet_project_category', '', '', '', 2615, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '党性修养', 'mt_iad6jf', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '政治理论', 'mt_t1aiiv', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '时政解读', 'mt_ralley', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '公共管理', 'mt_fc4sly', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '经济管理', 'mt_xeamzj', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '社会管理', 'mt_8osohw', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '文化管理', 'mt_rlrzn2', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '应急管理', 'mt_yh3re9', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '依法行政', 'mt_hot0vk', NULL, '', '', 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '能力建设', 'mt_lj9py9', NULL, '', '', 10, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '科技人文', 'mt_avqjjc', NULL, '', '', 11, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '国际战略', 'mt_0usseq', NULL, '', '', 12, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '身心健康', 'mt_p0fk5f', NULL, '', '', 13, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '教育实践', 'mt_a3ojvc', NULL, '', '', 14, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '专题讲座', 'mt_8f2gfg', NULL, '', '', 15, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '网络培训', 'mt_95kwc7', NULL, '', '', 16, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3002, '其他', 'mt_m4sr7y', NULL, '', '', 17, 1);

update cet_project p, base_meta_class c, base_meta_type t, cet_project_type pt
set p.category=t.id where p.category=pt.id and pt.name=t.name and t.class_id=c.id and c.code='mc_cet_project_category';

truncate table cet_project_type;
insert into cet_project_type(id, name, type, sort_order) select mt.id, mt.name, 1 as type, mt.sort_order from base_meta_type mt, base_meta_class mc where mc.code='mc_cet_train_type' and mt.class_id=mc.id;

insert into cet_project_type(id, name, type, sort_order) select mt.id + 100, mt.name, 2 as type, mt.sort_order from base_meta_type mt, base_meta_class mc where mc.code='mc_cet_train_type' and mt.class_id=mc.id;


update cet_project p, (select pp.project_id, t.`type` from cet_train t, cet_project_plan pp where t.plan_id=pp.id group by pp.project_id) t set p.project_type_id= t.type where p.id=t.project_id;

update cet_project set project_type_id = project_type_id+100 where type=2;

delete from base_meta_class where code='mc_cet_train_type';
delete from sys_resource where url='/metaClass_type_list?cls=mc_cet_train_type';

ALTER TABLE `cet_train`
	DROP COLUMN `type`;
-- 更新 cet_train_view

ALTER TABLE `cet_unit_project`
	ALTER `project_type` DROP DEFAULT;
ALTER TABLE `cet_unit_project`
	ADD COLUMN `project_type_id`  INT UNSIGNED NULL COMMENT '培训班类型' AFTER `project_name`,
	CHANGE COLUMN `project_type` `category` VARCHAR(200) NULL COMMENT '培训内容分类' AFTER `project_type_id`;

update cet_unit_project p, base_meta_class c, base_meta_type t, base_meta_type t1,  base_meta_class c1
set p.category=t.id where p.category=t1.id and t.name=t1.name and t.class_id=c.id
                      and c.code='mc_cet_project_category' and t1.class_id=c1.id and c1.code='mc_cet_upper_train_type2' ;

delete from base_meta_class where code='mc_cet_upper_train_type2';
delete from sys_resource where url='/metaClass_type_list?cls=mc_cet_upper_train_type2';

ALTER TABLE `cet_upper_train`
	ADD COLUMN `project_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班类型，针对其他培训' AFTER `special_type`,
	ADD COLUMN `category` VARCHAR(200) NULL DEFAULT NULL COMMENT '培训内容分类，针对其他培训' AFTER `project_type_id`;

-- 创建 triggers

update cet_project set project_type_id=414 where type=1 and (project_type_id is null or project_type_id<414);
update cet_project set project_type_id=514 where type=2 and (project_type_id is null or project_type_id<414);

ALTER TABLE `cet_upper_train`
	ADD COLUMN `is_graduate` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否结业' AFTER `pdf_note`;
update cet_upper_train set is_graduate=1;

ALTER TABLE `cet_unit_train`
	ADD COLUMN `is_graduate` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否结业' AFTER `score`;
update cet_unit_train set is_graduate=1;


2020.6.30
 西工大

ALTER TABLE `cet_unit_project`
	ADD COLUMN `special_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '培训类别 1专题培训 2日常培训' AFTER `project_type`;
DROP VIEW `cet_party_view`;
ALTER TABLE `cet_party`
  ADD COLUMN `admin_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '管理员数量' AFTER `name`;

-- 新建表
CREATE TABLE `cet_train_obj` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`train_id` INT(10) UNSIGNED NOT NULL COMMENT '所属培训班',
	`obj_id` INT(10) UNSIGNED NOT NULL COMMENT '所属培训对象，关联表cet_project_obj',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '参训人员',
	`train_course_id` INT(10) UNSIGNED NOT NULL COMMENT '培训班课程',
	`can_quit` TINYINT(1) UNSIGNED NULL DEFAULT '1' COMMENT '是否允许退课，由管理员设置的必选课程，不允许退课',
	`is_finished` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否实际完成，签到即完成',
	`sign_time` DATETIME NULL DEFAULT NULL COMMENT '签到时间',
	`sign_out_time` DATETIME NULL DEFAULT NULL COMMENT '签退时间',
	`sign_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '签到类型， 1 手动签到 2 批量导入 3 刷卡签到',
	`choose_time` DATETIME NULL DEFAULT NULL COMMENT '选课时间',
	`choose_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '选课操作人， 如果是管理员选的， 那么显示管理员姓名； 如果是本人选的， 那么显示“本人”字样。',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '选课IP',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `user_id_train_course_id` (`user_id`, `train_course_id`)
)
COMMENT='已选课参训人员及其签到情况'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=8672
;

insert into cet_train_obj select ctc.id, ct.train_id, ct.obj_id, o.user_id, ctc.train_course_id, ctc.can_quit, ctc.is_finished,
ctc.sign_time, ctc.sign_out_time, ctc.sign_type, ctc.choose_time, ctc.choose_user_id, ctc.ip, ctc.remark
from cet_trainee_course ctc, cet_trainee ct, cet_project_obj o where ctc.trainee_id=ct.id and ct.obj_id=o.id;

-- 更新 cet_train_obj_view（注意顺序—）

update sys_approval_log l, cet_trainee tee, cet_train t  set l.record_id=tee.obj_id,
stage=concat(stage, '(',  t.name, ')')  where l.type=4 and l.record_id=tee.id and tee.train_id=t.id;

update sys_approval_log set type= 4 where type=5;

drop table cet_trainee_course;
drop table cet_trainee;
drop view cet_trainee_course_view;

-- 删除 CetTraineeCourseController CetTraineeCourseService CetTraineeService  BranchMemberAdminService PartyMemberAdminService

-- 更新 cet_expert_view
-- 更新 cet_train_course_view
-- 更新 cet_trainee_view


delete from sys_resource where permission='userCetTrain:list2';

update cet_unit_project set special_type=1 where special_type is null;

ALTER TABLE `cet_record`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '培训类型，1 党校专题培训  2 党校日常培训 3 二级党委专题培训 4 上级调训 5 二级党委日常培训' AFTER `name`,
	CHANGE COLUMN `type_id` `source_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训记录来源ID，关联各个模块培训项目的主键' AFTER `type`,
	ADD COLUMN `source_type` TINYINT UNSIGNED NULL DEFAULT NULL COMMENT '培训记录来源类别，1 党校专题培训  2 党校日常培训 3 二级党委专题培训 4 上级调训 5 二级党委日常培训 6 党校其他培训 7 二级党委其他培训' AFTER `source_id`;

truncate table cet_record;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (780, 0, '组织关系转出打印', NULL, 'function', NULL, NULL, 252, '0/1/105/252/', 1, 'memberOut:print', 0, NULL, NULL, 1, NULL);

-- 给分党委、党建管理员添加 “memberOut:print”权限

ALTER TABLE `cet_project_type`
	COMMENT='培训班类型，针对过程培训班',
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '培训类型， 1 专题培训 2 日常培训' AFTER `name`,
	ADD COLUMN `code` VARCHAR(50) NULL DEFAULT NULL COMMENT '代码' AFTER `type`;
UPDATE `sys_resource` SET `name`='培训班类型（党校培训）' WHERE  `id`=658;
ALTER TABLE `cet_project`
	CHANGE COLUMN `project_type_id` `project_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班类型' AFTER `is_valid`;

insert cet_project_type(name, type, code, sort_order, remark, is_deleted) select name, 2 as type, code, sort_order, remark, is_deleted from cet_project_type;
-- update cet_project set project_type_id = project_type_id+14 where type=2;


-- 更新SyncService：状态已经变更为退休状态的，不再同步人事库 ； teacherInfo!=null && ；
--

ALTER TABLE `cet_upper_train`
	ADD COLUMN `update_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP
	  ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间' AFTER `back_reason`;

ALTER TABLE `cet_record`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除' AFTER `archive_time`,
	ADD COLUMN `special_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '培训大类， 1 专题 2 日常' AFTER `is_deleted`,
	ADD COLUMN `project_type` INT UNSIGNED NULL DEFAULT NULL COMMENT '培训班类型， 关联cet_project_type表' AFTER `special_type`,
	ADD COLUMN `user_type` INT UNSIGNED NULL DEFAULT NULL COMMENT '身份类别， 1 教职工  2学生' AFTER `project_type`,
	ADD COLUMN `no` SMALLINT UNSIGNED NULL DEFAULT NULL COMMENT '证书编号，结业情况下，生成证书编号。培训大类、身份类别、年份、培训子类相同时，从0001算起' AFTER `user_type`;

2020.6.23

ALTER TABLE `cet_annual_obj`
	ADD COLUMN `identity` VARCHAR(200) NULL COMMENT '参训人员身份（双肩挑，支部书记）' COLLATE 'utf8_general_ci' AFTER `post_type`;

ALTER TABLE `cadre`
	ADD COLUMN `original_post` VARCHAR(255) NULL DEFAULT NULL COMMENT '原在单位及职务，离任时赋值' AFTER `remark`,
	ADD COLUMN `appoint_date` DATE NULL DEFAULT NULL COMMENT '任职日期，离任时赋值' AFTER `original_post`,
	ADD COLUMN `depose_date` DATE NULL DEFAULT NULL COMMENT '免职日期，离任时赋值' AFTER `appoint_date`;

update cadre cv
left join cadre_post cp on cp.cadre_id=cv.id and cp.is_main_post=1 and cp.is_first_main_post=1
left join dispatch_cadre dc on dc.id = cv.dispatch_cadre_id
left join dispatch d on d.id=dc.dispatch_id
set original_post=cv.title, appoint_date=cp.lp_work_time,depose_date=d.work_time
 where cv.status in(3,4,9);

-- 更新 cadre_view

ALTER TABLE `cet_upper_train`
 CHANGE COLUMN `train_type` `train_type` INT(10) UNSIGNED NULL COMMENT '培训班类型' AFTER `other_organizer`;


INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`, `is_deleted`) VALUES
('调整预备党员所在党组织', '更新党员发展模块中的预备党员所在党组织与党员库中不一致的情况', 'job.member.MemberAutoAdjust', '0 0/10 * * * ?', 1, 0, 30, '2018-04-24 15:12:17', 0);

-- 更新录入样表

-- 撤销转出bug
update ow_member m, ow_member_out o set m.party_id=o.party_id, m.branch_id=o.branch_id
where m.party_id is null and m.status=1 and o.user_id=m.user_id and o.`status`<2;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cet_support_cert', '显示干部培训结业证书', 'false', 3, 61, '培训综合管理');

2020.6.19
南航

2020.6.18
西工大

-- 更新录入样表
UPDATE `sys_resource` SET `url`='/cet/cetUnitTrain_info?cls=2' WHERE  `id`=2536;

ALTER TABLE `ow_member_modify`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NULL COMMENT '所属分党委' AFTER `user_id`;

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2537, 0, '补录审核', '', 'function', '', NULL, 869, '0/1/384/869/', 1, 'cetUnitProject:check', NULL, NULL, NULL, 1, 100);

DELETE FROM sys_resource WHERE id=724;
DROP VIEW IF EXISTS `cet_party_view`;
ALTER TABLE `cet_party`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `sort_order`;


ALTER TABLE `cet_party_admin`
	CHANGE COLUMN `party_id` `cet_party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委' AFTER `id`;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2538, 0, '二级党委培训管理员', '', 'url', '', '/cet/cetParty', 656, '0/1/384/656/', 0, 'cetParty:*', NULL, NULL, NULL, 1, 55);
-- INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2539, 0, '管理员', '', 'function', '', NULL, 3040, '0/1/384/656/3040/', 1, 'cetPartyAdmin:*', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `cet_unit_project`
	CHANGE COLUMN `party_id` `cet_party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训班主办方，从二级党委中选择' AFTER `year`;
ALTER TABLE `cet_party`
	CHANGE COLUMN `party_name` `name` VARCHAR(100) NOT NULL COMMENT '分党委名称' AFTER `party_id`;

DROP VIEW IF EXISTS `cet_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_party_view` AS
select cp.*,COUNT(cpa.user_id) AS admin_count
from cet_party cp
left JOIN cet_party_admin cpa on cp.id=cpa.cet_party_id
GROUP BY cp.id;

-- 删除原分党委管理员的 二级党委培训的权限
-- 更新角色cet_admin_party 的权限

-- 初始化二级党委列表（同步基层党组织数据）
insert into cet_party(party_id, name, sort_order, is_deleted) select id as party_id, name, sort_order, is_deleted from  ow_party;
-- 把原二级党委id替换为新的id
update cet_unit_project up , cet_party p set up.cet_party_id=p.id where up.cet_party_id=p.party_id;


DROP VIEW IF EXISTS `ow_org_admin_view`;

ALTER TABLE `cet_unit_project`
	ADD COLUMN `is_online` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '培训形式，0：线下 1：线上' AFTER `project_type`;
update cet_unit_project up, base_meta_type t set up.is_online=1 where up.project_type=t.id and t.bool_attr=1;
-- 删除 培训班类型（mc_cet_upper_train_type2） 的布尔属性（是否网络培训）
update base_meta_class set bool_attr=null where code='mc_cet_upper_train_type2';

-- 新增表 cet_record
CREATE TABLE `cet_record` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`year` INT(10) UNSIGNED NOT NULL COMMENT '年度',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '参训人',
	`trainee_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '参训人员类型，从元数据中获取，0代表其他',
	`other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选其他参训人员类型时，需要填写',
	`title` VARCHAR(200) NULL DEFAULT NULL COMMENT '时任单位及职务',
	`start_date` DATE NULL DEFAULT NULL COMMENT '起始时间',
	`end_date` DATE NULL DEFAULT NULL COMMENT '结束时间',
	`name` VARCHAR(300) NULL DEFAULT NULL COMMENT '培训班名称，培训内容',
	`type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '培训类型，1 党校专题培训  2 党校日常培训 3 二级党委培训 4 上级调训 5 党校其他培训',
	`type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '培训项目ID，关联各个模块培训项目的主键',
	`organizer` VARCHAR(300) NULL DEFAULT NULL COMMENT '培训主办方，主办单位',
	`period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '完成学时数，总数，包含线上，线下',
	`online_period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '线上完成学时数',
	`should_finish_period` DECIMAL(10,1) UNSIGNED NULL DEFAULT NULL COMMENT '应完成学时数，总数，包含线上，线下',
	`is_graduate` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否结业',
	`is_valid` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否计入年度学习任务',
	`remark` VARCHAR(300) NULL DEFAULT NULL COMMENT '备注',
	`archive_time` DATETIME NULL DEFAULT NULL COMMENT '归档时间',
	PRIMARY KEY (`id`)
)
COMMENT='培训记录明细汇总表，如果已退出，则应删除相应的培训记录'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1983
;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (724, 0, '培训记录汇总', '', 'url', '', '/cet/cetRecord', 384, '0/1/384/', 1, 'cetRecord:*', NULL, NULL, NULL, 1, 195);

update cet_upper_train set trainee_type_id=1;


ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `train_name` `train_name` VARCHAR(300) NOT NULL COMMENT '培训班名称，研修方向（针对出国研修）' AFTER `train_type`,
	CHANGE COLUMN `agency` `agency` VARCHAR(300) NULL DEFAULT NULL COMMENT '组织培训机构，针对出国研修' AFTER `country`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('memberApply_needContinueDevelop', '申请继续培养', 'false', 3, 52, '在党员发展申请中，支持申请继续培养');

ALTER TABLE `cet_project`
	ADD COLUMN `trainee_type_ids` VARCHAR(200) NULL COMMENT '参训人员类型，逗号隔开' AFTER `year`,
	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选了其他参训人员类型时，需要填写' AFTER `trainee_type_ids`;

update cet_project p,
(select project_id, group_concat(trainee_type_id) as trainee_type_ids from cet_project_trainee_type group by project_id) tmp
set p.trainee_type_ids=tmp.trainee_type_ids where p.id=tmp.project_id;


-- 删除相关类
drop view cet_project_view;
drop table cet_project_trainee_type;

ALTER TABLE `cet_project`
	ADD COLUMN  `obj_count` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '参训人员数量， 选择或导入参训人时更新' AFTER `other_trainee_type`,
	ADD COLUMN `quit_count` INT(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '已退出参选人员数量' AFTER `obj_count`;

update cet_project p,
(select project_id, count(*) as obj_count from cet_project_obj group by project_id) tmp
set p.obj_count=tmp.obj_count where p.id=tmp.project_id;

update cet_project p,
(select project_id, count(*) as quit_count from cet_project_obj where is_quit=1 group by project_id) tmp
set p.quit_count=tmp.quit_count where p.id=tmp.project_id;


INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2601, NULL, '参训人员身份', '培训综合管理', '上级调训管理', 'mc_cet_identity', '', '', '', 2614, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2601, '双肩挑', 'mt_mg36bf', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2601, '支部书记', 'mt_mlzslp', NULL, '', '', 2, 1);
ALTER TABLE `cet_upper_train`
	DROP COLUMN `is_double`,
	DROP COLUMN `is_branch_secretary`;
ALTER TABLE `cet_upper_train`
	ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`,
	ADD COLUMN `score` VARCHAR(100) NULL DEFAULT NULL COMMENT '培训成绩' AFTER `pdf_note`,
	CHANGE COLUMN `post_id` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '时任职务属性' AFTER `title`;
ALTER TABLE `cet_unit_train`
  	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选其他参训人员类型时，需要填写' AFTER `trainee_type_id`,
	ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`,
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '时任职务属性' AFTER `title`;
ALTER TABLE `cet_upper_train`
	ADD COLUMN `special_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '其他培训类别 1党校专题培训 2党校日常培训' AFTER `type`;

ALTER TABLE `cet_unit_train`
	ADD COLUMN `score` VARCHAR(100) NULL DEFAULT NULL COMMENT '培训成绩' AFTER `pdf_note`;

ALTER TABLE `cet_project_obj`
	DROP INDEX `FK_cet_project_obj_cet_trainee_type`,
	DROP FOREIGN KEY `FK_cet_project_obj_cet_trainee_type`;

ALTER TABLE `cet_project_obj`
	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选了其他参训人员类型时，需要填写' AFTER `trainee_type_id`,
  ADD COLUMN `identity` VARCHAR(200) NULL DEFAULT NULL COMMENT '参训人员身份（双肩挑，支部书记）' AFTER `other_trainee_type`;

REPLACE INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('cet_admin_party', '二级党委管理员', 1, '384,869,880,2536,2537,652,881', '-1', 0, 0, 1, 29, '干部教育培训');
REPLACE INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('cet_admin_upper', '二级单位管理员', 1, '384,846', '-1', 1, 0, 1, 33, '干部培训-上级调训');
delete from sys_role where code in ('cet_admin_unit', 'cet_admin_ps');


2020.6.9

ALTER TABLE `cet_party`
	ADD COLUMN `party_name` VARCHAR(100) NOT NULL COMMENT '分党委名称' AFTER `party_id`,
	ADD COLUMN `sort_order` INT(10) UNSIGNED NOT NULL COMMENT '分党委排序，当前顺序' AFTER `party_name`,
	DROP COLUMN `user_id`;

ALTER TABLE `cet_party`
	ALTER `party_id` DROP DEFAULT;
ALTER TABLE `cet_party`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NULL COMMENT '所属基层党组织' AFTER `id`;

CREATE TABLE `cet_party_admin` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型， 1 书记 2副书记 3 委员 4 普通管理员',
	`remark` VARCHAR(255) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`),
	UNIQUE INDEX `party_id_user_id` (`party_id`, `user_id`)
)
COMMENT='二级党委管理员-干部培训，系统提供同步书记、组织委员为管理员的功能'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

ALTER TABLE `ow_member_apply`
	ADD COLUMN `apply_stage` TINYINT(3) UNSIGNED NULL DEFAULT 0 COMMENT '申请培养阶段，0申请 2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书' AFTER `remark`;

-- 更新 ow_member_apply_view

update sys_role r, (select * from sys_role where code='cet_trainee') tmp
set r.resource_ids = CONCAT_WS(',', r.resource_ids, tmp.resource_ids),r.m_resource_ids = CONCAT_WS(',',r.m_resource_ids,  tmp.m_resource_ids) where r.code='role_teacher';

delete from sys_role where code='cet_trainee';


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2536, 0, '参训人员信息', '', 'url', '', '/cet/cetUnitTrain_info', 869, '0/1/384/869/', 1, 'cetUnitTrain:list', NULL, NULL, NULL, 1, 250);
ALTER TABLE `cet_unit_train`
	CHANGE COLUMN `status` `status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '补录状态 0 审批通过 1 待二级党委审批 2 待组织部审批 3暂存' AFTER `add_time`,
	ADD COLUMN `reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '未补录成功原因' AFTER `status`;

-- 更新录入样表


2020.6.8
哈工大

update sys_resource set name='学习培训中心(选课)' where permission='m:cetTrainList:*';

delete from sys_resource where id in(681,718,721,722);

2020.6.5
西工大，北航

2020.6.3

drop view if exists crs_candidate_view;

ALTER TABLE `cet_train`
	DROP COLUMN `pub_status`;

-- 更新 cet_train_view

ALTER TABLE `cet_project`
	DROP COLUMN `status`,
	DROP COLUMN `pub_status`;

-- 更新 cet_project_view

delete from cet_upper_train where upper_type=2;

ALTER TABLE `cet_upper_train`
	DROP COLUMN `upper_type`;

ALTER TABLE `cet_upper_train_admin`
	DROP COLUMN `upper_type`;

ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '派出类型，1 组织部派出 2 其他部门派出 8 出国研修 10 其他培训（党校）' AFTER `year`;

ALTER TABLE `cet_upper_train`
	ADD COLUMN `is_online` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '培训形式，0：线下 1：线上' AFTER `type`,
	ADD COLUMN `is_double` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否双肩挑' AFTER `user_id`,
	ADD COLUMN `is_branch_secretary` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否支部书记' AFTER `is_double`;

-- select type, count(*) from cet_upper_train group by type;
update cet_upper_train  set type =2 where type=1;
update cet_upper_train  set type =1 where type=0;


update sys_resource set url='/user/cet/cetUpperTrain' where permission='userCetUpperTrain:*';

update sys_resource set url='/cet/cetUpperTrain?type=2&addType=2' where permission='cetUpperTrain:listUpper1';

update sys_resource set url='/cet/cetUpperTrain?type=1&addType=3' where permission='cetUpperTrain:*';

update sys_resource set url='/cet/cetUpperTrain?type=2&addType=3' where permission='cetUpperTrain:listUpper';

update sys_resource set url='/cet/cetUpperTrainAdmin' where permission='cetUpperTrainAdmin,mc_cet_upper_train_organizer,mc_cet_upper_train_type:*';


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (3039, 1, '离任干部信息', '', 'url', 'fa fa-history', '/m/cadreHistory', 692, '0/692/', 1, 'm:cadreHistory:*', NULL, NULL, NULL, 1, 1849);



update cet_trainee_type set name='处级干部' where code='t_cadre';
ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `post_id` `post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职务属性（弃用）' AFTER `title`;

ALTER TABLE `cet_upper_train`
	ALTER `is_double` DROP DEFAULT,
	ALTER `is_branch_secretary` DROP DEFAULT;
ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `is_double` `is_double` TINYINT(1) UNSIGNED NULL COMMENT '是否双肩挑' AFTER `user_id`,
	CHANGE COLUMN `is_branch_secretary` `is_branch_secretary` TINYINT(1) UNSIGNED NULL COMMENT '是否支部书记' AFTER `is_double`;
ALTER TABLE `cet_trainee_type`
	ADD UNIQUE INDEX `name` (`name`),
	ADD UNIQUE INDEX `code` (`code`);
update sys_resource set name='培训班类型(党校培训-线下培训)'  where permission='mc_cet_train_type:*';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
`count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (960, 0, '培训班主办方(上级调训)', '', 'url', '', '/metaClass_type_list?cls=mc_cet_upper_train_organizer', 656, '0/1/384/656/', 1, 'mc_cet_upper_train_organizer:*', 1, NULL, NULL, 1, 138);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
`count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (961, 0, '培训班类型(上级调训)', '', 'url', '', '/metaClass_type_list?cls=mc_cet_upper_train_type', 656, '0/1/384/656/', 1, 'mc_cet_upper_train_type:*', 1, NULL, NULL, 1, 136);

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                             `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                             `count_cache_roles`, `available`, `sort_order`)
                             VALUES (707, 0, '单位管理员（上级调训）', '', 'url', '', '/cet/cetUpperTrainAdmin', 656, '0/1/384/656/', 1, 'cetUpperTrainAdmin:*', 1, NULL, NULL, 1, 140);
-- 更新培训管理员权限 （其中参训人员类型不应提供权限）

update sys_resource set sort_order=800 where permission='cetTraineeType:*';

update sys_resource set name='专题分类（党校培训）' where permission='cetProjectType:list';

update sys_resource set name='授课方式（党校培训-课程中心）' where permission='mc_cet_teach_method:*';

update sys_resource set name='专家信息（党校培训-课程中心-主讲人）' where permission='cetExpert:list';

update sys_resource set permission='cetUpperTrain:unitAdmin' where permission='cetUpperTrain:listUpper1';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (962, 0, '出国研修管理', '', 'url', '', '/cet/cetUpperTrain?type=8&addType=3', 703, '0/1/384/703/', 1, 'cetUpperTrain:abroad', NULL, NULL, NULL, 1, 100);
ALTER TABLE `cet_upper_train`
	ADD COLUMN `country` VARCHAR(300) NULL DEFAULT NULL COMMENT '前往国家，针对出国研修' AFTER `address`,
	ADD COLUMN `agency` VARCHAR(300) NULL DEFAULT NULL COMMENT '培训机构，针对出国研修' AFTER `country`;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (963, 0, '出国研修信息', '', 'url', '', '/user/cet/cetUpperTrain?type=8', 714, '0/1/714/', 1, 'userCetUpperTrain:abroad', NULL, NULL, NULL, 1, 590);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (964, 0, '其他培训', '', 'url', '', '/cet/cetUpperTrain?type=10&addType=3', 684, '0/1/384/684/', 1, 'cetUpperTrain:school', NULL, NULL, NULL, 1, 90);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (965, 0, '其他培训', '', 'url', '', '/user/cet/cetUpperTrain?type=10', 715, '0/1/714/715/', 1, 'userCetUpperTrain:school', NULL, NULL, NULL, 1, 400);
update sys_resource set sort_order=500 where permission='userCetProject:list1';
update sys_resource set sort_order=450 where permission='userCetProject:list2';

ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `organizer` `organizer` INT(10) UNSIGNED NULL COMMENT '培训班主办方，有一个“其他”选项（值为0），其他培训（党校）时默认为空' AFTER `post_id`;

-- 更新SyncService proPost、proPostLevel  (teacherInfo!=null && )


ALTER TABLE `cet_upper_train`
	CHANGE COLUMN `upper_train_type_id` `trainee_type_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '参训人员类型，从元数据中获取，0代表其他' AFTER `is_branch_secretary`,
	ADD COLUMN `other_trainee_type` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他参训人员类型，如果选其他参训人员类型时，需要填写' AFTER `trainee_type_id`;

ALTER TABLE `cet_unit_train`
	ADD COLUMN `status` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 0 审批通过 1 待二级党委审批 2 待组织部审批' AFTER `add_time`;

-- 更新 utils
-- 更新录入样表

update sys_property set code='upa_displayPosts',name='配备一览表显示空岗列表', remark='干部配备一览表是否显示空岗名称列表'  where code='cadrePost_vacant';

-- 修复文件综合管理bug 已更新北化工
update base_meta_class set code='mc_dwf_work_type_gb' where code='mc_dwf_work_type';
update base_meta_class set code='mc_dwf_work_type_dj' where code='mc_dwf_work_type_ow';
update sys_resource  set permission='mc_dwf_work_type_gb:*', url='/metaClass_type_list?cls=mc_dwf_work_type_gb' where permission='mc_dwf_work_type:*';
update sys_resource  set permission='mc_dwf_work_type_dj:*', url='/metaClass_type_list?cls=mc_dwf_work_type_dj' where permission='mc_dwf_work_type_ow:*';

-- 修复文件综合管理bug
-- 更新 base_meta_type_view

-- 更新录入样表

2020.5.28
北航

update base_meta_class set bool_attr='在综合查询中是否显示' where code='mc_unit_type';

CREATE TABLE `unit_post_group` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分组编号',
	`name` VARCHAR(50) NULL DEFAULT NULL COMMENT '分组名称',
	`post_ids` TEXT NULL COMMENT '岗位ids',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='岗位分组'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3
;
ALTER TABLE `unit_post`
	ADD COLUMN `group_id` INT(10) UNSIGNED NULL COMMENT '岗位分组id' AFTER `unit_id`;

ALTER TABLE `unit_post`
	ADD CONSTRAINT `FK_unit_post_unit_post_group` FOREIGN KEY (`group_id`) REFERENCES `unit_post_group` (`id`) ON DELETE SET NULL;

-- 更新 dispatch_cadre_view
-- 更新 unit_post_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1200, 0, '岗位分组（查看）', '', 'function', '', NULL, 836, '0/1/836/', 1, 'unitPostGroup:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1201, 0, '岗位分组（管理）', '', 'function', '', NULL, 836, '0/1/836/', 1, 'unitPostGroup:*', NULL, NULL, NULL, 1, NULL);


REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                             `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                             `count_cache_roles`, `available`, `sort_order`) VALUES (835, 0, '干部岗位（管理）', '', 'function', '', NULL, 836, '0/1/836/', 1, 'unitPost:*', 2, NULL, NULL, 1, NULL);
update sys_resource set sort_order=9 where permission='unitPost:allocation';
update sys_resource set name='岗位配备统计（查看）' where permission='unitPostAllocation:module2';

-- 更新utils

2020.5.26
北邮，西北工大，北化工

2020.5.24

西交大

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES
 ('wx_support', '支持发送微信', 'false', 3, 60, '');

UPDATE sys_scheduler_job SET need_log='0' WHERE  id = 28;

/*INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('drLoginBg', '线上民主推荐登录页北京图片', '\\sysProperty\\20200522\\fdfdefeb-b7a3-4950-bb7c-d114fc979a0e.png', 5, 52, '大小820*363，PNG格式');

ALTER TABLE `dr_online`
	CHANGE COLUMN `notice` `notice` TEXT NULL COMMENT 'pc端手机端线上民主推荐说明' AFTER `members`,
	ADD COLUMN `mobile_notice` TEXT NULL COMMENT '手机端线上民主推荐说明' AFTER `notice`,
	ADD COLUMN `inspector_notice` TEXT NULL COMMENT '账号分发说明' AFTER `mobile_notice`;
ALTER TABLE `dr_online_inspector_log`
	ALTER `unit_id` DROP DEFAULT;
ALTER TABLE `dr_online_inspector_log`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '所属单位' AFTER `type_id`;
ALTER TABLE `dr_online_inspector`
	ALTER `unit_id` DROP DEFAULT;
ALTER TABLE `dr_online_inspector`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '所属单位' AFTER `type_id`;

*/

2020.5.20

西交大、南航

2020.5.19

INSERT INTO `sys_role` (`code`, `name`, `type`, `resource_ids`, `m_resource_ids`, `user_count`, `available`,
                        `is_sys_hold`, `sort_order`, `remark`) VALUES ('cadreDp', '民主党派成员', 1, '108,353,354,746,748,749,750,755,756,758,759,751,752,760,763,765,761,766,767,768,769,770,762,771,772,753,754,747,774,775,776,524,280,305,306,307,308,309,310,311,317,357,231,232,234,235,236,237,238,239,240,242,338,416,207,208,209', '695', NULL, 0, 1, 61, '干部其他信息-民主党派库的人员');

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                             VALUES (3038, 0, '干部信息表(简表)', '', 'function', '', NULL, 90, '0/1/88/90/', 1, 'cadreInfoFormSimple:*', NULL, NULL, NULL, 1, NULL);

2020.5.17

ALTER TABLE `oa_task`
	ADD COLUMN `user_file_count` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '须任务对象上传文件数量' AFTER `deadline`;
-- 更新 oa_task_view  oa_task_user_view

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1003, 0, '查看单位档案页', '', 'function', '', NULL, 87, '0/1/85/87/', 0, 'unit:view', 3, NULL, NULL, 1, NULL);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (53, 0, '基本信息', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:base', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (54, 0, '行政班子', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:unitTeam', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (55, 0, '干部岗位', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:unitPost', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (56, 0, '干部任免信息', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:dispatchCadre', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (57, 0, '单位发展历程文件', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:unitTransfer', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (58, 0, '校领导班子', '', 'function', '', NULL, 1003, '0/1/85/87/1003/', 1, 'unit:leaderView', NULL, NULL, NULL, 1, NULL);


-- 已更新南航
update sys_resource set permission='userPassport:*' where permission='userPassportApply:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (950, 0, '申办证件', '', 'function', '', NULL, 262, '0/1/262/', 1, 'userPassportApply:*', NULL, NULL, NULL, 1, NULL);
-- 给原来有userPassportApply:*权限的人，增加权限userPassport:*
update sys_role sr, (select id from sys_resource where permission='userPassportApply:*')tmp,
(select id from sys_resource where permission='userPassport:*')tmp2 set resource_ids=concat(resource_ids,',', tmp2.id)  where  find_in_set(tmp.id, resource_ids);

-- 更新录入样表

UPDATE sys_resource SET NAME='档案认定',menu_css=null,parent_ids='0/1/339/', parent_id=339,sort_order=190 WHERE id=411;
DELETE from sys_resource WHERE id=410;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2535, 0, '入党时间认定', '', 'function', '', NULL, 411, '0/1/339/411/', 1, 'verifyGrowTime:*', NULL, NULL, NULL, 1, NULL);

update sys_role sr, (select id from sys_resource where permission='verify:menu')tmp,
(select id from sys_resource where permission='verifyGrowTime:*')tmp2 set resource_ids=concat(resource_ids,',', tmp2.id)  where  find_in_set(tmp.id, resource_ids);

-- 0516已提交代码未更新


CREATE TABLE `verify_grow_time` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL,
	`old_grow_time` DATE NULL DEFAULT NULL COMMENT '系统中党员的入党时间（认定前日期)',
	`verify_grow_time` DATE NULL DEFAULT NULL COMMENT '组织认定入党时间(认定后日期)',
	`material_time` DATE NULL DEFAULT NULL COMMENT '形成时间',
	`material_grow_time` DATE NULL DEFAULT NULL COMMENT '记载的入党时间（入党志愿书）',
	`ad_time` DATE NULL DEFAULT NULL COMMENT '形成时间，档案中最新干部任免审批表',
	`ad_grow_time` DATE NULL DEFAULT NULL COMMENT '记载的入党时间（任免审批表）',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：历史记录 2：已删除，每个干部的正式记录只有一条',
	`submit_user_id` INT(10) UNSIGNED NOT NULL COMMENT '提交人',
	`submit_ip` VARCHAR(50) NOT NULL COMMENT '提交IP',
	`submit_time` DATETIME NOT NULL COMMENT '提交时间',
	`update_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '修改人',
	`update_ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '修改IP',
	`update_time` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`id`)
)
COMMENT='入党时间认定，干部档案审核'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;


-- 更新 cadre_view


2020.5.13
西北工大

-- 更新dispatch_cadre_view

ALTER TABLE `cadre_work`
	ADD COLUMN `note` VARCHAR(100) NULL DEFAULT NULL COMMENT '补充说明' AFTER `is_cadre`;


2020.5.12
西北工大

update base_meta_type set code='mt_dr_type_meeting' where id=513 and name='会议推荐';
ALTER TABLE `abroad_taiwan_record`
	DROP FOREIGN KEY `FK_abroad_taiwan_cadre`;
ALTER TABLE `abroad_taiwan_record`
	ADD CONSTRAINT `FK_abroad_taiwan_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE;
ALTER TABLE `abroad_passport`
	DROP FOREIGN KEY `FK_abroad_passport_abroad_taiwan_record`;
ALTER TABLE `abroad_passport`
	ADD CONSTRAINT `FK_abroad_passport_abroad_taiwan_record` FOREIGN KEY (`taiwan_record_id`) REFERENCES `abroad_taiwan_record` (`id`) ON DELETE CASCADE;

-- 更新 dispatch_cadre_view

ALTER TABLE `cadre_work`
	CHANGE COLUMN `detail` `detail` TEXT NULL DEFAULT NULL COMMENT '工作单位及担任职务（或专技职务）' AFTER `end_time`;


ALTER TABLE `cet_upper_train_admin`
	DROP COLUMN `type`,
	DROP COLUMN `leader_user_id`;


2020.5.9
北航，西北工大，南航

ALTER TABLE `unit`
	ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否已删除' AFTER `not_stat_post`;
-- 更新 unit_view

insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES
(50, 0, '干部任免信息', '', 'menu', 'fa fa-files-o', NULL, 1, '0/1/', 0, 'dispatchCadre:menu', 4, NULL, NULL, 1, 6901);

insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
`parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (51, 0, '历史任免信息', '', 'url', '', '/dispatchCadre?hasMenu=0', 50, '0/1/50/', 1, 'dispatchCadre:noMenu', NULL, NULL, NULL, 1, 200);

insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
`parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (52, 0, '历史任免文件', '', 'url', '', '/dispatch?hasMenu=0', 50, '0/1/50/', 1, 'dispatch:noMenu', NULL, NULL, NULL, 1, 100);

2020.5.8
哈工大

UPDATE `base_meta_class` SET `name`='推荐类型' WHERE  `id`=82;

2020.5.6
北航、西北工大

-- 现任科级干部
update cadre set status=8 where type=2 and status=1;
-- 离任科级干部
update cadre set status=9 where type=2 and status=3;
-- 删除干部类型（先检查一下）
ALTER TABLE `cadre` DROP COLUMN `type`;
-- 更新cadre_view
-- 更新 unit_post_view

insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
`parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (110, 0, '隐藏领导信息-基本信息的菜单', '只显示现任处级干部', 'function', '', NULL, 90, '0/1/88/90/', 1, 'hide:cadreMenu', NULL, NULL, NULL, 1, NULL);


2020.4.29
西北工大

ALTER TABLE `ow_organizer`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型， 1 校级组织员 2 院系级组织员   3支部组织员' AFTER `year`;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (2782, 0, '支部组织员信息', '', 'url', '', '/organizer?type=3', 1035, '0/1/105/1035/', 1, 'organizer:list3', NULL, NULL, NULL, 1, NULL);
ALTER TABLE `ow_organizer`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '联系党委' AFTER `user_id`,
	ADD COLUMN `branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '联系党支部' AFTER `party_id`;
ALTER TABLE `ow_organizer`
	ADD COLUMN `phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '联系方式' AFTER `units`;

2020.4.28
北航
/*
-- 修改竞争上岗，已更新南航
ALTER TABLE `cr_info`
	ADD COLUMN `name` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '表头名称，默认：干部竞争上岗' AFTER `year`;
update cr_info set name='干部竞争上岗';
ALTER TABLE `cr_info`
	ADD COLUMN `apply_post_num` INT NOT NULL COMMENT '允许填报的志愿数，1个或2个' AFTER `name`;
update cr_info set apply_post_num=2;
*/

2020.4.27
北航

ALTER TABLE `sys_user_info`
	ADD COLUMN `res_ids_add` TEXT NULL DEFAULT NULL COMMENT '网页端加权限资源id' AFTER `sync`,
	ADD COLUMN `m_res_ids_add` TEXT NULL DEFAULT NULL COMMENT '手机端加权限资源id' AFTER `res_ids_add`,
	ADD COLUMN `res_ids_minus` TEXT NULL DEFAULT NULL COMMENT '网页端减权限资源id' AFTER `m_res_ids_add`,
	ADD COLUMN `m_res_ids_minus` TEXT NULL DEFAULT NULL COMMENT '手机端减权限资源id' AFTER `res_ids_minus`;

REPLACE INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('sync', '同步字段是否只同步一次', '011011110110110', 1, 51, '0或空代表每次都同步覆盖，1代表仅同步一次，后面不覆盖，字段顺序：姓名、性别、出生年月、身份证号码、民族、籍贯、出生地、户籍地、职称、手机号、邮箱、办公电话、家庭电话、头像、专业技术职务级别');


2020.4.24
北航

ALTER TABLE `cg_team` ADD COLUMN `fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上级ID' AFTER `id`,
	ADD CONSTRAINT `FK_cg_team_cg_team` FOREIGN KEY (`fid`) REFERENCES `cg_team` (`id`) ON DELETE CASCADE;
-- 更新 cg_team_view

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('label_adminLevelNone', '无行政级别标签', '无行政级别', '1', '57', '无行政级别、聘任制');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cadrePost_vacant', '干部配备一览表显示空岗情况', 'false', 3, 59, '');

-- 如果任职时间精确到月postTimeToDay=false，需要更新一下数据库
-- update cadre_admin_level set s_work_time=DATE_FORMAT(s_work_time,'%y-%m-01');
-- update cadre_admin_level set e_work_time=DATE_FORMAT(e_work_time,'%y-%m-01');
-- update cadre_post set np_work_time=DATE_FORMAT(np_work_time,'%y-%m-01');

ALTER TABLE `sys_role`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT '1' COMMENT '类别，1加权限 2减权限' AFTER `name`;

-- 党员或民主党派变为群众，应删除其所属组织
/*ALTER TABLE `ow_member`
	CHANGE COLUMN `party_id` `party_id` INT(10) UNSIGNED NULL COMMENT '所属分党委' AFTER `user_id`;
update  ow_member om, ow_member_quit omq set om.party_id=null , om.branch_id=null
where om.user_id=omq.user_id and omq.`status`=3;
update  ow_member om, cadre_party cp set om.party_id=null , om.branch_id=null
where om.user_id=cp.user_id and cp.class_id=(select id from base_meta_type where code='mt_dp_qz');*/

ALTER TABLE `unit` ADD COLUMN `not_stat_post` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否不列入配备一览表' AFTER `status`;

-- 更新 unit_view

INSERT INTO `sys_resource` (id,`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (444,0, '聘任制干部信息', '', 'url', '', '/cadre?isEngage=1', 442, '0/1/88/442/', 1, 'statCadreEngage:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (id,`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
VALUES (445,0, '保留待遇干部信息', '', 'url', '', '/cadre?isKeepSalary=1', 442, '0/1/88/442/', 1, 'statCadreKeepSalary:list', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `cadre_inspect`
	CHANGE COLUMN `unit_post_id` `assign_unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '拟任职务' AFTER `record_id`;

-- 更新 cadre_view

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
VALUES (837, 0, '查看内设机构配备统计权限', '', 'function', '', NULL, 836, '0/1/836/', 1, 'unitPostAllocation:module2', NULL, NULL, NULL, 1, NULL);

update sys_role set resource_ids = CONCAT_WS(',', resource_ids, (select id from sys_resource where permission='unitPostAllocation:module2'))
where FIND_IN_SET ((select id from sys_resource where permission='unitPost:allocation'), resource_ids);

-- 更新 utils
-- 清空所有系统缓存（物理）

2020.4.16
北航，西北工大4.17，南航

2020.4.16

-- 更新 SyncService 标准化同步民族名称
-- select distinct nation from sys_user_info where nation !='' or nation is null;
update sys_user_info set nation=null where nation='族';
update sys_user_info set nation='藏族' where nation='藏族（区内）';
update sys_user_info set nation=null where nation='其他族';
update sys_user_info set nation=null where nation='未知族';
update sys_user_info set nation=null where nation='其他';
update sys_user_info set nation=null where nation='未知';

-- 更新utils

UPDATE `base_meta_type` SET `code`='mt_dp_qz' WHERE  `name`='群众' and class_id=(select id from base_meta_class where code='mc_democratic_party');

UPDATE `base_meta_type` SET `name`='毛南族' WHERE  `name`='毛难族';

-- 删除属性login.useCas，login.useSSO 增加数据库系统属性 cas_type（1：支持CAS 2：支持代理接口 3：同时支持CAS和代理接口）、mobile_login_useCas
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('mobile_login_useCas', '手机登录界面是否有CAS', 'true', 3, 55, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cas_type', '系统CAS类型', '1', 2, 56, '1：支持CAS 2：支持代理接口 3：同时支持CAS和代理接口');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('cas_url', '单点登录地址', '/cas', 1, 53, '');

ALTER TABLE `unit`
	DROP INDEX `code`;


2020.4.13
西北工大，南航

2020.4.10

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('default_login_btns', '登录页默认显示按钮', 'false', 3, 52, '');


2020.4.3

-- 修复 转接bug  谨慎操作！（北邮、北师应该已经都更新了）
select m.code, m.realname from ow_member_view m
,(select user_id, to_party_id, to_branch_id from ow_member_transfer where party_id=to_party_id and status=2)tmp
 where m.user_id=tmp.user_id and m.branch_id != tmp.to_branch_id;

update ow_member m
,(select user_id, to_party_id, to_branch_id from ow_member_transfer where party_id=to_party_id and status=2)tmp
set m.branch_id = tmp.to_branch_id
 where m.user_id=tmp.user_id and m.party_id=tmp.to_party_id and m.branch_id != tmp.to_branch_id;
-- bug

-- ！！！！！！！！！！
-- XXXX 西安交大应该去掉此更新（group by id）更新 cadre_view
-- 更新 jx.utils

-- ===========更新文件综合管理（已更新北化工）
ALTER TABLE `dispatch_work_file_auth`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，每个文件按照职务属性设置权限。' AFTER `work_file_id`;

ALTER TABLE `dispatch_work_file`
	CHANGE COLUMN `type` `type` INT UNSIGNED NOT NULL COMMENT '类别' AFTER `remark`;
-- // 干部工作文件类别  1 干部选拔任用  2 干部管理监督  3 机关学院换届  4 干部队伍建设 5 干部考核工作 6  干部教育培训
-- // 党建工作文件 类别 11 专题教育活动 12 基层党组织建设 13 党员队伍建设 14 党内民主建设 15各类工作总结
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`,
                               `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (310, NULL, '工作文件类别', '文件综合管理', '', 'mc_work_file_type', '', '文件大类', 'gb|干部工作文件,dj|党建工作文件,dx|党校工作文件', 2613, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '干部选拔任用', 'mt_work_file_xbry', NULL, 'gb', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '干部管理监督', 'mt_work_file_gljd', NULL, 'gb', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '机关学院换届', 'mt_work_file_xyhj', NULL, 'gb', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '干部队伍建设', 'mt_work_file_dwjs', NULL, 'gb', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '干部考核工作', 'mt_work_file_khgz', NULL, 'gb', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '干部教育培训', 'mt_work_file_jypx', NULL, 'gb', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '专题教育活动', 'mt_work_file_ztjy', NULL, 'dj', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '基层党组织建设', 'mt_work_file_jcdj', NULL, 'dj', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '党员队伍建设', 'mt_work_file_dydw', NULL, 'dj', '', 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '党内民主建设', 'mt_work_file_dnmz', NULL, 'dj', '', 10, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (310, '各类工作总结', 'mt_work_file_gzzj', NULL, 'dj', '', 11, 1);


update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_xbry') where type=1;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_gljd') where type=2;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_xyhj') where type=3;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_dwjs') where type=4;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_khgz') where type=5;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_jypx') where type=6;

update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_ztjy') where type=11;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_jcdj') where type=12;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_dydw') where type=13;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_dnmz') where type=14;
update dispatch_work_file set type = (select id from base_meta_type where code='mt_work_file_gzzj') where type=15;

update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_xbry')) where url='/dispatchWorkFile?type=1';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_gljd')) where url='/dispatchWorkFile?type=2';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_xyhj')) where url='/dispatchWorkFile?type=3';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_dwjs')) where url='/dispatchWorkFile?type=4';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_khgz')) where url='/dispatchWorkFile?type=5';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_jypx')) where url='/dispatchWorkFile?type=6';

update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_ztjy')) where url='/dispatchWorkFile?type=11';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_jcdj')) where url='/dispatchWorkFile?type=12';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_dydw')) where url='/dispatchWorkFile?type=13';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_dnmz')) where url='/dispatchWorkFile?type=14';
update sys_resource set url=concat('/dispatchWorkFile?type=',(select id from base_meta_type where code='mt_work_file_gzzj')) where url='/dispatchWorkFile?type=15';

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1101, 0, '查看文件列表', '给用户赋予的权限', 'function', '', NULL, 61, '0/1/61/', 1, 'userDispatchWorkFile:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1102, 0, '2019年度总结报告', '给用户赋予的权限', 'url', '', '/user/dispatchWorkFile?type=767&valid=f2201f5191c4e92cc5af043eebfd0946', 61, '0/1/61/', 1, 'userDispatchWorkFile:767', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('meta_type_valid_key', '元数据类型校验秘钥', 'u7^&*hJ^*()@', 1, 43, '');

-- ============更新文件综合管理

-- 更新同步设置（已更新北航）
ALTER TABLE `sys_user_info`
	ADD COLUMN `sync` INT UNSIGNED NULL DEFAULT NULL COMMENT '同步字段是否只同步一次，0或空代表每次都同步覆盖，1代表仅同步一次，后面不覆盖，字段顺序：姓名、性别、出生年月、身份证号码、民族、籍贯、出生地、户籍地、职称、手机号、邮箱、办公电话、家庭电话、头像' AFTER `resume`;
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('sync', '同步字段是否只同步一次', '01101111011011', 1, 51,
        '0或空代表每次都同步覆盖，1代表仅同步一次，后面不覆盖，字段顺序：姓名、性别、出生年月、身份证号码、民族、籍贯、出生地、户籍地、职称、手机号、邮箱、办公电话、家庭电话、头像');
-- -

-- 桑文帅
ALTER TABLE `sys_html_fragment` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `remark`;
ALTER TABLE `base_content_tpl` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `update_time`;
ALTER TABLE `sys_scheduler_job` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `create_time`;
ALTER TABLE `sys_attach_file` ADD COLUMN `is_deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否删除' AFTER `create_time`;
--

-- SyncService：参考3.30/4.13/4.14号的git修改日志进行修改

2020.03.10 15:30
新建西北工大

2020.2.28
北师大更新

ALTER TABLE `cadre_position_report`
	ADD COLUMN `code` CHAR(50) NULL DEFAULT NULL COMMENT '工作证号' AFTER `year`,
	ADD COLUMN `unit` VARCHAR(100) NULL DEFAULT NULL COMMENT '所在单位' AFTER `code`,
	CHANGE COLUMN `title` `title` VARCHAR(200) NULL DEFAULT NULL COMMENT '所在单位及职务' AFTER `unit`,
	ADD COLUMN `job` VARCHAR(200) NULL DEFAULT NULL COMMENT '分管工作' AFTER `title`,
	CHANGE COLUMN `content` `content` TEXT NULL COMMENT '述职报告内容' AFTER `job`;

ALTER TABLE `cadre_position_report`
	ADD COLUMN `user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '用户ID' AFTER `cadre_id`;

-- INSERT INTO cadre_position_report (cadre_id, user_id, `year`, `code`, `unit`, title, job, content, create_time)
-- SELECT cadre_id, user_id, CAST(left(nf,4) AS SIGNED) AS year, zgh AS code, ssdwm AS unit, dzzwm AS title, fggz AS job, grsz AS content, cast(czsj as DATE) AS create_time FROM t_zz_bgrjbqk WHERE nf IS NOT NULL and cadre_id IS NOT NULL

/*ALTER TABLE `pmd_member_pay`
	ADD COLUMN `has_check` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否线下补缴登记，用于已结算的月份，由未缴费变更为已缴费' AFTER `charge_branch_id`,
	ADD COLUMN `check_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '线下补缴登记人' AFTER `has_check`,
	ADD COLUMN `check_real_pay` DECIMAL(10,2) UNSIGNED NULL DEFAULT NULL COMMENT '线下补缴登记实缴金额' AFTER `check_user_id`,
	ADD COLUMN `check_date` DATE NULL DEFAULT NULL COMMENT '线下补缴登记日期' AFTER `check_real_pay`,
	ADD COLUMN `check_remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '线下补缴登记备注' AFTER `check_date`;*/
-- 更新 pmd_member_pay_view

update sys_resource set permission='pmdMember:changeDuePay' where permission='pmdMember:setDuePay';

2020.1.15
北师大更新

2020.1.11
北邮
2020.1.9

北航

2020.1.4
北邮、南航

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

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2712, 0, '党支部考核', '', 'url', '', '/member/memberReport', 260, '0/1/260/', 0, 'owReport:menu', NULL, NULL, NULL, 1, 80);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2717, 0, '党支部书记考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'memberReport:base', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2718, 0, '党支部考核：基本信息添加修改', '', 'function', '', NULL, 2712, '0/1/260/2712/', 1, 'partyReport:base', NULL, NULL, NULL, 1, NULL);

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


-- INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2524, 0, '基本情况登记表', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpInfoForm:list', NULL, NULL, NULL, 1, 90);
-- INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2525, 0, '基本情况登记表下载', '', 'function', '', NULL, 2524, '0/1/2574/2524/', 1, 'dpInfoForm:download', NULL, NULL, NULL, 1, NULL);

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

-- 有可能转出后又转入了


ALTER TABLE `leader_unit`
	CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NOT NULL COMMENT '校级领导，可以从干部库提取，也可以从校领导和党委常委中选择' AFTER `id`;

20191204
北邮

-- 更新 jx.utils.jar
-- jodconverter 两个jar包 4.2.2

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (1150, 0, '党员年度考核信息管理', '', 'function',
                                                                                    '', NULL, 260, '0/1/260/', 1, 'partyEva:*', NULL, NULL, NULL, 1, NULL);


ALTER TABLE `ow_party` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;
ALTER TABLE `ow_member` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `profile`;
ALTER TABLE `ow_branch` ADD COLUMN `integrity` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '信息完整度' AFTER `is_deleted`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('owCheckIntegrity', '党建是否验证信息完整度', 'false', 3, 49, '');

-- DELETE FROM `base_meta_type` WHERE  `id`=602;

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

-- 更新 jx.utils.jar

-- 删除 CadreUnderEdu 相关类
-- 删除 cadreTutor 相关类

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
-- 删除 MemberApplyLayoutController
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

