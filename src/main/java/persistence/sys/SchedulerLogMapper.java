package persistence.sys;

import domain.sys.SchedulerLog;
import domain.sys.SchedulerLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SchedulerLogMapper {
    long countByExample(SchedulerLogExample example);

    int deleteByExample(SchedulerLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SchedulerLog record);

    int insertSelective(SchedulerLog record);

    List<SchedulerLog> selectByExampleWithRowbounds(SchedulerLogExample example, RowBounds rowBounds);

    List<SchedulerLog> selectByExample(SchedulerLogExample example);

    SchedulerLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SchedulerLog record, @Param("example") SchedulerLogExample example);

    int updateByExample(@Param("record") SchedulerLog record, @Param("example") SchedulerLogExample example);

    int updateByPrimaryKeySelective(SchedulerLog record);

    int updateByPrimaryKey(SchedulerLog record);
}