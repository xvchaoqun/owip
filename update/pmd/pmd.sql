-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.16 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 db_owip.pmd_branch 结构
CREATE TABLE IF NOT EXISTS `pmd_branch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `month_id` int(10) unsigned NOT NULL COMMENT '所属月份',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned NOT NULL COMMENT '党支部',
  `party_name` varchar(100) NOT NULL COMMENT '所属党委',
  `branch_name` varchar(100) NOT NULL COMMENT '支部名称',
  `sort_order` int(10) unsigned NOT NULL COMMENT '党委的顺序',
  `history_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '往月延迟缴费党员数，本月已启动缴费且同步了党员信息之后计算',
  `history_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '应补缴往月党费数，本月已启动缴费且同步了党员信息之后计算',
  `has_report` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否报送，以下数据在报送时计算',
  `member_count` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '党员总数，本月启动时计算',
  `due_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月应交党费数，本月启动时计算',
  `finish_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月按时缴费党员数',
  `real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月实缴党费数',
  `online_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月线上缴费数',
  `cash_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月现金缴费数',
  `delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月延迟缴费数',
  `delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月延迟缴费党员数',
  `real_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '补缴往月党费党员数',
  `real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '实补缴往月党费数',
  `online_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '线上补缴往月党费数',
  `cash_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '现金补缴往月党费数',
  `report_user_id` int(10) unsigned DEFAULT NULL,
  `report_time` datetime DEFAULT NULL,
  `report_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `month_id_party_id_branch_id` (`month_id`,`party_id`,`branch_id`),
  CONSTRAINT `FK_pmd_branch_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='每月参与线上收费的党支部';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_branch_admin 结构
CREATE TABLE IF NOT EXISTS `pmd_branch_admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned NOT NULL COMMENT '所属党支部',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型， 1 书记 2副书记 3 组织委员 4 普通管理员',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `party_id_branch_id_user_id` (`party_id`,`branch_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党费收缴党支部管理员，系统提供同步书记、组织委员为管理员的功能';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_member 结构
CREATE TABLE IF NOT EXISTS `pmd_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `month_id` int(10) unsigned NOT NULL COMMENT '所属缴费月份',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `pay_month` date NOT NULL COMMENT '缴费月份，冗余字段',
  `party_id` int(10) unsigned NOT NULL COMMENT '所在分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所在党支部',
  `type` tinyint(3) unsigned NOT NULL COMMENT '党员类别，1 在职教职工党员 2 离退休教职工党员 3 学生党员',
  `has_salary` tinyint(1) unsigned DEFAULT NULL COMMENT '是否带薪就读，只针对学生党员',
  `talent_title` text COMMENT '人才称号',
  `post_class` varchar(50) DEFAULT NULL COMMENT '岗位类别',
  `main_post_level` varchar(50) DEFAULT NULL COMMENT '主岗等级',
  `authorized_type` varchar(50) DEFAULT NULL COMMENT '编制类别',
  `staff_type` varchar(50) DEFAULT NULL COMMENT '人员分类',
  `salary` decimal(10,2) unsigned DEFAULT NULL COMMENT '工资',
  `norm_type` tinyint(3) unsigned NOT NULL COMMENT '标准类型，根据党员的属性系统进行自动计算，0 不允许修改额度  1 可直接编辑额度  2 通过选择标准设定额度',
  `norm_name` varchar(50) DEFAULT NULL COMMENT '标准名称，系统自动计算得到',
  `norm_due_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '标准对应的额度，系统自动计算得到',
  `norm_id` int(10) unsigned DEFAULT NULL COMMENT '标准ID， 通过选择标准设定额度时使用; 如果标准的类型是"基层党委设定"，则此时需要直接编辑额度',
  `norm_value_id` int(10) unsigned DEFAULT NULL COMMENT '标准对应的固定额度ID，标准的类型为”统一标准“时使用',
  `norm_display_name` varchar(50) DEFAULT NULL COMMENT '党费缴纳标准，来源：系统自行设定名称、选择的标准的名称，展示给管理员',
  `due_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '应交金额，根据指定规则计算得到 或 由支部编辑，为空时不允许进行缴费或延迟操作',
  `real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '实交金额',
  `is_delay` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '按时/延迟缴费，0 正常 1 延迟',
  `delay_reason` varchar(255) DEFAULT NULL COMMENT '延迟缴费的原因',
  `has_pay` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '缴费状态，0 未缴费 1已缴费',
  `is_online_pay` tinyint(1) unsigned DEFAULT NULL COMMENT '缴费方式， 1 线上缴费、0 现金缴费',
  `pay_time` datetime DEFAULT NULL COMMENT '缴费时间',
  `charge_user_id` int(10) unsigned DEFAULT NULL COMMENT '收款人，现金缴费时的操作人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `month_id_user_id` (`month_id`,`user_id`),
  KEY `FK_pmd_member_pmd_norm` (`norm_id`),
  KEY `FK_pmd_member_pmd_norm_value` (`norm_value_id`),
  CONSTRAINT `FK_pmd_member_pmd_norm` FOREIGN KEY (`norm_id`) REFERENCES `pmd_norm` (`id`),
  CONSTRAINT `FK_pmd_member_pmd_norm_value` FOREIGN KEY (`norm_value_id`) REFERENCES `pmd_norm_value` (`id`),
  CONSTRAINT `pmd_member_ibfk_1` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党员每月快照，所有信息分别在每个月组织部开启缴费和支部报送时信息存档，当月报送存档后的记录不再变化。快照里只有两种情况发生：当月缴费（线上缴费或现金缴费）、当月延迟缴费';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_member_pay 结构
CREATE TABLE IF NOT EXISTS `pmd_member_pay` (
  `member_id` int(10) unsigned NOT NULL COMMENT '所属快照ID',
  `order_no` varchar(20) DEFAULT NULL COMMENT '缴费订单号，现金缴费时为空',
  `real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '实交金额',
  `has_pay` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '缴费状态，0 未缴费 1已缴费',
  `is_online_pay` tinyint(1) unsigned DEFAULT NULL COMMENT '缴费方式， 1 线上缴费、0 现金缴费',
  `pay_month_id` int(10) unsigned DEFAULT NULL COMMENT '付款时的缴费月份',
  `pay_time` datetime DEFAULT NULL COMMENT '缴费时间',
  `charge_user_id` int(10) unsigned DEFAULT NULL COMMENT '收款人，现金缴费时的操作人',
  `charge_party_id` int(10) unsigned DEFAULT NULL COMMENT '收款党委，用于统计往月补缴情况',
  `charge_branch_id` int(10) unsigned DEFAULT NULL COMMENT '收款支部',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `order_no` (`order_no`),
  KEY `FK_pmd_member_real_pay_pmd_month` (`pay_month_id`),
  CONSTRAINT `FK_pmd_member_real_pay_pmd_month` FOREIGN KEY (`pay_month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党员缴费账单，即每月实际缴纳情况，组织部开启缴费同步党员快照的同时，同步一条数据到这张表； 数据更新规则： 1、当月缴费或延迟缴费同步快照数据 2、补缴时只更新此表数据，不影响快照';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_month 结构
CREATE TABLE IF NOT EXISTS `pmd_month` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pay_month` date NOT NULL COMMENT '缴纳月份',
  `start_time` datetime DEFAULT NULL COMMENT '启动时间',
  `start_user_id` int(10) unsigned DEFAULT NULL COMMENT '启动操作人',
  `end_time` datetime DEFAULT NULL COMMENT '结算时间',
  `end_user_id` int(10) unsigned DEFAULT NULL COMMENT '结算操作人',
  `party_count` int(10) unsigned DEFAULT NULL COMMENT '分党委数，设置缴费分党委时计算',
  `history_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '往月延迟缴费党员数，本月已启动缴费且同步了党员信息之后计算',
  `history_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '应补缴往月党费数，本月已启动缴费且同步了党员信息之后计算',
  `has_report_count` int(10) unsigned DEFAULT NULL COMMENT '已报送分党委数，从此往下数据按已报送分党委汇总',
  `member_count` int(10) unsigned DEFAULT NULL COMMENT '党员总数',
  `due_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月应交党费数',
  `finish_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月按时缴费党员数',
  `real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月实缴党费数',
  `online_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月线上缴费数',
  `cash_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月现金缴费数',
  `delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月延迟缴费数',
  `delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月延迟缴费党员数',
  `real_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '补缴往月党费党员数',
  `real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '实补缴往月党费数',
  `online_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '线上补缴往月党费数',
  `cash_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '现金补缴往月党费数',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态，0 创建 1 启动缴费 2 已结算',
  `create_user_id` int(10) unsigned NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pay_month` (`pay_month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党费缴纳月份，在启动之后，结算之前，是缴费时间窗口。\r\n任何时刻只允许某一个月状态为启动缴费，且只有当前月份可能为未结算状态。\r\n不在缴费时间窗口时，缴费和补缴都不允许。结算之后设置为往月';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_norm 结构
CREATE TABLE IF NOT EXISTS `pmd_norm` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型， 1 党费缴纳标准  2 党费减免标准',
  `name` varchar(50) NOT NULL COMMENT '标准名称',
  `set_type` tinyint(3) unsigned NOT NULL COMMENT '额度设定类型， 1 统一标准  2 基层党委设定 3 免交',
  `start_time` datetime DEFAULT NULL COMMENT '启用日期',
  `end_time` datetime DEFAULT NULL COMMENT '作废日期',
  `start_user_id` int(10) unsigned DEFAULT NULL COMMENT '启用操作人',
  `end_user_id` int(10) unsigned DEFAULT NULL COMMENT '作废操作人',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态， 0 未启用  1 启用  2 作废  3 已删除， 状态只能按 0 - 1 - 2 的顺序变更',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收费标准，包括党费缴纳标准和党费减免标准两大类';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_norm_value 结构
CREATE TABLE IF NOT EXISTS `pmd_norm_value` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `norm_id` int(10) unsigned NOT NULL COMMENT '所属收费标准',
  `amount` decimal(10,2) unsigned DEFAULT NULL COMMENT '金额',
  `is_enabled` tinyint(1) unsigned NOT NULL COMMENT '是否启用，同一个收费标准下只允许启用一个固定额度，如果同一收费标准下没有启用任何一个固定额度，则这条收费标准暂时无效（支部管理员不可选择）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='固定额度';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_norm_value_log 结构
CREATE TABLE IF NOT EXISTS `pmd_norm_value_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `norm_value_id` int(10) unsigned NOT NULL COMMENT '所属固定额度',
  `start_time` datetime DEFAULT NULL COMMENT '开始使用时间',
  `end_time` datetime DEFAULT NULL COMMENT '停止使用时间',
  `start_user_id` int(10) unsigned DEFAULT NULL COMMENT '开始使用操作人',
  `end_user_id` int(10) unsigned DEFAULT NULL COMMENT '停止使用操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='固定额度使用日志';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_party 结构
CREATE TABLE IF NOT EXISTS `pmd_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `month_id` int(10) unsigned NOT NULL COMMENT '所属月份',
  `party_id` int(10) unsigned NOT NULL COMMENT '分党委',
  `is_direct_branch` tinyint(1) unsigned NOT NULL COMMENT '是否直属党支部',
  `party_name` varchar(100) NOT NULL COMMENT '分党委名称',
  `sort_order` int(10) unsigned NOT NULL COMMENT '分党委排序，当前顺序',
  `has_report` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否报送',
  `branch_count` int(10) unsigned DEFAULT NULL COMMENT '党支部数，设置缴费分党委时计算',
  `history_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '往月延迟缴费党员数，本月已启动缴费且同步了党员信息之后计算',
  `history_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '应补缴往月党费数，本月已启动缴费且同步了党员信息之后计算',
  `has_report_count` int(10) unsigned DEFAULT NULL COMMENT '已报送党支部数，从此往下数据按已报送汇总',
  `member_count` int(10) unsigned DEFAULT NULL COMMENT '党员总数，分党委：已报送支部汇总，直属党支部：本月启动时计算',
  `due_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月应交党费数，分党委：已报送支部汇总，直属党支部：本月启动时计算',
  `finish_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月按时缴费党员数',
  `real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月实缴党费数',
  `online_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月线上缴费数',
  `cash_real_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月现金缴费数',
  `delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '本月延迟缴费数',
  `delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '本月延迟缴费党员数',
  `real_delay_member_count` int(10) unsigned DEFAULT NULL COMMENT '补缴往月党费党员数',
  `real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '实补缴往月党费数',
  `online_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '线上补缴往月党费数',
  `cash_real_delay_pay` decimal(10,2) unsigned DEFAULT NULL COMMENT '现金补缴往月党费数',
  `report_user_id` int(10) unsigned DEFAULT NULL,
  `report_time` datetime DEFAULT NULL,
  `report_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `month_id_party_id` (`month_id`,`party_id`),
  CONSTRAINT `FK_pmd_party_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每月参与线上收费的分党委';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_party_admin 结构
CREATE TABLE IF NOT EXISTS `pmd_party_admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型， 1 书记 2副书记 3 组织委员 4 普通管理员',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `party_id_user_id` (`party_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='党费收缴分党委管理员，系统提供同步书记、组织委员为管理员的功能';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_pay_branch 结构
CREATE TABLE IF NOT EXISTS `pmd_pay_branch` (
  `branch_id` int(10) unsigned NOT NULL COMMENT '党支部',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `month_id` int(10) unsigned NOT NULL COMMENT '加入月份',
  PRIMARY KEY (`branch_id`),
  KEY `FK_pmd_pay_party_pmd_month` (`month_id`),
  CONSTRAINT `pmd_pay_branch_ibfk_1` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='已设置缴费的党支部，不可删除往月设置的党支部';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_pay_party 结构
CREATE TABLE IF NOT EXISTS `pmd_pay_party` (
  `party_id` int(10) unsigned NOT NULL COMMENT '分党委',
  `month_id` int(10) unsigned NOT NULL COMMENT '加入月份',
  PRIMARY KEY (`party_id`),
  KEY `FK_pmd_pay_party_pmd_month` (`month_id`),
  CONSTRAINT `FK_pmd_pay_party_pmd_month` FOREIGN KEY (`month_id`) REFERENCES `pmd_month` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='已加入线上缴费的分党委，不可删除，只允许添加';

-- 数据导出被取消选择。
-- 导出  表 db_owip.pmd_special_user 结构
CREATE TABLE IF NOT EXISTS `pmd_special_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(50) NOT NULL COMMENT '类型， eg: 年薪制教职工',
  `code` varchar(50) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `unit` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='特殊人员，党费由支部自行编辑额度';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
