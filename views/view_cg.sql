DROP VIEW IF EXISTS `cg_team_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cg_team_view`
AS select ct.`*`,cu.unit_id,cl.user_id,cl.phone
from cg_team ct
left join(
	select team_id, unit_id from cg_unit where is_current = 1) cu
	on ct.id = cu.team_id
left join(
	select team_id, user_id,phone from cg_leader where is_current = 1) cl
	on ct.id = cl.team_id;
