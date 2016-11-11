

# 重置因私申请审批状态
delete from abroad_approval_log where apply_id=203;
update abroad_apply_self set flow_node=-1, flow_nodes=null where id=203;
# 重置领取证件归还状态
update abroad_passport_draw apd, abroad_passport p set p.is_lent=1, apd.draw_status=1 , apd.use_record=null, 
apd.use_passport=null, apd.real_return_date=null where apd.id=147 and p.id=apd.passport_id;

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


自动生成代码步骤
1、生成Mybatis文件，修改sort_order
2、运行TplParser.execute
3、修改Controller和Service错误，添加encache缓存
4、修改页面

服务器
219.224.19.170
root
zzgz)(*QAZ!@#zzgz
数据库
longtor@2016

219.143.237.234 root  zzgz@2015


zzbgz2016  longtor@2016

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

