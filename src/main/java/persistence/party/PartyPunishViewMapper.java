package persistence.party;

import domain.party.PartyPunishView;
import domain.party.PartyPunishViewExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PartyPunishViewMapper {
    long countByExample(PartyPunishViewExample example);

    int deleteByExample(PartyPunishViewExample example);

    int insert(PartyPunishView record);

    int insertSelective(PartyPunishView record);

    List<PartyPunishView> selectByExampleWithRowbounds(PartyPunishViewExample example, RowBounds rowBounds);

    List<PartyPunishView> selectByExample(PartyPunishViewExample example);

    int updateByExampleSelective(@Param("record") PartyPunishView record, @Param("example") PartyPunishViewExample example);

    int updateByExample(@Param("record") PartyPunishView record, @Param("example") PartyPunishViewExample example);
}