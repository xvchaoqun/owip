package persistence;

import domain.CadreFamliy;
import domain.CadreFamliyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreFamliyMapper {
    int countByExample(CadreFamliyExample example);

    int deleteByExample(CadreFamliyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreFamliy record);

    int insertSelective(CadreFamliy record);

    List<CadreFamliy> selectByExampleWithRowbounds(CadreFamliyExample example, RowBounds rowBounds);

    List<CadreFamliy> selectByExample(CadreFamliyExample example);

    CadreFamliy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreFamliy record, @Param("example") CadreFamliyExample example);

    int updateByExample(@Param("record") CadreFamliy record, @Param("example") CadreFamliyExample example);

    int updateByPrimaryKeySelective(CadreFamliy record);

    int updateByPrimaryKey(CadreFamliy record);
}