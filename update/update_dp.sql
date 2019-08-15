

-- 插入角色和权限 -- 20190815 李阳
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2574, 0, '统战信息管理', '', 'menu', 'fa fa-binoculars', NULL, 1, '0/1/', 0, 'dp:*', NULL, NULL, NULL, 1, 7535);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2575, 0, '民主党派', '', 'url', '', '/dp/dpParty', 2574, '0/1/2574/', 0, 'dpParty:list', NULL, NULL, NULL, 1, 300);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2576, 0, '委员会', '', 'url', '', '/dp/dpPartyMemberGroup', 2574, '0/1/2574/', 0, 'dpPartyMemberGroup:list', NULL, NULL, NULL, 1, 200);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2577, 0, '党派成员', '', 'url', '', '/dp/dpMember', 2574, '0/1/2574/', 0, 'dpMember:list', NULL, NULL, NULL, 1, 100);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2578, 0, '编辑', '', 'function', '', NULL, 2575, '0/1/2574/2575/', 1, 'dpParty:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2579, 0, '删除', '', 'function', '', NULL, 2575, '0/1/2574/2575/', 1, 'dpParty:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2580, 0, '编辑', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2581, 0, '删除', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2582, 0, '完全删除已撤销委员会', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMemberGroup:realDel', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2583, 0, '编辑', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2584, 0, '删除', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2585, 0, '编辑', '', 'function', '', NULL, 2577, '0/1/2574/2577/', 1, 'dpMember:edit', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2586, 0, '删除', '', 'function', '', NULL, 2577, '0/1/2574/2577/', 1, 'dpMember:del', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2587, 0, '编辑管理员', '', 'function', '', NULL, 2574, '0/1/2574/', 1, 'dpOrgAdmin:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2588, 0, '查看委员', '', 'function', '', NULL, 2576, '0/1/2574/2576/', 1, 'dpPartyMember:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2590, 0, '查看民主党派', '', 'function', '', NULL, 2577, '0/1/2574/2577/', 1, 'dp:viewAll', NULL, NULL, NULL, 1, NULL);


INSERT INTO `sys_role` (`id`, `code`, `name`, `resource_ids`, `m_resource_ids`, `user_count`, `available`, `is_sys_hold`, `sort_order`, `remark`) VALUES (63, 'dp_org_admin', '民主党派管理员', '2574,2575,2578,2579,2576,2580,2581,2582,2583,2584,2588,2577,2585,2586,2590,2587', '-1', NULL, 0, 0, 58, '');


-- 插入元数据
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (8, 8, '学部、院、系所', 'mt_unit_type_xy', NULL, 'xy', '附加属性用于干部数据统计，不可修改', 66, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (557, 90, '中国国民党革命委员会（民革）', 'mt_dp_mg', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (558, 90, '中国民主同盟（民盟）', 'mt_dp_mm', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (559, 90, '中国民主建国会（民建）', 'mt_dp_mj', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (560, 90, '中国民主促进会（民进）', 'mt_dp_mjh', NULL, '', '', 4, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (561, 90, '中国农工民主党（农工党）', 'mt_dp_ngd', NULL, '', '', 5, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (562, 90, '中国致公党（致公党）', 'mt_dp_zgd', NULL, '', '', 6, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (563, 90, '九三学社（九三学社）', 'mt_dp_jsxs', NULL, '', '', 7, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (564, 90, '台湾民主自治同盟（台盟）', 'mt_dp_tm', NULL, '', '', 8, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (565, 91, '主委', 'mt_dp_zw', 1, '', '', 1, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (566, 91, '副主委', 'mt_dp_fzw', 1, '', '', 2, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (567, 91, '委员', 'mt_dp_wy', 1, '', '', 3, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (568, 90, '无党派', 'mt_dp_wdp', 1, '', '', 9, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (569, 92, '主持党委工作', 'mt_dp_zcdwgz', 1, '是否是管理员', '', 1, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (570, 92, '教师工作', 'mt_dp_jsgz', 1, '', '', 2, 1);
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (571, 92, '学生工作', 'mt_dp_xsgz', 1, '', '', 3, 1);


INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (90, NULL, '民主党派类别', '统战信息管理', '民主党派', 'mc_dp_party_class', '', '', '', 90, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (91, NULL, '民主党派委员职务', '统战信息管理', '委员会', 'mc_dp_party_member_post', '是否默认管理员', '', '', 91, 1);
INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (92, NULL, '民主党派委员分工', '统战信息管理', '委员会', 'mc_dp_party_member_type', '是否默认管理员', '', '', 92, 1);


-- 民主党派建表语句
DROP TABLE IF EXISTS `dp_party_member_group`;
CREATE TABLE IF NOT EXISTS `dp_party_member_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '上一届委员会',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属民主党派',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任委员会',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date NOT NULL COMMENT '任命时间，本届委员会任命时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='基层党组织领导班子';

DROP TABLE IF EXISTS `dp_party_member`;
CREATE TABLE IF NOT EXISTS `dp_party_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属委员会',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号',
  `type_ids` varchar(100) DEFAULT NULL COMMENT '分工',
  `post_id` int(10) unsigned DEFAULT NULL COMMENT '职务，关联元数据（主委、副主委、委员）',
  `assign_date` date DEFAULT NULL COMMENT '任职时间，具体到月',
  `office_phone` varchar(50) DEFAULT NULL COMMENT '办公电话',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `is_admin` tinyint(1) unsigned NOT NULL COMMENT '是否管理员',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `present_member` tinyint(1) unsigned NOT NULL COMMENT '是否现任委员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='党派委员';

DROP TABLE IF EXISTS `dp_party`;
CREATE TABLE IF NOT EXISTS `dp_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `short_name` varchar(20) NOT NULL COMMENT '简称',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `class_id` int(10) unsigned NOT NULL COMMENT '民主党派类别，关联元数据，民革、民盟、民建、民进、农工党、致公党、九三学社、台盟、无党派',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `mailbox` varchar(50) DEFAULT NULL COMMENT '信箱',
  `found_time` date DEFAULT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='民主党派';

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='党派管理员，民主党派管理员，单独设定的管理员，可能他不属于现任党派的成员';

DROP TABLE IF EXISTS `dp_member_out`;
CREATE TABLE IF NOT EXISTS `dp_member_out` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL,
  `phone` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `type` int(10) unsigned NOT NULL COMMENT '类别',
  `to_title` varchar(100) NOT NULL COMMENT '转入单位抬头',
  `to_unit` varchar(100) NOT NULL COMMENT '转入单位',
  `from_unit` varchar(100) NOT NULL COMMENT '转出单位，默认为中共北京师范大学+民主党派名称',
  `from_address` varchar(100) NOT NULL COMMENT '转出单位地址，默认同上',
  `from_phone` varchar(100) NOT NULL COMMENT '转出单位联系电话',
  `from_fax` varchar(100) NOT NULL COMMENT '转出单位传真',
  `from_post_code` varchar(10) NOT NULL COMMENT '转出单位邮编，默认为100875',
  `pay_time` date NOT NULL COMMENT '党费缴纳至年月',
  `valid_days` int(10) unsigned NOT NULL COMMENT '介绍信有效期天数',
  `handle_time` date NOT NULL COMMENT '办理时间',
  `has_receipt` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有回执',
  `status` tinyint(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1分党委审批 2组织部审批',
  `is_modify` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否修改，审批完成后是否修改过',
  `is_back` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否打回，当前状态是否是打回的',
  `reason` varchar(100) DEFAULT NULL COMMENT '返回修改原因',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `print_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '打印次数',
  `last_print_time` datetime DEFAULT NULL COMMENT '最后一次打印时间',
  `last_print_user_id` int(10) unsigned DEFAULT NULL COMMENT '最后一次打印人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织关系转出';

DROP TABLE IF EXISTS `dp_member`;
CREATE TABLE IF NOT EXISTS `dp_member` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属民主党派',
  `political_status` tinyint(3) unsigned NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `status` tinyint(3) unsigned NOT NULL COMMENT '1正常，2已退休（弃用） 3已出党 4已转出',
  `source` tinyint(3) unsigned NOT NULL COMMENT '来源，进入系统方式，1建系统时统一导入 2后台添加',
  `add_type` int(10) unsigned DEFAULT NULL COMMENT '增加类型',
  `transfer_time` datetime DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间，时间从入党申请同步过来',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `sponsor` varchar(50) DEFAULT NULL COMMENT '入党介绍人',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `party_post` varchar(100) DEFAULT NULL COMMENT '党内职务',
  `party_reward` varchar(255) DEFAULT NULL COMMENT '党内奖励',
  `other_reward` varchar(255) DEFAULT NULL COMMENT '其他奖励',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党派成员信息表';