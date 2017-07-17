package persistence.unit;

import domain.unit.HistoryUnit;
import domain.unit.HistoryUnitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface HistoryUnitMapper {
    int countByExample(HistoryUnitExample example);

    int deleteByExample(HistoryUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HistoryUnit record);

    int insertSelective(HistoryUnit record);

    List<HistoryUnit> selectByExampleWithRowbounds(HistoryUnitExample example, RowBounds rowBounds);

    List<HistoryUnit> selectByExample(HistoryUnitExample example);

    HistoryUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HistoryUnit record, @Param("example") HistoryUnitExample example);

    int updateByExample(@Param("record") HistoryUnit record, @Param("example") HistoryUnitExample example);

    int updateByPrimaryKeySelective(HistoryUnit record);

    int updateByPrimaryKey(HistoryUnit record);
}