package persistence.leader;

import domain.leader.LeaderView;
import domain.leader.LeaderViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface LeaderViewMapper {
    long countByExample(LeaderViewExample example);

    List<LeaderView> selectByExampleWithRowbounds(LeaderViewExample example, RowBounds rowBounds);

    List<LeaderView> selectByExample(LeaderViewExample example);
}