package persistence.sc.scDispatch;

import domain.sc.scDispatch.ScDispatchView;
import domain.sc.scDispatch.ScDispatchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScDispatchViewMapper {
    long countByExample(ScDispatchViewExample example);

    List<ScDispatchView> selectByExampleWithRowbounds(ScDispatchViewExample example, RowBounds rowBounds);

    List<ScDispatchView> selectByExample(ScDispatchViewExample example);
}