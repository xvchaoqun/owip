package persistence.cet;

import domain.cet.CetUnitView;
import domain.cet.CetUnitViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetUnitViewMapper {
    long countByExample(CetUnitViewExample example);

    List<CetUnitView> selectByExampleWithRowbounds(CetUnitViewExample example, RowBounds rowBounds);

    List<CetUnitView> selectByExample(CetUnitViewExample example);
}