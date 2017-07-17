package persistence.party;

import domain.party.PartyStaticView;
import domain.party.PartyStaticViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PartyStaticViewMapper {
    long countByExample(PartyStaticViewExample example);

    List<PartyStaticView> selectByExampleWithRowbounds(PartyStaticViewExample example, RowBounds rowBounds);

    List<PartyStaticView> selectByExample(PartyStaticViewExample example);
}