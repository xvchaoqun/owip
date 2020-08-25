-- 党代会投票测试，更新所有的参评人为已测评，并更新统计数量
update pcs_poll_inspector set is_finished=1, is_positive=0 where is_finished=0;

update pcs_poll pp

left join ( select poll_id, count(id) as num1, sum(if(is_positive=1, 1, 0)) as num2 from pcs_poll_inspector group by poll_id) tmp on tmp.poll_id=pp.id

set pp.inspector_finish_num=tmp.num1 , pp.positive_finish_num=tmp.num2