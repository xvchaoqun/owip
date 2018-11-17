package persistence.pmd;

import domain.pmd.PmdPayView;
import domain.pmd.PmdPayViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface PmdPayViewMapper {
    long countByExample(PmdPayViewExample example);

    List<PmdPayView> selectByExampleWithRowbounds(PmdPayViewExample example, RowBounds rowBounds);

    List<PmdPayView> selectByExample(PmdPayViewExample example);
}