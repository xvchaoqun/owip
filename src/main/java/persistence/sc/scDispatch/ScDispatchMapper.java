package persistence.sc.scDispatch;

import domain.sc.scDispatch.ScDispatch;
import domain.sc.scDispatch.ScDispatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScDispatchMapper {
    long countByExample(ScDispatchExample example);

    int deleteByExample(ScDispatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScDispatch record);

    int insertSelective(ScDispatch record);

    List<ScDispatch> selectByExampleWithRowbounds(ScDispatchExample example, RowBounds rowBounds);

    List<ScDispatch> selectByExample(ScDispatchExample example);

    ScDispatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScDispatch record, @Param("example") ScDispatchExample example);

    int updateByExample(@Param("record") ScDispatch record, @Param("example") ScDispatchExample example);

    int updateByPrimaryKeySelective(ScDispatch record);

    int updateByPrimaryKey(ScDispatch record);
}