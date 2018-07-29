package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyDc;
import domain.sc.scSubsidy.ScSubsidyDcExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyDcMapper {
    long countByExample(ScSubsidyDcExample example);

    int deleteByExample(ScSubsidyDcExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScSubsidyDc record);

    int insertSelective(ScSubsidyDc record);

    List<ScSubsidyDc> selectByExampleWithRowbounds(ScSubsidyDcExample example, RowBounds rowBounds);

    List<ScSubsidyDc> selectByExample(ScSubsidyDcExample example);

    ScSubsidyDc selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScSubsidyDc record, @Param("example") ScSubsidyDcExample example);

    int updateByExample(@Param("record") ScSubsidyDc record, @Param("example") ScSubsidyDcExample example);

    int updateByPrimaryKeySelective(ScSubsidyDc record);

    int updateByPrimaryKey(ScSubsidyDc record);
}