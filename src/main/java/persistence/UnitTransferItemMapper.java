package persistence;

import domain.UnitTransferItem;
import domain.UnitTransferItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitTransferItemMapper {
    int countByExample(UnitTransferItemExample example);

    int deleteByExample(UnitTransferItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitTransferItem record);

    int insertSelective(UnitTransferItem record);

    List<UnitTransferItem> selectByExampleWithRowbounds(UnitTransferItemExample example, RowBounds rowBounds);

    List<UnitTransferItem> selectByExample(UnitTransferItemExample example);

    UnitTransferItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitTransferItem record, @Param("example") UnitTransferItemExample example);

    int updateByExample(@Param("record") UnitTransferItem record, @Param("example") UnitTransferItemExample example);

    int updateByPrimaryKeySelective(UnitTransferItem record);

    int updateByPrimaryKey(UnitTransferItem record);
}