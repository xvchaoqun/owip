package persistence.cet;

import domain.cet.CetPartyView;
import domain.cet.CetPartyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetPartyViewMapper {
    long countByExample(CetPartyViewExample example);

    List<CetPartyView> selectByExampleWithRowbounds(CetPartyViewExample example, RowBounds rowBounds);

    List<CetPartyView> selectByExample(CetPartyViewExample example);
}