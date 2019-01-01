package persistence.cet;

import domain.cet.CetAnnual;
import domain.cet.CetAnnualExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetAnnualMapper {
    long countByExample(CetAnnualExample example);

    int deleteByExample(CetAnnualExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetAnnual record);

    int insertSelective(CetAnnual record);

    List<CetAnnual> selectByExampleWithRowbounds(CetAnnualExample example, RowBounds rowBounds);

    List<CetAnnual> selectByExample(CetAnnualExample example);

    CetAnnual selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetAnnual record, @Param("example") CetAnnualExample example);

    int updateByExample(@Param("record") CetAnnual record, @Param("example") CetAnnualExample example);

    int updateByPrimaryKeySelective(CetAnnual record);

    int updateByPrimaryKey(CetAnnual record);
}