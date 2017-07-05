

ALTER TABLE `sys_resource`
	ADD COLUMN `is_leaf` TINYINT(1) UNSIGNED NULL COMMENT '是否叶子节点' AFTER `parent_ids`;

	select group_concat(r.id) from sys_resource r where (select count(id) from sys_resource where parent_id=r.id) =0;

	update sys_resource set is_leaf=1 where id  in(108,210,312,416,22,31,41,256,301,302,313,404,414,421,257,261,112,113,114,116,117,118,233,83,84,157,158,159,149,150,151,152,153,154,155,145,146,147,160,320,66,245,124,125,126,127,318,333,334,335,336,337,338,374,375,395,396,133,134,135,140,141,142,143,231,232,234,235,236,237,238,239,240,241,242,280,304,305,306,307,308,309,310,311,316,317,319,357,393,94,95,96,97,98,99,100,101,102,103,104,246,247,249,250,251,252,253,254,290,291,296,440,183,184,185,220,222,299,300,376,186,187,188,189,190,191,192,193,194,195,392,196,197,198,199,204,205,206,208,209,213,214,244,285,286,287,288,295,441,292,293,283,267,268,278,272,273,274,275,276,281,303,271,321,264,266,270,279,75,294,297,415,315,446,329,355,358,359,360,370,371,324,325,326,327,444,330,331,332,445,346,348,342,343,344,345,349,350,351,352,356,354,362,363,365,366,367,368,369,373,382,383,391,387,388,389,390,378,399,400,407,408,409,412,413,418,419,420,424,425,426,429,430,431,432,433,434,435,436,437,438,443,447,448,449,450,451);

2017-06-28
ALTER TABLE `ow_member_out`
	ADD UNIQUE INDEX `user_id` (`user_id`);


2017-06-16
insert into cadre_party(user_id, type, class_id, grow_time, post, remark)
select user_id, 1, dp_type_id, dp_add_time, dp_post, dp_remark from cadre where is_dp = 1;

ALTER TABLE `cadre`
	DROP COLUMN `dp_type_id`,
	DROP COLUMN `dp_add_time`,
	DROP COLUMN `dp_post`,
	DROP COLUMN `dp_remark`,
	DROP COLUMN `is_dp`,
	DROP FOREIGN KEY `FK_cadre_base_meta_type`;

ALTER TABLE `cadre_ad_log`
	DROP COLUMN `dp_type_id`,
	DROP COLUMN `dp_add_time`,
	DROP COLUMN `dp_post`,
	DROP COLUMN `dp_remark`,
	DROP COLUMN `is_dp`;


2017-06-14
ALTER TABLE `abroad_apply_self`
	ADD COLUMN `approval_remark` VARCHAR(200) NULL COMMENT '审批意见，记录最新审批意见' AFTER `status`;


2017-06-13
ALTER TABLE `cis_inspect_obj`
	ADD COLUMN `log_file` VARCHAR(255) NULL COMMENT '考察原始记录' AFTER `summary`;



2017-06-10
update ow_member_stay set out_address=REPLACE(REPLACE(out_address, CHAR(10), ' '), CHAR(13), ' ');

2017-06-05
ALTER TABLE `ow_graduate_abroad`
	ADD COLUMN `code` VARCHAR(10) NULL COMMENT '编号，自动生成，今年就是从20170001开始，明年从20180001开始' AFTER `id`,
	ADD COLUMN `letter` VARCHAR(255) NULL DEFAULT NULL COMMENT '接收函/邀请函，图片格式' AFTER `email2`,
	ADD COLUMN `return_date` DATE NULL DEFAULT NULL COMMENT '预计回国时间（年/月）' AFTER `end_time`;


DROP TABLE `ow_apply_log`;

update ow_graduate_abroad set code = concat(left(create_time,4) , lpad(id, 4, '0'));

ALTER TABLE `ow_graduate_abroad`
	CHANGE COLUMN `code` `code` VARCHAR(10) NOT NULL COMMENT '编号，自动生成，今年就是从20170001开始，明年从20180001开始' AFTER `id`;

ALTER TABLE `ow_graduate_abroad`
	ADD UNIQUE INDEX `code` (`code`);


ALTER TABLE `ow_graduate_abroad`
	ADD COLUMN `print_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '打印次数' AFTER `create_time`,
	ADD COLUMN `last_print_time` DATETIME NULL DEFAULT NULL COMMENT '最后一次打印时间' AFTER `print_count`,
	ADD COLUMN `last_print_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '最后一次打印人' AFTER `last_print_time`;


DROP VIEW `ow_member_stay_view`;
DROP TABLE `ow_member_stay`;
DROP TABLE `ow_graduate_abroad_view`;

ALTER TABLE `ow_graduate_abroad`
	COMMENT='毕业生党员保留组织关系申请，分为出国（境）、境内两种',
	CHANGE COLUMN `type` `abroad_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '留学方式,公派/自费' AFTER `return_date`,
	ADD COLUMN `type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '类别， 1 出国  2 国内' AFTER `pay_time`;
RENAME TABLE `ow_graduate_abroad` TO `ow_member_stay`;




select * from sys_resource where url like '%memberStay%';

select * from sys_resource where url like '%graduateAbroad%';

select * from sys_role where resource_ids like '%296%';


delete from sys_resource where id in(255, 289);

update sys_resource set url='/memberStay?type=1' , permission='memberStay:*' where id=296;

update sys_resource set url='/user/memberStay?type=1' , permission='userMember:stay' where id=295;

清除缓存

update ow_member_stay set type =1;
ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类别， 1 出国  2 国内' AFTER `pay_time`;

	ALTER TABLE `ow_member_stay`
	ADD UNIQUE INDEX `user_id` (`user_id`);


ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `abroad_reason` `stay_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '出国原因' AFTER `user_type`,
	CHANGE COLUMN `return_date` `over_date` DATE NULL DEFAULT NULL COMMENT '（出国）预计回国时间（年/月）， （国内）预计转出时间' AFTER `end_time`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `stay_reason` `stay_reason` VARCHAR(255) NULL DEFAULT NULL COMMENT '出国原因，（国内）暂留原因' AFTER `user_type`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `letter` `letter` VARCHAR(255) NULL DEFAULT NULL COMMENT '接收函/邀请函，（国内）户档暂留证明， 图片格式' AFTER `email2`;

ALTER TABLE `ow_member_stay`
	CHANGE COLUMN `abroad_type` `abroad_type` TINYINT(3) UNSIGNED NULL COMMENT '留学方式,公派/自费' AFTER `over_date`;

		CREATE ALGORITHM = UNDEFINED VIEW `ow_member_stay_view` AS SELECT wms.*,  om.`status` as member_status
from ow_member_stay wms, ow_member om
where wms.user_id=om.user_id  ;


ALTER TABLE `dispatch_work_file`
	CHANGE COLUMN `file_path` `pdf_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT 'pdf文件，如果"保密级别"选择了"秘密、机密、绝密"的其中之一，那么文件上传功能不 可以用。' AFTER `file_name`,
	ADD COLUMN `word_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT 'word文件' AFTER `pdf_file_path`;