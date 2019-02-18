package persistence.leader;

import domain.leader.Leader;
import domain.leader.LeaderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LeaderMapper {
    long countByExample(LeaderExample example);

    int deleteByExample(LeaderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Leader record);

    int insertSelective(Leader record);

    List<Leader> selectByExampleWithRowbounds(LeaderExample example, RowBounds rowBounds);

    List<Leader> selectByExample(LeaderExample example);

    Leader selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Leader record, @Param("example") LeaderExample example);

    int updateByExample(@Param("record") Leader record, @Param("example") LeaderExample example);

    int updateByPrimaryKeySelective(Leader record);

    int updateByPrimaryKey(Leader record);
}