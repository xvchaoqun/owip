package persistence.party;

import domain.party.PartyReport;
import domain.party.PartyReportExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyReportMapper {
    long countByExample(PartyReportExample example);

    int deleteByExample(PartyReportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyReport record);

    int insertSelective(PartyReport record);

    List<PartyReport> selectByExampleWithRowbounds(PartyReportExample example, RowBounds rowBounds);

    List<PartyReport> selectByExample(PartyReportExample example);

    PartyReport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyReport record, @Param("example") PartyReportExample example);

    int updateByExample(@Param("record") PartyReport record, @Param("example") PartyReportExample example);

    int updateByPrimaryKeySelective(PartyReport record);

    int updateByPrimaryKey(PartyReport record);
}