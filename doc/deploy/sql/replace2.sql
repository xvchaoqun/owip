
--  set @keyowrd='师大';

set @keyowrd='师范';

update cet_record set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update cet_record set organizer = replace(organizer, @keyowrd, repeat('*', char_length(@keyowrd))) where organizer like concat('%',@keyowrd,'%');

update cet_party set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update cet_expert set unit = replace(unit, @keyowrd, repeat('*', char_length(@keyowrd))) where unit like concat('%',@keyowrd,'%');

update cet_course set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update cet_upper_train set title = replace(title, @keyowrd, repeat('*', char_length(@keyowrd))) where title like concat('%',@keyowrd,'%');

update cet_upper_train set train_name = replace(train_name, @keyowrd, repeat('*', char_length(@keyowrd))) where train_name like concat('%',@keyowrd,'%');


update cadre_company set approval_unit = replace(approval_unit, @keyowrd, repeat('*', char_length(@keyowrd))) where approval_unit like concat('%',@keyowrd,'%');
update cadre_company set unit = replace(unit, @keyowrd, repeat('*', char_length(@keyowrd))) where unit like concat('%',@keyowrd,'%');
update cadre_company set post = replace(post, @keyowrd, repeat('*', char_length(@keyowrd))) where post like concat('%',@keyowrd,'%');

update cg_team set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');
update cg_member set post = replace(post, @keyowrd, repeat('*', char_length(@keyowrd))) where post like concat('%',@keyowrd,'%');
update cg_rule set content = replace(content, @keyowrd, repeat('*', char_length(@keyowrd))) where content like concat('%',@keyowrd,'%');

update unit_team set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');
update cadre_post set post = replace(post, @keyowrd, repeat('*', char_length(@keyowrd))) where post like concat('%',@keyowrd,'%');

update unit_post set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

-- select name from unit where name like concat('%',@keyowrd,'%');
update cadre_post set post = replace(post, @keyowrd, repeat('*', char_length(@keyowrd))) where post like concat('%',@keyowrd,'%');
update cadre_party set post = replace(post, @keyowrd, repeat('*', char_length(@keyowrd))) where post like concat('%',@keyowrd,'%');
update cadre_post set post_name = replace(post_name, @keyowrd, repeat('*', char_length(@keyowrd))) where post_name like concat('%',@keyowrd,'%');



update ow_member_stay set in_address = replace(in_address, @keyowrd, repeat('*', char_length(@keyowrd))) where in_address like concat('%',@keyowrd,'%');
update base_meta_type set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update ow_member_in set from_unit = replace(from_unit, @keyowrd, repeat('*', char_length(@keyowrd))) where from_unit like concat('%',@keyowrd,'%');
update ow_member_in set from_title = replace(from_title, @keyowrd, repeat('*', char_length(@keyowrd))) where from_title like concat('%',@keyowrd,'%');
update ow_member_in set from_address = replace(from_address, @keyowrd, repeat('*', char_length(@keyowrd))) where from_address like concat('%',@keyowrd,'%');

update ow_member_out set from_unit = replace(from_unit, @keyowrd, repeat('*', char_length(@keyowrd))) where from_unit like concat('%',@keyowrd,'%');
update ow_member_out set from_address = replace(from_address, @keyowrd, repeat('*', char_length(@keyowrd))) where from_address like concat('%',@keyowrd,'%');

update ow_member_out set to_unit = replace(to_unit, @keyowrd, repeat('*', char_length(@keyowrd))) where to_unit like concat('%',@keyowrd,'%');
update ow_member_out set to_title = replace(to_title, @keyowrd, repeat('*', char_length(@keyowrd))) where to_title like concat('%',@keyowrd,'%');


update pcs_proposal set content = replace(content, @keyowrd, repeat('*', char_length(@keyowrd))) where content like concat('%',@keyowrd,'%');

update ow_party_member_group set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');
update ow_branch_member_group set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');


update pmd_party set party_name = replace(party_name, @keyowrd, repeat('*', char_length(@keyowrd))) where party_name like concat('%',@keyowrd,'%');


update pmd_branch set party_name = replace(party_name, @keyowrd, repeat('*', char_length(@keyowrd))) where party_name like concat('%',@keyowrd,'%');

update pmd_branch set branch_name = replace(branch_name, @keyowrd, repeat('*', char_length(@keyowrd))) where branch_name like concat('%',@keyowrd,'%');



update cet_project set name = replace(name, @keyowrd,  repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update cadre_work set detail = replace(detail, @keyowrd,  repeat('*', char_length(@keyowrd))) where detail like concat('%',@keyowrd,'%');

update cadre_info set content = replace(content, @keyowrd,  repeat('*', char_length(@keyowrd))) where content like concat('%',@keyowrd,'%');

-- update sys_teacher_info set ext_unit = replace(ext_unit, @keyowrd,  repeat('*', char_length(@keyowrd))) where ext_unit like concat('%',@keyowrd,'%');
update unit set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update cadre set title = replace(title, @keyowrd, repeat('*', char_length(@keyowrd))) where title like concat('%',@keyowrd,'%');

update ow_party set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update ow_party set short_name = replace(short_name, @keyowrd, repeat('*', char_length(@keyowrd))) where short_name like concat('%',@keyowrd,'%');

update ow_branch set name = replace(name, @keyowrd, repeat('*', char_length(@keyowrd))) where name like concat('%',@keyowrd,'%');

update ow_branch set short_name = replace(short_name, @keyowrd, repeat('*', char_length(@keyowrd))) where short_name like concat('%',@keyowrd,'%');


update sys_attach_file set filename = replace(filename, @keyowrd, repeat('*', char_length(@keyowrd))) where filename like concat('%',@keyowrd,'%');

