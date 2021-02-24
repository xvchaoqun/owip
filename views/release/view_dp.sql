
DROP VIEW IF EXISTS `dp_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_member_view` AS
select dm.*,sui.gender,sui.birth,sui.nation,sui.native_place,sui.unit,
t.work_time,t.authorized_type,t.education as high_edu,t.degree as high_degree,
if(isnull(t.is_retire), 0, t.is_retire) as is_retire,t.retire_time,t.is_honor_retire,
cv.admin_level,cv.post,t.pro_post

from dp_member dm left join dp_party dp on dp.id = dm.party_id
left join sys_user_info sui on dm.user_id=sui.user_id
left join sys_teacher_info t on t.user_id = dm.user_id
LEFT JOIN cadre_view cv ON (cv.STATUS IN (1,6) AND cv.user_id=dm.user_id);

DROP VIEW IF EXISTS `dp_party_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_party_member_view` AS
select dpm.*,dpmg.party_id as group_party_id,dpmg.is_present,dpmg.is_deleted,dp.is_deleted as is_dp_party_deleted,
dm.mobile,dm.email
from dp_party_member dpm
join dp_party_member_group dpmg on dpmg.id=dpm.group_id
left join dp_party dp on dp.id=dpmg.party_id
LEFT join dp_member dm on dpm.user_id=dm.user_id;

DROP VIEW IF EXISTS `dp_npm_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_npm_view` AS
SELECT npm.*,sui.unit,
sui.gender,sui.birth,sui.nation,sui.native_place,sui.mobile,sui.phone,
t.work_time,t.authorized_type,t.education as high_edu,t.degree as high_degree,t.pro_post
from dp_npm npm
left join sys_user_info sui on sui.user_id=npm.user_id
left join sys_teacher_info t on t.user_id = npm.user_id;

DROP VIEW IF EXISTS `dp_om_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_om_view` AS
SELECT om.*,m.party_id,m.dp_grow_time,
t.work_time,t.education,t.degree,t.school,t.major,
s.gender,s.birth,s.nation,s.native_place,s.unit,s.mobile,s.phone
from dp_om om
left join dp_member m on m.user_id=om.user_id
left join sys_teacher_info t on t.user_id=om.user_id
left join sys_user_info s on s.user_id=om.user_id;

DROP VIEW IF EXISTS `dp_npr_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_npr_view` AS
SELECT npr.*,m.party_id,m.dp_grow_time,
t.work_time,t.education,t.degree,t.school,t.major,
s.gender,s.birth,s.nation,s.native_place,s.unit,s.mobile,s.phone
from dp_npr npr
left join dp_member m on m.user_id=npr.user_id
left join sys_teacher_info t on t.user_id=npr.user_id
left join sys_user_info s on s.user_id=npr.user_id;

DROP VIEW IF EXISTS `dp_party_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dp_party_view` AS
select dp.*,mtmp.num as member_count,mtmp2.t_num as teacher_member_count,
mtmp2.t2_num as retire_member_count,mtmp2.t3_num as honor_retire_count,
pmgtmp.num as group_count, pmgtmp2.num as present_group_count
from dp_party dp
left join (SELECT  count(*) as num,  party_id from dp_member where  status=1 group by party_id) mtmp on mtmp.party_id=dp.id
left join (select sum(if(is_retire=0, 1, 0)) as t_num, sum(if(is_retire=1, 1, 0)) as t2_num,sum(if(is_honor_retire=1, 1, 0)) as t3_num, count(*) as num, party_id from dp_member_view where type=1 and status=1 group by party_id) mtmp2 on mtmp2.party_id=dp.id
left join (select count(*) as num, party_id from dp_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=dp.id
left join (select count(*) as num, party_id from dp_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=dp.id;

DROP VIEW IF EXISTS `dp_pr_cm_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dp_pr_cm_view` AS
SELECT dpc.*,m.party_id,m.dp_grow_time,
sui.gender,sui.birth,sui.nation
from dp_pr_cm dpc left join dp_member m on m.user_id=dpc.user_id
left join dp_party p on p.id=m.party_id
left join sys_user_info sui on sui.user_id=dpc.user_id;