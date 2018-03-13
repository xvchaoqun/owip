package persistence.cet;

import domain.cet.CetCourse;
import domain.cet.CetCourseExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CetCourseMapper {
    long countByExample(CetCourseExample example);

    int deleteByExample(CetCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetCourse record);

    int insertSelective(CetCourse record);

    List<CetCourse> selectByExampleWithBLOBsWithRowbounds(CetCourseExample example, RowBounds rowBounds);

    List<CetCourse> selectByExampleWithBLOBs(CetCourseExample example);

    List<CetCourse> selectByExampleWithRowbounds(CetCourseExample example, RowBounds rowBounds);

    List<CetCourse> selectByExample(CetCourseExample example);

    CetCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetCourse record, @Param("example") CetCourseExample example);

    int updateByExampleWithBLOBs(@Param("record") CetCourse record, @Param("example") CetCourseExample example);

    int updateByExample(@Param("record") CetCourse record, @Param("example") CetCourseExample example);

    int updateByPrimaryKeySelective(CetCourse record);

    int updateByPrimaryKeyWithBLOBs(CetCourse record);

    int updateByPrimaryKey(CetCourse record);
}