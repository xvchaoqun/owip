package persistence.unit;

import domain.unit.UnitTeamPlan;
import domain.unit.UnitTeamPlanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitTeamPlanMapper {
    long countByExample(UnitTeamPlanExample example);

    int deleteByExample(UnitTeamPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitTeamPlan record);

    int insertSelective(UnitTeamPlan record);

    List<UnitTeamPlan> selectByExampleWithRowbounds(UnitTeamPlanExample example, RowBounds rowBounds);

    List<UnitTeamPlan> selectByExample(UnitTeamPlanExample example);

    UnitTeamPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitTeamPlan record, @Param("example") UnitTeamPlanExample example);

    int updateByExample(@Param("record") UnitTeamPlan record, @Param("example") UnitTeamPlanExample example);

    int updateByPrimaryKeySelective(UnitTeamPlan record);

    int updateByPrimaryKey(UnitTeamPlan record);
}