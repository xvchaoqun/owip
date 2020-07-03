package persistence.cet;

import domain.cet.CetProjectPlan;
import domain.cet.CetProjectPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetProjectPlanMapper {
    long countByExample(CetProjectPlanExample example);

    int deleteByExample(CetProjectPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetProjectPlan record);

    int insertSelective(CetProjectPlan record);

    List<CetProjectPlan> selectByExampleWithRowbounds(CetProjectPlanExample example, RowBounds rowBounds);

    List<CetProjectPlan> selectByExample(CetProjectPlanExample example);

    CetProjectPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetProjectPlan record, @Param("example") CetProjectPlanExample example);

    int updateByExample(@Param("record") CetProjectPlan record, @Param("example") CetProjectPlanExample example);

    int updateByPrimaryKeySelective(CetProjectPlan record);

    int updateByPrimaryKey(CetProjectPlan record);
}