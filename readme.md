
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
zzgz@2015

http://219.224.19.170:8080/
admin
111111
