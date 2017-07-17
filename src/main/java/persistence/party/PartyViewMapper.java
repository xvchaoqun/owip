package persistence.party;

import domain.party.PartyView;
import domain.party.PartyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyViewMapper {
    int countByExample(PartyViewExample example);

    List<PartyView> selectByExampleWithRowbounds(PartyViewExample example, RowBounds rowBounds);

    List<PartyView> selectByExample(PartyViewExample example);
}