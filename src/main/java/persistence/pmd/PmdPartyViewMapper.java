package persistence.pmd;

import domain.pmd.PmdPartyView;
import domain.pmd.PmdPartyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdPartyViewMapper {
    long countByExample(PmdPartyViewExample example);

    List<PmdPartyView> selectByExampleWithRowbounds(PmdPartyViewExample example, RowBounds rowBounds);

    List<PmdPartyView> selectByExample(PmdPartyViewExample example);
}