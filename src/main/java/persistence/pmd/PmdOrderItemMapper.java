package persistence.pmd;

import domain.pmd.PmdOrderItem;
import domain.pmd.PmdOrderItemExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdOrderItemMapper {
    long countByExample(PmdOrderItemExample example);

    int deleteByExample(PmdOrderItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdOrderItem record);

    int insertSelective(PmdOrderItem record);

    List<PmdOrderItem> selectByExampleWithRowbounds(PmdOrderItemExample example, RowBounds rowBounds);

    List<PmdOrderItem> selectByExample(PmdOrderItemExample example);

    PmdOrderItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdOrderItem record, @Param("example") PmdOrderItemExample example);

    int updateByExample(@Param("record") PmdOrderItem record, @Param("example") PmdOrderItemExample example);

    int updateByPrimaryKeySelective(PmdOrderItem record);

    int updateByPrimaryKey(PmdOrderItem record);
}