2020-04-09
-- 添加字段 桑文帅
ALTER TABLE `cg_team` ADD COLUMN `fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上级ID' AFTER `id`,
	ADD CONSTRAINT `FK_cg_team_cg_team` FOREIGN KEY (`fid`) REFERENCES `cg_team` (`id`) ON DELETE CASCADE;

2019-09-19
-- 添加字段 桑文帅
ALTER TABLE `cg_member` ADD COLUMN `seat` VARCHAR(50) NULL DEFAULT NULL COMMENT '席位' AFTER `tag`;

2019-09-10
-- 建表语句 桑文帅

DROP TABLE IF EXISTS `cg_leader`;
CREATE TABLE IF NOT EXISTS `cg_leader` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` int(10) unsigned NOT NULL COMMENT '所属委员会或领导小组',
  `is_post` tinyint(1) unsigned NOT NULL COMMENT '是否席位制',
  `unit_post_id` int(10) unsigned DEFAULT NULL COMMENT '关联岗位，如果是席位制',
  `user_id` int(10) unsigned NOT NULL COMMENT '如果是非席位制，用户ID。如果是席位制，现任职干部ID',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系方式，办公电话或手机号码',
  `is_current` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否当前办公室主任',
  `confirm_date` date DEFAULT NULL COMMENT '确定时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='办公室主任';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.cg_member 结构
DROP TABLE IF EXISTS `cg_member`;
CREATE TABLE IF NOT EXISTS `cg_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` int(10) unsigned NOT NULL COMMENT '所属委员会或领导小组',
  `post` int(10) unsigned NOT NULL COMMENT '职务，关联元数据',
  `type` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '人员类型，1 现任干部 2 各类代表',
  `unit_post_id` int(10) unsigned DEFAULT NULL COMMENT '关联岗位，针对现任干部，岗位调整时系统自动提示',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '现任干部',
  `tag` varchar(50) DEFAULT NULL COMMENT '代表类型',
  `start_date` date DEFAULT NULL COMMENT '添加日期',
  `end_date` date DEFAULT NULL COMMENT '移除日期',
  `is_current` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否现任成员',
  `need_adjust` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否需要调整',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='委员会和领导小组成员';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.cg_rule 结构
DROP TABLE IF EXISTS `cg_rule`;
CREATE TABLE IF NOT EXISTS `cg_rule` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` int(10) unsigned NOT NULL COMMENT '所属委员会或领导小组',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1 人员组成规则 2 工作职责 3 议事规则',
  `is_current` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否当前规程，每种类型只有一个当前规程',
  `confirm_date` date DEFAULT NULL COMMENT '规程确定时间',
  `content` text COMMENT '规程内容',
  `file_path` varchar(100) DEFAULT NULL COMMENT '相关文件，pdf',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='委员会或领导小组相关规程，包含人员组成规则、工作职责、议事规则等';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.cg_team 结构
DROP TABLE IF EXISTS `cg_team`;
CREATE TABLE IF NOT EXISTS `cg_team` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL COMMENT '名称，委员会和领导小组的名称',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1 委员会 2 领导小组',
  `category` int(10) unsigned NOT NULL COMMENT '类别，关联元数据',
  `is_current` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否当前委员会或领导小组',
  `need_adjust` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否需要调整',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='委员会和领导小组，cg：Committee & leader Group';

-- 数据导出被取消选择。
-- 导出  表 db_owip_1.cg_unit 结构
DROP TABLE IF EXISTS `cg_unit`;
CREATE TABLE IF NOT EXISTS `cg_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `team_id` int(10) unsigned NOT NULL COMMENT '所属委员会或领导小组',
  `unit_id` int(10) unsigned NOT NULL COMMENT '单位',
  `is_current` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否当前挂靠单位',
  `confirm_date` date DEFAULT NULL COMMENT '确定时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='挂靠单位';

2019-09-10
-- 添加资源数据 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3001, 0, '委员会和领导小组', '', 'menu', 'fa fa-star', NULL, 1, '0/1/', 0, 'cg:menu', NULL, NULL, NULL, 1, 3500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3002, 0, '参数设置', '', 'url', '', '/metaClass_type_list?cls=mc_cg_type,mc_cg_staff', 3001, '0/1/3001/', 1, 'mc_cg_staff,mc_cg_type:*', NULL, NULL, NULL, 1, 3510);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3003, 0, '委员会和领导小组列表', '', 'url', '', '/cg/cgTeam', 3001, '0/1/3001/', 1, 'cgTeam:*', NULL, NULL, NULL, 1, 3590);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3004, 0, '办公室主任', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgLeader:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3005, 0, '委员会和小组成员', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgMember:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3006, 0, '工作职责', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgRule:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3007, 0, '挂靠单位', '', 'function', '', NULL, 3001, '0/1/3001/', 1, 'cgUnit:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3008, 0, '统计分析', '', 'url', '', '/cg/cgCount', 3001, '0/1/3001/', 1, 'cgCount:*', NULL, NULL, NULL, 1, 3550);
-- 添加角色
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_cg_admin', '委员会和领导小组管理员', '2084,2086,2091,2085,2087,2088,2089,2090', '-1', NULL, 0, 0, 60, '');

-- 添加源数据
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (300, NULL, '委员会和领导小组类别', '委员会和领导小组', '参数管理', 'mc_cg_type', '', '', '', 91, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (301, NULL, '委员会和领导小组人员职务', '委员会和领导小组', '参数管理', 'mc_cg_staff', '', '', '', 92, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '综合', 'mt_samlhc', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '党建和思想政治工作', 'mt_5lrndx', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '人才培养', 'mt_hlfynu', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '学科建设、科研和社会服务', 'mt_aorgh2', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '人财物管理', 'mt_kjiga9', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '国际交流、港澳台工作', 'mt_imdl4r', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '总务', 'mt_x90odt', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (300, '校区和附属中小学', 'mt_sajyjf', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '主任', 'mt_329tph', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '副主任', 'mt_tpa4me', NULL, '', '', 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '委员', 'mt_gssgbp', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '组长', 'mt_jbyim7', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '副组长', 'mt_hot9zc', NULL, '', '', 10, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '成员', 'mt_ilfi8p', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '党校校长', 'mt_0kdzma', NULL, NULL, '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '党校常务副校长', 'mt_lvylno', NULL, NULL, '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (301, '党校副校长', 'mt_nwn6kp', NULL, NULL, '', 3, 1);


2019-09-10
-- 添加定时任务数据 桑文帅
INSERT INTO `sys_scheduler_job` (`id`, `name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES (26, '委员会和领导小组人员变动提醒', '委员会和领导小组关联岗位的现任干部变动提醒', 'job.cg.NeedAdjustMember', '0 0 0/2 * * ?', 0, 0, 27, '2019-09-06 15:48:19');

2019-09-06
-- 添加字段 桑文帅
ALTER TABLE `cg_member` ADD COLUMN `need_adjust` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否需要调整' AFTER `is_current`;

2019-09-02
-- 删除字段 桑文帅
ALTER TABLE `cg_member` DROP COLUMN `user_ids`;

2019-08-08
-- 修改字段属性 桑文帅
ALTER TABLE `cg_member` ALTER `user_id` DROP DEFAULT;
ALTER TABLE `cg_member` CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NULL COMMENT '现任干部' AFTER `unit_post_id`;