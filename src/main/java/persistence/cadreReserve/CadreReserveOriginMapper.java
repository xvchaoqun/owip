package persistence.cadreReserve;

import domain.cadreReserve.CadreReserveOrigin;
import domain.cadreReserve.CadreReserveOriginExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreReserveOriginMapper {
    long countByExample(CadreReserveOriginExample example);

    int deleteByExample(CadreReserveOriginExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreReserveOrigin record);

    int insertSelective(CadreReserveOrigin record);

    List<CadreReserveOrigin> selectByExampleWithRowbounds(CadreReserveOriginExample example, RowBounds rowBounds);

    List<CadreReserveOrigin> selectByExample(CadreReserveOriginExample example);

    CadreReserveOrigin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreReserveOrigin record, @Param("example") CadreReserveOriginExample example);

    int updateByExample(@Param("record") CadreReserveOrigin record, @Param("example") CadreReserveOriginExample example);

    int updateByPrimaryKeySelective(CadreReserveOrigin record);

    int updateByPrimaryKey(CadreReserveOrigin record);
}