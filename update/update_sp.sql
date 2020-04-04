-- 2019-10-22

-- 添加源数据类别 桑文帅
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (3000, NULL, '人大代表和政协委员类型', '干部其他信息', '八类代表信息', 'mc_sp_npc_type', '', '', '', 2601, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (3001, NULL, '教代会执委会职务', '干部其他信息', '八大代表类型', 'mc_sp_teach', '', '', '', 2602, 1);

-- 添加源数据 桑文帅
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3000, '全国人大代表', 'mt_ojrkge', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3000, '全国政协委员', 'mt_fuycth', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3000, '北京市人大代表', 'mt_s1ekoy', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3000, '北京市政协委员', 'mt_xqxw5u', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3001, '主任', 'mt_4y6m8p', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3001, '副主任', 'mt_bek2ea', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (3001, '委员', 'mt_u59vlh', NULL, '', '', 3, 1);

-- 添加二级数据 桑文帅
INSERT INTO `base_layer_type` (`id`, `fid`, `code`, `name`, `first_level`, `second_level`, `num`, `sort_order`, `remark`) VALUES (3000, NULL, 'lt_spDp', '民主党派机构目录', '民主党派机构', '民主党派机构职务', 1, 14, '');
INSERT INTO `base_layer_type` (`id`, `fid`, `code`, `name`, `first_level`, `second_level`, `num`, `sort_order`, `remark`) VALUES (3001, 3000, '01', '民革支部', NULL, NULL, 3, 1, '');
INSERT INTO `base_layer_type` (`id`, `fid`, `code`, `name`, `first_level`, `second_level`, `num`, `sort_order`, `remark`) VALUES (3002, 3001, '0101', '主委', NULL, NULL, 0, 1, '');
INSERT INTO `base_layer_type` (`id`, `fid`, `code`, `name`, `first_level`, `second_level`, `num`, `sort_order`, `remark`) VALUES (3003, 3001, '0102', '副主委', NULL, NULL, 0, 2, '');
INSERT INTO `base_layer_type` (`id`, `fid`, `code`, `name`, `first_level`, `second_level`, `num`, `sort_order`, `remark`) VALUES (3004, 3001, '0103', '委员', NULL, NULL, 0, 3, '');

-- 添加资源数据 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3009, 0, '八类代表信息', '', 'menu', '', NULL, 314, '0/1/314/', 0, 'sp:menu', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3010, 0, '人大代表和政协委员', '', 'url', '', '/sp/spNpc', 3009, '0/1/314/3009/', 1, 'spNpc:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3011, 0, '高层次人才', '', 'url', '', '/sp/spTalent', 3009, '0/1/314/3009/', 1, 'spTalent:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3012, 0, '学术委员会委员', '', 'url', '', '/sp/spCg?type=1', 3009, '0/1/314/3009/', 1, 'spCg:type1', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3014, 0, '学位委员会委员', '', 'url', '', '/sp/spCg?type=2', 3009, '0/1/314/3009/', 1, 'spCg:type2', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3015, 0, '校务委员会委员', '', 'url', '', '/sp/spCg?type=3', 3009, '0/1/314/3009/', 1, 'spCg:type3', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3016, 0, '民主党派主委', '', 'url', '', '/sp/spDp', 3009, '0/1/314/3009/', 1, 'spDp:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3017, 0, '教代会执委会', '', 'url', '', '/sp/spTeach', 3009, '0/1/314/3009/', 1, 'spTeach:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3018, 0, '离退休教师代表', '', 'url', '', '/sp/spRetire', 3009, '0/1/314/3009/', 1, 'spRetire:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3029, 0, '八类代表操作', '', 'function', '', NULL, 3009, '0/1/314/3009/', 1, 'sp:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3030, 0, '八类代表查看', '', 'function', '', NULL, 3009, '0/1/314/3009/', 1, 'sp:list', NULL, NULL, NULL, 1, NULL);

-- 建表语句

-- 导出  表 db_owip_1.sp_cg 结构
CREATE TABLE IF NOT EXISTS `sp_cg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cg_team_id` int(10) unsigned DEFAULT NULL COMMENT '关联委员会和领导小组ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别 1.学术委员会 2.学位委员会 3.校务委员会',
  `post` varchar(200) DEFAULT NULL COMMENT '职务',
  `seat` varchar(200) DEFAULT NULL COMMENT '席位',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所在单位',
  `arrive_date` date DEFAULT NULL COMMENT '到校日期',
  `politics_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌',
  `pro_post` varchar(200) DEFAULT NULL COMMENT '专业技术职务',
  `manage_level` varchar(200) DEFAULT NULL COMMENT '管理岗位等级',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否领导干部',
  `admin_post` varchar(200) DEFAULT NULL COMMENT '所担任行政职务',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='委员会委员';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.sp_dp 结构
CREATE TABLE IF NOT EXISTS `sp_dp` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dp` int(10) unsigned DEFAULT NULL COMMENT '民主党派机构，两层级类型',
  `dp_post` int(10) unsigned DEFAULT NULL COMMENT '职务，两层级类型',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所在单位',
  `pro_post` varchar(200) DEFAULT NULL COMMENT '专业技术职务',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否领导干部',
  `admin_post` varchar(50) DEFAULT NULL COMMENT '所担任行政职务',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='民主党派主委';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.sp_npc 结构
CREATE TABLE IF NOT EXISTS `sp_npc` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(10) unsigned NOT NULL COMMENT '类别',
  `th` varchar(20) DEFAULT NULL COMMENT '届次',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `politics_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌',
  `npc_post` varchar(200) DEFAULT NULL COMMENT '人大/政协职务',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所在单位',
  `elected_post` varchar(200) DEFAULT NULL COMMENT '当选时职务',
  `post` varchar(200) DEFAULT NULL COMMENT '现任职务/离任时职务',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否现任领导干部',
  `is_history` tinyint(1) unsigned DEFAULT NULL COMMENT '是否离任',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='人大代表和政协委员';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.sp_retire 结构
CREATE TABLE IF NOT EXISTS `sp_retire` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所在单位',
  `politics_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌',
  `staff_status` varchar(50) DEFAULT NULL COMMENT '人员状态',
  `on_job` varchar(50) DEFAULT NULL COMMENT '在岗情况',
  `post_class` varchar(50) DEFAULT NULL COMMENT '岗位类别',
  `pro_post` varchar(200) DEFAULT NULL COMMENT '专业技术职务',
  `manage_level` varchar(50) DEFAULT NULL COMMENT '管理岗位等级',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否离任领导干部',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='离退休教师代表';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.sp_talent 结构
CREATE TABLE IF NOT EXISTS `sp_talent` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `unit_id` int(11) unsigned DEFAULT NULL COMMENT '所在单位',
  `country` varchar(50) DEFAULT NULL COMMENT '国家/地区',
  `arrive_date` date DEFAULT NULL COMMENT '到校时间',
  `authorized_type` varchar(50) DEFAULT NULL COMMENT '编制类别',
  `staff_type` varchar(50) DEFAULT NULL COMMENT '人员分类',
  `politics_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌',
  `pro_post` varchar(200) DEFAULT NULL COMMENT '专业技术职务',
  `pro_post_level` varchar(50) DEFAULT NULL COMMENT '专技岗位等级',
  `first_subject` varchar(50) DEFAULT NULL COMMENT '一级学科',
  `talent_title` varchar(200) DEFAULT NULL COMMENT '人才/荣誉称号',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否领导干部',
  `admin_post` varchar(200) DEFAULT NULL COMMENT '所担任行政职务',
  `award_date` date DEFAULT NULL COMMENT '授予日期',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='高层次人才';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.sp_teach 结构
CREATE TABLE IF NOT EXISTS `sp_teach` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `th` varchar(20) DEFAULT NULL COMMENT '界数',
  `post` varchar(200) DEFAULT NULL COMMENT '职务',
  `user_id` int(10) unsigned NOT NULL COMMENT '姓名',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所在单位',
  `pro_post` varchar(200) DEFAULT NULL COMMENT '专业技术职务',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否领导干部',
  `admin_post` varchar(200) DEFAULT NULL COMMENT '所担任行政职务',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='教代会执委会';

