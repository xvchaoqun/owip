







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