package persistence.cadre;

import domain.cadre.CadreEvaResult;
import domain.cadre.CadreEvaResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreEvaResultMapper {
    long countByExample(CadreEvaResultExample example);

    int deleteByExample(CadreEvaResultExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreEvaResult record);

    int insertSelective(CadreEvaResult record);

    List<CadreEvaResult> selectByExampleWithRowbounds(CadreEvaResultExample example, RowBounds rowBounds);

    List<CadreEvaResult> selectByExample(CadreEvaResultExample example);

    CadreEvaResult selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreEvaResult record, @Param("example") CadreEvaResultExample example);

    int updateByExample(@Param("record") CadreEvaResult record, @Param("example") CadreEvaResultExample example);

    int updateByPrimaryKeySelective(CadreEvaResult record);

    int updateByPrimaryKey(CadreEvaResult record);
}