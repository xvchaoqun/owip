package persistence.cet;

import domain.cet.CetCourseType;
import domain.cet.CetCourseTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetCourseTypeMapper {
    long countByExample(CetCourseTypeExample example);

    int deleteByExample(CetCourseTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetCourseType record);

    int insertSelective(CetCourseType record);

    List<CetCourseType> selectByExampleWithRowbounds(CetCourseTypeExample example, RowBounds rowBounds);

    List<CetCourseType> selectByExample(CetCourseTypeExample example);

    CetCourseType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetCourseType record, @Param("example") CetCourseTypeExample example);

    int updateByExample(@Param("record") CetCourseType record, @Param("example") CetCourseTypeExample example);

    int updateByPrimaryKeySelective(CetCourseType record);

    int updateByPrimaryKey(CetCourseType record);
}