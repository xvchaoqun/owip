


ALTER TABLE `pcs_poll_result`
	ADD COLUMN `is_candidate` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否是候选人' AFTER `user_id`;
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2542, 0, '党代会投票', '', 'url', '', '/pcs/pcsPoll?isDeleted=0', 469, '0/1/469/', 0, 'pcsPoll:*', NULL, NULL, NULL, 1, 1450);
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_jw_num', '纪委委员推荐人数', '11', 2, 70, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_dw_num', '党委委员推荐人数', '30', 2, 69, '');
ALTER TABLE `pcs_poll_result`
	ADD COLUMN `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '投票阶段 1一下阶段 2二下阶段 3三下阶段' AFTER `inspector_id`,
	DROP COLUMN `is_second`;
ALTER TABLE `pcs_poll`
	CHANGE COLUMN `is_second` `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '投票阶段 1一下阶段 2二下阶段 3三下阶段' AFTER `config_id`;


INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_site_bg', '党代会投票登录页面背景', '\\sysProperty\\20200814\\f31daf3b-b563-4055-b49b-708db925e6b0.png', 5, 68, '');

-- 2020.8.14 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2542, 0, '党代会投票', '', 'url', '', '/pcs/pcsPoll', 469, '0/1/469/', 0, 'pcsPoll:*', NULL, NULL, NULL, 1, 1450);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2543, 0, '党代会推荐人', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollCandidate:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2544, 0, '党代会投票人', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollInspector:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2545, 0, '党代会投票结果', '', 'function', '', NULL, 2542, '0/1/469/2542/', 1, 'pcsPollResult:*', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('pcs_poll_site_name', '党代会投票用户端名称', '党代会投票系统', 1, 67, '');

DROP TABLE IF EXISTS `pcs_poll`;
CREATE TABLE IF NOT EXISTS `pcs_poll` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `party_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属二级分党委',
  `branch_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属支部',
  `name` varchar(100) NOT NULL COMMENT '投票名称',
  `config_id` int(10) unsigned NOT NULL COMMENT '党代会',
  `is_second` tinyint(1) unsigned NOT NULL COMMENT '投票阶段 0一下阶段 1二下阶段',
  `has_report` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否报送，二级党委、组织部汇总数据在支部报送后计算',
  `pr_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '代表最大推荐人数，一下阶段填报，二下阶段根据上传的excel自动计算',
  `dw_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '党委委员最大推荐人数，针对一下阶段，二下阶段根据上传的excel自动计算',
  `jw_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '纪委委员最大推荐人数，针对一下阶段，二下阶段根据上传的excel自动计算',
  `inspector_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '参评人数量，根据生成的账号数计算',
  `inspector_finish_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '参评人完成测评数量',
  `positive_finish_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '参评人完成测评的正式党员数量',
  `notice` text COMMENT 'pc端投票说明',
  `mobile_notice` text COMMENT '手机端投票说明',
  `inspector_notice` text COMMENT '账号分发说明',
  `start_time` datetime DEFAULT NULL COMMENT '投票起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '投票截止时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否被删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='党代会投票';

DROP TABLE IF EXISTS `pcs_poll_candidate`;
CREATE TABLE IF NOT EXISTS `pcs_poll_candidate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `poll_id` int(10) unsigned NOT NULL COMMENT '所属投票',
  `user_id` int(10) unsigned NOT NULL COMMENT '候选人',
  `type` tinyint(3) unsigned NOT NULL COMMENT '推荐人类型 1 党代表 2 党委委员 3 纪委委员',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='党代会投票候选人，针对二下阶段';

DROP TABLE IF EXISTS `pcs_poll_inspector`;
CREATE TABLE IF NOT EXISTS `pcs_poll_inspector` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `poll_id` int(10) unsigned NOT NULL COMMENT '所属投票',
  `username` varchar(20) DEFAULT NULL COMMENT '登录账号',
  `passwd` varchar(32) DEFAULT NULL COMMENT '登录密码',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '所属二级党组织',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `is_positive` tinyint(1) unsigned DEFAULT NULL COMMENT '投票人身份 0预备党员 1正式党员',
  `tempdata` longtext COMMENT '临时数据',
  `is_finished` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否完成',
  `is_mobile` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否手机端完成投票，（每次保存、最后提交时更新）',
  `remark` text COMMENT '投票反馈意见',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='党代会投票投票人，即匿名账号';

DROP TABLE IF EXISTS `pcs_poll_result`;
CREATE TABLE IF NOT EXISTS `pcs_poll_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `poll_id` int(10) unsigned NOT NULL COMMENT '所属投票',
  `candidate_user_id` int(10) unsigned DEFAULT NULL COMMENT '候选人，一下留空',
  `inspector_id` int(10) unsigned NOT NULL COMMENT '投票人',
  `is_second` tinyint(1) unsigned NOT NULL COMMENT '投票阶段 0一下阶段 1二下阶段',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '所属二级党组织',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `is_positive` tinyint(1) unsigned DEFAULT NULL COMMENT '投票人身份 0预备党员 1正式党员',
  `status` tinyint(3) unsigned NOT NULL COMMENT '推荐结果 1 同意 2 不同意 3 弃权',
  `type` tinyint(3) unsigned NOT NULL COMMENT '推荐人类型 1 党代表 2 党委委员 3 纪委委员',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '推荐人选，一下时下拉选人，二下（同意时同候选人，不同意时必填另选他人下拉选，弃权时留空）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8 COMMENT='党代会投票结果';

