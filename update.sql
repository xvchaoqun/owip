

ALTER TABLE `sys_config`
	ADD COLUMN `school_short_name` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校简称' AFTER `school_name`,
	ADD COLUMN `school_login_url` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校门户地址' AFTER `school_short_name`;
ALTER TABLE `sys_config`
	CHANGE COLUMN `login_bg` `login_bg` VARCHAR(200) NULL DEFAULT NULL COMMENT '登录页背景，分辨率1920*890，JPG' AFTER `logo_white`,
	ADD COLUMN `login_top` VARCHAR(200) NULL DEFAULT NULL COMMENT '登录页顶部图片，分辨率1102*109，JPG' AFTER `login_bg`,
	ADD COLUMN `login_top_bg_color` VARCHAR(10) NULL DEFAULT NULL COMMENT '登录页顶部背景颜色' AFTER `login_top`;
ALTER TABLE `sys_config`
	ADD COLUMN `school_email` VARCHAR(50) NULL DEFAULT NULL COMMENT '学校邮箱' AFTER `school_login_url`;



update sys_user_info set health=382 where health like '%健康%' or health like '%优%'  or health like '%好%';
update sys_user_info set health=383 where health like '%良%';
update sys_user_info set health=384 where health like '%一般%';
update sys_user_info set health=385 where health like '%较差%';
update sys_user_info set health=null where health='';

ALTER TABLE `sys_user_info`
	CHANGE COLUMN `health` `health` INT UNSIGNED NULL DEFAULT NULL COMMENT '健康状况' AFTER `specialty`;

更新 sys_user_view


2017-9-20
ALTER TABLE `pcs_config`
	ADD COLUMN `proposal_submit_time` DATETIME NULL COMMENT '提交提案时间，精确到分钟' AFTER `remark`,
	ADD COLUMN `proposal_support_time` DATETIME NULL COMMENT '征集附议人时间，精确到分钟' AFTER `proposal_submit_time`,
	ADD COLUMN `proposal_support_count` INT(10) UNSIGNED NULL COMMENT '附议人数，立案标准（学校规定，附议人达到5个予以立案）' AFTER `proposal_support_time`;

ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `is_proposal` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否进入提案党代表名单，（从三上已选择的名单中选取）' AFTER `is_chosen`;

ALTER TABLE `pcs_pr_candidate`
	CHANGE COLUMN `is_proposal` `is_proposal` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否进入提案阶段，（从三上已选择的名单中选取）' AFTER `is_chosen`,
	ADD COLUMN `proposal_sort_order` INT UNSIGNED NULL DEFAULT NULL COMMENT '进入提案阶段的党代表顺序，初始化为三上的顺序，升序排列' AFTER `is_proposal`;

更新 pcs_pr_candidate_view

增加 mc_health

“健康状况”根据本人的具体情况选择“健康”、“良好”、“一般”、“较差”。


ALTER TABLE `cadre_paper`
	ADD COLUMN `name` VARCHAR(100) NULL DEFAULT NULL COMMENT '论文题目' AFTER `pub_time`,
	ADD COLUMN `press` VARCHAR(100) NULL DEFAULT NULL COMMENT '期刊名称' AFTER `name`;



2017-9-14
ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `meeting_type` TINYINT(3) UNSIGNED NOT NULL COMMENT '大会类型，1 全体党员大会 2 党员代表大会' AFTER `vote_member_count`;

update pcs_pr_recommend set meeting_type=1 where stage=3;

2017-9-12
删除 pcs_branch_view2
更新 ow_branch_view  （未更新mybaits）

更新 pcs_branch_view

2017-9-12
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `realname_sort_order` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '按笔画排序， 在recommend_id下排序，针对三下三上' AFTER `sort_order`;

	更新pcs_pr_candidate_view

2017-9-7
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `is_chosen` TINYINT(1) UNSIGNED NULL DEFAULT NULL COMMENT '是否选择，针对三下三上' AFTER `vote3`;

更新pcs_pr_candidate_view

2017-9-6
ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `vote3` INT(10) UNSIGNED NULL COMMENT '票数，针对三下三上' AFTER `vote`;

更新pcs_pr_candidate_view

2017-9-4
delete from pcs_exclude_branch;
insert into pcs_exclude_branch(party_id, branch_id, create_time)
select party_id, id as branch_id, now() as create_time from ow_branch where is_deleted=0 and name like '%留%';

2017-9-4
ALTER TABLE `pcs_pr_recommend`
	CHANGE COLUMN `expect_member_count` `expect_member_count` INT(10) UNSIGNED NOT NULL COMMENT '应参会党员数，三下三上不填' AFTER `party_id`,
	CHANGE COLUMN `actual_member_count` `actual_member_count` INT(10) UNSIGNED NOT NULL COMMENT '实参会党员数，三下三上不填' AFTER `expect_member_count`,
	CHANGE COLUMN `expect_positive_member_count` `expect_positive_member_count` INT(10) UNSIGNED NOT NULL COMMENT '应参会正式党员数' AFTER `actual_member_count`,
	ADD COLUMN `meeting_time` DATE NULL COMMENT '党员大会选举时间，针对三下三上' AFTER `actual_positive_member_count`,
	ADD COLUMN `meeting_address` VARCHAR(200) NULL COMMENT '党员大会选举地点，针对三下三上' AFTER `meeting_time`;

ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `vote_member_count` INT(10) UNSIGNED NULL COMMENT '具有选举权的正式党员数，针对三下三上' AFTER `actual_positive_member_count`;

	ALTER TABLE `pcs_pr_candidate`
	ADD COLUMN `mobile` VARCHAR(20) NULL COMMENT '手机号，针对三下三上' AFTER `nation`,
	ADD COLUMN `email` VARCHAR(100) NULL COMMENT '邮箱，针对三下三上' AFTER `mobile`;

ALTER TABLE `pcs_pr_recommend`
	ADD COLUMN `report_file_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '“选举结果报告单”扫描件，针对三下三上' AFTER `meeting_address`;

更新 pcs_pr_candidate_view



2017-9-1
ALTER TABLE `pcs_admin`
	DROP COLUMN `config_id`;



2017-9-1
ALTER TABLE `pcs_admin_report`
	ADD UNIQUE INDEX `party_id_config_id_stage` (`party_id`, `config_id`, `stage`);

ALTER TABLE `pcs_recommend`
	DROP INDEX `party_id_branch_id_config_id_is_finished`;

	ALTER TABLE `pcs_recommend`
	ADD UNIQUE INDEX `party_id_branch_id_config_id_stage` (`party_id`, `branch_id`, `config_id`, `stage`);


2017-8-29
ALTER TABLE `pcs_recommend`
	DROP INDEX `party_id_branch_id_config_id`;

	ALTER TABLE `pcs_recommend`
	ADD UNIQUE INDEX `party_id_branch_id_config_id_is_finished` (`party_id`, `branch_id`, `config_id`, `is_finished`);

ow_party_view

ALTER TABLE `pcs_recommend`
	CHANGE COLUMN `stage` `stage` TINYINT(3) UNSIGNED NOT NULL COMMENT '阶段，1 一下一上 2 二下二上 3 三下三上' AFTER `is_finished`;

pcs_candidate_view

ALTER TABLE `pcs_admin`
	ADD COLUMN `remark` VARCHAR(255) NULL COMMENT '备注' AFTER `config_id`;

2017-8-29
ALTER TABLE `sys_login_log`
	ADD COLUMN `country` VARCHAR(200) NULL DEFAULT NULL COMMENT '国家' AFTER `login_ip`,
	ADD COLUMN `area` VARCHAR(200) NULL DEFAULT NULL COMMENT '地区' AFTER `country`,
	ADD COLUMN `last_country` VARCHAR(200) NULL DEFAULT NULL COMMENT '上次国家' AFTER `last_login_ip`,
	ADD COLUMN `last_area` VARCHAR(200) NULL DEFAULT NULL COMMENT '上次地区' AFTER `last_country`;

更新party_view

2017-8-24
ALTER TABLE `crs_applicant`
	ADD COLUMN `is_quit` TINYINT(1) UNSIGNED NOT NULL COMMENT '是否退出' AFTER `enroll_time`;

	ALTER TABLE `crs_applicant`
	ADD COLUMN `status` TINYINT(3) UNSIGNED NOT NULL COMMENT '状态， 1 正常 0 已删除' AFTER `recommend_second_count`;

update crs_applicant set status=1;

ALTER TABLE `crs_applicant`
	COMMENT='报名人员，唯一 (user_id， post_id, status=1） ';

更新crs_applicant_view

2017-8-18
ALTER TABLE `crs_post`
	ADD COLUMN `job` VARCHAR(200) NULL COMMENT '分管工作' AFTER `name`,
	ADD COLUMN `num` INT(10) UNSIGNED NULL COMMENT '招聘人数' AFTER `unit_id`;

ALTER TABLE `crs_post`
	ADD COLUMN `quit_deadline` DATETIME NULL DEFAULT NULL COMMENT '退出报名的最后期限' AFTER `meeting_address`;

ALTER TABLE `base_short_msg`
	ADD COLUMN `content_tpl_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联模板' AFTER `receiver_id`;
ALTER TABLE `base_short_msg`
	CHANGE COLUMN `content_tpl_id` `relate_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '关联模板' AFTER `receiver_id`,
	ADD COLUMN `relate_type` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '关联类别，1 模板 2 定向短信' AFTER `relate_id`;

update base_short_msg bsm, base_content_tpl bct set bsm.relate_id = bct.id, relate_type=1 where bsm.`type`=bct.name;

update base_short_msg bsm, base_short_msg_tpl bsmt set bsm.relate_id = bsmt.id, relate_type=2 where bsm.`type`=bsmt.name and bsm.relate_id is null;

ALTER TABLE `crs_post_expert`
	CHANGE COLUMN `remark` `remark` VARCHAR(50) NULL COMMENT '备注' AFTER `sort_order`;

ALTER TABLE `crs_post`
	ADD COLUMN `stat_expert_count` INT UNSIGNED NULL COMMENT '招聘会专家组人数，专家组推荐意见汇总' AFTER `status`,
	ADD COLUMN `stat_give_count` INT UNSIGNED NULL COMMENT '发出推荐票数量' AFTER `stat_expert_count`,
	ADD COLUMN `stat_back_count` INT UNSIGNED NULL COMMENT '收回数量' AFTER `stat_give_count`,
	ADD COLUMN `stat_file` VARCHAR(200) NULL COMMENT '表扫描件' AFTER `stat_back_count`,
	ADD COLUMN `stat_date` DATE NULL COMMENT '记录日期' AFTER `stat_file`;

	ALTER TABLE `crs_applicant`
	ADD COLUMN `recommend_first_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '推荐排第一票数' AFTER `special_remark`,
	ADD COLUMN `recommend_second_count` INT UNSIGNED NULL DEFAULT NULL COMMENT '推荐排第二票数' AFTER `recommend_first_count`;

	ALTER TABLE `crs_post`
	ADD COLUMN `stat_file_name` VARCHAR(100) NULL DEFAULT NULL AFTER `stat_file`;



2017-8-4
update sys_resource set permission = replace(permission, 'recruit', 'crs') where permission like 'recruit%';
update sys_resource set url = replace(url, '/recruit', '/crs') where url like '/recruit%';

（以下已删除替换，不需更新）
RENAME TABLE `recruit_post` TO `crs_post`;
RENAME TABLE `recruit_template` TO `crs_template`;


ALTER TABLE `crs_post`
	ADD COLUMN `type` TINYINT(3) UNSIGNED NOT NULL COMMENT '招聘类型' AFTER `year`,
	ADD COLUMN `seq` INT(10) UNSIGNED NOT NULL COMMENT '编号，招聘类型+序号，eg：竞争上岗[2017]1号、公开招聘[2017]100号' AFTER `type`;
ALTER TABLE `crs_post`
	CHANGE COLUMN `sign_status` `sign_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '报名状态，1未启动报名、2正在报名、3报名结束' AFTER `qualification`;

ALTER TABLE `crs_post`
	ADD COLUMN `notice` VARCHAR(255) NULL COMMENT '招聘公告，pdf文件' AFTER `unit_id`,
	ADD COLUMN `qualification_group_id` INT UNSIGNED NULL COMMENT '任职资格组合' AFTER `qualification`;
ALTER TABLE `crs_post`
	CHANGE COLUMN `qualification_group_id` `post_require_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '岗位要求' AFTER `qualification`;

ALTER TABLE `crs_post`
	ADD COLUMN `start_time` DATETIME NULL DEFAULT NULL COMMENT '报名开启时间' AFTER `post_require_id`,
	ADD COLUMN `end_time` DATETIME NULL DEFAULT NULL COMMENT '报名关闭时间' AFTER `start_time`,
	CHANGE COLUMN `sign_status` `enroll_status` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名' AFTER `end_time`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_time` DATETIME NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	ADD COLUMN `meeting_address` VARCHAR(100) NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	DROP COLUMN `meeting_status`;

ALTER TABLE `crs_post`
	DROP COLUMN `meeting_time`,
	DROP COLUMN `meeting_address`;

	ALTER TABLE `crs_post`
	ADD COLUMN `meeting_time` DATETIME NOT NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	ADD COLUMN `meeting_address` VARCHAR(100) NOT NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	ADD COLUMN `meeting_status` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '招聘会是否完成' AFTER `meeting_address`;


ALTER TABLE `crs_post`
	CHANGE COLUMN `enroll_status` `enroll_status` TINYINT(3) UNSIGNED NOT NULL COMMENT '报名状态，0 根据报名时间而定 1 强制开启、2 强制关闭、3 暂停报名' AFTER `end_time`,
	CHANGE COLUMN `meeting_time` `meeting_time` DATETIME NULL COMMENT '招聘会时间，招聘会状态：未召开、已召开' AFTER `enroll_status`,
	CHANGE COLUMN `meeting_address` `meeting_address` VARCHAR(100) NULL COMMENT '招聘会地点' AFTER `meeting_time`,
	CHANGE COLUMN `meeting_status` `meeting_status` TINYINT(1) UNSIGNED NULL COMMENT '招聘会是否完成' AFTER `meeting_address`,
	CHANGE COLUMN `committee_status` `committee_status` TINYINT(1) UNSIGNED NULL COMMENT '常委会情况，未上会、已上会' AFTER `meeting_status`;


2017-7-24
ALTER TABLE `abroad_taiwan_record`
	CHANGE COLUMN `start_date` `start_date` DATE NOT NULL COMMENT '离境时间' AFTER `record_date`,
	CHANGE COLUMN `end_date` `end_date` DATE NOT NULL COMMENT '回国时间' AFTER `start_date`;


2017-7-24
ALTER TABLE `abroad_passport`
	ADD COLUMN `taiwan_record_id` INT(10) UNSIGNED NULL DEFAULT NULL COMMENT '如果是因公赴台备案' AFTER `apply_id`,
	ADD CONSTRAINT `FK_abroad_passport_abroad_taiwan_record` FOREIGN KEY (`taiwan_record_id`) REFERENCES `abroad_taiwan_record` (`id`) ON DELETE RESTRICT;

ALTER TABLE `abroad_passport_draw`
	CHANGE COLUMN `use_passport` `use_passport` TINYINT(3) UNSIGNED NULL DEFAULT NULL COMMENT '归还证件处理类别， 因私出国、因公赴台长期（1：持证件出国（境） 0：未持证件出国（境） 2：拒不交回证件） 处理其他事务（1：违规使用证件出国（境）0：没有使用证件出国（境） 2：拒不交回证件）' AFTER `attachment_filename`;

ALTER TABLE `abroad_passport`
	ADD COLUMN `pic` VARCHAR(255) NULL DEFAULT NULL COMMENT '上传证件首页' AFTER `safe_box_id`;



2017-7-11
ALTER TABLE `sys_feedback`
	ADD COLUMN `fid` INT(10) UNSIGNED NULL COMMENT '回复主题' AFTER `id`,
	ADD COLUMN `reply_count` INT UNSIGNED NOT NULL DEFAULT '0' COMMENT '回复数量，只有fid=null时有值' AFTER `ip`;


bug:
干部->离任干部，因私中已分配申请人身份的干部，没有删除；
解决：读取审批人时，过滤干部身份（SystemConstants.ABROAD_APPLICAT_CADRE_STATUS_SET）



select aat.*, a.aac_num, b.aao_num from abroad_applicat_type aat left join (
select count(aac.cadre_id) aac_num, aac.type_id from abroad_applicat_cadre aac, cadre c where c.`status` in(1,4,6) and aac.cadre_id=c.id group by aac.type_id
)a on a.type_id = aat.id
left join(
select count(aao.id) aao_num, aao.applicat_type_id from abroad_approval_order aao group by aao.applicat_type_id
)b on b.applicat_type_id = aat.id


2017-7-5
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