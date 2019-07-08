
20190707
更新南航，北邮，北航，西交大，哈工大

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `phone` `phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '办公电话' AFTER `unit`,
	CHANGE COLUMN `home_phone` `home_phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '家庭电话' AFTER `phone`,
	CHANGE COLUMN `mailing_address` `mailing_address` VARCHAR(100) NULL DEFAULT NULL COMMENT '通讯地址' AFTER `email`;

ALTER TABLE `cadre_reward`
	CHANGE COLUMN `unit` `unit` VARCHAR(300) NULL DEFAULT NULL COMMENT '颁奖单位' AFTER `name`;


20190706
更新南航，北邮，北航

20190706

更新common-utils

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('abroadContactUseSign', '办理证件联系人是否签名', 'true', 3, 32, '否则使用联系人（固定一个人）的姓名');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('abroadContactUser', '办理证件联系人工号', '', 1, 33, 'abroadContactUseSign=false时才有效，生效时如果为空，则默认为当前登录账号');


20190704 更新 南航


20190704 更新 北邮

ALTER TABLE `sys_user_info`
	ADD INDEX `idcard` (`idcard`);


20190704 更新 南航

UPDATE `sys_resource` SET `permission`='crpRecord:list' WHERE  `id`=417;
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1041, 0, '管理挂职', NULL, 'function', NULL, NULL, 417, '0/1/417/', 1, 'crpRecord:*', NULL, NULL, NULL, 1, NULL);


20190704 更新 北邮

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
 VALUES ('rewardOnlyYear', '仅保存获奖年份', 'true', 3, 31, '获奖日期仅保留年份，否则使用年月');

update base_meta_type SET code='mt_party_member' where name='委员' and class_id=22;

20190704 更新 北邮

update base_meta_class set extra_attr='任免简历描述（全日制|在职）' , extra_options='zk|学生|专科,bk|学生|本科,yjs|硕士研究生|研究生,yjskcb|硕士研究生（研究生课程班）|研究生,sstd|硕士研究生（硕士同等学历）|研究生,bs|博士研究生|博士研究生' where code='mc_edu';


update base_meta_type SET extra_attr='bk' where code='mt_edu_bk';
update base_meta_type SET extra_attr='yjs' where code='mt_edu_master';
update base_meta_type SET extra_attr='zk' where code='mt_edu_zk';
update base_meta_type SET extra_attr='bs' where code='mt_edu_doctor';
update base_meta_type SET extra_attr='sstd' where code='mt_edu_sstd';
update base_meta_type SET extra_attr='yjskcb' where code='mt_edu_yjskcb';

ALTER TABLE `ow_member_out`
	CHANGE COLUMN `from_post_code` `from_post_code` VARCHAR(100) NOT NULL COMMENT '转出单位邮编，默认为100875' AFTER `from_fax`;

20190704 更新 北邮

删除XlsUpload.java

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1062, 0, '导出附带工号', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'sysUser:filterExport', NULL, NULL, NULL, 1, NULL);


更新common-utils

20190703  更新 北邮

ALTER TABLE `cadre_family`
	CHANGE COLUMN `title` `title` INT UNSIGNED NULL DEFAULT NULL COMMENT '称谓' AFTER `cadre_id`;

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`, `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`) VALUES (89, NULL, '家庭成员称谓', '领导干部信息', '家庭成员信息', 'mc_family_title', '是否唯一', '类型', 'zb|长辈,po|配偶,zv|子女', 89, 1);

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (89, '父亲', 'mt_aliiib', 1, 'zb', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (89, '母亲', 'mt_knp6ff', 1, 'zb', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (89, '配偶', 'mt_ygndka', 1, 'po', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (89, '儿子', 'mt_mehm4n', 0, 'zv', '', 4, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (89, '女儿', 'mt_o7hti2', 0, 'zv', '', 5, 1);

DROP TABLE `cadre_concat`;

-- 不需要更新入 sys_user_view
ALTER TABLE `sys_user_info`
	ADD COLUMN `resume` LONGTEXT NULL DEFAULT NULL COMMENT '干部任免审批表简历' AFTER `msg_title`;

UPDATE sys_resource SET NAME='添加/更新账号', permission='sysUser:edit' WHERE permission='sysUser:*';
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (1060, 0, '查看中组部干部任免审批表简历', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'sysUser:resume', NULL, NULL, NULL, 1, NULL);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`,
                            `sort_order`) VALUES (1061, 0, '禁用/解禁账号', '', 'function', '', NULL, 22, '0/1/21/22/', 1, 'sysUser:del', NULL, NULL, NULL, 1, NULL);

-- 根据实际情况更新
update cadre_family set title=548 where title=1;
update cadre_family set title=549 where title=2;
update cadre_family set title=550 where title=3;
update cadre_family set title=551 where title=4;
update cadre_family set title=552 where title=5;
-- 更新[账号管理]的角色权限

更新common-utils



20190629  更新 北邮

drop view cet_party_school_view;

-- + metaClass:viewAll
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (1059, 0, '查看所有元数据的权限', '默认只能查看本角色相关的元数据', 'function', '', NULL, 72, '0/1/67/72/', 1, 'metaClass:viewAll', NULL, NULL, NULL, 1, NULL);

20190628
更新南航、北航

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

