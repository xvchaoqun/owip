package persistence;

import domain.UnitCadreTransfer;
import domain.UnitCadreTransferExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitCadreTransferMapper {
    int countByExample(UnitCadreTransferExample example);

    int deleteByExample(UnitCadreTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitCadreTransfer record);

    int insertSelective(UnitCadreTransfer record);

    List<UnitCadreTransfer> selectByExampleWithRowbounds(UnitCadreTransferExample example, RowBounds rowBounds);

    List<UnitCadreTransfer> selectByExample(UnitCadreTransferExample example);

    UnitCadreTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitCadreTransfer record, @Param("example") UnitCadreTransferExample example);

    int updateByExample(@Param("record") UnitCadreTransfer record, @Param("example") UnitCadreTransferExample example);

    int updateByPrimaryKeySelective(UnitCadreTransfer record);

    int updateByPrimaryKey(UnitCadreTransfer record);
}