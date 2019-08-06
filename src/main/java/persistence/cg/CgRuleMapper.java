package persistence.cg;

import domain.cg.CgRule;
import domain.cg.CgRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CgRuleMapper {
    long countByExample(CgRuleExample example);

    int deleteByExample(CgRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CgRule record);

    int insertSelective(CgRule record);

    List<CgRule> selectByExampleWithRowbounds(CgRuleExample example, RowBounds rowBounds);

    List<CgRule> selectByExample(CgRuleExample example);

    CgRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CgRule record, @Param("example") CgRuleExample example);

    int updateByExample(@Param("record") CgRule record, @Param("example") CgRuleExample example);

    int updateByPrimaryKeySelective(CgRule record);

    int updateByPrimaryKey(CgRule record);
}