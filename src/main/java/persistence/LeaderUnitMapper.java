package persistence;

import domain.LeaderUnit;
import domain.LeaderUnitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface LeaderUnitMapper {
    int countByExample(LeaderUnitExample example);

    int deleteByExample(LeaderUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LeaderUnit record);

    int insertSelective(LeaderUnit record);

    List<LeaderUnit> selectByExampleWithRowbounds(LeaderUnitExample example, RowBounds rowBounds);

    List<LeaderUnit> selectByExample(LeaderUnitExample example);

    LeaderUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LeaderUnit record, @Param("example") LeaderUnitExample example);

    int updateByExample(@Param("record") LeaderUnit record, @Param("example") LeaderUnitExample example);

    int updateByPrimaryKeySelective(LeaderUnit record);

    int updateByPrimaryKey(LeaderUnit record);
}