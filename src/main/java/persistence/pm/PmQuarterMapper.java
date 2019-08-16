package persistence.pm;

import domain.pm.PmQuarter;
import domain.pm.PmQuarterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmQuarterMapper {
    long countByExample(PmQuarterExample example);

    int deleteByExample(PmQuarterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmQuarter record);

    int insertSelective(PmQuarter record);

    List<PmQuarter> selectByExampleWithRowbounds(PmQuarterExample example, RowBounds rowBounds);

    List<PmQuarter> selectByExample(PmQuarterExample example);

    PmQuarter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmQuarter record, @Param("example") PmQuarterExample example);

    int updateByExample(@Param("record") PmQuarter record, @Param("example") PmQuarterExample example);

    int updateByPrimaryKeySelective(PmQuarter record);

    int updateByPrimaryKey(PmQuarter record);
}