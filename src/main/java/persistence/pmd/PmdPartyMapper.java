package persistence.pmd;

import domain.pmd.PmdParty;
import domain.pmd.PmdPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PmdPartyMapper {
    long countByExample(PmdPartyExample example);

    int deleteByExample(PmdPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PmdParty record);

    int insertSelective(PmdParty record);

    List<PmdParty> selectByExampleWithRowbounds(PmdPartyExample example, RowBounds rowBounds);

    List<PmdParty> selectByExample(PmdPartyExample example);

    PmdParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PmdParty record, @Param("example") PmdPartyExample example);

    int updateByExample(@Param("record") PmdParty record, @Param("example") PmdPartyExample example);

    int updateByPrimaryKeySelective(PmdParty record);

    int updateByPrimaryKey(PmdParty record);
}