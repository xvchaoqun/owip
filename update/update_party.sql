
-- 20191023 李阳 资源文件
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2500, 0, '党内奖惩情况', '', 'menu', '', NULL, 260, '0/1/260/', 0, 'RePu:function', NULL, NULL, NULL, 1, 99);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2501, 0, '查看党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2502, 0, '编辑党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2503, 0, '查看党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2504, 0, '编辑党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2505, 0, '党内任职情况', '', 'url', '', '/party/partyPost_menu', 260, '0/1/260/', 0, 'partyPost:menu', NULL, NULL, NULL, 1, 90);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2506, 0, '编辑党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:edit', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2507, 0, '查看党内任职', '', 'function', '', NULL, 2505, '0/1/260/2505/', 1, 'partyPost:list', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2508, 0, '分党委奖惩', '', 'url', '', '/party/partyRePu_page?type=1', 2500, '0/1/260/2500/', 1, 'partyRePu:menu', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2509, 0, '党支部奖惩', '', 'url', '', '/party/partyRePu_page?type=2', 2500, '0/1/260/2500/', 1, 'branchRePu:menu', NULL, NULL, NULL, 1, NULL);
REPLACE INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2510, 0, '个人奖惩', '', 'url', '', '/party/partyRePu_page?type=3', 2500, '0/1/260/2500/', 1, 'memberRePu:menu', NULL, NULL, NULL, 1, NULL);


-- 201910 李阳 党内奖惩、党内任职 三张视图创建

-- 10.15 李阳

replace INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2500, 0, '党内奖惩、党内任职', '', 'function', '', NULL, 260, '0/1/260/', 0, 'party:function', NULL, NULL, NULL, 1, NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党内惩罚信息';

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

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2500, 0, '党内奖惩、党内任职', '', 'function', '', NULL, 260, '0/1/260/', 0, 'party:function', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2501, 0, '查看党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2502, 0, '编辑党内奖励', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyReward:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2503, 0, '查看党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2504, 0, '编辑党内惩罚', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPunish:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2505, 0, '查看党内任职', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPost:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2506, 0, '编辑党内任职', '', 'function', '', NULL, 2500, '0/1/260/2500/', 1, 'partyPost:edit', NULL, NULL, NULL, 1, NULL);



