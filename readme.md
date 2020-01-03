
-- 打包commit文件
cd /cygdrive/d/IdeaProjects/owip 
# 打包更新文件（不含删除文件）
git diff-tree --diff-filter=d -r --no-commit-id --name-only 3c863699bc68e2418e3f0478a39ff53041ff84b4 | xargs tar -cf /cygdrive/d/tmp/update.tar
# 查看删除的文件
git diff-tree --diff-filter=acmr -r --no-commit-id --name-only cdfc827094b3fd6217ce9e72751e0e9e645777c3

#提取全部变更的文件
git diff --name-only --diff-filter=ACMRT HEAD^ | xargs tar -cf patch.tar

#提前“前后”两个版本之间变更的文件
git diff --diff-filter=d be3b61956832229a705810e8064563e58f22b268 16e2fa0bff234badff7bc5c193620575d0fe7211 --name-only | xargs tar -cf diff.zip


-- 忽略文件
git update-index --assume-unchanged pom.xml

-- 撤销commit 
git reset --soft HEAD~1
(如果进行了2次commit，想都撤回，可以使用HEAD~2)

select * from licdc_zg.v_zzry_gz_dydf t;--党员工资详情

-- 查询oracle数据表信息
select owner, column_name, data_type,data_length from all_tab_columns  where Table_Name=upper('v_cjc_ltxf');


## 修改干部工号步骤
1、查询一下变更的工号是否有干部档案，如果没有执行第二步即可。
2、在干部库中使用功能【更换工号】
3、在账号库里更新个人基本信息和头像
4、检查干部档案页的各项信息，如果有交叉需要变更cadre_id


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

# 京外打印
GRANT SELECT ON `db_owip`.`ext_member_out_view` TO 'jwprint'@'219.224.19.177' identified by '&2019!*j(w';
GRANT SELECT ON `db_owip`.`ext_member_out_view` TO 'jwprint'@'219.224.19.222' identified by '&2019!*j(w';

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

菜单说明
1、菜单样式提供给1级菜单
2、menu和url都会显示在菜单栏，function不会
3、当function需要访问地址时，需要填入url路径，这样才会打开所属上级url所在菜单的状态


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