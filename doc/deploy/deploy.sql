

-- 部署程序后操作
1. 删除岗位过程管理模块
2. 干部人事信息、职称信息，删除工勤、管理岗位信息
3. 删除具有人才荣誉称号的干部
4. 调整查看系统附件权限
5. 删除发展党员时间限制


# 导出数据库结构
mysqldump  -uroot -p123 --default-character-set=utf8 -d db_owip>D:/tmp/db_owip.sql
# 提取基础表数据
mysqldump  -uroot -p123 --default-character-set=utf8 -t -B db_owip --tables base_annual_type base_content_tpl base_country base_location base_meta_class base_meta_type base_sitemap base_sitemap_role base_stroke_count base_layer_type sys_attach_file sys_role sys_resource sys_config sys_html_fragment sys_property sys_scheduler_job  >> D:/tmp/db_owip.sql
# 提取控制台账号
mysqldump  -uroot -p123 --default-character-set=utf8 -t -B db_owip --tables sys_user -w "username='zzbgz'" >> D:/tmp/db_owip.sql
#替换 `db_owip`.

# 提取部分表结构
mysqldump  -uroot -p123 --default-character-set=utf8 -t -B db_owip --tables ext_jzg ext_bks ext_yjs  >> /tmp/t.sql


-- 统一修改干部的称谓（仅更新还没设置称谓的）
update sys_user_info ui, cadre c set ui.msg_title=concat(left(ui.realname,1), '老师')
where  ui.user_id=c.user_id and ui.msg_title is null;

--
update sys_role set remark=null;


-- 处理空格和换行
UPDATE cadre SET  title = REPLACE(REPLACE(title, CHAR(10), ''), CHAR(13), '');
UPDATE cadre SET  post = REPLACE(REPLACE(post, CHAR(10), ''), CHAR(13), '');
-- 导入后干部逆序
select max(sort_order) from cadre into @max;
update cadre set sort_order = @max - sort_order + 1;


-- 批量删除表
SET SESSION group_concat_max_len=102400;
select group_concat(CONCAT( 'drop ', if(table_type='VIEW', 'view ', 'table '), table_name) SEPARATOR ';')
FROM information_schema.tables
Where table_schema='db_owip_xjtu' and table_name REGEXP '^(pmd|cis|cet|pcs|oa|crs|ow).*'
and table_name not in('ow_party', 'ow_branch', 'ow_member',  'ow_member_view', 'ow_member_out');


-- 批量删除jsp模块
cd jsp目录
rm -rf pmd cis cet pcs oa verify crs member party