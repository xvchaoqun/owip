package persistence.abroad;

import domain.abroad.ApplySelfModify;
import domain.abroad.ApplySelfModifyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ApplySelfModifyMapper {
    int countByExample(ApplySelfModifyExample example);

    int deleteByExample(ApplySelfModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplySelfModify record);

    int insertSelective(ApplySelfModify record);

    List<ApplySelfModify> selectByExampleWithRowbounds(ApplySelfModifyExample example, RowBounds rowBounds);

    List<ApplySelfModify> selectByExample(ApplySelfModifyExample example);

    ApplySelfModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplySelfModify record, @Param("example") ApplySelfModifyExample example);

    int updateByExample(@Param("record") ApplySelfModify record, @Param("example") ApplySelfModifyExample example);

    int updateByPrimaryKeySelective(ApplySelfModify record);

    int updateByPrimaryKey(ApplySelfModify record);
}