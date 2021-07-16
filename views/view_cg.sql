DROP VIEW IF EXISTS `cg_team_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `cg_team_view`
AS select ct.`*`,cu.unit_id,cl.user_id,cl.phone
from cg_team ct
left join cg_unit cu on ct.id = cu.team_id and cu.is_current=1
left join cg_leader cl on ct.id = cl.team_id and cl.is_current=1;