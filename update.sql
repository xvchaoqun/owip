
20190910
北航、哈工大

20190909
北邮

20190908
北化工

20190908
-- 更新导入样表

20190907
-- 更新  jx.utils

20190906
北邮  南航

20190904
南航
20190904
ALTER TABLE `sc_subsidy_cadre`
	CHANGE COLUMN `unit_id` `unit_id` INT(10) UNSIGNED NULL COMMENT '所在单位' AFTER `cadre_id`;

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.corpID', '微信企业号ID', 'xxx', 1, 40, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.corpSecret', '微信企业号secret', 'xxxx', 1, 41, '');
INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('wx.agentId', '微信应用ID', '100', 2, 42, '');


UPDATE sys_resource SET NAME='消息模板' WHERE permission='contentTpl:*';
UPDATE sys_resource SET NAME='定向消息' WHERE permission='shortMsgTpl:*';

ALTER TABLE `base_content_tpl`
	CHANGE COLUMN `type` `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `role_id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `code`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL COMMENT '图片，微信使用' AFTER `wx_url`,
	CHANGE COLUMN `remark` `remark` VARCHAR(200) NULL DEFAULT NULL COMMENT '备注' AFTER `update_time`;

ALTER TABLE `base_short_msg_tpl`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `role_id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `type`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL DEFAULT NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL DEFAULT NULL COMMENT '图片，微信使用' AFTER `wx_url`;

ALTER TABLE `base_short_msg`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '类型，1 短信 2 微信 3 短信+微信' AFTER `id`,
	ADD COLUMN `wx_msg_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '微信消息类型，1 文本 2 图文' AFTER `type`,
	ADD COLUMN `wx_title` VARCHAR(200) NULL DEFAULT NULL COMMENT '模板标题，微信使用' AFTER `wx_msg_type`,
	ADD COLUMN `wx_url` VARCHAR(200) NULL DEFAULT NULL COMMENT '跳转地址，微信使用' AFTER `wx_title`,
	ADD COLUMN `wx_pic` VARCHAR(200) NULL DEFAULT NULL COMMENT '图片，微信使用' AFTER `wx_url`,
	CHANGE COLUMN `type` `type_str` VARCHAR(50) NULL DEFAULT NULL COMMENT '类别' AFTER `relate_type`;

UPDATE base_short_msg SET TYPE=1;
UPDATE base_short_msg_tpl SET TYPE=1;

-- 删除 sys.SendMsgResult
-- 更新  jx.utils
-- 更新 twelvemonkeys等jar包，删除原来的版本
-- common-io-1.3.2.jar  -> common-io-2.6.jar
-- 增加 wx.send

20190902
南航

delete from sys_resource where permission='cadre:view';

ALTER TABLE `base_short_msg`
	ADD COLUMN `repeat_times` TEXT NULL DEFAULT NULL COMMENT '重复发送时间（成功发送的时间）' AFTER `create_time`;

UPDATE base_meta_class SET CODE='mc_dr_type' WHERE CODE='mc_dr_recommend_type';

20190901
南航

ALTER TABLE `dr_offline`
	ADD COLUMN `supervice_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '监督人员，针对会议推荐' AFTER `chief_member_id`;
DROP VIEW IF EXISTS `dr_offline_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_offline_view` AS
select do.*, sr.seq as sr_seq, sm.hold_date, sm.sc_type, sm.unit_post_id, up.name as post_name,
up.job, up.admin_level, up.post_type, up.unit_id,
u.type_id as unit_type from dr_offline do
left join sc_record sr on sr.id=do.record_id
left join sc_motion sm on sm.id= sr.motion_id
left join unit_post up on up.id = sm.unit_post_id
left join unit u on u.id = up.unit_id ;

