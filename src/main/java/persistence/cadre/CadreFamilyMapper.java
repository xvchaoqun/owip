package persistence.cadre;

import domain.cadre.CadreFamily;
import domain.cadre.CadreFamilyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreFamilyMapper {
    long countByExample(CadreFamilyExample example);

    int deleteByExample(CadreFamilyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreFamily record);

    int insertSelective(CadreFamily record);

    List<CadreFamily> selectByExampleWithRowbounds(CadreFamilyExample example, RowBounds rowBounds);

    List<CadreFamily> selectByExample(CadreFamilyExample example);

    CadreFamily selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreFamily record, @Param("example") CadreFamilyExample example);

    int updateByExample(@Param("record") CadreFamily record, @Param("example") CadreFamilyExample example);

    int updateByPrimaryKeySelective(CadreFamily record);

    int updateByPrimaryKey(CadreFamily record);
}