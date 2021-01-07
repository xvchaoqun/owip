package persistence.cadreInspect;

import domain.cadreInspect.CadreInspectView;
import domain.cadreInspect.CadreInspectViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CadreInspectViewMapper {
    long countByExample(CadreInspectViewExample example);

    List<CadreInspectView> selectByExampleWithRowbounds(CadreInspectViewExample example, RowBounds rowBounds);

    List<CadreInspectView> selectByExample(CadreInspectViewExample example);
}