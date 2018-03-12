package persistence.cet;

import domain.cet.CetTrainCourse;
import domain.cet.CetTrainCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTrainCourseMapper {
    long countByExample(CetTrainCourseExample example);

    int deleteByExample(CetTrainCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTrainCourse record);

    int insertSelective(CetTrainCourse record);

    List<CetTrainCourse> selectByExampleWithRowbounds(CetTrainCourseExample example, RowBounds rowBounds);

    List<CetTrainCourse> selectByExample(CetTrainCourseExample example);

    CetTrainCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTrainCourse record, @Param("example") CetTrainCourseExample example);

    int updateByExample(@Param("record") CetTrainCourse record, @Param("example") CetTrainCourseExample example);

    int updateByPrimaryKeySelective(CetTrainCourse record);

    int updateByPrimaryKey(CetTrainCourse record);
}