package persistence.modify;

import domain.modify.ModifyBaseItem;
import domain.modify.ModifyBaseItemExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ModifyBaseItemMapper {
    int countByExample(ModifyBaseItemExample example);

    int deleteByExample(ModifyBaseItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ModifyBaseItem record);

    int insertSelective(ModifyBaseItem record);

    List<ModifyBaseItem> selectByExampleWithRowbounds(ModifyBaseItemExample example, RowBounds rowBounds);

    List<ModifyBaseItem> selectByExample(ModifyBaseItemExample example);

    ModifyBaseItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ModifyBaseItem record, @Param("example") ModifyBaseItemExample example);

    int updateByExample(@Param("record") ModifyBaseItem record, @Param("example") ModifyBaseItemExample example);

    int updateByPrimaryKeySelective(ModifyBaseItem record);

    int updateByPrimaryKey(ModifyBaseItem record);
}