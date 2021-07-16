
DROP VIEW IF EXISTS `ps_info_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ps_info_view`
AS select pi.*, pm.user_id as rector_user_id, pm.title as rector_title, pm.mobile as rector_mobile,
				joint.joint_ids as joint_ids,
				host.party_id as host_id,
				pa.user_id as admin_user_id,pa.mobile as admin_mobile
from ps_info pi
left join(
		select ps_id,user_id,title,mobile from ps_member where type = 557 and is_history = 0) pm
		on pi.id = pm.ps_id
left join(
		select ps_id,GROUP_CONCAT(PARTY_id) as joint_ids from ps_party where is_host = 0 and is_finish = 0 group by ps_id) joint
		on pi.id = joint.ps_id
left join (
			select ps_id, party_id from ps_party where is_finish = 0 and is_host = 1) host
			on pi.id = host.ps_id
left join(
		select ps_id,user_id,mobile from ps_admin where type = 1 and is_history = 0) pa
		on pi.id = pa.ps_id;


