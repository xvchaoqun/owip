package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupTopicUser;
import domain.sc.scGroup.ScGroupTopicUserExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupTopicUserMapper {
    long countByExample(ScGroupTopicUserExample example);

    int deleteByExample(ScGroupTopicUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScGroupTopicUser record);

    int insertSelective(ScGroupTopicUser record);

    List<ScGroupTopicUser> selectByExampleWithRowbounds(ScGroupTopicUserExample example, RowBounds rowBounds);

    List<ScGroupTopicUser> selectByExample(ScGroupTopicUserExample example);

    ScGroupTopicUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScGroupTopicUser record, @Param("example") ScGroupTopicUserExample example);

    int updateByExample(@Param("record") ScGroupTopicUser record, @Param("example") ScGroupTopicUserExample example);

    int updateByPrimaryKeySelective(ScGroupTopicUser record);

    int updateByPrimaryKey(ScGroupTopicUser record);
}