package persistence.sp;

import domain.sp.SpTeach;
import domain.sp.SpTeachExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SpTeachMapper {
    long countByExample(SpTeachExample example);

    int deleteByExample(SpTeachExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpTeach record);

    int insertSelective(SpTeach record);

    List<SpTeach> selectByExampleWithRowbounds(SpTeachExample example, RowBounds rowBounds);

    List<SpTeach> selectByExample(SpTeachExample example);

    SpTeach selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpTeach record, @Param("example") SpTeachExample example);

    int updateByExample(@Param("record") SpTeach record, @Param("example") SpTeachExample example);

    int updateByPrimaryKeySelective(SpTeach record);

    int updateByPrimaryKey(SpTeach record);
}