

-- 2020.12.24 ly
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2566, 0, '二级党委数据统计', '组织部,分党委', 'url', '', '/stat/partySum', 260, '0/1/260/', 1, 'stat:partySum', NULL, NULL, NULL, 1, 60);

-- 2020.12.23 ly 只给大连理工修改
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2565, 0, '组织机构数据统计', '组织部', 'url', '', '/stat/owSum', 260, '0/1/260/', 1, 'stat:owSum', NULL, NULL, NULL, 1, 70);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (19, '党工委', 'mt_party_work', NULL, '', '', 113, 1);
UPDATE `base_meta_type` SET `name`='二级党委' WHERE  `id`=55;
UPDATE `base_meta_type` SET `name`='二级党总支' WHERE  `id`=56;
ALTER TABLE `ow_party`
	ALTER `class_id` DROP DEFAULT;
ALTER TABLE `ow_party`
	CHANGE COLUMN `class_id` `class_id` INT(10) UNSIGNED NOT NULL COMMENT '党总支类别，关联元数据，党工委、分党委、党总支、直属党支部' AFTER `unit_id`;
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (68, 23, '研究生导师纵向党支部', 'mt_graduate_teacher', NULL, '', '', 122, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (69, 23, '硕博研究生党支部', 'mt_sb_graduate', NULL, '', '', 123, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (70, 23, '博士生党支部', 'mt_bs_graduate', NULL, '', '', 124, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (71, 23, '硕士生党支部', 'mt_ss_graduate', NULL, '', '', 125, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (72, 23, '本科生辅导员纵向党支部', 'mt_undergraduate_assistant', NULL, '', '', 126, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (73, 23, '离退休党支部', 'mt_retire', NULL, '', '', 127, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (74, 23, '专任教师党支部', 'mt_professional_teacher', NULL, '', '', 128, 1);
REPLACE INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (129, 23, '机关行政产业后勤教工党支部', 'mt_support_teacher', NULL, '', '', 129, 1);

-- 20191231 ly 党员发展流程时间点限制提醒开关
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`) VALUES ('memberApply_timeLimit', '党员发展流程', 'false', 3, 49, '党员发展流程时间点限制提醒:是否显示');

-- 20191129 ly

REPLACE INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('odAdmin', '党建工作管理员', '1042,1043,1044,312,260,106,183,184,185,1021,181,186,187,188,189,190,191,192,202,2520,1001,182,1063,1064,392,193,194,195,200,201,203,196,197,198,199,204,205,206,1000,2500,2501,2502,2503,2504,2508,2509,2510,2505,2506,2507,1150,105,107,1026,1065,299,300,376,220,1072,1035,1036,1037,1038,1039,211,1027,213,214,1018,1019,1020,252,910,911,1181,253,254,251,246,249,250,291,247,296,440,290,1028,1029,1030,1031,1004,2700,2705,2701,2702,2703,2704,2706,2707,2772,2774,2775,2776,2777,2778,2780,61,435,436,437,550,553,557,558,559,1100,554,556,552,67,72,112,113,114,116,117,118,233,21,22,853,1034,1040,414,3032,298,294,297,75,416,207,208,209', '-1', 13, NULL, 0, 4, '');
REPLACE INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('partyAdmin', '分党委管理员', '108,3033,260,106,183,181,186,187,188,189,190,191,192,202,2520,182,1063,193,194,195,200,201,203,196,197,198,199,204,205,206,2500,2501,2502,2503,2504,2508,2509,2510,2505,2506,2507,240,105,107,1026,299,300,220,1072,211,1027,214,252,910,911,1181,253,254,251,246,249,250,291,247,296,440,290,1028,1029,1031,2772,2774,2775,2776,2777,2778,2779,2780,384,869,880,881,416,207,208,209', '-1', 188, NULL, 1, 11, '');

REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2520, 0, '党员信息采集表权限', '', 'function', '', NULL, 181, '0/1/260/181/', 1, 'memberInfoForm:*', NULL, NULL, NULL, 1, NULL);

-- 20191023 李阳 资源文件
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2500, 0, '党内奖惩情况', '', 'menu', '', NULL, 260, '0/1/260/', 0, 'RePu:function', NULL, NULL, NULL, 1, 99);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2501, 0, '查看党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2502, 0, '编辑党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2503, 0, '查看党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2504, 0, '编辑党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2505, 0, '党内任职情况', '', 'url', '', '/party/partyPostList_page', 260, '0/1/260/', 0, 'partyPost:menu', NULL, NULL, NULL, 1, 90);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2506, 0, '编辑党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2507, 0, '查看党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2508, 0, '分党委奖惩', '', 'url', '', '/party/partyRePu_page?type=1', 2500, '0/1/260/2500/', 1, 'partyRePu:menu', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2509, 0, '党支部奖惩', '', 'url', '', '/party/partyRePu_page?type=2', 2500, '0/1/260/2500/', 1, 'branchRePu:menu', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2510, 0, '个人奖惩', '', 'url', '', '/party/partyRePu_page?type=3', 2500, '0/1/260/2500/', 1, 'memberRePu:menu', NULL, NULL, NULL, 1, NULL);


-- 201910 李阳 党内奖惩、党内任职 三张视图创建

-- 10.15 李阳 党内奖惩、党内任职 三张表

DROP TABLE IF EXISTS `ow_party_post`;
CREATE TABLE IF NOT EXISTS `ow_party_post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '所属党员',
  `start_date` date DEFAULT NULL COMMENT '开始日期',
  `end_date` date DEFAULT NULL COMMENT '结束日期',
  `detail` varchar(100) DEFAULT NULL COMMENT '工作单位及担任职务',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党内任职经历';

DROP TABLE IF EXISTS `ow_party_punish`;
CREATE TABLE IF NOT EXISTS `ow_party_punish` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1 分党委 2 党支部 3 个人',
  `party_id` int(10) unsigned DEFAULT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `punish_time` date DEFAULT NULL COMMENT '处分日期',
  `end_time` date DEFAULT NULL COMMENT '处分期限',
  `name` varchar(200) DEFAULT NULL COMMENT '受何种处分',
  `unit` varchar(300) DEFAULT NULL COMMENT '处分单位',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党内处分信息';

DROP TABLE IF EXISTS `ow_party_reward`;
CREATE TABLE IF NOT EXISTS `ow_party_reward` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1 分党委 2 党支部 3 个人',
  `party_id` int(10) unsigned DEFAULT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  `reward_time` date DEFAULT NULL COMMENT '获奖日期',
  `reward_type` int(10) unsigned DEFAULT NULL COMMENT '获奖类型',
  `name` varchar(200) DEFAULT NULL COMMENT '获得奖项',
  `unit` varchar(300) DEFAULT NULL COMMENT '颁奖单位',
  `proof` varchar(255) DEFAULT NULL COMMENT '获奖证书',
  `proof_filename` varchar(255) DEFAULT NULL COMMENT '获奖证书文件名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='党内奖励信息';

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2596, NULL, '分党委获奖类型', '组织机构管理', '基层党组织', 'mt_party_reward', '', '', '', 2601, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2597, NULL, '党支部获奖类型', '组织机构管理', '党支部管理', 'mt_branch_reward', '', '', '', 2602, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '校级先进党组织', 'mt_qmcq98', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '省部级先进党组织', 'mt_uttewz', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2596, '国家级先进党组织', 'mt_klwpii', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '校级先进党支部', 'mt_ie6tsu', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '省部级先进党支部', 'mt_idzxhq', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '国家级先进党支部', 'mt_cko3mg', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '校级红色“1+1”', 'mt_37xxui', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2597, '省部级红色“1+1”', 'mt_krnrhg', NULL, '', '', 5, 1);

