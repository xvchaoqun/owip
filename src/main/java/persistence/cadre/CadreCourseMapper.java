package persistence.cadre;

import domain.cadre.CadreCourse;
import domain.cadre.CadreCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreCourseMapper {
    int countByExample(CadreCourseExample example);

    int deleteByExample(CadreCourseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreCourse record);

    int insertSelective(CadreCourse record);

    List<CadreCourse> selectByExampleWithRowbounds(CadreCourseExample example, RowBounds rowBounds);

    List<CadreCourse> selectByExample(CadreCourseExample example);

    CadreCourse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreCourse record, @Param("example") CadreCourseExample example);

    int updateByExample(@Param("record") CadreCourse record, @Param("example") CadreCourseExample example);

    int updateByPrimaryKeySelective(CadreCourse record);

    int updateByPrimaryKey(CadreCourse record);
}