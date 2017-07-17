package persistence.modify;

import domain.modify.ModifyTableApply;
import domain.modify.ModifyTableApplyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ModifyTableApplyMapper {
    int countByExample(ModifyTableApplyExample example);

    int deleteByExample(ModifyTableApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ModifyTableApply record);

    int insertSelective(ModifyTableApply record);

    List<ModifyTableApply> selectByExampleWithRowbounds(ModifyTableApplyExample example, RowBounds rowBounds);

    List<ModifyTableApply> selectByExample(ModifyTableApplyExample example);

    ModifyTableApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ModifyTableApply record, @Param("example") ModifyTableApplyExample example);

    int updateByExample(@Param("record") ModifyTableApply record, @Param("example") ModifyTableApplyExample example);

    int updateByPrimaryKeySelective(ModifyTableApply record);

    int updateByPrimaryKey(ModifyTableApply record);
}