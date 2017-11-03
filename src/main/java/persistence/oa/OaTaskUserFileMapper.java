package persistence.oa;

import domain.oa.OaTaskUserFile;
import domain.oa.OaTaskUserFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OaTaskUserFileMapper {
    long countByExample(OaTaskUserFileExample example);

    int deleteByExample(OaTaskUserFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTaskUserFile record);

    int insertSelective(OaTaskUserFile record);

    List<OaTaskUserFile> selectByExampleWithRowbounds(OaTaskUserFileExample example, RowBounds rowBounds);

    List<OaTaskUserFile> selectByExample(OaTaskUserFileExample example);

    OaTaskUserFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTaskUserFile record, @Param("example") OaTaskUserFileExample example);

    int updateByExample(@Param("record") OaTaskUserFile record, @Param("example") OaTaskUserFileExample example);

    int updateByPrimaryKeySelective(OaTaskUserFile record);

    int updateByPrimaryKey(OaTaskUserFile record);
}