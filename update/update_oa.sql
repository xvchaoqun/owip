2021.6.9 xcq
ALTER TABLE `oa_task_user`
	ADD COLUMN `browse_time` DATETIME NULL DEFAULT NULL COMMENT '最后浏览时间' AFTER `assign_user_mobile`;
-- 更新 oa_task_user_view

-- 2020.9.23 ly

-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.16 - MySQL Community Server (GPL)
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table db_owip.oa_grid
DROP TABLE IF EXISTS `oa_grid`;
CREATE TABLE IF NOT EXISTS `oa_grid` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(100) DEFAULT NULL COMMENT '表格名称',
  `year` int(10) unsigned NOT NULL COMMENT '所属年度',
  `type` tinyint(3) unsigned NOT NULL COMMENT '类别，1 党统 2 其他',
  `template_file_path` varchar(100) DEFAULT NULL COMMENT '表格模板文件路径',
  `row` int(10) unsigned NOT NULL COMMENT '表格行数',
  `col` varchar(50) NOT NULL COMMENT '表格列数，使用对应的大写字母',
  `start_pos` varchar(10) NOT NULL COMMENT '数据填报的左上角单元格坐标，D8：第D列第8行',
  `end_pos` varchar(10) NOT NULL COMMENT '数据填报的右下角单元格坐标，V18：第V列第18行',
  `readonly_pos` varchar(200) DEFAULT NULL COMMENT '只读单元格坐标，即不需要填写数据的单元格坐标。格式：D9-D13;K8-K18;V9-V13;F8-F18;O8-O18;',
  `content` text NOT NULL COMMENT '具体事项',
  `deadline` datetime DEFAULT NULL COMMENT '应完成时间，具体到分',
  `contact` varchar(100) DEFAULT NULL COMMENT '联系方式，1个或2个座机号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` tinyint(3) unsigned DEFAULT NULL COMMENT '状态，0未发布 1 已发布  2 已完成 3 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据表格报送模板';

-- Data exporting was unselected.

-- Dumping structure for table db_owip.oa_grid_party
DROP TABLE IF EXISTS `oa_grid_party`;
CREATE TABLE IF NOT EXISTS `oa_grid_party` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `grid_id` int(10) unsigned NOT NULL COMMENT '所属表格模板',
  `year` int(10) unsigned NOT NULL COMMENT '所属年度',
  `party_id` int(10) unsigned NOT NULL COMMENT '报送分党委ID',
  `grid_name` varchar(200) NOT NULL COMMENT '表格名称',
  `party_name` varchar(200) NOT NULL COMMENT '报送分党委名称',
  `excel_file_path` varchar(100) DEFAULT NULL COMMENT '数据表格文件路径，与模板格式一致',
  `file_name` text COMMENT '签字文件名',
  `file_path` text COMMENT '签字文件，允许上传多个，pdf或图片格式，多个文件逗号隔开',
  `report_time` datetime DEFAULT NULL COMMENT '报送时间',
  `report_user_id` int(10) DEFAULT NULL COMMENT '报送人',
  `status` tinyint(3) unsigned NOT NULL COMMENT '状态，0未填报 1暂存 2已报送 3已退回',
  `back_reason` varchar(200) DEFAULT NULL COMMENT '退回原因',
  PRIMARY KEY (`id`),
  UNIQUE KEY `grid_id_year_party_id` (`grid_id`,`year`,`party_id`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='二级党委数据表格报送结果';

-- Data exporting was unselected.

-- Dumping structure for table db_owip.oa_grid_party_data
DROP TABLE IF EXISTS `oa_grid_party_data`;
CREATE TABLE IF NOT EXISTS `oa_grid_party_data` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `grid_party_id` int(10) unsigned NOT NULL COMMENT '所属二级党委上传表格',
  `cell_label` varchar(10) NOT NULL COMMENT '所属单元格，excel单元格坐标 格式：D5',
  `num` int(10) unsigned NOT NULL COMMENT '数量',
  PRIMARY KEY (`id`),
  KEY `FK_oa_grid_party_data_oa_grid_party` (`grid_party_id`),
  CONSTRAINT `FK_oa_grid_party_data_oa_grid_party` FOREIGN KEY (`grid_party_id`) REFERENCES `oa_grid_party` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=388 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='二级党委数据表格报送结果解析数据';

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2550, 0, '党统信息管理', '', 'menu', 'fa fa-bar-chart', NULL, 1, '0/1/', 0, 'partyStat:menu', NULL, NULL, NULL, 1, 4790);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2547, 0, '党统表格报送', '', 'url', '', '/oa/oaGridParty', 2550, '0/1/2550/', 1, 'oaGridParty:*', NULL, NULL, NULL, 1, 70);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2548, 0, '党统表格模板', '', 'url', '', '/oa/oaGrid', 2550, '0/1/2550/', 1, 'oaGrid:list', NULL, NULL, NULL, 1, 69);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2549, 0, '党统表格模板管理', '', 'function', '', NULL, 2550, '0/1/2550/', 1, 'oaGrid:*', NULL, NULL, NULL, 1, 69);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (2551, 0, '党统表格模板下发', '', 'function', '', NULL, 2550, '0/1/2550/', 1, 'oaGrid:release', NULL, NULL, NULL, 1, 60);
