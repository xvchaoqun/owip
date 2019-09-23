package persistence.dp;

import domain.dp.PmdOrderSumLog;
import domain.dp.PmdOrderSumLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdOrderSumLogMapper {
    long countByExample(PmdOrderSumLogExample example);

    int deleteByExample(PmdOrderSumLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdOrderSumLog record);

    int insertSelective(PmdOrderSumLog record);

    List<PmdOrderSumLog> selectByExampleWithRowbounds(PmdOrderSumLogExample example, RowBounds rowBounds);

    List<PmdOrderSumLog> selectByExample(PmdOrderSumLogExample example);

    PmdOrderSumLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdOrderSumLog record, @Param("example") PmdOrderSumLogExample example);

    int updateByExample(@Param("record") PmdOrderSumLog record, @Param("example") PmdOrderSumLogExample example);

    int updateByPrimaryKeySelective(PmdOrderSumLog record);

    int updateByPrimaryKey(PmdOrderSumLog record);
}