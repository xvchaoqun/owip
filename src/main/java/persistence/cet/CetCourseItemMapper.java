package persistence.cet;

import domain.cet.CetCourseItem;
import domain.cet.CetCourseItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetCourseItemMapper {
    long countByExample(CetCourseItemExample example);

    int deleteByExample(CetCourseItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetCourseItem record);

    int insertSelective(CetCourseItem record);

    List<CetCourseItem> selectByExampleWithRowbounds(CetCourseItemExample example, RowBounds rowBounds);

    List<CetCourseItem> selectByExample(CetCourseItemExample example);

    CetCourseItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetCourseItem record, @Param("example") CetCourseItemExample example);

    int updateByExample(@Param("record") CetCourseItem record, @Param("example") CetCourseItemExample example);

    int updateByPrimaryKeySelective(CetCourseItem record);

    int updateByPrimaryKey(CetCourseItem record);
}