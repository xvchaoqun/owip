package persistence.cadre;

import domain.cadre.CadreCompanyFile;
import domain.cadre.CadreCompanyFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreCompanyFileMapper {
    long countByExample(CadreCompanyFileExample example);

    int deleteByExample(CadreCompanyFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreCompanyFile record);

    int insertSelective(CadreCompanyFile record);

    List<CadreCompanyFile> selectByExampleWithRowbounds(CadreCompanyFileExample example, RowBounds rowBounds);

    List<CadreCompanyFile> selectByExample(CadreCompanyFileExample example);

    CadreCompanyFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreCompanyFile record, @Param("example") CadreCompanyFileExample example);

    int updateByExample(@Param("record") CadreCompanyFile record, @Param("example") CadreCompanyFileExample example);

    int updateByPrimaryKeySelective(CadreCompanyFile record);

    int updateByPrimaryKey(CadreCompanyFile record);
}