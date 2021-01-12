DROP VIEW IF EXISTS `cadre_view`;
CREATE ALGORITHM = UNDEFINED  VIEW `cadre_view` AS
SELECT c.*,
	main_cadre_post.unit_id, main_cadre_post.admin_level, main_cadre_post.post_type, main_cadre_post.post
	,if(!isnull(cm.id), 1, 0) as is_committee_member
	,`uv`.`msg_title` AS `msg_title`
	,`uv`.`mobile` AS `mobile`
	,`uv`.`phone` AS `phone`
	,`uv`.`home_phone` AS `home_phone`
	,`uv`.`email` AS `email`
	,`uv`.`code` AS `code`
	,`uv`.`realname` AS `realname`
	,`uv`.`gender` AS `gender`
	,`uv`.`nation` AS `nation`
	,`uv`.`native_place` AS `native_place`
	,`uv`.`idcard` AS `idcard`
	,if(isnull(_va.verify_birth),`uv`.`birth`,_va.verify_birth) AS `birth`
	,if(om.status=1, `om`.`party_id`, null) AS `party_id`
	,if(om.status=1, `om`.`branch_id`, null) AS `branch_id`
	,`om`.`status` AS `member_status`
	, dp.id as dp_id
	, dp.class_id as dp_type_id
	, dp.post as dp_post
	, dp.grow_time as dp_grow_time
	, dp.remark as dp_remark
	, ow.id as ow_id
	-- 判断是否是中共党员
	, if(!isnull(ow.id) or om.status=1 or om.status=4, 1, 0) as is_ow
	-- 优先以党员库中的入党时间为准
     ,if(isnull(_vgt.verify_grow_time), if(om.status=1 or om.status=4, om.grow_time, ow.grow_time), _vgt.verify_grow_time) as ow_grow_time
     ,DATE_ADD(if(isnull(_vgt.verify_grow_time), if(om.status=1 or om.status=4, om.grow_time, ow.grow_time), _vgt.verify_grow_time), INTERVAL 1 YEAR) as ow_positive_time
	, ow.remark as ow_remark
	,`max_ce`.`edu_id` AS `edu_id`
	,`max_ce`.`finish_time` AS `finish_time`
	,`max_ce`.`learn_style` AS `learn_style`
	,`max_ce`.`school` AS `school`
	,`max_ce`.`dep` AS `dep`
	,`max_ce`.`school_type` AS `school_type`
	,`max_ce`.`major` AS `major`
	,`max_degree`.`degree_type` AS `degree_type`
	,`max_degree`.`degree` AS `degree`
     , t.authorized_type
     ,t.staff_type
	,`t`.`post_class` AS `post_class`
	,`t`.`sub_post_class` AS `sub_post_class`
	,`t`.`main_post_level` AS `main_post_level`
	,`t`.`pro_post_time` AS `pro_post_time`
	,`t`.`pro_post_level` AS `pro_post_level`
	,`t`.`pro_post_level_time` AS `pro_post_level_time`
	,`t`.`pro_post` AS `pro_post`
	,`t`.`manage_level` AS `manage_level`
	,`t`.`manage_level_time` AS `manage_level_time`
	,`t`.`arrive_time` AS `arrive_time`
    , t.staff_status
    , t.is_temp
	,if(isnull(_vwt.verify_work_time), t.work_time, _vwt.verify_work_time) as work_time
	,`t`.`work_start_time` AS `work_start_time`
	,t.talent_title
	,main_cadre_post.id as main_cadre_post_id
	,main_cadre_post.unit_post_id
	,main_cadre_post.is_principal
	,main_cadre_post.lp_dispatch_id
	,main_cadre_post.lp_work_time
	,main_cadre_post.np_dispatch_id
	,main_cadre_post.np_work_time
	-- 是否班子负责人
	,up.leader_type
	,TIMESTAMPDIFF(YEAR,np_work_time,now()) as cadre_post_year
	,TIMESTAMPDIFF(YEAR,s_work_time,if(isnull(e_work_time), now(), e_work_time)) as admin_level_year
	, cal.s_dispatch_id, cal.s_work_time, cal.e_dispatch_id, cal.e_work_time
	,admin_level.code as admin_level_code
   ,admin_level.name as admin_level_name
   ,max_ce_edu.extra_attr as max_ce_edu_attr
   ,max_ce_edu.code as max_ce_edu_code
   ,max_ce_edu.name as max_ce_edu_name
   ,u.name as unit_name
   ,u.type_id as unit_type_id
   ,unit_type.code as unit_type_code
   ,unit_type.name as unit_type_name
   ,unit_type.extra_attr as unit_type_group
   ,_va.verify_birth as verify_birth
   ,_vwt.verify_work_time as verify_work_time
FROM  cadre c
left join cm_member cm on cm.is_quit=0 and cm.type=3 and cm.user_id=c.user_id
left join cadre_party dp on dp.user_id= c.user_id and dp.type = 1 and dp.is_first=1
left join cadre_party ow on ow.user_id= c.user_id and ow.type = 2
LEFT JOIN `sys_user_view` `uv` ON `uv`.`user_id` = `c`.`user_id`
LEFT JOIN `sys_teacher_info` `t` ON `t`.`user_id` = `c`.`user_id`
LEFT JOIN `ow_member` `om` ON `om`.`user_id` = `c`.`user_id`
LEFT JOIN `cadre_edu` `max_ce` ON  `max_ce`.`cadre_id` = `c`.`id` AND `max_ce`.`is_high_edu` = 1 and max_ce.is_second_degree=0 and max_ce.status=0
LEFT JOIN `cadre_edu` `max_degree` ON `max_degree`.`cadre_id` = `c`.`id` AND `max_degree`.`is_high_degree` = 1 and max_degree.is_second_degree=0 and max_degree.status=0
left join cadre_post main_cadre_post on(main_cadre_post.cadre_id=c.id and main_cadre_post.is_first_main_post=1)
left join unit_post up on up.id=main_cadre_post.unit_post_id
left join base_meta_type admin_level on(main_cadre_post.admin_level=admin_level.id)
left join base_meta_type max_ce_edu on(max_ce.edu_id=max_ce_edu.id)
left join unit u on(main_cadre_post.unit_id=u.id)
left join base_meta_type unit_type on(u.type_id=unit_type.id)
left join cadre_admin_level cal on cal.cadre_id=c.id and cal.admin_level=main_cadre_post.admin_level
left join (select cadre_id, verify_birth from verify_age where status=0) _va on _va.cadre_id=c.id
left join (select cadre_id, verify_work_time from verify_work_time where status=0) _vwt on _vwt.cadre_id=c.id
left join (select cadre_id, verify_grow_time from verify_grow_time where status=0) _vgt on _vgt.cadre_id=c.id;


-- ----------------------------
--  View definition for `cadre_inspect_view`
-- ----------------------------
DROP VIEW IF EXISTS `cadre_inspect_view`;
CREATE ALGORITHM=UNDEFINED VIEW `cadre_inspect_view` AS
select ci.id as inspect_id, ci.record_id, ci.assign_unit_post_id, ci.`type` as inspect_type, ci.`status` as inspect_status,
ci.remark as inspect_remark, ci.record_user_id,ci.valid_time as valid_time, ci.sort_order as inspect_sort_order, cv.*
from cadre_inspect ci left join cadre_view cv on ci.cadre_id=cv.id;


DROP VIEW IF EXISTS `cadre_reserve_view`;
CREATE ALGORITHM=UNDEFINED VIEW `cadre_reserve_view` AS
select cr.id as reserve_id, cr.`type` as reserve_type, cr.post_time as reserve_post_time, cr.`status` as reserve_status,
cr.remark as reserve_remark, cr.sort_order as reserve_sort_order, u.username, cv.*
from cadre_reserve cr left join cadre_view cv on cr.cadre_id=cv.id left join sys_user u on u.id=cv.user_id ;

