package persistence.party;

import domain.party.PartyMemberView;
import domain.party.PartyMemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PartyMemberViewMapper {
    int countByExample(PartyMemberViewExample example);

    List<PartyMemberView> selectByExampleWithRowbounds(PartyMemberViewExample example, RowBounds rowBounds);

    List<PartyMemberView> selectByExample(PartyMemberViewExample example);
}