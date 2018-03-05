package persistence.cet;

import domain.cet.CetExpert;
import domain.cet.CetExpertExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetExpertMapper {
    long countByExample(CetExpertExample example);

    int deleteByExample(CetExpertExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetExpert record);

    int insertSelective(CetExpert record);

    List<CetExpert> selectByExampleWithRowbounds(CetExpertExample example, RowBounds rowBounds);

    List<CetExpert> selectByExample(CetExpertExample example);

    CetExpert selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetExpert record, @Param("example") CetExpertExample example);

    int updateByExample(@Param("record") CetExpert record, @Param("example") CetExpertExample example);

    int updateByPrimaryKeySelective(CetExpert record);

    int updateByPrimaryKey(CetExpert record);
}