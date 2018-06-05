package persistence.cla;

import domain.cla.ClaApplyModify;
import domain.cla.ClaApplyModifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApplyModifyMapper {
    long countByExample(ClaApplyModifyExample example);

    int deleteByExample(ClaApplyModifyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApplyModify record);

    int insertSelective(ClaApplyModify record);

    List<ClaApplyModify> selectByExampleWithRowbounds(ClaApplyModifyExample example, RowBounds rowBounds);

    List<ClaApplyModify> selectByExample(ClaApplyModifyExample example);

    ClaApplyModify selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApplyModify record, @Param("example") ClaApplyModifyExample example);

    int updateByExample(@Param("record") ClaApplyModify record, @Param("example") ClaApplyModifyExample example);

    int updateByPrimaryKeySelective(ClaApplyModify record);

    int updateByPrimaryKey(ClaApplyModify record);
}