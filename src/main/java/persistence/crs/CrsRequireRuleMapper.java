package persistence.crs;

import domain.crs.CrsRequireRule;
import domain.crs.CrsRequireRuleExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsRequireRuleMapper {
    long countByExample(CrsRequireRuleExample example);

    int deleteByExample(CrsRequireRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsRequireRule record);

    int insertSelective(CrsRequireRule record);

    List<CrsRequireRule> selectByExampleWithRowbounds(CrsRequireRuleExample example, RowBounds rowBounds);

    List<CrsRequireRule> selectByExample(CrsRequireRuleExample example);

    CrsRequireRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsRequireRule record, @Param("example") CrsRequireRuleExample example);

    int updateByExample(@Param("record") CrsRequireRule record, @Param("example") CrsRequireRuleExample example);

    int updateByPrimaryKeySelective(CrsRequireRule record);

    int updateByPrimaryKey(CrsRequireRule record);
}