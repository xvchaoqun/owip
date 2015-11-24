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

-- 导出  表 owip.ow_member_apply 结构
CREATE TABLE IF NOT EXISTS `ow_member_apply` (
  `user_id` int(10) unsigned NOT NULL COMMENT '用户',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所属党支部，直属党支部没有这一项',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类型，1学生 2教职工',
  `apply_time` date DEFAULT NULL COMMENT '提交书面申请书时间',
  `fill_time` datetime DEFAULT NULL COMMENT '信息填报时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `stage` tinyint(4) NOT NULL COMMENT '阶段，-1不通过 0申请 1通过 2入党积极分子 3发展对象（积极分子满一年）4列入发展计划 5领取志愿书 6预备党员 7正式党员',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=FIXED COMMENT='申请入党人员';

-- 正在导出表  owip.ow_member_apply 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `ow_member_apply` DISABLE KEYS */;
INSERT INTO `ow_member_apply` (`user_id`, `party_id`, `branch_id`, `type`, `apply_time`, `fill_time`, `remark`, `stage`, `active_time`, `candidate_time`, `train_time`, `candidate_status`, `plan_time`, `plan_status`, `draw_time`, `draw_status`, `grow_time`, `grow_status`, `positive_time`, `positive_status`, `create_time`, `update_time`) VALUES
	(642, 1, 1, 1, '2015-11-11', '2015-11-20 00:16:21', '', 6, '2015-11-04', '2015-11-06', '2015-11-14', 1, '2015-11-13', 1, '2015-11-13', 1, '2015-10-30', 2, NULL, NULL, '2015-11-20 21:57:14', '2015-11-22 10:20:47'),
	(645, 2, NULL, 2, '2015-11-04', '2015-11-22 10:36:16', '一颗红心向太阳！', 6, '2015-11-12', '2015-11-04', '2015-11-20', 1, '2015-11-20', 1, '2015-11-21', 1, '2015-11-26', 2, NULL, NULL, '2015-11-22 10:36:16', '2015-11-22 11:22:46');
/*!40000 ALTER TABLE `ow_member_apply` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
