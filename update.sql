
20190628
更新南航、北航 北师大X

20190627
修改 avatar.folder.ext

20190626
更新西交大

20190626
更新哈工大


20190624
更新南航

20190619

修改avatar 路径  \\ -> /  ，相应的linux作变更

drop table pay_order;

ALTER TABLE `party_school`
	COMMENT='二级党校',
	CHANGE COLUMN `name` `name` VARCHAR(200) NOT NULL COMMENT '二级党校名称' AFTER `id`,
	CHANGE COLUMN `found_date` `found_date` DATE NULL DEFAULT NULL COMMENT '成立时间' AFTER `name`,
	ADD COLUMN `abolish_date` DATE NULL DEFAULT NULL COMMENT '撤销时间，撤销后成为历史党校' AFTER `found_date`,
	ADD COLUMN `seq` VARCHAR(50) NULL DEFAULT NULL COMMENT '批次' AFTER `abolish_date`,
	CHANGE COLUMN `is_history` `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态，1 正在运转  2 历史党校' AFTER `sort_order`,
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `status`;
RENAME TABLE `party_school` TO `ps_info`;
ALTER TABLE `ps_info`
	CHANGE COLUMN `seq` `seq` VARCHAR(50) NULL DEFAULT NULL COMMENT '批次' AFTER `id`;
ALTER TABLE `ps_info`
	ADD COLUMN `is_history` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '历史党校/正在运转' AFTER `sort_order`,
	DROP COLUMN `status`;

UPDATE sys_resource SET permission=REPLACE(permission, 'partySchool:', 'ps:') WHERE permission LIKE 'partySchool:%';
UPDATE sys_resource SET url='/ps/psInfo', permission='psInfo:*' WHERE url = '/partySchool';

UPDATE sys_resource SET permission='system:menu' WHERE permission = 'system:*';

-- 删除 party_school相关目录

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1058, 0, '批量导入', '', 'function', '', NULL, 862, '0/1/862/', 1, 'cadreCompanyList:import', NULL, NULL, NULL, 1, NULL);

-- 给cadreCompanyList:import 添加角色

20190615
创建北航

