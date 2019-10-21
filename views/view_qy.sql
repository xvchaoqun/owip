
DROP VIEW IF EXISTS `qy_reward_record_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `qy_reward_record_view`
AS select rr.*, yr.reward_id, yr.sort_order as reward_sort_order ,y.year, r.name as reward_name ,r.type as reward_type from  qy_reward_record rr
left join qy_year_reward yr on rr.year_reward_id=yr.id
left join qy_year y on yr.year_id=y.id
left join qy_reward r on yr.reward_id=r.id
;

DROP VIEW IF EXISTS `qy_reward_obj_view`;
CREATE ALGORITHM = UNDEFINED DEFINER=`root`@`localhost` VIEW `qy_reward_obj_view`
AS select obj.*, yr.reward_id, yr.sort_order as reward_sort_order ,y.year, r.name as reward_name ,r.type as reward_type from qy_reward_obj obj
left join qy_reward_record rr on obj.record_id=rr.id
left join qy_year_reward yr on rr.year_reward_id=yr.id
left join qy_year y on yr.year_id=y.id
left join qy_reward r on yr.reward_id=r.id
;

