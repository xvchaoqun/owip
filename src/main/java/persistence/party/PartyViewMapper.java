package persistence.party;

import domain.party.PartyView;
import domain.party.PartyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PartyViewMapper {
    long countByExample(PartyViewExample example);

    List<PartyView> selectByExampleWithRowbounds(PartyViewExample example, RowBounds rowBounds);

    List<PartyView> selectByExample(PartyViewExample example);
}