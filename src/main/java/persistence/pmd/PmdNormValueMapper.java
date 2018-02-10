package persistence.pmd;

import domain.pmd.PmdNormValue;
import domain.pmd.PmdNormValueExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdNormValueMapper {
    long countByExample(PmdNormValueExample example);

    int deleteByExample(PmdNormValueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNormValue record);

    int insertSelective(PmdNormValue record);

    List<PmdNormValue> selectByExampleWithRowbounds(PmdNormValueExample example, RowBounds rowBounds);

    List<PmdNormValue> selectByExample(PmdNormValueExample example);

    PmdNormValue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNormValue record, @Param("example") PmdNormValueExample example);

    int updateByExample(@Param("record") PmdNormValue record, @Param("example") PmdNormValueExample example);

    int updateByPrimaryKeySelective(PmdNormValue record);

    int updateByPrimaryKey(PmdNormValue record);
}