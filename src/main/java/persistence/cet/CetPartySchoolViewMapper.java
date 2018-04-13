package persistence.cet;

import domain.cet.CetPartySchoolView;
import domain.cet.CetPartySchoolViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetPartySchoolViewMapper {
    long countByExample(CetPartySchoolViewExample example);

    List<CetPartySchoolView> selectByExampleWithRowbounds(CetPartySchoolViewExample example, RowBounds rowBounds);

    List<CetPartySchoolView> selectByExample(CetPartySchoolViewExample example);
}