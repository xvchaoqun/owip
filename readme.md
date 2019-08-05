

select * from licdc_zg.v_zzry_gz_dydf t;--党员工资详情

-- 查询oracle数据表信息
select owner, column_name, data_type,data_length from all_tab_columns  where Table_Name=upper('v_cjc_ltxf');


### 查询某个党委的离退休费
select m.code, m.realname, s.ltxf from ow_member_teacher m
left join ext_retire_salary s on s.zgh = m.code and s.rq=(select max(rq) from ext_retire_salary)
 where m.party_id=21 and m.status=1 and ltxf is not null;


####
-- 当月未确认额度的记录
select u.code, u.realname from pmd_member pm
left join sys_user_view u on u.id=pm.user_id
left join pmd_config_member pcm on pcm.user_id=pm.user_id
left join pmd_config_member_type pcmt on pcm.config_member_type_id=pcmt.id
left join pmd_norm pn on pn.id = pcmt.norm_id
 where pm.has_pay=0 and  pm.pay_month='2018-05-01' and pcm.has_reset=0 and pn.set_type=2;

-- 更新当月未确认额度的记录
update pmd_member pm
left join sys_user_view u on u.id=pm.user_id
left join pmd_config_member pcm on pcm.user_id=pm.user_id
left join pmd_config_member_type pcmt on pcm.config_member_type_id=pcmt.id
left join pmd_norm pn on pn.id = pcmt.norm_id
set pcm.has_reset=1, pm.due_pay = pcm.due_pay
where pm.has_pay=0 and  pm.pay_month='2018-05-01' and pcm.has_reset=0 and pn.set_type=2;

-- 把某个缴费账号替换成新账号 （ 用于党建账号错误的情况，先应做转出转入操作，保证新账号是党员）
set @oriCode = '11312015116';
set @destCode = '11122016032';
-- 这里有问题，在sh下无法获取??
set @oriUserId = (select id from sys_user where code=@oriCode);
select @oriUserId;
set @destUserId = (select id from sys_user where code=@destCode);
select @destUserId;
update pmd_config_member set user_id=@destUserId where user_id=@origUserId;
update pmd_member set user_id=@destUserId where user_id=@origUserId;


############
## 视图权限
drop user data@219.224.19.33;
revoke select on db_owip.ext_branch_view from data@219.224.19.33;
revoke select on db_owip.ext_member_view from data@219.224.19.33;
revoke select on db_owip.ext_cadre_view from data@219.224.19.33;

select host,user from mysql.user;

show grants for data@219.224.19.33;

drop user data@172.16.181.13;
GRANT SELECT ON `db_owip`.`ext_branch_view` TO 'data'@'172.16.181.13' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_branch_view2` TO 'data'@'172.16.181.13' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_member_view` TO 'data'@'172.16.181.13' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_cadre_view` TO 'data'@'172.16.181.13' identified by '!@#dataQAZ';

drop user data@172.16.214.21;
GRANT SELECT ON `db_owip`.`ext_branch_view` TO 'data'@'172.16.214.21' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_member_view` TO 'data'@'172.16.214.21' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_cadre_view` TO 'data'@'172.16.214.21' identified by '!@#dataQAZ';

drop user data@219.224.19.40;
GRANT SELECT ON `db_owip`.`ext_branch_view` TO 'data'@'219.224.19.40' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_branch_view2` TO 'data'@'219.224.19.40' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_member_view` TO 'data'@'219.224.19.40' identified by '!@#dataQAZ';
GRANT SELECT ON `db_owip`.`ext_cadre_view` TO 'data'@'219.224.19.40' identified by '!@#dataQAZ';

# lxxt 20180615
drop user lxxt@219.224.19.48;
GRANT SELECT ON `db_owip`.`ext_branch_view` TO 'lxxt'@'219.224.19.48' identified by 'lxxt!@#QAZ';
GRANT SELECT ON `db_owip`.`ext_member_view` TO 'lxxt'@'219.224.19.48' identified by 'lxxt!@#QAZ';
#20190520
GRANT SELECT ON `db_owip`.`ext_branch_view` TO 'lxxt'@'219.224.19.228' identified by 'lxxt!@#QAZ';
GRANT SELECT ON `db_owip`.`ext_member_view` TO 'lxxt'@'219.224.19.228' identified by 'lxxt!@#QAZ';

flush privileges;

############
# 重置因私申请审批状态
set @applyId=419;
delete from abroad_approval_log where apply_id=@applyId;
update abroad_apply_self set status=1, is_finish=0, flow_node=-1, flow_nodes=null where id=@applyId;

# 重置 “领取证件” 操作
@drawId = 804;
update abroad_passport_draw set return_date=null, draw_record=null, draw_user_id=null, draw_status=0  where id=@drawId;
update abroad_passport_draw d, abroad_passport p set p.is_lent=0 where d.passport_id=p.id and d.id=@drawId;

## 因私审批误操作为不同意，更新为同意
select id, status,approval_remark,is_finish,flow_node,flow_nodes,flow_users,is_agreed from abroad_apply_self where id=498;
select id, user_id, type_id, od_type,status from abroad_approval_log where apply_id=498;
update abroad_approval_log set status=1 where id=1152;
select id, record_id, apply_user_id, type,stage, status from sys_approval_log where type=1 and record_id=498;
update sys_approval_log set status=1 where id=58493;

# 更换领取证件证件号码（更换的证件应该是未借出状态；如果原来关联的证件是借出状态，可能需要修改为未借出状态）
select ap.is_lent from abroad_passport_draw apd, abroad_passport ap where apd.id=196 and ap.id=apd.passport_id;
update abroad_passport_draw apd, abroad_passport ap 
set apd.passport_id=ap.id, ap.is_lent=1 
where ap.code='E11700904' and apd.id=196 and ap.is_lent=0 and ap.cadre_id=apd.cadre_id; // 未借出且属于同一干部

# 批量同步入党申请里预备党员的入党时间 到 党员库中
select om.user_id, om.grow_time, oma.grow_time from ow_member om, ow_member_apply oma where oma.user_id=om.user_id and oma.stage=6 and oma.grow_time is not null and om.grow_time!=oma.grow_time;
select  om.user_id, om.grow_time, oma.grow_time  from ow_member om, ow_member_apply oma where oma.user_id=om.user_id and oma.stage=6 and oma.grow_time is not null and om.grow_time is null;
update ow_member om, ow_member_apply oma set om.grow_time=oma.grow_time where oma.user_id=om.user_id and oma.stage=6 and oma.grow_time is not null and om.grow_time!=oma.grow_time;
update ow_member om, ow_member_apply oma set om.grow_time=oma.grow_time where oma.user_id=om.user_id and oma.stage=6 and oma.grow_time is not null and om.grow_time is null;


# 查询已经转入申请但未审核，被管理员后台添加党员的情况 （已处理：审核时提示打回）
select mi.id, mi.user_id, mi.status,mi.create_time, m.create_time, u.code from ow_member_in mi, ow_member m , sys_user u 
where mi.user_id=m.user_id and m.status=1 and mi.status<=0 and u.id=m.user_id;

# 查询已转出党员， 仍然在党员库中的情况（原因：在党员已经转出审批完成的情况下，进行了转入操作，结果又进入了党员库）
select mi.id, mi.user_id, mi.status, m.status m_stauts, mi.apply_time, m.create_time, u.code from ow_member_out mi, ow_member m, sys_user u where mi.user_id=m.user_id and m.status=1 and mi.status=2 and u.id=m.user_id;

组织工作管理与服务一体化平台
Organization Work Integration Platform

菜单说明
1、菜单样式提供给1级菜单
2、menu和url都会显示在菜单栏，function不会
3、当function需要访问地址时，需要填入url路径，这样才会打开所属上级url所在菜单的状态

拖动上下排序功能，要求
<有bug>
1、sort_order>0且不可重复
2、sort_order 降序排序
3.sort_order必须初始化赋值，且MySQL中sort_order = LAST_INSERT_ID()+1（需修改Mybatis Mapper文件）
<新方法>
在insert方法返回id，然后更新sort_order


检查排序功能是否有问题
select * , count(*) from cadre group by status, sort_order having count(*)>1;
select *  from cadre where status=1 and sort_order=410;


自动生成代码步骤
1、生成Mybatis文件，修改sort_order
2、运行TplParser.execute
3、修改Controller和Service错误，添加ehcache缓存
4、修改页面

服务器
219.224.19.170
root
zzgz)(*QAZ!@#zzgz
数据库
longtor@2016

备份机：219.224.19.177

219.143.237.234 root  zzgz@2015


http://cas.bnu.edu.cn/cas/login?service=http%3A%2F%2Fzzbgz.bnu.edu.cn%2F
http://219.224.19.170/
admin  zzbgb6879/ longtor@2016

http://cas.bnu.edu.cn/cas/login?service=http%3A%2F%2Fzzbgz.bnu.edu.cn%3A9090%2F
http://219.224.19.170:9090/
admin  test123


1、还不需要进行审批的审批记录， 不显示
2、审批之后的审批记录， 一直保留显示
3、撤除身份后，所有相关记录不显示
4、新任领导， 前任的已处理记录不显示，未处理记录需要显示

1、某个领导名下的 未处理的数据，一般不会超过50条
2、如果是该领导中间审批过的， 记录完成了全部的审批流程， 也需要保留显示


我和徐老师讨论的方案：
1. 如果本单位正职换人了，新领导可以看老领导审批的内容，老领导的审批权限就此结束，也不能看之前审批过的了。
2. 如果分管校领导换了分管单位，比如刘利原来分管组织部，现在不再分管组织部，分管别的部分了。那么，之前他所批的组织部的记录仍然可以看到，但是从此不再有审批组织部的权限了，也不能再看新的分管校领导审批组织部的记录。


在【证件管理】里面，增加一个“保险柜管理”。
然后“添加证件信息”的“存放保险柜编号”就直接选择。
所有的证件的存放保险柜编号都设定好之后，在“保险柜管理”中，每个保险柜都有什么证件，都显示出来
所以在这个地方设置“保险箱管理”。不管证件在哪个地方，只要在柜子里，都显示出来。在每个证件后面标注证件的状态。
是的。另外还有“是否借出”。


短信接口：
Https请求方式: POST
URL地址： https://weixin.bnu.edu.cn/sms/massms.php?id=2
内容是json格式（UTF-8编码）
{
   "mobile": "18612345678",
   "content": "短信内容"
}
参数说明：
mobile表示手机号（多个手机号用英文;分割，一次不超过400个）
content表示发送的短信内容，长度不超过350字


2016-09-02 确认需求
1、集中管理的 或 未确认的取消集中管理证件，才需要系统短信提醒
（发送规则修改为：自动发送，发送时间为上午10点，每三天发一次，直到将证件交回。比如，应交组织部日期为2016年9月1日，那么从第二天9月2日开始发，每三天发一次，直到交回证件为止。
）
2、已确认集中管理、已丢失的证件， 在领取证件中 显示为 "已取消集中管理" 或 “证件丢失”， 归还证件按钮显示为“-”
2、如果该证件找回之前被借出了，则找回时，应该修改领取证件中归还的状态为已归还

owip:organization work integrated platform 组织工作管理与服务一体化平台
ow:organization work 组织工作
cis: cadre inspection system 干部考察系统
ces: cadre evaluation system 干部考核系统
cpc: cadre post count 干部职数
crp: cadre role play 干部挂职锻炼
crs: cadre recruit system 干部招聘系统
dr: Democratic recommendation 民主推荐
pcs：Party Congress System 党代会信息管理系统
pr:Party representative 党代表
ps:Party School 二级党校
cm:committee member 委员
oa:Office Automation 办公自动化
pmd:party membership dues 党费
sc:select cadres 选拔干部
cet:Cadre education training 干部教育培训系统
cla:Cadre leave approval 干部请假审批
dp:democratic party 民主党派