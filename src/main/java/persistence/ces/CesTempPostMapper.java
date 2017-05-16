package persistence.ces;

import domain.ces.CesTempPost;
import domain.ces.CesTempPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CesTempPostMapper {
    long countByExample(CesTempPostExample example);

    int deleteByExample(CesTempPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CesTempPost record);

    int insertSelective(CesTempPost record);

    List<CesTempPost> selectByExampleWithRowbounds(CesTempPostExample example, RowBounds rowBounds);

    List<CesTempPost> selectByExample(CesTempPostExample example);

    CesTempPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CesTempPost record, @Param("example") CesTempPostExample example);

    int updateByExample(@Param("record") CesTempPost record, @Param("example") CesTempPostExample example);

    int updateByPrimaryKeySelective(CesTempPost record);

    int updateByPrimaryKey(CesTempPost record);
}