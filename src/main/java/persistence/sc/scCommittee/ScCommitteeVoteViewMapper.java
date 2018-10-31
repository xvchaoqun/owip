package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scCommittee.ScCommitteeVoteViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeVoteViewMapper {
    long countByExample(ScCommitteeVoteViewExample example);

    List<ScCommitteeVoteView> selectByExampleWithRowbounds(ScCommitteeVoteViewExample example, RowBounds rowBounds);

    List<ScCommitteeVoteView> selectByExample(ScCommitteeVoteViewExample example);
}