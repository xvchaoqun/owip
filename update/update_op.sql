
-- 2019.11.13 ly 组织处理管理 三张表

DROP TABLE IF EXISTS `op_attatch`;
CREATE TABLE IF NOT EXISTS `op_attatch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `record_id` int(10) unsigned NOT NULL COMMENT '所属组织处理',
  `file_name` varchar(200) DEFAULT NULL COMMENT '附件名称',
  `file_path` varchar(200) DEFAULT NULL COMMENT '附件word或pdf格式',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='组织处理管理-组织处理附件';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.op_record
DROP TABLE IF EXISTS `op_record`;
CREATE TABLE IF NOT EXISTS `op_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_date` date NOT NULL COMMENT '执行日期',
  `user_id` int(10) unsigned NOT NULL COMMENT '处理对象',
  `admin_level` int(10) unsigned DEFAULT NULL COMMENT '对象级别，即时任行政级别',
  `post` varchar(200) DEFAULT NULL COMMENT '时任职务',
  `type` int(10) unsigned NOT NULL COMMENT '组织处理方式 1提醒 2函询 3、诫勉',
  `way` int(10) unsigned NOT NULL COMMENT '开展方式 1书面方式 2谈话方式 3既采用书面方式也采用谈话方式',
  `talk_type` int(10) unsigned NOT NULL COMMENT '谈话人类型 1党委主要负责人 2组织部门主要负责人 3其他人',
  `talk_user_id` int(10) unsigned NOT NULL COMMENT '具体谈话人',
  `issue` int(10) unsigned NOT NULL COMMENT '针对问题，从“参数设置”中选择，提醒、函询、诫勉这三种组织处理方式各有一套，如果选择“其他”，需要输入具体信息',
  `issue_other` varchar(200) DEFAULT NULL COMMENT '其他针对问题',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='组织处理管理-组织处理，organizational process';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.op_report
DROP TABLE IF EXISTS `op_report`;
CREATE TABLE IF NOT EXISTS `op_report` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `report_date` date NOT NULL COMMENT '报送日期',
  `unit` varchar(200) NOT NULL COMMENT '报送上级单位',
  `start_date` date NOT NULL COMMENT '数据统计时间区间开始时间',
  `end_date` date NOT NULL COMMENT '数据统计时间区间截止时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='组织处理管理-报送上级部门';

-- sys_resource

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2511, 0, '组织处理管理', '', 'menu', 'fa fa-balance-scale', NULL, 1, '0/1/', 0, 'op:menu', NULL, NULL, NULL, 1, 5400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2512, 0, '组织处理', '', 'url', '', '/op/opRecord', 2511, '0/1/2511/', 0, 'opRecord:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2513, 0, '报送上级部门', '', 'url', '', '/op/opReport', 2511, '0/1/2511/', 0, 'opReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2514, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_op_way,mc_op_talktype,mc_op_type_remind,mc_op_type_ask,mc_op_type_encourage', 2511, '0/1/2511/', 0, 'mc_op_way,mc_op_talktype,mc_op_type_remind,mc_op_type_ask,mc_op_type_encourage:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2515, 0, '编辑', '', 'function', '', NULL, 2512, '0/1/2511/2512/', 1, 'opRecord:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2516, 0, '编辑', '', 'function', '', NULL, 2513, '0/1/2511/2513/', 1, 'opReport:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2517, 0, '编辑', '', 'function', '', NULL, 2514, '0/1/2511/2514/', 1, 'opParameter:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2518, 0, '组织处理附件查看', '', 'function', '', NULL, 2511, '0/1/2511/', 1, 'opAttatch:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2519, 0, '组织处理附件编辑', '', 'function', '', NULL, 2511, '0/1/2511/', 1, 'opAttatch:edit', NULL, NULL, NULL, 1, NULL);

-- sys_role
-- INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('op_admin', '组织处理管理员', '', '-1', NULL, 0, 0, 61, '');

-- base_meta_class

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2598, NULL, '组织处理方式', '组织处理管理', '组织处理', 'mc_op_type', '', '', '', 2605, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2599, NULL, '开展方式', '组织处理管理', '组织处理', 'mc_op_way', '', '', '', 2606, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2600, NULL, '谈话人类型', '组织处理管理', '组织处理', 'mc_op_talktype', '', '', '', 2607, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2602, NULL, '针对问题-提醒', '组织处理管理', '组织处理', 'mc_op_type_remind', '', '', '', 2608, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2603, NULL, '针对问题-函询', '组织处理管理', '组织处理', 'mc_op_type_ask', '', '', '', 2609, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2604, NULL, '针对问题-诫勉', '组织处理管理', '组织处理', 'mc_op_type_encourage', '', '', '', 2610, 1);

-- base_meta_type

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2598, '提醒', 'mt_op_remind', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2598, '函询', 'mt_op_ask', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2598, '诫勉', 'mt_op_encourage', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2599, '书面方式', 'mt_yojljj', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2599, '谈话方式', 'mt_etzl4z', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2599, '即采用书面方式也采用谈话方式', 'mt_phoug4', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2600, '党委主要负责人', 'mt_nkvilc', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2600, '组织部门主要负责人', 'mt_xb0kxj', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2600, '其他人', 'mt_3gbdj8', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2602, '提醒', 'mt_yhph3x', NULL, '', '暂时空着，先使用提醒', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '政治思想方面', 'mt_8siao6', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '履行职责方面', 'mt_nilm5g', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '工作作风方面', 'mt_es8nww', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '道德品质方面', 'mt_6hxzka', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '廉政勤政方面', 'mt_a0nvh6', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '组织纪律方面', 'mt_ag0rkm', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2603, '其他方面', 'mt_lr5fhp', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '遵守党的政治纪律、组织纪律不够严格的', 'mt_syilb9', NULL, NULL, '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '执行民主集中制不够严格的，个人决定应由集体决策事项或者在领导班子中闹无原则纠纷', 'mt_udjp0m', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '执行《党政领导干部选拔任用工作条例》不够严格，用人失察失误的', 'mt_qimnbk', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '法制观念淡薄，不依法履行职责或者妨碍他人依法履行职责的', 'mt_obtk3h', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '违反规定干预市场经济活动的', 'mt_jc0yjh', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '不认真落实中央八项规定精神和厉行节约反对浪费规定的', 'mt_ia7uzd', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '脱离实际、弄虚作假、损害群众利益和党群干群关系的', 'mt_pnfgvs', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '无正当理由不按时报告、不如实报告个人有关事项的', 'mt_xdzynw', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '执行廉洁自律规定不严格的', 'mt_wwuhhc', NULL, '', '', 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '纪律松弛、监管不力，对身边工作人员发生严重违纪违法行为负有责任的', 'mt_oalssc', NULL, '', '', 10, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '在巡视、经济责任审计中发现有违规行为的', 'mt_isfyas', NULL, '', '', 11, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '从事有悖于社会公德、职业道德、家庭美德活动的', 'mt_wqjfxv', NULL, '', '', 12, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2604, '其他需要进行诫勉的问题', 'mt_ptmkio', NULL, '', '', 13, 1);
