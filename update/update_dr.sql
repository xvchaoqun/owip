

-- 2020.7.21
ALTER TABLE `dr_online_inspector_log`
	ADD COLUMN `post_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '岗位筛选' AFTER `type_id`;


--2020.5.22
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('drLoginBg', '线上民主推荐登录页北京图片', '\\sysProperty\\20200522\\fdfdefeb-b7a3-4950-bb7c-d114fc979a0e.png', 5, 52, '大小820*363，PNG格式');

--2020.5.21
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
ALTER TABLE `sys_config`
	ADD COLUMN `dr_login_bg` VARCHAR(200) NULL DEFAULT NULL COMMENT '线上民主推荐登录页bg图片，分辨率840*383，PNG格式' AFTER `login_top_bg_color`,
	DROP COLUMN `dr_login_bg`;


-- 2020.5.8
UPDATE `base_meta_class` SET `name`='推荐类型' WHERE  `id`=82;

-- 2020.3.30

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (895, 0, '线上民主推荐', '', 'menu', '', NULL, 890, '0/1/339/890/', 0, 'drOnline:list', 2, NULL, NULL, 1, 700);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2527, 0, '单个岗位推荐', '', 'url', '', '/dr/drOnline', 895, '0/1/339/890/895/', 0, 'drOnline:*', NULL, NULL, NULL, 1, 500);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2528, 0, '推荐职务及资格条件', '', 'function', '', '/dr/drOnlinePost', 2527, '0/1/339/890/895/2527/', 1, 'drOnlinePost:*', NULL, NULL, NULL, 1, NULL);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2529, 0, '推荐结果', '', 'function', '', '/dr/drOnlineResult', 2527, '0/1/339/890/895/2527/', 1, 'drOnlineResult:*', NULL, NULL, NULL, 1, NULL);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2530, 0, '线上推荐参数', '', 'url', '', '/dr/drOnlineParam', 896, '0/1/339/890/896/', 0, 'drOnlineParam:menu', NULL, NULL, NULL, 1, 300);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2531, 0, '推荐人身份类型', '', 'function', '', '/dr/drOnlineInspectorType', 2530, '0/1/339/890/896/2530/', 1, 'drOnlineInspectorType:*', NULL, NULL, NULL, 1, NULL);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2532, 0, '线上民主推荐情况模板', '', 'function', '', '/dr/drOnlineNotice', 2530, '0/1/339/890/896/2530/', 1, 'drOnlineNotice:*', NULL, NULL, NULL, 1, NULL);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2533, 0, '参评人导出记录', '', 'function', '', '/dr/drOnlineInspectorLog', 2527, '0/1/339/890/895/2527/', 1, 'drOnlineInspectorLog:*', NULL, NULL, NULL, 1, NULL);
insert INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2534, 0, '参评人', '', 'function', '', '/dr/drOnlineInspector', 2527, '0/1/339/890/895/2527/', 1, 'drOnlineInspector:*', NULL, NULL, NULL, 1, NULL);
UPDATE sys_resource set url='/metaClass_type_list?cls=mc_dr_type',permission='mc_dr_type:*' WHERE id=897;

DROP TABLE IF EXISTS `dr_online`;
CREATE TABLE IF NOT EXISTS `dr_online` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `record_id` int(10) unsigned DEFAULT NULL COMMENT '所属纪实',
  `year` smallint(5) unsigned DEFAULT NULL COMMENT '年份',
  `recommend_date` date NOT NULL COMMENT '推荐日期',
  `seq` int(10) unsigned NOT NULL COMMENT '编号',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0未发布 1已发布 2已撤回 3已完成',
  `type` int(10) unsigned DEFAULT NULL COMMENT '推荐类型 会议推荐和谈话推荐 关联元数据',
  `chief_member_id` int(10) unsigned DEFAULT NULL COMMENT '推荐组负责人',
  `members` varchar(300) DEFAULT NULL COMMENT '推荐组成员',
  `notice` text COMMENT '干部民主推荐说明',
  `start_time` datetime DEFAULT NULL COMMENT '推荐起始时间',
  `end_time` datetime DEFAULT NULL COMMENT '推荐截止时间',
  `is_deleteed` tinyint(1) unsigned DEFAULT NULL COMMENT '是否被删除',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `recommend_date_seq` (`recommend_date`,`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='按批次管理线上民主推荐';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_candidate
DROP TABLE IF EXISTS `dr_online_candidate`;
CREATE TABLE IF NOT EXISTS `dr_online_candidate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `post_id` int(10) unsigned DEFAULT NULL COMMENT '推荐职务id',
  `user_id` int(10) unsigned NOT NULL COMMENT '候选人user_id',
  `candidate` varchar(50) NOT NULL COMMENT '更改后的候选人姓名',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='线上推荐候选人（管理员添加的候选人）';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_inspector
DROP TABLE IF EXISTS `dr_online_inspector`;
CREATE TABLE IF NOT EXISTS `dr_online_inspector` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `log_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属导出记录',
  `online_id` int(10) unsigned NOT NULL COMMENT '推荐批次',
  `username` varchar(20) DEFAULT NULL COMMENT '登陆账号',
  `passwd` varchar(32) DEFAULT NULL COMMENT '登陆密码',
  `passwd_change_type` tinyint(3) unsigned DEFAULT NULL COMMENT '更改密码方式，1 本人修改 2 系统管理员重置，本人修改之后系统管理员和单位管理员都看不到了， 系统管理员重置之后系统管理员能看到',
  `type_id` int(10) unsigned NOT NULL COMMENT '推荐人身份类型',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `type` int(10) unsigned NOT NULL COMMENT '1 列表生成  2 个别生成',
  `tempdata` longtext COMMENT '临时数据',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态（0可用、1作废、2完成 、3暂存）',
  `is_mobile` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否手机端完成测评，（每次保存、最后提交时更新）',
  `pub_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '分发状态（0未分发 1已分发）',
  `remark` text COMMENT '测评反馈意见',
  `submit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `submit_ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='参评人';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_inspector_log
DROP TABLE IF EXISTS `dr_online_inspector_log`;
CREATE TABLE IF NOT EXISTS `dr_online_inspector_log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `online_id` int(10) unsigned NOT NULL COMMENT '推荐批次',
  `type_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属身份类型',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `total_count` int(10) unsigned NOT NULL COMMENT '总数',
  `finish_count` int(10) unsigned NOT NULL COMMENT '完成测评数目',
  `pub_count` int(10) unsigned NOT NULL COMMENT '已分发数目',
  `create_time` datetime NOT NULL COMMENT '生成时间',
  `export_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '导出次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8 COMMENT='参评人账号生成记录';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_inspector_type
DROP TABLE IF EXISTS `dr_online_inspector_type`;
CREATE TABLE IF NOT EXISTS `dr_online_inspector_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(50) NOT NULL COMMENT '类型名称',
  `sort_order` int(10) unsigned DEFAULT '0' COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态（0可用、1锁定、2作废）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=651 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='参评人身份类型';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_notice
DROP TABLE IF EXISTS `dr_online_notice`;
CREATE TABLE IF NOT EXISTS `dr_online_notice` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '模板名称',
  `content` text COMMENT '模板内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='线上民主推荐说明模板';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_post
DROP TABLE IF EXISTS `dr_online_post`;
CREATE TABLE IF NOT EXISTS `dr_online_post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '序号',
  `unit_post_id` int(11) NOT NULL COMMENT '推荐职务，关联岗位ID',
  `online_id` int(10) unsigned NOT NULL COMMENT '所属批次',
  `has_candidate` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否有候选人',
  `candidates` varchar(500) DEFAULT NULL COMMENT '候选人user_id，逗号分割',
  `has_competitive` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否差额',
  `competitive_num` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '最多推荐人数',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='推荐职务';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dr_online_result
DROP TABLE IF EXISTS `dr_online_result`;
CREATE TABLE IF NOT EXISTS `dr_online_result` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `online_id` int(10) unsigned NOT NULL COMMENT '批次id',
  `post_id` int(10) unsigned NOT NULL COMMENT '推荐职务id',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '候选人user_id（仅管理员添加的有）',
  `candidate` varchar(200) NOT NULL COMMENT '候选人姓名',
  `inspector_id` int(10) unsigned NOT NULL COMMENT '参评人id',
  `inspector_type_id` int(10) unsigned NOT NULL COMMENT '参评人身份id',
  `is_agree` tinyint(1) unsigned DEFAULT NULL COMMENT '推荐意见',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8 COMMENT='线上民主推荐结果';

-- 新建视图 dr_online_post_view