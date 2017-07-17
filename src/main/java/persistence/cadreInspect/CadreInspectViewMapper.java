package persistence.cadreInspect;

import domain.cadreInspect.CadreInspectView;
import domain.cadreInspect.CadreInspectViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CadreInspectViewMapper {
    long countByExample(CadreInspectViewExample example);

    List<CadreInspectView> selectByExampleWithBLOBsWithRowbounds(CadreInspectViewExample example, RowBounds rowBounds);

    List<CadreInspectView> selectByExampleWithBLOBs(CadreInspectViewExample example);

    List<CadreInspectView> selectByExampleWithRowbounds(CadreInspectViewExample example, RowBounds rowBounds);

    List<CadreInspectView> selectByExample(CadreInspectViewExample example);
}