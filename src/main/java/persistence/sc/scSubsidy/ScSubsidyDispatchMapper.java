package persistence.sc.scSubsidy;

import domain.sc.scSubsidy.ScSubsidyDispatch;
import domain.sc.scSubsidy.ScSubsidyDispatchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ScSubsidyDispatchMapper {
    long countByExample(ScSubsidyDispatchExample example);

    int deleteByExample(ScSubsidyDispatchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScSubsidyDispatch record);

    int insertSelective(ScSubsidyDispatch record);

    List<ScSubsidyDispatch> selectByExampleWithRowbounds(ScSubsidyDispatchExample example, RowBounds rowBounds);

    List<ScSubsidyDispatch> selectByExample(ScSubsidyDispatchExample example);

    ScSubsidyDispatch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScSubsidyDispatch record, @Param("example") ScSubsidyDispatchExample example);

    int updateByExample(@Param("record") ScSubsidyDispatch record, @Param("example") ScSubsidyDispatchExample example);

    int updateByPrimaryKeySelective(ScSubsidyDispatch record);

    int updateByPrimaryKey(ScSubsidyDispatch record);
}