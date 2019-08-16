package persistence.pm;

import domain.pm.PmQuarterParty;
import domain.pm.PmQuarterPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmQuarterPartyMapper {
    long countByExample(PmQuarterPartyExample example);

    int deleteByExample(PmQuarterPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmQuarterParty record);

    int insertSelective(PmQuarterParty record);

    List<PmQuarterParty> selectByExampleWithRowbounds(PmQuarterPartyExample example, RowBounds rowBounds);

    List<PmQuarterParty> selectByExample(PmQuarterPartyExample example);

    PmQuarterParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmQuarterParty record, @Param("example") PmQuarterPartyExample example);

    int updateByExample(@Param("record") PmQuarterParty record, @Param("example") PmQuarterPartyExample example);

    int updateByPrimaryKeySelective(PmQuarterParty record);

    int updateByPrimaryKey(PmQuarterParty record);
}