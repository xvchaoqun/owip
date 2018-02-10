package persistence.sc.scCommittee;

import domain.sc.scCommittee.ScCommitteeTopicView;
import domain.sc.scCommittee.ScCommitteeTopicViewExample;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ScCommitteeTopicViewMapper {
    long countByExample(ScCommitteeTopicViewExample example);

    List<ScCommitteeTopicView> selectByExampleWithRowbounds(ScCommitteeTopicViewExample example, RowBounds rowBounds);

    List<ScCommitteeTopicView> selectByExample(ScCommitteeTopicViewExample example);
}