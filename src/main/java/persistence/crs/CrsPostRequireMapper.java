package persistence.crs;

import domain.crs.CrsPostRequire;
import domain.crs.CrsPostRequireExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsPostRequireMapper {
    long countByExample(CrsPostRequireExample example);

    int deleteByExample(CrsPostRequireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsPostRequire record);

    int insertSelective(CrsPostRequire record);

    List<CrsPostRequire> selectByExampleWithRowbounds(CrsPostRequireExample example, RowBounds rowBounds);

    List<CrsPostRequire> selectByExample(CrsPostRequireExample example);

    CrsPostRequire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsPostRequire record, @Param("example") CrsPostRequireExample example);

    int updateByExample(@Param("record") CrsPostRequire record, @Param("example") CrsPostRequireExample example);

    int updateByPrimaryKeySelective(CrsPostRequire record);

    int updateByPrimaryKey(CrsPostRequire record);
}