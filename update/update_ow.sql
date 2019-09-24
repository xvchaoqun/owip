2019-09-24

-- 添加资源 桑文帅
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (200, 0, '党小组', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroup:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (201, 0, '党小组成员', '', 'function', '', NULL, 182, '0/1/260/182/', 1, 'branchGroupMember:*', NULL, NULL, NULL, 1, NULL);

-- 建表语句 桑文帅
CREATE TABLE IF NOT EXISTS `ow_branch_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '小组名称',
  `branch_id` int(10) NOT NULL COMMENT '支部ID',
  `user_id` int(10) NOT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `count_member` int(10) unsigned DEFAULT NULL COMMENT '党小组总人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='党小组';

CREATE TABLE IF NOT EXISTS `ow_branch_group_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '党小组ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '成员',
  `is_leader` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否是党小组组长',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='党小组成员';

