package persistence.pmd;

import domain.pmd.PmdConfigReset;
import domain.pmd.PmdConfigResetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdConfigResetMapper {
    long countByExample(PmdConfigResetExample example);

    int deleteByExample(PmdConfigResetExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdConfigReset record);

    int insertSelective(PmdConfigReset record);

    List<PmdConfigReset> selectByExampleWithRowbounds(PmdConfigResetExample example, RowBounds rowBounds);

    List<PmdConfigReset> selectByExample(PmdConfigResetExample example);

    PmdConfigReset selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdConfigReset record, @Param("example") PmdConfigResetExample example);

    int updateByExample(@Param("record") PmdConfigReset record, @Param("example") PmdConfigResetExample example);

    int updateByPrimaryKeySelective(PmdConfigReset record);

    int updateByPrimaryKey(PmdConfigReset record);
}