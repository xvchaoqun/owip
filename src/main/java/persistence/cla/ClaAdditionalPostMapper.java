package persistence.cla;

import domain.cla.ClaAdditionalPost;
import domain.cla.ClaAdditionalPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ClaAdditionalPostMapper {
    long countByExample(ClaAdditionalPostExample example);

    int deleteByExample(ClaAdditionalPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClaAdditionalPost record);

    int insertSelective(ClaAdditionalPost record);

    List<ClaAdditionalPost> selectByExampleWithRowbounds(ClaAdditionalPostExample example, RowBounds rowBounds);

    List<ClaAdditionalPost> selectByExample(ClaAdditionalPostExample example);

    ClaAdditionalPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClaAdditionalPost record, @Param("example") ClaAdditionalPostExample example);

    int updateByExample(@Param("record") ClaAdditionalPost record, @Param("example") ClaAdditionalPostExample example);

    int updateByPrimaryKeySelective(ClaAdditionalPost record);

    int updateByPrimaryKey(ClaAdditionalPost record);
}