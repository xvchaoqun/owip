package persistence.dispatch;

import domain.dispatch.DispatchUnit;
import domain.dispatch.DispatchUnitExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchUnitMapper {
    long countByExample(DispatchUnitExample example);

    int deleteByExample(DispatchUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchUnit record);

    int insertSelective(DispatchUnit record);

    List<DispatchUnit> selectByExampleWithRowbounds(DispatchUnitExample example, RowBounds rowBounds);

    List<DispatchUnit> selectByExample(DispatchUnitExample example);

    DispatchUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchUnit record, @Param("example") DispatchUnitExample example);

    int updateByExample(@Param("record") DispatchUnit record, @Param("example") DispatchUnitExample example);

    int updateByPrimaryKeySelective(DispatchUnit record);

    int updateByPrimaryKey(DispatchUnit record);
}