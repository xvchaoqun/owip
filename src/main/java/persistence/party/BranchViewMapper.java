package persistence.party;

import domain.party.BranchView;
import domain.party.BranchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface BranchViewMapper {
    int countByExample(BranchViewExample example);

    List<BranchView> selectByExampleWithRowbounds(BranchViewExample example, RowBounds rowBounds);

    List<BranchView> selectByExample(BranchViewExample example);
}