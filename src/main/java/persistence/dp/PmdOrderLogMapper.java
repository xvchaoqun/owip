package persistence.dp;

import domain.dp.PmdOrderLog;
import domain.dp.PmdOrderLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdOrderLogMapper {
    long countByExample(PmdOrderLogExample example);

    int deleteByExample(PmdOrderLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdOrderLog record);

    int insertSelective(PmdOrderLog record);

    List<PmdOrderLog> selectByExampleWithRowbounds(PmdOrderLogExample example, RowBounds rowBounds);

    List<PmdOrderLog> selectByExample(PmdOrderLogExample example);

    PmdOrderLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdOrderLog record, @Param("example") PmdOrderLogExample example);

    int updateByExample(@Param("record") PmdOrderLog record, @Param("example") PmdOrderLogExample example);

    int updateByPrimaryKeySelective(PmdOrderLog record);

    int updateByPrimaryKey(PmdOrderLog record);
}