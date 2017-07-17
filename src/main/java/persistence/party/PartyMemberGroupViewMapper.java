package persistence.party;

import domain.party.PartyMemberGroupView;
import domain.party.PartyMemberGroupViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyMemberGroupViewMapper {
    int countByExample(PartyMemberGroupViewExample example);

    List<PartyMemberGroupView> selectByExampleWithRowbounds(PartyMemberGroupViewExample example, RowBounds rowBounds);

    List<PartyMemberGroupView> selectByExample(PartyMemberGroupViewExample example);
}