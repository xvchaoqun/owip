
2019-09-10
-- 添加定时任务数据 桑文帅
INSERT INTO `sys_scheduler_job` (`id`, `name`, `summary`, `clazz`, `cron`, `is_started`, `need_log`, `sort_order`, `create_time`) VALUES (26, '委员会和领导小组人员变动提醒', '委员会和领导小组关联岗位的现任干部变动提醒', 'job.cg.NeedAdjustMember', '0 0 0/2 * * ?', 0, 0, 27, '2019-09-06 15:48:19');

2019-09-06
-- 添加字段 桑文帅
ALTER TABLE `cg_member` ADD COLUMN `need_adjust` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否需要调整' AFTER `is_current`;

2019-09-02
-- 删除字段 桑文帅
ALTER TABLE `cg_member` DROP COLUMN `user_ids`;

2019-08-08
-- 修改字段属性 桑文帅
ALTER TABLE `cg_member` ALTER `user_id` DROP DEFAULT;
ALTER TABLE `cg_member` CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NULL COMMENT '现任干部' AFTER `unit_post_id`;