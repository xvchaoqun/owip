package persistence.sc.scGroup;

import domain.sc.scGroup.ScGroupMemberView;
import domain.sc.scGroup.ScGroupMemberViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScGroupMemberViewMapper {
    long countByExample(ScGroupMemberViewExample example);

    List<ScGroupMemberView> selectByExampleWithRowbounds(ScGroupMemberViewExample example, RowBounds rowBounds);

    List<ScGroupMemberView> selectByExample(ScGroupMemberViewExample example);
}