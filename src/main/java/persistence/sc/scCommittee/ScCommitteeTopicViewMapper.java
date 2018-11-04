package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopicView;
import domain.sc.scCommittee.ScCommitteeTopicViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface ScCommitteeTopicViewMapper {
    long countByExample(ScCommitteeTopicViewExample example);

    List<ScCommitteeTopicView> selectByExampleWithRowbounds(ScCommitteeTopicViewExample example, RowBounds rowBounds);

    List<ScCommitteeTopicView> selectByExample(ScCommitteeTopicViewExample example);
}