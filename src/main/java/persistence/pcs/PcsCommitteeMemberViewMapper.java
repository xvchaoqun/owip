package persistence.pcs;

import domain.pcs.PcsCommitteeMemberView;
import domain.pcs.PcsCommitteeMemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PcsCommitteeMemberViewMapper {
    long countByExample(PcsCommitteeMemberViewExample example);

    List<PcsCommitteeMemberView> selectByExampleWithRowbounds(PcsCommitteeMemberViewExample example, RowBounds rowBounds);

    List<PcsCommitteeMemberView> selectByExample(PcsCommitteeMemberViewExample example);
}