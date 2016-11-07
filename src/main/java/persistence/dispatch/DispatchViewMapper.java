package persistence.dispatch;

import domain.dispatch.DispatchView;
import domain.dispatch.DispatchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface DispatchViewMapper {
    int countByExample(DispatchViewExample example);

    List<DispatchView> selectByExampleWithRowbounds(DispatchViewExample example, RowBounds rowBounds);

    List<DispatchView> selectByExample(DispatchViewExample example);
}