package persistence.crs;

import domain.crs.CrsApplyRule;
import domain.crs.CrsApplyRuleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsApplyRuleMapper {
    long countByExample(CrsApplyRuleExample example);

    int deleteByExample(CrsApplyRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsApplyRule record);

    int insertSelective(CrsApplyRule record);

    List<CrsApplyRule> selectByExampleWithRowbounds(CrsApplyRuleExample example, RowBounds rowBounds);

    List<CrsApplyRule> selectByExample(CrsApplyRuleExample example);

    CrsApplyRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsApplyRule record, @Param("example") CrsApplyRuleExample example);

    int updateByExample(@Param("record") CrsApplyRule record, @Param("example") CrsApplyRuleExample example);

    int updateByPrimaryKeySelective(CrsApplyRule record);

    int updateByPrimaryKey(CrsApplyRule record);
}