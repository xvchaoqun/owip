package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeVoteView;
import domain.sc.scCommittee.ScCommitteeVoteViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeVoteViewMapper {
    long countByExample(ScCommitteeVoteViewExample example);

    List<ScCommitteeVoteView> selectByExampleWithRowbounds(ScCommitteeVoteViewExample example, RowBounds rowBounds);

    List<ScCommitteeVoteView> selectByExample(ScCommitteeVoteViewExample example);
}