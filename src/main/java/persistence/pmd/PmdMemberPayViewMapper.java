package persistence.pmd;

import domain.pmd.PmdMemberPayView;
import domain.pmd.PmdMemberPayViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdMemberPayViewMapper {
    long countByExample(PmdMemberPayViewExample example);

    List<PmdMemberPayView> selectByExampleWithRowbounds(PmdMemberPayViewExample example, RowBounds rowBounds);

    List<PmdMemberPayView> selectByExample(PmdMemberPayViewExample example);
}