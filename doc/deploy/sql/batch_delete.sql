








-- 批量删除表、视图
SET FOREIGN_KEY_CHECKS=0;

set @db = 'db_owip_xjtu';
set @prefix = 'cet_%';

SELECT CONCAT( 'DROP TABLE ', GROUP_CONCAT(table_name) , ';' )
    AS statement FROM information_schema.tables
    WHERE table_schema = @db AND table_name LIKE @prefix;

SELECT CONCAT( 'DROP VIEW ', GROUP_CONCAT(table_name) , ';' )
    AS statement FROM information_schema.tables
    WHERE table_schema = @db AND table_name LIKE @prefix;

show tables like 'cet_%';