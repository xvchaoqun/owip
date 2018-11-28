package persistence.dispatch;

import domain.dispatch.DispatchUnitView;
import domain.dispatch.DispatchUnitViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface DispatchUnitViewMapper {
    long countByExample(DispatchUnitViewExample example);

    List<DispatchUnitView> selectByExampleWithRowbounds(DispatchUnitViewExample example, RowBounds rowBounds);

    List<DispatchUnitView> selectByExample(DispatchUnitViewExample example);
}