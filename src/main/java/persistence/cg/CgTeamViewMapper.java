package persistence.cg;

import domain.cg.CgTeamView;
import domain.cg.CgTeamViewExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CgTeamViewMapper {
    long countByExample(CgTeamViewExample example);

    int deleteByExample(CgTeamViewExample example);

    int insert(CgTeamView record);

    int insertSelective(CgTeamView record);

    List<CgTeamView> selectByExampleWithRowbounds(CgTeamViewExample example, RowBounds rowBounds);

    List<CgTeamView> selectByExample(CgTeamViewExample example);

    int updateByExampleSelective(@Param("record") CgTeamView record, @Param("example") CgTeamViewExample example);

    int updateByExample(@Param("record") CgTeamView record, @Param("example") CgTeamViewExample example);
}