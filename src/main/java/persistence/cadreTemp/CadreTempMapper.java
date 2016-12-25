package persistence.cadreTemp;

import domain.cadreTemp.CadreTemp;
import domain.cadreTemp.CadreTempExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreTempMapper {
    int countByExample(CadreTempExample example);

    int deleteByExample(CadreTempExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreTemp record);

    int insertSelective(CadreTemp record);

    List<CadreTemp> selectByExampleWithRowbounds(CadreTempExample example, RowBounds rowBounds);

    List<CadreTemp> selectByExample(CadreTempExample example);

    CadreTemp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreTemp record, @Param("example") CadreTempExample example);

    int updateByExample(@Param("record") CadreTemp record, @Param("example") CadreTempExample example);

    int updateByPrimaryKeySelective(CadreTemp record);

    int updateByPrimaryKey(CadreTemp record);
}