

#2015.1129
# 联系院系所 mt_leader_contact
# 分管部门 mt_leader_manager
# 单位管理->综合管理 3 /unit_layout unitLayout:*
ALTER TABLE `base_unit_admin`
	CHANGE COLUMN `is_admin` `is_admin` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否管理员，暂未用' AFTER `post_id`;

##2015.11.28
drop table base_unit_transfer_item;
ALTER TABLE `base_unit_transfer`
	ADD COLUMN `dispatchs` VARCHAR(200) NULL COMMENT '相关发文' AFTER `pub_time`;

drop table base_unit_cadre_transfer_item;
	ALTER TABLE `base_unit_cadre_transfer`
	ADD COLUMN `dispatchs` VARCHAR(200) NOT NULL COMMENT '相关发文，逗号分隔' AFTER `dismiss_time`;

CREATE TABLE `base_unit_admin_group` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '上一届',
	`unit_id` INT(10) UNSIGNED NOT NULL COMMENT '所属单位',
	`name` VARCHAR(100) NOT NULL COMMENT '名称',
	`is_present` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否现任班子',
	`tran_time` DATE NULL DEFAULT NULL COMMENT '应换届时间',
	`actual_tran_time` DATE NULL DEFAULT NULL COMMENT '实际换届时间',
	`appoint_time` DATE NOT NULL COMMENT '任命时间，本届班子任命时间',
	`dispatch_unit_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发文，关联单位发文',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`),
	INDEX `FK_base_unit_admin_group_base_unit_admin_group` (`fid`),
	INDEX `FK_base_unit_admin_group_base_unit` (`unit_id`),
	CONSTRAINT `FK_base_unit_admin_group_base_unit` FOREIGN KEY (`unit_id`) REFERENCES `base_unit` (`id`),
	CONSTRAINT `FK_base_unit_admin_group_base_unit_admin_group` FOREIGN KEY (`fid`) REFERENCES `base_unit_admin_group` (`id`)
)
COMMENT='单位行政班子'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

CREATE TABLE `base_unit_admin` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`group_id` INT(10) UNSIGNED NOT NULL COMMENT '所属班子',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '关联干部',
	`post_id` INT(10) UNSIGNED NOT NULL COMMENT '职务属性，关联元数据',
	`is_admin` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否管理员，暂未用',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	PRIMARY KEY (`id`),
	INDEX `FK_base_unit_admin_base_unit_admin_group` (`group_id`),
	INDEX `FK_base_unit_admin_base_cadre` (`cadre_id`),
	INDEX `FK_base_unit_admin_base_meta_type` (`post_id`),
	CONSTRAINT `FK_base_unit_admin_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `base_cadre` (`id`),
	CONSTRAINT `FK_base_unit_admin_base_meta_type` FOREIGN KEY (`post_id`) REFERENCES `base_meta_type` (`id`),
	CONSTRAINT `FK_base_unit_admin_base_unit_admin_group` FOREIGN KEY (`group_id`) REFERENCES `base_unit_admin_group` (`id`)
)
COMMENT='行政班子成员信息'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=COMPACT
;

