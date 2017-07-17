package persistence.party;

import domain.party.EnterApply;
import domain.party.EnterApplyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface EnterApplyMapper {
    int countByExample(EnterApplyExample example);

    int deleteByExample(EnterApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EnterApply record);

    int insertSelective(EnterApply record);

    List<EnterApply> selectByExampleWithRowbounds(EnterApplyExample example, RowBounds rowBounds);

    List<EnterApply> selectByExample(EnterApplyExample example);

    EnterApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EnterApply record, @Param("example") EnterApplyExample example);

    int updateByExample(@Param("record") EnterApply record, @Param("example") EnterApplyExample example);

    int updateByPrimaryKeySelective(EnterApply record);

    int updateByPrimaryKey(EnterApply record);
}