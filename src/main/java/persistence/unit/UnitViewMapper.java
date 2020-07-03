package persistence.unit;

import domain.unit.UnitView;
import domain.unit.UnitViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface UnitViewMapper {
    long countByExample(UnitViewExample example);

    List<UnitView> selectByExampleWithRowbounds(UnitViewExample example, RowBounds rowBounds);

    List<UnitView> selectByExample(UnitViewExample example);
}