package persistence.cla;

import domain.cla.ClaApply;
import domain.cla.ClaApplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApplyMapper {
    long countByExample(ClaApplyExample example);

    int deleteByExample(ClaApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApply record);

    int insertSelective(ClaApply record);

    List<ClaApply> selectByExampleWithRowbounds(ClaApplyExample example, RowBounds rowBounds);

    List<ClaApply> selectByExample(ClaApplyExample example);

    ClaApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApply record, @Param("example") ClaApplyExample example);

    int updateByExample(@Param("record") ClaApply record, @Param("example") ClaApplyExample example);

    int updateByPrimaryKeySelective(ClaApply record);

    int updateByPrimaryKey(ClaApply record);
}