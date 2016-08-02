package persistence.cadre;

import domain.cadre.CadreConcat;
import domain.cadre.CadreConcatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreConcatMapper {
    int countByExample(CadreConcatExample example);

    int deleteByExample(CadreConcatExample example);

    int deleteByPrimaryKey(Integer cadreId);

    int insert(CadreConcat record);

    int insertSelective(CadreConcat record);

    List<CadreConcat> selectByExampleWithRowbounds(CadreConcatExample example, RowBounds rowBounds);

    List<CadreConcat> selectByExample(CadreConcatExample example);

    CadreConcat selectByPrimaryKey(Integer cadreId);

    int updateByExampleSelective(@Param("record") CadreConcat record, @Param("example") CadreConcatExample example);

    int updateByExample(@Param("record") CadreConcat record, @Param("example") CadreConcatExample example);

    int updateByPrimaryKeySelective(CadreConcat record);

    int updateByPrimaryKey(CadreConcat record);
}