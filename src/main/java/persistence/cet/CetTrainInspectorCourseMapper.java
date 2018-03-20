package persistence.cet;

import domain.cet.CetTrainInspectorCourse;
import domain.cet.CetTrainInspectorCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainInspectorCourseMapper {
    long countByExample(CetTrainInspectorCourseExample example);

    int deleteByExample(CetTrainInspectorCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainInspectorCourse record);

    int insertSelective(CetTrainInspectorCourse record);

    List<CetTrainInspectorCourse> selectByExampleWithRowbounds(CetTrainInspectorCourseExample example, RowBounds rowBounds);

    List<CetTrainInspectorCourse> selectByExample(CetTrainInspectorCourseExample example);

    CetTrainInspectorCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainInspectorCourse record, @Param("example") CetTrainInspectorCourseExample example);

    int updateByExample(@Param("record") CetTrainInspectorCourse record, @Param("example") CetTrainInspectorCourseExample example);

    int updateByPrimaryKeySelective(CetTrainInspectorCourse record);

    int updateByPrimaryKey(CetTrainInspectorCourse record);
}