
-- 2020.12.17 ly
DROP VIEW IF EXISTS `ow_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_view` AS
select
m.*,u.source as user_source, u.type as user_type, u.username, u.code, ui.realname, ui.gender, ui.nation, ui.native_place,
ui.birth, ui.idcard, ui.mobile, ui.email, ui.unit, p.unit_id,
p.name as party_name, b.name as branch_name, p.sort_order as party_sort_order, b.sort_order as branch_sort_order,
mo.status as out_status, mo.handle_time as out_handle_time,

t.education,t.degree,t.degree_time,t.major,t.school,t.school_type, t.degree_school,
t.authorized_type, t.staff_type, t.staff_status, t.on_job, t.main_post_level,
t.post_class, t.post, t.post_level, t.pro_post, t.pro_post_level, t.manage_level, t.office_level,
t.title_level,t.marital_status,t.address,
t.arrive_time, t.work_time, t.from_type, t.talent_type, t.talent_title,
if(isnull(t.is_retire), 0, t.is_retire) as is_retire, t.is_honor_retire, t.retire_time, t.is_high_level_talent,

s.delay_year,s.period,s.actual_graduate_time,
s.expect_graduate_time,s.actual_enrol_time,s.sync_source ,s.type as student_type,s.is_full_time,
s.enrol_year,s.grade,s.edu_type,s.edu_way,s.edu_level,s.edu_category,s.xj_status

from ow_member m
left join sys_user_info ui on ui.user_id=m.user_id
left join sys_user u on u.id=m.user_id
left join ow_party p on p.id = m.party_id
left join ow_branch b on b.id = m.branch_id
left join ow_member_out mo on mo.status!=10 and mo.user_id = m.user_id
left join sys_teacher_info t on t.user_id = m.user_id
left join sys_student_info s on s.user_id = m.user_id;

-- 2020.11.23
DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id,
u.status as unit_status, u.sort_order as unit_sort_order,
upg.name as group_name,
cp.cadre_id, cp.id as cadre_post_id, cp.admin_level as cp_admin_level, cp.is_main_post,
cv.gender, cv.admin_level as cadre_admin_level, cv.post_type as cadre_post_type,cv.lp_work_time as lp_work_time,cv.np_work_time as np_work_time,
cv.s_work_time as s_work_time,cv.is_principal as cadre_is_principal, cv.cadre_post_year, cv.admin_level_year from unit_post up
left join unit u on up.unit_id=u.id
left join unit_post_group upg on up.group_id=upg.id
left join cadre_post cp on up.id=cp.unit_post_id
left join cadre_view cv on cv.id=cp.cadre_id;

-- 20200506
DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id,
u.status as unit_status, u.sort_order as unit_sort_order,
cp.cadre_id, cp.id as cadre_post_id, cp.admin_level as cp_admin_level, cp.is_main_post,
cv.gender, cv.admin_level as cadre_admin_level, cv.post_type as cadre_post_type,cv.lp_work_time as lp_work_time,cv.s_work_time as s_work_time,
cv.is_principal as cadre_is_principal, cv.cadre_post_year, cv.admin_level_year from unit_post up
left join unit u on up.unit_id=u.id
left join cadre_post cp on up.id=cp.unit_post_id
left join cadre_view cv on cv.id=cp.cadre_id;