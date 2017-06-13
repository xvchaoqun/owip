package persistence.common;

import domain.sys.SysOnlineStatic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ISysMapper {

    @Select("select distinct parent_code from base_location order by parent_code asc")
    List<Integer> selectDistinctLocationParentCode();

    // 每日最高在线人数流量图
    List<SysOnlineStatic> online_static_day(@Param("start")Date start, @Param("end")Date end);
}
