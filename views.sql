
-- (分党委、党总支)排除了pcs_exclude_branch里头的支部之后的分党委表
DROP VIEW IF EXISTS `pcs_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_party_view` AS select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(ob.id) as num, ob.party_id from ow_branch ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null group by ob.party_id) btmp on btmp.party_id=p.id
left join (select sum(if(om.type=2, 1, 0)) as s_num, sum(if(om.political_status=2, 1, 0)) as positive_count, count(om.user_id) as num,  om.party_id from ow_member om
left join pcs_exclude_branch peb on peb.party_id=om.party_id and peb.branch_id=om.branch_id
where  om.status=1 and peb.id is null group by party_id) mtmp on mtmp.party_id=p.id
left join (select sum(if(omt.is_retire=0, 1, 0)) as t_num, sum(if(omt.is_retire=1, 1, 0)) as t2_num,
count(omt.user_id) as num, omt.party_id from ow_member_teacher omt
left join pcs_exclude_branch peb on peb.party_id=omt.party_id and peb.branch_id=omt.branch_id
where status=1 and peb.id is null group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id ;

DROP VIEW IF EXISTS `pcs_pr_candidate_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_pr_candidate_view` AS
SELECT pc.*,
uv.code, uv.realname, if(c.status=6, c.sort_order, -1)  as leader_sort_order, if(isnull(c.id), if(isnull(omt.user_id), 3 , 2), 1) as user_type, c.edu_id as edu_id, c.post,
  ifnull(omt.grow_time, oms.grow_time) as grow_time,  omt.work_time, omt.pro_post, omt.education, omt.is_retire, oms.edu_level,
ppr.party_id, ppr.config_id, ppr.stage, op.sort_order as party_sort_order
from pcs_pr_candidate pc
left join sys_user_view uv on uv.id=pc.user_id
left join cadre_view c on c.user_id = pc.user_id and c.status in(1, 6)
left join ow_member_teacher omt on omt.user_id = pc.user_id
left join ow_member_student oms on oms.user_id = pc.user_id
, pcs_pr_recommend ppr
left join ow_party op on op.id = ppr.party_id
where pc.recommend_id=ppr.id;


DROP VIEW IF EXISTS `pcs_branch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_branch_view` AS
select  ob.party_id, ob.id as branch_id, ob.name, ob.member_count from ow_branch_view ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null
union all
select  op.id as party_id, null as branch_id, op.name, op.member_count from ow_party_view op, base_meta_type bmt
where op.is_deleted=0 and op.class_id=bmt.id and bmt.code='mt_direct_branch' order by member_count desc;

CREATE ALGORITHM = UNDEFINED VIEW `pcs_branch_view2` AS
select pbv.*, p.sort_order as party_sort_order, p.name as party_name from pcs_branch_view pbv left join ow_party p on pbv.party_id = p.id ;

DROP VIEW IF EXISTS `pcs_candidate_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_candidate_view` AS
SELECT pc.*, omt.code, omt.realname, c.title, omt.ext_unit, omt.gender,
 omt.nation, omt.native_place, omt.birth, omt.grow_time, omt.work_time, omt.pro_post,
pr.party_id, pr.branch_id, pr.config_id, pr.stage
from pcs_candidate pc
left join ow_member_teacher omt on omt.user_id = pc.user_id
left join cadre c on c.user_id = pc.user_id and c.status in(1, 6)
, pcs_recommend pr where pc.recommend_id=pr.id;

--
DROP VIEW IF EXISTS `crs_applicant_adjust_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_applicant_adjust_view` AS
select caa.* from crs_applicant_adjust caa
        left join crs_post cp on (cp.meeting_status = 0 or cp.meeting_status is null)
        and find_in_set(cp.id , caa.after_post_ids) group by caa.id having count(cp.id)>0 ;

DROP VIEW IF EXISTS `crs_expert_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_expert_view` AS
select ce.*, uv.username, uv.code, uv.realname,
c.id as cadre_id, c.title as cadre_title, c.status as cadre_status, c.sort_order as cadre_sort_order
from crs_expert ce
left join cadre c on ce.user_id=c.user_id
left join sys_user_view uv on ce.user_id=uv.id;


DROP VIEW IF EXISTS `crs_applicant_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_applicant_view` AS
 select *, if(special_status||require_check_status=1, 1, 0) as is_require_check_pass from crs_applicant ;

DROP VIEW IF EXISTS `ow_member_out_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_out_view` AS
select mo.*, m.type as member_type, t.is_retire
from ow_member_out mo, ow_member m
left join sys_teacher_info t on t.user_id = m.user_id where mo.user_id=m.user_id;


-- ----------------------------
-- 2017.6.5 View definition for `ow_party_static_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_static_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_party_static_view` AS
select p.id, p.name,
s.bks, s.ss, s.bs, (s.bks+s.ss+s.bs) as student, s.positive_bks, s.positive_ss, s.positive_bs, (s.positive_bks + s.positive_ss + s.positive_bs) as positive_student,
t.teacher,t.teacher_retire, (t.teacher+t.teacher_retire) as teacher_total, t.positive_teacher, t.positive_teacher_retire, (t.positive_teacher + t.positive_teacher_retire)as positive_teacher_total,
b.bks_branch, b.ss_branch, b.bs_branch, b.sb_branch, b.bsb_branch,
(b.bks_branch + b.ss_branch + b.bs_branch + b.sb_branch + b.bsb_branch) as student_branch_total, b.teacher_branch, b.retire_branch, (b.teacher_branch + b.retire_branch) as teacher_branch_total
from ow_party p left join
(
select party_id,
sum(if(edu_level is null, 1, 0)) as bks,
sum(if(edu_level='硕士', 1, 0)) as ss,
sum(if(edu_level='博士', 1, 0)) as bs,
sum(if(edu_level is null and political_status=2, 1, 0)) as positive_bks,
sum(if(edu_level='硕士' and political_status=2, 1, 0)) as positive_ss,
sum(if(edu_level='博士' and political_status=2, 1, 0)) as positive_bs
from ow_member_student where status=1 group by party_id
) s on s.party_id = p.id
left join
(
select party_id,
sum(if(is_retire, 0, 1)) teacher,
sum(if(is_retire, 1, 0)) teacher_retire,
sum(if(!is_retire and political_status=2, 1, 0)) positive_teacher,
sum(if(is_retire and political_status=2, 1, 0)) positive_teacher_retire
from ow_member_teacher where status=1 group by party_id
) t on t.party_id = p.id
left join
(select b.party_id,
sum(if(locate('本科生',bmt.name), 1, 0)) as bks_branch,
sum(if(locate('硕士',bmt.name), 1, 0)) as ss_branch,
sum(if(locate('博士',bmt.name), 1, 0)) as bs_branch,
sum(if(POSITION('硕博' in bmt.name)=1, 1, 0)) as sb_branch,
sum(if(locate('本硕博',bmt.name), 1, 0)) as bsb_branch,
sum(if(locate('在职',bmt.name), 1, 0)) as teacher_branch,
sum(if(locate('离退休',bmt.name), 1, 0)) as retire_branch
from ow_branch b, base_meta_type bmt where b.is_deleted=0 and b.type_id=bmt.id group by b.party_id
)b on b.party_id = p.id

where p.is_deleted=0 order by p.sort_order desc;
-- ----------------------------
--  View definition for `abroad_passport_apply_view`
-- ----------------------------
DROP VIEW IF EXISTS `abroad_passport_apply_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `abroad_passport_apply_view` AS
select apa.`*` , ap.id as passport_id, ap.code from abroad_passport_apply apa  left join abroad_passport ap on ap.apply_id=apa.id ;
-- ----------------------------
--  View definition for `cadre_inspect_view`
-- ----------------------------
DROP VIEW IF EXISTS `cadre_inspect_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cadre_inspect_view` AS
select ci.id as inspect_id, ci.`type` as inspect_type, ci.`status` as inspect_status,
ci.remark as inspect_remark, ci.sort_order as inspect_sort_order, cv.*
from cadre_inspect ci left join cadre_view cv on ci.cadre_id=cv.id;


DROP VIEW IF EXISTS `cadre_reserve_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cadre_reserve_view` AS
select cr.id as reserve_id, cr.`type` as reserve_type, cr.`status` as reserve_status,
cr.remark as reserve_remark, cr.sort_order as reserve_sort_order, u.username, u.code,cv.*
from cadre_reserve cr left join cadre_view cv on cr.cadre_id=cv.id left join sys_user u on u.id=cv.user_id ;

-- ----------------------------
--  View definition for `cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `cadre_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `cadre_view` AS
SELECT c.*
	,`ui`.`msg_title` AS `msg_title`
	,`ui`.`mobile` AS `mobile`
	,`ui`.`phone` AS `phone`
	,`ui`.`home_phone` AS `home_phone`
	,`ui`.`email` AS `email`
	,`ui`.`realname` AS `realname`
	,`ui`.`gender` AS `gender`
	,`ui`.`nation` AS `nation`
	,`ui`.`native_place` AS `native_place`
	,`ui`.`idcard` AS `idcard`
	,if(isnull(_va.verify_birth),`ui`.`birth`,_va.verify_birth) AS `birth`
	,`om`.`party_id` AS `party_id`
	,`om`.`branch_id` AS `branch_id`
	,`om`.`status` AS `member_status`
	, dp.id as dp_id
	, dp.class_id as dp_type_id
	, dp.post as dp_post
	, dp.grow_time as dp_grow_time
	, dp.remark as dp_remark
	, ow.id as ow_id
	, ow.grow_time as ow_grow_time
	, ow.remark as ow_remark
	, if(isnull(dp.id), if(om.status=1 or om.status=4, om.grow_time, ow.grow_time), dp.grow_time) as cadre_grow_time
	, if(isnull(dp.id), if(!isnull(ow.id) or om.status=1 or om.status=4, 0, -1), dp.class_id) as cadre_dp_type
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
	,main_cadre_post.double_unit_id
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
left join cadre_party dp on dp.user_id= c.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= c.user_id and ow.type = 2
LEFT JOIN `sys_user_info` `ui` ON `ui`.`user_id` = `c`.`user_id`
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
(select cal.cadre_id, cal.admin_level_id , sdc.dispatch_id as s_dispatch_id , sd.work_time as s_work_time, edc.dispatch_id as e_dispatch_id, if(isnull(ed.work_time),now(),ed.work_time) as e_work_time  from cadre_admin_level cal
left join dispatch_cadre sdc on sdc.id=cal.start_dispatch_cadre_id
left join dispatch sd on sd.id=sdc.dispatch_id
left join dispatch_cadre edc on edc.id=cal.end_dispatch_cadre_id
left join dispatch ed on ed.id=edc.dispatch_id) nl on nl.cadre_id=c.id and nl.admin_level_id=main_cadre_post.admin_level_id
left join (select cadre_id, verify_birth from verify_age where status=0) _va on _va.cadre_id=c.id
left join (select cadre_id, verify_work_time from verify_work_time where status=0) _vwt on _vwt.cadre_id=c.id;
-- ----------------------------
--  View definition for `cis_inspector_view`
-- ----------------------------
DROP VIEW IF EXISTS `cis_inspector_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cis_inspector_view` AS
select ci.*, uv.username, uv.code, uv.realname
from cis_inspector ci left join sys_user_view uv on ci.user_id = uv.id;

-- ----------------------------
--  View definition for `cis_inspect_obj_view`
-- ----------------------------
DROP VIEW IF EXISTS `cis_inspect_obj_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cis_inspect_obj_view` AS
select cio.* from cis_inspect_obj cio, base_meta_type bmt
where cio.type_id=bmt.id order by cio.year desc, bmt.sort_order desc, cio.seq desc ;
-- ----------------------------
--  View definition for `dispatch_cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `dispatch_cadre_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dispatch_cadre_view` AS
select dc.*, d.year, d.dispatch_type_id, d.code , d.has_checked from dispatch_cadre dc, dispatch d, dispatch_type dt
    where dc.dispatch_id = d.id and  d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc, dc.type asc  ;

-- ----------------------------
--  View definition for `dispatch_view`
-- ----------------------------
DROP VIEW IF EXISTS `dispatch_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `dispatch_view` AS
select d.* from dispatch d, dispatch_type dt
    where d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc ;

-- ----------------------------
--  View definition for `ext_branch_view`
-- ----------------------------
DROP VIEW IF EXISTS `ext_branch_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_branch_view`
AS SELECT ob.code as branch_code, ob.name as branch_name,if(ob.is_staff, 1, 2) as branch_type, op.code as party_code,
op.name as party_name, u.code as dep_code, u.name as dep_name,
count1.member_count,tmp.code as sid, tmp.realname from ow_branch ob
left join
(select party_id, branch_id, count(om.user_id) as member_count from ow_member om where branch_id is not null group by party_id, branch_id) count1
on count1.branch_id=ob.id  left join (
select obm.user_id, obm.type_id, ob.id as branch_id, su.realname, su.code from
ow_branch_member obm, ow_branch_member_group obmg, ow_branch ob, base_meta_type bmt, sys_user_view su
 where obmg.is_present=1 and obm.group_id=obmg.id and obmg.branch_id=ob.id and bmt.code='mt_branch_secretary' and obm.type_id = bmt.id and obm.user_id=su.id) tmp on tmp.branch_id=ob.id,  ow_party op , unit u
where ob.party_id=op.id and op.unit_id=u.id and ob.is_deleted=0 and op.is_deleted=0
union all
SELECT op.code as branch_code,  op.name as branch_name, 0 as branch_type, op.code as party_code, op.name as party_name, u.code as dep_code, u.name as dep_name, count2.member_count,tmp.code as sid, tmp.realname from ow_party op
left join (select om.party_id, count(om.user_id) as member_count from ow_member om where om.branch_id is null group by om.party_id) count2 on  count2.party_id=op.id
 left join (
 select opm.user_id, opm.post_id, opmg.party_id, su.realname, su.code  from
ow_party_member opm, ow_party_member_group opmg, base_meta_type bmt, sys_user_view su
 where opmg.is_present=1 and opm.group_id=opmg.id and bmt.code='mt_party_secretary' and opm.post_id = bmt.id and opm.user_id=su.id
 )tmp on tmp.party_id=op.id, unit u, base_meta_type bmt
where op.is_deleted=0 and op.unit_id=u.id and bmt.code='mt_direct_branch' and op.class_id = bmt.id;
-- ----------------------------
--  View definition for `ext_cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `ext_cadre_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_cadre_view` AS
select u.code AS code,ui.realname AS realname,unit.code AS unit_code,
unit.name AS unit_name,_unittype.name AS unit_type,
posttype.name AS post, cadretype.name AS admin_level, c.lp_work_time as post_work_time from
cadre_view c left join unit on c.unit_id = unit.id
left join base_meta_type cadretype on cadretype.id = c.type_id
left join base_meta_type posttype on posttype.id = c.post_id
left join base_meta_type _unittype on unit.type_id = _unittype.id
, sys_user u, sys_user_info ui
where c.user_id = u.id and c.status = 1 and ui.user_id = u.id;

-- ----------------------------
--  View definition for `ext_member_view`
-- ----------------------------
DROP VIEW IF EXISTS `ext_member_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_member_view` AS
select u.code as sid, ui.realname, om.political_status, om.grow_time, if(bmt.code='mt_direct_branch', op.code, ob.code) as branch_code
from sys_user u, sys_user_info ui, ow_member om
left join ow_party op on om.party_id=op.id
left join base_meta_type bmt on op.class_id = bmt.id
left join ow_branch ob on om.branch_id=ob.id
where om.status=1 and om.user_id=u.id and ui.user_id=u.id;

-- 出国暂留当做转出
-- select u.code as sid, ui.realname, om.status, om.`type`, om.political_status  from sys_user u, sys_user_info ui, ow_member om
-- where om.user_id=u.id and ui.user_id=u.id and om.user_id not in(select user_id from ow_member_stay where status=3)
-- union all
-- select su.code as sid, sui.realname, (oga.status+1) as status, om.type, om.political_status
-- from ow_member_stay oga, ow_member om, sys_user su, sys_user_info sui where oga.status=3 and oga.user_id=om.user_id and oga.user_id=su.id and sui.user_id=su.id;


-- ----------------------------
--  View definition for `ow_branch_member_group_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_branch_member_group_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_branch_member_group_view` AS
SELECT bmg.`*`, b.party_id from ow_branch_member_group bmg, ow_branch b where bmg.branch_id=b.id ;

-- ----------------------------
--  View definition for `ow_branch_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_branch_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_branch_view` AS
select b.*,  mtmp.num as member_count,mtmp.s_num as student_member_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, gtmp.num as group_count, gtmp2.num as present_group_count from ow_branch b
left join (select sum(if(type=2, 1, 0)) as s_num, count(*) as num,  branch_id from ow_member where  status=1 group by branch_id) mtmp on mtmp.branch_id=b.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, branch_id from ow_member_teacher where status=1 group by branch_id) mtmp2 on mtmp2.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group group by branch_id) gtmp on gtmp.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_present=1 group by  branch_id) gtmp2 on gtmp2.branch_id=b.id;

-- ----------------------------
--  View definition for `ow_member_abroad_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_abroad_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_abroad_view` AS
select ea.`*`, m.user_id, u.realname, u.code, u.gender, m.party_id, m.branch_id
from ext_abroad ea , sys_user_view u, ow_member m where ea.gzzh=u.code and u.id=m.user_id ;

-- ----------------------------
--  View definition for `ow_member_apply_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select *, if((_status is null or _status=1), 0, 1) as member_status
from (select ma.*, m.`status` as _status from ow_member_apply ma
left join ow_member m  on ma.user_id = m.user_id) tmp;

-- ----------------------------
--  View definition for `ow_member_outflow_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_outflow_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_outflow_view` AS
SELECT omo.*, om.`status` as member_status from ow_member_outflow omo, ow_member om where omo.user_id=om.user_id  ;

-- ----------------------------
--  View definition for `ow_member_stay_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_stay_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER
VIEW `ow_member_stay_view` AS SELECT wms.*,  om.`status` as member_status
from ow_member_stay wms, ow_member om
where wms.user_id=om.user_id  ;
-- ----------------------------
--  View definition for `ow_member_student`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_student`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_student`
AS SELECT m.create_time,m.apply_time,m.source as member_source,u.source,m.positive_time,m.active_time,
m.political_status,m.transfer_time,m.user_id,m.branch_id,m.candidate_time,m.party_id,m.grow_time,m.status,m.party_post,
m.party_reward,m.other_reward,s.delay_year,
s.period,u.code,s.edu_category,ui.gender,ui.birth,ui.nation,s.actual_graduate_time,
s.expect_graduate_time,s.actual_enrol_time,s.sync_source ,s.type,s.is_full_time,ui.realname,
s.enrol_year,ui.native_place,s.edu_way,ui.idcard,s.edu_level,s.grade,s.edu_type,s.xj_status,p.unit_id
,mo.status as out_status, mo.handle_time as out_handle_time
from ow_member m left join ow_member_out mo on mo.user_id = m.user_id,
ow_party p, sys_student_info s, sys_user u, sys_user_info ui
where m.user_id = s.user_id and m.party_id = p.id and m.user_id = u.id and ui.user_id = u.id;
-- ----------------------------
--  View definition for `ow_member_teacher`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_teacher`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_teacher`
AS SELECT
t.*,
m.create_time,
m.apply_time,
m.source as member_source,
u.source,
m.positive_time,
m.active_time,
m.political_status,
m.transfer_time,
m.branch_id,
m.candidate_time,
m.party_id,
m.grow_time,
m.status,
m.party_post,
m.party_reward,
m.other_reward,
u.code,
ui.gender,
ui.nation,
ui.email,
ui.mobile,
ui.birth,ui.realname, ui.native_place, ui.phone, ui.idcard, p.unit_id
,mo.status as out_status, mo.handle_time as out_handle_time
from ow_member m left join ow_member_out mo on mo.user_id = m.user_id, ow_party p, sys_user u, sys_teacher_info t, sys_user_info ui
where m.user_id=t.user_id and m.party_id=p.id and m.user_id =u.id and ui.user_id = u.id;
-- ----------------------------
--  View definition for `ow_party_member_group_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_member_group_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_member_group_view` AS
select opmg.*, op.sort_order as party_sort_order from ow_party_member_group opmg, ow_party op where opmg.party_id=op.id ;

-- ----------------------------
--  View definition for `ow_party_member_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_member_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_party_member_view` AS
select opm.*,
`ui`.`msg_title` AS `msg_title`
	,`ui`.`email` AS `email`
	,`ui`.`realname` AS `realname`
	,`ui`.`gender` AS `gender`
	,`ui`.`nation` AS `nation`
	,`ui`.`native_place` AS `native_place`
	,`ui`.`idcard` AS `idcard`
	,`ui`.`birth` AS `birth`
	,`om`.`party_id` AS `party_id`
	,`om`.`branch_id` AS `branch_id`
	,`om`.`grow_time` AS `grow_time`
	,`om`.`status` AS `member_status`
	, opmg.party_id as group_party_id
	, op.unit_id
	, op.sort_order as party_sort_order
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
	, if(isnull(dp.id), if(om.status=1, om.grow_time, ow.grow_time), dp.grow_time) as cadre_grow_time
	, if(isnull(dp.id), if(!isnull(ow.id) or om.status=1, 0, -1), dp.class_id) as cadre_dp_type
	 from  ow_party_member opm  join ow_party_member_group opmg on opmg.is_present=1 and opmg.is_deleted=0 and opm.group_id=opmg.id
 left join sys_user_info ui on opm.user_id=ui.user_id
 left join ow_member om on opm.user_id=om.user_id
 left join ow_party op on opmg.party_id=op.id
 left join sys_teacher_info t on opm.user_id=t.user_id
left join cadre_party dp on dp.user_id= opm.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= opm.user_id and ow.type = 2;
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
--  View definition for `sys_user_view`
-- ----------------------------
DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;
-- ----------------------------
--  Records 
-- ----------------------------
