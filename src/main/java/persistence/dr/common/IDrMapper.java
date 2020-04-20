package persistence.dr.common;

import domain.dr.DrMember;
import domain.dr.DrOfflineView;
import domain.dr.DrOnlineResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface IDrMapper {

    @ResultMap("persistence.dr.DrOfflineViewMapper.BaseResultMap")
    @Select("select * from dr_offline_view where id=#{id}")
    DrOfflineView getDrOfflineView(@Param("id") int id);

    // 根据账号、姓名、学工号查找推荐组成员
    List<DrMember> selectMemberList(@Param("status") Byte status,
                                    @Param("search") String search, RowBounds rowBounds);
    int countMemberList(@Param("status") Byte status, @Param("search") String search);

    //批量插入推荐结果
    int batchInsert_result(@Param("records") List<DrOnlineResult> resultList);

    //所有的统计结果
    List<DrFinalResult>  resultOne(@Param("typeIds") List<Integer> typeIds,
                                   @Param("onlineId")Integer onlineId);
    int countResult(@Param("typeIds") List<Integer> typeIds,
                    @Param("onlineId")Integer onlineId);

}
