package persistence.pmd;

import domain.pmd.PmdMonth;
import domain.pmd.PmdMonthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdMonthMapper {
    long countByExample(PmdMonthExample example);

    int deleteByExample(PmdMonthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdMonth record);

    int insertSelective(PmdMonth record);

    List<PmdMonth> selectByExampleWithRowbounds(PmdMonthExample example, RowBounds rowBounds);

    List<PmdMonth> selectByExample(PmdMonthExample example);

    PmdMonth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdMonth record, @Param("example") PmdMonthExample example);

    int updateByExample(@Param("record") PmdMonth record, @Param("example") PmdMonthExample example);

    int updateByPrimaryKeySelective(PmdMonth record);

    int updateByPrimaryKey(PmdMonth record);
}