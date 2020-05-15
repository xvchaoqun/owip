
-- 20200515 ly
DROP TABLE IF EXISTS `verify_grow_time`;
CREATE TABLE IF NOT EXISTS `verify_grow_time` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cadre_id` int(10) unsigned DEFAULT NULL,
  `old_grow_time` date DEFAULT NULL COMMENT '系统中党员的入党时间（认定前日期)',
  `verify_grow_time` date DEFAULT NULL COMMENT '组织认定入党时间(认定后日期)',
  `material_time` date DEFAULT NULL COMMENT '形成时间',
  `material_grow_time` date DEFAULT NULL COMMENT '记载的入党时间（入党志愿书）',
  `ad_time` date DEFAULT NULL COMMENT '形成时间，档案中最新干部任免审批表',
  `ad_grow_time` date DEFAULT NULL COMMENT '记载的入党时间（任免审批表）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) unsigned DEFAULT '0' COMMENT '状态， 0：正式记录 1：历史记录 2：已删除，每个干部的正式记录只有一条',
  `submit_user_id` int(10) unsigned NOT NULL COMMENT '提交人',
  `submit_ip` varchar(50) NOT NULL COMMENT '提交IP',
  `submit_time` datetime NOT NULL COMMENT '提交时间',
  `update_user_id` int(10) unsigned DEFAULT NULL COMMENT '修改人',
  `update_ip` varchar(50) DEFAULT NULL COMMENT '修改IP',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='入党时间认定，干部档案审核';

UPDATE sys_resource SET NAME='档案认定',menu_css='fa fa-check-square-o',parent_ids='0/1/339/', parent_id=339,sort_order=190 WHERE id=411;
DELETE from sys_resource WHERE id=410;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2535, 0, '入党时间认定', '', 'function', '', NULL, 411, '0/1/339/411/', 1, 'verifyGrowTime:*', NULL, NULL, NULL, 1, NULL);
