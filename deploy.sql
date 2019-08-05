

-- 部署程序后操作
1. 删除岗位过程管理模块
2. 干部人事信息、职称信息
3. 删除具有人才荣誉称号的干部
4. 调整查看系统附件权限
5. 删除发展党员时间限制


-- 导出数据库结构
mysqldump  -uroot -p'123' --default-character-set=utf8 -d db_owip>/tmp/db_owip.sql
-- 提取基础表数据
mysqldump  -uroot -p'xxx' --default-character-set=utf8 -t -B owip --tables base_annual_type base_content_tpl base_country base_location base_meta_class base_meta_type base_sitemap base_sitemap_role base_stroke_count sys_attach_file sys_role sys_resource sys_config sys_html_fragment sys_property sys_scheduler_job  >/tmp/b.sql
-- 提取控制台账号
mysqldump  -uroot -p'xxx' --default-character-set=utf8 -t -B owip --tables sys_user -w "username='zzbgz'" >/tmp/u.sql


-- 统一修改干部的称谓（仅更新还没设置称谓的）
update sys_user_info ui, cadre c set ui.msg_title=concat(left(ui.realname,1), '老师')
where  ui.user_id=c.user_id and ui.msg_title is null;

-- 更新短信内容
update base_content_tpl set content = replace(content, '18612987573', '13581513455');
update base_content_tpl set content = replace(content, '58808302，58806879', '64434910');
update base_content_tpl set content = replace(content, '58806879', '64434910');
update base_content_tpl set content = replace(content, '58808302', '64434910');
update base_content_tpl set content = replace(content, '64434910、64434910', '64434910');
update base_content_tpl set content = replace(content, 'zzbgz.bnu.edu.cn', 'zzgz.buct.edu.cn');
update base_content_tpl set content = replace(content, '主楼A306', '行政楼216');

-- 更新系统说明
select title,content from sys_html_fragment where content like '%师范%';
update sys_html_fragment set content = replace(content, '北京师范大学', '西安交通大学');
update sys_html_fragment set content = replace(content, '北师大', '西安交大');
update sys_html_fragment set content = replace(content, '18612987573', '13800000000');
update sys_html_fragment set content = replace(content, '58808302', '88888888');
update sys_html_fragment set content = replace(content, '58806879', '88888888');
update sys_html_fragment set content = replace(content, '64434910', '88888888');
update sys_html_fragment set content = replace(content, 'zzbgz.bnu.edu.cn', 'gbgz.xjtu.edu.cn');

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