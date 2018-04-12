package persistence.cet;

import domain.cet.CetUnit;
import domain.cet.CetUnitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetUnitMapper {
    long countByExample(CetUnitExample example);

    int deleteByExample(CetUnitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetUnit record);

    int insertSelective(CetUnit record);

    List<CetUnit> selectByExampleWithRowbounds(CetUnitExample example, RowBounds rowBounds);

    List<CetUnit> selectByExample(CetUnitExample example);

    CetUnit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetUnit record, @Param("example") CetUnitExample example);

    int updateByExample(@Param("record") CetUnit record, @Param("example") CetUnitExample example);

    int updateByPrimaryKeySelective(CetUnit record);

    int updateByPrimaryKey(CetUnit record);
}