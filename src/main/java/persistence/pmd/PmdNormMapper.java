package persistence.pmd;

import domain.pmd.PmdNorm;
import domain.pmd.PmdNormExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdNormMapper {
    long countByExample(PmdNormExample example);

    int deleteByExample(PmdNormExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNorm record);

    int insertSelective(PmdNorm record);

    List<PmdNorm> selectByExampleWithRowbounds(PmdNormExample example, RowBounds rowBounds);

    List<PmdNorm> selectByExample(PmdNormExample example);

    PmdNorm selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNorm record, @Param("example") PmdNormExample example);

    int updateByExample(@Param("record") PmdNorm record, @Param("example") PmdNormExample example);

    int updateByPrimaryKeySelective(PmdNorm record);

    int updateByPrimaryKey(PmdNorm record);
}