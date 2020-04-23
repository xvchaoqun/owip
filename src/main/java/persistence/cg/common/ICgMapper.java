package persistence.cg.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ICgMapper {

    //查询现任干部有变化的委员会和领导小组成员
    public List<Integer> getNeedAdjustMember(@Param("teamIds") Integer[] teamIds);

    @Select("select count(*) from cg_member where team_id = #{id} and is_current = 1 and need_adjust = 1")
    public Integer getCountNeedAdjust(@Param("id") Integer id);
}
