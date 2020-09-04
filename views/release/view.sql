
DROP VIEW IF EXISTS `cadre_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cadre_party_view`
AS SELECT cp.*, cv.id as cadre_id, cv.code, cv.realname, cv.unit_id, cv.ow_grow_time, cv.member_status, cv.admin_level,
cv.post_type, cv.title AS cadre_title, cv.post AS cadre_post,
cv.status as cadre_status,cv.sort_order AS cadre_sort_order
FROM cadre_party cp
LEFT JOIN cadre_view cv ON cp.user_id=cv.user_id ;

-- 干部教育培训

DROP VIEW IF EXISTS `cet_unit_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_unit_view` AS
select cu.*,u.code as unit_code, u.name as unit_name, u.type_id as unit_type_id, u.status as unit_status,
u.sort_order from cet_unit cu left join unit u on cu.unit_id=u.id ;

/*DROP VIEW IF EXISTS `cet_party_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_party_view` AS
select cp.*,COUNT(cpa.user_id) AS admin_count
from cet_party cp
left JOIN cet_party_admin cpa on cp.id=cpa.cet_party_id
GROUP BY cp.id;*/

/*DROP VIEW IF EXISTS `cet_project_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_project_view` AS
select cp.*, count(cpo.id) as obj_count from cet_project cp
left join cet_project_obj cpo on cpo.project_id=cp.id group by cp.id;*/

DROP VIEW IF EXISTS `cet_column_course_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_column_course_view` AS
SELECT ccc.*, fcc.id as f_column_id, fcc.name as f_column_name,
cc.name as column_name, cc2.name as course_name, ce.realname from cet_column_course ccc
left join cet_column cc on ccc.column_id = cc.id
left join cet_column fcc on fcc.id = cc.fid
left join cet_course cc2 on ccc.course_id = cc2.id
left join cet_expert ce on cc2.expert_id=ce.id;

DROP VIEW IF EXISTS `cet_column_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_column_view` AS
select cc.*, count(cc2.id) as child_num, count(ccc.id) as course_num from cet_column cc
left join cet_column_course ccc on ccc.column_id=cc.id
left join cet_column cc2 on cc2.fid=cc.id
group by cc.id ;

/*DROP VIEW IF EXISTS `cet_train_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_train_view` AS
select ct.*, cp.year, cpp.project_id from cet_train ct
left join cet_project_plan cpp on cpp.id=ct.plan_id
left join cet_project cp on cp.id = cpp.project_id;*/

/*DROP VIEW IF EXISTS `cet_train_course_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_train_course_view` AS
select ctc.*,
-- 选课人数
cteec.selected_count,
-- 完成上课人数
cteec.finish_count,
-- 测评完成账号数量
ctic.eva_finish_count
from cet_train_course ctc
left join (select train_course_id, count(id) as selected_count, sum(if(is_finished, 1,0)) as finish_count
from  cet_train_obj group by train_course_id) cteec on ctc.id=cteec.train_course_id
left join (select train_course_id, count(id) as eva_finish_count from cet_train_inspector_course group by train_course_id)
 ctic on ctc.id=ctic.train_course_id;*/

DROP VIEW IF EXISTS `cet_train_obj_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_train_obj_view` AS
select cto.*,cpo.project_id as project_id,cpo.trainee_type_id as trainee_type_id,
cpo.is_quit, ct.plan_id, ctc.train_id, ctc.course_id as course_id, ctc.period as period,cp.year as year,uv.code as choose_user_code,uv.realname as choose_user_name
from cet_train_obj cto
left join cet_project_obj cpo on cpo.id=cto.obj_id
left join cet_train_course ctc on ctc.id=cto.train_course_id
left join cet_train ct on ct.id=ctc.train_id
left join cet_project cp on cp.id=cpo.project_id
left join sys_user_view uv on uv.id=cto.choose_user_id order by cpo.id;

DROP VIEW IF EXISTS `cet_trainee_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_trainee_view` AS
select user_id, obj_id, trainee_type_id, project_id, plan_id, train_id, is_quit as obj_is_quit,
       count(*) as course_count,
       sum(if(is_finished, 1, 0)) as finish_count,
       sum(period) as total_period,
       sum(if(is_finished, period, 0)) as finish_period
from cet_train_obj_view group by user_id, train_id, project_id;

DROP VIEW IF EXISTS `cet_train_inspector_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_train_inspector_view` AS
select cti.*, ctic.finish_course_num, ctic.save_course_num, cteev.course_count as selected_course_num,
(cteev.course_count - if(isnull(ctic.finish_course_num), 0, ctic.finish_course_num)) as un_eva_course_num
 from cet_train_inspector cti
left join (select inspector_id, sum(if(status=1, 1,0)) as finish_course_num,
sum(if(status=0, 1,0)) as save_course_num from cet_train_inspector_course group by inspector_id) ctic on ctic.inspector_id=cti.id
left join sys_user u on u.code=cti.mobile
left join cet_trainee_view cteev
on cteev.train_id=cti.train_id and cteev.user_id=u.id
group by cti.id ;

-- 培训班下的每个课程的统计结果（课程得分、已选人数、完成人数、完成测评人数）
DROP VIEW IF EXISTS `cet_train_course_stat_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_train_course_stat_view` AS
select ctc.*, ct.name as train_name,  round(sum(tmp.total_score)/count(tmp.inspector_id),1) as score from cet_train_course ctc
left join cet_train ct on ct.id=ctc.train_id and ct.is_deleted=0
 left join (
 -- 培训班内，每个测评账号对每个课程打分情况
select result.inspector_id, ic.train_course_id, ctc.train_id,  ctc.course_id, sum(rank.score) as total_score
from cet_train_eva_result result, cet_train_eva_rank rank, cet_train_inspector_course ic,
cet_train_course ctc where rank.id=result.rank_id and
ic.train_course_id=result.train_course_id and ic.train_course_id=ctc.id and
ic.inspector_id=result.inspector_id group by result.inspector_id, result.train_course_id
) tmp on tmp.train_course_id=ctc.id
group by ctc.id;

DROP VIEW IF EXISTS `cet_expert_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_expert_view` AS
select ce.* , count(distinct cc.id) as course_num, count(distinct cto.train_course_id, cto.obj_id) as trainee_num from cet_expert ce
left join cet_course cc on cc.expert_id=ce.id
left join cet_train_course ctc on ctc.course_id=cc.id
left join cet_train_obj cto on cto.train_course_id=ctc.id
group by ce.id;


-- 民主推荐
DROP VIEW IF EXISTS `dr_offline_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_offline_view` AS
select do.*, sr.seq as sr_seq, sm.hold_date, sm.sc_type, sm.unit_post_id, up.name as post_name,
up.job, up.admin_level, up.post_type, up.unit_id,
u.type_id as unit_type from dr_offline do
left join sc_record sr on sr.id=do.record_id
left join sc_motion sm on sm.id= sr.motion_id
left join unit_post up on up.id = sm.unit_post_id
left join unit u on u.id = up.unit_id ;

DROP VIEW IF EXISTS `dr_online_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_online_post_view` AS
SELECT dop.*,do.type AS online_type,ifnull(doc1.exist_num,0) AS exist_num,
up.name as post_name, up.job,up.admin_level,up.post_type,up.unit_id,u.type_id
FROM dr_online_post dop
LEFT join dr_online do ON(do.id=dop.online_id)
LEFT JOIN unit_post up ON(up.id=dop.unit_post_id)
LEFT JOIN unit u ON(u.id=up.unit_id)
LEFT JOIN (SELECT doc.post_id,COUNT(doc.id) AS exist_num FROM dr_online_candidate doc GROUP BY post_id) doc1 ON (doc1.post_id=dop.id);


-- 动议
DROP VIEW IF EXISTS `sc_motion_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_motion_view` AS
select sm.*, up.name as post_name, up.job, up.admin_level, up.post_type, up.unit_id, u.type_id as unit_type
from sc_motion sm
       left join unit_post up on up.id = sm.unit_post_id
       left join unit u on u.id = up.unit_id;

-- 纪实
DROP VIEW IF EXISTS `sc_record_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_record_view` AS
select sr.*, sm.hold_date, sm.sc_type, sm.unit_post_id, up.code AS post_code, up.name as post_name, up.job, up.admin_level, up.post_type, up.unit_id,
 u.type_id as unit_type from sc_record sr
left join sc_motion sm on sm.id=sr.motion_id
left join unit_post up on up.id = sm.unit_post_id
left join unit u on u.id = up.unit_id;

-- 干部津贴变动
DROP VIEW IF EXISTS `sc_subsidy_cadre_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_subsidy_cadre_view`
AS select ssc.*, ss.info_date, ss.year, ss.hr_type, ss.hr_num, ss.hr_file_path, ss.fe_type, ss.fe_num, ss.fe_file_path
from sc_subsidy_cadre ssc left join sc_subsidy ss on ssc.subsidy_id=ss.id ;

DROP VIEW IF EXISTS `sc_subsidy_dispatch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_subsidy_dispatch_view` AS
select ssd.*, ss.info_date, ss.year, ss.hr_type, ss.hr_num, ss.hr_file_path, ss.fe_type, ss.fe_num, ss.fe_file_path
from sc_subsidy_dispatch ssd left join sc_subsidy ss on ssd.subsidy_id=ss.id ;

-- 干部任免审批表归档
DROP VIEW IF EXISTS `sc_ad_archive_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_ad_archive_view` AS
select saa.*, sc.year, sc.file_path as committee_file_path, sc.hold_date, cv.code, cv.realname  from sc_ad_archive saa
left join sc_committee sc on sc.id = saa.committee_id
left join cadre_view cv on cv.id = saa.cadre_id ;

-- 文件起草签发
DROP VIEW IF EXISTS `sc_dispatch_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_dispatch_view` AS
 select sd.*, d.id as dispatch_id, d.file as dispatch_file, d.file_name as dispatch_file_name,
 dt.sort_order as dispatch_type_sort_order, sum(if(sdu.type=1, 1, 0)) as appoint_count,
 sum(if(sdu.type=2, 1, 0))  as dismiss_count from sc_dispatch sd
left join sc_dispatch_user sdu on sdu.dispatch_id=sd.id
left join dispatch_type dt on sd.dispatch_type_id = dt.id
left join dispatch d on d.sc_dispatch_id=sd.id
group by sd.id ;


-- 常委会
DROP VIEW IF EXISTS `sc_committee_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_member_view` AS
select distinct scm.*, uv.username, uv.code, uv.realname, c.id as cadre_id, c.title, c.post from sc_committee_member scm
left join sys_user_view uv on uv.id=scm.user_id
left join cadre_view c on c.user_id=scm.user_id;

DROP VIEW IF EXISTS `sc_committee_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_view` AS
select sc.*, sum(if(scm.is_absent, 0, 1)) as count, sum(if(scm.is_absent, 1, 0)) as absent_count from sc_committee sc
left join sc_committee_member scm on scm.committee_id=sc.id
group by sc.id  ;

DROP VIEW IF EXISTS `sc_committee_topic_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_topic_view` AS
select sct.*, sc.year, sc.hold_date, sc.committee_member_count, sc.count, sc.absent_count,
sc.attend_users, sc.file_path, sc.log_file from sc_committee_topic sct
left join sc_committee_view sc on sc.id=sct.committee_id
where sc.is_deleted=0 ;

DROP VIEW IF EXISTS `sc_committee_vote_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_vote_view` AS
select scv.*, sct.name, sct.seq, sct.content, sct.committee_id, sct.vote_file_path, sct.record_user_id,
sctc.original_post, sctc.original_post_time,
sc.year, sc.hold_date, sc.committee_member_count, sc.count, sc.absent_count, sc.attend_users, sc.file_path, sc.log_file,
-- 已使用的ID
sdu.id as dispatch_user_id
from sc_committee_vote scv
left join sc_committee_topic sct on sct.id=scv.topic_id
left join sc_committee_topic_cadre sctc on sctc.topic_id=scv.topic_id and sctc.cadre_id=scv.cadre_id
left join sc_committee_view sc on sc.id=sct.committee_id
left join sc_dispatch_user sdu on sdu.vote_id=scv.id;


DROP VIEW IF EXISTS `sc_committee_other_vote_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_committee_other_vote_view` AS
select scov.*, sct.name, sct.content, sct.committee_id, sc.year,
sc.hold_date, sc.committee_member_count, sc.count, sc.absent_count, sc.attend_users, sc.file_path, sc.log_file
from sc_committee_other_vote scov
left join sc_committee_topic sct on sct.id=scov.topic_id
left join sc_committee_view sc on sc.id=sct.committee_id;

DROP VIEW IF EXISTS `sc_public_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_public_user_view` AS
SELECT spu.*, sp.year, sp.committee_id, sp.publish_date, sp.public_start_date, sp.public_end_date,
sp.pdf_file_path, sp.word_file_path, sp.record_user_id,
sctc.original_post, scv.cadre_id, scv.post  from sc_public_user spu
LEFT JOIN sc_public sp ON sp.id=spu.public_id
LEFT JOIN sc_committee_vote scv ON scv.id=spu.vote_id
left join sc_committee_topic_cadre sctc on sctc.topic_id=scv.topic_id and sctc.cadre_id=scv.cadre_id;

-- 干部小组会
DROP VIEW IF EXISTS `sc_group_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_group_member_view` AS
select distinct sgm.*, uv.username, uv.code, uv.realname, c.id as cadre_id, c.title from sc_group_member sgm
left join sys_user_view uv on uv.id=sgm.user_id
left join cadre c on c.user_id=sgm.user_id;

DROP VIEW IF EXISTS `sc_group_topic_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_group_topic_view` AS
select sgt.*, sg.year, sg.hold_date, sg.file_path as group_file_path, sg.log_file, sg.attend_users, sgtu.unit_ids
 from sc_group_topic sgt
left join sc_group sg on sgt.group_id = sg.id
left join (select group_concat(unit_id) as unit_ids, topic_id from sc_group_topic_unit group by topic_id) sgtu on sgtu.topic_id=sgt.id
where sg.is_deleted=0;
-- 纪委函询视图
DROP VIEW IF EXISTS `sc_letter_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_letter_view` AS
select sl.*, count(distinct sli.id) as item_count, count(distinct slr.id) as reply_count  from sc_letter sl
left join sc_letter_item sli on sli.letter_id=sl.id
left join sc_letter_reply slr on slr.letter_id=sl.id group by sl.id;

DROP VIEW IF EXISTS `sc_letter_reply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_letter_reply_view` AS
select slr.*, sl.year as letter_year, sl.num as letter_num,
sl.file_path as letter_file_path, sl.file_name as letter_file_name,
sl.query_date as letter_query_date, sl.type as letter_type
, count(distinct slri.id) as reply_item_count from sc_letter_reply slr
left join sc_letter sl on sl.id=slr.letter_id
left join sc_letter_reply_item slri on slri.reply_id=slr.id group by slr.id;

DROP VIEW IF EXISTS `sc_letter_reply_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_letter_reply_item_view` AS
select slri.*, sli.id as item_id, sli.record_id, sli.record_ids, sli.record_user_id,
slr.letter_id, slr.type as reply_type, slr.reply_date, slr.num as reply_num,
slr.file_path as reply_file_path, slr.file_name as reply_file_name,
sl.year as letter_year, sl.num as letter_num,
sl.file_path as letter_file_path, sl.file_name as letter_file_name,
sl.query_date as letter_query_date, sl.type as letter_type, u.realname, u.code from sc_letter_reply_item slri
left join sys_user_view u on slri.user_id=u.id
left join sc_letter_reply slr on slr.id=slri.reply_id and slr.is_deleted=0
left join sc_letter sl on sl.id=slr.letter_id and sl.is_deleted=0
LEFT JOIN sc_letter_item sli ON sli.letter_id=sl.id AND sli.user_id=u.id;

-- 出入境备案视图
DROP VIEW IF EXISTS `sc_border_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_border_view` AS
select sb.*, count(sbi.id) as item_count from sc_border sb
left join sc_border_item sbi on sbi.border_id=sb.id
group by sb.id ;

DROP VIEW IF EXISTS `sc_border_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_border_item_view` AS
select bi.*, b.year, b.record_date, b.add_file, b.change_file, b.delete_file, b.record_file from sc_border_item bi
left join sc_border b on b.id=bi.border_id ;

-- 个人有关事项视图
DROP VIEW IF EXISTS `sc_matter_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_view` AS
select m.*, count(mi.id) as item_count, sum(if(isnull(mi.real_hand_time), 0, 1)) as hand_item_count
from sc_matter m left join sc_matter_item mi on mi.matter_id=m.id group by m.id ;

DROP VIEW IF EXISTS `sc_matter_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_item_view` AS
select smi.*, sm.year, sm.type, sm.draw_time, sm.hand_time, u.code, u.realname, c.title,
smt.transfer_date, smt.location as transfer_location, smt.reason as transfer_reason  from sc_matter_item smi
left join sc_matter sm on sm.id=smi.matter_id
left join sys_user_view u on smi.user_id = u.id
left join cadre_view c on c.user_id=smi.user_id
left join sc_matter_transfer smt on smt.id=smi.transfer_id;

DROP VIEW IF EXISTS `sc_matter_access_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_access_item_view` AS
select smai.*, smi.code, smi.realname, smi.type, smi.year,smi.fill_time, c.title from sc_matter_access_item smai
left join sc_matter_item_view smi on smi.id=smai.matter_item_id
left join cadre_view c on c.user_id=smi.user_id;

DROP VIEW IF EXISTS `sc_matter_check_item_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_check_item_view` AS
select smci.*, smc.year, smc.is_random, smc.check_date, smc.num, u.code, u.realname
from sc_matter_check_item smci
left join sc_matter_check smc on smc.id=smci.check_id
left join sys_user_view u on u.id=smci.user_id;

DROP VIEW IF EXISTS `sc_matter_check_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_check_view` AS
select smc.*, count(distinct smci.id) as item_count, sum(if(isnull(smci.confirm_type), 0,1)) item_check_count
from  sc_matter_check smc
left join sc_matter_check_item smci on smci.check_id=smc.id group by smc.id;

DROP VIEW IF EXISTS `sc_matter_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `sc_matter_user_view` AS
select distinct smi.user_id, uv.username, uv.code, uv.realname from sc_matter_item smi
left join sys_user_view uv on uv.id=smi.user_id;


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
select pp.*,pm.pay_month, pm.status as month_status from pmd_party pp
left join pmd_month pm on pp.month_id=pm.id ;

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
select  pmp.order_no, m.id as pay_month_id, m.pay_month, pm.user_id, pmp.order_user_id, pmp.member_id, pm.real_pay, 0 as is_delay, pmp.pay_time
from pmd_member pm, pmd_member_pay pmp, pmd_month m
where  pm.month_id=m.id and pmp.member_id=pm.id and pm.is_online_pay=1 and pm.is_delay=0
union all
-- 往月延迟缴费
select pmpv.order_no, m.id as pay_month_id, m.pay_month, pmpv.user_id, pmpv.order_user_id, pmpv.member_id, pmpv.real_pay, 1 as is_delay, pmpv.pay_time
from pmd_member_pay_view pmpv, pmd_month m
where pmpv.pay_month_id=m.id and pmpv.month_id < m.id and pmpv.has_pay=1 and pmpv.is_delay=1 and pmpv.is_online_pay=1
) t
left join pmd_order po on po.sn=t.order_no
left join pmd_order po_check on po_check.member_id=t.member_id and po_check.is_success=1
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
) t, pmd_order po_check WHERE  po_check.member_id=t.member_id and po_check.is_success=1 ;


/*DROP VIEW IF EXISTS `oa_task_view`;
CREATE ALGORITHM = UNDEFINED VIEW `oa_task_view`
AS select ot.*, count(distinct otf.id) as file_count,
-- 任务对象数量
count(distinct otu.id) as user_count,
-- 已完成数
count(distinct otu2.id) as finish_count from oa_task ot
left join oa_task_file otf on otf.task_id=ot.id
left join oa_task_user otu on otu.task_id = ot.id and otu.is_delete=0
left join oa_task_user otu2 on otu2.task_id = ot.id and otu2.is_delete=0 and otu2.status=1 group by ot.id;*/

DROP VIEW IF EXISTS `oa_task_user_view`;
CREATE ALGORITHM = UNDEFINED VIEW `oa_task_user_view` AS
select otu.*, uv.code, uv.realname, ot.name as task_name, ot.content as task_content,
ot.user_id as task_user_id, ot.user_ids as task_user_ids,
ot.deadline as task_deadline, ot.user_file_count, ot.contact as task_contact,
ot.is_delete as task_is_delete, ot.is_publish as task_is_publish, ot.status as task_status,
ot.pub_date as task_pub_date, ot.type as task_type,
ouv.code as assign_code, ouv.realname as assign_realname,
ruv.code as report_code, ruv.realname as report_realname from oa_task_user otu
left join oa_task ot on otu.task_id = ot.id
left join sys_user_view uv on otu.user_id = uv.id
left join sys_user_view ouv on otu.assign_user_id = ouv.id
left join sys_user_view ruv on otu.report_user_id = ruv.id;

DROP VIEW IF EXISTS `cm_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cm_member_view` AS
select cm.*, pcs.name as pcs_name, uv.code, uv.realname, uv.gender, uv.birth,
uv.nation, t.pro_post, c.ow_grow_time as grow_time, c.id as cadre_id,c.title from cm_member cm
left join pcs_config pcs on pcs.id=cm.config_id
left join sys_user_view uv on cm.user_id = uv.id
left join sys_teacher_info t on t.user_id=cm.user_id
left join cadre_view c on c.user_id=cm.user_id;

--

DROP VIEW IF EXISTS `crs_expert_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_expert_view` AS
select ce.*, uv.username, uv.code, uv.realname,
c.id as cadre_id, c.title as cadre_title, c.status as cadre_status, c.sort_order as cadre_sort_order
from crs_expert ce
left join cadre c on ce.user_id=c.user_id
left join sys_user_view uv on ce.user_id=uv.id;


DROP VIEW IF EXISTS `crs_applicant_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_applicant_view` AS
 select *, if(special_status||require_check_status=1, 1, 0) as is_require_check_pass from crs_applicant ;

-- 作废 20191010
DROP VIEW IF EXISTS `crs_candidate_view`;

DROP VIEW IF EXISTS `crs_applicant_stat_view`;
CREATE ALGORITHM = UNDEFINED VIEW `crs_applicant_stat_view` AS
select ca.*, cv.realname, cc.is_first, cpec.expert_count, cavc.applicant_count,
cp.type as crs_post_type,cp.year as crs_post_year,cp.seq as crs_post_seq,
cp.name as crs_post_name,cp.job as crs_post_job,cp.status as crs_post_status
 from crs_applicant_view ca
  left join crs_candidate cc on cc.user_id=ca.user_id and cc.post_id=ca.post_id
 left join cadre_view cv on cv.user_id=ca.user_id
left join crs_post cp on cp.id = ca.post_id
left join (select post_id, count(*) as expert_count from crs_post_expert cpe group by post_id) as cpec on cpec.post_id=ca.post_id
left join (select post_id, count(*) as applicant_count from crs_applicant_view where status=1 and is_require_check_pass=1  group by post_id) as cavc on cavc.post_id=ca.post_id;

DROP VIEW IF EXISTS `ow_member_out_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_out_view` AS
select mo.*, m.type as member_type, t.is_retire
from ow_member_out mo, ow_member m
left join sys_teacher_info t on t.user_id = m.user_id where mo.user_id=m.user_id;


-- ----------------------------
-- 2017.6.5 View definition for `ow_party_static_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_static_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_party_static_view` AS
select p.id, p.name,
s.bks, s.ss, s.bs, (s.bks+s.ss+s.bs) as student, s.positive_bks, s.positive_ss, s.positive_bs, (s.positive_bks + s.positive_ss + s.positive_bs) as positive_student,
t.teacher,t.teacher_retire, (t.teacher+t.teacher_retire) as teacher_total, t.positive_teacher, t.positive_teacher_retire, (t.positive_teacher + t.positive_teacher_retire)as positive_teacher_total,
b.bks_branch, b.ss_branch, b.bs_branch, b.sb_branch, b.bsb_branch,
(b.bks_branch + b.ss_branch + b.bs_branch + b.sb_branch + b.bsb_branch) as student_branch_total, b.teacher_branch, b.retire_branch, (b.teacher_branch + b.retire_branch) as teacher_branch_total,
a.teacher_apply_count, a.student_apply_count
from ow_party p left join
(
select party_id,
sum(if(edu_level is null, 1, 0)) as bks,
sum(if(edu_level='硕士', 1, 0)) as ss,
sum(if(edu_level='博士', 1, 0)) as bs,
sum(if(edu_level is null and political_status=2, 1, 0)) as positive_bks,
sum(if(edu_level='硕士' and political_status=2, 1, 0)) as positive_ss,
sum(if(edu_level='博士' and political_status=2, 1, 0)) as positive_bs
from ow_member_view where type=2 and status=1 group by party_id
) s on s.party_id = p.id
left join
(
select party_id,
sum(if(is_retire, 0, 1)) teacher,
sum(if(is_retire, 1, 0)) teacher_retire,
sum(if(!is_retire and political_status=2, 1, 0)) positive_teacher,
sum(if(is_retire and political_status=2, 1, 0)) positive_teacher_retire
from ow_member_view where type=1 and status=1 group by party_id
) t on t.party_id = p.id
left join
(select b.party_id,
sum(if(locate('本科生',bmt.name), 1, 0)) as bks_branch,
sum(if(locate('硕士',bmt.name), 1, 0)) as ss_branch,
sum(if(locate('博士',bmt.name), 1, 0)) as bs_branch,
sum(if(POSITION('硕博' in bmt.name)=1, 1, 0)) as sb_branch,
sum(if(locate('本硕博',bmt.name), 1, 0)) as bsb_branch,
sum(if(locate('在职',bmt.name), 1, 0)) as teacher_branch,
sum(if(locate('离退休',bmt.name), 1, 0)) as retire_branch
from ow_branch b, base_meta_type bmt where b.is_deleted=0 and find_in_set(bmt.id, b.types) group by b.party_id
)b on b.party_id = p.id
left join
(select p.id as party_id, sum(if(type=1, 1, 0)) as teacher_apply_count, sum(if(type=2, 1, 0)) as student_apply_count from ow_member_apply oma
left join ow_party p on oma.party_id=p.id
left join ow_branch b on oma.branch_id=b.id
where p.is_deleted=0 and (b.is_deleted=0 or b.id is null) group by p.id
)a on a.party_id = p.id
where p.is_deleted=0 order by p.sort_order desc;
-- ----------------------------
--  View definition for `abroad_passport_apply_view`
-- ----------------------------
DROP VIEW IF EXISTS `abroad_passport_apply_view`;
CREATE ALGORITHM = UNDEFINED  VIEW `abroad_passport_apply_view` AS
select apa.`*` , ap.id as passport_id, ap.code from abroad_passport_apply apa  left join abroad_passport ap on ap.apply_id=apa.id ;

DROP VIEW IF EXISTS `abroad_additional_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `abroad_additional_post_view` AS
select aap.*, c.code, c.realname, c.title, c.status as cadre_status, c.sort_order as cadre_sort_order, u.sort_order as unit_sort_order from abroad_additional_post aap
left join cadre_view c on aap.cadre_id=c.id
left join unit u on aap.unit_id=u.id ;


DROP VIEW IF EXISTS `cla_additional_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cla_additional_post_view` AS
select cap.*, c.code, c.realname, c.title, c.status as cadre_status, c.sort_order as cadre_sort_order, u.sort_order as unit_sort_order from cla_additional_post cap
left join cadre_view c on cap.cadre_id=c.id
left join unit u on cap.unit_id=u.id ;



DROP VIEW IF EXISTS `cadre_company_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cadre_company_view` AS
select cc.*, c.code, c.realname, c.title, c.`status` as cadre_status, c.admin_level, c.admin_level_code,
c.is_double, c.unit_type_id, c.unit_type_group,
c.sort_order as cadre_sort_order from cadre_company cc
left join cadre_view c on c.id=cc.cadre_id;

-- ----------------------------
--  View definition for `cis_inspector_view`
-- ----------------------------
/*DROP VIEW IF EXISTS `cis_inspector_view`;
CREATE ALGORITHM=UNDEFINED VIEW `cis_inspector_view` AS
select ci.*, uv.username, uv.code, uv.realname
from cis_inspector ci left join sys_user_view uv on ci.user_id = uv.id;*/
-- ----------------------------
--  View definition for `cis_inspect_obj_view`
-- ----------------------------
DROP VIEW IF EXISTS `cis_inspect_obj_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cis_inspect_obj_view` AS
select cio.*, saa.id as archive_id from cis_inspect_obj cio
left join sc_ad_archive saa on saa.obj_id=cio.id, base_meta_type bmt
where cio.type_id=bmt.id order by cio.year desc, bmt.sort_order desc, cio.seq desc;
-- ----------------------------
--  View definition for `dispatch_cadre_view`
-- ----------------------------
DROP VIEW IF EXISTS `dispatch_cadre_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dispatch_cadre_view` AS
select dc.*,up.name as post_name,up.group_id,d.category, d.year, d.pub_time,d.work_time,
d.dispatch_type_id, d.code , d.has_checked,d.record_user_id
from dispatch_cadre dc
left join unit_post up on up.id=dc.unit_post_id, dispatch d, dispatch_type dt
where dc.dispatch_id = d.id and  d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc, dc.type asc;

-- ----------------------------
DROP VIEW IF EXISTS `dispatch_unit_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dispatch_unit_view` AS
select du.*,
       d.category, d.year, d.pub_time, d.dispatch_type_id,
       d.code , d.has_checked
from dispatch_unit du, dispatch d, dispatch_type dt
where du.dispatch_id = d.id and  d.dispatch_type_id = dt.id
order by d.year desc, dt.sort_order desc, d.code desc, du.type asc;

-- ----------------------------
--  View definition for `dispatch_view`
-- ----------------------------
DROP VIEW IF EXISTS `dispatch_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dispatch_view` AS
select d.* from dispatch d, dispatch_type dt
    where d.dispatch_type_id = dt.id order by d.year desc, dt.sort_order desc, d.code desc ;


DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id,
u.status as unit_status, u.sort_order as unit_sort_order,
upg.name as group_name,
cp.cadre_id, cp.id as cadre_post_id, cp.admin_level as cp_admin_level, cp.is_main_post,
cv.gender, cv.admin_level as cadre_admin_level, cv.post_type as cadre_post_type,cv.lp_work_time as lp_work_time,cv.s_work_time as s_work_time,
cv.is_principal as cadre_is_principal, cv.cadre_post_year, cv.admin_level_year from unit_post up
left join unit u on up.unit_id=u.id
left join unit_post_group upg on up.group_id=upg.id
left join cadre_post cp on up.id=cp.unit_post_id
left join cadre_view cv on cv.id=cp.cadre_id;


-- ----------------------------
--  View definition for `ow_branch_member_group_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_branch_member_group_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_branch_member_group_view` AS
SELECT bmg.`*`, b.party_id, p.sort_order as party_sort_order, b.sort_order as branch_sort_order, count(obm.id) as member_count
from ow_branch_member_group bmg
left join ow_branch_member obm on obm.is_history=0 and obm.group_id=bmg.id
left join ow_branch b on bmg.branch_id=b.id
left join ow_party p on b.party_id=p.id group by bmg.id;



-- ----------------------------
--  View definition for `ow_member_apply_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_apply_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_member_apply_view` AS
select ma.*, m.status as _status, if((m.status is null or m.status=1), 0, 1) as member_status
     , p.sort_order as party_sort_order, b.sort_order as branch_sort_order
from  ow_member_apply ma
        left join ow_branch b on ma.branch_id=b.id
        left join ow_party p on b.party_id=p.id
        left join ow_member m  on ma.user_id = m.user_id;

-- ----------------------------
--  View definition for `ow_member_outflow_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_outflow_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_member_outflow_view` AS
SELECT omo.*, om.`status` as member_status from ow_member_outflow omo, ow_member om where omo.user_id=om.user_id  ;

-- ----------------------------
--  View definition for `ow_member_stay_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_member_stay_view`;
CREATE ALGORITHM=UNDEFINED  SQL SECURITY DEFINER
VIEW `ow_member_stay_view` AS SELECT wms.*,  om.`status` as member_status
from ow_member_stay wms left join ow_member om
on wms.user_id=om.user_id  ;

-- ----------------------------
--  View definition for `ow_party_member_group_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_member_group_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_member_group_view` AS
select opmg.*, op.class_id as party_class_id, op.sort_order as party_sort_order, count(opm.id) as member_count from ow_party_member_group opmg
left join ow_party_member opm on opm.is_history=0 and opm.group_id=opmg.id
left join  ow_party op on opmg.party_id=op.id group by opmg.id;

-- ----------------------------
--  View definition for `ow_party_member_view`
-- ----------------------------
DROP VIEW IF EXISTS `ow_party_member_view`;
CREATE ALGORITHM=UNDEFINED VIEW `ow_party_member_view` AS
select opm.*,
`ui`.`msg_title` AS `msg_title`
	,`ui`.`email` AS `email`
	,`ui`.`realname` AS `realname`
	,`ui`.`gender` AS `gender`
	,`ui`.`nation` AS `nation`
	,`ui`.`native_place` AS `native_place`
	,`ui`.`idcard` AS `idcard`
	,`ui`.`birth` AS `birth`
	,`om`.`party_id` AS `party_id`
	,`om`.`branch_id` AS `branch_id`
	,`om`.`status` AS `member_status`
	, opmg.party_id as group_party_id, opmg.is_present, opmg.is_deleted
	, op.class_id as party_class_id
	, op.unit_id
	, op.sort_order as party_sort_order
	,`t`.`post_class` AS `post_class`
	,`t`.`sub_post_class` AS `sub_post_class`
	,`t`.`main_post_level` AS `main_post_level`
	,`t`.`pro_post_time` AS `pro_post_time`
	,`t`.`pro_post_level` AS `pro_post_level`
	,`t`.`pro_post_level_time` AS `pro_post_level_time`
	,`t`.`pro_post` AS `pro_post`
	,`t`.`manage_level` AS `manage_level`
	,`t`.`manage_level_time` AS `manage_level_time`
	,`t`.`arrive_time` AS `arrive_time`
	, ow.id as ow_id
	-- 判断是否是中共党员
	, if(!isnull(ow.id) or om.status=1 or om.status=4, 1, 0) as is_ow
	-- 优先以党员库中的入党时间为准
	, if(om.status=1 or om.status=4, om.grow_time, ow.grow_time) as ow_grow_time
     , if(om.status=1 or om.status=4, om.positive_time, DATE_ADD(ow.grow_time, INTERVAL 1 YEAR )) as ow_positive_time
	, ow.remark as ow_remark
	, dp.grow_time as dp_grow_time
  , dp.class_id as dp_type_id
	 from  ow_party_member opm  join ow_party_member_group opmg on opm.group_id=opmg.id
 left join sys_user_info ui on opm.user_id=ui.user_id
 left join ow_member om on opm.user_id=om.user_id
 left join ow_party op on opmg.party_id=op.id
 left join sys_teacher_info t on opm.user_id=t.user_id
left join cadre_party dp on dp.user_id= opm.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= opm.user_id and ow.type = 2;

-- ----------------------------
--  Records 
-- ----------------------------

DROP VIEW IF EXISTS `ow_branch_member_view`;
CREATE ALGORITHM = UNDEFINED VIEW `ow_branch_member_view` AS select obm.*,
`ui`.`msg_title` AS `msg_title`
,`ui`.`email` AS `email`
,`ui`.`realname` AS `realname`
,`ui`.`gender` AS `gender`
,`ui`.`nation` AS `nation`
,`ui`.`native_place` AS `native_place`
,`ui`.`idcard` AS `idcard`
,`ui`.`birth` AS `birth`
,`om`.`party_id` AS `party_id`
,`om`.`branch_id` AS `branch_id`
,`om`.`status` AS `member_status`
, obmg.branch_id as group_branch_id, obmg.is_present,obmg.is_deleted
, op.id as group_party_id
, op.unit_id
, op.sort_order as party_sort_order
, ob.sort_order as branch_sort_order
,`t`.`post_class` AS `post_class`
,`t`.`sub_post_class` AS `sub_post_class`
,`t`.`main_post_level` AS `main_post_level`
,`t`.`pro_post_time` AS `pro_post_time`
,`t`.`pro_post_level` AS `pro_post_level`
,`t`.`pro_post_level_time` AS `pro_post_level_time`
,`t`.`pro_post` AS `pro_post`
,`t`.`manage_level` AS `manage_level`
,`t`.`manage_level_time` AS `manage_level_time`
,`t`.`arrive_time` AS `arrive_time`
, ow.id as ow_id
-- 判断是否是中共党员
, if(!isnull(ow.id) or om.status=1 or om.status=4, 1, 0) as is_ow
-- 优先以党员库中的入党时间为准
, if(om.status=1 or om.status=4, om.grow_time, ow.grow_time) as ow_grow_time
, if(om.status=1 or om.status=4, om.positive_time, DATE_ADD(ow.grow_time, INTERVAL 1 YEAR )) as ow_positive_time
, ow.remark as ow_remark
, dp.grow_time as dp_grow_time
, dp.class_id as dp_type_id
from  ow_branch_member obm  join ow_branch_member_group obmg on obm.group_id=obmg.id
left join sys_user_info ui on obm.user_id=ui.user_id
left join ow_member om on obm.user_id=om.user_id
left join ow_branch ob on obmg.branch_id=ob.id
left join ow_party op on ob.party_id=op.id
left join sys_teacher_info t on obm.user_id=t.user_id
left join cadre_party dp on dp.user_id= obm.user_id and dp.type = 1
left join cadre_party ow on ow.user_id= obm.user_id and ow.type = 2;
