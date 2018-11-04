package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopic;
import domain.sc.scCommittee.ScCommitteeTopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeTopicMapper {
    long countByExample(ScCommitteeTopicExample example);

    int deleteByExample(ScCommitteeTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommitteeTopic record);

    int insertSelective(ScCommitteeTopic record);

    List<ScCommitteeTopic> selectByExampleWithRowbounds(ScCommitteeTopicExample example, RowBounds rowBounds);

    List<ScCommitteeTopic> selectByExample(ScCommitteeTopicExample example);

    ScCommitteeTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommitteeTopic record, @Param("example") ScCommitteeTopicExample example);

    int updateByExample(@Param("record") ScCommitteeTopic record, @Param("example") ScCommitteeTopicExample example);

    int updateByPrimaryKeySelective(ScCommitteeTopic record);

    int updateByPrimaryKey(ScCommitteeTopic record);
}