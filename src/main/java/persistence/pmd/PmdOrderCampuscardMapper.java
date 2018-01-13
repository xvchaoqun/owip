package persistence.pmd;

import domain.pmd.PmdOrderCampuscard;
import domain.pmd.PmdOrderCampuscardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdOrderCampuscardMapper {
    long countByExample(PmdOrderCampuscardExample example);

    int deleteByExample(PmdOrderCampuscardExample example);

    int deleteByPrimaryKey(String sn);

    int insert(PmdOrderCampuscard record);

    int insertSelective(PmdOrderCampuscard record);

    List<PmdOrderCampuscard> selectByExampleWithRowbounds(PmdOrderCampuscardExample example, RowBounds rowBounds);

    List<PmdOrderCampuscard> selectByExample(PmdOrderCampuscardExample example);

    PmdOrderCampuscard selectByPrimaryKey(String sn);

    int updateByExampleSelective(@Param("record") PmdOrderCampuscard record, @Param("example") PmdOrderCampuscardExample example);

    int updateByExample(@Param("record") PmdOrderCampuscard record, @Param("example") PmdOrderCampuscardExample example);

    int updateByPrimaryKeySelective(PmdOrderCampuscard record);

    int updateByPrimaryKey(PmdOrderCampuscard record);
}