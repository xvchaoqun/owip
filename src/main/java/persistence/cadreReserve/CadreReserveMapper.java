package persistence.cadreReserve;

import domain.cadreReserve.CadreReserve;
import domain.cadreReserve.CadreReserveExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreReserveMapper {
    long countByExample(CadreReserveExample example);

    int deleteByExample(CadreReserveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreReserve record);

    int insertSelective(CadreReserve record);

    List<CadreReserve> selectByExampleWithRowbounds(CadreReserveExample example, RowBounds rowBounds);

    List<CadreReserve> selectByExample(CadreReserveExample example);

    CadreReserve selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreReserve record, @Param("example") CadreReserveExample example);

    int updateByExample(@Param("record") CadreReserve record, @Param("example") CadreReserveExample example);

    int updateByPrimaryKeySelective(CadreReserve record);

    int updateByPrimaryKey(CadreReserve record);
}