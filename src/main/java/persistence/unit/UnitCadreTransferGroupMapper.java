package persistence.unit;

import domain.unit.UnitCadreTransferGroup;
import domain.unit.UnitCadreTransferGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface UnitCadreTransferGroupMapper {
    int countByExample(UnitCadreTransferGroupExample example);

    int deleteByExample(UnitCadreTransferGroupExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UnitCadreTransferGroup record);

    int insertSelective(UnitCadreTransferGroup record);

    List<UnitCadreTransferGroup> selectByExampleWithRowbounds(UnitCadreTransferGroupExample example, RowBounds rowBounds);

    List<UnitCadreTransferGroup> selectByExample(UnitCadreTransferGroupExample example);

    UnitCadreTransferGroup selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UnitCadreTransferGroup record, @Param("example") UnitCadreTransferGroupExample example);

    int updateByExample(@Param("record") UnitCadreTransferGroup record, @Param("example") UnitCadreTransferGroupExample example);

    int updateByPrimaryKeySelective(UnitCadreTransferGroup record);

    int updateByPrimaryKey(UnitCadreTransferGroup record);
}