package persistence.cet;

import domain.cet.CetPlanCourse;
import domain.cet.CetPlanCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPlanCourseMapper {
    long countByExample(CetPlanCourseExample example);

    int deleteByExample(CetPlanCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetPlanCourse record);

    int insertSelective(CetPlanCourse record);

    List<CetPlanCourse> selectByExampleWithRowbounds(CetPlanCourseExample example, RowBounds rowBounds);

    List<CetPlanCourse> selectByExample(CetPlanCourseExample example);

    CetPlanCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetPlanCourse record, @Param("example") CetPlanCourseExample example);

    int updateByExample(@Param("record") CetPlanCourse record, @Param("example") CetPlanCourseExample example);

    int updateByPrimaryKeySelective(CetPlanCourse record);

    int updateByPrimaryKey(CetPlanCourse record);
}