package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopicCadre;
import domain.sc.scCommittee.ScCommitteeTopicCadreExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeTopicCadreMapper {
    long countByExample(ScCommitteeTopicCadreExample example);

    int deleteByExample(ScCommitteeTopicCadreExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScCommitteeTopicCadre record);

    int insertSelective(ScCommitteeTopicCadre record);

    List<ScCommitteeTopicCadre> selectByExampleWithRowbounds(ScCommitteeTopicCadreExample example, RowBounds rowBounds);

    List<ScCommitteeTopicCadre> selectByExample(ScCommitteeTopicCadreExample example);

    ScCommitteeTopicCadre selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScCommitteeTopicCadre record, @Param("example") ScCommitteeTopicCadreExample example);

    int updateByExample(@Param("record") ScCommitteeTopicCadre record, @Param("example") ScCommitteeTopicCadreExample example);

    int updateByPrimaryKeySelective(ScCommitteeTopicCadre record);

    int updateByPrimaryKey(ScCommitteeTopicCadre record);
}