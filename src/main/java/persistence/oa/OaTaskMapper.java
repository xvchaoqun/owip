package persistence.oa;

import domain.oa.OaTask;
import domain.oa.OaTaskExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OaTaskMapper {
    long countByExample(OaTaskExample example);

    int deleteByExample(OaTaskExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTask record);

    int insertSelective(OaTask record);

    List<OaTask> selectByExampleWithRowbounds(OaTaskExample example, RowBounds rowBounds);

    List<OaTask> selectByExample(OaTaskExample example);

    OaTask selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTask record, @Param("example") OaTaskExample example);

    int updateByExample(@Param("record") OaTask record, @Param("example") OaTaskExample example);

    int updateByPrimaryKeySelective(OaTask record);

    int updateByPrimaryKey(OaTask record);
}