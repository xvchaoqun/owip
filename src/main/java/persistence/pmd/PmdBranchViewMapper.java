package persistence.pmd;

import domain.pmd.PmdBranchView;
import domain.pmd.PmdBranchViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface PmdBranchViewMapper {
    long countByExample(PmdBranchViewExample example);

    List<PmdBranchView> selectByExampleWithRowbounds(PmdBranchViewExample example, RowBounds rowBounds);

    List<PmdBranchView> selectByExample(PmdBranchViewExample example);
}