package persistence.party;

import domain.party.BranchMemberView;
import domain.party.BranchMemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface BranchMemberViewMapper {
    long countByExample(BranchMemberViewExample example);

    List<BranchMemberView> selectByExampleWithRowbounds(BranchMemberViewExample example, RowBounds rowBounds);

    List<BranchMemberView> selectByExample(BranchMemberViewExample example);
}