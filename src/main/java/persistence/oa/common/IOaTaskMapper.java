package persistence.oa.common;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface IOaTaskMapper {

    @Select("select count(*) from oa_task task left join oa_task_user taskUser " +
            "on taskUser.task_id = task.id where task.status=#{status} " +
            "and taskUser.user_id=#{userId} and taskUser.is_delete=0 and taskUser.has_report=0")
    public int countTask(@Param("status") Byte status,
                         @Param("userId")Integer userId);

    @Select("select distinct taskUser.user_id from oa_task task left join oa_task_user taskUser " +
            "on taskUser.task_id = task.id where task.status=#{status} " +
            "and taskUser.is_delete=0 and taskUser.has_report=0")
    public List<Integer> getUserIds(@Param("status") Byte status);

    @Update("update oa_task ot, " +
            "(select ot.id, count(distinct otf.id) as file_count, " +
            //"-- 任务对象数量 " +
            "count(distinct otu.id) as user_count, " +
            //"-- 已报送数 " +
            "count(distinct otu3.id) as report_count," +
            //"-- 已完成数 " +
            "count(distinct otu2.id) as finish_count from oa_task ot " +
            "left join oa_task_file otf on otf.task_id=ot.id " +
            "left join oa_task_user otu on otu.task_id = ot.id and otu.is_delete=0 " +
            "left join oa_task_user otu3 on otu3.task_id = ot.id and otu3.is_delete=0 and otu3.status in(0,1) " +
            "left join oa_task_user otu2 on otu2.task_id = ot.id and otu2.is_delete=0 and otu2.status=1 group by ot.id) tmp " +
            "set ot.file_count=tmp.file_count, ot.report_count=tmp.report_count, ot.user_count=tmp.user_count, ot.finish_count=tmp.finish_count where ot.id=tmp.id and ot.id=#{taskId}")
    public void refreshCount(@Param("taskId")Integer taskId);

    @Select("SELECT ogpd.cell_label,sum(ogpd.num) sum from oa_grid_party ogp, oa_grid_party_data ogpd " +
            "WHERE ogpd.grid_party_id=ogp.id AND ogp.grid_id=#{gridId} AND ogp.status=2 " +
            "GROUP BY ogpd.cell_label")
    public List<Map> getOaGridPartyData(@Param("gridId")Integer gridId);
}
