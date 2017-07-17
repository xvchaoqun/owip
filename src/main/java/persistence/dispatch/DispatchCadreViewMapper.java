package persistence.dispatch;

import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchCadreViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface DispatchCadreViewMapper {
    int countByExample(DispatchCadreViewExample example);

    List<DispatchCadreView> selectByExampleWithRowbounds(DispatchCadreViewExample example, RowBounds rowBounds);

    List<DispatchCadreView> selectByExample(DispatchCadreViewExample example);
}