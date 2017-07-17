package persistence.cis;

import domain.cis.CisEvaluate;
import domain.cis.CisEvaluateExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CisEvaluateMapper {
    int countByExample(CisEvaluateExample example);

    int deleteByExample(CisEvaluateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CisEvaluate record);

    int insertSelective(CisEvaluate record);

    List<CisEvaluate> selectByExampleWithRowbounds(CisEvaluateExample example, RowBounds rowBounds);

    List<CisEvaluate> selectByExample(CisEvaluateExample example);

    CisEvaluate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CisEvaluate record, @Param("example") CisEvaluateExample example);

    int updateByExample(@Param("record") CisEvaluate record, @Param("example") CisEvaluateExample example);

    int updateByPrimaryKeySelective(CisEvaluate record);

    int updateByPrimaryKey(CisEvaluate record);
}