package persistence.dispatch;

import domain.dispatch.DispatchCadreView;
import domain.dispatch.DispatchCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface DispatchCadreViewMapper {
    long countByExample(DispatchCadreViewExample example);

    List<DispatchCadreView> selectByExampleWithRowbounds(DispatchCadreViewExample example, RowBounds rowBounds);

    List<DispatchCadreView> selectByExample(DispatchCadreViewExample example);
}