
--  党代会

DROP VIEW IF EXISTS `pcs_proposal_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pcs_proposal_view` AS select pp.*, pps1.invite_user_ids, pps1.invite_count, pps2.seconder_ids, pps2.seconder_count from pcs_proposal pp
left join (select proposal_id, group_concat(user_id) as invite_user_ids, count(*) as invite_count from pcs_proposal_seconder where is_invited=1 group by proposal_id) pps1 on pps1.proposal_id = pp.id
left join (select proposal_id, group_concat(user_id) as seconder_ids, count(*) as seconder_count from pcs_proposal_seconder where is_finished=1 group by proposal_id) pps2 on pps2.proposal_id = pp.id;