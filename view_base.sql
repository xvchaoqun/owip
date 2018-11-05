
-- ----------------------------
--  View definition for `sys_user_view`
-- ----------------------------
DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;
-- ----------------------------
--  View definition for `cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `cadre_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `cadre_view` AS
SELECT c.*
	,if(!isnull(pcm.id), 1, 0) as is_committee_member
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
	,`om`.`party_id` AS `party_id`
	,`om`.`branch_id` AS `branch_id`
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
	, if(om.status=1 or om.status=4, om.grow_time, ow.grow_time) as ow_grow_time
	, ow.remark as ow_remark
	,`max_ce`.`edu_id` AS `edu_id`
	,`max_ce`.`finish_time` AS `finish_time`
	,`max_ce`.`learn_style` AS `learn_style`
	,`max_ce`.`school` AS `school`
	,`max_ce`.`dep` AS `dep`
	,`max_ce`.`school_type` AS `school_type`
	,`max_ce`.`major` AS `major`
	,`max_degree`.`degree` AS `degree`
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
	,if(isnull(_vwt.verify_work_time), t.work_time, _vwt.verify_work_time) as work_time
	,`t`.`work_start_time` AS `work_start_time`
	,t.talent_title
	,main_cadre_post.id as main_cadre_post_id
	,main_cadre_post.is_double
	,main_cadre_post.double_unit_ids
	,main_cadre_post_type.bool_attr as is_principal_post
	,TIMESTAMPDIFF(YEAR,np_work_time,now()) as cadre_post_year
	,TIMESTAMPDIFF(YEAR,s_work_time,e_work_time) as admin_level_year
	,np.* ,lp.*, nl.*
	,admin_level.code as admin_level_code
   ,admin_level.name as admin_level_name
   ,max_ce_edu.extra_attr as max_ce_edu_attr
   ,max_ce_edu.code as max_ce_edu_code
   ,max_ce_edu.name as max_ce_edu_name
   ,u.name as unit_name
   ,u.type_id as unit_type_id
   ,unit_type.code as unit_type_code
   ,unit_type.name as unit_type_name
   ,unit_type.extra_attr as unit_type_attr
   ,_va.verify_birth as verify_birth
   ,_vwt.verify_work_time as verify_work_time
FROM  cadre c
left join (select pcm.* from pcs_committee_member pcm, base_meta_type pc_post where pcm.is_quit=0 and pcm.type=0 and pcm.post=pc_post.id and pc_post.bool_attr=1) as pcm on pcm.user_id=c.user_id
left join cadre_party dp on dp.user_id= c.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= c.user_id and ow.type = 2
LEFT JOIN `sys_user_view` `uv` ON `uv`.`user_id` = `c`.`user_id`
LEFT JOIN `sys_teacher_info` `t` ON `t`.`user_id` = `c`.`user_id`
LEFT JOIN `ow_member` `om` ON `om`.`user_id` = `c`.`user_id`
LEFT JOIN `cadre_edu` `max_ce` ON  `max_ce`.`cadre_id` = `c`.`id` AND `max_ce`.`is_high_edu` = 1 and max_ce.status=0
LEFT JOIN `cadre_edu` `max_degree` ON `max_degree`.`cadre_id` = `c`.`id` AND `max_degree`.`is_high_degree` = 1 and max_degree.status=0
left join cadre_post main_cadre_post on(main_cadre_post.cadre_id=c.id and main_cadre_post.is_main_post=1)
left join base_meta_type main_cadre_post_type on(main_cadre_post_type.id=main_cadre_post.post_id)
left join base_meta_type admin_level on(c.type_id=admin_level.id)
left join base_meta_type max_ce_edu on(max_ce.edu_id=max_ce_edu.id)
left join unit u on(c.unit_id=u.id)
left join base_meta_type unit_type on(u.type_id=unit_type.id)
left join
(select * from (select distinct dcr.relate_id as np_relate_id, d.id as np_id, d.file_name as np_file_name, d.file as np_file, d.work_time as np_work_time  from dispatch_cadre_relate dcr,
dispatch_cadre dc ,dispatch d where dcr.relate_type=2 and dc.id=dcr.dispatch_cadre_id and d.id=dc.dispatch_id order by d.work_time asc)t group by np_relate_id) np  on np.np_relate_id=main_cadre_post.id
left join
(select * from (select distinct dcr.relate_id as lp_relate_id, d.id as lp_id, d.file_name as lp_file_name, d.file as lp_file, d.work_time as lp_work_time  from dispatch_cadre_relate dcr,
dispatch_cadre dc ,dispatch d where dcr.relate_type=2 and dc.id=dcr.dispatch_cadre_id and d.id=dc.dispatch_id order by d.work_time desc)t group by lp_relate_id) lp on lp.lp_relate_id=main_cadre_post.id
left join
(select cal.cadre_id, cal.admin_level_id , sdc.dispatch_id as s_dispatch_id ,
sd.work_time as s_work_time, edc.dispatch_id as e_dispatch_id,
if(isnull(ed.work_time),now(),ed.work_time) as e_work_time  from cadre_admin_level cal
left join dispatch_cadre sdc on sdc.id=cal.start_dispatch_cadre_id
left join dispatch sd on sd.id=sdc.dispatch_id
left join dispatch_cadre edc on edc.id=cal.end_dispatch_cadre_id
left join dispatch ed on ed.id=edc.dispatch_id) nl on nl.cadre_id=c.id and nl.admin_level_id=main_cadre_post.admin_level_id
left join (select cadre_id, verify_birth from verify_age where status=0) _va on _va.cadre_id=c.id
left join (select cadre_id, verify_work_time from verify_work_time where status=0) _vwt on _vwt.cadre_id=c.id;

-- ----------------------------
--  View definition for `ow_party_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_view` AS
select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(*) as num, party_id from ow_branch where is_deleted=0 group by party_id) btmp on btmp.party_id=p.id
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from ow_member where  status=1 group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, party_id from ow_member_teacher where status=1 group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id;

-- ----------------------------
--  View definition for `ow_branch_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_branch_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_branch_view` AS
select b.*,  mtmp.num as member_count, mtmp.positive_count, mtmp.s_num as student_member_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, gtmp.num as group_count, gtmp2.num as present_group_count from ow_branch b
left join (select  sum(if(political_status=2, 1, 0)) as positive_count, sum(if(type=2, 1, 0)) as s_num, count(*) as num,  branch_id from ow_member where  status=1 group by branch_id) mtmp on mtmp.branch_id=b.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, branch_id from ow_member_teacher where status=1 group by branch_id) mtmp2 on mtmp2.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 group by branch_id) gtmp on gtmp.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 and is_present=1 group by branch_id) gtmp2 on gtmp2.branch_id=b.id;
-- ----------------------------
--  View definition for `ow_member_abroad_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_abroad_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_abroad_view` AS
select ea.`*`, m.user_id, u.realname, u.code, u.gender, m.party_id, m.branch_id
from ext_abroad ea , sys_user_view u, ow_member m where ea.gzzh=u.code and u.id=m.user_id ;

DROP VIEW IF EXISTS `unit_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_view` AS
select u.*, up.principal_post_count, up.vice_post_count, cpc.main_count, cpc.vice_count, cpc.none_count from unit u left join
(select unit_Id, sum(if(is_principal_post,1,0)) as principal_post_count, sum(if(is_principal_post,0,1)) as vice_post_count from unit_post group by unit_id) up on up.unit_id=u.id
left join
(select ca.unit_id, sum(if(bmt.code='mt_admin_level_main', num,0)) as main_count,
sum(if(bmt.code='mt_admin_level_vice', num,0)) as vice_count,
sum(if(bmt.code='mt_admin_level_none', num,0)) as none_count
from cpc_allocation ca , base_meta_type bmt where ca.admin_level_id=bmt.id group by ca.unit_id) cpc on cpc.unit_id=u.id;

DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id, u.sort_order as unit_sort_order
from unit_post up left join unit u on up.unit_id=u.id ;

DROP VIEW IF EXISTS `cadre_leader_unit_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cadre_leader_unit_view` AS
select clu.*, cl.cadre_id, cl.sort_order as leader_sort_order, u.sort_order as unit_sort_order from cadre_leader_unit clu
left join cadre_leader cl on clu.leader_id=cl.id
left join unit u on clu.unit_id=u.id ;