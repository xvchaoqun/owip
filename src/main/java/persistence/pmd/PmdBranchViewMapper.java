package persistence.pmd;

import domain.pmd.PmdBranchView;
import domain.pmd.PmdBranchViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdBranchViewMapper {
    long countByExample(PmdBranchViewExample example);

    List<PmdBranchView> selectByExampleWithRowbounds(PmdBranchViewExample example, RowBounds rowBounds);

    List<PmdBranchView> selectByExample(PmdBranchViewExample example);
}