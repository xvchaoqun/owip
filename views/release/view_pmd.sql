
-- 党员每月实际缴费视图
DROP VIEW IF EXISTS `pmd_member_pay_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_member_pay_view` AS
select pm.id, pmp.*, pm.pay_month, pm.month_id, pm.is_delay,
pm.delay_reason, pm.user_id, pm.party_id,
pm.branch_id, pm.type, pm.config_member_type_id, pm.due_pay_reason, pm.due_pay from pmd_member_pay pmp
left join pmd_member pm on pmp.member_id=pm.id ;

DROP VIEW IF EXISTS `pmd_pay_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_pay_party_view` AS
select ppp.*, op.name, op.is_deleted,op.sort_order,
count(ppa.user_id) as admin_count from pmd_pay_party ppp
left join ow_party op on ppp.party_id=op.id
left join pmd_party_admin ppa on ppa.party_id=ppp.party_id
group by ppp.party_id ;

DROP VIEW IF EXISTS `pmd_pay_branch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_pay_branch_view` AS
select ppb.*, ob.name, ob.is_deleted,
count(pba.user_id) as admin_count,
op.name as party_name, op.is_deleted as party_is_delete, op.sort_order as party_sort_order
from pmd_pay_branch ppb
left join ow_branch ob on ppb.branch_id=ob.id
left join ow_party op on ppb.party_id=op.id
left join pmd_branch_admin pba on pba.branch_id=ppb.branch_id
group by ppb.party_id, ppb.branch_id;

DROP VIEW IF EXISTS `pmd_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_party_view` AS
select pp.*,pm.pay_month, pm.status as month_status,pm.pay_status as month_pay_status,pm.pay_tip as month_pay_tip from pmd_party pp
left join pmd_month pm on pp.month_id=pm.id ;

DROP VIEW IF EXISTS `pmd_config_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_config_member_view` AS
SELECT  if(isnull(pcm.config_member_type),if(u.type in(2,3,4), 3, if(u.type=5, 2, u.type)), pcm.config_member_type) config_member_type ,pcm.config_member_type_id,pcm.due_pay,pcm.salary,om.user_id,om.party_id,om.branch_id
from pmd_config_member pcm
left join sys_teacher_info sti on sti.user_id= pcm.user_id
left join ow_member om on pcm.user_id= om.user_id
left join sys_user u on pcm.user_id= u.id ;

DROP VIEW IF EXISTS `pmd_branch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_branch_view` AS
select pb.*,pm.pay_month, pm.status as month_status from pmd_branch pb
left join pmd_month pm on pb.month_id=pm.id;

-- 缴费账单，用于对账
DROP VIEW IF EXISTS `pmd_pay_view`;
CREATE ALGORITHM = UNDEFINED VIEW `pmd_pay_view` AS select group_concat(po_check.sn) as real_order_no,
group_concat(po_check.payer) as payer, group_concat(po_check.payername) as payername ,
t.*, uv.code, uv.realname, ouv.code as order_code, ouv.realname as order_realname, po.create_time from
(
-- 本月正常缴费
select  pmp.order_no, m.id as pay_month_id, m.pay_month, pm.user_id, pmp.order_user_id, pmp.member_id, pm.real_pay, 0 as is_delay, pmp.charge_party_id, pmp.charge_branch_id, pmp.pay_time
from pmd_member pm, pmd_member_pay pmp, pmd_month m
where  pm.month_id=m.id and pmp.member_id=pm.id and pm.is_online_pay=1 and pm.is_delay=0
union all
-- 往月延迟缴费
select pmpv.order_no, m.id as pay_month_id, m.pay_month, pmpv.user_id, pmpv.order_user_id, pmpv.member_id, pmpv.real_pay, 1 as is_delay, pmpv.charge_party_id, pmpv.charge_branch_id, pmpv.pay_time
from pmd_member_pay_view pmpv, pmd_month m
where pmpv.pay_month_id=m.id and pmpv.month_id < m.id and pmpv.has_pay=1 and pmpv.is_delay=1 and pmpv.is_online_pay=1
) t
left join pmd_order po on po.sn=t.order_no
left join pmd_order po_check on po_check.type=1 and po_check.record_id=t.member_id and po_check.is_success=1
left join sys_user_view uv on t.user_id=uv.id
left join sys_user_view ouv on t.order_user_id=ouv.id
group by member_id;

-- 缴费订单，用于对账
DROP VIEW IF EXISTS `pmd_pay_item_view`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `pmd_pay_item_view` AS SELECT t.* from
(
-- 本月正常缴费
select  pmp.order_no, m.id as pay_month_id, m.pay_month, pm.user_id, pmp.order_user_id, pmp.member_id, pm.real_pay, 0 as is_delay, pmp.pay_time
from pmd_member pm, pmd_member_pay pmp, pmd_month m
where  pm.month_id=m.id and pmp.member_id=pm.id and pm.is_online_pay=1 and pm.is_delay=0
union all
-- 往月延迟缴费
select pmpv.order_no, m.id as pay_month_id, m.pay_month, pmpv.user_id, pmpv.order_user_id, pmpv.member_id, pmpv.real_pay, 1 as is_delay, pmpv.pay_time
from pmd_member_pay_view pmpv, pmd_month m
where pmpv.pay_month_id=m.id and pmpv.month_id < m.id and pmpv.has_pay=1 and pmpv.is_delay=1 and pmpv.is_online_pay=1
) t, pmd_order po_check WHERE  po_check.type=1 and po_check.record_id=t.member_id and po_check.is_success=1 ;

