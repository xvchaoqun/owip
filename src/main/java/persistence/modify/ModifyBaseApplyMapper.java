package persistence.modify;

import domain.modify.ModifyBaseApply;
import domain.modify.ModifyBaseApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ModifyBaseApplyMapper {
    int countByExample(ModifyBaseApplyExample example);

    int deleteByExample(ModifyBaseApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ModifyBaseApply record);

    int insertSelective(ModifyBaseApply record);

    List<ModifyBaseApply> selectByExampleWithRowbounds(ModifyBaseApplyExample example, RowBounds rowBounds);

    List<ModifyBaseApply> selectByExample(ModifyBaseApplyExample example);

    ModifyBaseApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ModifyBaseApply record, @Param("example") ModifyBaseApplyExample example);

    int updateByExample(@Param("record") ModifyBaseApply record, @Param("example") ModifyBaseApplyExample example);

    int updateByPrimaryKeySelective(ModifyBaseApply record);

    int updateByPrimaryKey(ModifyBaseApply record);
}