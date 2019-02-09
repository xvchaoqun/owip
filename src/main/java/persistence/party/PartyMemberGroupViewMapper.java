package persistence.party;

import domain.party.PartyMemberGroupView;
import domain.party.PartyMemberGroupViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PartyMemberGroupViewMapper {
    long countByExample(PartyMemberGroupViewExample example);

    List<PartyMemberGroupView> selectByExampleWithRowbounds(PartyMemberGroupViewExample example, RowBounds rowBounds);

    List<PartyMemberGroupView> selectByExample(PartyMemberGroupViewExample example);
}