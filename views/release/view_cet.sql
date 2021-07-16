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
select cto.*,cpo.project_id as project_id, cpo.trainee_type_id as trainee_type_id,
cp.name as project_name, cp.type as project_type, cp.is_party_project,
       cp.start_date, cp.end_date, cp.cet_party_id, cp.status as project_status, cp.is_deleted as project_is_deleted,
cpo.is_quit, ct.plan_id, ctc.train_id, ctc.course_id as course_id,
ctc.period as period, ctc.is_online, cp.year as year,uv.code as choose_user_code,uv.realname as choose_user_name
from cet_train_obj cto
left join cet_project_obj cpo on cpo.id=cto.obj_id
left join cet_train_course ctc on ctc.id=cto.train_course_id
left join cet_train ct on ct.id=ctc.train_id
left join cet_project cp on cp.id=cpo.project_id
left join sys_user_view uv on uv.id=cto.choose_user_id order by cpo.id;

DROP VIEW IF EXISTS `cet_trainee_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_trainee_view` AS
select obj_id, user_id, trainee_type_id, project_id, plan_id, train_id, is_quit as obj_is_quit,
        project_name, project_type, is_party_project, start_date, end_date, cet_party_id, project_status, project_is_deleted,
       count(*) as course_count,
       sum(if(is_finished, 1, 0)) as finish_count,
       sum(period) as total_period,
       sum(if(is_finished, period, 0)) as finish_period,
       sum(if(is_finished and is_online, period, 0)) as online_finish_period
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
