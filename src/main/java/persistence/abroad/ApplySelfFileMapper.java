package persistence.abroad;

import domain.abroad.ApplySelfFile;
import domain.abroad.ApplySelfFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ApplySelfFileMapper {
    int countByExample(ApplySelfFileExample example);

    int deleteByExample(ApplySelfFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplySelfFile record);

    int insertSelective(ApplySelfFile record);

    List<ApplySelfFile> selectByExampleWithRowbounds(ApplySelfFileExample example, RowBounds rowBounds);

    List<ApplySelfFile> selectByExample(ApplySelfFileExample example);

    ApplySelfFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ApplySelfFile record, @Param("example") ApplySelfFileExample example);

    int updateByExample(@Param("record") ApplySelfFile record, @Param("example") ApplySelfFileExample example);

    int updateByPrimaryKeySelective(ApplySelfFile record);

    int updateByPrimaryKey(ApplySelfFile record);
}