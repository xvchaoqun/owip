

20190802

更新common-utils

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('pdfResolution', 'PDF图像的解析度', '300', 2, 39, '');

删除 swfTools.command 和 swfTools.languagedir
运行 /test/dispatch.jsp

20190801

ALTER TABLE `ow_member`
	CHANGE COLUMN `transfer_time` `transfer_time` DATE NULL DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示' AFTER `add_type`;

CREATE TABLE `ow_member_check` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '当前所属分党委，用于权限分配',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '当前所属党支部，直属党支部没有这一项',
	`native_place` VARCHAR(100) NULL DEFAULT NULL COMMENT '籍贯',
	`mobile` VARCHAR(100) NULL DEFAULT NULL COMMENT '手机号',
	`phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '办公电话',
	`email` VARCHAR(100) NULL DEFAULT NULL COMMENT '邮箱',
	`political_status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
	`transfer_time` DATE NULL DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
	`apply_time` DATE NULL DEFAULT NULL COMMENT '提交书面申请书时间，时间从入党申请同步过来',
	`active_time` DATE NULL DEFAULT NULL COMMENT '确定为入党积极分子时间',
	`candidate_time` DATE NULL DEFAULT NULL COMMENT '确定为发展对象时间',
	`sponsor` VARCHAR(50) NULL DEFAULT NULL COMMENT '入党介绍人',
	`grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间',
	`grow_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '入党时所在党支部',
	`positive_time` DATE NULL DEFAULT NULL COMMENT '转正时间',
	`positive_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '转正时所在党支部',
	`party_post` VARCHAR(100) NULL DEFAULT NULL COMMENT '党内职务',
	`party_reward` VARCHAR(255) NULL DEFAULT NULL COMMENT '党内奖励',
	`other_reward` VARCHAR(255) NULL DEFAULT NULL COMMENT '其他奖励',
	`original_json` TEXT NULL COMMENT '变更记录对应的原来的值，JSON对象格式',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '申请人IP',
	`status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1审批通过',
	`reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '返回修改原因',
	PRIMARY KEY (`id`)
)
COMMENT='党员信息修改申请'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=5
;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1072, 0, '党员信息修改审批', '', 'url', '', '/member/memberCheck?cls=2', 105, '0/1/105/', 1, 'memberCheck:*', NULL, NULL, NULL, 1, 29800);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1073, 0, '党员信息修改申请', '', 'url', '', '/member/memberCheck?cls=1', 258, '0/1/258/', 1, 'memberCheck:list', NULL, NULL, NULL, 1, 980);

-- 党员信息修改申请/审批 设置角色权限

20190730
南航

20190730

-- 弃用 cet_project的 status 和 pub_status字段
ALTER TABLE `cet_project`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL DEFAULT '1' COMMENT '状态（弃用），0 未启动、 1 正在进行、 2 已结束' AFTER `remark`,
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `status`;
update cet_project SET STATUS = 1;
update cet_project SET pub_status = 1;

-- 弃用 cet_train的 pub_status字段
ALTER TABLE `cet_train`
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `enroll_status`;
update cet_train SET pub_status = 1;

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`)
VALUES ('培训数据校正', '包含：自动修改培训班的结课状态', 'job.cet.CetAutoAdjust', '0 0 0/1 * * ?', 1, 0, 25, '2019-07-29 16:47:26');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('scSubsidyLogo', '津贴调整文件LOGO', '', 5, 38, '大小628*205，PNG格式');

删除 OaTaskMsgService.java

20190728
哈工大
20190728

更新common-utils

-- 调整岗位排序为正序
UPDATE unit_post SET sort_order=code;

20190727
北邮、哈工大、北航

20190727

删除 service.base.ShortMsgService
删除 service.auth

改 login.service / xss.ignoreUrIs

20190726
更新 南航、西交大、 北化工