package persistence.cet;

import domain.cet.CetProjectObjPlan;
import domain.cet.CetProjectObjPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectObjPlanMapper {
    long countByExample(CetProjectObjPlanExample example);

    int deleteByExample(CetProjectObjPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectObjPlan record);

    int insertSelective(CetProjectObjPlan record);

    List<CetProjectObjPlan> selectByExampleWithRowbounds(CetProjectObjPlanExample example, RowBounds rowBounds);

    List<CetProjectObjPlan> selectByExample(CetProjectObjPlanExample example);

    CetProjectObjPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectObjPlan record, @Param("example") CetProjectObjPlanExample example);

    int updateByExample(@Param("record") CetProjectObjPlan record, @Param("example") CetProjectObjPlanExample example);

    int updateByPrimaryKeySelective(CetProjectObjPlan record);

    int updateByPrimaryKey(CetProjectObjPlan record);
}