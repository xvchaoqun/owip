package persistence.leader;

import domain.leader.LeaderUnitView;
import domain.leader.LeaderUnitViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface LeaderUnitViewMapper {
    long countByExample(LeaderUnitViewExample example);

    List<LeaderUnitView> selectByExampleWithRowbounds(LeaderUnitViewExample example, RowBounds rowBounds);

    List<LeaderUnitView> selectByExample(LeaderUnitViewExample example);
}