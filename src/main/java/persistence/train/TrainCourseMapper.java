package persistence.train;

import domain.train.TrainCourse;
import domain.train.TrainCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainCourseMapper {
    int countByExample(TrainCourseExample example);

    int deleteByExample(TrainCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainCourse record);

    int insertSelective(TrainCourse record);

    List<TrainCourse> selectByExampleWithRowbounds(TrainCourseExample example, RowBounds rowBounds);

    List<TrainCourse> selectByExample(TrainCourseExample example);

    TrainCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainCourse record, @Param("example") TrainCourseExample example);

    int updateByExample(@Param("record") TrainCourse record, @Param("example") TrainCourseExample example);

    int updateByPrimaryKeySelective(TrainCourse record);

    int updateByPrimaryKey(TrainCourse record);
}