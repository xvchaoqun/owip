package persistence.cet;

import domain.cet.CetPlanCourseObjResult;
import domain.cet.CetPlanCourseObjResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPlanCourseObjResultMapper {
    long countByExample(CetPlanCourseObjResultExample example);

    int deleteByExample(CetPlanCourseObjResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetPlanCourseObjResult record);

    int insertSelective(CetPlanCourseObjResult record);

    List<CetPlanCourseObjResult> selectByExampleWithRowbounds(CetPlanCourseObjResultExample example, RowBounds rowBounds);

    List<CetPlanCourseObjResult> selectByExample(CetPlanCourseObjResultExample example);

    CetPlanCourseObjResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetPlanCourseObjResult record, @Param("example") CetPlanCourseObjResultExample example);

    int updateByExample(@Param("record") CetPlanCourseObjResult record, @Param("example") CetPlanCourseObjResultExample example);

    int updateByPrimaryKeySelective(CetPlanCourseObjResult record);

    int updateByPrimaryKey(CetPlanCourseObjResult record);
}