package persistence.crs;

import domain.crs.CrsRuleItem;
import domain.crs.CrsRuleItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsRuleItemMapper {
    long countByExample(CrsRuleItemExample example);

    int deleteByExample(CrsRuleItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsRuleItem record);

    int insertSelective(CrsRuleItem record);

    List<CrsRuleItem> selectByExampleWithRowbounds(CrsRuleItemExample example, RowBounds rowBounds);

    List<CrsRuleItem> selectByExample(CrsRuleItemExample example);

    CrsRuleItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsRuleItem record, @Param("example") CrsRuleItemExample example);

    int updateByExample(@Param("record") CrsRuleItem record, @Param("example") CrsRuleItemExample example);

    int updateByPrimaryKeySelective(CrsRuleItem record);

    int updateByPrimaryKey(CrsRuleItem record);
}