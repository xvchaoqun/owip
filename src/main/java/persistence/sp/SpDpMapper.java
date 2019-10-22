package persistence.sp;

import domain.sp.SpDp;
import domain.sp.SpDpExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SpDpMapper {
    long countByExample(SpDpExample example);

    int deleteByExample(SpDpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpDp record);

    int insertSelective(SpDp record);

    List<SpDp> selectByExampleWithRowbounds(SpDpExample example, RowBounds rowBounds);

    List<SpDp> selectByExample(SpDpExample example);

    SpDp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpDp record, @Param("example") SpDpExample example);

    int updateByExample(@Param("record") SpDp record, @Param("example") SpDpExample example);

    int updateByPrimaryKeySelective(SpDp record);

    int updateByPrimaryKey(SpDp record);
}