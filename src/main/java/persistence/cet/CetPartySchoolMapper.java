package persistence.cet;

import domain.cet.CetPartySchool;
import domain.cet.CetPartySchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface CetPartySchoolMapper {
    long countByExample(CetPartySchoolExample example);

    int deleteByExample(CetPartySchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CetPartySchool record);

    int insertSelective(CetPartySchool record);

    List<CetPartySchool> selectByExampleWithRowbounds(CetPartySchoolExample example, RowBounds rowBounds);

    List<CetPartySchool> selectByExample(CetPartySchoolExample example);

    CetPartySchool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CetPartySchool record, @Param("example") CetPartySchoolExample example);

    int updateByExample(@Param("record") CetPartySchool record, @Param("example") CetPartySchoolExample example);

    int updateByPrimaryKeySelective(CetPartySchool record);

    int updateByPrimaryKey(CetPartySchool record);
}