
20190726
更新 南航、西交大、 北化工

20190726

转移 service.ext -> ext.service
删除 controller.ext
ext.utils.xxx

修改民主党派，增加群众
UPDATE base_meta_class SET bool_attr='是否群众'  WHERE CODE='mc_democratic_party';


UPDATE `sys_scheduler_job` SET `name`='系统数据校正',
                                         `clazz`='job.base.DataAutoAdjust' WHERE clazz='job.cadre.RefreshHasCrp';

删除 job.cadre.RefreshHasCrp

更新pom.xml 增加 imageio-tiff.jar

20190725
西交

20190723
南航

更正家庭成员排序
更正课程排序

20190722

ALTER TABLE `sys_student_info`
	CHANGE COLUMN `enrol_year` `enrol_year` VARCHAR(50) NULL DEFAULT NULL COMMENT '所在年级，即招生年度' AFTER `is_full_time`,
	CHANGE COLUMN `grade` `grade` VARCHAR(10) NULL DEFAULT NULL COMMENT '年级，弃用' AFTER `period`;

UPDATE cadre_post SET post=post_name WHERE is_main_post=0;

ALTER TABLE `cadre_reserve`
	ADD COLUMN `post_time` DATE NULL COMMENT '任职时间' AFTER `type`;

ALTER TABLE `cadre`
	ADD COLUMN `has_crp` TINYINT(1) UNSIGNED NULL COMMENT '是否有挂职锻炼经历，工作经历添加挂职锻炼经历或挂职锻炼模块中添加时更新此字段' AFTER `type`;

ALTER TABLE `cadre`
	ADD COLUMN `is_double` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否双肩挑，只用于主职' AFTER `has_crp`,
	ADD COLUMN `double_unit_ids` VARCHAR(200) NULL DEFAULT NULL COMMENT '双肩挑单位' AFTER `is_double`;

update cadre set is_double=0;
UPDATE cadre c ,(
SELECT cadre_id, is_double, double_unit_ids FROM cadre_post WHERE is_first_main_post=1 AND is_double=1
) cp SET c.is_double=cp.is_double, c.double_unit_ids=cp.double_unit_ids WHERE c.id=cp.cadre_id;

ALTER TABLE `cadre_post`
	DROP COLUMN `is_double`,
	DROP COLUMN `double_unit_ids`;

ALTER TABLE `cadre_post`
	ADD COLUMN `lp_work_time` DATE NULL DEFAULT NULL COMMENT '任职日期，最后一个任职日期' AFTER `post`,
	ADD COLUMN `np_work_time` DATE NULL DEFAULT NULL COMMENT '现任职务始任日期' AFTER `lp_work_time`;

ALTER TABLE `cadre_post`
	ADD COLUMN `lp_dispatch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联始任文件' AFTER `post`,
	ADD COLUMN `np_dispatch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联任免文件' AFTER `lp_work_time`;

-- 计算覆盖一下 任职日期和现任职务始任日期
UPDATE  cadre_post cp,
(
SELECT p.cadre_id, p.id AS p_id, np.*, lp.* FROM  cadre c left join cadre_post p ON p.cadre_id=c.id
left join
(select * from (select distinct dcr.relate_id as np_relate_id, d.id as np_id, d.file_name as np_file_name, d.file as np_file, d.work_time as np_work_time  from dispatch_cadre_relate dcr,
dispatch_cadre dc ,dispatch d where dcr.relate_type=2 and dc.id=dcr.dispatch_cadre_id and d.id=dc.dispatch_id order by d.work_time asc)t group by np_relate_id) np  on np.np_relate_id=p.id

left join
(select * from (select distinct dcr.relate_id as lp_relate_id, d.id as lp_id, d.file_name as lp_file_name, d.file as lp_file, d.work_time as lp_work_time  from dispatch_cadre_relate dcr,
dispatch_cadre dc ,dispatch d where dcr.relate_type=2 and dc.id=dcr.dispatch_cadre_id and d.id=dc.dispatch_id order by d.work_time desc)t group by lp_relate_id) lp on lp.lp_relate_id=p.id
) tmp SET cp.lp_dispatch_id=tmp.lp_id, cp.lp_work_time=tmp.lp_work_time,
cp.np_dispatch_id=tmp.np_id, cp.np_work_time=tmp.np_work_time
WHERE cp.id=tmp.p_id;


ALTER TABLE `cadre_admin_level`
	CHANGE COLUMN `admin_level` `admin_level` INT(10) UNSIGNED NOT NULL COMMENT '行政级别' AFTER `cadre_id`,
	ADD COLUMN `s_work_time` INT(10) UNSIGNED NULL COMMENT '职级始任日期' AFTER `admin_level`,
	ADD COLUMN `e_work_time` INT(10) UNSIGNED NULL COMMENT '职级结束日期' AFTER `s_work_time`,
	ADD COLUMN `s_post` INT(10) UNSIGNED NULL COMMENT '职级始任职务' AFTER `e_work_time`;

ALTER TABLE `cadre_admin_level`
	ADD COLUMN `s_dispatch_id` INT(10) UNSIGNED NULL COMMENT '关联始任任免文件' AFTER `admin_level`,
	ADD COLUMN `e_dispatch_id` INT(10) UNSIGNED NULL COMMENT '关联结束任免文件' AFTER `s_work_time`,
	CHANGE COLUMN `s_post` `s_post` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '职级始任职务，以上5个字段在选择关联干部任免文件时被覆盖' AFTER `e_work_time`,
	CHANGE COLUMN `start_dispatch_cadre_id` `start_dispatch_cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联始任干部任免文件' AFTER `s_post`,
	CHANGE COLUMN `end_dispatch_cadre_id` `end_dispatch_cadre_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联结束干部任免文件' AFTER `start_dispatch_cadre_id`;

ALTER TABLE `cadre_admin_level`
	CHANGE COLUMN `s_work_time` `s_work_time` DATE NULL DEFAULT NULL COMMENT '职级始任日期' AFTER `s_dispatch_id`,
	CHANGE COLUMN `e_work_time` `e_work_time` DATE NULL DEFAULT NULL COMMENT '职级结束日期' AFTER `e_dispatch_id`,
    CHANGE COLUMN `s_post` `s_post` VARCHAR(200) NULL DEFAULT NULL COMMENT '职级始任职务，以上5个字段在选择关联干部任免文件时被覆盖' AFTER `e_work_time`;

-- 计算覆盖一下 职级始任日期、职级结束日期、职级始任职务
UPDATE cadre_admin_level cal,
(
SELECT cal.id AS admin_level_id, cal.cadre_id, cal.admin_level , sdc.post AS s_post, sdc.dispatch_id as s_dispatch_id ,
sd.work_time as s_work_time, edc.dispatch_id as e_dispatch_id,
if(isnull(ed.work_time), null,ed.work_time) as e_work_time  from cadre_admin_level cal
left join dispatch_cadre sdc on sdc.id=cal.start_dispatch_cadre_id
left join dispatch sd on sd.id=sdc.dispatch_id
left join dispatch_cadre edc on edc.id=cal.end_dispatch_cadre_id
left join dispatch ed on ed.id=edc.dispatch_id
) tmp SET cal.s_post=tmp.s_post, cal.s_dispatch_id=tmp.s_dispatch_id, cal.s_work_time=tmp.s_work_time, cal.e_dispatch_id=tmp.e_dispatch_id, cal.e_work_time=tmp.e_work_time
WHERE cal.id=tmp.admin_level_id;

------------------ 更新 cadre_view 等

-- 修改 mc_cadre_work_type : 是否挂职
UPDATE base_meta_class SET bool_attr='是否挂职' WHERE CODE='mc_cadre_work_type';
update base_meta_type set bool_attr=1 where code='mt_gz';


-- 更新是否有干部挂职情况
update cadre set has_crp=0;
UPDATE cadre c,
(
SELECT c.id, ct.num AS work_num, crp.num AS crp_num FROM cadre c
LEFT JOIN (
SELECT cadre_id, COUNT(*) AS num FROM cadre_work cw, base_meta_type wt WHERE cw.work_type=wt.id AND wt.bool_attr=1 and cw.`status`=0 GROUP BY cadre_id
) ct ON ct.cadre_id=c.id
LEFT JOIN (
SELECT user_id, COUNT(*) num FROM crp_record WHERE user_id IS NOT null GROUP BY user_id
) crp ON crp.user_id=c.user_id
WHERE ct.num>0 OR crp.num>0) ct SET c.has_crp=1 WHERE c.id=ct.id;

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`)
VALUES ('统计是否有挂职经历', '', 'job.cadre.RefreshHasCrp', '0 0/1 * * * ?', 1, 0, 24, '2019-07-22 23:17:42');

-- 更新 unit_post_view （cadre_is_principal_post -> cadre_is_principal)
DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id,
u.status as unit_status, u.sort_order as unit_sort_order,
cp.cadre_id, cp.id as cadre_post_id, cp.admin_level as cp_admin_level, cp.is_main_post,
cv.gender, cv.admin_level as cadre_admin_level, cv.post_type as cadre_post_type,
cv.is_principal as cadre_is_principal, cv.cadre_post_year, cv.admin_level_year from unit_post up
left join unit u on up.unit_id=u.id
left join cadre_post cp on up.id=cp.unit_post_id
left join cadre_view cv on cv.id=cp.cadre_id;

-- 更新 sc_committee_member_view
DROP VIEW IF EXISTS `sc_committee_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_member_view` AS
select distinct scm.*, uv.username, uv.code, uv.realname, c.id as cadre_id, c.title, c.post from sc_committee_member scm
left join sys_user_view uv on uv.id=scm.user_id
left join cadre_view c on c.user_id=scm.user_id;

20190720

-- 任职情况：修改职务为岗位名称，新增字段职务（同步干部的职务字段），是否正职，是否第一主职
-- post->post_name
ALTER TABLE `cadre_post`
	CHANGE COLUMN `post` `post_name` VARCHAR(100) NULL DEFAULT NULL COMMENT '岗位名称' AFTER `unit_post_id`,
	ADD COLUMN `post` VARCHAR(100) NULL DEFAULT NULL COMMENT '职务' AFTER `post_name`,
	CHANGE COLUMN `is_main_post` `is_main_post` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否主职，否则兼职；主职和兼职均可以有多个' AFTER `double_unit_ids`,
	ADD COLUMN `is_first_main_post` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否第一主职，第一主职只有一个，兼职忽略此字段' AFTER `is_main_post`,
    ADD COLUMN `is_principal` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否正职' AFTER `admin_level`;

-- 把当前的主职设置为第一主职
UPDATE cadre_post SET is_first_main_post=1 WHERE is_main_post=1;
-- 同步干部的职务为第一主职的职务
UPDATE cadre_post cp, cadre c SET cp.post=c.post WHERE cp.is_first_main_post=1 AND cp.cadre_id=c.id;

-- 更新已有的cadre_post的is_principal字段
UPDATE cadre_post cp, base_meta_type bmt SET cp.is_principal=bmt.bool_attr WHERE cp.post_type=bmt.id;

-- SELECT realname FROM cadre_view WHERE id NOT IN(SELECT cadre_id FROM cadre_post WHERE is_main_post=1)
-- 如果干部的第一主职不存在，则从干部库中同步一份过去（行政级别、职务属性、所在单位、职务）
INSERT INTO cadre_post(cadre_id, post_name, admin_level, post_type, post, unit_id, is_principal, is_main_post, is_first_main_post, sort_order)
SELECT c.id as cadre_id, title AS post_name, admin_level, post_type, post, unit_id, pt.bool_attr AS is_principal, 1 as is_main_post, 1 AS is_first_main_post, 999 AS sort_order
FROM cadre_view c LEFT JOIN base_meta_type pt ON c.post_type=pt.id  WHERE c.id NOT IN(SELECT cadre_id FROM cadre_post WHERE is_first_main_post=1);

-- 查找岗位和干部属性不一样的名单
SELECT tmp.realname AS 姓名, tmp.code AS 工号, if(tmp.is_main_post=1, '主职', '兼职') AS 是否主职, t1.name AS 行政级别（干部）, t2.name AS 行政级别（岗位）, t3.name AS 职务属性（干部）, t4.name AS 职务属性（岗位）
, u1.name AS 所在单位（干部）, u2.name AS 所在单位（岗位） FROM
(
SELECT u.user_id, u.realname, cp.is_main_post, u.code, c.status, c.sort_order,
c.admin_level, c.post_type, c.unit_id, cp.is_first_main_post,
cp.admin_level AS _admin_level, cp.post_type AS _post_type, cp.unit_id AS _unit_id
FROM cadre_post cp, cadre c, sys_user_view u
WHERE cp.cadre_id=c.id AND c.user_id=u.id and (cp.admin_level!=c.admin_level OR cp.post_type!=c.post_type OR cp.unit_id!=c.unit_id)
)tmp
LEFT join base_meta_type t1 ON t1.id= tmp.admin_level
LEFT join base_meta_type t2 ON t2.id= tmp._admin_level
LEFT join base_meta_type t3 ON t3.id= tmp.post_type
LEFT join base_meta_type t4 ON t4.id= tmp._post_type
LEFT join unit u1 ON u1.id= tmp.unit_id
LEFT join unit u2 ON u2.id= tmp._unit_id
ORDER BY tmp.status DESC, tmp.sort_order desc;


-- 如果任职情况关联了岗位，则以岗位的属性为准（职务属性、所在单位、岗位名称、是否正职、职务类别）
update cadre_post cp, unit_post up SET cp.post_type=up.post_type, cp.unit_id=up.unit_id,
cp.post_name=up.name, cp.is_principal=up.is_principal_post, cp.post_class_id=up.post_class
WHERE up.id=cp.unit_post_id;

-- 准备删除干部的属性：行政级别、职务属性、所在单位、职务
ALTER TABLE `cadre`
	DROP COLUMN `admin_level`,
	DROP COLUMN `post_type`,
	DROP COLUMN `unit_id`,
	DROP COLUMN `post`,
	DROP INDEX `FK_base_cadre_base_meta_type`,
	DROP INDEX `FK_base_cadre_base_meta_type_2`,
	DROP INDEX `FK_base_cadre_base_unit`,
	DROP FOREIGN KEY `FK_base_cadre_base_meta_type`,
	DROP FOREIGN KEY `FK_base_cadre_base_meta_type_2`,
	DROP FOREIGN KEY `FK_base_cadre_base_unit`;

-- 删除元数据“职数属性”的是否正职、代码关联、所属班子成员，西交大等删除前应该先同步一下主职信息
UPDATE base_meta_class SET bool_attr=NULL, extra_attr=NULL, extra_options=null WHERE CODE='mc_post';
UPDATE base_meta_type mt, base_meta_class mc SET mt.bool_attr=NULL, mt.extra_attr=null  WHERE mc.CODE='mc_post' AND mt.class_id=mc.id;

ALTER TABLE `unit_post`
	CHANGE COLUMN `is_principal_post` `is_principal` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否正职' AFTER `job`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('useCadreState', '启用干部人员类别[M]', 'true', 3, 35, '');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('proPostTimeToDay', '专业技术职务评定时间是否精确到日', 'false', 3, 36, '');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('memberOutTypeRemark', '组织关系转出类型说明', '“京内”指党组织隶属北京市委的单位，如北京市各级政府、企事业单位党委，在京高等院校党委等。<br/>“京外”指党组织隶属关系不在北京市委的，如京外企事业单位党委、在京的中央直属企事业单位（央企、各大部委等）。', 1, 37, '');


INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`)
VALUES ('清理已过期干部信息修改权限', '清理已过期干部信息修改权限的规则', 'job.modify.ClearExpireAuth', '0 0 0/6 * * ?', 1, 0, 23, '2019-07-20 09:26:30');


更新 导入excel模板文件

更新 common-utils



20190718 更新北邮、南航

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`,
                            `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1071, 0, '更改干部工号', '', 'function',
                                    '', NULL, 88, '0/1/88/', 1, 'cadre:changeCode', NULL, NULL, NULL, 1, NULL);


20190718 更新北邮、南航

update cadre_family set sort_order=id WHERE sort_order is null and status=0;

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`,
                            `role_count`, `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                             VALUES (1070, 0, '添加离任干部账号', '', 'function', '', NULL, 80, '0/1/339/82/80/',
                                     1, 'dispatchCadre:addLeaveCadre', NULL, NULL, NULL, 1, NULL);



20190717 更新北邮，南航
UPDATE sys_student_info si, sys_user u SET si.sync_source=u.type  WHERE si.sync_source IS NULL AND si.user_id=u.id;

20190717 更新北邮、南航

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1069, 0, '更换学工号', '', 'function', '', NULL, 107, '0/1/105/107/', 1,
                                    'member:changeCode', NULL, NULL, NULL, 1, NULL);


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`, `parent_ids`,
                            `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1066, 0, '同步学校账号信息', '', 'function', '', NULL, 853,
                                                               '0/1/21/853/', 1, 'sysSync:user', NULL, NULL, NULL, 1, NULL);

UPDATE sys_resource SET permission='abroad:menu' WHERE permission='abroad:admin';

-- 因私管理员权限，调整干部权限
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1067, 0, '因私管理员权限', '', 'function', '', NULL, 284, '0/1/284/', 1, 'abroad:admin', NULL, NULL, NULL, 1, NULL);

UPDATE sys_resource SET permission='cla:menu' WHERE permission='cla:admin';
-- 请假管理员，调整干部权限
INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1068, 0, '请假管理员', '', 'function', '', NULL, 805, '0/1/805/', 1, 'cla:admin', NULL, NULL, NULL, 1, NULL);



20190716


20190716 更新北邮，南航

INSERT INTO `sys_resource` (`is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (0, '删除党员', '', 'function', '', NULL, 107, '0/1/105/107/', 1, 'member:del', NULL, NULL, NULL, 1, NULL);


20190714 更新南航

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('statPartyMemberCount', '统计党委的党员数', '20', 2, 34, '统计前多少个党派的党员人数');


20190714
更新 北航，南航，北邮

20190713

更新 南航，北邮

20190712

-- 学历新增 zz|学生|中专
UPDATE `base_meta_class` SET `extra_options`='zz|学生|中专,zk|学生|专科,bk|学生|本科,yjs|硕士研究生|研究生,yjskcb|硕士研究生（研究生课程班）|研究生,sstd|硕士研究生（硕士同等学历）|研究生,bs|博士研究生|博士研究生' WHERE  `id`=27;
INSERT INTO `base_meta_type` (`class_id`, `name`, `code`, `bool_attr`, `extra_attr`, `remark`, `sort_order`, `available`)
VALUES (27, '中专', 'mt_edu_zz', NULL, 'zz', '', 46, 1);

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
 VALUES ('evaYears', '显示几年考核结果', '3', 2, 34, '');


INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`,
                            `parent_id`, `parent_ids`, `is_leaf`, `permission`, `role_count`,
                            `count_cache_keys`, `count_cache_roles`, `available`, `sort_order`)
                            VALUES (1063, 0, '添加', '', 'function', '', NULL, 182, '0/1/260/182/',
                                    1, 'branch:add', NULL, NULL, NULL, 1, NULL);

INSERT INTO `sys_resource` (`id`, `is_mobile`, `name`, `remark`, `type`, `menu_css`, `url`, `parent_id`,
                            `parent_ids`, `is_leaf`, `permission`, `role_count`, `count_cache_keys`, `count_cache_roles`,
                            `available`, `sort_order`) VALUES (1064, 0, '导入', '', 'function', '', NULL, 182, '0/1/260/182/',
                                                               1, 'branch:import', NULL, NULL, NULL, 1, NULL);
UPDATE `sys_resource` SET `name`='修改' WHERE  `id`=193;
delete FROM sys_resource WHERE permission = 'orgAdmin:*' ;

-- 为 branch:import（党建） branch:add（分党委、党建管理员）， branch:edit（支部、分党委、党建管理员） 添加角色
-- 给支部 增加 支部管理 的权限

-- 给西交大新增ct_passportDraw_return_admin
INSERT INTO `base_content_tpl` (`name`, `remark`, `role_id`, `type`, `code`, `content`, `content_type`,
                                `engine`, `param_count`, `param_names`, `param_def_values`,
                                `sort_order`, `user_id`, `create_time`, `update_time`)
                                VALUES ('证件未归还给管理员发短信', '第七天给管理员发短信', NULL,
                                        1, 'ct_passportDraw_return_admin', '{0}，您好！{1}{2}于{3}领取的{4}应交回时间为{5}，目前还未交回组织部。[系统短信，请勿回复]', 1, 1, NULL, NULL, NULL, 60, NULL, NULL, '2019-07-13 08:58:25');


20190708 北邮，西交，南航

DROP VIEW IF EXISTS `cadre_company_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cadre_company_view` AS
select cc.*, c.code, c.realname, c.title, c.`status` as cadre_status, c.admin_level, c.admin_level_code,
c.is_double, c.unit_type_id, c.unit_type_group,
c.sort_order as cadre_sort_order from cadre_company cc
left join cadre_view c on c.id=cc.cadre_id;

20190708
西交大，南航，北邮

ALTER TABLE `abroad_passport_apply`
	ADD COLUMN `op_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '操作人，当审批人书写人名时，需要记录操作人' AFTER `approve_time`;
DROP VIEW IF EXISTS `abroad_passport_apply_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `abroad_passport_apply_view` AS
select apa.`*` , ap.id as passport_id, ap.code from abroad_passport_apply apa  left join abroad_passport ap on ap.apply_id=apa.id ;

UPDATE abroad_passport_apply SET op_user_id = user_id;

-- 更新导入样表

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

