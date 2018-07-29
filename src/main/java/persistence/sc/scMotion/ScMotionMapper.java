package persistence.sc.scMotion;

import domain.sc.scMotion.ScMotion;
import domain.sc.scMotion.ScMotionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMotionMapper {
    long countByExample(ScMotionExample example);

    int deleteByExample(ScMotionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMotion record);

    int insertSelective(ScMotion record);

    List<ScMotion> selectByExampleWithRowbounds(ScMotionExample example, RowBounds rowBounds);

    List<ScMotion> selectByExample(ScMotionExample example);

    ScMotion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMotion record, @Param("example") ScMotionExample example);

    int updateByExample(@Param("record") ScMotion record, @Param("example") ScMotionExample example);

    int updateByPrimaryKeySelective(ScMotion record);

    int updateByPrimaryKey(ScMotion record);
}