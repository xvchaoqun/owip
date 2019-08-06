package persistence.cg;

import domain.cg.CgTeam;
import domain.cg.CgTeamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CgTeamMapper {
    long countByExample(CgTeamExample example);

    int deleteByExample(CgTeamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CgTeam record);

    int insertSelective(CgTeam record);

    List<CgTeam> selectByExampleWithRowbounds(CgTeamExample example, RowBounds rowBounds);

    List<CgTeam> selectByExample(CgTeamExample example);

    CgTeam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CgTeam record, @Param("example") CgTeamExample example);

    int updateByExample(@Param("record") CgTeam record, @Param("example") CgTeamExample example);

    int updateByPrimaryKeySelective(CgTeam record);

    int updateByPrimaryKey(CgTeam record);
}