package persistence.cadre;

import domain.cadre.CadreFamilyAbroad;
import domain.cadre.CadreFamilyAbroadExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CadreFamilyAbroadMapper {
    long countByExample(CadreFamilyAbroadExample example);

    int deleteByExample(CadreFamilyAbroadExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CadreFamilyAbroad record);

    int insertSelective(CadreFamilyAbroad record);

    List<CadreFamilyAbroad> selectByExampleWithRowbounds(CadreFamilyAbroadExample example, RowBounds rowBounds);

    List<CadreFamilyAbroad> selectByExample(CadreFamilyAbroadExample example);

    CadreFamilyAbroad selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CadreFamilyAbroad record, @Param("example") CadreFamilyAbroadExample example);

    int updateByExample(@Param("record") CadreFamilyAbroad record, @Param("example") CadreFamilyAbroadExample example);

    int updateByPrimaryKeySelective(CadreFamilyAbroad record);

    int updateByPrimaryKey(CadreFamilyAbroad record);
}