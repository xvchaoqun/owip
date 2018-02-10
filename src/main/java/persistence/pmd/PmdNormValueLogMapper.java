package persistence.pmd;

import domain.pmd.PmdNormValueLog;
import domain.pmd.PmdNormValueLogExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdNormValueLogMapper {
    long countByExample(PmdNormValueLogExample example);

    int deleteByExample(PmdNormValueLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdNormValueLog record);

    int insertSelective(PmdNormValueLog record);

    List<PmdNormValueLog> selectByExampleWithRowbounds(PmdNormValueLogExample example, RowBounds rowBounds);

    List<PmdNormValueLog> selectByExample(PmdNormValueLogExample example);

    PmdNormValueLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdNormValueLog record, @Param("example") PmdNormValueLogExample example);

    int updateByExample(@Param("record") PmdNormValueLog record, @Param("example") PmdNormValueLogExample example);

    int updateByPrimaryKeySelective(PmdNormValueLog record);

    int updateByPrimaryKey(PmdNormValueLog record);
}