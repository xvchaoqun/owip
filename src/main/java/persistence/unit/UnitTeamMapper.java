package persistence.unit;

import domain.unit.UnitTeam;
import domain.unit.UnitTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitTeamMapper {
    long countByExample(UnitTeamExample example);

    int deleteByExample(UnitTeamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitTeam record);

    int insertSelective(UnitTeam record);

    List<UnitTeam> selectByExampleWithRowbounds(UnitTeamExample example, RowBounds rowBounds);

    List<UnitTeam> selectByExample(UnitTeamExample example);

    UnitTeam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitTeam record, @Param("example") UnitTeamExample example);

    int updateByExample(@Param("record") UnitTeam record, @Param("example") UnitTeamExample example);

    int updateByPrimaryKeySelective(UnitTeam record);

    int updateByPrimaryKey(UnitTeam record);
}