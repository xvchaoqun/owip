package persistence.cr;

import domain.cr.CrRuleItem;
import domain.cr.CrRuleItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrRuleItemMapper {
    long countByExample(CrRuleItemExample example);

    int deleteByExample(CrRuleItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrRuleItem record);

    int insertSelective(CrRuleItem record);

    List<CrRuleItem> selectByExampleWithRowbounds(CrRuleItemExample example, RowBounds rowBounds);

    List<CrRuleItem> selectByExample(CrRuleItemExample example);

    CrRuleItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrRuleItem record, @Param("example") CrRuleItemExample example);

    int updateByExample(@Param("record") CrRuleItem record, @Param("example") CrRuleItemExample example);

    int updateByPrimaryKeySelective(CrRuleItem record);

    int updateByPrimaryKey(CrRuleItem record);
}