package persistence.pmd;

import domain.pmd.PmdPayPartyView;
import domain.pmd.PmdPayPartyViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdPayPartyViewMapper {
    long countByExample(PmdPayPartyViewExample example);

    List<PmdPayPartyView> selectByExampleWithRowbounds(PmdPayPartyViewExample example, RowBounds rowBounds);

    List<PmdPayPartyView> selectByExample(PmdPayPartyViewExample example);
}