-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.1.48-community - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win32
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 owip 的数据库结构
CREATE DATABASE IF NOT EXISTS `owip` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `owip`;


-- 导出  表 owip.base_cadre 结构
CREATE TABLE IF NOT EXISTS `base_cadre` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号',
  `type_id` int(10) unsigned NOT NULL COMMENT '行政级别，关联元数据',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `title` varchar(50) DEFAULT NULL COMMENT '单位及职务',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort_order` int(11) unsigned DEFAULT '0' COMMENT '排序',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '状态，暂未用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `FK_base_cadre_base_meta_type` (`type_id`),
  KEY `FK_base_cadre_base_meta_type_2` (`post_id`),
  CONSTRAINT `FK_base_cadre_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_base_meta_type_2` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='干部库';

-- 正在导出表  owip.base_cadre 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `base_cadre` DISABLE KEYS */;
INSERT INTO `base_cadre` (`id`, `user_id`, `type_id`, `post_id`, `title`, `remark`, `sort_order`, `status`) VALUES
	(5, 640, 17, 16, '111', '', 279, 0),
	(6, 641, 19, 15, '1111', '', 280, 0);
/*!40000 ALTER TABLE `base_cadre` ENABLE KEYS */;


-- 导出  表 owip.base_dispatch 结构
CREATE TABLE IF NOT EXISTS `base_dispatch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `year` int(10) unsigned NOT NULL COMMENT '年份',
  `type_id` int(10) unsigned NOT NULL COMMENT '发文类型，关联元数据',
  `code` varchar(50) NOT NULL COMMENT '发文号，自动生成，比如师党干[2015]年01号',
  `meeting_time` date NOT NULL COMMENT '党委常委会日期',
  `pub_time` date NOT NULL COMMENT '发文日期',
  `work_time` date NOT NULL COMMENT '任免日期',
  `file` varchar(200) DEFAULT NULL COMMENT '任免文件',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `ppt` varchar(200) DEFAULT NULL COMMENT '上会ppt',
  `ppt_name` varchar(100) DEFAULT NULL COMMENT 'ppt文件名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_dispatch_base_meta_type` (`type_id`),
  CONSTRAINT `FK_dispatch_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='发文';

-- 正在导出表  owip.base_dispatch 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `base_dispatch` DISABLE KEYS */;
INSERT INTO `base_dispatch` (`id`, `year`, `type_id`, `code`, `meeting_time`, `pub_time`, `work_time`, `file`, `file_name`, `ppt`, `ppt_name`, `remark`, `sort_order`) VALUES
	(1, 2015, 33, '师任[2015]年01号', '2015-11-12', '2015-11-20', '2015-11-26', '\\dispatch\\2015\\file\\91f817ad-1aa5-40f8-b6c9-be3164769f92.png', 'toolbar_news.png', '\\dispatch\\2015\\ppt\\d9b13c29-80b8-4bc7-a3ca-9cc244fa1d74.png', 'toolbar_calender.png', '', 28);
/*!40000 ALTER TABLE `base_dispatch` ENABLE KEYS */;


-- 导出  表 owip.base_dispatch_cadre 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_cadre` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '所属发文',
  `type_id` int(10) unsigned NOT NULL COMMENT '类型，关联元数据, 师党干、师任',
  `way_id` int(10) unsigned NOT NULL COMMENT '任免方式，关联元数据（1 提任 2连任 3平级调动 4免职）',
  `procedure_id` int(10) unsigned NOT NULL COMMENT '任免程序，关联元数据（1 民主推荐 2公开招聘 3引进人才 4其他 5免职）',
  `code` varchar(50) NOT NULL COMMENT '工作证号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `admin_level_id` int(10) unsigned NOT NULL COMMENT '行政级别，关联元数据',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_dispatch_cadre_dispatch` (`dispatch_id`),
  KEY `FK_dispatch_cadre_base_meta_type` (`type_id`),
  KEY `FK_dispatch_cadre_base_meta_type_2` (`way_id`),
  KEY `FK_dispatch_cadre_base_meta_type_3` (`procedure_id`),
  KEY `FK_dispatch_cadre_base_unit` (`unit_id`),
  KEY `FK_dispatch_cadre_base_meta_type_4` (`post_id`),
  KEY `FK_dispatch_cadre_base_meta_type_5` (`admin_level_id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_2` FOREIGN KEY (`way_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_3` FOREIGN KEY (`procedure_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_4` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_5` FOREIGN KEY (`admin_level_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_cadre_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `base_dispatch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='干部发文，将发文进行干部认领，标识为干部任免发文';

-- 正在导出表  owip.base_dispatch_cadre 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `base_dispatch_cadre` DISABLE KEYS */;
INSERT INTO `base_dispatch_cadre` (`id`, `dispatch_id`, `type_id`, `way_id`, `procedure_id`, `code`, `name`, `post_id`, `admin_level_id`, `unit_id`, `remark`, `sort_order`) VALUES
	(1, 1, 38, 41, 44, '000', '小刘', 13, 18, 1, '', 57);
/*!40000 ALTER TABLE `base_dispatch_cadre` ENABLE KEYS */;


-- 导出  表 owip.base_dispatch_unit 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '发文',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `type_id` int(10) unsigned NOT NULL COMMENT '类型，关联元数据，1成立/2更名/3撤销',
  `year` int(10) unsigned NOT NULL COMMENT '年份',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_dispatch_unit_dispatch` (`dispatch_id`),
  KEY `FK_dispatch_unit_base_unit` (`unit_id`),
  KEY `FK_dispatch_unit_base_meta_type` (`type_id`),
  CONSTRAINT `FK_dispatch_unit_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_unit_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_unit_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `base_dispatch` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位发文，将发文进行单位认领';

-- 正在导出表  owip.base_dispatch_unit 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `base_dispatch_unit` DISABLE KEYS */;
INSERT INTO `base_dispatch_unit` (`id`, `dispatch_id`, `unit_id`, `type_id`, `year`, `remark`, `sort_order`) VALUES
	(1, 1, 10, 36, 2014, '', 56);
/*!40000 ALTER TABLE `base_dispatch_unit` ENABLE KEYS */;


-- 导出  表 owip.base_dispatch_unit_relate 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_unit_relate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_unit_id` int(10) unsigned NOT NULL COMMENT '单位发文',
  `unit_id` int(10) unsigned NOT NULL COMMENT '关联单位',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dispatch_unit_id_unit_id` (`dispatch_unit_id`,`unit_id`),
  KEY `FK_dispatch_unit_relate_base_unit` (`unit_id`),
  CONSTRAINT `FK_dispatch_unit_relate_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_unit_relate_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位发文关联单位';

-- 正在导出表  owip.base_dispatch_unit_relate 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `base_dispatch_unit_relate` DISABLE KEYS */;
INSERT INTO `base_dispatch_unit_relate` (`id`, `dispatch_unit_id`, `unit_id`, `sort_order`) VALUES
	(4, 1, 1, 75),
	(5, 1, 10, 74);
/*!40000 ALTER TABLE `base_dispatch_unit_relate` ENABLE KEYS */;


-- 导出  表 owip.base_history_unit 结构
CREATE TABLE IF NOT EXISTS `base_history_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `unit_id` int(10) unsigned NOT NULL COMMENT '单位',
  `old_unit_id` int(10) unsigned NOT NULL COMMENT '历史单位',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_id_old_unit_id` (`unit_id`,`old_unit_id`),
  KEY `FK_base_history_unit_base_unit_2` (`old_unit_id`),
  CONSTRAINT `FK_base_history_unit_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_base_history_unit_base_unit_2` FOREIGN KEY (`old_unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='历史单位，单位的历史单位记录';

-- 正在导出表  owip.base_history_unit 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `base_history_unit` DISABLE KEYS */;
INSERT INTO `base_history_unit` (`id`, `unit_id`, `old_unit_id`, `sort_order`, `create_time`) VALUES
	(1, 10, 12, 88, '2015-11-15 10:50:30'),
	(2, 10, 11, 89, '2015-11-15 10:50:32'),
	(3, 1, 12, 98, '2015-11-15 10:53:03'),
	(4, 1, 11, 97, '2015-11-15 10:53:05');
/*!40000 ALTER TABLE `base_history_unit` ENABLE KEYS */;


-- 导出  表 owip.base_leader 结构
CREATE TABLE IF NOT EXISTS `base_leader` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '校领导，关联干部',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据，党委、行政、校长助理等',
  `job` varchar(200) NOT NULL COMMENT '分管工作',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cadre_id_type_id` (`cadre_id`,`type_id`),
  KEY `FK_base_leader_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_leader_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_leader_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='校领导';

-- 正在导出表  owip.base_leader 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `base_leader` DISABLE KEYS */;
INSERT INTO `base_leader` (`id`, `cadre_id`, `type_id`, `job`, `sort_order`) VALUES
	(2, 5, 24, '1111', 287),
	(6, 6, 24, 'sdfsdf', 293),
	(7, 6, 23, 'sdfsdf', 294);
/*!40000 ALTER TABLE `base_leader` ENABLE KEYS */;


-- 导出  表 owip.base_leader_unit 结构
CREATE TABLE IF NOT EXISTS `base_leader_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `leader_id` int(10) unsigned NOT NULL COMMENT '校领导',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `type_id` int(10) unsigned DEFAULT NULL COMMENT '类别，关联元数据1：分管部门  2：联系院系所',
  PRIMARY KEY (`id`),
  UNIQUE KEY `leader_id_unit_id_type_id` (`leader_id`,`unit_id`,`type_id`),
  KEY `FK_base_leader_unit_base_unit` (`unit_id`),
  KEY `FK_base_leader_unit_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_leader_unit_base_leader` FOREIGN KEY (`leader_id`) REFERENCES `base_leader` (`id`),
  CONSTRAINT `FK_base_leader_unit_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_leader_unit_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='校领导单位，校领导关联的单位，比如分管机关部门、联系部、院、系所';

-- 正在导出表  owip.base_leader_unit 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_leader_unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_leader_unit` ENABLE KEYS */;


-- 导出  表 owip.base_meta_class 结构
CREATE TABLE IF NOT EXISTS `base_meta_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '名称，行政级别、发文类型',
  `code` varchar(50) NOT NULL COMMENT '代码，编程代码（随机生成，可修改）',
  `bool_attr` varchar(20) DEFAULT NULL COMMENT '布尔属性名称，元数据类型中bool_attr字段代表的意思，比如是否是正职（职务属性中）',
  `extra_attr` varchar(100) DEFAULT NULL COMMENT '附加属性名称，元数据类型中extra_attr字段代表的意思，比如党务、行政（发文类型中）',
  `sort_order` int(11) unsigned NOT NULL COMMENT '排序',
  `available` tinyint(1) unsigned NOT NULL COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='元数据分类';

-- 正在导出表  owip.base_meta_class 的数据：~14 rows (大约)
/*!40000 ALTER TABLE `base_meta_class` DISABLE KEYS */;
INSERT INTO `base_meta_class` (`id`, `name`, `code`, `bool_attr`, `extra_attr`, `sort_order`, `available`) VALUES
	(5, '行政级别', 'mc_admin_level', '', '', 3, 1),
	(7, '职务属性', 'mc_post', '是否正职', '', 5, 1),
	(8, '单位类型', 'mc_unit_type', '', '', 36, 1),
	(9, '校领导分类', 'mc_leader_type', '', '', 1, 1),
	(10, '系统日志', 'mc_sys_log', '', '', 1, 1),
	(11, '校领导联系单位类别', 'mc_leader_unit', '', '', 18, 1),
	(12, '发文类型', 'mc_dispatch', '', '发文属性', 12, 1),
	(13, '单位发文类型', 'mc_dispatch_unit', '', '', 29, 1),
	(14, '干部发文类型', 'mc_dispatch_cadre', '', '', 37, 1),
	(15, '干部任免方式', 'mc_dispatch_cadre_way', '', '', 41, 1),
	(16, '干部任免程序', 'mc_dispatch_cadre_procedure', '', '', 42, 1),
	(17, '账号分类', 'mc_user_type', '', '', 220, 1),
	(18, '账号来源', 'mc_user_source', '', '', 333, 1),
	(19, '党总支类别', 'mc_party_class', '', '', 344, 1),
	(20, '基层党组织类别', 'mc_part_type', '', '', 351, 1),
	(21, '党总支所在单位属性', 'mc_party_unit_type', '', '', 358, 1),
	(22, '基层党组织成员类别', 'mc_party_member_type', '', '', 361, 1),
	(23, '党支部类别', 'mc_branch_type', '', '', 371, 1),
	(24, '在职教工党支部类型', 'mc_branch_staff_type', '', '', 381, 1),
	(25, '党支部所在单位属性', 'mc_branch_unit_type', '', '', 384, 1);
/*!40000 ALTER TABLE `base_meta_class` ENABLE KEYS */;


-- 导出  表 owip.base_meta_type 结构
CREATE TABLE IF NOT EXISTS `base_meta_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `class_id` int(10) unsigned NOT NULL COMMENT '所属分类',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '代码，编程代码，（暂时未用）',
  `bool_attr` tinyint(1) unsigned DEFAULT NULL COMMENT '布尔属性',
  `extra_attr` varchar(100) DEFAULT NULL COMMENT '附加属性',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序，在各个分类中进行',
  `available` tinyint(1) unsigned NOT NULL COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_base_meta_type_base_meta_class` (`class_id`),
  CONSTRAINT `FK_base_meta_type_base_meta_class` FOREIGN KEY (`class_id`) REFERENCES `base_meta_class` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='元数据属性，元数据分类的取值';

-- 正在导出表  owip.base_meta_type 的数据：~48 rows (大约)
/*!40000 ALTER TABLE `base_meta_type` DISABLE KEYS */;
INSERT INTO `base_meta_type` (`id`, `class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES
	(7, 8, '校领导班子', 'MC:ow2r7r', NULL, '', '', 10, 1),
	(8, 8, '学部、院、系所', 'MC:wdhgh4', NULL, '', '', 7, 1),
	(9, 8, '机关职能部处', 'mc:iw29q5', NULL, '', '', 8, 1),
	(10, 8, '机关党总支', 'MC:buxulv', NULL, '', '', 9, 1),
	(11, 8, '直属单位', 'mc:7kk9wz', NULL, '', '', 12, 1),
	(12, 8, '附属单位', 'mc:ryu2r6', NULL, '', '', 11, 1),
	(13, 7, '院系党委正职', 'mt_ufqgrk', 1, '', '', 15, 1),
	(14, 7, '院系行政正职', 'mt_k4zztd', 1, '', '', 14, 1),
	(15, 7, '院系党委副职', 'mt_o9zjdb', 0, '', '', 13, 1),
	(16, 7, '院系行政副职', 'mt_d7dkjv', 0, '', '', 1, 1),
	(17, 5, '副部级', 'mt_rgdnjq', NULL, '', '', 22, 1),
	(18, 5, '正局级', 'mt_5va6i5', NULL, '', '', 21, 1),
	(19, 5, '副局级', 'mt_2eqioj', NULL, '', '', 20, 1),
	(20, 5, '正处级', 'mt_vbtibq', NULL, '', '', 19, 1),
	(21, 5, '副处级', 'mt_mstbs1', NULL, '', '', 18, 1),
	(22, 5, '无行政级别', 'mt_ofdjsp', NULL, '', '', 17, 1),
	(23, 9, '党委', 'mt_nnzsmo', NULL, '', '', 25, 1),
	(24, 9, '行政', 'mt_yhvkia', NULL, '', '', 24, 1),
	(25, 9, '校长助理', 'mt_spopub', NULL, '', '', 23, 1),
	(26, 10, '系统管理', 'mt_log_admin', NULL, '', '', 29, 1),
	(27, 10, '单位管理', 'mt_log_unit', NULL, '', '', 26, 1),
	(28, 10, '干部管理', 'mt_log_cadre', NULL, '', '', 25, 1),
	(29, 11, '分管部门', 'mt_byo5b7', NULL, '', '', 19, 1),
	(30, 11, '联系院系所', 'mt_varouj', NULL, '', '', 20, 1),
	(31, 10, '用户登录', 'mt_log_login', NULL, '', '', 28, 1),
	(32, 12, '师党干', 'mt_sdg', NULL, '党务', '', 14, 1),
	(33, 12, '师任', 'mt_sr', NULL, '行政', '', 15, 1),
	(34, 13, '成立', 'mt_en7j7t', NULL, '', '', 30, 1),
	(35, 13, '更名', 'mt_z6egdc', NULL, '', '', 31, 1),
	(36, 13, '撤销', 'mt_ucgja5', NULL, '', '', 32, 1),
	(37, 14, '师党干', 'mt_kdnfxl', NULL, '', '', 37, 1),
	(38, 14, '师任', 'mt_nobz7w', NULL, '', '', 38, 1),
	(39, 15, '提任', 'mt_ks6ftt', NULL, '', '', 43, 1),
	(40, 15, '连任', 'mt_79dn5v', NULL, '', '', 44, 1),
	(41, 15, '平级调动', 'mt_ftesxg', NULL, '', '', 45, 1),
	(42, 15, '免职', 'mt_vpbxr7', NULL, '', '', 46, 1),
	(43, 16, '民主推荐', 'mt_zpyftu', NULL, '', '', 49, 1),
	(44, 16, '公开招聘', 'mt_viwcjw', NULL, '', '', 50, 1),
	(45, 16, '引进人才', 'mt_qhuvas', NULL, '', '', 51, 1),
	(46, 16, '免职', 'mt_huxuxr', NULL, '', '', 48, 1),
	(47, 16, '其他', 'mt_b9tpdq', NULL, '', '', 47, 1),
	(48, 17, '学生', 'mt_digq10', NULL, '', '', 223, 1),
	(49, 17, '教师', 'mt_mu75ma', NULL, '', '', 222, 1),
	(50, 17, '其他', 'mt_gebpzb', NULL, '', '', 221, 1),
	(51, 10, '组织工作', 'mt_log_ow', NULL, '', '', 27, 1),
	(52, 18, '后台创建', 'mt_user_source_admin', NULL, '', '', 338, 1),
	(53, 18, '人事库', 'mt_user_source_personnel', NULL, '', '', 337, 1),
	(54, 18, '学生库', 'mt_user_source_student', NULL, '', '', 336, 1),
	(55, 19, '分党委', 'mt_vuqolb', NULL, '', '', 347, 1),
	(56, 19, '党总支', 'mt_hybqt0', NULL, '', '', 346, 1),
	(57, 19, '直属党支部', 'mt_vtbgr8', NULL, '', '', 345, 1),
	(58, 20, '院系党组织', 'mt_v7fwhz', NULL, '', '', 354, 1),
	(59, 20, '机关后勤直属单位党组织', 'mt_cnjlss', NULL, '', '', 353, 1),
	(60, 20, '附属学校党组织', 'mt_dp2idq', NULL, '', '', 352, 1),
	(61, 21, '事业单位', 'mt_6noq3v', NULL, '', '', 359, 1),
	(62, 21, '企业', 'mt_tqvqnt', NULL, '', '', 360, 1),
	(63, 22, '副书记', 'mt_mvblt2', NULL, '', '', 365, 1),
	(64, 22, '书记', 'mt_3mxciy', NULL, '', '', 366, 1),
	(65, 22, '组织委员', 'mt_ignnjh', NULL, '', '', 362, 1),
	(66, 22, '统战委员', 'mt_tmnzcp', NULL, '', '', 364, 1),
	(67, 22, '宣传委员', 'mt_wvj37j', NULL, '', '', 363, 1),
	(68, 23, '本硕博混合党支部', 'mt_jpzrke', NULL, '', '', 372, 1),
	(69, 23, '硕博混合党支部', 'mt_u79s7q', NULL, '', '', 373, 1),
	(70, 23, '博士生党支部', 'mt_mdeujy', NULL, '', '', 374, 1),
	(71, 23, '硕士生党支部', 'mt_jrh1xm', NULL, '', '', 375, 1),
	(72, 23, '本科生党支部', 'mt_etjzky', NULL, '', '', 376, 1),
	(73, 23, '离退休党支部', 'mt_b0elcr', NULL, '', '', 377, 1),
	(74, 23, '在职教工党支部', 'mt_qnwbhf', NULL, '', '', 378, 1),
	(76, 24, '非专业教师党支部', 'mt_hvddvn', NULL, '', '', 382, 1),
	(77, 24, '专业教师党支部', 'mt_yegxjf', NULL, '', '', 383, 1),
	(78, 25, '事业单位', 'mt_swerju', NULL, '', '', 385, 1),
	(79, 25, '企业', 'mt_4wryit', NULL, '', '', 386, 1);
/*!40000 ALTER TABLE `base_meta_type` ENABLE KEYS */;


-- 导出  表 owip.base_table 结构
CREATE TABLE IF NOT EXISTS `base_table` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '表名',
  `name` varchar(50) NOT NULL COMMENT '中文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据表';

-- 正在导出表  owip.base_table 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_table` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_table` ENABLE KEYS */;


-- 导出  表 owip.base_table_column 结构
CREATE TABLE IF NOT EXISTS `base_table_column` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `table_id` varchar(100) NOT NULL COMMENT '关联表',
  `name` varchar(100) NOT NULL COMMENT '字段中文名',
  `code` varchar(20) NOT NULL COMMENT '字段代码',
  `to_query` tinyint(1) unsigned NOT NULL COMMENT '是否用于查询',
  `to_list` tinyint(1) unsigned NOT NULL COMMENT '列表是否显示',
  `to_export` tinyint(1) unsigned NOT NULL COMMENT '是否用于导出',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_code_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表字段';

-- 正在导出表  owip.base_table_column 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_table_column` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_table_column` ENABLE KEYS */;


-- 导出  表 owip.base_unit 结构
CREATE TABLE IF NOT EXISTS `base_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(10) NOT NULL COMMENT '单位编号，唯一',
  `name` varchar(50) NOT NULL COMMENT '单位名称',
  `type_id` int(10) unsigned NOT NULL COMMENT '单位类型，关联元数据',
  `work_time` datetime NOT NULL COMMENT '成立时间',
  `url` varchar(200) DEFAULT NULL COMMENT '单位网址',
  `remark` text COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态，1正在运转单位、2历史单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_base_unit_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_unit_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='单位';

-- 正在导出表  owip.base_unit 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `base_unit` DISABLE KEYS */;
INSERT INTO `base_unit` (`id`, `code`, `name`, `type_id`, `work_time`, `url`, `remark`, `create_time`, `sort_order`, `status`) VALUES
	(1, '001', '艺术学院', 8, '2015-11-19 00:00:00', 'http://test.com', '测试11', '2015-11-12 15:50:02', 5, 1),
	(10, '111', '111', 7, '2015-11-20 00:00:00', '', '', '2015-11-13 17:41:00', 6, 1),
	(11, 'test', 'sdfsd', 11, '2015-11-25 00:00:00', '', '', '2015-11-15 10:50:13', 86, 2),
	(12, 'sdf', 'sdf', 11, '2015-11-19 00:00:00', '', '', '2015-11-15 10:50:18', 87, 2);
/*!40000 ALTER TABLE `base_unit` ENABLE KEYS */;


-- 导出  表 owip.base_unit_cadre_transfer 结构
CREATE TABLE IF NOT EXISTS `base_unit_cadre_transfer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属分组',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '关联干部',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `appoint_time` date NOT NULL COMMENT '任职日期',
  `dismiss_time` date NOT NULL COMMENT '免职日期',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_cadre_id` (`group_id`,`cadre_id`),
  KEY `FK_base_unit_cadre_transfer_base_meta_type` (`post_id`),
  KEY `FK_base_unit_cadre_transfer_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位任免记录，按组划分干部任免的发文';

-- 正在导出表  owip.base_unit_cadre_transfer 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_unit_cadre_transfer` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_unit_cadre_transfer` ENABLE KEYS */;


-- 导出  表 owip.base_unit_cadre_transfer_group 结构
CREATE TABLE IF NOT EXISTS `base_unit_cadre_transfer_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `name` varchar(50) NOT NULL COMMENT '分组名称，（比如“历任书记”）',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_id_name` (`unit_id`,`name`),
  CONSTRAINT `FK_base_unit_cadre_transfer_group_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='单位任免分组，干部任免记录分组';

-- 正在导出表  owip.base_unit_cadre_transfer_group 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `base_unit_cadre_transfer_group` DISABLE KEYS */;
INSERT INTO `base_unit_cadre_transfer_group` (`id`, `unit_id`, `name`, `sort_order`) VALUES
	(1, 1, '水水水水', 173);
/*!40000 ALTER TABLE `base_unit_cadre_transfer_group` ENABLE KEYS */;


-- 导出  表 owip.base_unit_cadre_transfer_item 结构
CREATE TABLE IF NOT EXISTS `base_unit_cadre_transfer_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `transfer_id` int(10) unsigned NOT NULL COMMENT '所属任免',
  `dispatch_cadre_id` int(10) unsigned NOT NULL COMMENT '干部发文，关联干部任免发文',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transfer_id_dispatch_cadre_id` (`transfer_id`,`dispatch_cadre_id`),
  KEY `FK_base_unit_cadre_transfer_item_base_dispatch_cadre` (`dispatch_cadre_id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_item_base_dispatch_cadre` FOREIGN KEY (`dispatch_cadre_id`) REFERENCES `base_dispatch_cadre` (`id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_item_base_unit_cadre_transfer` FOREIGN KEY (`transfer_id`) REFERENCES `base_unit_cadre_transfer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位任免记录关联发文';

-- 正在导出表  owip.base_unit_cadre_transfer_item 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_unit_cadre_transfer_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_unit_cadre_transfer_item` ENABLE KEYS */;


-- 导出  表 owip.base_unit_transfer 结构
CREATE TABLE IF NOT EXISTS `base_unit_transfer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `subject` varchar(200) NOT NULL COMMENT '文件主题',
  `content` longtext COMMENT '文件具体内容',
  `pub_time` date NOT NULL COMMENT '日期',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_base_unit_transfer_base_unit` (`unit_id`),
  CONSTRAINT `FK_base_unit_transfer_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='单位变更';

-- 正在导出表  owip.base_unit_transfer 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `base_unit_transfer` DISABLE KEYS */;
INSERT INTO `base_unit_transfer` (`id`, `unit_id`, `subject`, `content`, `pub_time`, `sort_order`) VALUES
	(1, 12, 'test', '水电费', '2015-11-13', 191);
/*!40000 ALTER TABLE `base_unit_transfer` ENABLE KEYS */;


-- 导出  表 owip.base_unit_transfer_item 结构
CREATE TABLE IF NOT EXISTS `base_unit_transfer_item` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `transfer_id` int(10) unsigned NOT NULL COMMENT '所属单位变更',
  `dispatch_unit_id` int(10) unsigned NOT NULL COMMENT '相关发文',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `transfer_id_dispatch_unit_id` (`transfer_id`,`dispatch_unit_id`),
  KEY `FK_base_unit_transfer_item_base_dispatch_unit` (`dispatch_unit_id`),
  CONSTRAINT `FK_base_unit_transfer_item_base_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`),
  CONSTRAINT `FK_base_unit_transfer_item_base_unit_transfer` FOREIGN KEY (`transfer_id`) REFERENCES `base_unit_transfer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位变更关联的单位发文';

-- 正在导出表  owip.base_unit_transfer_item 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `base_unit_transfer_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `base_unit_transfer_item` ENABLE KEYS */;


-- 导出  表 owip.ow_branch 结构
CREATE TABLE IF NOT EXISTS `ow_branch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称，需用全称：院系+支部名称',
  `short_name` varchar(20) NOT NULL COMMENT '简称',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属党总支',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据，在职教工党支部、离退休党支部、本科生党支部、硕士生党支部、博士生党支部、硕博混合党支部、本硕博混合党支部',
  `staff_type_id` int(10) unsigned NOT NULL COMMENT '在职教工党支部类型，关联元数据，专业教师党支部、非专业教师党支部',
  `unit_type_id` int(10) unsigned NOT NULL COMMENT '单位属性，关联元数据，企业、事业单位',
  `is_enterprise_big` tinyint(1) unsigned NOT NULL COMMENT '是否为大中型，针对企业单位',
  `is_enterprise_nationalized` tinyint(1) unsigned NOT NULL COMMENT '是否国有独资，针对企业单位，否：国有控股',
  `is_union` tinyint(1) unsigned NOT NULL COMMENT '是否联合党支部',
  `phone` varchar(11) NOT NULL COMMENT '联系电话',
  `fax` varchar(20) NOT NULL COMMENT '传真',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `found_time` date NOT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_ow_branch_ow_party` (`party_id`),
  CONSTRAINT `FK_ow_branch_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党支部';

-- 正在导出表  owip.ow_branch 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ow_branch` DISABLE KEYS */;
/*!40000 ALTER TABLE `ow_branch` ENABLE KEYS */;


-- 导出  表 owip.ow_party 结构
CREATE TABLE IF NOT EXISTS `ow_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `short_name` varchar(20) NOT NULL COMMENT '简称',
  `url` varchar(200) NOT NULL COMMENT '网址',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `class_id` int(10) unsigned NOT NULL COMMENT '党总支类别，关联元数据，分党委、党总支、直属党支部',
  `type_id` int(10) unsigned NOT NULL COMMENT '组织类别，关联元数据，院系党组织、机关后勤直属单位党组织、附属学校党组织',
  `unit_type_id` int(10) unsigned NOT NULL COMMENT '所在单位属性，关联元数据，企业，事业单位',
  `is_enterprise_big` tinyint(1) unsigned NOT NULL COMMENT '是否大中型，针对企业单位',
  `is_enterprise_nationalized` tinyint(1) unsigned NOT NULL COMMENT '是否国有独资，针对企业单位，否：国有控股',
  `is_separate` int(11) NOT NULL COMMENT '所在单位是否独立法人',
  `phone` varchar(11) NOT NULL COMMENT '联系电话',
  `fax` varchar(20) NOT NULL COMMENT '传真',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `found_time` varchar(200) NOT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_ow_party_base_meta_type` (`class_id`),
  KEY `FK_ow_party_base_meta_type_2` (`type_id`),
  KEY `FK_ow_party_base_unit` (`unit_id`),
  KEY `FK_ow_party_base_meta_type_3` (`unit_type_id`),
  CONSTRAINT `FK_ow_party_base_meta_type` FOREIGN KEY (`class_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_meta_type_2` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_meta_type_3` FOREIGN KEY (`unit_type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织';

-- 正在导出表  owip.ow_party 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ow_party` DISABLE KEYS */;
/*!40000 ALTER TABLE `ow_party` ENABLE KEYS */;


-- 导出  表 owip.ow_party_member 结构
CREATE TABLE IF NOT EXISTS `ow_party_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属班子',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号，除了书记、副书记其他委员一般没有行政级别，不属于干部',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据（书记、副书记、各类委员）',
  `is_admin` tinyint(1) unsigned NOT NULL COMMENT '是否管理员',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_user_id` (`group_id`,`user_id`),
  KEY `FK_ow_party_member_sys_user` (`user_id`),
  KEY `FK_ow_party_member_base_meta_type` (`type_id`),
  CONSTRAINT `FK_ow_party_member_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_member_ow_party_member_group` FOREIGN KEY (`group_id`) REFERENCES `ow_party_member_group` (`id`),
  CONSTRAINT `FK_ow_party_member_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织成员';

-- 正在导出表  owip.ow_party_member 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ow_party_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `ow_party_member` ENABLE KEYS */;


-- 导出  表 owip.ow_party_member_group 结构
CREATE TABLE IF NOT EXISTS `ow_party_member_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属党总支',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任班子',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date NOT NULL COMMENT '任命时间，本届班子任命时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_ow_party_member_group_ow_party` (`party_id`),
  KEY `FK_ow_party_member_group_base_dispatch_unit` (`dispatch_unit_id`),
  CONSTRAINT `FK_ow_party_member_group_base_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`),
  CONSTRAINT `FK_ow_party_member_group_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织领导班子';

-- 正在导出表  owip.ow_party_member_group 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `ow_party_member_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `ow_party_member_group` ENABLE KEYS */;


-- 导出  表 owip.sys_log 结构
CREATE TABLE IF NOT EXISTS `sys_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(11) DEFAULT '0' COMMENT '账号ID',
  `operator` varchar(50) DEFAULT NULL COMMENT '账号',
  `content` text COMMENT '内容',
  `type_id` int(10) unsigned DEFAULT NULL COMMENT '日志类型，关联元数据，此处不设定约束，可以为空或其他的数据',
  `api` varchar(50) DEFAULT NULL COMMENT 'api地址',
  `agent` varchar(255) DEFAULT NULL COMMENT '浏览器型号',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `status` tinyint(3) DEFAULT '0' COMMENT '状态',
  `ip` varchar(100) DEFAULT NULL COMMENT 'IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志';

-- 正在导出表  owip.sys_log 的数据：~451 rows (大约)
/*!40000 ALTER TABLE `sys_log` DISABLE KEYS */;
INSERT INTO `sys_log` (`id`, `user_id`, `operator`, `content`, `type_id`, `api`, `agent`, `create_time`, `status`, `ip`) VALUES
	(1, 5, 'admin', '添加校领导单位：null', 26, 'leaderUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 13:21:51', 1, '127.0.0.1'),
	(2, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:04:39', 1, '127.0.0.1'),
	(3, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:45:34', 1, '127.0.0.1'),
	(4, 5, 'admin', '更新资源：{"id":82,"name":"发文","type":"url","sortOrder":5,"menuCss":"","url":"/dispatch","parentId":61,"parentIds":"0/1/61/","permission":"dispatch:dispatch","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:46:00', 1, '127.0.0.1'),
	(5, 5, 'admin', '更新资源：{"id":61,"name":"发文管理","type":"menu","sortOrder":5,"menuCss":"fa fa-files-o","url":"","parentId":1,"parentIds":"0/1/","permission":"dispatch:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:46:07', 1, '127.0.0.1'),
	(6, 5, 'admin', '更新资源：{"id":81,"name":"单位发文","type":"url","sortOrder":4,"menuCss":"","url":"/dispatchUnit","parentId":61,"parentIds":"0/1/61/","permission":"dispatch:unit","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:46:18', 1, '127.0.0.1'),
	(7, 5, 'admin', '更新资源：{"id":80,"name":"干部任免","type":"url","sortOrder":3,"menuCss":"","url":"/dispatchCadre","parentId":61,"parentIds":"0/1/61/","permission":"dispatch:cadre","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:46:29', 1, '127.0.0.1'),
	(8, 5, 'admin', '更新资源：{"id":82,"name":"发文","type":"url","sortOrder":5,"menuCss":"","url":"/dispatch","parentId":61,"parentIds":"0/1/61/","permission":"dispatch:dispatch","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:52:15', 1, '127.0.0.1'),
	(9, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,83,66,78,77,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:53:16', 1, '127.0.0.1'),
	(10, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 14:54:50', 1, '127.0.0.1'),
	(11, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:34:39', 1, '127.0.0.1'),
	(12, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:38:26', 1, '127.0.0.1'),
	(13, 5, 'admin', '更新元数据：12', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:38:40', 1, '127.0.0.1'),
	(14, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:39:04', 1, '127.0.0.1'),
	(15, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:39:15', 1, '127.0.0.1'),
	(16, 5, 'admin', '更新元数据属性值：33', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:39:31', 1, '127.0.0.1'),
	(17, 5, 'admin', '更新元数据属性值：32', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:39:36', 1, '127.0.0.1'),
	(18, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:43:52', 1, '127.0.0.1'),
	(19, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 17:45:13', 1, '127.0.0.1'),
	(20, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 20:36:55', 1, '127.0.0.1'),
	(21, 5, 'admin', '更新元数据属性值：33', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 20:53:25', 1, '127.0.0.1'),
	(22, 5, 'admin', '更新元数据属性值：32', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 20:53:31', 1, '127.0.0.1'),
	(23, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:29:15', 1, '127.0.0.1'),
	(24, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:53:29', 1, '127.0.0.1'),
	(25, 5, 'admin', '更新元数据属性值：33', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:54:02', 1, '127.0.0.1'),
	(26, 5, 'admin', '更新元数据属性值：32', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:54:06', 1, '127.0.0.1'),
	(27, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:56:17', 1, '127.0.0.1'),
	(28, 5, 'admin', '添加发文：null', 26, 'dispatch_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 21:56:21', 1, '127.0.0.1'),
	(29, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 22:12:26', 1, '127.0.0.1'),
	(30, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 22:12:48', 1, '127.0.0.1'),
	(31, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 22:12:55', 1, '127.0.0.1'),
	(32, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 22:13:01', 1, '127.0.0.1'),
	(33, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:13:33', 1, '127.0.0.1'),
	(34, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:28:09', 1, '127.0.0.1'),
	(35, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:42:08', 1, '127.0.0.1'),
	(36, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:58:05', 1, '127.0.0.1'),
	(37, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:58:54', 1, '127.0.0.1'),
	(38, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-14 23:58:58', 1, '127.0.0.1'),
	(39, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:02:53', 1, '127.0.0.1'),
	(40, 5, 'admin', '元数据调序：8, -1', 26, 'metaClass_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:03:42', 1, '127.0.0.1'),
	(41, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:04:58', 1, '127.0.0.1'),
	(42, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:05:33', 1, '127.0.0.1'),
	(43, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:07', 1, '127.0.0.1'),
	(44, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:13', 1, '127.0.0.1'),
	(45, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:18', 1, '127.0.0.1'),
	(46, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:25', 1, '127.0.0.1'),
	(47, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:35', 1, '127.0.0.1'),
	(48, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:41', 1, '127.0.0.1'),
	(49, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:47', 1, '127.0.0.1'),
	(50, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:53', 1, '127.0.0.1'),
	(51, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:06:59', 1, '127.0.0.1'),
	(52, 5, 'admin', '元数据属性值调序：43, 10', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:07:02', 1, '127.0.0.1'),
	(53, 5, 'admin', '元数据属性值调序：44, 10', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:07:07', 1, '127.0.0.1'),
	(54, 5, 'admin', '元数据属性值调序：45, 10', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:07:10', 1, '127.0.0.1'),
	(55, 5, 'admin', '元数据属性值调序：46, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:07:13', 1, '127.0.0.1'),
	(56, 5, 'admin', '添加单位发文：null', 26, 'dispatchUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:07:59', 1, '127.0.0.1'),
	(57, 5, 'admin', '添加干部发文：null', 26, 'dispatchCadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:11:27', 1, '127.0.0.1'),
	(58, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 00:16:38', 1, '127.0.0.1'),
	(59, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:08:53', 1, '127.0.0.1'),
	(60, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:50:31', 1, '127.0.0.1'),
	(61, 5, 'admin', '更新发文：1', 26, 'dispatch_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:50:45', 1, '127.0.0.1'),
	(62, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:52:14', 1, '127.0.0.1'),
	(63, 5, 'admin', '更新发文：1', 26, 'dispatch_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:52:25', 1, '127.0.0.1'),
	(64, 5, 'admin', '更新干部发文：1', 26, 'dispatchCadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 09:58:41', 1, '127.0.0.1'),
	(65, 5, 'admin', '添加资源：{"name":"下载","type":"function","menuCss":"","url":"","parentId":82,"parentIds":"0/1/61/82/","permission":"dispatch:download","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:06:26', 1, '127.0.0.1'),
	(66, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,160,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,83,66,78,77,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:06:51', 1, '127.0.0.1'),
	(67, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:31:35', 1, '127.0.0.1'),
	(68, 5, 'admin', '添加单位发文关联单位：null', 26, 'dispatchUnitRelate_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:32:38', 1, '127.0.0.1'),
	(69, 5, 'admin', '删除单位发文关联单位：1', 26, 'dispatchUnitRelate_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:33:16', 1, '127.0.0.1'),
	(70, 5, 'admin', '添加单位发文关联单位：null', 26, 'dispatchUnitRelate_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:00', 1, '127.0.0.1'),
	(71, 5, 'admin', '删除单位发文关联单位：2', 26, 'dispatchUnitRelate_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:30', 1, '127.0.0.1'),
	(72, 5, 'admin', '添加单位发文关联单位：null', 26, 'dispatchUnitRelate_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:51', 1, '127.0.0.1'),
	(73, 5, 'admin', '删除单位发文关联单位：3', 26, 'dispatchUnitRelate_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:53', 1, '127.0.0.1'),
	(74, 5, 'admin', '添加单位发文关联单位：null', 26, 'dispatchUnitRelate_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:55', 1, '127.0.0.1'),
	(75, 5, 'admin', '添加单位发文关联单位：null', 26, 'dispatchUnitRelate_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:35:57', 1, '127.0.0.1'),
	(76, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:37:31', 1, '127.0.0.1'),
	(77, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:37:49', 1, '127.0.0.1'),
	(78, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:44:19', 1, '127.0.0.1'),
	(79, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:48:17', 1, '127.0.0.1'),
	(80, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:48:23', 1, '127.0.0.1'),
	(81, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:48:25', 1, '127.0.0.1'),
	(82, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:48:40', 1, '127.0.0.1'),
	(83, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:48:42', 1, '127.0.0.1'),
	(84, 5, 'admin', '单位调序：1, 1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:04', 1, '127.0.0.1'),
	(85, 5, 'admin', '单位调序：1, -1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:05', 1, '127.0.0.1'),
	(86, 5, 'admin', '添加单位：null', 26, 'unit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:13', 1, '127.0.0.1'),
	(87, 5, 'admin', '添加单位：null', 26, 'unit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:18', 1, '127.0.0.1'),
	(88, 5, 'admin', '添加历史单位：null', 26, 'historyUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:30', 1, '127.0.0.1'),
	(89, 5, 'admin', '添加历史单位：null', 26, 'historyUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:32', 1, '127.0.0.1'),
	(90, 5, 'admin', '历史单位调序：1, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:34', 1, '127.0.0.1'),
	(91, 5, 'admin', '历史单位调序：2, -1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:50:35', 1, '127.0.0.1'),
	(92, 5, 'admin', '历史单位调序：2, -1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:51:14', 1, '127.0.0.1'),
	(93, 5, 'admin', '历史单位调序：1, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:51:15', 1, '127.0.0.1'),
	(94, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:52:41', 1, '127.0.0.1'),
	(95, 5, 'admin', '历史单位调序：1, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:52:51', 1, '127.0.0.1'),
	(96, 5, 'admin', '历史单位调序：1, -1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:52:52', 1, '127.0.0.1'),
	(97, 5, 'admin', '添加历史单位：null', 26, 'historyUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:03', 1, '127.0.0.1'),
	(98, 5, 'admin', '添加历史单位：null', 26, 'historyUnit_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:05', 1, '127.0.0.1'),
	(99, 5, 'admin', '历史单位调序：3, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:07', 1, '127.0.0.1'),
	(100, 5, 'admin', '历史单位调序：3, -1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:08', 1, '127.0.0.1'),
	(101, 5, 'admin', '历史单位调序：2, -1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:12', 1, '127.0.0.1'),
	(102, 5, 'admin', '历史单位调序：2, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:14', 1, '127.0.0.1'),
	(103, 5, 'admin', '历史单位调序：3, 1', 26, 'historyUnit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:53:18', 1, '127.0.0.1'),
	(104, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:54:20', 1, '127.0.0.1'),
	(105, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:54:26', 1, '127.0.0.1'),
	(106, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:54:28', 1, '127.0.0.1'),
	(107, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:54:29', 1, '127.0.0.1'),
	(108, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:55:21', 1, '127.0.0.1'),
	(109, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:55:22', 1, '127.0.0.1'),
	(110, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:19', 1, '127.0.0.1'),
	(111, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:19', 1, '127.0.0.1'),
	(112, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:21', 1, '127.0.0.1'),
	(113, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:21', 1, '127.0.0.1'),
	(114, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:19', 1, '127.0.0.1'),
	(115, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 10:57:54', 1, '127.0.0.1'),
	(116, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:01:15', 1, '127.0.0.1'),
	(117, 5, 'admin', '单位发文关联单位调序：4,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:01:24', 1, '127.0.0.1'),
	(118, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:01:28', 1, '127.0.0.1'),
	(119, 5, 'admin', '单位发文关联单位调序：4,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:01:29', 1, '127.0.0.1'),
	(120, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:02:05', 1, '127.0.0.1'),
	(121, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:02:09', 1, '127.0.0.1'),
	(122, 5, 'admin', '单位发文关联单位调序：5,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:02:11', 1, '127.0.0.1'),
	(123, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:03:20', 1, '127.0.0.1'),
	(124, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:03:46', 1, '127.0.0.1'),
	(125, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:05:04', 1, '127.0.0.1'),
	(126, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:05:08', 1, '127.0.0.1'),
	(127, 5, 'admin', '单位发文关联单位调序：4,-1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:05:09', 1, '127.0.0.1'),
	(128, 5, 'admin', '单位发文关联单位调序：4,1', 26, 'dispatchUnitRelate_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:05:10', 1, '127.0.0.1'),
	(129, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:06:56', 1, '127.0.0.1'),
	(130, 5, 'admin', '单位调序：1, 1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:07:02', 1, '127.0.0.1'),
	(131, 5, 'admin', '单位调序：1, -1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:07:05', 1, '127.0.0.1'),
	(132, 5, 'admin', '单位调序：11, 1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:07:08', 1, '127.0.0.1'),
	(133, 5, 'admin', '单位调序：11, -1', 26, 'unit_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 11:07:09', 1, '127.0.0.1'),
	(134, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 12:54:49', 1, '127.0.0.1'),
	(135, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:27:44', 1, '127.0.0.1'),
	(136, 5, 'admin', '更新资源：{"id":79,"name":"单位任免记录","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnit:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:28:21', 1, '127.0.0.1'),
	(137, 5, 'admin', '更新资源：{"id":84,"name":"分组","type":"url","sortOrder":2,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:43:21', 1, '127.0.0.1'),
	(138, 5, 'admin', '更新资源：{"id":79,"name":"单位任免记录","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnitCadre:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:46:37', 1, '127.0.0.1'),
	(139, 5, 'admin', '更新资源：{"id":84,"name":"分组","type":"url","sortOrder":2,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:46:48', 1, '127.0.0.1'),
	(140, 5, 'admin', '更新资源：{"id":83,"name":"单条记录","type":"url","sortOrder":1,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreItem:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:47:01', 1, '127.0.0.1'),
	(141, 5, 'admin', '更新资源：{"id":83,"name":"任免记录","type":"url","sortOrder":1,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreItem:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:48:50', 1, '127.0.0.1'),
	(142, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:48:59', 1, '127.0.0.1'),
	(143, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreItem:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:49:22', 1, '127.0.0.1'),
	(144, 5, 'admin', '更新资源：{"id":79,"name":"单位任免事项","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnitCadre:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:49:35', 1, '127.0.0.1'),
	(145, 5, 'admin', '更新资源：{"id":79,"name":"单位任免","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnitCadre:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:49:44', 1, '127.0.0.1'),
	(146, 5, 'admin', '更新资源：{"id":79,"name":"单位干部任免","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnitCadre:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:49:58', 1, '127.0.0.1'),
	(147, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"/dispatchUnitCadreGroup","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:50:19', 1, '127.0.0.1'),
	(148, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"/dispatchUnitCadre","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadre:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:50:30', 1, '127.0.0.1'),
	(149, 5, 'admin', '更新资源：{"id":79,"name":"单位干部任免","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"dispatchUnitCadreTransfer:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:52:30', 1, '127.0.0.1'),
	(150, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"/dispatchUnitCadreTransferGroup","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreTransferGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:52:38', 1, '127.0.0.1'),
	(151, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"/dispatchUnitCadre","parentId":79,"parentIds":"0/1/61/79/","permission":"dispatchUnitCadreTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:52:58', 1, '127.0.0.1'),
	(152, 5, 'admin', '更新资源：{"id":79,"name":"单位干部任免","type":"menu","sortOrder":2,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"unitCadreTransfer:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:53:32', 1, '127.0.0.1'),
	(153, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"/dispatchUnitCadreTransferGroup","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransferGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:53:37', 1, '127.0.0.1'),
	(154, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"/dispatchUnitCadre","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:53:42', 1, '127.0.0.1'),
	(155, 5, 'admin', '更新资源：{"id":66,"name":"单位历程","type":"menu","sortOrder":1,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"unitTransfer:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:53:54', 1, '127.0.0.1'),
	(156, 5, 'admin', '更新资源：{"id":78,"name":"项目","type":"url","sortOrder":3,"menuCss":"","url":"","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:54:15', 1, '127.0.0.1'),
	(157, 5, 'admin', '更新资源：{"id":77,"name":"项目记录","type":"url","sortOrder":1,"menuCss":"","url":"","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransferItem:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:54:22', 1, '127.0.0.1'),
	(158, 5, 'admin', '更新资源：{"id":78,"name":"项目","type":"url","sortOrder":3,"menuCss":"","url":"/unitTransfer","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:54:47', 1, '127.0.0.1'),
	(159, 5, 'admin', '更新资源：{"id":77,"name":"项目记录","type":"url","sortOrder":1,"menuCss":"","url":"/unitTransferItem","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransferItem:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:54:54', 1, '127.0.0.1'),
	(160, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"/unitCadreTransferGroup","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransferGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:55:48', 1, '127.0.0.1'),
	(161, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"/unitCadre","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:55:54', 1, '127.0.0.1'),
	(162, 5, 'admin', '更新资源：{"id":83,"name":"任免事项记录","type":"url","sortOrder":1,"menuCss":"","url":"/unitCadreTransfer","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:56:03', 1, '127.0.0.1'),
	(163, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 13:59:28', 1, '127.0.0.1'),
	(164, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:05:02', 1, '127.0.0.1'),
	(165, 5, 'admin', '更新资源：{"id":84,"name":"任免事项","type":"url","sortOrder":2,"menuCss":"","url":"/unitCadreTransferGroup","parentId":79,"parentIds":"0/1/61/79/","permission":"unitCadreTransferGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:11:12', 1, '127.0.0.1'),
	(166, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,160,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,83,66,78,77,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:11:48', 1, '127.0.0.1'),
	(167, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:15:43', 1, '127.0.0.1'),
	(168, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,104,103,102,101,100,99,92,98,96,97,98,96,97,61,82,144,145,146,147,160,144,145,146,147,160,81,148,149,150,151,152,153,154,155,148,149,150,151,152,153,154,155,80,156,157,158,159,156,157,158,159,79,84,83,84,168,169,170,83,161,162,163,164,165,166,167,66,78,77,78,77,88,90,128,129,130,131,128,129,130,131,89,132,133,134,135,140,141,142,143,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,111,112,113,114,71,115,116,117,118,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:16:14', 1, '127.0.0.1'),
	(169, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:49:23', 1, '127.0.0.1'),
	(170, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:51:23', 1, '127.0.0.1'),
	(171, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:56:43', 1, '127.0.0.1'),
	(172, 5, 'admin', '更新角色：{"id":3,"role":"cadre","description":"干部","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 14:56:54', 1, '127.0.0.1'),
	(173, 5, 'admin', '添加单位任免分组：null', 26, 'unitCadreTransferGroup_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 15:13:02', 1, '127.0.0.1'),
	(174, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 15:19:53', 1, '127.0.0.1'),
	(175, 5, 'admin', '添加单位任免记录：null', 26, 'unitCadreTransfer_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 15:20:16', 1, '127.0.0.1'),
	(176, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 15:52:04', 1, '127.0.0.1'),
	(177, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 16:26:09', 1, '127.0.0.1'),
	(178, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 16:31:55', 1, '127.0.0.1'),
	(179, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 16:42:17', 1, '127.0.0.1'),
	(180, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 17:06:38', 1, '127.0.0.1'),
	(181, 5, 'admin', '更新资源：{"id":66,"name":"单位变更","type":"menu","sortOrder":1,"menuCss":"","url":"","parentId":61,"parentIds":"0/1/61/","permission":"unitTransfer:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 17:07:29', 1, '127.0.0.1'),
	(182, 5, 'admin', '更新资源：{"id":78,"name":"变更项目","type":"url","sortOrder":3,"menuCss":"","url":"/unitTransfer","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 17:07:35', 1, '127.0.0.1'),
	(183, 5, 'admin', '更新资源：{"id":78,"name":"变更项目","type":"url","sortOrder":3,"menuCss":"","url":"/unitTransfer","parentId":66,"parentIds":"0/1/61/66/","permission":"unitTransfer:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 17:10:10', 1, '127.0.0.1'),
	(184, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,160,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,168,169,170,83,161,162,163,164,165,166,167,66,78,171,172,173,77,174,175,176,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 17:10:30', 1, '127.0.0.1'),
	(185, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 19:09:03', 1, '127.0.0.1'),
	(186, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:09:17', 1, '127.0.0.1'),
	(187, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:17:44', 1, '127.0.0.1'),
	(188, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:19:29', 1, '127.0.0.1'),
	(189, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:21:16', 1, '127.0.0.1'),
	(190, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:24:30', 1, '127.0.0.1'),
	(191, 5, 'admin', '添加单位变更：null', 26, 'unitTransfer_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:24:42', 1, '127.0.0.1'),
	(192, 5, 'admin', '更新资源：{"id":61,"name":"发文管理","type":"menu","sortOrder":5,"menuCss":"fa fa-files-o","url":"","parentId":1,"parentIds":"0/1/","permission":"dispatch:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:31:53', 1, '127.0.0.1'),
	(193, 5, 'admin', '更新资源：{"id":81,"name":"单位发文","type":"url","sortOrder":4,"menuCss":"","url":"/dispatchUnit","parentId":61,"parentIds":"0/1/61/","permission":"dispatch:unit","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:35:15', 1, '127.0.0.1'),
	(194, 5, 'admin', '更新资源：{"id":171,"name":"添加/修改","type":"function","menuCss":"","url":"","parentId":66,"parentIds":"0/1/61/66","permission":"unitTransfer:edit","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:36:14', 1, '127.0.0.1'),
	(195, 5, 'admin', '更新资源：{"id":178,"name":"删除/批量删除","type":"function","menuCss":"","url":"","parentId":66,"parentIds":"0/1/61/66","permission":"unitTransferItem:del","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:36:45', 1, '127.0.0.1'),
	(196, 5, 'admin', '更新资源：{"id":178,"name":"删除/批量删除","type":"function","menuCss":"","url":"","parentId":66,"parentIds":"0/1/61/66","permission":"unitTransferItem:del","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:37:11', 1, '127.0.0.1'),
	(197, 5, 'admin', '更新角色：{"id":1,"description":"系统管理员","resourceIds":"108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,160,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,168,169,170,83,161,162,163,164,165,166,167,66,171,172,173,177,178,179,180,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 20:38:23', 1, '127.0.0.1'),
	(198, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 21:17:08', 1, '127.0.0.1'),
	(199, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 22:52:17', 1, '127.0.0.1'),
	(200, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:11:16', 1, '127.0.0.1'),
	(201, 5, 'admin', '创建角色：{"role":"组织部管理员","description":"odAdmin","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:42:40', 1, '127.0.0.1'),
	(202, 5, 'admin', '更新角色：{"id":4,"role":"odAdmin","description":"组织部管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:42:46', 1, '127.0.0.1'),
	(203, 5, 'admin', '创建角色：{"role":"partyAdmin","description":"分党委管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:43:38', 1, '127.0.0.1'),
	(204, 5, 'admin', '创建角色：{"role":"branchAdmin","description":"党支部管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:44:18', 1, '127.0.0.1'),
	(205, 5, 'admin', '更新角色：{"id":2,"role":"unit","description":"单位管理员","resourceIds":"108,105,106,107"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:44:30', 1, '127.0.0.1'),
	(206, 5, 'admin', '更新角色：{"id":4,"role":"orgdep","description":"组织部管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:45:10', 1, '127.0.0.1'),
	(207, 5, 'admin', '更新角色：{"id":5,"role":"party","description":"分党委管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:45:17', 1, '127.0.0.1'),
	(208, 5, 'admin', '更新角色：{"id":6,"role":"branch","description":"党支部管理员","resourceIds":"-1"}', 26, 'sysRole_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-15 23:45:32', 1, '127.0.0.1'),
	(209, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 00:04:13', 1, '127.0.0.1'),
	(210, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 13:56:38', 1, '127.0.0.1'),
	(211, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:02:11', 1, '127.0.0.1'),
	(212, 5, 'admin', '禁用用户：3019860034', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:03:43', 1, '127.0.0.1'),
	(213, 5, 'admin', '解禁用户：3019860034', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:15:52', 1, '127.0.0.1'),
	(214, 5, 'admin', '禁用用户：3019860034', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:15:56', 1, '127.0.0.1'),
	(215, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:21:51', 1, '127.0.0.1'),
	(216, 5, 'admin', '更新资源：{"id":22,"name":"账号管理","type":"url","sortOrder":5,"menuCss":"","url":"/sysUser","parentId":21,"parentIds":"0/1/21/","permission":"sysUser:url","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:28:27', 1, '127.0.0.1'),
	(217, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:35:18', 1, '127.0.0.1'),
	(218, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:37:15', 1, '127.0.0.1'),
	(219, 5, 'admin', '添加用户：null', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:44:05', 1, '127.0.0.1'),
	(220, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:21', 1, '127.0.0.1'),
	(221, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:35', 1, '127.0.0.1'),
	(222, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:41', 1, '127.0.0.1'),
	(223, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:45', 1, '127.0.0.1'),
	(224, 5, 'admin', '元数据属性值调序：48, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:50', 1, '127.0.0.1'),
	(225, 5, 'admin', '元数据属性值调序：49, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:50', 1, '127.0.0.1'),
	(226, 5, 'admin', '元数据属性值调序：48, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:52', 1, '127.0.0.1'),
	(227, 5, 'admin', '元数据属性值调序：48, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:53', 1, '127.0.0.1'),
	(228, 5, 'admin', '元数据属性值调序：49, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:45:54', 1, '127.0.0.1'),
	(229, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:55:55', 1, '127.0.0.1'),
	(230, 5, 'admin', '禁用用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 14:58:20', 1, '127.0.0.1'),
	(231, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:01:09', 1, '127.0.0.1'),
	(232, 5, 'admin', '禁用用户：admin', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:06:18', 1, '127.0.0.1'),
	(233, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:07:26', 1, '127.0.0.1'),
	(234, 5, 'admin', '解禁用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:07:42', 1, '127.0.0.1'),
	(235, 640, 'sdfsdf', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:07:54', 1, '127.0.0.1'),
	(236, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:14', 1, '127.0.0.1'),
	(237, 5, 'admin', '禁用用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:25', 1, '127.0.0.1'),
	(238, 5, 'admin', '解禁用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:29', 1, '127.0.0.1'),
	(239, 5, 'admin', '禁用用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:34', 1, '127.0.0.1'),
	(240, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:50', 1, '127.0.0.1'),
	(241, 5, 'admin', '解禁用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:08:56', 1, '127.0.0.1'),
	(242, 640, 'sdfsdf', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:09:03', 1, '127.0.0.1'),
	(243, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:09:09', 1, '127.0.0.1'),
	(244, 5, 'admin', '禁用用户：sdfsdf', 26, 'sysUser_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:09:17', 1, '127.0.0.1'),
	(245, 5, 'admin', '更新用户：640', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:09:31', 1, '127.0.0.1'),
	(246, 5, 'admin', '更新用户：640', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:13:00', 1, '127.0.0.1'),
	(247, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:13:15', 1, '127.0.0.1'),
	(248, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:13:46', 1, '127.0.0.1'),
	(249, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:14:07', 1, '127.0.0.1'),
	(250, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:14:20', 1, '127.0.0.1'),
	(251, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:14:27', 1, '127.0.0.1'),
	(252, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:14:35', 1, '127.0.0.1'),
	(253, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:15:56', 1, '127.0.0.1'),
	(254, 5, 'admin', '添加用户：null', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:18:17', 1, '127.0.0.1'),
	(255, 641, 'test', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:18:26', 1, '127.0.0.1'),
	(256, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:18:32', 1, '127.0.0.1'),
	(257, 5, 'admin', '更新用户：641', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:18:47', 1, '127.0.0.1'),
	(258, 5, 'admin', '更新用户：5', 26, 'sysUser_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:18:54', 1, '127.0.0.1'),
	(259, 641, 'test', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:25:49', 1, '127.0.0.1'),
	(260, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:25:55', 1, '127.0.0.1'),
	(261, 5, 'admin', '更新资源：{"id":108,"name":"首页","type":"url","sortOrder":8,"menuCss":"fa fa-tachometer","url":"/index","parentId":1,"parentIds":"0/1/","permission":"index:home","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:27:54', 1, '127.0.0.1'),
	(262, 5, 'admin', '删除校领导单位：4', 26, 'leaderUnit_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:31:57', 1, '127.0.0.1'),
	(263, 5, 'admin', '删除校领导单位：3', 26, 'leaderUnit_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:31:58', 1, '127.0.0.1'),
	(264, 5, 'admin', '删除校领导单位：1', 26, 'leaderUnit_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:32:00', 1, '127.0.0.1'),
	(265, 5, 'admin', '删除校领导：1', 26, 'leader_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:32:02', 1, '127.0.0.1'),
	(266, 5, 'admin', '删除单位任免记录：1', 26, 'unitCadreTransfer_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:33:00', 1, '127.0.0.1'),
	(267, 5, 'admin', '删除干部：1', 26, 'cadre_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 15:33:04', 1, '127.0.0.1'),
	(268, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:26:35', 1, '127.0.0.1'),
	(269, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:29:20', 1, '127.0.0.1'),
	(270, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:35:19', 1, '127.0.0.1'),
	(271, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:43:18', 1, '127.0.0.1'),
	(272, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:53:42', 1, '127.0.0.1'),
	(273, 5, 'admin', '更新干部：2', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 17:59:15', 1, '127.0.0.1'),
	(274, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:00:19', 1, '127.0.0.1'),
	(275, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:02:22', 1, '127.0.0.1'),
	(276, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:05:05', 1, '127.0.0.1'),
	(277, 5, 'admin', '更新干部：2', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:05:18', 1, '127.0.0.1'),
	(278, 5, 'admin', '更新干部：2', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:05:24', 1, '127.0.0.1'),
	(279, 5, 'admin', '添加干部：null', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:07:55', 1, '127.0.0.1'),
	(280, 5, 'admin', '添加干部：null', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:08:54', 1, '127.0.0.1'),
	(281, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:11:45', 1, '127.0.0.1'),
	(282, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:13:22', 1, '127.0.0.1'),
	(283, 5, 'admin', '更新干部：6', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:13:37', 1, '127.0.0.1'),
	(284, 5, 'admin', '更新干部：5', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:13:42', 1, '127.0.0.1'),
	(285, 5, 'admin', '更新干部：2', 26, 'cadre_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:13:48', 1, '127.0.0.1'),
	(286, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:18:05', 1, '127.0.0.1'),
	(287, 5, 'admin', '添加校领导：null', 26, 'leader_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:18:19', 1, '127.0.0.1'),
	(288, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:23:05', 1, '127.0.0.1'),
	(289, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:24:36', 1, '127.0.0.1'),
	(290, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:26:14', 1, '127.0.0.1'),
	(291, 5, 'admin', '更新校领导：2', 26, 'leader_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:26:19', 1, '127.0.0.1'),
	(292, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:30:09', 1, '127.0.0.1'),
	(293, 5, 'admin', '添加校领导：null', 26, 'leader_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:30:17', 1, '127.0.0.1'),
	(294, 5, 'admin', '添加校领导：null', 26, 'leader_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:30:23', 1, '127.0.0.1'),
	(295, 5, 'admin', '删除干部：2', 26, 'cadre_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 18:31:23', 1, '127.0.0.1'),
	(296, 5, 'admin', '更新资源：{"id":105,"name":"党建管理","type":"menu","sortOrder":7,"menuCss":"fa fa-star","url":"","parentId":1,"parentIds":"0/1/","permission":"party:menu","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:43:46', 1, '127.0.0.1'),
	(297, 5, 'admin', '更新资源：{"id":106,"name":"基层党组织","type":"url","sortOrder":2,"menuCss":"","url":"","parentId":105,"parentIds":"0/1/105/","permission":"party:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:44:20', 1, '127.0.0.1'),
	(298, 5, 'admin', '更新资源：{"id":106,"name":"基层党组织","type":"url","sortOrder":2,"menuCss":"","url":"/party","parentId":105,"parentIds":"0/1/105/","permission":"party:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:44:51', 1, '127.0.0.1'),
	(299, 5, 'admin', '添加资源：{"name":"分党委领导班子","type":"url","menuCss":"","url":"/partyMemberGroup","parentId":105,"parentIds":"0/1/105/","permission":"partyMemberGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:46:51', 1, '127.0.0.1'),
	(300, 5, 'admin', '更新资源：{"id":181,"name":"分党委领导班子","type":"url","sortOrder":1,"menuCss":"","url":"/partyMemberGroup","parentId":105,"parentIds":"0/1/105/","permission":"partyMemberGroup:list","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:47:00', 1, '127.0.0.1'),
	(301, 5, 'admin', '更新资源：{"id":107,"name":"党员信息管理","type":"url","sortOrder":0,"menuCss":"","url":"","parentId":105,"parentIds":"0/1/105/","permission":"partyBuild:person","available":1}', 26, 'sysResource_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 19:47:11', 1, '127.0.0.1'),
	(302, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:53:35', 1, '127.0.0.1'),
	(303, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:56:42', 1, '127.0.0.1'),
	(304, 5, 'admin', '元数据属性值调序：51, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:56:48', 1, '127.0.0.1'),
	(305, 5, 'admin', '元数据属性值调序：51, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:56:50', 1, '127.0.0.1'),
	(306, 5, 'admin', '元数据属性值调序：51, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:56:52', 1, '127.0.0.1'),
	(307, 5, 'admin', '元数据属性值调序：26, 10', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:56:57', 1, '127.0.0.1'),
	(308, 5, 'admin', '元数据属性值调序：51, 3', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:04', 1, '127.0.0.1'),
	(309, 5, 'admin', '元数据属性值调序：31, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:09', 1, '127.0.0.1'),
	(310, 5, 'admin', '元数据属性值调序：31, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:12', 1, '127.0.0.1'),
	(311, 5, 'admin', '元数据属性值调序：31, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:14', 1, '127.0.0.1'),
	(312, 5, 'admin', '元数据属性值调序：28, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:55', 1, '127.0.0.1'),
	(313, 5, 'admin', '元数据属性值调序：28, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:57:58', 1, '127.0.0.1'),
	(314, 5, 'admin', '元数据属性值调序：31, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:58:01', 1, '127.0.0.1'),
	(315, 5, 'admin', '元数据属性值调序：9, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:59:49', 1, '127.0.0.1'),
	(316, 5, 'admin', '元数据属性值调序：9, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:59:52', 1, '127.0.0.1'),
	(317, 5, 'admin', '元数据属性值调序：9, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:59:54', 1, '127.0.0.1'),
	(318, 5, 'admin', '元数据属性值调序：9, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 20:59:56', 1, '127.0.0.1'),
	(319, 5, 'admin', '元数据属性值调序：8, 10', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:01', 1, '127.0.0.1'),
	(320, 5, 'admin', '元数据属性值调序：8, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:05', 1, '127.0.0.1'),
	(321, 5, 'admin', '元数据属性值调序：8, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:07', 1, '127.0.0.1'),
	(322, 5, 'admin', '元数据属性值调序：8, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:08', 1, '127.0.0.1'),
	(323, 5, 'admin', '元数据属性值调序：8, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:09', 1, '127.0.0.1'),
	(324, 5, 'admin', '元数据属性值调序：9, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:00:10', 1, '127.0.0.1'),
	(325, 5, 'admin', '元数据属性值调序：27, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:27', 1, '127.0.0.1'),
	(326, 5, 'admin', '元数据属性值调序：27, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:29', 1, '127.0.0.1'),
	(327, 5, 'admin', '元数据属性值调序：27, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:30', 1, '127.0.0.1'),
	(328, 5, 'admin', '元数据属性值调序：27, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:32', 1, '127.0.0.1'),
	(329, 5, 'admin', '元数据属性值调序：27, 2', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:35', 1, '127.0.0.1'),
	(330, 5, 'admin', '元数据属性值调序：27, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:37', 1, '127.0.0.1'),
	(331, 5, 'admin', '元数据属性值调序：27, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:39', 1, '127.0.0.1'),
	(332, 5, 'admin', '元数据属性值调序：27, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:02:40', 1, '127.0.0.1'),
	(333, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:06:25', 1, '127.0.0.1'),
	(334, 5, 'admin', '更新元数据：18', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:06:34', 1, '127.0.0.1'),
	(335, 5, 'admin', '更新元数据：17', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:06:42', 1, '127.0.0.1'),
	(336, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:10:16', 1, '127.0.0.1'),
	(337, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:10:43', 1, '127.0.0.1'),
	(338, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:11:06', 1, '127.0.0.1'),
	(339, 5, 'admin', '元数据属性值调序：52, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:11:17', 1, '127.0.0.1'),
	(340, 5, 'admin', '元数据属性值调序：52, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:11:18', 1, '127.0.0.1'),
	(341, 5, 'admin', '元数据属性值调序：53, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:11:23', 1, '127.0.0.1'),
	(342, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 21:47:40', 1, '127.0.0.1'),
	(343, 5, 'admin', '登录成功', 31, 'login', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:26:27', 1, '127.0.0.1'),
	(344, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:14', 1, '127.0.0.1'),
	(345, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:27', 1, '127.0.0.1'),
	(346, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:34', 1, '127.0.0.1'),
	(347, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:40', 1, '127.0.0.1'),
	(348, 5, 'admin', '元数据属性值调序：55, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:47', 1, '127.0.0.1'),
	(349, 5, 'admin', '元数据属性值调序：57, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:48', 1, '127.0.0.1'),
	(350, 5, 'admin', '元数据属性值调序：57, -1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:31:49', 1, '127.0.0.1'),
	(351, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:25', 1, '127.0.0.1'),
	(352, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:34', 1, '127.0.0.1'),
	(353, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:39', 1, '127.0.0.1'),
	(354, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:44', 1, '127.0.0.1'),
	(355, 5, 'admin', '元数据属性值调序：58, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:49', 1, '127.0.0.1'),
	(356, 5, 'admin', '元数据属性值调序：58, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:50', 1, '127.0.0.1'),
	(357, 5, 'admin', '元数据属性值调序：59, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:32:52', 1, '127.0.0.1'),
	(358, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:33:21', 1, '127.0.0.1'),
	(359, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:33:30', 1, '127.0.0.1'),
	(360, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:33:35', 1, '127.0.0.1'),
	(361, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:34:20', 1, '127.0.0.1'),
	(362, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:34:32', 1, '127.0.0.1'),
	(363, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:34:38', 1, '127.0.0.1'),
	(364, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:36:16', 1, '127.0.0.1'),
	(365, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:36:21', 1, '127.0.0.1'),
	(366, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:36:26', 1, '127.0.0.1'),
	(367, 5, 'admin', '元数据属性值调序：64, 5', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:37:19', 1, '127.0.0.1'),
	(368, 5, 'admin', '元数据属性值调序：63, 3', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:37:23', 1, '127.0.0.1'),
	(369, 5, 'admin', '元数据属性值调序：66, 1', 26, 'metaType_changeOrder', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:37:28', 1, '127.0.0.1'),
	(370, 5, 'admin', '更新元数据：22', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:37:41', 1, '127.0.0.1'),
	(371, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:39:38', 1, '127.0.0.1'),
	(372, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:39:55', 1, '127.0.0.1'),
	(373, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:39:59', 1, '127.0.0.1'),
	(374, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:04', 1, '127.0.0.1'),
	(375, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:08', 1, '127.0.0.1'),
	(376, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:12', 1, '127.0.0.1'),
	(377, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:17', 1, '127.0.0.1'),
	(378, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:21', 1, '127.0.0.1'),
	(379, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:26', 1, '127.0.0.1'),
	(380, 5, 'admin', '删除元数据属性值：75', 26, 'metaType_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:40:31', 1, '127.0.0.1'),
	(381, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:41:08', 1, '127.0.0.1'),
	(382, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:41:19', 1, '127.0.0.1'),
	(383, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:41:24', 1, '127.0.0.1'),
	(384, 5, 'admin', '添加元数据：null', 26, 'metaClass_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:41:56', 1, '127.0.0.1'),
	(385, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:42:04', 1, '127.0.0.1'),
	(386, 5, 'admin', '添加元数据属性值：null', 26, 'metaType_au', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:42:08', 1, '127.0.0.1'),
	(387, 5, 'admin', '删除元数据：4', 26, 'metaClass_del', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36', '2015-11-16 22:46:05', 1, '127.0.0.1');
/*!40000 ALTER TABLE `sys_log` ENABLE KEYS */;


-- 导出  表 owip.sys_login_log 结构
CREATE TABLE IF NOT EXISTS `sys_login_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号ID',
  `login_time` datetime DEFAULT NULL COMMENT '登陆时间',
  `login_ip` varchar(10) DEFAULT NULL COMMENT '登陆IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登陆时间',
  `last_login_ip` varchar(10) DEFAULT NULL COMMENT '上次登陆IP',
  `agent` varchar(10) DEFAULT NULL COMMENT '客户端类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='平台用户';

-- 正在导出表  owip.sys_login_log 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `sys_login_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_login_log` ENABLE KEYS */;


-- 导出  表 owip.sys_resource 结构
CREATE TABLE IF NOT EXISTS `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `type` varchar(50) DEFAULT NULL COMMENT '类型, menu, url, function',
  `menu_css` varchar(50) DEFAULT NULL COMMENT '样式',
  `url` varchar(200) DEFAULT NULL COMMENT '资源路径',
  `parent_id` int(11) DEFAULT NULL COMMENT '上级资源',
  `parent_ids` varchar(100) DEFAULT NULL COMMENT '上级资源路径',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串',
  `available` tinyint(3) DEFAULT '0' COMMENT '状态，0禁用 1启用',
  `sort_order` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统资源';

-- 正在导出表  owip.sys_resource 的数据：~106 rows (大约)
/*!40000 ALTER TABLE `sys_resource` DISABLE KEYS */;
INSERT INTO `sys_resource` (`id`, `name`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `permission`, `available`, `sort_order`) VALUES
	(1, '顶级节点', 'menu', '', '', 0, '0/', '', 1, NULL),
	(21, '系统管理', 'menu', 'fa fa-gear fa-spin', '', 1, '0/1/', 'sys:menu', 1, 1),
	(22, '账号管理', 'url', '', '/sysUser', 21, '0/1/21/', 'sysUser:url', 1, 5),
	(31, '资源管理', 'url', 'fa fa-caret-right', '/sysResource', 21, '0/1/21/', 'sys:resource', 1, 3),
	(41, '角色管理', 'url', 'fa fa-caret-right', '/sysRole', 21, '0/1/21/', 'sys:role', 1, 4),
	(61, '发文管理', 'menu', 'fa fa-files-o', '', 1, '0/1/', 'dispatch:menu', 1, 5),
	(66, '单位变更', 'url', '', '/unitTransfer', 61, '0/1/61/', 'unitTransfer:list', 1, 1),
	(67, '元数据管理', 'menu', 'fa fa-database', '', 1, '0/1/', 'base:menu', 1, 2),
	(71, '元数据属性', 'url', '', '/metaType', 67, '0/1/67/', 'metaType:menu', 1, 4),
	(72, '元数据分类', 'url', '', '/metaClass', 67, '0/1/67/', 'metaClass:menu', 1, 5),
	(75, '系统日志', 'url', '', '/sysLog', 21, '0/1/21/', 'sys:log', 1, 1),
	(76, '系统设置', 'url', '', '/sysConfig', 21, '0/1/21/', 'sys:setting', 1, 2),
	(79, '单位干部任免', 'menu', '', '', 61, '0/1/61/', 'unitCadreTransfer:menu', 1, 2),
	(80, '干部任免', 'url', '', '/dispatchCadre', 61, '0/1/61/', 'dispatch:cadre', 1, 3),
	(81, '单位发文', 'url', '', '/dispatchUnit', 61, '0/1/61/', 'dispatch:unit', 1, 4),
	(82, '发文', 'url', '', '/dispatch', 61, '0/1/61/', 'dispatch:dispatch', 1, 5),
	(83, '任免事项记录', 'url', '', '/unitCadreTransfer', 79, '0/1/61/79/', 'unitCadreTransfer:list', 1, 1),
	(84, '任免事项', 'url', '', '/unitCadreTransferGroup', 79, '0/1/61/79/', 'unitCadreTransferGroup:list', 1, 2),
	(85, '单位管理', 'menu', 'fa fa-sitemap', '', 1, '0/1/', 'unit:menu', 1, 3),
	(86, '单位干部', 'url', '', '/unitCadre', 85, '0/1/85/', 'unit:cadre', 1, 1),
	(87, '单位', 'url', '', '/unit', 85, '0/1/85/', 'unit:unit', 1, 2),
	(88, '干部管理', 'menu', 'fa fa-users', '', 1, '0/1/', 'cadre:menu', 1, 4),
	(89, '校领导', 'url', '', '/leader', 88, '0/1/88/', 'leader:menu', 1, 1),
	(90, '干部', 'url', '', '/cadre', 88, '0/1/88/', 'cadre:cadre', 1, 2),
	(91, '干部考核', 'menu', 'fa fa-pencil-square-o', '', 1, '0/1/', 'ces:menu', 1, 6),
	(92, '年度测评结果', 'menu', '', '', 91, '0/1/91/', 'ces:result', 1, 1),
	(93, '年度测评管理', 'menu', '', '', 91, '0/1/91/', 'ces:anual', 1, 2),
	(94, '参数设置', 'url', '', '', 91, '0/1/91/', 'ces:config', 1, 3),
	(95, '评选单', 'url', '', '', 91, '0/1/91/', 'ces:survey', 1, 4),
	(96, '测评对象统计', 'url', '', '', 92, '0/1/91/92/', 'ces:statObj', 1, 2),
	(97, '分区分组统计', 'url', '', '', 92, '0/1/91/92/', 'ces:partion', 1, 1),
	(98, '评选单统计', 'url', '', '', 92, '0/1/91/92/', 'ces:stat', 1, 3),
	(99, '各单位参评人账号', 'url', '', '', 93, '0/1/91/93/', 'ces:inspector', 1, 1),
	(100, '参评人身份类型', 'url', '', '', 93, '0/1/91/93/', 'ces:inspectorType', 1, 2),
	(101, '年度测评对象', 'url', '', '', 93, '0/1/91/93/', 'ces:evaObj', 1, 3),
	(102, '指标模板', 'url', '', '', 93, '0/1/91/93/', 'ces:template', 1, 4),
	(103, '年度评选单', 'url', '', '', 93, '0/1/91/93/', 'ces:anualSurvey', 1, 5),
	(104, '参评单位', 'url', '', '', 93, '0/1/91/93/', 'ces:unit', 1, 6),
	(105, '党建管理', 'menu', 'fa fa-star', '', 1, '0/1/', 'party:menu', 1, 7),
	(106, '基层党组织', 'url', '', '/party', 105, '0/1/105/', 'party:list', 1, 2),
	(107, '党员信息管理', 'url', '', '', 105, '0/1/105/', 'partyBuild:person', 1, 0),
	(108, '首页', 'url', 'fa fa-tachometer', '/index', 1, '0/1/', 'index:home', 1, 8),
	(111, '列表', 'function', '', '', 72, '0/1/67/72/', 'metaClass:list', 1, NULL),
	(112, '添加/修改', 'function', '', '', 72, '0/1/67/72/', 'metaClass:edit', 1, NULL),
	(113, '删除/批量删除', 'function', '', '', 72, '0/1/67/72/', 'metaClass:del', 1, NULL),
	(114, '改变顺序', 'function', '', '', 72, '0/1/67/72/', 'metaClass:changeOrder', 1, NULL),
	(115, '列表', 'function', '', '', 71, '0/1/67/71/', 'metaType:list', 1, NULL),
	(116, '添加/修改', 'function', '', '', 71, '0/1/67/71/', 'metaType:edit', 1, NULL),
	(117, '删除/批量删除', 'function', '', '', 71, '0/1/67/71/', 'metaType:del', 1, NULL),
	(118, '调序', 'function', '', '', 71, '0/1/67/71/', 'metaType:changeOrder', 1, NULL),
	(119, '列表', 'function', '', '', 87, '0/1/85/87/', 'unit:list', 1, NULL),
	(120, '添加/修改', 'function', '', '', 87, '0/1/85/87/', 'unit:edit', 1, NULL),
	(121, '删除', 'function', '', '', 87, '0/1/85/87/', 'unit:del', 1, NULL),
	(122, '调序', 'function', '', '', 87, '0/1/85/87/', 'unit:changeOrder', 1, NULL),
	(123, '撤销', 'function', '', '', 87, '0/1/85/87/', 'unit:abolish', 1, NULL),
	(124, '查看历史单位', 'function', '', '', 87, '0/1/85/87/', 'unit:history', 1, NULL),
	(125, '删除历史单位', 'function', '', '', 87, '0/1/85/87/', 'historyUnit:del', 1, NULL),
	(126, '历史单位调序', 'function', '', '', 87, '0/1/85/87/', 'historyUnit:changeOrder', 1, NULL),
	(127, '添加历史单位', 'function', '', '', 87, '0/1/85/87/', 'historyUnit:edit', 1, NULL),
	(128, '列表', 'function', '', '', 90, '0/1/88/90', 'cadre:list', 1, NULL),
	(129, '添加/修改', 'function', '', '', 90, '0/1/88/90', 'cadre:edit', 1, NULL),
	(130, '删除/批量删除', 'function', '', '', 90, '0/1/88/90', 'cadre:del', 1, NULL),
	(131, '调序', 'function', '', '', 90, '0/1/88/90', 'cadre:changeOrder', 1, NULL),
	(132, '列表', 'function', NULL, NULL, 89, '0/1/88/89', 'leader:list', 1, NULL),
	(133, '添加/修改', 'function', NULL, NULL, 89, '0/1/88/89', 'leader:edit', 1, NULL),
	(134, '删除/批量删除', 'function', NULL, NULL, 89, '0/1/88/89', 'leader:del', 1, NULL),
	(135, '调序', 'function', NULL, NULL, 89, '0/1/88/89', 'leader:changeOrder', 1, NULL),
	(140, '校领导单位列表', 'function', NULL, NULL, 89, '0/1/88/89', 'leaderUnit:list', 1, NULL),
	(141, '校领导单位添加/修改', 'function', NULL, NULL, 89, '0/1/88/89', 'leaderUnit:edit', 1, NULL),
	(142, '校领导单位删除/批量删除', 'function', NULL, NULL, 89, '0/1/88/89', 'leaderUnit:del', 1, NULL),
	(143, '查看联系单位', 'function', '', '', 89, '0/1/88/89/', 'leader:unit', 1, NULL),
	(144, '列表', 'function', NULL, NULL, 82, '0/1/61/82', 'dispatch:list', 1, NULL),
	(145, '添加/修改', 'function', NULL, NULL, 82, '0/1/61/82', 'dispatch:edit', 1, NULL),
	(146, '删除/批量删除', 'function', NULL, NULL, 82, '0/1/61/82', 'dispatch:del', 1, NULL),
	(147, '调序', 'function', NULL, NULL, 82, '0/1/61/82', 'dispatch:changeOrder', 1, NULL),
	(148, '列表', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnit:list', 1, NULL),
	(149, '添加/修改', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnit:edit', 1, NULL),
	(150, '删除/批量删除', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnit:del', 1, NULL),
	(151, '调序', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnit:changeOrder', 1, NULL),
	(152, '关联单位列表', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnitRelate:list', 1, NULL),
	(153, '关联单位添加/修改', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnitRelate:edit', 1, NULL),
	(154, '关联单位删除/批量删除', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnitRelate:del', 1, NULL),
	(155, '关联单位调序', 'function', NULL, NULL, 81, '0/1/61/81', 'dispatchUnitRelate:changeOrder', 1, NULL),
	(156, '列表', 'function', NULL, NULL, 80, '0/1/61/80', 'dispatchCadre:list', 1, NULL),
	(157, '添加/修改', 'function', NULL, NULL, 80, '0/1/61/80', 'dispatchCadre:edit', 1, NULL),
	(158, '删除/批量删除', 'function', NULL, NULL, 80, '0/1/61/80', 'dispatchCadre:del', 1, NULL),
	(159, '调序', 'function', NULL, NULL, 80, '0/1/61/80', 'dispatchCadre:changeOrder', 1, NULL),
	(160, '下载', 'function', '', '', 82, '0/1/61/82/', 'dispatch:download', 1, NULL),
	(161, '添加/修改', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransfer:edit', 1, NULL),
	(162, '删除/批量删除', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransfer:del', 1, NULL),
	(163, '调序', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransfer:changeOrder', 1, NULL),
	(164, '列表', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransferItem:list', 1, NULL),
	(165, '添加/修改', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransferItem:edit', 1, NULL),
	(166, '删除/批量删除', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransferItem:del', 1, NULL),
	(167, '调序', 'function', NULL, NULL, 83, '0/1/61/79/83', 'unitCadreTransferItem:changeOrder', 1, NULL),
	(168, '添加/修改', 'function', NULL, NULL, 84, '0/1/61/79/84', 'unitCadreTransferGroup:edit', 1, NULL),
	(169, '删除/批量删除', 'function', NULL, NULL, 84, '0/1/61/79/84', 'unitCadreTransferGroup:del', 1, NULL),
	(170, '调序', 'function', NULL, NULL, 84, '0/1/61/79/84', 'unitCadreTransferGroup:changeOrder', 1, NULL),
	(171, '添加/修改', 'function', '', '', 66, '0/1/61/66', 'unitTransfer:edit', 1, NULL),
	(172, '删除/批量删除', 'function', NULL, NULL, 66, '0/1/61/66', 'unitTransfer:del', 1, NULL),
	(173, '调序', 'function', NULL, NULL, 66, '0/1/61/66', 'unitTransfer:changeOrder', 1, NULL),
	(177, '添加/修改', 'function', NULL, NULL, 66, '0/1/61/66', 'unitTransferItem:edit', 1, NULL),
	(178, '删除/批量删除', 'function', '', '', 66, '0/1/61/66', 'unitTransferItem:del', 1, NULL),
	(179, '调序', 'function', NULL, NULL, 66, '0/1/61/66', 'unitTransferItem:changeOrder', 1, NULL),
	(180, '列表', 'function', NULL, NULL, 66, '0/1/61/66', 'unitTransferItem:list', 1, NULL),
	(181, '分党委领导班子', 'url', '', '/partyMemberGroup', 105, '0/1/105/', 'partyMemberGroup:list', 1, 1);
/*!40000 ALTER TABLE `sys_resource` ENABLE KEYS */;


-- 导出  表 owip.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role` varchar(100) NOT NULL COMMENT '角色',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `resource_ids` text COMMENT '拥有的资源',
  `available` tinyint(3) DEFAULT '0' COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色';

-- 正在导出表  owip.sys_role 的数据：~6 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role`, `description`, `resource_ids`, `available`) VALUES
	(1, 'admin', '系统管理员', '108,105,106,107,91,95,94,93,104,103,102,101,100,99,92,98,96,97,61,82,144,145,146,147,160,81,148,149,150,151,152,153,154,155,80,156,157,158,159,79,84,168,169,170,83,161,162,163,164,165,166,167,66,171,172,173,177,178,179,180,88,90,128,129,130,131,89,132,133,134,135,140,141,142,143,85,87,119,120,121,122,123,124,125,126,127,86,67,72,111,112,113,114,71,115,116,117,118,21,22,41,31,76,75', 1),
	(2, 'unit', '单位管理员', '108,105,106,107', 1),
	(3, 'cadre', '干部', '-1', 1),
	(4, 'orgdep', '组织部管理员', '-1', NULL),
	(5, 'party', '分党委管理员', '-1', NULL),
	(6, 'branch', '党支部管理员', '-1', NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 owip.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名，工作证号、学号、个性账号',
  `passwd` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(50) NOT NULL COMMENT '密码盐',
  `role_ids` text COMMENT '所属角色，格式：,1,',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据，1老师 2学生 3其他',
  `code` varchar(20) DEFAULT NULL COMMENT '学工号，老师为工作证号，学生为学号',
  `realname` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `source_id` int(10) unsigned NOT NULL COMMENT '来源，关联元数据，后台创建、人事库、学生库',
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_sys_user_base_meta_type` (`type_id`),
  KEY `FK_sys_user_base_meta_type_2` (`source_id`),
  CONSTRAINT `FK_sys_user_base_meta_type_2` FOREIGN KEY (`source_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_sys_user_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=642 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='平台用户';

-- 正在导出表  owip.sys_user 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `passwd`, `salt`, `role_ids`, `type_id`, `code`, `realname`, `idcard`, `mobile`, `email`, `create_time`, `source_id`, `locked`) VALUES
	(5, 'admin', 'a6df748ca3c543ef07a59814c1e7230a', '0d4f07f3f2e1988777a39934e01026dd', ',1,', 50, '001', 'sdfsdf11', '', 'sdfsdf', '', '2014-08-24 11:13:07', 52, 0),
	(640, 'sdfsdf', 'ff6627539dfeb9117d33d13de355f673', '696453ab8d398d553d5a41e22ab2fcc9', NULL, 50, NULL, '', '', '', '', '2015-11-16 14:44:05', 52, 1),
	(641, 'test', 'b2612358b44b84fc9fd6fb40b6aba497', '953556bc9fc0b2fca2012c399630c9ec', NULL, 50, NULL, '', '', '', '', '2015-11-16 15:18:17', 52, 0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


-- 导出  表 owip.unit_election 结构
CREATE TABLE IF NOT EXISTS `unit_election` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type_id` int(10) unsigned NOT NULL COMMENT '统计分组，关联元数据',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '相关发文',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位换届分组记录';

-- 正在导出表  owip.unit_election 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `unit_election` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit_election` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
