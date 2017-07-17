package persistence.cadre;

import domain.cadre.CadreLeaderUnit;
import domain.cadre.CadreLeaderUnitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreLeaderUnitMapper {
    int countByExample(CadreLeaderUnitExample example);

    int deleteByExample(CadreLeaderUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreLeaderUnit record);

    int insertSelective(CadreLeaderUnit record);

    List<CadreLeaderUnit> selectByExampleWithRowbounds(CadreLeaderUnitExample example, RowBounds rowBounds);

    List<CadreLeaderUnit> selectByExample(CadreLeaderUnitExample example);

    CadreLeaderUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreLeaderUnit record, @Param("example") CadreLeaderUnitExample example);

    int updateByExample(@Param("record") CadreLeaderUnit record, @Param("example") CadreLeaderUnitExample example);

    int updateByPrimaryKeySelective(CadreLeaderUnit record);

    int updateByPrimaryKey(CadreLeaderUnit record);
}