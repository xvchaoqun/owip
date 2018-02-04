package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupTopic;
import domain.sc.scGroup.ScGroupTopicExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupTopicMapper {
    long countByExample(ScGroupTopicExample example);

    int deleteByExample(ScGroupTopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupTopic record);

    int insertSelective(ScGroupTopic record);

    List<ScGroupTopic> selectByExampleWithRowbounds(ScGroupTopicExample example, RowBounds rowBounds);

    List<ScGroupTopic> selectByExample(ScGroupTopicExample example);

    ScGroupTopic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupTopic record, @Param("example") ScGroupTopicExample example);

    int updateByExample(@Param("record") ScGroupTopic record, @Param("example") ScGroupTopicExample example);

    int updateByPrimaryKeySelective(ScGroupTopic record);

    int updateByPrimaryKey(ScGroupTopic record);
}