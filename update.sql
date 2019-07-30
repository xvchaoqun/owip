
20190730

-- 弃用 cet_project的 status 和 pub_status字段
ALTER TABLE `cet_project`
	CHANGE COLUMN `status` `status` TINYINT(3) NOT NULL DEFAULT '1' COMMENT '状态（弃用），0 未启动、 1 正在进行、 2 已结束' AFTER `remark`,
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `status`;
update cet_project SET STATUS = 1;
update cet_project SET pub_status = 1;

-- 弃用 cet_train的 pub_status字段
ALTER TABLE `cet_train`
	CHANGE COLUMN `pub_status` `pub_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '1' COMMENT '发布状态（弃用），0 未发布 1 已发布  2 取消发布' AFTER `enroll_status`;
update cet_train SET pub_status = 1;

INSERT INTO `sys_scheduler_job` (`name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`)
VALUES ('培训数据校正', '包含：自动修改培训班的结课状态', 'job.cet.CetAutoAdjust', '0 0 0/1 * * ?', 1, 0, 25, '2019-07-29 16:47:26');

INSERT INTO `sys_property` (`code`, `name`, `content`, `type`, `sort_order`, `remark`)
VALUES ('scSubsidyLogo', '津贴调整文件LOGO', '', 5, 38, '大小628*205，PNG格式');


20190728
哈工大
20190728

更新common-utils

-- 调整岗位排序为正序
UPDATE unit_post SET sort_order=code;

20190727
北邮、哈工大、北航

20190727

删除 service.base.ShortMsgService
删除 service.auth

改 login.service / xss.ignoreUrIs

20190726
更新 南航、西交大、 北化工