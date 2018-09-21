package persistence.cadre;

import domain.cadre.CadreEva;
import domain.cadre.CadreEvaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreEvaMapper {
    long countByExample(CadreEvaExample example);

    int deleteByExample(CadreEvaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreEva record);

    int insertSelective(CadreEva record);

    List<CadreEva> selectByExampleWithRowbounds(CadreEvaExample example, RowBounds rowBounds);

    List<CadreEva> selectByExample(CadreEvaExample example);

    CadreEva selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreEva record, @Param("example") CadreEvaExample example);

    int updateByExample(@Param("record") CadreEva record, @Param("example") CadreEvaExample example);

    int updateByPrimaryKeySelective(CadreEva record);

    int updateByPrimaryKey(CadreEva record);
}