package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupMemberView;
import domain.sc.scGroup.ScGroupMemberViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScGroupMemberViewMapper {
    long countByExample(ScGroupMemberViewExample example);

    List<ScGroupMemberView> selectByExampleWithRowbounds(ScGroupMemberViewExample example, RowBounds rowBounds);

    List<ScGroupMemberView> selectByExample(ScGroupMemberViewExample example);
}