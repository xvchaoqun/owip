

--201910 李阳 党内奖惩、党内任职视图 三张视图

DROP VIEW IF EXISTS `ow_party_post_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_post_view` AS
SELECT opp.*,om.party_id,om.branch_id
from ow_party_post opp
left join ow_member om ON opp.user_id=om.user_id

DROP VIEW IF EXISTS `ow_party_punish_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_punish_view` AS
SELECT opp.*,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order,ob.party_id as branch_party_id,om.party_id as user_party_id,om.branch_id as user_branch_id
from ow_party_punish opp
left join ow_party op ON (opp.party_id!="" and opp.party_id=op.id)
left join ow_branch ob ON (opp.branch_id!="" and opp.branch_id=ob.id)
left join ow_member om on (opp.user_id!="" and opp.user_id=om.user_id)

DROP VIEW IF EXISTS `ow_party_reward_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_reward_view` AS
SELECT opr.*,op.sort_order as party_sort_order,ob.sort_order as branch_sort_order,ob.party_id as branch_party_id,om.party_id as user_party_id,om.branch_id as user_branch_id
from ow_party_reward opr
left join ow_party op ON (opr.party_id!="" and opr.party_id=op.id)
left join ow_branch ob ON (opr.branch_id!="" and opr.branch_id=ob.id)
left join ow_member om on (opr.user_id!="" and opr.user_id=om.user_id)