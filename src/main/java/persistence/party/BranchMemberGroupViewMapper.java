package persistence.party;

import domain.party.BranchMemberGroupView;
import domain.party.BranchMemberGroupViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface BranchMemberGroupViewMapper {
    long countByExample(BranchMemberGroupViewExample example);

    List<BranchMemberGroupView> selectByExampleWithRowbounds(BranchMemberGroupViewExample example, RowBounds rowBounds);

    List<BranchMemberGroupView> selectByExample(BranchMemberGroupViewExample example);
}