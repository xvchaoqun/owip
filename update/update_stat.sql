2019-11-13

-- 修改资源名称 桑文帅
UPDATE `db_owip`.`sys_resource` SET `name`='系统消息' WHERE  `id`=3031;
UPDATE `db_owip`.`sys_resource` SET `name`='系统消息' WHERE  `id`=3032;

-- 添加资源数据 桑文帅

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3031, 0, '系统提醒', '', 'url', '', '/sysMsg?type=2', 1, '0/1/', 1, 'sysMsg:list', NULL, NULL, NULL, 1, 800);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3032, 0, '系统提醒', '', 'url', '', '/sysMsg?type=1', 21, '0/1/21/', 1, 'sysMsg:*', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (3033, 0, '分党委信息统计', '', 'function', '', NULL, 108, '0/1/108/', 1, 'stat:party', NULL, NULL, NULL, 1, NULL);

-- 创建表 sys_msg 桑文帅

CREATE TABLE IF NOT EXISTS `sys_msg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '接收人',
  `send_user_id` int(10) unsigned NOT NULL COMMENT '发送人',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `create_time` datetime NOT NULL COMMENT '通知发送时间',
  `ip` varchar(50) NOT NULL COMMENT 'ip',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态 1.未读 2.已读',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='系统提醒';
