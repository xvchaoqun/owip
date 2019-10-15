package persistence.party;

import domain.party.PartyPunish;
import domain.party.PartyPunishExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyPunishMapper {
    long countByExample(PartyPunishExample example);

    int deleteByExample(PartyPunishExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PartyPunish record);

    int insertSelective(PartyPunish record);

    List<PartyPunish> selectByExampleWithRowbounds(PartyPunishExample example, RowBounds rowBounds);

    List<PartyPunish> selectByExample(PartyPunishExample example);

    PartyPunish selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PartyPunish record, @Param("example") PartyPunishExample example);

    int updateByExample(@Param("record") PartyPunish record, @Param("example") PartyPunishExample example);

    int updateByPrimaryKeySelective(PartyPunish record);

    int updateByPrimaryKey(PartyPunish record);
}