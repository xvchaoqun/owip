package persistence.crs;

import domain.crs.CrsPostFile;
import domain.crs.CrsPostFileExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsPostFileMapper {
    long countByExample(CrsPostFileExample example);

    int deleteByExample(CrsPostFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsPostFile record);

    int insertSelective(CrsPostFile record);

    List<CrsPostFile> selectByExampleWithRowbounds(CrsPostFileExample example, RowBounds rowBounds);

    List<CrsPostFile> selectByExample(CrsPostFileExample example);

    CrsPostFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsPostFile record, @Param("example") CrsPostFileExample example);

    int updateByExample(@Param("record") CrsPostFile record, @Param("example") CrsPostFileExample example);

    int updateByPrimaryKeySelective(CrsPostFile record);

    int updateByPrimaryKey(CrsPostFile record);
}