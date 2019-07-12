
DROP VIEW IF EXISTS `ext_user_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ext_user_view` AS
  SELECT id as user_id, code from sys_user ;

-- ----------------------------
--  View definition for `ext_cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `ext_cadre_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_cadre_view` AS
select u.code AS code,ui.realname AS realname,unit.code AS unit_code,
unit.name AS unit_name,_unittype.name AS unit_type,
posttype.name AS post, cadretype.name AS admin_level, c.lp_work_time as post_work_time from
cadre_view c left join unit on c.unit_id = unit.id
left join base_meta_type cadretype on cadretype.id = c.admin_level
left join base_meta_type posttype on posttype.id = c.post_type
left join base_meta_type _unittype on unit.type_id = _unittype.id
, sys_user u, sys_user_info ui
where c.user_id = u.id and (c.status = 1 or c.status=6) and ui.user_id = u.id order by field(c.status, 2,5,3,1,4,6) desc;


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
--  View definition for `ext_branch_view2`
-- ----------------------------
DROP VIEW IF EXISTS `ext_branch_view2`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_branch_view2`
AS SELECT ob.code as zbdm, ob.name as zbmc,if(ob.is_staff, '教工党支部', '学生党支部') as zblb,
zbsj.code as zbsjgh, zbsj.realname as zbsjxm,
op.name as zzmc, zzlb.name as zzlb,
sj.code as sjdm, sj.realname as sjmc,
fsj.code as fsjdm, fsj.realname as fsjxm, op.sort_order from ow_branch ob
left join (
	select obm.user_id, obm.type_id, ob.id as branch_id, su.realname, su.code from
	ow_branch_member obm, ow_branch_member_group obmg, ow_branch ob, base_meta_type bmt, sys_user_view su
 	where obmg.is_present=1 and obm.group_id=obmg.id and obmg.branch_id=ob.id
 	and bmt.code='mt_branch_secretary' and obm.type_id = bmt.id and obm.user_id=su.id group by ob.id) zbsj on zbsj.branch_id=ob.id
left join(
 	select opm.user_id, opm.post_id, opmg.party_id, su.realname, su.code  from
	ow_party_member opm, ow_party_member_group opmg, base_meta_type bmt, sys_user_view su
 	where opmg.is_present=1 and opm.group_id=opmg.id and bmt.code='mt_party_secretary'
	and opm.post_id = bmt.id and opm.user_id=su.id group by opmg.party_id)sj on sj.party_id=ob.party_id
left join(
 	select opm.user_id, opm.post_id, opmg.party_id, su.realname, su.code  from
	ow_party_member opm, ow_party_member_group opmg, base_meta_type bmt, sys_user_view su
 	where opmg.is_present=1 and opm.group_id=opmg.id and bmt.code='mt_party_vice_secretary'
	and opm.post_id = bmt.id and opm.user_id=su.id group by opmg.party_id)fsj on fsj.party_id=ob.party_id
left join ow_party op on ob.party_id=op.id and op.is_deleted=0
left join base_meta_type zzlb on op.type_id=zzlb.id
where ob.is_deleted=0
union all
SELECT op.code as zbdm,  op.name as zbmc, '直属党支部' as zblb,
sj.code as zbsjdm, sj.realname as zbsjxm,
op.name as zzmc, zzlb.name as zzlb,
sj.code as sjdm, sj.realname as sjxm,
fsj.code as fsjdm, fsj.realname as fsjxm, op.sort_order from ow_party op
left join(
 	select opm.user_id, opm.post_id, opmg.party_id, su.realname, su.code  from
	ow_party_member opm, ow_party_member_group opmg, base_meta_type bmt, sys_user_view su
 	where opmg.is_present=1 and opm.group_id=opmg.id and bmt.code='mt_party_secretary'
	and opm.post_id = bmt.id and opm.user_id=su.id group by opmg.party_id)sj on sj.party_id=op.id
left join(
 	select opm.user_id, opm.post_id, opmg.party_id, su.realname, su.code  from
	ow_party_member opm, ow_party_member_group opmg, base_meta_type bmt, sys_user_view su
 	where opmg.is_present=1 and opm.group_id=opmg.id and bmt.code='mt_party_vice_secretary'
	and opm.post_id = bmt.id and opm.user_id=su.id group by opmg.party_id)fsj on fsj.party_id=op.id
left join base_meta_type bmt on op.class_id = bmt.id
left join base_meta_type zzlb on op.type_id=zzlb.id
where op.is_deleted=0 and bmt.code='mt_direct_branch' order by sort_order desc;


-- ----------------------------
--  View definition for `ext_member_view`
-- ----------------------------
DROP VIEW IF EXISTS `ext_member_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ext_member_view` AS
select u.code as sid, u.realname, om.type, if(oms.status=3, 4, om.status) as status, om.political_status, om.grow_time, if(bmt.code='mt_direct_branch', op.code, ob.code) as branch_code
from ow_member om
left join ow_member_stay oms on oms.user_id=om.user_id
left join sys_user_view u on om.user_id=u.id
left join ow_party op on om.party_id=op.id
left join base_meta_type bmt on op.class_id = bmt.id
left join ow_branch ob on om.branch_id=ob.id;

-- 出国暂留当做转出
-- select u.code as sid, ui.realname, om.status, om.`type`, om.political_status  from sys_user u, sys_user_info ui, ow_member om
-- where om.user_id=u.id and ui.user_id=u.id and om.user_id not in(select user_id from ow_member_stay where status=3)
-- union all
-- select su.code as sid, sui.realname, (oga.status+1) as status, om.type, om.political_status
-- from ow_member_stay oga, ow_member om, sys_user su, sys_user_info sui where oga.status=3 and oga.user_id=om.user_id and oga.user_id=su.id and sui.user_id=su.id;
