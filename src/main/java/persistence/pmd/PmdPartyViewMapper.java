package persistence.pmd;

import domain.pmd.PmdPartyView;
import domain.pmd.PmdPartyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdPartyViewMapper {
    long countByExample(PmdPartyViewExample example);

    List<PmdPartyView> selectByExampleWithRowbounds(PmdPartyViewExample example, RowBounds rowBounds);

    List<PmdPartyView> selectByExample(PmdPartyViewExample example);
}