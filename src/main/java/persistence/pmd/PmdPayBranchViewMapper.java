package persistence.pmd;

import domain.pmd.PmdPayBranchView;
import domain.pmd.PmdPayBranchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdPayBranchViewMapper {
    long countByExample(PmdPayBranchViewExample example);

    List<PmdPayBranchView> selectByExampleWithRowbounds(PmdPayBranchViewExample example, RowBounds rowBounds);

    List<PmdPayBranchView> selectByExample(PmdPayBranchViewExample example);
}