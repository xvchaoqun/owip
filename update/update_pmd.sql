-- 2020.03.27 桑文帅
-- 建表语句

CREATE TABLE `pmd_fee` (
	`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
	`type` INT(10) UNSIGNED NOT NULL COMMENT '缴费类型，元数据，包含补缴党费、捐赠党费',
	`pay_month` DATE NOT NULL COMMENT '缴费月份，默认当月，可以修改',
	`user_id` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
	`party_id` INT(10) UNSIGNED NOT NULL COMMENT '所属分党委，本人添加时，只允许选择本支部，支部、分党委同理',
	`branch_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所在党支部',
	`amt` DECIMAL(10,2) UNSIGNED NULL DEFAULT NULL COMMENT '缴费金额',
	`reason` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '缴费原因',
	`is_online_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT '缴费方式，1 线上缴费 0 现金缴费',
	`is_self_pay` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '线上缴费途径， 1 线上缴费、0 代缴党费',
	`has_pay` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '状态，0：未交费，1：已缴费',
	`pay_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '缴费人',
	`pay_time` DATETIME NULL DEFAULT NULL COMMENT '缴费时间',
	`order_no` VARCHAR(30) NULL DEFAULT NULL COMMENT '缴费订单号，批量缴费时允许重复',
	`order_user_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '生成订单号时的账号',
	`remark` TEXT NULL COMMENT '备注',
	`status` TINYINT(3) UNSIGNED NULL DEFAULT '0' COMMENT '审核状态，0 待审核 1 支部审核 2 分党委审核 3 组织部审核',
	PRIMARY KEY (`id`)
)
COMMENT='党员缴纳党费'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=2
;

