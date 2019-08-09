package persistence.cadre;

import domain.cadre.CadrePartyView;
import domain.cadre.CadrePartyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadrePartyViewMapper {
    long countByExample(CadrePartyViewExample example);

    List<CadrePartyView> selectByExampleWithRowbounds(CadrePartyViewExample example, RowBounds rowBounds);

    List<CadrePartyView> selectByExample(CadrePartyViewExample example);
}