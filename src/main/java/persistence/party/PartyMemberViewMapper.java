package persistence.party;

import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyMemberViewMapper {
    long countByExample(PartyMemberViewExample example);

    List<PartyMemberView> selectByExampleWithRowbounds(PartyMemberViewExample example, RowBounds rowBounds);

    List<PartyMemberView> selectByExample(PartyMemberViewExample example);
}