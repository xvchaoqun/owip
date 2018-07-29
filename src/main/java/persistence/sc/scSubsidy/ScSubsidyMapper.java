package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidy;
import domain.sc.scSubsidy.ScSubsidyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyMapper {
    long countByExample(ScSubsidyExample example);

    int deleteByExample(ScSubsidyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScSubsidy record);

    int insertSelective(ScSubsidy record);

    List<ScSubsidy> selectByExampleWithRowbounds(ScSubsidyExample example, RowBounds rowBounds);

    List<ScSubsidy> selectByExample(ScSubsidyExample example);

    ScSubsidy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScSubsidy record, @Param("example") ScSubsidyExample example);

    int updateByExample(@Param("record") ScSubsidy record, @Param("example") ScSubsidyExample example);

    int updateByPrimaryKeySelective(ScSubsidy record);

    int updateByPrimaryKey(ScSubsidy record);
}