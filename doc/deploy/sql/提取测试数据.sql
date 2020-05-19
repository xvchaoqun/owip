

-- 提取30个干部（现有数据库）
replace into sys_user select * from db_owip_1.sys_user where id in(select user_id from db_owip_1.cadre where status=1 ) order by rand() limit 30;

-- 提取5个校领导（现有数据库）
replace into sys_user select * from db_owip_1.sys_user where id in(select user_id from db_owip_1.cadre where status=6 ) order by rand() limit 5;
replace into cadre_leader select * from db_owip_1.cadre_leader where cadre_id in(select id from cadre);
replace into unit select * from db_owip_1.unit where id in(select unit_id from db_owip_1.cadre_leader_unit) and id not in(select id from unit);
replace into cadre_leader_unit select * from db_owip_1.cadre_leader_unit where leader_id in(select id from cadre_leader);


-- 因私证件
replace into abroad_safe_box  select * from db_owip_1.abroad_safe_box where id in( select safe_box_id from db_owip_1.abroad_passport where cadre_id in(select id from cadre));
replace into sys_user select * from  db_owip_1.sys_user where id in ( select user_id from db_owip_1.abroad_passport_apply where id in( select apply_id from db_owip_1.abroad_passport where cadre_id in(select id from cadre)));
replace into abroad_passport_apply  select * from db_owip_1.abroad_passport_apply where id in( select apply_id from db_owip_1.abroad_passport where cadre_id in(select id from cadre));
replace into abroad_taiwan_record select * from db_owip_1.abroad_taiwan_record where cadre_id in(select id from cadre);
replace into abroad_passport select * from db_owip_1.abroad_passport where cadre_id in(select id from cadre);
replace into abroad_apply_self select * from db_owip_1.abroad_apply_self where cadre_id in(select id from cadre);


replace into sys_teacher_info select * from db_owip_1.sys_teacher_info where user_id in(select id from sys_user where id>10);
replace into ext_jzg select * from db_owip_1.ext_jzg where zgh in(select code from sys_user where id>10);
replace into sys_user_info select * from db_owip_1.sys_user_info where user_id in(select id from sys_user where id>10);
replace into sys_teacher_info select * from db_owip_1.sys_teacher_info where user_id in(select id from sys_user where id>10);
SET FOREIGN_KEY_CHECKS=0;
replace into cadre select * from db_owip_1.cadre where user_id in(select id from sys_user where id>10) and id not in(select id from cadre);
SET FOREIGN_KEY_CHECKS=1;
replace into unit select * from db_owip_1.unit where id in(select unit_id from cadre where user_id in(select id from sys_user where id>10)) and id not in(select id from unit);



-- 插入数据（测试数据库）
replace into cadre_edu select * from db_owip_1.cadre_edu where cadre_id in(select id from cadre);

replace into cadre_work select * from db_owip_1.cadre_work where cadre_id in(select id from cadre);

replace into cadre_family select * from db_owip_1.cadre_family where cadre_id in(select id from cadre);

replace into cadre_family_abroad select * from db_owip_1.cadre_family_abroad where cadre_id in(select id from cadre);

replace into unit select * from db_owip_1.unit where id in( 
select unit_id from db_owip_1.ow_party where id in (select party_id from db_owip_1.ow_member where user_id in(select user_id from cadre)) and unit_id not in(select id from unit));

replace into ow_party 
select * from db_owip_1.ow_party where id in (select party_id from db_owip_1.ow_member where user_id in(select user_id from cadre)) and id not in(select id from ow_party);
replace into ow_party_member_group select * from db_owip_1.ow_party_member_group where party_id in(select id from ow_party);
SET FOREIGN_KEY_CHECKS=0;
replace into sys_user select * from db_owip_1.sys_user where id in(
select user_id from db_owip_1.ow_party_member where group_id in(select id from ow_party_member_group) and id not in(select id from sys_user));
SET FOREIGN_KEY_CHECKS=1;
replace into ow_party_member select * from db_owip_1.ow_party_member where group_id in(select id from ow_party_member_group);

replace into ow_branch 
select * from db_owip_1.ow_branch where id in (select branch_id from db_owip_1.ow_member where user_id in(select user_id from cadre)) and id not in(select id from ow_branch);
replace into ow_branch_member_group select * from db_owip_1.ow_branch_member_group where branch_id in(select id from ow_branch);
SET FOREIGN_KEY_CHECKS=0;
replace into sys_user select * from db_owip_1.sys_user where id in(
select user_id from db_owip_1.ow_branch_member where group_id in(select id from ow_branch_member_group) and id not in(select id from sys_user));
SET FOREIGN_KEY_CHECKS=1;
replace into ow_branch_member select * from db_owip_1.ow_branch_member where group_id in(select id from ow_branch_member_group);

replace into ow_member 
select * from db_owip_1.ow_member where user_id in (select user_id from cadre);

-- 干部职数
replace into cpc_allocation select * from db_owip_1.cpc_allocation where unit_id in(select id from unit);

replace into unit select * from db_owip_1.unit where id in(select unit_id from db_owip_1.cadre_post) and id not in(select id from unit);

replace into unit select * from db_owip_1.unit where id in(select double_unit_id from db_owip_1.cadre_post) and id not in(select id from unit);

replace into cadre_post select * from db_owip_1.cadre_post where cadre_id in(select id from cadre);

-- 工作文件
replace into dispatch_work_file select * from db_owip_1.dispatch_work_file order by rand() limit 20;

replace into crs_post select * from db_owip_1.crs_post order by rand() limit 2;

-- 任免文件
replace into dispatch_type select * from db_owip_1.dispatch_type;
replace into dispatch select * from db_owip_1.dispatch order by rand() limit 22;
delete from dispatch_type where id not in(select dispatch_type_id from dispatch);

-- 任免干部
replace into sys_user select * from db_owip_1.sys_user where id in(
select user_id from db_owip_1.cadre where id in (select cadre_id from db_owip_1.dispatch_cadre where dispatch_id in (select id from dispatch))) and id not in(select id from sys_user);
SET FOREIGN_KEY_CHECKS=0;
replace into dispatch_cadre select * from db_owip_1.dispatch_cadre where dispatch_id in (select id from dispatch);
SET FOREIGN_KEY_CHECKS=1;

SET FOREIGN_KEY_CHECKS=0;
replace into cadre 
select * from db_owip_1.cadre where user_id in(select id from sys_user);
SET FOREIGN_KEY_CHECKS=1;






