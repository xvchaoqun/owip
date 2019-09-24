package persistence.cg.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ICgMapper {

    @Select("select distinct cm.id from cg_member as cm left join " +
            "(unit_post_view as upv left join cadre on (cadre.id = upv.cadre_id)) on upv.id = cm.unit_post_id " +
            "where isnull(cm.user_id) != isnull(cadre.user_id) and cm.is_current = 1 and cm.type = 1")
    public List<Integer> getNeedAdjustMember();

    @Select("select count(*) from cg_member where team_id = #{id} and is_current = 1 and need_adjust = 1")
    public Integer getCountNeedAdjust(@Param("id") Integer id);
}
