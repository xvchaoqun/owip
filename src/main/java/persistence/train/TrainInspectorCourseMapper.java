package persistence.train;

import domain.train.TrainInspectorCourse;
import domain.train.TrainInspectorCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface TrainInspectorCourseMapper {
    int countByExample(TrainInspectorCourseExample example);

    int deleteByExample(TrainInspectorCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TrainInspectorCourse record);

    int insertSelective(TrainInspectorCourse record);

    List<TrainInspectorCourse> selectByExampleWithRowbounds(TrainInspectorCourseExample example, RowBounds rowBounds);

    List<TrainInspectorCourse> selectByExample(TrainInspectorCourseExample example);

    TrainInspectorCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TrainInspectorCourse record, @Param("example") TrainInspectorCourseExample example);

    int updateByExample(@Param("record") TrainInspectorCourse record, @Param("example") TrainInspectorCourseExample example);

    int updateByPrimaryKeySelective(TrainInspectorCourse record);

    int updateByPrimaryKey(TrainInspectorCourse record);
}