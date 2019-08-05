
--2019.8.5
DROP VIEW IF EXISTS dp_member_view;
CREATE ALGORITHM = UNDEFINED VIEW dp_member_view AS
select m.*,u.source as user_source,u.code,
ui.realname,ui.gender,ui.nation,ui.native_place,ui.birth,ui.idcard,ui.mobile,ui.email,ui.unit,
p.unit_id,mo.stattus as out_status,mo.handle_time as out_handle_time,
t.education,t.degree,t.degree_time,t.major,t.school,t.school_type,t.degree_school,t.authorized_type,t.staff_type,t.staff_status,t.on_job,t.main_post_level,t.post_class,t.post,
t.post_level,t.pro_post,t.pro_post_level,t.manage_level,t.office_level,t.title_level,t.marital_status,t.address,t.arrive_time,t.work_time,t.from_type,t.talent_type,t.talent_title,t.is_retire,t.is_honor_retire,t.retire_time,
s.delay_year,s.period,s.actual_graduate_time,s.expect_graduate_time,s.actual_enrol_time,s.sync_source,s.type as student_type,s.is_full_time,s.enrol_year,s.grade,s.edu_type,s.edu_way,s.edu_level,s.edu_category,s.xj_status
from ((((((dp_member m left join sys_user_info ui on((ui.user_id = m.user_id)))
left join sys_user u on((u.id = m.user_id)))
left join dp_party p on((p.id = m.party_id)))
left join dp_member_out mo on(((mo.status <> 10) and (mo.user_id = m.user_id))))
left join sys_teacher_info t on((t.user_id = m.user_id)))
left join sys_student_info s on((s.user_id = m.user_id)))

DROP VIEW IF EXISTS dp_org_admin_view;
CREATE ALGORITHM = UNDEFINED VIEW dp_org_admin_view AS
select
oa.id,
oa.user_id,
sui.realname,
su.code,
oa.party_id,
p.name as party_name,
oa.type,
oa.status,
oa.remark,
oa.create_time,
p.sort_order as party_sort_order
from dp_org_admin oa left join dp_party p on p.id = oa.party_id
left join sys_user_info sui on sui.user_id = oa.user_id
left join sys_user su on su.id = oa.user_id

DROP VIEW IF EXISTS dp_party_member_view;
CREATE ALGORITHM = UNDEFINED VIEW dp_party_member_view AS
select dpm.*,
ui.msg_title,ui.email,ui.realname,ui.gender,ui.nation,ui.native_place,ui.idcard,ui.birth,
dm.party_id,dm.status as member_status,
dpmg.party_id as group_party_id,dpmg.is_present,dpmg.is_deleted,
dp.unit_id,dp.sort_order as party_sort_order,
t.post_class,t.sub_post_class,t.main_post_level,t.pro_post_time,t.pro_post_level,t.pro_post_level_time,t.pro_post,t.manage_level,t.manage_level_time,t.arrive_time,
ow.id as ow_id,if(((ow.id is not null) or (dm.status=1) or (dm.status=4)),1,0) as is_ow,if(((dm.status=1) or (dm.status=4)),dm.grow_time,ow.grow_time) as ow_grow_time,
ow.remark as ow_remark, cp.grow_time as dp_grow_time, dp.class_id as dp_type_id
 from (((((((dp_party_member dpm join dp_party_member_group dpmg on((dpm.group_id = dpmg.id)))
 left join sys_user_info ui on((dpm.user_id = ui.user_id)))
 left join dp_member dm on((dpm.user_id = dm.user_id)))
 left join dp_party dp on((dpmg.party_id = dp.id)))
 left join sys_teacher_info t on((dpm.user_id = t.user_id)))
 left join cadre_party cp on(((cp.user_id = dpm.user_id) and (cp.type = 1))))
 left join cadre_party ow on(((ow.user_id = dpm.user_id) and (ow.type = 1))))

DROP VIEW IF EXISTS dp_party_view;
CREATE ALGORITHM = UNDEFINED VIEW dp_party_view AS
select p.*,
mtmp.num as member_count,mtmp.s_num as student_member_count,mtmp.positive_count,
mtmp2.t_num as teacher_member_count,mtmp2.t2_num as retire_member_count,
pmgtmp.num as group_count,
pmgtmp2.num as present_group_count
from ((((db_owip.dp_party p left join (select sum(if((db_owip.dp_member.type = 2),1,0)) AS s_num,
	sum(if((db_owip.dp_member.political_status = 2),1,0)) AS positive_count,
	count(0) AS num,db_owip.dp_member.party_id AS party_id
	from db_owip.dp_member where (db_owip.dp_member.status = 1)
	group by db_owip.dp_member.party_id) mtmp on((mtmp.party_id = p.id)))
	left join (select sum(if((dp_member_view.is_retire = 0),1,0)) AS t_num,sum(if((dp_member_view.is_retire = 1),1,0)) AS t2_num,
	count(0) AS num,dp_member_view.party_id AS party_id
	from db_owip.dp_member_view where ((dp_member_view.type = 1) and (dp_member_view.status = 1))
	group by dp_member_view.party_id) mtmp2 on((mtmp2.party_id = p.id)))
	left join (select count(0) AS num,db_owip.dp_party_member_group.party_id AS party_id
	from db_owip.dp_party_member_group group by db_owip.dp_party_member_group.party_id) pmgtmp on((pmgtmp.party_id = p.id)))
	left join (select count(0) AS num,db_owip.dp_party_member_group.party_id AS party_id
	from db_owip.dp_party_member_group where (db_owip.dp_party_member_group.is_present = 1)
	group by db_owip.dp_party_member_group.party_id) pmgtmp2 on((pmgtmp2.party_id = p.id)))