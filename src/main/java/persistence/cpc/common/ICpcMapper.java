package persistence.cpc.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import service.unit.UnitPostAllocationStatBean;
import sys.constants.CadreConstants;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICpcMapper {

    // 根据 单位类型分组 统计已设定的干部职数  (isMainPost=null)
    @Select("select ca.admin_level as adminLevel, sum(ca.num) as num from unit_post_count_view ca, unit u, base_meta_type ut " +
            "where ca.unit_id=u.id and u.status=1 and u.type_id=ut.id and ut.extra_attr=#{unitTypeGroup} group by ca.admin_level")
    public List<UnitPostAllocationStatBean> cpcStat_setting(@Param("unitTypeGroup") String unitTypeGroup);

    // 根据单位类型分组、行政级别、职务类别（主职、兼职）统计实际的干部职数
    @Select("select cp.admin_level as adminLevel, cp.is_main_post as isMainPost, count(*) as num " +
            "from cadre_post cp , unit u, base_meta_type ut, cadre c " +
            "where (cp.is_main_post=1 or (cp.is_main_post=0 and cp.is_cpc=1)) " +
            "and exists(select 1 from unit_post_count_view where unit_id=cp.unit_id) " +
            "and cp.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitTypeGroup} and cp.cadre_id=c.id " +
            "and c.status in(" + CadreConstants.CADRE_STATUS_MIDDLE + "," + CadreConstants.CADRE_STATUS_LEADER + ") " +
            "group by cp.admin_level, cp.is_main_post")
    public List<UnitPostAllocationStatBean> cpcStat_real(@Param("unitTypeGroup") String unitTypeGroup);
}
