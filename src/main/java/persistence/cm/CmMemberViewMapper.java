package persistence.cm;

import domain.cm.CmMemberView;
import domain.cm.CmMemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CmMemberViewMapper {
    long countByExample(CmMemberViewExample example);

    List<CmMemberView> selectByExampleWithRowbounds(CmMemberViewExample example, RowBounds rowBounds);

    List<CmMemberView> selectByExample(CmMemberViewExample example);
}