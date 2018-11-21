package persistence.cadre;

import domain.cadre.CadreCompanyView;
import domain.cadre.CadreCompanyViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreCompanyViewMapper {
    long countByExample(CadreCompanyViewExample example);

    List<CadreCompanyView> selectByExampleWithRowbounds(CadreCompanyViewExample example, RowBounds rowBounds);

    List<CadreCompanyView> selectByExample(CadreCompanyViewExample example);
}