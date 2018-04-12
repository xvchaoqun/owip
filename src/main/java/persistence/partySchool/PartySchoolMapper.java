package persistence.partySchool;

import domain.partySchool.PartySchool;
import domain.partySchool.PartySchoolExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartySchoolMapper {
    long countByExample(PartySchoolExample example);

    int deleteByExample(PartySchoolExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartySchool record);

    int insertSelective(PartySchool record);

    List<PartySchool> selectByExampleWithRowbounds(PartySchoolExample example, RowBounds rowBounds);

    List<PartySchool> selectByExample(PartySchoolExample example);

    PartySchool selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartySchool record, @Param("example") PartySchoolExample example);

    int updateByExample(@Param("record") PartySchool record, @Param("example") PartySchoolExample example);

    int updateByPrimaryKeySelective(PartySchool record);

    int updateByPrimaryKey(PartySchool record);
}