package persistence.oa;

import domain.oa.OaTaskRemind;
import domain.oa.OaTaskRemindExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskRemindMapper {
    long countByExample(OaTaskRemindExample example);

    int deleteByExample(OaTaskRemindExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTaskRemind record);

    int insertSelective(OaTaskRemind record);

    List<OaTaskRemind> selectByExampleWithRowbounds(OaTaskRemindExample example, RowBounds rowBounds);

    List<OaTaskRemind> selectByExample(OaTaskRemindExample example);

    OaTaskRemind selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTaskRemind record, @Param("example") OaTaskRemindExample example);

    int updateByExample(@Param("record") OaTaskRemind record, @Param("example") OaTaskRemindExample example);

    int updateByPrimaryKeySelective(OaTaskRemind record);

    int updateByPrimaryKey(OaTaskRemind record);
}