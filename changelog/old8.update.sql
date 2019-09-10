

20190831
西交

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (230, 0, '受处分情况', '',
'function', '', NULL, 90, '0/1/88/90/', 1, 'cadrePunish:*', NULL, NULL, NULL, 1, NULL);

CREATE TABLE `sc_group_topic_user` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
	`topic_id` INT(10) UNSIGNED NOT NULL COMMENT '议题',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '考察对象',
	`title` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '所在单位及职务，优先从干部库中读取，否则人事库的单位',
	PRIMARY KEY (`id`)
)
COMMENT='确定考察对象，干部小组会议题'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=482
;

INSERT INTO `base_meta_class` (`id`, `role_id`, `name`, `first_level`, `second_level`,
                               `code`, `bool_attr`, `extra_attr`, `extra_options`, `sort_order`, `available`)
VALUES (91, NULL, '议题类型', '干部小组会议题', '参数设置', 'mc_sc_group_topic_type', '', '', '', 2593, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (91, '干部选任-动议', 'mt_sgt_motion', NULL, '', '', 1, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (91, '干部选任-确定考察对象', 'mt_sgt_candidate', NULL, '', '', 2, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (91, '干部选任-推荐拟任人选', 'mt_sgt_recommend', NULL, '', '', 3, 1);
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`) VALUES (91, '其他', 'mt_sgt_other', NULL, '', '', 4, 1);

ALTER TABLE `sys_teacher_info`
	ADD COLUMN `is_temp` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否临时工' AFTER `is_honor_retire`;
-- 西安交大要重新同步 staff_status和is_temp字段， 只给管理员开放校领导权限
ALTER TABLE `sys_teacher_info`
	CHANGE COLUMN `is_temp` `is_temp` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否临时工' AFTER `is_honor_retire`;
update sys_teacher_info set  is_temp = 0;
ALTER TABLE `sys_teacher_info`
	CHANGE COLUMN `is_temp` `is_temp` VARCHAR(20) NULL DEFAULT NULL COMMENT '是否临时人员' AFTER `is_honor_retire`;

-- 更新 cadre_view 等

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`) VALUES (23, 0, '修改基本信息', '', 'function',
 '', NULL, 22, '0/1/21/22/', 1, 'sysUser:editInfo', NULL, NULL, NULL, 1, NULL);

CREATE TABLE `cadre_punish` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`cadre_id` INT(10) UNSIGNED NOT NULL COMMENT '所属干部',
	`punish_time` DATE NULL DEFAULT NULL COMMENT '日期',
	`name` VARCHAR(200) NULL DEFAULT NULL COMMENT '受何种处分',
	`unit` VARCHAR(300) NULL DEFAULT NULL COMMENT '处分单位',
	`list_in_ad` TINYINT(1) UNSIGNED NULL DEFAULT '0' COMMENT '是否列入干部任免审批表',
	`sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
	`status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态， 0：正式记录 1：修改记录',
	`remark` TEXT NULL COMMENT '备注',
	PRIMARY KEY (`id`),
	INDEX `FK_cadre_punish_cadre` (`cadre_id`),
	CONSTRAINT `FK_cadre_punish_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE
)
COMMENT='干部受处分情况'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=3
;


20190827
南航


20190826
北邮

20190826
ALTER TABLE `sc_motion`
	CHANGE COLUMN `seq` `seq` VARCHAR(50) NOT NULL COMMENT '动议编号' AFTER `id`,
	CHANGE COLUMN `way` `way` TINYINT(3) UNSIGNED NOT NULL COMMENT '动议主体，党委干部工作小组会、 党委常委会、 其他' AFTER `year`,
	ADD COLUMN `committee_id` INT UNSIGNED NULL COMMENT '动议记录，党委常委会' AFTER `way`,
	ADD COLUMN `group_topic_id` INT UNSIGNED NULL COMMENT '动议记录，干部小组会议题，类型为干部选任动议' AFTER `committee_id`,
	CHANGE COLUMN `hold_date` `hold_date` DATE NOT NULL COMMENT '动议时间' AFTER `group_topic_id`,
	CHANGE COLUMN `unit_post_id` `unit_post_id` INT(10) UNSIGNED NOT NULL COMMENT '拟调整岗位' AFTER `hold_date`,
	CHANGE COLUMN `topics` `topics` VARCHAR(300) NULL DEFAULT NULL COMMENT '关联议题，干部小组会议题或党委常委会议题，弃用' AFTER `way_other`;

ALTER TABLE `sc_motion`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `topics`;

ALTER TABLE `sc_motion`
	CHANGE COLUMN `way_other` `way_other` VARCHAR(100) NULL DEFAULT NULL COMMENT '其他动议主体' AFTER `way`,
	CHANGE COLUMN `committee_id` `committee_topic_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '动议记录，党委常委会议题' AFTER `way_other`,
	CHANGE COLUMN `sc_type` `sc_type` INT(10) UNSIGNED NOT NULL COMMENT '选任方式，关联元数据' AFTER `unit_post_id`,
	ADD COLUMN `content` VARCHAR(300) NULL COMMENT '工作方案' AFTER `sc_type`,
	DROP COLUMN `topics`;

ALTER TABLE `sc_motion`
	CHANGE COLUMN `content` `content` TEXT NULL DEFAULT NULL COMMENT '工作方案' AFTER `sc_type`;

DROP VIEW IF EXISTS `sc_motion_view`;
CREATE ALGORITHM = UNDEFINED  VIEW `sc_motion_view` AS
select sm.*, up.name as post_name, up.job, up.admin_level, up.post_type, up.unit_id, u.type_id as unit_type
from sc_motion sm
       left join unit_post up on up.id = sm.unit_post_id
       left join unit u on u.id = up.unit_id;

ALTER TABLE `crs_post`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实，选任方式为竞争上岗的纪实' AFTER `seq`,
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `record_id`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的岗位' AFTER `record_user_id`,
	CHANGE COLUMN `name` `name` VARCHAR(100) NOT NULL COMMENT '招聘岗位，如果选择了岗位则同步岗位信息' AFTER `unit_post_id`;

DROP VIEW IF EXISTS `sc_record_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_record_view` AS
select sr.*, sm.hold_date, sm.sc_type, sm.unit_post_id, up.code AS post_code, up.name as post_name, up.job, up.admin_level, up.post_type, up.unit_id,
 u.type_id as unit_type from sc_record sr
left join sc_motion sm on sm.id=sr.motion_id
left join unit_post up on up.id = sm.unit_post_id
left join unit u on u.id = up.unit_id;


update base_meta_type SET CODE='mt_sctype_crs' WHERE  class_id=70 AND NAME='竞争上岗';


ALTER TABLE `cadre_inspect`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实，确定考察对象' AFTER `id`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '拟任职务' AFTER `record_id`,
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `status`;

DROP VIEW IF EXISTS `cadre_inspect_view`;
CREATE ALGORITHM=UNDEFINED VIEW `cadre_inspect_view` AS
select ci.id as inspect_id, ci.record_id, ci.unit_post_id, ci.`type` as inspect_type, ci.`status` as inspect_status,
ci.remark as inspect_remark, ci.record_user_id, ci.sort_order as inspect_sort_order, cv.*
from cadre_inspect ci left join cadre_view cv on ci.cadre_id=cv.id;

UPDATE base_meta_type SET NAME='选任考察' WHERE CODE='mt_cis_type_assign';

ALTER TABLE `cis_inspect_obj`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `id`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '拟任职务' AFTER `record_id`,
	CHANGE COLUMN `assign_post` `assign_post` VARCHAR(200) NULL DEFAULT NULL COMMENT '考察对象拟任职务，弃用' AFTER `post`;

ALTER TABLE `cis_inspect_obj`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `log_file`;
ALTER TABLE `cis_inspect_obj`
	ADD COLUMN `record_ids` VARCHAR(100) NULL DEFAULT NULL COMMENT '考察复用对应的纪实' AFTER `record_id`,
	ADD COLUMN `report` VARCHAR(255) NULL DEFAULT NULL COMMENT '考察期间有无举报' AFTER `log_file`;

DROP VIEW IF EXISTS `cis_inspect_obj_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cis_inspect_obj_view` AS
select cio.*, saa.id as archive_id from cis_inspect_obj cio
left join sc_ad_archive saa on saa.obj_id=cio.id, base_meta_type bmt
where cio.type_id=bmt.id order by cio.year desc, bmt.sort_order desc, cio.seq desc;


ALTER TABLE `sc_letter_item`
	ADD COLUMN `record_ids` VARCHAR(100) NULL COMMENT '对应的选任纪实' AFTER `user_id`;

ALTER TABLE `sc_letter_item`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `user_id`,
	CHANGE COLUMN `record_ids` `record_ids` VARCHAR(100) NULL DEFAULT NULL COMMENT '函询复用对应的选任纪实' AFTER `record_id`,
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `record_ids`;

DROP VIEW IF EXISTS `sc_letter_reply_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_letter_reply_item_view` AS
select slri.*, sli.id as item_id, sli.record_id, sli.record_ids, sli.record_user_id,
slr.letter_id, slr.type as reply_type, slr.reply_date, slr.num as reply_num,
slr.file_path as reply_file_path, slr.file_name as reply_file_name,
sl.year as letter_year, sl.num as letter_num,
sl.file_path as letter_file_path, sl.file_name as letter_file_name,
sl.query_date as letter_query_date, sl.type as letter_type, u.realname, u.code from sc_letter_reply_item slri
left join sys_user_view u on slri.user_id=u.id
left join sc_letter_reply slr on slr.id=slri.reply_id and slr.is_deleted=0
left join sc_letter sl on sl.id=slr.letter_id and sl.is_deleted=0
LEFT JOIN sc_letter_item sli ON sli.letter_id=sl.id AND sli.user_id=u.id;

ALTER TABLE `sc_matter_check_item`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `user_id`,
	ADD COLUMN `record_ids` VARCHAR(100) NULL DEFAULT NULL COMMENT '核查复用对应的选任纪实' AFTER `record_id`,
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `record_ids`;

DROP VIEW IF EXISTS `sc_matter_check_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_check_item_view` AS
select smci.*, smc.year, smc.is_random, smc.check_date, smc.num, u.code, u.realname
from sc_matter_check_item smci
left join sc_matter_check smc on smc.id=smci.check_id
left join sys_user_view u on u.id=smci.user_id;

ALTER TABLE `sc_committee_topic`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `remark`;

DROP VIEW IF EXISTS `sc_committee_topic_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_topic_view` AS
select sct.*, sc.year, sc.hold_date, sc.committee_member_count, sc.count, sc.absent_count,
sc.attend_users, sc.file_path, sc.log_file from sc_committee_topic sct
left join sc_committee_view sc on sc.id=sct.committee_id
where sc.is_deleted=0 ;

ALTER TABLE `sc_committee_vote`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `remark`;

DROP VIEW IF EXISTS `sc_committee_vote_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_vote_view` AS
select scv.*, sct.name, sct.seq, sct.content, sct.committee_id, sct.vote_file_path, sct.record_user_id,
sctc.original_post, sctc.original_post_time,
sc.year, sc.hold_date, sc.committee_member_count, sc.count, sc.absent_count, sc.attend_users, sc.file_path, sc.log_file,
-- 已使用的ID
sdu.id as dispatch_user_id
from sc_committee_vote scv
left join sc_committee_topic sct on sct.id=scv.topic_id
left join sc_committee_topic_cadre sctc on sctc.topic_id=scv.topic_id and sctc.cadre_id=scv.cadre_id
left join sc_committee_view sc on sc.id=sct.committee_id
left join sc_dispatch_user sdu on sdu.vote_id=scv.id;

ALTER TABLE `sc_public_user`
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `sort_order`;


ALTER TABLE `sc_public`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `is_deleted`;

ALTER TABLE `sc_dispatch`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `remark`;

DROP VIEW IF EXISTS `sc_dispatch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_dispatch_view` AS
 select sd.*, d.id as dispatch_id, d.file as dispatch_file, d.file_name as dispatch_file_name,
 dt.sort_order as dispatch_type_sort_order, sum(if(sdu.type=1, 1, 0)) as appoint_count,
 sum(if(sdu.type=2, 1, 0))  as dismiss_count from sc_dispatch sd
left join sc_dispatch_user sdu on sdu.dispatch_id=sd.id
left join dispatch_type dt on sd.dispatch_type_id = dt.id
left join dispatch d on d.sc_dispatch_id=sd.id
group by sd.id ;

DROP VIEW IF EXISTS `sc_public_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_public_user_view` AS
SELECT spu.*, sp.year, sp.committee_id, sp.publish_date, sp.public_start_date, sp.public_end_date,
sp.pdf_file_path, sp.word_file_path, sp.record_user_id,
sctc.original_post, scv.cadre_id, scv.post  from sc_public_user spu
LEFT JOIN sc_public sp ON sp.id=spu.public_id
LEFT JOIN sc_committee_vote scv ON scv.id=spu.vote_id
left join sc_committee_topic_cadre sctc on sctc.topic_id=scv.topic_id and sctc.cadre_id=scv.cadre_id;

ALTER TABLE `cadre_admin_level`
	DROP FOREIGN KEY `FK_base_cadre_post_base_dispatch_cadre`,
	DROP FOREIGN KEY `FK_base_cadre_post_base_dispatch_cadre_2`;
ALTER TABLE `cadre_admin_level`
	ADD CONSTRAINT `FK_base_cadre_post_base_dispatch_cadre` FOREIGN KEY (`start_dispatch_cadre_id`) REFERENCES `dispatch_cadre` (`id`) ON DELETE SET NULL,
	ADD CONSTRAINT `FK_base_cadre_post_base_dispatch_cadre_2` FOREIGN KEY (`end_dispatch_cadre_id`) REFERENCES `dispatch_cadre` (`id`) ON DELETE SET NULL;

ALTER TABLE `dispatch_cadre`
	CHANGE COLUMN `post_type` `post_type` INT(10) UNSIGNED NOT NULL COMMENT '职务属性' AFTER `post`,
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别' AFTER `post_type`,
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实' AFTER `remark`;

ALTER TABLE `dispatch`
	ADD COLUMN `record_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `remark`;

DROP VIEW IF EXISTS `dispatch_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dispatch_view` AS
select d.* from dispatch d, dispatch_type dt
    where d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc ;

DROP VIEW IF EXISTS `dispatch_cadre_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dispatch_cadre_view` AS
select dc.*, bmt.extra_attr as post_team,
d.category, d.year, d.pub_time,d.work_time,
d.dispatch_type_id, d.code , d.has_checked,d.record_user_id
from dispatch_cadre dc
left join base_meta_type bmt on bmt.id=dc.post_type, dispatch d, dispatch_type dt
where dc.dispatch_id = d.id and  d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc, dc.type asc  ;

-- 给分党委和党建管理员增加批量导入转出记录的权限
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1181, 0, '批量导入转出记录', '', 'function', '', NULL, 252, '0/1/105/252/',
                                    1, 'memberOutImport:*', NULL, NULL, NULL, 1, NULL);

ALTER TABLE `sc_public`
	CHANGE COLUMN `public_start_date` `public_start_date` DATETIME NULL COMMENT '公示起始时间' AFTER `pdf_file_path`,
	CHANGE COLUMN `public_end_date` `public_end_date` DATETIME NULL COMMENT '公示结束时间' AFTER `public_start_date`;

-- 删除 sc_letter_item_view  及 相关代码
drop view sc_letter_item_view;
-- 删除 sc_public_view 及 相关代码
drop view sc_public_view;

-- 更新location.js


20190819
更新南航

20190819

ALTER TABLE `unit_function`
	DROP COLUMN `img_path`;

ALTER TABLE `modify_table_apply`
	ADD COLUMN `reason` VARCHAR(200) NULL COMMENT '修改原因' AFTER `type`;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                        `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1090, 0, '参数设置', '', 'function', '', NULL, 626, '0/1/339/626/', 1, 'mc_sc_group_topic_type:*', NULL, NULL, NULL, 1, NULL);
ALTER TABLE `sc_group_topic`
	ADD COLUMN `type` INT UNSIGNED NULL COMMENT '议题类型' AFTER `name`,
	ADD COLUMN `unit_post_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '拟调整的岗位，动议、确定考察对象、推荐拟任人选' AFTER `type`,
	ADD COLUMN `sc_type` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '选任方式，动议' AFTER `unit_post_id`,
	ADD COLUMN `record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '对应的纪实，确定考察对象、推荐拟任人选' AFTER `sc_type`,
	ADD COLUMN `candidate_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '推荐拟任人选，从确定的考察对象中选一人' AFTER `record_id`,
	CHANGE COLUMN `content` `content` LONGTEXT NULL COMMENT '议题内容（其他）/工作方案（动议）' AFTER `candidate_user_id`;

ALTER TABLE `sc_group`
	ADD COLUMN `record_user_id` INT UNSIGNED NULL DEFAULT NULL COMMENT '纪实人员' AFTER `attend_users`;

DROP VIEW IF EXISTS `sc_group_topic_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_group_topic_view` AS
select sgt.*, sg.year, sg.hold_date, sg.file_path as group_file_path, sg.log_file, sg.attend_users, sgtu.unit_ids
 from sc_group_topic sgt
left join sc_group sg on sgt.group_id = sg.id
left join (select group_concat(unit_id) as unit_ids, topic_id from sc_group_topic_unit group by topic_id) sgtu on sgtu.topic_id=sgt.id
where sg.is_deleted=0;


UPDATE base_meta_class SET CODE='mc_sc_type' WHERE  CODE='mc_sc_motion_sctype';

-- 重新发布jx.utils-1.1.jar

20190815
南航、北邮、北航、北化工、西交大、哈工大

update sys_resource SET url='/modifyBaseApply?type=admin' WHERE permission='modifyBaseApply:menu';
20190814
南航、北邮、北航、北化工、西交大、哈工大


20190813

-- 修改pom.xml，改为nexus仓库，并删除 /lib下所有jar
-- 改api.bnu.jar -> jx.bnu.jar
-- common-utils.jar -> jx.utils.jar
-- 重新传lib包

ALTER TABLE `cadre_edu`
	ADD COLUMN `is_second_degree` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否第二个学位，存在两个最高学位时有效' AFTER `is_high_degree`;
UPDATE cadre_edu SET  is_second_degree=0;

ALTER TABLE `cadre`
	ADD COLUMN `is_dep` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否院系干部' AFTER `type`;
UPDATE cadre c, unit u, base_meta_type bmt, cadre_post main_cadre_post SET c.is_dep=1 WHERE
main_cadre_post.cadre_id=c.id and main_cadre_post.is_first_main_post=1 and
main_cadre_post.unit_id=u.id AND u.type_id=bmt.id AND bmt.code='mt_unit_type_xy';

-- 更新 cadre_view 等

INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`)
VALUES (27, '高中', 'mt_edu_gz', NULL, 'gz', '', 135, 1);
-- 调整学历顺序

ALTER TABLE `cadre_work`
	ADD COLUMN `is_edu_work` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否学习其间工作' AFTER `fid`,
	DROP FOREIGN KEY `FK_base_cadre_work_base_cadre_work`;
ALTER TABLE `cadre_work`
	CHANGE COLUMN `fid` `fid` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所属学习或工作经历，其间工作时设定' AFTER `id`;
ALTER TABLE `cadre_edu`
	ADD COLUMN `sub_work_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '其间工作数量，提交其间工作时设定' AFTER `id`;

UPDATE sys_property SET `remark`='1：北京  2：中组部(仿宋_GB2312) 3: 中组部(宋体)（系统默认）' WHERE  `code`='adFormType';

UPDATE `base_meta_class` SET `extra_options`='gz|学生|高中,zz|学生|中专,zk|学生|专科,bk|学生|本科,yjs|硕士研究生|研究生,yjskcb|硕士研究生（研究生课程班）|研究生,sstd|硕士研究生（硕士同等学历）|研究生,bs|博士研究生|博士研究生' WHERE code='mc_edu';
-- 更新导入样表

20190809
UPDATE sys_role SET is_sys_hold=0 WHERE is_sys_hold IS NULL;
-- 增加SysSyncService.java，修改SyncService.java, Source.java

ALTER TABLE `cadre_party`
	ADD COLUMN `is_first` TINYINT(1) UNSIGNED NULL COMMENT '是否第一民主党派，针对干部可能有多个民主党派的情况' AFTER `type`;
ALTER TABLE `cadre_party`
	DROP INDEX `user_id_type`;
-- 更新 cadre_view 等

UPDATE cadre_party SET is_first=1 WHERE TYPE=1;
DROP VIEW IF EXISTS `cadre_party_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cadre_party_view`
AS SELECT cp.*, cv.id as cadre_id, cv.code, cv.realname, cv.unit_id, cv.ow_grow_time, cv.member_status, cv.admin_level,
cv.post_type, cv.title AS cadre_title, cv.post AS cadre_post,
cv.status as cadre_status,cv.sort_order AS cadre_sort_order
FROM cadre_party cp
LEFT JOIN cadre_view cv ON cp.user_id=cv.user_id ;


20190806

提交ps、cg模块


删除cpc.common

更新common-utils

-- 查询家庭成员重复申请的情况
SELECT cf.id, cf.cadre_id, cf.status, cf.title, bmt.name, c.realname, c.code FROM cadre_family cf
LEFT JOIN cadre_view c ON cf.cadre_id=c.id
LEFT JOIN base_meta_type bmt ON cf.title=bmt.id
 WHERE cf.STATUS IN(0,1) AND bmt.bool_attr=1
 AND c.user_id IN ( SELECT user_id FROM modify_table_apply WHERE module=17 AND TYPE=1)
 AND cf.id IN(SELECT modify_id FROM modify_table_apply WHERE module=17 AND TYPE=1)
 GROUP BY cf.cadre_id, cf.title HAVING COUNT(cf.id)>1;

20190804
南航、北邮、哈工大、北航、北化工、西交大

20190802

更新common-utils

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('pdfResolution', 'PDF图像的解析度', '300', 2, 39, '');

1.删除 swfTools.command 和 swfTools.languagedir
2.运行 /test/dispatch.jsp

20190801

ALTER TABLE `ow_member`
	CHANGE COLUMN `transfer_time` `transfer_time` DATE NULL DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示' AFTER `add_type`;

CREATE TABLE `ow_member_check` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '当前所属分党委，用于权限分配',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '当前所属党支部，直属党支部没有这一项',
	`avatar` VARCHAR(100) NULL DEFAULT NULL COMMENT '头像',
	`native_place` VARCHAR(100) NULL DEFAULT NULL COMMENT '籍贯',
	`mobile` VARCHAR(100) NULL DEFAULT NULL COMMENT '手机号',
	`phone` VARCHAR(100) NULL DEFAULT NULL COMMENT '办公电话',
	`email` VARCHAR(100) NULL DEFAULT NULL COMMENT '邮箱',
	`political_status` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '政治面貌，1 预备党员、2 正式党员',
	`transfer_time` DATE NULL DEFAULT NULL COMMENT '组织关系转入时间，进入系统方式为外校转入时显示',
	`apply_time` DATE NULL DEFAULT NULL COMMENT '提交书面申请书时间，时间从入党申请同步过来',
	`active_time` DATE NULL DEFAULT NULL COMMENT '确定为入党积极分子时间',
	`candidate_time` DATE NULL DEFAULT NULL COMMENT '确定为发展对象时间',
	`sponsor` VARCHAR(50) NULL DEFAULT NULL COMMENT '入党介绍人',
	`grow_time` DATE NULL DEFAULT NULL COMMENT '入党时间',
	`grow_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '入党时所在党支部',
	`positive_time` DATE NULL DEFAULT NULL COMMENT '转正时间',
	`positive_branch` VARCHAR(200) NULL DEFAULT NULL COMMENT '转正时所在党支部',
	`party_post` VARCHAR(100) NULL DEFAULT NULL COMMENT '党内职务',
	`party_reward` VARCHAR(255) NULL DEFAULT NULL COMMENT '党内奖励',
	`other_reward` VARCHAR(255) NULL DEFAULT NULL COMMENT '其他奖励',
	`original_json` TEXT NULL COMMENT '变更记录对应的原来的值，JSON对象格式',
	`create_time` DATETIME NULL DEFAULT NULL COMMENT '申请时间',
	`ip` VARCHAR(50) NULL DEFAULT NULL COMMENT '申请人IP',
	`status` TINYINT(3) NOT NULL COMMENT '状态，-1返回修改 0申请 1审批通过',
	`reason` VARCHAR(200) NULL DEFAULT NULL COMMENT '返回修改原因',
	PRIMARY KEY (`id`)
)
COMMENT='党员信息修改申请'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
AUTO_INCREMENT=17
;


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1072, 0, '党员信息修改审批', '', 'url', '', '/member/memberCheck?cls=2', 105, '0/1/105/', 1, 'memberCheck:*', NULL, NULL, NULL, 1, 29800);
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`) VALUES (1073, 0, '党员信息修改申请', '', 'url', '', '/member/memberCheck?cls=1', 258, '0/1/258/', 1, 'memberCheck:list', NULL, NULL, NULL, 1, 980);

-- 党员信息修改申请/审批 设置角色权限

20190730
南航

20190730

-- 弃用 cet_project的 status 和 pub_status字段
ALTER TABLE `cet_project`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL DEFAULT '1' COMMENT '状态（弃用），0 未启动、 1 正在进行、 2 已结束' AFTER `remark`,
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `status`;
update cet_project SET STATUS = 1;
update cet_project SET pub_status = 1;

-- 弃用 cet_train的 pub_status字段
ALTER TABLE `cet_train`
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `enroll_status`;
update cet_train SET pub_status = 1;

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`)
VALUES ('培训数据校正', '包含：自动修改培训班的结课状态', 'job.cet.CetAutoAdjust', '0 0 0/1 * * ?', 1, 0, 25, '2019-07-29 16:47:26');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('scSubsidyLogo', '津贴调整文件LOGO', '', 5, 38, '大小628*205，PNG格式');

删除 OaTaskMsgService.java

20190728
哈工大
20190728

更新common-utils

-- 调整岗位排序为正序
UPDATE unit_post SET sort_order=code;

20190727
北邮、哈工大、北航

20190727

删除 service.base.ShortMsgService
删除 service.auth

改 login.service / xss.ignoreUrIs

20190726
更新 南航、西交大、 北化工