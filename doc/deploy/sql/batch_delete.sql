


-- 删除不在党员库、不在干部库、不在登陆日志中的账号及账号信息
SET FOREIGN_KEY_CHECKS=0;
delete from sys_user where id not in(
select distinct user_id from (
select distinct user_id from ow_member
union all
select distinct user_id from ow_member_apply
union all
select distinct user_id from ow_member_in
union all
select distinct user_id from cadre
union all
select distinct user_id from cet_record
union all
select distinct user_id from crp_record
union all
select distinct user_id from sys_login_log
union all
select distinct user_id from ow_member_reg
)tmp where user_id is not null order by user_id asc
);
delete from sys_student_info where user_id not in(
select id from sys_user
);
delete from sys_teacher_info where user_id not in(
select id from sys_user
);
delete from ext_jzg where zgh not in(
select code from sys_user
);
delete from ext_yjs where xh not in(
select code from sys_user
);
delete from ext_bks where xh not in(
select code from sys_user
);
delete from sys_user_info where user_id not in(
select id from sys_user
);

delete from pmd_notify;
delete from pmd_order;
delete from pmd_order_item;
delete from sys_online_static;
delete from ext_jzg_salary;
delete from sys_approval_log;
SET FOREIGN_KEY_CHECKS=1;




-- 批量删除表、视图
SET FOREIGN_KEY_CHECKS=0;

set @db = 'db_owip_xjtu';
set @prefix = 'cet_%';

SELECT CONCAT( 'DROP TABLE ', GROUP_CONCAT(table_name) , ';' )
    AS statement FROM information_schema.tables
    WHERE table_schema = @db AND table_name LIKE @prefix;

SELECT CONCAT( 'DROP VIEW ', GROUP_CONCAT(table_name) , ';' )
    AS statement FROM information_schema.tables
    WHERE table_schema = @db AND table_name LIKE @prefix;

show tables like 'cet_%';