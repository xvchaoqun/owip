package persistence.cet;

import domain.cet.CetColumnCourse;
import domain.cet.CetColumnCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetColumnCourseMapper {
    long countByExample(CetColumnCourseExample example);

    int deleteByExample(CetColumnCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetColumnCourse record);

    int insertSelective(CetColumnCourse record);

    List<CetColumnCourse> selectByExampleWithRowbounds(CetColumnCourseExample example, RowBounds rowBounds);

    List<CetColumnCourse> selectByExample(CetColumnCourseExample example);

    CetColumnCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetColumnCourse record, @Param("example") CetColumnCourseExample example);

    int updateByExample(@Param("record") CetColumnCourse record, @Param("example") CetColumnCourseExample example);

    int updateByPrimaryKeySelective(CetColumnCourse record);

    int updateByPrimaryKey(CetColumnCourse record);
}