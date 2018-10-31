package persistence.dispatch;

import domain.dispatch.DispatchView;
import domain.dispatch.DispatchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface DispatchViewMapper {
    long countByExample(DispatchViewExample example);

    List<DispatchView> selectByExampleWithRowbounds(DispatchViewExample example, RowBounds rowBounds);

    List<DispatchView> selectByExample(DispatchViewExample example);
}