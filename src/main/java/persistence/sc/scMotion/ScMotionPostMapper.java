package persistence.sc.scMotion;

import domain.sc.scMotion.ScMotionPost;
import domain.sc.scMotion.ScMotionPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScMotionPostMapper {
    long countByExample(ScMotionPostExample example);

    int deleteByExample(ScMotionPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScMotionPost record);

    int insertSelective(ScMotionPost record);

    List<ScMotionPost> selectByExampleWithRowbounds(ScMotionPostExample example, RowBounds rowBounds);

    List<ScMotionPost> selectByExample(ScMotionPostExample example);

    ScMotionPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScMotionPost record, @Param("example") ScMotionPostExample example);

    int updateByExample(@Param("record") ScMotionPost record, @Param("example") ScMotionPostExample example);

    int updateByPrimaryKeySelective(ScMotionPost record);

    int updateByPrimaryKey(ScMotionPost record);
}