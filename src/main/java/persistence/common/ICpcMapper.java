package persistence.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import service.cpc.CpcStatBean;
import sys.constants.SystemConstants;

import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ICpcMapper {

    // 根据单位类型（jg/xy/fs)统计已设定的干部职数  (isMainPost=null)
    @ResultType(service.cpc.CpcStatBean.class)
    @Select("select ca.admin_level_id as adminLevelId, sum(ca.num) as num from cpc_allocation ca, unit u, base_meta_type ut " +
            "where ca.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitType} group by ca.admin_level_id;")
    public List<CpcStatBean> cpcStat_setting(@Param("unitType") String unitType);

    // 根据单位类型（jg/xy/fs)、行政级别、职务类别（主职、兼职）统计实际的干部职数
    @ResultType(service.cpc.CpcStatBean.class)
    @Select("select cp.admin_level_id as adminLevelId, cp.is_main_post as isMainPost, count(*) as num " +
            "from cadre_post cp , unit u, base_meta_type ut, cadre c " +
            "where (cp.is_main_post=1 or (cp.is_main_post=0 and cp.is_cpc=1)) " +
            "and cp.unit_id in(select distinct unit_id from cpc_allocation) " +
            "and cp.unit_id=u.id and u.type_id=ut.id and ut.extra_attr=#{unitType} and cp.cadre_id=c.id " +
            "and c.status in(" + SystemConstants.CADRE_STATUS_MIDDLE + "," + SystemConstants.CADRE_STATUS_LEADER + ") " +
            "group by cp.admin_level_id, cp.is_main_post")
    public List<CpcStatBean> cpcStat_real(@Param("unitType") String unitType);
}
