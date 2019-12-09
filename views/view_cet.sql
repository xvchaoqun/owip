

-- 20191204 ly 培训情况

DROP VIEW IF EXISTS `cet_trainee_course_view`;
CREATE ALGORITHM = UNDEFINED VIEW `cet_trainee_course_view` AS
select cteec.id as id,cteec.trainee_id as trainee_id,cteec.train_course_id as train_course_id,
cteec.can_quit as can_quit,cteec.is_finished as is_finished,cteec.sign_time as sign_time,cteec.sign_out_time as sign_out_time,
cteec.sign_type as sign_type,cteec.remark as remark,cteec.choose_time as choose_time,cteec.choose_user_id as choose_user_id,
cteec.ip as ip,ctee.train_id as train_id,cpo.project_id as project_id,cpo.trainee_type_id as trainee_type_id,
cpo.user_id as user_id,ctc.course_id as course_id,cc.period as period,cp.year as year,uv.code as choose_user_code,uv.realname as choose_user_name
from cet_trainee_course cteec
left join cet_trainee ctee on cteec.trainee_id=ctee.id
left join cet_project_obj cpo on cpo.id=ctee.obj_id
left join cet_train_course ctc on ctc.id=cteec.train_course_id
left join cet_train ct on ct.id=ctee.train_id
left join cet_course cc on cc.id=ctc.course_id
left join cet_project cp on cp.id=cpo.project_id
left join sys_user_view uv on uv.id=cteec.choose_user_id order by cpo.id;