package persistence.cet;

import domain.cet.CetTraineeCourse;
import domain.cet.CetTraineeCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeCourseMapper {
    long countByExample(CetTraineeCourseExample example);

    int deleteByExample(CetTraineeCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetTraineeCourse record);

    int insertSelective(CetTraineeCourse record);

    List<CetTraineeCourse> selectByExampleWithRowbounds(CetTraineeCourseExample example, RowBounds rowBounds);

    List<CetTraineeCourse> selectByExample(CetTraineeCourseExample example);

    CetTraineeCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetTraineeCourse record, @Param("example") CetTraineeCourseExample example);

    int updateByExample(@Param("record") CetTraineeCourse record, @Param("example") CetTraineeCourseExample example);

    int updateByPrimaryKeySelective(CetTraineeCourse record);

    int updateByPrimaryKey(CetTraineeCourse record);
}