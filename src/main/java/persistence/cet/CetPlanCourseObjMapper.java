package persistence.cet;

import domain.cet.CetPlanCourseObj;
import domain.cet.CetPlanCourseObjExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPlanCourseObjMapper {
    long countByExample(CetPlanCourseObjExample example);

    int deleteByExample(CetPlanCourseObjExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetPlanCourseObj record);

    int insertSelective(CetPlanCourseObj record);

    List<CetPlanCourseObj> selectByExampleWithRowbounds(CetPlanCourseObjExample example, RowBounds rowBounds);

    List<CetPlanCourseObj> selectByExample(CetPlanCourseObjExample example);

    CetPlanCourseObj selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetPlanCourseObj record, @Param("example") CetPlanCourseObjExample example);

    int updateByExample(@Param("record") CetPlanCourseObj record, @Param("example") CetPlanCourseObjExample example);

    int updateByPrimaryKeySelective(CetPlanCourseObj record);

    int updateByPrimaryKey(CetPlanCourseObj record);
}