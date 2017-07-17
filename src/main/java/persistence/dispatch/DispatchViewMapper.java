package persistence.dispatch;

import domain.dispatch.DispatchView;
import domain.dispatch.DispatchViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchViewMapper {
    int countByExample(DispatchViewExample example);

    List<DispatchView> selectByExampleWithRowbounds(DispatchViewExample example, RowBounds rowBounds);

    List<DispatchView> selectByExample(DispatchViewExample example);
}