package persistence.verify;

import domain.verify.VerifyGrowTime;
import domain.verify.VerifyGrowTimeExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface VerifyGrowTimeMapper {
    long countByExample(VerifyGrowTimeExample example);

    int deleteByExample(VerifyGrowTimeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(VerifyGrowTime record);

    int insertSelective(VerifyGrowTime record);

    List<VerifyGrowTime> selectByExampleWithRowbounds(VerifyGrowTimeExample example, RowBounds rowBounds);

    List<VerifyGrowTime> selectByExample(VerifyGrowTimeExample example);

    VerifyGrowTime selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") VerifyGrowTime record, @Param("example") VerifyGrowTimeExample example);

    int updateByExample(@Param("record") VerifyGrowTime record, @Param("example") VerifyGrowTimeExample example);

    int updateByPrimaryKeySelective(VerifyGrowTime record);

    int updateByPrimaryKey(VerifyGrowTime record);
}