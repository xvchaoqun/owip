

-- 2021.3.5 ly
ALTER TABLE `dp_party_member_group`
	DROP COLUMN `is_present`;
-- 更新dp_party_member_view、dp_party_view

-- 完整的组工系统统战数据表
DROP TABLE IF EXISTS `dp_member`;
CREATE TABLE IF NOT EXISTS `dp_member` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属民主党派',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教工',
  `status` tinyint(3) unsigned NOT NULL COMMENT '1正常   4已退出',
  `dp_post` varchar(50) DEFAULT NULL COMMENT '党派内职务',
  `source` tinyint(3) unsigned NOT NULL COMMENT '来源，进入系统方式，1建系统时统一导入 2后台添加',
  `add_type` int(10) unsigned DEFAULT NULL COMMENT '增加类型',
  `dp_grow_time` date DEFAULT NULL COMMENT '加入党派时间',
  `is_party_member` tinyint(1) unsigned NOT NULL COMMENT '是否是中国共产党员',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `out_time` date DEFAULT NULL COMMENT '退出时间',
  `address` varchar(255) DEFAULT NULL COMMENT '通讯地址',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT 'E-mail',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党派成员信息表';

DROP TABLE IF EXISTS `dp_npm`;
CREATE TABLE IF NOT EXISTS `dp_npm` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `add_time` date DEFAULT NULL COMMENT '认定时间',
  `post` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态：1无党派人士 2退出人士',
  `out_time` date DEFAULT NULL COMMENT '退出时间',
  `transfer_time` date DEFAULT NULL COMMENT '转出/退出时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='无党派和退出人士，无党派人士可加入中共或其他任一民主党派，故增加一个转出功能，选择党派后，自动转至相关库';

DROP TABLE IF EXISTS `dp_npr`;
CREATE TABLE IF NOT EXISTS `dp_npr` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `unit_post` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `type` int(10) unsigned DEFAULT NULL COMMENT '所属类别(元数据)：1各级党外人大代表、政协委员、政府参事等；2党外中层干部；3党派基层组织和统战团体负责人；4党外高层次人才',
  `level` int(10) unsigned DEFAULT NULL COMMENT '所属级别(元数据)：1党派中央、2省级、3支委',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '是否撤销',
  `transfer_time` date DEFAULT NULL COMMENT '撤销时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='党外代表人士，来自于三个方面：处级干部库提取、民主党派成员库提取、管理员录入';

DROP TABLE IF EXISTS `dp_om`;
CREATE TABLE IF NOT EXISTS `dp_om` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `type` int(10) unsigned DEFAULT NULL COMMENT '所属类别 1华侨、归侨及侨眷 2欧美同学会会员 3知联会员 4港澳台  5宗教信仰',
  `unit_post` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `is_deleted` tinyint(1) unsigned DEFAULT NULL COMMENT '是否撤销',
  `transfer_time` date DEFAULT NULL COMMENT '撤销时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='其他统战人员。华侨、归侨及侨眷、欧美同学会会员、知联会员';

DROP TABLE IF EXISTS `dp_org_admin`;
CREATE TABLE IF NOT EXISTS `dp_org_admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '所属党派',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1 民主党派管理员',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `code` varchar(20) DEFAULT NULL COMMENT '学工号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='党派管理员，民主党派管理员，单独设定的管理员，可能他不属于现任党派的成员';

DROP TABLE IF EXISTS `dp_party`;
CREATE TABLE IF NOT EXISTS `dp_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `short_name` varchar(20) DEFAULT NULL COMMENT '简称',
  `class_id` int(10) unsigned NOT NULL COMMENT '民主党派类别，关联元数据，民革、民盟、民建、民进、农工党、致公党、九三学社、台盟、无党派',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mailbox` varchar(50) DEFAULT NULL COMMENT '信箱',
  `found_time` date DEFAULT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `delete_time` date DEFAULT NULL COMMENT '撤销时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='民主党派';

DROP TABLE IF EXISTS `dp_party_member`;
CREATE TABLE IF NOT EXISTS `dp_party_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属委员会',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号',
  `type_ids` varchar(100) DEFAULT NULL COMMENT '分工',
  `post_id` int(10) unsigned DEFAULT NULL COMMENT '职务，关联元数据（主委、副主委、委员）',
  `assign_date` date DEFAULT NULL COMMENT '任职时间，具体到月',
  `office_phone` varchar(50) DEFAULT NULL COMMENT '办公电话',
  `is_admin` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否管理员',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `present_member` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否现任委员',
  `delete_time` date DEFAULT NULL COMMENT '离任时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='党派委员';

DROP TABLE IF EXISTS `dp_party_member_group`;
CREATE TABLE IF NOT EXISTS `dp_party_member_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '上一届委员会',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属民主党派',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date DEFAULT NULL COMMENT '成立时间，本届委员会成立时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  `group_session` varchar(50) DEFAULT NULL COMMENT '委员会届数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='基层党组织领导班子';

DROP TABLE IF EXISTS `dp_pr_cm`;
CREATE TABLE IF NOT EXISTS `dp_pr_cm` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `unit_post` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `executive_level` varchar(50) DEFAULT NULL COMMENT '行政级别',
  `work_time` date DEFAULT NULL COMMENT '参加工作时间',
  `education` varchar(50) DEFAULT NULL COMMENT '最高学历',
  `degree` varchar(50) DEFAULT NULL COMMENT '最高学位',
  `school` varchar(50) DEFAULT NULL COMMENT '毕业学校',
  `major` varchar(50) DEFAULT NULL COMMENT '所学专业',
  `elect_post` varchar(50) DEFAULT NULL COMMENT '当选时职务',
  `elect_session` varchar(50) DEFAULT NULL COMMENT '当选届次',
  `elect_time` date DEFAULT NULL COMMENT '当选时间',
  `end_time` date DEFAULT NULL COMMENT '到届时间',
  `status` tinyint(1) unsigned DEFAULT NULL COMMENT '状态',
  `type` int(10) unsigned DEFAULT NULL COMMENT '类别',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人大代表、政协委员信息。同一个人在一个库中可以出现多次，因为可能担任几类代表';


-- 2021.1.25 ly
DELETE FROM `base_meta_type` WHERE `id`=673;

-- 2020.10.19 ly 角色
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`)
VALUES ('dp_member', '民主党派成员', '-1', '-1', NULL, NULL, 0, 0, 0, 65, '');
UPDATE `sys_role` SET `name`='民主党派干部成员' WHERE  `id`=70;
UPDATE sys_user set role_ids=REPLACE(role_ids, ',69,',',75,') WHERE role_ids LIKE '%,69,%';

SET FOREIGN_KEY_CHECKS=0;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2524, 0, '基本情况登记表', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpInfoForm:list', NULL, NULL, NULL, 1, 90);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2525, 0, '基本情况登记表下载', '', 'function', '', NULL, 2524, '0/1/2574/2524/', 1, 'dpInfoForm:download', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2606, 0, '人大代表、政协委员', '', 'url', '', '/dp/dpPrCm', 2574, '0/1/2574/', 0, 'dpPrCm:list', NULL, NULL, NULL, 1, 125);

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2605, NULL, '人大代表、政协委员类别', '统战信息管理', '人大代表、政协委员', 'mc_dp_prcm_type', '', '', '', 2611, 1);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2590, '民主党派类别', '统战信息管理', '民主党派', 'mc_dp_party_class', '', '', '', 2589);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2591, '民主党派委员职务', '统战信息管理', '委员会', 'mc_dp_party_member_post', '是否默认管理员', '', '', 2590);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2592, '民主党派委员分工', '统战信息管理', '委员会', 'mc_dp_party_member_type', '是否默认管理员', '', '', 2592);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2593, '党外代表所属类别', '统战信息管理', '', 'mc_dp_npr_type', '', '', '', 2599);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2594, '党外代表级别', '统战信息管理', '', 'mc_dp_npr_level', '', '', '', 2594);
INSERT INTO `base_meta_class` (`id`,  `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`) VALUES (2595, '其他统战人员类别', '统战信息管理', '统战人员信息', 'mc_dp_other_type', '', '', '', 2595);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '无党派', 'mt_dp_wdp', 1, '', '', 9, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国国民党革命委员会（民革）', 'mt_dp_mg', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国民主同盟（民盟）', 'mt_dp_mm', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国民主建国会（民建）', 'mt_dp_mj', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国民主促进会（民进）', 'mt_dp_mjh', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国农工民主党（农工党）', 'mt_dp_ngd', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '中国致公党（致公党）', 'mt_dp_zgd', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '九三学社（九三学社）', 'mt_dp_jsxs', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2590, '台湾民主自治同盟（台盟）', 'mt_dp_tm', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2591, '主委', 'mt_dp_zw', 1, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2591, '副主委', 'mt_dp_fzw', 1, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2591, '委员', 'mt_dp_wy', 0, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2592, '主持党委工作', 'mt_dp_zcdwgz', 1, '是否是管理员', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2592, '教师工作', 'mt_dp_jsgz', 1, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2592, '学生工作', 'mt_dp_xsgz', 1, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2593, '各级党外人大代表、政协委员、政府参事等', 'mt_dp_npr_1', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2593, '党外中层干部', 'mt_dp_npr_2', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2593, '党派基层组织和统战团体负责人', 'mt_dp_npr_3', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2593, '党外高层次人才', 'mt_dp_npr_4', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2594, '党派中央', 'mt_dp_npr_center', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2594, '省级', 'mt_dp_npr_province', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2594, '支委', 'mt_dp_npr_branch', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '华侨、归侨及侨眷', 'mt_dp_other_type_1', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '欧美同学会会员', 'mt_dp_other_type_2', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2595, '知联会员', 'mt_dp_other_type_3', NULL, '', '', 3, 1);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2574, 0, '统战信息管理', '', 'menu', 'fa fa-binoculars', NULL, 1, '0/1/', 0, 'dp:list', NULL, NULL, NULL, 1, 7535);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2575, 0, '民主党派', '', 'url', '', '/dp/dpParty', 2574, '0/1/2574/', 0, 'dpParty:list', NULL, NULL, NULL, 1, 300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2576, 0, '委员会', '', 'url', '', '/dp/dpPartyMemberGroup', 2574, '0/1/2574/', 0, 'dpPartyMemberGroup:list', NULL, NULL, NULL, 1, 200);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2577, 0, '民主党派成员', '', 'url', '', '/dp/dpMember', 2592, '0/1/2574/2592/', 0, 'dpMember:list', NULL, NULL, NULL, 1, 500);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2578, 0, '编辑', '', 'function', '', NULL, 2575, '0/1/2574/2575/', 1, 'dpParty:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2579, 0, '删除', '', 'function', '', NULL, 2575, '0/1/2574/2575/', 1, 'dpParty:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2580, 0, '编辑', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2581, 0, '删除', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2582, 0, '完全删除已撤销委员会', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:realDel', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2583, 0, '编辑', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2584, 0, '删除', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2585, 0, '编辑', '', 'function', '', NULL, 2577, '0/1/2574/2592/2577/', 1, 'dpMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2586, 0, '删除', '', 'function', '', NULL, 2577, '0/1/2574/2592/2577/', 1, 'dpMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2587, 0, '编辑管理员', '', 'function', '', NULL, 2574, '0/1/2574/', 1, 'dpOrgAdmin:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2588, 0, '查看委员', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2590, 0, '查看民主党派', '', 'function', '', NULL, 2574, '0/1/2574/', 1, 'dp:viewAll', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2591, 0, '添加', '', 'function', '', NULL, 2575, '0/1/2574/2575/', 1, 'dpParty:add', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2592, 0, '统战人员信息', '', 'menu', '', '/dp/dpInfo', 2574, '0/1/2574/', 0, 'dpInfo:list', NULL, NULL, NULL, 1, 150);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2593, 0, '无党派人士', '', 'url', '', '/dp/dpNpm', 2592, '0/1/2574/2592/', 0, 'dpNpm:list', NULL, NULL, NULL, 1, 400);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2594, 0, '其他统战人员', '', 'url', '', '/dp/dpOm', 2592, '0/1/2574/2592/', 0, 'dpOm:list', NULL, NULL, NULL, 1, 300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2595, 0, '党外代表人士', '', 'url', '', '/dp/dpNpr', 2574, '0/1/2574/', 0, 'dpNpr:list', NULL, NULL, NULL, 1, 125);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2596, 0, '添加/修改', '', 'function', '', NULL, 2593, '0/1/2574/2592/2593/', 1, 'dpNpm:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2597, 0, '删除', '', 'function', '', NULL, 2593, '0/1/2574/2592/2593/', 1, 'dpNpm:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2598, 0, '调序', '', 'function', '', NULL, 2593, '0/1/2574/2592/2593/', 1, 'dpNpm:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2599, 0, '编辑', '', 'function', '', NULL, 2595, '0/1/2574/2595/', 1, 'dpNpr:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2600, 0, '删除', '', 'function', '', NULL, 2595, '0/1/2574/2595/', 1, 'dpNpr:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2601, 0, '调序', '', 'function', '', NULL, 2595, '0/1/2574/2595/', 1, 'dpNpr:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2602, 0, '调序', '', 'function', '', NULL, 2577, '0/1/2574/2592/2577/', 1, 'dpMember:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2603, 0, '编辑', '', 'function', '', NULL, 2594, '0/1/2574/2592/2594/', 1, 'dpOm:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2604, 0, '删除', '', 'function', '', NULL, 2594, '0/1/2574/2592/2594/', 1, 'dpOm:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2605, 0, '调序', '', 'function', '', NULL, 2594, '0/1/2574/2592/2594/', 1, 'dpOm:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_dp_party', '民主党派管理员', '2574,2575,2578,2576,2580,2581,2582,2583,2584,2588,2592,2577,2585,2586,2602,2587,90,307,309,310,319,852,357,230,231,232,235,236,237,238,239,240,241,334,1005,314,315', '-1', '-1', '-1', 0, 0, 1, 59, '');
INSERT INTO `sys_role` (`code`, `name`, `resource_ids`, `m_resource_ids`, `resource_ids_minus`, `m_resource_ids_minus`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES ('role_dp_admin', '统战部管理员', '2574,2575,2578,2579,2591,2576,2580,2581,2582,2583,2584,2588,2592,2577,2585,2586,2602,2593,2596,2597,2598,2594,2603,2604,2605,2610,2612,2613,2614,2615,2616,2617,2618,2619,2595,2599,2600,2601,2587,2590,90,307,309,310,319,852,357,230,231,232,235,236,237,238,239,240,241,334,1005,314,315', '-1', '-1', '-1', 0, 0, 0, 58, '');
SET FOREIGN_KEY_CHECKS=1;

-- 党费账单两张表
DROP TABLE IF EXISTS `pmd_order_log`;
CREATE TABLE IF NOT EXISTS `pmd_order_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_id` int(10) unsigned NOT NULL COMMENT '日期id',
  `account` int(10) unsigned NOT NULL COMMENT '账号',
  `third_order_id` varchar(50) NOT NULL COMMENT '第三方订单号',
  `to_account` int(10) unsigned NOT NULL COMMENT '商户号',
  `tranamt` int(10) unsigned NOT NULL COMMENT '金额',
  `order_id` varchar(50) NOT NULL COMMENT '支付平台订单号',
  `reforder_id` varchar(50) DEFAULT NULL COMMENT '支付平台退款订单号',
  `oper_type` int(10) unsigned NOT NULL COMMENT '交易类型，101为',
  `order_desc` varchar(50) DEFAULT NULL COMMENT '商品名称',
  `praram1` varchar(50) DEFAULT NULL COMMENT '第三方备注字段',
  `sno` varchar(50) NOT NULL COMMENT '工号',
  `actua_lamt` int(10) unsigned NOT NULL COMMENT '实际交易金额(分)',
  `state` tinyint(1) unsigned NOT NULL COMMENT '交易状态',
  `pay_name` varchar(50) NOT NULL COMMENT '支付名称',
  `rz_date` datetime NOT NULL COMMENT '支付平台入账日期',
  `jy_date` datetime NOT NULL COMMENT '交易日期',
  `third_system` varchar(50) NOT NULL COMMENT '系统注册方',
  `sign` varchar(200) NOT NULL COMMENT '签名数据',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1425 DEFAULT CHARSET=utf8 COMMENT='党费收缴每日明细';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.pmd_order_sum_log
DROP TABLE IF EXISTS `pmd_order_sum_log`;
CREATE TABLE IF NOT EXISTS `pmd_order_sum_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `file_id` varchar(50) NOT NULL COMMENT '文件id',
  `total_count` int(10) unsigned NOT NULL COMMENT '交易总单数',
  `total_money` int(10) unsigned NOT NULL COMMENT '总金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='党费收缴每日统计';

-- -- 20190124 李阳 统战1.0表和视图 北邮专属

DELETE FROM `sys_resource` WHERE  `id`>=2610 and `id`<=2616;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2617, 0, '编辑', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2618, 0, '删除', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2619, 0, '调序', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2620, 0, '年度考核', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpEva:list', NULL, NULL, NULL, 1, 120);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2621, 0, '年度考核编辑', '', 'function', '', NULL, 2620, '0/1/2574/2620/', 1, 'dpEva:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2622, 0, '年度考核删除', '', 'function', '', NULL, 2620, '0/1/2574/2620/', 1, 'dpEva:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2623, 0, '家庭成员情况', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpFamily:list', NULL, NULL, NULL, 1, 110);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2624, 0, '家庭成员情况编辑', '', 'function', '', NULL, 2623, '0/1/2574/2623/', 1, 'dpFamily:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2625, 0, '家庭成员情况删除', '', 'function', '', NULL, 2623, '0/1/2574/2623/', 1, 'dpFamily:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2626, 0, '工作经历', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpWork:list', NULL, NULL, NULL, 1, 105);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2627, 0, '工作经历编辑', '', 'function', '', NULL, 2626, '0/1/2574/2626/', 1, 'dpWork:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2628, 0, '工作经历删除', '', 'function', '', NULL, 2626, '0/1/2574/2626/', 1, 'dpWork:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2629, 0, '学习经历', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpEdu:list', NULL, NULL, NULL, 1, 100);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2630, 0, '学习经历编辑', '', 'function', '', NULL, 2629, '0/1/2574/2629/', 1, 'dpEdu:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2631, 0, '学习经历删除', '', 'function', '', NULL, 2629, '0/1/2574/2629/', 1, 'dpEdu:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2632, 0, '家庭成员情况排序', '', 'function', '', NULL, 2629, '0/1/2574/2629/', 1, 'dpFamily:changeOrder', NULL, NULL, NULL, 1, NULL);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '海淀区政协委员', 'mt_agaimg', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '海淀区人大代表', 'mt_lviukq', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '北京市政协委员', 'mt_4yn0qu', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '北京市人大代表', 'mt_zfjzzs', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '其他人大代表、政协委员', 'mt_vbnycy', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '全国政协委员', 'mt_btlwxy', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2605, '全国人大代表', 'mt_qwy8xn', NULL, '', '', 1, 1);

INSERT INTO `sys_html_fragment` (`fid`, `code`, `category`, `type`, `role_id`, `title`, `content`, `attr`, `remark`, `sort_order`) VALUES (NULL, 'hf_dp_family_note', NULL, NULL, NULL, '家庭成员信息填写说明（注意事项）', '<h4>\r\n	<span style="font-size:24px;font-family:宋体;">注意事项</span>\r\n</h4>\r\n<p class="MsoNormal">\r\n	<span style="font-size:18px;font-family:宋体;">1. 原则上必须填写</span><span style="font-size:14pt;font-family:宋体;"><strong><span style="color:#E53333;font-size:18px;">父亲</span></strong><span style="font-size:18px;">、</span><strong><span style="color:#E53333;font-size:18px;">母亲</span></strong><span style="font-size:18px;">、</span><strong><span style="color:#E53333;font-size:18px;">配偶</span></strong><span style="font-size:18px;">、</span><strong><span style="color:#E53333;font-size:18px;">子女</span></strong><span style="font-size:18px;">的信息；</span></span>\r\n</p>\r\n<p class="MsoNormal">\r\n	<span style="font-size:18px;font-family:宋体;">2. 父母已退休、离休或去世的，应在填写原工作单位职务后加括号注明“（已退休）、（已离休）、（已去世）”。</span>\r\n</p>', NULL, '', 49);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2606, 0, '人大代表、政协委员', '', 'menu', '', NULL, 2574, '0/1/2574/', 0, 'dpPrCm:list', NULL, NULL, NULL, 1, 140);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2610, 0, '全国人大代表', '', 'url', '', '/dp/dpPrCm?type=1', 2606, '0/1/2574/2606/', 1, 'dpPrCountry:list', NULL, NULL, NULL, 1, 7000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2612, 0, '全国政协委员', '', 'url', '', '/dp/dpPrCm?type=2', 2606, '0/1/2574/2606/', 1, 'dpCmCountry:list', NULL, NULL, NULL, 1, 6000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2613, 0, '北京市人大代表', '', 'url', '', '/dp/dpPrCm?type=3', 2606, '0/1/2574/2606/', 1, 'dpPrBeiJing:list', NULL, NULL, NULL, 1, 5000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2614, 0, '北京市政协委员', '', 'url', '', '/dp/dpPrCm?type=4', 2606, '0/1/2574/2606/', 1, 'dpCmBeiJing:list', NULL, NULL, NULL, 1, 4000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2615, 0, '海淀区人大代表', '', 'url', '', '/dp/dpPrCm?type=5', 2606, '0/1/2574/2606/', 1, 'dpPrHaiDian:list', NULL, NULL, NULL, 1, 3000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2616, 0, '海淀区政协委员', '', 'url', '', '/dp/dpPrCm?type=6', 2606, '0/1/2574/2606/', 1, 'dpCmHaiDian:list', NULL, NULL, NULL, 1, 2000);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2617, 0, '编辑', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2618, 0, '删除', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2619, 0, '调序', '', 'function', '', NULL, 2606, '0/1/2574/2606/', 1, 'dpPrCm:changeOrder', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2521, 0, '奖励情况', '', 'function', '', NULL, 2574, '0/1/2574/', 0, 'dpReward:list', NULL, NULL, NULL, 1, 95);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2522, 0, '奖励情况编辑', '', 'function', '', NULL, 2521, '0/1/2574/2521/', 1, 'dpReward:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2523, 0, '奖励情况删除', '', 'function', '', NULL, 2521, '0/1/2574/2521/', 1, 'dpReward:del', NULL, NULL, NULL, 1, NULL);

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (2606, NULL, '民主党派奖励情况', '统战信息管理', '党派成员档案表', 'mc_dp_reward_type', '', '', '', 2612, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '其他奖励', 'mt_2tinxl', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (2606, '党派内奖励', 'mt_ev1plk', NULL, '', '', 1, 1);


DROP TABLE IF EXISTS `dp_reward`;
CREATE TABLE IF NOT EXISTS `dp_reward` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '所属统战人员',
  `reward_level` int(10) unsigned DEFAULT NULL COMMENT '奖励级别',
  `reward_time` date DEFAULT NULL COMMENT '日期',
  `name` varchar(200) DEFAULT NULL COMMENT '获得奖项',
  `unit` varchar(300) DEFAULT NULL COMMENT '颁奖单位',
  `proof` varchar(255) DEFAULT NULL COMMENT '获奖证书',
  `proof_filename` varchar(255) DEFAULT NULL COMMENT '获奖证书文件名',
  `is_independent` tinyint(1) unsigned DEFAULT '0' COMMENT '是否独立获奖',
  `rank` int(10) unsigned DEFAULT NULL COMMENT '排名',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：修改记录',
  `reward_type` int(10) unsigned NOT NULL COMMENT '类别（元数据）， 党派内奖励 其他奖励情况',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='统战人员奖励信息';

DROP TABLE IF EXISTS `dp_edu`;
CREATE TABLE IF NOT EXISTS `dp_edu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sub_work_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '其间工作数量，提交其间工作时设定',
  `user_id` int(10) unsigned NOT NULL COMMENT '所属统战人员',
  `edu_id` int(10) unsigned DEFAULT NULL COMMENT '学历，关联元数据',
  `is_graduated` tinyint(1) unsigned DEFAULT NULL COMMENT '毕业/在读',
  `is_high_edu` tinyint(1) unsigned DEFAULT NULL COMMENT '是否最高学历',
  `school` varchar(100) DEFAULT NULL COMMENT '毕业/在读学校',
  `dep` varchar(100) DEFAULT NULL COMMENT '院系',
  `subject` int(10) unsigned DEFAULT NULL COMMENT '学科门类',
  `first_subject` int(10) unsigned DEFAULT NULL COMMENT '一级学科',
  `major` varchar(100) DEFAULT NULL COMMENT '所学专业',
  `school_type` tinyint(3) unsigned DEFAULT NULL COMMENT '学校类型， 1本校 2境内 3境外',
  `enrol_time` date DEFAULT NULL COMMENT '入学时间',
  `finish_time` date DEFAULT NULL COMMENT '毕业时间',
  `learn_style` int(10) unsigned DEFAULT NULL COMMENT '学习方式，关联元数据，全日制教育or在职教育',
  `has_degree` tinyint(1) unsigned NOT NULL COMMENT '是否获得学位',
  `degree_type` tinyint(3) unsigned DEFAULT NULL COMMENT '学位类型，1 学士 2 硕士 3 博士',
  `degree` varchar(100) DEFAULT NULL COMMENT '学位',
  `is_high_degree` tinyint(1) unsigned DEFAULT NULL COMMENT '是否为最高学位',
  `is_second_degree` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否第二个学位，存在两个最高学位时有效',
  `degree_country` varchar(50) DEFAULT NULL COMMENT '学位授予国家',
  `degree_unit` varchar(100) DEFAULT NULL COMMENT '学位授予单位',
  `degree_time` date DEFAULT NULL COMMENT '学位授予日期',
  `tutor_name` varchar(50) DEFAULT NULL COMMENT '导师姓名，只有博士和硕士需要填写导师信息，如果是大专和本科，则这两个字段为不可编辑状态，显示为“-”',
  `tutor_title` varchar(100) DEFAULT NULL COMMENT '所在单位及职务（职称）',
  `certificate` varchar(200) DEFAULT NULL COMMENT '学历学位证书，1个或2个证书（毕业证和学位证），逗号分隔开',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `note` varchar(100) DEFAULT NULL COMMENT '其他说明',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='统战人员学习经历';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_eva
DROP TABLE IF EXISTS `dp_eva`;
CREATE TABLE IF NOT EXISTS `dp_eva` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `year` int(10) unsigned NOT NULL COMMENT '年份',
  `title` varchar(100) DEFAULT NULL COMMENT '时任职务',
  `type` int(10) unsigned NOT NULL COMMENT '考核情况，关联元数据',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COMMENT='统战人员年度考核记录';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_family
DROP TABLE IF EXISTS `dp_family`;
CREATE TABLE IF NOT EXISTS `dp_family` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '所属统战人员',
  `title` int(10) unsigned DEFAULT NULL COMMENT '称谓',
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `with_god` tinyint(1) unsigned DEFAULT NULL COMMENT '是否去世',
  `political_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌，关联元数据',
  `unit` varchar(200) DEFAULT NULL COMMENT '工作单位及职务',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：修改记录',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='家庭成员信息';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_work
DROP TABLE IF EXISTS `dp_work`;
CREATE TABLE IF NOT EXISTS `dp_work` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '所属学习或工作经历，其间工作时设定',
  `is_edu_work` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否学习其间工作',
  `sub_work_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '期间工作数量，提交期间工作时设定',
  `user_id` int(10) unsigned NOT NULL COMMENT '所属统战人员',
  `start_time` date DEFAULT NULL COMMENT '开始日期',
  `end_time` date DEFAULT NULL COMMENT '结束日期',
  `detail` varchar(100) DEFAULT NULL COMMENT '工作单位及担任职务（或专技职务）',
  `unit_ids` varchar(100) DEFAULT NULL COMMENT '所属内设机构，包含历史单位',
  `work_types` varchar(200) DEFAULT NULL COMMENT '院系/机关工作经历，关联元数据，可多选',
  `is_cadre` tinyint(1) unsigned DEFAULT NULL COMMENT '是否干部任职',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作经历，目前有两级，工作经历->期间工作经历';
