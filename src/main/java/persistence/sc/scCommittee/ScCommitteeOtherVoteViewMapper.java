package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeOtherVoteView;
import domain.sc.scCommittee.ScCommitteeOtherVoteViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeOtherVoteViewMapper {
    long countByExample(ScCommitteeOtherVoteViewExample example);

    List<ScCommitteeOtherVoteView> selectByExampleWithRowbounds(ScCommitteeOtherVoteViewExample example, RowBounds rowBounds);

    List<ScCommitteeOtherVoteView> selectByExample(ScCommitteeOtherVoteViewExample example);
}