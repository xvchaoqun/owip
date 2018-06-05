package persistence.cla;

import domain.cla.ClaApplyFile;
import domain.cla.ClaApplyFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaApplyFileMapper {
    long countByExample(ClaApplyFileExample example);

    int deleteByExample(ClaApplyFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaApplyFile record);

    int insertSelective(ClaApplyFile record);

    List<ClaApplyFile> selectByExampleWithRowbounds(ClaApplyFileExample example, RowBounds rowBounds);

    List<ClaApplyFile> selectByExample(ClaApplyFileExample example);

    ClaApplyFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaApplyFile record, @Param("example") ClaApplyFileExample example);

    int updateByExample(@Param("record") ClaApplyFile record, @Param("example") ClaApplyFileExample example);

    int updateByPrimaryKeySelective(ClaApplyFile record);

    int updateByPrimaryKey(ClaApplyFile record);
}