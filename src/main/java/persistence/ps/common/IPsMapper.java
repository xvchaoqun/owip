package persistence.ps.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface IPsMapper {
    Map count(@Param("partyIdList") List partyIdList);

    @Select("select count(*) from ps_admin_party where admin_id = #{id} and is_history = 0;")
    public Integer getCountParty(@Param("id") Integer id);

    @Select("select count(*) from ps_task_file where task_id = #{id};")
    public Integer getCountFile(@Param("id") Integer id);

}
