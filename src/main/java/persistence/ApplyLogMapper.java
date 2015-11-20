package persistence;

import domain.ApplyLog;
import domain.ApplyLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ApplyLogMapper {
    int countByExample(ApplyLogExample example);

    int deleteByExample(ApplyLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplyLog record);

    int insertSelective(ApplyLog record);

    List<ApplyLog> selectByExampleWithRowbounds(ApplyLogExample example, RowBounds rowBounds);

    List<ApplyLog> selectByExample(ApplyLogExample example);

    ApplyLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplyLog record, @Param("example") ApplyLogExample example);

    int updateByExample(@Param("record") ApplyLog record, @Param("example") ApplyLogExample example);

    int updateByPrimaryKeySelective(ApplyLog record);

    int updateByPrimaryKey(ApplyLog record);
}