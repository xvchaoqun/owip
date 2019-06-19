package persistence.ps;

import domain.ps.PsParty;
import domain.ps.PsPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PsPartyMapper {
    long countByExample(PsPartyExample example);

    int deleteByExample(PsPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PsParty record);

    int insertSelective(PsParty record);

    List<PsParty> selectByExampleWithRowbounds(PsPartyExample example, RowBounds rowBounds);

    List<PsParty> selectByExample(PsPartyExample example);

    PsParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PsParty record, @Param("example") PsPartyExample example);

    int updateByExample(@Param("record") PsParty record, @Param("example") PsPartyExample example);

    int updateByPrimaryKeySelective(PsParty record);

    int updateByPrimaryKey(PsParty record);
}