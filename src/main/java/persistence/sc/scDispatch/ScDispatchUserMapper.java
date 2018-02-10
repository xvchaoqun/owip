package persistence.sc.scDispatch;

import domain.sc.scDispatch.ScDispatchUser;
import domain.sc.scDispatch.ScDispatchUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScDispatchUserMapper {
    long countByExample(ScDispatchUserExample example);

    int deleteByExample(ScDispatchUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScDispatchUser record);

    int insertSelective(ScDispatchUser record);

    List<ScDispatchUser> selectByExampleWithRowbounds(ScDispatchUserExample example, RowBounds rowBounds);

    List<ScDispatchUser> selectByExample(ScDispatchUserExample example);

    ScDispatchUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScDispatchUser record, @Param("example") ScDispatchUserExample example);

    int updateByExample(@Param("record") ScDispatchUser record, @Param("example") ScDispatchUserExample example);

    int updateByPrimaryKeySelective(ScDispatchUser record);

    int updateByPrimaryKey(ScDispatchUser record);
}