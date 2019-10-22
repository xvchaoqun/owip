package persistence.sp;

import domain.sp.SpNpc;
import domain.sp.SpNpcExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface SpNpcMapper {
    long countByExample(SpNpcExample example);

    int deleteByExample(SpNpcExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SpNpc record);

    int insertSelective(SpNpc record);

    List<SpNpc> selectByExampleWithRowbounds(SpNpcExample example, RowBounds rowBounds);

    List<SpNpc> selectByExample(SpNpcExample example);

    SpNpc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SpNpc record, @Param("example") SpNpcExample example);

    int updateByExample(@Param("record") SpNpc record, @Param("example") SpNpcExample example);

    int updateByPrimaryKeySelective(SpNpc record);

    int updateByPrimaryKey(SpNpc record);
}