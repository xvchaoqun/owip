package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeView;
import domain.sc.scCommittee.ScCommitteeViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeViewMapper {
    long countByExample(ScCommitteeViewExample example);

    List<ScCommitteeView> selectByExampleWithRowbounds(ScCommitteeViewExample example, RowBounds rowBounds);

    List<ScCommitteeView> selectByExample(ScCommitteeViewExample example);
}