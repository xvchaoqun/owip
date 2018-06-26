package persistence.crs;

import domain.crs.CrsPost;
import domain.crs.CrsPostExample;
import domain.crs.CrsPostWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsPostMapper {
    long countByExample(CrsPostExample example);

    int deleteByExample(CrsPostExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsPostWithBLOBs record);

    int insertSelective(CrsPostWithBLOBs record);

    List<CrsPostWithBLOBs> selectByExampleWithBLOBsWithRowbounds(CrsPostExample example, RowBounds rowBounds);

    List<CrsPostWithBLOBs> selectByExampleWithBLOBs(CrsPostExample example);

    List<CrsPost> selectByExampleWithRowbounds(CrsPostExample example, RowBounds rowBounds);

    List<CrsPost> selectByExample(CrsPostExample example);

    CrsPostWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsPostWithBLOBs record, @Param("example") CrsPostExample example);

    int updateByExampleWithBLOBs(@Param("record") CrsPostWithBLOBs record, @Param("example") CrsPostExample example);

    int updateByExample(@Param("record") CrsPost record, @Param("example") CrsPostExample example);

    int updateByPrimaryKeySelective(CrsPostWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(CrsPostWithBLOBs record);

    int updateByPrimaryKey(CrsPost record);
}