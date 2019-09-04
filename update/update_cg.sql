
2019-09-02
-- 删除字段 桑文帅
ALTER TABLE `cg_member` DROP COLUMN `user_ids`;

2019-08-08
-- 修改字段属性 桑文帅
ALTER TABLE `cg_member` ALTER `user_id` DROP DEFAULT;
ALTER TABLE `cg_member` CHANGE COLUMN `user_id` `user_id` INT(10) UNSIGNED NULL COMMENT '现任干部' AFTER `unit_post_id`;