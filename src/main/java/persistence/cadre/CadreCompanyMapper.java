package persistence.cadre;

import domain.cadre.CadreCompany;
import domain.cadre.CadreCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreCompanyMapper {
    long countByExample(CadreCompanyExample example);

    int deleteByExample(CadreCompanyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreCompany record);

    int insertSelective(CadreCompany record);

    List<CadreCompany> selectByExampleWithRowbounds(CadreCompanyExample example, RowBounds rowBounds);

    List<CadreCompany> selectByExample(CadreCompanyExample example);

    CadreCompany selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreCompany record, @Param("example") CadreCompanyExample example);

    int updateByExample(@Param("record") CadreCompany record, @Param("example") CadreCompanyExample example);

    int updateByPrimaryKeySelective(CadreCompany record);

    int updateByPrimaryKey(CadreCompany record);
}