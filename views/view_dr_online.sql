
-- 修改view
DROP VIEW IF EXISTS `dr_online_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_online_post_view` AS
SELECT dop.*,do.type AS online_type,ifnull(doc1.exist_num,0) AS exist_num,
up.name,up.job,up.admin_level,up.post_type,up.unit_id,u.type_id
FROM dr_online_post dop
LEFT join dr_online do ON(do.id=dop.online_id)
LEFT JOIN unit_post up ON(up.id=dop.unit_post_id)
LEFT JOIN unit u ON(u.id=up.unit_id)
LEFT JOIN (SELECT doc.post_id,COUNT(doc.id) AS exist_num FROM dr_online_candidate doc GROUP BY post_id) doc1 ON (doc1.post_id=dop.id);

DROP VIEW IF EXISTS `dr_online_result_view`;
CREATE ALGORITHM=UNDEFINED VIEW `dr_online_result_view` AS
SELECT dor1.*,doc.user_id,doil1.pub_counts,doil1.finish_counts
FROM (SELECT dor.online_id,dor.post_id,dor.candidate_id,sum(if(dor.ins_option=1, 1, 0)) AS option_sum
					FROM dr_online_result dor GROUP BY dor.post_id,dor.candidate_id) dor1
LEFT JOIN (SELECT doil.online_id,SUM(doil.pub_count) AS pub_counts,SUM(doil.finish_count)AS finish_counts
				FROM dr_online_inspector_log doil
				GROUP BY doil.online_id) doil1 ON doil1.online_id=dor1.online_id
LEFT JOIN dr_online_candidate doc ON doc.id=dor1.candidate_id;