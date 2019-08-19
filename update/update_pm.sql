2019-08-15
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2772, 0, '三会一课管理', '', 'menu', 'fa fa-pencil-square-o', NULL, 1, '0/1/', 0, 'pm:menu', NULL, NULL, NULL, 1, 4500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2774, 0, '支部党员大会', '', 'url', '', '/pmMeeting?type=1', 2772, '0/1/2772/', 1, 'pmMeeting:list:1', NULL, NULL, NULL, 1, 400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2775, 0, '支部委员会', '', 'url', '', '/pmMeeting?type=2', 2772, '0/1/2772/', 1, 'pmMeeting:list:2', NULL, NULL, NULL, 1, 300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2776, 0, '党小组会', '', 'url', '', '/pmMeeting?type=3', 2772, '0/1/2772/', 1, 'pmMeeting:list:3', NULL, NULL, NULL, 1, 200);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2777, 0, '党课学习', '', 'url', '', '/pmMeeting?type=4', 2772, '0/1/2772/', 1, 'pmMeeting:list:4', NULL, NULL, NULL, 1, 100);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2778, 0, '三会一课:更新', '', 'function', '', NULL, 2772, '0/1/2772/', 1, 'pmMeeting:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2779, 0, '三会一课:审批', '', 'function', '', NULL, 2772, '0/1/2772/', 1, 'pmMeeting:approve', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2780, 0, '三会一课:列表', '', 'function', '', NULL, 2772, '0/1/2772/', 1, 'pmMeeting:list', NULL, NULL, NULL, 1, NULL);

DROP TABLE IF EXISTS `pm_exclude_branch`;
CREATE TABLE IF NOT EXISTS `pm_exclude_branch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '分党委id',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '支部id',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '添加人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='不召开党员大会支部';

DROP TABLE IF EXISTS `pm_meeting`;
CREATE TABLE IF NOT EXISTS `pm_meeting` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '分党委id',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '支部id',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '会议类型 1 支部党员大会 2 支部委员会 3 党小组会  4 党课',
  `date` datetime DEFAULT NULL COMMENT '会议时间',
  `year` int(10) unsigned DEFAULT NULL COMMENT '年份',
  `quarter` tinyint(3) unsigned DEFAULT NULL COMMENT '季度',
  `name` varchar(200) DEFAULT NULL COMMENT '会议名称',
  `issue` varchar(200) DEFAULT NULL COMMENT '议题',
  `address` varchar(100) DEFAULT NULL COMMENT '地点',
  `presenter` int(10) unsigned DEFAULT NULL COMMENT '主持人',
  `recorder` int(10) unsigned DEFAULT NULL COMMENT '记录人',
  `attends` text COMMENT '参会人员（实到人员，列席人员）',
  `absents` text COMMENT '请假人员（缺席人员）',
  `Invitee` varchar(200) DEFAULT NULL COMMENT '列席人员，输入文本',
  `due_num` int(10) unsigned DEFAULT NULL COMMENT '应到人数',
  `attend_num` int(10) unsigned DEFAULT NULL COMMENT '实到人数',
  `absent_num` int(10) unsigned DEFAULT NULL COMMENT '请假人数',
  `content` text COMMENT '会议内容',
  `decision` text COMMENT '会议决议',
  `is_public` tinyint(1) unsigned DEFAULT '0' COMMENT '是否公开',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态， 0 未审核 1 审核通过 2 审核未通过',
  `is_back` tinyint(1) unsigned DEFAULT NULL COMMENT '是否退回',
  `reason` varchar(200) DEFAULT NULL COMMENT '退回原因',
  `is_delete` tinyint(1) unsigned DEFAULT NULL COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='三会一课(支部会议)';

DROP TABLE IF EXISTS `pm_meeting_file`;
CREATE TABLE IF NOT EXISTS `pm_meeting_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `meeting_id` int(10) unsigned DEFAULT NULL COMMENT '会议id',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `file_path` varchar(200) DEFAULT NULL COMMENT '文件地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='三会一课文件';

DROP TABLE IF EXISTS `pm_quarter`;
CREATE TABLE IF NOT EXISTS `pm_quarter` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `year` int(10) unsigned DEFAULT NULL COMMENT '年度',
  `quarter` tinyint(3) unsigned DEFAULT NULL COMMENT '季度',
  `is_finish` tinyint(1) unsigned DEFAULT NULL COMMENT '季度是否结束',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '类型 1 分党委 2 支部',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '分党委id',
  `num` int(10) unsigned DEFAULT NULL COMMENT '数量(分党委或支部数量)',
  `due_num` int(10) unsigned DEFAULT NULL COMMENT '应召开党员大会支部数量',
  `finish_num` int(10) unsigned DEFAULT NULL COMMENT '已召开党员大会数量',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='三会一课季度汇总';

DROP TABLE IF EXISTS `pm_quarter_branch`;
CREATE TABLE IF NOT EXISTS `pm_quarter_branch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `quarter_id` int(10) unsigned DEFAULT NULL COMMENT '季度汇总id',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '分党委id',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '支部id',
  `branch_name` varchar(50) DEFAULT NULL COMMENT '支部名称',
  `is_exclude` tinyint(1) unsigned DEFAULT NULL COMMENT '是否在排除召开会议的列表',
  `meeting_num` int(10) unsigned DEFAULT NULL COMMENT '会议次数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='三会一课支部季度汇总详情';

DROP TABLE IF EXISTS `pm_quarter_party`;
CREATE TABLE IF NOT EXISTS `pm_quarter_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `quarter_id` int(10) unsigned DEFAULT NULL COMMENT '季度汇总id',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '分党委id',
  `party_name` varchar(50) DEFAULT NULL COMMENT '分党委名称',
  `branch_num` int(10) unsigned DEFAULT NULL COMMENT '召开会议支部数量',
  `exculde_branch_num` int(10) unsigned DEFAULT NULL COMMENT '不召开会议支部数量',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='三会一课分党委季度汇总详情';

