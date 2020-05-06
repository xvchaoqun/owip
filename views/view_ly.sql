
-- 20200506
DROP VIEW IF EXISTS `unit_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `unit_post_view` AS
select up.*, u.name as unit_name, u.code as unit_code, u.type_id as unit_type_id,
u.status as unit_status, u.sort_order as unit_sort_order,
cp.cadre_id, cp.id as cadre_post_id, cp.admin_level as cp_admin_level, cp.is_main_post,
cv.gender, cv.admin_level as cadre_admin_level, cv.post_type as cadre_post_type,cv.lp_work_time as lp_work_time,cv.s_work_time as s_work_time,
cv.is_principal as cadre_is_principal, cv.cadre_post_year, cv.admin_level_year from unit_post up
left join unit u on up.unit_id=u.id
left join cadre_post cp on up.id=cp.unit_post_id
left join cadre_view cv on cv.id=cp.cadre_id;