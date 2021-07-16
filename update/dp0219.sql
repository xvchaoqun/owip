-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- Server version:               5.7.10 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL 版本:                  10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table db_owip.dp_member
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

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_npm
DROP TABLE IF EXISTS `dp_npm`;
CREATE TABLE IF NOT EXISTS `dp_npm` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `add_time` date DEFAULT NULL COMMENT '认定时间',
  `post` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `status` tinyint(3) unsigned DEFAULT '1' COMMENT '状态：1无党派人士 2退出人士',
  `out_time` date DEFAULT NULL COMMENT '退出时间',
  `transfer_time` date DEFAULT NULL COMMENT '退出/退出时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `remark` varchar(50) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='无党派和退出人士，无党派人士可加入中共或其他任一民主党派，故增加一个转出功能，选择党派后，自动转至相关库';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_npr
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='党外代表人士，来自于三个方面：处级干部库提取、民主党派成员库提取、管理员录入';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_om
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='其他统战人员。华侨、归侨及侨眷、欧美同学会会员、知联会员';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_party
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='民主党派';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_party_member
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='党派委员';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_party_member_group
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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='基层党组织领导班子';

-- Data exporting was unselected.
-- Dumping structure for table db_owip.dp_pr_cm
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

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
