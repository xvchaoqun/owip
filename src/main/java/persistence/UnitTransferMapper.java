package persistence;

import domain.UnitTransfer;
import domain.UnitTransferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitTransferMapper {
    int countByExample(UnitTransferExample example);

    int deleteByExample(UnitTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitTransfer record);

    int insertSelective(UnitTransfer record);

    List<UnitTransfer> selectByExampleWithRowbounds(UnitTransferExample example, RowBounds rowBounds);

    List<UnitTransfer> selectByExample(UnitTransferExample example);

    UnitTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitTransfer record, @Param("example") UnitTransferExample example);

    int updateByExample(@Param("record") UnitTransfer record, @Param("example") UnitTransferExample example);

    int updateByPrimaryKeySelective(UnitTransfer record);

    int updateByPrimaryKey(UnitTransfer record);
}