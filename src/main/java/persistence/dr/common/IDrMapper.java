package persistence.dr.common;

import domain.dr.DrMember;
import domain.dr.DrOfflineView;
import domain.dr.DrOnlineResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    //统计所有的结果
    List<DrFinalResult>  selectResultList(@Param("typeIds") List<Integer> typeIds,
                                   @Param("postId") Integer postId,
                                   @Param("onlineId")Integer onlineId,
                                   @Param("realname")String realname,
                                   @Param("unitId")Integer unitId,
                                   RowBounds rowBounds);
    int countResult(@Param("typeIds") List<Integer> typeIds,
                    @Param("postId") Integer postId,
                    @Param("onlineId")Integer onlineId,
                    @Param("realname")String realname,
                    @Param("unitId")Integer unitId);

    @Update("update dr_online_inspector_log l," +
            "(select sum(if(status!=1, 1, 0)) total_count, sum(if(status=2, 1, 0)) finish_count from dr_online_inspector where log_id=#{logId}) tmp " +
            "set l.total_count=tmp.total_count, l.finish_count=tmp.finish_count where l.id=#{logId}")
    void refreshInspectorLogCount(@Param("logId") int logId);

    @Select("select distinct(inspector_type_id) from dr_online_result where online_id=#{onlineId}")
    List<Integer>  selectTypeIds(@Param("onlineId")Integer onlineId);

    @Select("select distinct(i.unit_id) from dr_online_result r, dr_online_inspector i where r.inspector_id=i.id and r.online_id=#{onlineId}")
    List<Integer>  selectUnitIds(@Param("onlineId")Integer onlineId);
}
