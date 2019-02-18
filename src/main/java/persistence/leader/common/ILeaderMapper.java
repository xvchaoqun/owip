package persistence.leader.common;

import domain.leader.LeaderUnitView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ILeaderMapper {

    //查询校领导的分管单位
    @Select("select unit_id from leader_unit where user_id = #{userId} and type_id=#{leaderTypeId}")
    List<Integer> getLeaderManagerUnitId(@Param("userId") Integer userId, @Param("leaderTypeId") Integer leaderTypeId);

    //查询分管当前单位的校级领导分工
    @ResultMap("persistence.leader.LeaderUnitViewMapper.BaseResultMap")
    @Select("select * from leader_unit_view where type_id=#{leaderTypeId} and unit_id = #{unitId}")
    List<LeaderUnitView> getManagerUnitLeaders(@Param("unitId") Integer unitId, @Param("leaderTypeId") Integer leaderTypeId);
}


