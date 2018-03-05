package persistence.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetCourseMapper {
    long countByExample(CetCourseExample example);

    int deleteByExample(CetCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetCourse record);

    int insertSelective(CetCourse record);

    List<CetCourse> selectByExampleWithRowbounds(CetCourseExample example, RowBounds rowBounds);

    List<CetCourse> selectByExample(CetCourseExample example);

    CetCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetCourse record, @Param("example") CetCourseExample example);

    int updateByExample(@Param("record") CetCourse record, @Param("example") CetCourseExample example);

    int updateByPrimaryKeySelective(CetCourse record);

    int updateByPrimaryKey(CetCourse record);
}