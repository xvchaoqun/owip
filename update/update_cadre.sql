-- 2021.1.7 sxx
ALTER TABLE `cadre_inspect`
	ADD COLUMN `valid_time` DATETIME NULL DEFAULT NULL COMMENT '有效期' AFTER `record_user_id`;
-- 更新 cadre_inspect_view

CREATE TABLE `cadre_eva_result` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '所属干部',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年份',
	`group_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '测评类别(分区名称)',
	`sort_order` INT(11) NULL DEFAULT NULL COMMENT '排名',
	`num` INT(11) NULL DEFAULT NULL COMMENT '总人数',
	`remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注',
	PRIMARY KEY (`id`)
)
COMMENT='干部年度测评结果'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=4
;

-- 2020.9.3 ly
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`, `is_deleted`) VALUES ('更新领导干部的培训信息', '更新培训模块的培训信息至领导干部的培训信息', 'job.cadre.UpdateCadreTrain', '0 0 1 * * ?', 1, 0, 32, '2020-09-01 16:13:32', 0);
INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`, `is_deleted`) VALUES ('更新离退休党员的人员状态', '离退休分党委和党支部中，把党员的人员状态为“在职”的更新为“退休”', 'job.member.UpdateRetireStatus', '0 0 1 * * ?', 1, 0, 31, '2020-08-31 16:36:23', 0);

2020-08-19
update cadre_post set is_cpc=(select is_cpc from unit_post where cadre_post.unit_post_id=unit_post.id)

-- 20200513 ly
ALTER TABLE `cadre_work`
	ADD COLUMN `note` VARCHAR(100) NULL DEFAULT NULL COMMENT '补充说明' AFTER `is_cadre`;

2019-12-20
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 db_owip.cadre_position_report 结构
DROP TABLE IF EXISTS `cadre_position_report`;
CREATE TABLE IF NOT EXISTS `cadre_position_report` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cadre_id` int(10) unsigned DEFAULT NULL COMMENT '干部id',
  `year` int(10) unsigned DEFAULT NULL COMMENT '年度',
  `title` varchar(50) DEFAULT NULL COMMENT '所在单位及职务',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `content` text COMMENT '述职报告内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='干部述职报告';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

2019-12-18
-- 干部述职报告 苏小霞
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2708, 0, '述职报告', '', 'url', '', '/cadrePositionReport?admin=1', 88, '0/1/88/', 0, 'cadrePositionReport:adminMenu', NULL, NULL, NULL, 1, 1900);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2709, 0, '述职报告', '', 'url', '', '/cadrePositionReport?admin=0', 353, '0/1/353/', 1, 'cadrePositionReport:menu', NULL, NULL, NULL, 1, 450);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2710, 0, '述职报告:查看', '', 'function', '', NULL, 2708, '0/1/88/2708/', 1, 'cadrePositionReport:list', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2711, 0, '述职报告:编辑', '', 'function', '', NULL, 2708, '0/1/88/2708/', 1, 'cadrePositionReport:edit', NULL, NULL, NULL, 1, NULL);

CREATE TABLE `cadre_position_report` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '干部id',
	`year` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '年度',
	`create_date` DATE NULL DEFAULT NULL COMMENT '创建时间',
	`content` TEXT NULL COMMENT '述职报告内容',
	PRIMARY KEY (`id`)
)
COMMENT='干部述职报告'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=8
;