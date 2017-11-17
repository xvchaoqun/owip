package persistence.pmd;

import domain.pmd.PmdPayParty;
import domain.pmd.PmdPayPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdPayPartyMapper {
    long countByExample(PmdPayPartyExample example);

    int deleteByExample(PmdPayPartyExample example);

    int deleteByPrimaryKey(Integer partyId);

    int insert(PmdPayParty record);

    int insertSelective(PmdPayParty record);

    List<PmdPayParty> selectByExampleWithRowbounds(PmdPayPartyExample example, RowBounds rowBounds);

    List<PmdPayParty> selectByExample(PmdPayPartyExample example);

    PmdPayParty selectByPrimaryKey(Integer partyId);

    int updateByExampleSelective(@Param("record") PmdPayParty record, @Param("example") PmdPayPartyExample example);

    int updateByExample(@Param("record") PmdPayParty record, @Param("example") PmdPayPartyExample example);

    int updateByPrimaryKeySelective(PmdPayParty record);

    int updateByPrimaryKey(PmdPayParty record);
}