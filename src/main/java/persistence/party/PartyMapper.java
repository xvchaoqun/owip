package persistence.party;

import domain.party.Party;
import domain.party.PartyExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyMapper {
    int countByExample(PartyExample example);

    int deleteByExample(PartyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Party record);

    int insertSelective(Party record);

    List<Party> selectByExampleWithRowbounds(PartyExample example, RowBounds rowBounds);

    List<Party> selectByExample(PartyExample example);

    Party selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Party record, @Param("example") PartyExample example);

    int updateByExample(@Param("record") Party record, @Param("example") PartyExample example);

    int updateByPrimaryKeySelective(Party record);

    int updateByPrimaryKey(Party record);
}