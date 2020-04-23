
-- drOnline--view
DROP VIEW IF EXISTS `dr_online_post_view`;
CREATE ALGORITHM = UNDEFINED VIEW `dr_online_post_view` AS
SELECT dop.*,do.type AS online_type,ifnull(doc1.exist_num,0) AS exist_num,
up.name,up.job,up.admin_level,up.post_type,up.unit_id,u.type_id
FROM dr_online_post dop
LEFT join dr_online do ON(do.id=dop.online_id)
LEFT JOIN unit_post up ON(up.id=dop.unit_post_id)
LEFT JOIN unit u ON(u.id=up.unit_id)
LEFT JOIN (SELECT doc.post_id,COUNT(doc.id) AS exist_num FROM dr_online_candidate doc GROUP BY post_id) doc1 ON (doc1.post_id=dop.id);
