package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeOtherVoteView;
import domain.sc.scCommittee.ScCommitteeOtherVoteViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeOtherVoteViewMapper {
    long countByExample(ScCommitteeOtherVoteViewExample example);

    List<ScCommitteeOtherVoteView> selectByExampleWithRowbounds(ScCommitteeOtherVoteViewExample example, RowBounds rowBounds);

    List<ScCommitteeOtherVoteView> selectByExample(ScCommitteeOtherVoteViewExample example);
}