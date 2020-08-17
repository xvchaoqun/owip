
--  党代会

DROP VIEW IF EXISTS `pcs_proposal_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_proposal_view` AS select pp.*, pps1.invite_user_ids, pps1.invite_count, pps2.seconder_ids, pps2.seconder_count from pcs_proposal pp
left join (select proposal_id, group_concat(user_id) as invite_user_ids, count(*) as invite_count from pcs_proposal_seconder where is_invited=1 group by proposal_id) pps1 on pps1.proposal_id = pp.id
left join (select proposal_id, group_concat(user_id) as seconder_ids, count(*) as seconder_count from pcs_proposal_seconder where is_finished=1 group by proposal_id) pps2 on pps2.proposal_id = pp.id;

-- (分党委、党总支)排除了pcs_exclude_branch里头的支部之后的分党委表
/*DROP VIEW IF EXISTS `pcs_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_party_view` AS select p.*, btmp.num as branch_count, mtmp.num as member_count,  mtmp.s_num as student_member_count, mtmp.positive_count,
mtmp2.t_num as teacher_member_count, mtmp2.t2_num as retire_member_count, pmgtmp.num as group_count, pmgtmp2.num as present_group_count from ow_party p
left join (select count(ob.id) as num, ob.party_id from ow_branch ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null group by ob.party_id) btmp on btmp.party_id=p.id
left join (select sum(if(om.type=2, 1, 0)) as s_num, sum(if(om.political_status=2, 1, 0)) as positive_count, count(om.user_id) as num,  om.party_id from ow_member om
left join ow_branch ob on ob.id = om.branch_id
left join pcs_exclude_branch peb on peb.party_id=om.party_id and peb.branch_id=om.branch_id
where ob.is_deleted=0 and om.status=1 and peb.id is null group by party_id
union all
select sum(if(om.type=2, 1, 0)) as s_num, sum(if(om.political_status=2, 1, 0)) as positive_count, count(om.user_id) as num,  om.party_id from ow_member om
left join (select op.* from ow_party op, base_meta_type bmt  where op.class_id=bmt.id and bmt.code='mt_direct_branch') as op on op.id = om.party_id
where op.is_deleted=0 and om.status=1 group by party_id
) mtmp on mtmp.party_id=p.id
left join (select sum(if(om.is_retire=0, 1, 0)) as t_num, sum(if(om.is_retire=1, 1, 0)) as t2_num,
count(om.user_id) as num, om.party_id from ow_member_view om
left join pcs_exclude_branch peb on peb.party_id=om.party_id and peb.branch_id=om.branch_id
where type=1 and status=1 and peb.id is null group by party_id) mtmp2 on mtmp2.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group group by party_id) pmgtmp on pmgtmp.party_id=p.id
left join (select count(*) as num, party_id from ow_party_member_group where is_present=1 group by party_id) pmgtmp2 on pmgtmp2.party_id=p.id
where p.is_deleted=0;
*/

/*DROP VIEW IF EXISTS `pcs_branch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_branch_view` AS
select  ob.party_id, ob.id as branch_id, ob.name, ob.member_count, ob.positive_count,  ob.student_member_count, ob.teacher_member_count, ob.retire_member_count from ow_branch_view ob
left join pcs_exclude_branch peb on peb.party_id=ob.party_id and peb.branch_id=ob.id
where ob.is_deleted=0 and peb.id is null
union all
select  op.id as party_id, null as branch_id, op.name, op.member_count, op.positive_count, op.student_member_count, op.teacher_member_count, op.retire_member_count from ow_party_view op, base_meta_type bmt
where op.is_deleted=0 and op.class_id=bmt.id and bmt.code='mt_direct_branch' order by member_count desc;*/