package persistence.crs;

import domain.crs.CrsExpert;
import domain.crs.CrsExpertExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CrsExpertMapper {
    long countByExample(CrsExpertExample example);

    int deleteByExample(CrsExpertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsExpert record);

    int insertSelective(CrsExpert record);

    List<CrsExpert> selectByExampleWithRowbounds(CrsExpertExample example, RowBounds rowBounds);

    List<CrsExpert> selectByExample(CrsExpertExample example);

    CrsExpert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsExpert record, @Param("example") CrsExpertExample example);

    int updateByExample(@Param("record") CrsExpert record, @Param("example") CrsExpertExample example);

    int updateByPrimaryKeySelective(CrsExpert record);

    int updateByPrimaryKey(CrsExpert record);
}