

-- 更新角色说明

update sys_role set remark =null;


-- 更新短信内容
update base_content_tpl set content = replace(content, '18612987573', '13800000000');
update base_content_tpl set content = replace(content, '58808302，58806879', '88888888');
update base_content_tpl set content = replace(content, '58806879', '88888888');
update base_content_tpl set content = replace(content, '58808302', '88888888');
update base_content_tpl set content = replace(content, '64434910、64434910', '88888888');
update base_content_tpl set content = replace(content, '88888888、88888888', '88888888');
update base_content_tpl set content = replace(content, 'zzbgz.bnu.edu.cn', 'zzgz.xxx.edu.cn');
update base_content_tpl set content = replace(content, '主楼A306', '主楼XXX');

update sys_html_fragment set content = replace(content, '18612987573', '13800000000');
update sys_html_fragment set content = replace(content, '58808302，58806879', '88888888');
update sys_html_fragment set content = replace(content, '58806879', '88888888');
update sys_html_fragment set content = replace(content, '58808302', '88888888');
update sys_html_fragment set content = replace(content, '64434910、64434910', '88888888');
update sys_html_fragment set content = replace(content, '88888888、88888888', '88888888');
update sys_html_fragment set content = replace(content, 'zzbgz.bnu.edu.cn', 'zzgz.xxx.edu.cn');
update sys_html_fragment set content = replace(content, '主楼A306', '主楼XXX');

update sys_html_fragment set content = replace(content, '北京师范大学', '北京xxx大学');
update sys_html_fragment set content = replace(content, '北师大', '北x');

set @keyowrd='师范';
update sys_attach_file set filename = replace(filename, @keyowrd, repeat('*', char_length(@keyowrd))) where filename like concat('%',@keyowrd,'%');
set @keyowrd='师大';
update sys_attach_file set filename = replace(filename, @keyowrd, repeat('*', char_length(@keyowrd))) where filename like concat('%',@keyowrd,'%');








