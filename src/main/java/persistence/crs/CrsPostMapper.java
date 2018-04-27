package persistence.crs;

import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsPostMapper {
    long countByExample(CrsPostExample example);

    int deleteByExample(CrsPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsPost record);

    int insertSelective(CrsPost record);

    List<CrsPost> selectByExampleWithRowbounds(CrsPostExample example, RowBounds rowBounds);

    List<CrsPost> selectByExample(CrsPostExample example);

    CrsPost selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsPost record, @Param("example") CrsPostExample example);

    int updateByExample(@Param("record") CrsPost record, @Param("example") CrsPostExample example);

    int updateByPrimaryKeySelective(CrsPost record);

    int updateByPrimaryKey(CrsPost record);
}