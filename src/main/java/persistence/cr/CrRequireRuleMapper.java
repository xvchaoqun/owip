package persistence.cr;

import domain.cr.CrRequireRule;
import domain.cr.CrRequireRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrRequireRuleMapper {
    long countByExample(CrRequireRuleExample example);

    int deleteByExample(CrRequireRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrRequireRule record);

    int insertSelective(CrRequireRule record);

    List<CrRequireRule> selectByExampleWithRowbounds(CrRequireRuleExample example, RowBounds rowBounds);

    List<CrRequireRule> selectByExample(CrRequireRuleExample example);

    CrRequireRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrRequireRule record, @Param("example") CrRequireRuleExample example);

    int updateByExample(@Param("record") CrRequireRule record, @Param("example") CrRequireRuleExample example);

    int updateByPrimaryKeySelective(CrRequireRule record);

    int updateByPrimaryKey(CrRequireRule record);
}