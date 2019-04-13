
20190413
ALTER TABLE `base_short_msg`
	ADD COLUMN `relate_sn` VARCHAR(50) NULL DEFAULT NULL COMMENT '发送编号，每次定向发送时生成的UUID' AFTER `relate_id`;

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `send_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送次数' AFTER `sort_order`,
	ADD COLUMN `send_user_count` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '发送人次' AFTER `send_count`;

update base_short_msg_tpl set send_count=0, send_user_count=0;

ALTER TABLE `base_short_msg_tpl`
	CHANGE COLUMN `send_count` `send_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送次数' AFTER `sort_order`,
	CHANGE COLUMN `send_user_count` `send_user_count` INT(10) UNSIGNED NOT NULL DEFAULT '0' COMMENT '发送人次' AFTER `send_count`;

update base_short_msg_tpl tpl, (select relate_id, count(*) num from base_short_msg where relate_type=2 group by relate_id)
  msg set send_count=msg.num, send_user_count=msg.num where msg.relate_id=tpl.id;


20190411
更新北邮----
更新北化工
