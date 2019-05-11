
-- ----------------------------
--  View definition for `sys_user_view`
-- ----------------------------
DROP VIEW IF EXISTS `sys_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `sys_user_view`
AS select u.*, ui.* from sys_user u left join sys_user_info ui on u.id=ui.user_id;

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
select b.*, p.sort_order as party_sort_order, mtmp.num as member_count, mtmp.positive_count, mtmp.s_num as student_member_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, gtmp.num as group_count, gtmp2.num as present_group_count
from ow_branch b
left join ow_party p on b.party_id=p.id
left join (select  sum(if(political_status=2, 1, 0)) as positive_count, sum(if(type=2, 1, 0)) as s_num, count(*) as num,  branch_id from ow_member where  status=1 group by branch_id) mtmp on mtmp.branch_id=b.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,
count(*) as num, branch_id from ow_member_teacher where status=1 group by branch_id) mtmp2 on mtmp2.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 group by branch_id) gtmp on gtmp.branch_id=b.id
left join (select count(*) as num, branch_id from ow_branch_member_group where is_deleted=0 and is_present=1 group by branch_id) gtmp2 on gtmp2.branch_id=b.id;

DROP VIEW IF EXISTS `ow_org_admin_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ow_org_admin_view` AS
select oa.*, p.sort_order as party_sort_order, b.party_id as branch_party_id,
bp.sort_order as branch_party_sort_order, b.sort_order as branch_sort_order from ow_org_admin oa
left join ow_party p on p.id=oa.party_id
left join ow_branch b on b.id=oa.branch_id
left join ow_party bp on bp.id=b.party_id;

-- ----------------------------
--  View definition for `ow_member_student`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_student`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_student`
AS SELECT m.create_time,m.apply_time,m.source as member_source, m.add_type, u.source,m.positive_time,m.active_time,
m.political_status,m.transfer_time,m.user_id,m.branch_id,m.candidate_time,m.party_id,m.sponsor,m.grow_branch,m.positive_branch,m.grow_time,m.status,m.party_post,
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
m.add_type,
u.source,
m.positive_time,
m.active_time,
m.political_status,
m.transfer_time,
m.branch_id,
m.candidate_time,
m.party_id,
m.sponsor,m.grow_branch,m.positive_branch,
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
--  View definition for `ow_member_abroad_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_abroad_view`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ow_member_abroad_view` AS
select ea.`*`, m.user_id, u.realname, u.code, u.gender, m.party_id, m.branch_id
from ext_abroad ea , sys_user_view u, ow_member m where ea.gzzh=u.code and u.id=m.user_id ;

-- 只统计“现有岗位”中的“占干部职数”的岗位
DROP VIEW IF EXISTS `unit_post_count_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_count_view` AS
select unit_id, admin_level, count(is_cpc=1 or null) as num, count(*) as total
from unit_post where status=1 group by unit_id, admin_level ;

-- 只统计“现有岗位”中的“占干部职数”的岗位
DROP VIEW IF EXISTS `unit_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_view` AS
select u.*, up.main_post_count, up.vice_post_count, up.none_post_count, up.main_kj_post_count, up.vice_kj_post_count,
       cpc.main_count, cpc.vice_count, cpc.main_kj_count, cpc.vice_kj_count, cpc.none_count from unit u left join
(select up.unit_Id,
 sum(if(bmt.code='mt_admin_level_main',1,0)) as main_post_count,
 sum(if(bmt.code='mt_admin_level_vice',1,0)) as vice_post_count,
  sum(if(bmt.code='mt_admin_level_none',1,0)) as none_post_count,
 sum(if(bmt.code='mt_admin_level_main_kj',1,0)) as main_kj_post_count,
  sum(if(bmt.code='mt_admin_level_vice_kj',1,0)) as vice_kj_post_count
from unit_post up left join base_meta_type bmt on up.admin_level=bmt.id where up.status=1 and up.is_cpc=1 group by up.unit_id) up on up.unit_id=u.id
left join
(select upc.unit_id, sum(if(bmt.code='mt_admin_level_main', num,0)) as main_count,
sum(if(bmt.code='mt_admin_level_vice', num,0)) as vice_count,
sum(if(bmt.code='mt_admin_level_none', num,0)) as none_count,
sum(if(bmt.code='mt_admin_level_main_kj', num,0)) as main_kj_count,
sum(if(bmt.code='mt_admin_level_vice_kj', num,0)) as vice_kj_count
from unit_post_count_view upc , base_meta_type bmt where upc.admin_level=bmt.id group by upc.unit_id) cpc on cpc.unit_id=u.id;

DROP VIEW IF EXISTS `leader_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `leader_view` AS
select l.*, c.status as cadre_status, if(!isnull(cm.id), 1, 0) as is_committee_member from leader l
left join cadre c on c.user_id=l.user_id
left join cm_member cm on cm.is_quit=0 and cm.type=3 and cm.user_id=l.user_id ;

DROP VIEW IF EXISTS `leader_unit_view`;
CREATE ALGORITHM = UNDEFINED VIEW `leader_unit_view` AS
select lu.*, c.id as cadre_id, l.id as leader_id,
l.sort_order as leader_sort_order, u.sort_order as unit_sort_order from leader_unit lu
left join cadre c on c.user_id=lu.user_id
left join leader l on l.user_id=lu.user_id
left join unit u on lu.unit_id=u.id ;