
172.16.181.10:1521/gbklicdc
licdc_zg/neusoft
select * from licdc_zg.V_YJS_YJSXJJBXX t;--研究生学籍基本信息
select * from licdc_zg.v_bks_xjjbsjxx t;--本科生学籍基本信息
select * from licdc_zg.v_jzg_rs_jzg_jbxx t;--教职工基本信息


select count(*) from licdc_zg.V_YJS_YJSXJJBXX t
select xh from licdc_zg.V_YJS_YJSXJJBXX group by xh having count(*)>1;

select zgh from licdc_zg.v_jzg_rs_jzg_jbxx group by zgh having count(*)>1;

select * from licdc_zg.v_jzg_rs_jzg_jbxx where zgh in('11312015250', '11112015045');

select count(*) from licdc_zg.v_bks_xjjbsjxx t

select xh from licdc_zg.v_bks_xjjbsjxx group by xh having count(*)>1;


##### 测试系统
http://219.224.31.45/
（219.224.31.45 -> 219.224.21.144（师大组织部））
