package persistence.sp;

import domain.sp.SpCg;
import domain.sp.SpCgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SpCgMapper {
    long countByExample(SpCgExample example);

    int deleteByExample(SpCgExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpCg record);

    int insertSelective(SpCg record);

    List<SpCg> selectByExampleWithRowbounds(SpCgExample example, RowBounds rowBounds);

    List<SpCg> selectByExample(SpCgExample example);

    SpCg selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpCg record, @Param("example") SpCgExample example);

    int updateByExample(@Param("record") SpCg record, @Param("example") SpCgExample example);

    int updateByPrimaryKeySelective(SpCg record);

    int updateByPrimaryKey(SpCg record);
}