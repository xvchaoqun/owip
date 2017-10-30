package persistence.party;

import domain.party.PartyStaticView;
import domain.party.PartyStaticViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PartyStaticViewMapper {
    long countByExample(PartyStaticViewExample example);

    List<PartyStaticView> selectByExampleWithRowbounds(PartyStaticViewExample example, RowBounds rowBounds);

    List<PartyStaticView> selectByExample(PartyStaticViewExample example);
}