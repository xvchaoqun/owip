package persistence.cadre;

import domain.cadre.CadreLeader;
import domain.cadre.CadreLeaderExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreLeaderMapper {
    int countByExample(CadreLeaderExample example);

    int deleteByExample(CadreLeaderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreLeader record);

    int insertSelective(CadreLeader record);

    List<CadreLeader> selectByExampleWithRowbounds(CadreLeaderExample example, RowBounds rowBounds);

    List<CadreLeader> selectByExample(CadreLeaderExample example);

    CadreLeader selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreLeader record, @Param("example") CadreLeaderExample example);

    int updateByExample(@Param("record") CadreLeader record, @Param("example") CadreLeaderExample example);

    int updateByPrimaryKeySelective(CadreLeader record);

    int updateByPrimaryKey(CadreLeader record);
}