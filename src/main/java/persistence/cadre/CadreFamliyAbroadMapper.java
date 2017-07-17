package persistence.cadre;

import domain.cadre.CadreFamliyAbroad;
import domain.cadre.CadreFamliyAbroadExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreFamliyAbroadMapper {
    int countByExample(CadreFamliyAbroadExample example);

    int deleteByExample(CadreFamliyAbroadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreFamliyAbroad record);

    int insertSelective(CadreFamliyAbroad record);

    List<CadreFamliyAbroad> selectByExampleWithRowbounds(CadreFamliyAbroadExample example, RowBounds rowBounds);

    List<CadreFamliyAbroad> selectByExample(CadreFamliyAbroadExample example);

    CadreFamliyAbroad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreFamliyAbroad record, @Param("example") CadreFamliyAbroadExample example);

    int updateByExample(@Param("record") CadreFamliyAbroad record, @Param("example") CadreFamliyAbroadExample example);

    int updateByPrimaryKeySelective(CadreFamliyAbroad record);

    int updateByPrimaryKey(CadreFamliyAbroad record);
}