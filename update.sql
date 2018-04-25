
2018-4-25
update modify_table_apply set table_name='cadre_post_admin' where module=15;
update modify_table_apply set table_name='cadre_post_work' where module=16;
update modify_table_apply set table_name='cadre_family' where module=17;
update modify_table_apply set table_name='cadre_family_abroad' where module=18;


update cadre_edu set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_edu' and status !=0
);

update cadre_work set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_work' and status !=0
);

update cadre_book set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_book' and status !=0
);

update cadre_parttime set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_parttime' and status !=0
);

update cadre_research set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_research' and status !=0
);

update cadre_train set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_train' and status !=0
);


update cadre_paper set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_paper' and status !=0
);


update cadre_reward set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_reward' and status !=0
);


update cadre_course set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_course' and status !=0
);


update cadre_company set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_company' and status !=0
);

update cadre_post_pro set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_pro' and status !=0
);


update cadre_post_admin set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_admin' and status !=0
);


update cadre_post_work set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_post_work' and status !=0
);

update cadre_family set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_family' and status !=0
);

update cadre_family_abroad set status=2 where status=1 and id in(
select modify_id from modify_table_apply  where table_name='cadre_family_abroad' and status !=0
);


更新utils.jar


============更新北化工=========
2018-4-25

RENAME TABLE `cadre_famliy` TO `cadre_family`;

RENAME TABLE `cadre_famliy_abroad` TO `cadre_family_abroad`;

ALTER TABLE `cadre_family_abroad`
	CHANGE COLUMN `famliy_id` `family_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '所选家庭成员' AFTER `cadre_id`;

UPDATE `sys_resource` SET `permission`='cadreFamily:*' WHERE  `id`=239;
UPDATE `sys_resource` SET `permission`='cadreFamilyAbroad:*' WHERE  `id`=242;
UPDATE `sys_resource` SET `permission`='cadre:exportFamily' WHERE  `id`=454;

ALTER TABLE `cadre_family`
	DROP FOREIGN KEY `FK_base_cadre_famliy_base_cadre`;
ALTER TABLE `cadre_family`
	ADD CONSTRAINT `FK_base_cadre_family_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE;

	ALTER TABLE `cadre_family_abroad`
	DROP FOREIGN KEY `FK_base_cadre_famliy_abroad_base_cadre`,
	DROP FOREIGN KEY `FK_base_cadre_famliy_abroad_base_cadre_famliy`;
ALTER TABLE `cadre_family_abroad`
	ADD CONSTRAINT `FK_base_cadre_family_abroad_base_cadre` FOREIGN KEY (`cadre_id`) REFERENCES `cadre` (`id`) ON DELETE CASCADE,
	ADD CONSTRAINT `FK_base_cadre_family_abroad_base_cadre_family` FOREIGN KEY (`family_id`) REFERENCES `cadre_family` (`id`);
ALTER TABLE `cadre_family`
	DROP INDEX `FK_base_cadre_famliy_base_cadre`,
	ADD INDEX `FK_base_cadre_family_base_cadre` (`cadre_id`);

	ALTER TABLE `cadre_family_abroad`
	DROP INDEX `FK_base_cadre_famliy_abroad_base_cadre`,
	ADD INDEX `FK_base_cadre_family_abroad_base_cadre` (`cadre_id`),
	DROP INDEX `FK_base_cadre_famliy_abroad_base_cadre_famliy`,
	ADD INDEX `FK_base_cadre_family_abroad_base_cadre_family` (`family_id`);

ALTER TABLE `cadre_info_check`
	CHANGE COLUMN `famliy_abroad` `family_abroad` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '家庭成员移居国（境）外的情况' AFTER `reward`;


