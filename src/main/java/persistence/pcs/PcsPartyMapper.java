package persistence.pcs;

import domain.pcs.PcsParty;
import domain.pcs.PcsPartyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PcsPartyMapper {
    long countByExample(PcsPartyExample example);

    int deleteByExample(PcsPartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PcsParty record);

    int insertSelective(PcsParty record);

    List<PcsParty> selectByExampleWithRowbounds(PcsPartyExample example, RowBounds rowBounds);

    List<PcsParty> selectByExample(PcsPartyExample example);

    PcsParty selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PcsParty record, @Param("example") PcsPartyExample example);

    int updateByExample(@Param("record") PcsParty record, @Param("example") PcsPartyExample example);

    int updateByPrimaryKeySelective(PcsParty record);

    int updateByPrimaryKey(PcsParty record);
}