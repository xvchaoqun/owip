


-- 20190826 李阳 更新dp_party_member_view视图
DROP VIEW IF EXISTS `dp_party_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_party_member_view` AS
select dpm.*,dpmg.party_id as group_party_id,dpmg.is_present,dpmg.is_deleted,dp.unit_id,dp.is_deleted as is_dp_party_deleted
from dp_party_member dpm join dp_party_member_group dpmg on dpmg.id=dpm.group_id
left join dp_party dp on dp.id=dpmg.party_id

-- 20190819 李阳  添加dp_party_member_view视图，修改dp_member_view视图
-- dp_party_member_view
DROP VIEW IF EXISTS `dp_party_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_party_member_view` AS
select dpm.*,dpmg.party_id as group_party_id,dpmg.is_present,dpmg.is_deleted,dp.unit_id
from dp_party_member dpm join dp_party_member_group dpmg on dpmg.id=dpm.group_id
left join dp_party dp on dp.id=dpmg.party_id;

-- 2019.8.13 李阳
-- 把党派成员信息和学生老师的信息对应
DROP VIEW IF EXISTS `dp_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_member_view` AS
select dm.*,dp.unit_id,sui.gender,sui.birth,sui.nation,sui.native_place,sui.mobile,
dmo.status as out_status,dmo.handle_time as out_handle_time,
t.education,t.authorized_type,t.pro_post,t.is_retire,t.retire_time,t.is_honor_retire,t.staff_type,t.post_class,
s.edu_level,s.edu_type,s.actual_enrol_time,s.is_full_time,s.expect_graduate_time,s.delay_year,s.actual_graduate_time,s.sync_source,s.grade,s.type as student_type
from dp_member dm left join dp_party dp on dp.id = dm.party_id
left join sys_user_info sui on dm.user_id=sui.user_id
left join dp_member_out dmo on dmo.status!=10 and dmo.user_id = dm.user_id
left join sys_teacher_info t on t.user_id = dm.user_id
left join sys_student_info s on s.user_id = dm.user_id;

-- 统计民主党派所拥有的成员数量，委员会数量，老师学生数量，在职离退休数量
DROP VIEW IF EXISTS `dp_party_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dp_party_view` AS
select dp.*, mtmp.num as member_count, mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count,mtmp2.t3_num as honor_retire_count,
pmgtmp.num as group_count, pmgtmp2.num as present_group_count
from dp_party dp
left join (select sum(if(type=2, 1, 0)) as s_num, sum(if(political_status=2, 1, 0)) as positive_count, count(*) as num,  party_id from dp_member where  status=1 group by party_id) mtmp on mtmp.party_id=dp.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,sum(if(is_honor_retire=1, 1, 0)) as t3_num, count(*) as num, party_id from dp_member_view where type=1 and status=1 group by party_id) mtmp2 on mtmp2.party_id=dp.id
left join (select count(*) as num, party_id from dp_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=dp.id
left join (select count(*) as num, party_id from dp_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=dp.id;


