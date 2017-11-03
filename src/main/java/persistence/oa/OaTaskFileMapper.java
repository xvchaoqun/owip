package persistence.oa;

import domain.oa.OaTaskFile;
import domain.oa.OaTaskFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface OaTaskFileMapper {
    long countByExample(OaTaskFileExample example);

    int deleteByExample(OaTaskFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OaTaskFile record);

    int insertSelective(OaTaskFile record);

    List<OaTaskFile> selectByExampleWithRowbounds(OaTaskFileExample example, RowBounds rowBounds);

    List<OaTaskFile> selectByExample(OaTaskFileExample example);

    OaTaskFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OaTaskFile record, @Param("example") OaTaskFileExample example);

    int updateByExample(@Param("record") OaTaskFile record, @Param("example") OaTaskFileExample example);

    int updateByPrimaryKeySelective(OaTaskFile record);

    int updateByPrimaryKey(OaTaskFile record);
}