
组织工作管理与服务一体化平台
Organization Work Integration Platform


拖动上下排序功能，要求
1、sort_order>0且不可重复
2、sort_order 降序排序
3.sort_order必须初始化赋值，且MySQL中sort_order = LAST_INSERT_ID()+1（需修改Mybatis Mapper文件）


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
