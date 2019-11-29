package persistence.oa.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface IOaTaskUserMapper {

    @Select("select count(*) from oa_task task left join oa_task_user taskUser " +
            "on taskUser.task_id = task.id where task.status=#{status} " +
            "and taskUser.user_id=#{userId} and taskUser.is_delete=0 and taskUser.has_report=0")
    public int countTask(@Param("status") Byte status,
                         @Param("userId")Integer userId);

    @Select("select distinct taskUser.user_id from oa_task task left join oa_task_user taskUser " +
            "on taskUser.task_id = task.id where task.status=#{status} " +
            "and taskUser.is_delete=0 and taskUser.has_report=0")
    public List<Integer> getUserIds(@Param("status") Byte status);
}
