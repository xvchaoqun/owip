package persistence.crs;

import domain.crs.CrsPostExpert;
import domain.crs.CrsPostExpertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CrsPostExpertMapper {
    long countByExample(CrsPostExpertExample example);

    int deleteByExample(CrsPostExpertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CrsPostExpert record);

    int insertSelective(CrsPostExpert record);

    List<CrsPostExpert> selectByExampleWithRowbounds(CrsPostExpertExample example, RowBounds rowBounds);

    List<CrsPostExpert> selectByExample(CrsPostExpertExample example);

    CrsPostExpert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CrsPostExpert record, @Param("example") CrsPostExpertExample example);

    int updateByExample(@Param("record") CrsPostExpert record, @Param("example") CrsPostExpertExample example);

    int updateByPrimaryKeySelective(CrsPostExpert record);

    int updateByPrimaryKey(CrsPostExpert record);
}