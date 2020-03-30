-- 2020.03.27 桑文帅
-- 建表语句

CREATE TABLE IF NOT EXISTS `pmd_party_donate` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) unsigned NOT NULL COMMENT '用户ID',
  `party_id` int(10) unsigned NOT NULL COMMENT '所属分党委',
  `branch_id` int(10) unsigned DEFAULT NULL COMMENT '所在党支部',
  `donate_date` date DEFAULT NULL COMMENT '缴费日期',
  `money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `remark` text COMMENT '说明',
  `is_donate` tinyint(1) unsigned DEFAULT NULL COMMENT '是否已缴费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='党员捐赠党费';
