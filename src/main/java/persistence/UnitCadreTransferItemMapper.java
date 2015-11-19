package persistence;

import domain.UnitCadreTransferItem;
import domain.UnitCadreTransferItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitCadreTransferItemMapper {
    int countByExample(UnitCadreTransferItemExample example);

    int deleteByExample(UnitCadreTransferItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitCadreTransferItem record);

    int insertSelective(UnitCadreTransferItem record);

    List<UnitCadreTransferItem> selectByExampleWithRowbounds(UnitCadreTransferItemExample example, RowBounds rowBounds);

    List<UnitCadreTransferItem> selectByExample(UnitCadreTransferItemExample example);

    UnitCadreTransferItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitCadreTransferItem record, @Param("example") UnitCadreTransferItemExample example);

    int updateByExample(@Param("record") UnitCadreTransferItem record, @Param("example") UnitCadreTransferItemExample example);

    int updateByPrimaryKeySelective(UnitCadreTransferItem record);

    int updateByPrimaryKey(UnitCadreTransferItem record);
}