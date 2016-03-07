-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.10 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.5027
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 owip 的数据库结构
CREATE DATABASE IF NOT EXISTS `owip` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `owip`;


-- 导出  表 owip.abroad_applicat_post 结构
CREATE TABLE IF NOT EXISTS `abroad_applicat_post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type_id` int(10) unsigned NOT NULL COMMENT '申请人身份',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据，唯一',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序，每个申请人身份包含的审批人分类排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `post_id` (`post_id`),
  KEY `FK_abroad_approval_order_abroad_approval_identity` (`type_id`),
  CONSTRAINT `abroad_applicat_post_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `abroad_applicat_type` (`id`),
  CONSTRAINT `abroad_applicat_post_ibfk_2` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='申请人职务属性分组';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_applicat_type 结构
CREATE TABLE IF NOT EXISTS `abroad_applicat_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL COMMENT '申请人身份',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='申请人身份';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_apply_self 结构
CREATE TABLE IF NOT EXISTS `abroad_apply_self` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned DEFAULT NULL COMMENT '干部',
  `apply_date` date DEFAULT NULL COMMENT '申请日期',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '出行时间范围，公众假期／寒暑假／其他',
  `start_date` date DEFAULT NULL COMMENT '出发时间',
  `end_date` date DEFAULT NULL COMMENT '返回时间',
  `to_country` varchar(200) DEFAULT NULL COMMENT '前往国家或地区，可多选',
  `reason` varchar(200) DEFAULT NULL COMMENT '出国事由，可多选',
  `peer_staff` varchar(100) DEFAULT NULL COMMENT '同行人员，可多选',
  `cost_source` varchar(100) DEFAULT NULL COMMENT '费用来源',
  `need_passports` varchar(100) DEFAULT NULL COMMENT '所需证件，可多选',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT 'ip',
  PRIMARY KEY (`id`),
  KEY `FK_abroad_apply_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_abroad_apply_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='因私出国申请';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_apply_self_file 结构
CREATE TABLE IF NOT EXISTS `abroad_apply_self_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `apply_id` int(10) unsigned NOT NULL COMMENT '申请记录',
  `file_name` varchar(200) NOT NULL COMMENT '原文件名称',
  `file_path` varchar(200) NOT NULL COMMENT '保存路径',
  `create_time` datetime NOT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`),
  KEY `FK__abroad_apply_self` (`apply_id`),
  CONSTRAINT `FK__abroad_apply_self` FOREIGN KEY (`apply_id`) REFERENCES `abroad_apply_self` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请因私出国其他说明材料';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_approval_log 结构
CREATE TABLE IF NOT EXISTS `abroad_approval_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `apply_id` int(10) unsigned NOT NULL COMMENT '申请记录',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '审批人',
  `type_id` int(10) unsigned DEFAULT NULL COMMENT '审批人类别，NULL代表组织部管理员',
  `od_type` tinyint(3) unsigned DEFAULT NULL COMMENT '组织部管理员审批类型，0初审，1终审（type_id为null时）',
  `status` tinyint(1) unsigned NOT NULL COMMENT '审批状态，0未通过 1通过',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '审批时间',
  `ip` varchar(50) NOT NULL COMMENT 'IP',
  PRIMARY KEY (`id`),
  KEY `FK_abroad_apply_approval_abroad_apply` (`apply_id`),
  KEY `FK_abroad_apply_approval_base_cadre` (`cadre_id`),
  KEY `FK_abroad_apply_approval_abroad_approver_type` (`type_id`),
  CONSTRAINT `FK_abroad_apply_approval_abroad_apply` FOREIGN KEY (`apply_id`) REFERENCES `abroad_apply_self` (`id`),
  CONSTRAINT `FK_abroad_apply_approval_abroad_approver_type` FOREIGN KEY (`type_id`) REFERENCES `abroad_approver_type` (`id`),
  CONSTRAINT `FK_abroad_apply_approval_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='因私出国审批记录';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_approval_order 结构
CREATE TABLE IF NOT EXISTS `abroad_approval_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `applicat_type_id` int(10) unsigned NOT NULL COMMENT '申请人身份',
  `approver_type_id` int(10) unsigned NOT NULL COMMENT '审批人分类',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序，每个申请人身份包含的审批人分类排序',
  PRIMARY KEY (`id`),
  KEY `FK_abroad_approval_order_abroad_approval_identity` (`applicat_type_id`),
  KEY `FK_abroad_approval_order_abroad_approver_type` (`approver_type_id`),
  CONSTRAINT `FK_abroad_approval_order_abroad_approval_identity` FOREIGN KEY (`applicat_type_id`) REFERENCES `abroad_applicat_type` (`id`),
  CONSTRAINT `FK_abroad_approval_order_abroad_approver_type` FOREIGN KEY (`approver_type_id`) REFERENCES `abroad_approver_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请人身份关联的审批顺序，除组织部初审、终审';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_approver 结构
CREATE TABLE IF NOT EXISTS `abroad_approver` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '干部，唯一',
  `type_id` int(10) unsigned NOT NULL COMMENT '审批人类别，本单位正职／分管校领导／党委书记／校长等',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  KEY `cadre_id` (`cadre_id`),
  CONSTRAINT `abroad_approver_ibfk_1` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `abroad_approver_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `abroad_approver_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='审批人';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_approver_type 结构
CREATE TABLE IF NOT EXISTS `abroad_approver_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1本单位正职 2分管校领导 3 其他',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批人分类';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_passport 结构
CREATE TABLE IF NOT EXISTS `abroad_passport` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `apply_id` int(10) unsigned DEFAULT NULL COMMENT '申办证件，如果是申办的情况',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '干部',
  `class_id` int(10) unsigned NOT NULL COMMENT '证件名称，关联元数据',
  `code` varchar(50) NOT NULL DEFAULT '' COMMENT '证件号码',
  `authority` varchar(50) NOT NULL DEFAULT '' COMMENT '发证机关',
  `issue_date` date DEFAULT NULL COMMENT '发证日期',
  `expiry_date` date DEFAULT NULL COMMENT '有效期',
  `keep_date` date DEFAULT NULL COMMENT '集中保管日期',
  `safe_code` varchar(50) DEFAULT '' COMMENT '存放保险柜编号',
  `is_lent` tinyint(1) unsigned DEFAULT NULL COMMENT '是否借出，（干部领取）',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '类型，1:集中管理证件 2:取消集中保管证件 3:丢失证件',
  `cancel_type` tinyint(3) unsigned DEFAULT NULL COMMENT '取消集中保管原因，1证件过期 2不再担任行政职务',
  `cancel_confirm` tinyint(1) unsigned DEFAULT NULL COMMENT '是否已确认',
  `cancel_pic` varchar(200) DEFAULT NULL COMMENT '确认单，取回证件签字拍照上传的文件',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消集中保管确认时间',
  `lost_proof` varchar(200) DEFAULT '' COMMENT '丢失证明，pdf文件',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `abolish` tinyint(1) unsigned NOT NULL COMMENT '是否作废',
  PRIMARY KEY (`id`),
  UNIQUE KEY `apply_id` (`apply_id`),
  KEY `cadre_id` (`cadre_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `FK_abroad_passport_abroad_passport_apply` FOREIGN KEY (`apply_id`) REFERENCES `abroad_passport_apply` (`id`),
  CONSTRAINT `abroad_passport_ibfk_1` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `abroad_passport_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='证件信息';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_passport_apply 结构
CREATE TABLE IF NOT EXISTS `abroad_passport_apply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '干部',
  `class_id` int(10) unsigned NOT NULL COMMENT '申办证件名称，关联元数据',
  `apply_date` date NOT NULL COMMENT '申办日期',
  `status` tinyint(3) unsigned NOT NULL COMMENT '审批状态，0待审批 1已备案 2未批准',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '审批人',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `expect_date` date DEFAULT NULL COMMENT '应交组织部日期，组织部备案后',
  `handle_date` date DEFAULT NULL COMMENT '实交组织部日期，证件交到组织部后',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  `ip` varchar(50) DEFAULT NULL COMMENT '申请IP',
  PRIMARY KEY (`id`),
  KEY `FK_abroad_passport_apply_base_cadre` (`cadre_id`),
  KEY `FK_abroad_passport_apply_base_meta_type` (`class_id`),
  KEY `FK_abroad_passport_apply_sys_user` (`user_id`),
  CONSTRAINT `FK_abroad_passport_apply_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_abroad_passport_apply_base_meta_type` FOREIGN KEY (`class_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_abroad_passport_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='申请办理因私出国证件';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_passport_draw 结构
CREATE TABLE IF NOT EXISTS `abroad_passport_draw` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '申请人，干部',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '类型，1因私 2台湾 3其他事务',
  `apply_id` int(10) unsigned DEFAULT NULL COMMENT '因私出国申请，（行程ID）',
  `passport_id` int(10) unsigned DEFAULT NULL COMMENT '证件',
  `apply_date` date NOT NULL COMMENT '申请日期',
  `start_date` date DEFAULT NULL COMMENT '出发时间，（台湾）',
  `end_date` date DEFAULT NULL COMMENT '返回时间，（台湾）',
  `reason` varchar(200) DEFAULT NULL COMMENT '出国事由，可多选，（台湾）',
  `cost_source` varchar(200) DEFAULT NULL COMMENT '费用来源，（台湾）',
  `need_sign` tinyint(1) unsigned DEFAULT NULL COMMENT '是否申请签注，（因私和台湾）',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `ip` varchar(50) DEFAULT NULL COMMENT '申请IP',
  `status` tinyint(3) unsigned NOT NULL COMMENT '审批状态，0未审批 1批准 2未批准',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '审批人',
  `approve_remark` varchar(200) DEFAULT NULL COMMENT '审批备注，未用',
  `approve_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approve_ip` varchar(50) DEFAULT NULL COMMENT '审批IP',
  `real_start_date` date DEFAULT NULL COMMENT '实际出发时间',
  `real_end_date` date DEFAULT NULL COMMENT '实际返回时间',
  `real_to_country` varchar(200) DEFAULT NULL COMMENT '实际出行国家或地区，（因私）',
  `return_date` date DEFAULT NULL COMMENT '归还时间',
  `draw_user_id` int(10) unsigned DEFAULT NULL COMMENT '领取经办人',
  `draw_time` datetime DEFAULT NULL COMMENT '领取时间',
  `draw_record` varchar(200) DEFAULT NULL COMMENT '领取证明，拍照上传',
  `real_return_date` date DEFAULT NULL COMMENT '实际归还时间',
  `draw_status` tinyint(3) unsigned NOT NULL COMMENT '领取状态，0未领取 1已领取 2已归还 3已作废',
  `return_remark` varchar(200) DEFAULT NULL COMMENT '归还备注',
  `use_record` varchar(200) DEFAULT NULL COMMENT '使用记录，拍照上传',
  `job_certify` tinyint(1) unsigned DEFAULT NULL COMMENT '是否开具在职证明，人事处',
  PRIMARY KEY (`id`),
  KEY `FK_abroad_draw_base_meta_type` (`passport_id`),
  KEY `FK_abroad_draw_abroad_apply` (`apply_id`),
  KEY `FK_abroad_draw_sys_user` (`user_id`),
  KEY `FK_abroad_passport_draw_base_cadre` (`cadre_id`),
  KEY `FK_abroad_passport_draw_sys_user` (`draw_user_id`),
  CONSTRAINT `FK_abroad_draw_abroad_apply` FOREIGN KEY (`apply_id`) REFERENCES `abroad_apply_self` (`id`),
  CONSTRAINT `FK_abroad_draw_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK_abroad_passport_draw_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_abroad_passport_draw_sys_user` FOREIGN KEY (`draw_user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `abroad_passport_draw_ibfk_1` FOREIGN KEY (`passport_id`) REFERENCES `abroad_passport` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='领取证件';

-- 数据导出被取消选择。


-- 导出  表 owip.abroad_passport_draw_file 结构
CREATE TABLE IF NOT EXISTS `abroad_passport_draw_file` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `draw_id` int(10) unsigned NOT NULL COMMENT '申请记录',
  `file_name` varchar(200) NOT NULL COMMENT '原文件名称',
  `file_path` varchar(200) NOT NULL COMMENT '保存路径',
  `create_time` datetime NOT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`),
  KEY `FK__abroad_passport_draw` (`draw_id`),
  CONSTRAINT `abroad_passport_draw_file_ibfk_1` FOREIGN KEY (`draw_id`) REFERENCES `abroad_passport_draw` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='领取证件需要的材料，（国台办批件或其他材料）';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre 结构
CREATE TABLE IF NOT EXISTS `base_cadre` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号',
  `type_id` int(10) unsigned NOT NULL COMMENT '行政级别，关联元数据',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '单位',
  `title` varchar(50) DEFAULT NULL COMMENT '职务',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `sort_order` int(11) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) NOT NULL COMMENT '状态，1现任干部库  2 临时干部库 3离任处级干部库 4离任校领导干部库',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`),
  KEY `FK_base_cadre_base_meta_type` (`type_id`),
  KEY `FK_base_cadre_base_meta_type_2` (`post_id`),
  KEY `FK_base_cadre_base_unit` (`unit_id`),
  CONSTRAINT `FK_base_cadre_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_base_meta_type_2` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_base_cadre_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='干部库';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_company 结构
CREATE TABLE IF NOT EXISTS `base_cadre_company` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `start_time` date DEFAULT NULL COMMENT '兼职起始时间',
  `unit` varchar(100) DEFAULT NULL COMMENT '兼职单位及职务',
  `report_unit` varchar(100) DEFAULT NULL COMMENT '报批单位',
  PRIMARY KEY (`id`),
  KEY `cadre_id` (`cadre_id`),
  CONSTRAINT `base_cadre_company_ibfk_1` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='干部企业兼职情况';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_course 结构
CREATE TABLE IF NOT EXISTS `base_cadre_course` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `name` varchar(100) NOT NULL COMMENT '课程名称',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1本科生，2硕士 3博士',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_course_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_cadre_course_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部教学课程';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_edu 结构
CREATE TABLE IF NOT EXISTS `base_cadre_edu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `edu_id` int(10) unsigned DEFAULT NULL COMMENT '学历，关联元数据',
  `is_high_edu` tinyint(1) unsigned DEFAULT NULL COMMENT '是否最高学历',
  `school` varchar(100) DEFAULT NULL COMMENT '毕业学校',
  `dep` varchar(100) DEFAULT NULL COMMENT '院系',
  `major` varchar(100) DEFAULT NULL COMMENT '所学专业',
  `school_type` int(10) unsigned DEFAULT NULL COMMENT '学校类型， 关联元数据，本校or境内or境外',
  `enrol_time` date DEFAULT NULL COMMENT '入学时间',
  `finish_time` date DEFAULT NULL COMMENT '毕业时间',
  `school_len` tinyint(3) unsigned DEFAULT NULL COMMENT '学制，数字',
  `learn_style` int(10) unsigned DEFAULT NULL COMMENT '学习方式，关联元数据，全日制教育or在职教育',
  `degree` varchar(100) DEFAULT NULL COMMENT '学位',
  `is_high_degree` tinyint(1) unsigned DEFAULT NULL COMMENT '是否为最高学位',
  `degree_country` varchar(50) DEFAULT NULL COMMENT '学位授予国家',
  `degree_unit` varchar(100) DEFAULT NULL COMMENT '学位授予单位',
  `degree_time` date DEFAULT NULL COMMENT '学位授予日期',
  `tutor_name` varchar(50) DEFAULT NULL COMMENT '导师姓名',
  `tutor_unit` varchar(100) DEFAULT NULL COMMENT '导师目前所在单位及职务',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_edu_base_cadre` (`cadre_id`),
  KEY `FK_base_cadre_edu_base_meta_type` (`edu_id`),
  KEY `FK_base_cadre_edu_base_meta_type_2` (`school_type`),
  KEY `FK_base_cadre_edu_base_meta_type_3` (`learn_style`),
  CONSTRAINT `FK_base_cadre_edu_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_edu_base_meta_type` FOREIGN KEY (`edu_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_edu_base_meta_type_2` FOREIGN KEY (`school_type`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_edu_base_meta_type_3` FOREIGN KEY (`learn_style`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部学习经历';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_famliy 结构
CREATE TABLE IF NOT EXISTS `base_cadre_famliy` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `title` tinyint(3) unsigned DEFAULT NULL COMMENT '称谓，1父亲，2母亲， 3配偶， 4儿子， 5女儿',
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `birthday` date DEFAULT NULL COMMENT '出生年月',
  `political_status` int(10) unsigned DEFAULT NULL COMMENT '政治面貌，关联元数据',
  `unit` varchar(100) DEFAULT NULL COMMENT '工作单位及职务',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_famliy_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_cadre_famliy_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家庭成员信息';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_famliy_abroad 结构
CREATE TABLE IF NOT EXISTS `base_cadre_famliy_abroad` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned DEFAULT NULL COMMENT '所属干部',
  `famliy_id` int(10) unsigned DEFAULT NULL COMMENT '所选家庭成员',
  `type` int(10) unsigned DEFAULT NULL COMMENT '移居类别，关联元数据，外国国籍or永久居留权or长期居留许可',
  `abroad_time` date DEFAULT NULL COMMENT '移居时间',
  `country` varchar(100) DEFAULT NULL COMMENT '移居国家',
  `city` varchar(100) DEFAULT NULL COMMENT '现居住城市',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_famliy_abroad_base_cadre` (`cadre_id`),
  KEY `FK_base_cadre_famliy_abroad_base_cadre_famliy` (`famliy_id`),
  CONSTRAINT `FK_base_cadre_famliy_abroad_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_famliy_abroad_base_cadre_famliy` FOREIGN KEY (`famliy_id`) REFERENCES `base_cadre_famliy` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='家庭成员海外情况';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_info 结构
CREATE TABLE IF NOT EXISTS `base_cadre_info` (
  `cadre_id` int(10) unsigned NOT NULL COMMENT '干部ID',
  `mobile` varchar(11) NOT NULL COMMENT '手机号',
  `office_phone` varchar(20) NOT NULL COMMENT '办公电话',
  `home_phone` varchar(20) NOT NULL COMMENT '家庭电话',
  `email` varchar(50) NOT NULL COMMENT '电子邮箱',
  PRIMARY KEY (`cadre_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='干部联系方式';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_main_work 结构
CREATE TABLE IF NOT EXISTS `base_cadre_main_work` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部，每个干部只有一个主职',
  `work` varchar(100) DEFAULT NULL COMMENT '主职',
  `post_id` int(10) unsigned DEFAULT NULL COMMENT '职务属性，关联元数据',
  `admin_level_id` int(10) unsigned DEFAULT NULL COMMENT '行政级别，关联元数据',
  `is_positive` tinyint(1) unsigned DEFAULT NULL COMMENT '是否正职',
  `post_class_id` int(10) unsigned DEFAULT NULL COMMENT '职务类别，关联元数据',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '所属单位',
  `post_time` date DEFAULT NULL COMMENT '任职日期',
  `dispatchs` varchar(200) DEFAULT NULL COMMENT '关联干部发文，逗号分隔',
  `start_time` date DEFAULT NULL COMMENT '现职务始任日期，计算现职务任职年限',
  `dispatch_cadre_id` int(10) unsigned DEFAULT NULL COMMENT '现职务始任文号',
  `is_double` tinyint(1) unsigned DEFAULT NULL COMMENT '是否双肩挑',
  `double_unit_id` int(10) unsigned DEFAULT NULL COMMENT '双肩挑单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cadre_id` (`cadre_id`),
  KEY `FK_base_cadre_main_work_base_meta_type` (`post_id`),
  KEY `FK_base_cadre_main_work_base_meta_type_2` (`admin_level_id`),
  KEY `FK_base_cadre_main_work_base_meta_type_3` (`post_class_id`),
  KEY `FK_base_cadre_main_work_base_unit` (`unit_id`),
  KEY `FK_base_cadre_main_work_base_dispatch_cadre` (`dispatch_cadre_id`),
  KEY `FK_base_cadre_main_work_base_unit_2` (`double_unit_id`),
  CONSTRAINT `FK_base_cadre_main_work_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_dispatch_cadre` FOREIGN KEY (`dispatch_cadre_id`) REFERENCES `base_dispatch_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_meta_type_2` FOREIGN KEY (`admin_level_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_meta_type_3` FOREIGN KEY (`post_class_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_base_cadre_main_work_base_unit_2` FOREIGN KEY (`double_unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部主职';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_parttime 结构
CREATE TABLE IF NOT EXISTS `base_cadre_parttime` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `start_time` date DEFAULT NULL COMMENT '起始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间，留空 至今',
  `unit` varchar(100) DEFAULT NULL COMMENT '兼职单位',
  `post` varchar(100) DEFAULT NULL COMMENT '兼任职务',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_parttime_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_cadre_parttime_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部社会或学术兼职';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_post 结构
CREATE TABLE IF NOT EXISTS `base_cadre_post` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '关联干部',
  `admin_level_id` int(10) unsigned NOT NULL COMMENT '行政级别',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任职级',
  `start_time` date DEFAULT NULL COMMENT '职级始任日期',
  `start_dispatch_cadre_id` int(10) unsigned DEFAULT NULL COMMENT '职级始任文号',
  `end_time` date DEFAULT NULL COMMENT '职级结束日期',
  `end_dispatch_cadre_id` int(10) unsigned DEFAULT NULL COMMENT '职级结束文号',
  `remark` varchar(50) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cadre_id_admin_level_id` (`cadre_id`,`admin_level_id`),
  KEY `FK_base_cadre_post_base_meta_type` (`admin_level_id`),
  KEY `FK_base_cadre_post_base_dispatch_cadre` (`start_dispatch_cadre_id`),
  KEY `FK_base_cadre_post_base_dispatch_cadre_2` (`end_dispatch_cadre_id`),
  CONSTRAINT `FK_base_cadre_post_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_post_base_dispatch_cadre` FOREIGN KEY (`start_dispatch_cadre_id`) REFERENCES `base_dispatch_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_post_base_dispatch_cadre_2` FOREIGN KEY (`end_dispatch_cadre_id`) REFERENCES `base_dispatch_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_post_base_meta_type` FOREIGN KEY (`admin_level_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任职级经历';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_research 结构
CREATE TABLE IF NOT EXISTS `base_cadre_research` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned DEFAULT NULL COMMENT '所属干部，留空即为模板',
  `chair_file` varchar(200) DEFAULT NULL COMMENT '主持科研项目情况',
  `chair_file_name` varchar(200) DEFAULT NULL,
  `join_file` varchar(200) DEFAULT NULL COMMENT '参与科研项目情况',
  `join_file_name` varchar(200) DEFAULT NULL,
  `publish_file` varchar(200) DEFAULT NULL COMMENT '出版著作及发表论文等情况',
  `publish_file_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cadre_id` (`cadre_id`),
  CONSTRAINT `base_cadre_research_ibfk_1` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='干部科研情况';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_reward 结构
CREATE TABLE IF NOT EXISTS `base_cadre_reward` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `reward_time` date DEFAULT NULL COMMENT '日期',
  `name` varchar(200) DEFAULT NULL COMMENT '获得奖项',
  `unit` varchar(100) DEFAULT NULL COMMENT '颁奖单位',
  `rank` int(10) unsigned DEFAULT NULL COMMENT '排名',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别， 1,教学成果及获奖情况 2科研成果及获奖情况， 3其他奖励情况',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_teach_reward_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_cadre_teach_reward_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='干部奖励信息';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_sub_work 结构
CREATE TABLE IF NOT EXISTS `base_cadre_sub_work` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '关联干部',
  `unit_id` int(10) unsigned DEFAULT NULL COMMENT '兼职单位',
  `post` varchar(50) DEFAULT NULL COMMENT '兼任职务',
  `post_time` date DEFAULT NULL COMMENT '兼任职务任职日期',
  `dispatchs` varchar(200) DEFAULT NULL COMMENT '关联干部发文，逗号分隔',
  `start_time` date DEFAULT NULL COMMENT '兼任职务始任日期',
  `dispatch_cadre_id` int(10) unsigned DEFAULT NULL COMMENT '兼任职务始任文号',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_sub_work_base_cadre` (`cadre_id`),
  KEY `FK_base_cadre_sub_work_base_dispatch_cadre` (`dispatch_cadre_id`),
  KEY `FK_base_cadre_sub_work_base_unit` (`unit_id`),
  CONSTRAINT `FK_base_cadre_sub_work_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_sub_work_base_dispatch_cadre` FOREIGN KEY (`dispatch_cadre_id`) REFERENCES `base_dispatch_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_sub_work_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部兼职';

-- 数据导出被取消选择。


-- 导出  表 owip.base_cadre_work 结构
CREATE TABLE IF NOT EXISTS `base_cadre_work` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '所属工作经历，即期间经历',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `start_time` date DEFAULT NULL COMMENT '开始日期',
  `end_time` date DEFAULT NULL COMMENT '结束日期',
  `unit` varchar(100) DEFAULT NULL COMMENT '工作单位',
  `post` varchar(100) DEFAULT NULL COMMENT '担任职务或者专技职务',
  `type_id` int(10) unsigned DEFAULT NULL COMMENT '行政级别，关联元数据',
  `work_type` int(10) unsigned DEFAULT NULL COMMENT '院系/机关工作经历，1院系工作经历”2 机关工作经历”',
  `dispatchs` varchar(100) DEFAULT NULL COMMENT '关联发文，逗号分隔',
  `remark` varchar(300) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `FK_base_cadre_work_base_cadre_work` (`fid`),
  KEY `FK_base_cadre_work_base_cadre` (`cadre_id`),
  KEY `FK_base_cadre_work_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_cadre_work_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_cadre_work_base_cadre_work` FOREIGN KEY (`fid`) REFERENCES `base_cadre_work` (`id`),
  CONSTRAINT `FK_base_cadre_work_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作经历';

-- 数据导出被取消选择。


-- 导出  表 owip.base_country 结构
CREATE TABLE IF NOT EXISTS `base_country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `abbr` varchar(100) DEFAULT NULL COMMENT '英文缩写',
  `cninfo` varchar(200) DEFAULT NULL COMMENT '中文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。


-- 导出  表 owip.base_dispatch 结构
CREATE TABLE IF NOT EXISTS `base_dispatch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `year` int(10) unsigned NOT NULL COMMENT '年份',
  `dispatch_type_id` int(10) unsigned NOT NULL COMMENT '发文类型',
  `code` int(10) unsigned NOT NULL COMMENT '发文号，自动生成，比如师党干[2015]年01号',
  `meeting_time` date DEFAULT NULL COMMENT '党委常委会日期',
  `pub_time` date NOT NULL COMMENT '发文日期',
  `work_time` date NOT NULL COMMENT '任免日期',
  `file` varchar(200) DEFAULT NULL COMMENT '任免文件',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `ppt` varchar(200) DEFAULT NULL COMMENT '上会ppt',
  `ppt_name` varchar(100) DEFAULT NULL COMMENT 'ppt文件名',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `year_dispatch_type_id_code` (`year`,`dispatch_type_id`,`code`),
  KEY `FK_base_dispatch_base_dispatch_type` (`dispatch_type_id`),
  CONSTRAINT `FK_base_dispatch_base_dispatch_type` FOREIGN KEY (`dispatch_type_id`) REFERENCES `base_dispatch_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发文';

-- 数据导出被取消选择。


-- 导出  表 owip.base_dispatch_cadre 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_cadre` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '所属发文',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '所属干部',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1任命 2免职',
  `cadre_type_id` int(10) unsigned NOT NULL COMMENT '干部类型，关联元数据',
  `way_id` int(10) unsigned NOT NULL COMMENT '任免方式，关联元数据（1 提任 2连任 3平级调动 4免职）',
  `procedure_id` int(10) unsigned NOT NULL COMMENT '任免程序，关联元数据（1 民主推荐 2公开招聘 3引进人才 4其他 5免职）',
  `post` varchar(50) NOT NULL COMMENT '职务',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `admin_level_id` int(10) unsigned NOT NULL COMMENT '行政级别，关联元数据',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_dispatch_cadre_dispatch` (`dispatch_id`),
  KEY `FK_dispatch_cadre_base_meta_type_2` (`way_id`),
  KEY `FK_dispatch_cadre_base_meta_type_3` (`procedure_id`),
  KEY `FK_dispatch_cadre_base_unit` (`unit_id`),
  KEY `FK_dispatch_cadre_base_meta_type_4` (`post_id`),
  KEY `FK_dispatch_cadre_base_meta_type_5` (`admin_level_id`),
  KEY `FK_base_dispatch_cadre_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_dispatch_cadre_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_2` FOREIGN KEY (`way_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_3` FOREIGN KEY (`procedure_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_4` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_meta_type_5` FOREIGN KEY (`admin_level_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_cadre_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_cadre_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `base_dispatch` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='干部发文，将发文进行干部认领，标识为干部任免发文';

-- 数据导出被取消选择。


-- 导出  表 owip.base_dispatch_type 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `attr` varchar(50) DEFAULT NULL COMMENT '发文属性',
  `year` smallint(5) unsigned DEFAULT NULL COMMENT '所属年份',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发文类型';

-- 数据导出被取消选择。


-- 导出  表 owip.base_dispatch_unit 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '发文',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `type_id` int(10) unsigned NOT NULL COMMENT '类型，关联元数据，1成立/2更名/3撤销',
  `year` int(10) unsigned NOT NULL COMMENT '年份',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_dispatch_unit_dispatch` (`dispatch_id`),
  KEY `FK_dispatch_unit_base_unit` (`unit_id`),
  KEY `FK_dispatch_unit_base_meta_type` (`type_id`),
  CONSTRAINT `FK_dispatch_unit_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_dispatch_unit_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_unit_dispatch` FOREIGN KEY (`dispatch_id`) REFERENCES `base_dispatch` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位发文，将发文进行单位认领';

-- 数据导出被取消选择。


-- 导出  表 owip.base_dispatch_unit_relate 结构
CREATE TABLE IF NOT EXISTS `base_dispatch_unit_relate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `dispatch_unit_id` int(10) unsigned NOT NULL COMMENT '单位发文',
  `unit_id` int(10) unsigned NOT NULL COMMENT '关联单位',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dispatch_unit_id_unit_id` (`dispatch_unit_id`,`unit_id`),
  KEY `FK_dispatch_unit_relate_base_unit` (`unit_id`),
  CONSTRAINT `FK_dispatch_unit_relate_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_dispatch_unit_relate_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位发文关联单位';

-- 数据导出被取消选择。


-- 导出  表 owip.base_history_unit 结构
CREATE TABLE IF NOT EXISTS `base_history_unit` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `unit_id` int(10) unsigned NOT NULL COMMENT '单位',
  `old_unit_id` int(10) unsigned NOT NULL COMMENT '历史单位',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_id_old_unit_id` (`unit_id`,`old_unit_id`),
  KEY `FK_base_history_unit_base_unit_2` (`old_unit_id`),
  CONSTRAINT `FK_base_history_unit_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_base_history_unit_base_unit_2` FOREIGN KEY (`old_unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='历史单位，单位的历史单位记录';

-- 数据导出被取消选择。


-- 导出  表 owip.base_leader 结构
CREATE TABLE IF NOT EXISTS `base_leader` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '校领导，关联干部',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据，党委、行政、校长助理等',
  `job` varchar(200) NOT NULL COMMENT '分管工作',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cadre_id_type_id` (`cadre_id`,`type_id`),
  KEY `FK_base_leader_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_leader_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_leader_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='校领导';

-- 数据导出被取消选择。


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='校领导单位，校领导关联的单位，比如分管机关部门、联系部、院、系所';

-- 数据导出被取消选择。


-- 导出  表 owip.base_location 结构
CREATE TABLE IF NOT EXISTS `base_location` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` int(10) unsigned NOT NULL,
  `parent_code` int(10) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='省、市、地区';

-- 数据导出被取消选择。


-- 导出  表 owip.base_meta_class 结构
CREATE TABLE IF NOT EXISTS `base_meta_class` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` int(10) unsigned DEFAULT NULL COMMENT '所属角色',
  `name` varchar(50) NOT NULL COMMENT '名称，行政级别、发文类型',
  `first_level` varchar(100) DEFAULT NULL COMMENT '所属一级目录',
  `second_level` varchar(100) DEFAULT NULL COMMENT '所属二级目录',
  `code` varchar(50) NOT NULL COMMENT '代码，编程代码（随机生成，可修改）',
  `bool_attr` varchar(20) DEFAULT NULL COMMENT '布尔属性名称，元数据类型中bool_attr字段代表的意思，比如是否是正职（职务属性中）',
  `extra_attr` varchar(100) DEFAULT NULL COMMENT '附加属性名称，元数据类型中extra_attr字段代表的意思，比如党务、行政（发文类型中）',
  `sort_order` int(11) unsigned DEFAULT NULL COMMENT '排序',
  `available` tinyint(1) unsigned NOT NULL COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据分类';

-- 数据导出被取消选择。


-- 导出  表 owip.base_meta_type 结构
CREATE TABLE IF NOT EXISTS `base_meta_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `class_id` int(10) unsigned NOT NULL COMMENT '所属分类',
  `name` varchar(50) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '代码，编程代码，（暂时未用）',
  `bool_attr` tinyint(1) unsigned DEFAULT NULL COMMENT '布尔属性',
  `extra_attr` varchar(100) DEFAULT NULL COMMENT '附加属性',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序，在各个分类中进行',
  `available` tinyint(1) unsigned NOT NULL COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_base_meta_type_base_meta_class` (`class_id`),
  CONSTRAINT `FK_base_meta_type_base_meta_class` FOREIGN KEY (`class_id`) REFERENCES `base_meta_class` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='元数据属性，元数据分类的取值';

-- 数据导出被取消选择。


-- 导出  表 owip.base_short_msg 结构
CREATE TABLE IF NOT EXISTS `base_short_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` int(11) DEFAULT NULL COMMENT '类别',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `content` varchar(200) DEFAULT NULL COMMENT '短信内容',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  `status` varchar(50) DEFAULT NULL COMMENT '接口返回状态',
  `sender_id` int(10) unsigned DEFAULT NULL COMMENT '发送方',
  `receiver_id` int(10) unsigned DEFAULT NULL COMMENT '接收方',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信';

-- 数据导出被取消选择。


-- 导出  表 owip.base_table 结构
CREATE TABLE IF NOT EXISTS `base_table` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '表名',
  `name` varchar(50) NOT NULL COMMENT '中文名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据表';

-- 数据导出被取消选择。


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

-- 数据导出被取消选择。


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
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态，1正在运转单位、2历史单位',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_base_unit_base_meta_type` (`type_id`),
  CONSTRAINT `FK_base_unit_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='单位';

-- 数据导出被取消选择。


-- 导出  表 owip.base_unit_admin 结构
CREATE TABLE IF NOT EXISTS `base_unit_admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属班子',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '关联干部',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `is_admin` tinyint(1) unsigned DEFAULT '0' COMMENT '是否管理员，暂未用',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_base_unit_admin_base_unit_admin_group` (`group_id`),
  KEY `FK_base_unit_admin_base_cadre` (`cadre_id`),
  KEY `FK_base_unit_admin_base_meta_type` (`post_id`),
  CONSTRAINT `FK_base_unit_admin_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_unit_admin_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_base_unit_admin_base_unit_admin_group` FOREIGN KEY (`group_id`) REFERENCES `base_unit_admin_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='行政班子成员信息';

-- 数据导出被取消选择。


-- 导出  表 owip.base_unit_admin_group 结构
CREATE TABLE IF NOT EXISTS `base_unit_admin_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '上一届',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任班子',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date NOT NULL COMMENT '任命时间，本届班子任命时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_base_unit_admin_group_base_unit_admin_group` (`fid`),
  KEY `FK_base_unit_admin_group_base_unit` (`unit_id`),
  CONSTRAINT `FK_base_unit_admin_group_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
  CONSTRAINT `FK_base_unit_admin_group_base_unit_admin_group` FOREIGN KEY (`fid`) REFERENCES `base_unit_admin_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='单位行政班子';

-- 数据导出被取消选择。


-- 导出  表 owip.base_unit_cadre_transfer 结构
CREATE TABLE IF NOT EXISTS `base_unit_cadre_transfer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属分组',
  `cadre_id` int(10) unsigned NOT NULL COMMENT '关联干部',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `post_id` int(10) unsigned NOT NULL COMMENT '职务属性，关联元数据',
  `appoint_time` date NOT NULL COMMENT '任职日期',
  `dismiss_time` date NOT NULL COMMENT '免职日期',
  `dispatchs` varchar(200) DEFAULT NULL COMMENT '相关发文，逗号分隔',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_cadre_id` (`group_id`,`cadre_id`),
  KEY `FK_base_unit_cadre_transfer_base_meta_type` (`post_id`),
  KEY `FK_base_unit_cadre_transfer_base_cadre` (`cadre_id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
  CONSTRAINT `FK_base_unit_cadre_transfer_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位任免记录，按组划分干部任免的发文';

-- 数据导出被取消选择。


-- 导出  表 owip.base_unit_cadre_transfer_group 结构
CREATE TABLE IF NOT EXISTS `base_unit_cadre_transfer_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `name` varchar(50) NOT NULL COMMENT '分组名称，（比如“历任书记”）',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unit_id_name` (`unit_id`,`name`),
  CONSTRAINT `FK_base_unit_cadre_transfer_group_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位任免分组，干部任免记录分组';

-- 数据导出被取消选择。


-- 导出  表 owip.base_unit_transfer 结构
CREATE TABLE IF NOT EXISTS `base_unit_transfer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `subject` varchar(200) NOT NULL COMMENT '文件主题',
  `content` longtext COMMENT '文件具体内容',
  `pub_time` date NOT NULL COMMENT '日期',
  `dispatchs` varchar(200) DEFAULT NULL COMMENT '相关发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_base_unit_transfer_base_unit` (`unit_id`),
  CONSTRAINT `FK_base_unit_transfer_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='单位变更';

-- 数据导出被取消选择。


-- 导出  表 owip.ext_bks 结构
CREATE TABLE IF NOT EXISTS `ext_bks` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `xh` varchar(100) DEFAULT NULL COMMENT '学号',
  `xm` varchar(30) DEFAULT NULL COMMENT '姓名',
  `xb` varchar(100) DEFAULT NULL COMMENT '性别',
  `csrq` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `nj` varchar(100) DEFAULT NULL COMMENT '年级',
  `yxmc` varchar(100) DEFAULT NULL COMMENT '院系名称',
  `zymc` varchar(100) DEFAULT NULL COMMENT '专业名称',
  `fgyx` varchar(100) DEFAULT NULL COMMENT '分管院系',
  `pyfs` varchar(100) DEFAULT NULL COMMENT '培养方式',
  `mz` varchar(100) DEFAULT NULL COMMENT '民族',
  `zzmm` varchar(100) DEFAULT NULL COMMENT '政治面貌',
  `xjbd` varchar(100) DEFAULT NULL COMMENT '学籍变动',
  `xjrq` varchar(20) DEFAULT NULL COMMENT '变动日期',
  `xjyy` varchar(100) DEFAULT NULL COMMENT '变动原因',
  `sf` varchar(100) DEFAULT NULL COMMENT '省份',
  `dq` varchar(100) DEFAULT NULL COMMENT '地区',
  `syxx` varchar(100) DEFAULT NULL COMMENT '中学名称',
  `dxhwpdw` varchar(100) DEFAULT NULL,
  `dxdwszd` varchar(100) DEFAULT NULL,
  `ksh` varchar(20) DEFAULT NULL COMMENT '考生号',
  `xmpy` varchar(30) DEFAULT NULL COMMENT '姓名拼音',
  `kslb` varchar(10) DEFAULT NULL COMMENT '考生类别',
  `wyyz` varchar(100) DEFAULT NULL,
  `jtdz` varchar(150) DEFAULT NULL COMMENT '家庭地址',
  `yzbm` varchar(20) DEFAULT NULL COMMENT '邮政编码',
  `lxdh` varchar(100) DEFAULT NULL COMMENT '联系电话',
  `kl` varchar(100) DEFAULT NULL COMMENT '科类',
  `lqzymc` varchar(100) DEFAULT NULL COMMENT '录取专业名称',
  `lqyxmc` varchar(100) DEFAULT NULL COMMENT '录取院系名称',
  `mfsfs` varchar(100) DEFAULT NULL COMMENT '免费师范生否',
  `sfzh` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `kstc` varchar(1000) DEFAULT NULL COMMENT '考生特长',
  `grpj` varchar(2000) DEFAULT NULL COMMENT '个人评价',
  `ksjl` varchar(300) DEFAULT NULL COMMENT '考试奖励',
  `xkml` varchar(100) DEFAULT NULL COMMENT '学科门类',
  `zkzh` varchar(100) DEFAULT NULL,
  `byxx` varchar(100) DEFAULT NULL COMMENT '毕业学校',
  `bz` varchar(4000) DEFAULT NULL COMMENT '备注',
  `sfgfs` varchar(20) DEFAULT NULL COMMENT '是否国防生',
  `import_time_millis` varchar(30) DEFAULT NULL COMMENT '导入批次',
  `yxsh` varchar(30) DEFAULT NULL COMMENT '院系所号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `xh` (`xh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学籍基本数据信息(本科生基本信息子表)';

-- 数据导出被取消选择。


-- 导出  表 owip.ext_jzg 结构
CREATE TABLE IF NOT EXISTS `ext_jzg` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `zgh` varchar(32) NOT NULL COMMENT '职工号',
  `xm` varchar(32) DEFAULT NULL COMMENT '姓名',
  `xbm` varchar(32) DEFAULT NULL COMMENT '性别码',
  `xb` varchar(32) DEFAULT NULL,
  `dwdm` varchar(32) DEFAULT NULL COMMENT '单位ID',
  `dwmc` varchar(100) DEFAULT NULL,
  `yjxkm` varchar(255) DEFAULT NULL COMMENT '一级学科码',
  `yjxk` varchar(255) DEFAULT NULL,
  `lxrq` date DEFAULT NULL COMMENT '来校日期',
  `gwlbm` varchar(32) DEFAULT NULL COMMENT '岗位类别码',
  `gwlb` varchar(32) DEFAULT NULL,
  `zcm` varchar(255) DEFAULT NULL COMMENT '职称码',
  `zc` varchar(255) DEFAULT NULL,
  `gwjbm` varchar(32) DEFAULT NULL COMMENT '岗位级别码',
  `gwjb` varchar(32) DEFAULT NULL,
  `rclxm` varchar(32) DEFAULT NULL COMMENT '人才类型码',
  `rclx` varchar(32) DEFAULT NULL,
  `rcchm` varchar(32) DEFAULT NULL COMMENT '人才称号码',
  `rcch` varchar(32) DEFAULT NULL,
  `xyjgm` varchar(255) DEFAULT NULL COMMENT '学缘结构码',
  `xyjg` varchar(255) DEFAULT NULL,
  `gjm` varchar(255) DEFAULT NULL COMMENT '国籍码',
  `gj` varchar(255) DEFAULT NULL,
  `zhxwm` varchar(32) DEFAULT NULL COMMENT '最后学位码',
  `zhxw` varchar(32) DEFAULT NULL,
  `zhxl` varchar(32) DEFAULT NULL COMMENT '最后学历码',
  `zhxlmc` varchar(32) DEFAULT NULL,
  `xlbyxx` varchar(100) DEFAULT NULL COMMENT '学历毕业学校',
  `xwsyxx` varchar(100) DEFAULT NULL COMMENT '学位授予学校',
  `xzjbm` varchar(255) DEFAULT NULL COMMENT '行政级别码',
  `xzjb` varchar(255) DEFAULT NULL,
  `sfzgm` varchar(255) DEFAULT NULL COMMENT '是否在岗码',
  `sfzg` varchar(255) DEFAULT NULL,
  `ryztm` varchar(32) DEFAULT NULL COMMENT '人员状态码',
  `ryzt` varchar(32) DEFAULT NULL,
  `rylxm` varchar(255) DEFAULT NULL COMMENT '人员类型码',
  `rylx` varchar(255) DEFAULT NULL,
  `bzlxm` varchar(32) DEFAULT NULL COMMENT '编制类型码',
  `bzlx` varchar(32) DEFAULT NULL,
  `csrq` date DEFAULT NULL COMMENT '出生日期',
  `rszfm` varchar(255) DEFAULT NULL COMMENT '人事转否码',
  `rszf` varchar(255) DEFAULT NULL,
  `sfnxz` varchar(255) DEFAULT NULL COMMENT '是否年薪码',
  `sfzjlx` varchar(255) DEFAULT NULL COMMENT '身份证件类型',
  `name` varchar(255) DEFAULT NULL,
  `sfzh` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `yddh` varchar(128) DEFAULT NULL COMMENT '移动电话',
  `dzxx` varchar(64) DEFAULT NULL COMMENT '电子信箱',
  `bgdh` varchar(32) DEFAULT NULL COMMENT '办公电话',
  `jtdh` varchar(32) DEFAULT NULL COMMENT '家庭电话',
  `zjgjm` varchar(32) DEFAULT NULL COMMENT '专技岗位等级码',
  `zjgwdj` varchar(32) DEFAULT NULL,
  `glgjm` varchar(32) DEFAULT NULL COMMENT '管理岗位等级码',
  `glgwdj` varchar(32) DEFAULT NULL,
  `empid` varchar(32) DEFAULT NULL COMMENT '教职工主键',
  `jg` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `zzmm` varchar(255) DEFAULT NULL COMMENT '政治面貌',
  `zw` varchar(255) DEFAULT NULL COMMENT '职务',
  `zwmc` varchar(255) DEFAULT NULL,
  `mz` varchar(255) DEFAULT NULL COMMENT '民族',
  `xkmlm` varchar(255) DEFAULT NULL,
  `xkml` varchar(255) DEFAULT NULL COMMENT '学科门类',
  `ejxkm` varchar(255) DEFAULT NULL,
  `ejxk` varchar(255) DEFAULT NULL COMMENT '二级学科',
  `xmpy` varchar(255) DEFAULT NULL COMMENT '姓名拼音',
  `poxm` varchar(255) DEFAULT NULL COMMENT '配偶姓名',
  `ryxxshzt` varchar(255) DEFAULT NULL COMMENT '人员信息审核状态',
  `tbrq` varchar(20) DEFAULT NULL COMMENT '数据同步日期',
  `jzdw1` varchar(255) DEFAULT NULL COMMENT '兼职单位1',
  `jzdw2` varchar(255) DEFAULT NULL COMMENT '兼职单位2',
  `batch_num` varchar(30) DEFAULT NULL COMMENT '业务批次',
  `basic_active_id` varchar(30) DEFAULT NULL,
  `domain_code` varchar(30) DEFAULT NULL,
  `basic_active_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `zgh` (`zgh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人事_教职工基本信息表';

-- 数据导出被取消选择。


-- 导出  表 owip.ext_yjs 结构
CREATE TABLE IF NOT EXISTS `ext_yjs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `xh` varchar(20) NOT NULL COMMENT '学号',
  `ztm` varchar(2) DEFAULT NULL COMMENT '状态码',
  `zt` varchar(20) DEFAULT NULL COMMENT '状态',
  `gbm` varchar(1) DEFAULT NULL COMMENT '国别码',
  `gb` varchar(20) DEFAULT NULL COMMENT '国别',
  `xslbm2` varchar(2) DEFAULT NULL COMMENT '学生类别码',
  `xslb` varchar(40) DEFAULT NULL,
  `xm` varchar(40) DEFAULT NULL COMMENT '姓名',
  `xmpy` varchar(80) DEFAULT NULL COMMENT '姓名拼音',
  `xbm` varchar(1) DEFAULT NULL COMMENT '性别',
  `xb` varchar(10) DEFAULT NULL,
  `csrq` varchar(10) DEFAULT NULL COMMENT '出生日期',
  `sfzh` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `mzm` varchar(2) DEFAULT NULL COMMENT '民族码',
  `mz` varchar(20) DEFAULT NULL,
  `pyccm` varchar(1) DEFAULT NULL COMMENT '培养层次',
  `pycc` varchar(20) DEFAULT NULL,
  `pylxm` varchar(1) DEFAULT NULL COMMENT '培养类型',
  `pylx` varchar(20) DEFAULT NULL,
  `jylbm` varchar(1) DEFAULT NULL COMMENT '教育类别',
  `jylb` varchar(20) DEFAULT NULL,
  `pyfsm` varchar(2) DEFAULT NULL COMMENT '培养方式',
  `pyfs` varchar(20) DEFAULT NULL,
  `ksh` varchar(16) DEFAULT NULL COMMENT '考生编号',
  `yxsh` varchar(6) DEFAULT NULL COMMENT '院系号',
  `yxsm` varchar(3) DEFAULT NULL COMMENT '部院系所',
  `yxsmc` varchar(60) DEFAULT NULL COMMENT '院系名',
  `yjxkm` varchar(4) DEFAULT NULL COMMENT '一级学科码',
  `yjxkmc` varchar(40) DEFAULT NULL COMMENT '一级学科名称',
  `zydm` varchar(6) DEFAULT NULL COMMENT '专业代码',
  `zymc` varchar(50) DEFAULT NULL COMMENT '专业名称',
  `yjfxm` varchar(2) DEFAULT NULL COMMENT '方向代码',
  `yjfxmc` varchar(50) DEFAULT NULL COMMENT '方向名称',
  `dsh` varchar(50) DEFAULT NULL COMMENT '导师号',
  `dsxm` varchar(40) DEFAULT NULL COMMENT '导师姓名',
  `lqlbm` varchar(2) DEFAULT NULL COMMENT '录取类别',
  `lqlb` varchar(20) DEFAULT NULL,
  `dxwpdw` varchar(100) DEFAULT NULL COMMENT '定向委培单位',
  `daszdw` varchar(80) DEFAULT NULL COMMENT '档案所在单位',
  `zsnd` int(11) DEFAULT NULL COMMENT '招生年度',
  `blzgnx` int(11) DEFAULT NULL COMMENT '保留资格年限',
  `blzgm` varchar(1) DEFAULT NULL COMMENT '保留资格码',
  `nj` int(11) DEFAULT NULL COMMENT '年级',
  `xz` int(11) DEFAULT NULL COMMENT '学制',
  `yjrxny` varchar(8) DEFAULT NULL COMMENT '预计入学年月',
  `sjrxny` varchar(50) DEFAULT NULL COMMENT '实际入学年月',
  `yjbyny` varchar(8) DEFAULT NULL COMMENT '预计毕业年月',
  `yqbynx` float DEFAULT NULL COMMENT '延期毕业年限',
  `sjbyny` varchar(8) DEFAULT NULL COMMENT '实际毕业年月',
  `sjxwny` varchar(8) DEFAULT NULL COMMENT '实际学位年月',
  `xjglm` varchar(2) DEFAULT NULL COMMENT '学籍归零码',
  `xjglmc` varchar(20) DEFAULT NULL,
  `xjglny` varchar(8) DEFAULT NULL COMMENT '学籍归零年月',
  `xjbz` varchar(250) DEFAULT NULL COMMENT '学籍备注',
  `xjydbz` varchar(250) DEFAULT NULL COMMENT '学籍异动备注',
  `qtbz` varchar(250) DEFAULT NULL COMMENT '其它备注',
  `hkszdm` varchar(6) DEFAULT NULL COMMENT '户口所在地码',
  `syszdm` varchar(6) DEFAULT NULL COMMENT '生源所在地码',
  `xxgzdw` varchar(80) DEFAULT NULL COMMENT '学习工作单位',
  `zslqlbm` varchar(2) DEFAULT NULL COMMENT '录取类别',
  `zslqlb` varchar(20) DEFAULT NULL,
  `zxjhm` varchar(2) DEFAULT NULL COMMENT '专项计划码',
  `ksfsm` varchar(3) DEFAULT NULL COMMENT '考试方式码',
  `ksfs` varchar(20) DEFAULT NULL,
  `kslym` varchar(3) DEFAULT NULL,
  `ksly` varchar(40) DEFAULT NULL,
  `pyzxjhm` varchar(3) DEFAULT NULL COMMENT '培养专项计划',
  `pyzxjh` varchar(40) DEFAULT NULL,
  `zzmmm` varchar(2) DEFAULT NULL COMMENT '政治面貌码',
  `hfm` varchar(1) DEFAULT NULL COMMENT '婚否码',
  `xyjrm` varchar(1) DEFAULT NULL COMMENT '现役军人码',
  `xslbm5` varchar(5) DEFAULT NULL COMMENT '高基学生类别',
  `tjzydm` varchar(6) DEFAULT NULL COMMENT '统计专业码',
  `tjzymc` varchar(60) DEFAULT NULL COMMENT '统计专业名称',
  `ksh16` varchar(16) DEFAULT NULL COMMENT '研究生',
  `sjcqsj` varchar(20) DEFAULT NULL,
  `qzdwm` varchar(32) DEFAULT NULL COMMENT '前置单位码',
  `qzdw` varchar(120) DEFAULT NULL COMMENT '前置单位',
  `qzny` varchar(32) DEFAULT NULL COMMENT '前置年月',
  PRIMARY KEY (`id`),
  UNIQUE KEY `xh` (`xh`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='（研究生）研究生学籍数据信息';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_apply_log 结构
CREATE TABLE IF NOT EXISTS `ow_apply_log` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `operator_id` int(10) unsigned NOT NULL COMMENT '操作人',
  `stage` tinyint(4) NOT NULL COMMENT '当前阶段，同申请表的status字段',
  `content` varchar(200) NOT NULL COMMENT '操作内容',
  `ip` varchar(20) DEFAULT NULL COMMENT 'IP',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入党申请操作日志';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_apply_open_time 结构
CREATE TABLE IF NOT EXISTS `ow_apply_open_time` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '所属分党委，针对某个分党委单独开放',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部，针对某个党支部单独开放',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1申请  2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员',
  `is_global` tinyint(1) unsigned NOT NULL COMMENT '是否全局',
  PRIMARY KEY (`id`),
  KEY `FK_ow_apply_plan_time_ow_party` (`party_id`),
  KEY `FK_ow_apply_open_time_ow_branch` (`branch_id`),
  CONSTRAINT `FK_ow_apply_open_time_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
  CONSTRAINT `FK_ow_apply_plan_time_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党员申请开放时间段';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_branch 结构
CREATE TABLE IF NOT EXISTS `ow_branch` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(50) NOT NULL COMMENT '编号',
  `name` varchar(100) NOT NULL COMMENT '名称，需用全称：院系+支部名称',
  `short_name` varchar(50) NOT NULL COMMENT '简称',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属党总支',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据，在职教工党支部、离退休党支部、本科生党支部、硕士生党支部、博士生党支部、硕博混合党支部、本硕博混合党支部',
  `is_staff` tinyint(1) unsigned DEFAULT NULL COMMENT '是否是教工党支部',
  `is_prefessional` tinyint(1) unsigned DEFAULT NULL COMMENT '是否是专业教师党支部，教工党支部时才选择',
  `is_base_team` tinyint(1) unsigned DEFAULT NULL COMMENT '是否建立在团队，专业教师党支部时才选择',
  `unit_type_id` int(10) unsigned NOT NULL COMMENT '单位属性，关联元数据，企业、事业单位',
  `is_enterprise_big` tinyint(1) unsigned NOT NULL COMMENT '是否为大中型，针对企业单位',
  `is_enterprise_nationalized` tinyint(1) unsigned NOT NULL COMMENT '是否国有独资，针对企业单位，否：国有控股',
  `is_union` tinyint(1) unsigned NOT NULL COMMENT '是否联合党支部',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `found_time` date NOT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_ow_branch_ow_party` (`party_id`),
  CONSTRAINT `FK_ow_branch_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党支部';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_branch_member 结构
CREATE TABLE IF NOT EXISTS `ow_branch_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属支部委员会',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据（书记、副书记、各类委员）',
  `is_admin` tinyint(1) unsigned NOT NULL COMMENT '是否管理员',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_user_id` (`group_id`,`user_id`),
  KEY `FK_ow_branch_member_sys_user` (`user_id`),
  KEY `FK_ow_branch_member_base_meta_type` (`type_id`),
  CONSTRAINT `FK_ow_branch_member_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_branch_member_ow_branch_member_group` FOREIGN KEY (`group_id`) REFERENCES `ow_branch_member_group` (`id`),
  CONSTRAINT `FK_ow_branch_member_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='基层党组织成员';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_branch_member_group 结构
CREATE TABLE IF NOT EXISTS `ow_branch_member_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '上一届委员会',
  `branch_id` int(10) unsigned NOT NULL COMMENT '所属党支部',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任班子',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date NOT NULL COMMENT '任命时间，本届班子任命时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_ow_branch_member_group_ow_branch_member_group` (`fid`),
  KEY `FK_ow_branch_member_group_ow_branch` (`branch_id`),
  CONSTRAINT `FK_ow_branch_member_group_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
  CONSTRAINT `FK_ow_branch_member_group_ow_branch_member_group` FOREIGN KEY (`fid`) REFERENCES `ow_branch_member_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='支部委员会';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_enter_apply 结构
CREATE TABLE IF NOT EXISTS `ow_enter_apply` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '类别，1申请入党 2 留学归国申请 3转入申请  4 流入申请',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态，0申请 1本人撤销 2 管理员撤回 3通过；当前0的状态每个用户只允许一个，且是最新的一条',
  `remark` varchar(200) DEFAULT NULL COMMENT '撤回原因',
  `back_time` datetime DEFAULT NULL COMMENT '撤回时间',
  `pass_time` datetime DEFAULT NULL COMMENT '通过时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_enter_apply_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_enter_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限开通申请';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member 结构
CREATE TABLE IF NOT EXISTS `ow_member` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部，直属党支部没有这一项',
  `political_status` tinyint(3) unsigned NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `status` tinyint(3) unsigned NOT NULL COMMENT '党员状态，1正常，2已退休 3已出党 4已转出 5暂时转出（外出挂职、休学等）',
  `source` tinyint(3) unsigned NOT NULL COMMENT '来源，进入系统方式，1建系统时统一导入 2本校发展、3外校转入  4归国人员恢复入党 5后台添加',
  `transfer_time` datetime DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间，时间从入党申请或转入同步过来',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `party_post` varchar(100) DEFAULT NULL COMMENT '党内职务',
  `party_reward` varchar(255) DEFAULT NULL COMMENT '党内奖励',
  `other_reward` varchar(255) DEFAULT NULL COMMENT '其他奖励',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党员信息表';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_abroad 结构
CREATE TABLE IF NOT EXISTS `ow_member_abroad` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '党员',
  `party_id` int(10) unsigned NOT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `abroad_time` date DEFAULT NULL COMMENT '出国时间',
  `reason` varchar(200) DEFAULT NULL COMMENT '出国缘由',
  `expect_return_time` date DEFAULT NULL COMMENT '预计归国时间',
  `actual_return_time` date DEFAULT NULL COMMENT '实际归国时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_abroad_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_abroad_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党员出国境信息';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_apply 结构
CREATE TABLE IF NOT EXISTS `ow_member_apply` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部，直属党支部没有这一项',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1学生 2教职工',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `fill_time` datetime DEFAULT NULL COMMENT '信息填报时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `stage` tinyint(3) NOT NULL COMMENT '阶段，-1不通过（管理员或本人撤销） 0申请 1通过 2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员',
  `pass_time` date DEFAULT NULL COMMENT '通过时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `train_time` date DEFAULT NULL COMMENT '参加培训时间',
  `candidate_status` tinyint(3) unsigned DEFAULT NULL COMMENT '发展对象审核状态，0未审核 1已审核',
  `plan_time` date DEFAULT NULL COMMENT '列入发展计划时间，党支部填写，分党委审批，有固定开放时间；组织部也可给分党委单独设定开放时间',
  `plan_status` tinyint(3) unsigned DEFAULT NULL COMMENT '列入计划审核状态，0未审核，1已审核',
  `draw_time` date DEFAULT NULL COMMENT '领取志愿书时间，由党支部填写、分党委审核',
  `draw_status` tinyint(3) unsigned DEFAULT NULL COMMENT '志愿书领取审核状态， 0未审核 1已审核',
  `grow_time` date DEFAULT NULL COMMENT '入党时间，由党支部填写、分党委审核，党总支、直属党支部需增加组织部审核',
  `grow_status` tinyint(3) unsigned DEFAULT NULL COMMENT '发展审核状态， 0未审核 1分党委审核 2 组织部审核',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `positive_status` tinyint(3) unsigned DEFAULT NULL COMMENT '转正审核状态， 0未审核 1分党委审核 2组织部审核',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `FK_ow_member_apply_ow_party` (`party_id`),
  KEY `FK_ow_member_apply_ow_branch` (`branch_id`),
  CONSTRAINT `FK_ow_member_apply_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
  CONSTRAINT `FK_ow_member_apply_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
  CONSTRAINT `FK_ow_member_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请入党人员';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_in 结构
CREATE TABLE IF NOT EXISTS `ow_member_in` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `political_status` tinyint(3) unsigned NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，京内、京外',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `from_unit` varchar(100) NOT NULL COMMENT '转出单位',
  `from_title` varchar(100) NOT NULL COMMENT '转出单位抬头',
  `from_address` varchar(100) NOT NULL COMMENT '转出单位地址，默认同上',
  `from_phone` varchar(20) NOT NULL COMMENT '转出单位联系电话',
  `from_fax` varchar(20) NOT NULL COMMENT '转出单位传真',
  `from_post_code` varchar(10) NOT NULL COMMENT '转出单位邮编',
  `pay_time` date NOT NULL COMMENT '党费缴纳至年月',
  `valid_days` int(10) unsigned NOT NULL COMMENT '介绍信有效期天数',
  `from_handle_time` date NOT NULL COMMENT '转出办理时间',
  `handle_time` date NOT NULL COMMENT '转入办理时间，默认为填报信息当天',
  `apply_time` date NOT NULL COMMENT '提交书面申请书时间',
  `active_time` date NOT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date NOT NULL COMMENT '确定为发展对象时间',
  `grow_time` date NOT NULL COMMENT '入党时间',
  `positive_time` date NOT NULL COMMENT '转正时间',
  `has_receipt` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有回执',
  `status` tinyint(3) NOT NULL COMMENT '状态，-2本人撤回 -1返回修改 0申请 1分党委审批 2组织部审批',
  `reason` varchar(100) DEFAULT NULL COMMENT '返回修改原因',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_out_sys_user` (`user_id`),
  CONSTRAINT `ow_member_in_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='组织关系转入';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_inflow 结构
CREATE TABLE IF NOT EXISTS `ow_member_inflow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `party_name` varchar(50) DEFAULT NULL COMMENT '分党委名称',
  `branch_name` varchar(50) DEFAULT NULL COMMENT '党支部名称',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `original_job` int(10) unsigned DEFAULT NULL COMMENT '原职业，关联元数据，公有经济企事业单位、工人、其他',
  `province` int(10) unsigned DEFAULT NULL COMMENT '流入前所在省份',
  `has_papers` tinyint(1) unsigned DEFAULT NULL COMMENT '是否持有《中国共产党流动党员活动证》',
  `flow_time` date DEFAULT NULL COMMENT '流入时间',
  `reason` varchar(255) DEFAULT NULL COMMENT '流入原因',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `or_location` varchar(200) DEFAULT NULL COMMENT '组织关系所在地',
  `inflow_status` tinyint(4) DEFAULT NULL COMMENT '流入状态，-1不通过 0申请 1党支部审核 2分党委审核',
  `outflow_unit` varchar(200) DEFAULT NULL COMMENT '转出单位',
  `outflow_location` int(10) unsigned DEFAULT NULL COMMENT '转出地',
  `outflow_time` date DEFAULT NULL COMMENT '转出时间',
  `outflow_status` tinyint(4) DEFAULT NULL COMMENT '流出状态，-1不通过 0申请 1党支部审核 2分党委审核',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_inflow_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_inflow_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='流入党员，主要是教工；流入的流动党员转出';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_out 结构
CREATE TABLE IF NOT EXISTS `ow_member_out` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，京内、京外',
  `to_title` varchar(100) NOT NULL COMMENT '转入单位抬头',
  `to_unit` varchar(100) NOT NULL COMMENT '转入单位',
  `from_unit` varchar(100) NOT NULL COMMENT '转出单位，默认为中共北京师范大学+分党委名称',
  `from_address` varchar(100) NOT NULL COMMENT '转出单位地址，默认同上',
  `from_phone` varchar(20) NOT NULL COMMENT '转出单位联系电话',
  `from_fax` varchar(20) NOT NULL COMMENT '转出单位传真',
  `from_post_code` varchar(10) NOT NULL COMMENT '转出单位邮编，默认为100875',
  `pay_time` date NOT NULL COMMENT '党费缴纳至年月',
  `valid_days` int(10) unsigned NOT NULL COMMENT '介绍信有效期天数',
  `handle_time` date NOT NULL COMMENT '办理时间',
  `has_receipt` tinyint(1) unsigned DEFAULT NULL COMMENT '是否有回执',
  `status` tinyint(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1分党委审批 2组织部审批',
  `reason` varchar(100) DEFAULT NULL COMMENT '返回修改原因',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_out_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_out_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织关系转出';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_outflow 结构
CREATE TABLE IF NOT EXISTS `ow_member_outflow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教工 2学生',
  `party_name` varchar(50) DEFAULT NULL COMMENT '分党委名称',
  `branch_name` varchar(50) DEFAULT NULL COMMENT '党支部名称',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属党支部',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `original_job` int(10) unsigned DEFAULT NULL COMMENT '原职业，关联元数据，公有经济企事业单位、工人、其他',
  `direction` int(10) unsigned DEFAULT NULL COMMENT '外出流向，关联元数据，机关、事业单位、人才服务中心、公有经济控制港澳台商投资企业、公有经济控制外商投资企业、民办非企业单位、私营企业、非公有经济控制港澳台商投资企业、非公有经济控制外商投资企业、个体工商户、城市社区（居委会）、乡镇神曲（居委会）、建制村、其他、不掌握流向',
  `flow_time` date DEFAULT NULL COMMENT '流出时间',
  `province` int(10) unsigned DEFAULT NULL COMMENT '流出省份',
  `reason` varchar(255) DEFAULT NULL COMMENT '流出原因',
  `has_papers` tinyint(1) unsigned DEFAULT NULL COMMENT '是否持有《中国共产党流动党员活动证》',
  `or_status` tinyint(3) unsigned DEFAULT NULL COMMENT '组织关系状态，已转出、未转出',
  `status` tinyint(4) DEFAULT NULL COMMENT '流出状态，-1不通过 0申请 1党支部审核 2分党委审核',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_outflow_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_outflow_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流出党员，包括教工、学生';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_quit 结构
CREATE TABLE IF NOT EXISTS `ow_member_quit` (
  `user_id` int(10) unsigned NOT NULL COMMENT '账号ID',
  `party_id` int(10) unsigned NOT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `party_name` varchar(50) DEFAULT NULL COMMENT '分党委名称',
  `branch_name` varchar(50) DEFAULT NULL COMMENT '党支部名称',
  `type` tinyint(3) unsigned DEFAULT NULL COMMENT '出党原因，1自动退党 2开除党籍 3党员去世',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `quit_time` date NOT NULL COMMENT '出党时间',
  `status` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '状态，0未审核 1已审核，暂时未用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_ow_member_quit_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='党员出党';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_return 结构
CREATE TABLE IF NOT EXISTS `ow_member_return` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部，直属党支部没有这一项',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `active_time` date DEFAULT NULL COMMENT '确定为入党积极分子时间',
  `candidate_time` date DEFAULT NULL COMMENT '确定为发展对象时间',
  `grow_time` date DEFAULT NULL COMMENT '入党时间',
  `positive_time` date DEFAULT NULL COMMENT '转正时间',
  `political_status` tinyint(3) unsigned NOT NULL COMMENT '状态，1预备 2正式',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态，-1不通过（管理员或本人撤销） 0申请 1支部审核 2分党委审核',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_return_ow_party` (`party_id`),
  KEY `FK_ow_member_return_ow_branch` (`branch_id`),
  KEY `FK_ow_member_return_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_return_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
  CONSTRAINT `FK_ow_member_return_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
  CONSTRAINT `FK_ow_member_return_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='留学归国人员申请恢复组织生活';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_member_stay 结构
CREATE TABLE IF NOT EXISTS `ow_member_stay` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `country` varchar(50) NOT NULL COMMENT '留学国别',
  `abroad_time` date NOT NULL COMMENT '出国时间',
  `return_time` date NOT NULL COMMENT '预计回国时间',
  `pay_time` date NOT NULL COMMENT '党费缴纳至年月',
  `mobile` char(20) NOT NULL COMMENT '手机号码',
  `email` varchar(50) NOT NULL COMMENT '电子邮箱',
  `contact_name` varchar(20) NOT NULL COMMENT '国内联系人姓名',
  `contact_mobile` varchar(20) NOT NULL COMMENT '国内联系人手机号码',
  `status` tinyint(3) NOT NULL COMMENT '状态，-2本人撤回 -1返回修改 0申请 1分党委审批 2组织部审批',
  `reason` varchar(100) DEFAULT NULL COMMENT '修改原因',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_stay_sys_user` (`user_id`),
  CONSTRAINT `FK_ow_member_stay_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公派留学生党员申请组织关系暂留';

-- 数据导出被取消选择。


-- 导出  视图 owip.ow_member_student 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `ow_member_student` (
	`create_time` DATETIME NULL COMMENT '创建时间',
	`apply_time` DATE NULL COMMENT '提交书面申请书时间，时间从入党申请或转入同步过来',
	`source` TINYINT(3) UNSIGNED NOT NULL COMMENT '来源，进入系统方式，1建系统时统一导入 2本校发展、3外校转入  4归国人员恢复入党 5后台添加',
	`positive_time` DATE NULL COMMENT '转正时间',
	`active_time` DATE NULL COMMENT '确定为入党积极分子时间',
	`political_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
	`transfer_time` DATETIME NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`branch_id` INT(10) UNSIGNED NULL COMMENT '所属党支部，直属党支部没有这一项',
	`candidate_time` DATE NULL COMMENT '确定为发展对象时间',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委',
	`grow_time` DATE NULL COMMENT '入党时间',
	`status` TINYINT(3) UNSIGNED NOT NULL COMMENT '党员状态，1正常，2已退休 3已出党 4已转出 5暂时转出（外出挂职、休学等）',
	`party_post` VARCHAR(100) NULL COMMENT '党内职务' COLLATE 'utf8_general_ci',
	`party_reward` VARCHAR(255) NULL COMMENT '党内奖励' COLLATE 'utf8_general_ci',
	`other_reward` VARCHAR(255) NULL COMMENT '其他奖励' COLLATE 'utf8_general_ci',
	`delay_year` FLOAT NULL COMMENT '延期毕业年限',
	`period` VARCHAR(50) NULL COMMENT '学制' COLLATE 'utf8_general_ci',
	`code` VARCHAR(20) NOT NULL COMMENT '学生证号' COLLATE 'utf8_general_ci',
	`edu_category` VARCHAR(50) NULL COMMENT '教育类别' COLLATE 'utf8_general_ci',
	`gender` TINYINT(3) UNSIGNED NULL COMMENT '性别，1男 2女 0未知',
	`birth` DATE NULL COMMENT '出生日期',
	`nation` VARCHAR(20) NULL COMMENT '民族' COLLATE 'utf8_general_ci',
	`actual_graduate_time` DATE NULL COMMENT '实际毕业年月',
	`expect_graduate_time` DATE NULL COMMENT '预计毕业年月',
	`actual_enrol_time` DATE NULL COMMENT '实际入学年月',
	`sync_source` TINYINT(3) UNSIGNED NULL COMMENT '同步来源，同sys_user来源',
	`type` VARCHAR(50) NULL COMMENT '学生类别' COLLATE 'utf8_general_ci',
	`is_full_time` TINYINT(1) UNSIGNED NULL COMMENT '是否全日制',
	`realname` VARCHAR(50) NULL COMMENT '姓名' COLLATE 'utf8_general_ci',
	`enrol_year` VARCHAR(50) NULL COMMENT '招生年度' COLLATE 'utf8_general_ci',
	`native_place` VARCHAR(20) NULL COMMENT '籍贯' COLLATE 'utf8_general_ci',
	`edu_way` VARCHAR(50) NULL COMMENT '培养方式' COLLATE 'utf8_general_ci',
	`idcard` VARCHAR(20) NULL COMMENT '身份证号' COLLATE 'utf8_general_ci',
	`edu_level` VARCHAR(50) NULL COMMENT '培养层次' COLLATE 'utf8_general_ci',
	`grade` VARCHAR(10) NULL COMMENT '年级' COLLATE 'utf8_general_ci',
	`edu_type` VARCHAR(50) NULL COMMENT '培养类型' COLLATE 'utf8_general_ci'
) ENGINE=MyISAM;


-- 导出  视图 owip.ow_member_teacher 结构
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `ow_member_teacher` (
	`create_time` DATETIME NULL COMMENT '创建时间',
	`apply_time` DATE NULL COMMENT '提交书面申请书时间，时间从入党申请或转入同步过来',
	`source` TINYINT(3) UNSIGNED NOT NULL COMMENT '来源，进入系统方式，1建系统时统一导入 2本校发展、3外校转入  4归国人员恢复入党 5后台添加',
	`positive_time` DATE NULL COMMENT '转正时间',
	`active_time` DATE NULL COMMENT '确定为入党积极分子时间',
	`political_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
	`transfer_time` DATETIME NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`branch_id` INT(10) UNSIGNED NULL COMMENT '所属党支部，直属党支部没有这一项',
	`candidate_time` DATE NULL COMMENT '确定为发展对象时间',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委',
	`grow_time` DATE NULL COMMENT '入党时间',
	`status` TINYINT(3) UNSIGNED NOT NULL COMMENT '党员状态，1正常，2已退休 3已出党 4已转出 5暂时转出（外出挂职、休学等）',
	`party_post` VARCHAR(100) NULL COMMENT '党内职务' COLLATE 'utf8_general_ci',
	`party_reward` VARCHAR(255) NULL COMMENT '党内奖励' COLLATE 'utf8_general_ci',
	`other_reward` VARCHAR(255) NULL COMMENT '其他奖励' COLLATE 'utf8_general_ci',
	`code` VARCHAR(20) NOT NULL COMMENT '工作证号' COLLATE 'utf8_general_ci',
	`education` VARCHAR(50) NULL COMMENT '最高学历' COLLATE 'utf8_general_ci',
	`gender` TINYINT(3) UNSIGNED NULL COMMENT '性别， 1男 2女 0未知',
	`nation` VARCHAR(20) NULL COMMENT '民族' COLLATE 'utf8_general_ci',
	`school_type` VARCHAR(50) NULL COMMENT '毕业学校类型' COLLATE 'utf8_general_ci',
	`title_level` VARCHAR(50) NULL COMMENT '职称级别' COLLATE 'utf8_general_ci',
	`staff_status` VARCHAR(50) NULL COMMENT '人员状态' COLLATE 'utf8_general_ci',
	`retire_time` DATE NULL COMMENT '退休时间',
	`post_class` VARCHAR(50) NULL COMMENT '岗位类别' COLLATE 'utf8_general_ci',
	`pro_post` VARCHAR(50) NULL COMMENT '专业技术职务' COLLATE 'utf8_general_ci',
	`major` VARCHAR(50) NULL COMMENT '所学专业' COLLATE 'utf8_general_ci',
	`post` VARCHAR(50) NULL COMMENT '行政职务' COLLATE 'utf8_general_ci',
	`school` VARCHAR(50) NULL COMMENT '毕业学校' COLLATE 'utf8_general_ci',
	`is_retire` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否退休',
	`is_honor_retire` TINYINT(1) UNSIGNED NULL COMMENT '是否离休',
	`post_type` VARCHAR(50) NULL COMMENT '岗位子类别' COLLATE 'utf8_general_ci',
	`degree_time` VARCHAR(50) NULL COMMENT '学位授予日期' COLLATE 'utf8_general_ci',
	`manage_level` VARCHAR(50) NULL COMMENT '管理岗位等级' COLLATE 'utf8_general_ci',
	`email` VARCHAR(50) NULL COMMENT '联系邮箱' COLLATE 'utf8_general_ci',
	`post_level` VARCHAR(50) NULL COMMENT '任职级别' COLLATE 'utf8_general_ci',
	`office_level` VARCHAR(50) NULL COMMENT '工勤岗位等级' COLLATE 'utf8_general_ci',
	`talent_title` VARCHAR(50) NULL COMMENT '人才/荣誉称号' COLLATE 'utf8_general_ci',
	`address` VARCHAR(50) NULL COMMENT '居住地址' COLLATE 'utf8_general_ci',
	`degree` VARCHAR(50) NULL COMMENT '最高学位' COLLATE 'utf8_general_ci',
	`mobile` VARCHAR(50) NULL COMMENT '联系手机' COLLATE 'utf8_general_ci',
	`birth` DATE NULL COMMENT '出生日期',
	`authorized_type` VARCHAR(50) NULL COMMENT '编制类别' COLLATE 'utf8_general_ci',
	`realname` VARCHAR(50) NULL COMMENT '姓名' COLLATE 'utf8_general_ci',
	`arrive_time` VARCHAR(50) NULL COMMENT '到校日期' COLLATE 'utf8_general_ci',
	`native_place` VARCHAR(50) NULL COMMENT '籍贯' COLLATE 'utf8_general_ci',
	`marital_status` VARCHAR(50) NULL COMMENT '婚姻状况' COLLATE 'utf8_general_ci',
	`staff_type` VARCHAR(50) NULL COMMENT '人员分类' COLLATE 'utf8_general_ci',
	`phone` VARCHAR(50) NULL COMMENT '家庭电话' COLLATE 'utf8_general_ci',
	`idcard` VARCHAR(20) NULL COMMENT '身份证号' COLLATE 'utf8_general_ci',
	`on_job` VARCHAR(50) NULL COMMENT '在岗情况' COLLATE 'utf8_general_ci',
	`pro_post_level` VARCHAR(50) NULL COMMENT '专技岗位等级' COLLATE 'utf8_general_ci'
) ENGINE=MyISAM;


-- 导出  表 owip.ow_member_transfer 结构
CREATE TABLE IF NOT EXISTS `ow_member_transfer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL,
  `branch_id` int(10) unsigned DEFAULT NULL,
  `to_party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `to_branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部',
  `from_phone` varchar(20) NOT NULL COMMENT '转出单位联系电话',
  `from_fax` varchar(20) NOT NULL COMMENT '转出单位传真',
  `pay_time` date NOT NULL COMMENT '党费缴纳至年月',
  `valid_days` int(10) unsigned NOT NULL COMMENT '介绍信有效期天数',
  `from_handle_time` date NOT NULL COMMENT '转出办理时间',
  `status` tinyint(3) NOT NULL COMMENT '状态，-2本人撤回 -1返回修改 0申请 1转出分党委审批 2转入分党委审批',
  `reason` varchar(100) DEFAULT NULL COMMENT '返回修改原因',
  `apply_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`),
  KEY `FK_ow_member_out_sys_user` (`user_id`),
  CONSTRAINT `ow_member_transfer_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='校内组织关系互转';

-- 数据导出被取消选择。


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
  `is_separate` tinyint(1) unsigned NOT NULL COMMENT '所在单位是否独立法人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `found_time` date NOT NULL COMMENT '成立时间',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `FK_ow_party_base_meta_type` (`class_id`),
  KEY `FK_ow_party_base_meta_type_2` (`type_id`),
  KEY `FK_ow_party_base_meta_type_3` (`unit_type_id`),
  KEY `FK_ow_party_base_unit` (`unit_id`),
  CONSTRAINT `FK_ow_party_base_meta_type` FOREIGN KEY (`class_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_meta_type_2` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_meta_type_3` FOREIGN KEY (`unit_type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_party_member 结构
CREATE TABLE IF NOT EXISTS `ow_party_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `group_id` int(10) unsigned NOT NULL COMMENT '所属班子',
  `user_id` int(10) unsigned NOT NULL COMMENT '账号，除了书记、副书记其他委员一般没有行政级别，不属于干部',
  `type_id` int(10) unsigned NOT NULL COMMENT '类别，关联元数据（书记、副书记、各类委员）',
  `is_admin` tinyint(1) unsigned NOT NULL COMMENT '是否管理员',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id_user_id` (`group_id`,`user_id`),
  KEY `FK_ow_party_member_sys_user` (`user_id`),
  KEY `FK_ow_party_member_base_meta_type` (`type_id`),
  CONSTRAINT `FK_ow_party_member_base_meta_type` FOREIGN KEY (`type_id`) REFERENCES `base_meta_type` (`id`),
  CONSTRAINT `FK_ow_party_member_ow_party_member_group` FOREIGN KEY (`group_id`) REFERENCES `ow_party_member_group` (`id`),
  CONSTRAINT `FK_ow_party_member_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织成员';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_party_member_group 结构
CREATE TABLE IF NOT EXISTS `ow_party_member_group` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `fid` int(10) unsigned DEFAULT NULL COMMENT '上一届班子',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属党总支',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `is_present` tinyint(1) unsigned NOT NULL COMMENT '是否现任班子',
  `tran_time` date DEFAULT NULL COMMENT '应换届时间',
  `actual_tran_time` date DEFAULT NULL COMMENT '实际换届时间',
  `appoint_time` date NOT NULL COMMENT '任命时间，本届班子任命时间',
  `dispatch_unit_id` int(10) unsigned DEFAULT NULL COMMENT '发文，关联单位发文',
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  KEY `FK_ow_party_member_group_ow_party` (`party_id`),
  KEY `FK_ow_party_member_group_base_dispatch_unit` (`dispatch_unit_id`),
  KEY `FK_ow_party_member_group_ow_party_member_group` (`fid`),
  CONSTRAINT `FK_ow_party_member_group_base_dispatch_unit` FOREIGN KEY (`dispatch_unit_id`) REFERENCES `base_dispatch_unit` (`id`),
  CONSTRAINT `FK_ow_party_member_group_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
  CONSTRAINT `FK_ow_party_member_group_ow_party_member_group` FOREIGN KEY (`fid`) REFERENCES `ow_party_member_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基层党组织领导班子';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_retire_apply 结构
CREATE TABLE IF NOT EXISTS `ow_retire_apply` (
  `user_id` int(10) unsigned NOT NULL COMMENT '退休人员',
  `party_id` int(10) unsigned DEFAULT NULL COMMENT '退休后所在分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '退休后所在党支部',
  `status` tinyint(3) unsigned NOT NULL COMMENT '0 申请 1分党委审核',
  `apply_id` int(10) unsigned NOT NULL COMMENT '申请人',
  `create_time` datetime DEFAULT NULL COMMENT '填报时间',
  `verify_id` int(10) unsigned DEFAULT NULL COMMENT '审核人',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`user_id`),
  KEY `FK_ow_retire_apply_ow_party` (`party_id`),
  KEY `FK_ow_retire_apply_ow_branch` (`branch_id`),
  KEY `FK_ow_retire_apply_sys_user_2` (`apply_id`),
  KEY `FK_ow_retire_apply_sys_user_3` (`verify_id`),
  CONSTRAINT `FK_ow_retire_apply_ow_branch` FOREIGN KEY (`branch_id`) REFERENCES `ow_branch` (`id`),
  CONSTRAINT `FK_ow_retire_apply_ow_party` FOREIGN KEY (`party_id`) REFERENCES `ow_party` (`id`),
  CONSTRAINT `FK_ow_retire_apply_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK_ow_retire_apply_sys_user_2` FOREIGN KEY (`apply_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FK_ow_retire_apply_sys_user_3` FOREIGN KEY (`verify_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在职党员退休记录';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_student 结构
CREATE TABLE IF NOT EXISTS `ow_student` (
  `user_id` int(10) unsigned NOT NULL COMMENT '账号ID',
  `code` varchar(20) NOT NULL COMMENT '学生证号',
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(3) unsigned DEFAULT NULL COMMENT '性别，1男 2女 0未知',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `native_place` varchar(20) DEFAULT NULL COMMENT '籍贯',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `type` varchar(50) DEFAULT NULL COMMENT '学生类别',
  `edu_level` varchar(50) DEFAULT NULL COMMENT '培养层次',
  `edu_type` varchar(50) DEFAULT NULL COMMENT '培养类型',
  `edu_category` varchar(50) DEFAULT NULL COMMENT '教育类别',
  `edu_way` varchar(50) DEFAULT NULL COMMENT '培养方式',
  `is_full_time` tinyint(1) unsigned DEFAULT NULL COMMENT '是否全日制',
  `enrol_year` varchar(50) DEFAULT NULL COMMENT '招生年度',
  `period` varchar(50) DEFAULT NULL COMMENT '学制',
  `grade` varchar(10) DEFAULT NULL COMMENT '年级',
  `actual_enrol_time` date DEFAULT NULL COMMENT '实际入学年月',
  `expect_graduate_time` date DEFAULT NULL COMMENT '预计毕业年月',
  `delay_year` float unsigned DEFAULT NULL COMMENT '延期毕业年限',
  `actual_graduate_time` date DEFAULT NULL COMMENT '实际毕业年月',
  `sync_source` tinyint(3) unsigned DEFAULT NULL COMMENT '同步来源，同sys_user来源',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `FK_ow_student_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生党员信息表';

-- 数据导出被取消选择。


-- 导出  表 owip.ow_teacher 结构
CREATE TABLE IF NOT EXISTS `ow_teacher` (
  `user_id` int(10) unsigned NOT NULL COMMENT '账号ID',
  `code` varchar(20) NOT NULL COMMENT '工作证号',
  `realname` varchar(50) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(3) unsigned DEFAULT NULL COMMENT '性别， 1男 2女 0未知',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `native_place` varchar(50) DEFAULT NULL COMMENT '籍贯',
  `nation` varchar(20) DEFAULT NULL COMMENT '民族',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `education` varchar(50) DEFAULT NULL COMMENT '最高学历',
  `degree` varchar(50) DEFAULT NULL COMMENT '最高学位',
  `degree_time` varchar(50) DEFAULT NULL COMMENT '学位授予日期',
  `major` varchar(50) DEFAULT NULL COMMENT '所学专业',
  `school` varchar(50) DEFAULT NULL COMMENT '毕业学校',
  `school_type` varchar(50) DEFAULT NULL COMMENT '毕业学校类型',
  `arrive_time` varchar(50) DEFAULT NULL COMMENT '到校日期',
  `authorized_type` varchar(50) DEFAULT NULL COMMENT '编制类别',
  `staff_type` varchar(50) DEFAULT NULL COMMENT '人员分类',
  `staff_status` varchar(50) DEFAULT NULL COMMENT '人员状态',
  `post_class` varchar(50) DEFAULT NULL COMMENT '岗位类别',
  `post_type` varchar(50) DEFAULT NULL COMMENT '岗位子类别',
  `on_job` varchar(50) DEFAULT NULL COMMENT '在岗情况',
  `pro_post` varchar(50) DEFAULT NULL COMMENT '专业技术职务',
  `pro_post_level` varchar(50) DEFAULT NULL COMMENT '专技岗位等级',
  `title_level` varchar(50) DEFAULT NULL COMMENT '职称级别',
  `manage_level` varchar(50) DEFAULT NULL COMMENT '管理岗位等级',
  `office_level` varchar(50) DEFAULT NULL COMMENT '工勤岗位等级',
  `post` varchar(50) DEFAULT NULL COMMENT '行政职务',
  `post_level` varchar(50) DEFAULT NULL COMMENT '任职级别',
  `talent_title` varchar(50) DEFAULT NULL COMMENT '人才/荣誉称号',
  `address` varchar(50) DEFAULT NULL COMMENT '居住地址',
  `marital_status` varchar(50) DEFAULT NULL COMMENT '婚姻状况',
  `email` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '联系手机',
  `phone` varchar(50) DEFAULT NULL COMMENT '家庭电话',
  `is_retire` tinyint(1) unsigned NOT NULL COMMENT '是否退休',
  `retire_time` date DEFAULT NULL COMMENT '退休时间',
  `is_honor_retire` tinyint(1) unsigned DEFAULT NULL COMMENT '是否离休',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `FK_ow_teacher_sys_user` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教职工党员信息';

-- 数据导出被取消选择。


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统日志';

-- 数据导出被取消选择。


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

-- 数据导出被取消选择。


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
  `sort_order` int(10) unsigned DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission` (`permission`),
  KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统资源';

-- 数据导出被取消选择。


-- 导出  表 owip.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role` varchar(100) NOT NULL COMMENT '角色',
  `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `resource_ids` text COMMENT '拥有的资源',
  `available` tinyint(3) DEFAULT '0' COMMENT '状态，0禁用 1启用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='系统角色';

-- 数据导出被取消选择。


-- 导出  表 owip.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名，工作证号、学号、个性账号',
  `passwd` varchar(32) NOT NULL COMMENT '密码',
  `salt` varchar(50) NOT NULL COMMENT '密码盐',
  `role_ids` text COMMENT '所属角色，格式：,1,',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1教职工 2本科生 3研究生',
  `code` varchar(20) DEFAULT NULL COMMENT '学工号，老师为工作证号，学生为学号',
  `realname` varchar(100) DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(200) DEFAULT NULL COMMENT '系统头像',
  `gender` tinyint(3) unsigned DEFAULT NULL COMMENT '性别，1男 2女 0未知',
  `birth` date DEFAULT NULL COMMENT '出生年月',
  `idcard` varchar(100) DEFAULT NULL COMMENT '身份证',
  `sign` varchar(100) DEFAULT NULL COMMENT '手写签名，图片上传',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `source` tinyint(3) unsigned NOT NULL COMMENT '来源，0 后台创建、1人事库、2本科生库 3 研究生库',
  `locked` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否锁定',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='平台用户';

-- 数据导出被取消选择。


-- 导出  表 owip.sys_user_sync 结构
CREATE TABLE IF NOT EXISTS `sys_user_sync` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1人事库 2研究库 3本科生库',
  `user_id` int(10) unsigned DEFAULT NULL COMMENT '触发账号',
  `auto_start` tinyint(1) unsigned NOT NULL COMMENT '是否自动触发',
  `auto_stop` tinyint(1) unsigned NOT NULL COMMENT '是否正常结束',
  `is_stop` tinyint(1) unsigned NOT NULL COMMENT '是否结束',
  `total_page` int(10) unsigned DEFAULT NULL COMMENT '总页码',
  `total_count` int(10) unsigned DEFAULT NULL COMMENT '总记录数',
  `current_page` int(10) unsigned DEFAULT NULL COMMENT '当前页码',
  `current_count` int(10) unsigned DEFAULT NULL COMMENT '当前记录数',
  `insert_count` int(10) unsigned DEFAULT NULL COMMENT '插入数量',
  `update_count` int(10) unsigned DEFAULT NULL COMMENT '更新数量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='账号同步日志';

-- 数据导出被取消选择。


-- 导出  表 owip.unit_election 结构
CREATE TABLE IF NOT EXISTS `unit_election` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type_id` int(10) unsigned NOT NULL COMMENT '统计分组，关联元数据',
  `unit_id` int(10) unsigned NOT NULL COMMENT '所属单位',
  `dispatch_id` int(10) unsigned NOT NULL COMMENT '相关发文',
  `sort_order` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='单位换届分组记录';

-- 数据导出被取消选择。


-- 导出  视图 owip.ow_member_student 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `ow_member_student`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_student` AS select `m`.`create_time` AS `create_time`,`m`.`apply_time` AS `apply_time`,`m`.`source` AS `source`,`m`.`positive_time` AS `positive_time`,`m`.`active_time` AS `active_time`,`m`.`political_status` AS `political_status`,`m`.`transfer_time` AS `transfer_time`,`m`.`user_id` AS `user_id`,`m`.`branch_id` AS `branch_id`,`m`.`candidate_time` AS `candidate_time`,`m`.`party_id` AS `party_id`,`m`.`grow_time` AS `grow_time`,`m`.`status` AS `status`,`m`.`party_post` AS `party_post`,`m`.`party_reward` AS `party_reward`,`m`.`other_reward` AS `other_reward`,`s`.`delay_year` AS `delay_year`,`s`.`period` AS `period`,`s`.`code` AS `code`,`s`.`edu_category` AS `edu_category`,`s`.`gender` AS `gender`,`s`.`birth` AS `birth`,`s`.`nation` AS `nation`,`s`.`actual_graduate_time` AS `actual_graduate_time`,`s`.`expect_graduate_time` AS `expect_graduate_time`,`s`.`actual_enrol_time` AS `actual_enrol_time`,`s`.`sync_source` AS `sync_source`,`s`.`type` AS `type`,`s`.`is_full_time` AS `is_full_time`,`s`.`realname` AS `realname`,`s`.`enrol_year` AS `enrol_year`,`s`.`native_place` AS `native_place`,`s`.`edu_way` AS `edu_way`,`s`.`idcard` AS `idcard`,`s`.`edu_level` AS `edu_level`,`s`.`grade` AS `grade`,`s`.`edu_type` AS `edu_type` from (`ow_member` `m` join `ow_student` `s`) where (`m`.`user_id` = `s`.`user_id`) ;


-- 导出  视图 owip.ow_member_teacher 结构
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `ow_member_teacher`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` VIEW `ow_member_teacher` AS select `m`.`create_time` AS `create_time`,`m`.`apply_time` AS `apply_time`,`m`.`source` AS `source`,`m`.`positive_time` AS `positive_time`,`m`.`active_time` AS `active_time`,`m`.`political_status` AS `political_status`,`m`.`transfer_time` AS `transfer_time`,`m`.`user_id` AS `user_id`,`m`.`branch_id` AS `branch_id`,`m`.`candidate_time` AS `candidate_time`,`m`.`party_id` AS `party_id`,`m`.`grow_time` AS `grow_time`,`m`.`status` AS `status`,`m`.`party_post` AS `party_post`,`m`.`party_reward` AS `party_reward`,`m`.`other_reward` AS `other_reward`,`t`.`code` AS `code`,`t`.`education` AS `education`,`t`.`gender` AS `gender`,`t`.`nation` AS `nation`,`t`.`school_type` AS `school_type`,`t`.`title_level` AS `title_level`,`t`.`staff_status` AS `staff_status`,`t`.`retire_time` AS `retire_time`,`t`.`post_class` AS `post_class`,`t`.`pro_post` AS `pro_post`,`t`.`major` AS `major`,`t`.`post` AS `post`,`t`.`school` AS `school`,`t`.`is_retire` AS `is_retire`,`t`.`is_honor_retire` AS `is_honor_retire`,`t`.`post_type` AS `post_type`,`t`.`degree_time` AS `degree_time`,`t`.`manage_level` AS `manage_level`,`t`.`email` AS `email`,`t`.`post_level` AS `post_level`,`t`.`office_level` AS `office_level`,`t`.`talent_title` AS `talent_title`,`t`.`address` AS `address`,`t`.`degree` AS `degree`,`t`.`mobile` AS `mobile`,`t`.`birth` AS `birth`,`t`.`authorized_type` AS `authorized_type`,`t`.`realname` AS `realname`,`t`.`arrive_time` AS `arrive_time`,`t`.`native_place` AS `native_place`,`t`.`marital_status` AS `marital_status`,`t`.`staff_type` AS `staff_type`,`t`.`phone` AS `phone`,`t`.`idcard` AS `idcard`,`t`.`on_job` AS `on_job`,`t`.`pro_post_level` AS `pro_post_level` from (`ow_member` `m` join `ow_teacher` `t`) where (`m`.`user_id` = `t`.`user_id`) ;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
