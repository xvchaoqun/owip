package persistence.cadre;

import domain.cadre.CadreLeaderUnitView;
import domain.cadre.CadreLeaderUnitViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreLeaderUnitViewMapper {
    long countByExample(CadreLeaderUnitViewExample example);

    List<CadreLeaderUnitView> selectByExampleWithRowbounds(CadreLeaderUnitViewExample example, RowBounds rowBounds);

    List<CadreLeaderUnitView> selectByExample(CadreLeaderUnitViewExample example);
}