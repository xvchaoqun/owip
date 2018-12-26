package persistence.pmd;

import domain.pmd.PmdOrder;
import domain.pmd.PmdOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdOrderMapper {
    long countByExample(PmdOrderExample example);

    int deleteByExample(PmdOrderExample example);

    int deleteByPrimaryKey(String sn);

    int insert(PmdOrder record);

    int insertSelective(PmdOrder record);

    List<PmdOrder> selectByExampleWithRowbounds(PmdOrderExample example, RowBounds rowBounds);

    List<PmdOrder> selectByExample(PmdOrderExample example);

    PmdOrder selectByPrimaryKey(String sn);

    int updateByExampleSelective(@Param("record") PmdOrder record, @Param("example") PmdOrderExample example);

    int updateByExample(@Param("record") PmdOrder record, @Param("example") PmdOrderExample example);

    int updateByPrimaryKeySelective(PmdOrder record);

    int updateByPrimaryKey(PmdOrder record);
}