package persistence.dr.common;

import domain.dr.DrMember;
import domain.dr.DrOfflineView;
import domain.dr.DrOnlineInspectorType;
import domain.dr.DrOnlineResult;
import domain.unit.Unit;
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

    // 参评人身份列表
    @ResultMap("persistence.dr.DrOnlineInspectorTypeMapper.BaseResultMap")
    @Select("select distinct t.* from dr_online_inspector_log l, dr_online_inspector_type t " +
            "where l.type_id=t.id and l.online_id=#{onlineId} order by l.id desc")
    List<DrOnlineInspectorType>  getInspectorTypes(@Param("onlineId")Integer onlineId);

    // 参评人所在单位列表
    @ResultMap("persistence.unit.UnitMapper.BaseResultMap")
    @Select("select distinct u.* from dr_online_inspector_log l, unit u " +
            "where l.unit_id=u.id and l.online_id=#{onlineId} order by l.id desc")
    List<Unit>  getInspectorUnits(@Param("onlineId")Integer onlineId);
}
