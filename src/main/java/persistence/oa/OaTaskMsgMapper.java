package persistence.oa;

import domain.oa.OaTaskMsg;
import domain.oa.OaTaskMsgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskMsgMapper {
    long countByExample(OaTaskMsgExample example);

    int deleteByExample(OaTaskMsgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTaskMsg record);

    int insertSelective(OaTaskMsg record);

    List<OaTaskMsg> selectByExampleWithRowbounds(OaTaskMsgExample example, RowBounds rowBounds);

    List<OaTaskMsg> selectByExample(OaTaskMsgExample example);

    OaTaskMsg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTaskMsg record, @Param("example") OaTaskMsgExample example);

    int updateByExample(@Param("record") OaTaskMsg record, @Param("example") OaTaskMsgExample example);

    int updateByPrimaryKeySelective(OaTaskMsg record);

    int updateByPrimaryKey(OaTaskMsg record);
}