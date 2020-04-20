package persistence.pmd;

import domain.pmd.PmdFee;
import domain.pmd.PmdFeeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdFeeMapper {
    long countByExample(PmdFeeExample example);

    int deleteByExample(PmdFeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdFee record);

    int insertSelective(PmdFee record);

    List<PmdFee> selectByExampleWithRowbounds(PmdFeeExample example, RowBounds rowBounds);

    List<PmdFee> selectByExample(PmdFeeExample example);

    PmdFee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdFee record, @Param("example") PmdFeeExample example);

    int updateByExample(@Param("record") PmdFee record, @Param("example") PmdFeeExample example);

    int updateByPrimaryKeySelective(PmdFee record);

    int updateByPrimaryKey(PmdFee record);
}