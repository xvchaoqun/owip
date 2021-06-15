2021.6.9 xcq
DROP VIEW if EXISTS oa_task_user_view;
CREATE VIEW oa_task_user_view AS
select `otu`.`id` AS `id`,`otu`.`task_id` AS `task_id`,
`otu`.`browse_time` as `browse_time`,`otu`.`user_id` AS `user_id`,`otu`.`mobile` AS `mobile`,`otu`.`title` AS `title`,
`otu`.`assign_user_id` AS `assign_user_id`,`otu`.`assign_user_mobile` AS `assign_user_mobile`,`otu`.`content` AS `content`,
`otu`.`remark` AS `remark`,`otu`.`has_report` AS `has_report`,`otu`.`report_user_id` AS `report_user_id`,
`otu`.`report_time` AS `report_time`,`otu`.`is_back` AS `is_back`,`otu`.`is_delete` AS `is_delete`,`otu`.`check_remark` AS `check_remark`,`otu`.`sort_order` AS `sort_order`,
`otu`.`status` AS `status`,`uv`.`code` AS `code`,`uv`.`realname` AS `realname`,`ot`.`name` AS `task_name`,`ot`.`content` AS `task_content`,`ot`.`user_id` AS `task_user_id`,
`ot`.`user_ids` AS `task_user_ids`,`ot`.`deadline` AS `task_deadline`,`ot`.`user_file_count` AS `user_file_count`,`ot`.`contact` AS `task_contact`,`ot`.`is_delete` AS `task_is_delete`,
`ot`.`is_publish` AS `task_is_publish`,`ot`.`status` AS `task_status`,`ot`.`pub_date` AS `task_pub_date`,`ot`.`type` AS `task_type`,`ouv`.`code` AS `assign_code`,`ouv`.`realname` AS `assign_realname`,
`ruv`.`code` AS `report_code`,`ruv`.`realname` AS `report_realname` from ((((`oa_task_user` `otu` left join `oa_task` `ot` on((`otu`.`task_id` = `ot`.`id`))) left join `sys_user_view` `uv` on((`otu`.`user_id` = `uv`.`id`)))
left join `sys_user_view` `ouv` on((`otu`.`assign_user_id` = `ouv`.`id`))) left join `sys_user_view` `ruv` on((`otu`.`report_user_id` = `ruv`.`id`)))