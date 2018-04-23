package persistence.common;

import domain.sys.SysOnlineStatic;
import domain.sys.SysUserView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * Created by lm on 2017/6/13.
 */
public interface ISysMapper {


    List<SysUserView> selectUserList(@Param("query")String query, @Param("types") Byte[] types, RowBounds rowBounds);
    int countUserList(@Param("query")String query, @Param("types") Byte[] types);

    // 地区（一级）
    @Select("select distinct parent_code from base_location order by parent_code asc")
    List<Integer> selectDistinctLocationParentCode();

    // 每日最高在线人数流量图
    List<SysOnlineStatic> online_static_day(@Param("start")Date start, @Param("end")Date end);
}
