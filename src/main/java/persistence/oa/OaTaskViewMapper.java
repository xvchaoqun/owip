package persistence.oa;

import domain.oa.OaTaskView;
import domain.oa.OaTaskViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskViewMapper {
    long countByExample(OaTaskViewExample example);

    int deleteByExample(OaTaskViewExample example);

    int insert(OaTaskView record);

    int insertSelective(OaTaskView record);

    List<OaTaskView> selectByExampleWithRowbounds(OaTaskViewExample example, RowBounds rowBounds);

    List<OaTaskView> selectByExample(OaTaskViewExample example);

    int updateByExampleSelective(@Param("record") OaTaskView record, @Param("example") OaTaskViewExample example);

    int updateByExample(@Param("record") OaTaskView record, @Param("example") OaTaskViewExample example);
}