package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeMemberView;
import domain.sc.scCommittee.ScCommitteeMemberViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeMemberViewMapper {
    long countByExample(ScCommitteeMemberViewExample example);

    List<ScCommitteeMemberView> selectByExampleWithRowbounds(ScCommitteeMemberViewExample example, RowBounds rowBounds);

    List<ScCommitteeMemberView> selectByExample(ScCommitteeMemberViewExample example);
}