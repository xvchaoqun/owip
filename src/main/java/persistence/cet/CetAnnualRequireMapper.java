package persistence.cet;

import domain.cet.CetAnnualRequire;
import domain.cet.CetAnnualRequireExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetAnnualRequireMapper {
    long countByExample(CetAnnualRequireExample example);

    int deleteByExample(CetAnnualRequireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetAnnualRequire record);

    int insertSelective(CetAnnualRequire record);

    List<CetAnnualRequire> selectByExampleWithRowbounds(CetAnnualRequireExample example, RowBounds rowBounds);

    List<CetAnnualRequire> selectByExample(CetAnnualRequireExample example);

    CetAnnualRequire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetAnnualRequire record, @Param("example") CetAnnualRequireExample example);

    int updateByExample(@Param("record") CetAnnualRequire record, @Param("example") CetAnnualRequireExample example);

    int updateByPrimaryKeySelective(CetAnnualRequire record);

    int updateByPrimaryKey(CetAnnualRequire record);
}